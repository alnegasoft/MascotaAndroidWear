package mx.alnegasoft.mascotamd.adaptader;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mx.alnegasoft.mascotamd.MainActivity;
import mx.alnegasoft.mascotamd.pojo.Mascota;
import mx.alnegasoft.mascotamd.R;
import mx.alnegasoft.mascotamd.restApi.EndPointsApi;
import mx.alnegasoft.mascotamd.restApi.adapter.RestApiAdapter;
import mx.alnegasoft.mascotamd.restApi.model.UsuarioResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NEO on 12/07/2016.
 */
public class PerfilMascotaAdaptador extends RecyclerView.Adapter<PerfilMascotaAdaptador.PerfilMascotaViewHolder> {

    ArrayList<Mascota> mascotas;
    Activity activity;
    TextView tvIdUsuarioActivo, tvUsuarioActivo;
    String idUsuarioActivo="0", usuarioActivo="0";


    public PerfilMascotaAdaptador(ArrayList<Mascota> mascotas , Activity activity){
        this.mascotas = mascotas;
        this.activity = activity;

    }




    @Override
    public PerfilMascotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_perfil , parent , false);

        return new PerfilMascotaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PerfilMascotaViewHolder mascotaViewHolder, final int position) {
        final Mascota mascota = mascotas.get(position);
        //mascotaViewHolder.ivFotoPerfil.setImageResource(mascota.getFoto());
        Picasso.with(activity)
                .load(mascota.getUrlFoto())
                .placeholder(R.drawable.perro1)
                .into(mascotaViewHolder.ivFotoPerfil);

        //mascotaViewHolder.tvNombrePerfil.setText(mascota.getNombre());
        mascotaViewHolder.tvRaitingPerfil.setText(Integer.toString(mascota.getLikes()));


        mascotaViewHolder.btnRaitingAcumPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvUsuarioActivo = (TextView) activity.findViewById(R.id.tvUsuarioActivo);
                usuarioActivo = tvUsuarioActivo.getText().toString();

                tvIdUsuarioActivo = (TextView) activity.findViewById(R.id.tvIdUsuarioActivo);
                idUsuarioActivo = tvIdUsuarioActivo.getText().toString();

                if (!usuarioActivo.equals("-")) {
                    String token = FirebaseInstanceId.getInstance().getToken();
                    enviarTokenRegistro(token, mascota.getId(), mascota.getIdFoto(), String.valueOf(mascota.getLikes() + 1), mascota.getNombre(), mascota.getUrlFotoPerfil());

//                Toast.makeText(activity, "Token: " + token, Toast.LENGTH_SHORT).show();
//                Toast.makeText(activity, "Id: " + mascota.getId(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(activity, "IdFoto: " + mascota.getIdFoto(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(activity, "Likes: " + mascota.getLikes(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(activity, "Nombre: " + mascota.getNombre(), Toast.LENGTH_SHORT).show();


                    //Toast.makeText(activity, "Mascota: " + mascota.getIdFoto(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(activity, "Configura un usuario para dar Like", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return mascotas.size();
    }

    public static class PerfilMascotaViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivFotoPerfil;
        private TextView tvNombrePerfil;
        private TextView tvRaitingPerfil;
        private Button btnRaitingAcumPerfil;



        public PerfilMascotaViewHolder(View itemView) {
            super(itemView);
            ivFotoPerfil          = (ImageView) itemView.findViewById(R.id.ivFotoPerfil);
            tvNombrePerfil        = (TextView) itemView.findViewById(R.id.tvNombrePerfil);
            tvRaitingPerfil       = (TextView) itemView.findViewById(R.id.tvRaitingPerfil);
            btnRaitingAcumPerfil    = (Button) itemView.findViewById(R.id.btnRaitingAcumPerfil);

        }
    }

    private void enviarTokenRegistro(final String token, String userId, String idFoto, String fotoLikes, final String nombreUsuario, final  String urlFotoPerfil){
        Log.d("TOKEN", token);
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        final EndPointsApi endpoints = restApiAdapter.establecerConexionRestAPI();
        Call<UsuarioResponse> usuarioResponseCall =  endpoints.registrarUsuario(token, userId, idFoto, fotoLikes);


        usuarioResponseCall.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                UsuarioResponse usuarioResponse = response.body();
                Log.d("ID_FIREBASE", usuarioResponse.getId());
                Log.d("USUARIO_FIREBASE", usuarioResponse.getId_dispositivo());
                Log.d("USUARIO_INSTAGRAM", usuarioResponse.getId_instagram());

                Call<UsuarioResponse> usuarioResponseCall1 = endpoints.darLike(usuarioResponse.getId(), usuarioResponse.getId_instagram(), nombreUsuario, urlFotoPerfil,idUsuarioActivo,usuarioActivo);

                usuarioResponseCall1.enqueue(new Callback<UsuarioResponse>() {
                    @Override
                    public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                        UsuarioResponse usuarioResponse1 = response.body();
                        //Log.d("ID_FIREBASE", usuarioResponse1.getId());
                        Log.d("USUARIO_FIREBASE", usuarioResponse1.getId_dispositivo());
                        Log.d("USUARIO_INSTAGRAM", usuarioResponse1.getId_instagram());
                    }

                    @Override
                    public void onFailure(Call<UsuarioResponse> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {

            }
        });

    }


}
