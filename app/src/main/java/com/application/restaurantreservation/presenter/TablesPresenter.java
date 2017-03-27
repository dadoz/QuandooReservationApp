package com.application.restaurantreservation.presenter;

import android.util.Log;

import com.application.restaurantreservation.services.ReservationService;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.Disposable;


public class TablesPresenter implements BasePresenter {
    private static final String TAG = "TablesPresenter";
    private static WeakReference<ReservationService> serviceRef;
    private final WeakReference<BaseView> tablesViewRef;
    private Disposable disposable;

    public TablesPresenter(WeakReference<ReservationService> service, WeakReference<BaseView> view) {
        serviceRef = service;
        tablesViewRef = view;
    }

    /**
     * get customer list
     */
    public void getTableList() {
        if (serviceRef.get() != null)
            disposable = serviceRef.get().getTableList(new WeakReference<>(this));
    }

    @Override
    public void onFinishedRetrieveItems(List<?> items) {
        if (tablesViewRef.get() != null)
            tablesViewRef.get().onDataRetrieved(items);
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }

    @Override
    public void onError(String error) {
        if (tablesViewRef.get() != null)
            tablesViewRef.get().onFailure(error);
    }

}
