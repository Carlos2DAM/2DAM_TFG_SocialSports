package com.laura.pruebaconexion;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @GET("{idUsuario}")
    Call<ResponseBody> getRespuesta(@Path("idUsuario") int idUsuario);

    @POST("registro")
    Call<ResponseBody> post(@Body Usuario usuario);
}
