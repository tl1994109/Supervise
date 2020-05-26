package com.datcent.project.system.reModel.controller;

import com.datcent.common.constant.Constants;
import com.datcent.common.utils.ServletUtils;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.domain.AjaxResult;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.project.system.reModel.domain.ReModel;
import com.datcent.project.system.reModel.service.IReModelService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageInfo;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 工作流模型 信息操作处理
 * 
 * @author datcent
 * @date 2019-01-08
 */
@Controller
@RequestMapping("/system/reModel")
public class ReModelController extends BaseController
{
    private String prefix = "system/reModel";
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
	
	@RequiresPermissions("module:reModel:view")
	@GetMapping()
	public String reModel()
	{
	    return prefix + "/reModel";
	}
	
	/**
	 * 查询工作流模型列表
	 */
	@RequiresPermissions("module:reModel:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ReModel reModel)
	{
		//PAGE_SIZE
		Integer pageNum = ServletUtils.getParameterToInt(Constants.PAGE_NUM);
		Integer pageSize = ServletUtils.getParameterToInt(Constants.PAGE_SIZE);
		ModelQuery modelQuery = repositoryService.createModelQuery();
		if (reModel != null) {
			if (!StringUtils.isEmpty(reModel.getName())) {
				modelQuery.modelNameLike("%" + reModel.getName() + "%");
			}
		}
		List<Model> models = new ArrayList<Model>();
		TableDataInfo rspData = new TableDataInfo();
		rspData.setCode(0);
		if(pageNum!=null&&pageSize!=null){
			rspData.setTotal(modelQuery.count());
			models = modelQuery.listPage(pageSize * (pageNum - 1), pageSize);
		}else{
			models=modelQuery.list();
			rspData.setTotal(models.size());
		}
		List<ReModel> list = new ArrayList<>();
		models.forEach(mo -> list.add(new ReModel(mo)));
		rspData.setRows(list);
		return rspData;
	}
	
	/**
	 * 新增工作流模型
	 */
	@GetMapping("/add")
	@Log(title = "工作流模型", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	public String add()
	{
		Model model = repositoryService.newModel();
		String name = "新建流程";
		String description = "";
		int revision = 1;
		String key = "processKey";
		ObjectNode modelNode = objectMapper.createObjectNode();
		modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
		modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
		model.setName(name);
		model.setKey(key);
		model.setMetaInfo(modelNode.toString());
		repositoryService.saveModel(model);
		String id = model.getId();
		//完善ModelEditorSource
		ObjectNode editorNode = objectMapper.createObjectNode();
		editorNode.put("id", "canvas");
		editorNode.put("resourceId", "canvas");
		ObjectNode stencilSetNode = objectMapper.createObjectNode();
		stencilSetNode.put("namespace",
				"http://b3mn.org/stencilset/bpmn2.0#");
		editorNode.put("stencilset", stencilSetNode);
		try {
			repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:/modeler.html?modelId=" + id;
	}
	
	/**
	 * 新增保存工作流模型
	 */
	@RequiresPermissions("module:reModel:add")
	@Log(title = "工作流模型", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public String addSave(ReModel reModel)
	{
		return  "";
	}

	/**
	 * 修改工作流模型
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		return "redirect:/modeler.html?modelId=" + id;
	}

	@RequiresPermissions("module:reModel:fb")
	@Log(title = "工作流模型", businessType = BusinessType.OTHER)
	@PostMapping("/releaseProcess")
	@ResponseBody
	public AjaxResult releaseProcess(String modelId){
		try {
			if(modelId.indexOf(",")>-1){
				String[] modelId_arr = modelId.split(",");
				for (String i:modelId_arr) {
					Model modelData = repositoryService.getModel(i);
					byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

					if (bytes == null) {
						return error("模型为空");
					}
					JsonNode modelNode = null;
					modelNode = new ObjectMapper().readTree(bytes);
					BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
					if (model.getProcesses().size() == 0) {
						return error("数据不符合要求");
					}
					byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
					//发布流程
					String processName = modelData.getName() + ".bpmn20.xml";
					String convertToXML = new String(bpmnBytes);

					System.out.println(convertToXML);
					Deployment deployment = repositoryService.createDeployment()
							.name(modelData.getName())
							.addString(processName, new String(bpmnBytes, "UTF-8"))
							.deploy();
					modelData.setDeploymentId(deployment.getId());
					repositoryService.saveModel(modelData);
				}
			}else{
				Model modelData = repositoryService.getModel(modelId);
				byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

				if (bytes == null) {
					return error("模型为空");
				}
				JsonNode modelNode = null;
				modelNode = new ObjectMapper().readTree(bytes);
				BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
				if (model.getProcesses().size() == 0) {
					return error("数据不符合要求");
				}
				byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
				//发布流程
				String processName = modelData.getName() + ".bpmn20.xml";
				String convertToXML = new String(bpmnBytes);

				System.out.println(convertToXML);
				Deployment deployment = repositoryService.createDeployment()
						.name(modelData.getName())
						.addString(processName, new String(bpmnBytes, "UTF-8"))
						.deploy();
				modelData.setDeploymentId(deployment.getId());
				repositoryService.saveModel(modelData);
			}
		} catch (Exception e) {
			return error("发布失败");
		}
		return toAjax(1);
	}
	/**
	 * 修改保存工作流模型
	 */
	@RequiresPermissions("module:reModel:edit")
	@Log(title = "工作流模型", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ReModel reModel)
	{		
		return toAjax(reModelService.updateReModel(reModel));
	}
	
	/**
	 * 删除工作流模型
	 */
	@RequiresPermissions("module:reModel:remove")
	@Log(title = "工作流模型", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		int rows = 0;
		try {
			if(ids.indexOf(",")>=-1){
				String[] strarray = ids.split(",");
				for (String str: strarray) {
					repositoryService.deleteModel(str);
				}
			}else {
				repositoryService.deleteModel(ids);
			}
			rows = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toAjax(rows);
	}
	
}
