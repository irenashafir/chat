package shafir.irena.vetstreet;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;
import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class LatestNewsFragment extends Fragment implements LatestNewsDataSource.onLatestNewsArrivedListener{

    RecyclerView rvLatestNews;

    public LatestNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_latest_news, container, false);
        rvLatestNews = (RecyclerView) v.findViewById(R.id.rvLatestNews);

        LatestNewsDataSource.getLatestNews(this);
        rvLatestNews.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    @Override
    public void onLatestNewsArrived(final List<LatestNewsDataSource.LatestNews> data, final Exception e) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (e == null){
                    rvLatestNews.setAdapter(new LatestNewsAdapter(getActivity(), data));

                }
            }
        });
    }



    static class LatestNewsAdapter extends RecyclerView.Adapter<LatestNewsAdapter.LatestNewsViewHolder>{
        private LayoutInflater inflater;
        private Context context;
        private List<LatestNewsDataSource.LatestNews> data;

        public LatestNewsAdapter(Context context, List<LatestNewsDataSource.LatestNews> data) {
            this.inflater = LayoutInflater.from(context);
            this.context = context;
            this.data = data;
        }

        @Override
        public LatestNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.latest_news, parent, false);
            return new LatestNewsViewHolder(v);
        }

        @Override
        public void onBindViewHolder(LatestNewsViewHolder holder, int position) {
            LatestNewsDataSource.LatestNews ln = data.get(position);
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
        // create ViewHolder
        // getCount
        // bind view holder to the data
        // properties:




        class LatestNewsViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
            TextView tvDescription;
            ImageView ivImage;


            public LatestNewsViewHolder(View itemView) {
                super(itemView);

                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
                ivImage = (ImageView) itemView.findViewById(R.id.ivImage);

            }
        }

    }

}
