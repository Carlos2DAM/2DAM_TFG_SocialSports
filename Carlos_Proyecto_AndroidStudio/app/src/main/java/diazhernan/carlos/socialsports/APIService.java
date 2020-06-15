package diazhernan.carlos.socialsports;

import java.util.ArrayList;
import java.util.HashMap;

import diazhernan.carlos.socialsports.Clases.Evento;
import diazhernan.carlos.socialsports.Clases.PuntuacionEvento;
import diazhernan.carlos.socialsports.Clases.PuntuacionParticipante;
import diazhernan.carlos.socialsports.Clases.Usuario;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @FormUrlEncoded
    @POST("registro")
    Call<Usuario> postRegistro(@Field("emailUsuario") String emailUsuario, @Field("passwordUsuario") String passwordUsuario);

    @FormUrlEncoded
    @POST("login")
    Call<Usuario> postLogin(@Field("emailUsuario") String emailUsuario, @Field("passwordUsuario") String passwordUsuario);

    @POST("eventos/crear")
    Call<ResponseBody> crearEvento(@Header("Authorization") String authHeader, @Body Evento evento);

    /************************USUARIOS************************/

    @FormUrlEncoded
    @PUT("perfil/nombre")
    Call<ResponseBody> putNombre(@Header("Authorization") String authHeader, @Field("correo") String correo, @Field("nombre") String nombre);

    @FormUrlEncoded
    @PUT("perfil/apellidos")
    Call<ResponseBody> putApellidos(@Header("Authorization") String authHeader, @Field("correo") String correo, @Field("apellidos") String apellidos);

    @FormUrlEncoded
    @PUT("perfil/direccion")
    Call<ResponseBody> putDireccion(@Header("Authorization") String authHeader, @Field("correo") String correo, @Field("direccion") String direccion);

    @FormUrlEncoded
    @PUT("perfil/genero")
    Call<ResponseBody> putGenero(@Header("Authorization") String authHeader, @Field("correo") String correo, @Field("genero") String genero);

    @FormUrlEncoded
    @PUT("perfil/nacimiento")
    Call<ResponseBody> putFechaNacimiento(@Header("Authorization") String authHeader, @Field("correo") String correo, @Field("fecha") String fecha);

    @FormUrlEncoded
    @PUT("perfil/password")
    Call<ResponseBody> putPassword(@Header("Authorization") String authHeader, @Field("correo") String correo, @Field("password") String password);

    @DELETE("perfil/borrarusuario/{correo}")
    Call<ResponseBody> borrarUsuario(@Header("Authorization") String authHeader, @Path("correo") String correo);

    @GET("perfil/amigos/{correo}")
    Call<ArrayList<Usuario>> listaAmigos(@Header("Authorization") String authHeader, @Path("correo") String correo);

    @GET("perfil/bloqueados/{correo}")
    Call<ArrayList<Usuario>> listaBloqueados(@Header("Authorization") String authHeader, @Path("correo") String correo);

    @POST("perfil/amigos/agregar/{correo}/{correoAmigo}")
    Call<ResponseBody> agregarAmigo(@Header("Authorization") String authHeader, @Path("correo") String correo, @Path("correoAmigo") String correoAmigo);

    @DELETE("perfil/amigos/eliminar/{correo}/{correoAmigo}")
    Call<ResponseBody> eliminarAmigo(@Header("Authorization") String authHeader, @Path("correo") String correo, @Path("correoAmigo") String correoAmigo);

    @POST("perfil/bloquearusuario/{correo}/{correoBloqueado}")
    Call<ResponseBody> bloquearUsuario(@Header("Authorization") String authHeader, @Path("correo") String correo, @Path("correoBloqueado") String correoBloqueado);

    @DELETE("perfil/quitarbloqueo/{correo}/{correoBloqueado}")
    Call<ResponseBody> quitarBloqueo(@Header("Authorization") String authHeader, @Path("correo") String correo, @Path("correoBloqueado") String correoBloqueado);


    /************************EVENTOS************************/

    @GET("eventos/pendientes/{correo}")
    Call<ArrayList<Evento>> listaEventosPendientes(@Header("Authorization") String authHeader, @Path("correo") String correo);

    @GET("eventos/finalizados/{correo}")
    Call<ArrayList<Evento>> listaEventosFinalizados(@Header("Authorization") String authHeader, @Path("correo") String correo);

    @GET("eventos/buscar")
    Call<ArrayList<Evento>> buscarEventos(@Header("Authorization") String authHeader,
                                          @Query("deporte") String deporte, @Query("localidad") String localidad, @Query("fecha") String fecha,
                                          @Query("hora") String hora, @Query("reservado") boolean reservado, @Query("reputacion") float reputacion);

    @FormUrlEncoded
    @PUT("eventos/actualizar/fecha")
    Call<ResponseBody> actualizarFechaEvento(@Header("Authorization") String authHeader, @Field("idEvento") String idEvento, @Field("fecha") String fecha);

    @FormUrlEncoded
    @PUT("eventos/actualizar/hora")
    Call<ResponseBody> actualizarHoraEvento(@Header("Authorization") String authHeader, @Field("idEvento") String idEvento, @Field("hora") String hora);

    @FormUrlEncoded
    @PUT("eventos/actualizar/direccion")
    Call<ResponseBody> actualizarDireccionEvento(@Header("Authorization") String authHeader, @Field("idEvento") String idEvento, @Field("direccion") String direccion);

    @FormUrlEncoded
    @PUT("eventos/actualizar/maximoparticipantes")
    Call<ResponseBody> actualizarMaxParticipantesEvento(@Header("Authorization") String authHeader, @Field("idEvento") String idEvento, @Field("maxParticipantes") int maxParticipantes);

    @FormUrlEncoded
    @PUT("eventos/actualizar/terminado")
    Call<ResponseBody> actualizarTerminarEvento(@Header("Authorization") String authHeader, @Field("idEvento") String idEvento, @Field("terminado") boolean terminado);

    @FormUrlEncoded
    @PUT("eventos/actualizar/reserva")
    Call<ResponseBody> actualizarReserva(@Header("Authorization") String authHeader, @Field("idEvento") String idEvento, @Field("reserva") boolean reserva);

    @FormUrlEncoded
    @PUT("eventos/actualizar/coste")
    Call<ResponseBody> actualizarCoste(@Header("Authorization") String authHeader, @Field("idEvento") String idEvento, @Field("coste") float coste);

    @FormUrlEncoded
    @PUT("eventos/actualizar/precio")
    Call<ResponseBody> actualizarPrecio(@Header("Authorization") String authHeader, @Field("idEvento") String idEvento, @Field("precio") float precio);

    @FormUrlEncoded
    @PUT("eventos/actualizar/comentarios")
    Call<ResponseBody> actualizarComentarios(@Header("Authorization") String authHeader, @Field("idEvento") String idEvento, @Field("comentarios") String comentarios);

    @FormUrlEncoded
    @PUT("eventos/actualizar/edadminima")
    Call<ResponseBody> actualizarEdadMinima(@Header("Authorization") String authHeader, @Field("idEvento") String idEvento, @Field("edad") int edad);

    @FormUrlEncoded
    @PUT("eventos/actualizar/edadmaxima")
    Call<ResponseBody> actualizarEdadMaxima(@Header("Authorization") String authHeader, @Field("idEvento") String idEvento, @Field("edad") int edad);

    @FormUrlEncoded
    @PUT("eventos/actualizar/genero")
    Call<ResponseBody> actualizarGenero(@Header("Authorization") String authHeader, @Field("idEvento") String idEvento, @Field("genero") String genero);

    @FormUrlEncoded
    @PUT("eventos/actualizar/reputacion")
    Call<ResponseBody> actualizarReputacion(@Header("Authorization") String authHeader, @Field("idEvento") String idEvento, @Field("reputacion") float reputacion);

    @DELETE("eventos/eliminarparticipante/{idEvento}/{correo}")
    Call<ResponseBody> eliminarParticipante(@Header("Authorization") String authHeader, @Path("idEvento") String idEvento, @Path("correo") String correo);

    @FormUrlEncoded
    @POST("eventos/insertarparticipante")
    Call<ResponseBody> insertarParticipante(@Header("Authorization") String authHeader, @Field("idEvento") String idEvento, @Field("correo") String correo);

    @FormUrlEncoded
    @POST("eventos/insertarsolicitante")
    Call<ResponseBody> insertarSolicitante(@Header("Authorization") String authHeader, @Field("idEvento") String idEvento, @Field("correo") String correo);

    @DELETE("eventos/eliminarsolicitante/{idEvento}/{correo}")
    Call<ResponseBody> eliminarSolicitante(@Header("Authorization") String authHeader, @Path("idEvento") String idEvento, @Path("correo") String correo);

    @PUT("eventos/bloquearsolicitud/{idEvento}/{correo}")
    Call<ResponseBody> bloquearSolicitud(@Header("Authorization") String authHeader, @Path("idEvento") String idEvento, @Path("correo") String correo);

    @DELETE("eventos/eliminar/{idEvento}")
    Call<ResponseBody> eliminarEvento(@Header("Authorization") String authHeader, @Path("idEvento") String idEvento);

    @GET("perfil/puntuacionparticipante/{correo}")
    Call<Float> getReputacionParticipante(@Header("Authorization") String authHeader, @Path("correo") String correo);

    @GET("perfil/puntuacionorganizador/{correo}")
    Call<Float> getReputacionOrganizador(@Header("Authorization") String authHeader, @Path("correo") String correo);

    @GET("eventos/hasidopuntuado/{idevento}/{email}")
    Call<Boolean> getHaSidoPuntuado(@Header("Authorization") String authHeader, @Path("idevento") String idevento, @Path("email") String email);

    @POST("perfil/insertarpuntuacion")
    Call<ResponseBody> insertarPuntuacionParticipante(@Header("Authorization") String authHeader, @Body PuntuacionParticipante puntuacion);

    @POST("eventos/insertarpuntuacion")
    Call<ResponseBody> insertarPuntuacionEvento(@Header("Authorization") String authHeader, @Body PuntuacionEvento puntuacion);

    @Multipart
    @POST("imagenes/upload/{correo}")
    Call<ResponseBody> subirImagen(@Header("Authorization") String authHeader, @Part MultipartBody.Part filePart, @Part("filename") RequestBody name, @Path("correo") String correo);
}
