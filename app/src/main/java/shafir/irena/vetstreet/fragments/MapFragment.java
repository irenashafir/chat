package shafir.irena.vetstreet.fragments;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import shafir.irena.vetstreet.utils.GetNearbyPlaces;


/**
 * Created by irena on 17/07/2017.
 */

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private static final int RC_CODE = 123;
    public static final String PROXIMITY_RADIUS = "5000";
    private GoogleMap mMap;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMapAsync(this);
        view.setClickable(true);
    }

    private void myMapSettings() {
        if (!checkMyLocationPermissions()) return;
        //noinspection MissingPermission
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LatLng latLng = myLocation();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 1));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 1));
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                mMap.getUiSettings().setZoomGesturesEnabled(true);
                mMap.getUiSettings().setScrollGesturesEnabled(true);
                mMap.getUiSettings().setCompassEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                mMap.moveCamera(CameraUpdateFactory.zoomIn());
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
                marker.setTitle("You are Here!");
                marker.showInfoWindow();
                return false;
            }
        });
    }
    @NonNull
    private LatLng myLocation() {
        Location myLocation = mMap.getMyLocation();
        if (myLocation ==null){
            LatLng def = new LatLng(100, 100);
            Toast.makeText(getContext(), "Please open GPS", Toast.LENGTH_SHORT).show();
            return def;
        }
        findAVet();
        return new LatLng((myLocation.getLatitude()), myLocation.getLongitude());
    }

    private boolean checkMyLocationPermissions(){
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String [] permissions =  new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(getActivity(), permissions,RC_CODE );
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            myMapSettings();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        myMapSettings();
    }


    public void findAVet(){
        if (mMap.getMyLocation() == null) {
            Toast.makeText(getContext(), "Please open GPS", Toast.LENGTH_SHORT).show();
        } else {
            double lat = mMap.getMyLocation().getLatitude();
            double lng = mMap.getMyLocation().getLongitude();
            String url = getUrl(lat, lng);
            Object dataTransfer[] = new Object[2];
            dataTransfer[0] = mMap;
            dataTransfer[1] = url;
            GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
            getNearbyPlaces.execute(dataTransfer);
        }
    }


    @NonNull
    private String getUrl(double lat, double lng){
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location=" + lat + "," + lng);
        googlePlaceUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type=" + "veterinarian");
        googlePlaceUrl.append("&keyword=veterinarian");
        googlePlaceUrl.append("&key="+ "AIzaSyBtcmBPGVCRB4GbQWoXt_d4sBr_jJvAwTQ");
        return googlePlaceUrl.toString();
    }
}
