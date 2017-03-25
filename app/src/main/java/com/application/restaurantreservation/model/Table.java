package com.application.restaurantreservation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Table {

    public Table(long id, boolean isFree) {
        this.id = id;
        this.isFree = isFree;
    }

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("is_free")
    @Expose
    private boolean isFree;

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public long getId() {
        return id;
    }
}