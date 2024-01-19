package com.example.st_jokoa;

import android.os.AsyncTask;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateDataTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
        try {
            // Crear una instancia de Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.23.28.190:8012")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Crear una instancia de ApiService
            ApiService apiService = retrofit.create(ApiService.class);

            // Crear un objeto Item con la ruta correcta
            Item item = new Item();
            item.setRuta("/data/data/com.stenpresa.st_jokoa/databases/GaldetegiaST.db");

            // Realizar la solicitud POST
            Call<Void> call = apiService.actualizarDatos(item);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    // Manejar la respuesta exitosa
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // Manejar el fallo
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
