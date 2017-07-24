package shafir.irena.vetstreet.fragments;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by irena on 17/07/2017.
 */

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private static final int RC_CODE = 123;
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
                mMap.addMarker(new MarkerOptions().position(latLng));

                return false;
            }
        });
    }


    @NonNull
    private LatLng myLocation() {
        Location myLocation = mMap.getMyLocation();
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


    public void vetSearch(){
        String myStringLocaion = myLocation().toString();
        String search = "https://www.google.co.il/maps/search/" +
                "%D7%95%D7%98%D7%A8%D7%99%D7%A0%D7%A8%D7%99%D7%9D%E2%80%AD/@" +
                myStringLocaion +
                ",15z/data=!3m1!4b1";
    }



}
