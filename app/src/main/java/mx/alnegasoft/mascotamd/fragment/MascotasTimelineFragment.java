package mx.alnegasoft.mascotamd.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mx.alnegasoft.mascotamd.R;
import mx.alnegasoft.mascotamd.adaptader.PerfilMascotaAdaptador;
import mx.alnegasoft.mascotamd.pojo.Mascota;
import mx.alnegasoft.mascotamd.restApi.EndPointsApi;
import mx.alnegasoft.mascotamd.restApi.adapter.RestApiAdapter;
import mx.alnegasoft.mascotamd.restApi.model.MediaResponse;
import mx.alnegasoft.mascotamd.restApi.model.UserResponse;
import mx.alnegasoft.mascotamd.restApi.model.UsuarioResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MascotasTimelineFragment extends Fragment {

    private RecyclerView listaMascotas;
    ArrayList<Mascota> usuarios;
    ArrayList<Mascota> mascotas;
    View v;

    String userId = "0";
    String nombreCuenta="0";
    TextView tvIdUsuarioActivo, tvUsuarioActivo;

    public MascotasTimelineFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_mascotas_timeline, container, false);

        listaMascotas = (RecyclerView) v.findViewById(R.id.rvTimelineMascotas);

        GridLayoutManager glm = new GridLayoutManager(getActivity(),3);
        glm.setOrientation(GridLayoutManager.VERTICAL);
        listaMascotas.setLayoutManager(glm);

        if (getArguments() != null) {
            userId = getArguments().get("userId").toString();
            nombreCuenta = getArguments().get("nombreCuenta").toString();

            tvUsuarioActivo = (TextView) v.findViewById(R.id.tvUsuarioActivo);
            tvUsuarioActivo.setText(nombreCuenta);
            tvIdUsuarioActivo = (TextView) v.findViewById(R.id.tvIdUsuarioActivo);
            tvIdUsuarioActivo.setText(userId);
        }

        obtenerMediaTimeline();

        return v;
    }

    public void inicializarAdaptador(){
        PerfilMascotaAdaptador adaptador = new PerfilMascotaAdaptador(mascotas,getActivity());
        listaMascotas.setAdapter(adaptador);

    }

    public void obtenerMediaTimeline() {

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonUsersTimeline = restApiAdapter.construyeGsonDeserializadorMediaUsersTimeline();
        EndPointsApi endPointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonUsersTimeline);

        Call<UserResponse> usersResponseCall = endPointsApi.getUsersTimeline();

        usersResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse  userResponse = response.body();
                usuarios =  userResponse.getUsuarios();

                //Toast.makeText(getContext(), usuarios.get(0).getId().toString(), Toast.LENGTH_SHORT).show();

                for (int i = 0; i <usuarios.size() ; i++) {

                    //Toast.makeText(getContext(), usuarios.get(i).getId(), Toast.LENGTH_SHORT).show();
                    RestApiAdapter restApiAdapter = new RestApiAdapter();
                    Gson gsonMediaUserId = restApiAdapter.construyeGsonDeserializadorMediaUserId();
                    EndPointsApi endPointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonMediaUserId);

                    //Call<MediaResponse> mediaResponseCall = endPointsApi.getRecentMediaUser(usuarios.get(0).getId().toString());
                    Call<MediaResponse> mediaResponseCall = endPointsApi.getRecentMediaUser("3648605337");

                    //
                    mediaResponseCall.enqueue(new Callback<MediaResponse>() {
                        @Override
                        public void onResponse(Call<MediaResponse> call, Response<MediaResponse> response) {

                            MediaResponse mediaResponse = response.body();
                            mascotas = mediaResponse.getMascotas();
                            inicializarAdaptador();


                        }

                        @Override
                        public void onFailure(Call<MediaResponse> call, Throwable t) {
                            Toast.makeText(getContext(), "Intenta de nuevo!!!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Intenta de nuevo!!!", Toast.LENGTH_SHORT).show();
            }
        });


    }



}
