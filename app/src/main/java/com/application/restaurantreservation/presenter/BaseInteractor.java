package com.application.restaurantreservation.presenter;

import android.util.SparseArray;

import java.lang.ref.WeakReference;

public interface BaseInteractor {
    void unbindSubscription();

    void retrieveItems(WeakReference<BasePresenter> listener, SparseArray<String> params);
}
