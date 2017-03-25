package com.application.subitoit.githubstargazers.services;


import com.application.subitoit.githubstargazers.model.Stargazer;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StargazerService {
    @GET("repos/{owner}/{repo}/stargazers")
    Observable<ArrayList<Stargazer>> getStargazers(@Path("owner") String owner, @Path("repo") String repo);

}
