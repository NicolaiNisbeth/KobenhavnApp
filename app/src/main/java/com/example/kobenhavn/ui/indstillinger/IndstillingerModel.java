package com.example.kobenhavn.ui.indstillinger;

public class IndstillingerModel {
    private String navn;
    private String telefonNummer;
    private String email;
    private String kode;

    public IndstillingerModel(String navn, String telefonNummer, String email, String kode) {
        this.navn = navn;
        this.telefonNummer = telefonNummer;
        this.email = email;
        this.kode = kode;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getTelefonNummer() {
        return telefonNummer;
    }

    public void setTelefonNummer(String telefonNummer) {
        this.telefonNummer = telefonNummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }
}
