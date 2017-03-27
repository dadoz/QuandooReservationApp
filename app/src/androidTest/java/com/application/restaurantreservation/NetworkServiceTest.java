package com.application.restaurantreservation;

import com.application.restaurantreservation.db.DataManager;
import com.application.restaurantreservation.model.Customer;
import com.application.restaurantreservation.services.NetworkService;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NetworkServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    NetworkService networkService;
    @Mock
    DataManager dataManager;

    @Test
    public void testGetCustomerList() {
        when(networkService.getCustomerList()).thenReturn(Observable.just(new ArrayList<Customer>()));
        when(dataManager.getCustomerList()).thenReturn(Observable.just(new ArrayList<Customer>()));
    }

    @Test
    public void testGetTablesList() {
        when(networkService.getTableList()).thenReturn(Observable.just(new ArrayList<Boolean>()));
        when(dataManager.getTableList()).thenReturn(Observable.just(new ArrayList<Boolean>()));
    }
}
