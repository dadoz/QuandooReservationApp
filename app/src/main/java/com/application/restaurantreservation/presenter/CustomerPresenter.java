package com.application.restaurantreservation.presenter;

import android.app.Activity;
import android.util.SparseArray;

import com.application.restaurantreservation.services.ReservationService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;


public class CustomerPresenter implements BasePresenter {
    private static final String TAG = "CustomerPresenter";
    private static WeakReference<ReservationService> serviceRef;
    private final WeakReference<CustomerView> customerViewRef;
    private Disposable disposable;

    public CustomerPresenter(WeakReference<ReservationService> service, WeakReference<CustomerView> view) {
        serviceRef = service;
        customerViewRef = view;
    }

    /**
     * get customer list
     */
    public void getCustomerList() {
        if (serviceRef.get() != null)
            disposable = serviceRef.get().getCustomerList(new WeakReference<>(this));
    }

    @Override
    public void onFinishedRetrieveItems(List<?> items) {
        if (customerViewRef.get() != null)
            customerViewRef.get().onDataRetrieved(items);
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

    @Override
    public void onError(String error) {
        if (customerViewRef.get() != null)
            customerViewRef.get().onFailure(error);
    }

}
