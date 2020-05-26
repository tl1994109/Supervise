package com.datcent.project.module.lists.controller;

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
import com.datcent.project.module.lists.domain.Lists;
import com.datcent.project.module.lists.service.IListsService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 案件列 信息操作处理
 * 
 * @author datcent
 * @date 2018-12-26
 */
@Controller
@RequestMapping("/module/lists")
public class ListsController extends BaseController
{
    private String prefix = "module/lists";
	
	@Autowired
	private IListsService listsService;
	
	@RequiresPermissions("module:lists:view")
	@GetMapping()
	public String lists()
	{
	    return prefix + "/lists";
	}
	
	/**
	 * 查询案件列列表
	 */
	@RequiresPermissions("module:lists:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Lists lists)
	{
		startPage();
        List<Lists> list = listsService.selectListsList(lists);
		return getDataTable(list);
	}
	
	/**
	 * 新增案件列
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存案件列
	 */
	@RequiresPermissions("module:lists:add")
	@Log(title = "案件列", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Lists lists)
	{		
		return toAjax(listsService.insertLists(lists));
	}

	/**
	 * 修改案件列
	 */
	@GetMapping("/edit/{zhcxlistStringajbh}")
	public String edit(@PathVariable("zhcxlistStringajbh") String zhcxlistStringajbh, ModelMap mmap)
	{
		Lists lists = listsService.selectListsById(zhcxlistStringajbh);
		mmap.put("lists", lists);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存案件列
	 */
	@RequiresPermissions("module:lists:edit")
	@Log(title = "案件列", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Lists lists)
	{		
		return toAjax(listsService.updateLists(lists));
	}
	
	/**
	 * 删除案件列
	 */
	@RequiresPermissions("module:lists:remove")
	@Log(title = "案件列", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(listsService.deleteListsByIds(ids));
	}
	
}
