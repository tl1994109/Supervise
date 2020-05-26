package com.datcent.project.module.datashareFollow.controller;

import java.util.List;

import com.datcent.project.system.dept.domain.Dept;
import com.datcent.project.system.dept.service.IDeptService;
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
import com.datcent.project.module.datashareFollow.domain.DatashareFollow;
import com.datcent.project.module.datashareFollow.service.IDatashareFollowService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 各法院查看权 信息操作处理
 *
 * @author datcent
 * @date 2019-05-27
 */
@Controller
@RequestMapping("/module/datashareFollow")
public class DatashareFollowController extends BaseController
{
    private String prefix = "module/datashareFollow";

	@Autowired
	private IDatashareFollowService datashareFollowService;

	@Autowired
	private IDeptService deptService;


	@GetMapping("/{datashareCourtid}")
	public String datashareFollow(@PathVariable("datashareCourtid") String datashareCourtid,ModelMap mmap)
	{
		mmap.put("datashareCourtid",datashareCourtid);
	    return prefix + "/datashareFollow";
	}

	/**
	 * 查询各法院查看权列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(DatashareFollow datashareFollow)
	{
		startPage();
        List<DatashareFollow> list = datashareFollowService.selectDatashareFollowList(datashareFollow);
		return getDataTable(list);
	}

	/**
	 * 新增各法院查看权
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap,String datashareCourtid)
	{
		Dept dept = new Dept();
		dept.setDeptType("0");
		List<Dept> thirdList = deptService.selectDeptList(dept);
		DatashareFollow datashareFollow=null;
		Dept depts=deptService.selectDeptById(Long.valueOf(datashareCourtid));
		mmap.put("depts", depts);
		mmap.put("deptList", thirdList);
	    return prefix + "/add";
	}

	/**
	 * 新增保存各法院查看权
	 */
	@Log(title = "各法院查看权", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(DatashareFollow datashareFollow)
	{
		DatashareFollow item=new DatashareFollow();
		item.setDatashareCourtid(datashareFollow.getDatashareCourtid());
		item.setSharecourtId(datashareFollow.getSharecourtId());
		if(datashareFollowService.selectDatashareFollowList(item).size()>0){
			return error("该法院已添加！");
		}
		return toAjax(datashareFollowService.insertDatashareFollow(datashareFollow));
	}

	/**
	 * 修改各法院查看权
	 */
	@GetMapping("/edit/{iD}")
	public String edit(@PathVariable("iD") Integer iD, ModelMap mmap)
	{
		DatashareFollow datashareFollow = datashareFollowService.selectDatashareFollowById(iD);
		Dept dept = new Dept();
		dept.setDeptType("0");
		List<Dept> thirdList = deptService.selectDeptList(dept);
		mmap.put("deptList", thirdList);
		mmap.put("datashareFollow", datashareFollow);
	    return prefix + "/edit";
	}

	/**
	 * 修改保存各法院查看权
	 */
	@Log(title = "各法院查看权", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(DatashareFollow datashareFollow)
	{
		return toAjax(datashareFollowService.updateDatashareFollow(datashareFollow));
	}

	/**
	 * 删除各法院查看权
	 */
	@Log(title = "各法院查看权", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(datashareFollowService.deleteDatashareFollowByIds(ids));
	}

}
