package com.datcent.project.module.dispositionForm.controller;

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
import com.datcent.project.module.dispositionForm.domain.DispositionForm;
import com.datcent.project.module.dispositionForm.service.IDispositionFormService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 问题处置单 信息操作处理
 * 
 * @author datcent
 * @date 2019-01-18
 */
@Controller
@RequestMapping("/module/dispositionForm")
public class DispositionFormController extends BaseController
{
    private String prefix = "module/dispositionForm";
	
	@Autowired
	private IDispositionFormService dispositionFormService;
	
	@RequiresPermissions("module:dispositionForm:view")
	@GetMapping()
	public String dispositionForm()
	{
	    return prefix + "/dispositionForm";
	}
	
	/**
	 * 查询问题处置单列表
	 */
	@RequiresPermissions("module:dispositionForm:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(DispositionForm dispositionForm)
	{
		startPage();
        List<DispositionForm> list = dispositionFormService.selectDispositionFormList(dispositionForm);
		return getDataTable(list);
	}
	
	/**
	 * 新增问题处置单
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存问题处置单
	 */
	@RequiresPermissions("module:dispositionForm:add")
	@Log(title = "问题处置单", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(DispositionForm dispositionForm)
	{		
		return toAjax(dispositionFormService.insertDispositionForm(dispositionForm));
	}

	/**
	 * 修改问题处置单
	 */
	@GetMapping("/edit/{formId}")
	public String edit(@PathVariable("formId") String formId, ModelMap mmap)
	{
		DispositionForm dispositionForm = dispositionFormService.selectDispositionFormById(formId);
		mmap.put("dispositionForm", dispositionForm);
	    return prefix + "/edit";
	}

	/**
	 * 修改问题处置单
	 */
	@GetMapping("/select/{formId}")
	@ResponseBody
	public DispositionForm select(@PathVariable("formId") String formId, ModelMap mmap)
	{
		DispositionForm dispositionForm = dispositionFormService.selectDispositionFormById(formId);
		return dispositionForm;
	}
	
	/**
	 * 修改保存问题处置单
	 */
	@RequiresPermissions("module:dispositionForm:edit")
	@Log(title = "问题处置单", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(DispositionForm dispositionForm)
	{		
		return toAjax(dispositionFormService.updateDispositionForm(dispositionForm));
	}
	
	/**
	 * 删除问题处置单
	 */
	@RequiresPermissions("module:dispositionForm:remove")
	@Log(title = "问题处置单", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(dispositionFormService.deleteDispositionFormByIds(ids));
	}
	
}
