package com.datcent.project.module.flowchart;

import com.datcent.framework.web.controller.BaseController;
import com.datcent.project.module.area.service.IAreaService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 流程处置表单 信息操作处理
 * 
 * @author zhoushiji
 * @date 2019-1-15
 */
@Controller
@RequestMapping("/module/flowchart")
public class FlowChartController extends BaseController
{
    private String prefix = "module/flowchart";

	/**
	 * 问题线索移送（备案）呈批表 信息操作处理
	 *
	 * @author zhoushiji
	 * @date 2019-1-15
	 */
	@RequiresPermissions("module:flowchart:wtczbaform")
	@GetMapping("/wtczbaform")
	public String wtczbaform()
	{
	    return prefix + "/wtczbaform";
	}

	/**
	 * 问题线索分办呈批表 信息操作处理
	 *
	 * @author zhoushiji
	 * @date 2019-1-15
	 */
	@RequiresPermissions("module:flowchart:wtxscpform")
	@GetMapping("/wtxscpform")
	public String wtxscpform()
	{
		return prefix + "/wtxscpform";
	}

	/**
	 * 谈话呈批表 信息操作处理
	 *
	 * @author zhoushiji
	 * @date 2019-1-15
	 */
	@RequiresPermissions("module:flowchart:thcpform")
	@GetMapping("/thcpform")
	public String thcpform()
	{
		return prefix + "/thcpform";
	}

	/**
	 * 函询呈批表 信息操作处理
	 *
	 * @author zhoushiji
	 * @date 2019-1-15
	 */
	@RequiresPermissions("module:flowchart:hxcpform")
	@GetMapping("/hxcpform")
	public String hxcpform()
	{
		return prefix + "/hxcpform";
	}

	/**
	 * 关于对×××谈话（函询）的情况报告 信息操作处理
	 *
	 * @author zhoushiji
	 * @date 2019-1-16
	 */
	@RequiresPermissions("module:flowchart:thqkbgform")
	@GetMapping("/thqkbgform")
	public String thqkbgform()
	{
		return prefix + "/thqkbgform";
	}

	/**
	 * 初步核实呈批表 信息操作处理
	 *
	 * @author zhoushiji
	 * @date 2019-1-16
	 */
	@RequiresPermissions("module:flowchart:cbhscpform")
	@GetMapping("/cbhscpform")
	public String cbhscpform()
	{
		return prefix + "/cbhscpform";
	}

	/**
	 * 初步核实方案 信息操作处理
	 *
	 * @author zhoushiji
	 * @date 2019-1-16
	 */
	@RequiresPermissions("module:flowchart:cbhsfaform")
	@GetMapping("/cbhsfaform")
	public String cbhsfaform()
	{
		return prefix + "/cbhsfaform";
	}

    /**
     * 使用询问措施呈批表 信息操作处理
     *
     * @author zhoushiji
     * @date 2019-1-16
     */
    @RequiresPermissions("module:flowchart:xwcscpform")
    @GetMapping("/xwcscpform")
    public String xwcscpform()
    {
        return prefix + "/xwcscpform";
    }

    /**
     * 讯问笔录 信息操作处理
     *
     * @author zhoushiji
     * @date 2019-1-16
     */
    @RequiresPermissions("module:flowchart:xwblform")
    @GetMapping("/xwblform")
    public String xwblform()
    {
        return prefix + "/xwblform";
    }
	

	
}
