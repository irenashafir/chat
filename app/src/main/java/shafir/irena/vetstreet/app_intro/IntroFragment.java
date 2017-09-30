package shafir.irena.vetstreet.app_intro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import shafir.irena.vetstreet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment extends Fragment {

    private static int[] imageResources =
            {R.drawable.favorites_screen, R.drawable.articels_screen, R.drawable.map_screen};

    @BindView(R.id.ivAppIntro)
    ImageView ivAppIntro;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    Unbinder unbinder;

    String title1 = "Welcome to VetStreet!";
    String description1 = "learn all you need for your pet";
    String title2 ="Save All!";
    String description2 ="Save all your favorites articles";
    String title3 ="Get Help Fast!";
    String description3 ="Find A Vet where ever you are";

    public IntroFragment() {
        // Required empty public constructor
    }

    public static IntroFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        IntroFragment fragment = new IntroFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intro_app, container, false);
        unbinder = ButterKnife.bind(this, v);

        int position = getArguments().getInt("position");
        int imgResource = imageResources[position];
        ivAppIntro.setImageResource(imgResource);

        switch (position) {
            case 0:
                tvTitle.setText(title1);
                tvDescription.setText(description1);
                break;
            case 1:
                tvTitle.setText(title2);
                tvDescription.setText(description2);
                break;
            case 2:
                tvTitle.setText(title3);
                tvDescription.setText(description3);
                break;
        }


        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
