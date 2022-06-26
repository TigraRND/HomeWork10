package ru.retrofit.helpers;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitFactory {

    public static Retrofit buildRetrofit(String baseURI) {
        return new Retrofit.Builder()
                .baseUrl(baseURI)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

}