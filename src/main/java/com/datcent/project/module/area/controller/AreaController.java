package com.datcent.project.module.area.controller;

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
import com.datcent.project.module.area.domain.Area;
import com.datcent.project.module.area.service.IAreaService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 省市区数据 信息操作处理
 * 
 * @author datcent
 * @date 2018-11-06
 */
@Controller
@RequestMapping("/module/area")
public class AreaController extends BaseController
{
    private String prefix = "module/area";
	
	@Autowired
	private IAreaService areaService;
	
	@RequiresPermissions("module:area:view")
	@GetMapping()
	public String area()
	{
	    return prefix + "/area";
	}
	
	/**
	 * 查询省市区数据列表
	 */
	@RequiresPermissions("module:area:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Area area)
	{
		startPage();
        List<Area> list = areaService.selectAreaList(area);
		return getDataTable(list);
	}
	
	/**
	 * 根据pid分类查询省市区数据列表
	 */
	@PostMapping("/getAreaByPid")
	@ResponseBody
	public List<Area> getAreaByPid(long pid)
	{
        List<Area> list = areaService.selectProvinceByPid(pid);
		return list;
	}
	
	/**
	 * 新增省市区数据
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存省市区数据
	 */
	@RequiresPermissions("module:area:add")
	@Log(title = "省市区数据", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Area area)
	{		
		return toAjax(areaService.insertArea(area));
	}

	/**
	 * 修改省市区数据
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		Area area = areaService.selectAreaById(id);
		mmap.put("area", area);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存省市区数据
	 */
	@RequiresPermissions("module:area:edit")
	@Log(title = "省市区数据", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Area area)
	{		
		return toAjax(areaService.updateArea(area));
	}
	
	/**
	 * 删除省市区数据
	 */
	@RequiresPermissions("module:area:remove")
	@Log(title = "省市区数据", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(areaService.deleteAreaByIds(ids));
	}
	
}
