package com.datcent.project.module.remind.service;

import com.datcent.common.utils.DateUtils;
import com.datcent.common.utils.security.ShiroUtils;
import com.datcent.project.module.clue.domain.Clue;
import com.datcent.project.module.clue.service.IClueService;
import com.datcent.project.module.information.domain.Information;
import com.datcent.project.module.information.service.IInformationService;
import com.datcent.project.module.remind.domain.Remind;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: 张梦圆
 * @Email: zhangmengyuan@datcent.com
 * @CreateDate: 2019/1/25 17:18
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO
 **/
@Service
public class RemindServiceImpl implements IRemindService {

    @Autowired
    private IInformationService informationService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private IClueService clueService;

    //统计显示模块内容
    @Override
    public List<Remind> statisticsList() throws ParseException {
        String nowdate = DateUtils.dateTimeNow("yyyy-MM-dd");
        List<Remind> remindList = new ArrayList<Remind>();
        //统计个人任务未开始条数
        Information information = new Information();
        information.setTaskProcessor(ShiroUtils.getUserId().toString());
        information.setIsTXStatus("0");
        List<Information> informationList = new ArrayList<Information>();
        informationList = informationService.selectRemindList(information);
        //判断是否有未处理任务
        if(informationList.size()>0) {
            Remind remind = new Remind();
            remind.setCount(informationList.size());
            remind.setStatus("未开始");
            remind.setType("个人任务");
            remind.setUrl("module/information?isTXStatus=0");
            remind.setModalName("任务管理");
            remind.setTitle(remind.getType()+"有"+remind.getCount()+"条"+remind.getStatus());
            remindList.add(remind);
        }

        Information information_sub = new Information();
        information_sub.setSubmitter(ShiroUtils.getUserId().toString());
        information.setIsTXStatus("3");
        informationList = informationService.selectRemindList(information_sub);

        if(informationList.size()>0){
            Remind remind = new Remind();
            remind.setCount(informationList.size());
            remind.setStatus("待分配");
            remind.setType("个人任务");
            remind.setUrl("module/information?isTXStatus=1");
            remind.setModalName("任务管理");
            remind.setTitle(remind.getType()+"有"+remind.getCount()+"条"+remind.getStatus());
            remindList.add(remind);
        }

        //统计处理中但是要到期的任务
        information.setIsTXStatus("1");
        informationList = informationService.selectRemindList(information);
        //判断是否有进行中任务
        if(informationList.size()>0){
            int nowTask = 0;
            int planTask =0;
            for (Information ifm:informationList) {
                //判断当天到期的
                if(DateUtils.differentDays(nowdate,ifm.getTaskPlanEnddate())==0){
                    nowTask++;
                }else if(DateUtils.differentDays(nowdate,ifm.getTaskPlanEnddate())==1){//判断还有一天到期的
                    planTask++;
                }
            }
            if(nowTask>0){
                //当天到期的做处理
                Remind remind = new Remind();
                remind.setCount(nowTask);
                remind.setStatus("进行中任务今日截止");
                remind.setType("个人任务");
                remind.setUrl("module/information?isTXStatus=2");
                remind.setTitle(remind.getType()+"有"+nowTask+"条"+remind.getStatus());
                remind.setModalName("任务管理");
                remindList.add(remind);
            }
            if(planTask>0){
                Remind remind = new Remind();
                //处理即将到期的
                remind.setCount(planTask);
                remind.setStatus("进行中任务剩余1日");
                remind.setType("个人任务");
                remind.setUrl("module/information?isTXStatus=3");
                remind.setModalName("任务管理");
                remind.setTitle(remind.getType()+"有"+planTask+"条"+remind.getStatus());
                remindList.add(remind);
            }
        }

        //查询超期未完成的
        information.setIsTXStatus("5");
        informationList = informationService.selectRemindList(information);
        if(informationList.size()>0){
            Remind remind = new Remind();
            remind.setCount(informationList.size());
            remind.setStatus("超期未完成");
            remind.setType("个人任务");
            remind.setUrl("module/information?isTXStatus=4");
            remind.setModalName("任务管理");
            remind.setTitle(remind.getType()+"有"+remind.getCount()+"条"+remind.getStatus());
            remindList.add(remind);
        }
        Long userid = ShiroUtils.getUserId();
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.taskAssignee(userid.toString());
        Long task_count = taskQuery.count();
        if(task_count>0){
            Remind remind = new Remind();
            remind.setCount(task_count.intValue());
            remind.setStatus("待办");
            remind.setType("待办流程");
            remind.setUrl("module/ruTask");
            remind.setModalName("流程管理");
            remind.setTitle(remind.getType()+"有"+remind.getCount()+"条"+remind.getStatus());
            remindList.add(remind);
        }
        //暂存待查提醒
        Clue clue = new Clue();
        clue.setIsCQ("1");
        List<Clue> clueList = clueService.selectClueList(clue, null);
        if(clueList.size()>0){
            Remind remind = new Remind();
            remind.setCount(clueList.size());
            remind.setStatus("超期");
            remind.setType("暂存待查");
            remind.setUrl("module/problemdisposal/zcdcproblemDisposal?isCQ=1");
            remind.setModalName("暂存待查");
            remind.setTitle(remind.getType()+"有"+remind.getCount()+"条"+remind.getStatus());
            remindList.add(remind);
        }
        if(remindList.size()==0){
            Remind remind = new Remind();
            //逾期的
            remind.setTitle("暂无提醒");
            remindList.add(remind);
        }
        return remindList;
    }
}
