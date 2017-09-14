package shafir.irena.vetstreet.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import shafir.irena.vetstreet.PetNewsDataSource;
import shafir.irena.vetstreet.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PetNewsFragment extends Fragment implements PetNewsDataSource.onLatestNewsArrivedListener {

    private static final String ARG_URL = "url address";
    RecyclerView rvLatestNews;

    public PetNewsFragment() {
        // Required empty public constructor
    }


    public static PetNewsFragment newInstance(String address) {
        Bundle args = new Bundle();
        args.putString(ARG_URL,address);
        PetNewsFragment fragment = new PetNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recycler, container, false);
        rvLatestNews = (RecyclerView) v.findViewById(R.id.rvLatestNews);


        String urlAddress = getArguments().getString(ARG_URL, null);
        PetNewsDataSource.getNews(this, urlAddress);

        rvLatestNews.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

    @Override
    public void onLatestNewsArrived(final List<PetNewsDataSource.petNews> data, final Exception e) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (e == null){
                    rvLatestNews.setAdapter(new PetNewsAdapter(getActivity(), data));
                }
            }
        });
    }


    static class PetNewsAdapter extends RecyclerView.Adapter<PetNewsAdapter.PetNewsViewHolder>{
        private LayoutInflater inflater;
        private Context context;
        private List<PetNewsDataSource.petNews> data;

        public PetNewsAdapter(Context context, List<PetNewsDataSource.petNews> data) {
            this.inflater = LayoutInflater.from(context);
            this.context = context;
            this.data = data;
        }

        @Override
        public PetNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.recycler_template, parent, false);
            return new PetNewsViewHolder(v);
        }

        @Override
        public void onBindViewHolder(PetNewsViewHolder holder, int position) {
            PetNewsDataSource.petNews ln = data.get(position);
            holder.tvTitle.setText(ln.getTitle());
            holder.tvDescription.setText(ln.getDescription());
            if (ln.getImage() != null) {
                Glide.with(context).load(ln.getImage()).into(holder.ivImage);
            }
            else
                Glide.with(context).load(R.mipmap.ic_launcher).into(holder.ivImage);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        class PetNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvTitle;
            TextView tvDescription;
            ImageView ivImage;


            public PetNewsViewHolder(View itemView) {
                super(itemView);
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
                ivImage = (ImageView) itemView.findViewById(R.id.ivImage);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (context instanceof AppCompatActivity){
                    FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();

                    int adapterPosition = getAdapterPosition();
                    PetNewsDataSource.petNews petNews = data.get(adapterPosition);

                    petWebViewFragment webViewFragment = petWebViewFragment.newInstance
                            (petNews.getLink(), petNews.getTitle(), petNews.getDescription(), petNews.getImage());

                    fm.beginTransaction().replace(R.id.mainContainer, webViewFragment).commit();
                }
            }
        }

    }


}
