Ext.define('DJ.quickOrder.orderEdit', {
	extend : 'Ext.window.Window',

	requires : ['Ext.form.field.Number', 'Ext.form.field.Date',
			'Ext.form.field.ComboBox', 'Ext.button.Button',
			'Ext.form.FieldSet', 'Ext.form.field.TextArea',
			'Ext.toolbar.Spacer'],

	autoShow : true,
	// height: 391,
	width : 408,
	// title: 'My Window',

	// style : {
	// background : 'white'
	// },

	modal : true,

	closable : true,

	resizable : false,

	layout : {
		type : 'vbox',
		align : 'stretch'
	// ,
	// padding : 5
	},

	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {

			items : [{

				xtype : 'form',

				itemId : 'orderFrom',

				flex : 1,
				bodyPadding : 10,
				layout : {
					type : 'vbox',
					align : 'stretch'
				},

				items : [{
					xtype : 'numberfield',
					fieldLabel : '数量',
					allowDecimals : false,

					minValue : 1,
					allowBlank : false

				},

				{
					xtype : 'container',
					layout : {
						type : 'hbox',
						align : 'stretch'
					},
					items : [{
						xtype : 'datefield',
						fieldLabel : '交期',
						minValue : new Date(),
						allowBlank : false,
						value : Ext.Date.add(new Date(), Ext.Date.DAY, 5)
					}, {
						xtype : 'radiogroup',
						itemId : 'timeFrameRadiogroup',
						flex : 1,
						width : 400,
						allowBlank : false,
						items : [{
							name : 'timeFrame',
							boxLabel : '上午',
							inputValue : '9',
							checked : true
						}, {
							name : 'timeFrame',
							boxLabel : '下午',
							inputValue : '14'
						}]
					}]

				}, {
					xtype : 'container',
					layout : {
						type : 'hbox',
						align : 'stretch'
					},
					items : [{
						fieldLabel : '地址',
						xtype : 'cCombobox',
						name : 'faddressid',

						flex : 1,

						// enterIndex : 22,
						displayField : 'fname',
						valueField : 'fid',
						allowBlank : false,
						blankText : '请选择送货地址',

						beforeExpand : function() {

							var me = this;

							// var customerid = Ext
							// .getCmp("DJ.order.Deliver.DeliversEdit.fcustomerid")
							// .getValue();// _combo.getValue();
							// Ext
							// .getCmp("DJ.order.Deliver.DeliversEdit.fcusproductid")
							// .setDefaultfilter([{
							// myfilterfield : "t_bd_Custproduct.fcustomerid",
							// CompareType : "like",
							// type : "string",
							// value : customerid
							// }]);
							// Ext
							// .getCmp("DJ.order.Deliver.DeliversEdit.fcusproductid")
							// .setDefaultmaskstring(" #0 ");

							var win = me.up("window");

							var comb = win.down("cCombobox");

							var customerID = win.customerID;

							var myfilter = [];
							myfilter.push({
								myfilterfield : 'cd.fcustomerid',
								CompareType : "=",
								type : "string",
								value : customerID
							});

							maskstring = "#0";

							comb.setDefaultfilter(myfilter);
							comb.setDefaultmaskstring(maskstring);

							// storeT.setDefaultfilter(myfilter);
							// storeT.setDefaultmaskstring(maskstring);

						},

						// createPicker : function() {
						//
						// var me = this;
						//
						// var cc1 = this, cc2, cc3 = Ext.baseCSSPrefix
						// + "menu";
						//
						// var customerID = me.up("window").customerID;
						//
						// var myfilter = [];
						// myfilter.push({
						// myfilterfield : 'cd.fcustomerid',
						// CompareType : "=",
						// type : "string",
						// value : customerID
						// });
						//
						// maskstring = "#0";
						//
						// // storeT.setDefaultfilter(myfilter);
						// // storeT.setDefaultmaskstring(maskstring);
						//
						// // cc1.Defaultfilter = myfilter;
						// //
						// // cc1.Defaultmaskstring = maskstring;
						//
						// var cc4 = Ext.apply({
						// parentid : cc1.id,
						// selModel : {
						// mode : cc1.multiSelect
						// ? "SIMPLE"
						// : "SINGLE"
						// },
						// ownerCt : cc1.ownerCt,
						// cls : cc1.el.up("." + cc3) ? menuCls : "",
						// floating : true,
						// selectable : true,
						// closeable : true,
						// hidden : true,
						// hiddenToolbar : false,
						// pageSize : 0x64,
						// Defaultfilter : cc1.Defaultfilter || {},
						// Defaultmaskstring : cc1.Defaultmaskstring || "",
						// focusOnToFront : false,
						// listeners : {
						// accept : function(cc5) {
						// var cc6 = cc1.picker.getView()
						// .getSelectionModel()
						// .getSelection();
						// cc1.onListSelectionChange(cc1.picker,
						// cc6);
						// cc1.MyDataChanged(cc6)
						// },
						// cancel : function() {
						// cc1.collapse()
						// },
						// itemdblclick : function(cc5, cc6, cc7) {
						// var cc8 = [];
						// cc8[0x0] = cc6;
						// cc1.onListSelectionChange(cc1.picker,
						// cc8);
						// cc1.MyDataChanged(cc8);
						// },
						// cellkeydown : function(me, td, cellIndex,
						// record, tr, rowIndex, e) {
						// if (e.getKey() == Ext.EventObject.ENTER) {
						// this.fireEvent('itemdblclick', me,
						// record);
						// }
						// }
						// }
						// }, cc1.MyConfig, cc1.defaultListConfig);
						// cc2 = cc1.picker = Ext.create(
						// "Ext.c.QueryGridPanel", cc4);
						// cc1.mon(cc2, {});
						// return cc2
						// },
						listeners : {
							afterrender : function() {
								var me = this;
								// if (win.editstate != 'add' || me.getValue())
								// {
								// return;
								// }
								Ext.Ajax.request({
									timeout : 60000,
									url : "getQuickUserDefaultAddress.do",

									success : function(response, option) {
										var obj = Ext
												.decode(response.responseText);
										if (obj.success) {
											var config;

											if(obj.data.length){
											if ( obj.data.length == 1) {
												var record = obj.data[0];
												config = {
													fid : record['fid'],
													fname : record['fname']
												};
												me.setmyvalue(config);
											} else if (obj.data.length > 1
													&& localStorage.$singleBoardAddress) {
												var record = Ext.Array.findBy(
														obj.data, function(
																item, index) {
															return item['fid'] == localStorage.$singleBoardAddress;
														});
												if (record) {
													config = {
														fid : record['fid'],
														fname : record['fname']
													}
													me.setmyvalue(config);
												}
											}
											}
											// if (config) {
											// win.faddressData = config;
											// }
											// win.initData = win.getform()
											// .getValues();
										}
									}
								});
							}
						},
						MyConfig : {
							width : 650,
							height : 200,
							url : 'getQuickUserToCustAddress.do',
							hiddenToolbar : true,
							fields : [{
								name : 'fid'
							}, {
								name : 'fname',
								myfilterfield : 'ad.fname',
								myfiltername : '名称',
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
							}],
							columns : [{
								header : '地址名称',
								dataIndex : 'fname',
								flex : 5,
								sortable : true
							}, {
								header : '联系人',
								dataIndex : 'flinkman',
								flex : 1,
								sortable : true
							}, {
								header : '联系电话',
								dataIndex : 'fphone',
								flex : 1,
								sortable : true
							}]
						}
					}, {
						xtype : 'button',
						text : '新增地址',
						handler : function() {

							var editui = Ext
									.getCmp("DJ.System.UserAddressEdit")
									|| Ext.create('DJ.System.UserAddressEdit');
							editui.seteditstate("add");
							Ext.getCmp("DJ.System.UserAddressEdit")
									.down('cCombobox[name=fcustomerid]')
									.setReadOnly(true);
							editui.show();

						}
					}]
				}, {
					xtype : 'fieldset',
					flex : 1,
					layout : 'fit',
					collapsed : true,
					collapsible : true,
					title : '备注',
					items : [{
						xtype : 'textareafield'
					// ,
					// fieldLabel : '备注'
					}]
				}, {
					xtype : 'container',
					layout : {
						type : 'hbox',
						align : 'stretch'
					},
					items : [{
						xtype : 'tbspacer',
						flex : 1
					}, {
						xtype : 'button',
						text : '提交',

						handler : function() {

							var me = this;

							var win = me.up("window");

							var orderFrom = win.down("form");

							if (!orderFrom.getForm().isValid()) {

								return;

							}

							var count = win.down('numberfield').getValue();

							var cusProductID = win.cusProductID;

							var suplierID = win.suplierID;

							var submitDate = Ext.Date.format(win
									.down("datefield").getValue(), 'Y-m-d');

							var addressID = win
									.down('cCombobox[name=faddressid]')
									.getValue();

							var tf = win.down("radiogroup").getValue();

							var remark = win.down('textareafield').getValue();

							var el = win.getEl();
							el.mask("系统处理中,请稍候……");

							Ext.Ajax.request({
								timeout : 6000,

								params : {
									count : count,
									cusProductID : cusProductID,
									suplierID : suplierID,
									submitDate : submitDate,
									addressID : addressID,
									remark : remark,
									timeFrame : tf
								},

								url : "SaveQuickOrder.do",

								success : function(response, option) {

									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										djsuccessmsg(obj.msg);

										Ext
												.getCmp('DJ.quickOrder.quickOrderList')
												.down("grid").getStore().load();

										win.close();

									} else {
										Ext.MessageBox.alert('错误', obj.msg);
									}
									el.unmask();
								}
							});

						}
					}]
				}]

			}]

		});

		me.callParent(arguments);
	}

});