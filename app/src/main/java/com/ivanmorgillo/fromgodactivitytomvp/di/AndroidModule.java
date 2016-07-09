package com.ivanmorgillo.fromgodactivitytomvp.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ivanmorgillo.fromgodactivitytomvp.MainApplication;
import com.ivanmorgillo.fromgodactivitytomvp.R;
import com.ivanmorgillo.fromgodactivitytomvp.helpers.DateTimeSerializer;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import android.content.Context;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {

    private final MainApplication application;

    private static final String LOGGING_TAG = "StackOverflowTest";

    public AndroidModule(MainApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    @Named("cacheDir")
    File provideCacheDir(Context context) {
        return context.getCacheDir();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        DateTimeSerializer dateSerializer = new DateTimeSerializer(ISODateTimeFormat.dateTimeParser().withZoneUTC());
        return new GsonBuilder().registerTypeAdapter(DateTime.class, dateSerializer).create();
    }

    @Provides
    @Singleton
    @Named("api_baseurl")
    String provideApiBaseurl(Context context) {
        return context.getResources().getString(R.string.server);
    }
}
