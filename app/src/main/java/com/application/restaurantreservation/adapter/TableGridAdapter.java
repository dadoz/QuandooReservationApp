package com.application.restaurantreservation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.restaurantreservation.R;
import com.application.restaurantreservation.model.Customer;

import java.lang.ref.WeakReference;
import java.util.List;


public class TableGridAdapter extends RecyclerView.Adapter<TableGridAdapter.ViewHolder> {
    private List<Boolean> items;
    private WeakReference<OnItemClickListener> listener;

    public TableGridAdapter(List<Boolean> devices, WeakReference<OnItemClickListener> listener) {
        items = devices;
        this.listener = listener;
    }

    @Override
    public TableGridAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.table_item, viewGroup, false);
        return new TableGridAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        Context context = vh.itemView.getContext();

        //retrie item
        boolean isTableSelected = items.get(position);

        vh.statusTextView.setText(context.getString(isTableSelected ?
                R.string.table_selected : R.string.table_not_selected));
        vh.tableLayoutItem.setBackgroundColor(isTableSelected ?
                ContextCompat.getColor(context, R.color.light_yellow) : Color.TRANSPARENT);
        vh.itemView.setOnClickListener(v -> {
            if (listener.get() != null)
                listener.get().onItemClick(v, position);
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void selectTable(int position) {
        items.set(position, true);
    }

    public List<Boolean> getItems() {
        return items;
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView statusTextView;
        private final View tableLayoutItem;

        /**
         *
         * @param view
         */
        private ViewHolder(View view) {
            super(view);
            statusTextView = (TextView) view.findViewById(R.id.statusTextViewId);
            tableLayoutItem = view.findViewById(R.id.tableLayoutItemId);
        }


    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
