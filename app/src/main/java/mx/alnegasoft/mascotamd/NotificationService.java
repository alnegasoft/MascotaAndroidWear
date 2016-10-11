package mx.alnegasoft.mascotamd;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.ArrayList;

import mx.alnegasoft.mascotamd.pojo.Mascota;
import mx.alnegasoft.mascotamd.restApi.ConstantesRestApi;
import mx.alnegasoft.mascotamd.restApi.EndPointsApi;
import mx.alnegasoft.mascotamd.restApi.adapter.RestApiAdapter;
import mx.alnegasoft.mascotamd.restApi.model.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NEO on 02/09/2016.
 */
public class NotificationService extends FirebaseMessagingService {

    public static final String TAG = "FIREBASE";
    public static final int NOTIFICATION_ID = 001;
    String nombreUsuario    = "";
    String userId           = "";
    String fotoPerfil       = "";
    String idUsuarioLike       = "";
    String nombreUsuarioLike       = "";

    private Mascota usuarioLike;
    ArrayList<Mascota> usuarios;
    Intent i;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());


        //i = new Intent(this, MainActivity.class);
        Intent i = new Intent();
        i.setAction("LIKE_MASCOTA");
        Intent i_f = new Intent();
        i_f.setAction("FOLLOW");
        Intent i_uf = new Intent();
        i_uf.setAction("UNFOLLOW");
        Intent i_r = new Intent();
        i_r.setAction("RECIENTES");


        if (remoteMessage.getData().size() > 0) {


            Log.d(TAG, "Datos: " + remoteMessage.getData());

            usuarioLike =  new Mascota();

            userId = remoteMessage.getData().get("id_instagram");
            nombreUsuario = remoteMessage.getData().get("nombre_usuario");
            fotoPerfil= remoteMessage.getData().get("url_foto_perfil");

            i.putExtra("userId",userId);
            i.putExtra("nombreCuenta",nombreUsuario);
            i.putExtra("fotoPerfil",fotoPerfil);

            idUsuarioLike = remoteMessage.getData().get("id_usuario_like");
            nombreUsuarioLike = remoteMessage.getData().get("nombre_usuario_like");

            i_f.putExtra("idUsuarioLike",idUsuarioLike);
            i_f.putExtra("nombreUsuarioLike",nombreUsuarioLike);
            i_uf.putExtra("idUsuarioLike",idUsuarioLike);
            i_uf.putExtra("nombreUsuarioLike",nombreUsuarioLike);
            i_r.putExtra("idUsuarioLike",idUsuarioLike);
            i_r.putExtra("nombreUsuarioLike",nombreUsuarioLike);

//            i.putExtra("userId","3671713044");
//            i.putExtra("fotoPerfil","https://scontent.cdninstagram.com/t51.2885-19/s150x150/14033496_1668478746806697_667474009_a.jpg");
//            i.putExtra("nombreCuenta","mi_lucho_xd");
        }

        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_ONE_SHOT);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntentFollow = PendingIntent.getBroadcast(this, 0, i_f, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntentUnFollow = PendingIntent.getBroadcast(this, 0, i_uf, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntentRecientes = PendingIntent.getBroadcast(this, 0, i_r, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.mipmap.ic_perfil,
                        getString(R.string.texto_accion_home), pendingIntent)
                        .build();

        NotificationCompat.Action actionFollow =
                new NotificationCompat.Action.Builder(R.drawable.ic_follow,
                        getString(R.string.texto_accion_follow), pendingIntentFollow)
                        .build();

        NotificationCompat.Action actionUnFollow =
                new NotificationCompat.Action.Builder(R.drawable.ic_unfollow,
                        getString(R.string.texto_accion_unfollow), pendingIntentUnFollow)
                        .build();

        NotificationCompat.Action actionRecientes =
                new NotificationCompat.Action.Builder(R.drawable.ic_recientes,
                        getString(R.string.texto_accion_recientes), pendingIntentRecientes)
                        .build();



        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .setHintHideIcon(true)
                        .setBackground(BitmapFactory.decodeResource(getResources(),R.drawable.fondo_mascotas))
                        .setGravity(Gravity.CENTER_VERTICAL)
                ;


        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Notificación")
                .setContentText(remoteMessage.getNotification().getBody())
                .setSound(sonido)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .extend(wearableExtender.addAction(action))
                .extend(wearableExtender.addAction(actionFollow))
                .extend(wearableExtender.addAction(actionUnFollow))
                .extend(wearableExtender.addAction(actionRecientes))

                ;

        //NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        //notificationManager.notify(0, notification.build());
        notificationManager.notify(NOTIFICATION_ID, notification.build());


    }


}
