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
    private FirebaseUser user;
    private static final int RC_SIGN_IN = 1;


    FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
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

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabase.setPersistenceEnabled(true);


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
                shafir.irena.vetstreet.fragments.petWebViewFragment shoppingFragment =
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
                        final String text = etText.getText().toString();

                        if (text != null) {
                            Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                            sendIntent.setType("message/rfc822");
                            sendIntent.setData(Uri.parse("mailto:"));
                            sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"irena_shafir@walla.co.il"});
                            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Us");
                            sendIntent.putExtra(Intent.EXTRA_TEXT, text);

                            startActivity(Intent.createChooser(sendIntent, "Send Email"));
                            Toast.makeText(MainActivity.this, "Sent", Toast.LENGTH_SHORT).show();

                        } else if (text == null) {
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

                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.getInstance().signOut();
                    }
                });
                builder.show();
                return true;
            case R.id.sign_in:
                    signIn();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            if (intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }

        } else if (id == R.id.pet_shop) {
            Intent intent = new Intent(this, ShopActivity.class);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } else if (id == R.id.favorite) {


        } else if (id == R.id.vets){
            Intent vetIntent = new Intent(this, FindAVetActivity.class);
            if (vetIntent.resolveActivity(getPackageManager()) != null){
                startActivity(vetIntent);
            }


        } else if (id == R.id.nav_share) {


        } else if (id == R.id.nav_send) {

            // send app
            // send article

        } else if (id == R.id.rate){
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

        }else if (id == R.id.personal_area){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void signIn() {
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
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse idpResponse = IdpResponse.fromResultIntent(intent);
            if (requestCode == RESULT_OK) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                User user = new User(currentUser);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                reference.setValue(user);
            } else {
                Toast.makeText(this, "Connection Failed, pls try again later", Toast.LENGTH_SHORT).show();
            }
        }
    }


}

// TODO: 1.favorites
// 2. vets around me with google map
// 3. share feature
// 6. notifications
// 5. remove sign in in manu after user is signed in
// 6. check e mail sending?!
// 7. personal area
//A. user details
// B. user picture