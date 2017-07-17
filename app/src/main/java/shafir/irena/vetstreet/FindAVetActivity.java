package shafir.irena.vetstreet;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import shafir.irena.vetstreet.fragments.MapFragment;

public class FindAVetActivity extends FragmentActivity {


    @BindView(R.id.etLocation)
    EditText etLocation;
    @BindView(R.id.frameMap)
    FrameLayout frameMap;
    @BindView(R.id.map)
    ConstraintLayout map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_avet);
        ButterKnife.bind(this);

        MapFragment myLocation = new MapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameMap, myLocation).commit();

    }


}
