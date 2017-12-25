Ext.define('DJ.cardboard.system.MainProducePlanList',{
	extend : 'Ext.panel.Panel',
	title : "排产计划",
	id : 'DJ.cardboard.system.MainProducePlanList',
	closable : true,// 是否现实关闭按钮,默认为false
	layout : 'border',
	autoScroll : true,
	border : false,
	items:[{
		region : 'center',
		items:[Ext.create('DJ.cardboard.system.ProducePlanList')],
		layout:'fit'
	},{
		region : 'west',
		title : '制造商列表',
		items:[Ext.create("DJ.cardboard.system.ProducePlanSupplierTree")],
		collapsible : true,
		width : 220,
		layout:'fit'
	}]
})