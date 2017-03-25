package com.application.restaurantreservation;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.application.restaurantreservation.adapter.CustomerListAdapter;
import com.application.restaurantreservation.application.StargazersApplication;
import com.application.restaurantreservation.presenter.CustomerPresenter;
import com.application.restaurantreservation.presenter.CustomerView;
import com.application.restaurantreservation.utils.Utils;
import com.application.restaurantreservation.views.EmptyView;

import java.lang.ref.WeakReference;
import java.util.List;


public class TableGridActivity extends AppCompatActivity implements CustomerView {
    private String owner;
    private String repo;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    EmptyView emptyView;

    private CustomerPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_grid);

        repo = ((StargazersApplication) getApplication()).getRepo();
        owner = ((StargazersApplication) getApplication()).getOwner();

        bindView();
        onInitView();
    }

    /**
     *
     */
    private void bindView() {
        recyclerView = (RecyclerView) findViewById(R.id.stargazerRecyclerViewId);
        progressBar = (ProgressBar) findViewById(R.id.stargazerProgressbarId);
        emptyView = (EmptyView) findViewById(R.id.emptyViewId);
    }

    /**
     * iit view and retrieve stargazers data
     */
    private void onInitView() {
        initActionbar();
//        presenter = new CustomerPresenter();
//        presenter.init(new WeakReference<>(this),
//                new WeakReference<>(this), Utils.buildParams(owner, repo));
    }

    /**
     * actionbar set listener and back arrow
     */
    private void initActionbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * init recycler view binding data by adapter
     * @param items
     */
    private void initRecyclerView(List<?> items) {
        if (items.size() == 0) {
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.setAdapter(new CustomerListAdapter(items));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (presenter != null)
                    presenter.unsubscribe();
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFailure(String message) {
        //TODO implement it - show a view maybe
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        Snackbar.make(findViewById(R.id.activity_main), R.string.retrieve_error,
                Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onDataRetrieved(List<?> items) {
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        initRecyclerView(items);

    }
}
