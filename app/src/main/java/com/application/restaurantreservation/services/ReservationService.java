package com.application.restaurantreservation.services;

import android.util.Log;

import com.application.restaurantreservation.db.DataManager;
import com.application.restaurantreservation.model.Customer;
import com.application.restaurantreservation.presenter.BasePresenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ReservationService {
    /**
     * retrieve data from server
     */
    private final NetworkService networkService;
    private final DataManager dbDataManager;

    /**
     *
     * @param service
     */
    public ReservationService(NetworkService service, DataManager dbDataManager) {
        this.networkService = service;
        this.dbDataManager = dbDataManager;
    }

    public Disposable getCustomerList(WeakReference<BasePresenter> listener) {
        return Observable
                .concat(dbDataManager.getCustomerList(),
                        networkService.getCustomerList().doOnNext(dbDataManager::createAllCustomer))
                .filter(list -> list != null && list.size() != 0)
                .first(new ArrayList<>())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Customer>>() {
                    @Override
                    public void onNext(List<Customer> value) {
                        if (listener.get() != null)
                            listener.get().onFinishedRetrieveItems(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (listener.get() != null)
                            listener.get().onError(e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    public Disposable getTableList(WeakReference<BasePresenter> listener) {
        return Observable
                .concat(dbDataManager.getTableList(),
                        networkService.getTableList().doOnNext(dbDataManager::createAllTables))
                .filter(list -> list != null && list.size() != 0)
                .first(new ArrayList<>())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Boolean>>() {
                    @Override
                    public void onNext(List<Boolean> value) {
                        if (listener.get() != null)
                            listener.get().onFinishedRetrieveItems(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (listener.get() != null)
                            listener.get().onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}