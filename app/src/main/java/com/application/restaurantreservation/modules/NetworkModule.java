package com.application.restaurantreservation.modules;

import com.application.restaurantreservation.BuildConfig;
import com.application.restaurantreservation.services.NetworkService;
import com.application.restaurantreservation.services.ReservationService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private static String baseUrlEndpoint = BuildConfig.SERVICE_ENDPOINT;

    @Provides
    @Singleton
    Retrofit provideCall() {
        return new Retrofit.Builder()
                .baseUrl(baseUrlEndpoint)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();
        //.create(NetworkService.class);
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
            NetworkService networkService) {
        return new ReservationService(networkService);
    }

    private Gson getGson() {
        return new GsonBuilder().create();
    }
}
