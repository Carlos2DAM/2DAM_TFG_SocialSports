package diazhernan.carlos.socialsports;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {

    @POST("registro")
    Call<ResponseBody> postRegistro(@Body Map<String, String> mapa);

    @POST("login")
    Call<ResponseBody> postLogin(@Body Map<String, String> mapa);
}
