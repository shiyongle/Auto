

Ext.app.simplemessageEditComboStore = Ext.create("Ext.data.Store", {

			storeId : 'simplemessageEditComboStore',

			fields : ['fid', 'fname'],

			proxy : {
				type : 'ajax',// 使用Ext.data.proxy.LocalStorage代理
				api : {
					create : undefined,
					read : 'GetUserList.do',
					update : undefined,
					destroy : undefined
				},
				reader : {
					type : 'json',
					root : 'data',
					messageProperty : 'msg'
				},
				writer : {
					type : 'json',
					encode : true,
					root : 'data',
					allowSingle : false
				}
			},
			autoLoad : true
		});

Ext.app.simplemessageEditComboStoreChooser = Ext.create("Ext.data.Store", {

			storeId : 'simplemessageEditComboStoreChooser',

			fields : ['fid', 'fname'],

			proxy : {
				type : 'ajax',// 使用Ext.data.proxy.LocalStorage代理
				api : {
					create : undefined,
					read : 'selectUserByName.do',
					update : undefined,
					destroy : undefined
				},
				reader : {
					type : 'json',
					root : 'data',
					messageProperty : 'msg'
				},
				writer : {
					type : 'json',
					encode : true,
					root : 'data',
					allowSingle : false
				}
			},
			autoLoad : true
		});

Ext.define('DJ.System.SimplemessageEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : 'DJ.System.SimplemessageEdit',
	modal : true,
	title : "",
	ctype : "Simplemessage",
	width : 800, // Window宽度
	height : 450, // Window高度
	resizable : true,
	url : 'saveSimplemessage.do',
	infourl : 'getSimplemessageById.do',
	viewurl : 'getSimplemessageById.do',
	closable : true, // 关闭按钮，默认为true

	constrainHeader : true,

	custbar : [

	],
	cverifyinput : function() {
		// throw "数据异常，不能保存！";
	},

	items : [{
		layout : 'column',
		baseCls : 'x-plain',
		bodyStyle : 'padding-top:0px;padding-left:10px;padding-right:10px',
		items : [{
					// title:"列1",
					baseCls : "x-plain",
					// columnWidth : 600,
					bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
					items : []
				}, {
					baseCls : "x-plain",
					// columnWidth : .5,
					bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
					items : [{
								name : 'fid',
								xtype : 'textfield',
								labelWidth : 50,
								width : 580,
								hidden : true
							}, {
								name : 'fsender',
								xtype : 'textfield',
								labelWidth : 50,
								width : 580,
								hidden : true
							}

					]
				}, {
					id : 'DJ.System.SimplemessageEditForm',
					baseCls : "x-plain",
					// columnWidth : .5,
					bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
					items : [{
								name : 'frecipient',
								xtype : 'combo',
								fieldLabel : '接收人',

								width : 580,
								labelWidth : 70,

								multiSelect : true,
								delimiter : ',',

								store : Ext.app.simplemessageEditComboStore,
								valueField : 'fid',
								displayField : 'fname',
								forceSelection : true,

								queryMode : 'local'
							}, {
								name : 'frecipientChooser',
								xtype : 'combo',
								fieldLabel : '选择接收人',

								width : 580,
								labelWidth : 70,

								store : Ext.app.simplemessageEditComboStoreChooser,
								valueField : 'fid',
								displayField : 'fname',
								forceSelection : true,
								typeAhead : true,
								queryMode : 'remote',

								minChars : 1,
								queryDelay : 300,

								queryParam : 'frecipientName',

								listeners : {
									select : function(combo, records, eOpts) {

										var combox = Ext
												.getCmp("DJ.System.SimplemessageEditForm")
												.down("combo[name=frecipient]");

										var comboxValueT = combox.getValue();

										// 去重复
										if (!Ext.Array.contains(comboxValueT,
												records[0].get("fid"))) {
											comboxValueT.push(records[0]
													.get("fid"));

											combox.setValue(comboxValueT);

											combo.setValue("");
										}

									}
								}
							}, {
								fieldLabel : '',
								labelWidth : 600,

								xtype : 'checkboxfield',
								boxLabel : '发给所有人',

								listeners : {
									change : function(com, newValue, oldValue,
											eOpts) {

										var combox = Ext
												.getCmp("DJ.System.SimplemessageEditForm")
												.down("combo[name=frecipient]");

										if (newValue) {

											combox
													.setValue(Ext.app.simplemessageEditComboStore.data.items);

										} else {
											combox.setValue("");
										}

									}
								}

							}, {
								name : 'fcontent',

								xtype : 'textareafield',
								fieldLabel : '内容',
								width : 580,
								labelWidth : 70,
								rows : 10
							}, {
								name : 'fremark',

								xtype : 'textareafield',
								fieldLabel : '备注',
								width : 580,
								labelWidth : 70,
								rows : 10
							}

					]
				}]
	}],
	bodyStyle : "padding-top:5px;padding-left:30px"

});