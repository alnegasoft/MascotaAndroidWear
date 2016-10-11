package mx.alnegasoft.mascotamd.restApi;

import android.widget.TextView;

import mx.alnegasoft.mascotamd.R;

/**
 * Created by NEO on 11/08/2016.
 */
public class ConstantesRestApi {

    public static final String VERSION                      = "/v1/";
    public static final String ROOT_URL                     = "https://api.instagram.com" + VERSION;
    public static final String ACCESS_TOKEN                 = "3648605337.bef215d.5a54ae5f797a4ec2b2b2fef2df1f5832";
    public static final String KEY_ACCESS_TOKEN             = "&access_token=";
    public static final String KEY_USERS_SEARCH             = "users/search?";


    public static final String KEY_ACCESS_TOKEN_MEDIA_USER  = "access_token=";
    public static final String KEY_GET_RECENT_MEDIA_USER_ID = "users/{userid}/media/recent/?";
    //public static final String KEY_GET_RECENT_MEDIA_USER_ID = "users/3648605337/media/recent/?";
    public static final String URL_GET_RECENT_MEDIA_USER_ID = KEY_GET_RECENT_MEDIA_USER_ID + KEY_ACCESS_TOKEN_MEDIA_USER + ACCESS_TOKEN;


    public static final String KEY_GET_USERS_TIMELINE = "users/self/follows?";
    public static final String URL_GET_USERS_TIMELINE = KEY_GET_USERS_TIMELINE + KEY_ACCESS_TOKEN + ACCESS_TOKEN;

    //https://api.instagram.com/v1/users/3671713044/relationship?access_token=3648605337.bef215d.5a54ae5f797a4ec2b2b2fef2df1f5832

    public static final String KEY_POST_FOLLOW_UNFOLLOW = "users/{userid}/relationship?";
    public static final String URL_POST_FOLLOW_UNFOLLOW = KEY_POST_FOLLOW_UNFOLLOW + KEY_ACCESS_TOKEN + ACCESS_TOKEN;

    //https://api.instagram.com/v1/users/self/follows?access_token=3648605337.bef215d.5a54ae5f797a4ec2b2b2fef2df1f5832

    //https://api.instagram.com/v1/users/3648605337/media/recent/?access_token=3648605337.bef215d.5a54ae5f797a4ec2b2b2fef2df1f5832
    //https://api.instagram.com/v1/users/{user-id}/media/recent/?access_token=ACCESS-TOKEN
    //public static final String KEY_GET_RECENT_MEDIA_USER = "users/self/media/recent/";

    //public static final String URL_GET_USER_ID = KEY_USERS_SEARCH + R.string.user_id + KEY_ACCESS_TOKEN + ACCESS_TOKEN;
    //public static final String URL_GET_USER_ID = KEY_USERS_SEARCH + "alnegasoft" + KEY_ACCESS_TOKEN + ACCESS_TOKEN;
    //public static final String URL_GET_USER_ID = KEY_USERS_SEARCH + KEY_ACCESS_TOKEN + ACCESS_TOKEN;
    //https://api.instagram.com/v1/users/search?q=alnegasoft&access_token=3648605337.9793ffe.823c3055a691442f94e08157e7c6fa34


    public static final String ROOT_URL_HEROKU = "https://infinite-scrubland-18806.herokuapp.com/";
    public static final String KEY_POST_ID_TOKEN = "token-device/";

    public static final String KEY_LIKE = "likes/{id}/{id_instagram}/{nombre_usuario}/{url_foto_perfil}/{id_usuario_like}/{nombre_usuario_like}";


}
