package com.tritiger.doubanmovie.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tritiger.doubanmovie.R;
import com.tritiger.doubanmovie.widget.EndlessRecyclerView;

public abstract class EndlessAdapter<VH extends RecyclerView.ViewHolder>
        extends FooterAdapter<VH, EndlessAdapter.LoadingViewHolder> {

    private final EndlessRecyclerView recyclerView;

    private LoadingViewHolder loadingViewHolder;
    public int maxDataCount = 0;

    public EndlessAdapter(EndlessRecyclerView recyclerView) {
        super(true);
        this.recyclerView = recyclerView;
    }

    @Override
    protected LoadingViewHolder onCreateFooterViewHolder(ViewGroup parent) {
        final Context context = parent.getContext();
        if (loadingViewHolder == null) {
            loadingViewHolder = new LoadingViewHolder(context, LayoutInflater.from(context)
                    .inflate(R.layout.item_loading, parent, false));
        }
        return loadingViewHolder;
    }

    @Override
    protected void onBindFooterViewHolder(LoadingViewHolder holder, int position) {
        holder.itemView.setOnClickListener(listener);
        if (isLoadCompleted()) {
            holder.setViewState(LoadingViewHolder.STATE_COMPLETED);
        } else {
            holder.setViewState(LoadingViewHolder.STATE_LOADING);
        }
    }

    public boolean isLoadCompleted() {
        return getChildItemCount() >= maxDataCount;
    }

    public void loadingFail() {
        loadingViewHolder.setViewState(LoadingViewHolder.STATE_FAIL);
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loadingViewHolder.setViewState(LoadingViewHolder.STATE_LOADING);
            recyclerView.loadMore(EndlessAdapter.this);
        }
    };

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public static final int STATE_LOADING = 0;
        public static final int STATE_COMPLETED = 1;
        public static final int STATE_FAIL = 2;

        private final Context context;
        private final ProgressBar progressBar;
        private final TextView tipText;

        public LoadingViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            progressBar = (ProgressBar) itemView.findViewById(R.id.ld_progress_bar);
            tipText = (TextView) itemView.findViewById(R.id.ld_tips);
        }

        public void setViewState(int state) {
            switch (state) {
                case STATE_LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    tipText.setVisibility(View.GONE);
                    itemView.setClickable(false);
                    break;
                case STATE_COMPLETED:
                    progressBar.setVisibility(View.GONE);
                    tipText.setVisibility(View.GONE);
                    itemView.setClickable(false);
                    break;
                case STATE_FAIL:
                    progressBar.setVisibility(View.GONE);
                    tipText.setText(context.getString(R.string.load_fail_tip));
                    itemView.setClickable(true);
                    break;
                default:
                    break;
            }
        }
    }
}
