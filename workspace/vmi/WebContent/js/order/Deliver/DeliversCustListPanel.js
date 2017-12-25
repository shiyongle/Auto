Ext.require("Ext.ux.grid.print.MySimpleGridPrinterComponent");
Ext.define('DJ.order.Deliver.DeliversCustListPanel', {

	extend : 'Ext.ux.panel.GridAndLinePanel',

	id : 'DJ.order.Deliver.DeliversCustListPanel',

	requires : ['DJ.order.Deliver.DeliversCustList'],

	title : '我的订单',

	closable : true,// 是否现实关闭按钮,默认为false

	autoScroll : false,
	
	initComponent : function() {
		var me = this;

		var myCfg = {
			linePanelCfg : {
				title : "最近7天"+ me.title +"变动曲线",
				items : [{
					xtype : 'mysimpleline',
					mysimplelineStoreCfg : {
						fields : [{
							name : "famount"
						}, {
							name : "fcreatetime"
						}],
						proxy : {
							url : "selectDeliverapplyCusLineV.do"
						}
					},
					lineCfg : {
//						listeners : {
//							click : function(e, eOpts) {
//								mysimplelineStore.load();
//							}//测试时用
//
//						},
						axes : [{
							title : me.title + '数量'
						}, {
							title : '时间'
						}],
						series : [{
							title : '时间'// 配置图例字段说明
						}]
					}
				}]
			},
			gridCfg : {
				title : me.title,
				xtype : 'deliverscustlist',
				closable : false

			}
		};

		Ext.applyIf(me, myCfg);

		me.callParent(arguments);
	}

});