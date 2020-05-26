package com.datcent.project.module.accessory.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;

/**
 * 任务附件表 task_accessory
 * 
 * @author datcent
 * @date 2019-01-22
 */
public class Accessory extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 附件编号 */
	private String accessoryId;
	/** 附件路径 */
	private String accessoryUrl;
	/** 任务编号 */
	private String accessoryTaskid;
	/** 初始名称 */
	private String accessoryOldname;
	/* 附件类型 0.创建任务附件 1.完成任务附件*/
	private String accessoryType;

	public String getAccessoryType() {
		return accessoryType;
	}

	public void setAccessoryType(String accessoryType) {
		this.accessoryType = accessoryType;
	}

	public void setAccessoryId(String accessoryId)
	{
		this.accessoryId = accessoryId;
	}

	public String getAccessoryId() 
	{
		return accessoryId;
	}
	public void setAccessoryUrl(String accessoryUrl) 
	{
		this.accessoryUrl = accessoryUrl;
	}

	public String getAccessoryUrl() 
	{
		return accessoryUrl;
	}
	public void setAccessoryTaskid(String accessoryTaskid) 
	{
		this.accessoryTaskid = accessoryTaskid;
	}

	public String getAccessoryTaskid() 
	{
		return accessoryTaskid;
	}
	public void setAccessoryOldname(String accessoryOldname) 
	{
		this.accessoryOldname = accessoryOldname;
	}

	public String getAccessoryOldname() 
	{
		return accessoryOldname;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("accessoryId", getAccessoryId())
            .append("accessoryUrl", getAccessoryUrl())
            .append("accessoryTaskid", getAccessoryTaskid())
            .append("accessoryOldname", getAccessoryOldname())
            .toString();
    }
}
