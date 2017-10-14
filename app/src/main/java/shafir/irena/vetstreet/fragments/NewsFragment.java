package shafir.irena.vetstreet.fragments;


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
import shafir.irena.vetstreet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {


    @BindView(R.id.btnLatestNews)
    BootstrapButton btnLatestNews;
    @BindView(R.id.btnDogNews)
    BootstrapButton btnDogNews;
    @BindView(R.id.btnCatNews)
    BootstrapButton btnCatNews;
    @BindView(R.id.btnDrMarty)
    BootstrapButton btnDrMarty;
    @BindView(R.id.cvPuppy)
    CircularImageView cvPuppy;
    @BindView(R.id.cvKitten)
    CircularImageView cvKitten;

    String url;

    Unbinder unbinder;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.btnLatestNews, R.id.btnDogNews, R.id.btnCatNews, R.id.btnDrMarty, R.id.cvPuppy, R.id.cvKitten})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLatestNews:
                 url = "http://www.vetstreet.com/rss/dl.jsp";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .addToBackStack("news").commit();
                break;
            case R.id.btnDogNews:
                 url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:dog-news";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .addToBackStack("news").commit();
                break;
            case R.id.btnCatNews:
                 url = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=speciesTags:cats";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .addToBackStack("news").commit();
                break;
            case R.id.btnDrMarty:
                 url = "http://www.vetstreet.com/rss/news-feed" +
                        ".jsp?Categories=siteContentTags:marty-becker-health:marty-becker-on-health";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(url))
                        .addToBackStack("news").commit();
                break;
            case R.id.cvPuppy:
                String puppy = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "puppy-training:new-dog-owner-guide:puppies:puppy-issues:puppy-health-conditions";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(puppy))
                        .addToBackStack("news").commit();
                break;
            case R.id.cvKitten:
                String kitten = "http://www.vetstreet.com/rss/news-feed.jsp?Categories=siteContentTags:" +
                        "kitten-training:new-cat-owner-guide:kittens:kitten-training:kitten-health-conditions";
                getFragmentManager().beginTransaction().replace(R.id.mainFrame, PetNewsFragment.newInstance(kitten))
                        .addToBackStack("news").commit();
                break;
        }
    }
}
