package com.datcent.project.module.journal.controller;

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
import com.datcent.project.module.journal.domain.Journal;
import com.datcent.project.module.journal.service.IJournalService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 任务日志 信息操作处理
 * 
 * @author datcent
 * @date 2019-01-22
 */
@Controller
@RequestMapping("/module/journal")
public class JournalController extends BaseController
{
    private String prefix = "module/journal";
	
	@Autowired
	private IJournalService journalService;
	
	@RequiresPermissions("module:journal:view")
	@GetMapping()
	public String journal()
	{
	    return prefix + "/journal";
	}
	
	/**
	 * 查询任务日志列表
	 */
	@RequiresPermissions("module:journal:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Journal journal)
	{
		startPage();
        List<Journal> list = journalService.selectJournalList(journal);
		return getDataTable(list);
	}
	
	/**
	 * 新增任务日志
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存任务日志
	 */
	@RequiresPermissions("module:journal:add")
	@Log(title = "任务日志", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Journal journal)
	{		
		return toAjax(journalService.insertJournal(journal));
	}

	/**
	 * 修改任务日志
	 */
	@GetMapping("/edit/{journalId}")
	public String edit(@PathVariable("journalId") String journalId, ModelMap mmap)
	{
		Journal journal = journalService.selectJournalById(journalId);
		mmap.put("journal", journal);
	    return "module/information/edit_log";
	}
	
	/**
	 * 修改保存任务日志
	 */
	@RequiresPermissions("module:journal:edit")
	@Log(title = "任务日志", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Journal journal)
	{		
		return toAjax(journalService.updateJournal(journal));
	}
	
	/**
	 * 删除任务日志
	 */
	@RequiresPermissions("module:journal:remove")
	@Log(title = "任务日志", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(journalService.deleteJournalByIds(ids));
	}
	
}
