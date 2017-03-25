package com.application.restaurantreservation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.restaurantreservation.R;
import com.application.restaurantreservation.model.Customer;
import com.bumptech.glide.Glide;

import java.util.List;


public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder> {
    private List<?> items;

    public CustomerListAdapter(List<?> devices) {
        items = devices;
    }

    @Override
    public CustomerListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.customer_item, viewGroup, false);
        return new CustomerListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        Customer customer = (Customer) items.get(position);
        vh.firstNameView.setText(customer.getCustomerFirstName());
        vh.lastNameView.setText(customer.getCustomerLastName());
        vh.userIdView.setText(customer.getId());
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView userIdView;
        private final TextView firstNameView;
        private final TextView lastNameView;

        /**
         *
         * @param view
         */
        private ViewHolder(View view) {
            super(view);
            userIdView = (TextView) view.findViewById(R.id.userIdTextViewId);
            firstNameView = (TextView) view.findViewById(R.id.firstNameTextViewId);
            lastNameView = (TextView) view.findViewById(R.id.lastNameTextViewId);
        }


    }

}
