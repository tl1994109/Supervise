package com.datcent.project.module.ruTask.domain;

import com.datcent.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.activiti.engine.history.HistoricActivityInstance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: 张梦圆
 * @Email: zhangmengyuan@datcent.com
 * @CreateDate: 2019/1/12 16:38
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO
 **/
public class HistoricActivity {
    private String actName;
    private String assignee;
    private String owner;
    private String startTime;
    private String endTime;
    private String durationInMillis;

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDurationInMillis() {
        return durationInMillis;
    }

    public void setDurationInMillis(String durationInMillis) {
        this.durationInMillis = durationInMillis;
    }

    public HistoricActivity(){}
    public HistoricActivity(HistoricActivityInstance instance) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.actName = instance.getActivityName();
        this.assignee = instance.getAssignee();
        if(instance.getStartTime()!=null){
            this.startTime = simpleDateFormat.format(instance.getStartTime());
        }
        if(instance.getEndTime()!=null){
            this.endTime = simpleDateFormat.format(instance.getEndTime());
            this.durationInMillis = DateUtils.dateDiff(instance.getStartTime(),instance.getEndTime());
        }
        this.owner = this.getOwner();
    }
}
