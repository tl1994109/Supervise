package com.datcent.project.module.usertestinfo.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Model;
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
import com.datcent.project.module.usertestinfo.domain.Usertestinfo;
import com.datcent.project.module.usertestinfo.service.IUsertestinfoService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 测试 信息操作处理
 * 
 * @author datcent
 * @date 2018-11-26
 */
@Controller
@RequestMapping("/module/usertestinfo")
public class UsertestinfoController extends BaseController
{
    private String prefix = "module/usertestinfo";
	
	@Autowired
	private IUsertestinfoService usertestinfoService;

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
	
	@RequiresPermissions("module:usertestinfo:view")
	@GetMapping()
	public String usertestinfo ()throws Exception
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
		repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
		return "redirect:/modeler.html?modelId=" + id;
	}
	
	/**
	 * 查询测试列表
	 */
	@RequiresPermissions("module:usertestinfo:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Usertestinfo usertestinfo)
	{
		startPage();
        List<Usertestinfo> list = usertestinfoService.selectUsertestinfoList(usertestinfo);
		return getDataTable(list);
	}
	
	/**
	 * 新增测试
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存测试
	 */
	@RequiresPermissions("module:usertestinfo:add")
	@Log(title = "测试", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Usertestinfo usertestinfo)
	{		
		return toAjax(usertestinfoService.insertUsertestinfo(usertestinfo));
	}

	/**
	 * 修改测试
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		Usertestinfo usertestinfo = usertestinfoService.selectUsertestinfoById(id);
		mmap.put("usertestinfo", usertestinfo);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存测试
	 */
	@RequiresPermissions("module:usertestinfo:edit")
	@Log(title = "测试", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Usertestinfo usertestinfo)
	{		
		return toAjax(usertestinfoService.updateUsertestinfo(usertestinfo));
	}
	
	/**
	 * 删除测试
	 */
	@RequiresPermissions("module:usertestinfo:remove")
	@Log(title = "测试", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(usertestinfoService.deleteUsertestinfoByIds(ids));
	}
	
}
