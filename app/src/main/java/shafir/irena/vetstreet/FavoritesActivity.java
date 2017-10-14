package shafir.irena.vetstreet;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import shafir.irena.vetstreet.fragments.ShareFavoriteFragment;

import static shafir.irena.vetstreet.fragments.ShareFavoriteFragment.DB_DELETED;
import static shafir.irena.vetstreet.fragments.petWebViewFragment.DB_FAVORITES;


public class FavoritesActivity extends AppCompatActivity implements ShareFavoriteFragment.lastDeleted {
    public static final int RC_CFM = 123;
    public static final String MY_PHOTO = "myPhoto";
    public static final String UNDO = "unDo";

    @BindView(R.id.ibDeleteAll)
    ImageButton ibDeleteAll;
    @BindView(R.id.ibImageReset)
    ImageButton ibImageReset;
    @BindView(R.id.ibUndoDelete)
    ImageButton ibUndoDelete;
    @BindView(R.id.ibHome)
    ImageButton ibHome;


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


    FirebaseStorage storage;
    String picPath;
    @BindView(R.id.favorite_main)
    ConstraintLayout favoriteMain;
    private SharedPreferences pref;
    private SharedPreferences articlePref;
    Uri photoUrl;
    String savedLast =null;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

        mDatabase = FirebaseDatabase.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        pref = getSharedPreferences(MY_PHOTO, MODE_PRIVATE);
        picPath = pref.getString(MY_PHOTO, null);

        articlePref = getSharedPreferences(UNDO, MODE_PRIVATE);
         savedLast = articlePref.getString(UNDO, null);

        userSettings();
        getSupportFragmentManager().beginTransaction().replace(R.id.frFavorites, new FavoritesFragment())
                .addToBackStack("myFavorites").commit();

    }

    private void userSettings() {
        if (currentUser != null) {
            String displayName = currentUser.getDisplayName();
            photoUrl = currentUser.getPhotoUrl();
            String email = currentUser.getEmail();

            tvUserName.setText(displayName);
            tvUserEmail.setText(email);

            if (picPath != null) {
                StorageReference storageRef = storage.getInstance().getReference();
                StorageReference storageReference = storageRef.child(picPath);
                Glide.with(this).using(new FirebaseImageLoader()).load(storageReference).into(ivImage);
            } else if (photoUrl != null) {
                Glide.with(this).load(photoUrl).into(ivImage);
            } else {
                Glide.with(this).load(R.drawable.com_facebook_profile_picture_blank_square).into(ivImage);
            }
        }
    }
    @OnClick(R.id.ivImage)
    public void onViewClicked() {
        newPic();
    }
    public void newPic() {
        if (!checkForPermissions()) return;
        EasyImage.openChooserWithGallery(this, "Choose a new Pic", 0);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                Toast.makeText(FavoritesActivity.this, "Process was interrupted. please try again", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                final Uri uri = Uri.fromFile(imageFile);

                Picasso.with(FavoritesActivity.this).load(uri).into(ivImage);

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
                    }
                });
            }

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
        if (!granted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_CFM);
        }
        return granted;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_CFM && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            newPic();
        }
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


    @OnClick({R.id.ibDeleteAll, R.id.ibImageReset, R.id.ibUndoDelete, R.id.ibHome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibDeleteAll:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete All").setMessage("Are you sure you want to delete all articles?")
                        .setNegativeButton("no way!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                            }
                        }).setPositiveButton("Of course!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAll(DB_FAVORITES);
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;

            case R.id.ibImageReset:
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(MY_PHOTO, null);
                editor.apply();
                finish();
                Toast.makeText(this, "Profile Picture has been Reset", Toast.LENGTH_SHORT).show();
                startActivity(getIntent());
                break;

            case R.id.ibUndoDelete:
                if (savedLast == null){
                    Toast.makeText(this, "No Articles to return", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    DatabaseReference reference = mDatabase.getReference(DB_DELETED).child(currentUser.getUid());
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot favoriteSnapShot : dataSnapshot.getChildren()) {
                                //noinspection ConstantConditions
                                if (favoriteSnapShot.child("title").getValue().equals(savedLast)) {
                                    DatabaseReference databaseReference = mDatabase.getReference(DB_FAVORITES).child(currentUser.getUid());
                                    databaseReference.push().setValue(favoriteSnapShot.getValue());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(FavoritesActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });

                    DatabaseReference deletedRef = mDatabase.getReference(DB_DELETED).child(currentUser.getUid());
                    deletedRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot favoriteSnapShot : dataSnapshot.getChildren()) {
                                //noinspection ConstantConditions
                                if (favoriteSnapShot.child("title").getValue().equals(savedLast)) {
                                    favoriteSnapShot.getRef().removeValue();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(FavoritesActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                break;
            case R.id.ibHome:
                Intent homeIntent = new Intent(this, MainActivity.class);
                if (homeIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(homeIntent);
                }
                break;
        }
    }

    private void deleteAll(String type) {
        DatabaseReference child = mDatabase.getReference(type).child(currentUser.getUid());
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FavoritesActivity.this, "Error! Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void lastDeletedListener(String articleName) {
        savedLast = articleName;
            saveArticleName();
    }

    private void saveArticleName() {
        SharedPreferences.Editor editor = articlePref.edit();
        editor.putString(UNDO, savedLast);
        editor.apply();
    }


}

