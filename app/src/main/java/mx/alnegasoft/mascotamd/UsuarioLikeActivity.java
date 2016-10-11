package mx.alnegasoft.mascotamd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import mx.alnegasoft.mascotamd.adaptader.PerfilMascotaAdaptador;
import mx.alnegasoft.mascotamd.pojo.Mascota;
import mx.alnegasoft.mascotamd.restApi.EndPointsApi;
import mx.alnegasoft.mascotamd.restApi.adapter.RestApiAdapter;
import mx.alnegasoft.mascotamd.restApi.model.MediaResponse;
import mx.alnegasoft.mascotamd.restApi.model.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioLikeActivity extends AppCompatActivity {

    private RecyclerView listaMascotas;
    ArrayList<Mascota> mascotas;
    public String idUsuarioLike="0";
    public String nombreUsuarioLike="0";
    TextView tvUsuarioLike, tvIdUsuarioLike;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_like);

        listaMascotas = (RecyclerView) findViewById(R.id.rvFotosRecientes);

        GridLayoutManager glm = new GridLayoutManager( this ,3);
        glm.setOrientation(GridLayoutManager.VERTICAL);
        listaMascotas.setLayoutManager(glm);

        Bundle extras = getIntent().getExtras();
        if (extras.get("idUsuarioLike")== null){
            idUsuarioLike="0";
            nombreUsuarioLike="0";
        }
        else{
            idUsuarioLike = (String) extras.get("idUsuarioLike");
            nombreUsuarioLike = (String) extras.get("nombreUsuarioLike");
        }

        tvUsuarioLike = (TextView) findViewById(R.id.tvUsuarioLike);
        tvUsuarioLike.setText(nombreUsuarioLike);
        tvIdUsuarioLike = (TextView) findViewById(R.id.tvIdUsuarioLike);
        tvIdUsuarioLike.setText(idUsuarioLike);

        obtenerMediaFotosRecientes(idUsuarioLike);


    }


    public void inicializarAdaptador(){
        PerfilMascotaAdaptador adaptador = new PerfilMascotaAdaptador(mascotas,this);
        listaMascotas.setAdapter(adaptador);
    }

    public void obtenerMediaFotosRecientes(String idUsuarioLike) {

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaUserId = restApiAdapter.construyeGsonDeserializadorMediaUserId();
        EndPointsApi endPointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonMediaUserId);

        //Call<MediaResponse> mediaResponseCall = endPointsApi.getRecentMediaUser("3671713044");
        Call<MediaResponse> mediaResponseCall = endPointsApi.getRecentMediaUser(idUsuarioLike);

        mediaResponseCall.enqueue(new Callback<MediaResponse>() {
            @Override
            public void onResponse(Call<MediaResponse> call, Response<MediaResponse> response) {

                MediaResponse mediaResponse = response.body();
                mascotas = mediaResponse.getMascotas();
                inicializarAdaptador();
            }

            @Override
            public void onFailure(Call<MediaResponse> call, Throwable t) {
                Toast.makeText(UsuarioLikeActivity.this, "Intenta de nuevo!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
