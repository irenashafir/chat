package shafir.irena.vetstreet;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.EasyImage;
import shafir.irena.vetstreet.fragments.FavoritesFragment;

public class FavoritesActivity extends AppCompatActivity {
    private static final int RC_CFM = 123;
    private static final String IMAGES = "images";
    private static final String MY_PHOTO = "myPhoto";


    FirebaseDatabase mDatabase;
    FirebaseUser currentUser;


    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserEmail)
    TextView tvUserEmail;
    @BindView(R.id.tvHello)
    TextView tvHello;
    @BindView(R.id.frFavorites)
    FrameLayout frFavorites;
    @BindView(R.id.ivImage)
    CircularImageView ivImage;
    @BindView(R.id.tvChangePic)
    TextView tvChangePic;

    FirebaseStorage storage;
    String picPath;
    private SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

        mDatabase = FirebaseDatabase.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        pref = getSharedPreferences(MY_PHOTO, MODE_PRIVATE);
        picPath = pref.getString(MY_PHOTO, null);

        if (currentUser != null) {
            String displayName = currentUser.getDisplayName();
            Uri photoUrl = currentUser.getPhotoUrl();
            String email = currentUser.getEmail();

            tvUserName.setText(displayName);
            tvUserEmail.setText(email);

             if (picPath != null){
                 StorageReference storageRef = storage.getInstance().getReference();
                 StorageReference storageReference = storageRef.child(picPath);
                Glide.with(this).using(new FirebaseImageLoader()).load(storageReference).into(ivImage);
             }
             else if (photoUrl != null) {
                Glide.with(this).load(photoUrl).into(ivImage);
            } else {
                Glide.with(this).load(R.drawable.com_facebook_profile_picture_blank_square).into(ivImage);
            }
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frFavorites, new FavoritesFragment())
                .addToBackStack("myFavorites").commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(picPath, MY_PHOTO);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        picPath = savedInstanceState.getString(MY_PHOTO);
    }



    @OnClick(R.id.tvChangePic)
    public void onPicChange() {
        if (!checkForPermissions())return;
        EasyImage.openChooserWithGallery(this,"Choose a new Pic", 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                Toast.makeText(FavoritesActivity.this, "Process was interrupted. please try again", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                Uri uri = Uri.fromFile(imageFile);
                Picasso.with(getApplicationContext()).load(uri).into(ivImage);

                picPath = imageFile.getAbsolutePath();
                    FirebaseStorage storageInstance = storage.getInstance();
                    StorageReference reference = storageInstance.getReference();

                    StorageReference onlineStoragePhotoRef =
                            reference.child(picPath);
                    UploadTask uploadTask = onlineStoragePhotoRef.putFile(uri);

                uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FavoritesActivity.this, "Error, pls try again later", Toast.LENGTH_SHORT).show();
                        }
                    });
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(FavoritesActivity.this, "photo uploaded", Toast.LENGTH_SHORT).show();
                            savePic();
                        }});}
            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {

            }
        });
    }

    private void savePic() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(MY_PHOTO, picPath);
        editor.apply();
    }

    private boolean checkForPermissions() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            boolean granted = permission == PackageManager.PERMISSION_GRANTED;
        if (!granted){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_CFM);
        }
        return granted;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_CFM && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            onPicChange();
        }
    }



}
