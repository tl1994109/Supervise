/**
*@Author: 张梦圆
*@Email: zhangmengyuan@datcent.com
*@CreateDate: 2018/11/22 15:53
*@Copyright: © 2018 德讯科技 版权所有
*@Description: TODO 弹出层组件
*/
var modalComponent = {
    // 显示图标
    icon: function (type) {
        var icon = "";
        if (type ==statusCode.modal_status.WARNING) {
            icon = 0;
        } else if (type == statusCode.modal_status.SUCCESS) {
            icon = 1;
        } else if (type == statusCode.modal_status.FAIL) {
            icon = 2;
        } else {
            icon = 3;
        }
        return icon;
    },
    // 消息提示
    msg: function (content, type) {
        if (type != undefined) {
            layer.msg(content, {icon: modalComponent.icon(type), time: 1000, shift: 5});
        } else {
            layer.msg(content);
        }
    },
    // 错误消息
    msgError: function (content) {
        modalComponent.msg(content, statusCode.modal_status.FAIL);
    },
    // 成功消息
    msgSuccess: function (content) {
        modalComponent.msg(content, statusCode.modal_status.SUCCESS);
    },
    // 警告消息
    msgWarning: function (content) {
        modalComponent.msg(content, statusCode.modal_status.WARNING);
    },
    // 弹出提示
    alert: function (content, type) {
        layer.alert(content, {
            icon: modalComponent.icon(type),
            title: "系统提示",
            btn: ['确认'],
            btnclass: ['btn btn-primary'],
        });
    },
    // 消息提示并刷新父窗体
    msgReload: function (msg, type) {
        layer.msg(msg, {
                icon: modalComponent.icon(type),
                time: 500,
                shade: [0.1, '#8F8F8F']
            },
            function () {
                modalComponent.reload();
            });
    },
    // 错误提示
    alertError: function (content) {
        modalComponent.alert(content, statusCode.modal_status.FAIL);
    },
    // 成功提示
    alertSuccess: function (content) {
        modalComponent.alert(content, statusCode.modal_status.SUCCESS);
    },
    // 警告提示
    alertWarning: function (content) {
        modalComponent.alert(content, statusCode.modal_status.WARNING);
    },
    // 关闭窗体
    close: function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    },
    // 确认窗体
    confirm: function (content, callBack) {
        layer.confirm(content, {
            icon: 3,
            title: "系统提示",
            btn: ['确认', '取消'],
            btnclass: ['btn btn-primary', 'btn btn-danger'],
        }, function (index) {
            layer.close(index);
            callBack(true);
        });
    },
    // 弹出层指定宽度
    open: function (title, url, width, height) {
        //如果是移动端，就使用自适应大小弹窗
        if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
            width = 'auto';
            height = 'auto';
        }
        if (toolUtil.isEmpty(title)) {
            title = false;
        }
        ;
        if (toolUtil.isEmpty(url)) {
            url = "/404.html";
        }
        ;
        if (toolUtil.isEmpty(width)) {
            width = 800;
        }
        ;
        if (toolUtil.isEmpty(height)) {
            height = ($(window).height() - 50);
        }
        ;
        layer.open({
            type: 2,
            area: [width + 'px', height + 'px'],
            fix: false,
            //不固定
            maxmin: true,
            shade: 0.3,
            title: title,
            content: url,
            // 弹层外区域关闭
            shadeClose: true
        });
    },
    // 弹出层指定参数选项
    openOptions: function (options) {
        var _url = toolUtil.isEmpty(options.url) ? "/404.html" : options.url;
        var _title = toolUtil.isEmpty(options.title) ? "系统窗口" : options.title;
        var _width = toolUtil.isEmpty(options.width) ? "800" : options.width;
        var _height = toolUtil.isEmpty(options.height) ? ($(window).height() - 50) : options.height;
        layer.open({
            type: 2,
            maxmin: true,
            shade: 0.3,
            title: _title,
            fix: false,
            area: [_width + 'px', _height + 'px'],
            content: _url,
            shadeClose: true,
            btn: ['<i class="fa fa-check"></i> 确认', '<i class="fa fa-close"></i> 关闭'],
            yes: function (index, layero) {
                options.callBack(index, layero)
            }, cancel: function () {
                return true;
            }
        });
    },
    // 弹出层全屏
    openFull: function (title, url, width, height) {
        //如果是移动端，就使用自适应大小弹窗
        if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
            width = 'auto';
            height = 'auto';
        }
        if (toolUtil.isEmpty(title)) {
            title = false;
        }
        ;
        if (toolUtil.isEmpty(url)) {
            url = "/404.html";
        }
        ;
        if (toolUtil.isEmpty(width)) {
            width = 800;
        }
        ;
        if (toolUtil.isEmpty(height)) {
            height = ($(window).height() - 50);
        }
        ;

        var index = layer.open({
            type: 2,
            area: [width + 'px', height + 'px'],
            fix: false,
            //不固定
            maxmin: true,
            shade: 0.3,
            title: title,
            content: url,
            // 弹层外区域关闭
            shadeClose: true
        });
        layer.full(index);
    },
    // 打开遮罩层
    loading: function (message) {
        $.blockUI({message: '<div class="loaderbox"><div class="loading-activity"></div> ' + message + '</div>'});
    },
    // 关闭遮罩层
    closeLoading: function () {
        setTimeout(function () {
            $.unblockUI();
        }, 50);
    },
    // 重新加载
    reload: function () {
        parent.location.reload();
    }
};