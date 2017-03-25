package com.application.restaurantreservation.presenter;

import android.app.Activity;
import android.util.Log;
import android.util.SparseArray;

import com.application.restaurantreservation.managers.RetrofitManager;
import com.application.restaurantreservation.model.Customer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class StargazerPresenter implements BasePresenter {
    private static final String TAG = "StargazerPresenter";
    private static WeakReference<StargazerView> wifiDeviceNetworkView;
    private Interactor interactor;

    public void init(WeakReference<Activity> activity, WeakReference<StargazerView> view, SparseArray<String> params) {
        wifiDeviceNetworkView = view;
        interactor = new Interactor(activity);
        interactor.retrieveItems(new WeakReference<>(this), params);
    }

    @Override
    public void onFinishedRetrieveItems(List<?> items) {
        if (wifiDeviceNetworkView.get() != null)
            wifiDeviceNetworkView.get().bindData(items, -1);
    }

    @Override
    public void unsubscribe() {
        if (interactor != null)
            interactor.unbindSubscription();
    }

    @Override
    public void onError(String error) {
        if (wifiDeviceNetworkView.get() != null)
            wifiDeviceNetworkView.get().onRetrieveDataError(error);
    }

    /**
     * retrieve data from server
     */
    public static class Interactor implements BaseInteractor {
        private final WeakReference<Activity> activity;
        private DisposableObserver<ArrayList<Customer>> disposable;

        /**
         *
         * @param activity
         */
        public Interactor(WeakReference<Activity> activity) {
            this.activity = activity;
        }

        @Override
        public void unbindSubscription() {
            if (disposable != null)
                disposable.dispose();
        }

        @Override
        public void retrieveItems(WeakReference<BasePresenter> listener, SparseArray<String> params) {
            Log.e(TAG, params.toString());
            disposable = RetrofitManager.getInstance()
                    .getService()
                    .getStargazers(params.get(0), params.get(1))
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
        }
    }
}
