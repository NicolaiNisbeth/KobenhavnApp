package com.example.kobenhavn.ui.aktiviteter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.example.kobenhavn.R;

public class AktivitetActivity extends AppCompatActivity {
    public static final String EXTRA_DATO = "com.example.kobenhavn.EXTRA_DATO";
    public static final String EXTRA_SUBTITLE= "com.example.kobenhavn.EXTRA_SUBTITLE";
    public static final String EXTRA_TITLE = "com.example.kobenhavn.EXTRA_TITLE";
    public static final String EXTRA_BESKRIVELSE = "com.example.kobenhavn.EXTRA_BESKRIVELSE";
    public static final String EXTRA_INTERESSERET = "com.example.kobenhavn.EXTRA_INTERESSERET";

    private TextView dato_tekst;
    private TextView subtitle_tekst;
    private TextView title_tekst;
    private TextView beskrivelse_tekst;
    private TextView interesseret_tekst;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aktiviteter_aktivitet_activity);

        dato_tekst = findViewById(R.id.dato_tekst);
        subtitle_tekst = findViewById(R.id.subtitle_tekst);
        title_tekst = findViewById(R.id.title_tekst);
        beskrivelse_tekst = findViewById(R.id.beskrivelse_tekst);
        interesseret_tekst = findViewById(R.id.interesseret_tekst);

        Intent intent = getIntent();
        dato_tekst.setVisibility(View.INVISIBLE);
        subtitle_tekst.setText(intent.getStringExtra(EXTRA_SUBTITLE));
        title_tekst.setText(intent.getStringExtra(EXTRA_TITLE));
        beskrivelse_tekst.setText(intent.getStringExtra(EXTRA_BESKRIVELSE));
        interesseret_tekst.setText(intent.getStringExtra(EXTRA_INTERESSERET));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText(intent.getStringExtra(EXTRA_DATO));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setTitle(intent.getStringExtra(EXTRA_DATO));
    }
}
