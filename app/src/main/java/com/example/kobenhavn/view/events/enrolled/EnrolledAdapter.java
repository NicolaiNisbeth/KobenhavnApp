package com.example.kobenhavn.view.events.enrolled;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.EventModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnrolledAdapter extends RecyclerView.Adapter<EnrolledAdapter.ViewHolder> {

    private List<EventModel> eventData;
    private Context context;
    private OnItemClickListener listener;

    public EnrolledAdapter(Context context, ArrayList<EventModel> eventData) {
        this.eventData = eventData;
        this.context = context;
    }

    @NotNull
    @Override
    public EnrolledAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.events_enrolled_item, parent, false));
    }


    public void deleteItem(int position){
        Toast.makeText(context, eventData.get(position).getTitle() + " blev fjernet.", Toast.LENGTH_SHORT).show();
        eventData.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(EnrolledAdapter.ViewHolder holder, int position) {
        EventModel eventModel = eventData.get(position);
        holder.bindTo(eventModel);
    }

    @Override
    public int getItemCount() {
        return eventData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.enrolled_date_text) TextView _dateText;
        @BindView(R.id.enrolled_subtitle_text) TextView _subtitleText;
        @BindView(R.id.ennrolled_title_text) TextView _titleText;
        @BindView(R.id.enrolled_time_text) TextView _timeText;
        @BindView(R.id.enrolled_interested_text) TextView _interestedText;
        @BindView(R.id.enrolled_delete_btn) FloatingActionButton _deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(eventData.get(position));
                }
            });

            _deleteButton.setOnClickListener(v -> deleteItem(getAdapterPosition()));
        }

        void bindTo(EventModel event) {
            _dateText.setText(event.getDate());
            _subtitleText.setText(event.getSubtitle());
            _titleText.setText(event.getTitle());
            _timeText.setText(event.getTime());
            _interestedText.setText(event.getInterested());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(EventModel eventModel);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
