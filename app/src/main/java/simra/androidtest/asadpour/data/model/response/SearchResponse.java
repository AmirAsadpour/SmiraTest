package simra.androidtest.asadpour.data.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import simra.androidtest.asadpour.data.model.MiniMovie;

public class SearchResponse {
    @SerializedName("Search")
    private List<MiniMovie> movies;

    public List<MiniMovie> getMovies() {
        return movies;
    }
}
