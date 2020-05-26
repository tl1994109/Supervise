package com.datcent.project.module.appraise.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 任务评价表 task_appraise
 * 
 * @author datcent
 * @date 2019-01-24
 */
public class Appraise extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 评价编号 */
	private String appraiseId;
	/** 评价备注 */
	private String appraiseContent;
	/** 评价等级 0.优秀1.良好2.一般3.差 4.未评价 */
	private String appraiseLevel;
	/** 任务编号 */
	private String appraiseTaskid;
	/** 评价人 */
	private String appraiseCreateby;
	/** 评价时间 */
	private Date appraiseCreatedate;

	public void setAppraiseId(String appraiseId) 
	{
		this.appraiseId = appraiseId;
	}

	public String getAppraiseId() 
	{
		return appraiseId;
	}
	public void setAppraiseContent(String appraiseContent) 
	{
		this.appraiseContent = appraiseContent;
	}

	public String getAppraiseContent() 
	{
		return appraiseContent;
	}
	public void setAppraiseLevel(String appraiseLevel) 
	{
		this.appraiseLevel = appraiseLevel;
	}

	public String getAppraiseLevel() 
	{
		return appraiseLevel;
	}
	public void setAppraiseTaskid(String appraiseTaskid) 
	{
		this.appraiseTaskid = appraiseTaskid;
	}

	public String getAppraiseTaskid() 
	{
		return appraiseTaskid;
	}
	public void setAppraiseCreateby(String appraiseCreateby) 
	{
		this.appraiseCreateby = appraiseCreateby;
	}

	public String getAppraiseCreateby() 
	{
		return appraiseCreateby;
	}
	public void setAppraiseCreatedate(Date appraiseCreatedate) 
	{
		this.appraiseCreatedate = appraiseCreatedate;
	}

	public Date getAppraiseCreatedate() 
	{
		return appraiseCreatedate;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("appraiseId", getAppraiseId())
            .append("appraiseContent", getAppraiseContent())
            .append("appraiseLevel", getAppraiseLevel())
            .append("appraiseTaskid", getAppraiseTaskid())
            .append("appraiseCreateby", getAppraiseCreateby())
            .append("appraiseCreatedate", getAppraiseCreatedate())
            .toString();
    }
}
