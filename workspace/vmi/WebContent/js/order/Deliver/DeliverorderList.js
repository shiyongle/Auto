Ext.define('DJ.order.Deliver.DeliverorderList', {
	extend : 'Ext.c.GridPanel',
	title : "配送订单",
	id : 'DJ.order.Deliver.DeliverorderList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetDeliverorderListMV.do',
	// Delurl : "DelDeliversList.do",
	Delurl : "",
	// EditUI : "DJ.order.Deliver.DeliversEdit",
	mixins : ['DJ.tools.grid.mixer.MyGridSearchMixer'],
	EditUI : "",
	exporturl : "deliverordertoExcel.do",// 导出为EXCEL方法
	CREAT_TRUCKASSEMBLE_URL : "creatTruckassembleByCondition.do",
	mixins : ['DJ.tools.grid.MySimpleGridMixer',
			'DJ.tools.grid.mixer.MyGridSearchMixer'],
			
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		// alert("DeliverList");
		var grid = this;
		grid.store.on('beforeload',function( store, operation, eOpts ){
//			operation.sorters=''
			store.getProxy().timeout=600000;
		})
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
		var grid = Ext.getCmp("DJ.order.Deliver.DeliverorderList");
		var record = grid.getSelectionModel().getSelection();
		if (record.length != 1) {
			throw "只能选中一条记录进行修改!";
		}
		if (record[0].get("fordered") == "1") {
			throw "已下单数据不能修改!";
		}
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
		var grid = Ext.getCmp("DJ.order.Deliver.DeliverorderList");
		var record = grid.getSelectionModel().getSelection();
		for (var i = 0; i < record.length; i++) {
			if (record[i].get("fordered") == '1') {
				throw "已下单数据不能删除!";
			}
		}
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	custbar : [
			// {
			// text : '发货',
			// height : 30,
			// handler : function() {
			// var grid = Ext.getCmp("DJ.order.Deliver.DeliverorderList");
			// var record = grid.getSelectionModel().getSelection();
			// var ids = "(";
			// for (var i = 0; i < record.length; i++) {
			// var fid = record[i].get("fid");
			// ids += "'" + fid + "'";
			// if (i < record.length - 1) {
			// ids = ids + ",";
			// }
			// }
			// ids = ids + ")";
			// var el = grid.getEl();
			// el.mask("系统处理中,请稍候……");
			// Ext.Ajax.request({
			// url : "deliverorderSend.do",
			// params : {
			// fdeliverid : ids
			// }, // 参数
			// success : function(response, option) {
			// var obj = Ext.decode(response.responseText);
			// if (obj.success == true) {
			// djsuccessmsg( obj.msg);
			// grid.store.load();
			// el.unmask();
			// } else {
			// Ext.MessageBox.alert('错误', obj.msg);
			// el.unmask();
			// }
			// }
			// });
			// }
			// },
			{
				// text : '导入协同物流',
				text : '导入接口',
				height : 30,
				handler : function() {
					var grid = Ext.getCmp("DJ.order.Deliver.DeliverorderList");
					var record = grid.getSelectionModel().getSelection();
					if (record.length < 1) {
						Ext.MessageBox.alert("信息", "请选择至少一条记录导入！");
						return;
					}
					var ids = "";
					for (var i = 0; i < record.length; i++) {
						var fid = record[i].get("fid");
						ids += fid;
						if (i < record.length - 1) {
							ids = ids + ",";
						}
					}
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						timeout : 600000,
						url : "deliverorderImportEAS.do",
						params : {
							fidcls : ids
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								djsuccessmsg(obj.msg);
								Ext.getCmp("DJ.order.Deliver.DeliverorderList").store
										.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});
				}
			}, {
				text : '自运发货',
				height : 30,
				handler : function() {
					var grid = Ext.getCmp("DJ.order.Deliver.DeliverorderList");
					var record = grid.getSelectionModel().getSelection();
					if (record.length < 1) {
						Ext.MessageBox.alert("信息", "请选择至少一条记录导入EAS！");
						return;
					}
					var ids = "";
					for (var i = 0; i < record.length; i++) {
						var fid = record[i].get("fid");
						ids += fid;
						if (i < record.length - 1) {
							ids = ids + ",";
						}
					}

					if (record.length == 1) {
						if (record[0].get("fouted") == "1"
								|| record[0].get("fmatched") == "1") {
							Ext.MessageBox.alert('错误', "该配送单已装配或已发货不能自运！");
							return;
						}
						var fid = record[0].get("fid");
						var amount = parseInt(record[0].get("famount"));
						var fassembleQty = record[0].get("fassembleQty") == "null"
								? 0
								: parseInt(record[0].get("fassembleQty"));
						var RealAmountEdit = Ext
								.create('DJ.order.Deliver.RealAmountEdit');
						RealAmountEdit.parentid = 'DJ.order.Deliver.DeliverorderList';
						RealAmountEdit.show();
						Ext
								.getCmp("DJ.order.Deliver.RealAmountEdit.deliverorderid")
								.setValue(fid);
						Ext.getCmp("DJ.order.Deliver.RealAmountEdit.famount")
								.setValue(amount);
						Ext
								.getCmp("DJ.order.Deliver.RealAmountEdit.fassembleQty")
								.setValue(fassembleQty);
						Ext.getCmp("DJ.order.Deliver.RealAmountEdit.frealqty")
								.setValue(amount - fassembleQty);
					} else {
						var el = grid.getEl();
						el.mask("系统处理中,请稍候……");
						Ext.Ajax.request({
							timeout : 600000,
							url : "creatTruckassemble.do",
							params : {
								fidcls : ids,
								ftype : 0
							}, // 参数
							success : function(response, option) {
								var obj = Ext.decode(response.responseText);
								if (obj.success == true) {
									djsuccessmsg(obj.msg);
									Ext
											.getCmp("DJ.order.Deliver.DeliverorderList").store
											.load();
								} else {
									Ext.MessageBox.alert('错误', obj.msg);
								}
								el.unmask();
							}
						});
					}
				}
			}, {
				text : '审核',
				height : 30,
				handler : function() {
					var grid = Ext.getCmp("DJ.order.Deliver.DeliverorderList");
					var record = grid.getSelectionModel().getSelection();
					if (record.length < 1) {
						Ext.MessageBox.alert("信息", "请选择至少一条订单进行审核！");
						return;
					}
					var fids = "";
					for (var i = 0; i < record.length; i++) {
						fids += record[i].get("fid");
						if (i < record.length - 1) {
							fids = fids + ",";
						}
					}
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "auditDeliverorder.do",
						params : {
							fidcls : fids
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								djsuccessmsg(obj.msg);
								Ext.getCmp("DJ.order.Deliver.DeliverorderList").store
										.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});
				}
			}, {
				text : '反审核',
				height : 30,
				handler : function() {
					var grid = Ext.getCmp("DJ.order.Deliver.DeliverorderList");
					var record = grid.getSelectionModel().getSelection();
					if (record.length < 1) {
						Ext.MessageBox.alert("信息", "请选择至少一条订单进行反审核！");
						return;
					}
					var fids = "";
					for (var i = 0; i < record.length; i++) {
						fids += record[i].get("fid");
						if (i < record.length - 1) {
							fids = fids + ",";
						}
					}
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "unauditDeliverorder.do",
						params : {
							fidcls : fids
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								djsuccessmsg(obj.msg);
								Ext.getCmp("DJ.order.Deliver.DeliverorderList").store
										.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});
				}
			}, {
				text : '拆分订单',
				height : 30,
				handler : function() {
					var grid = Ext.getCmp("DJ.order.Deliver.DeliverorderList");
					var record = grid.getSelectionModel().getSelection();
					if (record.length != 1) {
						Ext.MessageBox.alert('错误', "请选择一条记录进行拆分！");
						return;
					}
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "getSplitDeliverorderInfo.do",
						params : {
							fid : record[0].get("fid")
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								var rinfo = obj.data;
								var assignOrderEdit = Ext
										.getCmp("DJ.order.Deliver.SplitDeliverorderEdit");

								if (assignOrderEdit != null) {
								} else {
									assignOrderEdit = Ext
											.create('DJ.order.Deliver.SplitDeliverorderEdit');
								}
								var amount = parseInt(rinfo[0].famount);
								var fassembleQty = parseInt(rinfo[0].fassembleQty);
								if (fassembleQty > 0 && fassembleQty <= amount) {
									Ext.MessageBox.alert('错误', "多次配送的配送单不能拆分！");
									return;
								}
								Ext
										.getCmp("DJ.order.Deliver.SplitDeliverorderEdit.orderfnumber")
										.setValue(rinfo[0].fnumber);
								Ext
										.getCmp("DJ.order.Deliver.SplitDeliverorderEdit.deliverorderid")
										.setValue(rinfo[0].fid);
								Ext
										.getCmp("DJ.order.Deliver.SplitDeliverorderEdit.ftotalnum")
										.setValue(amount);
								assignOrderEdit.show();
								var custgrid = Ext
										.getCmp("SplitOrderEditGridPanel");
								var custstore = custgrid.getStore();
								custstore.loadData("");
								el.unmask();
								// grid.store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
								el.unmask();
							}
						}
					});
				}

			}, {
				text : '异常跟踪',
				height : 30,
				handler : function() {
					var grid = Ext.getCmp("DJ.order.Deliver.DeliverorderList");
					var record = grid.getSelectionModel().getSelection();
					if (record.length != 1) {
						Ext.MessageBox.alert('错误', "请选择一条记录");
						return;
					}

					var fdeliverorderId = record[0].get("fid");
					var fnumber = record[0].get("fnumber");

					var win = Ext
							.getCmp("DJ.order.Deliver.DeliverorderexceptionEdit");

					if (win == null) {
						var win = Ext
								.create("DJ.order.Deliver.DeliverorderexceptionEdit");
					}

					var saveBT = Ext
							.getCmp("DJ.order.Deliver.DeliverorderexceptionEdit.savebutton");

					saveBT.setVisible(true);

					var fidField = win.down("textfield[name=fid]");
					var fnumberField = win.down("textfield[name=fnumber]");
					var fdeliverorderIdField = win
							.down("textfield[name=fdeliverorderId]");
					var fremarkField = win.down("textfield[name=fremark]");

					fnumberField.setValue(fnumber);
					fdeliverorderIdField.setValue(fdeliverorderId);

					win.show();
				}

			}, {
				text : '指定订单',
				height : 30,
				handler : function() {
					var grid = Ext.getCmp("DJ.order.Deliver.DeliverorderList");
					var record = grid.getSelectionModel().getSelection();
					if (record.length != 1) {
						Ext.MessageBox.alert('错误', "请选择一条记录进行替换！");
						return;
					}
					if (record[0].get("fmatched") == "1") {
						Ext.MessageBox.alert('错误', "该订单已配货！");
						return;
					}
					if (record[0].get("faudited") == "1") {
						Ext.MessageBox.alert('错误', '该订单已审核');
						return;
					}
					if (record[0].get("fimportEAS") == "1") {
						Ext.MessageBox.alert('错误', '该订单已导入EAS');
						return;
					}

					var amount = parseInt(record[0].get("famount"));
					var fassembleQty = parseInt(record[0].get("fassembleQty"));
					if (fassembleQty > 0 && fassembleQty <= amount) {
						Ext.MessageBox.alert('错误', "多次配送的配送单不能指定订单！");
						return;
					}

					var editui = Ext.create('DJ.order.Deliver.ChangeAllotEdit');
					editui.seteditstate("edit");
					editui.setparent('DJ.order.Deliver.DeliverorderList');
					editui.loadfields(record[0].get("fid"));
					editui.show();
				}
			},{
					xtype : 'mysimplesearchercombobox',
		filterMode : true,
		fields : [{

			displayName : '未发货',
			trueValue : '0',
			field : 'fstate'

		}, {

			displayName : '部分发货',
			trueValue : '1',
			field : 'fstate'
		}]
			},{
			text:'关闭',
			height : 30,
				handler : function() {
					var grid = Ext.getCmp("DJ.order.Deliver.DeliverorderList");
					var record = grid.getSelectionModel().getSelection();
					if (record.length < 1) {
						Ext.MessageBox.alert("信息", "请选择至少一条订单进行关闭！");
						return;
					}
					var fids = "";
					for (var i = 0; i < record.length; i++) {
						fids += record[i].get("fid");
						if (i < record.length - 1) {
							fids = fids + ",";
						}
					}
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "closeDeliverorder.do",
						params : {
							fidcls : fids
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								djsuccessmsg(obj.msg);
								Ext.getCmp("DJ.order.Deliver.DeliverorderList").store
										.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});
				}
			}

	],
	fields : [{
		name : 'fid'
	}, {
		name : 'faddress',
		myfilterfield : 'faddress',
		myfiltername : '配送地址',
		myfilterable : true
	}, {
		name : 'fnumber',
		myfilterfield : 'fnumber',
		myfiltername : '配送单号',
		myfilterable : true
	}, {
		name : 'fcreatorid'
	}, {
		name : 'fupdateuserid'
	}, {
		name : 'fcustomerid'
	}, {
		name : 'fcusproductid'
	}, {
		name : 'fcustname',
		myfilterfield : '_custname',
		myfiltername : '客户名称',
		myfilterable : true
	}, {
		name : 'cutpdtname',
		myfilterfield : '_custpdtname',
		myfiltername : '客户产品',
		myfilterable : true
	}, {
		name : 'flinkman',
		myfilterfield : 'flinkman',
		myfiltername : '联系人',
		myfilterable : true
	}, {
		name : 'flinkphone',
		myfilterfield : 'flinkphone',
		myfiltername : '联系电话',
		myfilterable : true
	}, {
		name : 'famount'
	}, {
		name : 'fdescription'
	}, {
		name : 'farrivetime',
		myfilterfield : 'farrivetime',
		myfiltername : '配送时间',
		myfilterable : true
	}, {
		name : 'fcreator'
	}, {
		name : 'flastupdater'
	}, {
		name : 'fcreatetime'
	}, {
		name : 'fupdatetime'
	}, {
		name : 'fordered'
	}, {
		name : 'fordermanid'
	}, {
		name : 'fordertime'
	}, {
		name : 'forderman'
	}, {
		name : 'fsaleorderid'
	}, {
		name : 'fordernumber',
		myfilterfield : 'fordernumber',
		myfiltername : '订单编号',
		myfilterable : true
	}, {
		name : 'forderentryid'
	}, {
		name : 'faudittime'
	}, {
		name : 'fauditor'
	}, {
		name : 'faudited',
		myfilterfield : 'faudited',
		myfiltername : '是否审核',
		myfilterable : true
	}, {
		name : 'fimportEAS',
		myfilterfield : 'fimportEAS',
		myfiltername : '是否导入',
		myfilterable : true
	}, {
		name : 'fouted',
		myfilterfield : 'fouted',
		myfiltername : '是否发货',
		myfilterable : true
	}, {
		name : 'fmatched'
	}, {
		name : 'suppliername',
		myfilterfield : '_suppliername',
		myfiltername : '制造商名称',
		myfilterable : true
	}, {
		name : 'ftype'
	}, {
		name : 'fassembleQty'
	}, {
		name : 'foutQty'
	}, {
		name : 'fcharacter'
	}, {
		name : 'fpcmordernumber'
	},{
		name:'fclosed'
	}],
	columns : [{
		'header' : '订单编号',
		'dataIndex' : 'fordernumber',
		sortable : true
	}, {
		'header' : '采购订单号',
		'dataIndex' : 'fpcmordernumber',
		sortable : true
	}, {
		'header' : '客户名称',
		width : 100,
		'dataIndex' : 'fcustname',
		sortable : true
	}, {
		'header' : '制造商名称',
		width : 100,
		'dataIndex' : 'suppliername',
		sortable : true
	}, {
		'header' : '配送单号',
		'dataIndex' : 'fnumber',
		sortable : true
	}, {
		'header' : '客户产品',
		width : 200,
		'dataIndex' : 'cutpdtname',
		sortable : true
	}, {
		'header' : '配送数量',
		width : 70,
		'dataIndex' : 'famount',
		sortable : true
	}, {
		'header' : '提货数量',
		width : 70,
		'dataIndex' : 'fassembleQty',
		sortable : true
	}, {
		'header' : '发货数量',
		width : 70,
		'dataIndex' : 'foutQty',
		sortable : true
	}, {
		'header' : '导入EAS',
		width : 60,
		dataIndex : 'fimportEAS',
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	}, {
		'header' : '发货',
		width : 40,
		dataIndex : 'fouted',
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	}, {

		'header' : '配货',
		dataIndex : 'fmatched',
		width : 40,
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	}, {
		'header' : '审核',
		dataIndex : 'faudited',
		width : 30,
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	},{
		'header' : '关闭',
		dataIndex : 'fclosed',
		width : 30,
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	}, {
		'header' : '配送时间',
		'dataIndex' : 'farrivetime',
		width : 140,
		sortable : true
	}, {
		'header' : '类型',
		dataIndex : 'ftype',
		width : 70,
		sortable : true,
		renderer : function(value) {
			return value === '0' ? '正常' : ((value == 1) ? '补单' : '补货');
		}
	}, {
		'header' : '联系人',
		width : 70,
		'dataIndex' : 'flinkman',
		sortable : true
	}, {
		'header' : '联系电话',
		width : 80,
		'dataIndex' : 'flinkphone',
		sortable : true
	}, {
		'header' : '配送地址',
		'dataIndex' : 'faddress',
		width : 250,
		sortable : true
	}, {
		'header' : '备注',
		'dataIndex' : 'fdescription',
		sortable : true
	}, {
		'header' : '审核人',
		'dataIndex' : 'fauditor',
		sortable : true
	}, {
		'header' : '审核时间',
		dataIndex : 'faudittime',
		width : 150,
		sortable : true
	}, {
		'header' : '创建人',
		'dataIndex' : 'fcreator',
		sortable : true
	}, {
		'header' : '创建时间',
		'dataIndex' : 'fcreatetime',
		width : 150,
		sortable : true
	}, {
		'header' : '修改人',
		'dataIndex' : 'flastupdater',
		sortable : true
	}, {
		'header' : '修改时间',
		'dataIndex' : 'fupdatetime',
		width : 150,
		sortable : true
	}, {
		header : '特性名称',
		dataIndex : 'fcharacter',
		width : 150,
		sortable : true
	}
	// , {
	// 'header' : '下单人',
	// 'dataIndex' : 'fordermanid',
	// hidden : true,
	// sortable : true
	//
	// }
	// {
	// 'header' : '下单人',
	// 'dataIndex' : 'forderman',
	// sortable : true
	// },
	// {
	// 'header' : '下单',
	// dataIndex : 'fordered',
	// sortable : true,
	// renderer : function(value) {
	// if (value == 1) {
	// return '是';
	// } else {
	// return '否';
	// }
	// }
	// }, {
	// 'header' : '下单时间',
	// dataIndex : 'fordertime',
	// width : 150,
	// sortable : true
	// }
	],
	selModel : Ext.create('Ext.selection.CheckboxModel')
})