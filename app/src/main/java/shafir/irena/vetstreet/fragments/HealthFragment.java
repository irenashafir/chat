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
public class HealthFragment extends Fragment {


    @BindView(R.id.btnHealth)
    BootstrapButton btnHealth;
    @BindView(R.id.btnExercise)
    BootstrapButton btnExercise;
    @BindView(R.id.btnAlternative)
    BootstrapButton btnAlternative;
    @BindView(R.id.btnInfo)
    BootstrapButton btnInfo;
    @BindView(R.id.cvPuppy)
    CircularImageView cvPuppy;
    @BindView(R.id.cvKitten)
    CircularImageView cvKitten;
    @BindView(R.id.mainContainer)
    ConstraintLayout mainContainer;
    Unbinder unbinder;

    String url;

    public HealthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnHealth, R.id.btnExercise, R.id.btnAlternative, R.id.btnInfo, R.id.cvPuppy, R.id.cvKitten})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnHealth:
                url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:symptom-center" +
                        ":health-issues:seasonal-dangers:symptoms:adult-dog-health-conditions,dogBreedTags=health-issues";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .addToBackStack("health").commit();
                break;
            case R.id.btnExercise:
                url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:exercise";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .addToBackStack("health").commit();
                break;
            case R.id.btnAlternative:
                url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=" +
                        "siteContentTags:complementary-and-alternative-medicine:holistic-and-natural-care";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .addToBackStack("health").commit();
                break;
            case R.id.btnInfo:
                url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "diets:digestive-care:nutrition-issues,conditionTags:weight-management";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .addToBackStack("health").commit();
                break;
            case R.id.cvPuppy:
                String puppy = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "puppy-training:new-dog-owner-guide:puppies:puppy-issues:puppy-health-conditions";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(puppy))
                        .addToBackStack("health").commit();
                break;
            case R.id.cvKitten:
                String kitten = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "kitten-training:new-cat-owner-guide:kittens:kitten-training:kitten-health-conditions";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(kitten))
                        .addToBackStack("health").commit();
                break;
        }
    }
}
