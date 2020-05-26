package com.datcent.project.tool.diagramSubgroup.controller;

import java.util.List;

import com.datcent.project.tool.diagramSubgroup.service.IDiagramSubgroupService;
import org.apache.ibatis.annotations.Param;
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
import com.datcent.project.tool.diagramSubgroup.domain.DiagramSubgroup;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 组图组件关系 信息操作处理
 * 
 * @author datcent
 * @date 2018-11-30
 */
@Controller
@RequestMapping("/tool/diagramSubgroup")
public class DiagramSubgroupController extends BaseController
{
    private String prefix = "tool/diagramSubgroup";
	
	@Autowired
	private IDiagramSubgroupService diagramSubgroupService;
	
	@RequiresPermissions("tool:diagramSubgroup:view")
	@GetMapping()
	public String diagramSubgroup()
	{
	    return prefix + "/diagramSubgroup";
	}
	
	/**
	 * 查询组图组件关系列表
	 */
	@RequiresPermissions("tool:diagramSubgroup:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(DiagramSubgroup diagramSubgroup)
	{
		startPage();
        List<DiagramSubgroup> list = diagramSubgroupService.selectDiagramSubgroupList(diagramSubgroup);
		return getDataTable(list);
	}
	
	/**
	 * 新增组图组件关系
	 */
	@GetMapping("/add/{addSubgroupId}/{subgroupId}")
	public String add(@PathVariable("addSubgroupId")String addSubgroupId, @PathVariable("subgroupId")String subgroupId,ModelMap modelMap)
	{
		modelMap.put("addSubgroupId",addSubgroupId);
		modelMap.put("subgroupId",subgroupId);
		return prefix + "/add";
	}
	
	/**
	 * 新增保存组图组件关系
	 */
	@RequiresPermissions("tool:diagramSubgroup:add")
	@Log(title = "组图组件关系", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(DiagramSubgroup diagramSubgroup)
	{		
		return toAjax(diagramSubgroupService.insertDiagramSubgroup(diagramSubgroup));
	}

	/**
	 * 修改组图组件关系
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		DiagramSubgroup diagramSubgroup = diagramSubgroupService.selectDiagramSubgroupById(id);
		mmap.put("diagramSubgroup", diagramSubgroup);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存组图组件关系
	 */
	@RequiresPermissions("tool:diagramSubgroup:edit")
	@Log(title = "组图组件关系", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(DiagramSubgroup diagramSubgroup)
	{		
		return toAjax(diagramSubgroupService.updateDiagramSubgroup(diagramSubgroup));
	}
	
	/**
	 * 删除组图组件关系
	 */
	@RequiresPermissions("tool:diagramSubgroup:remove")
	@Log(title = "组图组件关系", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(diagramSubgroupService.deleteDiagramSubgroupByIds(ids));
	}
	
}
