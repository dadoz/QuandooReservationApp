package com.application.restaurantreservation.application;

import android.app.Application;

public class StargazersApplication extends Application {
    private String owner;
    private String repo;

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
