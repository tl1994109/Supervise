package com.datcent.project.module.knowledgeClick.controller;

import java.util.List;

import com.datcent.project.module.knowledge.domain.Knowledge;
import com.datcent.project.module.knowledge.service.IKnowledgeService;
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
import com.datcent.project.module.knowledgeClick.domain.KnowledgeClick;
import com.datcent.project.module.knowledgeClick.service.IKnowledgeClickService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 知识点击纪录 信息操作处理
 * 
 * @author datcent
 * @date 2019-02-20
 */
@Controller
@RequestMapping("/module/knowledgeClick")
public class KnowledgeClickController extends BaseController
{
    private String prefix = "module/knowledgeClick";
	
	@Autowired
	private IKnowledgeClickService knowledgeClickService;

	@Autowired
	private IKnowledgeService knowledgeService;
	
	@RequiresPermissions("module:knowledgeClick:view")
	@GetMapping()
	public String knowledgeClick()
	{
	    return prefix + "/knowledgeClick";
	}
	
	/**
	 * 查询知识点击纪录列表
	 */
	@RequiresPermissions("module:knowledgeClick:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(KnowledgeClick knowledgeClick)
	{
		startPage();
        List<KnowledgeClick> list = knowledgeClickService.selectKnowledgeClickList(knowledgeClick);
		return getDataTable(list);
	}
}
