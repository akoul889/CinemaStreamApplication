package com.akshay.cinemastream.models;

/**
 * Created by AKSHAY on 03-10-2015.
 */
public class SongDetails {
    /**
     * Id : 107ee0a0
     * Title : Shava Shava
     * Duration : 5.5
     */

    private String Id;
    private String Title;
    private double Duration;

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setDuration(double Duration) {
        this.Duration = Duration;
    }

    public String getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public double getDuration() {
        return Duration;
    }
}
