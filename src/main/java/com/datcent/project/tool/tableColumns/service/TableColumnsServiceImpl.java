package com.datcent.project.tool.tableColumns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.datcent.project.tool.gen.domain.ColumnInfo;
import com.datcent.project.tool.gen.mapper.GenMapper;
import com.datcent.project.tool.tableColumns.mapper.TableColumnsMapper;
import com.datcent.project.tool.tableColumns.domain.TableColumns;
import com.datcent.project.tool.tableColumns.service.ITableColumnsService;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;

/**
 * 字段属性 服务层实现
 * 
 * @author datcent
 * @date 2018-11-23
 */
@Service
public class TableColumnsServiceImpl implements ITableColumnsService 
{
	@Autowired
	private TableColumnsMapper tableColumnsMapper;
	
	@Autowired
	private GenMapper genMapper;

	/**
     * 查询字段属性信息
     * 
     * @param columnId 字段属性ID
     * @return 字段属性信息
     */
    @Override
	public TableColumns selectTableColumnsById(String columnId)
	{
	    return tableColumnsMapper.selectTableColumnsById(columnId);
	}
	
	/**
     * 查询字段属性列表
     * 
     * @param tableColumns 字段属性信息
     * @return 字段属性集合
     */
	@Override
	public List<TableColumns> selectTableColumnsList(TableColumns tableColumns)
	{
	    return tableColumnsMapper.selectTableColumnsList(tableColumns);
	}
	
	/**
     * 同步表的字段属性
     * 
     * @param tableName 表名 tableColumns集合
     * @return 字段属性集合
     */
	@Override
	@Transactional
	public List<TableColumns> selectTableColumnsAndSyn(String tableName,List<ColumnInfo> tableColumns)
	{
		TableColumns columns=new TableColumns();
		columns.setTableName(tableName);
		List<TableColumns> columnsList = tableColumnsMapper.selectTableColumnsList(columns);
		//类似同步表数据操作
		if(columnsList.size()==0 && tableColumns.size()>0){
		    return sysTableColumns(columnsList, tableColumns, columns, tableName);
		}else{
			//当数据库字段有增加 修改可以及时同步
			tableColumnsMapper.deleteTableColumnsByTableName(tableName);
			return sysTableColumns(columnsList, tableColumns, columns, tableName);
		}
	}
	
	@Transactional
	public List<TableColumns> sysTableColumns(List<TableColumns> columnsList,List<ColumnInfo> tableColumns,TableColumns columns,String tableName){
		for (ColumnInfo item : tableColumns) {
			columns=new TableColumns();
			columns.setColumnId(StringUtils.getUUID());
			columns.setTableName(tableName);
			columns.setColumnName(item.getColumnName());
			if( item.getDataType().toLowerCase().equals("varchar") ||
			   item.getDataType().toLowerCase().equals("longtext") || item.getDataType().toLowerCase().equals("longblob") ||
			   item.getDataType().toLowerCase().equals("blob")|| item.getDataType().toLowerCase().equals("text") ){
				columns.setDataType("varchar");
			}else if(item.getDataType().toLowerCase().equals("date") || item.getDataType().toLowerCase().equals("datetime") ||
					 item.getDataType().toLowerCase().equals("timestamp") || item.getDataType().toLowerCase().equals("time") ||
					 item.getDataType().toLowerCase().equals("year")){
				columns.setDataType("datetime");
			}else{
				columns.setDataType(item.getDataType());
			}
			columns.setColumnComment(item.getColumnComment());
			if(item.getIsNullable().toLowerCase().equals("no")){
				//必填
				columns.setIsNullable("0");
			}else{
				//可填
				columns.setIsNullable("1");
			}
			if(item.getColumnKey().toLowerCase().equals("pri")){
				//当是主键是默认自增长
				columns.setIsKey("1");
			}else{
				columns.setIsKey("0");
			}
			//以下为默认设置字段
			columns.setIsSearch("0");
			columns.setIsVisible("0");
			columns.setIsVisibleType("0");
			columns.setIsSortable("0");
			columns.setVisibleCols("3");
			tableColumnsMapper.insertTableColumns(columns);
		}
		TableColumns item=new TableColumns();
		item.setTableName(tableName);
		columnsList = tableColumnsMapper.selectTableColumnsList(item);
		return columnsList;
	}
	
    /**
     * 新增字段属性
     * 
     * @param tableColumns 字段属性信息
     * @return 结果
     */
	@Override
	public int insertTableColumns(TableColumns tableColumns)
	{
	    return tableColumnsMapper.insertTableColumns(tableColumns);
	}
	
	/**
     * 修改字段属性
     * 
     * @param tableColumns 字段属性信息
     * @return 结果
     */
	@Override
	@Transactional
	public int updateTableColumns(TableColumns tableColumns)
	{
	    return tableColumnsMapper.updateTableColumns(tableColumns);
	}

	/**
     * 删除字段属性对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteTableColumnsByIds(String ids)
	{
		return tableColumnsMapper.deleteTableColumnsByIds(Convert.toStrArray(ids));
	}

	@Override
	public int insertByBatch(List<TableColumns> tableColumnsList) {
		return tableColumnsMapper.insertByBatch(tableColumnsList);
	}

	@Override
	public int updateByBatch(List<TableColumns> tableColumnsList) {
		return tableColumnsMapper.updateByBatch(tableColumnsList);
	}
}
