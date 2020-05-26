package com.datcent.project.module.process.controller;

import java.util.ArrayList;
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

import com.datcent.common.utils.StringUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.framework.aspectj.lang.annotation.Log;
import com.datcent.framework.aspectj.lang.enums.BusinessType;
import com.datcent.project.module.process.domain.ProcessMatter;
import com.datcent.project.module.process.domain.ProcessNodeDetail;
import com.datcent.project.module.process.service.IProcessMatterService;
import com.datcent.project.module.process.service.IProcessNodeDetailService;
import com.datcent.project.system.user.domain.User;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 流程事项  信息操作处理
 * 
 * @author datcent
 * @date 2018-11-08
 */
@Controller
@RequestMapping("/module/processMatter")
public class ProcessMatterController extends BaseController
{
    private String prefix = "module/processMatter";
	
	@Autowired
	private IProcessMatterService processMatterService;
	
	@Autowired
    private IProcessNodeDetailService processNodeDetailService;
	
	@RequiresPermissions("module:processMatter:view")
	@GetMapping()
	public String processMatter()
	{
	    return prefix + "/processMatter";
	}
	
	/**
	 * 查询流程事项 列表
	 */
	@RequiresPermissions("module:processMatter:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProcessMatter processMatter)
	{
		startPage();
        List<ProcessMatter> list = processMatterService.selectProcessMatterList(processMatter);
		return getDataTable(list);
	}
	
	/**
	 * 查询当前用户流程事项 列表
	 */
	/*@RequiresPermissions("module:processMatter:list")*/
	@PostMapping("/auditingList")
	@ResponseBody
	public TableDataInfo auditingList(ProcessMatter processMatter)
	{
		startPage();
        List<ProcessMatter> list = processMatterService.selectProcessMatterList(processMatter);
        if (StringUtils.isNotNull(ShiroUtils.getUserId()) && !User.isAdmin(ShiroUtils.getUserId()))
        {
        	Long localUser = ShiroUtils.getUserId();
            int row = list.size();
            for (int i = 0; i < row; i++) {
            	String[] allowBy = list.get(i).getAllowBy().split(",");
            	int removeRow=0;
    			for (int j = 0; j < allowBy.length; j++) {
    				if(!allowBy[j].equals(localUser.toString())){
    					removeRow++;
    				}
    			}
    			if(removeRow==allowBy.length){
    				list.remove(i);
    				row--;
    				i--;
    			}
    		}
        }
        
		return getDataTable(list);
	}
	
	
	/**
	 * 新增流程事项 
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存流程事项 
	 */
	@RequiresPermissions("module:processMatter:add")
	@Log(title = "流程事项 ", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProcessMatter processMatter)
	{
		return toAjax(processMatterService.insertProcessMatter(processMatter));
	}
	
	
	/**
	 * 公告新增保存流程事项 
	 */
	@RequiresPermissions("module:processMatter:addNotice")
	@Log(title = "流程事项 ", businessType = BusinessType.INSERT)
	@PostMapping("/addNotice")
	@ResponseBody
	public AjaxResult addNoticeSave(ProcessMatter processMatter)
	{
		return toAjax(processMatterService.insertProcessMatter(processMatter));
	}
	
	/**
	 * 修改流程事项 
	 */
	@GetMapping("/edit/{matterId}")
	public String edit(@PathVariable("matterId") String matterId, ModelMap mmap)
	{
		ProcessMatter processMatter = processMatterService.selectProcessMatterById(matterId);
		mmap.put("processMatter", processMatter);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存流程事项 
	 */
	@RequiresPermissions("module:processMatter:edit")
	@Log(title = "流程事项 ", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProcessMatter processMatter)
	{		
		return toAjax(processMatterService.updateProcessMatter(processMatter));
	}
	
	/**
	 * 删除流程事项 
	 */
	@RequiresPermissions("module:processMatter:remove")
	@Log(title = "流程事项 ", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(processMatterService.deleteProcessMatterByIds(ids));
	}
	
}
