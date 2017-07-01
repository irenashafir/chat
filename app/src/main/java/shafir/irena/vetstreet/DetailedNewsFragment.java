package shafir.irena.vetstreet;


import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailedNewsFragment extends Fragment {

    private static final java.lang.String ARG_URL = "url";

    private WebView webView;
    private ProgressBar progressBar;



    public DetailedNewsFragment() {
        // Required empty public constructor
    }

    public static DetailedNewsFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        DetailedNewsFragment fragment = new DetailedNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detailed_news, container, false);
        webView = (WebView) v.findViewById(R.id.webView);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        final String url = getArguments().getString(ARG_URL);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setProgress(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setProgress(View.VISIBLE);
            }

        });

        webView.loadUrl(url);

        return v;
    }


}
