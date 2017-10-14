package shafir.irena.vetstreet.fragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import shafir.irena.vetstreet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TipsFragment extends Fragment {


    @BindView(R.id.btnPerfect)
    BootstrapButton btnPerfect;
    @BindView(R.id.btnSafe)
    BootstrapButton btnSafe;
    @BindView(R.id.btnTraining)
    BootstrapButton btnTraining;
    @BindView(R.id.btnTravel)
    BootstrapButton btnTravel;
    @BindView(R.id.cvPuppy)
    CircularImageView cvPuppy;
    @BindView(R.id.cvKitten)
    CircularImageView cvKitten;
    @BindView(R.id.mainContainer)
    ConstraintLayout mainContainer;
    Unbinder unbinder;
    String url;

    public TipsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tips, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnPerfect, R.id.btnSafe, R.id.btnTraining, R.id.btnTravel, R.id.cvPuppy, R.id.cvKitten})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnPerfect:
                url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=dogBreedTags:breed-" +
                        "information,siteContentTags:breed-information:choosing-a-dog:choosing-a-cat";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .addToBackStack("Tips").commit();
                break;
            case R.id.btnSafe:
                url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "training:pet-food-recall:pet-safety";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .addToBackStack("Tips").commit();
                break;
            case R.id.btnTraining:
                url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:training:cat-training:" +
                        "dog-training:crate-training:kitten-training:puppy-training:house-training";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .addToBackStack("Tips").commit();
                break;
            case R.id.btnTravel:
                url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:travel";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .addToBackStack("Tips").commit();
                break;
            case R.id.cvPuppy:
                String puppy = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "puppy-training:new-dog-owner-guide:puppies:puppy-issues:puppy-health-conditions";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(puppy))
                        .addToBackStack("Tips").commit();
                break;
            case R.id.cvKitten:
                String kitten = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "kitten-training:new-cat-owner-guide:kittens:kitten-training:kitten-health-conditions";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(kitten))
                        .addToBackStack("Tips").commit();
                break;
        }
    }
}
