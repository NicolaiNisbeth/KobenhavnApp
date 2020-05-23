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
import com.example.kobenhavn.dal.local.CloneUtils;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.Subscription;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.viewmodel.PlaygroundsViewModel;
import com.example.kobenhavn.viewmodel.UserViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPlaygroundAdapter extends RecyclerView.Adapter<AddPlaygroundAdapter.ViewHolder> {

    private List<Playground> playgrounds;
    private Context context;
    private UserViewModel userViewModel;
    private List<Subscription> subscriptions;
    private boolean isFilterCalled;

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
    public void onBindViewHolder(@NotNull AddPlaygroundAdapter.ViewHolder holder, int position) {
        if (playgrounds == null) return;
        Playground playground = playgrounds.get(position);
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
            _titleText.setTextColor(playground.isSyncPending() ? Color.LTGRAY : Color.BLACK);
        }
    }

    public void filterSubscribedPlaygrounds(List<Subscription> subscriptions) {
        System.out.println("filtersubscribedplayground " + subscriptions);
        if (subscriptions == null) return;
        this.subscriptions = subscriptions;
        playgrounds.removeIf(playground -> subscriptions.contains(
                CloneUtils.cloneSubscription(playground, RemoteDataSource.loggedInUser.getUsername()))
        );

        notifyDataSetChanged();
        isFilterCalled = true;
    }

    void updatePlaygroundList(List<Playground> playgroundList) {
        System.out.println("updateplaygroundList " + playgroundList);
        if (playgroundList == null) return;
        playgrounds.clear();
        playgrounds.addAll(playgroundList);

        // both filter and update are async and the order matters!
        if (!isFilterCalled){
            filterSubscribedPlaygrounds(subscriptions);
            isFilterCalled = false;
        }
        else {
            notifyDataSetChanged();
        }
    }

    private void subscribeToPlayground(int position, Playground playground){
        if (playgrounds == null) return;
        Toast.makeText(context, "Legeplads er tilføjet", Toast.LENGTH_SHORT).show();
        playgrounds.remove(position);
        Subscription subscription = CloneUtils.cloneSubscription(playground, RemoteDataSource.loggedInUser.getUsername());
        userViewModel.insertSubscriptionLocally(RemoteDataSource.loggedInUser, subscription);
    }
}
