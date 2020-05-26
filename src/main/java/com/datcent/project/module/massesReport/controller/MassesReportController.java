package com.datcent.project.module.massesReport.controller;

import java.util.List;

import com.datcent.common.utils.StringUtils;
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
import com.datcent.project.module.massesReport.domain.MassesReport;
import com.datcent.project.module.massesReport.service.IMassesReportService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 群众举报情况 信息操作处理
 * 
 * @author datcent
 * @date 2019-04-01
 */
@Controller
@RequestMapping("/module/massesReport")
public class MassesReportController extends BaseController
{
    private String prefix = "module/massesReport";
	
	@Autowired
	private IMassesReportService massesReportService;
	
	//@RequiresPermissions("module:massesReport:view")
	@GetMapping()
	public String massesReport()
	{
	    return prefix + "/massesReport";
	}
	
	/**
	 * 查询群众举报情况列表
	 */
	//@RequiresPermissions("module:massesReport:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(MassesReport massesReport)
	{
		startPage();
        List<MassesReport> list = massesReportService.selectMassesReportList(massesReport);
		return getDataTable(list);
	}
	
	/**
	 * 新增群众举报情况
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存群众举报情况
	 */
	//@RequiresPermissions("module:massesReport:add")
	@Log(title = "群众举报情况", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(MassesReport massesReport)
	{

		if(StringUtils.isEmpty(massesReport.getXfjbqkId())){
			massesReport.setXfjbqkId(StringUtils.getUUID());
			massesReportService.insertMassesReport(massesReport);
		}
		int i = massesReportService.updateMassesReport(massesReport);
		if(i>0){
			return toAjax(i);
		}else{
			return error("信息已录入，请勿重复提交");
		}
	}

	/**
	 * 修改群众举报情况
	 */
	@GetMapping("/edit/{xfjbqkId}")
	public String edit(@PathVariable("xfjbqkId") String xfjbqkId, ModelMap mmap)
	{
		MassesReport massesReport = massesReportService.selectMassesReportById(xfjbqkId);
		mmap.put("massesReport", massesReport);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存群众举报情况
	 */
	//@RequiresPermissions("module:massesReport:edit")
	@Log(title = "群众举报情况", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(MassesReport massesReport)
	{		
		return toAjax(massesReportService.updateMassesReport(massesReport));
	}
	
	/**
	 * 删除群众举报情况
	 */
	//@RequiresPermissions("module:massesReport:remove")
	@Log(title = "群众举报情况", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(massesReportService.deleteMassesReportByIds(ids));
	}
	
}
