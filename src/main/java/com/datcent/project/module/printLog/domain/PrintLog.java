package com.datcent.project.module.printLog.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 单打印日志表 busi_print_log
 * 
 * @author datcent
 * @date 2019-02-15
 */
public class PrintLog extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 表单打印日志Id */
	private Integer printLogId;
	/** 线索Id */
	private String clueId;
	/** 点击用户名称 */
	private Integer userId;
	/** 表单名称 */
	private String formName;
	/** 点击时间(创建时间) */
	private Date clickTime;
	/** 案号*/
	private String clueAh;
	/** 操作人姓名 */
	private String userName;

	public void setPrintLogId(Integer printLogId) 
	{
		this.printLogId = printLogId;
	}

	public Integer getPrintLogId() 
	{
		return printLogId;
	}
	public void setClueId(String clueId) 
	{
		this.clueId = clueId;
	}

	public String getClueId() 
	{
		return clueId;
	}
	public void setUserId(Integer userId) 
	{
		this.userId = userId;
	}

	public Integer getUserId() 
	{
		return userId;
	}
	public void setFormName(String formName)
	{
		this.formName = formName;
	}

	public String getFormName()
	{
		return formName;
	}
	public void setClickTime(Date clickTime) 
	{
		this.clickTime = clickTime;
	}

	public Date getClickTime() 
	{
		return clickTime;
	}

	public String getClueAh() {
		return clueAh;
	}

	public void setClueAh(String clueAh) {
		this.clueAh = clueAh;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "PrintLog{" +
				"printLogId=" + printLogId +
				", clueId='" + clueId + '\'' +
				", userId=" + userId +
				", formName=" + formName +
				", clickTime=" + clickTime +
				", clueAh='" + clueAh + '\'' +
				", userName='" + userName + '\'' +
				'}';
	}
}
