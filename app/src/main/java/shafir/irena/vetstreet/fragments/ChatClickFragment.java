package shafir.irena.vetstreet.fragments;


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

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatClickFragment extends BottomSheetDialogFragment {


    @BindView(R.id.ibShare)
    ImageButton ibShare;
    @BindView(R.id.ibDelete)
    ImageButton ibDelete;
    @BindView(R.id.imageButton)
    ImageButton imageButton;
    @BindView(R.id.btnShare)
    Button btnShare;
    @BindView(R.id.btnDelete)
    Button btnDelete;
    @BindView(R.id.btnEdit)
    Button btnEdit;
    Unbinder unbinder;

    public ChatClickFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_click, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ibShare, R.id.ibDelete, R.id.imageButton, R.id.btnShare, R.id.btnDelete, R.id.btnEdit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibShare:
                break;
            case R.id.ibDelete:
                break;
            case R.id.imageButton:
                break;
            case R.id.btnShare:
                break;
            case R.id.btnDelete:
                break;
            case R.id.btnEdit:
                break;
        }
    }
}
