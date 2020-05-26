package com.datcent.project.module.datashareMaster.controller;

import java.util.List;

import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.dept.service.IDeptService;
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
import com.datcent.project.module.datashareMaster.domain.DatashareMaster;
import com.datcent.project.module.datashareMaster.service.IDatashareMasterService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 各法院数据权限 信息操作处理
 * 
 * @author datcent
 * @date 2019-05-27
 */
@Controller
@RequestMapping("/module/datashareMaster")
public class DatashareMasterController extends BaseController
{
    private String prefix = "module/datashareMaster";
	
	@Autowired
	private IDatashareMasterService datashareMasterService;

	@Autowired
	private IDeptService deptService;
	
	@RequiresPermissions("module:datashareMaster:view")
	@GetMapping()
	public String datashareMaster(ModelMap mmap)
	{
		Dept dept = new Dept();
		dept.setDeptType("0");
		List<Dept> thirdList = deptService.selectDeptList(dept);
		mmap.put("deptList", thirdList);
	    return prefix + "/datashareMaster";
	}
	
	/**
	 * 查询各法院数据权限列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(DatashareMaster datashareMaster)
	{
		startPage();
        List<DatashareMaster> list = datashareMasterService.selectDatashareMasterList(datashareMaster);
		return getDataTable(list);
	}
	
	/**
	 * 新增各法院数据权限
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
		Dept dept = new Dept();
		dept.setDeptType("0");
		List<Dept> thirdList = deptService.selectDeptList(dept);
		mmap.put("deptList", thirdList);
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存各法院数据权限
	 */
	@Log(title = "各法院数据权限", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(DatashareMaster datashareMaster)
	{		
		return toAjax(datashareMasterService.insertDatashareMaster(datashareMaster));
	}

	/**
	 * 修改各法院数据权限
	 */
	@GetMapping("/edit/{datashareId}")
	public String edit(@PathVariable("datashareId") Integer datashareId, ModelMap mmap)
	{
		DatashareMaster datashareMaster = datashareMasterService.selectDatashareMasterById(datashareId);
		Dept dept = new Dept();
		dept.setDeptType("0");
		List<Dept> thirdList = deptService.selectDeptList(dept);
		mmap.put("deptList", thirdList);
		mmap.put("datashareMaster", datashareMaster);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存各法院数据权限
	 */
	@Log(title = "各法院数据权限", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(DatashareMaster datashareMaster)
	{		
		return toAjax(datashareMasterService.updateDatashareMaster(datashareMaster));
	}
	
	/**
	 * 删除各法院数据权限
	 */
	@Log(title = "各法院数据权限", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(datashareMasterService.deleteDatashareMasterByIds(ids));
	}
	
}
