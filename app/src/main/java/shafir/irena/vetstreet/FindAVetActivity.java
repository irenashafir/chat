package shafir.irena.vetstreet;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindAVetActivity extends FragmentActivity {

    @BindView(R.id.frameMap)
    FrameLayout frameMap;
    @BindView(R.id.map)
    ConstraintLayout map;

    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_avet);
        ButterKnife.bind(this);

//        MapFragment mapFragment = new MapFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.frameMap, mapFragment).commit();

        placePicker();
    }


    private void placePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST){
            if (resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(this, data);

            }
        }
    }



}
