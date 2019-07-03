package de.schad.mi.webmvc.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Observation {

    @Id
    @GeneratedValue
    private long id;

    private String finder;

    private String location;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String[] daytime;

    private String description;

    private String rating;

    private String image;

    private double longitude;
    private double latitude;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("observation")
    private List<Comment> comments;

    public Observation() {
    }

    public Observation(String finder, String location, LocalDate date, String[] daytime, String description,
            String rating, String image, double longitude, double latitude) {
        this.finder = finder;
        this.location = location;
        this.date = date;
        this.daytime = daytime;
        this.description = description;
        this.rating = rating;
        this.image = image;
        this.setLongitude(longitude);
        this.setLatitude(latitude);
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "{" + " finder='" + getFinder() + "'" + ", location='" + getLocation() + "'" + ", date='" + getDate()
                + "'" + ", description='" + getDescription() + "'" + "}";
    }

}