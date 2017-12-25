Ext.define('DJ.System.supplier.TakenumberformulaList', {
	extend : 'Ext.c.GridPanel',
	title : "取数公式",
	id : 'DJ.System.supplier.TakenumberformulaList',

	selModel : Ext.create('Ext.selection.CheckboxModel'),
	pageSize : 100,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'selectTakenumberformulas.do',
	Delurl : "deleteTakenumberformulas.do",
	EditUI : "DJ.System.supplier.TakenumberformulaEdit",
	// exporturl:"suppliertoexcel.do",
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	custbar : [],
	fields : [{
		name : 'fid'
	}, {
		name : 'fname',
		myfilterfield : 'fname',
		myfiltername : '名称',
		myfilterable : true
	}, {
		name : 'fnumber',
		myfilterfield : 'fnumber',
		myfiltername : '编码',
		myfilterable : true
	}, {
		name : 'fsqlStatement'
	}],
	columns : {
		defaults : {
			width : 200
		},
		items : [Ext.create('DJ.Base.Grid.GridRowNum'), {
			'header' : 'fid',
			'dataIndex' : 'fid',
			hidden : true,
			hideable : false,
			sortable : true
		}, {
			'header' : '名称',
			'dataIndex' : 'fname',
			sortable : true
		}, {
			'header' : '编码',
			'dataIndex' : 'fnumber',
			sortable : true
		}, {
			'header' : '语句',
			width : 500,
			'dataIndex' : 'fsqlStatement',

			sortable : true
		}]
	}
});
