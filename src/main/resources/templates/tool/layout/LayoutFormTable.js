/*
 * Copyright 2016-2026 Bsoft File Name: LayoutFormTable.js Author: Caob Version:
 * 1.0 Date: 16/7/6 Description:
 * 
 * 主题表单
 * 
 */
$package('hcms.app.datamodel.script');
$import('app.simple.TableForm');
$import('app.main.rmi.JsonRequestAsync');
$import('app.plugin.htable.handsontable_full');
$import('app.main.rmi.JsonRequestSync');
$styleSheet('app.plugin.htable.handsontable_full');

hcms.app.datamodel.script.LayoutFormTable = function(cfg) {
	cfg.colCount = 4;
	hcms.app.datamodel.script.LayoutFormTable.superclass.constructor.apply(
			this, [cfg]);
	this.asyncReq = app.main.rmi.JsonRequestAsync;
};
Ext.extend(hcms.app.datamodel.script.LayoutFormTable, app.simple.TableForm, {
	init : function() {
		hcms.app.datamodel.script.LayoutFormTable.superclass.init.apply(this,
				[]);
		// this.tableId = Ext.id();
		this.tableId = 'hot';
		this.on({
					scope : this,
					outwinshow : this.onOutWinShow
				});
	},
	initPanel : function(sc) {
		var cfg = {
			html : '<div><div id="' + this.tableId + '"></div></div>'
		};
		this.initBars(cfg);
		var panel = new Ext.Panel(cfg);
		this.panel = panel;
		return panel;
	},
	/**
	 * 当这个 window 已经显示之后，对这个内容进行数据初始化，不能在 afterrender 之后进行初始化，那样会渲染存在问题
	 */
	onOutWinShow : function(win) {
		var layoutId;
		var height;
		this.cssSet = new Ext.util.MixedCollection();
		if (win.option == 'create') {
			layoutId = null;
			height = 4;
		} else if (win.option == 'update') {
			var record = win.recordData;
			layoutId = record.recordId;
			height = record.height;
		}
		var container = document.getElementById(this.tableId);
		var data = this.genTableData(height, 12);
		var mergeCells = this.getMergeCells(layoutId);
		var baseConfig = this.getHtableCfg();
		var cfg = {
			data : data,
			mergeCells : mergeCells
		};
		Ext.apply(cfg, baseConfig);
		this.hot = new Handsontable(container, cfg);
		this.cssSet.eachKey(function(key, item) {
					var row = key.charAt(0);
					var col = key.charAt(1);
					this.hot.setDataAtCell(Number(row), Number(col), item);
				}, this)
	},
	getHtableCfg : function() {
		this.layoutBasecfg = this.layoutBasecfg || {
			outsideClickDeselects : false,
			readOnly : true,
			rowHeaders : true,
			colHeaders : ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10',
					'11', '12'],
			autoWrapRow : true,
			rowHeights : 80,
			// stretchH : 'all'
			colWidths : [50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50]
		};
		return this.layoutBasecfg;
	},
	/**
	 * 获得合并的单元格数据
	 */
	getMergeCells : function(recordId) {
		var cells = [];
		if (recordId == null) {
			this.layoutMergeCells = cells;
			return cells;
		}
        var res = app.main.rmi.JsonRequestSync({
            serviceId: 'hcms.layoutService',
            action: 'getMergeCells',
            layoutId: recordId
        }, {}, {}, this).json.body;
		for (var i = 0; i < res.length; i++) {
			var cell = new Object();
			cell.col = res[i].HORPOS;
			cell.row = res[i].VERPOS;
			cell.colspan = res[i].COLSPAN;
			cell.rowspan = res[i].ROWSPAN;
			cells[i] = cell;
			var key = cell.row + '' + cell.col + '' + cell.rowspan + ''
					+ cell.colspan;
			this.cssSet.add(key, res[i].CSS);
		}
		this.layoutMergeCells = cells;
		return cells;
	},
	reRenderHotable : function() {
		var container = document.getElementById(this.tableId);
		var data = this.layoutData;
		var mergeCells = this.layoutMergeCells;
		var baseConfig = this.getHtableCfg();
		var cfg = {
			data : data,
			mergeCells : mergeCells,
			colHeaders : ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10',
					'11', '12']
		};
		Ext.apply(cfg, baseConfig);
		this.hot = new Handsontable(container, cfg);
		this.cssSet.eachKey(function(key, item) {
					var row = key.charAt(0);
					var col = key.charAt(1);
					this.hot.setDataAtCell(Number(row), Number(col), item);
				}, this)
	},
	/**
	 * 生成固定列和行的数据
	 * 
	 * @param row
	 *            列
	 * @param height
	 *            高
	 * @param val
	 *            默认值
	 */
	genTableData : function(row, height, val) {
		if (typeof row != 'number' || !row) {
			row = 3;
		}
		if (typeof height != 'number' || !height) {
			height = 12;
		}
		var outData = [];
		for (var i = 0; i < row; i++) {
			var inData = [];
			for (var j = 0; j < height; j++) {
				inData.push(val);
			}
			outData.push(inData);
		}
		this.layoutData = outData;
		return outData;
	},
	/**
	 * 生成一行数据
	 * 
	 * @param val
	 */
	createOnRow : function(len, val) {
		if (typeof len != 'number' || !len) {
			len = 12;
		}
		var data = [];
		for (var j = 0; j < len; j++) {
			data.push(val);
		}
		return data;
	},
	/**
	 * 增加一行
	 */
	doAddrow : function() {
		var hot = this.hot;
		if (!hot) {
			return;
		}
		var data = this.createOnRow();
		this.layoutData.push(data);
		hot.loadData(this.layoutData);
	},
	/**
	 * 删除一行
	 */
	doDelrow : function() {
		var hot = this.hot;
		if (!hot) {
			return;
		}
		var aiSel = hot.getSelected();
		if (!aiSel) {
			return;
		}
		var rowStart = aiSel[0];
		var rowEnd = aiSel[2];
		var howmany = Math.abs(rowStart - rowEnd) + 1;
		// 需要删除的是从start到end的项目
		if (rowStart > rowEnd) {// 交换大小
			var temp = rowEnd;
			rowEnd = rowStart;
			rowStart = temp;
		}
		this.layoutData.splice(rowStart, howmany);
		var layoutMergeCells = this.layoutMergeCells;
		var cellToRemove = new Array();
		for (var i = 0; i < layoutMergeCells.length; i++) {
			var cell = layoutMergeCells[i];
			var start = cell.row;
			var end = cell.row + cell.rowspan - 1;
			if (start >= aiSel[0] && end <= aiSel[2]) {
				cellToRemove.push(cell);
			} else if (start >= aiSel[0] && start <= aiSel[2]) {
				cell.row = aiSel[2] + 1;
				cell.rowspan = cell.rowspan - (aiSel[2] - start + 1);
				if (cell.rowspan * cell.colspan <= 1) {
					cellToRemove.push(cell);
				}
			} else if (end >= aiSel[0] && end <= aiSel[2]) {
				cell.rowspan = cell.rowspan - (aiSel[0] - start + 1);
				if (cell.rowspan * cell.colspan <= 1) {
					cellToRemove.push(cell);
				}
			} else if (end >= aiSel[2] && start <= aiSel[0]) {
				cell.rowspan = cell.rowspan - howmany;
			}
		}
		for (var i = 0; i < cellToRemove.length; i++) {
			cell = cellToRemove[i];
			this
					.removeMergeCell(cell.row, cell.col, cell.rowspan,
							cell.colspan);
		}
		// hot.loadData(this.layoutData);
		this.reRenderHotable();

	},
	/**
	 * 合并选中列
	 */
	doMerge : function() {
		var hot = this.hot;
		if (!hot) {
			return;
		}
		var aiSel = hot.getSelected();
		if (!aiSel) {
			return;
		}
		var rc = this.getRC(aiSel);
		if (rc[2] === 1 && rc[3] === 1) { // 单个单元格跳出
			return;
		}
		this.addMergeCell(rc[0], rc[1], rc[2], rc[3], rc[4], rc[5]);
	},
	getRC : function(aiSel) {
		var rowStart = aiSel[0], colStart = aiSel[1], rowEnd = aiSel[2], colEnd = aiSel[3], rowSpan = Math
				.abs(rowStart - rowEnd)
				+ 1, colSpan = Math.abs(colStart - colEnd) + 1;
		if (rowStart > rowEnd) {
			var temp = rowStart;
			rowStart = rowEnd;
			rowEnd = temp;
		}
		if (colStart > colEnd) {
			var temp = colStart;
			colStart = colEnd;
			colEnd = temp;
		}
		return [rowStart, colStart, rowSpan, colSpan, rowEnd, colEnd];
	},
	/**
	 * 取消选中列合并
	 */
	doUnmerge : function() {
		var hot = this.hot;
		if (!hot)
			return;
		}
		var aiSel = hot.getSelected();
		if (!aiSel) {
			return;
		}
		var rc = this.getRC(aiSel);
		if (rc[2] === 1 && rc[3] === 1) { // 单个单元格跳出
			return;
		}
		this.removeMergeCell(rc[0], rc[1], rc[2], rc[3]);
	},
	addMergeCell : function(row, col, rowspan, colspan, rowEnd, colEnd) {
		var cells = this.layoutMergeCells;
		if (!cells) {
			cells = this.layoutMergeCells = [];
		}

		if (rowspan === 1 && colspan === 1) { // 单个单元格跳出
			return;
		}
		var len = cells.length;
		for (var i = 0; i < len; i++) {
			var oCell = cells[i];
			// 如果已经是合并过的数据，那么不需要再合并
			if (oCell.row === row && oCell.col === col
					&& oCell.rowspan === rowspan && oCell.rowspan === rowspan) {
				MyMessageTip.msg('提示', '不能包含已经合并了的单元格', true, 2);
				return;
			}
			var mergeCellRowEnd = oCell.row + oCell.rowspan - 1;
			var mergeCellColEnd = oCell.col + oCell.colspan - 1;
			// 如果当前的合并包含了前面的合并，那么不能进行合并，给出提示
			if (row <= oCell.row && rowEnd >= mergeCellRowEnd
					&& col <= oCell.col && colEnd >= mergeCellColEnd) {
				MyMessageTip.msg('提示', '不能合并已经合并了的单元格', true, 2);
				return;
			}

		}
		cells.push({
					row : row,
					col : col,
					rowspan : rowspan,
					colspan : colspan
				});
		this.reRenderHotable();
	},
	removeMergeCell : function(row, col, rowspan, colspan) {
		var cells = this.layoutMergeCells;
		if (!cells) {
			return;
		}
		var len = cells.length;
		for (var i = 0; i < len; i++) {
			var oCell = cells[i];
			if (oCell.row === row && oCell.col === col
					&& oCell.rowspan === rowspan && oCell.rowspan === rowspan) {
				cells.splice(i, 1);
				this.reRenderHotable();
				return;
			}
		}
	},
	getData : function() {
		var height = this.layoutData.length;
		return {
			merge : this.layoutMergeCells,
			height : height,
			cssSet : this.cssSet
		};
	},
	/**
	 * 为选中的单元格设置css
	 */
	doAddCSS : function() {
		var hot = this.hot;
		if (!hot) {
			return;
		}
		var aiSel = hot.getSelected();
		if (!aiSel) {
			return;
		}
		var rc = this.getRC(aiSel);
		if (rc[2] === 1 && rc[3] === 1) { // 单个单元格跳出
			return;
		}
		var key = rc[0] + '' + rc[1] + '' + rc[2] + '' + rc[3];
		var initData = function() {
			var cssTextArea = cssPanel.findByType('textarea', false)[0];
			cssTextArea.setValue(this.cssSet.get(key));
		};
		// this.removeMergeCell(rc[0], rc[1], rc[2], rc[3]);
		var cssPanel = new Ext.Panel({
					layout : 'fit',
					anchor : '100% 100%',
					border : false,
					items : [{
								xtype : 'textarea',
								layout : 'fit',
								autoScroll : true
							}],
					listeners : {
						scope : this,
						afterrender : initData
					}
				});
		var confirm = function() {
			var cssTextArea = cssPanel.findByType('textarea', false)[0];
			var text = cssTextArea.getValue();
			this.cssSet.add(key, text);
			this.reRenderHotable();
			cssWin.close();
		};
		var cssWin = new Ext.Window({
					title : 'CSS配置',
					width : 600,
					height : 450,
					modal : true,
					closeAction : 'close',
					layout : 'anchor',
					buttonAlign : 'center',
					items : [cssPanel],
					buttons : [{
								text : '确定',
								scope : this,
								handler : confirm
							}, {
								text : '关闭',
								handler : function() {
									cssWin.close();
								}
							}]
				});
		cssWin.show();

	}
});
