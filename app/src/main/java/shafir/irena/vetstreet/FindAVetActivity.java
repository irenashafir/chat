package shafir.irena.vetstreet;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindAVetActivity extends FragmentActivity {

    private static final int RC_CODE = 123;
    @BindView(R.id.map)
    ConstraintLayout map;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_avet);
        ButterKnife.bind(this);

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = builder.build(this);
            startActivityForResult(intent, RC_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CODE){
            if (resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(this, data);

                String address = place.getAddress().toString();
                Toast.makeText(this, address, Toast.LENGTH_SHORT).show();
            }
        }
    }


//    PendingResult<PlaceLikelihoodBuffer> result =
//            Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
//            PlaceDetectionApi placeDetectionApi;
//
//    result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
//        @Override
//        public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
//            for (PlaceLikelihood placeLikelihood : likelyPlaces) {
//                Log.i(TAG, String.format("Place '%s' has likelihood: %g",
//                        placeLikelihood.getPlace().getName(),
//                        placeLikelihood.getLikelihood()));
//            }
//            likelyPlaces.release();
//        }
//    });
}
