package com.datcent.project.module.ruTask.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.web.domain.AjaxResult;
import com.datcent.project.module.clue.domain.Clue;
import com.datcent.project.module.clue.service.IClueService;
import com.datcent.project.module.dispositionAttachment.domain.DispositionAttachment;
import com.datcent.project.module.dispositionAttachment.service.IDispositionAttachmentService;
import com.datcent.project.module.dispositionForm.domain.DispositionForm;
import com.datcent.project.module.dispositionForm.service.IDispositionFormService;
import com.datcent.project.module.dispositionHandle.domain.DispositionHandle;
import com.datcent.project.module.dispositionHandle.service.IDispositionHandleService;
import com.datcent.project.system.person.domain.Person;
import com.datcent.project.system.person.service.IPersonService;
import com.datcent.project.system.user.domain.User;
import com.datcent.project.system.user.service.IUserService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.datcent.project.module.ruTask.mapper.RuTaskMapper;
import com.datcent.project.module.ruTask.domain.RuTask;
import com.datcent.common.support.Convert;
import com.datcent.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

/**
 * 任务列 服务层实现
 *
 * @author datcent
 * @date 2019-01-12
 */
@Service
public class RuTaskServiceImpl implements IRuTaskService
{
	@Autowired
	private RuTaskMapper ruTaskMapper;

	@Autowired
	private TaskService taskService;

	@Autowired
	private RuntimeService runtimeService;


	@Autowired
	private IDispositionAttachmentService dispositionAttachmentService;

	@Autowired
	private IDispositionHandleService dispositionHandleService;


	@Autowired
	private IDispositionFormService dispositionFormService;


	@Autowired
	private IClueService clueService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IPersonService personService;
	/**
     * 查询任务列信息
     *
     * @param id 任务列ID
     * @return 任务列信息
     */
    @Override
	public RuTask selectRuTaskById(String id)
	{
	    return ruTaskMapper.selectRuTaskById(id);
	}

	/**
     * 查询任务列列表
     *
     * @param ruTask 任务列信息
     * @return 任务列集合
     */
	@Override
	public List<RuTask> selectRuTaskList(RuTask ruTask)
	{
	    return ruTaskMapper.selectRuTaskList(ruTask);
	}

    /**
     * 新增任务列
     *
     * @param ruTask 任务列信息
     * @return 结果
     */
	@Override
	public int insertRuTask(RuTask ruTask)
	{
	    return ruTaskMapper.insertRuTask(ruTask);
	}

	/**
     * 修改任务列
     *
     * @param ruTask 任务列信息
     * @return 结果
     */
	@Override
	public int updateRuTask(RuTask ruTask)
	{
	    return ruTaskMapper.updateRuTask(ruTask);
	}

	/**
     * 删除任务列对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRuTaskByIds(String ids)
	{
		return ruTaskMapper.deleteRuTaskByIds(Convert.toStrArray(ids));
	}


	/**
	 *
	 * 审批流程启动
	 * @param process 流程id
	 * @param clue
	 * @param approver
	 * @param content
	 * @return
	 */
	@Override
	@Transactional
	public AjaxResult submitForm(int count,String resultType,String process, Clue clue, String approver, String content,String isEdit,
								 DispositionAttachment dispositionAttachment,String type,
								 String clueClassify,String handleUrl,String formType,String nodeFlag,String templateUrl){

		try {
			//流程定义的key
			String processDefinitionKey =process;
			Map<String,Object> map = new HashMap<String,Object>();
			Map<String,Object> paramsMap = new HashMap<String,Object>();

			String attchmentId = null;
			if (dispositionAttachment.getAttachmentId() == null) {
				attchmentId = StringUtils.getUUID();
			} else {
				attchmentId = dispositionAttachment.getAttachmentId();
			}
			dispositionAttachment.setAttachmentId(attchmentId);
			paramsMap.put("formType", formType);
            paramsMap.put("resultType", resultType);
			paramsMap.put("attchmentId", attchmentId);
			paramsMap.put("count", count);
			paramsMap.put("formContent", dispositionAttachment.getFormContent());

			map.put("isok","true");
			if (StringUtils.isNotEmpty(clue.getJbxxAh())){
				map.put("title",clue.getJbxxAh());
			}else if (StringUtils.isNotEmpty(dispositionAttachment.getAttachmentName())){
				map.put("title",dispositionAttachment.getAttachmentName());
			}
			map.put("type",type);
			map.put("clueId",clue.getClueId());
			map.put("content",content);
			map.put("paramsMap",paramsMap);
			map.put("clueClassify",clueClassify);
			map.put("handleUrl",handleUrl);
			//申请人
			map.put("proposerName",ShiroUtils.getUser().getUserName());
			map.put("proposer",ShiroUtils.getUserId());
			//审批人
			map.put("approverName",approver);
			map.put("approver",approver);
			//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
			ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefinitionKey,map);
			//查询当前流程实例任务对象
			Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
			runtimeService.updateBusinessKey(pi.getId(),clue.getClueId());
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
			clue.setProcessId(pi.getId());
			if(clue.getProcessStatus().equals("0")){
				clue.setClueCzjd(null);
				clue.setClueCzjg(null);
			}
			clueService.updateClue(clue);

			//修改流程状态
			checkProblem(count,"true",content,clueClassify,clue,nodeFlag,"审核","提交审核","");
			//呈批表单提交
			dispositionSubmit(count,nodeFlag,clue.getClueId(),isEdit,dispositionAttachment,templateUrl);
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error();
		}
		return AjaxResult.success();
	}


	/**
	 * 提交任务   通用方法
	 *
	 * @param  isok,  taskId,  opinion,  pid,  approver
	 * @return 结果
	 */
	@Override
	@Transactional
	public Map<String,Object> compleTask(String isok,String content,String proposerName, String taskId, String opinion, String pid, String approver,Map<String,Object> mmap) {
		Map<String,Object> map =new HashMap<String,Object>();
		Task task=null;
		ProcessInstance processInstance=null;
		content=opinion;
		try {
			map = taskService.getVariables(taskId);
			String type=map.get("type").toString();
			map.put("isok",isok);
			if(StringUtils.isNotEmpty(approver) && !"".equals(approver)){
				map.put("approver",approver);
			}else{
				map.remove("approver");
			}
			//遍历业务层所需要的参数 进行map赋值
			for (Map.Entry<String, Object> m : mmap.entrySet()) {
				map.put(m.getKey(),m.getValue());
			}
			task = taskService.createTaskQuery().taskId(taskId).singleResult();
			//设置批注人
			taskService.addComment(task.getId(),task.getProcessInstanceId(),"user#"+ ShiroUtils.getUserId());
			//设置审批标识
			taskService.addComment(task.getId(),task.getProcessInstanceId(),"isok#"+isok);
			//设置审批意见
			taskService.addComment(task.getId(),task.getProcessInstanceId(),content == null?"content#提交申请":"content#"+content.replaceAll("#",""));
			taskService.addComment(task.getId(),task.getProcessInstanceId(),"date#"+ DateUtils.getTime());
			taskService.complete(task.getId(),map);
			//processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(pid).singleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("task",task);
		map.put("processInstance",processInstance);
		return map;
	}

	/**
	 * 任务审核   通用方法
	 *
	 * @param isok  是否同意
	 * @param opinion 审核批注
	 * @param clueClassify 类型
	 * @param clue 线索
	 * @param s1 成功值
	 * @param s2 失败值
	 */
	@Override
	public void checkProblem(int count,String isok, String opinion, String clueClassify,Clue clue, String nodeFlag,String way,String s1, String s2) {
		DispositionHandle dispositionHandle=new DispositionHandle();
		dispositionHandle.setClueId(clue.getClueId());
		dispositionHandle.setCount(count);
		if(nodeFlag.equals("34")){
			dispositionHandle.setNodeFlag("2");
		}else if(nodeFlag.equals("29")){
			dispositionHandle.setNodeFlag("1");
		}else{
			dispositionHandle.setNodeFlag(nodeFlag);
		}
		List<DispositionHandle> dispositionHandles=dispositionHandleService.selectDeptList(dispositionHandle);
		if (isok.equals("true")) {
			for (DispositionHandle handle : dispositionHandles) {
				if (nodeFlag.equals(handle.getNodeFlag()) || handle.getNodeFlag().equals("1") || handle.getNodeFlag().equals("2")){
					handle.setStatus("1");
					handle.setSuggestion(opinion);
					handle.setCreateBy(ShiroUtils.getUser().getUserName());
					handle.setCreateTimes(DateUtils.getDate());
					handle.setResult(s1);
					dispositionHandleService.updateDispositionHandle(handle);
				}
			}
			clue.setClueStatus("0");
			clue.setClueCzjd(nodeFlag);
			clue.setClueCzjg(clueClassify);
			clue.setClueRemarks(s1);
		} else {
//
//			if (StringUtils.isEmpty(clueClassify)){
//				clue.setClueStatus("1");
//			}else{
//				clue.setProcessStatus("2");
//				clue.setClueStatus("1");
//				clue.setClueCzjg(clueClassify);
//			}
//			clue.setClueCzjd(nodeFlag);
			clue.setClueRemarks(s2);
			if(dispositionHandles.size()>0){
				dispositionHandle=dispositionHandles.get(0);
				dispositionHandle.setStatus("1");
				dispositionHandle.setSuggestion(opinion);
				dispositionHandle.setCreateTimes(DateUtils.getDate());
				dispositionHandle.setResult(s2);
				dispositionHandle.setCreateBy(ShiroUtils.getUser().getUserName());
				dispositionHandleService.updateDispositionHandle(dispositionHandle);
			}
		}
		if(clue.getProcessStatus().equals("0")){
			clue.setClueCzjd(null);
			clue.setClueCzjg(null);
		}
		clueService.updateClue(clue);
	}

	/**
	 * 页面跳转时判断表单是否填写
	 * @param clueId
	 * @param returnUrl
	 * @param modelMap
	 * @return
	 */
	@Override
	public String returnUrl(String nodeFlag,String clueId,String returnUrl, ModelMap modelMap) {
		modelMap.put("clueId",clueId);
		//审批页面  所有  表单
		DispositionForm dispositionForm=new DispositionForm();
		Clue clue = clueService.selectClueById(clueId);
		// 判断处置流程是否执行
		String czjg = clue.getClueCzjg();
		DispositionHandle handle=new DispositionHandle();
		handle.setClueId(clue.getClueId());
		//handle.setNodeType(czjg);
		handle.setStatus("1");
		handle.setNodeFlag(nodeFlag);
		boolean flag=false;
		if(modelMap.get("count")!=null && modelMap.get("count")!=""){
			handle.setCount(Integer.parseInt(modelMap.get("count").toString()));
		}
		List<DispositionHandle> handleList=dispositionHandleService.selectDeptList(handle);
		String deptId="";
		for (DispositionHandle dispositionHandle : handleList) {
			if (nodeFlag.equals(dispositionHandle.getNodeFlag())){
				deptId=dispositionHandle.getDeptId();
				flag=true;
				break;
			}
		}
		if (flag){
			//提交表单查找
			DispositionAttachment dispositionAttachment=new DispositionAttachment();
			dispositionAttachment.setClueId(clueId);
			dispositionAttachment.setHandleId(deptId);
			List<DispositionAttachment> dispositionAttachments = dispositionAttachmentService.selectDispositionAttachmentList(dispositionAttachment);
			dispositionAttachment=dispositionAttachments.get(0);
			modelMap.put("attchmentId",dispositionAttachment.getAttachmentId());
			modelMap.put("dispositionAttachment",dispositionAttachment);
			returnUrl = "module/problemdisposal/publicform";
		}else {
			dispositionForm.setFormType(clue.getClueCzjg());
			List<DispositionForm> dispositions = dispositionFormService.selectDispositionFormList(dispositionForm);
			//获取审批人
			Person person=personService.selectPersonById(ShiroUtils.getUser().getPersonId());
			List<User> userList = userService.selectUserListByDeptId(person.getDeptId());
			modelMap.put("userList",userList);
			modelMap.put("dispositions",dispositions);
			returnUrl = returnUrl;
		}
		return returnUrl;
	}

	@Override
	public void dispositionSubmit(int count,String nodeFlag,String clueId, String isEdit, DispositionAttachment dispositionAttachment,String templateUrl) {
		//查找当前填写表单所在流程节点
	    DispositionHandle dispositionHandle=new DispositionHandle();
		dispositionHandle.setNodeFlag(nodeFlag);
		dispositionHandle.setClueId(clueId);
		dispositionHandle.setCount(count);
		List<DispositionHandle> dispositionHandles = dispositionHandleService.selectDeptList(dispositionHandle);
		for (DispositionHandle handle : dispositionHandles) {
			dispositionAttachment.setHandleId(handle.getDeptId());
		}

        dispositionAttachment.setNodeFlag(nodeFlag);
		dispositionAttachment.setClueId(clueId);
		if (isEdit.equals("true")) {
			dispositionAttachment.setUpdateBy(ShiroUtils.getUser().getUserName());
			dispositionAttachment.setAttachmentUrl(templateUrl);
			dispositionAttachment.setUpdateTime(new Date());
			dispositionAttachmentService.updateDispositionAttachment(dispositionAttachment);
		} else {
			dispositionAttachment.setReceiveBy(ShiroUtils.getUser().getUserName());
            dispositionAttachment.setAttachmentUrl(templateUrl);
			dispositionAttachment.setReceiveTime(new Date());
			dispositionAttachmentService.insertDispositionAttachment(dispositionAttachment);
		}
	}

}
