package com.datcent.project.advice;

public class WrongException extends RuntimeException{

    /**
     * 详细信息，如果设置了detail，前台展示警告时会展示detail内容，否则就展示异常的类路径
     */
    private String detail;

    /**
     * 是否显示详细信息，默认不显示
     */
    private boolean showDetial = false;


    /**
     * 信息类型： 1警告 2错误
     */
    private int infType = 10000;



    public WrongException(String message, String detail, int infType) {
        super(message);
        this.detail = detail;
        this.showDetial =  false;
        this.setInfType(infType);
    }

    public WrongException(Throwable cause) {
        super(cause);
            this.showDetial =  false;
            this.setInfType(10000);
            this.setDetail(cause.getMessage());
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isShowDetial() {
        return showDetial;
    }

    public void setShowDetial(boolean showDetial) {
        this.showDetial = showDetial;
    }

    public int getInfType() {
        return infType;
    }

    public void setInfType(int infType) {
        this.infType = infType;
    }
}
