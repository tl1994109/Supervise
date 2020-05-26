package com.datcent.project.module.itemreport.controller;

import java.util.List;

import com.datcent.common.utils.StringUtils;
import com.datcent.project.module.basicinformation.domain.Basicinformation;
import com.datcent.project.module.basicinformation.service.BasicinformationServiceImpl;
import com.datcent.project.module.basicinformation.service.IBasicinformationService;
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
import com.datcent.project.module.itemreport.domain.Itemreport;
import com.datcent.project.module.itemreport.service.IItemreportService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 有关事件报告 信息操作处理
 * 
 * @author datcent
 * @date 2019-04-01
 */
@Controller
@RequestMapping("/module/itemreport")
public class ItemreportController extends BaseController
{
    private String prefix = "module/itemreport";
	
	@Autowired
	private IItemreportService itemreportService;

	@Autowired
	private IBasicinformationService basicinformationService;

	@GetMapping()
	public String itemreport()
	{
	    return prefix + "/itemreport";
	}
	
	/**
	 * 查询有关事件报告列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Itemreport itemreport)
	{
		startPage();
        List<Itemreport> list = itemreportService.selectItemreportList(itemreport);
		return getDataTable(list);
	}
	
	/**
	 * 新增有关事件报告
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存有关事件报告
	 */
	@Log(title = "有关事件报告", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Itemreport itemreport, Basicinformation basic)
	{
		AjaxResult json = new AjaxResult();

		if (StringUtils.isNotEmpty(itemreport.getSxbgId())){
			basic.setIsEdit("9");
			basicinformationService.updateBasicinformation(basic);
			int rows=itemreportService.updateItemreport(itemreport);
			if (rows>0){
				json.put("code",200);
				json.put("msg","保存成功！");
			}else{
				json.put("code",500);
				json.put("msg","保存失败！");
			}
		}else{
			itemreport.setSxbgId(StringUtils.getUUID());
			int rows=itemreportService.insertItemreport(itemreport);
			if (rows>0){
				json.put("sxbgId",itemreport.getSxbgId());
				json.put("code",200);
				json.put("msg","保存成功！");
			}else{
				json.put("code",500);
				json.put("msg","保存失败！");
			}
		}
		return json;
	}

	/**
	 * 修改有关事件报告
	 */
	@GetMapping("/edit/{sxbgId}")
	public String edit(@PathVariable("sxbgId") String sxbgId, ModelMap mmap)
	{
		Itemreport itemreport = itemreportService.selectItemreportById(sxbgId);
		mmap.put("itemreport", itemreport);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存有关事件报告
	 */
	@Log(title = "有关事件报告", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Itemreport itemreport)
	{		
		return toAjax(itemreportService.updateItemreport(itemreport));
	}
	
	/**
	 * 删除有关事件报告
	 */
	@Log(title = "有关事件报告", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(itemreportService.deleteItemreportByIds(ids));
	}
	
}
