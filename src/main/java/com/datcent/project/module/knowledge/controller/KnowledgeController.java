package com.datcent.project.module.knowledge.controller;

import java.util.Date;
import java.util.List;

import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.project.module.knowledgeClick.domain.KnowledgeClick;
import com.datcent.project.module.knowledgeClick.service.IKnowledgeClickService;
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
import com.datcent.project.module.knowledge.domain.Knowledge;
import com.datcent.project.module.knowledge.service.IKnowledgeService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 知识管理 信息操作处理
 * 
 * @author datcent
 * @date 2019-02-13
 */
@Controller
@RequestMapping("/module/knowledge")
public class KnowledgeController extends BaseController
{
    private String prefix = "module/knowledge";
	
	@Autowired
	private IKnowledgeService knowledgeService;

	@Autowired
	private IKnowledgeClickService knowledgeClickService;

	@RequiresPermissions("module:knowledge:view")
	@GetMapping("/knowledge")
	public String knowledge()
	{
		return prefix + "/knowledge";
	}

	@RequiresPermissions("module:knowledge:expshareview")
	@GetMapping("/expshareview")
	public String expshareview()
	{
		return prefix + "/expshare";
	}
	
	/**
	 * 查询知识管理列表
	 */
	@RequiresPermissions("module:knowledge:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Knowledge knowledge)
	{
		startPage();
        List<Knowledge> list = knowledgeService.selectKnowledgeList(knowledge);
		return getDataTable(list);
	}



	@PostMapping("/main")
	@RequiresPermissions("module:knowledge:main")
	@ResponseBody
	public TableDataInfo main(Knowledge knowledge)
	{
		startPage();
		List<Knowledge> list = knowledgeService.selectKnowledgeList(knowledge);
		return getDataTable(list);
	}
	
	/**
	 * 新增知识管理
	 */
	@GetMapping("/add")
	public String add(String type,ModelMap modelMap)
	{
		modelMap.put("type",type);
		return prefix + "/add";
	}
	
	/**
	 * 新增保存知识管理
	 */
	@RequiresPermissions("module:knowledge:add")
	@Log(title = "知识管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Knowledge knowledge, MultipartFile file_pdf)
	{		
		return toAjax(knowledgeService.insertKnowledge(knowledge,file_pdf));
	}

	@GetMapping("/detail/{id}")
	@RequiresPermissions("module:knowledge:detail")
	public String detail(@PathVariable("id")String id,ModelMap modelMap){
		Knowledge knowledge = knowledgeService.selectKnowledgeById(id);
		KnowledgeClick knowledgeClick = new KnowledgeClick();
		knowledgeClick.setClickDate(new Date());
		knowledgeClick.setClickUser(ShiroUtils.getUserId().toString());
		knowledgeClick.setId(StringUtils.getUUID());
		knowledgeClick.setKnowledgeId(id);
		knowledgeClickService.insertKnowledgeClick(knowledgeClick);
		modelMap.put("knowledge",knowledge);
		modelMap.put("count",knowledgeClickService.selectKnowledgeClickByKnowledgeId(id));
		return prefix+"/check";
	}


	/**
	 * 修改知识管理
	 */
	@GetMapping("/edit/{knowledgeId}")
	public String edit(@PathVariable("knowledgeId") String knowledgeId, ModelMap mmap)
	{
		Knowledge knowledge = knowledgeService.selectKnowledgeById(knowledgeId);
		mmap.put("knowledge", knowledge);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存知识管理
	 */
	@RequiresPermissions("module:knowledge:edit")
	@Log(title = "知识管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Knowledge knowledge,MultipartFile file_pdf)
	{		
		return toAjax(knowledgeService.updateKnowledge(knowledge,file_pdf));
	}
	
	/**
	 * 删除知识管理
	 */
	@RequiresPermissions("module:knowledge:remove")
	@Log(title = "知识管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(knowledgeService.deleteKnowledgeByIds(ids));
	}

	@GetMapping("/check/{id}")
	public String check(@PathVariable("id")String id,ModelMap map){
		Knowledge knowledge = knowledgeService.selectKnowledgeById(id);
		KnowledgeClick knowledgeClick = new KnowledgeClick();
		knowledgeClick.setClickDate(new Date());
		knowledgeClick.setClickUser(ShiroUtils.getUserId().toString());
		knowledgeClick.setId(StringUtils.getUUID());
		knowledgeClick.setKnowledgeId(id);
		knowledgeClickService.insertKnowledgeClick(knowledgeClick);
		map.put("knowledge",knowledge);
		map.put("count",knowledgeClickService.selectKnowledgeClickByKnowledgeId(id));
		return prefix+"/check";
	}
}
