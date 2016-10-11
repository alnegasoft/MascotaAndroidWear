package mx.alnegasoft.mascotamd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by NEO on 07/10/2016.
 */
public class LikeMascota extends BroadcastReceiver {

    public String userId="0";
    public String fotoPerfil="0";
    public String nombreCuenta="0";
    @Override
    public void onReceive(Context context, Intent intent) {
        String ACTION_KEY = "LIKE_MASCOTA";
        String action = intent.getAction();

        if(ACTION_KEY.equals(action)){
            //toqueAnimal();

            Bundle extras = intent.getExtras();
            if (extras.get("userId")== null){
                userId="0";}
            else{
                userId = (String) extras.get("userId");
            }

            fotoPerfil = (String) extras.get("fotoPerfil");
            nombreCuenta = (String) extras.get("nombreCuenta");

            Intent intentMain = new Intent(context ,MainActivity.class);
            intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentMain.putExtra("userId",userId);
            intentMain.putExtra("nombreCuenta",nombreCuenta);
            intentMain.putExtra("fotoPerfil",fotoPerfil);
            context.startActivity(intentMain);

            Toast.makeText(context, "Bienvenido a Tu Perfil", Toast.LENGTH_SHORT).show();
        }

    }


}
