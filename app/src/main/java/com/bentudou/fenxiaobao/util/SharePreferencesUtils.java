package com.bentudou.fenxiaobao.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lzz on 2016/9/19.
 */
public class SharePreferencesUtils {
    //保存token
    public static void saveBtdToken(Context context, String btd_token,String user_phone){
        SharedPreferences sp = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("btd_token", btd_token);
        editor.putString("user_phone", user_phone);
        editor.commit();

    }
    //获取token
    public static String getBtdToken(Context context){

        String btd_token = null;
        SharedPreferences sp = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        btd_token = sp.getString("btd_token", "");
        return btd_token;
    }
  //获取电话
    public static String getUserPhone(Context context){

        String user_phone = null;
        SharedPreferences sp = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        user_phone = sp.getString("user_phone", "");
        return user_phone;
    }

    public static void updateImg_url(Context context,String rate){
        SharedPreferences sp = context.getSharedPreferences("img_update", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("img_url",rate);
        editor.commit();
    }
    public static String getImg_url(Context context){
        SharedPreferences sp = context.getSharedPreferences("img_update", Context.MODE_PRIVATE);
        String dateRate =sp.getString("img_url","http://img.westwinglife.cn");
        return dateRate;
    }
}
