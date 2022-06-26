package ru.retrofit.helpers;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitFactory {
    private static final String REQ_RES_IN_BASE_URI = "https:/reqres.in/api/";
    private static final String CHUCK_NORRIS_BASE_URI = "https://api.chucknorris.io/jokes/";

    public static Retrofit chuckNorrisSite() {
        return buildRetrofit(CHUCK_NORRIS_BASE_URI);
    }

    public static Retrofit reqResInSite() {
        return buildRetrofit(REQ_RES_IN_BASE_URI);
    }

    public static Retrofit buildRetrofit(String baseURI) {
        return new Retrofit.Builder()
                .baseUrl(baseURI)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}