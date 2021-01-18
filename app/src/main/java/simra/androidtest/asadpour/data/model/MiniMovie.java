package simra.androidtest.asadpour.data.model;

import com.google.gson.annotations.SerializedName;

public class MiniMovie {
    @SerializedName("Title")
    private String title;
    @SerializedName("Year")
    private String year;
    private String imdbID;
    @SerializedName("Type")
    private String type;
    @SerializedName("Poster")
    private String posterImageUrl;

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return type;
    }

    public String getPosterImageUrl() {
        return posterImageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MiniMovie miniMovie = (MiniMovie) o;

        if (title != null ? !title.equals(miniMovie.title) : miniMovie.title != null) return false;
        if (year != null ? !year.equals(miniMovie.year) : miniMovie.year != null) return false;
        if (imdbID != null ? !imdbID.equals(miniMovie.imdbID) : miniMovie.imdbID != null)
            return false;
        if (type != null ? !type.equals(miniMovie.type) : miniMovie.type != null) return false;
        return posterImageUrl != null ? posterImageUrl.equals(miniMovie.posterImageUrl) : miniMovie.posterImageUrl == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (imdbID != null ? imdbID.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (posterImageUrl != null ? posterImageUrl.hashCode() : 0);
        return result;
    }
}
