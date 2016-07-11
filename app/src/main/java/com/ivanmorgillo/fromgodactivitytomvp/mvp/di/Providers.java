package com.ivanmorgillo.fromgodactivitytomvp.mvp.di;

import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class Providers {

    private static final long CONNECTION_TIMEOUT = 30;

    private static final long HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 2 * 1000;

    @Named("rx-retrofit")
    @Provides
    @Singleton
    Retrofit providesRetrofit(Gson gson, @Named("api_baseurl") String baseUrl, OkHttpClient client) {
        return new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(client)
            .build();
    }

    @SuppressWarnings("Duplicates")
    @Provides
    @Singleton
    OkHttpClient getOkHttpClient(@Named("cacheDir") File baseDir) {
        OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        okClientBuilder.addInterceptor(httpLoggingInterceptor);

        if (baseDir != null) {
            final File cacheDir = new File(baseDir, "HttpResponseCache");
            okClientBuilder.cache(new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE));
        }

        okClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        okClientBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        okClientBuilder.writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        return okClientBuilder.build();
    }
}
