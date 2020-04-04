package com.example.kobenhavn.view.playgrounds;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.Playground;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaygroundsFragment extends Fragment {
    private static final String LEGEPLADS_MODEL = "com.example.kobenhavn.ui.legepladser.playgroundsModel";
    private Playground playgroundModel;

    @BindView(R.id.future_date_text) TextView _titleText;
    @BindView(R.id.future_subtitle_text) TextView _addresseText;
    @BindView(R.id.future_title_text) TextView _descriptionText;
    @BindView(R.id.future_time_text) TextView _assignedPedagoguesText;
    @BindView(R.id.legepladser_info_img_url) ImageView _imageView;

    public PlaygroundsFragment() { }

    public static PlaygroundsFragment newInstance(Playground model) {
        PlaygroundsFragment fragment = new PlaygroundsFragment();
        Bundle args = new Bundle();
        args.putParcelable(LEGEPLADS_MODEL, model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            playgroundModel = getArguments().getParcelable(LEGEPLADS_MODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.playgrounds_info_item, container, false);
        ButterKnife.bind(this, root);
        _titleText.setText(playgroundModel.getName());
        _addresseText.setText(String.format("%s %s", playgroundModel.getStreetName(), playgroundModel.getStreetNumber()));
        _descriptionText.setText(playgroundModel.getCommune());
        return root;
    }
}
