package com.application.restaurantreservation.db;

import android.content.res.Resources;

import com.application.restaurantreservation.model.Customer;
import com.application.restaurantreservation.model.Table;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


@Singleton
public class DataManager {

    private DbHelper mDbHelper;

    @Inject
    public DataManager(DbHelper dbHelper) {
        mDbHelper = dbHelper;
    }

    public Observable<List<Customer>> getCustomerList() throws Resources.NotFoundException, NullPointerException {
        return mDbHelper.getCustomers();
    }

    public void createAllCustomer(List<?> items) throws Exception {
        for (Object item : items) {
            mDbHelper.insertCustomer((Customer) item);
        }
    }

    public Observable<List<Boolean>> getTableList() throws Resources.NotFoundException, NullPointerException {
        return mDbHelper.getTables();
    }

    public void createAllTables(List<Boolean> items) throws Exception {
        mDbHelper.insertTables(items);
    }


    public void updateTable(List<Boolean> items) {
        mDbHelper.updateTable(items);
    }

    public void clearAllReservations() {
        mDbHelper.truncateTableTbl();
    }

    public void clearAllCustomers() {
        mDbHelper.truncateCustomerTbl();
    }
    public void close() {
        mDbHelper.close();
    }
}