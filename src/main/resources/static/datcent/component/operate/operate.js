/**
*@Author: 张梦圆
*@Email: zhangmengyuan@datcent.com
*@CreateDate: 2018/11/22 16:55
*@Copyright: © 2018 德讯科技 版权所有
*@Description: TODO 操作组件封装
*/

var operateComponent = {
    // 提交数据
    submit: function (url, type, dataType, data) {
       modalComponent.loading("正在处理中，请稍后...");
        var config = {
            url: url,
            type: type,
            dataType: dataType,
            data: data,
            success: function (result) {
                operateComponent.ajaxSuccess(result);
            }
        };
        $.ajax(config)
    },
    // post请求传输
    post: function (url, data) {
        url = ctx+url;
       operateComponent.submit(url, "post", "json", data);
    },
    // 详细信息
    detail: function(url,id,modalName) {
        try {
            if(toolUtil.isEmpty(url)){
                throw Exception;
            }
            if(toolUtil.isEmpty(id)){
                var row = initSimpleTabl.selectFirstColumns();
                var _url =ctx+ url.replace("{id}",row);
            }else{
                var _url = ctx + url.replace("{id}",id);
            }
            layer.open({
                type: 2,
                area: ['800px', ($(window).height() - 50) + 'px'],
                fix: false,
                //不固定
                maxmin: true,
                shade: 0.3,
                title: modalName + "详细",
                content: _url,
                /*btn: ['<i class="fa fa-close"></i> 关闭'],*/
                // 弹层外区域关闭
                shadeClose: true,
                cancel: function(index) {
                    return true;
                }
            });
        }
        catch (e) {
            modalComponent.msgError("参数有误！");
        }
    },
    // 详细信息 全屏
    detailFull: function (url,id,modalName) {
        try {
            if(toolUtil.isEmpty(url)){
                throw  Exception;
            }
            if (toolUtil.isNotEmpty(id)) {
                var _url = ctx + url.replace("{id}", id);
            } else {
                var row = initSimpleTabl.selectFirstColumns();
                var _url = ctx + url.replace("{id}", row);
            }
           modalComponent.openFull(modalName + "详细", _url);
        }
        catch (e) {
            modalComponent.msgError("参数有误！");
        }
    },
    // 删除信息
    remove: function (id,url,modalName) {

        try {
            if(toolUtil.isEmpty(url)){
                throw Exception;
            }
            if(toolUtil.isEmpty(id)){
                throw Exception;
            }
            modalComponent.confirm("确定删除该条" + modalName + "信息吗？", function () {
                var data = {"ids": id};
                operateComponent.submit(ctx+url, "post", "json", data);
            });
        }
        catch (e) {
            modalComponent.msgError("参数有误！");
        }
    },

//转入
    change: function (id,url,modalName) {

        try {
            if(toolUtil.isEmpty(url)){
                throw Exception;
            }
            if(toolUtil.isEmpty(id)){
                throw Exception;
            }
            modalComponent.confirm("确定转入该条" + modalName + "信息吗？", function () {
                var data = {"dubvioId": id};
                operateComponent.submit(ctx+url, "post", "json", data);
            });
        }
        catch (e) {
            modalComponent.msgError("参数有误！");
        }
    },




    changeAdress: function (id,url,modalName) {

        try {
            if(toolUtil.isEmpty(url)){
                throw Exception;
            }
            if(toolUtil.isEmpty(id)){
                throw Exception;
            }
            modalComponent.confirm("确定共享这条" + modalName + "信息吗？", function () {
                var data = {"id": id};
                operateComponent.submit(ctx+url, "post", "json", data);
            });
        }
        catch (e) {
            modalComponent.msgError("参数有误！");
        }
    },

    // 批量删除信息
    removeAll: function (url) {
        try {
            if(toolUtil.isEmpty(url)){
                throw Exception;
            }
            var rows = initSimpleTabl.selectFirstColumns();
            if (rows.length == 0) {
                modalComponent.alertWarning("请至少选择一条记录");
                return;
            }
            modalComponent.confirm("确认要删除选中的" + rows.length + "条数据吗?", function () {
                var data = {"ids": rows.join()};
                operateComponent.submit(ctx+url, "post", "json", data);
            });
        }
        catch (e) {
            modalComponent.msgError("参数有误！");
        }
    },
    // 批量下载
    downloadAll: function (url) {
        try {
            if(toolUtil.isEmpty(url)){
                throw Exception;
            }
            var rows = initSimpleTabl.selectFirstColumns();
            if (rows.length == 0) {
                modalComponent.alertWarning("请至少选择一条记录");
                return;
            }
            modalComponent.confirm("确认要下载选中的" + rows.length + "条数据吗?", function () {
                var request = new XMLHttpRequest();
                var data = {"ids": rows.join()};


                operateComponent.submit(ctx+url, "post", "json", data);
            });
        }
        catch (e) {
            modalComponent.msgError("参数有误！");
        }
    },
    // 添加信息
    add: function (url,modalName) {
        try {
            if(toolUtil.isEmpty(url)){
                throw Exception;
            }
            modalComponent.open("添加" + modalName, ctx+url);
        }
        catch (e) {
            modalComponent.msgError("参数有误！");
        }
    },
    // 添加信息 全屏
    addFull: function (url,modalName) {
        try {
            if(toolUtil.isEmpty(url)){
                throw Exception;
            }
            modalComponent.openFull("添加" + modalName, ctx+url);
        }
        catch (e) {
            modalComponent.msgError("参数有误！");
        }
    },
    // 修改信息
    edit: function(id,url,modalName) {
        try {
            if(toolUtil.isEmpty(url)){
                throw Exception;
            }
            if (toolUtil.isNotEmpty(id)) {
               var _url = ctx + url.replace("{id}", id);
            } else {
                var _id = initSimpleTabl.selectFirstColumns();
                if (_id.length == 0) {
                    modalComponent.alertWarning("请至少选择一条记录");
                    return;
                }
                var _url = ctx + url.replace("{id}", _id);
            }
            modalComponent.open("修改" + modalName, _url);
        }
        catch (e) {
            modalComponent.msgError("参数有误！");
        }
    },
    // 修改信息 全屏
    editFull: function(id,url,modalName) {
        try {
            if(toolUtil.isEmpty(url)){
                throw Exception;
            }
            if(toolUtil.isNotEmpty(id)){
                var _url = ctx + url.replace("{id}",id);
            }else{
                var _id=initSimpleTabl.selectFirstColumns();
                if(_id.length == 0){
                    modalComponent.alertWarning("请至少选择一条记录");
                    return;
                }
                var _url = ctx + url.replace("{id}",_id);
            }
            modalComponent.openFull("修改" + modalName, _url);
        }
        catch (e) {
            modalComponent.msgError("参数有误！");
        }
    },
    // 查看信息 全屏
    checkFull: function (id,url,modalName) {
        try {
            if(toolUtil.isEmpty(url)){
                throw Exception;
            }
            if(toolUtil.isNotEmpty(id)){
                var _url = ctx + url.replace("{id}",id);
            }else{
                var _id = initSimpleTabl.selectFirstColumns();
                if(_id.length == 0){
                    modalComponent.alertWarning("请至少选择一条记录");
                    return;
                }
                var _url = ctx + url.replace("{id}",_id);
            }
            $.modal.openFull("查看" + modalName, _url);
        }
        catch (e) {
            modalComponent.msgError("参数有误！");
        }
    },
    // 查看信息
    check: function (id,url,modalName) {
        try {
            if(toolUtil.isEmpty(url)){
                throw Exception;
            }
            if(toolUtil.isNotEmpty(id)){
                var _url = ctx + url.replace("{id}",id);
            }else{
                var _id = initSimpleTabl.selectFirstColumns();
                if(_id.length == 0){
                    modalComponent.alertWarning("请至少选择一条记录");
                    return;
                }
                var _url = ctx + url.replace("{id}",_id);
            }
            $.modal.open("查看" + modalName, _url);
        }
        catch (e) {
            modalComponent.msgError("参数有误！");
        }
    },
    // 保存信息
    save: function (url, data) {
       modalComponent.loading("正在处理中，请稍后...");
        var config = {
            url: url,
            type: "post",
            dataType: "json",
            data: data,
            success: function (result) {
               operateComponent.saveSuccess(result);
            }
        };
        $.ajax(config)
    },
    // 保存结果弹出msg刷新table表格
    ajaxSuccess: function (result) {
        if (result.code == statusCode.web_status.SUCCESS) {
           modalComponent.msgSuccess(result.msg);
           initSimpleTabl.refresh();
        } else {
            modalComponent.alertError(result.msg);
        }
        modalComponent.closeLoading();
    },
    // 保存结果提示msg
    saveSuccess: function (result) {
        if (result.code == web_status.SUCCESS) {
            if (window.parent.$("#bootstrap-table").length > 0) {
                $.modal.close();
                window.parent.$.modal.msgSuccess(result.msg);
                window.parent.$.table.refresh();
            } else if (window.parent.$("#bootstrap-tree-table").length > 0) {
                $.modal.close();
                window.parent.$.modal.msgSuccess(result.msg);
                window.parent.$.treeTable.refresh();
            } else {
                $.modal.msgReload("保存成功,正在刷新数据请稍后……", modal_status.SUCCESS);
            }
        } else {
            $.modal.alertError(result.msg);
        }
        modalComponent.closeLoading();
    },
    //ajax异步删除
    ajaxRemove: function (id,url,modalName) {
        try {
            if(toolUtil.isEmpty(url)){
                throw Exception;
            }
            if(toolUtil.isEmpty(id)){
                throw Exception;
            }
            modalComponent.confirm("确定删除该条" + modalName + "信息吗？", function () {
                var data = {"ids": id};
                operateComponent.ajaxSubmit(ctx+url, "post", "json", data);
            });
        }
        catch (e) {
            modalComponent.msgError("参数有误！");
        }
    },
    // ajax提交数据
    ajaxSubmit: function (url, type, dataType, data) {
        modalComponent.loading("正在处理中，请稍后...");
        var config = {
            url: url,
            type: type,
            dataType: dataType,
            data: data,
            success: function (result) {
                operateComponent.success(result);
            }
        };
        $.ajax(config)
    },
    //ajax成功 reload当前页面
    success: function (result) {
        if (result.code == statusCode.web_status.SUCCESS) {
            modalComponent.msgSuccess(result.msg);
            location.reload();
        } else {
            modalComponent.alertError(result.msg);
        }
        modalComponent.closeLoading();
    },
};