package com.example.movies;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailAdapter.MovieDetailHolder> {
    private List<Trailers> list = new ArrayList<>();

    private OnTrailerClickListener onTrailerClickListener;

    public void setOnTrailerClickListener(OnTrailerClickListener onTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener;
    }

    public void setList(List<Trailers> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_details,
                parent,
                false);
        return new MovieDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDetailHolder holder, int position) {
        Trailers trailers = list.get(position);
        holder.textView.setText(trailers.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onTrailerClickListener!= null ){
                    onTrailerClickListener.onTrailerClick(trailers);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MovieDetailHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        public MovieDetailHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewTrailerName);
        }
    }
    interface OnTrailerClickListener{
        void onTrailerClick(Trailers trailers);
    }

}
