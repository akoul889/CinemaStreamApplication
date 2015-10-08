package com.akshay.cinemastream.Utils;

import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class CinemaApiClient {

    private static final String TAG = CinemaApiClient.class.getName();
    private static CinemaApiService sCinemaService;

    public static CinemaApiService getApiService() {
        if (sCinemaService == null) {
            OkHttpClient client = new OkHttpClient();
            client.interceptors().add(new TimberLoggingInterceptor());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            sCinemaService = retrofit.create(CinemaApiService.class);
        }
        return sCinemaService;
    }

}
