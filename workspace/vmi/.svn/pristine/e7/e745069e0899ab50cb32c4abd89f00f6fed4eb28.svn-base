Ext.require('DJ.System.Customer.CustomerStore');
Ext.define('DJ.System.UserAddressEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.System.UserAddressEdit",
	modal : true,
	ctype:'Address',
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		var win = this;
        var cform=win.getform().getForm();
//		cform.findField('fcustomerid').setDisabled(true); 这样设置后台无法获取表单值
//		win.down('[name=fcustomerid]').setReadOnly(true);
        var isQuickOrder = win.isQuickOrder;
		Ext.Ajax.request({
					url : 'getCountCustInfo.do',
					
					success : function(res) {
						var obj = Ext.decode(res.responseText);
						if (obj.success) {
							if (obj.total > 1 && !isQuickOrder) {
								Ext.Msg.alert('错误', "帐号关联多个客户,不能维护地址！");
								win.close();
							} else {								
								cform.findField('fcustomerid').setmyvalue(obj.data[0]);
							}
						} else {
							Ext.Msg.alert('错误', obj.msg);
							win.close();
						}
					},
					failure : function() {
						win.close();
						xt.Msg.alert('提示', '获取最值失败，请稍后再试');
					}
				});
	},
	title : "地址编辑界面",
	width : 680,
	resizable : false,
	url : 'saveUserAddress.do',
	infourl : 'getAddressInfo.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'getAddressInfo.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true
	items : [{
		layout : "column",
		baseCls : "x-plain",
		items : [{
					// title:"列1",
					baseCls : "x-plain",
					columnWidth : 600,
					bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
					items : [{
								name : 'fdetailaddress',
								xtype : 'textfield',
								width : 580,
								fieldLabel : '详细地址',
								labelWidth : 70,
								allowBlank : false,
								blankText : '详细地址不能为空'
							}

					]
				}, {// title:"列1",
					baseCls : "x-plain",
					columnWidth : .5,
					bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
					items : [{
								// id :
								// 'DJ.System.product.CustproductEdit.FID',
								name : 'fid',
								xtype : 'textfield',
								labelWidth : 70,
								width : 260,
								hidden : true
							}, {
								name : 'fcreatorid',
								xtype : 'textfield',
								labelWidth : 70,
								width : 260,
								hidden : true
							}, {
								name : 'flastupdateuserid',
								xtype : 'textfield',
								labelWidth : 70,
								width : 260,
								hidden : true
							}, {
								name : 'fcontrolunitid',
								xtype : 'textfield',
								labelWidth : 70,
								width : 260,
								hidden : true
							}, {
			  			        	width : 260,
			  			        	labelWidth:70,
			  			        	name:'fcustomerid',
			    	        		fieldLabel : '客户',
			    	        		xtype:'cCombobox',
			    	        		hidden: true,
			    	        		displayField:'fname', // 这个是设置下拉框中显示的值
			    	        	    valueField:'fid', // 这个可以传到后台的值
			    	        	    allowBlank : false,
			    	        	    blankText:'请选择客户',
			    	        	    editable: false, // 可以编辑不
			    	        	    MyConfig : {
			    	 					width : 800,//下拉界面
			    	 					height : 200,//下拉界面
			    	 					url : 'GetCustomerList.do',  //下拉数据来源
			    	 					fields : [ {
			    	 						name : 'fid'
			    	 					}, {
			    	 						name : 'fname',
			    	 						myfilterfield : 't_bd_customer.fname', //查找字段，发送到服务端
			    	 						myfiltername : '客户名称',//在过滤下拉框中显示的名称
			    	 						myfilterable : true//该字段是否查找字段
			    	 					}, {
			    	 						name : 'fnumber'
			    	 					}, {
			    	 						name : 'findustryid'
			    	 					}, {
			    	 						name : 'faddress'
			    	 					}, {
			    	 						name : 'fisinternalcompany',
			    	 						convert:function(value,record)
			    							{
			    								if(value=='1')
			    								{	
			    									return true;
			    								}else{
			    									return false;
			    								}	
			    							}
			    	 					} ],
			    	 					columns : [ {
			    	 						text : 'fid',
			    							dataIndex : 'fid',
			    							hidden : true,
			    							sortable : true
			    						}, {
			    							text : '编码',
			    							dataIndex : 'fnumber',
			    							sortable : true
			    						}, {
			    							text : '客户名称',
			    							dataIndex : 'fname',
			    							sortable : true
			    						}, {
			    							text : '行业',
			    							dataIndex : 'findustryid',
			    							sortable : true
			    						}, {
			    							text : '地址',
			    							dataIndex : 'faddress',
			    							sortable : true,
			    							width : 250
			    						}, {
			    							text : '内部客户',
			    							dataIndex : 'fisinternalcompany',
			    							xtype:'checkcolumn',
			    							processEvent : function() {
			    							},
			    							sortable : true
			    						}]
			    	 				}
				  			      	
				  	           	},{
								name : 'fnumber',
								xtype : 'textfield',
								fieldLabel : '编  码',
								hidden: true,
//								allowBlank : false,
								blankText : '编码不能为空',
								regex : /^([\u4E00-\u9FA5]|\w|[@.()\-])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
								regexText : "不能包含特殊字符",
								width : 260,
								labelWidth : 70
							}, {
								id : 'DJ.System.AddressEdit.fcountryid',
								name : 'fcountryid',
								fieldLabel : '国  家',
								hidden: true,
								width : 260,
								labelWidth : 70,
								store : Ext
										.create('DJ.System.Customer.CountryStore'),
								triggerAction : "all",
								xtype : 'combobox',
								displayField : 'fname', // 这个是设置下拉框中显示的值
								valueField : 'fid', // 这个可以传到后台的值
								editable : false, // 可以编辑不
								forceSelection : true,
								mode : 'local',
								listeners : {
									select : function(_combo, _record, _opt) {
										var fcountryidd = _combo.getValue();
										var cforms = Ext
												.getCmp("DJ.System.AddressEdit")
												.getform().getForm();
										cforms.findField('fdistrictidid')
												.clearValue();
										cforms.findField('fcityidid')
												.clearValue();
										cforms.findField('fprovinceid')
												.clearValue();
									}
								}

							}, {
								id : 'DJ.System.AddressEdit.fcityidid',
								name : 'fcityidid',
								fieldLabel : '城  市',
								hidden: true,
								width : 260,
								labelWidth : 70,
								store : Ext.create(
										'DJ.System.Customer.CityStore', {
											listeners : {

												"beforeload" : function(store,
														options) {
													Ext
															.apply(
																	store.proxy.extraParams,
																	{
																		fprovinceid : Ext
																				.getCmp("DJ.System.AddressEdit.fprovinceid")
																				.getValue()
																	});
												}
											}
										}),
								triggerAction : "all",
								xtype : 'combobox',
								displayField : 'fname', // 这个是设置下拉框中显示的值
								valueField : 'fid', // 这个可以传到后台的值
								editable : false, // 可以编辑不
								forceSelection : true,
								mode : 'local',
								listeners : {
									select : function(_combo, _record, _opt) {
										var fcityidd = _combo.getValue();

										var cforms = Ext
												.getCmp("DJ.System.AddressEdit")
												.getform().getForm();
										cforms.findField('fdistrictidid')
												.clearValue();
									}
								}
							}, {
								name : 'femail',
								xtype : 'textfield',
								fieldLabel : 'Email',
								hidden: true,
								width : 260,
								labelWidth : 70,
//								regex : /^(\w|[.])*@(\w|[@.])*\.(\w|[.])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
								regexText : "不能包含特殊字符"
							}, {
								name : 'flinkman',
								xtype : 'textfield',
								fieldLabel : '联系人',
								allowBlank : false,
								blankText : '联系人不能为空',
								width : 260,
								labelWidth : 70
							} 

					]
				}, {// title:"列2",
					baseCls : "x-plain",
					columnWidth : .5,
					bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
					items : [{
								name : 'fphone',
								xtype : 'textfield',
								fieldLabel : '电  话',
		//						regex : /^1[3|4|5|8][0-9]\d{4,8}$/,			// /^[^,\!@#$%^&*()_+}]*$/,
								regexText : "你输入的不是手机号",
								width : 260,
								labelWidth : 70
							},{
								name : 'fname',
								xtype : 'textfield',
								hidden: true,
								fieldLabel : '地址名称',
								style : "padding:5px",
								regexText : "不能包含特殊字符",
								width : 260,
								labelWidth : 70

							}, {
								id : 'DJ.System.AddressEdit.fprovinceid',
								name : 'fprovinceid',
								fieldLabel : '省  份',
								hidden: true,
								width : 260,
								labelWidth : 70,
								store : Ext.create(
										'DJ.System.Customer.ProvinceStore', {
											listeners : {

												"beforeload" : function(store,
														options) {
													Ext
															.apply(
																	store.proxy.extraParams,
																	{
																		fcountryid : Ext
																				.getCmp("DJ.System.AddressEdit.fcountryid")
																				.getValue()
																	});
												}
											}
										}),
								triggerAction : "all",
								xtype : 'combobox',
								displayField : 'fname', // 这个是设置下拉框中显示的值
								valueField : 'fid', // 这个可以传到后台的值
								editable : false, // 可以编辑不
								forceSelection : true,
								mode : 'local',
								listeners : {
									select : function(_combo, _record, _opt) {
										var fprovinceidd = _combo.getValue();
										var cforms = Ext
												.getCmp("DJ.System.AddressEdit")
												.getform().getForm();
										cforms.findField('fdistrictidid')
												.clearValue();
										cforms.findField('fcityidid')
												.clearValue();

									}
								}
							}, {
								id : 'DJ.System.AddressEdit.fdistrictidid',
								name : 'fdistrictidid',
								fieldLabel : '区  县',
								hidden: true,
								width : 260,
								labelWidth : 70,
								store : Ext.create(
										'DJ.System.Customer.RegionStore', {
											listeners : {

												"beforeload" : function(store,
														options) {
													Ext
															.apply(
																	store.proxy.extraParams,
																	{
																		fcityid : Ext
																				.getCmp("DJ.System.AddressEdit.fcityidid")
																				.getValue()
																	});
												}
											}
										}),
								triggerAction : "all",
								xtype : 'combobox',
								displayField : 'fname', // 这个是设置下拉框中显示的值
								valueField : 'fid', // 这个可以传到后台的值
								editable : false, // 可以编辑不
								forceSelection : true,
								mode : 'local'
							}, {
								name : 'fpostalcode',
								xtype : 'textfield',
								hidden: true,
								fieldLabel : '邮政编码',
								width : 260,
								labelWidth : 70
							}, {
								name : 'ffax',
								xtype : 'textfield',
								hidden: true,
								fieldLabel : '传  真',
								width : 260,
								labelWidth : 70
							}, {
								name : 'fcreatetime',
								xtype : 'textfield',
								fieldLabel : '创建时间',
								width : 260,
								labelWidth : 70,
								hidden : true

							}, {
								name : 'flastupdatetime',
								xtype : 'textfield',
								hidden : true

							}

					]
				}]
	}],
	bodyStyle : "padding-top:5px;padding-left:30px"
});