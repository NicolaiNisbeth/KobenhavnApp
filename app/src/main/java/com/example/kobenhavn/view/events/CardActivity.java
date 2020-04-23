package com.example.kobenhavn.view.events;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.kobenhavn.R;
import com.example.kobenhavn.dal.local.model.Details;
import com.example.kobenhavn.dal.local.model.Event;
import com.example.kobenhavn.dal.local.model.User;
import com.example.kobenhavn.dal.remote.RemoteDataSource;
import com.example.kobenhavn.viewmodel.UserViewModel;
import com.example.kobenhavn.viewmodel.UserViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class CardActivity extends AppCompatActivity {
    public static final String EXTRA_DATE = "com.example.kobenhavn.EXTRA_DATE";
    public static final String EXTRA_SUBTITLE= "com.example.kobenhavn.EXTRA_SUBTITLE";
    public static final String EXTRA_NAME = "com.example.kobenhavn.EXTRA_TITLE";
    public static final String EXTRA_STARTTIME = "com.example.kobenhavn.EXTRA_TIME";
    public static final String EXTRA_DESCRIPTION = "com.example.kobenhavn.EXTRA_DESCRIPTION";
    public static final String EXTRA_INTERESTED = "com.example.kobenhavn.EXTRA_INTERESTED";
    public static final String EXTRA_PLAYGROUND_NAME = "com.example.kobenhavn.EXTRA_PLAYGROUND_NAME";
    public static final String EXTRA_EVENT_ID = "com.example.kobenhavn.EXTRA_EVENT_ID";
    public static final String EXTRA_IMAGE_PATH = "com.example.kobenhavn.EXTRA_IMAGE_PATH";

    @BindView(R.id.future_playground_name_text) TextView _playgroundNameText;
    @BindView(R.id.future_title_text) TextView _titleText;
    @BindView(R.id.future_time_text) TextView _timeText;
    @BindView(R.id.future_description_text) TextView _descriptionText;
    @BindView(R.id.deltag) Button _enrollButton;

    @Inject
    UserViewModelFactory userViewModelFactory;

    private UserViewModel userViewModel;
    private String playgroundName;
    private String eventID;
    private Event event;
    private User user;
    private Details details;
    private boolean enrolled;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_card_activity);
        ButterKnife.bind(this);

        // TODO: beautify date presentation
        Intent intent = getIntent();
        details = intent.getParcelableExtra(EXTRA_DATE);
        playgroundName = intent.getStringExtra(EXTRA_PLAYGROUND_NAME);
        eventID = intent.getStringExtra(EXTRA_EVENT_ID);
        _playgroundNameText.setText(playgroundName);
        _titleText.setText(intent.getStringExtra(EXTRA_NAME));
        _timeText.setText(String.format("%s - %s", intent.getStringExtra(EXTRA_STARTTIME), details.getEndTime().toString()));
        _descriptionText.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        user = RemoteDataSource.loggedInUser;
        event = new Event(
                eventID,
                user.getUsername(),
                intent.getStringExtra(EXTRA_NAME),
                intent.getStringExtra(EXTRA_IMAGE_PATH),
                intent.getStringExtra(EXTRA_SUBTITLE),
                intent.getStringExtra(EXTRA_DESCRIPTION),
                intent.getIntExtra(EXTRA_INTERESTED, 0),
                intent.getStringExtra(EXTRA_PLAYGROUND_NAME),
                details
        );

        setupToolbar();

        userViewModel = ViewModelProviders.of(this, userViewModelFactory).get(UserViewModel.class);
        LiveData<List<Event>> eventLiveData = userViewModel.getEventsLive(user.getUsername());
        eventLiveData.observe(this, this::updateBtnLayout);
    }

    private void updateBtnLayout(List<Event> enrolledEvents) {
        enrolled = enrolledEvents.contains(event);
        if (enrolled){
            int color = getResources().getColor(R.color.design_default_color_error);
            _enrollButton.setBackgroundColor(color);
            _enrollButton.setText("Afmeld");
        }
        else {
            int color = getResources().getColor(R.color.colorPrimary);
            _enrollButton.setBackgroundColor(color);
            _enrollButton.setText("Tilmeld");
        }
    }

    @OnClick(R.id.deltag)
    void onBtnClick(){
        if (enrolled){
            Toast.makeText(this, "Du er nu afmeldt", Toast.LENGTH_SHORT).show();
            userViewModel.removeEventFromUser(event, user, playgroundName);
            finish();
        }
        else {
            Toast.makeText(this, "Du er nu tilmeldt", Toast.LENGTH_SHORT).show();
            userViewModel.joinEvent(event, user, playgroundName);
            finish();

        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText(details.getDate().toString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
