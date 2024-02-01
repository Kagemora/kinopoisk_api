package com.example.movies;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
    private List<Review> list = new ArrayList<>();

    public void setList(List<Review> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_details, parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        Review review = list.get(position);
        holder.textAuthor.setText(review.getAuthor());
        holder.textReview.setText(review.getReview());
        String type = review.getType();
        int idColor;
        if ("Позитивный".equals(type)) {
            idColor = android.R.color.holo_green_light;
        } else if ("Негативный".equals(type)) {
            idColor = android.R.color.holo_red_light;
        } else {
            idColor = android.R.color.holo_orange_light;
        }
        int color = ContextCompat.getColor(holder.itemView.getContext(),idColor);
        holder.linerLayoutContainer.setBackgroundColor(color);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ReviewHolder extends RecyclerView.ViewHolder {
        private final TextView textAuthor;
        private final TextView textReview;
        private final LinearLayout linerLayoutContainer;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            textAuthor = itemView.findViewById(R.id.textAuthor);
            textReview = itemView.findViewById(R.id.textReview);
            linerLayoutContainer = itemView.findViewById(R.id.linerLayoutContainer);
        }
    }
}
