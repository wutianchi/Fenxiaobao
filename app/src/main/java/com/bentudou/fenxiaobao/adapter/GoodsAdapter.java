package com.bentudou.fenxiaobao.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bentudou.fenxiaobao.R;
import com.bentudou.fenxiaobao.activity.ShareGoodsActivity;
import com.bentudou.fenxiaobao.config.Constant;
import com.bentudou.fenxiaobao.model.GoodsPictureData;
import com.bentudou.fenxiaobao.util.DensityUtil;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 *Created by lzz on 2016/3/2.
 * 成长记录适配器
 */
public class GoodsAdapter extends BaseAdapter {
    List<GoodsPictureData> list;
    Context context;
    public GoodsAdapter(List<GoodsPictureData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list==null) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (list==null) {
            return null;
        } else {
            return list.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ClassHolder classHolder;
        if(convertView==null){
            classHolder = new ClassHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_goods,null);
            classHolder.iv_goods = (ImageView) convertView.findViewById(R.id.iv_goods);
            classHolder.cbx_cart_cang_all_select = (CheckBox) convertView.findViewById(R.id.cbx_cart_cang_all_select);
            ViewGroup.LayoutParams params=classHolder.iv_goods.getLayoutParams();
            params.width=(DensityUtil.pxWith(context)-20)/3;
            params.height=(DensityUtil.pxWith(context)-20)/3;
            classHolder.iv_goods.setLayoutParams(params);
            convertView.setTag(classHolder);
        }else {
            classHolder = (ClassHolder) convertView.getTag();
        }
        Log.d("url", "----getView: "+Constant.URL_BASE_IMG+list.get(position).getGoodsImgPath()+Constant.IMG_200);
        Glide.with(context).load(Constant.URL_BASE_IMG+list.get(position).getGoodsImgPath()+Constant.IMG_200).into(classHolder.iv_goods);
        classHolder.cbx_cart_cang_all_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    ShareGoodsActivity.pictureList.add(Constant.URL_BASE_IMG+list.get(position).getGoodsImgPath());
                }else {
                    for (int i=0;i<ShareGoodsActivity.pictureList.size();i++){
                        if (ShareGoodsActivity.pictureList.get(i).equals(Constant.URL_BASE_IMG+list.get(position).getGoodsImgPath())){
                            ShareGoodsActivity.pictureList.remove(i);
                        }
                    }
                }
            }
        });
        return convertView;
    }
    class ClassHolder{
        ImageView iv_goods;
        CheckBox cbx_cart_cang_all_select;
    }

}
