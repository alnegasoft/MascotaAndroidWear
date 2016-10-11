package mx.alnegasoft.mascotamd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import mx.alnegasoft.mascotamd.restApi.EndPointsApi;
import mx.alnegasoft.mascotamd.restApi.adapter.RestApiAdapter;
import mx.alnegasoft.mascotamd.restApi.model.UsuarioResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ABITA on 11/10/2016.
 */
public class UnFollowUser extends BroadcastReceiver {
    public String idUsuarioLike="0";
    public String nombreUsuarioLike="0";
    @Override
    public void onReceive(Context context, Intent intent) {
        String ACTION_KEY = "UNFOLLOW";
        String action = intent.getAction();

        if(ACTION_KEY.equals(action)){
            Bundle extras = intent.getExtras();
            if (extras.get("idUsuarioLike")== null){
                idUsuarioLike="0";
                nombreUsuarioLike="0";
            }
            else{
                idUsuarioLike = (String) extras.get("idUsuarioLike");
                nombreUsuarioLike = (String) extras.get("nombreUsuarioLike");
            }
            darUnFollow(idUsuarioLike,"unfollow");
            Toast.makeText(context, "Has dado UnFollow al usuario: " + nombreUsuarioLike, Toast.LENGTH_SHORT).show();
        }
    }

    public void darUnFollow(String usuarioId, String accionPost) {

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonFollowUnfollow = restApiAdapter.construyeGsonDeserializadorPostFollowUnfollow();
        EndPointsApi endPointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonFollowUnfollow);

        Call<UsuarioResponse> usuarioResponseCall =  endPointsApi.followUnfollow(usuarioId,accionPost);

        usuarioResponseCall.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                Log.e("Instagram","OK Unfollow");
            }
            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                Log.e("FALLO LA CONEXION", t.toString());
            }
        });


    }
}
