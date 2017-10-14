package shafir.irena.vetstreet.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import shafir.irena.vetstreet.R;
import shafir.irena.vetstreet.models.Favorite;

import static shafir.irena.vetstreet.fragments.petWebViewFragment.ARG_TITLE;
import static shafir.irena.vetstreet.fragments.petWebViewFragment.ARG_URL;
import static shafir.irena.vetstreet.fragments.petWebViewFragment.DB_FAVORITES;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFavoriteFragment extends BottomSheetDialogFragment {


    public static final String DB_DELETED = "deleted favorites";
    @BindView(R.id.ibShare)
    ImageButton ibShare;
    @BindView(R.id.ibDelete)
    ImageButton ibDelete;
    @BindView(R.id.btnShare)
    Button btnShare;
    @BindView(R.id.btnDelete)
    Button btnDelete;
    Unbinder unbinder;

    FirebaseDatabase mDatabase;
    FirebaseUser currentUser;
    private lastDeleted listener;


    public interface lastDeleted{
        void lastDeletedListener(String articleName);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (lastDeleted) getActivity();
    }

    public ShareFavoriteFragment() {
        // Required empty public constructor
    }

    public static ShareFavoriteFragment newInstance(String title, String url) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_URL, url);
        ShareFavoriteFragment fragment = new ShareFavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_favorite, container, false);
        unbinder = ButterKnife.bind(this, view);

        mDatabase = FirebaseDatabase.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnShare, R.id.btnDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnShare:
                ShareArticle();
                break;
            case R.id.ibShare:
                ShareArticle();
                break;
            case R.id.btnDelete:
                DeleteArticle();
                break;
            case R.id.ibDelete:
                DeleteArticle();
                break;
        }
    }

    private void DeleteArticle() {
        final String articleTitle = getArguments().getString(ARG_TITLE);
        if (articleTitle == null){
            Toast.makeText(getContext(), "No Title", Toast.LENGTH_SHORT).show();
            dismiss();
        }
        final DatabaseReference ref = mDatabase.getReference(DB_FAVORITES).child(currentUser.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot favoriteSnapShot : dataSnapshot.getChildren()) {

                    if (favoriteSnapShot.child("title").getValue().equals(articleTitle)){
                        favoriteSnapShot.getRef().removeValue();
                        addFavoriteToDeletedDB(favoriteSnapShot);
                        getActivity().recreate();
                        listener.lastDeletedListener(articleTitle);
                        dismiss();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error! Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addFavoriteToDeletedDB(DataSnapshot favoriteSnapShot) {
        String title = (String) favoriteSnapShot.child("title").getValue();
        String link = (String) favoriteSnapShot.child("link").getValue();
        String description = (String) favoriteSnapShot.child("description").getValue();
        String image = (String) favoriteSnapShot.child("image").getValue();
        Favorite favorite = new Favorite(link, currentUser.getUid(), currentUser.getDisplayName(),
                title, description, image);

        DatabaseReference deleted_DB_Ref = mDatabase.getReference(DB_DELETED).child(currentUser.getUid());
        deleted_DB_Ref.push().setValue(favorite);
    }

    private void ShareArticle() {
        String url = getArguments().getString(ARG_URL);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendIntent.setType("text/plain");
        if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(Intent.createChooser(sendIntent, getArguments().getString(ARG_TITLE)));
        }
    }



}
