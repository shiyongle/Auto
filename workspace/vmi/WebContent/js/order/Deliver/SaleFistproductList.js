Ext.require(["DJ.myComponent.form.MainSchemeDesignListDeliverButton", 'Ext.ux.grid.MyGridItemDblClick']);

Ext.define('DJ.order.Deliver.SaleFistproductList', {
	extend : 'Ext.panel.Panel',
	id : 'DJ.order.Deliver.SaleFistproductList',
	autoScroll : false,
	border : false,
	layout : 'border',
	title:'我的需求',
	closable:true,
	
	
	
	items:
			[{
				region : 'center',
				items : [ Ext.create("DJ.order.Deliver.FistproductdemandList",{
					id:'DJ.order.Deliver.SaleFistproductdemandList',
					url:'getAllFistproductdemand.do',
					selModel : Ext.create('Ext.selection.CheckboxModel'),
					fields:[{name:'fid'},{name:'cname',myfilterfield : 'c.fname',myfiltername : '客户名称',myfilterable : true},
					        {name:'fname',myfilterfield : 'f.fname',myfiltername : '需求名称',myfilterable : true},{name:'fnumber',myfilterfield : 'f.fnumber',myfiltername : '需求编号',myfilterable : true},{name:'fdescription'},{name:'fboxlength'},
					        {name:'fboxwidth'},{name:'fboxheight'},{name:'fboardlength'},{name:'fboardwidth'},
					        {name:'fsuppliername',myfilterfield : 'sp.fname',myfiltername : '制造商',myfilterable : true},{name:'fsupplierid'},
					        {name:'fcreatid'},{name:'fcreatetime'},{name:'fupdateuserid'},{name:'fupdatetime'},
					        {name:'fauditorid'},{name:'fauditortime',myfilterfield : 'f.fauditortime',myfiltername : '发布时间',myfilterable : true},{name:'isfauditor'},{name:'fcustomerid'},
					        {name:'fsupplierid'},{name:'falloted'},{name:'fallotor'},{name:'fallottime'},{name:'freceived'},
					        {name:'freceiver',myfilterfield : 'u2.fname',myfiltername : '设计师',myfilterable : true},{name:'freceiverTel'},{name:'freceivetime',myfilterfield : 'f.freceivetime',myfiltername : '接收时间',myfilterable : true},{name:'fname'},{name:'ftype'},{name:'fcostneed'},{name:'fiszhiyang',myfilterfield : 'fiszhiyang',myfiltername : '是否制样(1是、0否)',myfilterable : true},{name:'famount'}
					        ,{name:'foverdate',myfilterfield : 'f.foverdate',myfiltername : '方案入库日期',myfilterable : true},{name:'farrivetime'},{name:'fboxpileid'},{name:'fmaterial'},{name:'fcorrugated'},{name:'fprintcolor'},{name:'fprintbarcode'},{name:'funitestyle'}
					        ,{name:'fprintstyle'},{name:'fsurfacetreatment'},{name:'fpackstyle'},{name:'fpackdescription'},{name:'fisclean'},{name:'fispackage'},{name:'fpackagedescription'},{name:'fislettering'},{name:'fletteringescription'},
					        {name:'fstate',myfilterfield : 'f.fstate',myfiltername : '需求状态',myfilterable : true},{name:'flinkman'},{name:'flinkphone'}],
					onload : function() {
						var sstate=this.down('toolbar').getComponent('scombo');
							sstate.fireEvent('select', sstate,sstate.setValue("-1"));
					},
					plugins : [{
	
		ptype : 'mygriditemdblclick',
		dbClickHandler : 'button[text=查   看]'
		
	}],
					custbar : [{
						text : '发布',
						height : 30,
//						id : "DJ.order.Deliver.FistproductdemandList.Audit",
						handler : function() {
							var grid = Ext.getCmp('DJ.order.Deliver.SaleFistproductdemandList');
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
								url:'fauditorFproductdemand.do',
								params:{
									fids:result
								},
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										djsuccessmsg(obj.msg);
										Ext.getCmp("DJ.order.Deliver.SaleFistproductdemandList").store
												.load();
//										console.log(Ext.getCmp("DJ.order.Deliver.FistproductdemandList").title);
									} else {
										Ext.MessageBox.alert('错误', obj.msg);

									}
									el.unmask();
								}
							})
						}
					},{
						text : '取消发布',
						height : 30,
//						id : "DJ.order.Deliver.FistproductdemandList.UnAudit",
						handler : function() {
							var grid = Ext.getCmp('DJ.order.Deliver.SaleFistproductdemandList');
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
										Ext.getCmp("DJ.order.Deliver.SaleFistproductdemandList").store
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
//						hidden:true,
						handler:function(){
							var grid = Ext.getCmp('DJ.order.Deliver.SaleFistproductdemandList');
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
										Ext.getCmp("DJ.order.Deliver.SaleFistproductdemandList").store
												.load();

									} else {
										Ext.MessageBox.alert('错误', obj.msg);

									}
									el.unmask();
								}
							})
						}
					},{
						text:'反关闭',
//						hidden:true,
						handler:function(){
							var grid = Ext.getCmp('DJ.order.Deliver.SaleFistproductdemandList');
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
										Ext.getCmp("DJ.order.Deliver.SaleFistproductdemandList").store
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
					}
					
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
//							Ext.getCmp('DJ.order.saleOrder.SaleSchemeDesignList').getStore().load({params : {
//								firstproductid : item.get('fid')
//								
//							}});
							var store = Ext.getCmp('DJ.order.saleOrder.SaleSchemeDesignList').getStore();
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
							Ext.getCmp('DJ.order.Deliver.SaleFistproductdemandList.exportbutton').setVisible(false);
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
					id:'DJ.order.saleOrder.SaleSchemeDesignList',
					url:'getProductSchemeDesignList.do',
					selModel : Ext.create('Ext.selection.CheckboxModel'),
					listeners :{
						render:function(){
							Ext.getCmp('DJ.order.saleOrder.SaleSchemeDesignList.addbutton').setVisible(false);
							Ext.getCmp('DJ.order.saleOrder.SaleSchemeDesignList.editbutton').setVisible(false);
							Ext.getCmp('DJ.order.saleOrder.SaleSchemeDesignList.viewbutton').setVisible(false);
							Ext.getCmp('DJ.order.saleOrder.SaleSchemeDesignList.delbutton').setVisible(false);
							Ext.getCmp('DJ.order.saleOrder.SaleSchemeDesignList.refreshbutton').setVisible(false);
							Ext.getCmp('DJ.order.saleOrder.SaleSchemeDesignList.querybutton').setVisible(false);
							Ext.getCmp('DJ.order.saleOrder.SaleSchemeDesignList.exportbutton').setVisible(false);
							Ext.getCmp('DJ.order.saleOrder.SaleSchemeDesignList.exportbutton').setVisible(false);
						}
						
					},
					plugins : [{
	
		ptype : 'mygriditemdblclick',
		dbClickHandler : 'button[text=查   看]'
		
	}],
					custbar:[{
						text : '确认',
						hidden:true,
						tooltip:'有特性的方案(唛稿类方案)确认后已直接下单',
						height : 30,
						handler : function() {
							var grid = Ext.getCmp('DJ.order.saleOrder.SaleSchemeDesignList');
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
										
										Ext.getCmp("DJ.order.saleOrder.SaleSchemeDesignList").store
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

							var grid = Ext.getCmp('DJ.order.saleOrder.SaleSchemeDesignList');
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
										Ext.getCmp("DJ.order.saleOrder.SaleSchemeDesignList").store
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
					}
					,'-',{
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
						text:'评价',
						handler:function(){
							var grid = Ext.getCmp('DJ.order.saleOrder.SaleSchemeDesignList');
							var records = grid.getSelectionModel().getSelection();
							if(records.length!=1){
								Ext.Msg.alert('提示','请选择一条数据！')
								return;
							}
							var win = Ext.create('DJ.order.Deliver.AppraiseEdit',{
								schemeDensignId:records[0].get('fid')
							});
							win.setparent('DJ.order.saleOrder.SaleSchemeDesignList');
							win.seteditstate('edit');
							win.show();
						}
					},{
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
