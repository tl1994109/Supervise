package com.datcent.project.module.subgroupEvent.controller;

import java.util.Date;
import java.util.List;

import com.datcent.project.tool.diagram.domain.Diagram;
import com.datcent.project.tool.diagram.service.IDiagramService;
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
import com.datcent.project.module.subgroupEvent.domain.SubgroupEvent;
import com.datcent.project.module.subgroupEvent.service.ISubgroupEventService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 添加事件 信息操作处理
 *
 * @author datcent
 * @date 2018-11-30
 */
@Controller
@RequestMapping("/module/subgroupEvent")
public class SubgroupEventController extends BaseController {
    private String prefix = "module/subgroupEvent";

    @Autowired
    private ISubgroupEventService subgroupEventService;

    @Autowired
    private IDiagramService diagramService;

    @RequiresPermissions("module:subgroupEvent:view")
    @GetMapping()
    public String subgroupEvent() {
        return prefix + "/subgroupEvent";
    }

    /**
     * 查询添加事件列表
     */
    @RequiresPermissions("module:subgroupEvent:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SubgroupEvent subgroupEvent) {
        startPage();
        List<SubgroupEvent> list = subgroupEventService.selectSubgroupEventList(subgroupEvent);
        return getDataTable(list);
    }

    /**
     * 新增添加事件
     */
    @GetMapping("/add")
    public String add(String tableName, Diagram diagram, ModelMap modelMap) {
        List<Diagram> diagramList = diagramService.selectDiagramList(diagram);
        modelMap.put("diagramList", diagramList);
        modelMap.put("tableName", tableName);
        return prefix + "/add";
    }

    /**
     * 新增保存添加事件
     */
    @RequiresPermissions("module:subgroupEvent:add")
    @Log(title = "添加事件", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SubgroupEvent subgroupEvent) {
        return toAjax(subgroupEventService.insertSubgroupEvent(subgroupEvent));
    }

    /**
     * 修改添加事件@PathVariable("eventId") String eventId
     */
    @GetMapping("/edit/{eventId}")
    public String edit(@PathVariable("eventId") String eventId, ModelMap mmap, Diagram diagram, String json) {
        SubgroupEvent subgroupEvent = subgroupEventService.selectSubgroupEventById(eventId);
        mmap.put("subgroupEvent", subgroupEvent);
        List<Diagram> diagramList = diagramService.selectDiagramList(diagram);
        mmap.put("diagramList", diagramList);
        return prefix + "/edit";
    }

    @GetMapping("/editEvent")
    public String editEvent(SubgroupEvent qsubgroupEvent, ModelMap mmap, Diagram diagram) {
        mmap.put("subgroupEvent", qsubgroupEvent);
        List<Diagram> diagramList = diagramService.selectDiagramList(diagram);
        mmap.put("diagramList", diagramList);
        return prefix + "/edit";
    }

    /**
     * 修改保存添加事件
     */
    @RequiresPermissions("module:subgroupEvent:edit")
    @Log(title = "添加事件", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SubgroupEvent subgroupEvent) {
        subgroupEvent.setUpdateTime(new Date());
        return toAjax(subgroupEventService.updateSubgroupEvent(subgroupEvent));
    }

    /**
     * 删除添加事件
     */
    @RequiresPermissions("module:subgroupEvent:remove")
    @Log(title = "添加事件", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(subgroupEventService.deleteSubgroupEventByIds(ids));
    }

    @PostMapping("/selectByTableName")
    @ResponseBody
    public List<SubgroupEvent> selectByTableName(SubgroupEvent subgroupEvent) {
        List<SubgroupEvent> subgroupEventList = subgroupEventService.selectSubgroupEventList(subgroupEvent);
        return subgroupEventList;
    }

}
