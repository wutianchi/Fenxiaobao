package com.bentudou.fenxiaobao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.fenxiaobao.FXBApplication;
import com.bentudou.fenxiaobao.R;
import com.bentudou.fenxiaobao.Zxing.CaptureActivity;
import com.bentudou.fenxiaobao.api.CallbackSupport;
import com.bentudou.fenxiaobao.api.FenXiaoBaoService;
import com.bentudou.fenxiaobao.config.Constant;
import com.bentudou.fenxiaobao.model.ImgUrl;
import com.bentudou.fenxiaobao.model.MainMessage;
import com.bentudou.fenxiaobao.retrofit.RTHttpClient;
import com.bentudou.fenxiaobao.util.SharePreferencesUtils;
import com.bentudou.fenxiaobao.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends Activity implements View.OnClickListener {
    private LinearLayout llt_to_sao,llt_to_set;
    private TextView tv_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        FXBApplication.getInstance().addActivity(MainActivity.this);
        initView();
        initImgUrl();
    }

    private void initImgUrl() {
        final FenXiaoBaoService benTuDouService = RTHttpClient.create(FenXiaoBaoService.class);
        Call<ImgUrl> call=benTuDouService.getImgUrl();
        call.enqueue(new CallbackSupport<ImgUrl>(this) {
            @Override
            public void onResponse(Call<ImgUrl> call, Response<ImgUrl> response) {
                super.onResponse(call,response);
                ImgUrl imgUrl=response.body();
                if (imgUrl.getStatus().equals("1")){
                    SharePreferencesUtils.updateImg_url(MainActivity.this,imgUrl.getData().getImgUrl());
                    Constant.URL_BASE_IMG = SharePreferencesUtils.getImg_url(MainActivity.this);
                }else {
                    ToastUtils.showToastCenter(MainActivity.this, imgUrl.getErrorMessage());
                }
            }

            @Override
            public void onFailure(Call<ImgUrl> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    private void initView() {
        llt_to_sao = (LinearLayout) findViewById(R.id.llt_to_sao);
        llt_to_set = (LinearLayout) findViewById(R.id.llt_to_set);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        llt_to_sao.setOnClickListener(this);
        llt_to_set.setOnClickListener(this);
        tv_phone.setText(SharePreferencesUtils.getUserPhone(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.llt_to_sao:
                startActivity(new Intent(this,CaptureActivity.class));
                break;
            case R.id.llt_to_set:
                startActivity(new Intent(this,SetActivity.class));
                break;
        }
    }

    //主线程中执行
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(MainMessage msg) {
        if (msg.getMessage().equals("close"))
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
