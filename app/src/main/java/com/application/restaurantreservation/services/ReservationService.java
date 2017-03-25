package com.application.restaurantreservation.services;

import com.application.restaurantreservation.model.Customer;
import com.application.restaurantreservation.presenter.BasePresenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ReservationService {
    private static final String TAG = "ResService";
    /**
     * retrieve data from server
     */
    private final NetworkService networkService;
    private DisposableObserver<ArrayList<Customer>> disposable;

    /**
     *
     * @param service
     */
    public ReservationService(NetworkService service) {
        this.networkService = service;
    }

    public void unbindSubscription() {
        if (disposable != null)
            disposable.dispose();
    }

    public Disposable retrieveItems(WeakReference<BasePresenter> listener) {
        disposable = networkService
                .getCustomerList()
                .filter(list -> list != null && list.size() != 0)
                .switchIfEmpty(Observable.just(new ArrayList<>()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<Customer>>() {
                    @Override
                    public void onNext(ArrayList<Customer> value) {
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
        return disposable;
    }
}