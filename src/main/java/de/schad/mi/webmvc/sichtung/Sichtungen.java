package de.schad.mi.webmvc.sichtung;

import java.util.ArrayList;
import java.util.List;

/**
 * Sichtungen
 */
public class Sichtungen {

    private List<Sichtung> sichtungen;

    public Sichtungen() {
        this.sichtungen = new ArrayList<>();
    }

    public void add(Sichtung sichtung) {
        sichtungen.add(sichtung);
    }

    public List<Sichtung> getList() {
        return this.sichtungen;
    }
}