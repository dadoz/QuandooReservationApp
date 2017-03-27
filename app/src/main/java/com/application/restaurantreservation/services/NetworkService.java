package com.application.restaurantreservation.services;


import com.application.restaurantreservation.model.Customer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NetworkService {
    @GET("customer-list.json")
    Observable<List<Customer>> getCustomerList();

    @GET("table-map.json")
    Observable<List<Boolean>> getTableList();
}
