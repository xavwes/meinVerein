package de.xwes.meinverein.main.model;

import java.io.Serializable;

/**
 * Created by Xaver on 06.06.2015.
 */
public class Game implements Serializable
{
    private long id;
    private String home;
    private String away;
    private String ergebnis;
    private String ort;
    private String zeit;


    public Game(String home, String away, String ergebnis, String ort, String zeit) {
        this.id = id;
        this.home = home;
        this.away = away;
        this.ergebnis = ergebnis;
        this.ort = ort;
        this.zeit = zeit;
    }

    public Game()
    {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getErgebnis() {
        return ergebnis;
    }

    public void setErgebnis(String ergebnis) {
        this.ergebnis = ergebnis;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getZeit() {
        return zeit;
    }

    public void setZeit(String zeit) {
        this.zeit = zeit;
    }
}
