package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class FavouritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        RecyclerView recyclerView = findViewById(R.id.favouritesRecycler);
        MoviesAdapter moviesAdapter = new MoviesAdapter();

        recyclerView.setAdapter(moviesAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        moviesAdapter.setOnIntentListener(new MoviesAdapter.OnIntentListener() {
            @Override
            public void intentPressAdapter(Movie movie) {
                Intent intent = MovieDetailActivity.intentMovieDetailActivity(FavouritesActivity.this,movie);
                startActivity(intent);
            }
        });

        FavouritesViewModel favouritesViewModel = new ViewModelProvider(this)
                .get(FavouritesViewModel.class);


        favouritesViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesAdapter.setMovies(movies);
            }
        });
    }
    public static Intent getIntentFavoritesActivity(Context context){
        return new Intent(context, FavouritesActivity.class);
    }
}