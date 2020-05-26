package com.datcent.project.module.hiTaskinst.domain;

import com.datcent.common.utils.DateUtils;
import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;

import java.text.ParseException;
import java.util.Date;

/**
 * 历史任务表 act_hi_taskinst
 * 
 * @author datcent
 * @date 2019-01-16
 */
public class HiTaskinst extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** $column.columnComment */
	private String id;
	/** 流程定义编号 */
	private String procDefId;
	/** 节点定义KEY */
	private String taskDefKey;
	/** 流程实例编号 */
	private String procInstId;
	/** 执行实例编号 */
	private String executionId;
	/** 名称 */
	private String name;
	/** 父节点实力编号 */
	private String parentTaskId;
	/** 描述 */
	private String description;
	/** 签收人 */
	private String owner;
	/** 签收人或者被委托人 */
	private String assignee;
	/** 开始时间 */
	private String startTime;
	/** 提醒时间 */
	private String claimTime;
	/** 结束时间 */
	private String endTime;
	/** 耗时 */
	private String duration;
	/** 删除原因 */
	private String deleteReason;
	/** 优先级 */
	private Integer priority;
	/** 应完成时间 */
	private String dueDate;
	/** desinger节点定义的 */
	private String formKey;
	/** $column.columnComment */
	private String category;
	/** $column.columnComment */
	private String tenantId;
	/*类型*/
	private String type;
	/*标题*/
	private String title;
	/*批注*/
	private String content;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	public void setProcDefId(String procDefId) 
	{
		this.procDefId = procDefId;
	}

	public String getProcDefId() 
	{
		return procDefId;
	}
	public void setTaskDefKey(String taskDefKey) 
	{
		this.taskDefKey = taskDefKey;
	}

	public String getTaskDefKey() 
	{
		return taskDefKey;
	}
	public void setProcInstId(String procInstId) 
	{
		this.procInstId = procInstId;
	}

	public String getProcInstId() 
	{
		return procInstId;
	}
	public void setExecutionId(String executionId) 
	{
		this.executionId = executionId;
	}

	public String getExecutionId() 
	{
		return executionId;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setParentTaskId(String parentTaskId) 
	{
		this.parentTaskId = parentTaskId;
	}

	public String getParentTaskId() 
	{
		return parentTaskId;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getDescription() 
	{
		return description;
	}
	public void setOwner(String owner) 
	{
		this.owner = owner;
	}

	public String getOwner() 
	{
		return owner;
	}
	public void setAssignee(String assignee) 
	{
		this.assignee = assignee;
	}

	public String getAssignee() 
	{
		return assignee;
	}
	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	public String getStartTime()
	{
		return startTime;
	}
	public void setClaimTime(String claimTime)
	{
		this.claimTime = claimTime;
	}

	public String getClaimTime()
	{
		return claimTime;
	}
	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}

	public String getEndTime()
	{
		return endTime;
	}
	public void setDuration(String duration)
	{
		this.duration = duration;
	}

	public String getDuration()
	{
		return duration;
	}
	public void setDeleteReason(String deleteReason) 
	{
		this.deleteReason = deleteReason;
	}

	public String getDeleteReason() 
	{
		return deleteReason;
	}
	public void setPriority(Integer priority) 
	{
		this.priority = priority;
	}

	public Integer getPriority() 
	{
		return priority;
	}
	public void setDueDate(String dueDate)
	{
		this.dueDate = dueDate;
	}

	public String getDueDate()
	{
		return dueDate;
	}
	public void setFormKey(String formKey) 
	{
		this.formKey = formKey;
	}

	public String getFormKey() 
	{
		return formKey;
	}
	public void setCategory(String category) 
	{
		this.category = category;
	}

	public String getCategory() 
	{
		return category;
	}
	public void setTenantId(String tenantId) 
	{
		this.tenantId = tenantId;
	}

	public String getTenantId() 
	{
		return tenantId;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("procDefId", getProcDefId())
            .append("taskDefKey", getTaskDefKey())
            .append("procInstId", getProcInstId())
            .append("executionId", getExecutionId())
            .append("name", getName())
            .append("parentTaskId", getParentTaskId())
            .append("description", getDescription())
            .append("owner", getOwner())
            .append("assignee", getAssignee())
            .append("startTime", getStartTime())
            .append("claimTime", getClaimTime())
            .append("endTime", getEndTime())
            .append("duration", getDuration())
            .append("deleteReason", getDeleteReason())
            .append("priority", getPriority())
            .append("dueDate", getDueDate())
            .append("formKey", getFormKey())
            .append("category", getCategory())
            .append("tenantId", getTenantId())
            .toString();
    }
	public HiTaskinst(){}
    public HiTaskinst(HistoricTaskInstance historicTaskInstance) throws ParseException {
		this.id = historicTaskInstance.getId();
		this.assignee = historicTaskInstance.getAssignee();
		this.owner = historicTaskInstance.getOwner();
		this.category = historicTaskInstance.getCategory();
		this.tenantId = historicTaskInstance.getTenantId();
		this.formKey = historicTaskInstance.getFormKey();
		//this.dueDate = historicTaskInstance.getDueDate().toString();
		this.priority = historicTaskInstance.getPriority();
		this.deleteReason = historicTaskInstance.getDeleteReason();
		if(historicTaskInstance.getEndTime()!=null && historicTaskInstance.getStartTime()!=null){
			this.duration = DateUtils.dateDiff(historicTaskInstance.getStartTime(),historicTaskInstance.getEndTime());
		}
		this.endTime = DateUtils.convertDate(historicTaskInstance.getEndTime(),"yyyy-MM-dd");
		//this.claimTime = DateUtils.convertDate(historicTaskInstance.getClaimTime(),"");
		this.startTime = DateUtils.convertDate(historicTaskInstance.getStartTime(),"yyyy-MM-dd");
		this.description = historicTaskInstance.getDescription();
		this.parentTaskId = historicTaskInstance.getParentTaskId();
		this.name = historicTaskInstance.getName();
		this.executionId = historicTaskInstance.getExecutionId();
		this.procInstId = historicTaskInstance.getProcessInstanceId();
		this.taskDefKey = historicTaskInstance.getTaskDefinitionKey();
		this.procDefId = historicTaskInstance.getProcessDefinitionId();
	}
}
