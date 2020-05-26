package com.datcent.project.module.court.controller;

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
import com.datcent.project.module.area.service.IAreaService;
import com.datcent.project.module.court.domain.Court;
import com.datcent.project.module.court.service.ICourtService;
import com.datcent.project.system.user.domain.User;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;
import com.sun.javafx.collections.MappingChange.Map;

/**
 * 法院  信息操作处理
 * 
 * @author datcent
 * @date 2018-11-06
 */
@Controller
@RequestMapping("/module/court")
public class CourtController extends BaseController
{
    private String prefix = "module/court";
	
	@Autowired
	private ICourtService courtService;
	
	@Autowired
	private IAreaService iAreaService;
	
	@RequiresPermissions("module:court:view")
	@GetMapping()
	public String court()
	{
	    return prefix + "/court";
	}
	
	/**
	 * 查询法院 列表
	 */
	@RequiresPermissions("module:court:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Court court)
	{
		startPage();
        List<Court> list = courtService.selectCourtList(court);
		return getDataTable(list);
	}
	
	/**
	 * 新增法院 
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
		mmap.put("province", iAreaService.selectProvinceByPid(100000000000L));
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存法院 
	 */
	@RequiresPermissions("module:court:add")
	@Log(title = "法院 ", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Court court)
	{		
		return toAjax(courtService.insertCourt(court));
	}

	/**
	 * 修改法院 
	 */
	@GetMapping("/edit/{courtId}")
	public String edit(@PathVariable("courtId") Integer courtId, ModelMap mmap)
	{
		Court court = courtService.selectCourtById(courtId);
		mmap.put("court", court);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存法院 
	 */
	@RequiresPermissions("module:court:edit")
	@Log(title = "法院 ", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Court court)
	{		
		return toAjax(courtService.updateCourt(court));
	}
	
	/**
	 * 删除法院 
	 */
	@RequiresPermissions("module:court:remove")
	@Log(title = "法院 ", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(courtService.deleteCourtByIds(ids));
	}
	
	/**
     * 校验用户名
     */
    @PostMapping("/checkCourtNameUnique")
    @ResponseBody
    public String checkCourtNameUnique(Court court)
    {
        return courtService.checkCourtNameUnique(court.getCourtName());
    }
	
}
