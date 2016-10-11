package mx.alnegasoft.mascotamd.restApi.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.alnegasoft.mascotamd.restApi.ConstantesRestApi;
import mx.alnegasoft.mascotamd.restApi.EndPointsApi;
import mx.alnegasoft.mascotamd.restApi.deserializador.MediaDeserializador;
import mx.alnegasoft.mascotamd.restApi.deserializador.UserDeserializador;
import mx.alnegasoft.mascotamd.restApi.deserializador.UsersTimelineDeserializador;
import mx.alnegasoft.mascotamd.restApi.model.MediaResponse;
import mx.alnegasoft.mascotamd.restApi.model.UserResponse;
import mx.alnegasoft.mascotamd.restApi.model.UsuarioResponse;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NEO on 12/08/2016.
 */
public class RestApiAdapter {

    public EndPointsApi establecerConexionRestApiInstagram(Gson gson){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(EndPointsApi.class);
    }

    public  Gson construyeGsonDeserializadorPostFollowUnfollow(){

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(UsuarioResponse.class, new UserDeserializador());

        return gsonBuilder.create();
    }

    public Gson construyeGsonDeserializadorUserId(){

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(UserResponse.class, new UserDeserializador());

        return gsonBuilder.create();
    }

    public Gson construyeGsonDeserializadorMediaUserId(){

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MediaResponse.class, new MediaDeserializador());

        return gsonBuilder.create();
    }

    public Gson construyeGsonDeserializadorMediaUsersTimeline(){

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(UserResponse.class, new UsersTimelineDeserializador());

        return gsonBuilder.create();
    }


    public EndPointsApi establecerConexionRestAPI(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.ROOT_URL_HEROKU)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                ;
        return retrofit.create(EndPointsApi.class);
    }

}
