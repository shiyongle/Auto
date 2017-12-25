Ext.define('DJ.System.CusWinCfgType', {
			extend : 'Ext.data.Model',

			idProperty : 'fid',

			fields : [{
						name : 'fid'
					}, {
						name : 'ftype'
					}, {
						name : 'ftypename'
					}, {
						name : 'fcode'
					}]

		});

Ext.app.cusWinCfgEditStore = Ext.create("Ext.data.Store", {

			storeId : 'cusWinCfgEditStoreId',

			model : "DJ.System.CusWinCfgType",
			proxy : {
				type : 'ajax',// 使用Ext.data.proxy.LocalStorage代理
				api : {
					create : undefined,
					read : 'getWinCfgTypes.do',
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

Ext.define('DJ.System.CusWinCfgEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.System.CusWinCfgEdit",
	modal : true,
	onload : function() {
	},
	title : "自定义桌面编辑",
	width : 680,// 230, //Window宽度
	ctype : "CusWinCfg",
	height : 400,// 137, //Window高度
	resizable : false,
	url : 'saveOrUpdateCusWinCfg.do',
	infourl : 'getWinCfg.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'getWinCfg.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true
	items : [{
		layout : "column",
		baseCls : "x-plain",
		items : [{
					// title:"列1",
					baseCls : "x-plain",
					// columnWidth : 600,
					bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
					items : [{
								name : 'userName',
								xtype : 'textfield',
								width : 580,
								fieldLabel : '用户',
								labelWidth : 70,
								readOnly : true,
								disabled : true
							}, {
								name : 'ftitle',
								xtype : 'textfield',
								width : 580,
								fieldLabel : '标题',
								labelWidth : 70
							}]
				}, {
					baseCls : "x-plain",
					// columnWidth : .5,
					bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
					items : [{
								// id :
								// 'DJ.System.product.CustproductEdit.FID',
								name : 'fid',
								xtype : 'textfield',
								labelWidth : 50,
								width : 580,
								hidden : true
							}, {

								name : 'fPositionx',
								xtype : 'numberfield',
								fieldLabel : 'X坐标',
								width : 200,
								labelWidth : 70,
								allowDecimals : false,
								minValue : 0,
								value : 10
							}, {
								name : 'fPositiony',
								xtype : 'numberfield',
								fieldLabel : 'Y坐标',
								width : 200,
								labelWidth : 70,
								allowDecimals : false,
								minValue : 0,
								value : 10
							}

					]
				}, {// title:"列2",
					baseCls : "x-plain",
					// columnWidth : .5,
					bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
					items : [{

								name : 'fwidth',
								xtype : 'numberfield',
								fieldLabel : '宽度',
								width : 200,
								labelWidth : 70,
								minValue : 0,
								value : 100
							}, {

								name : 'fheight',
								xtype : 'numberfield',
								fieldLabel : '高度',
								width : 200,
								labelWidth : 70,
								minValue : 0,
								value : 100
							}

					]
				}, {
					baseCls : "x-plain",
					// columnWidth : .5,
					bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
					items : [{

						name : 'ftype',
						xtype : 'combo',
						store : 'cusWinCfgEditStoreId',
						fieldLabel : '类型',
						displayField : 'ftypename',
						valueField : 'ftype',
						width : 580,
						labelWidth : 70,
						value : "cus",
						forceSelection : 'true',
						
						listeners : {
							change : function(com) {
								var type = com.getValue();

								var store = Ext.data.StoreManager
										.lookup('cusWinCfgEditStoreId');

								var modelt = store.findRecord("ftype", type);

								var textAre = com.nextNode("textareafield");

								textAre.setValue(modelt.get("fcode"));

								if (type == 'cus') {

									textAre.setReadOnly(false);
//									textAre.setDisabled(false);

								} else {
									textAre.setReadOnly(true);
//									textAre.setDisabled(true);
								}
							}
						}

					}, {
						name : 'fcode',
						// xtype: 'htmleditor',
						xtype : 'textareafield',
						fieldLabel : '内容代码',
						width : 580,
						labelWidth : 70,
						rows : 10,
						readOnly : true
//						,
//						disabled : true

					}

					]
				}]
	}],
	bodyStyle : "padding-top:5px;padding-left:30px"
});