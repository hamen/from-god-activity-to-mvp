package com.ivanmorgillo.fromgodactivitytomvp;

import com.ivanmorgillo.fromgodactivitytomvp.mvp.di.AndroidModule;
import com.ivanmorgillo.fromgodactivitytomvp.mvp.di.AppComponent;
import com.ivanmorgillo.fromgodactivitytomvp.mvp.di.DaggerAppComponent;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;

public class MainApplication extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
            .androidModule(new AndroidModule(this))
            .build();

        setupUil();

        System.setProperty("org.joda.time.DateTimeZone.Provider", "com.ivanmorgillo.fromgodactivitytomvp.helpers.FastDateTimeZoneProvider");
    }

    private void setupUil() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
            .defaultDisplayImageOptions(defaultOptions)
            .build();

        ImageLoader.getInstance().init(config);
    }

    public static AppComponent component() {
        return component;
    }
}
