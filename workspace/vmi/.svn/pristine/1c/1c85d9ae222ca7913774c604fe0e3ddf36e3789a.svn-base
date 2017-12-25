Ext.define('DJ.quickOrder.orderInfo.OrdInfoAndPrepareGoodsPanel', {
	extend : 'Ext.tab.Panel',

	id : 'DJ.quickOrder.orderInfo.OrdInfoAndPrepareGoodsPanel',

	requires : ['Ext.panel.Panel', 'Ext.tab.Tab'],

	title : '订单监控',

	closable : true,

	// height: 365,
	// width: 860,
	activeTab : 0,

	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [

			{
				xtype : 'panel',
				layout: 'fit',
				title : '订单信息',
				items : [Ext.create('DJ.quickOrder.orderInfo.OrdInfoList')]
			}, {
				xtype : 'panel',
				layout: 'fit',
				title : '备货信息',
				items : [Ext.create('DJ.quickOrder.orderInfo.PrepareGoodsList')]
			}

			]
		});

		me.callParent(arguments);
	}

});