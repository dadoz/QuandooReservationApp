package com.application.restaurantreservation.modules;

import android.support.v7.app.AppCompatActivity;

import com.application.restaurantreservation.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,})
public interface CustomerConnector {
    void inject(MainActivity mainActivity);
}