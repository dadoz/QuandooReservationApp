package com.application.restaurantreservation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.application.restaurantreservation.modules.DaggerDepsConnector;
import com.application.restaurantreservation.modules.DepsConnector;
import com.application.restaurantreservation.modules.NetworkModule;

public class BaseActivity extends AppCompatActivity {
        DepsConnector depsConnector;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//            File cacheFile = new File(getCacheDir(), "responses");
            depsConnector = DaggerDepsConnector.builder().networkModule(new NetworkModule()).build();

        }

        public DepsConnector getDeps() {
            return depsConnector;
        }
    }