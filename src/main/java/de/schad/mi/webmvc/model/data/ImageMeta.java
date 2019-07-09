package de.schad.mi.webmvc.model.data;

import java.time.LocalDate;

/**
 * ImageMeta Model to store custom Data from ImageService
 * 
 * @author Robin Schmidt
 */
public class ImageMeta {

    LocalDate date;
    double longitude;
    double latitude;

    public ImageMeta(LocalDate date, double longitude, double latitude){
        this.date = date;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public ImageMeta(double longitude, double latitude) {
        this(null, longitude, latitude);
    }


    public void setDate(LocalDate date){
        this.date = date;
    }
    public void setLogitude(double longitude){
        this.longitude = longitude;
    }
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public LocalDate getDate(){
        return this.date;
    }

    public double getLongitude(){
        return this.longitude;
    }
    public double getLatitude(){
        return this.latitude;
    }

}