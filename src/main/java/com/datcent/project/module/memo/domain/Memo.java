package com.datcent.project.module.memo.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 备忘录表 task_memo
 * 
 * @author datcent
 * @date 2019-01-26
 */
public class Memo extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 编号 */
	private String memoId;
	/** 日期 */
	private String memoDate;
	/** $column.columnComment */
	private String memoTime;
	/** 内容 */
	private String memoContent;
	/** 创建人 */
	private String memoCreateby;
	/** 创建时间 */
	private String memoCreatedate;

	public void setMemoId(String memoId) 
	{
		this.memoId = memoId;
	}

	public String getMemoId() 
	{
		return memoId;
	}
	public void setMemoDate(String memoDate)
	{
		this.memoDate = memoDate;
	}

	public String getMemoDate()
	{
		return memoDate;
	}
	public void setMemoTime(String memoTime) 
	{
		this.memoTime = memoTime;
	}

	public String getMemoTime() 
	{
		return memoTime;
	}
	public void setMemoContent(String memoContent) 
	{
		this.memoContent = memoContent;
	}

	public String getMemoContent() 
	{
		return memoContent;
	}
	public void setMemoCreateby(String memoCreateby) 
	{
		this.memoCreateby = memoCreateby;
	}

	public String getMemoCreateby() 
	{
		return memoCreateby;
	}
	public void setMemoCreatedate(String memoCreatedate) 
	{
		this.memoCreatedate = memoCreatedate;
	}

	public String getMemoCreatedate() 
	{
		return memoCreatedate;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("memoId", getMemoId())
            .append("memoDate", getMemoDate())
            .append("memoTime", getMemoTime())
            .append("memoContent", getMemoContent())
            .append("memoCreateby", getMemoCreateby())
            .append("memoCreatedate", getMemoCreatedate())
            .toString();
    }
}
