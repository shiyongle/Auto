Ext.define('DJ.System.supplier.SupplierjudgementList', {
	extend : 'Ext.c.GridPanel',
	title : "制造商评价类型",
	id : 'DJ.System.supplier.SupplierjudgementList',

	selModel : Ext.create('Ext.selection.CheckboxModel'),
	pageSize : 100,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'selectSupplierjudgements.do',
	Delurl : "deleteSupplierjudgements.do",
	EditUI : "DJ.System.supplier.SupplierjudgementEdit",
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
		name : 'frate',
		type : "float"
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
			'header' : '比例',
			// width : 70,
			'dataIndex' : 'frate',

			sortable : true
		}]
	}
});
