package com.datcent.project.system.notice.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.exception.file.FileNameLengthLimitExceededException;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.file.FileUploadUtils;
import com.datcent.common.utils.file.FileUtils;
import com.datcent.project.module.hiTaskinst.service.IHiTaskinstService;
import com.datcent.project.module.ruTask.domain.CommentInfo;
import com.datcent.project.system.dept.service.IDeptService;
import com.datcent.project.system.person.domain.Person;
import com.datcent.project.system.person.service.IPersonService;
import com.datcent.project.system.user.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.catalina.manager.util.SessionUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.domain.AjaxResult;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.project.module.noticeClick.domain.NoticeClick;
import com.datcent.project.module.noticeClick.mapper.NoticeClickMapper;
import com.datcent.project.module.noticeClick.service.INoticeClickService;
import com.datcent.project.module.process.domain.Process;
import com.datcent.project.module.process.domain.ProcessMatter;
import com.datcent.project.module.process.domain.ProcessNodeDetail;
import com.datcent.project.module.process.domain.ProcessResume;
import com.datcent.project.module.process.service.IProcessMatterService;
import com.datcent.project.module.process.service.IProcessNodeDetailService;
import com.datcent.project.module.process.service.IProcessResumeService;
import com.datcent.project.module.process.service.IProcessService;
import com.datcent.project.system.notice.domain.Notice;
import com.datcent.project.system.notice.service.INoticeService;
import com.datcent.project.system.user.domain.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @描述: 公告 信息操作处理
 * @创建人: zhou_shiji
 * @创建时间: 2018年11月16日下午5:02:06
 */
@Controller
@RequestMapping("/system/notice")
public class NoticeController extends BaseController {
    private static final Logger log = Logger.getLogger(NoticeController.class);
    private String prefix = "system/notice";

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private IProcessService processService;

    @Autowired
    private IProcessNodeDetailService processNodeDetailService;

    @Autowired
    private IProcessResumeService processResumeService;

    @Autowired
    private IProcessMatterService processMatterService;

    @Autowired
    private INoticeClickService noticeClickService;

    @Autowired
    private NoticeClickMapper noticeClickMapper;

    @Autowired
    private IUserService userService;

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

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IHiTaskinstService hiTaskinstService;

    @Autowired
    private IPersonService personService;

    @Value("${datcent.profile}")
    private String profile;

    @RequiresPermissions("system:notice:view")
    @GetMapping()
    public String notice() {
        return prefix + "/notice";
    }

    /**
     * 查询公告列表
     */
    @RequiresPermissions("system:notice:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Notice notice) {
        startPage();
        User user = getUser();
        notice.setCreateBy(user.getUserId().toString());
        List<Notice> list = noticeService.selectNoticeListOrderByprocessStatus(notice);
        return getDataTable(list);
    }

    @RequiresPermissions("module:notice:allListView")
    @GetMapping("/allListView")
    public String notice_allList() {
        return prefix + "/notice_allList";
    }
    /**
     * 查询所有公告列表
     *
     */
    @RequiresPermissions("module:notice:allListView")
    @PostMapping("/allList")
    @ResponseBody
    public TableDataInfo allList(Notice notice){
        startPage();
        List<Notice> noticeList = noticeService.selectNoticeList(notice);
        return getDataTable(noticeList);
    }

    /**
     * 查询公告列表首页展示
     */
    @PostMapping("/main")
    @RequiresPermissions("system:notice:main")
    @ResponseBody
    public TableDataInfo main(Notice notice) {
        startPage();
        notice.setProcessStatus("0");
        List<Notice> noticeList = noticeService.selectNoticeList(notice);
        return getDataTable(noticeList);
    }

    /**
     * 新增公告
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存公告
     */
    @RequiresPermissions("system:notice:add")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Notice notice,MultipartFile file,MultipartFile file_pdf) throws FileUploadBase.FileSizeLimitExceededException, FileNameLengthLimitExceededException, IOException {
         return toAjax(noticeService.insertNotice(notice,file,file_pdf,profile));
    }

    /**
     * 修改公告
     */
    @GetMapping("/edit/{noticeId}")
    public String edit(@PathVariable("noticeId") String noticeId, ModelMap mmap) {
        Notice notice = noticeService.selectNoticeById(noticeId);
        mmap.put("notice", notice);
        return prefix + "/edit";
    }

    /**
     * 发布公告
     */
    @GetMapping("/release/{noticeId}")
    public String release(@PathVariable("noticeId") String noticeId, ModelMap mmap) {
        Notice notice = noticeService.selectNoticeById(noticeId);
        Process process = new Process();
        process.setStatus("0");
        //List<Process> processList = processService.selectProcessList(process);
        mmap.put("notice", notice);
        Person person = personService.selectPersonById(ShiroUtils.getUser().getPersonId());
        List<User> userList = userService.selectUserListByDeptId(person.getDeptId().toString());
        mmap.put("userList", userList);
        return prefix + "/release";
    }

    /**
     * 首页已通过审核公告列表更多--页面跳转
     *
     * @return
     */
    @GetMapping("/checkNotice/{noticeType}")
    public String checkNotice(@PathVariable("noticeType") String noticeType, ModelMap mmap) {
        mmap.put("noticeType", noticeType);
        return prefix + "/noticeReadOnly";
    }

    /**
     * 查询已通过审核公告列表更多--数据加载
     */
    @PostMapping("/morelist")
    @ResponseBody
    public TableDataInfo morelist(Notice notice) {
        startPage();
        if (notice.getNoticeType().equals("0")) {
            notice.setNoticeType("");
        }
        notice.setProcessStatus("0");
        notice.setReleaseBy(ShiroUtils.getUserId().toString());
        List<Notice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 查看单个公告
     */
    @GetMapping("/check/{noticeId}")
    public String check(@PathVariable("noticeId") String noticeId, ModelMap mmap) {
        mmap.put("notice", noticeService.selectNoticeById(noticeId));
        NoticeClick noticeClick = new NoticeClick();
        noticeClick.setUserId(Integer.valueOf(ShiroUtils.getUserId().toString()));
        noticeClick.setNoticeId(noticeId.toString());
        //新增点击记录
        noticeClickService.insertNoticeClick(noticeClick);
        //查询点击数量
        mmap.put("clickCount", noticeClickMapper.selectNoticeClickCount(noticeClick));
        return prefix + "/check";
    }

    /**
     * 修改保存公告
     */
    @RequiresPermissions("system:notice:edit")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Notice notice,MultipartFile multipartFile,MultipartFile file_pdf) throws FileUploadBase.FileSizeLimitExceededException, FileNameLengthLimitExceededException, IOException {
        return toAjax(noticeService.updateNotice(notice,multipartFile,file_pdf,profile));
    }

    /**
     * 删除公告
     */
    @RequiresPermissions("system:notice:remove")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        AjaxResult ajaxResult = new AjaxResult();
        if (ids.indexOf(",") > -1) {
            String[] idsStr = ids.split(",");
            String newIds = "";
            for (String item : idsStr) {
                Notice notice = noticeService.selectNoticeById(item);
                if ("2".equals(notice.getProcessStatus()) || "3".equals(notice.getProcessStatus())) {
                    newIds += "," + notice.getNoticeId();
                }
            }
            newIds = newIds.replaceFirst(",", "");
            String[] newIdsArray = newIds.split(",");
            if (newIdsArray.length < idsStr.length) {
                int i=noticeService.deleteNoticeByIds(newIds);
                if(i>0){
                    ajaxResult.put("code",0);
                    ajaxResult.put("msg","操作成功，部分审批中和审批通过无法删除");
                }else{
                    ajaxResult.put("code",0);
                    ajaxResult.put("msg","操作成功，无法删除");
                }
            } else if (newIdsArray.length == idsStr.length) {
                int i=noticeService.deleteNoticeByIds(ids);
                if(i>0){
                    ajaxResult.put("code",0);
                    ajaxResult.put("msg","操作成功");
                }
            }
            return ajaxResult;
        } else {
            Notice notice = noticeService.selectNoticeById(ids);
            if ("2".equals(notice.getProcessStatus()) || "3".equals(notice.getProcessStatus())) {
                int i=noticeService.deleteNoticeByIds(ids);
                if(i>0){
                    ajaxResult.put("code",0);
                    ajaxResult.put("msg","操作成功");
                }
                return ajaxResult;
            }else if("0".equals(notice.getProcessStatus())){
                return error("审批通过，无法删除");
            }else{
                return error("审批中，无法删除");
            }
        }
    }

    /**
     * @Author: zhangmengyuan
     * @Email: zghangmengyuan@datcent.com
     * @CreateDate: 2019/1/10 14:34
     * @Copyright: © 2018 德讯科技 版权所有
     * @param:
     * @return:
     * @exception:
     * @Description: TODO  开始申报
     **/
    @RequiresPermissions("module:processMatter:addNotice")
    @Log(title = "发布公告", businessType = BusinessType.INSERT)
    @RequestMapping("/releaseNotice")
    @ResponseBody
    public AjaxResult releaseNotice(String matterId, String approverName,String approver,String content){
        try {
            //流程定义的key
            String processDefinitionKey = "noticeProcess";
            Map<String,Object> map = new HashMap<String,Object>();
            Notice notice = noticeService.selectNoticeById(matterId);
            map.put("isok","true");
            map.put("title",notice.getNoticeTitle());
            map.put("type", ActivitiDefinftion.ACTIVITI_NOTICE_TYPE);
            map.put("noticeId",notice.getNoticeId());
            //申请人
            map.put("proposerName",ShiroUtils.getUser().getUserName());
            map.put("proposer",ShiroUtils.getUserId());
            //审批人
            map.put("approverName",approverName);
            map.put("approver",approver);
            //使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
            ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefinitionKey,map);
            log.info("流程实例ID:"+pi.getId());
            log.info("流程定义ID:"+pi.getProcessDefinitionId());
            //查询当前流程实例任务对象
            Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
            runtimeService.updateBusinessKey(pi.getId(), notice.getNoticeId());
            //设置批注人
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"user#"+ShiroUtils.getUserId());
            //设置审批标识
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"isok#"+true);
            //设置审批意见
            taskService.addComment(task.getId(),task.getProcessInstanceId(),content == null?"content#提交申请":"content#"+content.replaceAll("#",""));
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"date#"+ DateUtils.getTime());
            //设置当前任务
            taskService.setAssignee(task.getId(),ShiroUtils.getUserId().toString());
            //提交任务并将流程变量放到任务中
            taskService.complete(task.getId(),map);

            notice.setProcessStatus("3");
            notice.setPid(pi.getId());
            noticeService.updateNotice(notice,null,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            return error();
        }
        return success();
    }

    @GetMapping("/hisTaskList")
    public String notice_hisTaskList(String matterId, ModelMap modelMap) throws ParseException {
        Notice notice = noticeService.selectNoticeById(matterId);
        List<CommentInfo> comments = new ArrayList<CommentInfo>();
        String pid = "";
        //taskId 获取当前任务所在节点
        String taskId = "";
        //流程执行完
        if("0".equals(notice.getProcessStatus()) || "1".equals(notice.getProcessStatus())){
            pid = historyService.createHistoricProcessInstanceQuery().processInstanceId(notice.getPid()).singleResult().getId();
            //流程执行完随意获取一个task实例
            HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().processInstanceId(pid).orderByTaskCreateTime().desc().list().get(0);
            taskId = historicTaskInstance.getId();
        }else if("3".equals(notice.getProcessStatus())){//流程审批中
            pid = runtimeService.createProcessInstanceQuery().processInstanceId(notice.getPid()).singleResult().getId();
            //获取当前任务实例
            Task task = taskService.createTaskQuery().processInstanceId(pid).orderByTaskCreateTime().desc().list().get(0);
            taskId = task.getId();
        }
        List<HistoricTaskInstance>  historicTaskInstanceList= historyService.createHistoricTaskInstanceQuery().orderByTaskCreateTime().desc()
                .processInstanceId(pid)
                .list();//返回一个list
        if(historicTaskInstanceList!=null && historicTaskInstanceList.size()>0){
            for (HistoricTaskInstance hk: historicTaskInstanceList) {
                String his_taskId = hk.getId();
                List<Comment> his_taskCommentList = taskService.getTaskComments(his_taskId);
                CommentInfo commentInfo = new CommentInfo();
                for (Comment c: his_taskCommentList) {
                    String message = c.getFullMessage();
                    if(c!=null && StringUtils.isNotEmpty(message)){
                        String[] value_arr = message.split("\\#");
                        String varible = value_arr[0];
                        String result = "";
                        if(value_arr.length>1){
                            result = value_arr[1];
                        }
                        if("user".equals(varible)){//用户标识
                            commentInfo.setUserId(result==""?"用户标识为空！":result);
                            if(StringUtils.isNotEmpty(result)){
                                commentInfo.setUserName(userService.selectUserById(Long.parseLong(result)).getUserName());
                            }
                        }else if("isok".equals(varible)){//审批标识
                            commentInfo.setIsok(result);
                        }else if("content".equals(varible)){//批注内容
                            commentInfo.setContent(result);
                        }
                    }
                }
                String startTime =DateUtils.convertDate(hk.getStartTime(),"");
                if(hk.getEndTime()!=null){
                    String endTime = DateUtils.convertDate(hk.getEndTime(),"");
                    commentInfo.setEndTime(endTime);
                }
                if(StringUtils.isEmpty(commentInfo.getUserName())){
                    commentInfo.setUserName(userService.selectUserById(Long.parseLong(hk.getAssignee())).getUserName());
                }
                if(StringUtils.isEmpty(commentInfo.getIsok())){
                    commentInfo.setIsok("处理中...");
                }
                commentInfo.setStartTime(startTime);
                comments.add(commentInfo);
            }
        }
        ProcessDefinition processDefinition = hiTaskinstService.getProcessDefinitionByTaskId(taskId);
        modelMap.put("dId",processDefinition.getId());
        modelMap.put("dName",processDefinition.getDiagramResourceName());
        modelMap.put("taskId",taskId);
        modelMap.put("comments",comments);
        return prefix+"/notice_hisTaskList";
    }

    @RequiresPermissions(value={"module:notice:detail","module:notice:look"},logical = Logical.OR)
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id")String id,ModelMap modelMap){
        Notice notice = noticeService.selectNoticeById(id);
        modelMap.put("notice",notice);
        return prefix+"/notice_detail";
    }

    @PostMapping("/noticePhoto")
    @ResponseBody
    public List<Notice> noticePhoto()
    {
        return noticeService.selectNoticePhoto();
    }
}
