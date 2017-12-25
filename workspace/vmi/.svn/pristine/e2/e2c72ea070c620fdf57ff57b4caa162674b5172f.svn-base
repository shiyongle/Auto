Ext.require(["Ext.app.MySimplePrinterXTemplateString",
		"Ext.app.MySimplePrinter", "MyApp.tools.MyDataHelper","Ext.ux.form.SimpleCombo"]);
Ext.define('MyApp.store.StoreMultiRecords', {
	extend : 'Ext.data.Store',

	// requires : ['MyApp.model.MyModel'],

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
				url : 'getCusSaledeliverListByIds2.do',
				type : 'ajax',
				reader : {
					type : 'json',
					root : 'data'
				}

			}

		}, cfg)]);
	}
});
Ext.define('DJ.traffic.SaleDeliver.SaleDeliverList', {
	extend : 'Ext.c.GridPanel',
	title : "出库单",
	id : 'DJ.traffic.SaleDeliver.SaleDeliverList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetSaledeliverList.do',
	Delurl : "DelSaledeliverList",
	EditUI : "DJ.traffic.SaleDeliver.SaleDeliverEdit",
	exporturl : "SaledelivertoExcel.do",// 导出为EXCEL方法
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		// alert("DeliverList");
		Ext.getCmp("DJ.traffic.SaleDeliver.SaleDeliverList.addbutton")
				.setVisible(false);
		Ext.getCmp("DJ.traffic.SaleDeliver.SaleDeliverList.editbutton")
				.setVisible(false);
		Ext.getCmp("DJ.traffic.SaleDeliver.SaleDeliverList.delbutton")
				.setVisible(false);
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},

	printCfg : {
		mySimplePrinterBuildIds : ["outboundOrderHeader2", "stockOut",
				"outboundOrderFooter2"]
	},

	custbar : [

	{
		// id : 'DelButton',
		text : '打印',
		height : 30,
		handler : function() {

			var grid = Ext.getCmp("DJ.traffic.SaleDeliver.SaleDeliverList");
			var record = grid.getSelectionModel().getSelection();

			if (record.length == 0) {
				Ext.Msg.alert("提示", "条数为0，不能打印。<br/>请选择欲打印条目。");
				return;
			}

			// 构建好的用于sql查询的Id串
			var ids = "";

			Ext.each(record, function(item, index, all) {

				var id = item.get("fid");

				if (record.length == 1) {

					ids = "" + id + "";

				} else {
					if (index == 0) {
						ids += "" + id + "";
					} else if (index == record.length - 1) {

						ids += "," + id + "";

					} else {
						ids += "," + id + "";
					}
				}

			});

			this._myPrintMulti(ids);

		},
		_myPrintMulti : function(ids) {

			var cusStore = Ext.create("MyApp.store.StoreMultiRecords");

			var cusStoreHeaderAndDataIndex = cusStore.headerAndDataIndex;

			var data1 = {

				tableName : "",
				tableInterval : "0cm",
				pageSize : 10,
				border : "0",
				cellspacing : "0px", // 表格中行间距

				height : "100",

				style : "text-align:center;height:100;",

				countField : "count",
				priceField : "0",

				billLadingNo : "",
				receiptNo : "",
				customerName : "",
				vehicle : "",
				deliveryAddress : "",
				supplierName : "",

				supplierNumber : "",
				productNumber : ""
			};

			var me = this;

			cusStore.load({
				params : {
					fids : ids
				},
				// 当打印单子出现不足的现象时，可以*2调整
				limit : 5000,
				scope : me,
				callback : function(records, operation, success) {

					var obj = Ext
							.decode(operation.request.callback.arguments[2].responseText);
					if (obj.success == false) {
						Ext.MessageBox.alert('错误', obj.msg);
						return;
					}

					var modelsArray = [];

					var preidSd = " ";

					var models = [];

					cusStore.sort('idSd', 'DESC');

					cusStore.data.each(function(item, index, len) {

						if (index == 0) {
							preidSd = item.get("idSd");
						}

						// cusStore.data.items[index
						// + 1].data.idSd

						// 当订单号不同，
						if (preidSd != item.get("idSd")) {

							preidSd = item.get("idSd");
							// 第一个不加入，may be no use here
							if (models.length != 0) {

								var datat = Ext.clone(data1);

								var modelsT = Ext.Array.clone(models);

								modelsArray.push({
									models : modelsT,
									data2 : Ext
											.apply(datat,
													cusStore.data.items[index
															- 1].data)
								});

							}

							models.length = 0;

						}

						// 最后的时候单独判断
						if (index == len - 1) {
							if (preidSd == item.get("idSd")) {
								models.push(item.getData(false));

								var datat = Ext.clone(data1);

								var modelsT = Ext.Array.clone(models);

								modelsArray.push({
									models : modelsT,
									data2 : Ext.apply(datat, item
											.getData(false))
								});

							} else {
								var datat = Ext.clone(data1);

								var modelsT = Ext.Array.clone(models);

								modelsArray.push({
									models : modelsT,
									data2 : Ext
											.apply(datat,
													cusStore.data.items[index
															- 1].data)
								});

								models.length = 0;

								models.push(item.getData(false));

								var datat2 = Ext.clone(data1);

								var modelsT2 = Ext.Array.clone(models);

								modelsArray.push({
									models : modelsT2,
									data2 : Ext.apply(datat2, item
											.getData(false))
								});
							}
						}

						models.push(item.getData(false));

					});

					var newwin = window.open("", "printer");

					var ids = Ext.app.MySimplePrinter.buildIds([
							"outboundOrderHeader2", "stockOut",
							"outboundOrderFooter2"]);

					Ext.app.MySimplePrinter.setTpl(ids);

					Ext.each(modelsArray, function(item, index, all) {

						var data3 = MyApp.tools.MyDataHelper.buildDataByModels(
								item.data2, item.models,
								cusStoreHeaderAndDataIndex);

						Ext.app.MySimplePrinter.appendDataToPage(data3, newwin);

					});

					// 打印
					newwin.print();
					newwin.close();

					//					
					//
					// var data3 = MyApp.tools.MyDataHelper.buildDataByStore(
					// data2, cusStore, cusStoreHeaderAndDataIndex);
					//
					// data3.supplierName =
					// cusStore.getAt(0).get("supplierName");
					//
					// Ext.app.MySimplePrinter.doPrintByGridEasyer(data3, [
					// "outboundOrderHeader", "gridTest",
					// "outboundOrderFooter"]);
				}
			});

		}

	},{
		xtype: 'scombo',
		data : [
		    {text:"全部", value:""},
		    {text:"自运发货", value:"0"},
		    {text:"协同发货", value:"1"}
		],
		filterfield: 'sd.ftype',
		compareType: '=',
		value: ''
	}

	],
	fields : [
			{
				name : "fid"
			}, {
				name : 'fnumber',
				myfilterfield : 'sd.fnumber',
				myfiltername : '单据编号',
				myfilterable : true
			}, {
				name : 'fbizdate',
				myfilterfield : 'sd.fbizdate',
				myfiltername : '业务日期',
				myfilterable : true
			}, {
				name : "fcreatorid"
			}, {
				name : "fcreator"
			}, {
				name : "fcreatetime"
			}, {
				name : "flastupdateuserid"
			}, {
				name : "fupdateuser"
			}, {
				name : "flastupdatetime"
			}, {
				name : 'ftruckid',
				myfilterfield : 'sd.ftruckid',
				myfiltername : '车辆名称',
				myfilterable : true
			}, {
				name : "fasselbleid"
			}, {
				name : "fassemblenum",
				myfilterfield : 't.fnumber',
				myfiltername : '提货单号',
				myfilterable : true
			}, {
				name : "fcustomerid"
			}, {
				name : "fcustname"
			}, {
				name : "faddressid"
			}, {
				name : "faddress"
			}, {
				name : "fauditorid"
			}, {
				name : "fauditor",
				myfilterfield : 'ad.fname',
				myfiltername : '审核人',
				myfilterable : true
			}, {
				name : "fauditdate"
			}, {
				name : 'faudited',
				myfilterfield : 'sd.faudited',
				myfiltername : '审核',
				myfilterable : true
			}, {
				name : "ftype",
				myfilterfield : 'sd.ftype',
				myfiltername : '发货类型',
				myfilterable : true
			}],
	columns : [{
		text : '序号',
		xtype : 'rownumberer',
		width : 35
	},{
		'header' : '出库单号',
		'dataIndex' : 'fnumber',
		sortable : true
	}, {
		'header' : '客户名称',
		'dataIndex' : 'fcustname',
		sortable : true
	}, {
		'header' : '发货类型',
		'dataIndex' : 'ftype',
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '协同发货';
			} else {
				return '自运发货';
			}
		}
	}, {
		'header' : '配送地址',
		'dataIndex' : 'faddress',
		sortable : true
	}, {
		'header' : '业务日期',
		'dataIndex' : 'fbizdate',
		sortable : true
	},{
		'header' : '制单人',
		'dataIndex' : 'fcreator',
		sortable : true
	}, {
		'header' : '制单日期',
		'dataIndex' : 'fcreatetime',
		width : 100,
		sortable : true
	}, {
		'header' : '审核',
		dataIndex : 'faudited',
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	}, {
		'header' : '审核人',
		'dataIndex' : 'fauditor',
		sortable : true
	}, {
		'header' : '审核时间',
		'dataIndex' : 'fauditdate',
		sortable : true,
		flex: 1
	}],
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	plugins : [Ext.create('Ext.grid.plugin.CellEditing')]

});
