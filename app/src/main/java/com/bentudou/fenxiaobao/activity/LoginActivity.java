package com.bentudou.fenxiaobao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bentudou.fenxiaobao.R;
import com.bentudou.fenxiaobao.api.CallbackSupport;
import com.bentudou.fenxiaobao.api.FenXiaoBaoService;
import com.bentudou.fenxiaobao.model.BtnToken;
import com.bentudou.fenxiaobao.retrofit.RTHttpClient;
import com.bentudou.fenxiaobao.util.SharePreferencesUtils;
import com.bentudou.fenxiaobao.util.ToastUtils;
import com.bentudou.fenxiaobao.view.ProgressHUD;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by lzz on 2016/9/19.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    private LinearLayout root_layout;
    private EditText et_login_name,etLoginPassword;
    private ImageView iv_icon;
    private Button btnLogin;
    ProgressHUD progressHUD = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (!SharePreferencesUtils.getBtdToken(this).isEmpty()){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        initView();
    }

    private void initView() {
        root_layout = (LinearLayout) findViewById(R.id.root_layout);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        et_login_name = (EditText) findViewById(R.id.et_login_name);
        etLoginPassword = (EditText) findViewById(R.id.et_login_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        iv_icon.setFocusable(true);
        iv_icon.setFocusableInTouchMode(true);
        iv_icon.requestFocus();
        et_login_name.setOnClickListener(this);
        etLoginPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        root_layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff= root_layout.getRootView().getHeight() - root_layout.getHeight();
                if(heightDiff>200){
                    Log.d("变化大于100", "----onGlobalLayout: ");
//                    Animation mHiddenAction = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
//                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                    mHiddenAction.setDuration(50);
//                    iv_icon.startAnimation(mHiddenAction);
                    iv_icon.setVisibility(View.GONE);
                }else {
                    Animation mShowAction = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    mShowAction.setDuration(300);
                    iv_icon.startAnimation(mShowAction);
                    iv_icon.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.et_login_name:
                break;
            case R.id.et_login_password:
                break;
            case R.id.btn_login:
                if (et_login_name.length()==0){
                    ToastUtils.showToastCenter(this, "请输入账号!");
                }else if (etLoginPassword.length()==0){
                    ToastUtils.showToastCenter(this,"请输入密码!");
                }else {
                    login();
                }
                break;
        }
    }

    //登录接口
    private void login() {
        progressHUD = ProgressHUD.show(this, "登录中", true, null);
        final FenXiaoBaoService benTuDouService = RTHttpClient.create(FenXiaoBaoService.class);
        Call<BtnToken> call=benTuDouService.userLogin(et_login_name.getText().toString(), etLoginPassword.getText().toString());
        call.enqueue(new CallbackSupport<BtnToken>(progressHUD,LoginActivity.this) {
            @Override
            public void onResponse(Call<BtnToken> call, Response<BtnToken> response) {
                super.onResponse(call,response);
                BtnToken btnToken=response.body();
                progressHUD.dismiss();
                if (btnToken.getStatus().equals("1")){
                    SharePreferencesUtils.saveBtdToken(LoginActivity.this,btnToken.getData().getBtdToken(),et_login_name.getText().toString());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else {
                    ToastUtils.showToastCenter(LoginActivity.this, btnToken.getErrorMessage());
                }
            }

            @Override
            public void onFailure(Call<BtnToken> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

}
