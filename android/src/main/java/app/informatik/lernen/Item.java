package app.informatik.lernen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fabia on 2/16/2017.
 */

public class Item {

    private int completed = 0;

    private int progress;
    private int color;
    private int id;
    private String name;
    private String beschreibung;
    private List<Thema> themen = new ArrayList<>();

    public Item(int id, String name, String beschreibung, List<Thema> themen) {
        this.setId(id);
        this.setName(name);
        this.setBeschreibung(beschreibung);
        this.setThemen(themen);
    }

    public Item(int id, String name, String beschreibung) {
        this.setId(id);
        this.setName(name);
        this.setBeschreibung(beschreibung);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public List<Thema> getThemen() {
        return themen;
    }

    public void setThemen(List<Thema> themen) {
        this.themen = themen;
    }

    public void addThema(Thema t) {
        this.themen.add(t);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
