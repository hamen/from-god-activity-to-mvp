package com.ivanmorgillo.fromgodactivitytomvp.mvp.di;

import com.ivanmorgillo.fromgodactivitytomvp.mvp.ui.MvpActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AndroidModule.class, Providers.class})
public interface AppComponent {

    void inject(MvpActivity mvpActivity);
}
