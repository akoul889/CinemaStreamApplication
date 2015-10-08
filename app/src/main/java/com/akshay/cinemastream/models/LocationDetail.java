package com.akshay.cinemastream.models;

/**
 * Created by AKSHAY on 03-10-2015.
 */
public class LocationDetail {
    /**
     * Id : 652a1679
     * Name : The Great Pyramids, Giza Plateau, Giza, Egypt
     * State :  Giza
     * Country :  Egypt
     */

    private String Id;
    private String Name;
    private String State;
    private String Country;

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setState(String State) {
        this.State = State;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getState() {
        return State;
    }

    public String getCountry() {
        return Country;
    }
}
