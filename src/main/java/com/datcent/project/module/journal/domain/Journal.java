package com.datcent.project.module.journal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 任务日志表 task_journal
 * 
 * @author datcent
 * @date 2019-01-22
 */
public class Journal extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 日志编号 */
	private String journalId;
	/** 日志内容 */
	private String journalContent;
	/** 任务编号 */
	private String journalTaskid;
	/** 任务创建时间 */
	private String journalCreatedate;
	/** 日志创建人 */
	private String journalCreateby;
	/* 显示类型 */
	private String journalType;
	/* 任务类型 */
	private String taskName;
	/* 任务类型 */
	private String type;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJournalType() {
		return journalType;
	}

	public void setJournalType(String journalType) {
		this.journalType = journalType;
	}

	public void setJournalId(String journalId)
	{
		this.journalId = journalId;
	}

	public String getJournalId() 
	{
		return journalId;
	}
	public void setJournalContent(String journalContent) 
	{
		this.journalContent = journalContent;
	}

	public String getJournalContent() 
	{
		return journalContent;
	}
	public void setJournalTaskid(String journalTaskid) 
	{
		this.journalTaskid = journalTaskid;
	}

	public String getJournalTaskid() 
	{
		return journalTaskid;
	}
	public void setJournalCreatedate(String journalCreatedate)
	{
		this.journalCreatedate = journalCreatedate;
	}

	public String getJournalCreatedate()
	{
		return journalCreatedate;
	}
	public void setJournalCreateby(String journalCreateby) 
	{
		this.journalCreateby = journalCreateby;
	}

	public String getJournalCreateby() 
	{
		return journalCreateby;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("journalId", getJournalId())
            .append("journalContent", getJournalContent())
            .append("journalTaskid", getJournalTaskid())
            .append("journalCreatedate", getJournalCreatedate())
            .append("journalCreateby", getJournalCreateby())
            .toString();
    }
}
