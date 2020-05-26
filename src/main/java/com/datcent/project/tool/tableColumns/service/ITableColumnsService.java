package com.datcent.project.tool.tableColumns.service;

import com.datcent.project.tool.gen.domain.ColumnInfo;
import com.datcent.project.tool.tableColumns.domain.TableColumns;

import java.util.List;

/**
 * 字段属性 服务层
 * 
 * @author datcent
 * @date 2018-11-23
 */
public interface ITableColumnsService 
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
     * 同步表字段并存储数据
     * 
     * @param tableColumns 字段属性信息
     * @return 字段属性集合
     */
	public List<TableColumns> selectTableColumnsAndSyn(String tableName,List<ColumnInfo> tableColumns);
	
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
     * 删除字段属性信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteTableColumnsByIds(String ids);

	public int insertByBatch(List<TableColumns> tableColumnsList);

	public int updateByBatch(List<TableColumns> tableColumnsList);
}
