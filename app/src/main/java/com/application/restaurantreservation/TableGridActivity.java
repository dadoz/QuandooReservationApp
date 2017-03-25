package com.application.restaurantreservation;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.application.restaurantreservation.adapter.StargazerListAdapter;
import com.application.restaurantreservation.application.StargazersApplication;
import com.application.restaurantreservation.presenter.StargazerPresenter;
import com.application.restaurantreservation.presenter.StargazerView;
import com.application.restaurantreservation.utils.Utils;
import com.application.restaurantreservation.views.EmptyView;

import java.lang.ref.WeakReference;
import java.util.List;


public class TableGridActivity extends AppCompatActivity implements StargazerView {
    private String owner;
    private String repo;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    EmptyView emptyView;

    private StargazerPresenter presenter;

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
        presenter = new StargazerPresenter();
        presenter.init(new WeakReference<>(this),
                new WeakReference<>(this), Utils.buildParams(owner, repo));
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

    @Override
    public void bindData(List<?> items, int i) {
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        initRecyclerView(items);
    }


    @Override
    public void onRetrieveDataError(String error) {
        //TODO implement it - show a view maybe
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        Snackbar.make(findViewById(R.id.activity_main), R.string.retrieve_error,
                Snackbar.LENGTH_SHORT).show();
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new StargazerListAdapter(items));
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
}
