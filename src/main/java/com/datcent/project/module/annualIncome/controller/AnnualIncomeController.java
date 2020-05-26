package com.datcent.project.module.annualIncome.controller;

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
import com.datcent.project.module.annualIncome.domain.AnnualIncome;
import com.datcent.project.module.annualIncome.service.IAnnualIncomeService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 家庭全年收入情况 信息操作处理
 * 
 * @author datcent
 * @date 2019-04-03
 */
@Controller
@RequestMapping("/module/annualIncome")
public class AnnualIncomeController extends BaseController
{
    private String prefix = "module/annualIncome";
	
	@Autowired
	private IAnnualIncomeService annualIncomeService;
	
	//@RequiresPermissions("module:annualIncome:view")
	@GetMapping()
	public String annualIncome()
	{
	    return prefix + "/annualIncome";
	}
	
	/**
	 * 查询家庭全年收入情况列表
	 */
//	@RequiresPermissions("module:annualIncome:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(AnnualIncome annualIncome)
	{
		startPage();
        List<AnnualIncome> list = annualIncomeService.selectAnnualIncomeList(annualIncome);
		return getDataTable(list);
	}
	
	/**
	 * 新增家庭全年收入情况
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存家庭全年收入情况
	 */
//	@RequiresPermissions("module:annualIncome:add")
	@Log(title = "家庭全年收入情况", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(AnnualIncome annualIncome)
	{		
		return toAjax(annualIncomeService.insertAnnualIncome(annualIncome));
	}

	/**
	 * 修改家庭全年收入情况
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		AnnualIncome annualIncome = annualIncomeService.selectAnnualIncomeById(id);
		mmap.put("annualIncome", annualIncome);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存家庭全年收入情况
	 */
//	@RequiresPermissions("module:annualIncome:edit")
	@Log(title = "家庭全年收入情况", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(AnnualIncome annualIncome)
	{		
		return toAjax(annualIncomeService.updateAnnualIncome(annualIncome));
	}
	
	/**
	 * 删除家庭全年收入情况
	 */
	//@RequiresPermissions("module:annualIncome:remove")
	@Log(title = "家庭全年收入情况", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(annualIncomeService.deleteAnnualIncomeByIds(ids));
	}
	
}
