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
public class PetGeeksFragment extends Fragment {


    @BindView(R.id.btnWhy)
    BootstrapButton btnWhy;
    @BindView(R.id.btnHumer)
    BootstrapButton btnHumer;
    @BindView(R.id.btnCelebs)
    BootstrapButton btnCelebs;
    @BindView(R.id.btnVideos)
    BootstrapButton btnVideos;
    @BindView(R.id.cvPuppy)
    CircularImageView cvPuppy;
    @BindView(R.id.cvKitten)
    CircularImageView cvKitten;
    @BindView(R.id.mainContainer)
    ConstraintLayout mainContainer;
    Unbinder unbinder;
    String url;

    public PetGeeksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pet_geeks, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnWhy, R.id.btnHumer, R.id.btnCelebs, R.id.btnVideos, R.id.cvPuppy, R.id.cvKitten})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnWhy:
                url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "why-does-my-dog:why-does-my-cat:why-does-my-bird";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .addToBackStack("Geek").commit();
                break;
            case R.id.btnHumer:
                url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "funny-animals:funny-cat-pictures:funny-cat-videos:" +
                        "funny-dog-pictures:funny-dog-videos:funny-stories:funny-animal-videos:humor";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .addToBackStack("Geek").commit();
                break;
            case R.id.btnCelebs:
                url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "celebrities-and-pets:celebrity-pets";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .addToBackStack("Geek").commit();
                break;
            case R.id.btnVideos:
                url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:animal-videos:cat-videos:" +
                        "dog-videos:funny-cat-videos:funny-dog-videos:funny-animal-videos";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .addToBackStack("Geek").commit();
                break;
            case R.id.cvPuppy:
                String puppy = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "puppy-training:new-dog-owner-guide:puppies:puppy-issues:puppy-health-conditions";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(puppy))
                        .addToBackStack("Geek").commit();
                break;
            case R.id.cvKitten:
                String kitten = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "kitten-training:new-cat-owner-guide:kittens:kitten-training:kitten-health-conditions";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(kitten))
                        .addToBackStack("Geek").commit();
                break;
        }
    }
}
