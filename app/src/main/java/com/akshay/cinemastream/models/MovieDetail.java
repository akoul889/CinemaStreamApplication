package com.akshay.cinemastream.models;

/**
 * Created by AKSHAY on 02-10-2015.
 */
public class MovieDetail {
    /**
     * Id : e80b298d
     * ImdbId : tt0248126
     * OriginalTitle : Kabhi Khushi Kabhie Gham
     * Title : Kabhi Khushi Kabhie Gham
     * Description : Yash Raichand is a wealthy business man married to Naina Raichand and has two sons, Rahul and Rohan. Rahul was adopted since he was born. Rahul falls in love with a poor girl named Anjali but his dad forbids their love, for he has wanted him to marry his friend's daughter. Rahul marries Anjali, so his dad kicks him out of the house and moves to London with Anjali, Pooja (her young sister), and his nanny. Ten years later, Rahul and Anjali have a sum named Krish who is in forth grade. Rohan then graduates from boarding school and insists on reuniting the family together. Will he reunite them again?
     * Country : IN
     * Region : BOLLYWOOD
     * Genre : Family
     * RatingCount : 1003
     * Rating : 4.3
     * CensorRating : U/A
     * ReleaseDate : 12/14/2001
     * Runtime : 210
     * Budget : 400000000
     * Revenue : 1350000000
     * PosterPath : https://s3-ap-southeast-1.amazonaws.com/cinemalytics/movie/2B1E0C88C7A3A76463A5F2B4E979F98C.jpg
     */

    private String Id;
    private String ImdbId;
    private String OriginalTitle;
    private String Title;
    private String Description;
    private String Country;
    private String Region;
    private String Genre;
    private int RatingCount;
    private double Rating;
    private String CensorRating;
    private String ReleaseDate;
    private int Runtime;
    private int Budget;
    private int Revenue;
    private String PosterPath;

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setImdbId(String ImdbId) {
        this.ImdbId = ImdbId;
    }

    public void setOriginalTitle(String OriginalTitle) {
        this.OriginalTitle = OriginalTitle;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public void setRegion(String Region) {
        this.Region = Region;
    }

    public void setGenre(String Genre) {
        this.Genre = Genre;
    }

    public void setRatingCount(int RatingCount) {
        this.RatingCount = RatingCount;
    }

    public void setRating(double Rating) {
        this.Rating = Rating;
    }

    public void setCensorRating(String CensorRating) {
        this.CensorRating = CensorRating;
    }

    public void setReleaseDate(String ReleaseDate) {
        this.ReleaseDate = ReleaseDate;
    }

    public void setRuntime(int Runtime) {
        this.Runtime = Runtime;
    }

    public void setBudget(int Budget) {
        this.Budget = Budget;
    }

    public void setRevenue(int Revenue) {
        this.Revenue = Revenue;
    }

    public void setPosterPath(String PosterPath) {
        this.PosterPath = PosterPath;
    }

    public String getId() {
        return Id;
    }

    public String getImdbId() {
        return ImdbId;
    }

    public String getOriginalTitle() {
        return OriginalTitle;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public String getCountry() {
        return Country;
    }

    public String getRegion() {
        return Region;
    }

    public String getGenre() {
        return Genre;
    }

    public int getRatingCount() {
        return RatingCount;
    }

    public double getRating() {
        return Rating;
    }

    public String getCensorRating() {
        return CensorRating;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public int getRuntime() {
        return Runtime;
    }

    public int getBudget() {
        return Budget;
    }

    public int getRevenue() {
        return Revenue;
    }

    public String getPosterPath() {
        return PosterPath;
    }
}
