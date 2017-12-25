Ext.define('DJ.order.Deliver.DeliverorderexceptionList', {
	extend : 'Ext.c.GridPanel',
	title : "异常跟踪",
	
//	requires: ['DJ.order.Deliver.DeliverorderexceptionEdit'],
	
	id : 'DJ.order.Deliver.DeliverorderexceptionList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'selectDeliverorderexceptionList.do',
	Delurl : "",
	EditUI : "DJ.order.Deliver.DeliverorderexceptionEdit",
	exporturl : "",// 导出为EXCEL方法
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		// alert("DeliverList");
		this._operateButtonsView(true, [1, 2, 4]);
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件

	},
	Action_AfterAddButtonClick : function(EditUI) {
		
		
		
	},
	Action_BeforeEditButtonClick : function(EditUI) {
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
		
		
	},
	Action_BeforeDelButtonClick : function(me, record) {
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	custbar : [],
	fields : [{
		name : 'fid'
	}, {
		name : 'fdeliverorderId'
	}, {
		name : 'fremark'
	}, {
		name : 'fnumber'
	}],
	columns : [{
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true

	}, {
		'header' : 'fdeliverorderId',
		'dataIndex' : 'fdeliverorderId',
		hidden : true,
		hideable : false,
		sortable : true

	}, {
		'header' : '运货单号',
		width : 140,
		'dataIndex' : 'fnumber',
		sortable : true
	}, {
		'header' : '备注',
		width : 90,
		'dataIndex' : 'fremark',
		sortable : true
	}],
	selModel : Ext.create('Ext.selection.CheckboxModel'),

	/**
	 * 主要调用这个方法
	 * 
	 * @param {}
	 *            show，bool，
	 * @param {}
	 *            array，元素为button ID或为数字索引（从0开始）
	 */
	_operateButtonsView : function(show, array) {

		if (Ext.typeOf(array[0]) == "number") {
			array = this._translateNumToDataIndex(array);
		}

		if (show) {
			this._showButtons(array);
		} else {
			this._hideButtons(array);
		}

	},
	_translateNumToDataIndex : function(array) {
		var arrayt = [];

		var arrayAll = this._getToolsButtonIDs();

		Ext.each(arrayAll, function(item, index, all) {
			if (Ext.Array.contains(array, index)) {
				arrayt.push(arrayAll[index]);
			}
		});

		return arrayt;
	},

	_hideButtons : function(array) {
		var t = array;
		Ext.each(t, function(item, index, all) {
			Ext.getCmp(item).hide();
		});
	},

	_showButtons : function(array) {
		var defaultButtons = this._getToolsButtonIDs();

		var tArray = Ext.Array.difference(defaultButtons, array);

		this._hideButtons(tArray);
	},

	_getToolsButtonIDs : function() {
		var defaultButtons = [];

		var t = this.dockedItems.items[2].items.items;

		Ext.each(t, function(item, index, all) {

			defaultButtons.push(item.id);

		});
		return defaultButtons;
	}
})