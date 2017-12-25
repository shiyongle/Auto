
Ext.define("CustproductdetailsGridStore", {
	extend : "Ext.data.Store",
	constructor : function(cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
			storeId : "CustproductdetailsGridStore",
			fields : [{
				name : "fname"
			}, {
				name : "fnumber"
			}, {
				name : "fspec"
			}, {
				name : "famount"
			}, {
				name : "fcustproductid"
			}],
			proxy : {
				type : 'memory',
//				url : 'GetCustProductList.do?',
				reader : {
					type : 'json',
					root : 'data'
				}
			}
		}, cfg)])
	}
});
custproductgridStore = Ext.create("CustproductdetailsGridStore");
Ext.define("CustproductDetailsGridPanel", {
	extend : "Ext.grid.Panel",
	alias : "widget.custproductdetailsgridpanel",
	height : 138,
	width : 596,
	store : custproductgridStore,
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			columns : [{
			xtype : "rownumberer",
			text : "No"
			},{
					'header' : '客户产品名称',
					'dataIndex' : 'fname',
					sortable : true
				}, {
					'header' : '编码',
					'dataIndex' : 'fnumber',
					sortable : true
				}, {
					'header' : '规格',
					width : 70,
					'dataIndex' : 'fspec',
					sortable : true
				}, {
					'header' : '比例',
					'dataIndex' : 'famount',
					sortable : true
				}, {
					'header' : 'fcustproductid',
					'dataIndex' : 'fcustproductid',
					hidden : true
			}]	
		});
		me.callParent(arguments);
	}
});
Ext.define("DJ.order.saleOrder.CustproductDetail", {
	extend : "Ext.window.Window",
	id : "DJ.order.saleOrder.CustproductDetail",
	closeAction : "hide",
	height : 357,
	width : 611,
	title : "查看客户产品",
	modal : true,
	resizable : true,
	layout : {
		align : "stretch",
		type : "vbox"
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
				xtype : "textfield",
				id : "DJ.order.saleOrder.CustproductDetail.fname",
				fieldLabel : "产品名称",
				readOnly : true,
				flex : 1,
				width : 400,
				labelWidth : 80
			}, {
				flex : 10,
				xtype : "custproductdetailsgridpanel",
				height : 250

			}],
			buttons : [{
				xtype : "button",
				text : "关闭",
				handler : function() {
					var windows = Ext.getCmp("DJ.order.saleOrder.CustproductDetail");
					if (windows != null) {
						windows.hide();
					}
				}
			}],
			buttonAlign : "right"
		});
		me.callParent(arguments)
	}
});
