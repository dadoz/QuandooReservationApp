package com.application.restaurantreservation.components;


import android.content.Context;

import com.application.restaurantreservation.MainActivity;
import com.application.restaurantreservation.db.DataManager;
import com.application.restaurantreservation.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface CustomerComponent {
    void inject(MainActivity mainActivity);

    @NetworkModule.ApplicationContext
    Context getContext();

    DataManager getDataManager();
}