package shafir.irena.vetstreet;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import shafir.irena.vetstreet.fragments.FavoritesFragment;

public class FavoritesActivity extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    FirebaseUser currentUser;

    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserEmail)
    TextView tvUserEmail;
    @BindView(R.id.tvHello)
    TextView tvHello;
    @BindView(R.id.frFavorites)
    FrameLayout frFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

        mDatabase = FirebaseDatabase.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String displayName = currentUser.getDisplayName();
            Uri photoUrl = currentUser.getPhotoUrl();
            String email = currentUser.getEmail();

            tvUserName.setText(displayName);
            tvUserEmail.setText(email);
            if (photoUrl != null) {
                Glide.with(this).load(photoUrl).into(ivImage);
            } else {
                Glide.with(this).load(R.drawable.com_facebook_profile_picture_blank_square).into(ivImage);
            }
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frFavorites, new FavoritesFragment())
                .addToBackStack("myFavorites").commit();
    }


}
