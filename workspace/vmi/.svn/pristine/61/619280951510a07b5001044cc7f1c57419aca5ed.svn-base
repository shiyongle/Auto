Ext.define('DJ.System.product.ProductdefDetails', {
	extend : 'Ext.Window',
	id : 'DJ.System.product.ProductdefDetails',
//	autoScroll : true,
	border : false,
	layout : 'border',
	title:'产品信息',
	closable:true,
	width:820,
	height:520,
	initComponent : function() {
		Ext.apply(this, {
	items:
			[{
				 
				region : 'center',
				items : [ Ext.create("DJ.System.product.ProductDefDetail")],
				layout:'fit'
				
			},
			{
				
				header:false,
				region : 'west',
				collapsible : true,
				width : 180,
				layout:'fit',
				items : [ Ext.create("DJ.System.product.ProductStructureTree")]
		
	}]}), this.callParent(arguments);
	}
	       
});