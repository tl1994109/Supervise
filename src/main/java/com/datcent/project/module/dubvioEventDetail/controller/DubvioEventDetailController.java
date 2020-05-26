package com.datcent.project.module.dubvioEventDetail.controller;

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
import com.datcent.project.module.dubvioEventDetail.domain.DubvioEventDetail;
import com.datcent.project.module.dubvioEventDetail.service.IDubvioEventDetailService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 案件详细 信息操作处理
 * 
 * @author datcent
 * @date 2019-01-16
 */
@Controller
@RequestMapping("/module/dubvioEventDetail")
public class DubvioEventDetailController extends BaseController
{
    private String prefix = "module/dubvioEventDetail";
	
	@Autowired
	private IDubvioEventDetailService dubvioEventDetailService;
	
	@RequiresPermissions("module:dubvioEventDetail:view")
	@GetMapping()
	public String dubvioEventDetail()
	{
	    return prefix + "/dubvioEventDetail";
	}
	
	/**
	 * 查询案件详细列表
	 */
	@RequiresPermissions("module:dubvioEventDetail:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(DubvioEventDetail dubvioEventDetail)
	{
		startPage();
        List<DubvioEventDetail> list = dubvioEventDetailService.selectDubvioEventDetailList(dubvioEventDetail);
		return getDataTable(list);
	}
	
	/**
	 * 新增案件详细
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存案件详细
	 */
	@RequiresPermissions("module:dubvioEventDetail:add")
	@Log(title = "案件详细", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(DubvioEventDetail dubvioEventDetail)
	{		
		return toAjax(dubvioEventDetailService.insertDubvioEventDetail(dubvioEventDetail));
	}

	/**
	 * 修改案件详细
	 */
	@GetMapping("/edit/{jbxxAjbh}")
	public String edit(@PathVariable("jbxxAjbh") String jbxxAjbh, ModelMap mmap)
	{
		DubvioEventDetail dubvioEventDetail = dubvioEventDetailService.selectDubvioEventDetailById(jbxxAjbh);
		mmap.put("dubvioEventDetail", dubvioEventDetail);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存案件详细
	 */
	@RequiresPermissions("module:dubvioEventDetail:edit")
	@Log(title = "案件详细", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(DubvioEventDetail dubvioEventDetail)
	{		
		return toAjax(dubvioEventDetailService.updateDubvioEventDetail(dubvioEventDetail));
	}
	
	/**
	 * 删除案件详细
	 */
	@RequiresPermissions("module:dubvioEventDetail:remove")
	@Log(title = "案件详细", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(dubvioEventDetailService.deleteDubvioEventDetailByIds(ids));
	}
	
}
