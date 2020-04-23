package com.example.kobenhavn.view.events.future;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.Subscription;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FutureAdapter extends RecyclerView.Adapter<FutureAdapter.ViewHolder> {
    private List<Event> futureEvents = new ArrayList<>();
    private Context context;
    private OnItemClickListener listener;

    FutureAdapter(Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public FutureAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.events_future_item, parent, false));
    }

    public void onBindViewHolder(FutureAdapter.ViewHolder holder, int position) {
        holder.bindTo(position);
    }

    @Override
    public int getItemCount() {
        return futureEvents.size();
    }

    void handleFutureEvents(List<Subscription> subscriptions) {
        if (subscriptions == null) return;

        futureEvents.clear();
        for (Subscription subscription : subscriptions){
            for (Event event : subscription.getPlayground().getEvents()){
                Date date = event.getDetails().getDate();
                if (DateUtils.isToday(date.getTime()) || date.after(new Date(System.currentTimeMillis()))){
                    futureEvents.add(event);
                }
            }
        }
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.future_time_text) TextView _timeText;
        @BindView(R.id.future_playground_name) TextView _playgroundName;
        @BindView(R.id.future_title_text) TextView _titleText;
        @BindView(R.id.future_interested_text) TextView _interestedText;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null & position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(futureEvents.get(position));
                }
            });
        }

        void bindTo(int position) {
            Event event = futureEvents.get(position);
            _timeText.setText(event.getDetails().getDate().toString());
            _playgroundName.setText(event.getPlaygroundName());
            _titleText.setText(event.getName());
            _interestedText.setText(String.valueOf(event.getParticipants()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
