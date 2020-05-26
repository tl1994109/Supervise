package com.datcent.project.module.information.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 任务表 task_information
 * 
 * @author datcent
 * @date 2019-01-20
 */
public class Information extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 任务编号 */
	private String taskId;
	/** 任务名称 */
	private String taskName;
	/** 任务状态  0.未开始 1.进行中 2.已完成 3.待分配 4.超期完成 5.超期未完成 6.待提交*/
	private String taskStatus;
	/** 处理人 */
	private String taskProcessor;
	/** 完成人 */
	private String taskConsimmator;
	/** 重要度 */
	private String taskImportance;
	/** 紧迫度 */
	private String taskUrgency;
	/** 规划开始时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String taskPlanStartdate;
	/** 规划结束时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String taskPlanEnddate;
	/** 开始时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String taskStartdate;
	/** 结束时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String taskEnddate;
	/** 任务创建人 */
	private String taskCreateby;
	/** 任务创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date taskCreatedate;
	/* 任务类型 */
	private String taskType;
	/* 任务描述 */
	private String taskContent;
	/* 任务处理人姓名 */
	private String taskProcessorName;
	/* 任务创建人姓名 */
	private String taskCreateName;
	/* 任务完成人姓名 */
	private String taskConsimmatorName;
	/* 是否删除   0正常 1取消任务 */
	private String isDelete;
	/* 最后修改人 */
	private String lastUpdateBy;
	/* 最后修改时间 */
	private String lastUpdateDate;
	/* 开始备注 */
	private String taskStartContent;
	/* 结束备注 */
	private String taskEndContent;
	/* 完成任务百分比 */
	private String taskProportion;
	/* 评价内容 */
	private String appContent;

	private String deptId;

	private String parentDeptId;
	/* 提交人 */
	private String submitter;

	private String appraise_level;

	//0.未开始 1.待分配 2.进行中任务今日截止的 3.进行中任务剩余1日 4.超期未完成
	private String isTXStatus;

	public String getIsTXStatus() {
		return isTXStatus;
	}

	public void setIsTXStatus(String isTXStatus) {
		this.isTXStatus = isTXStatus;
	}

	public String getAppraise_level() {
		return appraise_level;
	}

	public void setAppraise_level(String appraise_level) {
		this.appraise_level = appraise_level;
	}

	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getParentDeptId() {
		return parentDeptId;
	}

	public void setParentDeptId(String parentDeptId) {
		this.parentDeptId = parentDeptId;
	}

	public String getAppContent() {
		return appContent;
	}

	public void setAppContent(String appContent) {
		this.appContent = appContent;
	}

	public String getTaskProportion() {
		return taskProportion;
	}

	public void setTaskProportion(String taskProportion) {
		this.taskProportion = taskProportion;
	}

	public String getTaskStartContent() {
		return taskStartContent;
	}

	public void setTaskStartContent(String taskStartContent) {
		this.taskStartContent = taskStartContent;
	}

	public String getTaskEndContent() {
		return taskEndContent;
	}

	public void setTaskEndContent(String taskEndContent) {
		this.taskEndContent = taskEndContent;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskProcessor() {
		return taskProcessor;
	}

	public void setTaskProcessor(String taskProcessor) {
		this.taskProcessor = taskProcessor;
	}

	public String getTaskConsimmator() {
		return taskConsimmator;
	}

	public void setTaskConsimmator(String taskConsimmator) {
		this.taskConsimmator = taskConsimmator;
	}

	public String getTaskImportance() {
		return taskImportance;
	}

	public void setTaskImportance(String taskImportance) {
		this.taskImportance = taskImportance;
	}

	public String getTaskUrgency() {
		return taskUrgency;
	}

	public void setTaskUrgency(String taskUrgency) {
		this.taskUrgency = taskUrgency;
	}

	public String getTaskPlanStartdate() {
		return taskPlanStartdate;
	}

	public void setTaskPlanStartdate(String taskPlanStartdate) {
		this.taskPlanStartdate = taskPlanStartdate;
	}

	public String getTaskPlanEnddate() {
		return taskPlanEnddate;
	}

	public void setTaskPlanEnddate(String taskPlanEnddate) {
		this.taskPlanEnddate = taskPlanEnddate;
	}

	public String getTaskStartdate() {
		return taskStartdate;
	}

	public void setTaskStartdate(String taskStartdate) {
		this.taskStartdate = taskStartdate;
	}

	public String getTaskEnddate() {
		return taskEnddate;
	}

	public void setTaskEnddate(String taskEnddate) {
		this.taskEnddate = taskEnddate;
	}

	public String getTaskCreateby() {
		return taskCreateby;
	}

	public void setTaskCreateby(String taskCreateby) {
		this.taskCreateby = taskCreateby;
	}

	public Date getTaskCreatedate() {
		return taskCreatedate;
	}

	public void setTaskCreatedate(Date taskCreatedate) {
		this.taskCreatedate = taskCreatedate;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskContent() {
		return taskContent;
	}

	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}

	public String getTaskProcessorName() {
		return taskProcessorName;
	}

	public void setTaskProcessorName(String taskProcessorName) {
		this.taskProcessorName = taskProcessorName;
	}

	public String getTaskCreateName() {
		return taskCreateName;
	}

	public void setTaskCreateName(String taskCreateName) {
		this.taskCreateName = taskCreateName;
	}

	public String getTaskConsimmatorName() {
		return taskConsimmatorName;
	}

	public void setTaskConsimmatorName(String taskConsimmatorName) {
		this.taskConsimmatorName = taskConsimmatorName;
	}

	@Override
	public String toString() {
		return "Information{" +
				"taskId='" + taskId + '\'' +
				", taskName='" + taskName + '\'' +
				", taskStatus='" + taskStatus + '\'' +
				", taskProcessor='" + taskProcessor + '\'' +
				", taskConsimmator='" + taskConsimmator + '\'' +
				", taskImportance='" + taskImportance + '\'' +
				", taskUrgency='" + taskUrgency + '\'' +
				", taskPlanStartdate='" + taskPlanStartdate + '\'' +
				", taskPlanEnddate='" + taskPlanEnddate + '\'' +
				", taskStartdate='" + taskStartdate + '\'' +
				", taskEnddate='" + taskEnddate + '\'' +
				", taskCreateby='" + taskCreateby + '\'' +
				", taskCreatedate=" + taskCreatedate +
				", taskType='" + taskType + '\'' +
				", taskContent='" + taskContent + '\'' +
				", taskProcessorName='" + taskProcessorName + '\'' +
				", taskCreateName='" + taskCreateName + '\'' +
				", taskConsimmatorName='" + taskConsimmatorName + '\'' +
				'}';
	}
}
