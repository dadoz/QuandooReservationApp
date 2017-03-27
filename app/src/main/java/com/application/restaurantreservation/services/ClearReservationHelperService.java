package com.application.restaurantreservation.services;

import com.application.restaurantreservation.db.DataManager;

public class ClearReservationHelperService {
    private final DataManager dataManager;

    public ClearReservationHelperService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void clearAllReservations() {
        dataManager.clearAllReservations();
    }
}
