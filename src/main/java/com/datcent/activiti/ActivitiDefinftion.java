package com.datcent.activiti;

import java.io.Serializable;

/**
 * @Author: 张梦圆
 * @Email: zhangmengyuan@datcent.com
 * @CreateDate: 2019/1/13 15:08
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO 常亮
 **/
public class ActivitiDefinftion implements Serializable {

    private static final long serialVersionUID = 8955583311687055970L;

    //公告审批KEY
    public static final String ACTIVITI_NOTICE_TYPE = "公告审批";

    //可疑违纪转入线索KEY
    public static final String ACTIVITI_DUBVIOEVENT_TYPE = "可疑违纪转入线索";

    //可疑违纪事件退回KEY
    public static final String ACTIVITI_DUBVIOEVENT_TH_TYPE = "可疑违纪事件退回";

    //可疑违纪事件转入立案二庭KEY
    public static final String ACTIVITI_DUBVIOEVENT_LA_TYPE = "可疑违纪事件转入立案二庭";


    //可疑违纪事件转入监察处KEY
    public static final String ACTIVITI_DUBVIOEVENT_JCC_TYPE = "可疑违纪事件转入监察处";


    //可疑违纪事件转其他KEY
    public static final String ACTIVITI_DUBVIOEVENT_QT_TYPE = "可疑违纪事件转其他";

    //可疑违纪事件删除KEY
    public static final String ACTIVITI_REMOVE_DUBVIOEVENT_TYPE = "可疑违纪事件删除";

    //可疑违纪转入线索KEY
    public static final String ACTIVITI_CLUE_TYPE = "问题处置流程";


    //
    public static final String ACTIVITI_CLUE_XSCZ = "线索处置分类";


    //书记员权限标识
    public static final String ROLE_Recorder_KEY = "recorder";

    //审批权限标识  管理员
    public static final String ROLE_KEY = "approve";

    //数据权限
    public static final String ROLE_COMMON_KEY="common";

    //系统管理员
    public static final String ROLE_ADMIN_KEY = "admin";

    //任务指派权
    public static final String ROLE_TASKASSIGNMENT="taskAssignment";

    //流程图结束线标识
    public static final String ACTIVITI_FINISH = "结束";

    //申请人姓名
    public static final String ACTIVITI_PROPOSER_NAME="proposerName";

    //申请人ID
    public static final String ACTIVITI_PROPOSER_ID="proposer";

    //虚拟路径
    public static final String VIRTUAL_PATH = "/profile/";
}
