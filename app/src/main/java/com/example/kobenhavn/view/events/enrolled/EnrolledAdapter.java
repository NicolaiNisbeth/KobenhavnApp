package com.example.kobenhavn.view.events.enrolled;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnrolledAdapter extends RecyclerView.Adapter<EnrolledAdapter.ViewHolder> {

    private Context context;
    private OnItemClickListener listener;
    private ArrayList<Event> events;

    EnrolledAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NotNull
    @Override
    public EnrolledAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.events_enrolled_item, parent, false));
    }

    @Override
    public void onBindViewHolder(EnrolledAdapter.ViewHolder holder, int position) {
        holder.bindTo(position);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    void updateEnrolledList(List<Event> events) {
        if (events == null) return;
        this.events.clear();
        this.events.addAll(events);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.enrolled_date_text) TextView _dateText;
        @BindView(R.id.enrolled_subtitle_text) TextView _subtitleText;
        @BindView(R.id.ennrolled_title_text) TextView _titleText;
        @BindView(R.id.enrolled_time_text) TextView _timeText;
        @BindView(R.id.enrolled_interested_text) TextView _interestedText;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(events.get(position));
                }
            });
        }

        void bindTo(int position) {
            final Event event = events.get(position);
            System.out.println("bindTo " + event);
            _dateText.setText(event.getDetails().getDate().toString());
            _subtitleText.setText(event.getSubtitle());
            _titleText.setText(event.getName());
            _timeText.setText(event.getDetails().getStartTime().toString());
            _interestedText.setText(String.valueOf(event.getParticipants()));
            _titleText.setTextColor(event.isSyncPending() ? Color.LTGRAY : Color.BLACK);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Event eventModel);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
