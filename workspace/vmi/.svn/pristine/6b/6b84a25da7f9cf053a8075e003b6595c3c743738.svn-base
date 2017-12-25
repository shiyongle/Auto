Ext.QuickTips.init();
var supplierCombo = {
		valueField : 'fid', 
		name : "fsupplierid",
		id:'DJ.order.Deliver.DeliversCustEdit.fsupplierid',
		xtype : 'combobox',
		displayField : 'fname',
		fieldLabel : '制造商',
//		editable:false,
		queryMode: 'local',
		typeAhead : true,
		forceSelection : true,
		width : 300,
		labelWidth : 100
};
Ext.define('DJ.order.Deliver.DeliversCustEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.order.Deliver.DeliversCustEdit",
	modal : true,
	onload : function() {
		var store=Ext.getCmp('DJ.order.Deliver.DeliversCustEdit.fsupplierid').store;
		store.load();
	},
	title : "我的订单编辑界面",
	width : 680,// 230, //Window宽度
	height : 285,// 137, //Window高度
	resizable : false,
	url : 'SaveCustDeliverapply.do',
	infourl : 'getCustDeliverapplyInfo.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'getCustDeliverapplyInfo.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true
	listeners:{
		beforeshow:function(){
			var me = this;
			
			var cutidF = me.down('cCombobox[name=fcustomerid]');
			cutidF.hide();
			
			if(this.editstate=='add'){
				
				Ext.Ajax.request({
					url:'gainCurrentCusID.do',
					success:function(res){
						var obj = Ext.decode(res.responseText);
						if(obj.success){
							cutidF.setmyvalue({
							
								fid : obj.data[0].fcustmerid,
								fname : ""
								
							});
						} else {
						
							Ext.MessageBox.alert('错误', obj.msg);
							me.close();
						}
					}
				});
				
			}else
			{
							me.down("button[text=下单方式切换]").hide();

			}
		}
	},
	custbar: [{
		text: '下单方式切换',
		handler: function(){
			var win = this.up('window'),
				relatedPanel = win._relatedPanel;
			win.close();
			relatedPanel.switchOrderType('multi');
		}
	}],
	initComponent : function() {
		var win = this;
		Ext.apply(this, {
			items : [{
				layout : "column",
				baseCls : "x-plain",
				items : [{// title:"列1",
					baseCls : "x-plain",
					columnWidth : .5,
					bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
					items : [{
						name : 'fid',
						xtype : 'textfield',
						labelWidth : 100,
						width : 300,
						hidden : true
					}, {
						name : 'fcreatorid',
						xtype : 'textfield',
						labelWidth : 100,
						width : 300,
						hidden : true
					}, {
						name : 'fupdateuserid',
						xtype : 'textfield',
						labelWidth : 100,
						width : 300,
						hidden : true
					}, {
						id : 'DJ.order.Deliver.DeliversCustEdit.ftraitid',
						name : 'ftraitid',
						xtype : 'textfield',
						labelWidth : 100,
						width : 300,
						hidden : true
					}, {
						id : 'DJ.order.Deliver.DeliversCustEdit.FNUMBER',
						name : 'fnumber',
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : '采购订单号',
						readOnlyCls:'x-item-disabled',
						// allowBlank : false,
						// blankText : '采购订单号不能为空',
						regex : /^([\u4E00-\u9FA5]|\w|[@.()\-])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						regexText : "不能包含特殊字符",
						width : 300,
						labelWidth : 100,
						hidden:true
					},{
						name : 'fordernumber',
						xtype : 'textfield',
						fieldLabel : '采购订单号',
						regex : /^([\u4E00-\u9FA5]|\w|[@.()\-])*$/,
						regexText : "不能包含特殊字符",
						width : 300,
						labelWidth : 100
					},{
						valueField : 'fid', // 组件隐藏值
						id : "DJ.order.Deliver.DeliversCustEdit.fcusproductid",
						name : "fcusproductid",
						xtype : 'cCombobox',
						displayField : 'fname',// 组件显示值
						fieldLabel : '包装物名称/编号 ',
						width : 300,
						labelWidth : 100,
						allowBlank:false,
//						editable:false,
						listeners : {
							select : function(combo, records, eOpts) {
							 Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.fcharacter").setValue(records[0].get("fcharactername"));
							 Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.ftraitid").setValue(records[0].get("fcharacterid"));
							 var balanceFiled = Ext
										.getCmp("DJ.order.Deliver.DeliversCustEdit.balance");
//							var ftraitid=Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.ftraitid").getValue();

//								var me = this;
//								var el = me.getEl();
//								el.mask("系统处理中,请稍候……");
								 var customerid = Ext
									.getCmp("DJ.order.Deliver.DeliversCustEdit.fcustomerid")
									.getValue();// _combo.getValue();

								Ext.Ajax.request({
									timeout : 60000,
									url:"countBalanceqtyByCusproductandCustomer.do",
									params : {
										cusproduct : records[0].get("fid"),
										customerid :customerid,
										ftraitid:records[0].get("fcharacterid")
									},

									success : function(response, option) {

										var obj = Ext
												.decode(response.responseText);
										if (obj.success == true) {

											balanceFiled
													.setValue(obj.data[0].balanceqty);

										} else {
											Ext.MessageBox.alert('错误', obj.msg);

										}
//										el.unmask();
									}
								});

							},
							change : function(com, newValue, oldValue, eOpts) {
								Ext.tip.QuickTipManager.register({
									target : "DJ.order.Deliver.DeliversCustEdit.fcusproductid",
									text : com.getRawValue()
									
								});

							},
							render: function() {
								
								Ext.tip.QuickTipManager.register({
									target : "DJ.order.Deliver.DeliversCustEdit.fcusproductid"
//										,
//									title : ''
									
									
								});
							}
						},

						beforeExpand : function() {
							

							var customerid = Ext
									.getCmp("DJ.order.Deliver.DeliversCustEdit.fcustomerid")
									.getValue();// _combo.getValue();
							Ext
									.getCmp("DJ.order.Deliver.DeliversCustEdit.fcusproductid")
									.setDefaultfilter([{
										myfilterfield : "t_bd_Custproduct.fcustomerid",
										CompareType : "like",
										type : "string",
										value : customerid
									},{
										myfilterfield : "t_bd_Custproduct.feffect",
										CompareType : "=",
										type : "int",
										value : 1
									}]);
							Ext
									.getCmp("DJ.order.Deliver.DeliversCustEdit.fcusproductid")
									.setDefaultmaskstring(" #0 and #1");
									
							var grid = Ext.getCmp('DJ.order.Deliver.DeliversCustEdit.custproductList');
							grid.down('toolbar').hide();
						},
						MyConfig : {
							id:'DJ.order.Deliver.DeliversCustEdit.custproductList',
							width : 600,// 下拉界面
							height : 200,// 下拉界面
							url : 'selectCustProductList.do', // 下拉数据来源
							ShowImg: true,
							fields : [{
								name : 'fid'
							}, {
								name : 'fname',
								myfilterfield : 't_bd_Custproduct.fname', // 查找字段，发送到服务端
								myfiltername : '名称', // 在过滤下拉框中显示的名称
								myfilterable : true
									// 该字段是否查找字段
									}, {
										name : 'fnumber',
										myfilterfield : 't_bd_Custproduct.fnumber', // 查找字段，发送到服务端
										myfiltername : '编码', // 在过滤下拉框中显示的名称
										myfilterable : true
									// 该字段是否查找字段
									},{
										name:'fcharactername'
										,
										myfilterfield : 't_bd_Custproduct.fcharactername', // 查找字段，发送到服务端
										myfiltername : '特性', // 在过滤下拉框中显示的名称
										myfilterable : true
									}, {
										name : 'fspec'
									}, {
										name : 'forderunit'
									}, {
										name : 'fcustomerid'
									}, {
										name : 'fdescription'
									}, {
										name : 'fcreatorid'
									}, {
										name : 'fcreatetime'
									}, {
										name : 'flastupdateuserid'
									}, {
										name : 'flastupdatetime'
									}, {
										name : 'fcharacterid'
									}

							],
							columns : [{
								'header' : 'fid',
								'dataIndex' : 'fid',
								hidden : true,
								hideable : false,
								autoHeight : true,
								autoWidth : true,
								sortable : true
							}, {
								'header' : 'fcharacterid',
								'dataIndex' : 'fcharacterid',
								hidden : true,
								hideable : false
							}, {
								'header' : '包装物名称',
								'dataIndex' : 'fname',
								sortable : true,
								width : 200,
								filter : {
									type : 'string'
								}
							}, {
								'header' : '包装物编码',
								'dataIndex' : 'fnumber',
								width : 200,
								sortable : true,
								filter : {
									type : 'string'
								}
							}, {
								'header' : '规格',
								width : 100,
								'dataIndex' : 'fspec',
								sortable : true
							}, {
								'header' : '特性',
								'dataIndex' : 'fcharactername',
								filter : {
									type : 'string'
								},
								sortable : true
//							}, {
//								'header' : '单位',
//								width : 70,
//								'dataIndex' : 'forderunit',
//								sortable : true
//							}, {
//								'header' : '客户',
//								hidden : true,
//								'dataIndex' : 'fcustomerid',
//								sortable : true
//							}, {
//								'header' : '修改时间',
//								'dataIndex' : 'flastupdatetime',
//								filter : {
//									type : 'datetime',
//									date : {
//										format : 'Y-m-d'
//									},
//									time : {
//										format : 'H:i:s A',
//										increment : 1
//									}
//								},
//								width : 140,
//								sortable : true
//							}, {
//								'header' : '创建时间',
//								'dataIndex' : 'fcreatetime',
//								filter : {
//									type : 'datetime',
//									date : {
//										format : 'Y-m-d'
//									},
//									time : {
//										format : 'H:i:s A',
//										increment : 1
//									}
//								},
//								width : 140,
//								sortable : true
//							}, {
//								'header' : '描述',
//								hidden : true,
//								'dataIndex' : 'fdescription',
//								sortable : true
							}]
						}
//					}, {
//						name : 'flinkman',
//						xtype : 'textfield',
//						fieldLabel : '联系人',
//						width : 300,
//						labelWidth : 100
					}, {
//						name : 'flinkphone',
//						xtype : 'textfield',
//						fieldLabel : '联系电话',
//						// regex : /^1[3|4|5|8][0-9]\d{4,8}$/,//
//						// /^[^,\!@#$%^&*()_+}]*$/,
//						// regexText : "你输入的不是手机号",
//						width : 300,
//						labelWidth : 100
					id : "DJ.order.Deliver.DeliversCustEdit.faddressid",
					valueField : 'fid', // 组件隐藏值
						name : "faddressid",
						xtype : 'cCombobox',
						width : 300,
						labelWidth : 100,
						displayField : 'fname',// 组件显示值
						fieldLabel : '配送地址 ',
						allowBlank:false,
//						editable:false,
						beforeExpand : function() {
						

							var customerid = Ext
									.getCmp("DJ.order.Deliver.DeliversCustEdit.fcustomerid")
									.getValue();// _combo.getValue();
							
//							var me = this;
//							
//							me.getStore().getProxy( ).setExtraParam( "customerid", customerid );
							
//							me.getStore().load({
//							
//								params : {
//								
//									customerid : customerid
//								},
//								
//								callback : function (records, operation, success) {
//								
//									
//									
//								}
//								
//							});
							
							
							//传值技巧，
							this.setDefaultfilter([{
								myfilterfield : "1",
								CompareType : "=",
								type : "string",
								value : 1
							},{
								myfilterfield : "cd.fcustomerid",
								CompareType : "like",
								type : "string",
								value : customerid
							}]);
							this.setDefaultmaskstring(" #0 or #1 ");
							var grid = Ext.getCmp('DJ.order.Deliver.DeliversCustEdit.addressList');
							
							
							grid.down('toolbar').hide();
						},
						MyConfig : {
							id:'DJ.order.Deliver.DeliversCustEdit.addressList',
							width : 800,// 下拉界面
							height : 200,// 下拉界面
							url : 'getUserToCustAddress.do', // 下拉数据来源
							fields:[ {
								name : 'fid'
							}, {
								name : 'fname',
								myfilterfield : 'ad.fname',
								myfiltername : '名称',
								myfilterable : true
							}, {
								name : 'fnumber',
								myfilterfield : 'ad.fnumber',
								myfiltername : '地址编号',
								myfilterable : true
							}, {
								name : 'flinkman',
								myfilterfield : 'ad.flinkman',
								myfiltername : '联系人',
								myfilterable : true
							}, {
								name : 'fphone',
								myfilterfield : 'ad.fphone', 
								myfiltername : '联系电话',
								myfilterable : true
							},{
								name: 'fdetailaddress'
							}
					        ],
							columns:[ {
											'header' : '地址名称',
											'dataIndex' : 'fname',
											width : 405,
											sortable : true
										}, {
											'header' : '地址编号',
											'dataIndex' : 'fnumber',
											width : 80,
											sortable : true
										}, {
											'header' : '联系人',
											'dataIndex' : 'flinkman',
											width : 80,
											sortable : true
										}, {
											'header' : '联系电话',
											'dataIndex' : 'fphone',
											width : 150,
											sortable : true
										}
							]
						},
						listeners : {
							'select' : function(combo, records, eOpts) {

								var detailaddress = records[0]
										.get("fdetailaddress");
								var form = Ext
										.getCmp("DJ.order.Deliver.DeliversCustEdit")
										.getform().getForm();
								form.findField('faddress')
										.setValue(detailaddress);// _combo.getValue();
								form.findField('flinkman').setValue(records[0]
										.get("flinkman"));// _combo.getValue();
								form.findField('flinkphone')
										.setValue(records[0].get("fphone"));// _combo.getValue();

								//														
							},
							afterrender: function(){
									var me = this;
									if(win.editstate!='add' || me.getValue()){
										return;
									}
									Ext.Ajax.request({
										timeout : 60000,
										url : "getUserDefaultAddress.do",
										success : function(response, option) {
											var obj = Ext.decode(response.responseText);
											if(obj.success){
												var config;
												if(obj.data &&　obj.data.length==1){
													var record = obj.data[0];
													config = {
														fid: record['fid'],
														fname: record['fname']
													};
													me.setmyvalue(config);
													var form = me.up('window').getform().getForm();
													form.findField('faddress')	.setValue(obj.data[0].fname);// _combo.getValue();
													form.findField('flinkman').setValue(obj.data[0].flinkman);// _combo.getValue();
													form.findField('flinkphone').setValue(obj.data[0].fphone);// _combo.getValue();
												}
											}
										}
									});
							}
						}
					}, {

			    	     	xtype:'fieldcontainer',
			    	     width:300,
			    	     hideLabel: true,
			    	     labelWidth : 100,
			    	     height:23,
			    	     items:[{
						xtype : 'button',
						text:'<font color=blue>新增地址</font>',
						width : 100,
//						margin : '5 0 0 5',
						handler : function() {
							var editui = Ext.getCmp("DJ.System.UserAddressEdit");
							var customerFiled = Ext
						.getCmp("DJ.order.Deliver.DeliversCustEdit.fcustomerid");
							if (editui == null) {
								editui = Ext.create('DJ.System.UserAddressEdit');
							}
						
							editui.seteditstate("add");
//							editui.setparent('DJ.order.Deliver.DeliversCustEdit');
//							var editform=editui.getForm.form;
//							var editcustomer=editform.findField("fcustomerid");
							Ext.getCmp("DJ.System.UserAddressEdit").down('cCombobox[name=fcustomerid]').setReadOnly(true);
						    editui.show();
						    
						editui.getform().form.findField("fcustomerid").setmyvalue("\"fid\":\""
										+customerFiled.getValue()  + "\",\"fname\":\""
										+customerFiled.getRawValue() + "\"");
						}
						}]

					}, {
						name : 'flinkman',
						xtype : 'textfield',
						fieldLabel : '联系人',
						width : 300,
						labelWidth : 100
					}, {
						name : 'flinkphone',
						xtype : 'textfield',
						fieldLabel : '联系电话',
						// regex : /^1[3|4|5|8][0-9]\d{4,8}$/,//
						// /^[^,\!@#$%^&*()_+}]*$/,
						// regexText : "你输入的不是手机号",
						width : 300,
						labelWidth : 100
					}]
				}, {	// title:"列2",
					baseCls : "x-plain",
					columnWidth : .5,
					items : [
						
//					{
//						name : "fcustomerid",
//						xtype : "hiddenfield"
//					}
//					,
								
									{
						valueField : 'fid', // 组件隐藏值
						id : "DJ.order.Deliver.DeliversCustEdit.fcustomerid",
						name : "fcustomerid",
						xtype : 'cCombobox',
						displayField : 'fname',// 组件显示值
						fieldLabel : '客户',
//						allowBlank:false,
						editable:false,
						width : 300,
						labelWidth : 100,
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
						},
						listeners : {
							'select' : function(combo, records, eOpts) {
								Ext.getCmp('DJ.order.Deliver.DeliversCustEdit.fcusproductid').setmyvalue({});
								Ext.getCmp('DJ.order.Deliver.DeliversCustEdit.fsupplierid').setValue('');
								var addressFiled = Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.faddressid");
								addressFiled.setmyvalue('');
								var fcustomerid = records[0].get("fid");
								Ext.Ajax.request({
									timeout : 60000,
									url : "selectAddressByCustomer.do",
									params : {
										ftype : 1,
										fcustomerid:fcustomerid
									},
									success : function(response, option) {
										var obj = Ext.decode(response.responseText);
										if (obj.success == true) {
											console.log(1);
											if (obj.data != undefined) {
												var form = Ext.getCmp("DJ.order.Deliver.DeliversCustEdit").getform().getForm();
												addressFiled.setmyvalue("\"fid\":\""
														+ obj.data[0].address + "\",\"fname\":\""
														+ obj.data[0].addressname + "\"");
												form.findField('faddress').setValue(obj.data[0].fdetailaddress);// _combo.getValue();
												form.findField('flinkman').setValue(obj.data[0].flinkman);// _combo.getValue();
												form.findField('flinkphone').setValue(obj.data[0].fphone);// _combo.getValue();
												
											}
										} else {
											Ext.MessageBox.alert('错误', obj.msg);
											
										}
									}
								});
							}
						}
					},
					Ext.apply(supplierCombo,{
						listeners:{
							expand:function(combo, store,index){
								this.setValue('');
								this.getStore().load();
							}
						},
						store:Ext.create('Ext.data.Store',{
							fields: ['fid', 'fname'],
							autoLoad:true,
							proxy:{
								type:'ajax',
								url:'getSupplierForDeliverApply.do',
								//url: 'getSupplierListOfCustomer.do',
						         reader: {
						             type: 'json',
						             root: 'data'
						         }
							},
							listeners:{
								load:function(supplierCombo,records){
									if(records.length==1){
										Ext.getCmp('DJ.order.Deliver.DeliversCustEdit.fsupplierid').setValue(records[0].get('fid'));
										//Ext.getCmp('DJ.order.Deliver.DeliversCustEdit.fsupplierid').setText(records[0].get('fname'));
									}
									// 2015-06-04 by lu  关联东经，默认东经
									else if(records.length>1 && win.editstate=='add'){
										var newArry = Ext.Array.filter(records,function(item,index,records){ 
											  if(item.get('fname')=='东经' )
												  return true;
										  });
										if(newArry.length==1){
											Ext.getCmp('DJ.order.Deliver.DeliversCustEdit.fsupplierid').setValue(newArry[0].get('fid'));
										}
									}
								}
							}
						})
					}),{
					bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
						id : 'DJ.order.Deliver.DeliversCustEdit.famount',
						name : 'famount',
						// xtype : 'numberfield',
						xtype : 'textfield',
						fieldLabel : '配送数量',
						width : 300,
						labelWidth : 100,
						allowBlank : false,
						blankText : '配送数量不能为空',
						regex : /^(?!0)\d{0,10}$/,
						regexText : "请输入不超过10位大于0的数字",

						listeners : {
							change : function(com, newValue, oldValue, eOpts) {

								var balanceField = Ext
										.getCmp("DJ.order.Deliver.DeliversCustEdit.balance");
								var farrivetdate = Ext
										.getCmp("DJ.order.Deliver.DeliversCustEdit.farrivedate");
								var styleT = "color:black";
							    var datevalue=Ext.Date.add(new Date(),Ext.Date.DAY,1);;
								if (Ext.Number.from(newValue, 0) > Ext.Number
										.from(balanceField.getValue(), 0)) {
									styleT = "color:red";
									datevalue=Ext.Date.add(new Date(),Ext.Date.DAY,5);
								}
								var editstate = Ext.getCmp('DJ.order.Deliver.DeliversCustEdit').editstate;
								com.setFieldStyle(styleT);
								if(editstate=="add"){
									farrivetdate.setValue(datevalue);
								
								}
								
							}
						}

					}, {
					
						id : 'DJ.order.Deliver.DeliversCustEdit.balance',
//						name : 'famount',
//						xtype : 'numberfield',
						xtype : 'textfield',
						fieldLabel : '可用库存',
						width : 300,
						labelWidth : 100,
//						allowBlank : false,
						value : 0,
						listeners : {
							change : function(com, newValue, oldValue, eOpts) {

								var famountField = com
										.previousSibling("textfield[id=DJ.order.Deliver.DeliversCustEdit.famount]");

								var styleT = "color:black";

								if (Ext.Number.from(famountField.getValue(), 0) > Ext.Number
										.from(newValue, 0)) {
									styleT = "color:red";
								}

								famountField.setFieldStyle(styleT);
							}
						}
						   	     }, {
						id : 'DJ.order.Deliver.DeliversCustEdit.fcharacter',
						xtype : 'textfield',
						fieldLabel : '特性',
						name : 'fcharacter',
						width : 300,
						labelWidth : 100
					}, {
						 xtype:'fieldcontainer',
			    	     fieldLabel : '配送时间',
			    	     width:300,
			    	     labelWidth : 100,
		        	     layout: {
                                type: 'hbox'
                            },
		        	     items:[{
		        	     	xtype:'datefield',
							id:'DJ.order.Deliver.DeliversCustEdit.farrivedate',
//							labelWidth : 100,
//							width : 100,
							width:90,
							name : 'farrivedate',
							format : 'Y-m-d',
							minValue:　new Date(),
							allowBlank : false,
							blankText : '配送时间不能为空',
							hideLabel: true,
							margin : '0 10 10 0'
		        	     },{
//		        	      	xtype: 'radiogroup',
//		        	      	 hideLabel: true,
////		        	      	 name : 'farrivettime',
//		        	      	 width:100,
//		        	      	 items: [  {
		        	     	
                                            xtype: 'radiofield',
                                            boxLabel: '上午',
                                            inputValue: '1',
                                            name : 'farrivettime',
                                            margin : '0 5 5 0'
                                        },
                                        {
                                            xtype: 'radiofield',
                                            boxLabel: '下午',
                                            inputValue: '2' ,
                                             checked: true,
                                            name : 'farrivettime',
                                              margin : '0 5 5 0'
//                                        }]
						
		        	     }]
					}]
					
				}, {
					// title:"列1",
					baseCls : "x-plain",
					columnWidth : 600,
					bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
					items : [{

						name : 'faddress',
					//	xtype : 'textfield',
    					xtype : 'hidden',
						width : 630,
						fieldLabel : '详细地址',
						labelWidth : 100
					}, {
						name : 'fdescription',
						xtype : 'textfield',
						width : 620,
						fieldLabel : '备  注',
						labelWidth : 100
					}]
				}]
			}]
		}), this.callParent(arguments);
	},
	bodyStyle : "padding-top:15px;padding-left:30px"
// ,listeners : {
// 'beforeshow':function(win)
// {
//// 	var editstate = Ext.getCmp('DJ.order.Deliver.FistproductEdit').editstate;
////			if(editstate=="edit"||editstate=="view"){
////				var record = Ext.getCmp('DJ.order.Deliver.FistproductdemandList').getSelectionModel().getSelection();
////				if (record[0].get('fiszhiyang')=='true'){ 
////					Ext.getCmp('DJ.order.Deliver.FistproductEdit.true').setValue(true); 
////					}else{
////						 Ext.getCmp('DJ.order.Deliver.FistproductEdit.false').setValue(true);
////					}
//// }
// }
});

function formatEffect(value) {
	return value == '1' ? '是' : '否';
}