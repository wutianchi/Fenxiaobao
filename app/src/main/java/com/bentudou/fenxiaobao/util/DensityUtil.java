package com.bentudou.fenxiaobao.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.WindowManager;

import com.bentudou.fenxiaobao.config.Constant;


public class DensityUtil {
	  

    public static int dip2px(float dpValue,Context context)
    {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue,Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 获取手机的分辨率宽
     */
    public static int pxWith(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }
    /**
     * 获取手机的分辨率高
     */
    public static int pxHight(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }


}  