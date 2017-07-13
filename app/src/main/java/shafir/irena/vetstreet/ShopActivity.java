package shafir.irena.vetstreet;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopActivity extends AppCompatActivity {


    @BindView(R.id.shopView)
    WebView shopView;

    String shopSite = "https://www.petshoponline.ie/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);

        shopView.getSettings().setJavaScriptEnabled(true);
        shopView.setWebViewClient(new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                shopView.loadUrl(request.getUrl().toString());
                return true;
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                shopView.loadUrl(shopSite);
                return true;
            }


        });
        shopView.loadUrl(shopSite);
    }


}
