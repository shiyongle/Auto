Ext.require("Ext.ux.form.MyMixedSearchBox");
Ext.require("DJ.tools.common.MyCommonToolsZ");
Ext.define('DJ.order.saleOrder.SupplierStoreBalanceRpt', {
	extend : 'Ext.c.GridPanel',
	title : "制造商产品库存",
	id : 'DJ.order.saleOrder.SupplierStoreBalanceRpt',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetSupplierStoreBalanceRpt.do',
	Delurl : "",
	EditUI : "DJ.order.saleOrder.SupplierStoreBalanceEdit",
	exporturl : "GetSupplierStoreBalancetoexct.do",
	onload : function() {
		Ext.getCmp('DJ.order.saleOrder.SupplierStoreBalanceRpt.addbutton')
				.hide();
		Ext.getCmp('DJ.order.saleOrder.SupplierStoreBalanceRpt.editbutton')
				.hide();
		Ext.getCmp('DJ.order.saleOrder.SupplierStoreBalanceRpt.delbutton')
				.hide();
		Ext.getCmp('DJ.order.saleOrder.SupplierStoreBalanceRpt.querybutton')
				.hide();
		Ext.getCmp('DJ.order.saleOrder.SupplierStoreBalanceRpt.viewbutton')
				.setText("查询明细");
		var tip = '<p><h4 align="center">"产品库存报表"---根据制造商订单计算产品库存量</h4></p>'
				+ '<ul><li>库存数量：该产品在仓库的库存实物数量</li>'
				+ '<li>待发货数量：已生成配货信息且未发货的数量</li>'
				+ '<li>在生产数量：制造商已确认订单但还未入库的产品数量</li></ul>';
		MyCommonToolsZ.setComAfterrender(this, function(com) {
					MyCommonToolsZ
							.setQuickTip(
									"DJ.order.saleOrder.SupplierStoreBalanceRpt.refreshbutton",
									"", tip);

				})

	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
		// Ext.getCmp("DJ.order.saleOrder.SaleOrderEdit").getform().getForm().findField('fnumber').hide();
	},
	Action_BeforeEditButtonClick : function(EditUI) {
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
		// 可对编辑界面进行复制
	},
	Action_BeforeDelButtonClick : function(me, record) {
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	custbar : [{
		xtype : 'mymixedsearchbox',
		condictionFields : ['cname', 'fname', 'fnumber', 'pspec'],
		tip : '可输入:客户名称、包装物编号、包装物名称、规格',
		useDefaultfilter : true

	}],
	fields : [{
				name : 'fnumber'
			}, {
				name : 'fid'
			}, {
				name : 'fsupplierid'
			}, {
				name : 'cname'
			}, {
				name : 'fname',
				myfilterfield : 'fname',
				myfiltername : '包装物名称',
				myfilterable : true
			}, {
				name : 'fproductdefid'
			}, {
				name : 'pspec'
			}, {
				name : 'fstoreqty'
			}, {
				name : 'forderunitid'
			}, {
				name : 'fcommitted'
			}, {
				name : 'fproduce'
			}],
	columns : [{
				'header' : 'fid',
				'dataIndex' : 'fid',
				hidden : true,
				hideable : false,
				sortable : true
			}, {
				'header' : 'fproductdefid',
				'dataIndex' : 'fproductdefid',
				hidden : true,
				hideable : false,
				sortable : true
			}, {
				'header' : 'fsupplierid',
				'dataIndex' : 'fsupplierid',
				hidden : true,
				hideable : false,
				sortable : true
			}, {
				'header' : '客户名称',
				width : 150,
				'dataIndex' : 'cname',
				sortable : true
			}, {
				'header' : '包装物名称',
				width : 180,
				'dataIndex' : 'fname',
				sortable : true
			}, {
				'header' : '包装物编号',
				width : 150,
				'dataIndex' : 'fnumber',
				sortable : true
			}, {
				'header' : '规格',
				width : 100,
				'dataIndex' : 'pspec',
				sortable : true
			}, {
				'header' : '单位',
				width : 50,
				'dataIndex' : 'forderunitid',
				sortable : true
			}, {
				'header' : '库存数量',
				width : 60,
				'dataIndex' : 'fstoreqty',
				sortable : true
			}, {
				'header' : '待发货数量',
				width : 90,
				'dataIndex' : 'fcommitted',
				sortable : true
			}, {
				'header' : '在生产数量',
				width : 90,
				'dataIndex' : 'fproduce',
				sortable : true
			}],
	selModel : Ext.create('Ext.selection.CheckboxModel')
})