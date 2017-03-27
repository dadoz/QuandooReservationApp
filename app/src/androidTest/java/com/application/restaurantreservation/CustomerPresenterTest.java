package com.application.restaurantreservation;

import com.application.restaurantreservation.model.Customer;
import com.application.restaurantreservation.presenter.BasePresenter;
import com.application.restaurantreservation.presenter.BaseView;
import com.application.restaurantreservation.presenter.CustomerPresenter;
import com.application.restaurantreservation.services.ReservationService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerPresenterTest {
    @Mock
    ReservationService service;

    @Mock
    BaseView view;

    private WeakReference<BasePresenter> presenterRef;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        //handle service to get customer list
        when(service.getCustomerList(presenterRef))
                    .thenReturn(Observable.just(getMockData()).subscribe());

        //create presenter
        CustomerPresenter presenter = new CustomerPresenter(new WeakReference<>(service),
                new WeakReference<>(view));
        presenterRef = new WeakReference<>(presenter);
    }

    @Test
    public void testDisplayCalled() {
        //check displayed data on view
        presenterRef.get().onFinishedRetrieveItems(getMockData());
        verify(view).onDataRetrieved(any());
    }

    /**
     * get mock data
     * @return
     */
    public ArrayList<Customer> getMockData() {
        ArrayList<Customer> list = new ArrayList<>();
        list.add(new Customer(0, "Test", "sample"));
        return list;
    }
}
