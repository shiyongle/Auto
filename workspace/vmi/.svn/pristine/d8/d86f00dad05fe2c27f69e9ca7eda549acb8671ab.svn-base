Ext.define('DJ.System.product.CustPdtMainList',{
	extend: 'DJ.System.MainTreePanel',
	requires : ['DJ.System.product.CustproductAccessoryList'],
	id: 'DJ.System.product.CustPdtMainList',
	title: '客户产品资料',
	grid: 'DJ.System.product.CustproductList',
	childItemConfig: [{
		region : 'east',
		collapsible : false,
		items : [{
			xtype : "custproductaccessorylist",
			padding : '0 15 0 0',
			title : ' 产品附件'
		}],
		layout:'fit',
		width : 180
	}]
});