Ext.define('DJ.order.saleOrder.mainOrderAffirmedList', {
	extend : 'Ext.panel.Panel',
	id : 'DJ.order.saleOrder.mainOrderAffirmedList',
	autoScroll : false,
	border : false,
	layout :  'border',
	title:'订单信息',
	closable:true,
	requires: ['DJ.order.saleOrder.OrderAffirmedList','DJ.System.product.CustproductCustAccessoryList'],
//	width:1000,
//	height:500,
	
//	layout:'fit',

	defaults : {
		collapsible : true,
		split : true
	},
	items:
			[{
				split : false,
				region : 'center',
				collapsible : false,
				items : [ {xtype : "OrderAffirmedList",
					listeners:{
						itemclick:function(me,record,item,index,e,eOpts ){
							Ext.getCmp('DJ.order.saleOrder.CustproductCustAccessoryList').getStore().load();
						},
						afterrender:function( com, eOpts ){
							com.findProductPlan();
						}
					}
				}],
				layout:'fit'
				
			},
			{
			
				region : 'east',
				collapsible : false,
				items : [ {xtype : "custproductcustaccessorylist",
//					padding : '0 15 0 0',
					id:'DJ.order.saleOrder.CustproductCustAccessoryList',
					url:'getProductFile.do',
					title : ' 产品附件',
					onload:function(){
						this._operateButtonsView(true, ['DJ.order.saleOrder.CustproductCustAccessoryList.refreshbutton']);
						this.getStore().on("beforeload", function(store, operation, eOpts) {

							var records = Ext
									.getCmp("DJ.order.saleOrder.OrderAffirmedList")
									.getSelectionModel().getSelection();

							if (records.length == 1) {

								operation.params = {

									fid : records[0].get("fid")

								}

							}

						});
					}
					
				}],
				layout:'fit',
				width : 180
				
			}
			]
	       
});