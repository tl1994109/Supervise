package com.datcent.project.module.implementationSystem.controller;

import java.util.List;

import com.datcent.common.utils.StringUtils;
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
import com.datcent.project.module.implementationSystem.domain.ImplementationSystem;
import com.datcent.project.module.implementationSystem.service.IImplementationSystemService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 落实规章制度情况 信息操作处理
 * 
 * @author datcent
 * @date 2019-04-01
 */
@Controller
@RequestMapping("/module/implementationSystem")
public class ImplementationSystemController extends BaseController
{
    private String prefix = "module/implementationSystem";
	
	@Autowired
	private IImplementationSystemService implementationSystemService;
	
	//@RequiresPermissions("module:implementationSystem:view")
	@GetMapping()
	public String implementationSystem()
	{
	    return prefix + "/implementationSystem";
	}
	
	/**
	 * 查询落实规章制度情况列表
	 */
	//@RequiresPermissions("module:implementationSystem:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ImplementationSystem implementationSystem)
	{
		startPage();
        List<ImplementationSystem> list = implementationSystemService.selectImplementationSystemList(implementationSystem);
		return getDataTable(list);
	}
	
	/**
	 * 新增落实规章制度情况
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存落实规章制度情况
	 */
	//@RequiresPermissions("module:implementationSystem:add")
	@Log(title = "落实规章制度情况", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ImplementationSystem implementationSystem)
	{
		if(StringUtils.isEmpty(implementationSystem.getLsgzzdqkId())){
			implementationSystem.setLsgzzdqkId(StringUtils.getUUID());
			implementationSystemService.insertImplementationSystem(implementationSystem);
		}
		int i = implementationSystemService.updateImplementationSystem(implementationSystem);
		if(i>0){
			return toAjax(i);
		}else{
			return error("保存失败！");
		}
	}

	/**
	 * 修改落实规章制度情况
	 */
	@GetMapping("/edit/{lsgzzdqkId}")
	public String edit(@PathVariable("lsgzzdqkId") String lsgzzdqkId, ModelMap mmap)
	{
		ImplementationSystem implementationSystem = implementationSystemService.selectImplementationSystemById(lsgzzdqkId);
		mmap.put("implementationSystem", implementationSystem);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存落实规章制度情况
	 */
	//@RequiresPermissions("module:implementationSystem:edit")
	@Log(title = "落实规章制度情况", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ImplementationSystem implementationSystem)
	{		
		return toAjax(implementationSystemService.updateImplementationSystem(implementationSystem));
	}
	
	/**
	 * 删除落实规章制度情况
	 */
	//@RequiresPermissions("module:implementationSystem:remove")
	@Log(title = "落实规章制度情况", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(implementationSystemService.deleteImplementationSystemByIds(ids));
	}
	
}
