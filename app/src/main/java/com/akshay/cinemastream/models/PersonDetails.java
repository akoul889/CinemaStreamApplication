package com.akshay.cinemastream.models;

/**
 * Created by AKSHAY on 02-10-2015.
 */
public class PersonDetails implements Comparable<PersonDetails>{
    /**
     * Id : 25e02bee
     * Name : Farida Jalal
     * Gender : F
     * ProfilePath : https://s3-ap-southeast-1.amazonaws.com/cinemalytics/person/EED9EF016477198DD513A50D107FDB70.JPG
     */

    private String Id;
    private String Name;
    private String Gender;
    private String ProfilePath;

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public void setProfilePath(String ProfilePath) {
        this.ProfilePath = ProfilePath;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getGender() {
        return Gender;
    }

    public String getProfilePath() {
        return ProfilePath;
    }

    @Override
    public int compareTo(PersonDetails another) {
        return this.getName().compareTo(another.getName());
    }
}
