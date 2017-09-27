package shafir.irena.vetstreet.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by irena on 23/09/2017.
 */

public class DataParser {
    public static final String NAME = "place_name";
    public static final String VICINITY = "vicinity";
    public static final String GEO = "geometry";
    public static final String REF = "reference";
    public static final String RESULTS = "results";


    private HashMap<String, String> getPlace(JSONObject googlePlaceJson){
        HashMap<String, String > googlePlaceMap = new HashMap<>();
        String placeName= "-NA-";
        String vicinity = "-NA-";
        String lat = "";
        String lng="";
        String ref="";

        try {
        if (!googlePlaceJson.isNull("name")){
            placeName = googlePlaceJson.getString("name");
        }
        if (!googlePlaceJson.isNull(VICINITY)){
            vicinity = googlePlaceJson.getString(VICINITY);
        }

            lat = googlePlaceJson.getJSONObject(GEO).getJSONObject("location").getString("lat");
            lng = googlePlaceJson.getJSONObject(GEO).getJSONObject("location").getString("lng");
            ref = googlePlaceJson.getString(REF);

            googlePlaceMap.put("name", placeName);
            googlePlaceMap.put(VICINITY, vicinity);
            googlePlaceMap.put("lat", lat);
            googlePlaceMap.put("lng", lng);
            googlePlaceMap.put(REF, ref);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlaceMap;
    }


    private List<HashMap<String, String>> getPlaces (JSONArray jsonArray){
        int count = jsonArray.length();
        List<HashMap<String, String >> placesList = new ArrayList<>();
        HashMap<String, String> placeMap = null;

        for (int i = 0; i < count; i++) {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placesList;
    }

    public List<HashMap<String, String>> parse(String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject((jsonData));
            jsonArray = jsonObject.getJSONArray(RESULTS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }

}
