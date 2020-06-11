package com.datcent.project.module.dubvioEvent.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.poi.ExcelUtil;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.common.utils.simlier.CosineSimilarity;
import com.datcent.framework.web.page.PageDomain;
import com.datcent.framework.web.page.TableSupport;
import com.datcent.project.module.clue.service.IClueService;
import com.datcent.project.module.dubvioEventDetail.domain.DubvioEventDetail;
import com.datcent.project.module.dubvioEventDetail.service.IDubvioEventDetailService;
import com.datcent.project.module.hiTaskinst.service.IHiTaskinstService;
import com.datcent.project.module.process.domain.Process;
import com.datcent.project.module.ruTask.domain.CommentInfo;
import com.datcent.project.module.statistics.service.IStatisticsService;
import com.datcent.project.module.strategy.service.IStrategyService;
import com.datcent.project.system.courtOrgan.domain.CourtOrgan;
import com.datcent.project.system.courtOrgan.service.ICourtOrganService;
import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.dept.service.IDeptService;
import com.datcent.project.system.person.domain.Person;
import com.datcent.project.system.person.service.IPersonService;
import com.datcent.project.system.user.domain.User;
import com.datcent.project.system.user.service.IUserService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.project.module.dubvioEvent.domain.DubvioEvent;
import com.datcent.project.module.dubvioEvent.service.IDubvioEventService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: taolin
 * @Email: taolin@datcent.com
 * @CreateDate: 2019/1/15 10:34
 * @Copyright: © 2018 德讯科技 版权所有
 * @param:
 * @return:
 * @exception:
 * @Description:
 **/
@Controller
@RequestMapping("/module/dubvioEvent")
public class DubvioEventController extends BaseController {

    private static final Logger log = Logger.getLogger(DubvioEventController.class);

    private String prefix = "module/dubvioEvent";

    @Autowired
    private IDubvioEventService dubvioEventService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDubvioEventDetailService dubvioEventDetailService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IHiTaskinstService hiTaskinstService;

    @Autowired
    private IStrategyService strategyService;

    @Autowired
    private IClueService clueService;

    @Autowired
    private IDeptService deptService;
    @Autowired
    private IPersonService personService;

    @Autowired
    private ICourtOrganService iCourtOrganService;

    @Autowired
    private IStatisticsService iStatisticsService;

    @RequiresPermissions("module:dubvioEvent:view")
    @GetMapping()
    public String dubvioEvent(ModelMap mmap) {
        CourtOrgan courtOrgan = new CourtOrgan();
        courtOrgan.setDeptType("1");
        courtOrgan.setStatus("0");
        List<CourtOrgan> thirdList = iCourtOrganService.selectNewDeptList(courtOrgan);
        Person person = personService.selectPersonById(ShiroUtils.getUser().getPersonId());
        List<User> userList = userService.selectUserListByDeptId(person.getDeptId());
        List<Map> suspectsNameList = iStatisticsService.getSuspectsNameList();
        mmap.put("suspectsList", suspectsNameList);
        mmap.put("deptList", thirdList);
        mmap.put("userList", userList);
        return prefix + "/dubvioEvent";
    }


    @RequiresPermissions("module:dubvioEventSecond:view")
    @GetMapping("/dubvioEventSecond")
    public String dubvioEventSecond(ModelMap mmap) {

        CourtOrgan courtOrgan = new CourtOrgan();
        courtOrgan.setDeptType("1");
        courtOrgan.setStatus("0");
        List<CourtOrgan> thirdList = iCourtOrganService.selectNewDeptList(courtOrgan);
        mmap.put("deptList", thirdList);
        Person person = personService.selectPersonById(ShiroUtils.getUser().getPersonId());
        List<User> userList = userService.selectUserListByDeptId(person.getDeptId());
        mmap.put("userList", userList);
        return prefix + "/dubvioEvent_second";
    }





    @GetMapping("/dubvioEventTest")
    public String dubvioEventTest(ModelMap mmap) {
        CourtOrgan courtOrgan = new CourtOrgan();
        courtOrgan.setDeptType("1");
        courtOrgan.setStatus("0");
        List<CourtOrgan> thirdList = iCourtOrganService.selectNewDeptList(courtOrgan);
        mmap.put("deptList", thirdList);
        Person person = personService.selectPersonById(ShiroUtils.getUser().getPersonId());
        List<User> userList = userService.selectUserListByDeptId(person.getDeptId());
        mmap.put("userList", userList);
        return prefix + "/dubvioEvent_test";
    }




    /**
     * 查询可疑违纪事件列表
     */
    @RequiresPermissions("module:dubvioEvent:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DubvioEvent dubvioEvent) {
        startPage();
        if (StringUtils.isNotEmpty(dubvioEvent.getParams())) {
            if (StringUtils.isNotEmpty(dubvioEvent.getParams().get("createTime").toString())) {
                dubvioEvent.setJbxxLarq(dubvioEvent.getParams().get("createTime").toString());
            }
        }
        dubvioEvent.setDubvioSource("1");

        if (StringUtils.isNotEmpty(dubvioEvent.getStatus())) {
            if (dubvioEvent.getStatus().equals("2")) {
                dubvioEvent.setDeleteFlag("0");
            } else if (dubvioEvent.getStatus().equals("0")) {
                dubvioEvent.setDubvioStatus("3");
                PageDomain pageDomain = TableSupport.buildPageRequest();
                List<DubvioEvent> list = dubvioEventService.selectPassDubvioEventList(dubvioEvent, pageDomain);
                return getDataTable(list);


            } else if (dubvioEvent.getStatus().equals("20")) {
                dubvioEvent.setDeleteFlag("2");
                dubvioEvent.setStatus("");
            } else if (dubvioEvent.getStatus().equals("30")) {
                dubvioEvent.setDeleteFlag("1");
                dubvioEvent.setStatus("");
            } else if (dubvioEvent.getStatus().equals("8")) {
                dubvioEvent.setDeleteFlag("0");
                dubvioEvent.setStatus("8");
            } else if (dubvioEvent.getStatus().equals("11")) {
                dubvioEvent.setDeleteFlag("0");
                dubvioEvent.setStatus("11");
            } else if (dubvioEvent.getStatus().equals("13")) {
                dubvioEvent.setDeleteFlag("0");
                dubvioEvent.setStatus("13");
            }
        }
        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<DubvioEvent> list = dubvioEventService.selectDubvioEventList(dubvioEvent, pageDomain);

        list.forEach(DubvioEvents -> {
            String jbfyName = clueService.queryDeptNameByCid(DubvioEvents.getJbfyId());
            DubvioEvents.setJbfyName(jbfyName);
        });

        return getDataTable(list);
    }


    /**
     * 查询信访录入列表
     */
    @RequiresPermissions("module:dubvioEventSecond:secondList")
    @PostMapping("/secondList")
    @ResponseBody
    public TableDataInfo secondList(DubvioEvent dubvioEvent) {
        startPage();
        String userId = ShiroUtils.getUserId() + "";
        if (StringUtils.isNotEmpty(dubvioEvent.getParams())) {
            if (StringUtils.isNotEmpty(dubvioEvent.getParams().get("createTime").toString())) {
                dubvioEvent.setJbxxLarq(dubvioEvent.getParams().get("createTime").toString());
            }
        }
        // 根据部门id   查出 所属parentId
        String deptId = ShiroUtils.getUser().getDeptId() + "";
        String parentId = null;
        if (StringUtils.isNotEmpty(deptId)) {

            Dept dept = deptService.selectDeptById(Long.parseLong(deptId));
            if (!StringUtils.isNull(dept.getParentId())) {
                parentId = dept.getParentId().toString();
            }
        }
        String deptName = dubvioEventService.queryDeptNameByUserId(userId);

        List<DubvioEvent> list = null;

        //判断 这是市 中院 的 监察处
        if (deptId.equals("144368222") && parentId.equals("2203")) {
            dubvioEvent.setXfDeptId(deptId);
        }
        //这是区县的监察处
        else if (deptName.contains("监察")) {
            dubvioEvent.setXfDeptId(deptId);
        }
        PageDomain pageDomain = TableSupport.buildPageRequest();

        dubvioEvent.setDubvioSource("2");
        if (StringUtils.isNotEmpty(dubvioEvent.getStatus())) {
            if (dubvioEvent.getStatus().equals("2")) {
                dubvioEvent.setDeleteFlag("0");
                list = dubvioEventService.selectDubvioEventList(dubvioEvent, pageDomain);
            } else if (dubvioEvent.getStatus().equals("0")) {
                dubvioEvent.setDubvioStatus("3");
                list = dubvioEventService.selectSecondDubvioEventList(dubvioEvent, pageDomain);
            } else if (dubvioEvent.getStatus().equals("20")) {
                dubvioEvent.setDeleteFlag("2");
                dubvioEvent.setStatus("");
                list = dubvioEventService.selectDubvioEventList(dubvioEvent, pageDomain);
            } else if (dubvioEvent.getStatus().equals("30")) {
                dubvioEvent.setDeleteFlag("1");
                dubvioEvent.setStatus("");
                list = dubvioEventService.selectDubvioEventList(dubvioEvent, pageDomain);
            } else if (dubvioEvent.getStatus().equals("13")) {
                dubvioEvent.setDeleteFlag("0");
                dubvioEvent.setStatus("13");
                list = dubvioEventService.selectDubvioEventList(dubvioEvent, pageDomain);
            } else {
                list = dubvioEventService.selectDubvioEventList(dubvioEvent, pageDomain);
            }
        } else {
            list = dubvioEventService.selectDubvioEventList(dubvioEvent, pageDomain);
        }

        list.forEach(DubvioEvents -> {
            String jbfyName = clueService.queryDeptNameByCid(DubvioEvents.getJbfyId());
            DubvioEvents.setJbfyName(jbfyName);
        });

        return getDataTable(list);
    }

    /**
     * 新增可疑违纪事件
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {

        List<Map> list = strategyService.queryStrategy();
        List<Map> secondList = clueService.queryAllCbt();
        CourtOrgan courtOrgan = new CourtOrgan();

        courtOrgan.setStatus("0");
        courtOrgan.setDeptType("1");
        startPage();
        List<CourtOrgan> thirdList = iCourtOrganService.selectNewDeptList(courtOrgan);

        mmap.put("strategyList", list);
        mmap.put("cbtList", secondList);
        mmap.put("deptList", thirdList);
        return prefix + "/add";
    }

    /**
     * 新增保存可疑违纪事件
     */
    @RequiresPermissions("module:dubvioEvent:add")
    @Log(title = "可疑违纪事件", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DubvioEvent dubvioEvent, @Param("type") String type, String jbxxAjbhs, String jbxxAhs, String dubvioStrategyIds, String jbxxBgrs, String jbxxCbtNames, String jbxxCbtIds, String jbfyIds, String jbxxAjlbs, String risksLevels, String jbxxSarqs, String jbxxLarqs, String jbxxFarqs, String jbxxKtrqs, String jbxxJarqs, String jbxxGdrqs) throws ParseException {
        //定义日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long deptId = ShiroUtils.getUser().getDeptId();
        dubvioEvent.setXfDeptId(deptId + "");
        dubvioEvent.setDubvioId(StringUtils.getUUID());
        String strategyName = dubvioEvent.getDubvioStrategyName();
        dubvioEvent.setCreateTime(DateUtils.getNowDate());
        dubvioEvent.setCreateBy(ShiroUtils.getUser().getUserName());
        if (StringUtils.isNotEmpty(dubvioEvent.getDubvioFydx())) {
            List<Map> cbrIds = clueService.queryCidByDeptNames(dubvioEvent.getDubvioFydx());
            if (cbrIds.size() > 0) {
                String cbrId = cbrIds.get(0).get("id").toString();
                dubvioEvent.setJbxxCbrId(cbrId);
            }
        }
        if (strategyName.contains("<p>")) {
            strategyName = strategyName.replace("<p>", " ");
            strategyName = strategyName.replace("</p>", " ");
            strategyName = strategyName.trim();
            dubvioEvent.setDubvioStrategyName(strategyName);
        }

        if (StringUtils.isEmpty(type)) {
            if (StringUtils.isNotEmpty(dubvioEvent.getJbxxCbtId())) {

                String name = iCourtOrganService.queryDeptNameByCid(dubvioEvent.getJbxxCbtId(), "2");
                dubvioEvent.setJbxxCbtName(name);
            }
        } else {
            dubvioEvent.setJbxxAjbh(jbxxAjbhs);
            dubvioEvent.setJbxxAh(jbxxAhs);
            dubvioEvent.setDubvioStrategyId(dubvioStrategyIds);
            dubvioEvent.setJbxxBgr(jbxxBgrs);
            dubvioEvent.setJbxxCbtName(jbxxCbtNames);
            dubvioEvent.setJbxxCbtId(jbxxCbtIds);
            dubvioEvent.setJbfyId(jbfyIds);
            dubvioEvent.setJbxxAjlb(jbxxAjlbs);
            dubvioEvent.setJbxxSarq(jbxxSarqs);
            dubvioEvent.setJbxxLarq(jbxxLarqs);
            dubvioEvent.setJbxxFarq(jbxxFarqs);
            dubvioEvent.setJbxxKtrq(jbxxKtrqs);
            if (StringUtils.isNotEmpty(jbxxJarqs)) {
                if (jbxxJarqs.equals("&nbsp;")) {
                    dubvioEvent.setJbxxJarq("");
                }
            }
            if (StringUtils.isNotEmpty(jbxxGdrqs)) {
                if (jbxxGdrqs.equals("&nbsp;")) {
                    dubvioEvent.setJbxxGdrq("");
                }
            }
        }


        dubvioEvent.setDubvioSource("2");
        dubvioEvent.setJbxxCbrName(dubvioEvent.getDubvioFydx());

        dubvioEvent.setUpdateTime(DateUtils.getNowDate());
        return toAjax(dubvioEventService.insertDubvioEvent(dubvioEvent));
    }


    /**
     * 修改可疑违纪事件
     */
    @GetMapping("/dubvioEdit/{dubvioId}")
    public String dubvioEdit(@PathVariable("dubvioId") String dubvioId, ModelMap mmap) {
        DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(dubvioId);

        Dept dept = new Dept();
        List secondList = iCourtOrganService.selectCourtOrganByParentId(dubvioEvent.getJbfyId());


        dept.setDeptType("0");
        List<Map> list = strategyService.queryStrategy();
        CourtOrgan courtOrgan = new CourtOrgan();
        courtOrgan.setStatus("0");
        courtOrgan.setDeptType("1");
        List<CourtOrgan> thirdList = iCourtOrganService.selectDeptList(courtOrgan);
        mmap.put("deptList", thirdList);
        mmap.put("strategyList", list);
        mmap.put("dubvioEvent", dubvioEvent);
        mmap.put("cbtList", secondList);

        return prefix + "/dubvioEvent_edit";
    }


    /**
     * 修改可疑违纪事件
     */
    @GetMapping("/edit/{dubvioId}")
    public String edit(@PathVariable("dubvioId") String dubvioId, ModelMap mmap) {
        DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(dubvioId);

        DubvioEventDetail dubvioEventDetail = dubvioEventDetailService.selectDubvioEventDetailById(dubvioEvent.getJbxxAjbh());
        if (dubvioEventDetail == null) {
            dubvioEventDetail = new DubvioEventDetail();
        }
        mmap.put("dubvioEventDetail", dubvioEventDetail);
        mmap.put("status", dubvioEvent.getStatus());


        return prefix + "/edit";
    }


    /**
     * 修改可疑违纪事件
     */
    @GetMapping("/edits")
    public String edits(String dubvioId, ModelMap mmap) {
        DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(dubvioId);

        DubvioEventDetail dubvioEventDetail = dubvioEventDetailService.selectDubvioEventDetailById(dubvioEvent.getJbxxAjbh());
        if (dubvioEventDetail == null) {
            dubvioEventDetail = new DubvioEventDetail();
        }
        dubvioEventDetail.setDubvioId(dubvioId);
        mmap.put("dubvioEventDetail", dubvioEventDetail);
        return prefix + "/edits";
    }

    /**
     * 修改保存可疑违纪事件
     */
    @RequiresPermissions("module:dubvioEvent:edit")
    @Log(title = "可疑违纪事件", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DubvioEvent dubvioEvent, String createTimes) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        dubvioEvent.setUpdateTime(date);

        String name = iCourtOrganService.queryDeptNameByCid(dubvioEvent.getJbxxCbtId(), "2");
        dubvioEvent.setJbxxCbtName(name);

        if (StringUtils.isNotEmpty(dubvioEvent.getDubvioStrategyId())) {
            String strategyName = strategyService.queryStrategyById(Integer.parseInt(dubvioEvent.getDubvioStrategyId()));
            dubvioEvent.setDubvioStrategyName(strategyName);
        }
        if (StringUtils.isNotNull(createTimes)) {
            try {
                dubvioEvent.setCreateTime(sdf.parse(createTimes));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isNotEmpty(dubvioEvent.getDubvioFydx())) {
            dubvioEvent.setJbxxCbrName(dubvioEvent.getDubvioFydx());

            List<Map> cbrIds = clueService.queryCidByDeptNames(dubvioEvent.getDubvioFydx());
            if (cbrIds.size() > 0) {
                String cbrId = cbrIds.get(0).get("id").toString();
                dubvioEvent.setJbxxCbrId(cbrId);
            }
        }
        return toAjax(dubvioEventService.updateDubvioEvent(dubvioEvent));
    }

    /**
     * 删除可疑违纪事件
     */
    @RequiresPermissions("module:dubvioEvent:remove")
    @Log(title = "可疑违纪事件", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids, String content) {
        //return toAjax(dubvioEventService.deleteDubvioEventByIds(ids));


        List idList = Arrays.asList(ids.split(","));


        for (int i = 0; i < idList.size(); i++) {
            String dubvioId = idList.get(i).toString();


            try {
                //流程定义的key
                String processDefinitionKey = "caseProcess";
                Map<String, Object> map = new HashMap<String, Object>();
                DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(dubvioId);
                map.put("isok", "true");
                if (StringUtils.isEmpty(dubvioEvent.getJbxxAh())) {
                    map.put("title", dubvioEvent.getDubvioStrategyName());
                } else {
                    map.put("title", dubvioEvent.getJbxxAh());
                }
                map.put("type", ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_TYPE);
                map.put("dubvioId", dubvioEvent.getDubvioId());
                //申请人
                map.put("proposerName", ShiroUtils.getUser().getUserName());
                map.put("proposer", ShiroUtils.getUserId());
                //使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
                //使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
                ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefinitionKey, map);
                log.info("流程实例ID:" + pi.getId());
                log.info("流程定义ID:" + pi.getProcessDefinitionId());
                //查询当前流程实例任务对象
                Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
                runtimeService.updateBusinessKey(pi.getId(), dubvioEvent.getDubvioId());
                //设置批注人
                taskService.addComment(task.getId(), task.getProcessInstanceId(), "user#" + ShiroUtils.getUserId());
                //设置审批标识
                taskService.addComment(task.getId(), task.getProcessInstanceId(), "isok#" + true);
                //设置审批意见
                taskService.addComment(task.getId(), task.getProcessInstanceId(), content == null ? "content#提交申请" : "content#" + content.replaceAll("#", ""));
                taskService.addComment(task.getId(), task.getProcessInstanceId(), "date#" + DateUtils.getTime());
                //设置当前任务
                taskService.setAssignee(task.getId(), ShiroUtils.getUserId().toString());
                //提交任务并将流程变量放到任务中
                taskService.complete(task.getId(), map);

                dubvioEvent.setStatus("1");
                dubvioEvent.setPid(pi.getId());
                dubvioEvent.setUpdateTime(DateUtils.getNowDate());
                dubvioEventService.updateDubvioEvent(dubvioEvent);

            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        }
        return success();

    }

    /**
     * 可疑违纪转入 线索的流程
     *
     * @param dubvioId
     * @param approverName
     * @param approver
     * @param content
     * @return
     */
    @RequiresPermissions("module:dubvioEvent:releaseDubvioEvent")
    @PostMapping("/releaseDubvioEvent")
    @ResponseBody
    public AjaxResult releaseDubvioEvent(String dubvioId, String approverName, String approver, String content, String dubvioCzjg, ModelMap mmap) {

        AjaxResult json = new AjaxResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();


            //流程定义的key
            String processDefinitionKey = null;
            if (dubvioCzjg.equals("4")) {
                processDefinitionKey = "caseProcess2";
                map.put("type", ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_TYPE);
            } else if (dubvioCzjg.equals("0")) {
                processDefinitionKey = "kythprocess";
                map.put("type", ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_TH_TYPE);
            } else if (dubvioCzjg.equals("1")) {
                processDefinitionKey = "kylaprocess";
                map.put("type", ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_LA_TYPE);
            } else if (dubvioCzjg.equals("2")) {
                processDefinitionKey = "kyjccprocess";
                map.put("type", ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_JCC_TYPE);
            } else if (dubvioCzjg.equals("3")) {
                processDefinitionKey = "kyqtprocess";
                map.put("type", ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_QT_TYPE);
            }


            DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(dubvioId);
            map.put("isok", "true");

            if (StringUtils.isEmpty(dubvioEvent.getJbxxAh())) {
                map.put("title", dubvioEvent.getDubvioStrategyName());
            } else {
                map.put("title", dubvioEvent.getJbxxAh());
            }

            map.put("dubvioId", dubvioEvent.getDubvioId());
            //申请人
            map.put("proposerName", ShiroUtils.getUser().getUserName());
            map.put("proposer", ShiroUtils.getUserId());
            //审批人
            map.put("approverName", approverName);
            map.put("approver", approver);
            //使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
            //使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
            ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefinitionKey, map);
            log.info("流程实例ID:" + pi.getId());
            log.info("流程定义ID:" + pi.getProcessDefinitionId());
            //查询当前流程实例任务对象
            Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
            runtimeService.updateBusinessKey(pi.getId(), dubvioEvent.getDubvioId());
            //设置批注人
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "user#" + ShiroUtils.getUserId());
            //设置审批标识
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "isok#" + true);
            //设置审批意见
            taskService.addComment(task.getId(), task.getProcessInstanceId(), content == null ? "content#提交申请" : "content#" + content.replaceAll("#", ""));
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "date#" + DateUtils.getTime());
            //设置当前任务
            taskService.setAssignee(task.getId(), ShiroUtils.getUserId().toString());
            //提交任务并将流程变量放到任务中
            taskService.complete(task.getId(), map);

            if (dubvioCzjg.equals("4")) {
                dubvioEvent.setStatus("1");
            } else if (dubvioCzjg.equals("0")) {
                dubvioEvent.setStatus("4");
            } else if (dubvioCzjg.equals("1")) {
                dubvioEvent.setStatus("5");
            } else if (dubvioCzjg.equals("2")) {
                dubvioEvent.setStatus("6");
            } else if (dubvioCzjg.equals("3")) {
                dubvioEvent.setStatus("7");
            }


            dubvioEvent.setPid(pi.getId());
            dubvioEvent.setUpdateTime(DateUtils.getNowDate());
            dubvioEventService.updateDubvioEvent(dubvioEvent);

            json.put("dubvioCzjg", dubvioCzjg);
            json.put("msg", "操作成功");
            json.put("code", 0);

        } catch (Exception e) {
            e.printStackTrace();
            return error();
        }
        return json;
    }

    @RequiresPermissions("module:dubvioEvent:releaseRemoveDubvioEvent")
    @RequestMapping("/releaseRemoveDubvioEvent")
    @ResponseBody
    public AjaxResult releaseRemoveDubvioEvent(String ids, String content, String cause, String approver) {

        try {
            //流程定义的key
            String processDefinitionKey = "caseProcess";
            Map<String, Object> map = new HashMap<String, Object>();
            DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(ids);
            map.put("isok", "true");
            if (StringUtils.isEmpty(dubvioEvent.getJbxxAh())) {
                map.put("title", dubvioEvent.getDubvioStrategyName());
            } else {
                map.put("title", dubvioEvent.getJbxxAh());
            }
            map.put("type", ActivitiDefinftion.ACTIVITI_REMOVE_DUBVIOEVENT_TYPE);
            map.put("dubvioId", dubvioEvent.getDubvioId());
            map.put("cause", cause);
            //申请人
            map.put("proposerName", ShiroUtils.getUser().getUserName());
            map.put("proposer", ShiroUtils.getUserId());
            map.put("approver", approver);
            //使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
            //使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
            ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefinitionKey, map);
            log.info("流程实例ID:" + pi.getId());
            log.info("流程定义ID:" + pi.getProcessDefinitionId());
            //查询当前流程实例任务对象
            Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
            runtimeService.updateBusinessKey(pi.getId(), dubvioEvent.getDubvioId());
            //设置批注人
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "user#" + ShiroUtils.getUserId());
            //设置审批标识
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "isok#" + true);
            //设置审批意见
            taskService.addComment(task.getId(), task.getProcessInstanceId(), content == null ? "content#提交申请" : "content#" + content.replaceAll("#", ""));
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "date#" + DateUtils.getTime());
            //设置当前任务
            taskService.setAssignee(task.getId(), ShiroUtils.getUserId().toString());
            //提交任务并将流程变量放到任务中
            taskService.complete(task.getId(), map);

            //dubvioEvent.setStatus("1");
            dubvioEvent.setDeleteFlag("2");
            dubvioEvent.setUpdateTime(DateUtils.getNowDate());
            dubvioEvent.setPid(pi.getId());
            dubvioEventService.updateDubvioEvent(dubvioEvent);

        } catch (Exception e) {
            e.printStackTrace();
            return error();
        }
        return success();
    }


    /**
     * 可疑事件转入线索
     */
    @RequiresPermissions("module:dubvioEvent:release")
    @GetMapping("/release")
    public String release(String dubvioId, ModelMap mmap, String dubvioCzjg, String source) {

        Person person = personService.selectPersonById(ShiroUtils.getUser().getPersonId());
        List<User> userList = userService.selectUserListByDeptId(person.getDeptId());

        DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(dubvioId);
        Process process = new Process();
        process.setStatus("0");
        //List<Process> processList = processService.selectProcessList(process);
        mmap.put("dubvioEvent", dubvioEvent);

        mmap.put("userList", userList);
        mmap.put("dubvioCzjg", dubvioCzjg);
//        mmap.put("source", source);

        if (source.equals("1")) {
            return prefix + "/release";
        } else {

            String jbfyName = clueService.queryDeptNameByCid(dubvioEvent.getJbfyId());

            dubvioEvent.setJbfyName(jbfyName);
            return prefix + "/xfrelease";
        }
    }


    /**
     * 可疑事件删除
     */
    @GetMapping("/removeRelease/{dubvioId}")
    public String removeRelease(@PathVariable("dubvioId") String dubvioId, ModelMap mmap) {
        DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(dubvioId);
        Process process = new Process();
        process.setStatus("0");
        //List<Process> processList = processService.selectProcessList(process);
        mmap.put("dubvioEvent", dubvioEvent);
        //  mmap.put("processList", processList);
        User user = getUser();
        List<User> userList = userService.selectUserByDepId(user.getDeptId());
        mmap.put("userList", userList);
        return prefix + "/removeRelease";
    }

    /**
     * 根据名字查询人员相关的案号和案件编号
     *
     * @param name
     * @return
     */


    @GetMapping("/queyAhAndAjbhByAjbh")
    @ResponseBody
    public List<Map> queyAhAndAjbhByAjbh(String name) {
        List<Map> list = null;
        if (StringUtils.isNotEmpty(name)) {
            list = dubvioEventService.queyAhAndAjbhByAjbh(name);


        }
        return list;
    }


    @GetMapping("/queryCbtByFyId")
    @ResponseBody
    public List queryCbtByFyId(String fyId) {
        List list = null;
        if (StringUtils.isNotEmpty(fyId)) {
            list = iCourtOrganService.selectCourtOrganByParentId(fyId);

        }
        return list;
    }


    @RequiresPermissions("module:dubvioEvent:queryAllByAjbh")
    @PostMapping("/queryAllByAjbh")
    @ResponseBody
    public Map<String, Object> queryAllByAjbh(DubvioEvent dubvioEvent) {


        List<DubvioEvent> list = dubvioEventService.selectDubvioEventList(dubvioEvent, null);


        if (StringUtils.isNotNull(list)) {
            list.forEach(dubvioEvents -> {
                        if (StringUtils.isNotNull(dubvioEvents.getJbxxKtrq())) {
                        } else {
                            dubvioEvents.setJbxxKtrq("");
                        }
                        if (StringUtils.isNotNull(dubvioEvents.getJbxxSarq())) {
                        } else {
                            dubvioEvents.setJbxxSarq("");
                        }
                        if (StringUtils.isNotNull(dubvioEvents.getJbxxLarq())) {
                        } else {
                            dubvioEvents.setJbxxLarq("");
                        }
                        if (StringUtils.isNotNull(dubvioEvents.getJbxxFarq())) {
                        } else {
                            dubvioEvents.setJbxxFarq("");
                        }
                        if (StringUtils.isNotNull(dubvioEvents.getJbxxJarq())) {
                        } else {
                            dubvioEvents.setJbxxJarq("");
                        }
                        if (StringUtils.isNotNull(dubvioEvents.getJbxxGdrq())) {
                        } else {
                            dubvioEvents.setJbxxGdrq("");
                        }

                        if (StringUtils.isNotNull(dubvioEvents.getJbfyId())) {

                            String jbfyName = clueService.queryDeptNameByCid(dubvioEvents.getJbfyId());
                            dubvioEvents.setJbfyName(jbfyName);
                        }

                    }
            );
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dubvioEventList", list);
        return map;
    }


    @GetMapping("/hisTaskList")
    public String dubvioEvent_hisTaskList(String matterId, ModelMap modelMap) throws ParseException {
        DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(matterId);
        List<CommentInfo> comments = new ArrayList<CommentInfo>();
        String pid = "";
        //taskId 获取当前任务所在节点
        String taskId = "";
        //流程执行完
        if ("0".equals(dubvioEvent.getStatus()) || "3".equals(dubvioEvent.getStatus()) || "8".equals(dubvioEvent.getStatus()) || "9".equals(dubvioEvent.getStatus()) || "10".equals(dubvioEvent.getStatus()) || "11".equals(dubvioEvent.getStatus()) || "12".equals(dubvioEvent.getStatus()) || "13".equals(dubvioEvent.getStatus())) {
            pid = historyService.createHistoricProcessInstanceQuery().processInstanceId(dubvioEvent.getPid()).singleResult().getId();
            //流程执行完随意获取一个task实例
            HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().processInstanceId(pid).orderByTaskCreateTime().desc().list().get(0);
            taskId = historicTaskInstance.getId();
        } else if ("1".equals(dubvioEvent.getStatus()) || "4".equals(dubvioEvent.getStatus()) || "5".equals(dubvioEvent.getStatus()) || "6".equals(dubvioEvent.getStatus()) || "7".equals(dubvioEvent.getStatus()) || "2".equals(dubvioEvent.getDeleteFlag())) {//流程审批中
            pid = runtimeService.createProcessInstanceQuery().processInstanceId(dubvioEvent.getPid()).singleResult().getId();
            //获取当前任务实例
            Task task = taskService.createTaskQuery().processInstanceId(pid).orderByTaskCreateTime().desc().list().get(0);
            taskId = task.getId();
        }
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().orderByTaskCreateTime().desc()
                .processInstanceId(pid)
                .list();//返回一个list
        if (historicTaskInstanceList != null && historicTaskInstanceList.size() > 0) {
            for (HistoricTaskInstance hk : historicTaskInstanceList) {
                String his_taskId = hk.getId();
                List<Comment> his_taskCommentList = taskService.getTaskComments(his_taskId);
                CommentInfo commentInfo = new CommentInfo();
                for (Comment c : his_taskCommentList) {
                    String message = c.getFullMessage();
                    if (c != null && StringUtils.isNotEmpty(message)) {
                        String[] value_arr = message.split("\\#");
                        String varible = value_arr[0];
                        String result = "";
                        if (value_arr.length > 1) {
                            result = value_arr[1];
                        }
                        if ("user".equals(varible)) {//用户标识
                            commentInfo.setUserId(result == "" ? "用户标识为空！" : result);
                            if (StringUtils.isNotEmpty(result)) {
                                commentInfo.setUserName(userService.selectUserById(Long.parseLong(result)).getUserName());
                            }
                        } else if ("isok".equals(varible)) {//审批标识
                            commentInfo.setIsok(result);
                        } else if ("content".equals(varible)) {//批注内容
                            commentInfo.setContent(result);
                        }
                    }
                }
                String startTime = DateUtils.convertDate(hk.getStartTime(), "");
                if (hk.getEndTime() != null) {
                    String endTime = DateUtils.convertDate(hk.getEndTime(), "");
                    commentInfo.setEndTime(endTime);
                }
                if (StringUtils.isEmpty(commentInfo.getUserName())) {
                    commentInfo.setUserName(userService.selectUserById(Long.parseLong(hk.getAssignee())).getUserName());
                }
                if (StringUtils.isEmpty(commentInfo.getIsok())) {
                    commentInfo.setIsok("处理中...");
                }
                commentInfo.setStartTime(startTime);
                comments.add(commentInfo);
            }
        }
        ProcessDefinition processDefinition = hiTaskinstService.getProcessDefinitionByTaskId(taskId);
        modelMap.put("dId", processDefinition.getId());
        modelMap.put("dName", processDefinition.getDiagramResourceName());
        modelMap.put("taskId", taskId);
        modelMap.put("comments", comments);
        return prefix + "/dubvioEvent_hisTaskList";
    }


    /**
     * 详细
     */
    @RequiresPermissions("module:dubvioEvent:detail")
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") String id, ModelMap mmap) {
        DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(id);
        List<Map> secondList = clueService.queryAllCbt();
        String name = clueService.queryDeptNameByCid(dubvioEvent.getJbfyId());
        dubvioEvent.setJbfyName(name);
        mmap.put("dubvioEvent", dubvioEvent);

        mmap.put("cbtList", secondList);
        return prefix + "/detail";
    }


    /**
     * 跳转历史反应问题页面
     */
    @GetMapping("/selectDubvioByFyr")
    public String selectDubvioByFyr(ModelMap mmap) {
        return prefix + "/selectDubvioByFyr";
    }


    /**
     * 查询信访录入列表
     */


    @PostMapping("/queryList")
    @ResponseBody
    public TableDataInfo queryList(DubvioEvent dubvioEvent, String chidubvioStrategyName) {
        startPage();
        if (StringUtils.isNotEmpty(dubvioEvent.getParams())) {
            if (StringUtils.isNotEmpty(dubvioEvent.getParams().get("createTime").toString())) {
                dubvioEvent.setJbxxLarq(dubvioEvent.getParams().get("createTime").toString());
            }
        }
        dubvioEvent.setDubvioSource("2");
        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<DubvioEvent> list = dubvioEventService.selectDubvioEventList(dubvioEvent, pageDomain);
        for (DubvioEvent event : list) {
            String s1 = chidubvioStrategyName;
            String s2 = event.getDubvioStrategyName();
            double score = CosineSimilarity.getSimilarity(s1, s2) * 100;
            event.setSimiler(String.valueOf(score) + "%");
        }
        return getDataTable(list);
    }


    /**
     * 查询策略详细
     */
    @RequiresPermissions("module:dubvioEvent:eventDetail")
    @GetMapping("/eventDetail")
    public String detail() {
        //mmap.put("clue", clueService.selectClueById(clueId));
//        mmap.put("dictList", dictTypeService.selectDictTypeAll());
        return "module/dubvioEvent/eventData";
    }

    @GetMapping("/clueDetail")
    public String clueDetail(String id, String name, ModelMap mmap) {

        mmap.put("id", id);
        mmap.put("name", name);

        return "module/dubvioEvent/dubvioEventDetail";
    }

    /**
     * 统计所有的策略的名称
     *
     * @return
     */
    //@RequiresPermissions("module:clue:clueDetailByIdAndAjlb")
    @PostMapping("/eventDetailByIdAndAjlb")
    @ResponseBody
    public TableDataInfo eventDetailByIdAndAjlb(String name, String id) {
        startPage();
        List<Map> firstList = null;
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(name)) {

            if (name.equals("1")) {
                name = "民事";
                firstList = dubvioEventService.selectEventListByAjlbAndStrategyId(name, id);
            } else if (name.equals("2")) {
                name = "刑事";
                firstList = dubvioEventService.selectEventListByAjlbAndStrategyId(name, id);
            } else if (name.equals("3")) {
                name = "行政";
                firstList = dubvioEventService.selectEventListByAjlbAndStrategyId(name, id);
            } else if (name.equals("4")) {
                name = "执行";
                firstList = dubvioEventService.selectEventListByAjlbAndStrategyId(name, id);
            } else if (name.equals("5")) {
                firstList = dubvioEventService.selectEventListByStrategyId(id);
            }
        }
        // = clueService.selectClueListByAjlbAndStrategyId(name, id);

        return getDataTable(firstList);
    }


    /**
     * 被反映人情况分布情况统计
     */
    @RequiresPermissions("module:dubvioEvent:eventFyrTj")
    @GetMapping("/eventFyrTj")
    public String fyrTj() {
        //mmap.put("clue", clueService.selectClueById(clueId));
//        mmap.put("dictList", dictTypeService.selectDictTypeAll());
        return "module/dubvioEvent/eventFyrTj";
    }





    /**
     * 根据承办庭统计线索数量
     *
     * @return
     */
   // @RequiresPermissions("module:dubvioEvent:eventFyrTj")
    @PostMapping("/queryEventFyrInfo")
    @ResponseBody
    public TableDataInfo queryFyrInfo() {

        String fyId = null;
        if (ShiroUtils.getUserId() != 1) {
            fyId = String.valueOf(ShiroUtils.getUser().getDept().getParentId());
        }
        startPage();
        Map<String, Object> map = new HashMap<>();
        //统计各个承办庭线索数量
        List<Map<String, Object>> countList = dubvioEventService.queryEventFyrInfoByFyy(fyId);

        if (!StringUtils.isNull(countList)) {
            for (int i = 0; i < countList.size(); i++) {

                Object cbrId = countList.get(i).get("jbxx_cbr_id");


                if (!StringUtils.isNull(cbrId)) {

                    String dept = clueService.queryDeptByRyId(cbrId.toString());
                    String fycs = dubvioEventService.queryEventFycs(cbrId.toString());
                    String age = clueService.queryAgeById(cbrId.toString());
                    countList.get(i).put("fycs", fycs);
                    countList.get(i).put("dept", dept);
                    countList.get(i).put("age", age);
                } else {
                    countList.get(i).put("fycs", "0");
                }


            }
        }

        return getDataTable(countList);
    }



    @GetMapping("/eventFyrTjDetail")
    public String fyrTjDetail(String id, String index, ModelMap mmap) {

        mmap.put("id", id);
        mmap.put("index", index);
        return "module/dubvioEvent/fyrEventTjDetail";
    }


    @PostMapping("/eventFyrDetailById")
    @ResponseBody
    public TableDataInfo fyrDetailById(String id, String index) {
        startPage();
        if (index.equals("1")){

            index="民事";
        }else if (index.equals("2")){
            index="刑事";
        }else if(index.equals("3")){
            index="执行";
        }else if (index.equals("4")){
            index="行政";
        }
        DubvioEvent dubvioEvent = new DubvioEvent();
        dubvioEvent.setJbxxCbrId(id);
        dubvioEvent.setJbxxAjlb(index);
        dubvioEvent.setDeleteFlag("0");
        PageDomain pageDomain = TableSupport.buildPageRequest();

        List<DubvioEvent> list = dubvioEventService.selectDubvioEventList(dubvioEvent,pageDomain);

        return getDataTable(list);
    }

    @RequiresPermissions("module:dubvioEvent:importTemp")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<DubvioEvent> util = new ExcelUtil<DubvioEvent>(DubvioEvent.class);
        return util.importTemplateExcel("信访数据");
    }

    @Log(title = "信访管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("module:dubvioEvent:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<DubvioEvent> util = new ExcelUtil<DubvioEvent>(DubvioEvent.class);
        List<DubvioEvent> dubvioEventList = util.importExcel(file.getInputStream());
        String message = dubvioEventService.importDubvioEvent(dubvioEventList);
        return AjaxResult.success(message);
    }


}
