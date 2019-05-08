package de.schad.mi.webmvc.sichtung;

/**
 * Sichtung
 */
public class Sichtung {

    private String finder;
    private String location;
    private String date;
    private String description;

    public Sichtung() {}

    public Sichtung(String finder, String location, String date, String description) {
        this.finder = finder;
        this.location = location;
        this.date = date;
        this.description = description;
    }


    public String getFinder() {
        return this.finder;
    }

    public void setFinder(String finder) {
        this.finder = finder;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{" +
            " finder='" + getFinder() + "'" +
            ", location='" + getLocation() + "'" +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }

}