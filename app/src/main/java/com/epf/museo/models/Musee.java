package com.epf.museo.models;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "musees")
public class Musee {
    @PrimaryKey
    @NonNull
    private String id;
    private String nom;
    private String periode_ouverture;
    private String adresse;
    private String ville;
    private boolean ferme;
    private String fermeture_annuelle;
    private String site_web;
    private int cp;
    private String region;
    private String dept;

    public Musee(String id, String nom, String periode_ouverture, String adresse, String ville, boolean ferme, String fermeture_annuelle, String site_web, int cp, String region, String dept) {
        this.id = id;
        this.nom = nom;
        this.periode_ouverture = periode_ouverture;
        this.adresse = adresse;
        this.ville = ville;
        this.ferme = ferme;
        this.fermeture_annuelle = fermeture_annuelle;
        this.site_web = site_web;
        this.cp = cp;
        this.region = region;
        this.dept = dept;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPeriode_ouverture() {
        return periode_ouverture;
    }

    public void setPeriode_ouverture(String periode_ouverture) {
        this.periode_ouverture = periode_ouverture;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public boolean isFerme() {
        return ferme;
    }

    public void setFerme(boolean ferme) {
        this.ferme = ferme;
    }

    public String getFermeture_annuelle() {
        return fermeture_annuelle;
    }

    public void setFermeture_annuielle(String fermeture_annuielle) {
        this.fermeture_annuelle = fermeture_annuielle;
    }

    public String getSite_web() {
        return site_web;
    }

    public void setSite_web(String site_web) {
        this.site_web = site_web;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
