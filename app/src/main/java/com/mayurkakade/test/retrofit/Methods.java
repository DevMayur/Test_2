package com.mayurkakade.test.retrofit;

import com.mayurkakade.test.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Methods {

    @GET("users")
    Call<List<UserModel>> getUsers();
}
