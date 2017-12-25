Ext.QuickTips.init();
Ext.app.myKeypressSetObj = {
	setmyKeypressSetObjProperty : function(myKey, myHandler) {
		this.myKey = myKey;
		this.myHandler = myHandler;
	},
	myKey : Ext.EventObject.ENTER,
	keypress : function(obj, e, eOpts) {
		if (e.getKey() == this.myKey) {
			this.myHandler();
		}
	},
	myHandler : function() {
	}
};
function addNodes() {
	var tree = Ext.getCmp("DJ.System.product.CustProductTreeEdit.CustProductTreeEdit");
	var parent = tree.getSelectionModel().getSelection()[0];
	if (!parent) {
		parent = tree.store.tree.root;
	}
	var rows = Ext.getCmp("DJ.System.product.CustProductTreeEdit.CustProductDefList")
			.getSelectionModel().getSelection();
	if(parent.childNodes.length>=1)
	{
		Ext.MessageBox.alert('提示', '只能关联一个产品!');
		return;
	}
	if(rows.length!=1)
	{
		Ext.MessageBox.alert('提示', '只能选择一个产品关联!');
		return;
	}
	var fproductid =  rows[0].data.d_fid;
	Ext.Ajax.request({
		url : 'getCustProductsRelevance.do',
		params : {
			fproductid : fproductid
		},
		success: function(resp,opts) {
			var obj = Ext.decode(resp.responseText);
			var total = obj.total;
			if(total==0){
				var depthPT = parent.data.depth;
				if (depthPT < 1) {
					for (var i = 0, len = rows.length; i < len; i++) {
//						var innerLenth = len;
//						var succes = true;
							var rec = Ext.create("DJ.System.product.CustProductTreeEdit.TreeTest",{
									text : rows[i].data.d_fname,
//									id : rows[i].data.d_fid,
//									parentId : parent.data.id,
									productid : rows[i].data.d_fid,
									custproductid : parent.data.id,
									leaf : true,
									mcount : Ext.getCmp("DJ.System.product.CustProductTreeEdit.CustProductTreeEdit.defaultAddNo").value,
									depth : depthPT + 1
								});
//						var rec = new DJ.System.product.CustProductTreeEdit.TreeTest({
//									text : rows[i].data.d_fname,
////									id : rows[i].data.d_fid,
////									parentId : parent.data.id,
//									productid : rows[i].data.d_fid,
//									custproductid : parent.data.id,
//									leaf : true,
//									mcount : Ext.getCmp("DJ.System.product.CustProductTreeEdit.CustProductTreeEdit.defaultAddNo").value,
//									depth : depthPT + 1
//								});
						parent.appendChild(rec);
//						rec.save({
//									action : "create",
//									parentNode : parent,
//									success : function(rec, opt) {
//										innerLenth--;
//										if (innerLenth == 0) {
//											refreshStores();
//											tree.expandNode(parent);
//										}
//									},
//									failure : function(e, op) {
//										Ext.Msg.alert("发生错误", op.error);
//										succes = false;
//									},
//									scope : tree
//								});
				 	}
					tree.store.sync(
						{
							success : function(rec, opt) {
//								var obj = tree.store.getProxy().getReader().rawData;
//								parent.data.id = obj.msg;
								refreshStores();
								tree.expandNode(parent);
										},
							failure : function(e, op) {
								var obj = tree.store.getProxy().getReader().rawData;
								Ext.Msg.alert("发生错误", obj.msg);
//								succes = false;
								refreshStores();
							}
						}
					);
				} else {
					Ext.Msg.alert("", "最多只能添加2层树！");
				}
			}else{
				Ext.Msg.alert('提示','产品资料已关联客户产品');
				return ;
			}
		}
	});


}
function deleteNode() {
	var tree = Ext.getCmp("DJ.System.product.CustProductTreeEdit.CustProductTreeEdit");
	var rs = tree.getSelectionModel().getSelection();
	if (rs.length > 0) {
		rs = rs[0];
		if (rs.data.root) {
			Ext.Msg.alert("删除节点", "根节点不允许删除！");
			return;
		}
		if (rs.isExpandable() || rs.hasChildNodes()) {
			Ext.Msg.alert("删除节点", "请先删除所有子节点，再删除该节点！");
			return;
		} else {
//			var content = "确定删除节点：" + rs.data.text + "？";
			var content = "确定删除当前节点？";
			Ext.Msg.confirm("删除节点", content, function(btn) {
						if (btn == "yes") {
							var rs = this.getSelectionModel().getSelection();
							if (rs.length > 0) {
								rs = rs[0];
								rs.remove();
								this.store.sync();
								refreshStores();
								this.view.select(0);
								this.view.focus(false);
							}
						}
					}, tree);
		}
	}
}
Ext.define("DJ.System.product.CustProductTreeEdit.ProductDefStore", {
			extend : "Ext.data.Store",
			storeId : "DJ.System.product.CustProductTreeEdit.ProductDefStore",
			fields : [{
						name : "d_fid"
					}, {
						name : "d_fname"
					}, {
						name : "d_fnumber"
					}, {
						name : "d_fcreatorid"
					}, {
						name : "d_fcreatetime"
					}, {
						name : "d_flastupdateuserid"
					}, {
						name : "d_flastupdatetime"
					}, {
						name : "u2_fname"
					}, {
						name : "u1_fname"
					}, {
						name : "d_fcharacter"
					}, {
						name : "d_fboxmodelid"
					}, {
						name : "d_feffect"
					}, {
						name : "d_fnewtype"
					}, {
						name : "d_fversion"
					}, {
						name : "d_fishistory"
					}],
			pageSize : 100,
			proxy : {
				type : "ajax",
				url : "getCustProductsWithoutTreeNode.do",
				extraParams : {
					dbfid : ""
				},
				reader : {
					type : "json",
					root : "data",
					totalProperty : "total"
				}
			},
			remoteFilter : true,
			autoLoad : true
		});
var productstore = Ext
		.create("DJ.System.product.CustProductTreeEdit.ProductDefStore");
var selModel = Ext.create("Ext.selection.CheckboxModel");
function formatEffect(value) {
	return value == "1" ? "是" : "否";
}
Ext.define("DJ.System.product.CustProductTreeEdit.MySimpleFilter", {
	id : "DJ.System.product.CustProductTreeEdit.MySimpleFilter",
	myFilter : function(action) {
		switch (action) {
			case "cus" :
				Ext
						.getStore("DJ.System.product.CustProductTreeEdit.ProductDefStore")
						.filter([{
									property : Ext.getCmp("custProductDefCmFilterFiled")
											.getValue(),
									anyMatch : true,
									value : Ext.getCmp("custProductDefTfCondition")
											.getValue()
								}]);
				Ext.getCmp("DJ.System.product.CustProductTreeEdit.CustProductDefList")
						.getSelectionModel().deselectAll();
				break;
			case "all" :
				Ext
						.getStore("DJ.System.product.CustProductTreeEdit.ProductDefStore")
						.clearFilter(true);
				Ext
						.getStore("DJ.System.product.CustProductTreeEdit.ProductDefStore")
						.load();
				Ext.getCmp("DJ.System.product.CustProductTreeEdit.CustProductDefList")
						.getSelectionModel().deselectAll();
				break;
		}
	},
	constructor : function(cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
					comboxStore : cfg.comboxStore
				}, cfg)]);
	}
});
var filters = {
	ftype : "filters",
	encode : true,
	local : false
};
Ext.define("DJ.System.product.CustProductTreeEdit.CustProductDefList", {
			extend : "Ext.grid.Panel",
			id : "DJ.System.product.CustProductTreeEdit.CustProductDefList",
			selModel : Ext.create("Ext.selection.CheckboxModel", {}),
			columnLines : true,
			disableSelection : false,
			title : "产品基础资料 ( 请在这里选择要添加的数据 ) ",
			emptyMsg : "没有数据显示",
			alias : "widget.custproductdeflist",
			listeners : {
				cellkeydown : function(ttb, td, cellIndex, record, tr,
						rowIndex, e, eOpts) {
					Ext.app.myKeypressSetObj.myKey = 13;
					Ext.app.myKeypressSetObj.myHandler = addNodes;
					Ext.app.myKeypressSetObj.keypress(ttb, e, eOpts);
				}
			},
			dockedItems : [{
						xtype : "pagingtoolbar",
						store : productstore,
						dock : "bottom",
						displayInfo : true,
						items : []
					}, {
						xtype : "toolbar",
						dock : "top",
						items : [{
									id : "custProductDefCmFilterFiled",
									xtype : "combobox",
									fieldLabel : "过滤字段",
									forceSelection : true,
									value : "d_fname",
									store : [["d_fname", "名称"],
											["d_fnumber", "编号"]]
								}, {
									id : "custProductDefTfCondition",
									xtype : "textfield",
									enableKeyEvents : true,
									listeners : {
										keypress : function(tf, e, eOpts) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												Ext.create("DJ.System.product.CustProductTreeEdit.MySimpleFilter")
														.myFilter("cus");
											}
										}
									}
								}, {
									text : "过滤",
									tooltip : "试试回车键",
									handler : function() {
										Ext.create("DJ.System.product.CustProductTreeEdit.MySimpleFilter")
												.myFilter("cus");
									}
								}, {
									text : "显示全部",
									handler : function() {
										Ext.create("DJ.System.product.CustProductTreeEdit.MySimpleFilter")
												.myFilter("all");
									}
								}]
					}],
			autoExpandColumn : "d_fcharacter",
			store : productstore,
			columns : [
					{
						xtype : "rownumberer"
					}, 
						{
						text : "编号",
						dataIndex : "d_fnumber",
						sortable : true,
						filter : {
							type : "string"
						}
					}, {
						text : "名称",
						dataIndex : "d_fname",
						sortable : true,
						filter : {
							type : "string"
						}
					}, {
						text : "版本号",
						dataIndex : "d_fversion",
						sortable : true
					}, {
						text : "特征",
						dataIndex : "d_fcharacter",
						sortable : true,
						width : 100
					}, {
						text : "箱型",
						dataIndex : "d_fboxmodelid",
						sortable : true
					}, {
						text : "类型",
						dataIndex : "d_fnewtype",
						sortable : true
					}, {
						text : "历史版本",
						dataIndex : "d_fishistory",
						renderer : formatEffect,
						sortable : true
					}, {
						text : "是否启用",
						dataIndex : "d_feffect",
						renderer : formatEffect,
						sortable : true
					}, {
						text : "修改人",
						dataIndex : "u2_fname",
						sortable : true
					}, {
						text : "修改时间",
						dataIndex : "d_flastupdatetime",
						width : 150,
						sortable : true
					}, {
						text : "创建人",
						dataIndex : "u1_fname",
						sortable : true
					}, {
						text : "创建时间",
						dataIndex : "d_fcreatetime",
						width : 150,
						sortable : true
					}, {
						text : "审核",
						dataIndex : "d_faudited",
						renderer : formatEffect,
						sortable : true,
						filter : {
							type : "boolean"
						}
					}, {
						text : "审核人",
						dataIndex : "u3_fname",
						sortable : true
					}, {
						text : "审核时间",
						dataIndex : "d_faudittime",
						width : 150,
						sortable : true
					}]
		});
function converInteger(v, record) {
	return record.data.isleaf == "1" ? true : false;
}
Ext.define("DJ.System.product.CustProductTreeEdit.TreeTest", {
			extend : "Ext.data.TreeModel",
			fields : [{
						name : "dbId"
					}, {
						name : "id"
					}, {
						name : "text"
					}, {
						name : "isleaf"
					}, {
						name : "fnumber"
					}, {
						name : "leaf",
						convert : converInteger
					}, {
						name : "fparentid"
					}, {
						name : "mcount",
						type : "int",
						defaultValue : 1
					}, {
						name : "depth",
						type : "int",
						defaultValue : 0
					}, {
						name : "custproductid"
					}, {
						name : "productid"
					}],
			validations : [{
						type : "presence",
						field : "text"
					}],
			proxy : {
				type : "ajax",
				api : {
					read : "GetCustProductStructTree.do",
					create : "updateAndCreateCustproductTree.do",
					destroy : "deleteCustproductTreeNodes.do",
					update : "updateAndCreateCustproductTree.do"
				},
				reader : {
					messageProperty : "Msg"
				},
				writer : {
					type : "json",
					encode : true,
					root : "data",
					allowSingle : false
				}
			}
		});
Ext.define("DJ.System.product.CustProductTreeEdit.ProductStructTreeStore", {
			extend : "Ext.data.TreeStore",
			id : "DJ.System.product.CustProductTreeEdit.ProductStructTreeStore",
			model : DJ.System.product.CustProductTreeEdit.TreeTest,
			autoLoad : false,
			nodeParam : "id",
//			defaultRootId : "-1",
//			root : {
//				text : "目录",
//				id : "-1",
//				expanded : true
//			},
			listeners : {
				move : function(tree, node) {
					var me = this;
					node.save({
								failure : function(e, op) {
									Ext.Msg.alert("发生错误",
											"保存移动时发生错误，现在要刷新树！<br/>" + "错误原因："
													+ op.error, function() {
												this.load();
											}, me);
								},
								scope : me
							});
				}
			}
		});
var cTreeStore = Ext
		.create("DJ.System.product.CustProductTreeEdit.ProductStructTreeStore");
function refreshStores() {
	productstore.load();
	cTreeStore.load();
}
Ext.define("DJ.System.product.CustProductTreeEdit.CustProductTreeEdit", {
			extend : "Ext.tree.Panel",
			id : "DJ.System.product.CustProductTreeEdit.CustProductTreeEdit",
			rootVisible : true,
			useArrows : true,
			autoScroll : true,
			border : false,
			store : cTreeStore,
			containerScroll : true,
			alias : "widget.custproducttreeedit",
			listeners : {
				cellkeydown : function(ttb, td, cellIndex, record, tr,
						rowIndex, e, eOpts) {
					Ext.app.myKeypressSetObj.setmyKeypressSetObjProperty(
							Ext.EventObject.DELETE, deleteNode);
					Ext.app.myKeypressSetObj.keypress(ttb, e, eOpts);
				}
			},
			plugins : [Ext.create("Ext.grid.plugin.CellEditing", {
						listeners : {
							beforeedit : function(edit, e) {
								if (e.record.data.root) {
									return false;
								}
							},
							edit : function(edit, e) {
								e.record.save({
											success : function(rec, opt) {
												opt.records[0].commit();
											},
											failure : function(e, op) {
												op.records[0].reject();
												Ext.Msg.alert("发生错误", op.error);
											}
										});
							}
						}
					})],
			viewConfig : {
				toggleOnDblClick : false,
				listeners : {
					refresh : function() {
						this.select(0);
						this.focus(false);
					}
				}
			},
			columns : [{
						xtype : "treecolumn",
						dataIndex : "text",
						text : "产品",
						flex : 1
					}, {
						xtype : "gridcolumn",
						dataIndex : "mcount",
						text : "数量",
						flex : 1,
						editor : {
							xtype : "numberfield",
							allowBlank : false,
							minValue : 1,
							allowDecimals : false
						}
					}],
			tbar : [{
						text : "增加",
//						id : "DJ.System.product.CustProductTreeEdit.CustProductTreeEdit.add",
						tooltip : "试试回车键",
						handler : addNodes
					}, {
						text : "删除",
//						id : "DJ.System.product.CustProductTreeEdit.CustProductTreeEdit.delete",
						tooltip : "试试Delete键",
						handler : deleteNode
					}, "|", {
						text : "刷新",
						handler : function() {
							refreshStores();
						}
					}, "|", {
						id : "DJ.System.product.CustProductTreeEdit.CustProductTreeEdit.defaultAddNo",
						xtype : "numberfield",
						fieldLabel : "添加数量",
						width : 145,
						labelWidth : 60,
						minValue : 1,
						value : 1,
						allowDecimals : false,
						allowBlank : false,
						listeners : {
							blur : function(numfield) {
							}
						}
					}],
			listeners : {}
		});
Ext.define("DJ.System.product.CustProductTreeEdit", {
			extend : "Ext.window.Window",
			id : "DJ.System.product.CustProductTreeEdit",
			closeAction : "hide",
			modal : true,
			x : 150,
			y : 50,
			resizable : true,
			height : 500,
			width : 1000,
			layout : {
				type : "border"
			},
			title : "客户产品关联产品",
			initComponent : function() {
				var me = this;
				Ext.applyIf(me, {
							items : [{
										xtype : "custproductdeflist",
										region : "center"
									}, {
										xtype : "custproducttreeedit",
										region : "west",
										width : 350
									}]
						});
				me.callParent(arguments);
			}
		});