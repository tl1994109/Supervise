package com.datcent.project.monitor.job.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.datcent.common.utils.StringUtils;
import com.datcent.project.module.colleinterface.domain.Colleinterface;
import com.datcent.project.module.colleinterface.service.IColleinterfaceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.datcent.common.utils.poi.ExcelUtil;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.domain.AjaxResult;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.project.monitor.job.domain.Job;
import com.datcent.project.monitor.job.service.IJobService;

/**
 * 调度任务信息操作处理
 * 
 * @author datcent
 */
@Controller
@RequestMapping("/monitor/job")
public class JobController extends BaseController
{
    private String prefix = "monitor/job";

    @Autowired
    private IJobService jobService;


    @Autowired
    private IColleinterfaceService colleinterfaceService;

    @RequiresPermissions("monitor:job:view")
    @GetMapping()
    public String job()
    {
        return prefix + "/job";
    }

    @RequiresPermissions("monitor:job:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Job job)
    {
        startPage();
        //查询  1：采集任务
        job.setJobType("1");
        List<Job> list = jobService.selectJobList(job);
        return getDataTable(list);
    }

    @Log(title = "定时任务", businessType = BusinessType.EXPORT)
    @RequiresPermissions("monitor:job:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Job job)
    {
        List<Job> list = jobService.selectJobList(job);
        ExcelUtil<Job> util = new ExcelUtil<Job>(Job.class);
        return util.exportExcel(list, "job");
    }

    @Log(title = "定时任务", businessType = BusinessType.DELETE)
    @RequiresPermissions("monitor:job:remove")
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
    @RequiresPermissions("monitor:job:changeStatus")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(Job job)
    {
        Colleinterface colleinterface=new Colleinterface();
        colleinterface.setColleinterfaceJob( String.valueOf(job.getJobId()));
        int list=colleinterfaceService.selectColleinterfaceList(colleinterface).size();
        return toAjax(jobService.changeStatus(job));
    }

    /**
     * 任务调度立即执行一次
     */
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @RequiresPermissions("monitor:job:changeStatus")
    @PostMapping("/run")
    @ResponseBody
    public AjaxResult run(Job job)
    {
        job=jobService.selectJobById(job.getJobId());
       return toAjax(jobService.run(job));
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
    @RequiresPermissions("monitor:job:add")
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
    @RequiresPermissions("monitor:job:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Job job)
    {
        return toAjax(jobService.updateJobCron(job));
    }



    /**
     * 查询列表详细
     */
    @RequiresPermissions("monitor:job:detail")
    @GetMapping("/detail/{jobId}")
    public String  detail(@PathVariable("jobId") String jobId, ModelMap mmap)
    {
        mmap.put("jobId",jobId);
        return prefix+"/detail";
    }
    /**
     * 查询列表详细
     */
    @RequiresPermissions("monitor:job:collist")
    @PostMapping("/collist")
    @ResponseBody
    public TableDataInfo collist(String jobId,Colleinterface colleinterface, ModelMap mmap)
    {
        Job job=jobService.selectJobById( Long.parseLong(jobId));
        String methodParams=job.getMethodParams();
        List<Colleinterface> list=new ArrayList<Colleinterface>();
        startPage();
        mmap.put("jobId",jobId);
        String[] colleinterfaceIds = methodParams.split(",");
        for (int i = 0; i < colleinterfaceIds.length; i++) {
            colleinterface=colleinterfaceService.selectColleinterfaceById(colleinterfaceIds[i]);
            if (colleinterface!=null){
                list.add(colleinterface);
            }
        }
        return getDataTable(list);
    }


    /**
     * 定时任务新增接口
     */
    @RequiresPermissions("monitor:job:addcol")
    @GetMapping("/addcol/{jobId}")
    public String  addcol(@PathVariable("jobId") String jobId, ModelMap mmap)
    {
        mmap.put("jobId",jobId);
        return prefix+"/addcolleinterface";
    }

    /**
     * 定时任务新增接口
     */
    @RequiresPermissions("monitor:job:addcol")
    @PostMapping("/addcol")
    @ResponseBody
    public AjaxResult  addcol( String jobId,String methodParams, ModelMap mmap)
    {
        Job job=jobService.selectJobById( Long.parseLong(jobId));
        job.setMethodParams(methodParams);
        return toAjax(jobService.updateJobCron(job));
    }

    @RequiresPermissions("monitor:job:removecol")
    @PostMapping("/removecol")
    @ResponseBody
    public AjaxResult removecol(String rows,String jobId)
    {
        String[] id = rows.split(",");
        Job job = jobService.selectJobById(Long.parseLong(jobId));
        String[] params = job.getMethodParams().split(",");
        String[] arrResult = arrContrast(params, id);
        String newParams="";
        for (String s : arrResult) {
            newParams+=(s+",");
        }
        job.setMethodParams(newParams.substring(0,newParams.length()-1));
        return toAjax(jobService.updateJobCron(job));
    }


    //处理数组字符
    private  String[] arrContrast(String[] arr1, String[] arr2){
        List<String> list = new LinkedList<String>();
        for (String str : arr1) {                //处理第一个数组,list里面的值为1,2,3,4
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        for (String str : arr2) {      //如果第二个数组存在和第一个数组相同的值，就删除
            if(list.contains(str)){
                list.remove(str);
            }
        }
        String[] result = {};   //创建空数组
        return list.toArray(result);    //List to Array
    }
}
