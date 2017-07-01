package shafir.irena.vetstreet;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    @BindView(R.id.btnNews)
    BootstrapButton btnNews;
    @BindView(R.id.btnHealth)
    BootstrapButton btnHealth;
    @BindView(R.id.btnTips)
    BootstrapButton btnTips;
    @BindView(R.id.btnPetGeeks)
    BootstrapButton btnPetGeeks;
    @BindView(R.id.mainContainer)
    ConstraintLayout mainContainer;
    Unbinder unbinder;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnNews)
    public void onBtnNewsClicked() {
        btnNews.setText("Latest News");
        btnHealth.setText("Dog News");
        btnTips.setText("Cat News");
        btnPetGeeks.setText("dr marty becker");

        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, new LatestNewsFragment())
                        .addToBackStack("main").commit();
            }
        });
    }

    @OnClick(R.id.btnHealth)
    public void onBtnHealthClicked() {
    }

    @OnClick(R.id.btnTips)
    public void onBtnTipsClicked() {
    }

    @OnClick(R.id.btnPetGeeks)
    public void onBtnPetGeeksClicked() {
    }
}
