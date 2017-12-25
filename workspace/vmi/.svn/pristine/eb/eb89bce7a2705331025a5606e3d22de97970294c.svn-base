Ext.define('DJ.order.Deliver.AppraiseEdit',{
	extend:'Ext.c.BaseEditUI',
	title:'评价编辑界面',
	id:'DJ.order.Deliver.AppraiseEdit',
	width:300,
	height:300,
	bodyPadding : '10 15',
	onload:function(){
		this.down('toolbar').setVisible(false);
	},
	schemeDensignId:'',
	items:[{
		xtype:'displayfield',
		fieldLabel:'您对本方案的评价是：',
		labelSeparator : '',
		labelWidth:200
	},{
		xtype:'displayfield',
		fieldLabel:'基本评价',
		labelSeparator : ''
	},{
		xtype:'radiogroup',
		id:'DJ.order.Deliver.AppraiseEdit.fbasicappraise',
		items:[ { boxLabel: '非常满意', name: 'fbasicappraise', inputValue: '0' },
				 { boxLabel: '满意', name: 'fbasicappraise', inputValue: '1' },
				 { boxLabel: '不满意', name: 'fbasicappraise', inputValue: '2' }]
	},{
		xtype:'displayfield',
		fieldLabel:'详细描述',
		labelSeparator : ''
		
	},{
		xtype:'textareafield',
		name:'fdescription',
		id:'DJ.order.Deliver.AppraiseEdit.fdescription',
		width:250,
		height:80
	}],
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'bottom',
		layout: {
		    pack: 'center'
		},
		items : [{
			text : '<b>提交</b>',
			handler:function(){
				var edit = Ext.getCmp('DJ.order.Deliver.AppraiseEdit');
				var fbasicappraise = Ext.getCmp('DJ.order.Deliver.AppraiseEdit.fbasicappraise').getValue().fbasicappraise;
				var fdescription = Ext.getCmp('DJ.order.Deliver.AppraiseEdit.fdescription').getValue();
				Ext.Ajax.request({
					url:'saveOrUpdateAppraise.do',
					params:{
						fbasicappraise:fbasicappraise,
						fdescription:fdescription,
						schemeDensignId:edit.schemeDensignId
					},
					success:function(res){
						var obj = Ext.decode(res.responseText);
                		if(obj.success){
                			djsuccessmsg(obj.msg);
                			edit.close();
                		}else{
                			Ext.Msg.alert('提示',obj.msg);
                		}
					}
				})
			}
		},{
			text : '<b>取消</b>',
			handler:function(){
				Ext.getCmp('DJ.order.Deliver.AppraiseEdit').close();
				
			}
		}]
	}]
})