package com.example.kobenhavn.view.events.future;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.Event;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FutureAdapter extends RecyclerView.Adapter<FutureAdapter.ViewHolder> {
    private List<Event> event;
    private Context context;
    private OnItemClickListener listener;

    public FutureAdapter(Context context, List<Event> event) {
        this.event = event;
        this.context = context;
    }

    @NotNull
    @Override
    public FutureAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.events_future_item, parent, false));
    }

    public void onBindViewHolder(FutureAdapter.ViewHolder holder, int position) {
        Event currentEvent = event.get(position);
        holder.bindTo(currentEvent);
    }

    @Override
    public int getItemCount() {
        return event.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.future_date_text) TextView _dateText;
        @BindView(R.id.future_subtitle_text) TextView _subtitleText;
        @BindView(R.id.future_title_text) TextView _titleText;
        @BindView(R.id.future_time_text) TextView _timeText;
        @BindView(R.id.future_description_text) TextView _descriptionText;
        @BindView(R.id.future_interested_text) TextView _interestedText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null & position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(event.get(position));
                }
            });
        }

        void bindTo(Event event) {
            _dateText.setText(event.getDetails().getDate().toString());
            _subtitleText.setText(event.getSubtitle());
            _titleText.setText(event.getName());
            _timeText.setText(event.getDetails().getStartTime().toString());
            _descriptionText.setText(event.getDescription());
            _interestedText.setText(event.getParticipants());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
