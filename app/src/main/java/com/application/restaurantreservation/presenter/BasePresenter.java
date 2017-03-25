package com.application.restaurantreservation.presenter;

import java.util.List;

public interface BasePresenter {
    void onFinishedRetrieveItems(List<?> items);
    void unsubscribe();
    void onError(String error);
}
