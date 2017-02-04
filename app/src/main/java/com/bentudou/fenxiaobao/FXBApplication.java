package com.bentudou.fenxiaobao;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.bentudou.fenxiaobao.config.Constant;
import com.bentudou.fenxiaobao.retrofit.RTHttpClient;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lzz on 2016/9/19.
 */
public class FXBApplication extends Application {
    /**
     * 全局唯一实例
     */
    private static FXBApplication instance;
    /**
     * Activity列表
     */
    private List<Activity> activityList = new LinkedList<Activity>();
    @Override
    public void onCreate() {
        super.onCreate();
        RTHttpClient.init(Constant.URL_BASE_TEST, this);
    }

    /**
     * 获取类实例对象
     * @return    MyActivityManager
     */
    public static FXBApplication getInstance(){
        if (null== instance){
            instance = new FXBApplication();
        }
        return instance;
    }
    /**
     * 保存Activity到现有列表中
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 关闭保存的Activity
     */
    public void exit() {
        if(activityList!=null)
        {
            Activity activity;

            for (int i=0; i<activityList.size(); i++) {
                activity = activityList.get(i);
                if(activity!=null)
                {
                    if(!activity.isFinishing())
                    {
                        activity.finish();
                    }

                    activity = null;
                }

                activityList.remove(i);
            }
        }
    }

}
