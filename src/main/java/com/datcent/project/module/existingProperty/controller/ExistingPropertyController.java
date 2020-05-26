package com.datcent.project.module.existingProperty.controller;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.datcent.common.utils.StringUtils;
import com.datcent.project.module.annualIncome.domain.AnnualIncome;
import com.datcent.project.module.annualIncome.service.IAnnualIncomeService;
import com.datcent.project.module.basicinformation.domain.Basicinformation;
import com.datcent.project.module.basicinformation.service.IBasicinformationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.project.module.existingProperty.domain.ExistingProperty;
import com.datcent.project.module.existingProperty.service.IExistingPropertyService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 现有财产状况 信息操作处理
 * 
 * @author datcent
 * @date 2019-04-01
 */
@Controller
@RequestMapping("/module/existingProperty")
public class ExistingPropertyController extends BaseController
{
    private String prefix = "module/existingProperty";
	
	@Autowired
	private IExistingPropertyService existingPropertyService;

	@Autowired
	private IBasicinformationService basicinformationService;

	@Autowired
	private IAnnualIncomeService annualIncomeService;
	
	//@RequiresPermissions("module:existingProperty:view")
	@GetMapping()
	public String existingProperty()
	{
	    return prefix + "/existingProperty";
	}
	
	/**
	 * 查询现有财产状况列表
	 */
	//@RequiresPermissions("module:existingProperty:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ExistingProperty existingProperty)
	{
		startPage();
        List<ExistingProperty> list = existingPropertyService.selectExistingPropertyList(existingProperty);
		return getDataTable(list);
	}
	
	/**
	 * 新增现有财产状况
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存现有财产状况
	 */
	//@RequiresPermissions("module:existingProperty:add")
	@Log(title = "现有财产状况", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	public AjaxResult addSave(AnnualIncome annualIncome, String jtccqk, Basicinformation basic)
	{
		//已填家庭成員信息
		List<ExistingProperty> existingProperties = JSONObject.parseArray(jtccqk, ExistingProperty.class);
		int i=0;
		for (ExistingProperty property : existingProperties) {
			if (StringUtils.isEmpty(property.getJtccqkId())) {
				if (StringUtils.isEmpty(property.getXycczkFcdz())&&
						StringUtils.isEmpty(property.getXycczkYt())&&
						StringUtils.isEmpty(property.getXycczkXz())&&
						StringUtils.isEmpty(property.getXycczkMj())&&
						StringUtils.isEmpty(property.getXycczkFcly())){
					continue;
				}else {
					property.setJtccqkId(StringUtils.getUUID());
					existingPropertyService.insertExistingProperty(property);
				}
			}
				int row=existingPropertyService.updateExistingProperty(property);
				i+=row;
			}
		basic.setIsEdit("9");
		basicinformationService.updateBasicinformation(basic);
		if (StringUtils.isNotEmpty(annualIncome.getId())){
            int row=annualIncomeService.updateAnnualIncome(annualIncome);
            i+=row;
        }else{
            int row=annualIncomeService.insertAnnualIncome(annualIncome);
            i+=row;
        }
		if(i>0){
			return success();
		}else{
			return error("信息已录入，请勿重复提交");
		}
	}

	/**
	 * 修改现有财产状况
	 */
	@GetMapping("/edit/{jtccqkId}")
	public String edit(@PathVariable("jtccqkId") String jtccqkId, ModelMap mmap)
	{
		ExistingProperty existingProperty = existingPropertyService.selectExistingPropertyById(jtccqkId);
		mmap.put("existingProperty", existingProperty);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存现有财产状况
	 */
	//@RequiresPermissions("module:existingProperty:edit")
	@Log(title = "现有财产状况", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ExistingProperty existingProperty)
	{		
		return toAjax(existingPropertyService.updateExistingProperty(existingProperty));
	}
	
	/**
	 * 删除现有财产状况
	 */
	//@RequiresPermissions("module:existingProperty:remove")
	@Log(title = "现有财产状况", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(existingPropertyService.deleteExistingPropertyByIds(ids));
	}
	
}
