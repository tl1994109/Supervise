/*
 * ExceptionUtil.java
 * 版权所有： 2007 - 2012
 * 保留所有权利，未经允许不得以任何形式使用。
 */
package com.datcent.project.advice;

import javax.servlet.ServletException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * 异常工具类 <p>
 * 创建日期：2013-7-9<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author zhou
 * @version 1.0
 */
public class ExceptionUtil {
	/**
	 * 构造方法
	 */
	private ExceptionUtil(){
		
	};
	/**
	 * 返回一个错误信息的执行路径信息
	 * @author Fu Qiming
	 * @param err 
	 * @return String
	 */
	public static String getExceptionStackTrace(Throwable err){
		StringWriter sw=new StringWriter();
		PrintWriter pw=new PrintWriter(sw);
		err.printStackTrace(pw);
		return sw.toString();
	}
	
	/**
	 * 
	 * 获得原始错误信息
	 * @param th 
	 * @return Throwable
	 */
	public static Throwable getOrigErr(Throwable th){
		Throwable ex=th;
		while (ex.getCause()!=null) ex=ex.getCause();
		if (ex instanceof ServletException){
			Throwable exTmp=((ServletException)ex).getRootCause();
			if (exTmp!=null) {
				ex=exTmp;
				while (ex.getCause()!=null) ex=ex.getCause();
			}
		}
		return ex;
	}	
}
