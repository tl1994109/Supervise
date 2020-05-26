package com.datcent.project.module.appraise.controller;

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
import com.datcent.project.module.appraise.domain.Appraise;
import com.datcent.project.module.appraise.service.IAppraiseService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 任务评价 信息操作处理
 * 
 * @author datcent
 * @date 2019-01-24
 */
@Controller
@RequestMapping("/module/appraise")
public class AppraiseController extends BaseController
{
    private String prefix = "module/appraise";
	
	@Autowired
	private IAppraiseService appraiseService;
	
	@RequiresPermissions("module:appraise:view")
	@GetMapping()
	public String appraise()
	{
	    return prefix + "/appraise";
	}
	
	/**
	 * 查询任务评价列表
	 */
	@RequiresPermissions("module:appraise:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Appraise appraise)
	{
		startPage();
        List<Appraise> list = appraiseService.selectAppraiseList(appraise);
		return getDataTable(list);
	}
	
	/**
	 * 新增任务评价
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存任务评价
	 */
	@RequiresPermissions("module:information:appraise")
	@Log(title = "任务评价", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Appraise appraise)
	{
		Appraise newAppraise = new Appraise();
		newAppraise.setAppraiseTaskid(appraise.getAppraiseTaskid());
		return toAjax(appraiseService.insertAppraise(appraise));
	}

	/**
	 * 修改任务评价
	 */
	@GetMapping("/edit/{appraiseId}")
	public String edit(@PathVariable("appraiseId") String appraiseId, ModelMap mmap)
	{
		Appraise appraise = appraiseService.selectAppraiseById(appraiseId);
		mmap.put("appraise", appraise);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存任务评价
	 */
	@RequiresPermissions("module:appraise:edit")
	@Log(title = "任务评价", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Appraise appraise)
	{		
		return toAjax(appraiseService.updateAppraise(appraise));
	}
	
	/**
	 * 删除任务评价
	 */
	@RequiresPermissions("module:appraise:remove")
	@Log(title = "任务评价", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(appraiseService.deleteAppraiseByIds(ids));
	}
	
}
