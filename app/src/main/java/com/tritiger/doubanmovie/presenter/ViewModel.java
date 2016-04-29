package com.tritiger.doubanmovie.presenter;

import android.databinding.BaseObservable;

/**
 * Interface representing a ViewModel in a model view presenter (MVP) pattern.
 */
public abstract class ViewModel extends BaseObservable {
    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    abstract void resume();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    abstract void pause();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    abstract void destroy();
}
