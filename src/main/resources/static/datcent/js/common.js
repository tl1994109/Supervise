/**
 * 通用方法封装处理
 * Copyright (c) 2018 ruoyi
 */

$(function () {
    // select2复选框事件绑定
    if ($.fn.select2 !== undefined) {
        $("select.form-control:not(.noselect2)").each(function () {
            $(this).select2().on("change", function () {
                $(this).valid();
            })
        })
    }
    // checkbox 事件绑定
    if ($(".check-box").length > 0) {
        $(".check-box").iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
        })
    }
    // radio 事件绑定
    if ($(".radio-box").length > 0) {
        $(".radio-box").iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
        })
    }
    // laydate 时间控件绑定
    if ($(".select-time").length > 0) {
        layui.use('laydate', function () {
            var laydate = layui.laydate;
            // 开始日期
            laydate.render({elem: '#startTime', theme: 'molv'});
            // 结束日期
            laydate.render({elem: '#endTime', theme: 'molv'});
            // 立案日期
            laydate.render({elem: '#collectDate', theme: 'molv'});
            // 收案日期
            laydate.render({elem: '#filingDate', theme: 'molv'});
            // 结案日期
            laydate.render({elem: '#settleCaseDate', theme: 'molv'});
            // 审判日期
            laydate.render({elem: '#trialDate', theme: 'molv'});
            // 归档日期
            laydate.render({elem: '#fileDate', theme: 'molv'});
            // 执行日期
            laydate.render({elem: '#implementDate', theme: 'molv'});
            // 审限届满日期
            laydate.render({elem: '#trialExpirationDate', theme: 'molv'});
            // 立案届满日期
            laydate.render({elem: '#filingExpirationDate', theme: 'molv'});
        });
    }
    // 复选框后按钮样式状态变更
    $("#bootstrap-table").on("check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table", function (e, row, element) {
        var children = $(this)["0"].children[1].children;
        for (var i = 0; i < children.length; i++) {
            if ($(children[i]).find(":checkbox").prop("checked")) {
                $(children[i]).addClass("select-table-row");
            } else {
                $(children[i]).removeClass("select-table-row");
            }
        }
        var ids = $("#bootstrap-table").bootstrapTable("getSelections");
        $('#toolbar .btn-del').toggleClass('disabled', !ids.length);
        $('#toolbar .btn-edit').toggleClass('disabled', ids.length != 1);
        $('#toolbar .btn-detail').toggleClass('disabled', ids.length != 1);
        $('#toolbar .btn-enable').toggleClass('disabled', ids.length != 1);
        $('#toolbar .btn-disable').toggleClass('disabled', ids.length != 1);
    });
    // 选中行触发事件
    // $("#bootstrap-table").on("click-row.bs.table", function (e, row, element) {
    //     $(".select-table-row").removeClass("select-table-row");
    //     $(element).addClass("select-table-row");
    //     if ($(element).find(":checkbox").prop("checked")) {
    //         $(this).find(":checkbox").removeAttr("checked");
    //         $(element).addClass("select-table-row");
    //         $('#toolbar .btn-del').toggleClass('disabled', true);
    //         $('#toolbar .btn-edit').toggleClass('disabled', true);
    //         $('#toolbar .btn-detail').toggleClass('disabled', true);
    //     } else {
    //         $(this).find(":checkbox").removeAttr("checked");
    //         $(element).find(":checkbox").prop("checked", true);
    //         $(element).removeClass("select-table-row");
    //         $('#toolbar .btn-del').toggleClass('disabled', false);
    //         $('#toolbar .btn-edit').toggleClass('disabled', false);
    //         $('#toolbar .btn-detail').toggleClass('disabled', false);
    //     }
    // });
    //获取选中行参数
    // $.table.getSelectRowData = function () {
    //     var index = $("#bootstrap-table").find("tr.select-table-row").data("index");
    //     return $("#bootstrap-table").bootstrapTable('getData')[index];
    // };


    $("#bootstrap-table").on("click","tbody tr",function () {
        $(this).toggleClass("selected select-table-row").find(".bs-checkbox input").trigger('click');
        if($(this).hasClass('selected select-table-row')) {
            $(this).addClass('select-table-row')
        }else{
            $(this).removeClass('selected select-table-row')
        }
    })

    // tree 关键字搜索绑定
    if ($("#keyword").length > 0) {
        $("#keyword").bind("focus", function focusKey(e) {
            if ($("#keyword").hasClass("empty")) {
                $("#keyword").removeClass("empty");
            }
        }).bind("blur", function blurKey(e) {
            if ($("#keyword").val() === "") {
                $("#keyword").addClass("empty");
            }
            $.tree.searchNode(e);
        }).bind("input propertychange", $.tree.searchNode);
    }

});

/** 创建选项卡 */
function createMenuItem(dataUrl, menuName) {
    var panelUrl = window.frameElement.getAttribute('data-id');
    dataIndex = $.common.random(1,100),
        flag = true;
    if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;
    var topWindow = $(window.parent.document);
    // 选项卡菜单已存在
    $('.menuTab', topWindow).each(function() {
        if ($(this).data('id') == dataUrl) {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('.menuTab').removeClass('active');
                $('.page-tabs-content').animate({ marginLeft: ""}, "fast");
                // 显示tab对应的内容区
                $('.mainContent .RuoYi_iframe', topWindow).each(function() {
                    if ($(this).data('id') == dataUrl) {
                        $(this).show().siblings('.RuoYi_iframe').hide();
                        return false;
                    }
                });
            }
            flag = false;
            return false;
        }
    });
    // 选项卡菜单不存在
    if (flag) {
        var str = '<a href="javascript:;" class="active menuTab" data-id="' + dataUrl + '" data-panel="' + panelUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
        $('.menuTab', topWindow).removeClass('active');

        // 添加选项卡对应的iframe
        var str1 = '<iframe class="RuoYi_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" data-panel="' + panelUrl + '" seamless></iframe>';
        $('.mainContent', topWindow).find('iframe.RuoYi_iframe').hide().parents('.mainContent').append(str1);

        window.parent.$.modal.loading("数据加载中，请稍后...");
        $('.mainContent iframe:visible', topWindow).load(function () {
            window.parent.$.modal.closeLoading();
        });

        // 添加选项卡
        $('.menuTabs .page-tabs-content', topWindow).append(str);
    }
    return false;
}
/** 关闭选项卡 */
var closeItem = function(){
    var topWindow = $(window.parent.document);
    var panelUrl = window.frameElement.getAttribute('data-panel');
    $('.page-tabs-content .active i', topWindow).click();
    if($.common.isNotEmpty(panelUrl)){
        $('.menuTab[data-id="' + panelUrl + '"]', topWindow).addClass('active').siblings('.menuTab').removeClass('active');
        $('.mainContent .RuoYi_iframe', topWindow).each(function() {
            if ($(this).data('id') == panelUrl) {
                $(this).show().siblings('.RuoYi_iframe').hide();
                return false;
            }
        });
    }
}
/** 设置全局ajax超时处理 */
$.ajaxSetup({
    complete: function (XMLHttpRequest, textStatus) {
        if (textStatus == "parsererror") {
            $.modal.confirm("登陆超时！请重新登陆！", function () {
                window.location.href = ctx + "login";
            })
        }
    }
});
