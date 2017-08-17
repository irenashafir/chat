package shafir.irena.vetstreet;


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
import shafir.irena.vetstreet.fragments.PetNewsFragment;


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

    private static final String ARG_URL_LATEST = "url latest news";
    private static final String ARG_URL = "url";
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
        btnNews.setText("Latest News");
        btnHealth.setText("Dog News");
        btnTips.setText("Cat News");
        btnPetGeeks.setText("dr marty becker");

        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://www.vetstreet.com/rss/dl.jsp";

                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .commit();
            }
        });

        btnHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:dog-news";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .commit();
            }
        });

        btnTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=speciesTags:cats";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .commit();
            }
        });

        btnPetGeeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.vetstreet.com/rss/news-feed" +
                        ".jsp?Categories=siteContentTags:marty-becker-health:marty-becker-on-health";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .commit();
            }
        });

    }

    @OnClick(R.id.btnHealth)
    public void onBtnHealthClicked() {
        btnNews.setText("Pet Health");
        btnHealth.setText("Pets and Exercise");
        btnTips.setText("Alternative Medicine");
        btnPetGeeks.setText("Nutrition Information");

        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:symptom-center" +
                        ":health-issues:seasonal-dangers:symptoms:adult-dog-health-conditions,dogBreedTags=health-issues";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .commit();
            }
        });

        btnHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:exercise";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .commit();
            }
        });

        btnTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=" +
                        "siteContentTags:complementary-and-alternative-medicine:holistic-and-natural-care";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .commit();
            }
        });

        btnTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "diets:digestive-care:nutrition-issues,conditionTags:weight-management";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .commit();
            }
        });
    }

    @OnClick(R.id.btnTips)
    public void onBtnTipsClicked() {
        btnNews.setText("The Perfect Dog or Cat Breed for You");
        btnHealth.setText("Pet Safety");
        btnTips.setText("Training Tips");
        btnPetGeeks.setText("Traveling with Your Pet");


        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=dogBreedTags:breed-" +
                        "information,siteContentTags:breed-information:choosing-a-dog:choosing-a-cat";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                       .commit();
            }
        });

        btnHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "training:pet-food-recall:pet-safety";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .commit();
            }
        });

        btnTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:training:cat-training:" +
                        "dog-training:crate-training:kitten-training:puppy-training:house-training";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                       .commit();
            }
        });

        btnPetGeeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:travel";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .commit();
            }
        });
    }

    @OnClick(R.id.btnPetGeeks)
    public void onBtnPetGeeksClicked() {
        btnNews.setText("Why Does My Pet...");
        btnHealth.setText("Pet Humor");
        btnTips.setText("Celebrities and Pets");
        btnPetGeeks.setText("Pet Videos");


        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "why-does-my-dog:why-does-my-cat:why-does-my-bird";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                       .commit();
            }
        });

        btnHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "funny-animals:funny-cat-pictures:funny-cat-videos:" +
                        "funny-dog-pictures:funny-dog-videos:funny-stories:funny-animal-videos:humor";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                      .commit();
            }
        });
        btnTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "celebrities-and-pets:celebrity-pets";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .commit();
            }
        });

        btnPetGeeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:animal-videos:cat-videos:" +
                        "dog-videos:funny-cat-videos:funny-dog-videos:funny-animal-videos";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .commit();
            }
        });

    }

    @OnClick({R.id.cvPuppy, R.id.cvKitten})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cvPuppy:
                String puppy = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "puppy-training:new-dog-owner-guide:puppies:puppy-issues:puppy-health-conditions";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(puppy)).commit();
                break;
            case R.id.cvKitten:
                String kitten = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "kitten-training:new-cat-owner-guide:kittens:kitten-training:kitten-health-conditions";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(kitten)).commit();

                break;
        }
    }
}
