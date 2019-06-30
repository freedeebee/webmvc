package de.schad.mi.webmvc.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import de.schad.mi.webmvc.annotations.Siebzehnhaft;

/**
 * ObservationCreationForm
 */
public class ObservationCreationForm {

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

    private MultipartFile image;


    public ObservationCreationForm() {
    }

    public ObservationCreationForm(String location, LocalDate date, String[] daytime, String description, String rating, MultipartFile image) {
        this.location = location;
        this.date = date;
        this.daytime = daytime;
        this.description = description;
        this.rating = rating;
        this.image = image;
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

    public MultipartFile getImage() {
        return this.image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

}