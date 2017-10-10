package shafir.irena.vetstreet.fragments;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

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
import shafir.irena.vetstreet.models.ChatItem;
import shafir.irena.vetstreet.models.User;

import static shafir.irena.vetstreet.fragments.NewChatFragment.ARG_CHAT;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditChatFragment extends BottomSheetDialogFragment {

    public static final String MODEL = "model";
    FirebaseDatabase mDatabase;
    String message;
    FirebaseUser mUser;
    ChatItem item;

    @BindView(R.id.etText)
    EditText etText;
    @BindView(R.id.ibSave)
    ImageButton ibSave;
    Unbinder unbinder;

    public EditChatFragment() {
        // Required empty public constructor
    }

    public static EditChatFragment newInstance(ChatItem model) {

        Bundle args = new Bundle();
        args.putParcelable(MODEL, model);
        EditChatFragment fragment = new EditChatFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_chat, container, false);
        unbinder = ButterKnife.bind(this, view);

        item = getArguments().getParcelable(MODEL);
        if (item != null) {
            message = item.getMessage();
        } else {
            message = "";
        }

        etText.setText(message);

        mDatabase = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ibSave)
    public void onViewClicked() {
        DatabaseReference reference = mDatabase.getReference(ARG_CHAT);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("message").getValue().equals(item.getMessage())){
                        snapshot.getRef().removeValue();
                        ChatItem chatItem = new ChatItem(new User(mUser.getDisplayName(),mUser.getPhotoUrl().toString(),
                                mUser.getUid(), mUser.getEmail()), etText.getText().toString());
                        mDatabase.getReference(ARG_CHAT).push().setValue(chatItem);
                        dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

