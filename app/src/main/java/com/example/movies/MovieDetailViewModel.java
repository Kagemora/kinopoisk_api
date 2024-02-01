package com.example.movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {
    private final String logMovieDetailViewModel = "MovieDetailViewModel";
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<Trailers>> trailers = new MutableLiveData<>();
    private MutableLiveData<List<Review>> review = new MutableLiveData<>();

    private final MovieDao movieDao;

    public LiveData<List<Review>> getReview() {
        return review;

    }
    public LiveData<Movie> getFavouriteMovie(int movieId){
        return movieDao.getFavouriteMovie(movieId);
    }

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDatabase.getInstance(application).movieDao();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public LiveData<List<Trailers>> getTrailers() {
        return trailers;
    }

    public void insertMovie(Movie movie){
        Disposable disposable = movieDao.insertMovie(movie)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }
    public void removeMovie(int movieId){
        Disposable disposable = movieDao.removeMovie(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }
    public void loadTrailers(int id){
      Disposable disposable =  ApiFactory.apiService.loadTrailers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<TrailerResponse, List<Trailers>>() {
                    @Override
                    public List<Trailers> apply(TrailerResponse trailerResponse) throws Throwable {
                        return trailerResponse.getVideos().getTrailers();
                    }
                })
                .subscribe(new Consumer<List<Trailers>>() {
                    @Override
                    public void accept(List<Trailers> trailerResponse) throws Throwable {
                        trailers.setValue(trailerResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(logMovieDetailViewModel, throwable.toString());
                    }
                });
      compositeDisposable.add(disposable);
    }
    public void loadReview(int id){
        Disposable disposable = ApiFactory.apiService.loadReview(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ReviewResponse, List<Review>>() {
                    @Override
                    public List<Review> apply(ReviewResponse reviewResponse) throws Throwable {
                        return reviewResponse.getReviewList();
                    }
                })
                .subscribe(new Consumer<List<Review>>() {
                    @Override
                    public void accept(List<Review> reviews) throws Throwable {
                        review.setValue(reviews);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(logMovieDetailViewModel,throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);

    }
}
