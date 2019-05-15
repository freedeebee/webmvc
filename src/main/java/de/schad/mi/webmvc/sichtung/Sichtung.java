package de.schad.mi.webmvc.sichtung;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Sichtung
 */
public class Sichtung {

    private String[] daytimecbs = {"morgens", "mittags", "abends"};
    private String[] ratingsbs = {"*", "**", "***", "****", "*****"};

    @Size(min = 3, message = "Muss mindestens *{min} Zeichen lang sein")
    private String finder;

    @NotBlank(message = "Dieses Feld darf nicht leer sein")
    private String location;

    @PastOrPresent(message = "Datum muss in der Zukunft liegen")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "Mindestens eins auswählen")
    private String[] daytime;

    @Size(max = 80, message = "Dieses Feld darf maximal *{max} Zeichen enthalten und darf nicht leer sein")
    @NotBlank
    private String description;

    private String rating;

    public Sichtung() {}

    public Sichtung(String finder, String location, LocalDate date, String[] daytime, String description, String rating) {
        this.finder = finder;
        this.location = location;
        this.date = date;
        this.daytime = daytime;
        this.description = description;
        this.rating = rating;
    }

    public String[] getDaytimecbs() {
        return this.daytimecbs;
    }

    public String[] getRatingsbs() {
        return this.ratingsbs;
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

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public String[] getDaytime() {
        return this.daytime;
    }

    public void setDaytime(String[] daytime) {
        this.daytime = daytime;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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