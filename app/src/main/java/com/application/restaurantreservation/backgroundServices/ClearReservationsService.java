package com.application.restaurantreservation.backgroundServices;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.application.restaurantreservation.components.DaggerServiceComponent;
import com.application.restaurantreservation.modules.ServiceModule;
import com.application.restaurantreservation.services.ClearReservationHelperService;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

public class ClearReservationsService extends Service {
    private Handler hanlder = new Handler();
    @Inject
    ClearReservationHelperService helperService;
    private Timer timer;

    @Override
    public void onCreate() {
        DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(getApplicationContext()))
                .build()
                .inject(this);

        initTimer();
    }

    /**
     * init timer fx
     */
    private void initTimer() {
        //init timer
        if (timer == null) {
            timer = new Timer();
            return;
        }

        //clear timer
        timer.cancel();
    }

    //service not bindable - not interact with user
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not Bindable Service");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //cancel timer
        timer.cancel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //schedule clear reservation
        timer.scheduleAtFixedRate(new CustomTimerTask(), 0, 600000);

        //not recreate after service has been destroyed
        return START_NOT_STICKY;
    }

    /**
     * clear reservation task
     */
    private class CustomTimerTask extends TimerTask {
        @Override
        public void run() {
            hanlder.post(() -> helperService.clearAllReservations());
        }
    }
}
