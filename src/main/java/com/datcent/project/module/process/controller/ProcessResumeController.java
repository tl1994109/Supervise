package com.datcent.project.module.process.controller;

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
import com.datcent.project.module.process.domain.ProcessResume;
import com.datcent.project.module.process.service.IProcessResumeService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 流程履历  信息操作处理
 *
 * @author datcent
 * @date 2018-11-08
 */
@Controller
@RequestMapping("/module/processResume")
public class ProcessResumeController extends BaseController {
    private String prefix = "module/processResume";

    @Autowired
    private IProcessResumeService processResumeService;

    @RequiresPermissions("module.processResume:view")
    @GetMapping()
    public String processResume(String matterId,ModelMap mmap) {
    	mmap.put("matterId", matterId);
        return prefix + "/processResume";
    }

    /**
     * 查询流程履历 列表
     */
    @RequiresPermissions("module.processResume:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ProcessResume processResume) {
        startPage();
        List<ProcessResume> list = processResumeService.selectProcessResumeList(processResume);
        return getDataTable(list);
    }

    /**
     * 新增流程履历
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存流程履历
     */
    @RequiresPermissions("module.processResume:add")
    @Log(title = "流程履历 ", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ProcessResume processResume) {
        return toAjax(processResumeService.insertProcessResume(processResume));
    }

    /**
     * 修改流程履历
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        ProcessResume processResume = processResumeService.selectProcessResumeById(id);
        mmap.put("processResume", processResume);
        return prefix + "/edit";
    }

    /**
     * 修改保存流程履历
     */
    @RequiresPermissions("module.processResume:edit")
    @Log(title = "流程履历 ", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ProcessResume processResume) {
        return toAjax(processResumeService.updateProcessResume(processResume));
    }

    /**
     * 删除流程履历
     */
    @RequiresPermissions("module.processResume:remove")
    @Log(title = "流程履历 ", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(processResumeService.deleteProcessResumeByIds(ids));
    }

}
