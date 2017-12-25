Ext
		.define(
				'DJ.traffic.Truckassemble.TruckassembleEdit',
				{
					extend : 'Ext.c.BaseEditUI',
					id : 'DJ.traffic.Truckassemble.TruckassembleEdit',
					modal : true,
					title : "提货单编辑界面",
					ctype : "Truckassemble",
					width : 1050,// 230, //Window宽度
					height : 600,// 137, //Window高度
					resizable : false,
					url : 'SaveTruckassemble.do',
					infourl : 'GetTruckassembleInfo.do',
					viewurl : 'GetTruckassembleInfo.do',
					closable : true, // 关闭按钮，默认为true
					custbar : [
							{
								// id : 'DelButton',
								text : '取消配送',
								height : 30,
								handler : function() {
								var assembleID = Ext.getCmp('DJ.traffic.Truckassemble.TruckassembleEdit.fid').getValue();
								var grid = Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleEdit.table");
								var record = grid.getSelectionModel().getSelection();
								if(record.length<1){
									Ext.MessageBox.alert("信息","请选择需要取消配送数据！");
									return;
								}
								var entryIDs = "";
								var fdeliverorderids = "";
								for ( var i = 0; i < record.length; i++) {
									var fid = record[i].get("fid");
									var fdeliverorderid = record[i].get("fdeliverorderid");
									entryIDs +=  fid ;
									fdeliverorderids += fdeliverorderid ;
									if (i < record.length - 1) {
										entryIDs = entryIDs + ",";
										fdeliverorderids = fdeliverorderids + ",";
									}
								}
								var isclose=0;
								if(grid.items.items[0].all.count==record.length){
									isclose=1;
								}
								
								var el = Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleEdit").getEl();
								el.mask("系统处理中,请稍候……");
									Ext.Ajax.request({
														timeout: 600000,
														url : "cancelDeliver.do",
														params : {
															assembleid : assembleID,
															entryfidcls : entryIDs,
															fdeliverorderfidcls : fdeliverorderids
														}, // 参数
														success : function(response, option) {
															var obj = Ext.decode(response.responseText);
															if (obj.success == true) {
																//Ext.MessageBox.alert('成功', obj.msg);
																djsuccessmsg( obj.msg);
																Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleEdit.table").store.load();
																if(isclose == 1){
																	Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleEdit").close();
																}
																Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleList").store.load();
																
																if(Ext.getCmp("DJ.order.Deliver.DeliverorderList")== undefined){
																	if(Ext.getCmp("DJ.order.Deliver.SDeliverorderSendList")!= undefined){
																		Ext.getCmp("DJ.order.Deliver.SDeliverorderSendList").store.load();
																	}
																  }else{
																  	Ext.getCmp("DJ.order.Deliver.DeliverorderList").store.load();
																  }
															} else {
																Ext.MessageBox.alert('错误', obj.msg);
															}
															el.unmask();
														}
													})}
							},
							{
								// id : 'DelButton',
								text : '车辆提货',
								height : 30,
								handler : function() {
									var assembleID = Ext.getCmp('DJ.traffic.Truckassemble.TruckassembleEdit.fid').getValue();
									var grid=Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleEdit.table");
									var record = grid.getSelectionModel().getSelection();
									if(record.length<1){
										Ext.MessageBox.alert("信息","请选择需要提货数据！");
										return;
									}
//									var entryIDs = "(";
									var entryIDs = "";
									for ( var i = 0; i < record.length; i++) {
										var fid = record[i].get("fid");
										entryIDs += fid;
										if (i < record.length - 1) {
											entryIDs = entryIDs + ",";
										}
									}
//									if(entryIDs.length <= 1){
//										entryIDs = "('')";
//									}else{
//										entryIDs = entryIDs + ")";
//									}
									
									var el =Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleEdit").getEl();
									el.mask("系统处理中,请稍候……");
									Ext.Ajax.request({
														timeout: 600000,
														url : "actionTruckassemble.do",
														params : {
															assembleID : assembleID,
															fidcls : entryIDs
														}, // 参数
														success : function(response, option) {
															var obj = Ext.decode(response.responseText);
															if (obj.success == true) {
																//Ext.MessageBox.alert('成功', obj.msg);
																djsuccessmsg( obj.msg);
																Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleEdit.table").store.load();
															} else {
																Ext.MessageBox.alert('错误', obj.msg);
															}
															el.unmask();
														}
													})
								}
							},
							{
								// id : 'DelButton',
								text : '取消提货',
								height : 30,
								handler : function() {
									var assembleID = Ext.getCmp('DJ.traffic.Truckassemble.TruckassembleEdit.fid').getValue();
									var grid = Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleEdit.table");
									var record = grid.getSelectionModel().getSelection();
									if(record.length<1){
										Ext.MessageBox.alert("信息","请选择需要取消提货数据！");
										return;
									}
									var entryIDs = "";
									for ( var i = 0; i < record.length; i++) {
										if(record[i].get("fouted")==1){
											Ext.MessageBox.alert("信息","不能选择已发车分录取消提货！");
											return;
										}
										var fid = record[i].get("fid");
										entryIDs += fid ;
										if (i < record.length - 1) {
											entryIDs = entryIDs + ",";
										}
									}
									
									var el = Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleEdit").getEl();
									el.mask("系统处理中,请稍候……");
									Ext.Ajax.request({
														timeout: 600000,
														url : "cancelTruckassemble.do",
														params : {
															assembleid : assembleID,
															fidcls : entryIDs
														}, // 参数
														success : function(response, option) {
															var obj = Ext.decode(response.responseText);
															if (obj.success == true) {
																//Ext.MessageBox.alert('成功', obj.msg);
																djsuccessmsg( obj.msg);

																Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleEdit.table").store.load();
															} else {
																Ext.MessageBox.alert('错误', obj.msg);
															}
															el.unmask();
														}
													})
								}
							},
							{
								// id : 'DelButton',
								text : '发车',
								height : 30,
								handler : function() {
										Ext.MessageBox.show({
											title : '提示',
											msg : '发车后会扣减库存并无法撤销，确认发车?',
											width : 250,
											buttons : Ext.MessageBox.YESNO,
											animEl : Ext.getBody(),
											icon : Ext.MessageBox.QUESTION,
											fn : function(btn) {
												if (btn == 'yes') {
													var fid = Ext.getCmp('DJ.traffic.Truckassemble.TruckassembleEdit.fid').getValue();
													var grid = Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleEdit.table");
													var el = Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleEdit").getEl();
													el.mask("系统处理中,请稍候……");
													Ext.Ajax.request({
																		timeout: 600000,
																		url : "actionAudit.do",
																		params : {
																			fid : fid
																		}, // 参数
																		success : function(response, option) {
																			var obj = Ext.decode(response.responseText);
																			if (obj.success == true) {
																				//Ext.MessageBox.alert('成功', obj.msg);
																				djsuccessmsg( obj.msg);
																				var record = grid.getSelectionModel().getSelection();
																				if(record.length<1){
																					Ext.MessageBox.alert("信息","请选择需要取消配送数据！");
																					return;
																				}
//																				var productid = "";
//																				for ( var i = 0; i < record.length; i++) {
//																					var fid = record[i].get("fproductid");
//																					productid += fid ;
//																					if (i < record.length - 1) {
//																						productid = productid + ",";
//																					}
//																				}
//																				Ext.Ajax.request({
//																					url:'myStockIsconsumed.do',
//																					params : {
//																						fid : productid
//																					}, // 参数
//																					success : function(response, option) {
//																					}
//																				})
																				Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleEdit.table").store.load();
																			} else {
																				Ext.MessageBox.alert('错误', obj.msg);
																			}
																			el.unmask();
																		}
																	})


                                                 }
											}
										});
								}
							}
							],
					cverifyinput : function() {
						 //throw "数据异常，不能保存！";
					},
					initComponent : function() {
								Ext
										.apply(
												this,
												{
													items : [
															{
																layout : 'column',
																baseCls : 'x-plain',
																bodyStyle : 'padding-top:0px;padding-left:10px;padding-right:10px',
																items : [
																		{
																			bodyStyle : 'padding:5px;',
																			baseCls : 'x-plain',
																			columnWidth : .25,
																			layout : 'form',
																			defaults : {
																				xtype : 'textfield'
																			},
																			items : [
																					{
																						id : 'DJ.traffic.Truckassemble.TruckassembleEdit.fid',
																						name : 'fid',
																						xtype : 'textfield',
																						labelWidth : 50,
																						width : 260,
																						hidden : true
																					},{
																						name : 'fcreatorid',
																						xtype : 'textfield',
																						labelWidth : 50,
																						width : 260,
																						hidden : true
																					},
																					{
																						name : 'fnumber',
																						fieldLabel : '单据编码:',
																						allowBlank : false,
																						blankText : '编码不能为空',
																						regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"
																					}
//																					,
//																					Ext.create(
//																						'Ext.ux.form.DateTimeField',
//																						{
//																							fieldLabel : '制单日期',
//																							name : 'FCREATETIME',
//																							format : 'Y-m-d'
//																					}),
//																						{
//																						name : 'fcustomerid',
//																						fieldLabel : '车辆名称',
//																						xtype : 'cCombobox',
//																						displayField : 'fname', // 这个是设置下拉框中显示的值
//																						valueField : 'fid', // 这个可以传到后台的值
//																						MyConfig : {
//																							width : 800,// 下拉界面
//																							height : 200,// 下拉界面
//																							url : 'GetCustomerList.do', // 下拉数据来源
//																							fields : [
//																									{
//																										name : 'fid'
//																									},
//																									{
//																										name : 'fname',
//																										myfilterfield : 't_bd_customer.fname', // 查找字段，发送到服务端
//																										myfiltername : '客户名称',// 在过滤下拉框中显示的名称
//																										myfilterable : true
//																									// 该字段是否查找字段
//																									},
//																									{
//																										name : 'fnumber'
//																									},
//																									{
//																										name : 'findustryid'
//																									},
//																									{
//																										name : 'faddress'
//																									},
//																									{
//																										name : 'fisinternalcompany',
//																										convert : function(
//																												value,
//																												record) {
//																											if (value == '1') {
//																												return true;
//																											} else {
//																												return false;
//																											}
//																										}
//																									} ],
//																							columns : [
//																									{
//																										text : 'fid',
//																										dataIndex : 'fid',
//																										hidden : true,
//																										sortable : true
//
//																									},
//																									{
//																										text : '编码',
//																										dataIndex : 'fnumber',
//																										sortable : true
//																									},
//																									{
//																										text : '客户名称',
//																										dataIndex : 'fname',
//																										sortable : true
//																									},
//																									{
//																										text : '行业',
//																										dataIndex : 'findustryid',
//																										sortable : true
//																									},
//																									{
//																										text : '地址',
//																										dataIndex : 'faddress',
//																										sortable : true,
//																										width : 250
//																									},
//																									{
//																										text : '内部客户',
//																										dataIndex : 'fisinternalcompany',
//																										xtype : 'checkcolumn',
//																										processEvent : function() {
//																										},
//																										sortable : true
//																									} ]
//																							}
//																						}
																					]
																		},
																		{
																			bodyStyle : 'padding:5px;',
																			baseCls : 'x-plain',
																			columnWidth : .25,
																			layout : 'form',
																			defaults : {
																				xtype : 'textfield'
																			},
																			items : [
																					Ext.create(
																						'Ext.ux.form.DateTimeField',
																						{
																							fieldLabel : '业务日期:',
																							name : 'fbizdate',
																							format : 'Y-m-d'
																					})
//																					,{
//																						xtype : 'textfield',
//																						name : 'fname',
//																						fieldLabel : '产品名称',
//																						allowBlank : false,
//																						blankText : '名称不能为空',
//																						regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//																						regexText : "不能包含特殊字符"
//																					},
//																					{
//																						name : 'forderunitid',
//																						fieldLabel : '计量单位',
//																						regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//																						regexText : "不能包含特殊字符"
//
//																					},
//																					{
//																						name : 'ftilemodelid',
//																						fieldLabel : '瓦楞楞型',
//																						regex : /^([\u4E00-\u9FA5]|\w|\S)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//																						regexText : "不能包含特殊字符"
//
//																					}
																					]
																		},
																		{

																			bodyStyle : 'padding: 5px;',
																			baseCls : 'x-plain',
																			columnWidth : .25,
																			layout : 'form',
																			defaults : {
																				xtype : 'textfield'
																			},
																			items : [
																					{
																						name : 'ftruckid',
																						fieldLabel : '车辆名称:',
																						regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"

																					}
//																					,{
//																						xtype : 'numberfield',
//																						name : 'fmaterialcost',
//																						fieldLabel : '材料成本',
//																						value : 0,
//																						minValue : 0,
//																						negativeText : '不能为负数'
//																					},
//																					{
//
//																						name : 'fmaterialcode',
//																						fieldLabel : '用料编码',
//																						regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//																						regexText : "不能包含特殊字符"
//																					},
//																					{
//
//																						xtype : 'hidden',
//																						name : 'fid'
//
//																					}
																					]
																		} ]
															},
															//必须在最外层，中间不能加panel
															{
																xtype : 'cTable',
																name : "Truckassembleentry",
																id : "DJ.traffic.Truckassemble.TruckassembleEdit.table",
																width : 1000,
																height : 460,
																pageSize : 100,
																url : "GetTruckassembleentry.do",
																parentfield : "t.fparentid",
																fields : [
																		{name : "fid"},
																		{name : "fseq"},
																		{name : "fdeliveryed"},
																		{name : "fparentid"},
																		{name : "fsaleorderid"},
																		{name : "salenumber"},
																		{name : "fdeliverorderid"},
																		{name : "delivernumber"},
																		{name : "fsupplierid"},
																		{name : "supplier"},
																		{name : "fproductid"},
																		{name : "product"},
																		{name : "fproductspec"},
																		{name : "famount"},
																		{name : "freceiveaddress"},
																		{name : "raddress"},
																		{name : "freceiver"},
																		{name : "freceiverphone"},
																		{name : "fdeliveryaddress"},
																		{name : "daddress"},
																		{name : "fdelivery"},
																		{name : "fdeliveryphone"},
																		{name : "fremark"},
																		{name : "foutor"
																		},
																		{name : 'foutorname'
																		},
																		{name : 'fouted'
																		},{
																		name:"fouttime"
																		}
																		  ],
																columns : [
																		{
																			xtype : "rownumberer",
																			text : "No"
																		},
																		{
																		width : 40 ,
																		'header' : 'FID',
																		'dataIndex' : 'fid',
																		hidden : true,
																		hideable : false,
																		sortable : true
																		},
																		{
																		'header' : ' 分录',
																		width : 30,
																		'dataIndex' : 'fseq',
																		sortable : true,
																		editor : {
																				xtype: 'textfield'
																			}
																		},
																		{
																		'header' : '提货',
																		width : 40,
																		'dataIndex' : 'fdeliveryed',
																		sortable : true,
																		renderer: function(value){
																		        if (value == 1) {
																		            return '是';
																		        }
																		        else{
																		        	return '否';
																		        }
																		    }
																		},
																		{
																		'header' : 'FPARENTID',
																		'dataIndex' : 'fparentid',
																		hidden : true,
																		hideable : false,
																		sortable : true
																		},
																		{
																		'header' : 'FSALEORDERID',
																		'dataIndex' : 'fsaleorderid',
																		hidden : true,
																		hideable : false,
																		sortable : true
																		},
																		{
																		'header' : '订单编号',
																		width : 90,
																		'dataIndex' : 'salenumber',
																		sortable : true,
																		editor : {
																				xtype: 'textfield'
																			}
																		},
																		{
																		'header' : 'fdeliverorderid',
																		'dataIndex' : 'fdeliverorderid',
																		hidden : true,
																		hideable : false,
																		sortable : true
																		},
																		{
																		'header' : '配送编号',
																		width : 90,
																		'dataIndex' : 'delivernumber',
																		sortable : true,
																		editor : {
																				xtype: 'textfield'
																			}
																		},
																		{
																		'header' : 'fsupplierid',
																		'dataIndex' : 'fsupplierid',
																		hidden : true,
																		hideable : false,
																		sortable : true
																		},
																		{
																		'header' : 'fproductid',
																		'dataIndex' : 'fproductid',
																		hidden : true,
																		hideable : false,
																		sortable : true
																		},
																		{
																		'header' : '产品名称',
																		width : 300,
																		'dataIndex' : 'product',
																		sortable : true,
																		editor : {
																				xtype: 'textfield'
																			}
																		},
																		{
																		'header' : '规格',
																		width : 120,
																		'dataIndex' : 'fproductspec',
																		sortable : true,
																		editor : {
																				xtype: 'textfield'
																			}
																		},
																		{
																		'header' : '数量',
																		width : 60,
																		'dataIndex' : 'famount',
																		sortable : true,
																		editor : {
																				xtype: 'textfield'
																			}
																		,xtype : 'numbercolumn',
																		format : '0,000'
																		},
																		{
																		'header' : 'freceiveaddress',
																		'dataIndex' : 'freceiveaddress',
																		sortable : true,
																		hidden : true,
																		hideable : false
																		},
																		{
																		'header' : '收货人地址',
																		width : 500,
																		'dataIndex' : 'raddress',
																		sortable : true,
																		editor : {
																				xtype: 'textfield'
																			}
																		},
																		{
																		'header' : '收货人',
																		'dataIndex' : 'freceiver',
																		sortable : true,
																		editor : {
																				xtype: 'textfield'
																			}
																		},
																		{
																		'header' : '收货人电话',
																		'dataIndex' : 'freceiverphone',
																		sortable : true,
																		editor : {
																				xtype: 'textfield'
																			}
																		},
																		{
																		'header' : '制造商',
																		'dataIndex' : 'supplier',
																		sortable : true,
																		editor : {
																				xtype: 'textfield'
																			}
																		},
																		{
																		'header' : 'fdeliveryaddress',
																		'dataIndex' : 'fdeliveryaddress',
																		sortable : true,
																		hidden : true,
																		hideable : false
																		},
																		{
																		'header' : '提货人地址',
																		'dataIndex' : 'daddress',
																		sortable : true,
																		editor : {
																				xtype: 'textfield'
																			}
																		},
																		{
																		'header' : '提货人',
																		'dataIndex' : 'fdelivery',
																		sortable : true,
																		editor : {
																				xtype: 'textfield'
																			}
																		},
																		{
																		'header' : '提货人电话',
																		'dataIndex' : 'fdeliveryphone',
																		sortable : true,
																		editor : {
																				xtype: 'textfield'
																			}
																		},
																		{
																		'header' : '发车',
																		width : 40,
																		'dataIndex' : 'fouted',
																		sortable : true,
																		renderer: function(value){
																		        if (value == 1) {
																		            return '是';
																		        }
																		        else{
																		        	return '否';
																		        }
																		    }
																		},
																		{
																		'header' : '发车人',
																		'dataIndex' : 'foutorname',
//																		hidden : true,
																		hideable : false,
																		sortable : true
																		},{
																		'header' : '发车时间',
																		'dataIndex' : 'fouttime',
//																		hidden : true,
																		hideable : false,
																		sortable : true
																		},
																		{
																		'header' : '备注',
																		'dataIndex' : 'fremark',
																		sortable : true,
																		editor : {
																				xtype: 'textfield'
																			}
																		}
//																		,{
//																			xtype : "numbercolumn",
//																			dataIndex : "plifecycle",
//																			text : "产品生产周期",
//																			format : "0,000",
//																			editor : {
//																				xtype : "numberfield",
//																				allowBlank : false,
//																				allowDecimals : false,
//																				minValue : 0
//																			}
//																		}
//																		,{
//																			xtype : "numbercolumn",
//																			summaryRenderer : function(
//																					val,
//																					params,
//																					data) {
//																				return "总计 : "
//																						+ val
//																						+ "% <br> 还剩 :"
//																						+ (100 - val)
//																						+ "%"
//																			}
//																			,
//																			format : "0.00%",
//																			summaryType : "sum",
//																			dataIndex : "mproportion",
//																			text : "分配比例",
//																			editor : {
//																				xtype : "numberfield",
//																				allowBlank : false,
//																				minValue : 0,
//																				step : 1
//																			}
//																		  }
																		],
																selModel:Ext.create('Ext.selection.CheckboxModel'),
//																features : [ {
//																	ftype : "summary"
//																} ],
																plugins : [ Ext
																		.create(
																				'Ext.grid.plugin.CellEditing',
																				{
																					clicksToEdit : 2
																				})
																// Ext
																// .create(
																// "Ext.grid.plugin.RowEditing",
																// {
																// clicksToEdit
																// : 2,
																// errorSummary
																// : false,
																// saveBtnText :
																// "更新",
																// cancelBtnText
																// : "取消",
																// autoCancel :
																// false
																// })
																]
															},
															{
																layout : 'column',
																baseCls : 'x-plain',
																bodyStyle : 'padding-top:0px;padding-left:55px;padding-right:55px',
																items : [
																		{
																			bodyStyle : 'padding-top:10px;padding-left:5px;padding-right:5px',
																			baseCls : 'x-plain',
																			columnWidth : .5,
																			labelWidth : 50,
//																			layout : 'form',
																			defaults : {
																				xtype : 'textfield'
																			},
																			items : [
																					{
																						xtype : 'textfield',
																						name : 'creator',
																						fieldLabel : '制单人',
																						regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"
																					}
																				]
																		},
																		{
																			bodyStyle : 'padding-top:10px;padding-left:5px;padding-right:5px',
																			baseCls : 'x-plain',
																			columnWidth : .5,
																			labelWidth : 50,
//																			layout : 'form',
																			defaults : {
																				xtype : 'textfield'
																			},
																			items : [

																					Ext
																							.create(
																									'Ext.ux.form.DateTimeField',
																									{
																										fieldLabel : '制单日期',
																										name : 'fcreatetime',
																										format : 'Y-m-d'
																									})

																			]
																		} ]
															}
															]
												}), this.callParent(arguments);
					}
				// bodyStyle : "padding-top:5px;padding-left:30px"
				});