package com.ssthouse.retrofitlearn;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ssthouse on 02/11/2016.
 */

public interface GithubService {


    @GET("/users/{username}")
    Call<GithubUser> getGithubUser(@Path("username") String username);
}
