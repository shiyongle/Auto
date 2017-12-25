Ext.require(["Ext.ux.form.MyMixedSearchBox","Ext.ux.grid.MySimpleGridContextMenu",'Ext.ux.grid.MyGridItemDblClick']);

Ext.define('DJ.order.saleOrder.OrderAffirmedList', {
			extend : 'Ext.c.GridPanel',
			title : "订单信息",
			id : 'DJ.order.saleOrder.OrderAffirmedList',
			alias: ['widget.OrderAffirmedList'],
			pageSize : 50,
			closable : false,// 是否现实关闭按钮,默认为false
			url : 'GetAuditedOrderProductPlans.do?',
			Delurl : "",
			EditUI : "",
			exporturl:"OrderAffirmedtoexect.do",
			requires:'DJ.order.saleOrder.OrderAffirmList',
			onload : function() {
				Ext.getCmp('DJ.order.saleOrder.OrderAffirmedList.addbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.OrderAffirmedList.editbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.OrderAffirmedList.delbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.OrderAffirmedList.viewbutton').hide();
				
				MyGridTools.rebuildExcelAction(this);
				
				this.store.on('beforeload',function( store, operation, eOpts ){
					if(store.autoLoad==true){
						store.autoLoad = false;
						return false;
					}
				})
			},
//			listeners:{
//				afterrender:function( com, eOpts ){
//					com.findProductPlan();
//				}
//			},
			Action_BeforeAddButtonClick : function(EditUI) {
				// 新增界面弹出前事件
			},
			Action_AfterAddButtonClick : function(EditUI) {
				// 新增界面弹出后事件
//				Ext.getCmp("DJ.order.saleOrder.SaleOrderEdit").getform().getForm().findField('fnumber').hide();
			},
			Action_BeforeEditButtonClick : function(EditUI) {
				// 修改界面弹出前事件
			},
			Action_AfterEditButtonClick : function(EditUI) {
				// 修改界面弹出后事件
				// 可对编辑界面进行复制
			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			},
			plugins  : [{

		ptype : "mysimplegridcontextmenu",
		
		useExistingButtons : ["button[text=查看客户产品]",'menuseparator',"button[text=查看产品]","button[text=查看图片]"]//选择器，menuseparator为分隔符。菜单链接到按钮，用这个就不用设置处理器了
//		viewHandler : "button[text=查看客户产品]",//查看处理器或是选择符
//		editHandler : null,//编辑
//		deleteHandler : null,//删除
		//其他菜单项
//		otherItems : [{
//			text : "test submenu",
//			menu : {
//
//				xtype : 'menu',
//				ignoreParentClicks : true,
//				items : [{
//					text : "apple",
//					handler : function() {
//
//						Ext.Msg.alert("apple","apple");
//
//					}
//				}, {
//					text : "color picker",
//					menu : {
//						xtype : "colormenu"
//					}
//				}]
//			}
//
//		}]//其他菜单组件
	},{
		ptype : "mygriditemdblclick",
		dbClickHandler : "button[text=查看客户产品]"
		
	}],
			custbar : [ {
				// id : 'DelButton',
				text : '确认',
				height : 30,
				handler : //actionAudit(1)
					function() {
					var grid = Ext.getCmp("DJ.order.saleOrder.OrderAffirmedList");
					var record = grid.getSelectionModel().getSelection();
					if (record.length <= 0) {
						Ext.MessageBox.alert('提示', '请选中记录确认！');
						return;
					}
					var fid = "";
					for(var i=0;i<record.length;i++){
						if(record[i].get("faffirmed")==1){
							continue;
						}
						if(fid.length<=0){
							fid = record[i].get("fid");
						}else{
							fid = fid + ","+record[i].get("fid");
						}
					}
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "supplierAffirm.do",
						params : {
							fidcls : fid
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								  djsuccessmsg( obj.msg);
								Ext.getCmp("DJ.order.saleOrder.OrderAffirmedList").store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});
				}
			},{
						text : '取消确认',
						height : 30,
						handler : // actionAudit(1)
							function() {
							var grid = Ext.getCmp("DJ.order.saleOrder.OrderAffirmedList");
							var record = grid.getSelectionModel().getSelection();
							if (record.length <= 0) {
								Ext.MessageBox.alert('提示', '请选中记录取消确认！');
								return;
							}
							var fid = "";
							for(var i=0;i<record.length;i++){
								if(record[i].get("faffirmed")==0){
									continue;
								}
								if(fid.length<=0){
									fid = record[i].get("fid");
								}else{
									fid = fid + ","+record[i].get("fid");
								}
							}
								var el = grid.getEl();
							el.mask("系统处理中,请稍候……");
							Ext.Ajax.request({
								url : "supplierUnAffirm.do",
								params : {
									fidcls : fid
								}, // 参数
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										  djsuccessmsg( obj.msg);
										Ext.getCmp("DJ.order.saleOrder.OrderAffirmedList").store.load();
									} else {
										Ext.MessageBox.alert('错误', obj.msg);
									}
									el.unmask();
								}
							});
						}
			},{
				text : '入库',
				height : 30,
				handler : // actionAudit(1)
				function() {
					var grid = Ext.getCmp("DJ.order.saleOrder.OrderAffirmedList");
					var record = grid.getSelectionModel().getSelection();
					if (record.length != 1) {
						Ext.MessageBox.alert('提示', '请选中一条记录入库！');
						return;
					}
					if (record[0].get("faffirmed") == 0
							|| record[0].get("faffirmed") == 'null') {
						Ext.MessageBox.alert('提示', '供应商未确认不能入库！');
						return;
					}
					var fid = record[0].get("fid");
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "GetproductplanToIn.do",
						params : {
							fid : fid
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								var datas = obj.data[0];
								var SaleOrderOIEdit = Ext
										.create('DJ.order.saleOrder.OISaleOrderEdit');
								var finqty = datas.famount - datas.fstockinqty;
								Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.famount").setValue(finqty);
								if(datas.fschemedesignid!=''){
									Ext.Ajax.request({
										url:'getFtraitidBySchemeDesignId.do',
										params:{
											fid : datas.fschemedesignid
										},
										success:function(res){
											var o = Ext.decode(res.responseText);
											if(o.success){
												if(o.data.length>0){
													SaleOrderOIEdit.add({
														xtype:'cCombobox',
														fieldLabel:'特性',
														id:'DJ.order.saleOrder.OISaleOrderEdit.ftraitid',
														name:'ftraitid',
														valueField:'fid',
														displayField:'fcharacter',
														blankText:'请填写客户',
														MyDataChanged:function(ui){
															var field = Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.famount");
															var num = ui[0].get('fentryamount')-ui[0].get('finqty');
															field.setValue(num);
															field.setMaxValue(num);
														},
														MyConfig:{
															width:500,
															height:200,
															hiddenToolbar:true,
															url : 'getFtraitidBySchemeDesignId.do?fid='+datas.fschemedesignid,
															fields:[{
																name:'fid'
															},{
																name:'fcharacter'
															},{
																name:'fentryamount'
															},{
																name:'finqty'
															}],
															columns:[{
																text:'特性',
																dataIndex:'fcharacter',
																sortable:true,
																flex:1
															},{
																text:'配送数量',
																dataIndex:'fentryamount',
																sortable:true,
																flex:1
															},{
																text:'入库数量',
																dataIndex:'finqty',
																sortable:true,
																flex:1
															}]
														}
													});
												}
											}else{
												Ext.Msg.alert('错误',o.msg);
											}
										}
									});
								}
								SaleOrderOIEdit.show();
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FProductID")
										.setValue(datas.fproductdefid);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.forderid")
										.setValue(datas.forderid);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.forderEntryid")
										.setValue(datas.fparentorderid);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fplanid")
										.setValue(fid);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseID")
										.setmyvalue("\"fid\":\"" + datas.fwarehouseid
												+ "\",\"fname\":\"" + datas.wname
												+ "\"");
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseID")
										.setReadOnly(true);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseSiteID")
										.setReadOnly(true);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseID")
										.setVisible(false);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseSiteID")
										.setVisible(false);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseSiteID")
										.setmyvalue("\"fid\":\""
												+ datas.fwarehousesiteid
												+ "\",\"fname\":\"" + datas.wsfname
												+ "\"");
								
								Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.ftype")
										.setValue("0");
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fordertype")
										.setValue(datas.fordertype);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fentryProductType")
										.setValue(datas.fentryProductType);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fassemble")
										.setValue(datas.fassemble);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fiscombinecrosssubs")
										.setValue(datas.fiscombinecrosssubs);
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});

				}
			},{
//				text : '导入东经生产',
			/*	text : '导入接口',
				height : 30,
				handler : //AssageSupplier(1,fids)
				function() {
					var grid = Ext.getCmp("DJ.order.saleOrder.OrderAffirmedList");
					var record = grid.getSelectionModel().getSelection();
					var ids="";
					if(record.length<1){
						Ext.MessageBox.alert("信息","请选择至少一条订单分录导入！");
						return;
					}
					for ( var i = 0; i < record.length; i++) {
						ids +=  record[i].get("fid");
						if(record[i].get("sname")==null || record[i].get("sname")=='')
							{
							Ext.MessageBox.alert("信息","未分配制造商不能导入生产...或数据不同步请刷新！");
							return;
							}
						if(record[i].get("fimportEAS")==1)
						{
						Ext.MessageBox.alert("信息","订单不能重复导入生产...或数据不同步请刷新！");
						return;
						}
						if (i < record.length - 1) {
								ids = ids + ",";
							}
					}
					var el = grid.getEl();
							el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						timeout: 600000,
						url : "ImportEAS.do?",
						params : {
							fidcls:ids
						},
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								  djsuccessmsg( obj.msg);
								Ext.getCmp("DJ.order.saleOrder.OrderAffirmedList").store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});
					
				}
			}
			,{*/
				text : '查看客户产品',
				height : 30,
				handler : function() {
					var grid = Ext.getCmp("DJ.order.saleOrder.OrderAffirmedList");
					var record = grid.getSelectionModel().getSelection();
					var ids="";
					if(record.length!=1){
						Ext.MessageBox.alert("信息","请选择一条记录进行操作");
						return;
					}
					
					var el = grid.getEl();
							el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						timeout: 600000,
						url : "GetCustProductList.do?",
						params : {
							fid:record[0].get("fid")
//							Defaultfilter:"[{\"myfilterfield\":\"p.fid\",\"CompareType\":\" = \",\"type\":\"string\",\"value\":"+record[0].get("fid")+"}]",
//							Defaultmaskstring: "#0" 
						},
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								var details=Ext.getCmp("DJ.order.saleOrder.CustproductDetail");
								if (details != null) {
								} else {
									details = Ext.create('DJ.order.saleOrder.CustproductDetail');
								}
								Ext.getCmp("DJ.order.saleOrder.CustproductDetail.fname").setValue(record[0].get("fname"));
								details.show();
								var store=	details.items.get(1).getStore();
								  store.loadRawData(obj);
							} else {
								Ext.MessageBox.alert('错误',obj.msg);
							}
							el.unmask();
						}
					});
				}
				
				},{
					text : '查看产品',
					height : 30,
					handler : function() {
						var grid = Ext.getCmp("DJ.order.saleOrder.OrderAffirmedList");// Ext.getCmp("DJ.System.UserListPanel")
						var record = grid.getSelectionModel().getSelection();
						if (record.length != 1) {
							Ext.MessageBox.alert('提示', '只能选中一条记录进行查看!');
							return;
						}
						var fid = record[0].get("fproductdefid");
					
						var productdefdetail = Ext.create('DJ.System.product.ProductdefDetails');
						var structtree = Ext.getCmp('DJ.System.product.ProductStructureTree');
						var structStore = structtree.store;
						var rootvalue = {
							expanded : true,
							leaf : false,
							text : record[0].get('fname'),
							id : fid + ",-1",
							fnumber : '',
							fparentnode : "-1"
						};
						structStore.setRootNode(rootvalue);
						productdefdetail.show();
						}
				}, {
					text : '查看图片',
					height : 30,
					handler : function() {
						var grid = Ext.getCmp("DJ.order.saleOrder.OrderAffirmedList");// Ext.getCmp("DJ.System.UserListPanel")
						var record = grid.getSelectionModel().getSelection();
						if (record.length != 1) {
							Ext.MessageBox.alert('提示', '只能选中一条记录进行操作!');
							return;
						}
			
						var win = Ext.getCmp('DJ.System.product.ProductDrawListWin');
						if (win == null) {
							win = Ext.create('DJ.System.product.ProductDrawListWin');
						}
			
						var myfilter = [];
						myfilter.push({
							myfilterfield : "fproductID",
							CompareType : " = ",
							type : "string",
							value : record[0].get("fproductdefid")
						});
			
						var pstore = win.down("grid").getStore();
			
						pstore.setDefaultfilter(myfilter);
						pstore.setDefaultmaskstring(" #0 ");
			
						var me = this;
						// var el = me.getEl();
						// el.mask("系统处理中,请稍候……");
			
						Ext.Ajax.request({
							timeout : 60000,
							url : "selectProductdraws.do?productID="
									+ encodeURIComponent(record[0].get("fproductdefid")),
							success : function(response, option) {
			
								var obj = Ext.decode(response.responseText);
								if (obj.success == true) {
			
									if (obj.data != undefined && !obj.data.length == 0) {
			
										// win.down("grid").productID =
										// encodeURIComponent(record[0].get("d_fid"));
			
										// Ext.getCmp("DJ.System.product.ProductDrawList.fproductID")
										// .setDefaultfilter([{
										// myfilterfield : "fproductID",
										// CompareType : "=",
										// type : "string",
										// value : record[0].get("d_fid")
										// }]);
										win.show();
			
										pstore.load();
			
									} else {
										Ext.Msg.alert("提示", "没有图片");
			
										// win.close( );
			
										Ext.destroy(win);
									}
			
								} else {
									Ext.MessageBox.alert('错误', obj.msg);
			
								}
								// el.unmask();
							}
						});
			
					}
				},{
					text:'查看附件',
					showInMenu : true,
					handler:function(){
						var grid = Ext.getCmp("DJ.order.saleOrder.OrderAffirmedList");
						var record = grid.getSelectionModel().getSelection();
						if (record.length != 1) {
							Ext.MessageBox.alert('提示', '只能选中一条记录进行操作!');
							return;
						}
						var win = Ext.create('Ext.Window',{
							items:{
								xtype:'productfile',
								url:'GetProductfile.do?fid='+record[0].get('fproductdefid')
							}
						});
						win.show();
					}
				},'|',
				
				{
//				xtype : "mymixedsearchbox",
//				useDefaultfilter : true,
//				emptyText : '按制造订单号、产品名称、制造商名称查询...',
//				condictionFields : ['fname', 'fnumber', 'fdescription','sname']
				xtype:'textfield',
				emptyText:'按回车键查询...',
				id:'DJ.order.saleOrder.OrderAffirmedList.text',
				enableKeyEvents:true,
				listeners:{
					keypress:function(me,e){
								if(e.getKey()==13){
									me.up('grid').findProductPlan();
								}
						},
						render:function(){Ext.tip.QuickTipManager.register({
							 target: 'DJ.order.saleOrder.OrderAffirmedList.text',
							    text: '按制造订单号、包装物名称、制造商名称、采购订单号、备注查询...',
							    dismissDelay: 10000 // Hide after 10 seconds hover
							});
						}
						   
				}
			// 参数数组
			},{
				text:'查询',
				handler:function(){
					this.up('grid').findProductPlan();
				}
			},'|',{
				xtype:'combobox',
				displayField: 'name',
			    valueField: 'value',
			    editable : false,
			    id:'DJ.order.saleOrder.OrderAffirmedList.fstate',
			    store:Ext.create('Ext.data.Store', {
					fields : [ 'name', 'value' ],
					data : [ {
						"name" : "全部",
						"value" : "-1"
					},{
						"name" : "待确认",
						"value" : "0"
					}, {
						"name" : "已确认",
						"value" : "1"
					}, {
						"name" : "部分入库",
						"value" : "2"
					}, {
						"name" : "全部入库",
						"value" : '3'
					},{
						"name" : "存量",
						"value" : '4'
					}]
				}),
				value:'0',
				listeners:{
					select:function( combo, records, eOpts ){
						combo.up('grid').findProductPlan();
					}
				}
			}
			],
			findProductPlan:function(){
				var filter = [];
				var stateValue = Ext.getCmp('DJ.order.saleOrder.OrderAffirmedList.fstate').getValue();
				var textValue = Ext.getCmp('DJ.order.saleOrder.OrderAffirmedList.text').getValue();
				if(stateValue==-1){//-1 时显示全部
					filter.push({
						myfilterfield : 1,
						CompareType : "=",
						type : "int",
						value : 1
					})
				}else if(stateValue==4){
					filter.push({
						myfilterfield : 'd.fstockinqty',
						CompareType : ">",
						type : "int",
						value : 0
					},{
						myfilterfield : 'd.fstoreqty',
						CompareType : ">",
						type : "int",
						value : 0
					})
					
				}else{
					filter.push({
						myfilterfield : 'd.fstate',
						CompareType : "=",
						type : "int",
						value : stateValue
					})
				}
				filter.push({
						myfilterfield : "d.fnumber",
						CompareType : "like",
						type : "String",
						value : textValue
					},{
						myfilterfield : "f.fname",
						CompareType : "like",
						type : "String",
						value : textValue
					},{
						myfilterfield : "s.fname",
						CompareType : "like",
						type : "String",
						value : textValue
					},{
						myfilterfield : "d.fdescription",
						CompareType : "like",
						type : "String",
						value : textValue
					},{
						myfilterfield : "d.fpcmordernumber",
						CompareType : "like",
						type : "String",
						value : textValue
					});

					this.getStore().setDefaultfilter(filter);
					if(stateValue==4){
						this.getStore().setDefaultmaskstring(" #0 and #1 and (#2 or #3 or #4 or #5 or #6 )");
					}else{
						this.getStore().setDefaultmaskstring(" #0 and (#1 or #2 or #3 or #4 or #5)");
					}
					
					this.getStore().load();	
			},
			fields : [{
						name : 'fid'
					}, {
						name : 'fnumber',
						myfilterfield : 'd.fnumber',
						myfiltername : '编号',
						myfilterable : true
					}, {
						name : 'cname',
						myfilterfield : 'c.fname',
						myfiltername : '客户名称',
						myfilterable : true
					}, {
						name : 'pname',
						myfilterfield : 'p.fname',
						myfiltername : '包装物名称',
						myfilterable : true
					}, {
						name : 'fname',
						myfilterfield : 'f.fname',
						myfiltername : '包装物名称',
						myfilterable : true
					}, {
						name:'fproductdefid'
					},{
						name : 'sname',
						myfilterfield : 's.fname',
						myfiltername : '制造商名称',
						myfilterable : true
					}, {
						name : 'fspec'
					}, {
						name : 'farrivetime'
					}, {
						name : 'fbizdate'
					}, {
						name : 'famount'
					}, {
						name : 'faudited'
					}, {
						name : 'fordertype'
					}, {
						name : 'fsuitProductID'
					}, {
						name : 'fparentOrderEntryId'
					}, {
						name : 'forderid'
					},{
						name : 'fimportEAS',
						myfilterfield : 'd.fimportEAS',
						myfiltername : '导入',
						myfilterable : true
					}, {
						name : 'fstockoutqty'
					}, {
						name : 'fstockinqty'
					}, {
						name : 'fstoreqty'
					},{
						name : 'faffirmed',
						myfilterfield : 'd.faffirmed',
						myfiltername : '确认',
						myfilterable : true
					}, {
						name : 'fassemble'
					},{
						name : 'fiscombinecrosssubs'
					},{
						name : 'fparentorderid'
						
					},{
						name : 'ftype',
						myfilterfield : 'd.ftype',
						myfiltername : '订单类型',
						myfilterable : true
					},{
						name:'fboxlength'
					},{
						name:'fboxwidth'
					},{
						name:'fboxheight'
					},{
						name:'fschemename'
					},{
						name:'fdescription'
					},{
						name:'fstate'
					},{
						name:'faffirmtime'
					},{
						name:'faffirmer'
					},{
						name:'fcreatetime'
					},{
						name:'fcharacter'
					},{
						name:'fpcmordernumber'
					},'pnumber'],
		columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					},{
						'header' : 'fsuitProductID',
						'dataIndex' : 'fsuitProductID',
						hidden : true,
						hideable : false,
						sortable : true
					},{
						'header' : 'fassemble',
						'dataIndex' : 'fassemble',
						hidden : true,
						hideable : false,
						sortable : true
					},{
						'header' : 'fproductdefid',
						'dataIndex' : 'fproductdefid',
						hidden : true,
						hideable : false,
						sortable : true
					},{
						'header' : 'fiscombinecrosssubs',
						'dataIndex' : 'fiscombinecrosssubs',
						hidden : true,
						hideable : false,
						sortable : true
					},{
						'header' : 'fparentOrderEntryId',
						'dataIndex' : 'fparentOrderEntryId',
						hidden : true,
						hideable : false,
						sortable : true
					},{
						'header' : 'forderid',
						'dataIndex' : 'forderid',
						hidden : true,
						hideable : false,
						sortable : true
					},{
						'header' : 'fparentorderid',
						'dataIndex' : 'fparentorderid',
						hidden : true,
						hideable : false,
						sortable : true
				
					}, {
						'header' : '制造订单号',
						'dataIndex' : 'fnumber',
						width:100,
						sortable : true
					}, {
						'header' : '产品类型',
						'dataIndex' : 'fordertype',
						width:60,
						sortable : true,
						 renderer: function(value){
						        if (value == 1) {
						            return '普通';
						        }else if(value==2){
						        	return '套装';}
					        	
						    }
					
					}, {
						'header' : '订单类型',
						'dataIndex' : 'ftype',
						width:60,
						sortable : true,
						 renderer: function(value){
						        if (value == 0) {
						            return '初始化';
						        }else if(value==1){
						        	return '正常';}
					        	
						    }
					
					},{
						header:'订单状态',
						dataIndex:'fstate',
						renderer:function(val){
							switch(val){
								case '0' : return '待确认';
								break;
								case '1' : return '已确认';
								break;
								case '2' : return '部分入库';
								break;
								case '3' : return '全部入库';
								break;
								default : return '';
							}
//							if(val==0){
//								return '待确认';
//							}else if(val==1){
//								return '已确认';
//							}else if(val==2){
//								return '部分入库';
//							}else if(val==3){
//								return '全部入库';
//							}else{
//								return '';
//							}
						}
					}, {
						'header' : '客户名称',
						width:100,
						'dataIndex' : 'cname',
						sortable : true
					}, {
						'header' : '采购订单号',
						'dataIndex' : 'fpcmordernumber',
						sortable : true
					},{
						'header' : '包装物编码',
						width:180,
						'dataIndex' : 'pnumber',
						sortable : true
					},{
						'header' : '包装物名称',
						width:180,
						'dataIndex' : 'pname',
						sortable : true
					},{
						header:'特性',
						dataIndex:'fcharacter'
					}, {
						'header' : '创建时间',
						'dataIndex' : 'fcreatetime',
//						xtype:'datecolumn',
//						format:'Y-m-d H:i',
						width:130,
						sortable : true
					}, {
						'header' : '确认时间',
						'dataIndex' : 'faffirmtime',
//						xtype:'datecolumn',
//						format:'Y-m-d H:i',
						width:130,
						sortable : true
					},{
						header:'确认人',
						dataIndex:'faffirmer'
					}, {
						'header' : '交期',
						'dataIndex' : 'farrivetime',
//						xtype:'datecolumn',
//						format:'Y-m-d H:i',
						width:130,
						sortable : true
					}, {
						'header' : '数量',
						width:50,
						'dataIndex' : 'famount',
						sortable : true
					}, {
						'header' : '入库',
						width:50,
						'dataIndex' : 'fstockinqty',
						sortable : true
					}, {
						'header' : '出库',
						width:50,
						'dataIndex' : 'fstockoutqty',
						sortable : true
					}, {
						'header' : '存量',
						width:50,
						'dataIndex' : 'fstoreqty',
						sortable : true
					},{
						'header' : '确认',
						width:50,
						'dataIndex' : 'faffirmed',
						hidden : false,
						hideable : false,
						sortable : true,
						renderer: function(value){
					        if (value == 1) {
					            return '是';
					        }
					        else{
					        	return '否';
					        }
					    }
					}, {
						'header' : '导入EAS',
						dataIndex : 'fimportEAS',
						sortable : true,
						renderer: function(value){
						        if (value == 1) {
						            return '是';
						        }
						        else{
						        	return '否';
						        }
						    }
						},  {
						'header' : '制造商名称',
						'dataIndex' : 'sname',
						sortable : true
					}, {
						'header' : '规格',
						'dataIndex' : 'fspec',
						sortable : true
					}, {
						'header' : '业务时间',
						'dataIndex' : 'fbizdate',
						sortable : true
//						xtype:'datecolumn',
//						format:'Y-m-d H:i'
					},{
						'header' : '审核',
						dataIndex : 'faudited',
						sortable : true,
						renderer: function(value){
						        if (value == 1) {
						            return '是';
						        }
						        else{
						        	return '否';
						        }
						    }
					},{
						header:'长度',
						dataIndex:'fboxlength',
						sortable:true
					},{
						header:'宽度',
						dataIndex:'fboxwidth',
						sortable:true
					},{
						header:'高度',
						dataIndex:'fboxheight',
						sortable:true
					},{
						header:'方案名称',
						dataIndex:'fschemename',
						sortable:true
					},{
						header:'备注',
						dataIndex:'fdescription'
					}
					],
					selModel:Ext.create('Ext.selection.CheckboxModel')
		})