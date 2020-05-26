package com.datcent.project.module.hiTaskinst.controller;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.constant.Constants;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.ServletUtils;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.project.module.clue.domain.Clue;
import com.datcent.project.module.clue.service.IClueService;
import com.datcent.project.module.dubvioEvent.domain.DubvioEvent;
import com.datcent.project.module.dubvioEvent.service.IDubvioEventService;
import com.datcent.project.module.ruTask.domain.CommentInfo;
import com.datcent.project.system.notice.domain.Notice;
import com.datcent.project.system.notice.service.INoticeService;
import com.datcent.project.system.user.service.IUserService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.*;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.datcent.project.module.hiTaskinst.domain.HiTaskinst;
import com.datcent.project.module.hiTaskinst.service.IHiTaskinstService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;
import sun.nio.cs.ext.GBK;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 历史任务 信息操作处理
 * 
 * @author datcent
 * @date 2019-01-16
 */
@Controller
@RequestMapping("/module/hiTaskinst")
public class HiTaskinstController extends BaseController
{
    private String prefix = "module/hiTaskinst";
	
	@Autowired
	private IHiTaskinstService hiTaskinstService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private IUserService userService;

	@Autowired
	private INoticeService noticeService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private IDubvioEventService dubvioEventService;
	@Autowired
	private IClueService clueService;

	@RequiresPermissions("module:hiTaskinst:view")
	@GetMapping()
	public String hiTaskinst()
	{
	    return prefix + "/hiTaskinst";
	}
	
	/**
	 * 查询历史任务列表
	 */
	@RequiresPermissions("module:hiTaskinst:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(HiTaskinst hiTaskinst)
	{
		//PAGE_SIZE
		Integer pageNum = ServletUtils.getParameterToInt(Constants.PAGE_NUM);
		Integer pageSize = ServletUtils.getParameterToInt(Constants.PAGE_SIZE);
		Long userid = ShiroUtils.getUserId();
		HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
		query.taskAssignee(userid.toString()).finished();
		if(hiTaskinst.getParams().containsKey("startTime")){
			String startTime = hiTaskinst.getParams().get("startTime").toString();
			if(StringUtils.isNotEmpty(startTime)){
				Date d_startTime =  DateUtils.dateTime("yyyy-MM-dd",startTime);
				query.taskCompletedAfter(d_startTime);
			}
		}
		if(hiTaskinst.getParams().containsKey("endTime")){
			String endTime = hiTaskinst.getParams().get("endTime").toString();
			if(StringUtils.isNotEmpty(endTime)){
				Date d_endTime =  DateUtils.dateTime("yyyy-MM-dd",endTime);
				query.taskCompletedBefore(d_endTime);
			}
		}
		if(StringUtils.isNotEmpty(hiTaskinst.getType())){
			query.processVariableValueEquals("type",hiTaskinst.getType());
		}
		query.orderByTaskCreateTime().desc();
		List<HiTaskinst> taskinstList = new ArrayList<HiTaskinst>();
		List<HistoricTaskInstance> hiTaskinsts = new ArrayList<HistoricTaskInstance>();
		TableDataInfo rspData = new TableDataInfo();
		rspData.setCode(0);
		if(pageNum!=null && pageSize!=null){
			rspData.setTotal(query.count());
			hiTaskinsts = query.listPage(pageSize * (pageNum - 1), pageSize);
		}else{
			hiTaskinsts = query.list();
			rspData.setTotal(hiTaskinsts.size());
		}
		hiTaskinsts.forEach(hi -> {
			try {
					taskinstList.add(new HiTaskinst(hi));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});
		for (HiTaskinst hi: taskinstList) {
			hi.setAssignee(userService.selectUserById(Long.parseLong(hi.getAssignee())).getUserName());
			if(StringUtils.isNotEmpty(hi.getOwner())){
				hi.setOwner(userService.selectUserById(Long.parseLong(hi.getOwner())).getUserName());
			}
			List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(hi.getProcInstId()).list();
			for (HistoricVariableInstance hv:historicVariableInstances) {
				if("title".equals(hv.getVariableName())){
					hi.setTitle(hv.getValue()== null?"":hv.getValue().toString());
				}else if("type".equals(hv.getVariableName())){
					hi.setType(hv.getValue()== null?"":hv.getValue().toString());
				}
			}
		}
		rspData.setRows(taskinstList);
		return rspData;
	}

	@PostMapping("/main")
	@ResponseBody
	public TableDataInfo main(HiTaskinst hiTaskinst)
	{
		//PAGE_SIZE
		Integer pageNum = ServletUtils.getParameterToInt(Constants.PAGE_NUM);
		Integer pageSize = ServletUtils.getParameterToInt(Constants.PAGE_SIZE);
		Long userid = ShiroUtils.getUserId();
		HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
		query.taskAssignee(userid.toString()).finished();
		query.orderByTaskCreateTime().desc();
		List<HiTaskinst> taskinstList = new ArrayList<HiTaskinst>();
		List<HistoricTaskInstance> hiTaskinsts = new ArrayList<HistoricTaskInstance>();
		TableDataInfo rspData = new TableDataInfo();
		rspData.setCode(0);
		if(pageNum!=null && pageSize!=null){
			rspData.setTotal(query.count());
			hiTaskinsts = query.listPage(pageSize * (pageNum - 1), pageSize);
		}else{
			hiTaskinsts = query.list();
			rspData.setTotal(hiTaskinsts.size());
		}
		hiTaskinsts.forEach(hi -> {
			try {
				taskinstList.add(new HiTaskinst(hi));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});
		for (HiTaskinst hi: taskinstList) {
			hi.setAssignee(userService.selectUserById(Long.parseLong(hi.getAssignee())).getUserName());
			if(StringUtils.isNotEmpty(hi.getOwner())){
				hi.setOwner(userService.selectUserById(Long.parseLong(hi.getOwner())).getUserName());
			}
			List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(hi.getProcInstId()).list();
			for (HistoricVariableInstance hv:historicVariableInstances) {
				if("title".equals(hv.getVariableName())){
					hi.setTitle(hv.getValue()==null?"":hv.getValue().toString());
				}else if("type".equals(hv.getVariableName())){
					hi.setType(hv.getValue()==null?"":hv.getValue().toString());
				}
			}
		}
		rspData.setRows(taskinstList);
		return rspData;
	}

	
	/**
	 * 新增历史任务
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存历史任务
	 */
	@RequiresPermissions("module:hiTaskinst:add")
	@Log(title = "历史任务", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(HiTaskinst hiTaskinst)
	{		
		return toAjax(hiTaskinstService.insertHiTaskinst(hiTaskinst));
	}

	/**
	 * 修改历史任务
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		HiTaskinst hiTaskinst = hiTaskinstService.selectHiTaskinstById(id);
		mmap.put("hiTaskinst", hiTaskinst);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存历史任务
	 */
	@RequiresPermissions("module:hiTaskinst:edit")
	@Log(title = "历史任务", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(HiTaskinst hiTaskinst)
	{		
		return toAjax(hiTaskinstService.updateHiTaskinst(hiTaskinst));
	}
	
	/**
	 * 删除历史任务
	 */
	@RequiresPermissions("module:hiTaskinst:remove")
	@Log(title = "历史任务", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(hiTaskinstService.deleteHiTaskinstByIds(ids));
	}

	@RequiresPermissions("module:hiTaskinst:detail")
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id")String id,ModelMap modelMap) throws Exception{
		HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(id).singleResult();
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery().processInstanceId(historicProcessInstance.getId()).list();
		//流程类型
		String type = "";
		//申请人
		String proposerName = "";

		for (HistoricVariableInstance hi:historicVariableInstances) {
			if("type".equals(hi.getVariableName())){
				type = hi.getValue().toString();
			}else if(ActivitiDefinftion.ACTIVITI_PROPOSER_NAME.equals(hi.getVariableName())){
				proposerName = hi.getValue().toString();
			}
		}
//		//查询所有的委托记录
//		Owner o = new Owner();
//		o.setProcessinstandId(historicProcessInstance.getId());
//		List<Owner> ownerList = ownerService.selectOwnerList(o);
//		for (Owner ow:ownerList) {
//			if(ow.getEndDate()==null){
//				ow.setISOK("处理中...");
//			}else{
//			}
//		}
		//查询所有备注信息
		List<CommentInfo> comments = new ArrayList<CommentInfo>();
		List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().processInstanceId(historicProcessInstance.getId()).orderByTaskCreateTime().desc().list();
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
		modelMap.put("commentInfoList",comments);
//		modelMap.put("ownerList",ownerList);
		modelMap.put("proposerName",proposerName);
		if(ActivitiDefinftion.ACTIVITI_NOTICE_TYPE.equals(type)){
			String noticeId = historicProcessInstance.getBusinessKey();
			Notice notice = noticeService.selectNoticeById(noticeId);
			modelMap.put("notice",notice);
			return prefix+"/hiTaskDetail";
		}else if(ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_TYPE.equals(type)||ActivitiDefinftion.ACTIVITI_REMOVE_DUBVIOEVENT_TYPE.equals(type)||ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_JCC_TYPE.equals(type)||ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_LA_TYPE.equals(type)||ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_QT_TYPE.equals(type)||ActivitiDefinftion.ACTIVITI_DUBVIOEVENT_TH_TYPE.equals(type)){
			String dubvioId = historicProcessInstance.getBusinessKey();
			DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(dubvioId);
			modelMap.put("dubvioEvent",dubvioEvent);
			return prefix+"/hiTaskDubvio";
		}else{
			String clueId = historicProcessInstance.getBusinessKey();
			Clue clue = clueService.selectClueById(clueId);
			DubvioEvent dubvioEvent = dubvioEventService.selectDubvioEventById(clue.getDubvioId());
			modelMap.put("dubvioEvent",dubvioEvent);
			modelMap.put("clue",clue);
			return prefix+"/hiTaskClue";
		}
	}

	@RequiresPermissions("module:hiTaskinst:processImg")
	@GetMapping("/showProcessImg")
	public String showProcessImg(String id,ModelMap modelMap) throws ParseException {
		List<CommentInfo> comments = new ArrayList<CommentInfo>();
		HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(id).singleResult();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).singleResult();
		List<HistoricTaskInstance>  historicTaskInstanceList = null;
		if(processInstance !=null){
			historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().orderByTaskCreateTime().desc()
					.processInstanceId(processInstance.getId())
					.list();//返回一个list
		}else{
			HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).singleResult();
			historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().orderByTaskCreateTime().desc()
					.processInstanceId(historicProcessInstance.getId())
					.list();//返回一个list
		}
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
		ProcessDefinition processDefinition = hiTaskinstService.getProcessDefinitionByTaskId(id);
		modelMap.put("dId",processDefinition.getId());
		modelMap.put("dName",processDefinition.getDiagramResourceName());
		modelMap.put("taskId",id);
		modelMap.put("commentList",comments);
		return prefix+"/processImages";
	}

	@GetMapping("/lookProcessImg")
	public void lookprocessImg(String deploymentId, String resourceName,String taskId,HttpServletResponse response) throws Exception{
		BpmnModel bpmnModel = repositoryService.getBpmnModel(deploymentId);
		InputStream is = null;
		ProcessDiagramGenerator p = new DefaultProcessDiagramGenerator();
		if(taskId!=null && taskId!=""){
			HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).singleResult();
			if(processInstance != null){
				List<String> activeIds = runtimeService.getActiveActivityIds(historicTaskInstance.getProcessInstanceId());
				is = p.generateDiagram(bpmnModel, "png", activeIds, Collections.<String> emptyList(), "宋体", "宋体","宋体",
						null, 1.0);
			}else{
				is = p.generateDiagram(bpmnModel,"png","宋体","宋体","宋体",null);
			}
		}else{
			is = p.generateDiagram(bpmnModel,"png","宋体","宋体","宋体",null);
		}

		if(is!=null){
			byte[] data = IOUtils.toByteArray(is);
			ServletOutputStream os = (ServletOutputStream) response
					.getOutputStream();
			os.write(data, 0, data.length);
			os.flush();
			os.close();
		}
	}

}
