package com.example.kobenhavn.view.playgrounds;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.view.events.enrolled.EnrolledAdapter;
import com.example.kobenhavn.viewmodel.UserViewModel;
import com.example.kobenhavn.viewmodel.UserViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class PlaygroundsFragment extends Fragment {

    @BindView(R.id.future_date_text) TextView _titleText;
    @BindView(R.id.future_subtitle_text) TextView _addresseText;
    @BindView(R.id.future_title_text) TextView _descriptionText;
    @BindView(R.id.future_time_text) TextView _assignedPedagoguesText;
    @BindView(R.id.legepladser_info_img_url) ImageView _imageView;

    private static final String LEGEPLADS_MODEL = "com.example.kobenhavn.ui.legepladser.playgroundsModel";
    private Playground playground;
    private OnItemClickListener listener;

    public PlaygroundsFragment() { }

    static PlaygroundsFragment newInstance(Playground model) {
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
            playground = getArguments().getParcelable(LEGEPLADS_MODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.playgrounds_info_item, container, false);
        ButterKnife.bind(this, root);
        _titleText.setText(playground.getName());
        _addresseText.setText(String.format("%s %s", playground.getStreetName(), playground.getStreetNumber()));
        _descriptionText.setText(playground.getCommune());
        return root;
    }

    @OnClick(R.id.playground_delete_btn)
    void onDeletePlayground(){
        if (listener != null){
            listener.onItemClick(playground);
        }
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Playground playground);
    }
}
