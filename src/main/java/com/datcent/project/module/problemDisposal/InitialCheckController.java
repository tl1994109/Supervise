package com.datcent.project.module.problemDisposal;

import com.datcent.activiti.ActivitiDefinftion;
import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.domain.AjaxResult;
import com.datcent.project.module.clue.domain.Clue;
import com.datcent.project.module.clue.service.IClueService;
import com.datcent.project.module.dispositionAttachment.domain.DispositionAttachment;
import com.datcent.project.module.dispositionAttachment.service.IDispositionAttachmentService;
import com.datcent.project.module.dispositionForm.domain.DispositionForm;
import com.datcent.project.module.dispositionForm.service.IDispositionFormService;
import com.datcent.project.module.dispositionHandle.domain.DispositionHandle;
import com.datcent.project.module.dispositionHandle.service.IDispositionHandleService;
import com.datcent.project.module.ruTask.service.IRuTaskService;
import com.datcent.project.system.notice.domain.Notice;
import com.datcent.project.system.user.domain.User;
import com.datcent.project.system.user.service.IUserService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问题处置管理 信息操作处理
 *
 * @author wujing
 * @date 2019-1-23
 */
@Controller
@RequestMapping("/module/initcheck")
public class InitialCheckController extends BaseController
{
	private String prefix = "module/initcheck";

	@Autowired
	private IDispositionFormService dispositionFormService;

	@Autowired
	private IDispositionAttachmentService dispositionAttachmentService;

	@Autowired
	private IRuTaskService ruTaskService;

	@Autowired
	private IClueService clueService;

	@Autowired
	private IDispositionHandleService dispositionHandleService;

	@Autowired
	private IUserService userService;


	//---------------------------------------------------------------------------------------------------------------------------------------------------------------

	public InitialCheckController() {
	}

	/**
	 * 线索处置跳转 初步核实
	 *
	 * @author zhoushiji
	 * @date 2019-1-17
	 */
	@GetMapping("/cbhscpform")
	public String cbhsform(String clueId,String count, ModelMap modelMap){
		String returnUrl=prefix + "/cbhscpform";
		String nodeFlag="7";
		modelMap.put("count",count);
		returnUrl = ruTaskService.returnUrl(nodeFlag,clueId,returnUrl,modelMap);
		return returnUrl;
	}



	/**
	 *
	 * 初步核实 修改的呈批表 提交
	 * @param content
	 * @param approver
	 * @param clueId
	 * @param isEdit
	 * @param dispositionAttachment
	 * @return
	 */
	@PostMapping("/xgcbhscpcompleTask")
	@ResponseBody
	public AjaxResult xgcbhscpcompleTask (int count,String resultType, String pid,String opinion,String approverName,String taskId,String content,  String approver, String clueId, String isEdit,DispositionAttachment dispositionAttachment,String formType,String templateUrl) {
		System.out.println("---------------------------上传路径："+templateUrl);
		Clue clue = clueService.selectClueById(clueId);
		String process="cbhscpProcess";
		String clueClassify="3";
		String type="初步核实呈批";
		String handleUrl=prefix+"/spcbhscpform";
		Map<String, Object> mmap = new HashMap<String, Object>();
		mmap.put("formType", formType);
		mmap.put("resultType", resultType);
		mmap.put("attchmentId", dispositionAttachment.getAttachmentId());
		mmap.put("formContent", dispositionAttachment.getFormContent());
		if (StringUtils.isNotEmpty(clue.getJbxxAh())){
			mmap.put("title",clue.getJbxxAh());
		}else if (StringUtils.isNotEmpty(dispositionAttachment.getAttachmentName())){
			mmap.put("title",dispositionAttachment.getAttachmentName());
		}
		mmap.put("type",type);
		mmap.put("clueId",clue.getClueId());
		mmap.put("dispositionAttachment",dispositionAttachment);
		mmap.put("content",content);
		mmap.put("clueClassify",clueClassify);
		mmap.put("handleUrl", handleUrl);
		String nodeFlag="7";
		ruTaskService.compleTask("true",content,approverName,taskId,content,pid,approver,mmap);
		//表单修改
		ruTaskService.dispositionSubmit(count,nodeFlag,clueId,isEdit,dispositionAttachment,templateUrl);
		return success("保存成功");
	}

	/**
	 *
	 * 初步核实 呈批表 提交
	 * @param content
	 * @param approver
	 * @param clueId
	 * @param isEdit
	 * @param dispositionAttachment
	 * @return
	 */
	@PostMapping("/cbhscpcompleTask")
	@ResponseBody
	public AjaxResult cbhscpcompleTask(int count,String content,  String approver, String clueId, String isEdit,DispositionAttachment dispositionAttachment,String formType,String templateUrl) {


        System.out.println("---------------------------上传路径："+templateUrl);
		Clue clue = clueService.selectClueById(clueId);
		String process="cbhscpProcess";
		String clueClassify="3";
		String type="初步核实呈批";
		String handleUrl=prefix+"/spcbhscpform";
		String nodeFlag="7";
		AjaxResult ajaxResult = ruTaskService.submitForm(count,"",process,clue,approver,content,isEdit,dispositionAttachment,
				type,clueClassify,handleUrl,formType,nodeFlag,templateUrl);
		return ajaxResult;
	}

	/**
	 * 初步核实呈批表审核
	 *
	 * @author wujing
	 * @date 2019-1-26
	 */
	@PostMapping("/spcbhscpCompleTask")
	@ResponseBody
	@Transactional
	public AjaxResult spcbhscpCompleTask(int  count,String isok,String formType, String content, String proposerName, String clueId, String taskId, String opinion, String nodeFlag, String pid, String approver, String approverName, String clueClassify,DispositionAttachment dispositionAttachment) {
		try {
			String cbResult="";
			String truehandleUrl=prefix+"/cbhsfaform";
			String falsehandleUrl=prefix+"/xgcbhscpform";
			String trueRemarks="同意-初步核实呈批表";
			String falseRemarks="不同意-初步核实呈批表";
			String paramNodeFlag="8";
			sameAduditing( count,isok,cbResult,formType, content, proposerName, clueId, taskId, opinion, pid, approver, clueClassify, dispositionAttachment, truehandleUrl, falsehandleUrl, trueRemarks, falseRemarks, paramNodeFlag);
			return success();
		} catch (Exception e) {
			e.printStackTrace();
			return error();
		}
	}


	/**
	 * 线索处置跳转 初步核实方案
	 *
	 * @author zhoushiji
	 * @date 2019-1-17
	 */
	@GetMapping("/cbhsfaform")
	public String cbhsfaform(String clueId,String count, ModelMap modelMap){
		String returnUrl=prefix + "/cbhsfaform";
		String nodeFlag="9";
		modelMap.put("count",count);
		returnUrl = ruTaskService.returnUrl(nodeFlag,clueId,returnUrl,modelMap);
		return returnUrl;
	}


	/**
	 * 初步核实 修改的工作方案 提交  修改
	 *
	 * @param isok
	 * @param taskId
	 * @param opinion
	 * @param pid
	 * @param approver
	 * @return
	 */
	@PostMapping("/xgcbhsfacompleTask")
	@ResponseBody
	public AjaxResult xgcbhsfacompleTask(int count, String clueId,String content, String proposerName,
										String attachmentName, String formContent,
										String isok, String taskId, String opinion,
										String pid, String approver, String clueClassify,
										String nodeFlag, String formType,String isEdit,DispositionAttachment dispositionAttachment,String templateUrl) {
		Clue clue = clueService.selectClueById(clueId);
		String process="cbhsfaProcess";
		String type="初步核实工作方案提交";
		String handleUrl=prefix+"/spcbhsfaform";
		nodeFlag="9";
		Map<String, Object> mmap = new HashMap<String, Object>();
		mmap.put("formType", formType);
		mmap.put("attchmentId", dispositionAttachment.getAttachmentId());
		mmap.put("formContent", dispositionAttachment.getFormContent());
		if (StringUtils.isNotEmpty(clue.getJbxxAh())){
			mmap.put("title",clue.getJbxxAh());
		}else if (StringUtils.isNotEmpty(dispositionAttachment.getAttachmentName())){
			mmap.put("title",dispositionAttachment.getAttachmentName());
		}
		mmap.put("type",type);
		mmap.put("clueId",clue.getClueId());
		mmap.put("dispositionAttachment",dispositionAttachment);
		mmap.put("content",content);
		mmap.put("clueClassify",clueClassify);
		mmap.put("handleUrl", handleUrl);
		ruTaskService.compleTask("true",content,proposerName,taskId,content,pid,approver,mmap);
		//表单修改
		ruTaskService.dispositionSubmit(count,nodeFlag,clueId,isEdit,dispositionAttachment,templateUrl);
		return success("保存成功");
	}
	/**
	 * 初步核实 工作方案 提交
	 *
	 * @param isok
	 * @param taskId
	 * @param opinion
	 * @param pid
	 * @param approver
	 * @return
	 */
	@PostMapping("/cbhsfacompleTask")
	@ResponseBody
	public AjaxResult cbhsfacompleTask(int count, String clueId,String content, String proposerName,
									   String attachmentName, String formContent,
									   String isok, String taskId, String opinion,
									   String pid, String approver, String clueClassify,
									   String nodeFlag, String formType,String isEdit,DispositionAttachment dispositionAttachment,String templateUrl) {
		Clue clue = clueService.selectClueById(clueId);
		String process="cbhsfaProcess";
		String type="初步核实工作方案提交";
		String handleUrl=prefix+"/spcbhsfaform";
		nodeFlag="9";
		AjaxResult ajaxResult = ruTaskService.submitForm(count,"",process,clue,approver,content,isEdit,dispositionAttachment,
				type,clueClassify,handleUrl,formType,nodeFlag,templateUrl);
		return ajaxResult;
	}
	/**
	 * 初步核实工作方案审核
	 *
	 * @author wujing
	 * @date 2019-1-26
	 */
	@PostMapping("/spcbhsfaCompleTask")
	@ResponseBody
	@Transactional
	public AjaxResult spcbhsfaCompleTask(int  count,String isok,String formType, String content, String proposerName, String clueId, String taskId, String opinion, String nodeFlag, String pid, String approver, String approverName, String clueClassify,DispositionAttachment dispositionAttachment) {
		try {
			String cbResult="";
			String truehandleUrl=prefix+"/cbhsqkbgform";
			String falsehandleUrl=prefix+"/xgcbhsfaform";
			String trueRemarks="同意-初步核实工作方案";
			String falseRemarks="不同意-初步核实工作方案";
			String paramNodeFlag="10";
			sameAduditing( count,isok,cbResult, formType, content, proposerName, clueId, taskId, opinion, pid, approver, clueClassify, dispositionAttachment, truehandleUrl, falsehandleUrl, trueRemarks, falseRemarks, paramNodeFlag);
			return success();
		} catch (Exception e) {
			return error();
		}
	}


	/**
	 * 线索处置跳转 初步核实情况
	 *
	 * @author zhoushiji
	 * @date 2019-1-17
	 */
	@GetMapping("/cbhsqkform")
	public String cbhsqkform(String clueId,String count, ModelMap modelMap){
		String returnUrl=prefix + "/cbhsqkbgform";
		String nodeFlag="11";
		modelMap.put("count",count);
		returnUrl = ruTaskService.returnUrl(nodeFlag,clueId,returnUrl,modelMap);
		return returnUrl;
	}

	/**
	 * 初步核实 情况报告 提交 修改
	 *
	 * @param isok
	 * @param taskId
	 * @param opinion
	 * @param pid
	 * @param approver
	 * @return
	 */
	@PostMapping("/xgcbhsqkbgcompleTask")
	@ResponseBody
	public AjaxResult xgcbhsqkbgcompleTask(int count,String content,String resultType, String proposerName, String attachmentName, String formContent,
										 String isok, String taskId, String opinion, String pid, String approver,
										 String clueId, String clueClassify, String nodeFlag, String formType,String isEdit,
										 DispositionAttachment dispositionAttachment,String templateUrl) {
		Clue clue = clueService.selectClueById(clueId);
		String process="cnhsqkProcess";
		String type="初步核实情况报告提交";
		String handleUrl=prefix+"/spcbhsqkbgform";
		nodeFlag="11";
		Map<String, Object> mmap = new HashMap<String, Object>();
		mmap.put("formType", formType);
		mmap.put("resultType",resultType);
		mmap.put("attchmentId", dispositionAttachment.getAttachmentId());
		mmap.put("formContent", dispositionAttachment.getFormContent());
		if (StringUtils.isNotEmpty(clue.getJbxxAh())){
			mmap.put("title",clue.getJbxxAh());
		}else if (StringUtils.isNotEmpty(dispositionAttachment.getAttachmentName())){
			mmap.put("title",dispositionAttachment.getAttachmentName());
		}
		mmap.put("type",type);
		mmap.put("clueId",clue.getClueId());
		mmap.put("dispositionAttachment",dispositionAttachment);
		mmap.put("content",content);
		mmap.put("clueClassify",clueClassify);
		mmap.put("handleUrl", handleUrl);
		ruTaskService.compleTask("true",content,proposerName,taskId,content,pid,approver,mmap);
		//表单修改
		ruTaskService.dispositionSubmit(count,nodeFlag,clueId,isEdit,dispositionAttachment,templateUrl);
		return success("保存成功");
	}

	/**
	 * 初步核实 情况报告 提交
	 *
	 * @param isok
	 * @param taskId
	 * @param opinion
	 * @param pid
	 * @param approver
	 * @return
	 */
	@PostMapping("/cbhsqkbgcompleTask")
	@ResponseBody
	public AjaxResult cbhsqkbgcompleTask(int count,String content,String resultType, String proposerName, String attachmentName, String formContent,
										 String isok, String taskId, String opinion, String pid, String approver,
										 String clueId, String clueClassify, String nodeFlag, String formType,String isEdit,
										 DispositionAttachment dispositionAttachment,String templateUrl) {
		Clue clue = clueService.selectClueById(clueId);
		String process="cnhsqkProcess";
		String type="初步核实情况报告提交";
		String handleUrl=prefix+"/spcbhsqkbgform";
		nodeFlag="11";
		AjaxResult ajaxResult = ruTaskService.submitForm(count,resultType,process,clue,approver,content,isEdit,dispositionAttachment,
				type,clueClassify,handleUrl,formType,nodeFlag,templateUrl);
		return ajaxResult;
	}

	/**
	 * 初步核实情况报告审核
	 *
	 * @author wujing
	 * @date 2019-1-26
	 */
	@PostMapping("/spcbhsqkbgCompleTask")
	@ResponseBody
	@Transactional
	public AjaxResult spcbhsqkbgCompleTask(int  count,String isok,String resultType,String formType,
										   String content, String proposerName,
										   String clueId, String taskId, String opinion, String nodeFlag,
										   String pid, String approver, String approverName,
										   String clueClassify,DispositionAttachment dispositionAttachment) {

		try {
		if ("true".equals(isok)){
			String truehandleUrl="";
			String falsehandleUrl=prefix+"/xgcbhsqkbgform";
			String trueRemarks="同意-初步核实情况报告";
			String falseRemarks="不同意-初步核实情况报告";
			String paramNodeFlag="12";
			sameAduditing( count,isok,resultType, formType, content, proposerName, clueId, taskId, opinion, pid, approver, clueClassify, dispositionAttachment, truehandleUrl, falsehandleUrl, trueRemarks, falseRemarks, paramNodeFlag);
			DispositionHandle dispositionHandle = new DispositionHandle();
			dispositionHandle.setParentId(clueId);
			dispositionHandle.setSuggestion(opinion);
			dispositionHandle.setDeptName("最后处置结果");
			dispositionHandle.setNodeId(taskId);
			dispositionHandle.setDeptId(StringUtils.getUUID());
			dispositionHandle.setNodeType(clueClassify);
			dispositionHandle.setWay("归类");
			dispositionHandle.setClueId(clueId);
			dispositionHandle.setCreateBy(ShiroUtils.getUser().getUserName());
			dispositionHandle.setCreateTimes(DateUtils.getDate());
			if(!resultType.equals("6") && !resultType.equals("7")){
				Clue clue = clueService.selectClueById(clueId);
				if(resultType.equals("1")){
					dispositionHandle.setSuggestion("予以了结");
					clue.setClueCzjd("29");
					clue.setProcessStatus("0");
					dispositionHandle.setNodeFlag("0");
				}else if(resultType.equals("2")){
					dispositionHandle.setSuggestion("移送有关单位处理");
					clue.setClueCzjd("30");
					clue.setProcessStatus("0");
					dispositionHandle.setNodeFlag("0");
				}else if(resultType.equals("10")){
					dispositionHandle.setSuggestion("");
					clue.setClueCzjd("33");
					clue.setClueCzjg("10");
					clueClassify="10";
					clue.setProcessStatus("1");
					dispositionHandle.setNodeFlag("0");
					DispositionHandle parentHandle=dispositionHandleService.selectDeptById(clueId);
					parentHandle.setCount(parentHandle.getCount()+1);
					dispositionHandleService.updateDispositionHandle(parentHandle);
					saveDispositionHandle("线索处置分类", "32", "提交", "提出-立案调查", clueId, "立案调查", clueClassify, clue,  "1",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
					saveDispositionHandle("处置审批确认", "33", "审核", "同意-立案调查", clueId, "", clueClassify, clue,  "1",ShiroUtils.getUser().getUserName(),DateUtils.getDate());
					saveDispositionHandle("立案调查呈批表", "36", "提交", "立案调查呈批表-等待审核", clueId, "", clueClassify, clue, "0","",null);
					saveDispositionHandle("立案调查呈批表审核", "37", "审核", "同意-立案调查呈批表", clueId, "", clueClassify, clue,  "0","",null);
					saveDispositionHandle("立案调查呈批报告", "42", "提交", "立案调查呈批报告-等待审核", clueId, "", clueClassify, clue,  "0","",null);
					saveDispositionHandle("立案调查呈批报告审核", "43", "审核", "同意-立案调查呈批报告", clueId, "", clueClassify, clue,  "0","",null);
					saveDispositionHandle("提交立案调查决定", "40", "提交", "立案调查决定-等待审核", clueId, "", clueClassify, clue,  "0","",null);
					saveDispositionHandle("立案调查决定审核", "41", "审核", "同意-立案调查决定", clueId, "", clueClassify, clue,  "0","",null);
				}else if(resultType.equals("4")){
					dispositionHandle.setSuggestion("谈话提醒批评");
					clue.setClueCzjd("31");
					clue.setProcessStatus("0");
					dispositionHandle.setNodeFlag("0");
				}else if(resultType.equals("5")){
					dispositionHandle.setSuggestion("暂存待查");
					clue.setClueCzjd("34");
					clue.setProcessStatus("0");
					dispositionHandle.setNodeFlag("0");
				}
				dispositionHandleService.insertDispositionHandle(dispositionHandle);
				clueService.updateClue(clue);
			}
			return success();
		}else {
			String truehandleUrl="";
			String falsehandleUrl=prefix+"/xgcbhsqkbgform";
			String trueRemarks="同意-初步核实情况报告";
			String falseRemarks="不同意-初步核实情况报告";
			String paramNodeFlag="12";
			sameAduditing( count,isok,resultType, formType, content, proposerName, clueId, taskId, opinion, pid, approver, clueClassify, dispositionAttachment, truehandleUrl, falsehandleUrl, trueRemarks, falseRemarks, paramNodeFlag);
			return success();
		}

		} catch (Exception e) {
			return error();
		}
	}


	/**
	 * 线索处置跳转 立案调查呈批表
	 *
	 * @author wujing
	 * @date 2019-1-17
	 */
	@GetMapping("/lacpform")
	public String lacpform(String clueId,String count, ModelMap modelMap){
		String returnUrl=prefix + "/lacpform";
		String nodeFlag="36";
		modelMap.put("count",count);
		returnUrl = ruTaskService.returnUrl(nodeFlag,clueId,returnUrl,modelMap);
		return returnUrl;
	}
	/**
	 * 线索处置跳转 立案调查呈批报告
	 *
	 * @author wujing
	 * @date 2019-1-17
	 */
	@GetMapping("/labgform")
	public String labgform(String clueId,String count, ModelMap modelMap){

		String returnUrl=prefix + "/labgform";
		String nodeFlag="42";
		modelMap.put("count",count);
		returnUrl = ruTaskService.returnUrl(nodeFlag,clueId,returnUrl,modelMap);
		return returnUrl;
	}
	/**
	 * 线索处置跳转 立案调查决定
	 *
	 * @author wujing
	 * @date 2019-1-17
	 */
	@GetMapping("/lajdform")
	public String lajdform(String clueId,String count, ModelMap modelMap){
		String returnUrl=prefix + "/lajdform";
		String nodeFlag="40";
		modelMap.put("count",count);
		returnUrl = ruTaskService.returnUrl(nodeFlag,clueId,returnUrl,modelMap);
		return returnUrl;
	}

	/**
	 * 立案调查呈批表 提交
	 *
	 * @param isok
	 * @param taskId
	 * @param opinion
	 * @param pid
	 * @param approver
	 * @return
	 */
	@PostMapping("/lacpcompleTask")
	@ResponseBody
	public AjaxResult lacpcompleTask(int count,String content, String proposerName, String attachmentName, String formContent,
									 String isok, String taskId, String opinion, String pid, String approver,
									 String clueId, String clueClassify, String nodeFlag, String formType,
									 String isEdit,DispositionAttachment dispositionAttachment,String templateUrl) {
		Clue clue = clueService.selectClueById(clueId);
		String process="ladccpProcess";
		String type="立案调查呈批表";
		String handleUrl=prefix+"/splacpform";
		nodeFlag="36";
		AjaxResult ajaxResult = ruTaskService.submitForm(count,"",process,clue,approver,content,
				isEdit,dispositionAttachment,type,clueClassify,handleUrl,formType,nodeFlag,templateUrl);
		return ajaxResult;

	}
	/**
	 * 立案调查呈批表 提交 修改
	 *
	 * @param isok
	 * @param taskId
	 * @param opinion
	 * @param pid
	 * @param approver
	 * @return
	 */
	@PostMapping("/xglacpcompleTask")
	@ResponseBody
	public AjaxResult xglacpcompleTask(String content, String proposerName, String attachmentName, String formContent,
									 String isok, String taskId, String opinion, String pid, String approver,
									 String clueId, String clueClassify, String nodeFlag, String formType,
									 String isEdit,DispositionAttachment dispositionAttachment,String templateUrl) {
		Clue clue = clueService.selectClueById(clueId);
		String process="ladccpProcess";
		String type="立案调查呈批表";
		String handleUrl=prefix+"/splacpform";
		nodeFlag="36";
		Map<String, Object> mmap = new HashMap<String, Object>();
		mmap.put("handleUrl", handleUrl);
		ruTaskService.compleTask("true",content,proposerName,taskId,opinion,pid,approver,mmap);
		return AjaxResult.success();

	}

	/**
	 * 立案调查呈批表
	 *
	 * @author zhoushiji
	 * @date 2019-1-26
	 */
	@PostMapping("/splacpCompleTask")
	@ResponseBody
	@Transactional
	public AjaxResult splacpCompleTask(int  count,String isok,String formType, String content, String proposerName, String clueId, String taskId, String opinion, String nodeFlag, String pid, String approver, String approverName, String clueClassify,DispositionAttachment dispositionAttachment) {
		try {
			String resultType="";
			String truehandleUrl=prefix+"/labgform";
			String falsehandleUrl=prefix+"/xglacpform";
			String trueRemarks="同意-立案调查呈批表";
			String falseRemarks="不同意-立案调查呈批表";
			String paramNodeFlag="37";
			Map<String, Object> paramsMap=new HashMap<String, Object>();
			sameAduditing( count,isok,resultType, formType, content, proposerName, clueId, taskId, opinion, pid, approver, clueClassify, dispositionAttachment, truehandleUrl, falsehandleUrl, trueRemarks, falseRemarks, paramNodeFlag,paramsMap);
			return success();
		} catch (Exception e) {
			return error();
		}
	}
	/**
	 * 立案调查呈批报告 提交
	 *
	 * @param isok
	 * @param taskId
	 * @param opinion
	 * @param pid
	 * @param approver
	 * @return
	 */
	@PostMapping("/labgcompleTask")
	@ResponseBody
	public AjaxResult labgcompleTask(int count,String content, String proposerName, String attachmentName, String formContent, String isok,
									 String taskId, String opinion, String pid, String approver, String clueId, String clueClassify,
									 String nodeFlag, String formType,String isEdit,DispositionAttachment dispositionAttachment,String templateUrl) {
		Clue clue = clueService.selectClueById(clueId);
		String process="ladcbgProcess";
		String type="立案调查呈批报告";
		String handleUrl=prefix+"/splabgform";
		nodeFlag="42";
		AjaxResult ajaxResult = ruTaskService.submitForm(count,"",process,clue,approver,content,isEdit,
				dispositionAttachment,type,clueClassify,handleUrl,formType,nodeFlag,templateUrl);
		return ajaxResult;
	}

	/**
	 * 立案调查呈批报告 提交 修改
	 *
	 * @param isok
	 * @param taskId
	 * @param opinion
	 * @param pid
	 * @param approver
	 * @return
	 */
	@PostMapping("/xglabgcompleTask")
	@ResponseBody
	public AjaxResult xglabgcompleTask(int count,String content, String proposerName, String attachmentName, String formContent, String isok,
									 String taskId, String opinion, String pid, String approver, String clueId, String clueClassify,
									 String nodeFlag, String formType,String isEdit,DispositionAttachment dispositionAttachment,String templateUrl) {
		Clue clue = clueService.selectClueById(clueId);
		String process="ladcbgProcess";
		String type="立案调查呈批报告";
		String handleUrl=prefix+"/splabgform";
		nodeFlag="42";
		Map<String, Object> mmap = new HashMap<String, Object>();
		mmap.put("formType", formType);
		mmap.put("attchmentId", dispositionAttachment.getAttachmentId());
		mmap.put("formContent", dispositionAttachment.getFormContent());
		if (StringUtils.isNotEmpty(clue.getJbxxAh())){
			mmap.put("title",clue.getJbxxAh());
		}else if (StringUtils.isNotEmpty(dispositionAttachment.getAttachmentName())){
			mmap.put("title",dispositionAttachment.getAttachmentName());
		}
		mmap.put("type",type);
		mmap.put("clueId",clue.getClueId());
		mmap.put("dispositionAttachment",dispositionAttachment);
		mmap.put("content",content);
		mmap.put("clueClassify",clueClassify);
		mmap.put("handleUrl", handleUrl);
		ruTaskService.compleTask("true",content,proposerName,taskId,content,pid,approver,mmap);
		//表单修改
		ruTaskService.dispositionSubmit(count,nodeFlag,clueId,isEdit,dispositionAttachment,templateUrl);
		return success("保存成功");
	}
	/**
	 * 立案调查呈批报告审核
	 *
	 * @author zhoushiji
	 * @date 2019-1-26
	 */
	@PostMapping("/splabgCompleTask")
	@ResponseBody
	@Transactional
	public AjaxResult splabgCompleTask(int  count,String isok,String formType, String content, String proposerName, String clueId, String taskId, String opinion, String nodeFlag, String pid, String approver, String approverName, String clueClassify,DispositionAttachment dispositionAttachment) {
		try {
			String resultType="";
			String truehandleUrl=prefix+"/lajdform";
			String falsehandleUrl=prefix+"/xglabbgform";
			String trueRemarks="同意-立案调查呈批报告";
			String falseRemarks="不同意-立案调查呈批报告";
			String paramNodeFlag="43";
			Map<String, Object> paramsMap=new HashMap<String, Object>();
			sameAduditing( count,isok,resultType,formType, content, proposerName, clueId, taskId, opinion, pid, approver, clueClassify, dispositionAttachment, truehandleUrl, falsehandleUrl, trueRemarks, falseRemarks, paramNodeFlag,paramsMap);
			return success();
		} catch (Exception e) {
			return error();
		}
	}

	/**
	 * 立案调查决定书 提交
	 *
	 * @param isok
	 * @param taskId
	 * @param opinion
	 * @param pid
	 * @param approver
	 * @return
	 */
	@PostMapping("/lajdcompleTask")
	@ResponseBody
	public AjaxResult lajdcompleTask(int count,String content,String resultType, String proposerName, String attachmentName,
									   String formContent, String isok, String taskId, String opinion, String pid, String approver,
									   String clueId, String clueClassify, String nodeFlag, String formType,String isEdit,
									   DispositionAttachment dispositionAttachment,String templateUrl) {

		Clue clue = clueService.selectClueById(clueId);
		String process="ladcjdProcess";
		String type="立案调查决定书";
		String handleUrl=prefix+"/splajdform";
		nodeFlag="40";
		AjaxResult ajaxResult = ruTaskService.submitForm(count,resultType,process,clue,approver,content,isEdit,
				dispositionAttachment,type,clueClassify,handleUrl,formType,nodeFlag,templateUrl);
		return ajaxResult;
	}
	/**
	 * 立案调查决定书 提交 修改
	 *
	 * @param isok
	 * @param taskId
	 * @param opinion
	 * @param pid
	 * @param approver
	 * @return
	 */
	@PostMapping("/xglajdcompleTask")
	@ResponseBody
	public AjaxResult xglajdcompleTask(int count,String content,String resultType, String proposerName, String attachmentName,
									 String formContent, String isok, String taskId, String opinion, String pid, String approver,
									 String clueId, String clueClassify, String nodeFlag, String formType,String isEdit,
									 DispositionAttachment dispositionAttachment,String templateUrl) {

		Clue clue = clueService.selectClueById(clueId);
		String process="ladcjdProcess";
		String type="立案调查决定书";
		String handleUrl=prefix+"/splajdform";
		Map<String, Object> mmap = new HashMap<String, Object>();
		mmap.put("formType", formType);
		mmap.put("resultType", resultType);
		mmap.put("attchmentId", dispositionAttachment.getAttachmentId());
		mmap.put("formContent", dispositionAttachment.getFormContent());
		if (StringUtils.isNotEmpty(clue.getJbxxAh())){
			mmap.put("title",clue.getJbxxAh());
		}else if (StringUtils.isNotEmpty(dispositionAttachment.getAttachmentName())){
			mmap.put("title",dispositionAttachment.getAttachmentName());
		}
		mmap.put("type",type);
		mmap.put("clueId",clue.getClueId());
		mmap.put("dispositionAttachment",dispositionAttachment);
		mmap.put("content",content);
		mmap.put("clueClassify",clueClassify);
		mmap.put("handleUrl", handleUrl);
		nodeFlag="40";
		ruTaskService.compleTask("true",content,proposerName,taskId,content,pid,approver,mmap);
		//表单修改
		ruTaskService.dispositionSubmit(count,nodeFlag,clueId,isEdit,dispositionAttachment,templateUrl);
		return success("保存成功");
	}
	/**
	 * 立案调查决定书审核
	 *
	 * @author zhoushiji
	 * @date 2019-1-26
	 */
	@PostMapping("/splajdCompleTask")
	@ResponseBody
	public AjaxResult splajdCompleTask(int  count,String isok,String resultType,String formType, String content, String proposerName, String clueId, String taskId, String opinion, String nodeFlag, String pid, String approver, String approverName, String clueClassify,DispositionAttachment dispositionAttachment) {
		try {

		    if("true".equals(isok)){
                String truehandleUrl="";
                String falsehandleUrl=prefix+"/xglajdform";
                String trueRemarks="同意-立案调查决定书";
                String falseRemarks="不同意-立案调查决定书";
                String paramNodeFlag="41";
                Map<String, Object> paramsMap=new HashMap<String, Object>();
                paramsMap.put("resultType",resultType);
                sameAduditing( count,isok,resultType, formType, content, proposerName, clueId, taskId, opinion, pid, approver, clueClassify, dispositionAttachment, truehandleUrl, falsehandleUrl, trueRemarks, falseRemarks, paramNodeFlag,paramsMap);
                DispositionHandle dispositionHandle = new DispositionHandle();
                dispositionHandle.setParentId(clueId);
                dispositionHandle.setSuggestion(opinion);
                dispositionHandle.setDeptName("最后处置结果");
                dispositionHandle.setNodeId(taskId);
                dispositionHandle.setDeptId(StringUtils.getUUID());
                dispositionHandle.setNodeType(resultType);
                DispositionHandle findway=new DispositionHandle();
                findway.setClueId(clueId);
                findway.setWay("归类");
                if(dispositionHandleService.selectDeptList(findway).size()>0){
                    DispositionHandle parentHandle=dispositionHandleService.selectDeptById(clueId);
                    dispositionHandle.setCount(parentHandle.getCount());
                }else{
                    dispositionHandle.setCount(1);
                }
                dispositionHandle.setWay("归类");
                dispositionHandle.setClueId(clueId);
                dispositionHandle.setCreateBy(ShiroUtils.getUser().getUserName());
                dispositionHandle.setCreateTimes(DateUtils.getDate());
                if(!resultType.equals("6") && !resultType.equals("7")){
                    Clue clue = clueService.selectClueById(clueId);
                    if(resultType.equals("11")){
                        dispositionHandle.setSuggestion("留置措施登记");
                        clue.setClueCzjd("41");
                        clue.setProcessStatus("0");
                        dispositionHandle.setNodeFlag("0");
                    }else if(resultType.equals("12")){
                        dispositionHandle.setSuggestion("向被调查人宣布");
                        clue.setClueCzjd("41");
                        clue.setProcessStatus("0");
                        dispositionHandle.setNodeFlag("0");
                    }
                    dispositionHandleService.insertDispositionHandle(dispositionHandle);
                    clueService.updateClue(clue);
                }
                return success();
            }else {
                String truehandleUrl="";
                String falsehandleUrl=prefix+"/xglajdform";
                String trueRemarks="同意-立案调查决定书";
                String falseRemarks="不同意-立案调查决定书";
                String paramNodeFlag="41";
                Map<String, Object> paramsMap=new HashMap<String, Object>();
                paramsMap.put("resultType",resultType);
                sameAduditing( count,isok,resultType, formType, content, proposerName, clueId, taskId, opinion, pid, approver, clueClassify, dispositionAttachment, truehandleUrl, falsehandleUrl, trueRemarks, falseRemarks, paramNodeFlag,paramsMap);
                return success();
            }

		} catch (Exception e) {
			return error();
		}
	}






	private void sameAduditing(int  count,String isok,String cbResult,String formType, String content, String proposerName, String clueId,
							   String taskId, String opinion, String pid, String approver, String clueClassify,
							   DispositionAttachment dispositionAttachment, String truehandleUrl,
							   String falsehandleUrl, String trueRemarks, String falseRemarks, String paramNodeFlag) {
		String nodeFlag;
		dispositionAttachment.setUpdateBy(ShiroUtils.getUser().getUserName());
		dispositionAttachment.setUpdateTime(new Date());
		dispositionAttachmentService.updateDispositionAttachment(dispositionAttachment);
		Map<String, Object> mmap = new HashMap<String, Object>();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		if (isok.equals("true")) {
			mmap.put("handleUrl", truehandleUrl);
			mmap.put("cbResult", cbResult);
		} else {
			paramsMap.put("formType", formType);
			paramsMap.put("attchmentId", dispositionAttachment.getAttachmentId());
			paramsMap.put("formContent", dispositionAttachment.getFormContent());
			paramsMap.put("count", count);
			mmap.put("handleUrl", falsehandleUrl);
		}
		mmap.put("paramsMap", paramsMap);
		Map<String, Object> map = ruTaskService.compleTask(isok, content, proposerName, taskId, opinion, pid, approver, mmap);
		Task task = (Task) map.get("task");
		Clue clue = clueService.selectClueById(clueId);
		nodeFlag = paramNodeFlag;
		ruTaskService.checkProblem( count,isok, opinion, clueClassify, clue, nodeFlag, "审核", trueRemarks, falseRemarks);
	}


	private void sameAduditing(int  count,String isok,String resultType, String formType, String content, String proposerName, String clueId, String taskId, String opinion, String pid, String approver, String clueClassify, DispositionAttachment dispositionAttachment, String truehandleUrl, String falsehandleUrl, String trueRemarks, String falseRemarks, String paramNodeFlag,Map<String, Object> paramsMap) {
		String nodeFlag;
		dispositionAttachment.setUpdateBy(ShiroUtils.getUser().getUserName());
		dispositionAttachment.setUpdateTime(new Date());
		dispositionAttachmentService.updateDispositionAttachment(dispositionAttachment);
		Map<String, Object> mmap = new HashMap<String, Object>();
		if (isok.equals("true")) {
			mmap.put("handleUrl", truehandleUrl);
			mmap.put("resultType",resultType);
		} else {
			paramsMap.put("formType", formType);
			paramsMap.put("attchmentId", dispositionAttachment.getAttachmentId());
			paramsMap.put("formContent", dispositionAttachment.getFormContent());
			paramsMap.put("count", count);
			mmap.put("handleUrl", falsehandleUrl);
		}
		mmap.put("paramsMap", paramsMap);
		Map<String, Object> map = ruTaskService.compleTask(isok, content, proposerName, taskId, opinion, pid, approver, mmap);
		Task task = (Task) map.get("task");
		Clue clue = clueService.selectClueById(clueId);
		nodeFlag = paramNodeFlag;
		ruTaskService.checkProblem( count,isok, opinion, clueClassify, clue, nodeFlag, "审核", trueRemarks, falseRemarks);
	}



	private void saveDispositionHandle(String DeptName, String NodeFlag, String way, String Result, String clueId, String content,
									   String clueClassify, Clue clue,  String status,String createBy,String createTime) {
		DispositionHandle parentHandle=dispositionHandleService.selectDeptById(clueId);
		DispositionHandle dispositionHandle = new DispositionHandle();
		dispositionHandle.setParentId(clue.getClueId());
		dispositionHandle.setSuggestion(content);
		dispositionHandle.setDeptName(DeptName);
		dispositionHandle.setNodeType(clueClassify);
		dispositionHandle.setNodeFlag(NodeFlag);
		dispositionHandle.setNodeId(NodeFlag);
		dispositionHandle.setDeptId(StringUtils.getUUID());
		dispositionHandle.setClueId(clue.getClueId());
		dispositionHandle.setCreateBy(createBy);
		dispositionHandle.setWay(way);
		dispositionHandle.setCount(parentHandle.getCount());
		dispositionHandle.setResult(Result);
		dispositionHandle.setStatus(status);
		dispositionHandle.setCreateTimes(createTime);
		dispositionHandleService.insertDispositionHandle(dispositionHandle);
	}

}
