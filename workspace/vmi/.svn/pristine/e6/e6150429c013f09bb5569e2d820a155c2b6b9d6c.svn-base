Ext.define('DJ.order.saleOrder.SchemeDesignTabPanel', {
	id : 'DJ.order.saleOrder.SchemeDesignTabPanel',
	width : 730,
	height : 600,
	extend : 'Ext.c.BaseEditUI',
	schemeDesignId:'',
	listeners:{
		show:function(){
			this.down('button[text=取   消]').setText('关闭');
			//获取附件信息
			var schemeDesignId = Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanel').schemeDesignId;
			var fileStore = Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles').getStore();
			Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles.productActionss').hide();

			Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles.addfile').hide();
			
			fileStore.setDefaultfilter([{
				myfilterfield : "fparentid",
				CompareType : "=",
				type : "string",
				value : schemeDesignId
			}]);
			fileStore.setDefaultmaskstring(" #0 ");
			fileStore.load();
//			Ext.Ajax.request({
//				url:'getSDProductdemandfileList.do',
//				params:{
//					fid:schemeDesignId
//				},
//				success:function(res){
//					var obj = Ext.decode(res.responseText);
//            		if(obj.success){
//            			fileStore.loadRawData(obj.data);
//            		}
//				}
//			})
			//获取方案信息
			Ext.Ajax.request({
				url:'getSchemeDesignInfo.do',
				params:{
					fid:schemeDesignId
				},
				success:function(res){
					var obj = Ext.decode(res.responseText);
				
            		if(obj.success){
            			var form = Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit').getForm();
    					form.setValues(obj.data[0]);
            		}else{
            			Ext.Msg.alert('提示',obj.msg);
            		}
				}
			})
			//获取产品结构信息
			var productStore = Ext.getCmp('DJ.order.Deliver.SchemeDesignProduct').getStore();
			productStore.setDefaultfilter([{
				myfilterfield : "p.schemedesignid",
				CompareType : "=",
				type : "string",
				value : schemeDesignId
			}]);
			productStore.setDefaultmaskstring(" #0 ");
			productStore.load();
		
			//获取特性信息
			var SchemeDesignEntry = Ext.getCmp('DJ.order.Deliver.SchemeDesignEntry').getStore();
			SchemeDesignEntry.setDefaultfilter([{
				myfilterfield : "fparentid",
				CompareType : "=",
				type : "string",
				value : schemeDesignId
			}]);
			SchemeDesignEntry.setDefaultmaskstring(" #0 ");
			SchemeDesignEntry.load();
			
			var AppraiseLisStore = Ext.getCmp('DJ.order.Deliver.AppraiseList.view').getStore();
			AppraiseLisStore.load({params:{schemeDensignId:schemeDesignId}});
			//隐藏按钮或设置成只读模式
			Ext.getCmp('DJ.order.saleOrder.SchemeDesign.ProductTreess').down('toolbar').setVisible(false);
//			Ext.getCmp('DJ.order.Deliver.SchemeDesignEntry.addlinebutton').hide();
//			Ext.getCmp('DJ.order.Deliver.SchemeDesignEntry.dellinebutton').hide();
			Ext.getCmp('DJ.order.Deliver.SchemeDesignEntry').down('toolbar').setVisible(false);
//			Ext.getCmp('DJ.order.Deliver.SchemeDesignEntry').setDisabled(true);
			Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.CharacterInfo').setDisabled(true);
			Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.fname').setReadOnly(true);
			Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.fnumber').setReadOnly(true);
			Ext.getCmp('DJ.order.Deliver.SchemeDesignEntry').getStore().on('datachanged',function(me){
				if(me.count()==0){
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.CharacterInfo').collapse();
				}
			})
		}
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [ Ext.create('Ext.tab.Panel', {
				id : 'DJ.order.saleOrder.panel',
//				width : 500,
//				height : 500,
				autoScroll:true,
				activeTab : 0,
				items : [ 
				          Ext.create('DJ.order.saleOrder.SchemeDesignTabPanelEdit',{
								closable : false,
								frame:true
							})
							
				,  
				Ext.create('DJ.order.Deliver.AppraiseList',{
					closable : false,
					frame:true,
					maxHeight:550
				}) ]
			})]
		});
		me.callParent(arguments);
	}
})
