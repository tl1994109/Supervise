package com.datcent.project.tool.tableColumns.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.datcent.common.utils.StringUtils;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.project.system.dict.domain.DictType;
import com.datcent.project.system.dict.service.IDictTypeService;
import com.datcent.project.tool.gen.domain.ColumnInfo;
import com.datcent.project.tool.gen.mapper.GenMapper;
import com.datcent.project.tool.tableColumns.domain.TableColumns;
import com.datcent.project.tool.tableColumns.mapper.TableColumnsMapper;
import com.datcent.project.tool.tableColumns.service.ITableColumnsService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 字段属性 信息操作处理
 * 
 * @author datcent
 * @date 2018-11-23
 */
@Controller
@RequestMapping("/tool/tableColumns")
public class TableColumnsController extends BaseController
{
    private String prefix = "tool/tableColumns";
	
	@Autowired
	private ITableColumnsService tableColumnsService;
	
	@Autowired
    private IDictTypeService dictTypeService;
	
	@Autowired
	private GenMapper genMapper;
	
	@RequiresPermissions("tool:tableColumns:view")
	@GetMapping()
	public String tableColumns()
	{
	    return prefix + "/tableColumns";
	}
	
	/**
	 * 查询字段属性列表
	 */
	@RequiresPermissions("tool:tableColumns:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(TableColumns tableColumns)
	{
		startPage();
        List<TableColumns> list = tableColumnsService.selectTableColumnsList(tableColumns);
		return getDataTable(list);
	}
	
	/**
	 * 字段属性
	 */
	@GetMapping("/maintainace")
	public String maintainace(String tableName,ModelMap mmap)
	{
		List<ColumnInfo> tableColumns = genMapper.selectTableColumnsByName(tableName);
		List<TableColumns> columnsList = tableColumnsService.selectTableColumnsAndSyn(tableName, tableColumns);
		mmap.put("tableColumns", columnsList);
		List<DictType> dictList = dictTypeService.selectDictTypeAll();
		mmap.put("dictList", dictList);
	    return prefix + "/maintainace";
	}
	
	/**
	 * 新增保存字段属性
	 */
	@RequiresPermissions("tool:tableColumns:maintainace")
	@Log(title = "字段属性", businessType = BusinessType.INSERT)
	@PostMapping("/maintainace")
	@ResponseBody
	public AjaxResult addSaveMaintainace(TableColumns tableColumns)
	{		
		return toAjax(tableColumnsService.insertTableColumns(tableColumns));
	}

	/**
	 * 修改字段属性
	 */
	@GetMapping("/edit/{columnId}")
	public String edit(@PathVariable("columnId") String columnId, ModelMap mmap)
	{
		TableColumns tableColumns = tableColumnsService.selectTableColumnsById(columnId);
		mmap.put("tableColumns", tableColumns);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存字段属性
	 */
	@RequiresPermissions("tool:tableColumns:edit")
	@Log(title = "字段属性", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(TableColumns tableColumns)
	{
		return toAjax(tableColumnsService.updateTableColumns(tableColumns));
	}
	
	/**
	 * 批量修改保存字段属性
	 */
	@Log(title = "字段属性", businessType = BusinessType.UPDATE)
	@PostMapping("/editColumns")
	@ResponseBody
	public AjaxResult editColumns(String colums)
	{
		try {
			if (!StringUtils.isEmpty(colums)) {
				colums = colums.replaceAll("&quot;", "'");
				List<TableColumns> columns = JSON.parseArray(colums, TableColumns.class);
				for (TableColumns tableColumns : columns) {
					tableColumnsService.updateTableColumns(tableColumns);
				}
			}
			return toAjax(1);
		} catch (Exception e) {
			e.printStackTrace();
			return toAjax(0);
		}
	}
	
	/**
	 * 删除字段属性
	 */
	@RequiresPermissions("tool:tableColumns:remove")
	@Log(title = "字段属性", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(tableColumnsService.deleteTableColumnsByIds(ids));
	}

	@GetMapping("/dataTableComponent")
	public String ataTableComponent(){
		return prefix+"/dateTable";
	}
}
