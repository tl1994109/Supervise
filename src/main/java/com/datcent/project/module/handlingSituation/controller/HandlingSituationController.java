package com.datcent.project.module.handlingSituation.controller;

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
import com.datcent.project.module.handlingSituation.domain.HandlingSituation;
import com.datcent.project.module.handlingSituation.service.IHandlingSituationService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 党政纪处理及四拒情况 信息操作处理
 * 
 * @author datcent
 * @date 2019-04-01
 */
@Controller
@RequestMapping("/module/handlingSituation")
public class HandlingSituationController extends BaseController
{
    private String prefix = "module/handlingSituation";
	
	@Autowired
	private IHandlingSituationService handlingSituationService;
	
	//@RequiresPermissions("module:handlingSituation:view")
	@GetMapping()
	public String handlingSituation()
	{
	    return prefix + "/handlingSituation";
	}
	
	/**
	 * 查询党政纪处理及四拒情况列表
	 */
	//@RequiresPermissions("module:handlingSituation:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(HandlingSituation handlingSituation)
	{
		startPage();
        List<HandlingSituation> list = handlingSituationService.selectHandlingSituationList(handlingSituation);
		return getDataTable(list);
	}
	
	/**
	 * 新增党政纪处理及四拒情况
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存党政纪处理及四拒情况
	 */
	//@RequiresPermissions("module:handlingSituation:add")
	@Log(title = "党政纪处理及四拒情况", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(HandlingSituation handlingSituation)
	{


		if(StringUtils.isEmpty(handlingSituation.getCljsjqkId())){
			handlingSituation.setCljsjqkId(StringUtils.getUUID());
			handlingSituationService.insertHandlingSituation(handlingSituation);
		}
		int i = handlingSituationService.updateHandlingSituation(handlingSituation);
		if(i>0){
			return toAjax(i);
		}else{
			return error("信息已录入，请勿重复提交");
		}
	}

	/**
	 * 修改党政纪处理及四拒情况
	 */
	@GetMapping("/edit/{cljsjqkId}")
	public String edit(@PathVariable("cljsjqkId") String cljsjqkId, ModelMap mmap)
	{
		HandlingSituation handlingSituation = handlingSituationService.selectHandlingSituationById(cljsjqkId);
		mmap.put("handlingSituation", handlingSituation);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存党政纪处理及四拒情况
	 */
	//@RequiresPermissions("module:handlingSituation:edit")
	@Log(title = "党政纪处理及四拒情况", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(HandlingSituation handlingSituation)
	{		
		return toAjax(handlingSituationService.updateHandlingSituation(handlingSituation));
	}
	
	/**
	 * 删除党政纪处理及四拒情况
	 */
	//@RequiresPermissions("module:handlingSituation:remove")
	@Log(title = "党政纪处理及四拒情况", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(handlingSituationService.deleteHandlingSituationByIds(ids));
	}
	
}
