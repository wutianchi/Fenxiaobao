package com.bentudou.fenxiaobao.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.bentudou.fenxiaobao.R;

import java.util.ArrayList;


/**
 * Created by 天池 on 2015/8/18.
 */
public class DialogUtils {
 //分享展示框
    public static void showShareToast(final Activity activity, final ArrayList<Uri> uriList, final String shareContent) {
        View layout = activity.getLayoutInflater().inflate(R.layout.dialog_share_show,
                null);
        LinearLayout llt_marth_size = (LinearLayout) layout.findViewById(R.id.llt_marth_size);
        ViewGroup.LayoutParams params=llt_marth_size.getLayoutParams();
        params.width = DensityUtil.pxWith(activity);
        llt_marth_size.setLayoutParams(params);
        LinearLayout llt_share_dialog_qq = (LinearLayout) layout.findViewById(R.id.llt_share_dialog_qq);
        LinearLayout llt_share_dialog_weixin = (LinearLayout) layout.findViewById(R.id.llt_share_dialog_weixin);
        LinearLayout llt_share_dialog_pengyouquan = (LinearLayout) layout.findViewById(R.id.llt_share_dialog_pengyouquan);
        LinearLayout llt_share_dialog_weibo = (LinearLayout) layout.findViewById(R.id.llt_share_dialog_weibo);
        LinearLayout llt_share_dialog_cancel = (LinearLayout) layout.findViewById(R.id.llt_share_dialog_cancel);
        final Dialog dialog = new AlertDialog.Builder(activity).create();
        dialog.show();
        WindowManager.LayoutParams lp=dialog.getWindow().getAttributes();
        lp.alpha=1.0f;
        lp.dimAmount=0.8f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(layout);
        llt_share_dialog_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(shareContent);
                Toast.makeText(activity, "描述已复制到粘贴板", Toast.LENGTH_LONG).show();
                shareQQ(activity,uriList,shareContent);
                dialog.dismiss();
            }
        });
        llt_share_dialog_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(shareContent);
                Toast.makeText(activity, "描述已复制到粘贴板", Toast.LENGTH_LONG).show();
                shareWeixin(activity,uriList,shareContent);
                dialog.dismiss();
            }
        });
        llt_share_dialog_pengyouquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(shareContent);
                Toast.makeText(activity, "描述已复制到粘贴板", Toast.LENGTH_LONG).show();
                sharePengyou(activity,uriList,shareContent);
                dialog.dismiss();
            }
        });
        llt_share_dialog_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWeibo(activity,uriList,shareContent);
                dialog.dismiss();
            }
        });
        llt_share_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private static void shareQQ(Activity activity, ArrayList<Uri> uriList, String content){
        Intent intent=new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity"));
        activity.startActivity(intent);
    }
    private static void shareWeixin(Activity activity,ArrayList<Uri> uriList,String content){
        Intent intent=new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI"));
        activity.startActivity(intent);
    }
    private static void sharePengyou(Activity activity,ArrayList<Uri> uriList,String content){
        Intent intent=new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"));
        activity.startActivity(intent);
    }
    private static void shareWeibo(Activity activity,ArrayList<Uri> uriList,String content){
        Intent intent=new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName("com.sina.weibo", "com.sina.weibo.EditActivity");
//        intent.setComponent(new ComponentName("com.sina.weibo", "com.sina.weibo.EditActivity"));
        activity.startActivity(intent);

    }
    private static void sendMultiple(Activity activity,ArrayList<Uri> uriList,String content){
        Intent intent=new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(intent, "分享到"));
    }

}
