package diazhernan.carlos.socialsports;

import java.util.ArrayList;

import diazhernan.carlos.socialsports.Clases.Evento;
import diazhernan.carlos.socialsports.Clases.Usuario;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<ResponseBody> putNombre(@Field("correo") String correo, @Field("nombre") String nombre);

    @FormUrlEncoded
    @PUT("perfil/apellidos")
    Call<ResponseBody> putApellidos(@Field("correo") String correo, @Field("apellidos") String apellidos);

    @FormUrlEncoded
    @PUT("perfil/direccion")
    Call<ResponseBody> putDireccion(@Field("correo") String correo, @Field("direccion") String direccion);

    @FormUrlEncoded
    @PUT("perfil/genero")
    Call<ResponseBody> putGenero(@Field("correo") String correo, @Field("genero") String genero);

    @FormUrlEncoded
    @PUT("perfil/nacimiento")
    Call<ResponseBody> putFechaNacimiento(@Field("correo") String correo, @Field("fecha") String fecha);

    @DELETE("perfil/borrarusuario/{correo}")
    Call<ResponseBody> borrarUsuario(@Path("correo") String correo);

    @POST("eventos/crear")
    Call<ResponseBody> crearEvento(@Body Evento evento);

    @POST("eventos/invitaciones/{idEvento}")
    Call<ResponseBody> enviarInvitaciones(@Path("idEvento") String idEvento, @Body ArrayList<String> listaInvitados);
}
