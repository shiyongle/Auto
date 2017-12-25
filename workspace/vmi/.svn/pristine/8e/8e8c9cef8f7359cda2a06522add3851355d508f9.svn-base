Ext.define('DJ.order.saleOrder.PreProductDemand',{
	extend : 'Ext.panel.Panel',
	requires : ['DJ.order.saleOrder.PreProductDemandSouth','DJ.order.saleOrder.PreProductDemandWest','DJ.order.saleOrder.PreProductDemandEast','Ext.ux.form.MyDDProxy'],
	id : 'DJ.order.saleOrder.PreProductDemand',
	border : false,
	layout : 'border',
	title : '产品图纸查找界面',
	closable : true,
	items : [{
		region : 'center',
		xtype : 'panel',
		title : '产品图纸查找界面',
		layout : 'border',
		items : [{
			region : 'west',
			xtype : 'preproductdemandwest',
			id : 'DJ.order.saleOrder.PreProductDemandWest',
			width : 470,
			split : true
		},{
			region : 'center',
			xtype : 'panel',
			id : 'DJ.order.saleOrder.PreProductDemandCenter',
			layout : 'vbox',
			defaults : {
				width : '100%'
			},
			items : [{
				xtype : 'toolbar',
				height : 30,
				items : [{
					xtype : 'button',
					iconCls : 'app',
					text : '保存并生成新需求',
					layout:'fit',
					handler : function(){
						var west = Ext.getCmp('DJ.order.saleOrder.PreProductDemandWest'),
							east = Ext.getCmp('DJ.order.saleOrder.PreProductDemandEast'),
							westRecords = west.getSelectionModel().getSelection(),
							eastRecords = east.getSelectionModel().getSelection(),
							westRecord,eastRecord,win,form,store,customerId;
						customerId = Ext.getCmp('DJ.order.saleOrder.PreProductDemand.fcustomerid').getValue();
						if(!customerId){
							Ext.Msg.alert('提示','请先选择客户进行操作！');
							return;
						}
						if(westRecords.length==1 && eastRecords.length==1 && westRecords[0].get('ffileid')){
							westRecord = westRecords[0];
							eastRecord = eastRecords[0];
							win = Ext.create('DJ.order.saleOrder.PreProductDemandEdit');
							form = win.getbaseform();
							form.findField("fname").setValue(westRecord.get('fcpname'));
							form.findField("fcustomerid").setValue(customerId);
							form.findField("fstructureid").setValue(westRecord.get('fid'));
							form.findField("fplanid").setValue(eastRecord.get('fid'));
							store = win.filePanel.getStore(); 
							store.add({fname:westRecord.get('ffilename'),fid:westRecord.get('ffileid'),fparentid:0});
							store.add({fname:eastRecord.get('ffilename'),fid:eastRecord.get('ffileid'),fparentid:1});
							win.seteditstate('add');
							win.show();
						}else{
							Ext.Msg.alert('提示','请选择结构图纸和平面图纸！');
						}
					}
				},{
					xtype:'cCombobox',
					name:'fcustomerid',
					id: 'DJ.order.saleOrder.PreProductDemand.fcustomerid',
					valueField:'fid',
					displayField:'fname',
					emptyText:'选择客户...',
					width: 170,
					hidden: true,
					enableKeyEvents:true,
					listeners:{
						keydown:function(me,e){
							if(e.getKey()==13 && this.picker){
								var store = this.picker.getStore();
								if(store.count()==1){
									this.setmyvalue(store.getAt(0).data);
								}
							}
						}
					},
					MyDataChanged : function(records){
						var val = records[0].get('fid'),
							west = Ext.getCmp('DJ.order.saleOrder.PreProductDemandWest'),
							east = Ext.getCmp('DJ.order.saleOrder.PreProductDemandEast'),
							westStore = west.getStore(),
							eastStore = east.getStore();
						westStore.setDefaultfilter([{
							myfilterfield : "e.fcustomerid",
							CompareType : " = ",
							type : "string",
							value : val
						}]);
						westStore.setDefaultmaskstring(" #0 ");
						westStore.loadPage(1);
						eastStore.setDefaultfilter([{
							myfilterfield : "e.fcustomerid",
							CompareType : " = ",
							type : "string",
							value : val
						}]);
						eastStore.setDefaultmaskstring(" #0 ");
						eastStore.loadPage(1);
						Ext.getCmp('DJ.order.saleOrder.PreProductDemandSouth').getStore().removeAll();
					},
					MyConfig:{
						width:500,
						height:200,
						hiddenToolbar:true,
						url : 'getMyCustomerList.do',
						fields:[{
							name:'fid'
						},{
							name:'fname',
							myfilterfield : 'e1.fname',
							myfilterable : true
						},{
							name:'fnumber',
							myfilterfield : 'e1.fnumber',
							myfilterable : true
						}],
						columns:[{
							text:'客户名称',
							dataIndex:'fname',
							sortable:true,
							flex:1
						},{
							text:'编号',
							dataIndex:'fnumber',
							sortable:true,
							flex:1
						}]
					}
				}]
			},{
				xtype : 'panel',
				title: '结构图纸',
				flex : 1,
				id: 'DJ.order.saleOrder.PreProductDemand.StructureDrawing',
				frame: true,
				html: '<div id="structure_drawing_div"><img id="structure_drawing" data-qtip="双击" style="width:100%;height:100%" alt="图片不存在！" /></div>',
				listeners : {
					render:function(){
						var me = this,
							el = me.getEl();
						el.setStyle('cursor','pointer');
						el.on('dblclick',function(){
							if(me.viewImg){
								new Ext.ux.form.MyDDProxy(me.viewImg.id);
							}
						});
					}
				}
			},{
				xtype : 'panel',
				title: '平面图纸',
				flex : 1,
				id: 'DJ.order.saleOrder.PreProductDemand.PlanDrawing',
				frame: true,
				html: '<div id="plan_drawing_div"><img id="plan_drawing" data-qtip="双击" style="width:100%;height:100%" alt="图片不存在！" /></div>',
				listeners : {
					render:function(){
						var me = this,
							el = me.getEl();
						el.setStyle('cursor','pointer');
						el.on('dblclick',function(){
							if(me.viewImg){
								new Ext.ux.form.MyDDProxy(me.viewImg.id);
							}
						});
					}
				}
			}]
		},{
			region : 'east',
			xtype : 'preproductdemandeast',
			id : 'DJ.order.saleOrder.PreProductDemandEast',
			width : 415,
			split : true
		}]
	},{
		region : 'south',
		xtype : 'preproductdemandsouth',
		id : 'DJ.order.saleOrder.PreProductDemandSouth',
		title : '快速生成需求界面',
		height : 250,
		layout : 'fit',
		split : true,
		collapsible : true
	}]
});