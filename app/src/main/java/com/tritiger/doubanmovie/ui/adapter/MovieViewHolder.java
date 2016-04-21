package com.tritiger.doubanmovie.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tritiger.doubanmovie.R;

/**
 * View holder for movie in grid view.
 */
public class MovieViewHolder extends RecyclerView.ViewHolder {
    public ImageView coverImage;
    public TextView titleText;
    public TextView scoreText;
    public RatingBar ratingBar;

    public MovieViewHolder(View itemView) {
        super(itemView);
        coverImage = (ImageView) itemView.findViewById(R.id.mv_cover);
        titleText = (TextView) itemView.findViewById(R.id.mv_title);
        scoreText = (TextView) itemView.findViewById(R.id.mv_score);
        ratingBar = (RatingBar) itemView.findViewById(R.id.mv_rating);
    }
}
