package diazhernan.carlos.socialsports;

import java.util.ArrayList;

import diazhernan.carlos.socialsports.Clases.Evento;
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

public interface APIService {

    @FormUrlEncoded
    @POST("registro")
    Call<ResponseBody> postRegistro(@Field("emailUsuario") String emailUsuario, @Field("passwordUsuario") String passwordUsuario);

    @FormUrlEncoded
    @POST("login")
    Call<Usuario> postLogin(@Field("emailUsuario") String emailUsuario, @Field("passwordUsuario") String passwordUsuario);

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

    @POST("eventos/crear")
    Call<ResponseBody> crearEvento(@Header("Authorization") String authHeader, @Body Evento evento);

    @POST("eventos/invitaciones/{idEvento}")
    Call<ResponseBody> enviarInvitaciones(@Header("Authorization") String authHeader, @Path("idEvento") String idEvento, @Body ArrayList<String> listaInvitados);

    @GET("perfil/amigos/{correo}")
    Call<ArrayList<Usuario>> listaAmigos(@Header("Authorization") String authHeader, @Path("correo") String correo);

    @POST("perfil/amigos/agregar/{correo}/{correoAmigo}")
    Call<ResponseBody> agregarAmigo(@Header("Authorization") String authHeader, @Path("correo") String correo, @Path("correoAmigo") String correoAmigo);

    @DELETE("perfil/amigos/eliminar/{correo}/{correoAmigo}")
    Call<ResponseBody> eliminarAmigo(@Header("Authorization") String authHeader, @Path("correo") String correo, @Path("correoAmigo") String correoAmigo);

    @GET("eventos/pendientes/{correo}")
    Call<ArrayList<Evento>> listaEventosPendientes(@Header("Authorization") String authHeader, @Path("correo") String correo);

}
