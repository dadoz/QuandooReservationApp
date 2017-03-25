package com.application.restaurantreservation.services;


import com.application.restaurantreservation.model.Customer;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StargazerService {
    @GET("repos/{owner}/{repo}/stargazers")
    Observable<ArrayList<Customer>> getStargazers(@Path("owner") String owner, @Path("repo") String repo);

}
