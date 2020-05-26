package com.datcent.project.system.reProcdef.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.constant.Constants;
import com.datcent.common.utils.ServletUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.project.system.notice.domain.Notice;
import com.datcent.project.system.reModel.service.IReModelService;
import com.datcent.project.system.reProcdef.domain.ReProcdef;
import com.datcent.project.system.reProcdef.service.IReProcdefService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
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
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 业务流程定义 信息操作处理
 *
 * @author datcent
 * @date 2019-01-08
 */
@Controller
@RequestMapping("/system/reProcdef")
public class ReProcdefController extends BaseController {
    private String prefix = "system/reProcdef";

    @Autowired
    private IReProcdefService reProcdefService;
    @Autowired
    private IReModelService reModelService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private TaskService taskService;

    @RequiresPermissions("module:reProcdef:view")
    @GetMapping()
    public String reProcdef() {
        return prefix + "/reProcdef";
    }

    /**
     * 查询业务流程定义列表
     */
    @RequiresPermissions("module:reProcdef:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ReProcdef reProcdef) {
        //PAGE_SIZE
        Integer pageNum = ServletUtils.getParameterToInt(Constants.PAGE_NUM);
        Integer pageSize = ServletUtils.getParameterToInt(Constants.PAGE_SIZE);
        ProcessDefinitionQuery processDefinitionQuery = repositoryService
                .createProcessDefinitionQuery();
        List<org.activiti.engine.repository.ProcessDefinition> processDefinitionList = null;
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        if (reProcdef != null) {
            if (!StringUtils.isEmpty(reProcdef.getDeploymentId())) {
                processDefinitionQuery.deploymentId(reProcdef.getDeploymentId());
            }
            if (!StringUtils.isEmpty(reProcdef.getName())) {
                processDefinitionQuery.processDefinitionNameLike("%" + reProcdef.getName() + "%");

            }
        }
        if (pageNum != null && pageSize != null) {
            rspData.setTotal(processDefinitionQuery.count());
            processDefinitionList = processDefinitionQuery.listPage(pageSize * (pageNum - 1), Integer.valueOf(pageSize));
        } else {
            processDefinitionList = processDefinitionQuery.list();
            rspData.setTotal(processDefinitionList.size());
        }
        long count = repositoryService.createDeploymentQuery().count();
        List<ReProcdef> list = new ArrayList<>();
        processDefinitionList
                .forEach(processDefinition -> list.add(new ReProcdef(processDefinition)));
        rspData.setRows(list);
        return rspData;
    }

    /**
     * 新增业务流程定义
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存业务流程定义
     */
    @RequiresPermissions("module:reProcdef:add")
    @Log(title = "业务流程定义", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ReProcdef reProcdef) {
        return toAjax(reProcdefService.insertReProcdef(reProcdef));
    }

    /**
     * 修改业务流程定义
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        ReProcdef reProcdef = reProcdefService.selectReProcdefById(id);
        mmap.put("reProcdef", reProcdef);
        return prefix + "/edit";
    }

    /**
     * 修改保存业务流程定义
     */
    @RequiresPermissions("module:reProcdef:edit")
    @Log(title = "业务流程定义", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ReProcdef reProcdef) {
        return toAjax(reProcdefService.updateReProcdef(reProcdef));
    }

    /**
     * 删除业务流程定义
     */
    @RequiresPermissions("module:reProcdef:remove")
    @Log(title = "业务流程定义", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            if (ids.indexOf(",") > -1) {
                String[] id_str = ids.split(",");
                for (String i : id_str) {
                    repositoryService.deleteDeployment(i);
                }
            } else {
                repositoryService.deleteDeployment(ids,true);
            }
            return success();
        } catch (Exception e) {
            e.printStackTrace();
            return error();
        }
    }

}
