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
import com.example.kobenhavn.dal.local.model.inmemory.LoggedInUser;
import com.example.kobenhavn.viewmodel.UserViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class AddPlaygroundAdapter extends RecyclerView.Adapter<AddPlaygroundAdapter.ViewHolder> {

    private List<Playground> playgrounds;
    private Context context;
    private UserViewModel userViewModel;

    public AddPlaygroundAdapter(Context context, List<Playground> playgrounds, UserViewModel userViewModel) {
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

        Toast.makeText(context, "Legeplads er tilføjet", Toast.LENGTH_SHORT).show();
        List<Playground> d = LoggedInUser.user.getSubscribedPlaygrounds();
        d.add(playground);
        LoggedInUser.user.setSubscribedPlaygrounds(d);
        LoggedInUser.user.setPassword("hahahahah");
        userViewModel.update(LoggedInUser.user);
        notifyDataSetChanged();
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


        List<Playground> subscribed = LoggedInUser.user.getSubscribedPlaygrounds();
        Timber.e("updateplaygroundlist %s", subscribed);
        playgroundList.removeIf(subscribed::contains);
        this.playgrounds.addAll(playgroundList);
        notifyDataSetChanged();

    }

}
