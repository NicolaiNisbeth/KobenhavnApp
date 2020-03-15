package com.example.kobenhavn.ui.aktiviteter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kobenhavn.R;


// https://github.com/yuyakaido/CardStackView
public class CardActivity extends AppCompatActivity {
    public static final String EXTRA_DATO = "com.example.kobenhavn.EXTRA_DATO";
    public static final String EXTRA_SUBTITLE= "com.example.kobenhavn.EXTRA_SUBTITLE";
    public static final String EXTRA_TITLE = "com.example.kobenhavn.EXTRA_TITLE";
    public static final String EXTRA_TID = "com.example.kobenhavn.EXTRA_TID";
    public static final String EXTRA_BESKRIVELSE = "com.example.kobenhavn.EXTRA_BESKRIVELSE";
    public static final String EXTRA_INTERESSERET = "com.example.kobenhavn.EXTRA_INTERESSERET";

    private TextView dato_tekst;
    private TextView subtitle_tekst;
    private TextView title_tekst;
    private TextView tid_tekst;
    private TextView beskrivelse_tekst;
    private TextView interesseret_tekst;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aktiviteter_card_activity);

        dato_tekst = findViewById(R.id.legepladser_info_titel);
        subtitle_tekst = findViewById(R.id.legepladser_info_adresse);
        title_tekst = findViewById(R.id.legepladser_info_beskrivelse);
        tid_tekst = findViewById(R.id.legepladser_info_kontaktpersoner);
        beskrivelse_tekst = findViewById(R.id.beskrivelse_tekst);
        interesseret_tekst = findViewById(R.id.interesseret_tekst);

        Intent intent = getIntent();
        dato_tekst.setText(intent.getStringExtra(EXTRA_DATO));
        subtitle_tekst.setText(intent.getStringExtra(EXTRA_SUBTITLE));
        title_tekst.setText(intent.getStringExtra(EXTRA_TITLE));
        tid_tekst.setText(intent.getStringExtra(EXTRA_TID));
        beskrivelse_tekst.setText(intent.getStringExtra(EXTRA_BESKRIVELSE));
        interesseret_tekst.setText(intent.getStringExtra(EXTRA_INTERESSERET));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText(intent.getStringExtra(EXTRA_DATO));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }
}
