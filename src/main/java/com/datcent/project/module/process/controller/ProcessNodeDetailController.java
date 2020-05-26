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
import com.datcent.project.module.process.domain.ProcessNodeDetail;
import com.datcent.project.module.process.service.IProcessNodeDetailService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 流程节点  信息操作处理
 * 
 * @author datcent
 * @date 2018-11-02
 */
@Controller
@RequestMapping("/module/processNodeDetail")
public class ProcessNodeDetailController extends BaseController
{
    private String prefix = "module/processNodeDetail";
	
	@Autowired
	private IProcessNodeDetailService processNodeDetailService;
	
	@RequiresPermissions("module:processNodeDetail:view")
	@GetMapping()
	public String processNodeDetail()
	{
	    return prefix + "/processNodeDetail";
	}
	
	/**
	 * 查询流程节点 列表
	 */
	@RequiresPermissions("module:processNodeDetail:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProcessNodeDetail processNodeDetail)
	{
		startPage();
        List<ProcessNodeDetail> list = processNodeDetailService.selectProcessNodeDetailList(processNodeDetail);
		return getDataTable(list);
	}
	
	/**
	 * 新增流程节点 
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存流程节点 
	 */
	@RequiresPermissions("module:processNodeDetail:add")
	@Log(title = "流程节点 ", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProcessNodeDetail processNodeDetail)
	{		
		return toAjax(processNodeDetailService.insertProcessNodeDetail(processNodeDetail));
	}

	/**
	 * 修改流程节点 
	 */
	@GetMapping("/edit/{processId}")
	public String edit(@PathVariable("processId") String processId, ModelMap mmap)
	{
		ProcessNodeDetail processNodeDetail = processNodeDetailService.selectProcessNodeDetailById(processId);
		mmap.put("processNodeDetail", processNodeDetail);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存流程节点 
	 */
	@RequiresPermissions("module:processNodeDetail:edit")
	@Log(title = "流程节点 ", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProcessNodeDetail processNodeDetail)
	{		
		return toAjax(processNodeDetailService.updateProcessNodeDetail(processNodeDetail));
	}
	
	/**
	 * 删除流程节点 
	 */
	@RequiresPermissions("module:processNodeDetail:remove")
	@Log(title = "流程节点 ", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(processNodeDetailService.deleteProcessNodeDetailByIds(ids));
	}
	
}
