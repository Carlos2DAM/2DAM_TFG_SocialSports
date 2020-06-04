package diazhernan.carlos.socialsports;

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
    Call<String> putNombre(@Field("correo") String correo, @Field("nombre") String nombre);

    @FormUrlEncoded
    @PUT("perfil/apellidos")
    Call<String> putApellidos(@Field("correo") String correo, @Field("apellidos") String apellidos);

    @FormUrlEncoded
    @PUT("perfil/direccion")
    Call<String> putDireccion(@Field("correo") String correo, @Field("direccion") String direccion);

    @FormUrlEncoded
    @PUT("perfil/genero")
    Call<String> putGenero(@Field("correo") String correo, @Field("genero") String genero);

    @DELETE("perfil/borrarusuario")
    Call<ResponseBody> borrarUsuario(@Body Usuario usuario);
}
