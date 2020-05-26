package com.datcent.project.module.dispositionAttachment.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 工作记录附件表 busi_disposition_attachment
 * 
 * @author datcent
 * @date 2019-01-25
 */
public class DispositionAttachment extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	/** 线索ID */
	private String clueId;
	/** 附件ID */
	private String attachmentId;
	/** 记录表ID */
	private String handleId;
	/** 附件名称 */
	private String attachmentName;
	/** 节点标识 */
	private String nodeFlag;
	/** 附件路径 */
	private String attachmentUrl;
	/** 附件所属节点 */
	private String fileOfNode;
	/** 附件所属状态 */
	private String fileOfStatus;
	/** 表单内容 */
	private String formContent;
	/** 上报时间 */
	private Date reportTime;
	/** 上报人 */
	private String reportBy;
	/** 接收人 */
	private String receiveBy;
	/** 接收时间 */
	private Date receiveTime;
	/** 处置次数标识*/
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setClueId(String clueId)
	{
		this.clueId = clueId;
	}

	public String getClueId() 
	{
		return clueId;
	}
	public void setAttachmentId(String attachmentId) 
	{
		this.attachmentId = attachmentId;
	}

	public String getAttachmentId() 
	{
		return attachmentId;
	}
	public void setHandleId(String handleId) 
	{
		this.handleId = handleId;
	}

	public String getHandleId() 
	{
		return handleId;
	}
	public void setAttachmentName(String attachmentName) 
	{
		this.attachmentName = attachmentName;
	}

	public String getAttachmentName() 
	{
		return attachmentName;
	}
	public void setNodeFlag(String nodeFlag) 
	{
		this.nodeFlag = nodeFlag;
	}

	public String getNodeFlag() 
	{
		return nodeFlag;
	}
	public void setAttachmentUrl(String attachmentUrl) 
	{
		this.attachmentUrl = attachmentUrl;
	}

	public String getAttachmentUrl() 
	{
		return attachmentUrl;
	}
	public void setFormContent(String formContent) 
	{
		this.formContent = formContent;
	}

	public String getFormContent() 
	{
		return formContent;
	}
	public void setReportTime(Date reportTime) 
	{
		this.reportTime = reportTime;
	}

	public Date getReportTime() 
	{
		return reportTime;
	}
	public void setReportBy(String reportBy) 
	{
		this.reportBy = reportBy;
	}

	public String getReportBy() 
	{
		return reportBy;
	}
	public void setReceiveBy(String receiveBy) 
	{
		this.receiveBy = receiveBy;
	}

	public String getReceiveBy() 
	{
		return receiveBy;
	}
	public void setReceiveTime(Date receiveTime) 
	{
		this.receiveTime = receiveTime;
	}

	public Date getReceiveTime() 
	{
		return receiveTime;
	}

	public String getFileOfNode() {
		return fileOfNode;
	}

	public void setFileOfNode(String fileOfNode) {
		this.fileOfNode = fileOfNode;
	}

	public String getFileOfStatus() {
		return fileOfStatus;
	}

	public void setFileOfStatus(String fileOfStatus) {
		this.fileOfStatus = fileOfStatus;
	}

	@Override
	public String toString() {
		return "DispositionAttachment{" +
				"clueId='" + clueId + '\'' +
				", attachmentId='" + attachmentId + '\'' +
				", handleId='" + handleId + '\'' +
				", attachmentName='" + attachmentName + '\'' +
				", nodeFlag='" + nodeFlag + '\'' +
				", attachmentUrl='" + attachmentUrl + '\'' +
				", fileOfNode='" + fileOfNode + '\'' +
				", fileOfStatus='" + fileOfStatus + '\'' +
				", formContent='" + formContent + '\'' +
				", reportTime=" + reportTime +
				", reportBy='" + reportBy + '\'' +
				", receiveBy='" + receiveBy + '\'' +
				", receiveTime=" + receiveTime +
				", count=" + count +
				'}';
	}
}
