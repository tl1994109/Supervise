package com.datcent.project.module.echart.controller;

import com.datcent.framework.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/echarts/Tjfx")
public class TjfxController extends BaseController {

    private String prefix = "echarts/home";

    private String secondPrefix = "echarts/disposal";

    private String thirdPrefix = "echarts/work";


    @RequiresPermissions("echarts:home:view")
    @GetMapping("/home")
    public String home()
    {
        return prefix + "/home";
    }


    @RequiresPermissions("echarts:disposal:view")
    @GetMapping("/disposal")
    public String disposal()
    {
        return secondPrefix + "/disposal";
    }


    @RequiresPermissions("echarts:work:view")
    @GetMapping("/work")
    public String work()
    {
        return thirdPrefix + "/work";
    }
}

