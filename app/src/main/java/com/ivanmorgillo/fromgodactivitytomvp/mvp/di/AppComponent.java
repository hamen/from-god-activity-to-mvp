package com.ivanmorgillo.fromgodactivitytomvp.mvp.di;

import com.ivanmorgillo.fromgodactivitytomvp.god.GodActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AndroidModule.class)
public interface AppComponent {

    void inject(GodActivity godActivity);
}
