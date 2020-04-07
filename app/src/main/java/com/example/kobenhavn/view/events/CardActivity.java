package com.example.kobenhavn.view.events;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.usecases.user.LogoutUserUseCase;
import com.example.kobenhavn.viewmodel.UserViewModel;
import com.example.kobenhavn.viewmodel.UserViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class CardActivity extends AppCompatActivity {
    public static final String EXTRA_DATE = "com.example.kobenhavn.EXTRA_DATE";
    public static final String EXTRA_SUBTITLE= "com.example.kobenhavn.EXTRA_SUBTITLE";
    public static final String EXTRA_TITLE = "com.example.kobenhavn.EXTRA_TITLE";
    public static final String EXTRA_TIME = "com.example.kobenhavn.EXTRA_TIME";
    public static final String EXTRA_DESCRIPTION = "com.example.kobenhavn.EXTRA_DESCRIPTION";
    public static final String EXTRA_INTERESTED = "com.example.kobenhavn.EXTRA_INTERESTED";
    public static final String EXTRA_PLAYGROUND_NAME = "com.example.kobenhavn.EXTRA_PLAYGROUND_NAME";
    public static final String EXTRA_EVENT_ID = "com.example.kobenhavn.EXTRA_EVENT_ID";

    @BindView(R.id.future_date_text) TextView _dateText;
    @BindView(R.id.future_subtitle_text) TextView _subtitleText;
    @BindView(R.id.future_title_text) TextView _titleText;
    @BindView(R.id.future_time_text) TextView _timeText;
    @BindView(R.id.future_description_text) TextView _descriptionText;
    @BindView(R.id.future_interested_text) TextView _interestedText;
    @BindView(R.id.deltag) Button _enrollButton;

    @Inject
    UserViewModelFactory userViewModelFactory;

    private UserViewModel userViewModel;
    private String playgroundName;
    private String eventID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_card_activity);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        _dateText.setText(intent.getStringExtra(EXTRA_DATE));
        _subtitleText.setText(intent.getStringExtra(EXTRA_SUBTITLE));
        _titleText.setText(intent.getStringExtra(EXTRA_TITLE));
        _timeText.setText(intent.getStringExtra(EXTRA_TIME));
        _descriptionText.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        _interestedText.setText(String.valueOf(intent.getStringExtra(EXTRA_INTERESTED)));
        playgroundName = intent.getStringExtra(EXTRA_PLAYGROUND_NAME);
        eventID = intent.getStringExtra(EXTRA_EVENT_ID);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText(intent.getStringExtra(EXTRA_DATE));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);
    }

    @OnClick(R.id.deltag)
    void onJoinEventClick(){
        Toast.makeText(this, "Du er nu tilmeldt", Toast.LENGTH_SHORT).show();
        User user = RemoteDataSource.loggedInUser;
        userViewModel.joinEvent(playgroundName, eventID, user.getUsername(), user.getEvents());
    }
}
