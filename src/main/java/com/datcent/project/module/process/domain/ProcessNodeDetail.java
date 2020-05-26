package com.datcent.project.module.process.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;

/**
 * 流程节点 表 oa_process_node_detail
 * 
 * @author datcent
 * @date 2018-11-08
 */
public class ProcessNodeDetail extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 流程ID */
	private String processId;
	/** 节点ID */
	private String nodeId;
	/** 节点名称 */
	private String nodeName;
	/** 节点类型 */
	private String nodeType;
	/** 可否修改 */
	private String isUpdate;
	/** 负责人 */
	private String allowBy;

	public void setProcessId(String processId) 
	{
		this.processId = processId;
	}

	public String getProcessId() 
	{
		return processId;
	}
	public void setNodeId(String nodeId) 
	{
		this.nodeId = nodeId;
	}

	public String getNodeId() 
	{
		return nodeId;
	}
	public void setNodeName(String nodeName) 
	{
		this.nodeName = nodeName;
	}

	public String getNodeName() 
	{
		return nodeName;
	}
	public void setNodeType(String nodeType) 
	{
		this.nodeType = nodeType;
	}

	public String getNodeType() 
	{
		return nodeType;
	}
	public void setIsUpdate(String isUpdate) 
	{
		this.isUpdate = isUpdate;
	}

	public String getIsUpdate() 
	{
		return isUpdate;
	}
	public void setAllowBy(String allowBy) 
	{
		this.allowBy = allowBy;
	}

	public String getAllowBy() 
	{
		return allowBy;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("processId", getProcessId())
            .append("nodeId", getNodeId())
            .append("nodeName", getNodeName())
            .append("nodeType", getNodeType())
            .append("isUpdate", getIsUpdate())
            .append("allowBy", getAllowBy())
            .toString();
    }
}
