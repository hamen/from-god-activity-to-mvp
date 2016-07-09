package com.ivanmorgillo.fromgodactivitytomvp.di;

import com.ivanmorgillo.fromgodactivitytomvp.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AndroidModule.class)
public interface AppComponent {

    void inject(MainActivity mainActivity);
}
