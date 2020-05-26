package com.datcent.project.system.notice.domain;

import java.util.Date;

import com.datcent.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 公告表 sys_notice
 * 
 * @author datcent
 */
public class Notice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公告ID */
    private String noticeId;
    /** 公告标题 */
    private String noticeTitle;
    /** 公告类型（1通知 2公告） */
    private String noticeType;
    /** 公告内容 */
    private String noticeContent;
    /** 公告状态（0正常 1关闭） */
    private String status;
    /** 公告周期 */
    private String noticeCycle;
    /** 发布人 */
    private String releaseBy;
    /** 删除标识（0正常 1关闭） */
    private String deleteFlag;
    /** 删除人 */
    private String deleteBy;
    /** 删除时间 */
    private Date deleteTime;
    /** 发布审核通过时间 */
    private Date launchTime;
    /**公告审核状态 0通过 1 不通过 2 创建未发布 3待审核*/
    private String processStatus;
    /**当前用户公告点击*/
    private String noticeClickId;
    /*登陆昵称*/
    private String loginName;
    /*流程ID*/
    private String pid;
    /*置顶时间*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String topDate;

    private String username;

    private String noticePhoto;

    //0.手填 1.上传PDF
    private String fillWay;

    public String getFillWay() {
        return fillWay;
    }

    public void setFillWay(String fillWay) {
        this.fillWay = fillWay;
    }

    public String getNoticePhoto() {
        return noticePhoto;
    }

    public void setNoticePhoto(String noticePhoto) {
        this.noticePhoto = noticePhoto;
    }

    public String getTopDate() {
        return topDate;
    }

    public void setTopDate(String topDate) {
        this.topDate = topDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNoticeId()
    {
        return noticeId;
    }

    public void setNoticeId(String noticeId)
    {
        this.noticeId = noticeId;
    }

    public void setNoticeTitle(String noticeTitle)
    {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeTitle()
    {
        return noticeTitle;
    }

    public void setNoticeType(String noticeType)
    {
        this.noticeType = noticeType;
    }

    public String getNoticeType()
    {
        return noticeType;
    }

    public void setNoticeContent(String noticeContent)
    {
        this.noticeContent = noticeContent;
    }

    public String getNoticeClickId() {
		return noticeClickId;
	}

	public void setNoticeClickId(String noticeClickId) {
		this.noticeClickId = noticeClickId;
	}

	public String getNoticeContent()
    {
        return noticeContent;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

	public String getNoticeCycle() {
		return noticeCycle;
	}

	public void setNoticeCycle(String noticeCycle) {
		this.noticeCycle = noticeCycle;
	}

	public String getReleaseBy() {
		return releaseBy;
	}

	public void setReleaseBy(String releaseBy) {
		this.releaseBy = releaseBy;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getDeleteBy() {
		return deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	
	public Date getLaunchTime() {
		return launchTime;
	}

	public void setLaunchTime(Date launchTime) {
		this.launchTime = launchTime;
	}

	@Override
	public String toString() {
		return "Notice [noticeId=" + noticeId + ", noticeTitle=" + noticeTitle
				+ ", noticeType=" + noticeType + ", noticeContent="
				+ noticeContent + ", status=" + status + ", noticeCycle="
				+ noticeCycle + ", releaseBy=" + releaseBy + ", deleteFlag="
				+ deleteFlag + ", deleteBy=" + deleteBy + ", deleteTime="
				+ deleteTime + ", launchTime=" + launchTime
				+ ", processStatus=" + processStatus + ", noticeClickId="
				+ noticeClickId + "]";
	}
}
