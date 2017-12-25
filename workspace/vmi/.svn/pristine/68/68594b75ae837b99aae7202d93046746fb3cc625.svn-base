function addNew() {
	noOperation = false;
	var pr = Ext.create("Delivers", {
		deliverid : Ext.getCmp("DJ.order.Deliver.deliversid").getValue()
			});
	gridStore.add(pr);
}
function removeRows() {
	Ext.Msg.confirm("提示", "确定删除?", function(id) {
				if (id == "yes") {
					noOperation = false;
					var rows = Ext.getCmp("AssginOrderEditGridPanel")
							.getSelectionModel().getSelection();
					gridStore.remove(rows);
				}
			})
}
function saveToServer() {
	noOperation = true;
	
	var me=Ext.getCmp("DJ.order.Deliver.AssignOrderEdit").getEl();
	me.mask("系统处理中,请稍候……");
	gridStore.sync({
				success : function() {
					me.unmask();
					showMsgAfterSync(true);
					closeWinEP();
					
				},
				failure : function() {
					me.unmask();
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
			Ext.Msg.alert("提示", "分配数量的和必须为"+Ext.getCmp("DJ.order.Deliver.ftotalnum").getValue()+"!")
		} else {
			if (!verifyProductPlanid()) {
				Ext.Msg.alert("提示", "制造商订单字段不能为空  !")
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
	var totalnum= Ext.getCmp("DJ.order.Deliver.ftotalnum").getValue();
	if (s == totalnum) {
		return true
	} else {
		return false
	}
}
function verifyProductPlanid() {
	var r = true;
	for (var i = 0; i < gridStore.data.items.length; i++) {
		if (gridStore.data.items[i].data.fproductplanid == "") {
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
Ext.define("Delivers", {
			extend : "Ext.data.Model",
			fields : [{
						name : "fproductplanid"
					}, {
						name : "fproductplanfnumber"
					}, {
						name : "famount"
					}, {
						name : "deliverid"
					}]
		});
Ext.define("AssginOrderEditGridStore", {
			extend : "Ext.data.Store",
			requires : ["Delivers"],
			constructor : function(cfg) {
				var me = this;
				cfg = cfg || {};
				me.callParent([Ext.apply({
							model : "Delivers",
							storeId : "AssginOrderEditGridStore",
							data:'',
							proxy : {
								type : "ajax",
								api : {
									create : "saveAssginOrder.do"
								},
								writer : {
									type : "json",
									root : "data"
								}
							}				
						}, cfg)])
			}
		});
gridStore = Ext.create("AssginOrderEditGridStore");
Ext.define("AssginOrderEditGridPanel", {
	extend : "Ext.grid.Panel",
//	alias : "widget.proportioneditgridpanel",
	alias : "widget.assginordereditgridpanel",
	id : "AssginOrderEditGridPanel",
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
						dataIndex : "fproductplanfnumber",
						text : "制造订单",
						editor : {
							id:"DJ.order.Deliver.fproductplanfnumber",
							name : "fproductplanid",
							allowBlank : false,
							blankText : "请选择制造订单",
							editable : false,
							xtype : "cCombobox",
							displayField : "fid",
							valueField : "fnumber",
							listeners : {
								change : function(com) {
									var rows = Ext
											.getCmp("AssginOrderEditGridPanel")
											.getSelectionModel().getSelection();
									rows[0].set("fproductplanid", com
															.getRawValue())
								}
							},
//							Defaultfilter:[{
//								myfilterfield : "d.fproductdefid",
//								CompareType : "=",
//								type : "string",
////								value:"1"
//								value : Ext.getCmp("DJ.order.Deliver.fproductid").getValue()
//							}],
//							Defaultmaskstring:" #0 ",
							 beforeExpand : function() {
							 var editwidgt=	Ext.getCmp("DJ.order.Deliver.fproductplanfnumber");
							var productid=Ext.getCmp("DJ.order.Deliver.fproductid").getValue();//_combo.getValue();
	        	    		editwidgt.setDefaultfilter([{
								myfilterfield : "d.fproductdefid",
								CompareType : "=",
								type : "string",
								value : productid
							}]);
							editwidgt.setDefaultmaskstring(" #0 ");
	        	    		
    	        	   		 },
							MyConfig : {
								width : 800,
								height : 200,
								url : "GetProductPlans.do",
									fields : [{
										name : 'fid'
									}, {
										name : 'fnumber',
										myfilterfield : 'd.fnumber',
										myfiltername : '编号',
										myfilterable : true
									}, {
										name : 'cname',
										myfilterfield : 'c.fname',
										myfiltername : '客户名称',
										myfilterable : true
									}, {
										name : 'pname',
										myfilterfield : 'p.fname',
										myfiltername : '客户产品名称',
										myfilterable : true
									}, {
										name : 'fname',
										myfilterfield : 'f.fname',
										myfiltername : '产品名称',
										myfilterable : true
									}, {
										name : 'sname'
									}, {
										name : 'fspec'
									}, {
										name : 'farrivetime'
									}, {
										name : 'fbizdate'
									}, {
										name : 'famount'
									}, {
										name : 'fcreatetime'
									}, {
										name : 'u1_fname'
									}, {
										name : 'fordertype'
									}, {
										name : 'fseq'
									}, {
										name : 'fstockoutqty'
									}, {
										name : 'fstockinqty'
									}, {
										name : 'fstoreqty'
									},{
										name : 'faffirmed'
									},{
										name : 'salefnumber'	
									}],
					columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						'header' : '编号',
						'dataIndex' : 'fnumber',
						sortable : true
					}, {
						'header' : '订单类型',
						'dataIndex' : 'fordertype',
						sortable : true,
						 renderer: function(value){
						        if (value == 1) {
						            return '普通纸板';
						        }else if(value==2){
						        	return '套装纸板';}
					        	else if(value==3){
					        	return '普通纸箱';}
					        	else if(value==4){
					        	return '套装纸箱';}
					        	else if(value==5){
					        	return '面板产品';}
						    }
					
					}, {
						'header' : '客户名称',
						'dataIndex' : 'cname',
						sortable : true
					}, {
						'header' : '客户产品名称',
						'dataIndex' : 'pname',
						sortable : true
					}, {
						'header' : '产品名称',
						'dataIndex' : 'fname',
						sortable : true
					}, {
						'header' : '数量',
						'dataIndex' : 'famount',
						sortable : true
					}, {
						'header' : '入库',
						'dataIndex' : 'fstockinqty',
						sortable : true
					}, {
						'header' : '出库',
						'dataIndex' : 'fstockoutqty',
						sortable : true
					}, {
						'header' : '存量',
						'dataIndex' : 'fstoreqty',
						sortable : true
					}, {
						'header' : '制造商名称',
						'dataIndex' : 'sname',
						sortable : true
					}, {
						'header' : '规格',
						'dataIndex' : 'fspec',
						sortable : true
					}, {
						'header' : '交期',
						'dataIndex' : 'farrivetime',
						sortable : true
					}, {
						'header' : '业务时间',
						'dataIndex' : 'fbizdate',
						sortable : true
						}, {
							text : '创建人',
							dataIndex : 'u1_fname',
							sortable : true
						}, {
							text : '创建时间',
							dataIndex : 'fcreatetime',
							width : 150,
							sortable : true
						}, {
						'header' : '订单编号',
						'dataIndex' : 'salefnumber',
						sortable : true  
						}, {
						'header' : '订单分录',
						'dataIndex' : 'fseq',
						sortable : true
					}]
							}
						}
					}, {
						xtype : "numbercolumn",
						summaryRenderer : function(val, params, data) {
							return "总计 : " + val + " <br> 还剩 :" + (Ext.getCmp("DJ.order.Deliver.ftotalnum").getValue() - val)
									
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
Ext.define("DJ.order.Deliver.AssignOrderEdit", {
			extend : "Ext.window.Window",
			id : "DJ.order.Deliver.AssignOrderEdit",
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
										id : "DJ.order.Deliver.deliverFnumber",
										fieldLabel : "要货管理单号",
										readOnly : true,
										flex : 1,
										width : 400,
										labelWidth : 80
										}, {
										xtype : "textfield",
										id : "DJ.order.Deliver.deliversid",
										fieldLabel : "FID",
										hidden : true,
										flex : 1
									}, {
										xtype : "textfield",
										id : "DJ.order.Deliver.ftotalnum",
										fieldLabel : "Totalnum",
										hidden : true,
										flex : 1
									}, {
										xtype : "textfield",
										id : "DJ.order.Deliver.fproductid",
										fieldLabel : "fproductid",
										hidden : true,
										flex : 1
									}, {
										flex : 10,
										xtype : "assginordereditgridpanel",
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
	var windows = Ext.getCmp("DJ.order.Deliver.AssignOrderEdit");
	if (windows != null) {

		windows.hide();
		Ext.getCmp("DJ.order.Deliver.DeliversOrderList").store.load();
	}
		
}

var cConfirm = false, noOperation = true;