package com.datcent.project.module.ruTask.domain;

import com.datcent.common.utils.DateUtils;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;

import java.text.ParseException;
import java.util.Date;

/**
 * 任务列表 act_ru_task
 * 
 * @author datcent
 * @date 2019-01-12
 */
public class RuTask extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** $column.columnComment */
	private String id;
	/** $column.columnComment */
	private Integer rev;
	/** $column.columnComment */
	private String executionId;
	/** $column.columnComment */
	private String procInstId;
	/** $column.columnComment */
	private String procDefId;
	/** $column.columnComment */
	private String name;
	/** $column.columnComment */
	private String parentTaskId;
	/** $column.columnComment */
	private String description;
	/** $column.columnComment */
	private String taskDefKey;
	/** $column.columnComment */
	private String owner;
	/** $column.columnComment */
	private String assignee;
	/** $column.columnComment */
	private String delegation;
	/** $column.columnComment */
	private Integer priority;
	/** $column.columnComment */
	private String createDate;
	/** $column.columnComment */
	private Date dueDate;
	/** $column.columnComment */
	private String category;
	/** $column.columnComment */
	private Integer suspensionState;
	/** $column.columnComment */
	private String tenantId;
	/** $column.columnComment */
	private String formKey;
	//申请人
	private String proposerName;
	//申请类型 1.公告申请
	private String type;
	//标题
	private String title;

	private String isok;

	public String getIsok() {
		return isok;
	}

	public void setIsok(String isok) {
		this.isok = isok;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProposerName() {
		return proposerName;
	}

	public void setProposerName(String proposerName) {
		this.proposerName = proposerName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	public void setRev(Integer rev) 
	{
		this.rev = rev;
	}

	public Integer getRev() 
	{
		return rev;
	}
	public void setExecutionId(String executionId) 
	{
		this.executionId = executionId;
	}

	public String getExecutionId() 
	{
		return executionId;
	}
	public void setProcInstId(String procInstId) 
	{
		this.procInstId = procInstId;
	}

	public String getProcInstId() 
	{
		return procInstId;
	}
	public void setProcDefId(String procDefId) 
	{
		this.procDefId = procDefId;
	}

	public String getProcDefId() 
	{
		return procDefId;
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
	public void setTaskDefKey(String taskDefKey) 
	{
		this.taskDefKey = taskDefKey;
	}

	public String getTaskDefKey() 
	{
		return taskDefKey;
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
	public void setDelegation(String delegation) 
	{
		this.delegation = delegation;
	}

	public String getDelegation() 
	{
		return delegation;
	}
	public void setPriority(Integer priority) 
	{
		this.priority = priority;
	}

	public Integer getPriority() 
	{
		return priority;
	}

	public void setDueDate(Date dueDate)
	{
		this.dueDate = dueDate;
	}

	public Date getDueDate() 
	{
		return dueDate;
	}
	public void setCategory(String category) 
	{
		this.category = category;
	}

	public String getCategory() 
	{
		return category;
	}
	public void setSuspensionState(Integer suspensionState) 
	{
		this.suspensionState = suspensionState;
	}

	public Integer getSuspensionState() 
	{
		return suspensionState;
	}
	public void setTenantId(String tenantId) 
	{
		this.tenantId = tenantId;
	}

	public String getTenantId() 
	{
		return tenantId;
	}
	public void setFormKey(String formKey) 
	{
		this.formKey = formKey;
	}

	public String getFormKey() 
	{
		return formKey;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("rev", getRev())
            .append("executionId", getExecutionId())
            .append("procInstId", getProcInstId())
            .append("procDefId", getProcDefId())
            .append("name", getName())
            .append("parentTaskId", getParentTaskId())
            .append("description", getDescription())
            .append("taskDefKey", getTaskDefKey())
            .append("owner", getOwner())
            .append("assignee", getAssignee())
            .append("delegation", getDelegation())
            .append("priority", getPriority())
            .append("createTime", getCreateTime())
            .append("dueDate", getDueDate())
            .append("category", getCategory())
            .append("suspensionState", getSuspensionState())
            .append("tenantId", getTenantId())
            .append("formKey", getFormKey())
            .toString();
    }
	public RuTask(){}
	public RuTask(Task task) throws ParseException {
		this.id = task.getId();
		this.executionId = task.getExecutionId();
		this.procInstId = task.getProcessInstanceId();
		this.procDefId = task.getProcessDefinitionId();
		this.name = task.getName();
		this.parentTaskId = task.getParentTaskId();
		this.description = task.getDescription();
		this.taskDefKey = task.getTaskDefinitionKey();
		this.owner = task.getOwner();
		this.assignee = task.getAssignee();
		this.priority = task.getPriority();
		this.createDate = DateUtils.convertDate(task.getCreateTime(),"yyyy-MM-dd");
		this.dueDate = task.getDueDate();
		this.category = task.getCategory();
		this.tenantId = task.getTenantId();
		this.formKey = task.getFormKey();
	}
}
