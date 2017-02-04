package com.bentudou.fenxiaobao.model;

import java.util.List;

/**
 * Created by lzz on 2016/11/18.
 */
public class GoodsDetailStat {
    private String goodsTitle;
    private List<GoodsPictureData> goodsImgList;

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public List<GoodsPictureData> getGoodsImgList() {
        return goodsImgList;
    }

    public void setGoodsImgList(List<GoodsPictureData> goodsImgList) {
        this.goodsImgList = goodsImgList;
    }
}
