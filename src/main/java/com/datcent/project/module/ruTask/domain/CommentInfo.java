package com.datcent.project.module.ruTask.domain;

import java.io.Serializable;

/**
 * @Author: 张梦圆
 * @Email: zhangmengyuan@datcent.com
 * @CreateDate: 2019/1/14 11:30
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO 流程批注
 **/
public class CommentInfo implements Serializable {

    private static final long serialVersionUID = 8941753060666996801L;
    //批注人
    private String userId;
    //批注人姓名
    private String userName;
    //审批标识   true  同意   false   不同意
    private String isok;
    //批注的审批意见
    private String content;
    //设置批注时间
    private String endTime;
    //开始时间
    private String startTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsok() {
        return isok;
    }

    public void setIsok(String isok) {
        this.isok = isok;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
