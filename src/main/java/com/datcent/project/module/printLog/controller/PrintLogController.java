package com.datcent.project.module.printLog.controller;

import java.util.Date;
import java.util.List;

import com.datcent.common.utils.security.ShiroUtils;
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
import com.datcent.project.module.printLog.domain.PrintLog;
import com.datcent.project.module.printLog.service.IPrintLogService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 单打印日志 信息操作处理
 * 
 * @author datcent
 * @date 2019-02-15
 */
@Controller
@RequestMapping("/module/printLog")
public class PrintLogController extends BaseController
{
    private String prefix = "module/printLog";
	
	@Autowired
	private IPrintLogService printLogService;
	
	@RequiresPermissions("module:printLog:view")
	@GetMapping()
	public String printLog()
	{
	    return prefix + "/printLog";
	}
	
	/**
	 * 查询单打印日志列表
	 */
	@RequiresPermissions("module:printLog:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(PrintLog printLog)
	{
		startPage();
        List<PrintLog> list = printLogService.selectPrintLogList(printLog);
		return getDataTable(list);
	}
	
	/**
	 * 新增单打印日志
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存单打印日志
	 */
	//@RequiresPermissions("module:printLog:add")
	@Log(title = "单打印日志", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(PrintLog printLog)
	{
		printLog.setClickTime(new Date());
		printLog.setUserId(Integer.parseInt(ShiroUtils.getUserId().toString()));
		return toAjax(printLogService.insertPrintLog(printLog));
	}

	/**
	 * 修改单打印日志
	 */
	@GetMapping("/edit/{printLogId}")
	public String edit(@PathVariable("printLogId") Integer printLogId, ModelMap mmap)
	{
		PrintLog printLog = printLogService.selectPrintLogById(printLogId);
		mmap.put("printLog", printLog);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存单打印日志
	 */
	//@RequiresPermissions("module:printLog:edit")
	@Log(title = "单打印日志", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(PrintLog printLog)
	{		
		return toAjax(printLogService.updatePrintLog(printLog));
	}
	
	/**
	 * 删除单打印日志
	 */
	//@RequiresPermissions("module:printLog:remove")
	@Log(title = "单打印日志", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(printLogService.deletePrintLogByIds(ids));
	}
	
}
