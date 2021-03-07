package com.common.pojo;


/**
 * 自定义异常类
 * @author roger
 * @since  2020/11/2 14:23
 */
public class ParameterVerificationException extends RuntimeException {

    private String msg;

    public ParameterVerificationException(String msg) {
        super(msg);
    }

    public ParameterVerificationException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }




    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
