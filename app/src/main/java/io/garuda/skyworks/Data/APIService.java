package io.garuda.skyworks.Data;

import java.util.List;

import io.garuda.skyworks.Models.CreditCard;
import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.Models.Service;
import io.garuda.skyworks.Models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    @PUT("/api/jobs")
    Call<Service> putJob(@Body Service job);

    @POST("/api/jobs/{token}")
    Call<Service> postJob(@Path("token") String token, @Body Service job);

    @GET("/api/jobs/{token}")
    Call<Service> getJob(@Path("token") String token);

    @GET("/api/operator")
    Call<List<Provider>> getOperator();

    @GET("/api/operator/{token}")
    Call<Provider> getProvider(@Path("token") String token);

    @GET("/api/customer/{token}")
    Call<User> getUser(@Path("token") String token);

    @POST("/api/customer/{token}")
    Call<User> postUser(@Path("token") String token, @Body User customer);

    @PUT("/api/creditcard")
    Call<CreditCard> putCard(@Body CreditCard card);

    @GET("/api/creditcard/{token}")
    Call<CreditCard> getCard(@Path("token") String token);

    @DELETE("/api/creditcard/{token}")
    Call<CreditCard> deleteCard(@Path("token") String token);

}