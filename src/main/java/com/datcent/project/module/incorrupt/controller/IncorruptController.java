package com.datcent.project.module.incorrupt.controller;

import com.datcent.common.utils.StringUtils;
import com.datcent.framework.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: 张梦圆
 * @Email: zhangmengyuan@datcent.com
 * @CreateDate: 2019/3/29 10:38
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO
 **/
@Controller
@RequestMapping("/module/incorrupt")
public class IncorruptController  extends BaseController {
    private String prefix = "module/incorrupt";

    @RequiresPermissions("module:incorrupt:view")
    @GetMapping()
    public String knowledge(ModelMap mmap)
    {
        return prefix + "/toincorrupt";
    }

    /**
     * 查看 档案审核状态
     * @param mmap
     * @return
     */
    @GetMapping("/checkprogress")
    public String checkprogress(ModelMap mmap)
    {
        return prefix + "/checkprogress";
    }


    @GetMapping("/editincorrupt")
    public String editIncorrupt(ModelMap mmap)
    {
        return prefix + "/incorrupt";
    }

    @RequiresPermissions("module:incorrupt:detail")
    @GetMapping("/incorruptdetail")
    public String incorrupt(ModelMap mmap)
    {
        return prefix + "/incorruptdetail";
    }
}
