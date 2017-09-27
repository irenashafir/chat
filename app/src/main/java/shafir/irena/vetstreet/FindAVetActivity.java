package shafir.irena.vetstreet;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import shafir.irena.vetstreet.fragments.MapFragment;

public class FindAVetActivity extends FragmentActivity {

    @BindView(R.id.map)
    FrameLayout map;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_avet);
        ButterKnife.bind(this);


        getSupportFragmentManager().beginTransaction().replace(R.id.map, new MapFragment()).
                addToBackStack("map").commit();
    }




}
