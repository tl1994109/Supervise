/**
 * @Author: Chanyin尹强
 * @Email: yinqiang@datcent.com
 * @CreateDate: 2018/11/20 15:24
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO taleForm表单组件
 **/
var formComponent = {
    // 表单重置
    reset: function (formId) {
        var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
        $("#" + currentId)[0].reset();
    },
    // 获取选中复选框项
    selectCheckeds: function (name) {
        var checkeds = "";
        $('input:checkbox[name="' + name + '"]:checked').each(function (i) {
            if (0 == i) {
                checkeds = $(this).val();
            } else {
                checkeds += ("," + $(this).val());
            }
        });
        return checkeds;
    },
    // 获取选中下拉框项
    selectSelects: function (name) {
        var selects = "";
        $('#' + name + ' option:selected').each(function (i) {
            if (0 == i) {
                selects = $(this).val();
            } else {
                selects += ("," + $(this).val());
            }
        });
        return selects;
    }
};