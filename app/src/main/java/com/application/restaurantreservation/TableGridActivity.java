package com.application.restaurantreservation;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.application.restaurantreservation.adapter.TableGridAdapter;
import com.application.restaurantreservation.components.DaggerTableComponent;
import com.application.restaurantreservation.components.TableComponent;
import com.application.restaurantreservation.db.DataManager;
import com.application.restaurantreservation.modules.NetworkModule;
import com.application.restaurantreservation.presenter.BaseView;
import com.application.restaurantreservation.presenter.TablesPresenter;
import com.application.restaurantreservation.services.ReservationService;
import com.application.restaurantreservation.views.EmptyView;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;


public class TableGridActivity extends AppCompatActivity implements BaseView, TableGridAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private EmptyView emptyView;

    @Inject
    public ReservationService service;

    private TablesPresenter presenter;
    private View tableGridLayout;
    private TableComponent tableConnector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_grid);
        tableConnector = DaggerTableComponent.builder()
                .networkModule(new NetworkModule(getApplicationContext())).build();

        bindView();
        initActionbar();
        onInit();
    }

    /**
     *
     */
    private void bindView() {
        recyclerView = (RecyclerView) findViewById(R.id.tableRecyclerViewId);
        progressBar = (ProgressBar) findViewById(R.id.tableProgressbarId);
        emptyView = (EmptyView) findViewById(R.id.tableEmptyViewId);
        tableGridLayout = findViewById(R.id.tableGridLayoutId);
    }

    /**
     * iit view and retrieve stargazers data
     */
    private void onInit() {
        //inject activity on connector module
        tableConnector.inject(this);

        presenter = new TablesPresenter(new WeakReference<>(service),
                new WeakReference<>(this));
        presenter.getTableList();
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
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        recyclerView.setAdapter(new TableGridAdapter((List<Boolean>) items, new WeakReference<>(this)));
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
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        Snackbar.make(findViewById(R.id.tableGridLayoutId), R.string.retrieve_error,
                Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onDataRetrieved(List<?> items) {
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        initRecyclerView(items);
    }

    @Override
    public void onItemClick(View view, int position) {
        Snackbar.make(tableGridLayout, getString(R.string.table_has_been_selected), Snackbar.LENGTH_SHORT)
                .show();
        ((TableGridAdapter) recyclerView.getAdapter()).selectTable(position);
        recyclerView.getAdapter().notifyDataSetChanged();

        //set table as reserved
        try {
            service.updateTable(((TableGridAdapter) recyclerView.getAdapter()).getItems());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
