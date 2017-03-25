package com.application.restaurantreservation.modules;

import com.application.restaurantreservation.TableGridActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,})
public interface TableConnector {
    void inject(TableGridActivity activity);
}