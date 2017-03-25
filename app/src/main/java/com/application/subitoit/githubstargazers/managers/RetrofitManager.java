package com.application.subitoit.githubstargazers.managers;


import com.application.subitoit.githubstargazers.BuildConfig;
import com.application.subitoit.githubstargazers.services.StargazerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import retrofit2.Retrofit;
 import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static RetrofitManager instance;
    private StargazerService service;
    private final String TAG = "RetrofitManager";
    public static String baseUrlEndpoint = BuildConfig.GITHUB_ENDPOINT;

    //dadoz/SelectCardViewPrototype
    // API URL https://api.github.com/repos/{owner}/{repo}/stargazers
    //Object -> avatar and username

    private RetrofitManager() {
        initRetrofit();
    }

    public static RetrofitManager getInstance() {
        return instance == null ? instance = new RetrofitManager() : instance;
    }

    public StargazerService getService() {
        return service;
    }

    private void initRetrofit() {
        try {
            service = new Retrofit.Builder()
                    .baseUrl(baseUrlEndpoint)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build()
                    .create(StargazerService.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Gson getGson() {
        return new GsonBuilder().create();
    }
}
