package com.datcent.project.module.remind.service;

import com.datcent.project.module.remind.domain.Remind;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: 张梦圆
 * @Email: zhangmengyuan@datcent.com
 * @CreateDate: 2019/1/25 17:18
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO
 **/
public interface IRemindService {

    //统计显示模块内容
    public List<Remind> statisticsList() throws ParseException;
}
