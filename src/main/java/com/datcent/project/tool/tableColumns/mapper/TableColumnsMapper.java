package com.datcent.project.tool.tableColumns.mapper;

import com.datcent.project.tool.tableColumns.domain.TableColumns;
import java.util.List;	

/**
 * 字段属性 数据层
 * 
 * @author datcent
 * @date 2018-11-23
 */
public interface TableColumnsMapper 
{
	/**
     * 查询字段属性信息
     * 
     * @param columnId 字段属性ID
     * @return 字段属性信息
     */
	public TableColumns selectTableColumnsById(String columnId);
	
	/**
     * 查询字段属性列表
     * 
     * @param tableColumns 字段属性信息
     * @return 字段属性集合
     */
	public List<TableColumns> selectTableColumnsList(TableColumns tableColumns);
	
	/**
     * 新增字段属性
     * 
     * @param tableColumns 字段属性信息
     * @return 结果
     */
	public int insertTableColumns(TableColumns tableColumns);
	
	/**
     * 修改字段属性
     * 
     * @param tableColumns 字段属性信息
     * @return 结果
     */
	public int updateTableColumns(TableColumns tableColumns);
	
	/**
     * 删除字段属性
     * 
     * @param columnId 字段属性ID
     * @return 结果
     */
	public int deleteTableColumnsById(String columnId);
	
	/**
     * 删除字段属性
     * 
     * @param TableName 字段属性TableName
     * @return 结果
     */
	public int deleteTableColumnsByTableName(String tableName);
	
	/**
     * 批量删除字段属性
     * 
     * @param columnIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteTableColumnsByIds(String[] columnIds);

	public int insertByBatch(List<TableColumns> tableColumnsList);

	public int updateByBatch(List<TableColumns> tableColumnsList);
}