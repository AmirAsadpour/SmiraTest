package simra.androidtest.asadpour.data.model;

import com.google.gson.annotations.SerializedName;

public class MovieRating {
    @SerializedName("Source")
    private String source;
    @SerializedName("Value")
    private String value;

    public String getSource() {
        return source;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieRating that = (MovieRating) o;

        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = source != null ? source.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
