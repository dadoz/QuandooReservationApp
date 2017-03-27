package com.application.restaurantreservation;


import com.application.restaurantreservation.services.NetworkService;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;


public class ReservationServiceTest {

        @Inject
        NetworkService service;

//        @Inject MyPrinter myPrinter;
//
//        @Inject MainService mainService;

        @Before
        public void setUp() {
//            CustomerComponent component = DaggerCustomerComponent.builder()
//                    .networkModule(new NetworkModule(getContext())).build();
//            component.inject(this);
        }

        @Test
        public void testDoSomething() {
//            when(service.getCustomerList()).thenReturn("abc");
//
//            mainService.doSomething();
//
//            verify(myPrinter).print("ABC");
        }
    }