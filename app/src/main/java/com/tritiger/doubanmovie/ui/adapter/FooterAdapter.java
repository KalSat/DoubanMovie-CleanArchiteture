package com.tritiger.doubanmovie.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public abstract class FooterAdapter
        <IV extends RecyclerView.ViewHolder, FV extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int FOOTER = 1;

    private final boolean mHasFooter;


    protected abstract IV onCreateItemViewHolder(ViewGroup parent);

    protected abstract void onBindItemViewHolder(IV holder, int position);

    protected abstract long getChildItemId(int position);

    public abstract int getChildItemCount();

    protected abstract FV onCreateFooterViewHolder(ViewGroup parent);

    protected abstract void onBindFooterViewHolder(FV holder, int position);


    public FooterAdapter(boolean hasHeader) {
        mHasFooter = hasHeader;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM:
                return onCreateItemViewHolder(parent);
            case FOOTER:
                return onCreateFooterViewHolder(parent);
            default:
                return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM:
                onBindItemViewHolder((IV) holder, position);
                break;
            case FOOTER:
                onBindFooterViewHolder((FV) holder, position);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHasFooter && position == getItemCount() - 1) {
            return FOOTER;
        } else {
            return ITEM;
        }
    }

    @Override
    public long getItemId(int position) {
        if (mHasFooter && position == getItemCount() - 1) {
            return -1;
        } else {
            return getChildItemId(position);
        }
    }

    @Override
    public int getItemCount() {
        return mHasFooter ? getChildItemCount() + 1 : getChildItemCount();
    }
}
