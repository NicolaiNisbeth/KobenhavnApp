package com.example.kobenhavn.ui.events;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kobenhavn.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardActivity extends AppCompatActivity {
    public static final String EXTRA_DATE = "com.example.kobenhavn.EXTRA_DATE";
    public static final String EXTRA_SUBTITLE= "com.example.kobenhavn.EXTRA_SUBTITLE";
    public static final String EXTRA_TITLE = "com.example.kobenhavn.EXTRA_TITLE";
    public static final String EXTRA_TIME = "com.example.kobenhavn.EXTRA_TIME";
    public static final String EXTRA_DESCRIPTION = "com.example.kobenhavn.EXTRA_DESCRIPTION";
    public static final String EXTRA_INTERESTED = "com.example.kobenhavn.EXTRA_INTERESTED";

    @BindView(R.id.future_date_text) TextView _dateText;
    @BindView(R.id.future_subtitle_text) TextView _subtitleText;
    @BindView(R.id.future_title_text) TextView _titleText;
    @BindView(R.id.future_time_text) TextView _timeText;
    @BindView(R.id.future_description_text) TextView _descriptionText;
    @BindView(R.id.future_interested_text) TextView _interestedText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_card_activity);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        _dateText.setText(intent.getStringExtra(EXTRA_DATE));
        _subtitleText.setText(intent.getStringExtra(EXTRA_SUBTITLE));
        _titleText.setText(intent.getStringExtra(EXTRA_TITLE));
        _timeText.setText(intent.getStringExtra(EXTRA_TIME));
        _descriptionText.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        _interestedText.setText(intent.getStringExtra(EXTRA_INTERESTED));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText(intent.getStringExtra(EXTRA_DATE));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
