package io.garuda.skyworks.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import io.garuda.skyworks.Activities.ChooseService;

/**
 * Created by Joshua on 29/01/2018.
 */

public class ApiUtils {

    private ApiUtils() {}

    //public static final String BASE_URL = "http://192.168.1.149:3003";

    public static APIService getAPIService(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        String ip = sharedPref.getString("IP", "invalid");
        return RetrofitClient.getClient(ip).create(APIService.class);
    }

}
