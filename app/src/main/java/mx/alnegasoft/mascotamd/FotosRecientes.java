package mx.alnegasoft.mascotamd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by ABITA on 11/10/2016.
 */
public class FotosRecientes extends BroadcastReceiver {
    public String idUsuarioLike="0";
    public String nombreUsuarioLike="0";

    @Override
    public void onReceive(Context context, Intent intent) {
        String ACTION_KEY = "RECIENTES";
        String action = intent.getAction();

        if(ACTION_KEY.equals(action)){

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

                Intent intentRecientes = new Intent(context ,UsuarioLikeActivity.class);
                intentRecientes.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentRecientes.putExtra("idUsuarioLike",idUsuarioLike);
                intentRecientes.putExtra("nombreUsuarioLike",nombreUsuarioLike);
                context.startActivity(intentRecientes);

                Toast.makeText(context, "Fotos Recientes de: " + nombreUsuarioLike, Toast.LENGTH_SHORT).show();
            }


        }
    }
}
