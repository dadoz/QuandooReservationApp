package com.application.restaurantreservation.components;


import android.content.Context;

import com.application.restaurantreservation.backgroundServices.ClearReservationsService;
import com.application.restaurantreservation.modules.NetworkModule;
import com.application.restaurantreservation.modules.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ServiceModule.class})
public interface ServiceComponent {
    void inject(ClearReservationsService service);

    @NetworkModule.ApplicationContext
    Context getContext();
}