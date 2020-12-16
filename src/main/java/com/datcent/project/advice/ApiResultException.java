/*
 * ApiResultException.java
 * 版权所有： 2007 - 2012
 * 保留所有权利，未经允许不得以任何形式使用。
 */
package com.datcent.project.advice;


/**
 * 业务系统的异常类,主要用于封装业务逻辑错,给客户端提示 <p>
 * 创建日期：2012-4-17<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author
 * @version 1.0
 */
public class ApiResultException extends RuntimeException {
	/**
	 * 序列化编码
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * 信息类型： 1警告 2错误
	 */
	private int infType = ReturnEnum.WARN_RES.getCode();
	/**
	 * 是否显示详细信息，默认不显示
	 */
	private boolean showDetial = false;
	/**
	 * 错误编码
	 */
	private String errorCode;
	/**
	 * 详细信息，如果设置了detail，前台展示警告时会展示detail内容，否则就展示异常的类路径
	 */
	private String detail;
		
    /**
     * 构造法
     */
    public ApiResultException() {
    	super();
    }
    
    /**
     * 构造法
     * @param message 异常信息
     */
    public ApiResultException(String message) {
    	super(message);
    	this.setInfType(ReturnEnum.WARN_RES.getCode());
    }
    

    /**
     * 构造法
     * @param message 异常信息
     * @param detail 异常详细信息
     */
    public ApiResultException(String message,String detail) {
    	super(message);
    	this.detail = detail;
    	this.setInfType(ReturnEnum.WARN_RES.getCode());
    	this.showDetial =  false;
    }
    
    /**
     * 构造函数
     * @param cause 异常信息
     */
    public ApiResultException(Throwable cause) {
    	super(cause);
    	this.setInfType(ReturnEnum.ERROR_RES.getCode());
    }
    /**
     * 构造函数
     * @param message 异常信息描述
     * @param cause  异常信息
     */
    public ApiResultException(String message, Throwable cause) {
    	super(message,cause);
    	this.setInfType(ReturnEnum.ERROR_RES.getCode());

    }
    /**
     * 构造函数
     * @param message  异常信息描述
     * @param infType  信息类型
     */
    public ApiResultException(String message, String detail, int infType) {
    	super(message);
    	this.detail = detail;
    	this.showDetial =  false;
    	this.setInfType(infType);
    }
    
    /**
     * 构造函数
     * @param message  异常信息描述
     * @param infType  信息类型
     */
    public ApiResultException(String message,int infType) {
    	super(message);
    	this.setInfType(infType);
    }
    
    /**
     * 构造函数
     * @param cause 异常信息
     * @param infType 信息类型
     */
    public ApiResultException(Throwable cause,int infType) {
    	super(cause);
    	this.setInfType(infType);
    }
    
    /**
     * 构造函数
     * @param message 异常信息描述
     * @param cause 异常信息
     * @param infType 信息类型
     */
    public ApiResultException(String message, Throwable cause, int infType) {
    	super(message,cause);
    	this.setInfType(infType);
    	
    }

    /**
     * 错误编码 
     * @return 错误编码
     */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 
	 * 错误编码
	 * @param errorCode 错误编码
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 
	 * 方法描述  信息类型
	 * @return 信息类型
	 */
	public int getInfType() {
		return infType;
	}

	/**
	 * 信息类型
	 * @param infType 信息类型
	 */
	public void setInfType(int infType) {
		this.infType = infType;
	}

	/**
	 * 
	 * 方法描述 是否显示详细信息
	 * @return 是否显示详细信息
	 */
	public boolean isShowDetial() {
		return showDetial;
	}

	/**
	 * 
	 * 方法描述 是否显示详细信息
	 * @param showdetial 是否显示详细信息
	 * @return 是否显示详细信息
	 */
	public ApiResultException setShowDetial(boolean showdetial) {
		this.showDetial = showdetial;
		return this;
	}

	/**
	 * 详细信息
	 * @return 详细信息
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * 详细信息
	 * @param detail 详细信息
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}     
	
}
