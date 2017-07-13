package shafir.irena.vetstreet.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import shafir.irena.vetstreet.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class petWebViewFragment extends Fragment {

    private static final String ARG_URL = "url";
    private WebView webView;
    private FloatingActionButton fbLike;

    public petWebViewFragment() {
        // Required empty public constructor
    }

    public static petWebViewFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        petWebViewFragment fragment = new petWebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_webview, container, false);
        webView = (WebView) v.findViewById(R.id.webView);
        fbLike = (FloatingActionButton) v.findViewById(R.id.fbLike);


        final String url = getArguments().getString(ARG_URL);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                webView.loadUrl(request.getUrl().toString());
                return true;
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
        return v;
    }

    public void onFabClicked(){
        
    }


}
