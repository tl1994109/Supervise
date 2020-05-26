/**
*@Author: 张梦圆
*@Email: zhangmengyuan@datcent.com
*@CreateDate: 2018/11/23 17:41
*@Copyright: © 2018 德讯科技 版权所有
*@Description: TODO 树插件封装处理
*/

var treeComponent = {

    _tree : {},
    // 树插件封装处理
    tree: {
        _option: {},
        _lastValue: {},
        // 初始化树结构
        init: function (options) {
           treeComponent.tree._option = options;
            // 属性ID
            var _id = toolUtil.isEmpty(options.id) ? "tree" : options.id;
            // 展开等级节点
            var _expandLevel = toolUtil.isEmpty(options.expandLevel) ? 0 : options.expandLevel;
            // 树结构初始化加载
            var setting = {
                check: options.check,
                view: {selectedMulti: false, nameIsHTML: true},
                data: {key: {title: "title"}, simpleData: {enable: true}},
                callback: {onClick: options.onClick}
            };
            $.get(options.url, function (data) {
                var treeName = $("#treeName").val();
                var treeId = $("#treeId").val();
                tree = $.fn.zTree.init($("#" + _id), setting, data);
                treeComponent._tree = tree;
                // 展开第一级节点
                var nodes = tree.getNodesByParam("level", 0);
                for (var i = 0; i < nodes.length; i++) {
                    if (_expandLevel > 0) {
                        tree.expandNode(nodes[i], true, false, false);
                    }
                    treeComponent.tree.selectByIdName(treeId, treeName, nodes[i]);
                }
                // 展开第二级节点
                nodes = tree.getNodesByParam("level", 1);
                for (var i = 0; i < nodes.length; i++) {
                    if (_expandLevel > 1) {
                        tree.expandNode(nodes[i], true, false, false);
                    }
                    treeComponent.tree.selectByIdName(treeId, treeName, nodes[i]);
                }
                // 展开第三级节点
                nodes = tree.getNodesByParam("level", 2);
                for (var i = 0; i < nodes.length; i++) {
                    if (_expandLevel > 2) {
                        tree.expandNode(nodes[i], true, false, false);
                    }
                    treeComponent.tree.selectByIdName(treeId, treeName, nodes[i]);
                }
            }, null, null, "正在加载，请稍后...");
        },
        // 搜索节点
        searchNode: function () {
            // 取得输入的关键字的值
            var value = toolUtil.trim($("#keyword").val());
            if (treeComponent.tree._lastValue === value) {
                return;
            }
            // 保存最后一次搜索名称
           treeComponent.tree._lastValue = value;
            var nodes = treeComponent._tree.getNodes();
            // 如果要查空字串，就退出不查了。
            if (value == "") {
                treeComponent.tree.showAllNode(nodes);
                return;
            }
            treeComponent.tree.hideAllNode(nodes);
            // 根据搜索值模糊匹配
            treeComponent.tree.updateNodes(treeComponent._tree.getNodesByParamFuzzy("name", value));
        },
        // 根据Id和Name选中指定节点
        selectByIdName: function (treeId, treeName, node) {
            if (toolUtil.isNotEmpty(treeName) && toolUtil.isNotEmpty(treeId)) {
                if (treeId == node.id && treeName == node.name) {
                    treeComponent._tree.selectNode(node, true);
                }
            }
        },
        // 显示所有节点
        showAllNode: function (nodes) {
            nodes = treeComponent._tree.transformToArray(nodes);
            for (var i = nodes.length - 1; i >= 0; i--) {
                if (nodes[i].getParentNode() != null) {
                    treeComponent._tree.expandNode(nodes[i], true, false, false, false);
                } else {
                    treeComponent._tree.expandNode(nodes[i], true, true, false, false);
                }
                treeComponent._tree.showNode(nodes[i]);
                treeComponent.tree.showAllNode(nodes[i].children);
            }
        },
        // 隐藏所有节点
        hideAllNode: function (nodes) {
            var tree = $.fn.zTree.getZTreeObj("tree");
            var nodes = treeComponent._tree.transformToArray(nodes);
            for (var i = nodes.length - 1; i >= 0; i--) {
                treeComponent._tree.hideNode(nodes[i]);
            }
        },
        // 显示所有父节点
        showParent: function (treeNode) {
            var parentNode;
            while ((parentNode = treeNode.getParentNode()) != null) {
                treeComponent._tree.showNode(parentNode);
                treeComponent._tree.expandNode(parentNode, true, false, false);
                treeNode = parentNode;
            }
        },
        // 显示所有孩子节点
        showChildren: function (treeNode) {
            if (treeNode.isParent) {
                for (var idx in treeNode.children) {
                    var node = treeNode.children[idx];
                    treeComponent._tree.showNode(node);
                    treeComponent.tree.showChildren(node);
                }
            }
        },
        // 更新节点状态
        updateNodes: function (nodeList) {
            treeComponent._tree.showNodes(nodeList);
            for (var i = 0, l = nodeList.length; i < l; i++) {
                var treeNode = nodeList[i];
                treeComponent.tree.showChildren(treeNode);
                treeComponent.tree.showParent(treeNode)
            }
        },
        // 获取当前被勾选集合
        getCheckedNodes: function (column) {
            var _column = toolUtil.isEmpty(column) ? "id" : column;
            var nodes = treeComponent._tree.getCheckedNodes(true);
            return $.map(nodes, function (row) {
                return row[_column];
            }).join();
        },
        // 不允许根父节点选择
        notAllowParents: function (_tree) {
            var nodes = _tree.getSelectedNodes();
            for (var i = 0; i < nodes.length; i++) {
                if (nodes[i].level == 0) {
                   modalComponent.msgError("不能选择根节点（" + nodes[i].name + "）");
                    return false;
                }
                if (nodes[i].isParent) {
                    modalComponent.msgError("不能选择父节点（" + nodes[i].name + "）");
                    return false;
                }
            }
            return true;
        },
        // 隐藏/显示搜索栏
        toggleSearch: function () {
            $('#search').slideToggle(200);
            $('#btnShow').toggle();
            $('#btnHide').toggle();
            $('#keyword').focus();
        },
        // 折叠
        collapse: function () {
           treeComponent._tree.expandAll(false);
        },
        // 展开
        expand: function () {
            treeComponent._tree.expandAll(true);
        }
    },
};