package com.tritiger.doubanmovie.data.repository.datasource;

import android.content.Context;

import com.tritiger.doubanmovie.data.net.NetworkManager;

/**
 * Factory that creates different implementations of {@link MovieDataStore}.
 */
public class MovieDataStoreFactory {

    private final Context context;

    public MovieDataStoreFactory(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
    }

    /**
     * Create {@link MovieDataStore} to retrieve data from the Cloud.
     */
    public MovieDataStore createCloudDataStore() {
        NetworkManager networkManager = new NetworkManager(this.context);

        return new CloudMovieDataStore(networkManager);
    }
}
