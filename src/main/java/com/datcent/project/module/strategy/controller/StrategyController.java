package com.datcent.project.module.strategy.controller;

import java.util.List;

import com.datcent.common.utils.DateUtils;
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
import com.datcent.project.module.strategy.domain.Strategy;
import com.datcent.project.module.strategy.service.IStrategyService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 策略  信息操作处理
 * 
 * @author datcent
 * @date 2019-01-16
 */
@Controller
@RequestMapping("/module/strategy")
public class StrategyController extends BaseController
{
    private String prefix = "module/strategy";
	
	@Autowired
	private IStrategyService strategyService;


	@Autowired
	private IJobService jobService;
	
	@RequiresPermissions("module:strategy:view")
	@GetMapping()
	public String strategy()
	{
	    return prefix + "/strategy";
	}
	
	/**
	 * 查询策略 列表
	 */
	@RequiresPermissions("module:strategy:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Strategy strategy)
	{
		startPage();
        List<Strategy> list = strategyService.selectStrategyList(strategy);
		return getDataTable(list);
	}
	
	/**
	 * 新增策略 
	 */
	@GetMapping("/add/{jobId}")
	public String add(@PathVariable("jobId") String jobId,ModelMap mmap)
	{
		mmap.addAttribute("jobId",jobId);
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存策略 
	 */
	@RequiresPermissions("module:strategy:add")
	@Log(title = "策略 ", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Strategy strategy)
	{		
		strategy.setCreateBy(ShiroUtils.getLoginName());
		strategy.setCreateTime(DateUtils.getNowDate());
		//同时定义job中的方法和参数
		Job job=jobService.selectJobById(Long.valueOf(strategy.getStrategyJob()));
		job.setMethodParams(String.valueOf(job.getJobId()));
		jobService.updateJobCron(job);
		return toAjax(strategyService.insertStrategy(strategy));
	}

	/**
	 * 修改策略 
	 */
	@GetMapping("/edit/{strategyId}")
	public String edit(@PathVariable("strategyId") Integer strategyId, ModelMap mmap)
	{
		Strategy strategy = strategyService.selectStrategyById(strategyId);
		mmap.put("strategy", strategy);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存策略 
	 */
	@RequiresPermissions("module:strategy:edit")
	@Log(title = "策略 ", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Strategy strategy)
	{
		strategy.setUpdateBy(ShiroUtils.getLoginName());
		strategy.setUpdateTime(DateUtils.getNowDate());
		return toAjax(strategyService.updateStrategy(strategy));
	}
	
	/**
	 * 删除策略 
	 */
	@RequiresPermissions("module:strategy:remove")
	@Log(title = "策略 ", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(strategyService.deleteStrategyByIds(ids));
	}
	
}
