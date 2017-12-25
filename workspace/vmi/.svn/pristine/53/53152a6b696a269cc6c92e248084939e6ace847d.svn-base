
//var validate = Ext.create('Ext.ux.form.DateTimeField', {
//						fieldLabel : '配送时间',
//						labelWidth : 70,
//						width : 260,
//						name : 'farrivetime',
//						format : 'Y-m-d',
//						allowBlank : false,
//						blankText : '配送时间不能为空'
//					})

function saveToServers() {
	noOperations = true;
//	sgridStore.getAt(0).set("ftotalnum",Ext.getCmp("DJ.order.Deliver.batchDeliverapplyEdit.ftotalnum").getValue());
	var obj ;
	sgridStore.sync({
		success : function() {
			obj = sgridStore.getProxy().getReader().rawData;
			showMsgsAfterSync(obj);
			closesplitWin();

		},
		failure : function(batch, options ) {
			obj = sgridStore.getProxy().getReader().rawData;
			showMsgsAfterSync(obj);
		}
	})
}

//function verifysplitfamountNotZero() {
//	var r = true;
//	for (var i = 0, n = sgridStore.data.items.length; i < n; i++) {
//		if (sgridStore.data.items[i].data.famount == 0) {
//			r = false
//		}
//	}
//	return r
//}
function saveToServerWithverifys() {
//	if (verifysplitTotalnum()  && verifysplitfamountNotZero()) {
		saveToServers()
//	} else {
//		if (!verifysplitTotalnum()) {
//			Ext.Msg
//					.alert("提示", "数量的和必须为"
//							+ Ext.getCmp("DJ.order.Deliver.batchDeliverapplyEdit.ftotalnum")
//									.getValue() + "!")
//		} else {
//			if (!verifysplitfamountNotZero()) {
//				Ext.Msg.alert("提示", "拆分数量不能为0  !")
//			}
//		}
//	}
}
//function verifysplitTotalnum() {
//	var s = 0;
//	for (var i = 0; i < sgridStore.data.items.length; i++) {
//		s += sgridStore.data.items[i].data.famount;
//	}
//	var totalnum = Ext.getCmp("DJ.order.Deliver.batchDeliverapplyEdit.ftotalnum").getValue();
//	if (s == totalnum) {
//		return true
//	} else {
//		return false
//	}
//}

function showMsgsAfterSync(obj) {
	Ext.Msg.alert("提示", obj.msg)
	if(!obj.success){
		cConfirms = true;
//		Ext.getCmp("DJ.order.Deliver.batchDeliverapplyEdit").close();
	}
//	Ext.getCmp("DJ.order.Deliver.DeliversList").store.load();
	Ext.getCmp("DJ.order.Deliver.DeliversCustList").store.load();
}

// function showItems() {
// var rows = Ext.getCmp("ProportionEditGridStore").getSelectionModel()
// .getSelection();
// for (var i = 0; i < rows.length; i++) {
// var result = "";
// result += Ext.JSON.encode(rows[i]) + "<br>"
// }
// Ext.Msg.alert("", result)
// }
Ext.define("Deliverapply", {
	extend : "Ext.data.Model",
	fields : [{
		name : "fid"
	}, {
		name : "cutpdtname"
	}, {
		name : "fcustproductid"
	}, {
		name : "famount"
	},{
		name : "farrivetime"
	},
	{
		name : "faddress"
	},
	{
		name : "faddressid"
	}, {
		name : "flinkman"
	}, {
		name : "flinkphone"
	},{
		name : "fdescription"
//		,defaultValue: 'Unknown'
	}
	]
});
Ext.define("SplitOrderEditGridStore", {
	extend : "Ext.data.Store",
	requires : ["Deliverapply"],
	constructor : function(cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
			model : "Deliverapply",
			storeId : "SplitOrderEditGridStore",
			data : '',
			proxy : {
				type : "ajax",
				timeout : 300000,
				api : {
					create : "SavebatchDeliverapply.do"
				},
				writer : {
					type : "json",
					root : "data"
				}
			}
		}, cfg)])
	}
});
sgridStore = Ext.create("SplitOrderEditGridStore");
Ext.define("SplitOrderEditGridPanel", {
	extend : "Ext.grid.Panel",
	alias : "widget.splitordereditgridpanel",
	id : "SplitOrderEditGridPanel",
	height : 138,
	width : 850,
	store : sgridStore,
	requires: ['Ext.ux.form.DateTimeField'],
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			columns : [
			           /*{
				xtype : "rownumberer",
				text : "No"
			}
			, {
				xtype : "numbercolumn",
				summaryRenderer : function(val, params, data) {
					return "总计 : "
							+ val
							+ " <br> 还剩 :"
							+ (Ext
									.getCmp("DJ.order.Deliver.batchDeliverapplyEdit.ftotalnum")
									.getValue() - val)

				},
				format : "0,000",
				summaryType : "sum",
				dataIndex : "famount",
				text : "数量",
				editor : {
					xtype : "numberfield",
					allowBlank : false,
					minValue : 1,
					step : 1,
					minText : '填写的数量要大于等于1',
					// negativeText :'不能填写负数',
					blankText : "请填写数量"
				}
			}, 
				{
					xtype : "datecolumn",
					format : "Y-m-d G:i:s",
					width : 150 ,
					dataIndex : "farrivetime",
					text : "配送时间",
					editor : {
						xtype : "datetimefield",
						blankText : "请选择配送时间",
						format : "Y-m-d",
						allowBlank : false
						
//						xtype : "datefield",
//						blankText : "请选择配送时间",
//						format : "Y-m-d",
//						allowBlank : false
//						,validator : validate
					}
				}*/
	           {
					'header' : 'fcustproductid',
					'dataIndex' : 'fcustproductid',
					hidden : true,
					hideable : false,
					sortable : true
	           },{
	        	   'header' : 'faddressid',
					'dataIndex' : 'faddressid',
					hidden : true,
					hideable : false,
					sortable : true
	           },
				{
						'header' : '包装物名称',
						'dataIndex' : 'cutpdtname',
						width : 180,
						editor : {
							valueField : 'fname', // 组件隐藏值
							cautoquery:true,
							name : "cutpdtname",
							xtype : 'cCombobox',
							displayField : 'fnumber',// 组件显示值
							// fieldLabel : '客户产品 ',
							width : 260,
							labelWidth : 70,
							beforeExpand : function(){
								var grid = Ext.getCmp('DJ.order.Deliver.batchDeliverapplyEdit.cutpdtname');
								grid.down('toolbar').hide();
							},
							MyDataChanged : function(com) {
								var rows = Ext.getCmp("SplitOrderEditGridPanel").getSelectionModel().getSelection();
									rows[0].set("fcustproductid", com[0].data.fid);
							},		
							MyConfig : {
								width : 800,// 下拉界面
								height : 200,// 下拉界面
								id : "DJ.order.Deliver.batchDeliverapplyEdit.cutpdtname",
								url : 'GetCustproductList.do', // 下拉数据来源
								fields : [{
									name : 'fid'
								}, {
									name : 'fname',
									myfilterfield : 'fname', // 查找字段，发送到服务端
									myfiltername : '名称', // 在过滤下拉框中显示的名称
									myfilterable : true
										// 该字段是否查找字段
										}, {
											name : 'fnumber',
											myfilterfield : 'fnumber', // 查找字段，发送到服务端
											myfiltername : '编码', // 在过滤下拉框中显示的名称
											myfilterable : true
										// 该字段是否查找字段
										}
								],
								columns : [
								{
									'header' : '产品名称',
									'dataIndex' : 'fname',
									sortable : true,
									width : 200,
									filter : {
										type : 'string'
									}
								}, {
									'header' : '编码',
									'dataIndex' : 'fnumber',
									sortable : true,
									width : 200,
									filter : {
										type : 'string'
									}
								}]
							}
						},
						sortable : true
					}, {
						'header' : '配送数量',
						width : 60,
						'dataIndex' : 'famount',
						sortable : true,
						allowBlank : false,
						editor : {
							xtype : 'numberfield'
						}
					}, {
						'header' : '配送时间',
						'dataIndex' : 'farrivetime',
//						xtype : "datecolumn",
						format : "Y-m-d H:i",
						minValue: new Date(),
						width : 140,
						sortable : true,
						editor : Ext.create('Ext.ux.form.DateTimeField', {
							allowBlank : false,
							format : 'Y-m-d H:i'
						})
					}, {
						'header' : '送货地址',
						'dataIndex' : 'faddress',
						width : 200,
						editor : {
				//			valueField : 'fname', // 组件隐藏值
							valueField:'fname',
							cautoquery:true,
							name : "faddress",
							xtype : 'cCombobox',
							width : 260,
							labelWidth : 70,
							displayField : 'fnumber',// 组件显示值
							beforeExpand : function(){
								var grid = Ext.getCmp('DJ.order.Deliver.batchDeliverapplyEdit.faddress');
								grid.down('toolbar').hide();
							},
							MyDataChanged : function(com) {
								var rows = Ext.getCmp("SplitOrderEditGridPanel").getSelectionModel().getSelection();
									rows[0].set("faddressid", com[0].data.fid);
							},																				
							MyConfig : {
								width : 800,// 下拉界面
								height : 200,// 下拉界面
								id : 'DJ.order.Deliver.batchDeliverapplyEdit.faddress',
								url : 'GetAddressList.do', // 下拉数据来源
								fields : [{
									name : 'fid'
								}, {
									name : 'fname',
									myfilterfield : 'tba.fname',
									myfiltername : '名称',
									myfilterable : true
								}, {
									name : 'fnumber',
									myfilterfield : 'tba.fnumber',
									myfiltername : '编码',
									myfilterable : true
								}],
								columns : [{
									'header' : '地址名称',
									'dataIndex' : 'fname',
									width : 405,
									sortable : true
								}, {
									'header' : '地址编码',
									'dataIndex' : 'fnumber',
									width : 80,
									sortable : true
								}
								]
							}
						},
						width : 200,
						sortable : true
					}, {
						'header' : '联系人',
						'dataIndex' : 'flinkman',
						width : 50,
						sortable : true,
						editor : {
							
						}
					}, {
						'header' : '联系电话',
						'dataIndex' : 'flinkphone',
						sortable : true,
						editor : {
							
						}
					}, {
						'header' : '备注',
						'dataIndex' : 'fdescription',
						width : 160,
						sortable : true,
						editor : {
							
						}
					}
				],
			features : [{
				ftype : "summary"
			}],
			selModel : Ext.create("Ext.selection.CheckboxModel", {}),
			plugins : [Ext.create("Ext.grid.plugin.CellEditing", {
				clicksToEdit : 2,
				errorSummary : false,
				saveBtnText : "更新",
				cancelBtnText : "取消",
				autoCancel : false

			})],
			dockedItems : [{
				xtype : "toolbar",
				dock : "top",
				items : [{
					xtype : "button",
					text : "添加",
					handler : function() {
						noOperations = false;
//						var pr = Ext.create("Deliverapply",{
//						fid: Ext.getCmp("DJ.order.Deliver.batchDeliverapplyEdit.deliverorderid").getValue(),
//						farrivetime: Ext.getCmp("DJ.order.Deliver.batchDeliverapplyEdit.farrivetime").getValue()
//						});
//						sgridStore.add(pr);
						for (var i = 0; i < 5; i++) {
							var pr = Ext.create("Deliverapply",{
								famount : 0
							});
							sgridStore.add(pr);
						}
					}
				}, {
					xtype : "button",
					text : "删除",
					handler : function() {
						Ext.Msg.confirm("提示", "确定删除?", function(id) {
							if (id == "yes") {
								noOperations = false;
								var rows = Ext
										.getCmp("SplitOrderEditGridPanel")
										.getSelectionModel().getSelection();
								sgridStore.remove(rows);
							}
						})
					}
				}]
			}]
		});
		
		me.callParent(arguments);
		
	}
});
Ext.define("DJ.order.Deliver.batchDeliverapplyEdit", {
	extend : "Ext.window.Window",
	id : "DJ.order.Deliver.batchDeliverapplyEdit",
	closeAction : "hide",
	height : 357,
	width : 960,
	title : "批量新增编辑界面",
	modal : true,
	resizable : true,
	layout : {
		align : "stretch",
		type : "vbox"
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [/*{
				xtype : "textfield",
				id : "DJ.order.Deliver.batchDeliverapplyEdit.orderfnumber",
				fieldLabel : "配送单号",
				readOnly : true,
				flex : 1,
				width : 400,
				labelWidth : 80
			}, */{
				xtype : "textfield",
				id : "DJ.order.Deliver.batchDeliverapplyEdit.deliverorderid",
				fieldLabel : "FID",
				hidden : true,
				flex : 1
			}, {
				xtype : "textfield",
				id : "DJ.order.Deliver.batchDeliverapplyEdit.farrivetime",
				fieldLabel : "farrivetime",
				hidden : true,
				flex : 1
			}
			, {
				xtype : "textfield",
				id : "DJ.order.Deliver.batchDeliverapplyEdit.ftotalnum",
				fieldLabel : "Totalnum",
				hidden : true,
				flex : 1
			}
			, {
				flex : 10,
				xtype : "splitordereditgridpanel",
				height : 250

			}],
			listeners : {
				'beforeshow' : function(win) {
					sgridStore.removeAll();
					for (var i = 0; i < 5; i++) {
						var pr = Ext.create("Deliverapply",{
							famount : 0
						});
						sgridStore.add(pr);
					}
				},
				'beforehide' : function(win) {
					if (sgridStore.getModifiedRecords().length != 0) {
						noOperations = false
					}
					if (cConfirms || noOperations) {
						cConfirms = false;
						noOperations = true;
						return true
					} else {
						Ext.Msg.confirm("提示", "确定要在没有保存之前离开？！", function(id) {
							if (id == "yes") {
								cConfirms = true;
								noOperations = true;
								closesplitWin();
								// win.hide();
							}
						});
						return false
					}
				}
			},
			buttons : [{
				xtype : "button",
				text : "保存",
				handler : saveToServerWithverifys
			}, {
				xtype : "button",
				text : "关闭",
				handler : closesplitWin
			}],
			buttonAlign : "right"
		});
		me.callParent(arguments)
	}
});

function closesplitWin() {
	var windows = Ext.getCmp("DJ.order.Deliver.batchDeliverapplyEdit");
	if (windows != null) {

		windows.hide();
		Ext.getCmp("DJ.order.Deliver.DeliversCustList").store.load();
	}

}

var cConfirms = false, noOperations = true;