package io.garuda.skyworks.Data;

/**
 * Created by Joshua on 29/01/2018.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://172.25.96.31:3003";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
