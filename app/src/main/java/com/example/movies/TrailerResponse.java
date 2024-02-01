package com.example.movies;

import com.google.gson.annotations.SerializedName;

public class TrailerResponse {
    @SerializedName("videos")
    private Videos videos;

    public Videos getVideos() {
        return videos;
    }

    public TrailerResponse(Videos videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "TrailerResponse{" +
                "videos=" + videos +
                '}';
    }
}
