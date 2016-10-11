package mx.alnegasoft.mascotamd;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import mx.alnegasoft.mascotamd.adaptader.PageAdapter;
import mx.alnegasoft.mascotamd.fragment.MascotasFragment;
import mx.alnegasoft.mascotamd.fragment.MascotasTimelineFragment;
import mx.alnegasoft.mascotamd.fragment.PerfilFragment;
import mx.alnegasoft.mascotamd.restApi.EndPointsApi;
import mx.alnegasoft.mascotamd.restApi.adapter.RestApiAdapter;
import mx.alnegasoft.mascotamd.restApi.model.UsuarioResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public String userId="0";
    public String fotoPerfil="0";
    public String nombreCuenta="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        setUpViewPager();

        if(getIntent().getExtras() != null){
            for(String key: getIntent().getExtras().keySet()){
                String value = getIntent().getExtras().getString(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }

    }

    public void irMascotasVotadas(){
        Intent intent = new Intent(this , MascotasVotadas.class);
        startActivity(intent);
   }

    public void irFormulario(){
        Intent intent = new Intent(this , ComentariosActivity.class);
        startActivity(intent);
    }

    public void irAcercaDe(){
        Intent intent = new Intent(this , AcercaDeActivity.class);
        startActivity(intent);
    }

    public void irConfigurarCuenta(){
        Intent intent = new Intent(this , ConfigurarCuenta.class);
        startActivity(intent);
    }


    public void recibirNotificaciones(){
        if (!userId.equals("0")) {
            String token = FirebaseInstanceId.getInstance().getToken();
            enviarTokenRegistro(token, userId);
        }else{
            Toast.makeText(MainActivity.this, "No hay un usuario activo, selecciona un usuario en el menu Configurar Cuenta ", Toast.LENGTH_SHORT).show();
        }

    }

    private void enviarTokenRegistro(String token, String userId){
        Log.d("TOKEN", token);
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        EndPointsApi endpoints = restApiAdapter.establecerConexionRestAPI();
        Call<UsuarioResponse> usuarioResponseCall =  endpoints.registrarUsuario(token, userId, userId,"0");

        usuarioResponseCall.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                UsuarioResponse usuarioResponse = response.body();
                Log.d("ID_FIREBASE", usuarioResponse.getId());
                Log.d("USUARIO_FIREBASE", usuarioResponse.getId_dispositivo());
                Log.d("USUARIO_INSTAGRAM", usuarioResponse.getId_instagram());
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
/*        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favoritas) {
            irMascotasVotadas();
            return true;
        }*/

        switch (item.getItemId()){
            case R.id.action_favoritas:
                irMascotasVotadas();
                break;
            case R.id.mContacto:
                irFormulario();
                break;
            case R.id.mConfigurarCuenta:
                irConfigurarCuenta();
                break;
            case R.id.mAcercaDe:
                irAcercaDe();
                break;
            case R.id.mRecibirNotificaciones:
                recibirNotificaciones();
                break;

        }




        return super.onOptionsItemSelected(item);
    }


    private ArrayList<Fragment> agregarFragments(){
        ArrayList<Fragment> fragments = new ArrayList<>();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {

            if (extras.get("userId")== null){
                userId="0";}
            else{
                userId = (String) extras.get("userId");
            }

            fotoPerfil = (String) extras.get("fotoPerfil");
            nombreCuenta = (String) extras.get("nombreCuenta");
            //Toast.makeText(MainActivity.this, "Main Activity UserId: "+ userId, Toast.LENGTH_SHORT).show();

            if (!userId.equals("0")) {

                MascotasTimelineFragment mascotasTimelineFragment = new MascotasTimelineFragment();
                Bundle argumentsTL = new Bundle(2);
                argumentsTL.putString("userId", userId);
                argumentsTL.putString("nombreCuenta", nombreCuenta);
                mascotasTimelineFragment.setArguments(argumentsTL);
                fragments.add(mascotasTimelineFragment);

                PerfilFragment perfilFragment =  new PerfilFragment();
                Bundle arguments = new Bundle(3);

                arguments.putString("userId", userId);
                arguments.putString("fotoPerfil", fotoPerfil);
                arguments.putString("nombreCuenta", nombreCuenta);

                perfilFragment.setArguments(arguments);

                fragments.add(perfilFragment);
            }else{
                fragments.add(new MascotasTimelineFragment());
            }

        }else {
            fragments.add(new MascotasTimelineFragment());
        }


        return  fragments;
    }


    private void setUpViewPager(){
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), agregarFragments()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_home);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (!userId.equals("0")){
            tabLayout.getTabAt(1).setIcon(R.mipmap.ic_perfil);
            tabLayout.getTabAt(1).select();
        }



    }



}
