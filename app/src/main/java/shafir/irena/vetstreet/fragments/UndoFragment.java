package shafir.irena.vetstreet.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import shafir.irena.vetstreet.R;

import static android.content.Context.MODE_PRIVATE;
import static shafir.irena.vetstreet.FavoritesActivity.MY_PHOTO;

/**
 * A simple {@link Fragment} subclass.
 */
public class UndoFragment extends BottomSheetDialogFragment {


    public static final String DEFAULT_PIC = "defalt profile picture";
    @BindView(R.id.ibPic)
    ImageButton ibPic;
    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.btnResetPic)
    Button btnResetPic;
    @BindView(R.id.btnUndo)
    Button btnUndo;
    Unbinder unbinder;

    private SharedPreferences pref;
    boolean refresh;

    public UndoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_undo, container, false);
        unbinder = ButterKnife.bind(this, view);

        pref = getActivity().getSharedPreferences(MY_PHOTO, MODE_PRIVATE);
        refresh = true;

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ibPic, R.id.ibBack, R.id.btnResetPic, R.id.btnUndo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibPic:
                picToDefault();
                break;
            case R.id.ibBack:
                break;
            case R.id.btnResetPic:
                picToDefault();
                break;
            case R.id.btnUndo:
                break;
        }
    }

    private void picToDefault() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(MY_PHOTO, null);
        editor.putBoolean(DEFAULT_PIC, true);
        editor.apply();
        dismiss();
    }

}
