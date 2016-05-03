package com.tritiger.doubanmovie.ui.adapter;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Adapter for Data Binding
 */
@SuppressWarnings("unused")
@BindingMethods({
        @BindingMethod(type = SwipeRefreshLayout.class, attribute = "app:onRefresh", method = "setOnRefreshListener")
})
public class DataBindingAdapter {

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.with(view.getContext()).load(url).into(view);
    }

}
