/**
 * @Author: Chanyin尹强
 * @Email: yinqiang@datcent.com
 * @CreateDate: 2018/11/20 15:18
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO simpleTable数据表组件
 **/

var  initSimpleTabl = {

    _option: {},
    _params: {},
    _config: {},
    set: function(id) {
        if($.common.getLength(initSimpleTabl._config) > 1) {
            var tableId = $.common.isEmpty(id) ? $(event.currentTarget).parents(".bootstrap-table").find(".table").attr("id") : id;
            if ($.common.isNotEmpty(tableId)) {
                initSimpleTabl._option = initSimpleTabl.get(tableId);
            }
        }
    },
    // 获取实例配置
    get: function(id) {
        return table.config[id];
    },
    init:function(options){
        initSimpleTabl._option = options;
        initSimpleTabl._params = toolUtil.isEmpty(options.queryParams) ? initSimpleTabl.queryParams : options.queryParams;
        _sortOrder = toolUtil.isEmpty(options.sortOrder) ? "asc" : options.sortOrder;
        _sortName = toolUtil.isEmpty(options.sortName) ? "" : options.sortName;
        _showFooter = toolUtil.isEmpty(options.showFooter) ? initSimpleTabl.showFooter : options.showFooter;
        $('#bootstrap-table').bootstrapTable({
            url: options.url,                                   // 请求后台的URL（*）
            contentType: "application/x-www-form-urlencoded",   // 编码类型
            method: 'post',                                     // 请求方式（*）
            cache: false,                                       // 是否使用缓存
            sortable: true,                                     // 是否启用排序
            sortStable: true,                                   // 设置为 true 将获得稳定的排序
            sortName: _sortName,                                // 排序列名称
            sortOrder: _sortOrder,                              // 排序方式  asc 或者 desc
            pagination: toolUtil.visible(options.pagination),   // 是否显示分页（*）
            pageNumber: 1,                                      // 初始化加载第一页，默认第一页
            pageSize: options.pageSize || 10,                                       // 每页的记录行数（*）
            pageList: [10, 25, 50],                             // 可供选择的每页的行数（*）
            iconSize: 'outline',                                // 图标大小：undefined默认的按钮尺寸 xs超小按钮sm小按钮lg大按钮
            toolbar: '#toolbar',                                // 指定工作栏
            sidePagination: "server",                           // 启用服务端分页
            search: $.common.visible(options.search),          // 是否显示搜索框功能
            showSearch: $.common.visible(options.showSearch),   // 是否显示检索信息
            showRefresh: $.common.visible(options.showRefresh), // 是否显示刷新按钮
            showColumns: $.common.visible(options.showColumns), // 是否显示隐藏某列下拉框
            showToggle: $.common.visible(options.showToggle),   // 是否显示详细视图和列表视图的切换按钮
            showExport: $.common.visible(options.showExport),   // 是否支持导出文件
            queryParams: initSimpleTabl._params,                       // 传递参数（*）
            columns: options.columns,                           // 显示列信息（*）
            responseHandler: initSimpleTabl.responseHandler,            // 回调函数
            showFooter: _showFooter,
        });


    },

    // 查询条件
    queryParams: function (params) {
        return {
            // 传递参数查询参数
            pageSize: params.limit,
            pageNum: params.offset / params.limit + 1,
            searchValue: params.search,
            orderByColumn: params.sort,
            isAsc: params.order
        };
    },

    // 请求获取数据后处理回调函数
    responseHandler: function (res) {
        if (res.code == 0) {
            return {rows: res.rows, total: res.total};
        } else {
            $.modal.alertWarning(res.msg);
            return {rows: [], total: 0};
        }
    },

    // 搜索
    search: function (formId) {
        var currentId = toolUtil.isEmpty(formId) ? $('form').attr('id') : formId;
        var params = $("#bootstrap-table").bootstrapTable('getOptions');
        params.queryParams = function (params) {
            var search = {};
            $.each($("#" + currentId).serializeArray(), function (i, field) {
                search[field.name] = field.value;
            });
            search.pageSize = params.limit;
            search.pageNum = params.offset / params.limit + 1;
            search.searchValue = params.search;
            search.orderByColumn = params.sort;
            search.isAsc = params.order;
            return search;
        }
        $("#bootstrap-table").bootstrapTable('refresh', params);
    },

    // 导出数据
    exportExcel: function (formId) {
        var currentId = toolUtil.isEmpty(formId) ? $('form').attr('id') : formId;
        modalComponent.loading("正在导出数据，请稍后...");
        $.post( initSimpleTabl._option.exportUrl, $("#" + currentId).serializeArray(), function (result) {
            if (result.code == statusCode.web_status.SUCCESS) {
                window.location.href = ctx + "common/download?fileName=" + result.msg + "&delete=" + true;
            } else {
               modalComponent.alertError(result.msg);
            }
            modalComponent.closeLoading();
        });
    },
    // 下载模板
    importTemplate: function() {
        initSimpleTabl.set();
        $.get(initSimpleTabl._option.importTemplateUrl, function(result) {
            if (result.code == web_status.SUCCESS) {
                window.location.href = ctx + "common/download?fileName=" + encodeURI(result.msg) + "&delete=" + true;
            } else if (result.code == web_status.WARNING) {
                $.modal.alertWarning(result.msg)
            } else {
                $.modal.alertError(result.msg);
            }
        });
    },
    // 导入数据
    importExcel: function(formId) {
        initSimpleTabl.set();
        var currentId = $.common.isEmpty(formId) ? 'importTpl' : formId;
        layer.open({
            type: 1,
            area: ['400px', '230px'],
            fix: false,
            //不固定
            maxmin: true,
            shade: 0.3,
            title: '导入' + initSimpleTabl._option.modalName + '数据',
            content: $('#' + currentId).html(),
            btn: ['<i class="fa fa-check"></i> 导入', '<i class="fa fa-remove"></i> 取消'],
            // 弹层外区域关闭
            shadeClose: true,
            btn1: function(index, layero){
                var file = layero.find('#file').val();
                if (file == '' || (!$.common.endWith(file, '.xls') && !$.common.endWith(file, '.xlsx'))){
                    $.modal.msgWarning("请选择后缀为 “xls”或“xlsx”的文件。");
                    return false;
                }
                var index = layer.load(2, {shade: false});
                $.modal.disable();
                var formData = new FormData();
                formData.append("file", $('#file')[0].files[0]);
                formData.append("updateSupport", $("input[name='updateSupport']").is(':checked'));
                $.ajax({
                    url: table.options.importUrl,
                    data: formData,
                    cache: false,
                    contentType: false,
                    processData: false,
                    type: 'POST',
                    success: function (result) {
                        if (result.code == web_status.SUCCESS) {
                            $.modal.closeAll();
                            $.modal.alertSuccess(result.msg);
                            $.table.refresh();
                        } else if (result.code == web_status.WARNING) {
                            layer.close(index);
                            $.modal.enable();
                            $.modal.alertWarning(result.msg)
                        } else {
                            layer.close(index);
                            $.modal.enable();
                            $.modal.alertError(result.msg);
                        }
                    }
                });
            }
        });
    },
    // 刷新
    refresh: function () {
        $("#bootstrap-table").bootstrapTable('refresh', {
           // url: initSimpleTabl._option.url,
            silent: true
        });
    },
    // 查询选中列值
    selectColumns: function (column) {
        return $.map($('#bootstrap-table').bootstrapTable('getSelections'), function (row) {
            return row[column];
        });
    },
    // 查询选中首列值
    selectFirstColumns: function () {
        return $.map($('#bootstrap-table').bootstrapTable('getSelections'), function (row) {
            return row[initSimpleTabl._option.columns[1].field];
        });
    },
    // 回显数据字典
    selectDictLabel: function (datas, value) {
        var actions = [];
        $.each(datas, function (index, dict) {
            if (dict.dictValue == value) {
                actions.push("<span class='badge badge-" + dict.listClass + "'>" + dict.dictLabel + "</span>");
                return false;
            }
        });
        return actions.join('');
    }

};
