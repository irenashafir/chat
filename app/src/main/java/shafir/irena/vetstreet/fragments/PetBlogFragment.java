package shafir.irena.vetstreet.fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import shafir.irena.vetstreet.MainActivity;
import shafir.irena.vetstreet.R;
import shafir.irena.vetstreet.models.ChatItem;

import static shafir.irena.vetstreet.fragments.NewChatFragment.ARG_CHAT;
import static shafir.irena.vetstreet.fragments.petWebViewFragment.ARG_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetBlogFragment extends Fragment {



    @BindView(R.id.rvChat)
    RecyclerView rvChat;
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;
    Unbinder unbinder;

    FirebaseDatabase mDatabase;
    FirebaseUser user;



    public PetBlogFragment() {
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
        user = FirebaseAuth.getInstance().getCurrentUser();
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
        final String url = getArguments().getString(ARG_URL);
        if (user.isAnonymous()){
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Register").setMessage("In order to add to favorites, you'll need to signed up")
                    .setMessage("would you like to do that now?");
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("wantToSignIn", true);
                    intent.putExtra("article", url);
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null){
                        startActivity(intent);
                    }
                }
            });
            builder.show();
        }
        else if (!user.isAnonymous() || user != null) {
            NewChatFragment newChatFragment = new NewChatFragment();
            newChatFragment.show(getChildFragmentManager(), "chat");
        }
    }


    private void setUpRecycler() {
        ChatAdapter adapter = new ChatAdapter(getActivity(),mDatabase.getReference(ARG_CHAT).orderByChild("date"));
        rvChat.setAdapter(adapter);
        rvChat.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase.getReference(ARG_CHAT).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mDatabase.getReference(ARG_CHAT).orderByChild("date");
                Toast.makeText(getContext(), "Message Added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                mDatabase.getReference(ARG_CHAT).orderByChild("date");
                Toast.makeText(getContext(), "Message Changed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Toast.makeText(getContext(), "Message Moved", Toast.LENGTH_SHORT).show();

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


        public static class ChatAdapter extends FirebaseRecyclerAdapter<ChatItem, ChatAdapter.ChatViewHolder> {
            Activity activity;

            public ChatAdapter(Activity activity, Query query) {
                super(ChatItem.class, R.layout.chat_item, ChatViewHolder.class, query);
                this.activity = activity;
            }

            @Override
            protected void populateViewHolder(ChatViewHolder viewHolder, ChatItem model, int position) {
                viewHolder.tvName.setText(model.getUserName());
                viewHolder.tvText.setText(model.getMessage());
                viewHolder.tvTime.setText(model.getTime());

            }

            public static class ChatViewHolder extends RecyclerView.ViewHolder {
                TextView tvName;
                TextView tvText;
                TextView tvTime;


                public ChatViewHolder(View itemView) {
                    super(itemView);
                    tvName = (TextView) itemView.findViewById(R.id.tvName);
                    tvText = (TextView) itemView.findViewById(R.id.tvText);
                    tvTime = (TextView) itemView.findViewById(R.id.tvTime);

//                    Random r = new Random();
//                    tvTime.setTextColor(Color.rgb(r.nextInt(0)-256,r.nextInt(0)-256,r.nextInt(0)-256));
//                    tvText.setTextColor(Color.rgb(r.nextInt(0)-256,r.nextInt(0)-256,r.nextInt(0)-256));
//                    tvName.setTextColor(Color.rgb(r.nextInt(0)-256,r.nextInt(0)-256,r.nextInt(0)-256));
                }
            }

        }


}
