package com.application.restaurantreservation.services;


import com.application.restaurantreservation.model.Customer;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NetworkService {
    @GET("customer-list.json")
    Observable<ArrayList<Customer>> getCustomerList();

    @GET("table-map.json")
    Observable<Boolean[]> getTableMap();
}
