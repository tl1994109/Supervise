package com.datcent.project.monitor.job.task;

import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.StringUtils;
import com.datcent.project.module.information.domain.Information;
import com.datcent.project.module.information.service.IInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: 张梦圆
 * @Email: zhangmengyuan@datcent.com
 * @CreateDate: 2019/5/23 17:15
 * @Description: TODO 任务管理   定时任务更改状态
 **/
@Component("CheckInformation")
public class CheckInformation {

    @Autowired
    private IInformationService informationService;

    public void updateInformation(){
        Information information = new Information();
        //获取当前时间
        information.setTaskPlanStartdate(DateUtils.getDate());
        //查询计划当前时间开始任务
        List<Information> informationList = informationService.selectInformationList(information);
        //判断是否存在
        if(informationList.size()>0){
            //循环集合
            for (Information i:informationList) {
                //如果填写指派人
                if(StringUtils.isNotEmpty(i.getTaskProcessor())){
                    //设置状态进行中
                    i.setTaskStatus("1");
                    //任务开始时间当天
                    i.setTaskStartdate(DateUtils.getDate());
                    informationService.updateTask(i);
                }
            }
        }
        Information end_information = new Information();
        end_information.setTaskPlanEnddate(DateUtils.getDate());
        //查询出计划时间
        List<Information> end_informationList = informationService.selectInformationList(end_information);
        if(end_informationList.size()>0){
            for (Information e:end_informationList) {
                if("1".equals(e.getTaskStatus()) || "0".equals(e.getTaskStatus())){
                    e.setTaskStatus("5");
                    informationService.updateTask(e);
                }
            }
        }
    }
}
