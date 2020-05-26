/**
*@Author: 张梦圆
*@Email: zhangmengyuan@datcent.com
*@CreateDate: 2018/11/23 17:29
*@Copyright: © 2018 德讯科技 版权所有
*@Description: TODO 校验封装处理
*/

var validateComponent = {
    // 判断返回标识是否唯一 false 不存在 true 存在
    unique: function (value) {
        if (value == "0") {
            return true;
        }
        return false;
    },
    // 表单验证
    form: function (formId) {
        var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
        return $("#" + currentId).validate().form();
    }
};