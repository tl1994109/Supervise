package com.datcent.project.tool.diagram.controller;

import java.util.List;

import com.datcent.common.utils.StringUtils;
import com.datcent.project.tool.diagramSubgroup.domain.DiagramSubgroup;
import com.datcent.project.tool.diagramSubgroup.service.IDiagramSubgroupService;
import com.datcent.project.tool.diagram.domain.Diagram;
import com.datcent.project.tool.diagram.service.IDiagramService;
import com.datcent.project.tool.layout.domain.Layout;
import com.datcent.project.tool.layout.service.ILayoutService;
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
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 组图管理 信息操作处理
 * 
 * @author datcent
 * @date 2018-11-29
 */
@Controller
@RequestMapping("/tool/diagram")
public class DiagramController extends BaseController
{
    private String prefix = "tool/diagram";
	
	@Autowired
	private IDiagramService diagramService;

	@Autowired
	private IDiagramSubgroupService diagramSubgroupService;

    @Autowired
    private ILayoutService iLayoutService;




	@RequiresPermissions("tool:diagram:view")
	@GetMapping()
	public String diagram()
	{
	    return prefix + "/diagram";
	}
	
	/**
	 * 查询组图管理列表
	 */
	@RequiresPermissions("tool:diagram:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Diagram diagram)
	{
		startPage();
        List<Diagram> list = diagramService.selectDiagramList(diagram);
		return getDataTable(list);
	}

	/**
	 * 查询组图管理列表
	 */
	@RequiresPermissions("tool:diagram:list")
	@GetMapping("/list")
	@ResponseBody
	public TableDataInfo getlist(Diagram diagram)
	{
		startPage();
		List<Diagram> list = diagramService.selectDiagramList(diagram);
		return getDataTable(list);
	}
	
	/**
	 * 新增组图管理
	 */
	@GetMapping("/add")
	public String add(Layout layout,ModelMap modelMap)
	{
		System.out.print("新增======");

        List<Layout> layouts = iLayoutService.selectLayoutList(layout);
        modelMap.put("layouts",layouts);
		return prefix + "/add";
	}
	
	/**
	 * 新增保存组图管理
	 */
	@RequiresPermissions("tool:diagram:add")
	@Log(title = "组图管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Diagram diagram,String[] subgroupId)
	{
		diagram.setDiagramId(StringUtils.getUUID());
		//同时添加组图组件关系表
		for(String sub:subgroupId){
			DiagramSubgroup diagramSubgroup=new DiagramSubgroup(diagram.getDiagramId(),sub);
			diagramSubgroupService.insertDiagramSubgroup(diagramSubgroup);
		}
		return toAjax(diagramService.insertDiagram(diagram));
	}

	/**
	 * 修改组图管理
	 */
	@GetMapping("/edit/{diagramId}")
	public String edit(@PathVariable("diagramId") String diagramId, ModelMap mmap,DiagramSubgroup diagramSubgroup)
	{
		Diagram diagram = diagramService.selectDiagramById(diagramId);
		//同时查看组图下所有有关组件关系
		List<Layout> layouts = iLayoutService.selectLayoutList(null);
		mmap.put("layouts",layouts);
		diagramSubgroup.setDiagramId(diagramId);
		List<DiagramSubgroup> diagramSubgroups=diagramSubgroupService.selectDiagramSubgroupList(diagramSubgroup);
		mmap.put("diagramSubgroups",diagramSubgroups);
		mmap.put("diagram", diagram);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存组图管理
	 */
	@RequiresPermissions("tool:diagram:edit")
	@Log(title = "组图管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Diagram diagram)
	{		
		return toAjax(diagramService.updateDiagram(diagram));
	}
	
	/**
	 * 删除组图管理
	 */
	@RequiresPermissions("tool:diagram:remove")
	@Log(title = "组图管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids,DiagramSubgroup diagramSubgroup)
	{		
		//同步删除该组图下所有组件和布局
		//查找该组图下的所有组件对应关系
		diagramSubgroup.setDiagramId(ids);
		List<DiagramSubgroup> diagramSubgroups=diagramSubgroupService.selectDiagramSubgroupList(diagramSubgroup);
		for(DiagramSubgroup sub:diagramSubgroups){
			diagramSubgroupService.deleteDiagramSubgroupByIds(sub.getId());
		}
		return toAjax(diagramService.deleteDiagramByIds(ids));
	}
	
}
