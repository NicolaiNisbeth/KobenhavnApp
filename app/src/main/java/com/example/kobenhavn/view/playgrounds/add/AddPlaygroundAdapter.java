package com.example.kobenhavn.view.playgrounds.add;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.Subscriptions;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.viewmodel.UserViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class AddPlaygroundAdapter extends RecyclerView.Adapter<AddPlaygroundAdapter.ViewHolder> {

    private List<Playground> playgrounds;
    private Context context;
    private UserViewModel userViewModel;
    private boolean isFilterCalled;
    private Subscriptions subscriptions ;

    AddPlaygroundAdapter(Context context, List<Playground> playgrounds, UserViewModel userViewModel) {
        this.playgrounds = playgrounds;
        this.context = context;
        this.userViewModel = userViewModel;
    }

    @NotNull
    @Override
    public AddPlaygroundAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ButterKnife.bind(this, parent);
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.playgrounds_add_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AddPlaygroundAdapter.ViewHolder holder, int position) {
        if (playgrounds == null || playgrounds.isEmpty()) return;

        Playground playground = playgrounds.get(position);
        if (playground.isSyncPending())
            holder._titleText.setTextColor(Color.LTGRAY);
        else
            holder._titleText.setTextColor(Color.BLACK);

        holder.bindTo(playground);
    }

    @Override
    public int getItemCount() {
        return playgrounds.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.add_item_title) TextView _titleText;
        @BindView(R.id.add_item_address) TextView _addressText;
        @BindView(R.id.add_item_button) ImageButton _imageButton;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            _imageButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    subscribeToPlayground(position, playgrounds.get(position));
                }
            });
        }

        void bindTo(Playground playground) {
            _titleText.setText(playground.getName());
            _addressText.setText(String.format("%s %s", playground.getStreetName(), playground.getStreetNumber()));
        }
    }

    void filterSubscribedPlaygrounds(Subscriptions subscriptions) {
        if (subscriptions == null || subscriptions.getSubscriptions().isEmpty()) return;

        Timber.e("filterplaygroundlist");
        playgrounds.removeIf(subscriptions.getSubscriptions()::contains);
        notifyDataSetChanged();
        isFilterCalled = true;
        this.subscriptions = subscriptions;
    }

    void updatePlaygroundList(List<Playground> playgroundList) {
        if (playgroundList == null || playgroundList.isEmpty()) return;

        Timber.e("updatePlaygroundList");
        this.playgrounds.clear();
        this.playgrounds.addAll(playgroundList);
        if (isFilterCalled){
            filterSubscribedPlaygrounds(subscriptions);
            isFilterCalled = false;
        }
        else {
            notifyDataSetChanged();
        }
    }

    private void subscribeToPlayground(int position, Playground playground){
        if (playgrounds == null || playgrounds.isEmpty()) return;

        Toast.makeText(context, "Legeplads er tilføjet", Toast.LENGTH_SHORT).show();
        playgrounds.remove(position);

        if (subscriptions == null)
            subscriptions = new Subscriptions(RemoteDataSource.loggedInUser.getUsername(), new ArrayList<>());

        subscriptions.getSubscriptions().add(playground);
        userViewModel.updateSubscriptionsLocally(RemoteDataSource.loggedInUser, subscriptions.getSubscriptions());
    }

}
