package shafir.irena.vetstreet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsActivity extends AppCompatActivity {

    @BindView(R.id.btnLatestNews)
    BootstrapButton btnLatestNews;
    @BindView(R.id.btnDogNews)
    BootstrapButton btnDogNews;
    @BindView(R.id.btnCatNews)
    BootstrapButton btnCatNews;
    @BindView(R.id.btnMartyBecker)
    BootstrapButton btnMartyBecker;
    @BindView(R.id.ivDoc)
    ImageView ivDoc;
    @BindView(R.id.ivKids)
    ImageView ivKids;
    @BindView(R.id.ivVetStreetLogo)
    ImageView ivVetStreetLogo;
    @BindView(R.id.frame)
    FrameLayout frame;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        Intent intent = getIntent();


    }





    @OnClick(R.id.btnLatestNews)
    public void onViewClicked() {
        btnMartyBecker.setVisibility(View.GONE);
        btnDogNews.setVisibility(View.GONE);
        btnLatestNews.setVisibility(View.GONE);
        btnCatNews.setVisibility(View.GONE);

        getSupportFragmentManager().beginTransaction().addToBackStack("newsActivity").replace
                (R.id.frame, new LatestNewsFragment()).commit();
    }



}
