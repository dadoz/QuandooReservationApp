package com.application.restaurantreservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.application.restaurantreservation.adapter.CustomerListAdapter;
import com.application.restaurantreservation.modules.CustomerConnector;
import com.application.restaurantreservation.modules.DaggerCustomerConnector;
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
    private CustomerConnector customerConnector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        customerConnector = DaggerCustomerConnector.builder().networkModule(new NetworkModule()).build();

        bindView();
        onInit();
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
        customerConnector.inject(this);

        //init presenter
        CustomerPresenter presenter = new CustomerPresenter(new WeakReference<>(service),
                new WeakReference<>(this));
        presenter.getCustomerList();
    }

    @Override
    public void onDataRetrieved(List<?> items) {
        customerProgressbarView.setVisibility(View.GONE);
        customerRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        customerRecyclerView.setAdapter(new CustomerListAdapter(items, new WeakReference<CustomerListAdapter.OnItemClickListener>(this)));
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
