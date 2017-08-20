package shafir.irena.vetstreet.utils;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static shafir.irena.vetstreet.fragments.petWebViewFragment.DB_FAVORITES;

/**
 * Created by irena on 19/08/2017.
 */

public class DB_Utils {

    boolean isInFavorites = false;
    FirebaseUser user;
    FirebaseDatabase mDatabase;

    public interface onCheckedInDBArrivedListener{
        public boolean onDBChecked();
    }


    public boolean checkFavoritesInDB(){
        DatabaseReference child = mDatabase.getReference(DB_FAVORITES).child(user.getUid());
        child.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
               //     isInFavorites = listener.onDBChecked();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return isInFavorites;
    }





}
