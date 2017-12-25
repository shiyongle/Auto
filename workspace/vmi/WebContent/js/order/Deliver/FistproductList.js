Ext.require(["DJ.myComponent.form.MainSchemeDesignListDeliverButton", 'Ext.ux.grid.MyGridItemDblClick','DJ.tools.grid.MyGridTools','Ext.ux.grid.print.MySimpleGridPrinterComponent','DJ.tools.myPrint.MySimpleGridPrinter']);

//Ext.DomHelper.append(document.getElementsByTagName("head")[0],
Ext.define('DJ.order.Deliver.FistproductList', {
	extend : 'Ext.panel.Panel',
	id : 'DJ.order.Deliver.FistproductList',
	autoScroll : false,
	border : false,
	layout : 'border',
	title:'我的需求',
	closable:true,
	
	items:
			[{
				region : 'center',
				items : [ Ext.create("DJ.order.Deliver.FistproductdemandList",{
					title:'我的需求',
					exporturl : "exportProductDemandList.do",
					onload : function() {
						
						var me = this;
						
						
							MyCommonToolsZ.setComAfterrender(me, function() {

					

					

				});
						
						
						
							
							
// MyGridTools.rebuildExcelAction(this);
						
						var sstate=this.down('toolbar').getComponent('scombo');
							sstate.fireEvent('select', sstate,sstate.setValue("-1"));
					},
					plugins : [{
						ptype : 'mygriditemdblclick',
						dbClickHandler : 'button[text=查   看]'
					}, Ext.create('Ext.grid.plugin.CellEditing', {
			            clicksToEdit: 1,
			            listeners:{
			            	beforeedit:function(editor, e){
			            		if(e.record.get('isfauditor')=='true'){
			            			return false;
			            		}
			            	},
			            	edit:function(editor, e){
			            		if(e.column.dataIndex=='fsuppliername' && e.grid.chooseSupplierModel){
			            			var record = e.record;
			            			Ext.Ajax.request({
			            				url:'updateSupplierOfProductDemand.do',
			            				params:{
			            					fsupplierid : e.value,
			            					fid : record.get('fid')
			            				},
			            				success:function(res){
			            					var obj = Ext.decode(res.responseText);
			            					if(obj.success){
			            						record.set('fsuppliername',e.grid.chooseSupplierModel.get('fname'));
			            					}else{
			            						record.set('fsuppliername',e.originalValue);
			            					}
			            					e.grid.chooseSupplierModel = '';
			            				}
			            			})
										
			            		}
			            	}
			            }
			        })],
					custbar : [{
						text : '发布',
						height : 30,
//						id : "DJ.order.Deliver.FistproductdemandList.Audit",
						handler:function(){
							var grid = Ext.getCmp('DJ.order.Deliver.FistproductdemandList');
							var records = grid.getSelectionModel().getSelection();
							if(records.length<1){
								Ext.MessageBox.alert("错误","请选择一条记录！");
								return;
							}
							var bool = true,
								result = "('";
							for(var i=0;i<records.length;i++){
								if(!records[i].get('fsupplierid')){
									bool = false;
								}
								result += records[i].get("fid");
								if(i<records.length-1){
									result += "','";
								}
							}
							result+="')";
							if(!bool){
								Ext.MessageBox.confirm('提示','制造商未指定，是否继续发布？',function(id){
									if(id=='yes'){
										requestHandler();
									}
								})
							}else{
								requestHandler();
							}
							function requestHandler(){
								var el = grid.getEl();
								el.mask("系统处理中,请稍候……");
								Ext.Ajax.request({
									url:'fauditorFproductdemand.do',
									params:{
										fids:result
									},
									success : function(response, option) {
										var obj = Ext.decode(response.responseText);
										if (obj.success) {
											djsuccessmsg(obj.msg);
											Ext.getCmp("DJ.order.Deliver.FistproductdemandList").store
											.load();
										} else {
											Ext.MessageBox.alert('错误', obj.msg);
											
										}
										el.unmask();
									}
								})
							}
						}
					},{
						text : '取消发布',
						height : 30,
//						id : "DJ.order.Deliver.FistproductdemandList.UnAudit",
						handler : function() {
							var grid = Ext.getCmp('DJ.order.Deliver.FistproductdemandList');
							var records = grid.getSelectionModel().getSelection();
							if(records.length<1){
								Ext.MessageBox.alert("错误","请选择一条记录！");
								return;
							}
							var result = "('";
							for(var i=0;i<records.length;i++){
								result += records[i].get("fid");
								if(i<records.length-1){
									result += "','";
								}
							}
							result+="')";
							var el = grid.getEl();
							el.mask("系统处理中,请稍候……");
							Ext.Ajax.request({
								url:'unfauditorFproductdemand.do',
								params:{
									fids:result
								},
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										djsuccessmsg(obj.msg);
										Ext.getCmp("DJ.order.Deliver.FistproductdemandList").store
												.load();

									} else {
										Ext.MessageBox.alert('错误', obj.msg);

									}
									el.unmask();
								}
							})
						}
					},{
						text:'关闭',
						hidden:true,
						handler:function(){
							var grid = Ext.getCmp('DJ.order.Deliver.FistproductdemandList');
							var records = grid.getSelectionModel().getSelection();
							if(records.length<1){
								Ext.MessageBox.alert("错误","请选择一条记录！");
								return;
							}
							var result = "('";
							for(var i=0;i<records.length;i++){
								result += records[i].get("fid");
								if(i<records.length-1){
									result += "','";
								}
							}
							result+="')";
							var el = grid.getEl();
							el.mask("系统处理中,请稍候……");
							Ext.Ajax.request({
								url:'closeProductDemand.do',
								params:{fids:result},
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										djsuccessmsg(obj.msg);
										Ext.getCmp("DJ.order.Deliver.FistproductdemandList").store
												.load();

									} else {
										Ext.MessageBox.alert('错误', obj.msg);

									}
									el.unmask();
								}
							})
						}
					},{
						text:'取消关闭',
						hidden:true,
						handler:function(){
							var grid = Ext.getCmp('DJ.order.Deliver.FistproductdemandList');
							var records = grid.getSelectionModel().getSelection();
							if(records.length<1){
								Ext.MessageBox.alert("错误","请选择一条记录！");
								return;
							}
							var result = "('";
							for(var i=0;i<records.length;i++){
								result += records[i].get("fid");
								if(i<records.length-1){
									result += "','";
								}
							}
							result+="')";
							var el = grid.getEl();
							el.mask("系统处理中,请稍候……");
							Ext.Ajax.request({
								url:'uncloseProductDemand.do',
								params:{fids:result},
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										djsuccessmsg(obj.msg);
										Ext.getCmp("DJ.order.Deliver.FistproductdemandList").store
												.load();
										
									} else {
										Ext.MessageBox.alert('错误', obj.msg);

									}
									el.unmask();
								}
							})
						}
					
					},'-',{
						xtype:'textfield',
						width:120,
						emptyText:'请输入需求名称...',
						itemId:'text',
						enableKeyEvents:true,
						listeners:{
							keypress:function(me,e){
										if(e.getKey()==13){
											this.up('grid').filterByName();
										}
								}
						}
					},{
									
										xtype:'combo',
										itemId:'scombo',
										queryMode:'local',
										store:[["-1","全部"],["存草稿","存草稿"],["已发布","已发布"],["已分配","已分配"],["已接收","已接收"],["已设计","已设计"],["已完成","已完成"],["关闭","已关闭"]],
										listeners:{
											select:function(){
												this.up('grid').filterByName();
											}
										}
					},{
						text:'查找',
						itemId:'search',
						handler:function(){
							this.up('grid').filterByName();
						}
					},{
						text:'打印',
						handler:function(){
							var me = this;
							var record = me.up('grid').getSelectionModel().getSelection(),productDemanid = '';
							if(record.length==0){
								Ext.MessageBox.alert('错误','请选择一条数据！');
								return;
							}
							for(var i = 0;i<record.length;i++){
								productDemanid += record[i].get('fid');
								if(i<record.length-1){
									productDemanid += ',';
								}
							}
							if(!Ext.get('panelPrint')){
								Ext.DomHelper.append(Ext.getBody(), Ext.String.format(
										'<iframe id = "{0}" width="100%" height="100%" />', 'panelPrint'));
							}
							
							
							var win = Ext.create('DJ.tools.myPrint.TemplatePrint');
							win.show().hide();
							
							var AppraiseLisStore = Ext.getCmp('DJ.tools.myPrint.TemplatePrint.view').getStore();
							AppraiseLisStore.load({params:{fids:productDemanid}, callback: function(records, operation, success) {
								Ext.DomHelper.overwrite(Ext.get('panelPrint').dom.contentWindow.document.body, Ext.getCmp('DJ.tools.myPrint.TemplatePrint.view').el.dom.innerHTML);
								Ext.get('panelPrint').dom.contentWindow.focus();
								Ext.get('panelPrint').dom.contentWindow.print();
								win.close();
						    }});
							
						}
					}
//					,{
//						text:'在线沟通'
////						,
////						handler:function(){
////							
////							return "<>"
////						}
////						url: (function(){
////							
////							return Ext.getCmp('DJ.order.Deliver.FistproductList').urlIM;
////							
////						})()
//					}
					],
					filterByName:function(){
						var store = this.getStore();
//						store.setDefaultfilter([{
//							myfilterfield : "f.fname",
//							CompareType : " like ",
//							type : "string",
//							value : this.down('toolbar').getComponent('text').getValue()
//						}]);
//						store.setDefaultmaskstring(" #0 ");
//						store.loadPage(1); 
						var compare="=",vvalue=this.down('toolbar').getComponent('scombo').getValue();

												if(vvalue=="-1")
												{
												   compare="<>";
												   vvalue="已完成";
												}
									
												store.setDefaultfilter([{
													myfilterfield : "f.fstate",
													CompareType : compare,
													type : "string",
													value : vvalue
													},{
													myfilterfield : "f.fname",
													CompareType : " like ",
													type : "string",
													value : this.down('toolbar').getComponent('text').getValue()
													},{
														myfilterfield : "c.fname",
														CompareType : " like ",
														type : "string",
														value : this.down('toolbar').getComponent('text').getValue()
													}]);
												
												store.setDefaultmaskstring(" #0 and (#1 or #2)");
												store.loadPage(1);
					},
					listeners:{
						itemclick:function(record, item, index, e, eOpts ){
//							Ext.getCmp('DJ.order.saleOrder.ProductSchemeDesignList').getStore().load({params : {
//								firstproductid : item.get('fid')
//								
//							}});
							var store = Ext.getCmp('DJ.order.saleOrder.ProductSchemeDesignList').getStore();
							store.setDefaultfilter([{
								myfilterfield : "s.ffirstproductid",
								CompareType : " = ",
								type : "string",
								value : item.get("fid")
							}]);
							store.setDefaultmaskstring(" #0 ");
							store.load();
							
						},
						render:function(){
//							this.down('button[text=接收]').hide();
//							this.down('button[text=取消接收]').hide();
							Ext.getCmp('DJ.order.Deliver.FistproductdemandList.exportbutton').setVisible(true);
						}
					}
				})],
				layout:'fit'
			},
			{
				region : 'south',
				width : 220,
				height:250,
				split:true,
				layout:'fit',
				items : [ Ext.create("DJ.order.saleOrder.SchemeDesignList", {
					id:'DJ.order.saleOrder.ProductSchemeDesignList',
					url:'getProductSchemeDesignList.do',
					selModel : Ext.create('Ext.selection.CheckboxModel'),
					fields:[{name:'fgroupid'},{name:'fid'},{name:'fdescription'},{name:'ffirstproductid'},
					        {name:'fname',myfilterfield : 's.fname',myfiltername : '方案名称',myfilterable : true},
					        {name:'fnumber',myfilterfield : 's.fnumber',myfiltername : '方案编码',myfilterable : true},
					        {name:'fcustomer'},{name:'fsupplier',myfilterfield : 'sp.fname',myfiltername : '制造商',myfilterable : true},{name:'fcreatid'},
					        {name:'fcreator',myfilterfield : 'u1.fname',myfiltername : '创建人',myfilterable : true},
					        {name:'fcreatetime'},
					        {name:'fconfirmed',myfilterfield : 's.fconfirmed',myfiltername : '是否确认（1是 0否）',myfilterable : true},'coname','fconfirmtime','sfid','fauditorid','fqq'],
					Action_AfterViewButtonClick : function(EditUI) {
						Ext.Ajax.request({
							url:"getCustomerSchemedesign.do",
							success : function(response, option) {
								var obj = Ext.decode(response.responseText);
								if (obj.success == false) {
									var grid = Ext.getCmp(EditUI.EditUI);
									grid.down('button[text=确认]').setVisible(false);
									grid.down('button[text=取消确认]').setVisible(false);
								}
							}
						})
					
					},
					listeners :{
						render:function(){
							Ext.getCmp('DJ.order.saleOrder.ProductSchemeDesignList.addbutton').setVisible(false);
							Ext.getCmp('DJ.order.saleOrder.ProductSchemeDesignList.editbutton').setVisible(false);
							Ext.getCmp('DJ.order.saleOrder.ProductSchemeDesignList.viewbutton').setVisible(false);
							Ext.getCmp('DJ.order.saleOrder.ProductSchemeDesignList.delbutton').setVisible(false);
							Ext.getCmp('DJ.order.saleOrder.ProductSchemeDesignList.refreshbutton').setVisible(false);
//							Ext.getCmp('DJ.order.saleOrder.ProductSchemeDesignList.querybutton').setVisible(false);
							Ext.getCmp('DJ.order.saleOrder.ProductSchemeDesignList.exportbutton').setVisible(false);
							
							this.down('#coname').hide();
							Ext.Ajax.request({
								url:"getCustomerSchemedesign.do",
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										var grid = Ext.getCmp('DJ.order.saleOrder.ProductSchemeDesignList');
										if(grid!=undefined){
											grid.down('button[text=确认]').setVisible(true);
											grid.down('button[text=取消确认]').setVisible(true);
										}
										
									}
								}
							})
						}
						
					},
					plugins : [{
	
		ptype : 'mygriditemdblclick',
		dbClickHandler : 'button[text=查   看]'
		
	}],
					custbar:[
					'-',{
						xtype:'textfield',
						width:120,
						emptyText:'请输入方案名称...',
						itemId:'text',
						enableKeyEvents:true,
						listeners:{
							keypress:function(me,e){
										if(e.getKey()==13){
											this.up('grid').filterByName();
										}
								}
						}
					},{
						text:'查找',
						itemId:'search',
						handler:function(){
							this.up('grid').filterByName();
						}
					},{
						text : '确认',
						hidden:true,
						tooltip:'有特性的方案(唛稿类方案)确认后已直接下单',
						height : 30,
						handler : function() {
							var grid = Ext.getCmp('DJ.order.saleOrder.ProductSchemeDesignList');
							var records = grid.getSelectionModel().getSelection();
							if(records.length==0){
								Ext.MessageBox.alert("错误","请选择一条记录！");
								return;
							}
							var result = "'";
							for(var i=0;i<records.length;i++){
								result += records[i].get("fid");
								if(i<records.length-1){
									result += "','";
								}
							}
							result+="'";
//							var el = grid.getEl();
//							el.mask("系统处理中,请稍候……");
							var productdemand = Ext.getCmp('DJ.order.Deliver.FistproductdemandList');
							var productrecord = productdemand.getSelectionModel().getSelection();
							if(productrecord.length==0){
								Ext.MessageBox.alert("错误","请选择一条包装需求！");
								return;
							}
							Ext.Ajax.request({
								url:"AffirmSchemeDesign.do",
								params:{
									fids:result,
									fparentid:productrecord[0].get("fid")
								},
								success : function(response, option) {
								
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										if(obj.total==true){
										Ext.MessageBox.confirm('提示', '是否全部生成配送信息!',function(btn, text){
											if(btn=="yes"){
												var fid ="";
												for(var i=0;i<records.length;i++){
													fid += records[i].get("fid");
													if(i<records.length-1){
														fid += ",";
													}
												}
												//Ext.MessageBox.alert('错误', "未完.待续.....");
												Ext.Ajax.request({
													timeout : 6000,
													url : 'generateDelivery.do',
													params : {
														fids : fid
													},
													success : function(response, option) {

														var obj = Ext.decode(response.responseText);
														if (obj.success == true) {
															Ext.MessageBox.alert('成功', obj.msg);

														} else {
															Ext.MessageBox.alert('错误', obj.msg);

														}
														el.unmask();
													}
												});
											}
										})
										}
										djsuccessmsg(obj.msg);
//										var record = Ext.getCmp('DJ.order.Deliver.FistproductdemandList').getSelectionModel().getSelection();
										
										Ext.getCmp("DJ.order.saleOrder.ProductSchemeDesignList").store
												.load({params : {
													firstproductid : productrecord[0].get('fid')
												}});
//										productdemand.getStore().load({
//											
//											 callback: function(records, operation, success) {
//												 productdemand.getSelectionModel().select(productrecord[0].index);
//											    }
//											
//										});
										
										
									} else {
										Ext.MessageBox.alert('错误', obj.msg);

									}
//									el.unmask();
								}
							})
						}
					},{
						text : '取消确认',
						height : 30,
						hidden:true,
						handler : function() {

							var grid = Ext.getCmp('DJ.order.saleOrder.ProductSchemeDesignList');
							var records = grid.getSelectionModel().getSelection();
							if(records.length==0){
								Ext.MessageBox.alert("错误","请选择一条记录！");
								return;
							}
							var result = "'";
							for(var i=0;i<records.length;i++){
								result += records[i].get("fid");
								if(i<records.length-1){
									result += "','";
								}
							}
							result+="'";
//							var el = grid.getEl();
//							el.mask("系统处理中,请稍候……");
							var productdemand = Ext.getCmp('DJ.order.Deliver.FistproductdemandList');
							var productrecord = productdemand.getSelectionModel().getSelection();
							if(productrecord.length==0){
								Ext.MessageBox.alert("错误","请选择一条包装需求！");
								return;
							}
							Ext.Ajax.request({
								url:"UnAffirmSchemeDesign.do",
								params:{
									fids:result,
									fparentid:productrecord[0].get("fid")
								},
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										djsuccessmsg(obj.msg);
//										var record = Ext.getCmp('DJ.order.Deliver.FistproductdemandList').getSelectionModel().getSelection();
										Ext.getCmp("DJ.order.saleOrder.ProductSchemeDesignList").store
												.load({params : {
													firstproductid : productrecord[0].get('fid')
												}});
//										productdemand.getStore().load({
//											
//											 callback: function(records, operation, success) {
//												 productdemand.getSelectionModel().select(productrecord[0].index);
//											    }
//											
//										});
											
									} else {
										Ext.MessageBox.alert('错误', obj.msg);

									}
//									el.unmask();
								}
							})
						
						}
					},{
						text:'评价',
						handler:function(){
							var grid = Ext.getCmp('DJ.order.saleOrder.ProductSchemeDesignList');
							var records = grid.getSelectionModel().getSelection();
							if(records.length!=1){
								Ext.Msg.alert('提示','请选择一条数据！')
								return;
							}
							var win = Ext.create('DJ.order.Deliver.AppraiseEdit',{
								schemeDensignId:records[0].get('fid')
							});
							win.setparent('DJ.order.saleOrder.ProductSchemeDesignList');
							win.seteditstate('edit');
							win.show();
						}
					},
					{
						text:'查看',
						handler:function(me){
							Ext.Ajax.request({
								url:"getCustomerSchemedesign.do",
								success : function(response, option) {
									var grid = me.up('grid');
									var records = grid.getSelectionModel().getSelection();
									if(records.length==0){
										Ext.MessageBox.alert("错误","请选择一条记录！");
										return;
									}
									var panel = Ext.create('DJ.order.saleOrder.SchemeDesignTabPanel',{
										schemeDesignId:records[0].get('fid')
									});
									panel.parentid = me.up('grid').id;
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										panel.show();
									}else{
										panel.down('panel').down('panel').down('button[text=确认]').hide();
										panel.down('panel').down('panel').down('button[text=取消确认]').hide();
										panel.show();
									}
								}
							})
							
						}
					}
					],
					filterByName:function(){
						var store = this.getStore();
						store.setDefaultfilter([{
							myfilterfield : "s.fname",
							CompareType : " like ",
							type : "string",
							value : this.down('toolbar').getComponent('text').getValue()
						}]);
						store.setDefaultmaskstring(" #0 ");
						store.loadPage(1); 
					}
				})]
				}
			]
	       
});
