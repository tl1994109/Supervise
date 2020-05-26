package com.datcent.project.module.ruTask.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.constant.Constants;
import com.datcent.common.exception.file.FileNameLengthLimitExceededException;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.ServletUtils;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.project.module.dispositionForm.domain.DispositionForm;
import com.datcent.project.module.dispositionForm.service.IDispositionFormService;
import com.datcent.project.module.hiTaskinst.service.IHiTaskinstService;
import com.datcent.project.module.clue.domain.Clue;
import com.datcent.project.module.clue.service.IClueService;
import com.datcent.project.module.dubvioEvent.domain.DubvioEvent;
import com.datcent.project.module.dubvioEvent.service.IDubvioEventService;
import com.datcent.project.module.ruTask.domain.CommentInfo;
import com.datcent.project.module.ruTask.domain.HistoricActivity;
import com.datcent.project.system.notice.domain.Notice;
import com.datcent.project.system.notice.service.INoticeService;
import com.datcent.project.system.person.domain.Person;
import com.datcent.project.system.person.service.IPersonService;
import com.datcent.project.system.user.domain.User;
import com.datcent.project.system.user.domain.UserRole;
import com.datcent.project.system.user.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.spring.integration.Activiti;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.aspectj.weaver.loadtime.Aj;
import org.jsoup.helper.StringUtil;
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
import com.datcent.project.module.ruTask.domain.RuTask;
import com.datcent.project.module.ruTask.service.IRuTaskService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 任务列 信息操作处理
 *
 * @author datcent
 * @date 2019-01-12
 */
@Controller
@RequestMapping("/module/ruTask")
public class RuTaskController extends BaseController {
    private String prefix = "module/ruTask";

    @Autowired
    private IRuTaskService ruTaskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private IClueService clueService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private IDubvioEventService dubvioEventService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IHiTaskinstService hiTaskinstService;

    @Autowired
    private IDispositionFormService dispositionFormService;

    @Autowired
    private IPersonService personService;


    @RequiresPermissions("module:ruTask:view")
    @GetMapping()
    public String ruTask() {
        return prefix + "/ruTask";
    }

    /**
     * 查询任务列列表
     */
    @RequiresPermissions("module:ruTask:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RuTask ruTask) {
        //PAGE_SIZE
        Integer pageNum = ServletUtils.getParameterToInt(Constants.PAGE_NUM);
        Integer pageSize = ServletUtils.getParameterToInt(Constants.PAGE_SIZE);
        Long userid = ShiroUtils.getUserId();
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.taskAssignee(userid.toString()).orderByTaskCreateTime().desc();
        if(ruTask.getParams().containsKey("startTime")){
            String startTime = ruTask.getParams().get("startTime").toString();
            if(StringUtils.isNotEmpty(startTime)){
                Date d_startTime =  DateUtils.dateTime("yyyy-MM-dd",startTime);
                taskQuery.taskCreatedAfter(d_startTime);
            }
        }
        if(ruTask.getParams().containsKey("endTime")){
            String endTime = ruTask.getParams().get("endTime").toString();
            if(StringUtils.isNotEmpty(endTime)){
                Date d_endTime =  DateUtils.dateTime("yyyy-MM-dd",endTime);
                taskQuery.taskCreatedBefore(d_endTime);
            }
        }
        if(StringUtils.isNotEmpty(ruTask.getType())){
            taskQuery.processVariableValueEquals("type",ruTask.getType());
        }
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        List<Task> taskList = new ArrayList<Task>();
        if (pageNum != null && pageSize != null) {
            rspData.setTotal(taskQuery.count());
            taskList = taskQuery.listPage(pageSize * (pageNum - 1), pageSize);
        } else {
            taskList = taskQuery.list();
            rspData.setTotal(taskList.size());
        }
        List<RuTask> ruTaskList = new ArrayList<RuTask>();
        taskList.forEach(run -> {
            try {
                ruTaskList.add(new RuTask(run));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        for (RuTask task : ruTaskList) {
            Map<String, Object> stringObjectMap = taskService.getVariables(task.getId());
            task.setIsok(stringObjectMap.get("isok").toString());
            task.setProposerName(taskService.getVariable(task.getId(), "proposerName").toString());
            task.setType(taskService.getVariable(task.getId(), "type").toString());
            task.setAssignee(userService.selectUserById(Long.parseLong(task.getAssignee())).getUserName());
            task.setTitle(taskService.getVariable(task.getId(), "title")==null?"":taskService.getVariable(task.getId(), "title").toString());
        }
        rspData.setRows(ruTaskList);
        return rspData;
    }

    @PostMapping("/main")
    @ResponseBody
    public TableDataInfo main(RuTask ruTask) {
        //PAGE_SIZE
        Integer pageNum = ServletUtils.getParameterToInt(Constants.PAGE_NUM);
        Integer pageSize = ServletUtils.getParameterToInt(Constants.PAGE_SIZE);
        Long userid = ShiroUtils.getUserId();
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.taskAssignee(userid.toString()).orderByTaskCreateTime().desc();
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        List<Task> taskList = new ArrayList<Task>();
        if (pageNum != null && pageSize != null) {
            rspData.setTotal(taskQuery.count());
            taskList = taskQuery.listPage(pageSize * (pageNum - 1), pageSize);
        } else {
            taskList = taskQuery.list();
            rspData.setTotal(taskList.size());
        }
        List<RuTask> ruTaskList = new ArrayList<RuTask>();
        taskList.forEach(run -> {
            try {
                ruTaskList.add(new RuTask(run));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        for (RuTask task : ruTaskList) {
            task.setIsok(taskService.getVariable(task.getId(),"isok").toString());
            task.setProposerName(taskService.getVariable(task.getId(), "proposerName").toString());
            task.setType(taskService.getVariable(task.getId(), "type").toString());
            task.setAssignee(userService.selectUserById(Long.parseLong(task.getAssignee())).getUserName());
            task.setTitle(taskService.getVariable(task.getId(), "title") == null?"":taskService.getVariable(task.getId(), "title").toString());
            task.setOwner(task.getOwner() == null ? null : userService.selectUserById(Long.parseLong(task.getOwner())).getUserName());
        }
        rspData.setRows(ruTaskList);
        return rspData;
    }

    /**
     * 审批
     */
    @RequiresPermissions("module:ruTask:examine")
    @GetMapping("/approval/{id}")
    public String edit(@PathVariable("id") String id, String type, String taskId, String isok, ModelMap mmap) throws Exception {
        List<HistoricActivityInstance> historicActivityInstance = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(id).orderByHistoricActivityInstanceStartTime().desc().list();
        Iterator<HistoricActivityInstance> it = historicActivityInstance.iterator();
        while (it.hasNext()) {
            HistoricActivityInstance hi = it.next();
            if ("startEvent".equals(hi.getActivityType()) || "endEvent".equals(hi.getActivityType())) {
                it.remove();
            }
        }
        List<HistoricActivity> activityList = new ArrayList<HistoricActivity>();
        historicActivityInstance.forEach(hi -> {
            try {
                activityList.add(new HistoricActivity(hi));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(id).singleResult();
        for (HistoricActivity his : activityList) {
            if (StringUtils.isNotEmpty(his.getAssignee())) {
                his.setAssignee(userService.selectUserById(Long.parseLong(his.getAssignee())).getUserName());
            }
            if (StringUtils.isNotEmpty(his.getOwner())) {
                his.setOwner(userService.selectUserById(Long.parseLong(his.getOwner())).getUserName());
            }
        }
        //判断后面是否有审批人
        List<String> list = new ArrayList<String>();
        //获取任务对象
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //获取流程定义Id
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());
        //获取流程实例实例ID
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId()).singleResult();
        //获取当前活动id
        String activityId = pi.getActivityId();
        //获取当前活动
        ActivityImpl activity = processDefinitionEntity.findActivity(activityId);
        //获取当前活动完成之后连线名称
        List<PvmTransition> transitionList = activity.getOutgoingTransitions();
        if (transitionList != null && transitionList.size() > 0) {
            for (PvmTransition pvm : transitionList) {
                String name = "";
                if (pvm.getProperty("name") != null) {
                    name = pvm.getProperty("name").toString();
                    if (StringUtils.isNotBlank(name)) {
                        list.add(name);
                    } else {
                        list.add("默认提交");
                    }
                }
            }
        }
        //判断夏季节点是否该出现选择审批人
        if (list.contains(ActivitiDefinftion.ACTIVITI_FINISH)) {
            mmap.put("proIsok", 0);
        } else {
            mmap.put("proIsok", 1);
            Person person = personService.selectPersonById(ShiroUtils.getUser().getPersonId());
            List<User> userList = userService.selectUserListByDeptId(person.getDeptId().toString());
            mmap.put("userList", userList);
        }
        mmap.put("taskId", taskId);
        mmap.put("pid", id);
        if (ActivitiDefinftion.ACTIVITI_NOTICE_TYPE.equals(type)) {
            String noticeId = historicProcessInstance.getBusinessKey();
            Notice notice = noticeService.selectNoticeById(noticeId);
            mmap.put("notice", notice);
            mmap.put("hisAct", activityList);
            mmap.put("isok", isok);
            return prefix + "/approval";
        } else if (ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_TYPE.equals(type) || ActivitiDefinftion.ACTIVITI_REMOVE_DUBVIOEVENT_TYPE.equals(type)||ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_JCC_TYPE.equals(type)||ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_LA_TYPE.equals(type)||ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_QT_TYPE.equals(type)||ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_TH_TYPE.equals(type)) {
            String dubvioId = historicProcessInstance.getBusinessKey();
            DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(dubvioId);
            Map<String, Object> paramsMap = taskService.getVariables(task.getId());
            if (paramsMap != null && paramsMap.containsKey("cause")) {
                mmap.put("cause", paramsMap.get("cause").toString());
            }
            mmap.put("dubvioEvent", dubvioEvent);
            mmap.put("isok", "true");
            mmap.put("hisAct", activityList);
            return prefix + "/dubvioApproval";
        } else {
            String clueId = historicProcessInstance.getBusinessKey();
            Clue clue = clueService.selectClueById(clueId);
            DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(clue.getDubvioId());
            mmap.put("dubvioEvent", dubvioEvent);
            mmap.put("clue", clue);
            mmap.put("clueId", clue.getClueId());
            mmap.put("content", taskService.getVariable(task.getId(), "content")==null?"":taskService.getVariable(task.getId(), "content").toString());
            //mmap.put("proposerTime",taskService.getVariable(task.getId(),"proposerTime").toString());
            mmap.put("clueClassify", taskService.getVariable(task.getId(), "clueClassify")==null?"":taskService.getVariable(task.getId(), "clueClassify").toString());
            mmap.put("proposerName", taskService.getVariable(task.getId(), "proposerName")==null?"":taskService.getVariable(task.getId(), "proposerName").toString());
            mmap.put("resultType", taskService.getVariable(task.getId(), "resultType")==null?"":taskService.getVariable(task.getId(), "resultType").toString());
            mmap.put("localCourtPerson",getlocalCourtPerson());
            Map<String, Object> paramsMap = (Map<String, Object>) taskService.getVariable(task.getId(), "paramsMap");
            if (paramsMap != null) {
                for (Map.Entry<String, Object> m : paramsMap.entrySet()) {
                    mmap.put(m.getKey(), m.getValue());
                }
            }
            //判断夏季节点是否该出现选择审批人
            if (list.contains(ActivitiDefinftion.ACTIVITI_FINISH)) {
                mmap.put("proIsok", 0);
            } else {
                mmap.put("proIsok", 1);
                Person person = personService.selectPersonById(ShiroUtils.getUser().getPersonId());
                List<User> userList = userService.selectUserListByDeptId(person.getDeptId().toString());
                mmap.put("userList", userList);
            }
            mmap.put("taskId", taskId);
            mmap.put("pid", id);
            String handleUrl = taskService.getVariable(task.getId(), "handleUrl").toString();
            mmap.put("hisAct", activityList);
            return handleUrl;
        }
    }


    /**
     * @Author: 张梦圆
     * @Email: zhangmengyuan@datcent.com
     * @CreateDate: 2019/1/14 11:22
     * @param:
     * @return:
     * @exception:
     * @Description: TODO 提交任务
     **/
    @PostMapping("/compleTask")
    @ResponseBody
    public AjaxResult compleTask(String isok, String taskId, String opinion, String pid, String approver) {
        try {
            Map<String, Object> map = taskService.getVariables(taskId);
            DubvioEvent dubvioEvent = new DubvioEvent();
            if(map.containsKey("dubvioId")){
                String dubvioId = map.get("dubvioId").toString();
                dubvioEvent = dubvioEventService.selectDubvioEventById(dubvioId);
            }
            String type = map.get("type").toString();
            map.put("isok", isok);
            if (StringUtils.isNotEmpty(approver) && !"".equals(approver)) {
                map.put("approver", approver);
            } else {
                map.remove("approver");
            }
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

            //设置批注人
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "user#" + ShiroUtils.getUserId());
            //设置审批标识
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "isok#" + isok);
            //设置审批意见
            taskService.addComment(task.getId(), task.getProcessInstanceId(), opinion == null ? "content#提交申请" : "content#" + opinion.replaceAll("#", ""));
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "date#" + DateUtils.getTime());
            taskService.complete(task.getId(), map);
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(pid).singleResult();
            //判断流程是否结束
            if (processInstance == null) {
                if ("公告审批".equals(type)) {
                    String noticeId = map.get("noticeId").toString();
                    Notice notice = noticeService.selectNoticeById(noticeId);
                    if ("true".equals(isok)) {
                        notice.setProcessStatus("0");
                    } else {
                        notice.setProcessStatus("1");
                    }
                    noticeService.updateNotice(notice, null, null,null);
                } else if ("可疑违纪转入线索".equals(type)) {
                    if ("true".equals(isok) && dubvioEvent!=null) {
                        Clue clue = new Clue();
                        clue.setDubvioId(dubvioEvent.getDubvioId());
                        clue.setClueId(StringUtils.getUUID());
                        clue.setDubvioStrategyId(dubvioEvent.getDubvioStrategyId());
                        clue.setDubvioStrategyName(dubvioEvent.getDubvioStrategyName());
                        clue.setJbxxAjbh(dubvioEvent.getJbxxAjbh());
                        clue.setJbxxAh(dubvioEvent.getJbxxAh());
                        clue.setJbxxCbrId(dubvioEvent.getJbxxCbrId());
                        clue.setJbxxCbrName(dubvioEvent.getJbxxCbrName());
                        clue.setJbxxBgr(dubvioEvent.getJbxxBgr());
                        clue.setJbxxCbtId(dubvioEvent.getJbxxCbtId());
                        clue.setJbxxCbtName(dubvioEvent.getJbxxCbtName());
                        clue.setJbxxAjlb(dubvioEvent.getJbxxAjlb());
                        clue.setJbxxSarq(dubvioEvent.getJbxxSarq());
                        clue.setJbxxLarq(dubvioEvent.getJbxxLarq());
                        clue.setJbxxFarq(dubvioEvent.getJbxxFarq());
                        clue.setJbxxKtrq(dubvioEvent.getJbxxKtrq());
                        clue.setClueSource(dubvioEvent.getDubvioSource());
                        clue.setJbxxJarq(dubvioEvent.getJbxxJarq());
                        clue.setJbxxGdrq(dubvioEvent.getJbxxGdrq());
                        clue.setRisksLevel(dubvioEvent.getRisksLevel());
                        clue.setCreateTime(DateUtils.getNowDate());
                        clue.setJbfyId(dubvioEvent.getJbfyId());
                        clue.setClueFydx(dubvioEvent.getJbxxCbrName());
                        clue.setClueFyrdh(dubvioEvent.getDubvioFyrdh());
                        clue.setClueRemarks("线索处置");

                        if (dubvioEvent.getDubvioFyr() == null || dubvioEvent.getDubvioFyr().equals("")) {
                            clue.setClueFyr("系统检测");
                        } else {
                            clue.setClueFyr(dubvioEvent.getDubvioFyr());
                        }
                        clueService.insertClue(clue);
                        dubvioEvent.setStatus("0");
                        dubvioEvent.setDubvioCzjg("4");
                    } else {
                       dubvioEvent.setStatus("8");
                    }

                } else if ("可疑违纪事件删除".equals(type)) {
//                    String dubvioId = map.get("dubvioId").toString();
//                    DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(dubvioId);
                    if ("true".equals(isok)) {
                        dubvioEvent.setDeleteFlag("1");
                        String loginName = ShiroUtils.getLoginName();
                        dubvioEvent.setStatus("0");
                        dubvioEvent.setDeleteBy(loginName);
                    }
                    else {
                        dubvioEvent.setStatus("13");
                        dubvioEvent.setDeleteFlag("0");
                    }

                } else if ("可疑违纪事件退回".equals(type)) {

                    if ("true".equals(isok)) {
                        dubvioEvent.setStatus("3");
                        dubvioEvent.setDubvioCzjg("0");

                    }else {
                        dubvioEvent.setStatus("9");
                    }

                } else if ("可疑违纪事件转入立案二庭".equals(type)) {

                    if ("true".equals(isok)) {
                        dubvioEvent.setStatus("3");
                        dubvioEvent.setDubvioCzjg("1");
                    }else {
                        dubvioEvent.setStatus("10");
                    }
                } else if ("可疑违纪事件转入监察处".equals(type)) {

                    if ("true".equals(isok)) {
                        dubvioEvent.setStatus("3");
                        dubvioEvent.setDubvioCzjg("2");

                    }else {
                        dubvioEvent.setStatus("11");
                    }
                } else if ("可疑违纪事件转其他".equals(type)) {
                        if ("true".equals(isok)) {
                            dubvioEvent.setStatus("3");
                            dubvioEvent.setDubvioCzjg("3");

                        }else {
                            dubvioEvent.setStatus("12");
                        }
                }
                if(map.containsKey("dubvioId")){
                    dubvioEventService.updateDubvioEvent(dubvioEvent);
                }
            }
            return success();
        } catch (Exception e) {
            return error();
        }
    }

    @RequiresPermissions("module:ruTask:histaskList")
    @GetMapping("/histList")
    public String histList(String proInstId, String taskId, ModelMap modelMap) throws Exception {
        List<CommentInfo> comments = new ArrayList<CommentInfo>();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(proInstId).singleResult();
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().orderByTaskCreateTime().desc()
                .processInstanceId(proInstId)
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
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
        modelMap.put("dId", processDefinition.getId());
        modelMap.put("dName", processDefinition.getDiagramResourceName());
        modelMap.put("taskId", taskId);
        modelMap.put("commentList", comments);
        return prefix + "/histList";
    }

    @GetMapping("/setOwner")
    public String setOwner(String taskId, ModelMap modelMap) {
        Map<String, Object> map = taskService.getVariables(taskId);
        String type = map.get("type").toString();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if (ActivitiDefinftion.ACTIVITI_NOTICE_TYPE.equals(type)) {
            String noticeId = pi.getBusinessKey();
            Notice notice = noticeService.selectNoticeById(noticeId);
            User user = getUser();
            List<User> userList = userService.selectUserByDepId(user.getDeptId());
            modelMap.put("notice", notice);
            modelMap.put("userList", userList);
            modelMap.put("taskId", task.getId());
            modelMap.put("pid", task.getProcessInstanceId());
            return prefix + "/setOwner";
        } else if (ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_TYPE.equals(type) || ActivitiDefinftion.ACTIVITI_REMOVE_DUBVIOEVENT_TYPE.equals(type)||ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_JCC_TYPE.equals(type)||ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_LA_TYPE.equals(type)||ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_QT_TYPE.equals(type)||ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_TH_TYPE.equals(type)) {
            String dubvioId = pi.getBusinessKey();
            DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(dubvioId);
            User user = getUser();
            List<User> userList = userService.selectUserByDepId(user.getDeptId());
            modelMap.put("dubvioEvent", dubvioEvent);
            modelMap.put("userList", userList);
            modelMap.put("taskId", task.getId());
            modelMap.put("pid", task.getProcessInstanceId());
            return prefix + "/setDubvioApproval";
        }
        return "1";
    }

    @PostMapping("/endProc")
    @ResponseBody
    public AjaxResult endProc(String pid, String type, String id) throws FileUploadBase.FileSizeLimitExceededException, FileNameLengthLimitExceededException, IOException {
        //公告终止流程
        if (ActivitiDefinftion.ACTIVITI_NOTICE_TYPE.equals(type)) {
            Notice notice = noticeService.selectNoticeByPid(pid);
            notice.setProcessStatus("2");
            int i = noticeService.updateNotice(notice, null, null,null);
            if(i>0){
                AjaxResult aj = deleteHis(pid,id);
                if("0".equals(aj.get("code").toString())){
                    return success();
                }else{
                    return error();
                }
            }else{
                return error();
            }
        } else {
            return error();
        }
    }

    public AjaxResult deleteHis(String pid,String id){
        try {
            ActivityImpl endActivity = findActivitiImpl(id, "end");
            commitProcess(id, null, endActivity.getId());
            historyService.deleteHistoricProcessInstance(pid);
        } catch (Exception e) {
            return error("系统错误！");
        }
        return success();
    }
    /* 根据任务ID和节点ID获取活动节点 <br>
     */
    public ActivityImpl findActivitiImpl(String taskId, String activityId)
            throws Exception {

        // 取得流程定义
        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

        // 获取当前活动节点ID
        if (StringUtils.isEmpty(activityId)) {
            activityId = findTaskById(taskId).getTaskDefinitionKey();
        }

        // 根据流程定义，获取该流程实例的结束节点
        if (activityId.toUpperCase().equals("END")) {
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {
                List<PvmTransition> pvmTransitionList = activityImpl
                        .getOutgoingTransitions();
                if (pvmTransitionList.isEmpty()) {
                    return activityImpl;
                }
            }
        }

        // 根据节点ID，获取对应的活动节点
        ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition)
                .findActivity(activityId);
        return activityImpl;
    }

    public ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(
            String taskId) throws Exception {

        // 取得流程定义
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(findTaskById(taskId).getProcessDefinitionId());

        if (processDefinition == null) {
            throw new Exception("流程定义未找到!");
        }

        return processDefinition;
    }

    /*
     * 通过任务id获取任务对象
     */
    public Task findTaskById(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        return task;
    }

    /*
     * 逐步提交任务过程（终止任务的中间方法）
     *
     */
    public void commitProcess(String taskId, Map<String, Object> variables,
                              String activityId) throws Exception {

        if (variables == null) {
            variables = new HashMap<String, Object>();
        }
        // 跳转节点为空，默认提交操作
        if (StringUtils.isEmpty(activityId)) {
            taskService.complete(taskId, variables);
        } else {// 流程转向操作
            turnTransition(taskId, activityId, variables);
        }

    }

    public void turnTransition(String taskId, String activityId,
                               Map<String, Object> variables) throws Exception {

        // 当前节点
        ActivityImpl currActivity = findActivitiImpl(taskId, null);
        // 清空当前流向
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

        // 创建新流向
        TransitionImpl newTransition = currActivity.createOutgoingTransition();
        // 目标节点
        ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
        // 设置新流向的目标节点
        newTransition.setDestination(pointActivity);

        // 执行转向任务
        taskService.complete(taskId, variables);
        // 删除目标节点新流入
        pointActivity.getIncomingTransitions().remove(newTransition);

        // 还原以前流向
        restoreTransition(currActivity, oriPvmTransitionList);

    }

    /**
     * 清空指定活动节点流向
     *
     * @param activityImpl 活动节点
     * @return 节点流向集合
     */
    private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
        // 存储当前节点所有流向临时变量
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
        // 获取当前节点所有流向，存储到临时变量，然后清空
        List<PvmTransition> pvmTransitionList = activityImpl
                .getOutgoingTransitions();
        for (PvmTransition pvmTransition : pvmTransitionList) {
            oriPvmTransitionList.add(pvmTransition);
        }
        pvmTransitionList.clear();

        return oriPvmTransitionList;
    }

    /**
     * 还原指定活动节点流向
     *
     * @param activityImpl         活动节点
     * @param oriPvmTransitionList 原有节点流向集合
     */
    private void restoreTransition(ActivityImpl activityImpl,
                                   List<PvmTransition> oriPvmTransitionList) {
        // 清空现有流向
        List<PvmTransition> pvmTransitionList = activityImpl
                .getOutgoingTransitions();
        pvmTransitionList.clear();
        // 还原以前流向
        for (PvmTransition pvmTransition : oriPvmTransitionList) {
            pvmTransitionList.add(pvmTransition);
        }
    }
}
