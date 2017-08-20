package shafir.irena.vetstreet.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import shafir.irena.vetstreet.MainActivity;
import shafir.irena.vetstreet.R;
import shafir.irena.vetstreet.models.Favorite;


/**
 * A simple {@link Fragment} subclass.
 */
public class petWebViewFragment extends Fragment implements View.OnClickListener {

    public static final String ARG_URL = "url";
    protected static final String ARG_TITLE = "title";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_IMAGE = "image";
    public static final String DB_FAVORITES = "favorites";
    private static final java.lang.String ARG_UID = "favorite UID";
    private boolean isInFavorites;

    private WebView webView;
    private FloatingActionButton fbLike;

    FirebaseDatabase mDatabase;
    FirebaseUser user;
    Favorite tempFav;

    public petWebViewFragment() {
        // Required empty public constructor
    }

    public static petWebViewFragment newInstance(String url, String title, String description, String image) {
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESCRIPTION, description);
        args.getString(ARG_IMAGE, image);
        petWebViewFragment fragment = new petWebViewFragment();
        fragment.setArguments(args);
        return fragment;
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
        fbLike.setOnClickListener(this);


        mDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

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

        checkFavoritesInDB();
        return v;
    }

    @Override
    public void onClick(View v) {
        final String url = getArguments().getString(ARG_URL);
        if (user.isAnonymous()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Registration").setMessage("You Must be a Registered User to Use Favorites");
            builder.setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("Register Now", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("wantToSignIn", true);
                    intent.putExtra(ARG_URL, url);
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
            builder.show();
        } else if (!user.isAnonymous()) {
            if (!checkFavoritesInDB()) {
                Toast.makeText(getContext(), "Article has been added to Favorite", Toast.LENGTH_SHORT).show();

                String description = getArguments().getString(ARG_DESCRIPTION);
                String image = getArguments().getString(ARG_IMAGE);
                final String title = getArguments().getString(ARG_TITLE);
                Favorite favorite = new Favorite(url, user.getUid(), user.getDisplayName(), title, description, image);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference(DB_FAVORITES).child(user.getUid());
                ref.push().setValue(favorite);
            }
            else
                Toast.makeText(getContext(), "Article is already in Favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private synchronized boolean checkFavoritesInDB() {
        DatabaseReference child = mDatabase.getReference(DB_FAVORITES).child(user.getUid());
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String FBTitle = snapshot.child("title").getValue().toString().trim();
                    String title = getArguments().getString("title");

                    if (FBTitle.equals(title)) {
                        isInFavorites = true;
                        fbLike.setImageResource(R.drawable.ic_added);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        return isInFavorites;
    }


}
