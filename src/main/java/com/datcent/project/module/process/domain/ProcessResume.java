package com.datcent.project.module.process.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 流程履历 表 oa_process_resume
 * 
 * @author datcent
 * @date 2018-11-08
 */
public class ProcessResume extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键ID */
	private String id;
	/** 流程ID */
	private String processId;
	/** 节点ID */
	private String nodeId;
	/** 事项ID */
	private String matterId;
	/** 节点名称 */
	private String nodeName;
	/** 操作内容 */
	private String operationContent;
	/** 操作者 */
	private String operationBy;
	/** 操作时间 */
	private Date operationTime;
	/** 发布者 */
	private String personName;
	/** 下一节点名称 */
	private String nextNodeName;
	/** 下一节点操作人 */
	private String nextAllowBy;

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
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
	public void setOperationContent(String operationContent) 
	{
		this.operationContent = operationContent;
	}

	public String getOperationContent() 
	{
		return operationContent;
	}
	public void setOperationBy(String operationBy) 
	{
		this.operationBy = operationBy;
	}

	public String getOperationBy() 
	{
		return operationBy;
	}
	public void setOperationTime(Date operationTime) 
	{
		this.operationTime = operationTime;
	}

	public Date getOperationTime() 
	{
		return operationTime;
	}

    public String getMatterId() {
		return matterId;
	}

	public void setMatterId(String matterId) {
		this.matterId = matterId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getNextNodeName() {
		return nextNodeName;
	}

	public void setNextNodeName(String nextNodeName) {
		this.nextNodeName = nextNodeName;
	}

	public String getNextAllowBy() {
		return nextAllowBy;
	}

	public void setNextAllowBy(String nextAllowBy) {
		this.nextAllowBy = nextAllowBy;
	}

	@Override
	public String toString() {
		return "ProcessResume [id=" + id + ", processId=" + processId
				+ ", nodeId=" + nodeId + ", matterId=" + matterId
				+ ", nodeName=" + nodeName + ", operationContent="
				+ operationContent + ", operationBy=" + operationBy
				+ ", operationTime=" + operationTime + ", personName="
				+ personName + ", nextNodeName=" + nextNodeName
				+ ", nextAllowBy=" + nextAllowBy + "]";
	}
	
	
}
