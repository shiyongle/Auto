//产品结构树
Ext.define('DJ.order.saleOrder.FistDemandProduct.ProductTree',{
	extend:Ext.tree.Panel,
	alias:'widget.fistDemandProductTree',
	id:'DJ.order.saleOrder.FistDemandProduct.ProductTree',
    height: 150,
    rootVisible: false,
    fields:['text','amount','number','version','length','width','height','id'],
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
    				id:'DJ.order.saleOrder.FistDemandProduct.ProductTree.AddButton',
    				handler:function(){
    					var win = Ext.create('DJ.order.saleOrder.SchemeDesignProductdefEdit');
    					var store = Ext.getCmp('DJ.order.Deliver.FistDemandProductProducts').getStore();
    					if(store.data.items.length==0){
    						Ext.getCmp('DJ.order.saleOrder.ProductDefDetail.subProductAmount').hide();
    					}
    					Ext.Ajax.request({
    						url:'getSDproductfid.do',
    						success:function(res){
    							obj = Ext.decode(res.responseText);
    							if(obj.success){
    								win.down('hidden[name=fid]').setValue(obj.data[0].fid);
    							}else{
    								Ext.Msg.alert('错误',obj.msg);
    							}
    						}
    					})
    					var fcustomerid=Ext.getCmp("DJ.order.saleOrder.FistDemandProductEdit.fcustomerid")
    					if(Ext.isEmpty(fcustomerid.getRawValue())){Ext.Msg.alert('提示','请先选择客户，再新增产品！'); return;}
    					win.down('textfield[name=fcustomerid]').setValue(fcustomerid.getValue());
    					win.down('textfield[name=fcustomername]').setValue(fcustomerid.getRawValue());
    					 var fsupplierid=Ext.getCmp("DJ.order.saleOrder.FistDemandProductEdit.fsupplierid")
    					 if(!Ext.isEmpty(fsupplierid.getRawValue())){
    						win.down('hidden[name=fsupplierid]').setValue(fsupplierid.getValue());
    					 }
    					win.setparentWin({product:'DJ.order.Deliver.FistDemandProductProducts',tree:'DJ.order.saleOrder.FistDemandProduct.ProductTree'})
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
    					var store = Ext.getCmp('DJ.order.Deliver.FistDemandProductProducts').getStore();
    					var model = store.findRecord('fname',items[0].get('text'),null,null,null,true),
    						win = Ext.create('DJ.order.saleOrder.SchemeDesignProductdefEdit');
    					if(store.indexOf(model)==0){
    						Ext.getCmp('DJ.order.saleOrder.ProductDefDetail.subProductAmount').hide();
    					}
    					win.state='edit';
    					win.setparentWin({product:'DJ.order.Deliver.FistDemandProductProducts',tree:'DJ.order.saleOrder.FistDemandProduct.ProductTree'})
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
    					var firstfid=Ext.getCmp("DJ.order.saleOrder.FistDemandProductEdit.fid").getValue();
    					var store = Ext.getCmp('DJ.order.Deliver.FistDemandProductProducts').getStore();
    					var model = store.findRecord('fname',items[0].get('text'),null,null,null,true);
    					Ext.Ajax.request({//删除后台附件数据
    						url:'getproductIsDeleteFile.do',
    						params:{
    							fid:model.get('fid'),
    							firstfid:firstfid
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
    listeners:{
 	 itemclick:function(view,record,item,index){
			var fileGrid = Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.SchemeDesignFile');
			var store = fileGrid.getStore();
			store.setDefaultfilter([{
				myfilterfield : "fparentid",
				CompareType : " = ",
				type : "string",
				value : record.get("id")
			}]);
			store.setDefaultmaskstring(" #0 ");
			fileGrid.noLoad=false;
			store.loadPage(1);
			}    
    },
    reloadProductNode:function(){
    	var me=this;
    	var rootNode = me.getStore().getRootNode(),
    		hideProductGrid = Ext.getCmp('DJ.order.Deliver.FistDemandProductProducts'),
    		parentProductNode;
    	if(!hideProductGrid){
    		return;
    	}
    	rootNode.removeAll();
		hideProductGrid.getStore().each(function(record,index,len){
			if(index==0){
				record.set('schemedesignid',Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.fid').getValue());
				parentProductNode=rootNode.appendChild({
					id:record.get('fid'),
					text:record.get('fname'),
					expanded: true,
					number:record.get('fnumber'),
					version:record.get('fversion'),
					length:record.get('fboxlength'),
					width:record.get('fboxwidth'),
					height:record.get('fboxheight')
				});
				me.fireEvent("itemclick",me,parentProductNode);
			}else{
				parentProductNode.appendChild({
					id:record.get('fid'),
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

Ext.define('DJ.order.saleOrder.FistDemandProductEdit',{
	extend : 'Ext.c.BaseEditUI',
	id:'DJ.order.saleOrder.FistDemandProductEdit',
	title : "产品编辑界面",
	resizable : false,
	width : 730,
	height : 580,
	ctype:'Firstproductdemand',
	bodyPadding:'20 15',
	autoScroll:true,
	resizable : false,
	closable : true,
	modal : true,
	requires: 'Ext.ux.form.DateTimeField',
	url:'SaveOrupdateFirstProduct.do',//需求新增产品 
	infourl:'getFirstProductInfo.do',//不需要查看
	viewurl:'getFirstProductInfo.do',//不需要查看
	Action_BeforeSubmit:function(){
		var store = Ext.getCmp('DJ.order.Deliver.FistDemandProductProducts').getStore();
		if(store.getCount()==0){
			throw '请添加产品！';
		} 
	},
	onload:function(){
			 Ext.getCmp('DJ.order.Deliver.FistDemandProductProducts').getStore().on('datachanged',function(me){
			 	if(this.data.items.length>0)
			 	{
			 			Ext.getCmp("DJ.order.saleOrder.FistDemandProductEdit.fcustomerid").setReadOnly(true);
    					Ext.getCmp("DJ.order.saleOrder.FistDemandProductEdit.fsupplierid").setReadOnly(true)
			 	}else
			 	{
			 		Ext.getCmp("DJ.order.saleOrder.FistDemandProductEdit.fcustomerid").setReadOnly(false);
    					Ext.getCmp("DJ.order.saleOrder.FistDemandProductEdit.fsupplierid").setReadOnly(false)
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
			var editstate = Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit').editstate;
			if(editstate=="view"){
				
				Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.SchemeDesignFile.addfile').hide();
				Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.SchemeDesignFile.productActions').hide();
				Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.SchemeDesignFile').setDisabled(false);
			}
		}
	},
	initComponent:function(){
		var hideProductGrid = Ext.create('widget.cTable',{
			name:'Productstructure',
			height:200,
			pageSize:100,
			hidden:true,
			parentfield:'p.schemedesignid',
			id:'DJ.order.Deliver.FistDemandProductProducts',
			url:'getSchemeDesignProducts.do',
			fields:[
//			        {name:'fcusproductname'},
			        {name:'fcustomerid'},
			        {name:'fsupplierid'},//新增供应商字段
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
			Ext.getCmp('DJ.order.saleOrder.FistDemandProduct.ProductTree').reloadProductNode();
		})
		//初始化文件窗口
		var SDproductdemandfileGrid = Ext.widget({
				xtype:'SDproductfile',
				id:'DJ.order.saleOrder.FistDemandProductEdit.SchemeDesignFile',
				url:'getSDProductdemandfileList.do',
				onload:function(){
					Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.SchemeDesignFile.addbutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.SchemeDesignFile.editbutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.SchemeDesignFile.viewbutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.SchemeDesignFile.delbutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.SchemeDesignFile.refreshbutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.SchemeDesignFile.querybutton').setVisible(false);
					Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.SchemeDesignFile.exportbutton').setVisible(false);
				 },
				custbar:[{
					text:'上传附件',
					id : 'DJ.order.saleOrder.FistDemandProductEdit.SchemeDesignFile.addfile',
					height : 30,
					handler : function(){
			        	var id = Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.fid').getValue();
			    		var records=Ext.getCmp("DJ.order.saleOrder.FistDemandProduct.ProductTree").getSelectionModel().getSelection();
			    		if(records.length==0){
							Ext.MessageBox.alert("错误","请选择一条产品记录再添加附件！");
							return;
						}
			        	var win = Ext.create('DJ.order.saleOrder.FistDemandProductEdit.loadupWin');
			        	 win.down("textfield[name=fparentid]").setValue(records[0].get("id"));
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
			  {name:'fid',id:'DJ.order.saleOrder.FistDemandProductEdit.fid',xtype:'textfield',hidden:true},
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
					bodyStyle:'padding:20 20 20 50;'
				},
				items:[{
						valueField : 'fid', // 组件隐藏值
						name : "fcustomerid",
						readOnlyCls:'x-item-disabled',
						id:'DJ.order.saleOrder.FistDemandProductEdit.fcustomerid',
						xtype : 'cCombobox',
						displayField : 'fname',// 组件显示值
						fieldLabel : '客  户',
						allowBlank:false,
//						editable:false,
						width:300,
						labelWidth : 75,
						MyConfig : {
							width : 800,// 下拉界面
							height : 200,// 下拉界面
							url : 'GetCustomerList.do', // 下拉数据来源
							fields : [{
								name : 'fid'
							}, {
								name : 'fname',
								myfilterfield : 't_bd_customer.fname', // 查找字段，发送到服务端
								myfiltername : '名称', // 在过滤下拉框中显示的名称
								myfilterable : true
									// 该字段是否查找字段
									}, {
										name : 'fnumber',
										myfilterfield : 't_bd_customer.fnumber', // 查找字段，发送到服务端
										myfiltername : '编码', // 在过滤下拉框中显示的名称
										myfilterable : true
									// 该字段是否查找字段
									}, {
										name : 'findustryid'
									}, {
										name : 'fmnemoniccode'
									}, {
										name : 'faddress'
									}],
							columns : [{
								'header' : 'fid',
								'dataIndex' : 'fid',
								hidden : true,
								hideable : false,
								sortable : true

							}, {
								'header' : '编码',
								'dataIndex' : 'fnumber',
								sortable : true
							}, {
								'header' : '客户名称',
								'dataIndex' : 'fname',
								sortable : true
							}, {
								'header' : '助记码',
								'dataIndex' : 'fmnemoniccode',
								sortable : true
							}, {
								'header' : '行业',
								'dataIndex' : 'findustryid',
								sortable : true
							}, {
								'header' : '地址',
								'dataIndex' : 'faddress',
								sortable : true,
								width : 250
							}]
						}
						,listeners:{
							select:function(combo, store,index){
							
								Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.fsupplierid').getStore().load();
							}
						}
				},{
					valueField : 'fid', 
					name : "fsupplierid",
					id:'DJ.order.saleOrder.FistDemandProductEdit.fsupplierid',
					xtype : 'combobox',
					displayField : 'fname',
					readOnlyCls:'x-item-disabled',
					fieldLabel : '制造商',
			//		editable:false,
					queryMode : 'local',
					typeAhead : true,
//					forceSelection : true,
					width : 300,
					labelWidth : 75,
					labelStyle:'padding-left:30px;',
					listeners:{
							expand:function(combo, store,index){
								this.setValue('');
								this.getStore().loadPage(1);
							}
						},
					store:Ext.create('Ext.data.Store',{
							fields: ['fid', 'fname'],
							autoLoad:true,
							proxy:{
								type:'ajax',
								url: 'getSupplierListOfCustomerByDesign.do',
						         reader: {
						             type: 'json',
						             root: 'data'
						         }
							},
							listeners:{
								beforeload:function(store){
									var fcustomerid=Ext.getCmp("DJ.order.saleOrder.FistDemandProductEdit.fcustomerid");
									if(Ext.isEmpty(fcustomerid.getRawValue()))
									{
										fcustomerid.setValue("");
									}
									store.getProxy().setExtraParam("fcustomerid",fcustomerid.getValue());
								},
								load:function(supplierCombo,records){
									if(records.length==1){
										Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.fsupplierid').setValue(records[0].get("fid"));
									}else if (records.length==0)
									{
										Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.fsupplierid').clearValue();
									}
								}
							}
						})
		}]
			},
			{
				xtype:'fieldset',
				title:'结构及信息列表',
				id:'DJ.order.saleOrder.FistDemandProductEdit.InfoList',
				anchor:'100%',
				style:'margin-top:10px;padding:5px 10px 10px;',
				items:[{
					xtype:'fistDemandProductTree'
				}]
			},hideProductGrid,
			{
					xtype:'fieldset',
				title:'附件列表',
				style:{
					marginTop:'12px'
				},
				items:[SDproductdemandfileGrid]
			}
			]
		});
		this.callParent(arguments);
	}
});

Ext.define('DJ.order.saleOrder.FistDemandProductEdit.SchemeDesignFile',{
	ctype:'Productdemandfile',
	extend : 'Ext.c.GridPanel',
	pageSize : 50,
	alias:'widget.SDproductfile',
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
			id:'DJ.order.saleOrder.FistDemandProductEdit.SchemeDesignFile.productActions',
	        items: [
	        {
	            icon: 'images/delete.gif',
	            tooltip: '删除附件',
	            handler: function(grid, rowIndex, colIndex) {
	                var fid = grid.getStore().getAt(rowIndex).get('fid');
	                var fpath = grid.getStore().getAt(rowIndex).get('fpath');
	                var records=Ext.getCmp("DJ.order.saleOrder.FistDemandProduct.ProductTree").getSelectionModel().getSelection();
	                var firstfid=Ext.getCmp("DJ.order.saleOrder.FistDemandProductEdit.fid").getValue()
			    	if(records.length==0){
							Ext.MessageBox.alert("错误","请选择一条产品记录再删除附件！");
							return;
					}
	                Ext.Ajax.request({
	                	url:'delFirstProductfile.do',
	                	params:{
	                		fid : fid,
	                		fparentid : records[0].get("id"),
	                		fpath : fpath,
	                		firstfid:firstfid

	                	},
	                	success:function(res){
	                		var obj = Ext.decode(res.responseText);
	                		if(obj.success){
	                			djsuccessmsg(obj.msg);
	                			Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.SchemeDesignFile').getStore().load();
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

Ext.define('DJ.order.saleOrder.FistDemandProductEdit.loadupWin', {
	extend : 'Ext.Window',
	id : 'DJ.order.saleOrder.FistDemandProductEdit.loadupWin',
	modal : true,
	// title : "E上传",
	width : 450,// 230, //Window宽度
	height : 300,// 137, //Window高度
	resizable : false,
	closable : true, // 关闭按钮，默认为true
	// renderTo: 'upload',
	items : [{
		xtype : 'form',
		id : 'DJ.order.saleOrder.FistDemandProductEdit.loadupWin.form',
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
			id:'DJ.order.saleOrder.FistDemandProductEdit.loadupWin.form.ftype',
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
				var firstfid=Ext.getCmp("DJ.order.saleOrder.FistDemandProductEdit.fid").getValue();
				var parentid = form.findField('fparentid').getValue();
				var ftype = Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.loadupWin.form.ftype').getValue();
				var fdescription = form.findField('fdescription').getValue();
//				var fname = form.findField('fileName').getValue();
//				var fname = Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.loadupWin.form.fileName').getValue();
				var paramsArray = [];
				
								// 解决特殊字符的编码问题
								
								paramsArray.push("?firstfid");
								paramsArray.push("=" + encodeURIComponent(firstfid) + "&");
								paramsArray.push("fparentid");
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
							url:'uploadFirstProductFile.do'+paramsT,
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
									var store = Ext.getCmp('DJ.order.saleOrder.FistDemandProductEdit.SchemeDesignFile').getStore();
									store.setDefaultfilter(myfilter);
									store.setDefaultmaskstring(" #0 ");
									store.load();
										var windows = Ext.getCmp("DJ.order.saleOrder.FistDemandProductEdit.loadupWin");
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
