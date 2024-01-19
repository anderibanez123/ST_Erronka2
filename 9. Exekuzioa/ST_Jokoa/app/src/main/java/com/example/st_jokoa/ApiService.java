// ApiService.java
package com.example.st_jokoa;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/datuak_berritu")
    Call<Void> actualizarDatos(@Body Item item);
}
