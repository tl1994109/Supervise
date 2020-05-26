package com.datcent.project.module.process.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;

/**
 * 流程线条 表 oa_process_line_detail
 * 
 * @author datcent
 * @date 2018-11-05
 */
public class ProcessLineDetail extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 流程ID */
	private String processId;
	/** 线条ID */
	private String lineId;
	/** 线条名称 */
	private String lineName;
	/** 上一节点ID */
	private String fromNodeId;
	/** 下一节点ID */
	private String toNodeId;

	public void setProcessId(String processId) 
	{
		this.processId = processId;
	}

	public String getProcessId() 
	{
		return processId;
	}
	public void setLineId(String lineId) 
	{
		this.lineId = lineId;
	}

	public String getLineId() 
	{
		return lineId;
	}
	public void setLineName(String lineName) 
	{
		this.lineName = lineName;
	}

	public String getLineName() 
	{
		return lineName;
	}
	public void setFromNodeId(String fromNodeId) 
	{
		this.fromNodeId = fromNodeId;
	}

	public String getFromNodeId() 
	{
		return fromNodeId;
	}
	public void setToNodeId(String toNodeId) 
	{
		this.toNodeId = toNodeId;
	}

	public String getToNodeId() 
	{
		return toNodeId;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("processId", getProcessId())
            .append("lineId", getLineId())
            .append("lineName", getLineName())
            .append("fromNodeId", getFromNodeId())
            .append("toNodeId", getToNodeId())
            .toString();
    }
}
