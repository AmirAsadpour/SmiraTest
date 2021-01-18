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
}
