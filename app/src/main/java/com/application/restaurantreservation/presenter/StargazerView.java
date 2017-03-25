package com.application.restaurantreservation.presenter;

import java.util.List;

public interface StargazerView {
    void bindData(List<?> items, int i);
    void onRetrieveDataError(String error);
}
