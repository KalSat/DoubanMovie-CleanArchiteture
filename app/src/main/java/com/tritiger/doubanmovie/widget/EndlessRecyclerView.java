package com.tritiger.doubanmovie.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.tritiger.doubanmovie.ui.adapter.EndlessAdapter;

/**
 * An recyclerView to show endless data.
 */
public class EndlessRecyclerView extends RecyclerView {

    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading = false;

    public EndlessRecyclerView(Context context) {
        super(context);
        init();
    }

    public EndlessRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EndlessRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        addOnScrollListener(onScrollListener);
    }

    @Override
    public void setAdapter(Adapter adapter) throws IllegalArgumentException {
        if (!(adapter instanceof EndlessAdapter)) {
            throw new IllegalArgumentException("can only use EndlessAdapter");
        }
        super.setAdapter(adapter);
    }

    public void loadingComplete() {
        isLoading = false;
    }

    public void loadingFail() {
        isLoading = false;
        final EndlessAdapter adapter = (EndlessAdapter) getAdapter();
        adapter.loadingFail();
    }

    public void loadMore(EndlessAdapter adapter) {
        isLoading = true;
        onLoadMoreListener.onLoadMore(adapter.getChildItemCount());
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    private final OnScrollListener onScrollListener = new OnScrollListener() {
        int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView,
                                         int newState) {
            switch (newState) {
                case SCROLL_STATE_IDLE:
                    if (!(recyclerView.getAdapter() instanceof EndlessAdapter)) {
                        break;
                    }

                    final EndlessAdapter adapter = (EndlessAdapter) recyclerView.getAdapter();
                    if (lastVisibleItem + 2 > adapter.getItemCount()
                            && adapter.getChildItemCount() > 0
                            && adapter.getChildItemCount() < adapter.maxDataCount) {
                        if (onLoadMoreListener != null && !isLoading) {
                            loadMore(adapter);
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            LinearLayoutManager layoutManager =
                    (LinearLayoutManager) recyclerView.getLayoutManager();
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }
    };

    public interface OnLoadMoreListener {
        void onLoadMore(int currentSize);
    }
}
