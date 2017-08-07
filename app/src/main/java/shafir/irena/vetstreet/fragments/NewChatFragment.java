package shafir.irena.vetstreet.fragments;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import shafir.irena.vetstreet.R;
import shafir.irena.vetstreet.models.ChatItem;
import shafir.irena.vetstreet.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewChatFragment extends BottomSheetDialogFragment {


    @BindView(R.id.etText)
    EditText etText;
    @BindView(R.id.fabNew)
    FloatingActionButton fabNew;
    Unbinder unbinder;

    public static final String ARG_CHAT = "chat";

    FirebaseDatabase mDatabase;
    FirebaseUser user;


    public NewChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_chat, container, false);
        unbinder = ButterKnife.bind(this, view);
        mDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fabNew)
    public void onFabClicked() {
        if (etText.getText() == null) {
            Toast.makeText(getContext(), "Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (user.isAnonymous()) {

            User anonymousUser = new User("Anonymous",
                    String.valueOf(R.drawable.com_facebook_profile_picture_blank_portrait),
                    user.getUid(),
                    null);

            ChatItem chatItem = new ChatItem(anonymousUser, etText.getText().toString());
            mDatabase.getReference(ARG_CHAT).push().setValue(chatItem);
            dismiss();
        } else if (!user.isAnonymous()) {
            ChatItem chatItem = new ChatItem(new User(user),etText.getText().toString());
            mDatabase.getReference(ARG_CHAT).push().setValue(chatItem);
            dismiss();
        }
    }


}
