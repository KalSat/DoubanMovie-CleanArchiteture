package com.tritiger.doubanmovie.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.tritiger.doubanmovie.R;
import com.tritiger.doubanmovie.domain.Movie;
import com.tritiger.doubanmovie.widget.EndlessRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for an movie grid view.
 */
public class MovieGridAdapter extends EndlessAdapter<MovieViewHolder> {

    public final Context context;
    public final List<Movie> movieList;

    public OnItemClickListener listener;

    public MovieGridAdapter(Context context, EndlessRecyclerView recyclerView) {
        super(recyclerView);
        this.context = context.getApplicationContext();
        movieList = new ArrayList<>();
    }

    @Override
    protected MovieViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(MovieViewHolder holder, int position) {
        final Movie movie = movieList.get(position);
        Picasso.with(context).load(getImageUrl(movie.cover)).into(holder.coverImage);
        holder.titleText.setText(movie.title);
        holder.titleText.setSelected(true);
        if (movie.rating.average <= 0) {
            holder.scoreText.setText("暂无评价");
            holder.ratingBar.setVisibility(View.GONE);
        } else {
            holder.scoreText.setText(String.valueOf(movie.rating.average));
            holder.ratingBar.setRating(movie.rating.average / 2);
            holder.ratingBar.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(movie);
                }
            }
        });
    }

    @Override
    public int getChildItemCount() {
        return movieList.size();
    }

    @Override
    protected long getChildItemId(int position) {
        return position;
    }

    //图片质量
    private static int IMAGE_QUALITY = 0;
    //头像质量
    private static int LOGO_QUALITY = 0;
    //自动
    public static final int QUALITY_AUTO = 0;
    //高
    public static final int QUALITY_HIGH = 1;
    //中
    public static final int QUALITY_MID = 2;
    //低
    public static final int QUALITY_LOW = 3;

    /**
     * 根据当前设置自动获取图片质量
     */
    public String getImageUrl(Movie.Image bean) {
        String url = "";
        switch (IMAGE_QUALITY) {
            case QUALITY_AUTO:
                url = bean.large;
                break;
            case QUALITY_HIGH:
                url = bean.large;
                break;
            case QUALITY_MID:
                url = bean.medium;
                break;
            case QUALITY_LOW:
                url = bean.small;
                break;
        }
        return url;
    }

    public void add(List<Movie> movieList) {
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }

    public void set(List<Movie> movieList) {
        this.movieList.clear();
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }

    public void clear() {
        movieList.clear();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }
}
