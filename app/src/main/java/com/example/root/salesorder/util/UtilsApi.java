package com.example.root.salesorder.util;

public class UtilsApi {
    public static final String BASE_URL_API = "http://gawong.tugasakhir.site/index.php/api/";

    // deklarasi interface base api service
    public static BaseApiService getAPIService() {
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
