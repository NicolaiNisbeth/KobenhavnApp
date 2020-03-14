package com.example.kobenhavn.ui.aktiviteter;

public class AktivitetModel {
    private String dato, subtitle, title, beskrivelse, interesseret;

    public AktivitetModel(String dato, String subtitle, String title, String beskrivelse, String interesseret) {
        this.dato = dato;
        this.subtitle = subtitle;
        this.title = title;
        this.beskrivelse = beskrivelse;
        this.interesseret = interesseret;
    }


    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public String getInteresseret() {
        return interesseret;
    }

    public void setInteresseret(String interesseret) {
        this.interesseret = interesseret;
    }
}
