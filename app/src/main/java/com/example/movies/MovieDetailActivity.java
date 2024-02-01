package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String tagMovieDetailActivity = "MovieDetailActivity";
    private MovieDetailViewModel model;
    private static final String EXTRA_MOVIE = "movie";
    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewDescription;
    private RecyclerView recyclerView;
    private MovieDetailAdapter movieDetailAdapter;
    private ReviewAdapter reviewAdapter;
    private RecyclerView reviewRycycler;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        model = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        initViews();
        movieDetailAdapter = new MovieDetailAdapter();
        recyclerView.setAdapter(movieDetailAdapter);
        reviewAdapter = new ReviewAdapter();
        reviewRycycler.setAdapter(reviewAdapter);

        Movie movie =(Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(imageViewPoster);
        textViewTitle.setText(movie.getName());
        textViewDescription.setText(movie.getDescription());
        textViewYear.setText(String.valueOf(movie.getYear()));
        model.loadTrailers(movie.getId());
        model.loadReview(movie.getId());
        model.getTrailers().observe(this, new Observer<List<Trailers>>() {
            @Override
            public void onChanged(List<Trailers> trailers) {
                movieDetailAdapter.setList(trailers);
            }
        });
        model.getReview().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewAdapter.setList(reviews);
            }
        });
        movieDetailAdapter.setOnTrailerClickListener(new MovieDetailAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(Trailers trailers) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailers.getUrl()));
                startActivity(intent);
            }
        });
        Drawable starOff = ContextCompat.getDrawable(
                MovieDetailActivity.this,
                android.R.drawable.star_big_off
        );
        Drawable starOn = ContextCompat.getDrawable(
                MovieDetailActivity.this,
                android.R.drawable.star_big_on
        );
        imageView.setImageDrawable(starOn);
        model.getFavouriteMovie(movie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieFromDb) {
                if(movieFromDb == null){
                    imageView.setImageDrawable(starOff);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            model.insertMovie(movie);
                        }
                    });
                }else {
                    imageView.setImageDrawable(starOn);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            model.removeMovie(movie.getId());
                        }
                    });
                }
            }
        });



    }
    private void initViews(){
        imageView = findViewById(R.id.imageViewStar);
        reviewRycycler = findViewById(R.id.reviewRycycler);
        recyclerView = findViewById(R.id.detailRycycler);
        textViewDescription = findViewById(R.id.textViewDescription);
        imageViewPoster = findViewById(R.id.imageViewPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewYear = findViewById(R.id.textViewYear);
    }
    public static Intent intentMovieDetailActivity(Context context,Movie movie){
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE,movie);
        return intent;
    }
}