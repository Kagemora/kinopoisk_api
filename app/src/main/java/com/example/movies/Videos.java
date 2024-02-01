package com.example.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Videos {
    @SerializedName("trailers")
    private List<Trailers> trailers;

    public List<Trailers> getTrailers() {
        return trailers;
    }

    public Videos(List<Trailers> trailers) {
        this.trailers = trailers;
    }

    @Override
    public String toString() {
        return "Videos{" +
                "trailers=" + trailers +
                '}';
    }
}
