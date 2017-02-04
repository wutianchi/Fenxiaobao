package com.bentudou.fenxiaobao.model;

/**
 * Created by lzz on 2016/11/28.
 */
public class BtnToken {
    private String errorMessage,status,errorCode;
    private BackToken data;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public BackToken getData() {
        return data;
    }

    public void setData(BackToken data) {
        this.data = data;
    }
}
