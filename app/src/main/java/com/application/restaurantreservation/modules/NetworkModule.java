package com.application.restaurantreservation.modules;

import android.content.Context;

import com.application.restaurantreservation.BuildConfig;
import com.application.restaurantreservation.db.DataManager;
import com.application.restaurantreservation.services.NetworkService;
import com.application.restaurantreservation.services.ReservationService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private static String baseUrlEndpoint = BuildConfig.SERVICE_ENDPOINT;
    private final Context context;

    public NetworkModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Retrofit provideCall() {
        return new Retrofit.Builder()
                .baseUrl(baseUrlEndpoint)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public NetworkService providesNetworkService(
            Retrofit retrofit) {
        return retrofit.create(NetworkService.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public ReservationService providesService(
            NetworkService networkService, DataManager dataManager) {
        return new ReservationService(networkService, dataManager);
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return context;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return "restaurant-reservations.db";
    }

    @Provides
    @DatabaseInfo
    Integer provideDatabaseVersion() {
        return 1;
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DatabaseInfo {
    }
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ApplicationContext {
    }
}
