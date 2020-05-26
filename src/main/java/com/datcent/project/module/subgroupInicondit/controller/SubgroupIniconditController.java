package com.datcent.project.module.subgroupInicondit.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.datcent.common.utils.StringUtils;
import com.datcent.project.system.dict.domain.DictType;
import com.datcent.project.system.dict.service.IDictTypeService;
import com.datcent.project.tool.gen.domain.ColumnInfo;
import com.datcent.project.tool.gen.mapper.GenMapper;
import com.datcent.project.tool.gen.service.IGenService;
import com.datcent.project.tool.tableColumns.domain.TableColumns;
import com.datcent.project.tool.tableColumns.service.ITableColumnsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.project.module.subgroupInicondit.domain.SubgroupInicondit;
import com.datcent.project.module.subgroupInicondit.service.ISubgroupIniconditService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 初始条件 信息操作处理
 * 
 * @author datcent
 * @date 2018-11-30
 */
@Controller
@RequestMapping("/module/subgroupInicondit")
public class SubgroupIniconditController extends BaseController
{
    private String prefix = "module/subgroupInicondit";
	
	@Autowired
	private ISubgroupIniconditService subgroupIniconditService;

	@Autowired
	private ITableColumnsService tableColumnsService;

	@Autowired
	private GenMapper genMapper;

	@Autowired
	private IDictTypeService dictTypeService;

	@RequiresPermissions("module:subgroupInicondit:view")
	@GetMapping()
	public String subgroupInicondit()
	{
	    return prefix + "/subgroupInicondit";
	}
	
	/**
	 * 查询初始条件列表
	 */
	@RequiresPermissions("module:subgroupInicondit:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SubgroupInicondit subgroupInicondit)
	{
		startPage();
        List<SubgroupInicondit> list = subgroupIniconditService.selectSubgroupIniconditList(subgroupInicondit);
		return getDataTable(list);
	}
	
	/**
	 * 新增初始条件
	 */
	@GetMapping("/add")
	public String add(String tableName,ModelMap modelMap)
	{
		List<ColumnInfo> tableColumns = genMapper.selectTableColumnsByName(tableName);
		modelMap.put("tableColumnsList",tableColumns);
		modelMap.put("tableName",tableName);
		return prefix + "/add";
	}
	
	/**
	 * 新增保存初始条件
	 */
	@RequiresPermissions("module:subgroupInicondit:add")
	@Log(title = "初始条件", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(SubgroupInicondit subgroupInicondit)
	{
		subgroupInicondit.setIniconditId(StringUtils.getUUID());
		subgroupInicondit.setCreateTime(new Date());
		return toAjax(subgroupIniconditService.insertSubgroupInicondit(subgroupInicondit));
	}

	/**
	 * 修改初始条件
	 */
	@GetMapping("/edit/{iniconditId}")
	public String edit(@PathVariable("iniconditId") String iniconditId, ModelMap mmap)
	{
		SubgroupInicondit subgroupInicondit = subgroupIniconditService.selectSubgroupIniconditById(iniconditId);
		List<ColumnInfo> columnInfoList = genMapper.selectTableColumnsByName(subgroupInicondit.getTableName());
		mmap.put("subgroupInicondit", subgroupInicondit);
		mmap.put("columnInfoList", columnInfoList);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存初始条件
	 */
	@RequiresPermissions("module:subgroupInicondit:edit")
	@Log(title = "初始条件", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SubgroupInicondit subgroupInicondit)
	{
		subgroupInicondit.setUpdateTime(new Date());
		return toAjax(subgroupIniconditService.updateSubgroupInicondit(subgroupInicondit));
	}

	@GetMapping("/edit_condition")
	public String edit_condition(SubgroupInicondit qsubgroupInicondit, ModelMap mmap)
	{
		List<ColumnInfo> columnInfoList = genMapper.selectTableColumnsByName(qsubgroupInicondit.getTableName());
		mmap.put("subgroupInicondit", qsubgroupInicondit);
		mmap.put("columnInfoList", columnInfoList);
		return prefix + "/edit";
	}
	
	/**
	 * 删除初始条件
	 */
	@RequiresPermissions("module:subgroupInicondit:remove")
	@Log(title = "初始条件", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		System.out.println(ids);
		return toAjax(subgroupIniconditService.deleteSubgroupIniconditByIds(ids));
	}

	/**
	 * 根据表明查询列
	 */
	@PostMapping("/selectIncoditionByName")
	@ResponseBody
	public List<SubgroupInicondit> selectIncoditionByName(SubgroupInicondit subgroupInicondit){
		List<SubgroupInicondit> subgroupIniconditList = subgroupIniconditService.selectSubgroupIniconditList(subgroupInicondit);
		return subgroupIniconditList;
	}

}
