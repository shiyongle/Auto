

Ext.define('DJ.order.saleOrder.OrderBalanceDetailLists.OrderBalanceDetail', {
	extend : 'Ext.c.GridPanel',
//	title : "查看明细",
	id : 'DJ.order.saleOrder.OrderBalanceDetailLists.OrderBalanceDetail',
	pageSize : 50,
	height:320,
//	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetProductplanofBanlancesDetails.do?',
	Delurl : "",
	EditUI : "",
	exporturl : "",
	onload : function() {
	this.down('toolbar').setVisible(false);
	},
	fields : [{
		name : 'fid'
	}, {
		name : 'fname'
	}, {
		name : 'fnumber'
	}, {
		name : 'fcharacter'
	}, {
		name : 'ftype'
	}, {
		name : 'famount'
	}, {
		name : 'fcreator'
	}, {
		name : 'fcreatetime'
	}],
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : '制造商订单号',
		'dataIndex' : 'fnumber',
		width : 100,
		sortable : true
	}, {
		'header' : '包装物名称',
		'dataIndex' : 'fname',
		width : 175,
		sortable : true

	}, {
		'header' : '规格',
		'dataIndex' : 'fcharacter',
		width : 150,
		sortable : true
	}, {
		'header' : '类型',
		'dataIndex' : 'ftype',
		sortable : true
	}, {
		'header' : '数量',
		'dataIndex' : 'famount',
		sortable : true
	}, {
		'header' : '操作人',
		'dataIndex' : 'fcreator',
		sortable : true
	}, {
		'header' : '操作时间',
		'dataIndex' : 'fcreatetime',
		width : 120,
		sortable : true
	}]
})

Ext.define("DJ.order.saleOrder.OrderBalanceDetailLists", {
	extend : "Ext.window.Window",
	id : "DJ.order.saleOrder.OrderBalanceDetailLists",
	closeAction : "hide",
	modal : true,
	x : 50,
	y : 50,
	resizable : true,
	height : 350,
	width : 900,
	title : "查看明细",
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [
			Ext.create("DJ.order.saleOrder.OrderBalanceDetailLists.OrderBalanceDetail")
			]
		});
		me.callParent(arguments);
	}
});