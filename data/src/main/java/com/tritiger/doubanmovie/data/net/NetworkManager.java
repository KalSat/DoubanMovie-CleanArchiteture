package com.tritiger.doubanmovie.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.tritiger.doubanmovie.data.entity.MovieListEntity;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Network Manager class used to retrieve data from the cloud.
 * Powered by Retrofit.
 */
public class NetworkManager {
    private static final String TAG = "NetworkManager";

    private final DoubanApi doubanApi;

    public NetworkManager(Context context) {
        final OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(getCacheControlInterceptor(context))
                .addNetworkInterceptor(getCacheControlInterceptor(context))
//                .addInterceptor(getLogInterceptor())
                .cache(new Cache(new File(context.getExternalCacheDir(),
                        "responses"), 10 * 1024 * 1024))
                .build();

        doubanApi = new Retrofit.Builder()
                .baseUrl(DoubanApi.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(DoubanApi.class);
    }

    public Observable<MovieListEntity> getTopMovies(int start, int count) {
        return doubanApi.getTopMovies(start, count, DoubanApi.API_KEY);
    }

    public Observable<MovieListEntity> getTopMoviesFromCache(int start, int count) {
        return doubanApi.getTopMoviesFromCache(start, count, DoubanApi.API_KEY);
    }

    private Interceptor getCacheControlInterceptor(final Context context) {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                final boolean networkAvailable = isNetworkAvailable(context);
                if (!networkAvailable) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                okhttp3.Response originalResponse = chain.proceed(request);
                if (networkAvailable) {
                    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                    String cacheControl = request.cacheControl().toString();
                    return originalResponse.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
            }
        };
    }

    private Interceptor getLogInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                long t1 = System.nanoTime();
                Log.i(TAG, String.format("Sending request %s on %s%n%s",
                        request.url(), chain.connection(), request.headers()));
                okhttp3.Response response = chain.proceed(request);
                long t2 = System.nanoTime();
                Log.i(TAG, String.format("Received response for %s in %.1fms%n%s",
                        response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                return response;
            }
        };
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        return current != null && current.isAvailable();
    }
}
