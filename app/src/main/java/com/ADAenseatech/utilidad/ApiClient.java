package com.ADAenseatech.utilidad;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import java.util.List;

public class ApiClient {
    private static final String BASE_URL = "https://tu-api.com/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static ApiService getService() {
        return retrofit.create(ApiService.class);
    }
}

interface ApiService {
    @GET("cursos")
    Call<List<Object>> getCursos(); // Cambia Object por tu modelo Curso

    @GET("lecciones")
    Call<List<Object>> getLecciones(); // Cambia Object por tu modelo Leccion
}