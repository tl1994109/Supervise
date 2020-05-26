package com.datcent.project.module.noticeClick.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 公告点击记录表 sys_notice_click
 * 
 * @author datcent
 * @date 2018-11-05
 */
public class NoticeClick extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 公告点击量Id */
	private Integer noticeClickrateId;
	/** 公告Id */
	private String noticeId;
	/** 点击用户Id */
	private Integer userId;
	/** 公告Name */
	private String noticeName;
	/** 点击用户名称 */
	private String userName;
	/** 点击时间(创建时间) */
	private Date clickTime;

	public void setNoticeClickrateId(Integer noticeClickrateId) 
	{
		this.noticeClickrateId = noticeClickrateId;
	}

	public Integer getNoticeClickrateId() 
	{
		return noticeClickrateId;
	}
	public void setNoticeId(String noticeId) 
	{
		this.noticeId = noticeId;
	}

	public String getNoticeId() 
	{
		return noticeId;
	}
	public void setUserId(Integer userId) 
	{
		this.userId = userId;
	}

	public Integer getUserId() 
	{
		return userId;
	}
	public void setClickTime(Date clickTime) 
	{
		this.clickTime = clickTime;
	}

	public Date getClickTime() 
	{
		return clickTime;
	}

    public String getNoticeName() {
		return noticeName;
	}

	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "NoticeClick [noticeClickrateId=" + noticeClickrateId
				+ ", noticeId=" + noticeId + ", userId=" + userId
				+ ", noticeName=" + noticeName + ", userName=" + userName
				+ ", clickTime=" + clickTime + "]";
	}

	
}
