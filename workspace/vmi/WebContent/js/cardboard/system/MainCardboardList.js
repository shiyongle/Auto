Ext.define('DJ.cardboard.system.MainCardboardList',{
	extend : 'Ext.panel.Panel',
	title : "纸板材料",
	id : 'DJ.cardboard.system.MainCardboardList',
	closable : true,// 是否现实关闭按钮,默认为false
	layout : 'border',
	autoScroll : false,
	border : false,
	items:[{
		region : 'center',
		items:[Ext.create('DJ.cardboard.system.CardboardList')],
		layout:'fit'
	},{
		region : 'west',
		title : '制造商列表',
		items:[Ext.create("DJ.cardboard.system.SupplierTree")],
		collapsible : true,
		width : 220,
		layout:'fit'
	}]
})