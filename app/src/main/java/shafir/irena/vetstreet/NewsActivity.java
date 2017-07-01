package shafir.irena.vetstreet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity {

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



    }





}
