Ext.define('DJ.order.Deliver.ActualDeliverRptPanel', {
	
	extend : 'Ext.ux.panel.GridAndLinePanel',
	
	id : 'DJ.order.Deliver.ActualDeliverRptPanel',
	
	requires : ['DJ.order.Deliver.ActualDeliverRpt'],

	title : '我的收货',
	
	autoScroll : false,
	
	closable : true,// 是否现实关闭按钮,默认为false

	initComponent : function() {
		var me = this;

		var myCfg = {
			linePanelCfg : {
				title : "最近7天内收货变动曲线",
				items : [{
					xtype : 'mysimpleline',
					mysimplelineStoreCfg : {
						fields : [{
							name : "famount"
						}, {
							name : "farrivetime"
						}],
						proxy : {
							url : "selectFoutedDeliverorderQtyAndDate.do"
						}
					},
					lineCfg : {
						debug : true,
						axes : [{
							title : '出库数量'
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

				xtype : 'actualdeliverrpt'

			}
		};

		Ext.applyIf(me, myCfg);

		me.callParent(arguments);
	}

});

// Ext.define('DJ.order.Deliver.ActualDeliverRptPanel', {
// extend : 'Ext.panel.Panel',
//
// id : 'DJ.order.Deliver.ActualDeliverRptPanel',
//
// requires : ['Ext.ux.chart.MySimpleLine',
// 'DJ.order.Deliver.ActualDeliverRpt'],
//
// layout : {
// type : 'border'
// },
// title : '我的收货',
//
// closable : true,// 是否现实关闭按钮,默认为false
//
// initComponent : function() {
// var me = this;
//
// Ext.applyIf(me, {
// items : [{
// region : 'center',
// padding : '0 20 0 0',
// xtype : 'panel',
// title : "近30天库存数量",
// layout : 'fit',
// items : [{
// xtype : 'mysimpleline',
// mysimplelineStoreCfg : {
// fields : [{
// name : "famount"
// }, {
// name : "fcreatetime"
// }],
// proxy : {
// url : "selectFoutedDeliverorderQtyAndDate.do"
// }
// },
// lineCfg : {
// axes : [{
// title : '库存数量'
// }, {
// title : '时间'
// }],
// series : [
// {
// title : '时间'// 配置图例字段说明
// }]
// }
// }]
// }, {
// xtype : 'actualdeliverrpt',
// region : 'west',
// width : 550,
// split : true
//
// }]
// });
//
// me.callParent(arguments);
// }
//
// });
