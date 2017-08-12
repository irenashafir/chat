package shafir.irena.vetstreet.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import shafir.irena.vetstreet.R;
import shafir.irena.vetstreet.models.Favorite;

import static shafir.irena.vetstreet.fragments.petWebViewFragment.DB_FAVORITES;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    private static final String ARG_USER = "user";
    FirebaseDatabase mDatabase;
    FirebaseUser currentUser;



    @BindView(R.id.rvLatestNews)
    RecyclerView rvLatestNews;
    Unbinder unbinder;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        unbinder = ButterKnife.bind(this, view);

        mDatabase = FirebaseDatabase.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        if (currentUser == null) return view;

        setUpAdapter();
        return view;
    }


    private void setUpAdapter() {
        DatabaseReference dbUserRef = mDatabase.getReference(DB_FAVORITES).child(currentUser.getUid());
        FavoritesAdapter adapter = new FavoritesAdapter(getActivity(),dbUserRef);
        rvLatestNews.setAdapter(adapter);
        rvLatestNews.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public class FavoritesAdapter extends FirebaseRecyclerAdapter<Favorite, FavoritesAdapter.FavoritesViewHolder> {
        Activity activity;


        public FavoritesAdapter(Activity activity, Query query) {
            super(Favorite.class, R.layout.recycler_template, FavoritesViewHolder.class, query);
            this.activity = activity;
        }

        @Override
        public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            return new FavoritesViewHolder(view);
        }

        @Override
        protected void populateViewHolder(FavoritesViewHolder viewHolder, Favorite model, int position) {
            viewHolder.tvTitle.setText(model.getTitle());
            viewHolder.tvDescription.setText(model.getDescription());
            if (model.getImage() != null) {
                Glide.with(activity).load(model.getImage()).into(viewHolder.ivImage);
            } else
                Glide.with(activity).load(R.drawable.com_facebook_profile_picture_blank_portrait).into(viewHolder.ivImage);

        }

        public class FavoritesViewHolder extends RecyclerView.ViewHolder {
            private TextView tvTitle;
            private TextView tvDescription;
            private ImageView ivImage;

            public FavoritesViewHolder(View itemView) {
                super(itemView);
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
                ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            }
        }
    }
}
