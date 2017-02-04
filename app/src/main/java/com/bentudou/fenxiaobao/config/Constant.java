package com.bentudou.fenxiaobao.config;

import android.graphics.Bitmap;

/**
 * Created by lzz on 2016/9/19.
 * 常量类
 */
public class Constant {
    public static final String IMG_200 = ".w200";//根据图片大小给的每次下载字节大小
    public static final String IMG_400 = ".w400";//根据图片大小给的每次下载字节大小
    public static int push_value = 1;//首页标志
    public static Bitmap bitmap=null;//首页标志
    //服务器端
    public static /* final */ String URL_BASE_TEST = "http://cross.bentudou.com/";// 线上服务器地址
//    public static /* final */ String URL_BASE_TEST = "http://cross.bentudou.dev.costrun.cn/";// 测试入口地址
//    public static /* final */ String URL_BASE_TEST = "http://192.168.20.62:8080/www.westwingexpress.com/";// 志鹏开发入口地址
    public static /* final */ String URL_BASE_IMG = "http://img.westwinglife.cn";// 图片地址
}
