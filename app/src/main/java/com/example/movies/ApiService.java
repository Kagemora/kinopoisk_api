package com.example.movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("v1.4/movie?rating.kp=7-10&sortField=votes.kp&sortType=-1&limit=30")
    @Headers("X-API-KEY: DXCXC37-SGG4EQS-JW41EJ3-NDTB0X3")
    Single<MovieResponse> loadMovies(@Query("page") int page);

    @Headers("X-API-KEY: DXCXC37-SGG4EQS-JW41EJ3-NDTB0X3")
    @GET("v1.4/movie/{id}")
    Single<TrailerResponse> loadTrailers(@Path("id") int id);

    @Headers("X-API-KEY: DXCXC37-SGG4EQS-JW41EJ3-NDTB0X3")
    @GET("v1/review?")
    Single<ReviewResponse> loadReview(@Query("movieId") int id);


}