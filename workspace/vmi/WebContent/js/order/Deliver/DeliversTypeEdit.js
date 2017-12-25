var store = Ext.create('Ext.data.Store',{
	fields:['name','number'],
	data:[{
		number:'0',
		name:'正常'
	},{
		number:'1',
		name:'补单'
	},{
		number:'2',
		name:'补货'
	}]
})

Ext.define('DJ.order.Deliver.DeliversTypeEdit',{
	id:'DJ.order.Deliver.DeliversTypeEdit',
	extend:'Ext.Window',
	width:280,
	height:150,
	title:'更改要货申请状态页面',
	modal:true,
	closable:true,
	resizable:'false',
	bodyPadding:'20 10',
	closeAction:'hide',
	initComponent:function(){
		var me = this;
		me.form = Ext.create('widget.form',{
			border:false,
//			frame:true,
			baseCls:'x-plain',
			items:[{
				itemId:'deliversType',
				xtype:'combo',
				fieldLabel:'选择状态',
				displayField:'name',
				valueField:'number',
				store:store,
				allowBlank:false,
				blankText:'请选择状态',
				labelWidth:70
			}]
		});
		Ext.apply(me,{
			items:me.form,
			buttons:[{
				text:'提交',
				handler:function(){
					var grid = Ext.getCmp('DJ.order.Deliver.DeliversList'),
						record = grid.getSelectionModel().getSelection(),
						row = [];
					for(var i=0;i<record.length;i++){
						row.push(record[i].get('fid'));
					}
					me.getEl().mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url:'updateDeliversType.do',
						params:{
							ftype:me.form.getComponent('deliversType').getValue(),
							fids:row.join(',')
						},
						success:function(response){
							var data = Ext.decode(response.responseText);
							if(data.success){
								Ext.MessageBox.alert('成功', data.msg);
								grid.store.load();
							}else{
								Ext.MessageBox.alert('失败',data.msg);
							}
							me.getEl().unmask();
							me.form.getForm().reset();
							me.hide();
						}
					})
				}
			},{
				scope:me,
				text:'取消',
				handler:function(){
					this.form.getForm().reset();
					this.hide();
				}
			}]
		});
		me.callParent(arguments);
	}
	
	
})