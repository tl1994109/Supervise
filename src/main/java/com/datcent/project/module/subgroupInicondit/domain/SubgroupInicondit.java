package com.datcent.project.module.subgroupInicondit.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.datcent.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 初始条件表 tool_subgroup_inicondit
 * 
 * @author datcent
 * @date 2018-11-30
 */
public class SubgroupInicondit extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 初始条件ID */
	private String iniconditId;
	/** 字段ID */
	private String columnName;
	/** 条件 */
	private String conditionMark;
	/** 逻辑运算符 */
	private String conditionOperator;
	/** 所属表名 */
	private String tableName;
	/** 值 */
	private String conditionValue;

	private String guId;

	private String jsonStr;

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public String getGuId() {
		return guId;
	}

	public void setGuId(String guId) {
		this.guId = guId;
	}

	public void setIniconditId(String iniconditId)
	{
		this.iniconditId = iniconditId;
	}

	public String getIniconditId() 
	{
		return iniconditId;
	}
	public void setColumnName(String columnName) 
	{
		this.columnName = columnName;
	}

	public String getColumnName() 
	{
		return columnName;
	}
	public void setConditionMark(String conditionMark) 
	{
		this.conditionMark = conditionMark;
	}

	public String getConditionMark() 
	{
		return conditionMark;
	}
	public void setConditionOperator(String conditionOperator) 
	{
		this.conditionOperator = conditionOperator;
	}

	public String getConditionOperator() 
	{
		return conditionOperator;
	}
	public void setTableName(String tableName) 
	{
		this.tableName = tableName;
	}

	public String getTableName() 
	{
		return tableName;
	}
	public void setConditionValue(String conditionValue) 
	{
		this.conditionValue = conditionValue;
	}

	public String getConditionValue() 
	{
		return conditionValue;
	}

	@Override
	public String toString() {
		return "SubgroupInicondit{" +
				"iniconditId='" + iniconditId + '\'' +
				", columnName='" + columnName + '\'' +
				", conditionMark='" + conditionMark + '\'' +
				", conditionOperator='" + conditionOperator + '\'' +
				", tableName='" + tableName + '\'' +
				", conditionValue='" + conditionValue + '\'' +
				", guId='" + guId + '\'' +
				", jsonStr='" + jsonStr + '\'' +
				'}';
	}
}
