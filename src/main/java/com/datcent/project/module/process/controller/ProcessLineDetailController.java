package com.datcent.project.module.process.controller;

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
import com.datcent.project.module.process.domain.ProcessLineDetail;
import com.datcent.project.module.process.service.IProcessLineDetailService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 流程线条  信息操作处理
 * 
 * @author datcent
 * @date 2018-11-02
 */
@Controller
@RequestMapping("/module/processLineDetail")
public class ProcessLineDetailController extends BaseController
{
    private String prefix = "module/processLineDetail";
	
	@Autowired
	private IProcessLineDetailService processLineDetailService;
	
	@RequiresPermissions("module:processLineDetail:view")
	@GetMapping()
	public String processLineDetail()
	{
	    return prefix + "/processLineDetail";
	}
	
	/**
	 * 查询流程线条 列表
	 */
	@RequiresPermissions("module:processLineDetail:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProcessLineDetail processLineDetail)
	{
		startPage();
        List<ProcessLineDetail> list = processLineDetailService.selectProcessLineDetailList(processLineDetail);
		return getDataTable(list);
	}
	
	/**
	 * 新增流程线条 
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存流程线条 
	 */
	@RequiresPermissions("module:processLineDetail:add")
	@Log(title = "流程线条 ", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProcessLineDetail processLineDetail)
	{		
		return toAjax(processLineDetailService.insertProcessLineDetail(processLineDetail));
	}

	/**
	 * 修改流程线条 
	 */
	@GetMapping("/edit/{processId}")
	public String edit(@PathVariable("processId") String processId, ModelMap mmap)
	{
		ProcessLineDetail processLineDetail = processLineDetailService.selectProcessLineDetailById(processId);
		mmap.put("processLineDetail", processLineDetail);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存流程线条 
	 */
	@RequiresPermissions("module:processLineDetail:edit")
	@Log(title = "流程线条 ", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProcessLineDetail processLineDetail)
	{		
		return toAjax(processLineDetailService.updateProcessLineDetail(processLineDetail));
	}
	
	/**
	 * 删除流程线条 
	 */
	@RequiresPermissions("module:processLineDetail:remove")
	@Log(title = "流程线条 ", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(processLineDetailService.deleteProcessLineDetailByIds(ids));
	}
	
}
