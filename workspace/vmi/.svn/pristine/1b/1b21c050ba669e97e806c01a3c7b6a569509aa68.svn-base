Ext.Loader.setPath({

	'Ext.app' : 'js/tools/myPrint',
	'MyApp.tools' : 'js/tools/myPrint'
});

Ext.require(["Ext.app.MySimplePrinterXTemplateString",
		"Ext.app.MySimplePrinter", "MyApp.tools.MyDataHelper","Ext.ux.form.DateTimeField"]);

Ext.define('MyApp.store.MyStoretCus', {
	extend : 'Ext.data.Store',

	headerAndDataIndex : {
		header : ['物料名称', '物料编码', '采购订单号', '数量', '实收数量', '备注'],
		headDataIndexs : ['proName', 'productNumber', 'purchaseOrderNo',
				'count', 'receivedCount', 'remark']
	},

	constructor : function(cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({

			fields : ['customerName', 'idSd', {

				name : 'billLadingNo',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
			}, {

				name : 'receiptNo',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
			},

			{

				name : 'productNumber',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
			},

			{

				name : 'vehicle',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
			}, {

				name : 'deliveryAddress',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
			},

			{

				name : 'supplierName',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
			}, {

				name : 'supplierNumber',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
			}, {

				name : 'createTime',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
			}, {
				name : 'proName',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}

				}
			}, {
				name : 'count',
				type : 'int',
				convert : function(v, rec) {

					if (v == 'null') {
						return " ";
					} else {
						var r = new Number(v);
						return r.toFixed(0);
					}

				}

			}, {
				name : 'receivedCount',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
					// ,
					// type : 'int',
					// defaultValue : 0
					}, {
						name : 'unitPrice',
						convert : function(v, rec) {
							if (v == 'null') {
								return " ";
							} else {
								return v;
							}
						}
					// ,
					// type : 'float',
					// defaultValue : 0
					}, {
						name : 'sum',
						convert : function(v, rec) {
							if (v == 'null') {
								return " ";
							} else {
								return v;
							}
						}
					// ,
					// type : 'float',
					// defaultValue : 0
					}, {
						name : 'receivedCount',
						convert : function(v, rec) {
							if (v == 'null') {
								return " ";
							} else {
								return v;
							}
						}
					// ,
					// type : 'float',
					// defaultValue : 0
					}, {
						name : 'purchaseOrderNo',
						convert : function(v, rec) {
							if (v == 'null') {
								return " ";
							} else {
								return v;
							}
						}
					// ,
					// type : 'float',
					// defaultValue : 0
					}, {
						name : 'remark',
						convert : function(v, rec) {
							if (v == 'null') {
								return " ";
							} else {
								return v;
							}
						}
					// ,
					// type : 'float',
					// defaultValue : 0
					}],

			storeId : 'MyStoretCus',
			proxy : {
				url : 'getCusSaledeliverList.do',
				type : 'ajax',
				reader : {
					type : 'json',
					root : 'data'
				}
			}

		}, cfg)]);
	}
});

Ext.require(["Ext.app.MySimplePrinterXTemplateString",
		"Ext.app.MySimplePrinter", "MyApp.tools.MyDataHelper"]);

Ext.define('DJ.traffic.SaleDeliver.SaleDeliverEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : 'DJ.traffic.SaleDeliver.SaleDeliverEdit',
	modal : true,
	title : "出库单编辑界面",
	ctype : "Saledeliver",
	width : 900,// 230, //Window宽度
	resizable : false,
	url : 'SaveSaledeliver.do',
	infourl : 'GetSaledeliverInfo.do',
	viewurl : 'GetSaledeliverInfo.do',
	closable : true, // 关闭按钮，默认为true
	tools : [{
		xtype : 'tool',

		handler : function(event, toolEl, owner, tool) {

			var fn = function() {

				var gridT = Ext
						.getCmp("DJ.traffic.SaleDeliver.SaleDeliverEdit.table");

				var formt1 = Ext.getCmp

				("DJ.traffic.SaleDeliver.SaleDeliverEdit.form1");

				var formt2 = Ext.getCmp

				("DJ.traffic.SaleDeliver.SaleDeliverEdit.form2");

				var formt3 = Ext.getCmp

				("DJ.traffic.SaleDeliver.SaleDeliverEdit.form3");

				var data2 = {

					tableName : "",
					tableInterval : "0cm",
					pageSize : 10,
					border : "0",
					cellspacing : "0px", // 表格中行间距

					height : "100",

					style : "text-align:center;height:100;",

					countField : "count",
					priceField : "0",

					billLadingNo : formt1.down("textfield[name=fassemblenum]")
							.getValue(),
					receiptNo : formt1.down("textfield[name=fnumber]")
							.getValue(),
					customerName : formt1.down("textfield[name=fcustname]")
							.getValue(),
					vehicle : formt2.down("textfield[name=ftruckid]")
							.getValue(),
					deliveryAddress : formt3.down("textfield[name=faddress]")
							.getValue(),
					supplierName : ""

				};

				// var gridT = Ext
				// .getCmp("DJ.traffic.SaleDeliver.SaleDeliverEdit.table");

				var me = this;

				var cusStore = Ext.create("MyApp.store.MyStoretCus");

				// var storet = Ext.create("MyApp.store.MyStoret");

				// storet.load();

				var fid = Ext
						.getCmp("DJ.traffic.SaleDeliver.SaleDeliverEdit.fid")
						.getValue();

				cusStore.load({
					params : {
						fid : fid
					},
					scope : me,
					callback : function(records, operation, success) {

						var obj = Ext
								.decode(operation.request.callback.arguments[2].responseText);
						if (obj.success == false) {
							Ext.MessageBox.alert('错误', obj.msg);
							return;
						}
						var data3 = MyApp.tools.MyDataHelper.buildDataByStore(
								data2, cusStore, cusStore.headerAndDataIndex);

						// 设置上面的属性
						data3.supplierNumber = cusStore.getAt(0)
								.get("supplierNumber");

						data3.productNumber = cusStore.getAt(0)
								.get("productNumber");

						data3.createTime = cusStore.getAt(0).get("createTime");

						data3.supplierName = cusStore.getAt(0)
								.get("supplierName");

						Ext.app.MySimplePrinter
								.doPrintByGridEasyer(
										data3,
										Ext
												.getCmp("DJ.traffic.SaleDeliver.SaleDeliverList").printCfg.mySimplePrinterBuildIds);
					}
				});

					// gridT.getStore().load({
					// scope : me,
					// callback : function(records, operation, success) {
					// // var data3 =
					// // MyApp.tools.MyDataHelper.buildDataByStartAndL(
					// // gridT, 10, 5, data2);
					//
					// var data3 = MyApp.tools.MyDataHelper.buildDataByIndexs(
					// gridT, ['product', 'fproductspec', 'famount'],
					// data2);
					//
					// // var data3 =
					// // MyApp.tools.MyDataHelper.buildDataByStore(
					// // data2, storet, storet.headerAndDataIndex);
					//
					// Ext.app.MySimplePrinter.doPrintByGridEasyer(data3, [
					// "outboundOrderHeader", "gridTest",
					// "outboundOrderFooter"]);
					// }
					// });
			};

			// DJ.traffic.SaleDeliver.SaleDeliverEdit._setPathAndGetClass(fn);
			fn();

		},
		type : 'print'
	}],
	custbar : [{
		text : '返单',
		height : 30,
		handler : function() {
			var grid = Ext
					.getCmp("DJ.traffic.SaleDeliver.SaleDeliverEdit.table");
			var record = grid.getSelectionModel().getSelection();
			if (record.length != 1) {
				Ext.MessageBox.alert('提示', '请选中一条记录返单！');
				return;
			}
			var fid = record[0].get("fid");
			var famount = record[0].get("frealamount");
			var SaleOrderOIEdit = Ext
					.create('DJ.traffic.SaleDeliver.RealReceiptEdit');
			SaleOrderOIEdit.show();
			Ext.getCmp("DJ.traffic.SaleDeliver.RealReceiptEdit.frealamount")
					.setValue(famount);
			Ext.getCmp("DJ.traffic.SaleDeliver.RealReceiptEdit.fid")
					.setValue(fid);

		}
	}],
	initComponent : function() {
		Ext.apply(this, {
			items : [{
				layout : 'column',
				baseCls : 'x-plain',
				bodyStyle : 'padding-top:0px;padding-left:10px;padding-right:10px',
				items : [{
					id : "DJ.traffic.SaleDeliver.SaleDeliverEdit.form1",
					bodyStyle : 'padding:5px;',
					baseCls : 'x-plain',
					columnWidth : .35,
					layout : 'form',
					defaults : {
						xtype : 'textfield'
					},
					items : [{
						id : 'DJ.traffic.SaleDeliver.SaleDeliverEdit.fid',
						name : 'fid',
						xtype : 'textfield',
						labelWidth : 50,
						width : 260,
						hidden : true
					}, {
						name : 'fcreatorid',
						xtype : 'textfield',
						labelWidth : 50,
						width : 260,
						hidden : true
					}/*, {
						name : 'fassemblenum',
						fieldLabel : '提货单号:',
						allowBlank : false,
						blankText : '编码不能为空',
						regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						regexText : "不能包含特殊字符"
					}*/, {
						name : 'fnumber',
						fieldLabel : '出库单号:',
						allowBlank : false,
						blankText : '编码不能为空',
						regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						regexText : "不能包含特殊字符"
					},/* {
						xtype : 'textfield',
						name : 'fcustname',
						fieldLabel : '客户名称',
						allowBlank : false,
						blankText : '名称不能为空',
						regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						regexText : "不能包含特殊字符"
					}*/
					// ,
					 
					 {
						xtype: 'textfield',
						name: 'ftypetext',
						fieldLabel: '发货类型'
					},{
						xtype: 'textfield',
						name: 'ftype',
						hidden: true
					},{
						name : 'faddress',
						fieldLabel : '配送地址:'
					}]
				}, {
					id : "DJ.traffic.SaleDeliver.SaleDeliverEdit.form2",
					bodyStyle : 'padding:5px;',
					baseCls : 'x-plain',
					columnWidth : .3,
					layout : 'form',
					defaults : {
						xtype : 'textfield'
					},
					items : [{
						 xtype: 'datetimefield',
						 fieldLabel : '制单日期',
						 name : 'fcreatetime',
						 format : 'Y-m-d'
					 },{
						xtype: 'datetimefield',
						fieldLabel : '业务日期:',
						name : 'fbizdate',
						format : 'Y-m-d'
					}]
				}, {
					id : "DJ.traffic.SaleDeliver.SaleDeliverEdit.form3",
					bodyStyle : 'padding: 5px;',
					baseCls : 'x-plain',
					columnWidth : .25,
					layout : 'form',
					defaults : {
						xtype : 'textfield'
					},
					items : [{
						xtype : 'textfield',
						name : 'fcreator',
						fieldLabel : '制单人',
						regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						regexText : "不能包含特殊字符"
					}]
				}]
			},
					// 必须在最外层，中间不能加panel
					{
						xtype : 'cTable',
						name : "Saledeliverentry",
						id : "DJ.traffic.SaleDeliver.SaleDeliverEdit.table",
						width : 880,
						height : 400,
						pageSize : 100,
						url : "GetSaledeliverentry.do",
						parentfield : "t.fparentid",
						fields : [{
							name : "fid"
						}, {
							name : "fseq"
						}, {
							name : "fparentid"
						}, {
							name : "fsaleorderid"
						}, {
							name : "salenumber"
						}, {
							name : "fdeliverorderid"
						}, {
							name : "delivernumber"
						}, {
							name : "fsupplierid"
						}, {
							name : "supplier"
						}, {
							name : "fproductid"
						}, {
							name : "product"
						}, {
							name : "fproductspec"
						}, {
							name : "famount",
							type : 'int'
						}, {
							name : "freceiveaddress"
						}, {
							name : "raddress"
						}, {
							name : "freceiver"
						}, {
							name : "freceiverphone"
						}, {
							name : "fdeliveryaddress"
						}, {
							name : "daddress"
						}, {
							name : "fdelivery"
						}, {
							name : "fdeliveryphone"
						}, {
							name : "fremark"
						}, {
							name : "frealamount",
							type : 'int'
						}, {
							name : "freceiptdate"
						}, {
							name : "fisreceipts"
						}, {
							name : "freceiptor"
						},{
							name: 'fcustname'
						},{
							name: 'productnumber'
						},{
							name: 'fmaterialspec'
						},{
							name: 'fboxtype'
						},{
							name: 'fpcmordernumber'
						}],
						columns : [{
							xtype : "rownumberer",
							text : "序号",
							width: 40
						},/*, {
							'header' : ' 分录',
							width : 30,
							'dataIndex' : 'fseq',
							sortable : true,
							editor : {
								xtype : 'textfield'
							}
						}*/ /*{
							'header' : '订单编号',
							width : 90,
							'dataIndex' : 'salenumber',
							sortable : true,
							editor : {
								xtype : 'textfield'
							}
						}, */{
							'header' : '配送单号',
							width : 90,
							'dataIndex' : 'delivernumber',
							sortable : true,
							editor : {
								xtype : 'textfield'
							}
						}, {
							'header' : '制造商',
							'dataIndex' : 'supplier',
							sortable : true,
							editor : {
								xtype : 'textfield'
							}
						},{
							header: '客户名称',
							dataIndex: 'fcustname'
							},{
							header: '采购订单号',
							dataIndex: 'fpcmordernumber'	
							
						},{
							header: '包装物编号',
							dataIndex: 'productnumber',
							renderer: function(val,meta){
								var record = meta.record;
								return record.get('fboxtype')=='0'? val : '';
							}
						}, {
							'header' : '包装物名称',
							width : 260,
							'dataIndex' : 'product',
							sortable : true,
							editor : {
								xtype : 'textfield'
							}
						},{
							header: '订单类型',
							dataIndex: 'fboxtype',
							renderer: function(val){
								return parseInt(val) == 1 ? '纸板订单' : '纸箱订单'; 
							}
						}, {
							'header' : '纸箱规格',
							width : 80,
							'dataIndex' : 'fproductspec',
							sortable : true,
							editor : {
								xtype : 'textfield'
							}
						},{
							header: '下料规格',
							width: 80,
							sortable : true,
							dataIndex: 'fmaterialspec'
						}, {
							'header' : '配送数量',
							width : 60,
							'dataIndex' : 'famount',
							sortable : true,
							editor : {
								xtype : 'textfield'
							},
							xtype : 'numbercolumn',
							format : '0,000'
						}, {
							'header' : '实际配送数量',
							'dataIndex' : 'frealamount',
							sortable : true,
							editor : {
								xtype : 'textfield'
							},
							xtype : 'numbercolumn',
							format : '0,000'
						}/*, {
							'header' : '返单',
							'dataIndex' : 'fisreceipts',
							sortable : true,
							editor : {
								xtype : 'textfield'
							},
							renderer : function(value) {
								return value == '1' ? '是' : '否';
							}
						}*/, {
							'header' : '备注',
							'dataIndex' : 'fremark',
							sortable : true,
							editor : {
								xtype : 'textfield'
							}
						}
						],
						plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
							clicksToEdit : 2
						})]
					}]
		});
		this.callParent(arguments);
	}
// bodyStyle : "padding-top:5px;padding-left:30px"
});