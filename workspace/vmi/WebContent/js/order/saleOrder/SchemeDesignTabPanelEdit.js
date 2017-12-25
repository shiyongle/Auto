//产品结构树
Ext.define('DJ.order.saleOrder.SchemeDesign.ProductTreess',{
	extend:Ext.tree.Panel,
	alias:'widget.schemeDesignProductTrees',
	id:'DJ.order.saleOrder.SchemeDesign.ProductTreess',
    height: 150,
    rootVisible: false,
    fields:['text','amount','number','version','length','width','height'],
    columns:[{
    	xtype:'treecolumn',
    	text:'产品名称',
    	dataIndex:'text',
    	width:200
    },{
    	text:'编号',
    	dataIndex:'number',
    	flex:2
    },{
    	text:'数量',
    	dataIndex:'amount',
    	flex:1
    },{
    	text:'版本号',
    	dataIndex:'version',
    	flex:1
    },{
    	text:'长',
    	dataIndex:'length',
    	flex:1
    },{
    	text:'宽',
    	dataIndex:'width',
    	flex:1
    },{
    	text:'高',
    	dataIndex:'height',
    	flex:1
    }],
    initComponent:function(){
    	Ext.apply(this,{
    		dockedItems:{
    			xtype:'toolbar',
    			docked:top,
    			items:[{
    				text:'新增',
    				id:'DJ.order.saleOrder.SchemeDesign.ProductTreess.AddButton',
    				handler:function(){
    					var win = Ext.create('DJ.order.saleOrder.SchemeDesignProductdefEdit');
    					var store = Ext.getCmp('DJ.order.Deliver.SchemeDesignProduct').getStore();
    					if(store.data.items.length==0){
    						Ext.getCmp('DJ.order.saleOrder.ProductDefDetail.subProductAmount').hide();
    					}
    					var ffirstproductid = this.up('window').down("textfield[name=ffirstproductid]").getValue();
    					Ext.Ajax.request({
    						url:'getCustomerIdByFirstProduct.do',
    						params:{
    							fid:ffirstproductid
    						},
    						success:function(res){
    							obj = Ext.decode(res.responseText);
    							if(obj.success){
    								win.down('textfield[name=fcustomerid]').setValue(obj.data[0].fcustomerid);
    								win.down('textfield[name=fcustomername]').setValue(obj.data[0].fcustomername);
    							}else{
    								Ext.Msg.alert('错误',obj.msg);
    							}
    						}
    					})
    					win.state='add';
    					win.show();
    				}
    			},{
    				text:'编辑',
    				handler:function(){
    					var productTree = this.up('treepanel'),
    						items = productTree.getSelectionModel().getSelection();
    					if(items.length!=1){
    						Ext.Msg.alert('提示','请选择一条记录进行编辑！');
    						return ;
    					}
    					var store = Ext.getCmp('DJ.order.Deliver.SchemeDesignProduct').getStore();
    					var model = store.findRecord('fname',items[0].get('text'),null,null,null,true),
    						win = Ext.create('DJ.order.saleOrder.SchemeDesignProductdefEdit');
    					if(store.indexOf(model)==0){
    						Ext.getCmp('DJ.order.saleOrder.ProductDefDetail.subProductAmount').hide();
    					}
    					win.state='edit';
    					win.editModel = model;
    					win.down('form').loadRecord(model);
    					win.show();
    				}
    			},{
    				text:'删除',
    				handler:function(){
    					var productTree = this.up('treepanel'),
						items = productTree.getSelectionModel().getSelection();
    					if(items.length!=1){
    						Ext.Msg.alert('提示','请选择一条记录进行删除！');
    						return ;
    					}
    					var store = Ext.getCmp('DJ.order.Deliver.SchemeDesignProduct').getStore();
    					var model = store.findRecord('fname',items[0].get('text'),null,null,null,true);
    					Ext.Ajax.request({
    						url:'productIsUsedBySupplier.do',
    						params:{
    							fid:model.get('fid')
    						},
    						success:function(res){
    							var obj = Ext.decode(res.responseText);
    							if(obj.success){
    								if(store.indexOf(model)==0){
    									store.removeAll();
    								}else{
    									store.remove(model);
    								}
    							}else{
    								Ext.Msg.alert('提示',obj.msg);
    							}
    						}
    					});
    				}
    			}]
    		}
    	});
//    	this.createMenu();
    	this.callParent(arguments);
    },
    reloadProductNode:function(){
    	var rootNode = this.getStore().getRootNode(),
    		hideProductGrid = Ext.getCmp('DJ.order.Deliver.SchemeDesignProduct'),
    		parentProductNode;
    	if(!hideProductGrid){
    		return;
    	}
    	rootNode.removeAll();
		hideProductGrid.getStore().each(function(record,index,len){
			if(index==0){
				record.set('schemedesignid',Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.fid').getValue());
				parentProductNode=rootNode.appendChild({
					text:record.get('fname'),
					expanded: true,
					number:record.get('fnumber'),
					version:record.get('fversion'),
					length:record.get('fboxlength'),
					width:record.get('fboxwidth'),
					height:record.get('fboxheight')
				});
			}else{
				parentProductNode.appendChild({
					text:record.get('fname'),
					leaf:true,
					amount:record.get('subProductAmount'),
					number:record.get('fnumber'),
					version:record.get('fversion'),
					length:record.get('fboxlength'),
					width:record.get('fboxwidth'),
					height:record.get('fboxheight')
				});
			}
		});
    }
});

Ext.define('DJ.order.saleOrder.SchemeDesignTabPanelEdit',{
	extend : 'Ext.form.Panel',
	id:'DJ.order.saleOrder.SchemeDesignTabPanelEdit',
	title : "方案设计编辑界面",
	resizable : false,
	width : 730,
	height : 540,
	ctype:'Schemedesign',
	bodyPadding:'20 15',
	autoScroll:true,
	resizable : false,
	closable : true,
	modal : true,
	url:'SaveOrupdateSchemeDesign.do',
	viewurl:'getSchemeDesignInfo.do',
	tbar : [{
		text:'确认',
		handler:function(){
			var parentid = this.up('panel').up('panel').up('window').parentid;
			Ext.getCmp(parentid).down('button[text=确认]').handler();
			this.up('panel').up('panel').up('window').close();
		}
	},{
		text:'取消确认',
		handler:function(){
			var parentid = this.up('panel').up('panel').up('window').parentid;
			Ext.getCmp(parentid).down('button[text=取消确认]').handler();
			this.up('panel').up('panel').up('window').close();
		}
	}],
	Action_BeforeSubmit:function(){
		var store = Ext.getCmp('DJ.order.Deliver.SchemeDesignEntry').getStore();
		store.each(function(record){
			if(record.get('fcharacter')==''){
				throw '特性名称不能为空！';
			}
			if(record.get('fentryamount')==''){
				throw '特性数量不能为空！';
			}
		})
	},
	onload:function(){
		var fidField = Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.fid');
		fidField.on('change',function(){
			var fileGrid = Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles');
			var store = fileGrid.getStore();
			store.setDefaultfilter([{
				myfilterfield : "fparentid",
				CompareType : " = ",
				type : "string",
				value : this.getValue()
			}]);
			store.setDefaultmaskstring(" #0 ");
			fileGrid.noLoad=false;
			store.loadPage(1);
		});
		var descriptionField = Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.fdescription');
		descriptionField.on('change',function(){
			descriptionField.setValue(descriptionField.getValue().replace(/<br\/>/g,'\n'));
		},descriptionField,{single:true})
	},
//	Action_BeforeSubmit:function(){
//    	var foverdateField = this.down('form').getForm().findField('foverdate');
//    	var grid = Ext.getCmp('DJ.order.Deliver.SchemeDesignProduct');
//    	if(grid.getStore().getCount()>0&&!foverdateField.getValue()){
//    		foverdateField.setValue(new Date());
//    	}
//    },
	listeners:{
		show:function(){
			var editstate = Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit').editstate;
			if(editstate=="view"){
				Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles.addfile').hide();
				Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles.productActionss').hide();
				Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles').setDisabled(false);
//				Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles.ftype').setreadonly(true);
//				Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles.fdescription').setreadonly(true);
//				Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.fieldcontainer').setDisabled(false);
//				Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.fboard').setDisabled(false);
//				Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.fbox').setDisabled(false);
			}
			Ext.getCmp('DJ.order.Deliver.SchemeDesignEntry').getStore().on('datachanged',function(me){
				if(me.count()>0){
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.InfoList').collapse();
				}else{
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.InfoList').expand();
				}
			})
		}
	},
	initComponent:function(){
		var hideProductGrid = Ext.create('widget.cTable',{
			name:'Productstructure',
			height:200,
			pageSize:100,
			hidden:true,
			parentfield:'p.schemedesignid',
			id:'DJ.order.Deliver.SchemeDesignProduct',
			url:'getSchemeDesignProducts.do',
//			parentfield:'d.schemedesignid',
			fields:[
//			        {name:'fcusproductname'},
			        {name:'fcustomerid'},
//			        {name:'ftilemodelid'},
//			        {name:'fnewtype'},
			        {name:'fboxlength'},
			        {name:'fboxwidth'},
			        {name:'fboxheight'},
//			        {name:'fmateriallength'},
//			        {name:'fmaterialwidth'},
//			        {name:'fhstaveexp'},
//			        {name:'farea'},
//			        {name:'fmaterialcost'},
//			        {name:'fcharacter'},
//			        {name:'fmodelmethod'},
//			        {name:'fdescription'},
			        {name:'fid'},
			        {name:'fname'},
			        {name:'fversion'},
			        {name:'fmaterialcodeid'},
			        {name:'fnumber'},
//			        {name:'fmaterialcode'},
//			        {name:'flayer'},
//			        {name:'fboxmodelid'},
//			        {name:'fboardlength'},
//			        {name:'fboardwidth'},
//			        {name:'fstavetype'},
//			        {name:'fvstaveexp'},
//			        {name:'fchromaticprecision'},
//			        {name:'fcleartype'},
//			        {name:'fquality'},
//			        {name:'forderprice'},
//			        {name:'fnumber'},
//			        {name:'forderunitid'},
			        {name:'schemedesignid'},
			        {name:'subProductAmount'},
			        {name:'fcustomername'}
			        ],
			        columns:[{
			        	dataIndex:'fname',
			        	header :'名称'
			        }]
		});
		var hideProductGridStore = hideProductGrid.getStore();
		hideProductGridStore.on('datachanged',function(me){
			var fieldset = Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.CharacterInfo');
			var myfieldset = Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.InfoList');
			var form = fieldset.up('form').getForm();
			if(me.count()>0){
//				Ext.getCmp('DJ.order.Deliver.SchemeDesignEntry.addlinebutton').disable();
				form.findField('ftilemodel').allowBlank=true;
				form.findField('fmaterial').allowBlank=true;
				form.findField('fboxlength').allowBlank=true;
				form.findField('fboxwidth').allowBlank=true;
				form.findField('fboxheight').allowBlank=true;
				form.findField('foverdate').allowBlank=true;
				Ext.getCmp('DJ.order.saleOrder.SchemeDesign.ProductTreess').show();
				fieldset.collapse();
				myfieldset.expand();
				
			}else{
				form.findField('ftilemodel').allowBlank=false;
				form.findField('fmaterial').allowBlank=false;
				form.findField('fboxlength').allowBlank=false;
				form.findField('fboxwidth').allowBlank=false;
				form.findField('fboxheight').allowBlank=false;
				form.findField('foverdate').allowBlank=false;
				Ext.getCmp('DJ.order.saleOrder.SchemeDesign.ProductTreess').hide();
				fieldset.expand();
				myfieldset.collapse();
				
				
			}
			
			Ext.getCmp('DJ.order.saleOrder.SchemeDesign.ProductTreess').reloadProductNode();
		})
		//初始化文件窗口
		var SDproductdemandfileGrid = Ext.widget({
				xtype:'SDproductdemandfiles',
				id:'DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles',
				url:'getSDProductdemandfileList.do',
				onload:function(){
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles.addbutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles.editbutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles.viewbutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles.delbutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles.refreshbutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles.querybutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles.exportbutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles.exportbutton').setVisible(false);
				 },
				custbar:[{
					text:'上传附件',
					id : 'DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles.addfile',
					height : 30,
					handler : function(){
			        	var id = Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.fid').getValue();
			        	var win = Ext.create('DJ.order.saleOrder.SchemeDesignTabPanelEdit.loadupWin');
						win.down("textfield[name=fparentid]").setValue(id);
						win.show();
					}
				}]
				
			});
		SDproductdemandfileGrid.getStore().addListener('beforeload',function(){
			if(SDproductdemandfileGrid.noLoad){
				return false;
			}
		});
		Ext.apply(this,{
			items:[
//			  {name:'isfauditor',xtype:'textfield',id:'DJ.order.saleOrder.SchemeDesignTabPanelEdit.isfauditor',hidden:true},
			  {name:'fid',id:'DJ.order.saleOrder.SchemeDesignTabPanelEdit.fid',xtype:'textfield',hidden:true},
			  {name:'fcreatorid',xtype:'textfield',hidden:true},
//			  {name:'fcreatetime',xtype:'textfield',hidden:true,value:'2014-05-27 00:00:00.000'},
			  {name:'fcustomerid',xtype:'textfield',hidden:true},
			  {name:'fsupplierid',xtype:'textfield',hidden:true},
			  {name:'ffirstproductid',xtype:'textfield',hidden:true},
			  {
				layout:{
					type:'table',
					columns:2
				},
				defaultType:'textfield',
				baseCls:'x-plain',
				defaults:{
					width:337,
					labelWidth:75,
					bodyStyle:'padding:20;',
					allowBlank:false
				},
				items:[{
					fieldLabel:'方案名称',id:'DJ.order.saleOrder.SchemeDesignTabPanelEdit.fname',name:'fname',width:333
				},{
					fieldLabel:'方案编码',id:'DJ.order.saleOrder.SchemeDesignTabPanelEdit.fnumber',name:'fnumber',labelStyle:'padding-left:30px;',labelWidth:105
				}
				/*,{
					xtype:'numberfield',fieldLabel:'数量',name:'famount',labelStyle:'padding-left:30px;',labelWidth:105,value:0
				}*/]
			},
			{
				xtype:'displayfield',
				value:'附件列表',
				style:{
					marginTop:'12px'
				}
			},SDproductdemandfileGrid,
			{
				xtype:'fieldset',
				title:'结构及信息列表',
				id:'DJ.order.saleOrder.SchemeDesignTabPanelEdit.InfoList',
				anchor:'100%',
				style:'margin-top:10px;padding:5px 10px 10px;',
				items:[{
					xtype:'schemeDesignProductTrees'
				}]
			}
				/*{
				xtype:'displayfield',
				value:'结构及信息列表',
				style:{
					marginTop:'10px'
				}
			},{
				//treepanel
				xtype:'schemeDesignProductTrees',
				style:'margin-top:5px'
			}*/,hideProductGrid,
			{
				xtype:'fieldset',
				title:'特性信息',
				anchor:'100%',
				id:'DJ.order.saleOrder.SchemeDesignTabPanelEdit.CharacterInfo',
				style:'margin-top:10px;padding:5px 10px 10px;',
				fieldDefaults:{
					allowBlank:false
				},
				items:[
					{
				   		layout:{
							type:'table',
							columns:2
						},
						baseCls:'x-plain',
						labelStyle:'width:80',
						defaults:{
							labelWidth:75,
							allowBlank:false
						},
						defaultType:'textfield',
						items:[
							{fieldLabel:'楞型',name:'ftilemodel',width:310},
							{fieldLabel:'材料',name:'fmaterial',labelStyle:'padding-left:40px;',labelWidth:105,width:338}
						]
				   	
					},{
						xtype:'fieldcontainer',
						fieldLabel:'纸箱规格',
						width:700,
						style:'margin-top:10px',
						combineErrors:true,
						layout:'hbox',
						/*fieldDefaults:{
							allowBlank:false
						},*/
						items:[
						       {xtype:'numberfield',hideTrigger:true,name:'fboxlength',emptyText:'长'},
						       {xtype:'displayfield',value:' mm X',margin:'3 2 0'},
						       {xtype:'numberfield',hideTrigger:true,name:'fboxwidth',emptyText:'宽'},
						       {xtype:'displayfield',value:' mm X',margin:'3 2 0'},
						       {xtype:'numberfield',hideTrigger:true,name:'fboxheight',emptyText:'高'},
						       {xtype:'displayfield',value:' mm',margin:'3 2 0'}
						]
					},Ext.create('Ext.ux.form.DateTimeField', {
						fieldLabel:'配送时间',
						name:'foverdate',
						format:'Y-m-d',
						width:310,
						style:'margin-top:10px',
						labelWidth:75,
						onExpand : function() {
							this.picker.setValue( new Date(new Date().setHours(17,0,0,0)));
						}

					}),{
						xtype:'displayfield',
						value:'产品特性',
						style:{
							marginTop:'12px'
						}
					},{

						xtype:'cTable',
						name:'SchemeDesignEntry',
						height:200,
						pageSize:100,
						parentfield:'fparentid',
						url:'getSchemeDesignEntry.do',
						id:'DJ.order.Deliver.SchemeDesignEntry',
						emptyText:'可新增若干分录，若只有一个特性，不用填写',
						plugins:[Ext.create("Ext.grid.plugin.CellEditing",{
				   			clicksToEdit:1
				   		})],
				   		features : [{
							ftype : 'summary'
						}],
				   		fields:[{
				   			name:'fid'
				   		},{
				   			name:'fpurchasenumber'
				   		},{
				   			name:'fentryamount',type:'int'
				   		},{
				   			name:'fcharacter'
				   		}],
				   		columns:[{
				   			dataIndex:'fid',
				   			hidden:true,
				   			hideable:false
				   		},{
				   			dataIndex:'fpurchasenumber',
				   			text:'采购订单号',
				   			sortable:true,
				   			flex:1,
				   			editor:{
				   				xtype:'textfield',
				   				allowBlank:true
				   			}
				   		},{
				   			dataIndex:'fcharacter',
				   			text:'特性',
				   			sortable:true,
				   			flex:2,
				   			editor:{
				   				xtype:'textfield',
				   				allowBlank:true
				   			}
				   		},{
				   			dataIndex:'fentryamount',
				   			text:'数量',
				   			sortable:true,
				   			flex:1,
				   			summaryType:"sum",
				   			summaryRenderer:function(v,d,f){
				   				return "合计:"+v;
				   				
				   			},
				   			editor:{
				   				xtype:'numberfield',
				   				allowBlank:true,
				   				minValue:1,
				   				allowDecimals:false
				   			}
				   		}]
					
					}
				]
			}
			
			
			
			/*{
		   		layout:{
					type:'table',
					columns:2
				},
				baseCls:'x-plain',
				style:'margin-top:15px;margin-bottom:10px;',
				labelStyle:'width:80',
				defaults:{
					width:333,
					labelWidth:75,
					bodyStyle:'padding:20;',
					allowBlank:false
				},
				defaultType:'textfield',
				items:[
					{fieldLabel:'楞型',name:'ftilemodel'},
					{fieldLabel:'材料',name:'fmaterial',labelStyle:'padding-left:30px;',labelWidth:105,width:335}
				]
		   	
			}*/,{
				xtype:'displayfield',
				value:'方案描述:',
				style:{
					marginTop:'20px'
				}
			},{
				layout:{
					type:'table',
					columns:2
				},
				baseCls:'x-plain',
				defaults:{
					width:350,
					labelWidth:75,
					bodyStyle:'padding:20;'
				},
				items:[{name:'fdescription',xtype:'textarea',colspan:2,allowBlank:true,width:671,maxLength:255,id:'DJ.order.saleOrder.SchemeDesignTabPanelEdit.fdescription'}]
			},{
		   		layout:{
					type:'table',
					columns:2
				},
				baseCls:'x-plain',
				defaults:{
					width:350,
					labelWidth:75,
					bodyStyle:'padding:20;'
				},defaultType:'displayfield',
				items:[
					{fieldLabel:'创建人',name:'fcreator',id:'DJ.System.MaigaoEdit.fcreatorname'},
					{fieldLabel:'创建时间',name:'fcreatetime',labelStyle:'padding-left:30px;',labelWidth:140,width:415}
				]
		   	
			}]
		});
		this.callParent(arguments);
	}
});

Ext.define('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles',{
	ctype:'Productdemandfile',
	extend : 'Ext.c.GridPanel',
	pageSize : 50,
	alias:'widget.SDproductdemandfiles',
	closable : false,// 是否现实关闭按钮,默认为false
	url:'',
	exporturl : "",// 导出为EXCEL方法
	height:200,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
//	noLoad:true,
	onload:function(){
	
	},
	fields:[{
			name:'fid'
		},{
			name:'fname'
		},{
			name:'fpath'
		},{
			name:'fparentid'
		},{
			name:'ftype'
		},{
			name:'fdescription'
		}],
		columns:[{
			dataIndex:'fid',
			hidden:true,
			hideable:false
		},{
			text:'路径',
			dataIndex:'fpath',
			hidden:true,
			hideable:false
		},{
			xtype:'actioncolumn',
			width:100,
			align:'center',
			text:'操作',
			id:'DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles.productActionss',
	        items: [
	        {
	            icon: 'images/delete.gif',
	            tooltip: '删除附件',
	            handler: function(grid, rowIndex, colIndex) {
	                var fid = grid.getStore().getAt(rowIndex).get('fid');
	                var fpath = grid.getStore().getAt(rowIndex).get('fpath');
	                Ext.Ajax.request({
	                	url:'delSchemeDesignfile.do',
	                	params:{
	                		fid : fid,
	                		fparentid : Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.fid').getValue(),
	                		fpath : fpath
	                	},
	                	success:function(res){
	                		var obj = Ext.decode(res.responseText);
	                		if(obj.success){
	                			djsuccessmsg(obj.msg);
	                			Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles').getStore().load();
	                		}else{
	                			Ext.Msg.alert('提示',obj.msg);
	                		}
	                	},
	                	failure:function(){
	                		Ext.Msg.alert('错误','系统故障，请检查网络状况或联系管理员！');
	                	}
	                });
	            }
	        }]
		},{
			xtype:'templatecolumn',
			text:'附件名称',
			dataIndex:'fname',
			tpl : '<a href="downloadProductdemandFile.do?fid={fid}" target="_blank" style="text-decoration:none;">{fname}</a>',
			flex:1
		},
		{
			dataIndex:'ftype',
			text:'类型'
		}, 
		{
			dataIndex:'fdescription',
			text:'备注'
		},
		{
			dataIndex:'fparentid',
			hidden:true,
			hideable:false
		}],plugins : [ Ext.create(
								'Ext.grid.plugin.CellEditing',
								{
									clicksToEdit : 2
								})
				]
})

Ext.define('DJ.order.saleOrder.SchemeDesignTabPanelEdit.loadupWin', {
	extend : 'Ext.Window',
	id : 'DJ.order.saleOrder.SchemeDesignTabPanelEdit.loadupWin',
	modal : true,
	// title : "E上传",
	width : 450,// 230, //Window宽度
	height : 300,// 137, //Window高度
	resizable : false,
	closable : true, // 关闭按钮，默认为true
	// renderTo: 'upload',
	items : [{
		xtype : 'form',
		id : 'DJ.order.saleOrder.SchemeDesignTabPanelEdit.loadupWin.form',
		baseCls : 'x-plain',

		width : 400,
		bodyPadding : 10,
		margin : '20 10 20 20',
		frame : true,

		defaults : {
			labelSeparator : ":",
			labelWidth : 100,
			width : 200,
			allowBlank : false,
			labelAlign : "left",
			msgTarget : ""
		},

		items : [

		{
			xtype : 'textfield',
//			id : 'DJ.order.saleOrder.SchemeDesignTabPanelEdit.loadupWin.fparentid',
			name : 'fparentid',
			hidden : true

		}
		, {
			xtype:'combo',
			id:'DJ.order.saleOrder.SchemeDesignTabPanelEdit.loadupWin.form.ftype',
			fieldLabel:'类型',
			width : 315,
			name:'ftype',
			displayField:'text',
			valueField:'value',
			value:'结构图',
			allowBlank:false,
			editable:false,
			store:Ext.create('Ext.data.Store',{
				fields:['text','value'],
				data:[
				    {value:'结构图',text:'结构图'},
				    {value:'版面图',text:'版面图'},
				    {value:'作业指导书',text:'作业指导书'},
				    {value:'作业视频',text:'作业视频'},
				    {value:'效果图',text:'效果图'},
				    {value:'技术要求',text:'技术要求'}
				]
			})
		}, {
			xtype : 'filefield',
			name : 'loadupfileName',
			fieldLabel : '上传',

			msgTarget : 'side',
			allowBlank : false,
			anchor : '100%',
			buttonText : '选择文件'
		}, {
			xtype : 'textarea',
			name : 'fdescription',
			fieldLabel : '备注',
			width : 315,
			labelWidth : 100,
			height : 100,
			allowBlank : true
		}],
		buttons : [{
			text : '上传',
			handler : function() {
//				var form = Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.schemedesign').getForm();
				var form = this.up('form').getForm();
//					var parentid = Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.fparentid').getValue();
				var parentid = form.findField('fparentid').getValue();
				var ftype = Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.loadupWin.form.ftype').getValue();
				var fdescription = form.findField('fdescription').getValue();
//				var fname = form.findField('fileName').getValue();
//				var fname = Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.loadupWin.form.fileName').getValue();
				var paramsArray = [];
				
								// 解决特殊字符的编码问题
				
								paramsArray.push("?fparentid");
								paramsArray.push("=" + encodeURIComponent(parentid) + "&");
				
								paramsArray.push("ftype");
								paramsArray.push("=" + encodeURIComponent(ftype) + "&");
				
								paramsArray.push("fdescription");
								paramsArray.push("=" + encodeURIComponent(fdescription) + "");
				
//								paramsArray.push("fname");
//								paramsArray.push("=" + encodeURIComponent(fname) + "");
				
								var paramsT = paramsArray.join("");
					if(form.isValid()){
						form.submit({
							url:'uploadSchemedesignFile.do'+paramsT,
							success:function(me,action){
								var obj = Ext.decode(action.response.responseText);
								if(obj.success){
//									Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles').getStore().load({params : {fparentid : parentid}});
									
//									var store = Ext.getCmp('DJ.order.Deliver.productdemandfile').getStore();
//										store.filter([
//										           Ext.create('Ext.util.Filter', {property: "fparentid", value: parentid, root: 'data'})
//										       ]);
//										store.reload();
										
									var myfilter = [];
									myfilter.push({
										myfilterfield : "fparentid",
										CompareType : " = ",
										type : "string",
										value : parentid
									});
									var store = Ext.getCmp('DJ.order.saleOrder.SchemeDesignTabPanelEdit.SchemeDesignFiles').getStore();
									store.setDefaultfilter(myfilter);
									store.setDefaultmaskstring(" #0 ");
									store.load();
										var windows = Ext.getCmp("DJ.order.saleOrder.SchemeDesignTabPanelEdit.loadupWin");
										if (windows != null) {
											windows.close();
										}
										djsuccessmsg(obj.msg);
									}
								else{
									Ext.Msg.alert('错误',obj.msg);
								}
							}
						,failure : function(response, option) {
							Ext.Msg.alert('错误',option.result.msg);
						}
						})
					}
			}
		}]
	}]
});
