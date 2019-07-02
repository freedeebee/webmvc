package de.schad.mi.webmvc.model.data;

import java.time.LocalDate;

/**
 * ImageMeta
 */
public class ImageMeta {

    LocalDate date;
    String geolocation;

    public ImageMeta(LocalDate date, String geolocation){
        this.date = date;
        this.geolocation = geolocation;
    }


    public void setDate(LocalDate date){
        this.date = date;
    }
    public void setGeoLocation(String geolocation){
        this.geolocation = geolocation;
    }

    public LocalDate getDate(){
        return this.date;
    }
    
    public String getGeoLocation(){
        return this.geolocation;
    }

}