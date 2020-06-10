package diazhernan.carlos.socialsports;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RETROFIT {

    private APIService service;

    public RETROFIT(){

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://socialsports.ddns.net:8081/API-0.0.1-SNAPSHOT/rest/")
                //.baseUrl("http://192.168.1.39:8082/APIREST/rest/")
                .baseUrl("http://192.168.43.70:8080/APIREST/rest/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(APIService.class);
    }

    public APIService getAPIService(){
        return this.service;
    }
}
