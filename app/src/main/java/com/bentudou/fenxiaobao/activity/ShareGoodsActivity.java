package com.bentudou.fenxiaobao.activity;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.fenxiaobao.R;
import com.bentudou.fenxiaobao.adapter.GoodsAdapter;
import com.bentudou.fenxiaobao.api.CallbackSupport;
import com.bentudou.fenxiaobao.api.FenXiaoBaoService;
import com.bentudou.fenxiaobao.model.GoodsDetailSession;
import com.bentudou.fenxiaobao.model.GoodsPictureData;
import com.bentudou.fenxiaobao.retrofit.RTHttpClient;
import com.bentudou.fenxiaobao.util.SharePreferencesUtils;
import com.bentudou.fenxiaobao.util.ToastUtils;
import com.bentudou.fenxiaobao.view.MyGridView;
import com.bentudou.fenxiaobao.view.ProgressHUD;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by lzz on 2016/11/30.
 */
public class ShareGoodsActivity extends Activity implements View.OnClickListener {
    private static int IMAGE_NAME = 1000;
    private TextView title_back,tv_share,tv_goods_content;
    private MyGridView mgv_img;
    private List<GoodsPictureData> goodsPictureDataList;
    public static List<String> pictureList;
    private GoodsAdapter goodsAdapter;
    private ProgressHUD progressHUD = null;
    private ArrayList<Uri> myList = null;
    private String result;
    private String goodsId;
    private String tittle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_goods);
        initView();
    }

    private void initView() {
        result = getIntent().getStringExtra("result");
        int n = result.indexOf("=");
        goodsId = result.substring(n+1);
        Log.d("goodsid", "------initView: "+goodsId);
        title_back = (TextView) findViewById(R.id.title_back);
        tv_share = (TextView) findViewById(R.id.tv_share);
        tv_goods_content = (TextView) findViewById(R.id.tv_goods_content);
        mgv_img = (MyGridView) findViewById(R.id.mgv_img);
        title_back.setOnClickListener(this);
        tv_share.setOnClickListener(this);
        initData();
        pictureList = new ArrayList<>();
    }

    private void initData() {
        progressHUD = ProgressHUD.show(this, "读取中..", true, null);
        final FenXiaoBaoService benTuDouService = RTHttpClient.create(FenXiaoBaoService.class);
        Call<GoodsDetailSession> call=benTuDouService.downloadGooodsInfo(goodsId,SharePreferencesUtils.getBtdToken(this));
        call.enqueue(new CallbackSupport<GoodsDetailSession>(progressHUD,this) {
            @Override
            public void onResponse(Call<GoodsDetailSession> call, Response<GoodsDetailSession> response) {
                super.onResponse(call,response);
                GoodsDetailSession goodsDetailSession=response.body();
                progressHUD.dismiss();
                if (goodsDetailSession.getStatus().equals("1")){
                    tittle = goodsDetailSession.getData().getGoodsTitle();
                    tv_goods_content.setText(tittle);
                    goodsPictureDataList = goodsDetailSession.getData().getGoodsImgList();
                    goodsAdapter = new GoodsAdapter(goodsPictureDataList,ShareGoodsActivity.this);
                    mgv_img.setAdapter(goodsAdapter);
                }else {
                    ToastUtils.showToastCenter(ShareGoodsActivity.this, goodsDetailSession.getErrorMessage());
                }
            }

            @Override
            public void onFailure(Call<GoodsDetailSession> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.tv_share:
                if (pictureList.size()<=0){
                    ToastUtils.showToastCenter(this,"请选择需要分享得图片");
                    return;
                }
                progressHUD = ProgressHUD.show(ShareGoodsActivity.this, "处理中..", true, null);
                getUriListForImages();
                break;
        }
    }

    private void sendMultiple(){
        Intent intent=new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, myList);
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, tittle);
        intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "分享到"));
    }

    /**
     * 设置需要分享的照片放入Uri类型的集合里
     */
    private void getUriListForImages() {
        myList = new ArrayList<>();
        final int picsize = pictureList.size();
        for (int i=0;i<picsize;i++){
            final int finalI = i;
            Log.d("数量", "-----: "+pictureList.get(i));
            Glide.with(ShareGoodsActivity.this).load(pictureList.get(i)).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (finalI ==picsize-1){
                        saveImageToSdCard(resource,true);
                    }else {
                        saveImageToSdCard(resource,false);
                    }

                }
            });
        }
    }

    public void saveImageToSdCard(Bitmap bitmap,boolean b) {
        File file = null;
        try {
            file = createStableImageFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        FileOutputStream outStream;
        try {
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
//           100 to keep full quality of the image
            outStream.flush();
            outStream.close();
            Log.d("成功", "-----saveImageToSdCard: ");
            myList.add(Uri.fromFile(file));
            if (b){
                progressHUD.dismiss();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                IMAGE_NAME = 1000;
//                DialogUtils.showShareToast(GoodsActivity.this,myList,"分享的文字内容");
                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tittle);
                Toast.makeText(ShareGoodsActivity.this, "描述已复制到粘贴板", Toast.LENGTH_LONG).show();
                sendMultiple();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File createStableImageFile() throws IOException {
        IMAGE_NAME++;
        String imageFileName = Integer.toString(IMAGE_NAME) + ".png";
        File storageDir =getExternalCacheDir();
        File image = new File(storageDir, imageFileName);
        return image;
    }
}
