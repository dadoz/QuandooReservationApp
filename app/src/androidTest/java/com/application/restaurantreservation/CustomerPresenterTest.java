package com.application.restaurantreservation;

import com.application.restaurantreservation.model.Customer;
import com.application.restaurantreservation.presenter.BasePresenter;
import com.application.restaurantreservation.presenter.BaseView;
import com.application.restaurantreservation.presenter.CustomerPresenter;
import com.application.restaurantreservation.services.ReservationService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class CustomerPresenterTest {
    @Mock
    ReservationService service;
    @Mock
    BaseView view;

    private CustomerPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(service.getCustomerList(new WeakReference<>(presenter)))
                    .thenReturn(Observable.just(new ArrayList<Customer>()).subscribe());
        presenter = new CustomerPresenter(new WeakReference<>(service),
                new WeakReference<>(view));
    }

    @Test
    public void testDisplayCalled() {
        verify(view).onDataRetrieved(any());
        verify(view).onFailure(null);
    }
}
