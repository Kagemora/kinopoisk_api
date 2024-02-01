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
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {
    private final String logMainViewModel = "MainViewModel";

    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int page = 1;

    public MainViewModel(@NonNull Application application) {
        super(application);
        loadMovies();//т.к. виьюмодел может переживать уничтожение активти, то добавляем в конструктор
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public void loadMovies() {
        Boolean loading = isLoading.getValue();
        if(loading!=null && loading){
            return;
        }
        Disposable disposable = ApiFactory.apiService.loadMovies(page)//загружаем фильмы
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoading.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isLoading.setValue(false);
                    }
                })
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Throwable {
                        List<Movie> loadedMovies = movies.getValue();
                        if (loadedMovies != null) {
                            loadedMovies.addAll(movieResponse.getMovies());//если ранее были загружены фильм, добавляем
                            movies.setValue(loadedMovies);
                        } else {
                            movies.setValue(movieResponse.getMovies());//если не были загружены добавляем с 0
                        }
                        Log.d(logMainViewModel, "Loaded: " + page);
                        page++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.e(logMainViewModel, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);


    }
}
