
package com.example.android.myapplication.base;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

public final class Constant {

    public final static String SSL_CER = "/assets/tomcat.cer";


    // 安全服务器地址
    public final static String SECURE_SERVER_URL = "https://www.baidu";
    //高德KEY

    public final static String AMAP_KEY = "c78b1f9a26252879037e2f0ba359f34a";


    public static int TIME_OUT = 60000;
    /**
     * SharedPreferences保存用户基本信息
     */
    public static final String SAVE = "save";
    /**
     * 城市名
     */
    public static final String CITY = "city";

    public static final String REMOTE_LOGIN = "remoteLogin";

    public static final String USER_INFO_TEL = "tel";

    public static final String USER_INFO_NAME = "name";

    public static final String USER_INFO_User_ID = "userId";

    public static final String USER_INFO_PASSWORD = "password";

    public static final String USER_FIRST_LOGIN = "first_login";


}
