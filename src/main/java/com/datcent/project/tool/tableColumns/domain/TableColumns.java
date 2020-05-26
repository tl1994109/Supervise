package com.datcent.project.tool.tableColumns.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.datcent.framework.web.domain.BaseEntity;

/**
 * 字段属性表 sys_table_columns
 * 
 * @author datcent
 * @date 2018-11-23
 */
public class TableColumns extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private String columnId;
	
	/** Table名称 */
	private String tableName;
	
	/** 主键生成规则(0无 1自增长 2 UUID) */
	private String isKey;
	
	/** 字段名称 */
	private String columnName;
	
	/** 字段类型 */
	private String dataType;
	
	/** 非空验证（0:必填 1:可填） */
	private String isNullable;
	
	/** 字段备注 */
	private String columnComment;
	
	/** 查询条件(0是 1否) */
	private String isSearch;
	
	/** 显示状态(0显示1隐藏) */
	private String isVisible;
	
	/** 显示方式(0输入框 1下拉框 2文本框 3日期 4密码框 5隐藏框 6富文本框 7单选框 8复选框) */
	private String isVisibleType;
	
	/** 字典类型 */
	private String dictType;
	
	/** 显示列数 */
	private String visibleCols;
	
	/** 排序方式(0无 1 desc 2 asc) */
	private String isSortable;
	
	/** Java属性类型 */
    private String attrType;

    /** Java属性名称(第一个字母大写)，如：user_name => UserName */
    private String attrName;

    /** Java属性名称(第一个字母小写)，如：user_name => userName */
    private String attrname;

    /*所属组件*/
    private String columnsType;

	public String getColumnsType() {
		return columnsType;
	}

	public void setColumnsType(String columnsType) {
		this.columnsType = columnsType;
	}

	public void setColumnId(String columnId)
	{
		this.columnId = columnId;
	}

	public String getColumnId() 
	{
		return columnId;
	}
	public void setIsKey(String isKey) 
	{
		this.isKey = isKey;
	}

	public String getIsKey() 
	{
		return isKey;
	}
	public void setColumnName(String columnName) 
	{
		this.columnName = columnName;
	}

	public String getColumnName() 
	{
		return columnName;
	}
	public void setDataType(String dataType) 
	{
		this.dataType = dataType;
	}

	public String getDataType() 
	{
		return dataType;
	}
	public void setIsNullable(String isNullable) 
	{
		this.isNullable = isNullable;
	}

	public String getIsNullable() 
	{
		return isNullable;
	}
	public void setColumnComment(String columnComment) 
	{
		this.columnComment = columnComment;
	}

	public String getColumnComment() 
	{
		return columnComment;
	}
	public void setIsSearch(String isSearch) 
	{
		this.isSearch = isSearch;
	}

	public String getIsSearch() 
	{
		return isSearch;
	}
	public void setIsVisible(String isVisible) 
	{
		this.isVisible = isVisible;
	}

	public String getIsVisible() 
	{
		return isVisible;
	}
	public void setIsVisibleType(String isVisibleType) 
	{
		this.isVisibleType = isVisibleType;
	}

	public String getIsVisibleType() 
	{
		return isVisibleType;
	}
	public void setDictType(String dictType) 
	{
		this.dictType = dictType;
	}

	public String getDictType() 
	{
		return dictType;
	}
	
	public void setVisibleCols(String visibleCols) 
	{
		this.visibleCols = visibleCols;
	}

	public String getVisibleCols() 
	{
		return visibleCols;
	}
	public void setIsSortable(String isSortable) 
	{
		this.isSortable = isSortable;
	}

	public String getIsSortable() 
	{
		return isSortable;
	}

    public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrname() {
		return attrname;
	}

	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}

	@Override
	public String toString() {
		return "TableColumns{" +
				"columnId='" + columnId + '\'' +
				", tableName='" + tableName + '\'' +
				", isKey='" + isKey + '\'' +
				", columnName='" + columnName + '\'' +
				", dataType='" + dataType + '\'' +
				", isNullable='" + isNullable + '\'' +
				", columnComment='" + columnComment + '\'' +
				", isSearch='" + isSearch + '\'' +
				", isVisible='" + isVisible + '\'' +
				", isVisibleType='" + isVisibleType + '\'' +
				", dictType='" + dictType + '\'' +
				", visibleCols='" + visibleCols + '\'' +
				", isSortable='" + isSortable + '\'' +
				", attrType='" + attrType + '\'' +
				", attrName='" + attrName + '\'' +
				", attrname='" + attrname + '\'' +
				", columnsType='" + columnsType + '\'' +
				'}';
	}
}
