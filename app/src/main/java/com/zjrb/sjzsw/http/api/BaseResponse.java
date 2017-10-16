package com.zjrb.sjzsw.http.api;

/**
 * 服务器返回结构体基类
 * 注：可根据实际接口返回结构体配置基类
 * Created by jinzifu on 2017/7/11.
 */
public class BaseResponse<T> {
    private String error_code;
    private String reason;
    private T result;

    public String getCode() {
        return error_code;
    }

    public void setCode(String code) {
        this.error_code = code;
    }

    public String getMsg() {
        return reason;
    }

    public void setMsg(String msg) {
        this.reason = msg;
    }

    public T getDatas() {
        return result;
    }

    public void setDatas(T datas) {
        this.result = datas;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("error_code=" + error_code + " reason=" + reason);
        if (null != result) {
            sb.append(" result:" + result.toString());
        }
        return sb.toString();
    }
}