package com.application.restaurantreservation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        bindView();
        onInitView();
    }

    /**
     * bind view layout items
     */
    private void bindView() {
        findViewById(R.id.testTableGridButtonId)
                .setOnClickListener(v -> {
            startActivity(new Intent(this, TableGridActivity.class));
        });
    }

    /**
     * init view to handle button in custom view interaction
     */
    private void onInitView() {

    }

}
