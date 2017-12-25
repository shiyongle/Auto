Ext.define('DJ.order.Deliver.PdtAllotChooseEdit',{
	extend : 'Ext.Window',
	id:'DJ.order.Deliver.PdtAllotChooseEdit',
	title : "产品分配规则选择",
	width : 300,
	height : 120,
	closable : true,
	modal : true,
	bodyPadding:'10 20',
	dockedItems: [{
        xtype: 'toolbar',
        dock: 'top',
        items: [{
	            text: '确认',
	            handler:function(){
	   			 Ext.Ajax.request({
	   			 url:'manualAllotruletoproductdemand.do',
	   			 params:{
	   				fsupplierid:this.up("window").down("combo").getValue(),
	   				productdemandid:Ext.getCmp('DJ.order.Deliver.PdtAllotChooseEdit.fProductdemandid').getValue()//this.up("window").down("textfield").getValue()
	   			 },
	   			 success : function(response, option) {
		   			 var obj = Ext.decode(response.responseText);
		   			 if (obj.success == true) {
		   				Ext.getCmp('DJ.order.Deliver.PdtAllotChooseEdit').close();
			   			Ext.getCmp("DJ.order.Deliver.ProductdemandOfallotList").store.load();
			   			djsuccessmsg(obj.msg);
		   			 } else {
	   					 Ext.MessageBox.alert('错误', obj.msg);
		   			 }
		   			 }
	   			 })
    		}
        }]
    }],
	items:[{
		xtype:'combo',
		 fieldLabel: '制造商',
		    store: Ext.create('Ext.data.Store', {
		    	proxy: {
		            type: 'ajax',
		            url: 'GetSupplierByProductdemandList.do',
		            reader: {
		                type: 'json',
		                root: 'data'
		            }
		        },
		        fields: ['fname', 'fid']
		        }),
		        
//		    queryMode: 'local',
		    displayField: 'fname',
		    valueField: 'fid'
	},{
		name : 'fProductdemandid',
		id : 'DJ.order.Deliver.PdtAllotChooseEdit.fProductdemandid',
		xtype : 'textfield',
		fieldLabel : '需求ID',
		hidden : true
	}]
});
