package com.datcent.project.monitor.job.controller;

import com.datcent.common.utils.poi.ExcelUtil;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.domain.AjaxResult;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.project.module.colleinterface.domain.Colleinterface;
import com.datcent.project.module.colleinterface.service.IColleinterfaceService;
import com.datcent.project.module.strategy.domain.Strategy;
import com.datcent.project.module.strategy.service.IStrategyService;
import com.datcent.project.monitor.job.domain.Job;
import com.datcent.project.monitor.job.service.IJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 调度任务信息操作处理
 * 
 * @author datcent
 */
@Controller
@RequestMapping("/monitor/strategyjob")
public class StrategyJobController extends BaseController
{
    private String prefix = "monitor/strategyjob";

    @Autowired
    private IJobService jobService;


    @Autowired
    private IStrategyService strategyService;

    @RequiresPermissions("monitor:strategyjob:view")
    @GetMapping()
    public String job()
    {
        return prefix + "/job";
    }

    @RequiresPermissions("monitor:strategyjob:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Job job)
    {
        startPage();
        //查询  2：筛选任务
        job.setJobType("2");
        List<Job> list = jobService.selectJobList(job);
        return getDataTable(list);
    }

    @Log(title = "定时任务", businessType = BusinessType.EXPORT)
    @RequiresPermissions("monitor:strategyjob:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Job job)
    {
        List<Job> list = jobService.selectJobList(job);
        ExcelUtil<Job> util = new ExcelUtil<Job>(Job.class);
        return util.exportExcel(list, "job");
    }

    @Log(title = "定时任务", businessType = BusinessType.DELETE)
    @RequiresPermissions("monitor:strategyjob:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            jobService.deleteJobByIds(ids);
            return success();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return error(e.getMessage());
        }
    }

    /**
     * 任务调度状态修改
     */
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @RequiresPermissions("monitor:strategyjob:changeStatus")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(Job job)
    {
        Job jobs=jobService.selectJobById(job.getJobId());
        jobs.setStatus(job.getStatus());
        Strategy strategy=new Strategy();
        strategy.setStrategyJob( String.valueOf(job.getJobId()));
        int list=strategyService.selectStrategyList(strategy).size();
        if (list>0){
            return toAjax(jobService.changeStatus(jobs));
        }else {
            return error("没有可执行的方法！");
        }
    }

    /**
     * 任务调度立即执行一次
     */
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @RequiresPermissions("monitor:strategyjob:changeStatus")
    @PostMapping("/run")
    @ResponseBody
    public AjaxResult run(Job job)
    {
        Strategy strategy=new Strategy();
        strategy.setStrategyJob( String.valueOf(job.getJobId()));
        int list=strategyService.selectStrategyList(strategy).size();
        if (list>0){
            return toAjax(jobService.run(job));
        }else{
            return error("没有可执行的方法！");
        }

    }

    /**
     * 新增调度
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存调度
     */
    @Log(title = "定时任务", businessType = BusinessType.INSERT)
    @RequiresPermissions("monitor:strategyjob:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Job job)
    {
        return toAjax(jobService.insertJobCron(job));
    }

    /**
     * 修改调度
     */
    @GetMapping("/edit/{jobId}")
    public String edit(@PathVariable("jobId") Long jobId, ModelMap mmap)
    {
        mmap.put("job", jobService.selectJobById(jobId));
        return prefix + "/edit";
    }

    /**
     * 修改保存调度
     */
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @RequiresPermissions("monitor:strategyjob:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Job job)
    {
        return toAjax(jobService.updateJobCron(job));
    }


    /**
     * 查询列表详细
     */
    @RequiresPermissions("monitor:strategyjob:detail")
    @GetMapping("/detail/{jobId}")
    public String detail(@PathVariable("jobId") String jobId, ModelMap mmap)
    {
        mmap.put("jobId",jobId);
        return "module/strategy/strategy";
    }


}
