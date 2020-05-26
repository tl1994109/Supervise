package com.datcent.activiti;

import com.datcent.common.utils.activiti.TaskVariablesName;
import org.activiti.engine.delegate.DelegateTask;

import java.util.Map;

/**
 * @Author: 张梦圆
 * @Email: zhangmengyuan@datcent.com
 * @CreateDate: 2019/1/9 16:52
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO
 **/
public class TaskListener implements org.activiti.engine.delegate.TaskListener {
    private static final long serialVersionUID = 1L;

    /**
     * 用来指定任务的办理人
     * */
    @Override
    public void notify(DelegateTask delegateTask) {
        //获取办理人信息
        Map<String, Object> variables = delegateTask.getVariables();
        if("true".equals(variables.get("isok"))){
            //流程变量
            delegateTask.setVariables(variables);
            //设置下级任务人
            delegateTask.setAssignee(variables.get("approver").toString());
        }else{
            //流程变量
            delegateTask.setVariables(variables);
            //设置下级任务人
            delegateTask.setAssignee(variables.get("proposer").toString());
        }
    }
}
