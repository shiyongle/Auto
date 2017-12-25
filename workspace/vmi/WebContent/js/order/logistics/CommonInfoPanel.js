Ext.define('DJ.order.logistics.CommonInfoPanel', {
	extend : 'Ext.tab.Panel',
	id : 'CommonInfoPanel',
	requires : ['Ext.panel.Panel', 'Ext.tab.Tab'],
	title : '常用信息',
	closable : true,
	activeTab : 0,
	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [

			{
				xtype : 'panel',
				layout: 'fit',
				title : '我的地址',
				items : [Ext.create('DJ.order.logistics.LogisticsaddressInfoList')]
				}, {
				xtype : 'panel',
				layout: 'fit',
				title : '常用车辆信息',
				items : [Ext.create('DJ.order.logistics.LogisticsCarInfoList')]
			}, {
				xtype : 'panel',
				layout: 'fit',
				title : '常用收货信息',
				items : [Ext.create('DJ.order.logistics.ConsigneeaddressInfoList')]
			}

			]
		});

		me.callParent(arguments);
	}

});