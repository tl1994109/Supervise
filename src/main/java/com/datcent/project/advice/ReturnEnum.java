package com.datcent.project.advice;

public enum ReturnEnum {

    SUCCESS_RES(200, "调用成功"),
    WARN_RES(400, "业务拦截"),
    ERROR_RES(500, "调用失败");


    private int code;
    private String message;

    private ReturnEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return message;
    }
    public void setMsg(String message) {
        this.message = message;
    }
}
