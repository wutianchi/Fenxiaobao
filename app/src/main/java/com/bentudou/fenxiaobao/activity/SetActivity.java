package com.bentudou.fenxiaobao.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bentudou.fenxiaobao.FXBApplication;
import com.bentudou.fenxiaobao.R;
import com.bentudou.fenxiaobao.model.MainMessage;
import com.bentudou.fenxiaobao.util.DataCleanManager;
import com.bentudou.fenxiaobao.util.SharePreferencesUtils;
import com.bentudou.fenxiaobao.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lzz on 2016/11/29.
 */
public class SetActivity extends Activity implements View.OnClickListener {
    private LinearLayout llt_clear_size;
    private TextView title_back,tv_clear_size,tv_out_tudou;
    private String size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initView();
    }

    private void initView() {
        title_back = (TextView) findViewById(R.id.title_back);
        tv_out_tudou = (TextView) findViewById(R.id.tv_out_tudou);
        llt_clear_size = (LinearLayout) findViewById(R.id.llt_clear_size);
        tv_clear_size = (TextView) findViewById(R.id.tv_clear_size);
        title_back.setOnClickListener(this);
        tv_out_tudou.setOnClickListener(this);
        llt_clear_size.setOnClickListener(this);
        try {
            size = DataCleanManager.getTotalCacheSize(this);
            tv_clear_size.setText(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;//返回
            case R.id.tv_out_tudou:
                EventBus.getDefault().post(new MainMessage("close"));
//                FXBApplication.getInstance().exit();
                SharePreferencesUtils.saveBtdToken(this,"","");
                ToastUtils.showToastCenter(this,"账号已退出");
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;//退出账号
            case R.id.llt_clear_size:
                showClearHuancun();
                break;//清除缓存
        }
    }

    private void showClearHuancun() {
        View layout =getLayoutInflater().inflate(R.layout.dialog_huancun_clear,
                null);
        TextView tv_connect = (TextView) layout.findViewById(R.id.tv_connect);
        tv_connect.setText("确定清除本地缓存吗?");
        TextView cancelGo = (TextView) layout.findViewById(R.id.cancel_go);
        TextView sureNoGo = (TextView) layout.findViewById(R.id.sure_go);
        final Dialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setContentView(layout);
        /**监听对话框里面的button点击事件*/
        sureNoGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.clearAllCache(SetActivity.this);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ToastUtils.showToastCenter(SetActivity.this,"共清除"+size+"缓存");
                tv_clear_size.setText("0K");
                dialog.dismiss();
            }
        });
        cancelGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
