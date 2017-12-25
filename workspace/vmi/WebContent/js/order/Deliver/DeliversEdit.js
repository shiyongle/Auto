//Ext.QuickTips.init();
var supplierCombo = {
		valueField : 'fid', 
		name : "fsupplierid",
		id:'DJ.order.Deliver.DeliversEdi.fsupplierid',
		xtype : 'combobox',
		displayField : 'fname',
		fieldLabel : '制造商',
//		editable:false,
		queryMode : 'local',
		typeAhead : true,
		forceSelection : true,
		width : 260,
		labelWidth : 70
};
Ext.define('DJ.order.Deliver.DeliversEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.order.Deliver.DeliversEdit",
	modal : true,
	onload : function() {
//		Ext.getCmp("DJ.order.Deliver.DeliversEdit.fcusproductid").setTooltip("要货申请状态过滤值为数字");
	},
	title : "要货申请编辑界面",
	width : 680,// 230, //Window宽度
	height : 300,// 137, //Window高度
	resizable : false,
	url : 'SaveDeliverapply.do',
	infourl : 'getDeliverapplyInfo.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'getDeliverapplyInfo.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true
	custbar: [{
		text: '下单方式切换',
		handler: function(){
			var owin = this.up('window');
			owin.close();
			var nwin = Ext.create('DJ.order.Deliver.batchdeliversEdit');
			nwin.setparent('DJ.order.Deliver.DeliversList');
			nwin.seteditstate('add');
			nwin.show();
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
						labelWidth : 50,
						width : 260,
						hidden : true
					}, {
						name : 'ftraitid',
						id:'DJ.order.Deliver.DeliversEdit.ftraitid',
						xtype : 'textfield',
						labelWidth : 100,
						width : 300,
						hidden : true
					}, {
						name : 'fcreatorid',
						xtype : 'textfield',
						labelWidth : 50,
						width : 260,
						hidden : true
					}, {
						name : 'fupdateuserid',
						xtype : 'textfield',
						labelWidth : 50,
						width : 260,
						hidden : true
					}, {
						id : 'DJ.order.Deliver.DeliversEdit.FNUMBER',
						name : 'fnumber',
						xtype : 'textfield',
						readOnly : true,
						hidden : true,
						fieldLabel : '申请单号',
						// allowBlank : false,
						// blankText : '采购订单号不能为空',
						regex : /^([\u4E00-\u9FA5]|\w|[@.()\-])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						regexText : "不能包含特殊字符",
						width : 260,
						labelWidth : 70
					}, {
						name : 'fordernumber',
						xtype : 'textfield',
						readOnly : true,
						fieldLabel : '采购订单号',
						// allowBlank : false,
						// blankText : '采购订单号不能为空',
						regex : /^([\u4E00-\u9FA5]|\w|[@.()\-])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						regexText : "不能包含特殊字符",
						width : 260,
						labelWidth : 70
					}, {
						valueField : 'fid', // 组件隐藏值
						id : "DJ.order.Deliver.DeliversEdit.fcusproductid",
						name : "fcusproductid",
						xtype : 'cCombobox',
						displayField : 'fname',// 组件显示值
						fieldLabel : '客户产品 ',
						width : 260,
						labelWidth : 70,
						allowBlank:false,
//						selectOnFocus:true,
//						editable:false,
						listeners : {
							select : function(combo, records, eOpts) {
							 Ext.getCmp("DJ.order.Deliver.DeliversEdit.fcharacter").setValue(records[0].get("fcharactername"));
							 Ext.getCmp("DJ.order.Deliver.DeliversEdit.ftraitid").setValue(records[0].get("fcharacterid"));
							 var balanceFiled = Ext
										.getCmp("DJ.order.Deliver.DeliversEdit.balance");

//								var me = this;
//								var el = me.getEl();
//								el.mask("系统处理中,请稍候……");
								 var customerid = Ext
									.getCmp("DJ.order.Deliver.DeliversEdit.fcustomerid")
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
									target : "DJ.order.Deliver.DeliversEdit.fcusproductid",
									text : com.getRawValue()
									
								});
//								var balanceFiled = Ext
//										.getCmp("DJ.order.Deliver.DeliversEdit.balance");
//
//								var me = this;
////								var el = me.getEl();
////								el.mask("系统处理中,请稍候……");
//
//								Ext.Ajax.request({
//									timeout : 60000,
//									url : "countBalanceqtyByCusproduct.do",
//									params : {
//										cusproduct : newValue
//									},
//
//									success : function(response, option) {
//
//										var obj = Ext
//												.decode(response.responseText);
//										if (obj.success == true) {
//											// Ext.MessageBox.alert('成功',
//											// obj.msg);
//
//											balanceFiled
//													.setValue(obj.data[0].balanceqty);
//
//										} else {
//											Ext.MessageBox.alert('错误', obj.msg);
//
//										}
////										el.unmask();
//									}
//								});

							}
					,

							render: function() {
								
								Ext.tip.QuickTipManager.register({
									target : "DJ.order.Deliver.DeliversEdit.fcusproductid"
//										,
//									title : ''
									
									
								});
							}
							
						},

						beforeExpand : function() {
							var customerid = Ext
									.getCmp("DJ.order.Deliver.DeliversEdit.fcustomerid")
									.getValue();// _combo.getValue();
							Ext
									.getCmp("DJ.order.Deliver.DeliversEdit.fcusproductid")
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
									.getCmp("DJ.order.Deliver.DeliversEdit.fcusproductid")
									.setDefaultmaskstring(" #0 and #1");
						},
						MyConfig : {
							width : 800,// 下拉界面
							height : 200,// 下拉界面
							url : 'GetCustproductList.do', // 下拉数据来源
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
										name:'fcharactername',
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
								'header' : '产品名称',
								'dataIndex' : 'fname',
								sortable : true,
								filter : {
									type : 'string'
								}
							}, {
								'header' : '编码',
								'dataIndex' : 'fnumber',
								sortable : true,
								filter : {
									type : 'string'
								}
							}, {
								'header' : '规格',
								width : 70,
								'dataIndex' : 'fspec',
								sortable : true
							}, {
								'header' : '单位',
								width : 70,
								'dataIndex' : 'forderunit',
								sortable : true
							}, {
								'header' : '客户',
								hidden : true,
								'dataIndex' : 'fcustomerid',
								sortable : true
							}, {
								'header' : '特性',
								'dataIndex' : 'fcharactername',
								filter : {
									type : 'string'
								},
								sortable : true
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
							}, {
								'header' : '描述',
								hidden : true,
								'dataIndex' : 'fdescription',
								sortable : true
							}]
						}
					}, {
						name : 'flinkman',
						xtype : 'textfield',
						fieldLabel : '联系人',
						width : 260,
						labelWidth : 70
					}, {
						name : 'flinkphone',
						xtype : 'textfield',
						fieldLabel : '联系电话',
						// regex : /^1[3|4|5|8][0-9]\d{4,8}$/,//
						// /^[^,\!@#$%^&*()_+}]*$/,
						// regexText : "你输入的不是手机号",
						width : 260,
						labelWidth : 70
					}, {
					valueField : 'fid', // 组件隐藏值
						name : "faddressid",
						xtype : 'cCombobox',
						width : 260,
						labelWidth : 70,
						displayField : 'fname',// 组件显示值
						fieldLabel : '配送地址 ',
						allowBlank:false,
//						editable:false,
						beforeExpand : function() {
							var customerid = Ext
									.getCmp("DJ.order.Deliver.DeliversEdit.fcustomerid")
									.getValue();// _combo.getValue();
							this.setDefaultfilter([{
								myfilterfield : "cd.fcustomerid",
								CompareType : "like",
								type : "string",
								value : customerid
							}]);
							this.setDefaultmaskstring(" #0 ");
						},
						MyConfig : {
							width : 800,// 下拉界面
							height : 200,// 下拉界面
							url : 'getDjCustAddress.do', // 下拉数据来源
							fields : [{
								name : 'fid'
							}, {
								name : 'fname',
								myfilterfield : 'ad.fname',
								myfiltername : '名称',
								myfilterable : true
							}, {
								name : 'fnumber',
								myfilterfield : 'ad.fnumber',
								myfiltername : '编码',
								myfilterable : true
							}, {
								name : 'fcreatorid'
							}, {
								name : 'flastupdateuserid'
							}, {
								name : 'fcontrolunitid'
							}, {
								name : 'fdetailaddress'
							}, {
								name : 'fcountryid'
							}, {
								name : 'fcityidid'
							}, {
								name : 'femail'
							}, {
								name : 'flinkman'
							}, {
								name : 'fphone'
							}, {
								name : 'fprovinceid'
							}, {
								name : 'fdistrictidid'
							}, {
								name : 'fpostalcode'
							}, {
								name : 'ffax'
							}, {
								name : 'fcreatetime'
							}, {
								name : 'flastupdatetime'
							}],
							columns : [{
								'header' : 'fid',
								'dataIndex' : 'fid',
								hidden : true,
								hideable : false,
								sortable : true

							}, {
								'header' : 'fcreatorid',
								'dataIndex' : 'fcreatorid',
								hidden : true,
								hideable : false,
								sortable : true

							}, {
								'header' : 'flastupdateuserid',
								'dataIndex' : 'flastupdateuserid',
								hidden : true,
								hideable : false,
								sortable : true

							}, {
								'header' : 'fprovinceid',
								'dataIndex' : 'fprovinceid',
								hidden : true,
								hideable : false,
								sortable : true

							}, {
								'header' : 'fcountryid',
								'dataIndex' : 'fcountryid',
								hidden : true,
								hideable : false,
								sortable : true

							}, {
								'header' : 'fcityidid',
								'dataIndex' : 'fcityidid',
								hidden : true,
								hideable : false,
								sortable : true

							}, {
								'header' : 'fdistrictidid',
								'dataIndex' : 'fdistrictidid',
								hidden : true,
								hideable : false,
								sortable : true

							}, {
								'header' : 'fcontrolunitid',
								'dataIndex' : 'fcontrolunitid',
								hidden : true,
								hideable : false,
								sortable : true

							}, {
								'header' : '地址名称',
								'dataIndex' : 'fname',
								width : 405,
								sortable : true
							}, {
								'header' : '地址编码',
								'dataIndex' : 'fnumber',
								width : 70,
								sortable : true
							}, {
								'header' : '邮箱',
								'dataIndex' : 'femail',
								width : 50,
								sortable : true
							}, {
								'header' : '联系人',
								'dataIndex' : 'flinkman',
								width : 50,
								sortable : true
							}, {
								'header' : '电话',
								'dataIndex' : 'fphone',
								width : 110,
								sortable : true
							}, {
								'header' : '详细地址',
								'dataIndex' : 'fdetailaddress',
								sortable : true
							}]
						},
						listeners : {
							'select' : function(combo, records, eOpts) {

								var detailaddress = records[0]
										.get("fdetailaddress");
								var form = Ext
										.getCmp("DJ.order.Deliver.DeliversEdit")
										.getform().getForm();
								form.findField('faddress')
										.setValue(detailaddress);// _combo.getValue();
								form.findField('flinkman').setValue(records[0]
										.get("flinkman"));// _combo.getValue();
								form.findField('flinkphone')
										.setValue(records[0].get("fphone"));// _combo.getValue();

								//														
							}
						}
					}, {
						id : 'DJ.order.Deliver.DeliversEdit.fcharacter',
						name:'fcharacter',
						xtype : 'textfield',
						fieldLabel : '特性',
						width : 260,
						labelWidth : 70
					}]
				}, {	// title:"列2",
					baseCls : "x-plain",
					columnWidth : .5,
					bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
					items : [{
						valueField : 'fid', // 组件隐藏值
						id : "DJ.order.Deliver.DeliversEdit.fcustomerid",
						name : "fcustomerid",
						xtype : 'cCombobox',
						displayField : 'fname',// 组件显示值
						fieldLabel : '客  户',
						allowBlank:false,
//						editable:false,
						width : 260,
						labelWidth : 70,
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
					
					},	Ext.apply(supplierCombo,{
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
								url: 'getSupplierListOfCustomer.do',
						         reader: {
						             type: 'json',
						             root: 'data'
						         }
							},
							listeners:{
//								beforeload:function(store){
//									store.getProxy().setExtraParam("fcustomerid",Ext.getCmp("DJ.order.Deliver.DeliversEdit.fcustomerid").getValue());
//								},
								load:function(supplierCombo,records){
									if(records.length==1){
										Ext.getCmp('DJ.order.Deliver.DeliversEdi.fsupplierid.fsupplierid').setValue(records[0].get('fid'));
										//Ext.getCmp('DJ.order.Deliver.DeliversCustEdit.fsupplierid').setText(records[0].get('fname'));
									}
									// 2015-06-04 by lu  关联东经，默认东经
									else if(records.length>1 && win.editstate=='add'){
										var newArry = Ext.Array.filter(records,function(item,index,records){ 
											  if(item.get('fname')=='东经' )
												  return true;
										  });
										if(newArry.length==1){
											Ext.getCmp('DJ.order.Deliver.DeliversEdi.fsupplierid').setValue(newArry[0].get('fid'));
										}
									}
								}
							}
						})
					}), Ext.create('Ext.ux.form.DateTimeField', {
						fieldLabel : '配送时间',
						labelWidth : 70,
						width : 260,
						name : 'farrivetime',
						format : 'Y-m-d',
						allowBlank : false,
						blankText : '配送时间不能为空'
					}), {
						id : 'DJ.order.Deliver.DeliversEdit.famount',
						name : 'famount',
						// xtype : 'numberfield',
						xtype : 'textfield',
						fieldLabel : '配送数量',
						width : 260,
						labelWidth : 70,
						allowBlank : false,
						blankText : '配送数量不能为空',
						regex : /^(?!0)\d{0,10}$/,
						regexText : "请输入不超过10位大于0的数字",

						listeners : {
							change : function(com, newValue, oldValue, eOpts) {

								var balanceField = Ext
										.getCmp("DJ.order.Deliver.DeliversEdit.balance");

								var styleT = "color:black";

								if (Ext.Number.from(newValue, 0) > Ext.Number
										.from(balanceField.getValue(), 0)) {
									styleT = "color:red";
								}

								com.setFieldStyle(styleT);
							}
						}

							// vtype : "numRange",
							// numRange : {
							// begin : "DJ.order.Deliver.DeliversEdit.famount",
							// end : "DJ.order.Deliver.DeliversEdit.balance"
							// }

					}, {
						id : 'DJ.order.Deliver.DeliversEdit.balance',
//						name : 'famount',
//						xtype : 'numberfield',
						xtype : 'textfield',
						fieldLabel : '结存',
						width : 260,
						labelWidth : 70,
//						allowBlank : false,
						value : 0,
						listeners : {
							change : function(com, newValue, oldValue, eOpts) {

								var famountField = com
										.previousSibling("textfield[id=DJ.order.Deliver.DeliversEdit.famount]");

								var styleT = "color:black";

								if (Ext.Number.from(famountField.getValue(), 0) > Ext.Number
										.from(newValue, 0)) {
									styleT = "color:red";
								}

								famountField.setFieldStyle(styleT);
							}
						}
						}]
				}, {
					// title:"列1",
					baseCls : "x-plain",
					columnWidth : 600,
					bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
					items : [{
//						valueField : 'fid', // 组件隐藏值
//						name : "faddressid",
//						xtype : 'cCombobox',
//						width : 260,
//						labelWidth : 70,
//						displayField : 'fname',// 组件显示值
//						fieldLabel : '配送地址 ',
//						allowBlank:false,
////						editable:false,
//						beforeExpand : function() {
//							var customerid = Ext
//									.getCmp("DJ.order.Deliver.DeliversEdit.fcustomerid")
//									.getValue();// _combo.getValue();
//							this.setDefaultfilter([{
//								myfilterfield : "tba.fcustomerid",
//								CompareType : "like",
//								type : "string",
//								value : customerid
//							}]);
//							this.setDefaultmaskstring(" #0 ");
//						},
//						MyConfig : {
//							width : 800,// 下拉界面
//							height : 200,// 下拉界面
//							url : 'GetAddressList.do', // 下拉数据来源
//							fields : [{
//								name : 'fid'
//							}, {
//								name : 'fname',
//								myfilterfield : 'tba.fname',
//								myfiltername : '名称',
//								myfilterable : true
//							}, {
//								name : 'fnumber',
//								myfilterfield : 'tba.fnumber',
//								myfiltername : '编码',
//								myfilterable : true
//							}, {
//								name : 'fcreatorid'
//							}, {
//								name : 'flastupdateuserid'
//							}, {
//								name : 'fcontrolunitid'
//							}, {
//								name : 'fdetailaddress'
//							}, {
//								name : 'fcountryid'
//							}, {
//								name : 'fcityidid'
//							}, {
//								name : 'femail'
//							}, {
//								name : 'flinkman'
//							}, {
//								name : 'fphone'
//							}, {
//								name : 'fprovinceid'
//							}, {
//								name : 'fdistrictidid'
//							}, {
//								name : 'fpostalcode'
//							}, {
//								name : 'ffax'
//							}, {
//								name : 'fcreatetime'
//							}, {
//								name : 'flastupdatetime'
//							}],
//							columns : [{
//								'header' : 'fid',
//								'dataIndex' : 'fid',
//								hidden : true,
//								hideable : false,
//								sortable : true
//
//							}, {
//								'header' : 'fcreatorid',
//								'dataIndex' : 'fcreatorid',
//								hidden : true,
//								hideable : false,
//								sortable : true
//
//							}, {
//								'header' : 'flastupdateuserid',
//								'dataIndex' : 'flastupdateuserid',
//								hidden : true,
//								hideable : false,
//								sortable : true
//
//							}, {
//								'header' : 'fprovinceid',
//								'dataIndex' : 'fprovinceid',
//								hidden : true,
//								hideable : false,
//								sortable : true
//
//							}, {
//								'header' : 'fcountryid',
//								'dataIndex' : 'fcountryid',
//								hidden : true,
//								hideable : false,
//								sortable : true
//
//							}, {
//								'header' : 'fcityidid',
//								'dataIndex' : 'fcityidid',
//								hidden : true,
//								hideable : false,
//								sortable : true
//
//							}, {
//								'header' : 'fdistrictidid',
//								'dataIndex' : 'fdistrictidid',
//								hidden : true,
//								hideable : false,
//								sortable : true
//
//							}, {
//								'header' : 'fcontrolunitid',
//								'dataIndex' : 'fcontrolunitid',
//								hidden : true,
//								hideable : false,
//								sortable : true
//
//							}, {
//								'header' : '地址名称',
//								'dataIndex' : 'fname',
//								width : 405,
//								sortable : true
//							}, {
//								'header' : '地址编码',
//								'dataIndex' : 'fnumber',
//								width : 70,
//								sortable : true
//							}, {
//								'header' : '邮箱',
//								'dataIndex' : 'femail',
//								width : 50,
//								sortable : true
//							}, {
//								'header' : '联系人',
//								'dataIndex' : 'flinkman',
//								width : 50,
//								sortable : true
//							}, {
//								'header' : '电话',
//								'dataIndex' : 'fphone',
//								width : 110,
//								sortable : true
//							}, {
//								'header' : '详细地址',
//								'dataIndex' : 'fdetailaddress',
//								sortable : true
//							}]
//						},
//						listeners : {
//							'select' : function(combo, records, eOpts) {
//
//								var detailaddress = records[0]
//										.get("fdetailaddress");
//								var form = Ext
//										.getCmp("DJ.order.Deliver.DeliversEdit")
//										.getform().getForm();
//								form.findField('faddress')
//										.setValue(detailaddress);// _combo.getValue();
//								form.findField('flinkman').setValue(records[0]
//										.get("flinkman"));// _combo.getValue();
//								form.findField('flinkphone')
//										.setValue(records[0].get("fphone"));// _combo.getValue();
//
//								//														
//							}
//						}
//					}, {
						name : 'faddress',
						xtype : 'textfield',
						width : 580,
						fieldLabel : '详细地址',
						labelWidth : 70
					}, {
						name : 'fdescription',
						xtype : 'textfield',
						width : 580,
						fieldLabel : '备  注',
						labelWidth : 70
					}]
				}]
			}]
		}), this.callParent(arguments);
	},
	bodyStyle : "padding-top:15px;padding-left:30px"
// ,listeners : {
// 'beforeshow':function(win)
// {
// }
// }
});

function formatEffect(value) {
	return value == '1' ? '是' : '否';
}