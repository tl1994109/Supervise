package com.datcent.project.system.layout.controller;

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
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.project.tool.layout.domain.Layout;
import com.datcent.project.tool.layout.service.ILayoutService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 布局管理 信息操作处理
 * 
 * @author datcent
 * @date 2018-11-22
 */
@Controller
@RequestMapping("/tool/layout")
public class LayoutController extends BaseController
{
    private String prefix = "tool/layout";
	
	@Autowired
	private ILayoutService layoutService;
	
	@RequiresPermissions("tool:layout:view")
	@GetMapping()
	public String layout()
	{
	    return prefix + "/layout";
	}
	
	/**
	 * 查询布局管理列表
	 */
	@RequiresPermissions("tool:layout:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Layout layout)
	{
		startPage();
        List<Layout> list = layoutService.selectLayoutList(layout);
		return getDataTable(list);
	}
	
	/**
	 * 新增布局管理
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存布局管理
	 */
	@RequiresPermissions("tool:layout:add")
	@Log(title = "布局管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Layout layout)
	{		
		return toAjax(layoutService.insertLayout(layout));
	}

	/**
	 * 修改布局管理
	 */
	@GetMapping("/edit/{layoutId}")
	public String edit(@PathVariable("layoutId") String layoutId, ModelMap mmap)
	{
		Layout layout = layoutService.selectLayoutById(layoutId);
		mmap.put("layout", layout);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存布局管理
	 */
	@RequiresPermissions("tool:layout:edit")
	@Log(title = "布局管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Layout layout)
	{		
		return toAjax(layoutService.updateLayout(layout));
	}
	
	/**
	 * 删除布局管理
	 */
	@RequiresPermissions("tool:layout:remove")
	@Log(title = "布局管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(layoutService.deleteLayoutByIds(ids));
	}

	/**
	 * 删除布局管理
	 */
	@RequiresPermissions("tool:layout:getlayout")
	@Log(title = "布局管理", businessType = BusinessType.DELETE)
	@GetMapping( "/getlayout")
	@ResponseBody
	public Layout getLayout(String layoutId){
		return layoutService.selectLayoutById(layoutId);
	}
}
