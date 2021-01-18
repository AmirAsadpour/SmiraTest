package simra.androidtest.asadpour.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie extends MiniMovie {
    @SerializedName("Released")
    private String released;
    @SerializedName("Director")
    private String director;
    @SerializedName("Actors")
    private String actors;
    @SerializedName("Plot")
    private String plot;
    @SerializedName("Genre")
    private String genre;
    @SerializedName("Ratings")
    private List<MovieRating> ratings;

    public String getReleased() {
        return released;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getGenre() {
        return genre;
    }

    public List<MovieRating> getRatings() {
        return ratings;
    }
}