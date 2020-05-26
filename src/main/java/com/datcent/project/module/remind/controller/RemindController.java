package com.datcent.project.module.remind.controller;

import com.datcent.framework.web.controller.BaseController;
import com.datcent.project.module.remind.domain.Remind;
import com.datcent.project.module.remind.service.IRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: 张梦圆
 * @Email: zhangmengyuan@datcent.com
 * @CreateDate: 2019/1/25 17:45
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO
 **/
@Controller
@RequestMapping("/module/remind")
public class RemindController extends BaseController {

    @Autowired
    private IRemindService remindService;

    @PostMapping("/remindList")
    @ResponseBody
    public List<Remind> remindList() throws ParseException {
        return remindService.statisticsList();
    }

}
