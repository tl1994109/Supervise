package com.datcent.project.module.remind.domain;

import java.io.Serializable;

/**
 * @Author: 张梦圆
 * @Email: zhangmengyuan@datcent.com
 * @CreateDate: 2019/1/25 17:12
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO
 **/
public class Remind implements Serializable {

    private static final long serialVersionUID = -277029274551462505L;

    //显示类型 0.任务模块
    private String type;
    //状态 0.未处理 1.到期 2.逾期任务
    private String status;
    //标题
    private String title;
    //数量
    private Integer count;
    //url地址
    private String url;
    //他弹出框名字
    private String modalName;

    public String getModalName() {
        return modalName;
    }

    public void setModalName(String modalName) {
        this.modalName = modalName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
