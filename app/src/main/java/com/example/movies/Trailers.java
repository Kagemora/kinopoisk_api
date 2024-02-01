package com.example.movies;

import com.google.gson.annotations.SerializedName;

public class Trailers {
    @SerializedName("url")
    private String url;
    @SerializedName("name")
    private String name;

    public Trailers(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Trailers{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
