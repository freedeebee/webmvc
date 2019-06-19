package de.schad.mi.webmvc.model.data;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;

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

    public Observation() {
    }

    public Observation(String finder, String location, LocalDate date, String[] daytime, String description,
            String rating, String image) {
        this.finder = finder;
        this.location = location;
        this.date = date;
        this.daytime = daytime;
        this.description = description;
        this.rating = rating;
        this.image = image;
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

    @Override
    public String toString() {
        return "{" + " finder='" + getFinder() + "'" + ", location='" + getLocation() + "'" + ", date='" + getDate()
                + "'" + ", description='" + getDescription() + "'" + "}";
    }

    public void setObservation(Observation observation) {
        setImage(observation.getImage());
        setRating(observation.getRating());
        setDescription(observation.getDescription());
        setDaytime(observation.getDaytime());
        setLocation(observation.getLocation());
        setFinder(observation.getFinder());
    }

    public String toJason() throws JSONException {
        JSONObject jo =new JSONObject();
        jo.put("id", getId());
        jo.put("finder", getFinder());
        jo.put("location", getLocation());
        jo.put("date", getDate());
        jo.put("daytime", getDaytime());
        jo.put("description", getDescription());
        jo.put("rating", getRating());
        jo.put("image", getImage());

		return jo.toString();
	}

}