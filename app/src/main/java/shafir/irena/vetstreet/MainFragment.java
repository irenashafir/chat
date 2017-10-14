package shafir.irena.vetstreet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import shafir.irena.vetstreet.fragments.HealthFragment;
import shafir.irena.vetstreet.fragments.NewsFragment;
import shafir.irena.vetstreet.fragments.PetGeeksFragment;
import shafir.irena.vetstreet.fragments.PetNewsFragment;
import shafir.irena.vetstreet.fragments.TipsFragment;


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
    Unbinder unbinder;

    @BindView(R.id.cvPuppy)
    CircularImageView cvPuppy;
    @BindView(R.id.cvKitten)
    CircularImageView cvKitten;


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
        getFragmentManager().beginTransaction().replace(R.id.mainFrame, new NewsFragment()).addToBackStack("news").commit();
    }


    @OnClick(R.id.btnHealth)
    public void onBtnHealthClicked() {
        getFragmentManager().beginTransaction().replace(R.id.mainFrame, new HealthFragment()).addToBackStack("health").commit();
    }

    @OnClick(R.id.btnTips)
    public void onBtnTipsClicked() {
        getFragmentManager().beginTransaction().replace(R.id.mainFrame, new PetGeeksFragment()).addToBackStack("Tips").commit();
    }

    @OnClick(R.id.btnPetGeeks)
    public void onBtnPetGeeksClicked() {
        getFragmentManager().beginTransaction().replace(R.id.mainFrame, new TipsFragment()).addToBackStack("Geek").commit();
    }

    @OnClick({R.id.cvPuppy, R.id.cvKitten})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cvPuppy:
                String puppy = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "puppy-training:new-dog-owner-guide:puppies:puppy-issues:puppy-health-conditions";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(puppy))
                        .addToBackStack("main").commit();
                break;
            case R.id.cvKitten:
                String kitten = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "kitten-training:new-cat-owner-guide:kittens:kitten-training:kitten-health-conditions";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(kitten))
                        .addToBackStack("main").commit();

                break;
        }
    }
}
