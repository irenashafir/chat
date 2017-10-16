package shafir.irena.vetstreet.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import shafir.irena.vetstreet.R;
import shafir.irena.vetstreet.models.ChatItem;

import static shafir.irena.vetstreet.fragments.NewChatFragment.ARG_CHAT;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetChatFragment extends Fragment {



    @BindView(R.id.rvChat)
    RecyclerView rvChat;
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;
    Unbinder unbinder;

    FirebaseDatabase mDatabase;
    FirebaseUser user;
    FirebaseAuth mAuth;



    public PetChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pet_blog, container, false);
        unbinder = ButterKnife.bind(this, view);

        mDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        setUpRecycler();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fabAdd)
    public void onFabClicked() {
            NewChatFragment newChatFragment = new NewChatFragment();
            newChatFragment.show(getChildFragmentManager(), "chat");
    }


    private void setUpRecycler() {
        ChatAdapter adapter = new ChatAdapter(mDatabase.getReference(ARG_CHAT), this);
        rvChat.setAdapter(adapter);
        rvChat.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase.getReference(ARG_CHAT).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mDatabase.getReference(ARG_CHAT).orderByChild("date");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                mDatabase.getReference(ARG_CHAT).orderByChild("date");
                Toast.makeText(getContext(), "Message Changed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(getContext(), "Message Deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


        static class ChatAdapter extends FirebaseRecyclerAdapter<ChatItem, ChatAdapter.ChatViewHolder> {
            Fragment fragment;

            ChatAdapter(Query query, Fragment fragment) {
                super(ChatItem.class, R.layout.chat_item, ChatViewHolder.class, query);
                this.fragment = fragment;
            }

            @Override
            protected void populateViewHolder(ChatViewHolder viewHolder, ChatItem model, int position) {
                viewHolder.tvName.setText(model.getUserName());
                viewHolder.tvText.setText(model.getMessage());
                viewHolder.tvTime.setText(model.getTime());
                if (model.getProfileImage() != null) {
                    Glide.with(fragment.getContext()).load(model.getProfileImage()).into(viewHolder.ivProfile);
                } else {
                    Glide.with(fragment.getContext()).load(R.mipmap.default_image)
                            .into(viewHolder.ivProfile);
                }
                viewHolder.model = model;
            }

            @Override
            public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
                return new ChatViewHolder(view, fragment);
            }


            static class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
                TextView tvName;
                TextView tvText;
                TextView tvTime;
                CircularImageView ivProfile;
                ChatItem model;
                Fragment fragment;

                 ChatViewHolder(View itemView, Fragment fragment) {
                    super(itemView);
                    tvName = (TextView) itemView.findViewById(R.id.tvName);
                    tvText = (TextView) itemView.findViewById(R.id.tvText);
                    tvTime = (TextView) itemView.findViewById(R.id.tvTime);
                    ivProfile = (CircularImageView) itemView.findViewById(R.id.ivProfile);

                    this.fragment = fragment;
                    itemView.setOnClickListener(this);
                }

                @Override
                public void onClick(View v) {
                    if (tvName.getText().toString().equals(model.getUserName())){
                    EditChatFragment editChatFragment = EditChatFragment.newInstance(model);
                        editChatFragment.show(fragment.getChildFragmentManager(), "show");
                    }
                    else
                        Toast.makeText(v.getContext(), "you can only change your own chat item", Toast.LENGTH_SHORT).show();
                }
            }

        }

}
