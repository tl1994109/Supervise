package com.datcent.project.system.activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: 张梦圆
 * @Email: zhangmengyuan@datcent.com
 * @CreateDate: 2019/1/7 17:46
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO
 **/
@Controller
@RequestMapping("/activiti")
public class ActivitiController {

    @RequestMapping("/show")
    public String show(){
        return "";
    }
}
