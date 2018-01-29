package io.garuda.skyworks.Data;

import java.util.List;

import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @POST("/api/jobs")
    Call<Service> saveJob(@Body Service job);

    @GET("/api/jobs")
    Call<List<Service>> getJob();

    @GET("/api/operator")
    Call<List<Provider>> getOperator();

}