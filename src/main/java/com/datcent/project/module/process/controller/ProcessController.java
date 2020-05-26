package com.datcent.project.module.process.controller;

import java.util.List;
import java.util.Map;

import com.datcent.common.constant.UserConstants;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.web.page.PageDomain;
import com.datcent.project.module.process.domain.ProcessLineDetail;
import com.datcent.project.module.process.domain.ProcessMatter;
import com.datcent.project.module.process.domain.ProcessNodeDetail;
import com.datcent.project.module.process.service.IProcessLineDetailService;
import com.datcent.project.module.process.service.IProcessMatterService;
import com.datcent.project.module.process.service.IProcessNodeDetailService;
import com.datcent.project.system.user.domain.User;
import com.datcent.project.system.user.service.IUserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.datcent.project.module.process.domain.Process;
import com.datcent.project.module.process.service.IProcessService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 流程 信息操作处理
 *
 * @author datcent
 * @date 2018-11-01
 */
@Controller
@RequestMapping("/module/process")
public class ProcessController extends BaseController {
    private String prefix = "module/process";

    @Autowired
    private IProcessService processService;
    @Autowired
    private IProcessNodeDetailService processNodeDetailService;

    @Autowired
    private IProcessMatterService processMatterService;
    @Autowired
    private IProcessLineDetailService processLineDetailService;
    @Autowired
    private IUserService userService;

    @RequiresPermissions("module:process:view")
    @GetMapping()
    public String process(ModelMap mmap) {
        mmap.put("users", userService.selectUserList(new User(),new PageDomain()));
        return prefix + "/process";
    }

    /**
     * 查询流程列表
     */
    @RequiresPermissions("module:process:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Process process) {
        startPage();
        return getDataTable(processService.selectProcessList(process));
    }

    /**
     * 新增流程
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("users", userService.selectUserList(new User(),new PageDomain()));
        return prefix + "/add";
    }


    /**
     * 新增保存流程
     */
    @RequiresPermissions("module:process:add")
    @Log(title = "新增保存流程", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Process process) {
        process.setCreateBy(ShiroUtils.getLoginName());
        process.setCreateTime(DateUtils.getNowDate());
        processConfigAnalysis(process.getProcessConfig(), process.getProcessId());
        return toAjax(processService.insertProcess(process));
    }

    /**
     * @Author: Chanyin尹强
     * @CreateDate: 2018/11/5 17:11
     * @Param: [processConfig, processId]
     * @Return: void
     * @Description: 解析nodes节点和lines线条数据
     **/
    void processConfigAnalysis(String processConfig, String processId) {
        processNodeDetailService.deleteProcessNodeDetailByIds(processId);
        processLineDetailService.deleteProcessLineDetailByIds(processId);
        JSONArray jsonArray = JSONArray.fromObject(processConfig);
        JSONObject obj = (JSONObject) jsonArray.get(0);
        JSONObject nodes = (JSONObject) obj.get("nodes");
        for (Object id : nodes.keySet()) {
            String node_id = stringValueOf(id);
            JSONObject node = (JSONObject) nodes.get(node_id);
            ProcessNodeDetail processNode = new ProcessNodeDetail();
            processNode.setProcessId(processId);
            processNode.setNodeId(node_id);
            processNode.setNodeName(stringValueOf(node.get("name")));
            processNode.setNodeType(stringValueOf(node.get("type")));
            if (stringValueOf(node.get("type")).indexOf("start") == -1 && stringValueOf(node.get("type")).indexOf("end") == -1) {
                processNode.setAllowBy(stringValueOf(node.get("allow_by")));
                processNode.setIsUpdate(stringValueOf(node.get("is_update")));
            }
            processNodeDetailService.insertProcessNodeDetail(processNode);
        }
        JSONObject lines = (JSONObject) obj.get("lines");
        for (Object id : lines.keySet()) {
            String line_id = stringValueOf(id);
            JSONObject line = (JSONObject) lines.get(line_id);
            ProcessLineDetail processLine = new ProcessLineDetail();
            processLine.setProcessId(processId);
            processLine.setLineId(line_id);
            processLine.setLineName(stringValueOf(line.get("name")));
            processLine.setFromNodeId(stringValueOf(line.get("from")));
            processLine.setToNodeId(stringValueOf(line.get("to")));
            processLineDetailService.insertProcessLineDetail(processLine);
        }
    }

    /**
     * 修改流程
     */
    @GetMapping("/edit/{processId}")
    public String edit(@PathVariable("processId") String processId, ModelMap mmap) {
        mmap.put("process", processService.selectProcessById(processId));
        mmap.put("users", userService.selectUserList(new User(),new PageDomain()));
        return prefix + "/edit";
    }
    
    /**
     * 只读流程
     */
    @GetMapping("/detailReadOnly/{processId}")
    public String detailReadOnly(@PathVariable("processId") String processId, ModelMap mmap) {
        mmap.put("process", processService.selectProcessById(processId));
        mmap.put("users", userService.selectUserList(new User(),new PageDomain()));
        return prefix + "/detailReadOnly";
    }

    /**
     * 修改保存流程
     */
    @RequiresPermissions("module:process:edit")
    @Log(title = "修改保存流程", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Process process) {
        process.setUpdateBy(ShiroUtils.getLoginName());
        process.setUpdateTime(DateUtils.getNowDate());
        processConfigAnalysis(process.getProcessConfig(), process.getProcessId());
        return toAjax(processService.updateProcess(process));
    }

    /**
     * 删除流程
     */
    @RequiresPermissions("module:process:remove")
    @Log(title = "删除流程", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        processNodeDetailService.deleteProcessNodeDetailByIds(ids);
        processLineDetailService.deleteProcessLineDetailByIds(ids);
        return toAjax(processService.deleteProcessByIds(ids));
    }

    /**
     * 修改流程状态
     */
    @RequiresPermissions("module:process:updateProcessStatus")
    @Log(title = "修改流程状态", businessType = BusinessType.DELETE)
    @PostMapping("/updateProcessStatus")
    @ResponseBody
    public AjaxResult updateProcessStatus(Process process) {
        if (process.getStatus() == "2") {
            process.setDeleteBy(ShiroUtils.getLoginName());
            process.setDeleteTime(DateUtils.getNowDate());
        }
        return toAjax(processService.updateProcess(process));
    }

    /**
     * @Author: Chanyin尹强
     * @CreateDate: 2018/11/5 17:11
     * @Param: [obj]
     * @Return: java.lang.String
     * @Description: String字符非空判断
     **/
    String stringValueOf(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    /**
     * 查询流程名称是否重复
     */
    @PostMapping("/findProcessName")
    @ResponseBody
    public String findProcessName(Process process) {
        String processId = process.getProcessId();
        process.setProcessId(null);
        List<Process> maps = processService.selectProcessList(process);
        Process process1 = processService.selectProcessById(processId);
        if (maps.size() > 0 && !maps.get(0).getProcessName().equals(process1.getProcessName())) {
            return UserConstants.PROCESS_NOT_UNIQUE;
        }
        return UserConstants.PROCESS_UNIQUE;
    }


    /**
     * 通过processId查询流程
     */
    @PostMapping("/selectProcessById")
    @ResponseBody
    public Process selectProcessById(String processId) {
        return processService.selectProcessById(processId);
    }
}
