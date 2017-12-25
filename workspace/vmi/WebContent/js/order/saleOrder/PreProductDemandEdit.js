Ext.define('DJ.order.saleOrder.PreProductDemandEdit',{
	extend : 'Ext.ux.form.FileEditUI',
	id : 'DJ.order.saleOrder.PreProductDemandEdit',
	title : "包装需求生成界面",
	resizable : false,
	width : 830,
	height : 600,
	ctype:'Firstproductdemand',
	bodyPadding:'20 15',
	autoScroll:true,
	resizable : false,
	closable : true,
	modal : true,
	url:'saveProductDemand.do',
	cautoverifyinput : false,
	requires : 'Ext.ux.form.DateTimeField',
	doCreate : function(){
		Ext.getCmp(this.id+'.savebutton').setText("存草稿");
	},
	doShow: function(){
		var me = this;
		Ext.Ajax.request({
			url:'getCreateidAndFnumber.do',
			success: function(res){
				var obj = Ext.decode(res.responseText);
				if(obj.success){
					me.getbaseform().findField('fnumber').setValue(obj.data[0].fnumber);
				}else{
					me.close();
					Ext.Msg.alert('提示','获取编号失败！');
				}
			},
			failure: function(){
				me.close();
			}
		});
	},
	Action_AfterSubmit: function(){
		var west = Ext.getCmp('DJ.order.saleOrder.PreProductDemandWest'),
			east = Ext.getCmp('DJ.order.saleOrder.PreProductDemandEast'),
			south = Ext.getCmp('DJ.order.saleOrder.PreProductDemandSouth');
		west.getSelectionModel().deselectAll();
		east.getSelectionModel().deselectAll();
		south.getStore().removeAll();
	},
	custbar : [{
		text : '发布',
		height : 30,
		doSubmit : function(edit,box){
			edit.url="releaseProductDemand.do";
			edit.cautoverifyinput = true;
			try{
				edit.Action_Submit(edit);
				edit.Action_AfterSubmit(edit);
			}catch(e){
				var task = new Ext.util.DelayedTask(function(){  //解决两个提示框同时显示时，提示框在窗口后的问题
					Ext.MessageBox.alert('提示', e);
	            });  
				task.delay(10);
				edit.cautoverifyinput = false;
			}
		},
		handler : function() {
			var edit=this.up('window'),
				form=edit.getbaseform(),
				me = this;
			if(!form.findField("fsupplierid").getValue()){
				Ext.MessageBox.confirm('提示','制造商未指定，是否继续发布？',function(id){
					if(id=='yes'){
						me.doSubmit(edit);
					}
				});
			}else{
				me.doSubmit(edit);
			}
		}
	}],
	initComponent:function(){
		Ext.apply(this,{
			items:[
			  {name:'fcustomerid',xtype:'textfield',hidden:true},
			  {name:'fstate',xtype:'textfield',hidden:true,value:'存草稿'},
			  {name:'fisaccessory',id:'DJ.order.saleOrder.PreProductDemandEdit.fisaccessory',xtype:'textfield',hidden:true},
			  {name:'fpreproductdemandid',xtype:'textfield',hidden:true},
			  {name:'fstructureid',xtype:'textfield',hidden:true},
			  {name:'fplanid',xtype:'textfield',hidden:true},
			  {
				layout:{
					type:'table',
					columns:2
				},
				defaultType:'textfield',
				baseCls:'x-plain',
				defaults:{
					width:350,
					labelWidth:75,
					bodyStyle:'padding:20;'
				},
				items:[{
					fieldLabel:'需求编号',name:'fnumber',labelWidth:130,width:380
				},{
					fieldLabel:'需求名称',name:'fname',labelWidth:130,width:380,labelStyle:'padding-left:30px;',
					allowBlank:false,blankText:'需求未完成编辑。请填写需求名称。'
				},{
					fieldLabel:'是否制样',xtype:'radiogroup',
					items:[{
						boxLabel:'是',
						id:'DJ.order.saleOrder.PreProductDemandEdit.true',
						name:'fiszhiyang',
						inputValue:'true'
					},{
						boxLabel:'否',
						id:'DJ.order.saleOrder.PreProductDemandEdit.false',
						name:'fiszhiyang',
						inputValue:'false',
						checked:true
					}],
					labelWidth:130,width:380,
					listeners:{
						change:function(newVal,oldVal,eOpts){
							if(oldVal.fiszhiyang=='true'){
								Ext.getCmp('DJ.order.saleOrder.PreProductDemandEdit.foverdate').setValue(Ext.Date.add(new Date(new Date().setHours(17,0,0,0)),Ext.Date.DAY,3));
								Ext.getCmp('DJ.order.saleOrder.PreProductDemandEdit.farrivetime').setValue(new Date(Ext.Date.add(new Date(new Date().setHours(17,0,0,0)),Ext.Date.DAY,4)));
							}else if(oldVal.fiszhiyang=='false'){
								Ext.getCmp('DJ.order.saleOrder.PreProductDemandEdit.foverdate').setValue(Ext.Date.add(new Date(new Date().setHours(17,0,0,0)),Ext.Date.DAY,2));
								Ext.getCmp('DJ.order.saleOrder.PreProductDemandEdit.farrivetime').setValue('');
							}
						}
					}
				},{
					fieldLabel:'订单数量',xtype:'numberfield',name:'famount',labelWidth:130,width:380,labelStyle:'padding-left:30px;'
				},{
					xtype : 'datetimefield',
					fieldLabel : '方案入库日期',
					labelWidth:130,
					width:380,
					format:'Y-m-d',
					value:Ext.Date.add(new Date(new Date().setHours(17,0,0,0)),Ext.Date.DAY,2),
					name : 'foverdate',
					allowBlank : false,
					id:'DJ.order.saleOrder.PreProductDemandEdit.foverdate',
					blankText : '方案入库日期不能为空',
					onExpand : function() {
						this.picker.setValue( new Date(new Date().setHours(17,0,0,0)));
					}
				},{
					xtype : 'datetimefield',
					fieldLabel : '发货日期',
					id:'DJ.order.saleOrder.PreProductDemandEdit.farrivetime',
					labelWidth:130,
					width:380,
					format:'Y-m-d',
					name : 'farrivetime',
					value:Ext.Date.add(new Date(new Date().setHours(17,0,0,0)),Ext.Date.DAY,2),
					labelStyle:'padding-left:30px;',
					onExpand : function() {
						this.picker.setValue( new Date(new Date().setHours(17,0,0,0)));
					}
				},{
					fieldLabel:'联系人',name:'flinkman',labelWidth:130,width:380,id:'DJ.order.saleOrder.PreProductDemandEdit.flinkman'
				},{
					fieldLabel:'联系电话',name:'flinkphone',labelWidth:130,width:380,labelStyle:'padding-left:30px;',id:'DJ.order.saleOrder.PreProductDemandEdit.flinkphone'
				},{
					fieldLabel:'制造商名称',
					name:'fsupplierid',
					id:'DJ.order.saleOrder.PreProductDemandEdit.fsupplierid',
					labelWidth:130,
					width:380,
					xtype:'combobox',
					displayField:'fname',
					valueField:'fid',
					editable:false,
					store:Ext.create('Ext.data.Store',{
						fields: ['fid', 'fname'],
						proxy:{
							type:'ajax',
							url: 'getSupplierListOfCustomer.do',
					        reader: {
					            type: 'json',
					            root: 'data'
					        }
						},
						listeners:{
							load:function(me,records){
								if(records.length==1){
									Ext.getCmp('DJ.order.saleOrder.PreProductDemandEdit.fsupplierid').setValue(records[0].get('fid'));
								}
							}
						}
				})
			}]
			},{
				xtype:'displayfield',
				value:'<b>附件分录</b>'
			},{
				xtype : 'cTable',
				name:'Productdemandfile',
				isFilePanel : true,
				pageSize:100,
				height : 200,
				fields:[{
					name:'fid'
				},{
					name:'fname'
				},{
					name:'fpath'
				},{
					name:'fparentid'
				}],
				fileName: 'fname',
				fileId: 'fparentid',
				columns:[{
					text:'附件名称',
					dataIndex:'fname',
					flex:1
				}]
			},{
				xtype:'displayfield',
				value:'<br/><B>需求描述</B>:(当填写包装要求、特殊要求、现状问题，填写内容准确、具体，设计效率越高.)'
			},{
				layout:{
					type:'table',
					columns:2
				},
				baseCls:'x-plain',
				frame:true,
				defaults:{
					width:350,
					labelWidth:75,
					bodyStyle:'padding:20;'
				},
				items:[
				    {
				    	name:'fdescription',
				    	xtype:'textareafield',
				    	width:768,
				    	height:100,
				    	validator : function(val){
				    		var cTable = this.up('window').down('cTable');
				    		if(cTable.getStore().getCount()==0 && !Ext.String.trim(val)){
				    			return '需求未完成编辑。请上传附件或添加描述。';
				    		}
				    		return true;
				    	}
				    }
				]
			}]
		});
		this.callParent(arguments);
	}
})
