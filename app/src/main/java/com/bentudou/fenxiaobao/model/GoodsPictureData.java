package com.bentudou.fenxiaobao.model;

/**
 * Created by lzz on 2016/11/18.
 */
public class GoodsPictureData {
    /**
     *商品id
     **/
    private int goodsId;
    /**
     *商品图片id
     **/
    private int goodsImgId;
    /**
     *商品详情图片地址
     **/
    private String goodsImgPath;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getGoodsImgId() {
        return goodsImgId;
    }

    public void setGoodsImgId(int goodsImgId) {
        this.goodsImgId = goodsImgId;
    }

    public String getGoodsImgPath() {
        return goodsImgPath;
    }

    public void setGoodsImgPath(String goodsImgPath) {
        this.goodsImgPath = goodsImgPath;
    }
}

