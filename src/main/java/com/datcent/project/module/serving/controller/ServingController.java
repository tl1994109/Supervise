package com.datcent.project.module.serving.controller;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.datcent.common.utils.DateUtils;
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
import com.datcent.project.module.serving.domain.Serving;
import com.datcent.project.module.serving.service.IServingService;
import com.datcent.framework.web.controller.BaseController;
import com.datcent.framework.web.page.TableDataInfo;
import com.datcent.framework.web.domain.AjaxResult;

/**
 * 主要任职情况 信息操作处理
 * 
 * @author datcent
 * @date 2019-04-01
 */
@Controller
@RequestMapping("/module/serving")
public class ServingController extends BaseController
{
    private String prefix = "module/serving";
	
	@Autowired
	private IServingService servingService;

	@GetMapping()
	public String serving()
	{
	    return prefix + "/serving";
	}
	
	/**
	 * 查询主要任职情况列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Serving serving)
	{
		startPage();
        List<Serving> list = servingService.selectServingList(serving);
		return getDataTable(list);
	}
	
	/**
	 * 新增主要任职情况
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存主要任职情况
	 */
	@Log(title = "主要任职情况", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(String jbqkZyrz,String jbqkId)
	{
		AjaxResult json = new AjaxResult();
		Serving serv=new Serving();
		serv.setBasicinforId(jbqkId);
		List<Serving> serList = servingService.selectServingList(serv);
		if (serList.size()>0){
			json.put("code",500);
			json.put("msg","信息已录入,请勿重复提交！");
		}else{
			List<Serving> servings = JSONObject.parseArray(jbqkZyrz, Serving.class);
			int count=0;
			for (Serving serving : servings) {
				if (StringUtils.isNotEmpty(serving.getStartTime())
						||StringUtils.isNotEmpty(serving.getEndTime())
						||StringUtils.isNotEmpty(serving.getToUnit())
						||StringUtils.isNotEmpty(serving.getAssumeOffice())){
					serving.setId(StringUtils.getUUID());
					serving.setBasicinforId(jbqkId);
					serving.setCreateTime(DateUtils.getNowDate());
					int row = servingService.insertServing(serving);
					count+=row;
				}
			}
			if (count>0){
				json.put("code",200);
				json.put("msg","保存成功！");
			}
		}
		return json;
	}

	/**
	 * 修改主要任职情况
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		Serving serving = servingService.selectServingById(id);
		mmap.put("serving", serving);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存主要任职情况
	 */
	@Log(title = "主要任职情况", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Serving serving)
	{		
		return toAjax(servingService.updateServing(serving));
	}
	
	/**
	 * 删除主要任职情况
	 */
	@Log(title = "主要任职情况", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(servingService.deleteServingByIds(ids));
	}

}
