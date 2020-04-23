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
import butterknife.OnClick;

public class PlaygroundsFragment extends Fragment {

    @BindView(R.id.future_playground_name) TextView _titleText;
    @BindView(R.id.future_playground_name_text) TextView _addresseText;
    @BindView(R.id.future_title_text) TextView _descriptionText;
    @BindView(R.id.future_time_text) TextView _assignedPedagoguesText;
    @BindView(R.id.legepladser_info_img_url) ImageView _imageView;

    private static final String PLAYGROUNDS_MODEL = "com.example.kobenhavn.ui.legepladser.playgroundsModel";
    private static final String SUBSCRIPTION_ID = "com.example.kobenhavn.ui.legepladser.subscriptionID";
    private Playground playground;
    private long subscriptionID;
    private OnItemClickListener listener;

    public PlaygroundsFragment() { }

    static PlaygroundsFragment newInstance(Playground model, long id) {
        PlaygroundsFragment fragment = new PlaygroundsFragment();
        Bundle args = new Bundle();
        args.putParcelable(PLAYGROUNDS_MODEL, model);
        args.putLong(SUBSCRIPTION_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            playground = getArguments().getParcelable(PLAYGROUNDS_MODEL);
            subscriptionID = getArguments().getLong(SUBSCRIPTION_ID);
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
            listener.onItemClick(playground, subscriptionID);
        }
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Playground playground, long subscriptionsID);
    }
}
