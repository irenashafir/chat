package shafir.irena.vetstreet;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import shafir.irena.vetstreet.fragments.PetChatFragment;
import shafir.irena.vetstreet.fragments.petWebViewFragment;
import shafir.irena.vetstreet.models.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.mainContainer)
    ConstraintLayout mainContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivDoc)
    ImageView ivDoc;
    @BindView(R.id.ivKids)
    ImageView ivKids;
    @BindView(R.id.ivVetStreetLogo)
    ImageView ivVetStreetLogo;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;
    public static final int RC_SIGN_IN = 1;
    private static final String ARG_URL = "url";
    private static final String DB_USERS = "users";
    String contactText = null;


    FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            mUser = FirebaseAuth.getInstance().getCurrentUser();
            if (mUser == null) {
                mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            User user = new User(currentUser);
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                            reference.setValue(user);

                            Toast.makeText(MainActivity.this, "you're logged in", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(MainActivity.this, "pls try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boolean wantToSignIn = getIntent().getBooleanExtra("wantToSignIn", false);
        boolean cameFromFavorite = getIntent().getBooleanExtra("fullArticle", false);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();


        if (wantToSignIn){
            signIn();
        }

        if (cameFromFavorite){
            String url = getIntent().getStringExtra(ARG_URL);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, petWebViewFragment.newInstance(url)).commit();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new MainFragment()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                  return true;

            case R.id.about:
                petWebViewFragment petWebViewFragment =  new petWebViewFragment();
                petWebViewFragment shoppingFragment =
                        petWebViewFragment.newInstance("http://www.vetstreet.com/about");
                getSupportFragmentManager().beginTransaction().replace
                        (R.id.mainFrame, shoppingFragment ).addToBackStack("main").commit();
                return true;

            case R.id.contact:
                View view = View.inflate(this, R.layout.contact_us, null);
                final AlertDialog.Builder contactBuilder = new AlertDialog.Builder(this);
                contactBuilder.setView(view);
                final EditText etText = (EditText) view.findViewById(R.id.etText);
                contactBuilder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        contactText = etText.getText().toString();

                        if (contactText != null) {
                            Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                            sendIntent.setType("message/rfc822");
                            sendIntent.setData(Uri.parse("mailto:"));
                            sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"irena_shafir@walla.co.il"});
                            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Us");
                            sendIntent.putExtra(Intent.EXTRA_TEXT, contactText);

                            startActivity(Intent.createChooser(sendIntent, "Send Email"));
                            Toast.makeText(MainActivity.this, "Sent", Toast.LENGTH_SHORT).show();

                        } else if (contactText == null) {
                            Toast.makeText(MainActivity.this, "No Message", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                contactBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                contactBuilder.create();
                contactBuilder.show();
                return true;

            case R.id.sign_out:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are You Sure You Want to Sign Out?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                return true;

            case R.id.sign_in:
                if (mUser == null || mUser.isAnonymous()) {
                   signIn();

                }
                else
                    Toast.makeText(this, "You Are Already Signed In", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.home: {
                Intent intent = new Intent(this, MainActivity.class);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

                break;
            }
            case R.id.pet_shop: {
                Intent intent = new Intent(this, ShopActivity.class);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            }
            case R.id.favorites: {
                if (mUser.isAnonymous()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Favorites").setMessage("Must be a registered user to user favorites");
                    builder.setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            signIn();
                        }
                    });
                    builder.show();
                }
                else if (mUser != null && !mUser.isAnonymous()) {
                    Intent intent = new Intent(this, FavoritesActivity.class);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
                break;
            }
            case R.id.vets:
                Intent vetIntent = new Intent(this, FindAVetActivity.class);
                if (vetIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(vetIntent);
                }
                break;

            case R.id.pet_Chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new PetChatFragment())
                        .addToBackStack("main").commit();
                    break;

            case R.id.nav_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra("MyApp", "http://play.google.com/store/apps/details?id=" +
                        getApplicationContext().getPackageName());
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(sendIntent, "Vet Street"));
                }

                break;
            case R.id.rate:
                Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                }
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void signIn() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER)
                        .setPermissions(Arrays.asList(Scopes.PROFILE, Scopes.EMAIL)).build(),
                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build());

        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setLogo(R.mipmap.logo_big)
                .setAvailableProviders(providers)
                .build();
        startActivityForResult(intent, RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                User user = new User(currentUser);
                DatabaseReference ref = mDatabase.getReference(DB_USERS);
                ref.setValue(user);

                String url = getIntent().getStringExtra(ARG_URL);
                if (url != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, petWebViewFragment.newInstance(url))
                            .addToBackStack("main").commit();
                }
            }
                if ((idpResponse != null ? idpResponse.getErrorCode() : 0) == ErrorCodes.NO_NETWORK){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("No Internet Connection").setMessage("Would you like to Reconnect?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            }
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        }

    }



// TODO:
// 1. vets around me with google map -- not finished
// 2. notifications
// 3. finish onClick in petChatFragment --- on click doesn't work
// 4. check e mail sending?!
// 5. personal area -- favorites
//      A. resize pic before upload to storage
//      B. add option to undo delete (save deleted to shared prefs in Que)
//      C. get to article from favorite-- "not executable code" -- recheck again later
// 6. app intro

