package com.common.pojo;

/**
  * 自定义异常类，适用于swagger的返回值说明
  *
  * @author: qinjie
 **/
public class Result<T> {
    /**
     * 状态码
     */
    private int code;
    /**
     * 信息
     */
    private String msg;
    private T datas;
    public static Result ok(){
        return new Result<>(ExceptionEnum._200,null);
    }
    public static Result ok(ExceptionEnum exceptionEnum){
        return new Result<>(exceptionEnum,null);
    }

    public static<T> Result<T> ok(T object){
        return new Result<T>(ExceptionEnum._200, object);
    }
    public static<T> Result<T> ok(ExceptionEnum exceptionEnum,T object){
        return new Result<>(exceptionEnum, object);
    }

    public static Result error(ExceptionEnum exceptionEnum){
        return new Result<>(exceptionEnum,null);
    }

    public static<T> Result<T> error(ExceptionEnum exceptionEnum,T object){
        return new Result<T>(exceptionEnum,object);
    }

    private Result(ExceptionEnum exceptionEnum, T datas) {
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMessage();
        this.datas = datas;
    }

    public Result() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }
}
