requires : ['DJ.System.Customer.CustomerStore'];
var proivinceStore = Ext.create('DJ.System.Customer.ProvinceStore');
var countryStore = Ext.create('DJ.System.Customer.CountryStore');
var cityStore = Ext.create('DJ.System.Customer.CityStore');
var regionStore = Ext.create('DJ.System.Customer.RegionStore');

Ext
		.define(
				'DJ.System.AddressEdit',
				{
					extend : 'Ext.c.BaseEditUI',
					id : "DJ.System.AddressEdit",
					modal : true,
					onload : function() {
						// // 加载后事件，可以设置按钮，控件值等
						// var grid = Ext.getCmp("DJ.System.AddressList");//
						// Ext.getCmp("DJ.System.UserListPanel")
						// var record = grid.getSelectionModel().getSelection();
						// if (record.length != 1) {
						// Ext.MessageBox.alert('提示', '只能选中一条记录进行修改!');
						// return;
						// }
						// var fid = record[0].get("fid");
						// Ext.Ajax.request({
						// url : "getAddressInfo.do",
						// params : {
						// fid : fid
						// }, // 参数
						// success : function(response, option) {
						// var obj = Ext.decode(response.responseText);
						// if (obj.success == true) {
						//
						// var cforms =
						// Ext.getCmp("DJ.System.AddressEdit").getform().getForm();
						// var store1 = cforms.findField('fdistrictidid').store;
						// var store2 = cforms.findField('fcityidid').store;
						// var store3 = cforms.findField('fprovinceid').store;
						// cforms.findField('fcountryid').store.load();
						// countryStore.load();
						//
						// proivinceStore.on("beforeload", function(store,
						// options) {
						// Ext.apply(store.proxy.extraParams, {
						// fcountryid :
						// Ext.getCmp("DJ.System.AddressEdit").editdata.fcountryid//obj.data[0].fcountryid
						// });
						// });
						// proivinceStore.load();
						// cityStore.on("beforeload", function(store, options) {
						// Ext.apply(store.proxy.extraParams, {
						// fprovinceid :
						// Ext.getCmp("DJ.System.AddressEdit").editdata.fprovinceid//obj.data[0].fprovinceid
						// });
						// });
						// cityStore.load();
						// regionStore.on("beforeload", function(store, options)
						// {
						// Ext.apply(store.proxy.extraParams, {
						// fcityid :
						// Ext.getCmp("DJ.System.AddressEdit").editdata.fcityidid//obj.data[0].fcityidid
						// });
						// });
						// regionStore.load();
						// cforms.setValues(obj.data[0]);
						// } else {
						// Ext.MessageBox.alert('错误', obj.msg);
						// }
						// }
						// });

					},
					custbar : [ {
						// id : 'DelButton',
						text : '自定义按钮1',
						height : 30,
						handler : function() {
							var a = Ext.getCmp('DJ.System.AddressEdit');
							a.seteditstate("edit"); // 设置界面可编辑"view"和""为不可编辑，其他都是可以编辑

						}
					}, {
						// id : 'DelButton',
						text : '自定义按钮2',
						height : 30
					} ],
					title : "用户管理编辑界面",
					width : 680,// 230, //Window宽度
					height : 300,// 137, //Window高度
					resizable : false,
					url : 'SaveAddress.do',
					infourl : 'getAddressInfo.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
					viewurl : 'getAddressInfo.do', // 查看状态数据源
					closable : true, // 关闭按钮，默认为true
					items : [ {
						layout : "column",
						baseCls : "x-plain",
						items : [
								{
									// title:"列1",
									baseCls : "x-plain",
									columnWidth : 600,
									bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
									items : [ {
										name : 'fdetailaddress',
										xtype : 'textfield',
										width : 580,
										fieldLabel : '详细地址',
										labelWidth : 70
									}
									// , {
									// name : 'fdescription',
									// xtype : 'textfield',
									// width : 565,
									// fieldLabel : '描述',
									// labelWidth : 70
									// }

									]
								},
								{// title:"列1",
									baseCls : "x-plain",
									columnWidth : .5,
									bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
									items : [
											{
												// id :
												// 'DJ.System.product.CustproductEdit.FID',
												name : 'fid',
												xtype : 'textfield',
												labelWidth : 50,
												width : 260,
												hidden : true
											},
											{
												// id :
												// 'DJ.System.product.CustproductEdit.FID',
												name : 'fcreatorid',
												xtype : 'textfield',
												labelWidth : 50,
												width : 260,
												hidden : true
											},
											{
												// id :
												// 'DJ.System.product.CustproductEdit.FID',
												name : 'flastupdateuserid',
												xtype : 'textfield',
												labelWidth : 50,
												width : 260,
												hidden : true
											},
											{
												// id :
												// 'DJ.System.product.CustproductEdit.FID',
												name : 'fcontrolunitid',
												xtype : 'textfield',
												labelWidth : 50,
												width : 260,
							        			hidden : true
						        			},
						        			{
												// id :
												// 'DJ.System.product.CustproductEdit.FNUMBER',
												name : 'fnumber',
												xtype : 'textfield',
												fieldLabel : '编  码',
												allowBlank : false,
												blankText : '编码不能为空',
												regex : /^([\u4E00-\u9FA5]|\w|[@.()\-])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
												regexText : "不能包含特殊字符",
												width : 260,
												labelWidth : 70
											},
											{
												id : 'DJ.System.AddressEdit.fcountryid',
												name : 'fcountryid',
												fieldLabel : '国  家',
												width : 260,
												labelWidth : 70,
												store : countryStore,
												triggerAction : "all",
												xtype : 'combobox',
												displayField : 'fname', // 这个是设置下拉框中显示的值
												valueField : 'fid', // 这个可以传到后台的值
												editable : false, // 可以编辑不
												forceSelection : true,
												mode : 'local',
												listeners : {
													select : function(_combo,_record, _opt) {
														var fcountryidd = _combo
																.getValue();
														var cforms = Ext
																.getCmp(
																		"DJ.System.AddressEdit")
																.getform()
																.getForm();
														cforms
																.findField(
																		'fdistrictidid')
																.clearValue();
														cforms.findField(
																'fcityidid')
																.clearValue();
														cforms.findField(
																'fprovinceid')
																.clearValue();

														proivinceStore.on(
																		"beforeload",
																		function(store,options) {
																			Ext.apply(
																							store.proxy.extraParams,
																							{
																								fcountryid : fcountryidd
																							});
																		});
														proivinceStore.load();

														cityStore.on(
																		"beforeload",
																		function(store,options) {
																			Ext.apply(
																							store.proxy.extraParams,
																							{
																								fprovinceid : 't'
																							});
																		});
														cityStore.load();

														regionStore.on(
																		"beforeload",
																		function(store,options) {
																			Ext.apply(
																							store.proxy.extraParams,
																							{
																								fcityid : 't'
																							});
																		});
														regionStore.load();
													}
												}

											},
											{
												id : 'DJ.System.AddressEdit.fcityidid',
												name : 'fcityidid',
												fieldLabel : '城  市',
												width : 260,
												labelWidth : 70,
												store : cityStore,
												triggerAction : "all",
												xtype : 'combobox',
												displayField : 'fname', // 这个是设置下拉框中显示的值
												valueField : 'fid', // 这个可以传到后台的值
												editable : false, // 可以编辑不
												forceSelection : true,
												mode : 'local',
												listeners : {
//													beforeload : function(store, options) {
//														Ext.apply(
//																	store.proxy.extraParams,
//																	{
//																		fprovinceid : Ext.getCmp("DJ.System.AddressEdit.fprovinceid").getValue()// obj.data[0].fprovinceid
//																	}
//																);
//													},
													select : function(_combo,_record, _opt) {
														var fcityidd = _combo
																.getValue();

														var cforms = Ext
																.getCmp("DJ.System.AddressEdit")
																.getform()
																.getForm();
														cforms
																.findField('fdistrictidid')
																.clearValue();
														
														regionStore.on(
																		"beforeload",
																		function(store,options) {
																			Ext.apply(
																					store.proxy.extraParams,
																					{
																						fcityid : fcityidd
																					});
																		});
														regionStore.load();
													}
												}
											},{
												// id :
												// 'DJ.System.product.CustproductEdit.FMNEMONICCODE',
												name : 'femail',
												xtype : 'textfield',
												fieldLabel : 'Email',
												width : 260,
												labelWidth : 70,
												regex : /^(\w|[.])*@(\w|[@.])*\.(\w|[.])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
												regexText : "不能包含特殊字符"
											},
											{
												// id :
												// 'DJ.System.product.CustproductEdit.Fcustomer',
												name : 'flinkman',
												xtype : 'textfield',
												fieldLabel : '联系人',
												width : 260,
												labelWidth:70
											},{
												// id :
												// 'DJ.System.product.CustproductEdit.Fcustomer',
												name : 'fphone',
												xtype : 'textfield',
												fieldLabel : '电  话',
												regex : /^1[3|4|5|8][0-9]\d{4,8}$/,// /^[^,\!@#$%^&*()_+}]*$/,
												regexText : "你输入的不是手机号",
												width : 260,
												labelWidth : 70
											}

									]
								},
								{// title:"列2",
									baseCls : "x-plain",
									columnWidth : .5,bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
		  				items : [{
												// id :
												// 'DJ.System.product.CustproductEdit.FNAME',
												name : 'fname',
												xtype : 'textfield',
												fieldLabel : '名  称',
												style : "padding:5px",
												allowBlank : false,
												blankText : '名称不能为空',
												regex : /^([\u4E00-\u9FA5]|\w|[()\-])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					  			        regexText : "不能包含特殊字符",
					  			        width : 260,
					  			     	labelWidth:70
					  			     	
			  			        	},{
												id : 'DJ.System.AddressEdit.fprovinceid',
												name : 'fprovinceid',
												fieldLabel : '省  份',
												width : 260,
												labelWidth : 70,
												store : proivinceStore,
												triggerAction : "all",
												xtype : 'combobox',
												displayField : 'fname', // 这个是设置下拉框中显示的值
												valueField : 'fid', // 这个可以传到后台的值
												editable : false, // 可以编辑不
												forceSelection : true,
												mode : 'local',
												listeners : {
//			  			        					beforeload : function(store, options){
//														Ext.apply(
//														store.proxy.extraParams,{
//															fcountryid : Ext.getCmp("DJ.System.AddressEdit.fcountryid").getValue()
//															}
//														);
//													},
													select : function(_combo,_record, _opt) {
														var fprovinceidd = _combo
																.getValue();
														var cforms = Ext
																.getCmp(
																		"DJ.System.AddressEdit")
																.getform()
																.getForm();
														cforms
																.findField(
																		'fdistrictidid')
																.clearValue();
														cforms.findField(
																'fcityidid')
																.clearValue();
														
														cityStore.on(
															"beforeload",
															function(store,options) {
																Ext.apply(
																			store.proxy.extraParams,
																			{
																				fprovinceid : fprovinceidd
																			});
														});
														cityStore.load();

														regionStore.on(
															"beforeload",
															function(store,options) {
																Ext.apply(
																			store.proxy.extraParams,
																			{
																				fcityid : 't'
																			});
															});
														regionStore.load();
														
													}
												}
											}, {
												id :'DJ.System.AddressEdit.fdistrictidid',
												name : 'fdistrictidid',
												fieldLabel : '区  县',
												width : 260,
												labelWidth : 70,
												store : regionStore,
												triggerAction : "all",
												xtype : 'combobox',
												displayField : 'fname', // 这个是设置下拉框中显示的值
												valueField : 'fid', // 这个可以传到后台的值
												editable : false, // 可以编辑不
												forceSelection : true,
												mode : 'local'
//												,listeners : function(){
//													beforeload : function(store, options) {
//															Ext.apply(
//															store.proxy.extraParams,
//															{
//																fcityidid : Ext.getCmp("DJ.System.AddressEdit.fcityidid").getValue()// obj.data[0].fprovinceid
//															});
//													}
//												}

											}, {
												// id :
												// 'DJ.System.product.CustproductEdit.Fcreatetime',
												name : 'fpostalcode',
												xtype : 'textfield',
												fieldLabel : '邮政编码',
												width : 260,
												labelWidth : 70
											}, {
												// id :
												// 'DJ.System.product.CustproductEdit.FDESCRIPTION',
												name : 'ffax',
												xtype : 'textfield',
												fieldLabel : '传  真',
												width : 260,
												labelWidth : 70
											}, {
												// id :
												// 'DJ.System.product.CustproductEdit.Fcreatetime',
												name : 'fcreatetime',
												xtype : 'textfield',
												fieldLabel : '创建时间',
												width : 260,
												labelWidth : 70
											// hidden : true

											}, {
												// id :
												// 'DJ.System.product.CustproductEdit.Fcreatetime',
												name : 'flastupdatetime',
												xtype : 'textfield',
												hidden : true

											}

									]
								} ]
					} ],
					bodyStyle : "padding-top:5px;padding-left:30px"
				});