package com.bentudou.fenxiaobao.model;

/**
 * Created by lzz on 2016/11/18.
 */
public class GoodsDetailSession {
    private String errorMessage,status,errorCode;
    private GoodsDetailStat data;

    public GoodsDetailStat getData() {
        return data;
    }

    public void setData(GoodsDetailStat data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
