package com.example.kobenhavn.view.playgrounds.add;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.EventModel;
import com.example.kobenhavn.dal.local.model.PlaygroundModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPlaygroundAdapter extends RecyclerView.Adapter<AddPlaygroundAdapter.ViewHolder> {

    private List<PlaygroundModel> legepladsData;
    private Context context;
    private OnItemClickListener listener;

    public AddPlaygroundAdapter(Context context, ArrayList<PlaygroundModel> playgroundData) {
        this.legepladsData = playgroundData;
        this.context = context;
    }

    @NotNull
    @Override
    public AddPlaygroundAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ButterKnife.bind(this, parent);
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.playgrounds_add_item, parent, false));
    }

    public void addItem(int position, PlaygroundModel playgroundData){
        legepladsData.remove(position);
        notifyDataSetChanged();
        Toast.makeText(context, "Legeplads er tilføjet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBindViewHolder(AddPlaygroundAdapter.ViewHolder holder, int position) {
        PlaygroundModel currentPlayground = legepladsData.get(position);
        holder.bindTo(currentPlayground);
    }

    @Override
    public int getItemCount() {
        return legepladsData.size();
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
                    addItem(position, legepladsData.get(position));
                }
            });
        }

        void bindTo(PlaygroundModel playgroundData) {
            _titleText.setText(playgroundData.getTitle());
            _addressText.setText(playgroundData.getAddress());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(EventModel eventModel);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
