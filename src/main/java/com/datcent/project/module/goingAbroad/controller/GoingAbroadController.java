package com.datcent.project.module.goingAbroad.controller;

import java.util.List;

import com.datcent.common.utils.StringUtils;
import com.datcent.project.module.basicinformation.domain.Basicinformation;
import com.datcent.project.module.basicinformation.service.IBasicinformationService;
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
import com.datcent.project.module.goingAbroad.domain.GoingAbroad;
import com.datcent.project.module.goingAbroad.service.IGoingAbroadService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 出国情况登记 信息操作处理
 * 
 * @author datcent
 * @date 2019-04-01
 */
@Controller
@RequestMapping("/module/goingAbroad")
public class GoingAbroadController extends BaseController
{
    private String prefix = "module/goingAbroad";
	
	@Autowired
	private IGoingAbroadService goingAbroadService;

	@Autowired
	private IBasicinformationService basicinformationService;
	
	//@RequiresPermissions("module:goingAbroad:view")
	@GetMapping()
	public String goingAbroad()
	{
	    return prefix + "/goingAbroad";
	}
	
	/**
	 * 查询出国情况登记列表
	 */
	//@RequiresPermissions("module:goingAbroad:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoingAbroad goingAbroad)
	{
		startPage();
        List<GoingAbroad> list = goingAbroadService.selectGoingAbroadList(goingAbroad);
		return getDataTable(list);
	}
	
	/**
	 * 新增出国情况登记
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存出国情况登记
	 */
	//@RequiresPermissions("module:goingAbroad:add")
	@Log(title = "出国情况登记", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GoingAbroad goingAbroad, Basicinformation basic)
	{
		basic.setIsEdit("9");
		basicinformationService.updateBasicinformation(basic);
		if(StringUtils.isEmpty(goingAbroad.getCgqkdjId())){
			goingAbroad.setCgqkdjId(StringUtils.getUUID());
			goingAbroadService.insertGoingAbroad(goingAbroad);
		}
		int i = goingAbroadService.updateGoingAbroad(goingAbroad);
		if(i>0){
			return toAjax(i);
		}else{
			return error("信息已录入，请勿重复提交");
		}
	}

	/**
	 * 修改出国情况登记
	 */
	@GetMapping("/edit/{cgqkdjId}")
	public String edit(@PathVariable("cgqkdjId") String cgqkdjId, ModelMap mmap)
	{
		GoingAbroad goingAbroad = goingAbroadService.selectGoingAbroadById(cgqkdjId);
		mmap.put("goingAbroad", goingAbroad);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存出国情况登记
	 */
	//@RequiresPermissions("module:goingAbroad:edit")
	@Log(title = "出国情况登记", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GoingAbroad goingAbroad)
	{		
		return toAjax(goingAbroadService.updateGoingAbroad(goingAbroad));
	}
	
	/**
	 * 删除出国情况登记
	 */
	//@RequiresPermissions("module:goingAbroad:remove")
	@Log(title = "出国情况登记", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(goingAbroadService.deleteGoingAbroadByIds(ids));
	}
	
}
