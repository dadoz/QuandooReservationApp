package com.application.restaurantreservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.application.restaurantreservation.adapter.CustomerListAdapter;
import com.application.restaurantreservation.backgroundServices.ClearReservationsService;
import com.application.restaurantreservation.components.CustomerComponent;
import com.application.restaurantreservation.components.DaggerCustomerComponent;
import com.application.restaurantreservation.db.DataManager;
import com.application.restaurantreservation.modules.NetworkModule;
import com.application.restaurantreservation.presenter.CustomerPresenter;
import com.application.restaurantreservation.presenter.BaseView;
import com.application.restaurantreservation.services.ReservationService;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity implements BaseView, CustomerListAdapter.OnItemClickListener {
    @Inject
    public ReservationService service;

    private View  customerProgressbarView;
    private RecyclerView customerRecyclerView;
    private View emptyCustomerView;
    String TAG = "TAG";
    private CustomerComponent customerComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        customerComponent = DaggerCustomerComponent.builder()
                .networkModule(new NetworkModule(getApplicationContext()))
                .build();

        bindView();
        onInit();
        initService();
    }

    /**
     * init service to clear reservation
     *
     * - start in NOT_STICKY mode (and not bindable)
     */
    private void initService() {
        // use this to start and trigger a service
        Intent intentService = new Intent(getApplicationContext(),
                ClearReservationsService.class);
        // potentially add data to the intent
        getApplicationContext().startService(intentService);
    }

    /**
     * bind view layout items
     */
    private void bindView() {
        customerRecyclerView = ((RecyclerView) findViewById(R.id.customerRecyclerViewId));
        customerProgressbarView = findViewById(R.id.customerProgressbarId);
        emptyCustomerView = findViewById(R.id.emptyCustomerViewId);
    }

    /**
     * init view to handle button in custom view interaction
     */
    private void onInit() {
        //inject activity on depsConnector module
        customerComponent.inject(this);

        //init presenter
        CustomerPresenter presenter = new CustomerPresenter(new WeakReference<>(service),
                new WeakReference<>(this));
        presenter.getCustomerList();
    }

    @Override
    public void onDataRetrieved(List<?> items) {
        customerProgressbarView.setVisibility(View.GONE);
        customerRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        customerRecyclerView.setAdapter(new CustomerListAdapter(items, new WeakReference<>(this)));
    }

    @Override
    public void onFailure(String error) {
        customerProgressbarView.setVisibility(View.GONE);
        emptyCustomerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(View view, int position) {
        startActivity(new Intent(this, TableGridActivity.class));
    }
}
