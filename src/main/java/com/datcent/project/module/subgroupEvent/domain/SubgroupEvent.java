package com.datcent.project.module.subgroupEvent.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 添加事件表 tool_subgroup_event
 * 
 * @author datcent
 * @date 2018-11-30
 */
public class SubgroupEvent extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 事件ID */
	private String eventId;
	/** 事件名称 */
	private String eventName;
	/** 事件类型(0页面跳转 1触发事件) */
	private String eventType;
	/** 事件方法名称 */
	private String eventMethod;
	/** 引用组图 */
	private String diagramId;
	/** 所属表名 */
	private String tableName;
	/** 按钮颜色(0蓝色 1绿色 2 黄色 3红色) */
	private String btnColor;

	private String jsonStr;

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public void setEventId(String eventId)
	{
		this.eventId = eventId;
	}

	public String getEventId() 
	{
		return eventId;
	}
	public void setEventName(String eventName) 
	{
		this.eventName = eventName;
	}

	public String getEventName() 
	{
		return eventName;
	}
	public void setEventType(String eventType) 
	{
		this.eventType = eventType;
	}

	public String getEventType() 
	{
		return eventType;
	}
	public void setEventMethod(String eventMethod) 
	{
		this.eventMethod = eventMethod;
	}

	public String getEventMethod() 
	{
		return eventMethod;
	}
	public void setDiagramId(String diagramId) 
	{
		this.diagramId = diagramId;
	}

	public String getDiagramId() 
	{
		return diagramId;
	}
	public void setTableName(String tableName) 
	{
		this.tableName = tableName;
	}

	public String getTableName() 
	{
		return tableName;
	}
	public void setBtnColor(String btnColor) 
	{
		this.btnColor = btnColor;
	}

	public String getBtnColor() 
	{
		return btnColor;
	}

	@Override
	public String toString() {
		return "SubgroupEvent{" +
				"eventId='" + eventId + '\'' +
				", eventName='" + eventName + '\'' +
				", eventType='" + eventType + '\'' +
				", eventMethod='" + eventMethod + '\'' +
				", diagramId='" + diagramId + '\'' +
				", tableName='" + tableName + '\'' +
				", btnColor='" + btnColor + '\'' +
				", jsonStr='" + jsonStr + '\'' +
				'}';
	}
}
