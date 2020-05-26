package com.datcent.project.module.noticeClick.controller;

import java.util.List;
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
import com.datcent.project.module.noticeClick.domain.NoticeClick;
import com.datcent.project.module.noticeClick.service.INoticeClickService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 公告点击记录 信息操作处理
 * 
 * @author datcent
 * @date 2018-11-05
 */
@Controller
@RequestMapping("/module/noticeClick")
public class NoticeClickController extends BaseController
{
    private String prefix = "module/noticeClick";
	
	@Autowired
	private INoticeClickService noticeClickService;
	
	@RequiresPermissions("module:noticeClick:view")
	@GetMapping()
	public String noticeClick()
	{
	    return prefix + "/noticeClick";
	}
	
	/**
	 * 查询公告点击记录列表
	 */
	@RequiresPermissions("module:noticeClick:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(NoticeClick noticeClick)
	{
		startPage();
        List<NoticeClick> list = noticeClickService.selectNoticeClickList(noticeClick);
		return getDataTable(list);
	}
	
	/**
	 * 新增公告点击记录
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存公告点击记录
	 */
	//@RequiresPermissions("module:noticeClick:add")
	@Log(title = "公告点击记录", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(NoticeClick noticeClick)
	{
		return toAjax(noticeClickService.insertNoticeClick(noticeClick));
	}


/*	*//**
	 * 修改公告点击记录
	 *//*
	@GetMapping("/edit/{noticeClickrateId}")
	public String edit(@PathVariable("noticeClickrateId") Integer noticeClickrateId, ModelMap mmap)
	{
		NoticeClick noticeClick = noticeClickService.selectNoticeClickById(noticeClickrateId);
		mmap.put("noticeClick", noticeClick);
	    return prefix + "/edit";
	}
	
	*//**
	 * 修改保存公告点击记录
	 *//*
	@RequiresPermissions("module:noticeClick:edit")
	@Log(title = "公告点击记录", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(NoticeClick noticeClick)
	{		
		return toAjax(noticeClickService.updateNoticeClick(noticeClick));
	}
	
	*//**
	 * 删除公告点击记录
	 *//*
	@RequiresPermissions("module:noticeClick:remove")
	@Log(title = "公告点击记录", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(noticeClickService.deleteNoticeClickByIds(ids));
	}*/
	
}
