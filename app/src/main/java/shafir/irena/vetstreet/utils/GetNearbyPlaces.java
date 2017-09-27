package shafir.irena.vetstreet.utils;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static shafir.irena.vetstreet.utils.DataParser.VICINITY;

/**
 * Created by irena on 23/09/2017.
 */

public class GetNearbyPlaces extends AsyncTask<Object, String, String> {
    String googlePlacesData;
    GoogleMap mMap;
    String url;

    @Override
    protected String doInBackground(Object... params) {
        mMap = (GoogleMap) params[0];
        url = (String) params[1];
        try {
            DownloadURL downloadURL = new DownloadURL();
            googlePlacesData = downloadURL.readURL(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String , String >> nearByPlaces = null;
        DataParser parser = new DataParser();
        nearByPlaces = parser.parse(s);
        showNearByPlaces(nearByPlaces);
    }

    private void showNearByPlaces(List<HashMap<String, String>> nearByPlacesList){
        for (int i = 0; i < nearByPlacesList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String , String> googlePlace = nearByPlacesList.get(i);

            String placeName = googlePlace.get("name");
            String vicinity = googlePlace.get(VICINITY);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName+ " : " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(markerOptions);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }

}
