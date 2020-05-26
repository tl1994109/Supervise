package com.datcent.project.tool.gen.domain;

import java.util.List;

import com.datcent.common.utils.StringUtils;
import com.datcent.framework.web.domain.BaseEntity;
import com.datcent.project.tool.tableColumns.domain.TableColumns;

/**
 * ry 数据库表
 * 
 * @author datcent
 */
public class TableInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    
    /** 表名称 */
    private String tableName;

    /** 表描述 */
    private String tableComment;

    /** 表的主键列信息 */
    private TableColumns primaryKey;

    /** 表的列名(不包含主键) */
    private List<TableColumns> columns;

    /** 类名(第一个字母大写) */
    private String className;

    /** 类名(第一个字母小写) */
    private String classname;

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getTableComment()
    {
        return tableComment;
    }

    public void setTableComment(String tableComment)
    {
        this.tableComment = tableComment;
    }

    public List<TableColumns> getColumns()
    {
        return columns;
    }

    public TableColumns getColumnsLast()
    {
    	TableColumns columnInfo = null;
        if (StringUtils.isNotNull(columns) && columns.size() > 0)
        {
            for (TableColumns tableColumns : columns) {
				if(tableColumns.getIsKey().equals("1") || tableColumns.getIsKey().equals("2")){
					return tableColumns;
				}
			}
        }
        return columnInfo;
    }

    public void setColumns(List<TableColumns> columns)
    {
        this.columns = columns;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getClassname()
    {
        return classname;
    }

    public void setClassname(String classname)
    {
        this.classname = classname;
    }

    public TableColumns getPrimaryKey()
    {
        return primaryKey;
    }

    public void setPrimaryKey(TableColumns primaryKey)
    {
        this.primaryKey = primaryKey;
    }

    @Override
    public String toString()
    {
        return "TableInfo [tableName=" + tableName + ", tableComment=" + tableComment + ", primaryKey=" + primaryKey
                + ", columns=" + columns + ", className=" + className + ", classname=" + classname + "]";
    }

}
