
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
	sgridStore.getAt(0).set("ftotalnum",Ext.getCmp("DJ.order.Deliver.SplitDeliversEdit.ftotalnum").getValue());
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

function verifysplitfamountNotZero() {
	var r = true;
	for (var i = 0, n = sgridStore.data.items.length; i < n; i++) {
		if (sgridStore.data.items[i].data.famount == 0) {
			r = false
		}
	}
	return r
}
function saveToServerWithverifys() {
	if (verifysplitTotalnum()  && verifysplitfamountNotZero()) {
		saveToServers()
	} else {
		if (!verifysplitTotalnum()) {
			Ext.Msg
					.alert("提示", "数量的和必须为"
							+ Ext.getCmp("DJ.order.Deliver.SplitDeliversEdit.ftotalnum")
									.getValue() + "!")
		} else {
			if (!verifysplitfamountNotZero()) {
				Ext.Msg.alert("提示", "拆分数量不能为0  !")
			}
		}
	}
}
function verifysplitTotalnum() {
	var s = 0;
	for (var i = 0; i < sgridStore.data.items.length; i++) {
		s += sgridStore.data.items[i].data.famount;
	}
	var totalnum = Ext.getCmp("DJ.order.Deliver.SplitDeliversEdit.ftotalnum").getValue();
	if (s == totalnum) {
		return true
	} else {
		return false
	}
}

function showMsgsAfterSync(obj) {
	Ext.Msg.alert("提示", obj.msg)
	if(!obj.success){
		cConfirms = true;
//		Ext.getCmp("DJ.order.Deliver.SplitDeliversEdit").close();
	}
	Ext.getCmp("DJ.order.Deliver.DeliversList").store.load();
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
Ext.define("Deliverorder", {
	extend : "Ext.data.Model",
	fields : [{
		name : "fid"
	}, {
		name : "famount"
	}, {
		name : "farrivetime"
	},{
		name : "ftotalnum"
//		,defaultValue: 'Unknown'
	}
	]
});
Ext.define("SplitOrderEditGridStore", {
	extend : "Ext.data.Store",
	requires : ["Deliverorder"],
	constructor : function(cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
			model : "Deliverorder",
			storeId : "SplitOrderEditGridStore",
			data : '',
			proxy : {
				type : "ajax",
				timeout : 300000,
				api : {
					create : "saveSplitDeliverapply.do"
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
	width : 596,
	store : sgridStore,
	requires: ['Ext.ux.form.DateTimeField'],
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			columns : [{
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
									.getCmp("DJ.order.Deliver.SplitDeliversEdit.ftotalnum")
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
						var pr = Ext.create("Deliverorder",{
						fid: Ext.getCmp("DJ.order.Deliver.SplitDeliversEdit.deliverorderid").getValue(),
						farrivetime: Ext.getCmp("DJ.order.Deliver.SplitDeliversEdit.farrivetime").getValue()
						});
						sgridStore.add(pr);
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
		me.callParent(arguments)
	}
});
Ext.define("DJ.order.Deliver.SplitDeliversEdit", {
	extend : "Ext.window.Window",
	id : "DJ.order.Deliver.SplitDeliversEdit",
	closeAction : "hide",
	height : 357,
	width : 611,
	title : "拆分要货申请",
	modal : true,
	resizable : true,
	layout : {
		align : "stretch",
		type : "vbox"
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
				xtype : "textfield",
				id : "DJ.order.Deliver.SplitDeliversEdit.orderfnumber",
				fieldLabel : "配送单号",
				readOnly : true,
				flex : 1,
				width : 400,
				labelWidth : 80
			}, {
				xtype : "textfield",
				id : "DJ.order.Deliver.SplitDeliversEdit.deliverorderid",
				fieldLabel : "FID",
				hidden : true,
				flex : 1
			}, {
				xtype : "textfield",
				id : "DJ.order.Deliver.SplitDeliversEdit.farrivetime",
				fieldLabel : "farrivetime",
				hidden : true,
				flex : 1
			}
			, {
				xtype : "textfield",
				id : "DJ.order.Deliver.SplitDeliversEdit.ftotalnum",
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
	var windows = Ext.getCmp("DJ.order.Deliver.SplitDeliversEdit");
	if (windows != null) {

		windows.hide();
		Ext.getCmp("DJ.order.Deliver.DeliverorderList").store.load();
	}

}

var cConfirms = false, noOperations = true;