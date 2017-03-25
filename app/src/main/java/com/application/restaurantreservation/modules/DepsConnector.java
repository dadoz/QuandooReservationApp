package com.application.restaurantreservation.modules;

import com.application.restaurantreservation.MainActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,})
public interface DepsConnector {
    void inject(MainActivity mainActivity);
}