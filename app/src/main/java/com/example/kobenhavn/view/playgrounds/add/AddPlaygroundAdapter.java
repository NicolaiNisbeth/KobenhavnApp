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
import com.example.kobenhavn.dal.local.model.EventModel;
import com.example.kobenhavn.dal.local.model.Playground;
import com.example.kobenhavn.dal.local.model.PlaygroundModel;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.viewmodel.UserViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPlaygroundAdapter extends RecyclerView.Adapter<AddPlaygroundAdapter.ViewHolder> {

    private List<Playground> playgrounds;
    private Context context;
    private final UserViewModel userViewModel;
    private final User loggedInUSer;

    public AddPlaygroundAdapter(Context context, List<Playground> playgrounds, UserViewModel userViewModel) {
        this.playgrounds = playgrounds;
        this.context = context;
        this.userViewModel = userViewModel;
        loggedInUSer = userViewModel.getLoggedInUser();
    }

    @NotNull
    @Override
    public AddPlaygroundAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ButterKnife.bind(this, parent);
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.playgrounds_add_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AddPlaygroundAdapter.ViewHolder holder, int position) {
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

    public void addItem(int position, Playground playground){
        playgrounds.remove(position);
        notifyDataSetChanged();
        Toast.makeText(context, "Legeplads er tilføjet", Toast.LENGTH_SHORT).show();

        loggedInUSer.getSubscribedPlaygrounds().add(playground);
        userViewModel.update(loggedInUSer);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.add_item_title) TextView _titleText;
        @BindView(R.id.add_item_address) TextView _addressText;
        @BindView(R.id.add_item_button) ImageButton _imageButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            _imageButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    addItem(position, playgrounds.get(position));
                }
            });
        }

        void bindTo(Playground playground) {
            _titleText.setText(playground.getName());
            _addressText.setText(String.format("%s %s", playground.getStreetName(), playground.getStreetNumber()));
        }
    }

    public void updatePlaygroundList(List<Playground> playgroundList) {
        this.playgrounds.clear();

        List<Playground> subscribed = loggedInUSer.getSubscribedPlaygrounds();
        this.playgrounds.addAll(
                playgroundList.stream()
                        .filter(p -> !subscribed.contains(p))
                        .collect(Collectors.toList()));
        notifyDataSetChanged();

    }

}
