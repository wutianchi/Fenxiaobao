package com.bentudou.fenxiaobao.api;

import com.bentudou.fenxiaobao.model.BtnToken;
import com.bentudou.fenxiaobao.model.GoodsDetailSession;
import com.bentudou.fenxiaobao.model.ImgUrl;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lzz on 2016/11/28.
 */
public interface FenXiaoBaoService {
    /**
     * 用户登录
     * @param userName
     * @param password
     */
    @GET("Login/loginAccount.htm")
    Call<BtnToken> userLogin(@Query("userName") String userName, @Query("password") String password);

    /**
     * 获取图片根地址
     */
    @GET("Index/getImgUrl.htm")
    Call<ImgUrl> getImgUrl();

    /**
     * 获取商品详情接口
     * @param BtdToken
     * @param goodsId
     */
    @GET("Goods/downloadGooodsInfo.htm")
    Call<GoodsDetailSession> downloadGooodsInfo(@Query("goodsId") String goodsId,
                                                @Query("BtdToken") String BtdToken);

}
