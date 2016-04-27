package com.tritiger.doubanmovie.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public abstract class AbstractFragment extends Fragment {

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected boolean isPrepared = false;

    protected abstract View onChildCreateView(LayoutInflater inflater, ViewGroup container,
                                              Bundle savedInstanceState);

    protected abstract void childLoadData();


    public AbstractFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        isPrepared = true;
        final View view = onChildCreateView(inflater, container, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(true);
                        }
                        loadData();
                    }
                });
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadData();
        }
    }

    public void loadData() {
        if (!isPrepared || !getUserVisibleHint()) {
            return;
        }
        childLoadData();
    }

}
