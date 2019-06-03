package de.schad.mi.webmvc.model.data;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import de.schad.mi.webmvc.annotations.Siebzehnhaft;

@Entity
public class Observation {

    @Transient
    private String[] daytimecbs = {"morgens", "mittags", "abends"};
    @Transient
    private String[] ratingsbs = {"*", "**", "***", "****", "*****"};

    @Id
    @GeneratedValue
    private long id;

    @Size(min = 3, message = "{sichtung.form.error.name}")
    private String finder;

    @NotBlank(message = "{sichtung.form.error.location}")
    private String location;

    @PastOrPresent(message = "{sichtung.form.error.date.future}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{sichtung.form.error.date.notnull}")
    private LocalDate date;

    @NotEmpty(message = "{sichtung.form.error.daytime}")
    private String[] daytime;

    @Size(min = 1, max = 80, message = "{sichtung.form.error.description.size}")
    @Siebzehnhaft(message = "{sichtung.form.error.description.siebzehn}")
    private String description;

    private String rating;

    public Observation() {}

    public Observation(String finder, String location, LocalDate date, String[] daytime, String description, String rating) {
        this.finder = finder;
        this.location = location;
        this.date = date;
        this.daytime = daytime;
        this.description = description;
        this.rating = rating;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
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