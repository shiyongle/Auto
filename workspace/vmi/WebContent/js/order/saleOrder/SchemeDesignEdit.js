//产品结构树
Ext.define('DJ.order.saleOrder.SchemeDesign.ProductTree',{
	extend:Ext.tree.Panel,
	alias:'widget.schemeDesignProductTree',
	id:'DJ.order.saleOrder.SchemeDesign.ProductTree',
    height: 150,
    rootVisible: false,
    fields:['text','amount','number','version','length','width','height'],
    columns:[{
    	xtype:'treecolumn',
    	text:'包装物名称',
    	dataIndex:'text',
    	width:200
    },{
    	text:'包装物编号',
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
    				id:'DJ.order.saleOrder.SchemeDesign.ProductTree.AddButton',
    				handler:function(){
    					var win = Ext.create('DJ.order.saleOrder.SchemeDesignProductdefEdit');
    					var store = Ext.getCmp('DJ.order.Deliver.SchemeDesignProducts').getStore();
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
    					win.setparentWin({product:'DJ.order.Deliver.SchemeDesignProducts',tree:'DJ.order.saleOrder.SchemeDesign.ProductTree'})
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
    					var store = Ext.getCmp('DJ.order.Deliver.SchemeDesignProducts').getStore();
    					var model = store.findRecord('fname',items[0].get('text'),null,null,null,true),
    						win = Ext.create('DJ.order.saleOrder.SchemeDesignProductdefEdit');
    					if(store.indexOf(model)==0){
    						Ext.getCmp('DJ.order.saleOrder.ProductDefDetail.subProductAmount').hide();
    					}
    					win.state='edit';
    					win.setparentWin({product:'DJ.order.Deliver.SchemeDesignProducts',tree:'DJ.order.saleOrder.SchemeDesign.ProductTree'})
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
    					var store = Ext.getCmp('DJ.order.Deliver.SchemeDesignProducts').getStore();
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
    		hideProductGrid = Ext.getCmp('DJ.order.Deliver.SchemeDesignProducts'),
    		parentProductNode;
    	if(!hideProductGrid){
    		return;
    	}
    	rootNode.removeAll();
		hideProductGrid.getStore().each(function(record,index,len){
			if(index==0){
				record.set('schemedesignid',Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.fid').getValue());
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

Ext.define('DJ.order.saleOrder.SchemeDesignEdit',{
	extend : 'Ext.c.BaseEditUI',
	id:'DJ.order.saleOrder.SchemeDesignEdit',
	title : "方案设计编辑界面",
	resizable : false,
	width : 730,
	height : 580,
	ctype:'Schemedesign',
	bodyPadding:'20 15',
	autoScroll:true,
	resizable : false,
	closable : true,
	modal : true,
	requires: 'Ext.ux.form.DateTimeField',
	url:'SaveOrupdateSchemeDesign.do',
	viewurl:'getSchemeDesignInfo.do',
	custbar : [{
			text:'方案评价界面',
			id:'DJ.order.saleOrder.SchemeDesignEdit.appraise',
			handler:function(){
				var fid = Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.fid').getValue();
				var win = Ext.create('Ext.c.BaseEditUI',{
					modal:true,
					resizable:false,
					items:[Ext.create('DJ.order.Deliver.AppraiseList',{
						frame:true,
						maxHeight:400
//						,
//						width:730
					})]
				})
				var AppraiseLisStore = Ext.getCmp('DJ.order.Deliver.AppraiseList.view').getStore();
				AppraiseLisStore.load({params:{schemeDensignId:Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.fid').getValue()}});
				
				win.showAt(400,100);
			}
	},{
		text:'确认',
		handler:function(){
			var edit = Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit');
			grid = Ext.getCmp(edit.parent);
			grid.down('button[text=确认]').handler();
			Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit').close();
		}
	},{
		text:'取消确认',
		handler:function(){
			var edit = Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit');
			grid = Ext.getCmp(edit.parent);
			grid.down('button[text=取消确认]').handler();
			Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit').close();
		}
	}],
	Action_BeforeSubmit:function(){
		 var filedvalue=true;
		 Ext.Array.each(this.down("fieldset[title=特性信息]").query("field[allowBlank=false]"), function(cmp, value, myself) {
				if(cmp.getXType()!="displayfield"&&!Ext.isEmpty(cmp.getValue()))  {filedvalue=false; return;}
			});
		var store = Ext.getCmp('DJ.order.Deliver.SchemeDesignEntry').getStore();
		if(store.getCount()==0&&filedvalue){
			Ext.Array.each(this.down("fieldset[title=特性信息]").query("[allowBlank=false]"), function(cmp, value, myself) {
				cmp.allowBlank=true;
			});
		} 
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
		var fidField = Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.fid');
		fidField.on('change',function(){
			var fileGrid = Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile');
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
		var descriptionField = Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.fdescription');
		descriptionField.on('change',function(){
			descriptionField.setValue(descriptionField.getValue().replace(/<br\/>/g,'\n'));
		},descriptionField,{single:true})
		var s=Ext.getCmp("DJ.order.Deliver.SchemeDesignEntry").getStore();
		s.on('add', function() {
					var rec1 =s.getAt(0);
					var rec2 =s.getAt(1);
					if(!Ext.isEmpty(rec2)){
						rec1.set("fpurchasenumber",rec2.get("fpurchasenumber"));
						rec1.commit();
					}
				});
	},
// Action_BeforeSubmit:function(){
// var foverdateField = this.down('form').getForm().findField('foverdate');
//    	var grid = Ext.getCmp('DJ.order.Deliver.SchemeDesignProducts');
//    	if(grid.getStore().getCount()>0&&!foverdateField.getValue()){
//    		foverdateField.setValue(new Date());
//    	}
//    },
	listeners:{
		show:function(){
			var editstate = Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit').editstate;
			if(editstate=="add"){
				Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.appraise').hide();
				
				Ext.Array.each(this.query("fieldset"), function(cmp, value, myself) {
				cmp.collapse();
				});
				this.down('button[text=确认]').hide();
				this.down('button[text=取消确认]').hide();
				
			}
			if(editstate=="view"){
				Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile.addfile').hide();
				Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile.productActions').hide();
				Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile').setDisabled(false);
//				Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile.ftype').setreadonly(true);
//				Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile.fdescription').setreadonly(true);
//				Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.fieldcontainer').setDisabled(false);
//				Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.fboard').setDisabled(false);
//				Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.fbox').setDisabled(false);
			}
			Ext.getCmp('DJ.order.Deliver.SchemeDesignEntry').getStore().on('datachanged',function(me){
				if(me.count()>0){
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.InfoList').collapse();
				}else{
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.CharacterInfo').collapse();
					if(Ext.getCmp('DJ.order.Deliver.SchemeDesignProducts').getStore().count()==0)
					{
						Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.InfoList').collapse();
					}
					else{Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.InfoList').expand();}
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
			id:'DJ.order.Deliver.SchemeDesignProducts',
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
			var fieldset = Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.CharacterInfo');
			var form = fieldset.up('form').getForm();
			if(me.count()>0){
//				Ext.getCmp('DJ.order.Deliver.SchemeDesignEntry.addlinebutton').disable();
				form.findField('ftilemodel').allowBlank=true;
				form.findField('fmaterial').allowBlank=true;
				form.findField('fboxlength').allowBlank=true;
				form.findField('fboxwidth').allowBlank=true;
				form.findField('fboxheight').allowBlank=true;
				form.findField('foverdate').allowBlank=true;
				form.findField('ftemplateproduct').allowBlank=true;
				form.findField('ftemplatenumber').allowBlank=true;
				fieldset.collapse();
				
			}else{
//				Ext.getCmp('DJ.order.Deliver.SchemeDesignEntry.addlinebutton').enable();
				form.findField('ftilemodel').allowBlank=false;
				form.findField('fmaterial').allowBlank=false;
				form.findField('fboxlength').allowBlank=false;
				form.findField('fboxwidth').allowBlank=false;
				form.findField('fboxheight').allowBlank=false;
				form.findField('foverdate').allowBlank=false;
				form.findField('ftemplateproduct').allowBlank=false;
				form.findField('ftemplatenumber').allowBlank=false;
				fieldset.expand();
			}
			Ext.getCmp('DJ.order.saleOrder.SchemeDesign.ProductTree').reloadProductNode();
		})
		//初始化文件窗口
		var SDproductdemandfileGrid = Ext.widget({
				xtype:'SDproductdemandfile',
				id:'DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile',
				url:'getSDProductdemandfileList.do',
				onload:function(){
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile.addbutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile.editbutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile.viewbutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile.delbutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile.refreshbutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile.querybutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile.exportbutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile.exportbutton').setVisible(false);
				 },
				custbar:[{
					text:'上传附件',
					id : 'DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile.addfile',
					height : 30,
					handler : function(){
			        	var id = Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.fid').getValue();
			        	var win = Ext.create('DJ.order.saleOrder.SchemeDesignEdit.loadupWin');
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
//			  {name:'isfauditor',xtype:'textfield',id:'DJ.order.saleOrder.SchemeDesignEdit.isfauditor',hidden:true},
			  {name:'fid',id:'DJ.order.saleOrder.SchemeDesignEdit.fid',xtype:'textfield',hidden:true},
			  {name:'fcreatorid',xtype:'textfield',hidden:true},
			  {name:'fcreatetime',xtype:'textfield',hidden:true,value:'2014-05-27 00:00:00.000'},
			  {name:'fcustomerid',xtype:'textfield',hidden:true},
			  {name:'fgroupid',xtype:'textfield',hidden:true},
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
					fieldLabel:'方案名称',id:'DJ.order.saleOrder.SchemeDesignEdit.fname',name:'fname',width:333
				},{
					fieldLabel:'方案编号',id:'DJ.order.saleOrder.SchemeDesignEdit.fnumber',name:'fnumber',labelStyle:'padding-left:30px;',labelWidth:105
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
				collapsible:true,
				title:'结构及信息列表',
				id:'DJ.order.saleOrder.SchemeDesignEdit.InfoList',
				anchor:'100%',
				style:'margin-top:10px;padding:5px 10px 10px;',
				items:[{
					xtype:'schemeDesignProductTree'
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
				xtype:'schemeDesignProductTree',
				style:'margin-top:5px'
			}*/,hideProductGrid,
			{
				xtype:'fieldset',
				collapsible:true,
				title:'特性信息',
				anchor:'100%',
				id:'DJ.order.saleOrder.SchemeDesignEdit.CharacterInfo',
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
						fieldLabel:'规格',
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
					},{
						xtype: 'datetimefield',
						fieldLabel:'配送时间',
						name:'foverdate',
						format:'Y-m-d',
						width:310,
						style:'margin-top:10px',
						labelWidth:75,
						onExpand : function() {
							this.picker.setValue( new Date(new Date().setHours(9,0,0,0)));
						}

					},{
						layout:{
							type:'table',
							columns:2
						},
						baseCls:'x-plain',
						labelStyle:'width:80',
						style: 'margin-top: 18px',
						defaults:{
							labelWidth:75,
							allowBlank:false
						},
						defaultType:'textfield',
						items:[
							{fieldLabel:'模版产品',name:'ftemplateproduct',width:310},
							{fieldLabel:'模版编号',name:'ftemplatenumber',labelStyle:'padding-left:40px;',labelWidth:105,width:338}
						]
					},{
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
						features : [{
							ftype : 'summary'
						}],
						plugins:[Ext.create("Ext.grid.plugin.CellEditing",{
				   			clicksToEdit:1
				   		})],
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
				   			xtype: "numbercolumn",
				   			renderer:function(val){
				   				return val;
				   			},
				   			summaryType:"sum",
				   			summaryRenderer:function(v,d,f){
				   				return "合计:"+v;
				   				
				   			},
				   			editor:{
				   				xtype:'numberfield',
				   				allowBlank:true,
//				   				minValue:1,
				   				allowDecimals:false
				   			}
				   		}],
				   		listeners:{
//				   			'click':function(){
//				   				alert("ss");
//				   			}
				   		}
					
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
				items:[{name:'fdescription',xtype:'textarea',colspan:2,allowBlank:true,width:671,maxLength:255,id:'DJ.order.saleOrder.SchemeDesignEdit.fdescription'}]
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

Ext.define('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile',{
	ctype:'Productdemandfile',
	extend : 'Ext.c.GridPanel',
	pageSize : 50,
	alias:'widget.SDproductdemandfile',
	closable : false,// 是否现实关闭按钮,默认为false
	url:'',
	exporturl : "",// 导出为EXCEL方法
	height:200,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	noLoad:true,
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
			id:'DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile.productActions',
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
	                		fparentid : Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.fid').getValue(),
	                		fpath : fpath
	                	},
	                	success:function(res){
	                		var obj = Ext.decode(res.responseText);
	                		if(obj.success){
	                			djsuccessmsg(obj.msg);
	                			Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile').getStore().load();
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

Ext.define('DJ.order.saleOrder.SchemeDesignEdit.loadupWin', {
	extend : 'Ext.Window',
	id : 'DJ.order.saleOrder.SchemeDesignEdit.loadupWin',
	modal : true,
	// title : "E上传",
	width : 450,// 230, //Window宽度
	height : 300,// 137, //Window高度
	resizable : false,
	closable : true, // 关闭按钮，默认为true
	// renderTo: 'upload',
	items : [{
		xtype : 'form',
		id : 'DJ.order.saleOrder.SchemeDesignEdit.loadupWin.form',
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
//			id : 'DJ.order.saleOrder.SchemeDesignEdit.loadupWin.fparentid',
			name : 'fparentid',
			hidden : true

		}
		, {
			xtype:'combo',
			id:'DJ.order.saleOrder.SchemeDesignEdit.loadupWin.form.ftype',
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
//				var form = Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.schemedesign').getForm();
				var form = this.up('form').getForm();
//					var parentid = Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.fparentid').getValue();
				var parentid = form.findField('fparentid').getValue();
				var ftype = Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.loadupWin.form.ftype').getValue();
				var fdescription = form.findField('fdescription').getValue();
//				var fname = form.findField('fileName').getValue();
//				var fname = Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.loadupWin.form.fileName').getValue();
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
//									Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile').getStore().load({params : {fparentid : parentid}});
									
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
									var store = Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.SchemeDesignFile').getStore();
									store.setDefaultfilter(myfilter);
									store.setDefaultmaskstring(" #0 ");
									store.load();
										var windows = Ext.getCmp("DJ.order.saleOrder.SchemeDesignEdit.loadupWin");
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