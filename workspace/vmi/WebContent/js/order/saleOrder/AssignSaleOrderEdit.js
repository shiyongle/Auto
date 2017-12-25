function addNew() {
	noOperation = false;
	var pr = Ext.create("Saleorder", {
		Saleorderid : Ext.getCmp("DJ.order.saleOrder.saleorderid").getValue()
			});
	gridStore.add(pr);
}
function removeRows() {
	Ext.Msg.confirm("提示", "确定删除?", function(id) {
				if (id == "yes") {
					noOperation = false;
					var rows = Ext.getCmp("AssginSaleOrderEditGridPanel")
							.getSelectionModel().getSelection();
					gridStore.remove(rows);
				}
			})
}
function saveToServer() {
	noOperation = true;
	
	

	gridStore.sync({
				success : function() {
					showMsgAfterSync(true);
					closeWinEP();
					
				},
				failure : function() {
					showMsgAfterSync(false)
				}
			})
}

function verifyfamountNotZero() {
	var r = true;
	for (var i = 0, n = gridStore.data.items.length; i < n; i++) {
		if (gridStore.data.items[i].data.famount == 0) {
			r = false
		}
	}
	return r
}
function saveToServerWithverify() {
	if (verifyTotalnum() && verifyProductPlanid() && verifyfamountNotZero()) {
		saveToServer()
	} else {
		if (!verifyTotalnum()) {
			Ext.Msg.alert("提示", "分配数量的和必须为"+Ext.getCmp("DJ.order.saleOrder.ftotalnum").getValue()+"!")
		} else {
			if (!verifyProductPlanid()) {
				Ext.Msg.alert("提示", "制造商字段不能为空  !")
			} else if (!verifyfamountNotZero()) {
				Ext.Msg.alert("提示", "分配数量不能为0  !")
			}
		}
	}
}
function verifyTotalnum() {
	var s = 0;
	for (var i = 0; i < gridStore.data.items.length; i++) {
		s += gridStore.data.items[i].data.famount;
	}
	var totalnum= Ext.getCmp("DJ.order.saleOrder.ftotalnum").getValue();
	if (s == totalnum) {
		return true
	} else {
		return false
	}
}
function verifyProductPlanid() {
	var r = true;
	for (var i = 0; i < gridStore.data.items.length; i++) {
		if (gridStore.data.items[i].data.fsupplierid == "") {
			r = false
		}
	}
	return r
}
function showMsgAfterSync(succes) {
	var s = "保存成功";
	if (!succes) {
		s = "保存失败"
	}
	Ext.Msg.alert("提示", s)
}

//function showItems() {
//	var rows = Ext.getCmp("ProportionEditGridStore").getSelectionModel()
//			.getSelection();
//	for (var i = 0; i < rows.length; i++) {
//		var result = "";
//		result += Ext.JSON.encode(rows[i]) + "<br>"
//	}
//	Ext.Msg.alert("", result)
//}
Ext.define("Saleorder", {
			extend : "Ext.data.Model",
			fields : [{
						name : "fsupplierid"
					}, {
						name : "fsupplierfname"
					}, {
						name : "famount"
					}, {
						name : "Saleorderid"
					}]
		});
Ext.define("AssginOrderEditGridStore", {
			extend : "Ext.data.Store",
			requires : ["Saleorder"],
			constructor : function(cfg) {
				var me = this;
				cfg = cfg || {};
				me.callParent([Ext.apply({
							model : "Saleorder",
							storeId : "AssginOrderEditGridStore",
							data:'',
							proxy : {
								type : "ajax",
								api : {
									create : "saveAssginSaleOrder.do"
								},
								writer : {
									type : "json",
									root : "data"
								}
							}				
						}, cfg)])
			}
		});
function formatfusedstatus(value) {
	return value == '1' ? '启用' : '禁用';
}
gridStore = Ext.create("AssginOrderEditGridStore");
Ext.define("AssginSaleOrderEditGridPanel", {
	extend : "Ext.grid.Panel",
//	alias : "widget.proportioneditgridpanel",
	alias : "widget.assginsaleordereditgridpanel",
	id : "AssginSaleOrderEditGridPanel",
	height : 138,
	width : 596,
	store : gridStore,
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			columns : [{
						xtype : "rownumberer",
						text : "No"
					}, {
						width : 164,
						dataIndex : "fsupplierfname",
						text : "制造商",
						editor : {
							id:"DJ.order.saleOrder.fsupplierfname",
							name : "fsupplierid",
							allowBlank : false,
							blankText : "请选择制造商",
							editable : false,
							xtype : "cCombobox",
							displayField : "fid",
							valueField : "fname",
							listeners : {
								change : function(com) {
									var rows = Ext.getCmp("AssginSaleOrderEditGridPanel").getSelectionModel().getSelection();
									rows[0].set("fsupplierid", com.getRawValue())
									
								}
							},
//							 beforeExpand : function() {
//							 var editwidgt=	Ext.getCmp("DJ.order.saleOrder.fsupplierfname");
//							var productid=Ext.getCmp("DJ.order.saleOrder.fproductid").getValue();//_combo.getValue();
//	        	    		editwidgt.setDefaultfilter([{
//								myfilterfield : "d.fproductdefid",
//								CompareType : "=",
//								type : "string",
//								value : productid
//							}]);
//							editwidgt.setDefaultmaskstring(" #0 ");
//	        	    		
//    	        	   		 },
							MyConfig : {
								width : 800,
								height : 200,
								url : "GetSupplierList.do",
									fields : [{
										name : 'fid'
									}, {
										name : 'fname',
										myfilterfield : 'fname',
										myfiltername : '名称',
										myfilterable : true
									}, {
										name : 'fnumber',
										myfilterfield : 'fnumber',
										myfiltername : '编码',
										myfilterable : true
									}, {
										name : 'fdescription'
									}, {
										name : 'femail'
									}, {
										name : 'ftel'
									}, {
										name : 'fcreatetime'
									}, {
										name : 'flastupdatetime'
									}, {
										name : 'fsimplename'
									}, {
										name : 'fartificialperson'
									}, {
										name : 'fbarcode'
									}, {
										name : 'fbusiexequatur'
									}, {
										name : 'fbizregisterno'
									}, {
										name : 'fbusilicence'
									}, {
										name : 'fgspauthentication'
									}, {
										name : 'ftaxregisterno'
									}, {
										name : 'fusedstatus'
									}, {
										name : 'fmnemoniccode'
									}, {
										name : 'fforeignname'
									}, {
										name : 'faddress'
									}, {
										name : 'fcreatorid'
									}, {
										name : 'flastupdateuserid'
									}, {
										name : 'fcountry'
									}, {
										name : 'fcity'
									}],
									columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
										'header' : 'fid',
										'dataIndex' : 'fid',
										hidden : true,
										hideable : false,
										sortable : true
									}, {
										'header' : '供应商名称',
										'dataIndex' : 'fname',
										sortable : true
									}, {
										'header' : '编码',
										'dataIndex' : 'fnumber',
										sortable : true
									}, {
										'header' : '简称',
										width : 70,
										'dataIndex' : 'fsimplename',
										sortable : true
									}, {
										'header' : '法人代表',
										width : 70,
										'dataIndex' : 'fartificialperson',
										sortable : true
									}, {
										'header' : '条形码',
										hidden : true,
										'dataIndex' : 'fbarcode',
										sortable : true
									}, {
										'header' : '经营许可证',
										'dataIndex' : 'fbusiexequatur',
										sortable : true
									}, {
										'header' : '工商注册号',
										'dataIndex' : 'fbizregisterno',
										sortable : true
									}, {
										'header' : '营业执照',
										'dataIndex' : 'fbusilicence',
										sortable : true
									}, {
										'header' : 'GSP认证',
										'dataIndex' : 'fgspauthentication',
										sortable : true
									}, {
										'header' : '税务登记号',
										'dataIndex' : 'ftaxregisterno',
										sortable : true
									}, {
										'header' : '状态',
										width : 50,
										'dataIndex' : 'fusedstatus',
										renderer : formatfusedstatus,
										sortable : true
									}, {
										'header' : '助记码',
										'dataIndex' : 'fmnemoniccode',
										sortable : true
									}, {
										'header' : '外文名称',
										hidden : true,
										'dataIndex' : 'fforeignname',
										sortable : true
									}, {
										'header' : '地址',
										'dataIndex' : 'faddress',
										sortable : true
									}, {
										'header' : '手机',
										hidden : true,
										'dataIndex' : 'ftel',
										sortable : true
									}, {
										'header' : '邮箱',
										hidden : true,
										'dataIndex' : 'femail',
										sortable : true
									}, {
										'header' : '国家',
										hidden : true,
										width : 50,
										'dataIndex' : 'fcountry',
										sortable : true
									}, {
										'header' : '城市',
										hidden : true,
										width : 50,
										'dataIndex' : 'fcity',
										sortable : true
									}, {
										'header' : '修改时间',
										'dataIndex' : 'flastupdatetime',
										width : 140,
										sortable : true
									}, {
										'header' : '创建时间',
										'dataIndex' : 'fcreatetime',
										width : 140,
										sortable : true
									}, {
										'header' : '描述',
										hidden : true,
										'dataIndex' : 'fdescription',
										sortable : true
									}]
							}
						}
					}, {
						xtype : "numbercolumn",
						summaryRenderer : function(val, params, data) {
							return "总计 : " + val + " <br> 还剩 :" + (Ext.getCmp("DJ.order.saleOrder.ftotalnum").getValue() - val)
									
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
							minText:'填写的数量要大于等于1',
//							negativeText :'不能填写负数',
							blankText : "请填写数量"
						}
					}],
			features : [{
						ftype : "summary"
					}],
			selModel : Ext.create("Ext.selection.CheckboxModel", {}),
			plugins : [Ext.create("Ext.grid.plugin.RowEditing", {
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
									handler : addNew
								}, {
									xtype : "button",
									text : "删除",
									handler : removeRows
								}]
					}]
		});
		me.callParent(arguments)
	}
});
Ext.define("DJ.order.saleOrder.AssignSaleOrderEdit", {
			extend : "Ext.window.Window",
			id : "DJ.order.saleOrder.AssignSaleOrderEdit",
			closeAction : "hide",
			height : 357,
			width : 611,
			title : "指定订单",
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
										id : "DJ.order.saleOrder.saleorderFnumber",
										fieldLabel : "生产订单编号",
										readOnly : true,
										flex : 1,
										width : 400,
										labelWidth : 80
										}, {
										xtype : "textfield",
										id : "DJ.order.saleOrder.saleorderid",
										fieldLabel : "FID",
										hidden : true,
										flex : 1
									}, {
										xtype : "textfield",
										id : "DJ.order.saleOrder.ftotalnum",
										fieldLabel : "Totalnum",
										hidden : true,
										flex : 1
									}, {
										xtype : "textfield",
										id : "DJ.order.saleOrder.fproductid",
										fieldLabel : "fproductid",
										hidden : true,
										flex : 1
									}, {
										flex : 10,
										xtype : "assginsaleordereditgridpanel",
										height : 250
									
									}],
							listeners : {
								'beforeshow':function(win)
								{
									gridStore.removeAll();
									
								},
								'beforehide': function(win) {
									if (gridStore.getModifiedRecords().length != 0) {
										noOperation = false
									}
									if (cConfirm || noOperation) {
										cConfirm = false;
										noOperation = true;
										return true
									} else {
										Ext.Msg.confirm("提示", "确定要在没有保存之前离开？！",
												function(id) {
													if (id == "yes") {
														cConfirm = true;
														noOperation = true;
														closeWinEP();
//														win.hide();
													}
												});
										return false
									}
								}
							},
							buttons : [{
										id : "DJ.System.product.SaveButton",
										xtype : "button",
										text : "保存",
										handler : saveToServerWithverify
									}, {
										id : "DJ.System.product.ColseButton",
										xtype : "button",
										text : "关闭",
										handler : closeWinEP
									}],
							buttonAlign : "right"
						});
				me.callParent(arguments)
			}
		});

function closeWinEP() {
	var windows = Ext.getCmp("DJ.order.saleOrder.AssignSaleOrderEdit");
	if (windows != null) {

		windows.hide();
		Ext.getCmp("DJ.order.saleOrder.SaleOrderList").store.load();
	}
		
}

var cConfirm = false, noOperation = true;