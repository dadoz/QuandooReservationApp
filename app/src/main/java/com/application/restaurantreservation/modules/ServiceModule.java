package com.application.restaurantreservation.modules;

import android.content.Context;

import com.application.restaurantreservation.db.DataManager;
import com.application.restaurantreservation.services.ClearReservationHelperService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {
    private final Context context;

    public ServiceModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public ClearReservationHelperService providesService(DataManager dataManager) {
        return new ClearReservationHelperService(dataManager);
    }

    @Provides
    @NetworkModule.ApplicationContext
    Context provideContext() {
        return context;
    }

    @Provides
    @NetworkModule.DatabaseInfo
    String provideDatabaseName() {
        return "restaurant-reservations.db";
    }

    @Provides
    @NetworkModule.DatabaseInfo
    Integer provideDatabaseVersion() {
        return 1;
    }

}
