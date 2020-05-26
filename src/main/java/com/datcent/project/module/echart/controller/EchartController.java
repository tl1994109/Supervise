package com.datcent.project.module.echart.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.datcent.project.monitor.logininfor.domain.Logininfor;
import com.datcent.project.monitor.logininfor.service.ILogininforService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.datcent.framework.web.controller.BaseController;

/**
 * echart统计图展示
 * 
 * @author datcent
 * @date 2018-11-20
 */
@Controller
@RequestMapping("/echart/data")
public class EchartController extends BaseController
{
    private String prefix = "/chart";

	@Autowired
	private ILogininforService logininforService;
	
	@RequiresPermissions("echart:data:view")
	@GetMapping()
	public String area()
	{
	    return prefix + "/chart1";
	}

	@RequiresPermissions("echart:data:view")
	@PostMapping("/pie")
	@ResponseBody
	public Map<String, Object> querypie(Logininfor logininfor) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name","数据表");
		map.put("xdata",list);
		return resultMap;
	}
}