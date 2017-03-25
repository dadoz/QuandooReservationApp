package com.application.restaurantreservation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.application.restaurantreservation.adapter.CustomerListAdapter;
import com.application.restaurantreservation.presenter.CustomerPresenter;
import com.application.restaurantreservation.presenter.CustomerView;
import com.application.restaurantreservation.services.ReservationService;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;


public class MainActivity extends BaseActivity implements CustomerView, CustomerListAdapter.OnItemClickListener {
    @Inject
    public ReservationService service;
    private View  customerProgressbarView;
    private RecyclerView customerRecyclerView;
    private View emptyCustomerView;
    String TAG = "TAG";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        bindView();
        init();
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
    private void init() {
        //inject activity on depsConnector module
        getDeps().inject(this);

        //init presenter
        CustomerPresenter presenter = new CustomerPresenter(new WeakReference<>(service),
                new WeakReference<>(this));
        presenter.getCustomerList();
    }

    @Override
    public void onDataRetrieved(List<?> items) {
        Log.e(TAG, "---" + items.size());
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
