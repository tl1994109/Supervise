package com.datcent.project.module.process.domain;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 流程事项 表 oa_process_matter
 * 
 * @author datcent
 * @date 2018-11-08
 */
public class ProcessMatter extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 事项ID */
	private String matterId;
	/** 事项名称 */
	private String matterName;
	/** 事项类型 */
	private String matterType;
	/** 事项内容 */
	private String matterContent;
	/** 流程ID */
	private String processId;
	/** 节点ID */
	private String nodeId;
	/** 发起者 */
	private String launchBy;
	/** 发起时间 */
	private Date launchTime;
	/** 负责人 */
	private String allowBy;
	/** 备注 */
	private String remarks;
	/** 审核状态 */
	private String status;
	/** 发布者姓名*/
	private String launchByName;

	public void setMatterId(String matterId) 
	{
		this.matterId = matterId;
	}

	public String getMatterId() 
	{
		return matterId;
	}
	public void setMatterName(String matterName) 
	{
		this.matterName = matterName;
	}

	public String getMatterName() 
	{
		return matterName;
	}
	public void setMatterContent(String matterContent) 
	{
		this.matterContent = matterContent;
	}

	public String getMatterContent() 
	{
		return matterContent;
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
	public void setLaunchBy(String launchBy) 
	{
		this.launchBy = launchBy;
	}

	public String getLaunchBy() 
	{
		return launchBy;
	}
	public void setLaunchTime(Date launchTime) 
	{
		this.launchTime = launchTime;
	}

	public Date getLaunchTime() 
	{
		return launchTime;
	}
	public void setAllowBy(String allowBy) 
	{
		this.allowBy = allowBy;
	}

	public String getAllowBy() 
	{
		return allowBy;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}

	public String getRemarks() 
	{
		return remarks;
	}

    public String getLaunchByName() {
		return launchByName;
	}

	public void setLaunchByName(String launchByName) {
		this.launchByName = launchByName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMatterType() {
		return matterType;
	}

	public void setMatterType(String matterType) {
		this.matterType = matterType;
	}

	@Override
	public String toString() {
		return "ProcessMatter [matterId=" + matterId + ", matterName="
				+ matterName + ", matterType=" + matterType
				+ ", matterContent=" + matterContent + ", processId="
				+ processId + ", nodeId=" + nodeId + ", launchBy=" + launchBy
				+ ", launchTime=" + launchTime + ", allowBy=" + allowBy
				+ ", remarks=" + remarks + ", status=" + status
				+ ", launchByName=" + launchByName + "]";
	}
}
