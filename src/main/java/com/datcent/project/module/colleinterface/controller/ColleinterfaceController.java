package com.datcent.project.module.colleinterface.controller;

import java.util.List;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.project.monitor.job.domain.Job;
import com.datcent.project.monitor.job.service.IJobService;
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
import com.datcent.project.module.colleinterface.domain.Colleinterface;
import com.datcent.project.module.colleinterface.service.IColleinterfaceService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 采集接口 信息操作处理
 * 
 * @author datcent
 * @date 2019-01-10
 */
@Controller
@RequestMapping("/module/colleinterface")
public class ColleinterfaceController extends BaseController
{
    private String prefix = "module/colleinterface";
	
	@Autowired
	private IColleinterfaceService colleinterfaceService;

	@Autowired
	private IJobService jobService;
	
	@RequiresPermissions("module:colleinterface:view")
	@GetMapping()
	public String colleinterface()
	{
	    return prefix + "/colleinterface";
	}
	
	/**
	 * 查询采集接口列表
	 */
	@RequiresPermissions("module:colleinterface:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Colleinterface colleinterface)
	{
		startPage();
        List<Colleinterface> list = colleinterfaceService.selectColleinterfaceList(colleinterface);
		return getDataTable(list);
	}
	
	/**
	 * 新增采集接口
	 */
	@GetMapping("/add")
	public String add()
		{
		return prefix + "/add";
	}
	
	/**
	 * 新增保存采集接口
	 */
	@RequiresPermissions("module:colleinterface:add")
	@Log(title = "采集接口", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Colleinterface colleinterface)
	{
		//前段url 转换异常处理
		colleinterface.setColleinterfaceParam(colleinterface.getColleinterfaceParam().replace("&amp;", "&"));
		colleinterface.setColleinterfaceId(StringUtils.getUUID());
		colleinterface.setCreateBy(ShiroUtils.getLoginName());
		colleinterface.setCreateTime(DateUtils.getNowDate());

		return toAjax(colleinterfaceService.insertColleinterface(colleinterface));
	}


	/**
	 * 修改采集接口
	 */
	@GetMapping("/edit/{colleinterfaceId}")
	public String edit(@PathVariable("colleinterfaceId") String colleinterfaceId, ModelMap mmap)
	{
		Colleinterface colleinterface = colleinterfaceService.selectColleinterfaceById(colleinterfaceId);
		mmap.put("colleinterface", colleinterface);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存采集接口
	 */
	@RequiresPermissions("module:colleinterface:edit")
	@Log(title = "采集接口", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Colleinterface colleinterface)
	{
		colleinterface.setUpdateBy(ShiroUtils.getLoginName());
		colleinterface.setUpdateTime(DateUtils.getNowDate());
		//前端url 转换异常处理
		colleinterface.setColleinterfaceParam(colleinterface.getColleinterfaceParam().replace("&amp;", "&"));
		return toAjax(colleinterfaceService.updateColleinterface(colleinterface));
	}
	
	/**
	 * 删除采集接口
	 */
	@RequiresPermissions("module:colleinterface:remove")
	@Log(title = "采集接口", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(colleinterfaceService.deleteColleinterfaceByIds(ids));
	}

}
