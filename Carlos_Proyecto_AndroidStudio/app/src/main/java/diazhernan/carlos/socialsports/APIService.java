package diazhernan.carlos.socialsports;

import diazhernan.carlos.socialsports.Clases.Usuario;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @FormUrlEncoded
    @POST("registro")
    Call<ResponseBody> postRegistro(@Field("emailUsuario") String emailUsuario, @Field("passwordUsuario") String passwordUsuario);

    @FormUrlEncoded
    @POST("login")
    Call<Usuario> postLogin(@Field("emailUsuario") String emailUsuario, @Field("passwordUsuario") String passwordUsuario);
}
