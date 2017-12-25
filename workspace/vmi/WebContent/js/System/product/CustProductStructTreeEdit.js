Ext.ns("Ext.app.CustProductStructTreeEdit");
Ext.app.CustProductStructTreeEdit.myKeypressSetObj = {
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
Ext.app.CustProductStructTreeEdit.addNodes= function() {
	var tree = Ext.getCmp("DJ.System.product.CustProductStructTreeEdit.CustProductTreeEdit");
	var parent = tree.getSelectionModel().getSelection()[0];
	if (!parent) {
		parent = tree.store.tree.root;
	}
	var rows = Ext.getCmp("DJ.System.product.CustProductStructTreeEdit.CustProductList")
			.getSelectionModel().getSelection();
	var depthPT = parent.data.depth;
	if (depthPT < 1) {
		for (var i = 0, len = rows.length; i < len; i++) {
			var innerLenth = len;
			var succes = true;
			var rec = new CustproductTreeTest({
						text : "新节点",
						id : rows[i].data.fid,
						parentId : parent.data.id,
						leaf : true,
						mcount : Ext.getCmp("DJ.System.product.CustProductStructTreeEdit.defaultAddNo").value,
						depth : depthPT + 1
					});
			rec.save({
						action : "create",
						parentNode : parent,
						success : function(rec, opt) {
							innerLenth--;
							if (innerLenth == 0) {
								Ext.app.CustProductStructTreeEdit.refreshStores();
								tree.expandNode(parent);
							}
						},
						failure : function(e, op) {
							 var obj = op.request.proxy.reader.rawData;
							Ext.Msg.alert("发生错误", obj.msg);
							succes = false;
						},
						scope : tree
					});
		}
	} else {
		Ext.Msg.alert("", "最多只能添加2层树！");
	}
}
Ext.app.CustProductStructTreeEdit.deleteNode=function () {
	var tree = Ext.getCmp("DJ.System.product.CustProductStructTreeEdit.CustProductTreeEdit");
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
			var content = "确定删除节点：" + rs.data.text + "？";
			Ext.Msg.confirm("删除节点", content, function(btn) {
						if (btn == "yes") {
							var rs = this.getSelectionModel().getSelection();
							if (rs.length > 0) {
								rs = rs[0];
								rs.remove();
								this.store.sync({
										success : function(opt) {
										Ext.app.CustProductStructTreeEdit.refreshStores();
											
								},
								failure : function(opt) {
									var obj = cTreeStore.getProxy().getReader().rawData;
									Ext.Msg.alert("发生错误", obj.msg);
									Ext.app.CustProductStructTreeEdit.refreshStores();
								}
								});
//								refreshStores();
								this.view.select(0);
								this.view.focus(false);
							}
						}
					}, tree);
		}
	}
}
Ext.define("DJ.System.product.CustProductStructTreeEdit.CustProductDefStore", {
			extend : "Ext.data.Store",
			storeId : "DJ.System.product.CustProductStructTreeEdit.CustProductDefStore",
			fields : [{
						name : 'fid'
				}, {
					name : 'fname'
				}, {
					name : 'fnumber'
				}, {
					name : 'fspec'
				}, {
					name : 'forderunit'
				}, {
					name : 'fdescription'
					}],
			pageSize : 100,
			proxy : {
				type : "ajax",
				url : "getCustProductsWithoutTreeNodeList.do",
				extraParams : {
					dbfid : "",
					fcustomerid:""
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
Ext.app.CustProductStructTreeEdit.productstore = Ext
		.create("DJ.System.product.CustProductStructTreeEdit.CustProductDefStore");
////////var selModel = Ext.create("Ext.selection.CheckboxModel");

Ext.define("DJ.System.product.CustProductStructTreeEdit.MySimpleFilter", {
	id : "DJ.System.product.CustProductStructTreeEdit.MySimpleFilter",
	myFilter : function(action) {
		switch (action) {
			case "cus" :
				Ext
						.getStore("DJ.System.product.CustProductStructTreeEdit.CustProductDefStore")
						.clearFilter(true);
				Ext
						.getStore("DJ.System.product.CustProductStructTreeEdit.CustProductDefStore")
						.filter([{
									property : Ext.getCmp("DJ.System.product.CustProductStructTreeEdit.CustProductList.cmFilterFiled").getValue(),
									anyMatch : true,
									value : Ext.getCmp("DJ.System.product.CustProductStructTreeEdit.CustProductList.tfCondition").getValue()
								}]);
				Ext.getCmp("DJ.System.product.CustProductStructTreeEdit.CustProductList")
						.getSelectionModel().deselectAll();
				break;
			case "all" :
				Ext
						.getStore("DJ.System.product.CustProductStructTreeEdit.CustProductDefStore")
						.clearFilter(true);
				Ext
						.getStore("DJ.System.product.CustProductStructTreeEdit.CustProductDefStore")
						.load();
				Ext.getCmp("DJ.System.product.CustProductStructTreeEdit.CustProductList")
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
Ext.define("DJ.System.product.CustProductStructTreeEdit.CustProductList", {
			extend : "Ext.grid.Panel",
			id : "DJ.System.product.CustProductStructTreeEdit.CustProductList",
			selModel : Ext.create("Ext.selection.CheckboxModel", {}),
			columnLines : true,
			disableSelection : false,
			title : "客户产品基础资料 ( 请在这里选择要添加的数据 ) ",
			emptyMsg : "没有数据显示",
			alias : "widget.custproductgridpanel",
			listeners : {
				cellkeydown : function(ttb, td, cellIndex, record, tr,
						rowIndex, e, eOpts) {
					Ext.app.CustProductStructTreeEdit.myKeypressSetObj.myKey = 13;
					Ext.app.CustProductStructTreeEdit.myKeypressSetObj.myHandler = Ext.app.CustProductStructTreeEdit.addNodes;
					Ext.app.CustProductStructTreeEdit.myKeypressSetObj.keypress(ttb, e, eOpts);
				}
			},
			dockedItems : [{
						xtype : "pagingtoolbar",
						store : Ext.app.CustProductStructTreeEdit.productstore,
						dock : "bottom",
						displayInfo : true,
						items : []
					}, {
						xtype : "toolbar",
						dock : "top",
						items : [{
									id : "DJ.System.product.CustProductStructTreeEdit.CustProductList.cmFilterFiled",
									xtype : "combobox",
									fieldLabel : "过滤字段",
									forceSelection : true,
									value : "fname",
									store : [["fname", "名称"],
											["fnumber", "编号"]]
								}, {
									id : "DJ.System.product.CustProductStructTreeEdit.CustProductList.tfCondition",
									xtype : "textfield",
									enableKeyEvents : true,
									listeners : {
										keypress : function(tf, e, eOpts) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												Ext.create("DJ.System.product.CustProductStructTreeEdit.MySimpleFilter")
														.myFilter("cus");
											}
										}
									}
								}, {
									text : "过滤",
									tooltip : "试试回车键",
									handler : function() {
										Ext.create("DJ.System.product.CustProductStructTreeEdit.MySimpleFilter")
												.myFilter("cus");
									}
								}, {
									text : "显示全部",
									handler : function() {
										Ext.create("DJ.System.product.CustProductStructTreeEdit.MySimpleFilter")
												.myFilter("all");
									}
								}]
					}],
			autoExpandColumn : "fdescription",
			store : Ext.app.CustProductStructTreeEdit.productstore,
			columns : [{
						xtype : "rownumberer"
					}, {
					'header' : '编码',
					'dataIndex' : 'fnumber',
					filter : {
							type : "string"
						},
					width : 150,
					sortable : true
					}, {
					'header' : '产品名称',
					'dataIndex' : 'fname',
					filter : {
							type : "string"
						},
					width : 150,
					sortable : true
				}, {
					'header' : '规格',
					width : 70,
					'dataIndex' : 'fspec',
					sortable : true
				}, {
					'header' : '单位',
					width : 50,
					'dataIndex' : 'forderunit',
					sortable : true
				}, {
					'header' : '材料',
					width : 50,
					'dataIndex' : 'fmaterial',
					hidden : true,
					sortable : true
				}, {
					'header' : '楞型',
					'dataIndex' : 'ftilemodel',
					hidden : true,
					width : 50,
					sortable : true
				}, {
					'header' : '特性',
					'dataIndex' : 'fcharactername',
					width : 140,
					sortable : true
				}, {
					'header' : '描述',
					hidden : true,
					'dataIndex' : 'fdescription',
					sortable : true
					}]
		});
Ext.define("CustproductTreeTest", {
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
						convert :function(v, record)
						{
							return record.data.isleaf == "1" ? true : false;
						}
					}, {
						name : "parentId"
					}, {
						name : "mcount",
						type : "int",
						defaultValue : 1
					}, {
						name : "depth",
						type : "int",
						defaultValue : 0
					}],
			validations : [{
						type : "presence",
						field : "text"
					}],
			proxy : {
				type : "ajax",
				api : {
					read : "GetCustProductStructTreeInfo.do",
					create : "updateAndCreateCustProductStructTree.do",
					destroy : "deleteCustProductStructTreeNodes.do",
					update : "updateAndCreateCustProductStructTree.do"
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
Ext.define("DJ.System.product.CustProductStructTreeEdit.CustProductStructTreeStore", {
			extend : "Ext.data.TreeStore",
			id : "DJ.System.product.CustProductStructTreeEdit.CustProductStructTreeStore",
			model : CustproductTreeTest,
			autoLoad : false,
			nodeParam : "id",
			defaultRootId : "-1",
			root : {
				text : "目录",
				id : "-1",
				expanded : true
			},
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
 Ext.app.CustProductStructTreeEdit.cTreeStore = Ext
		.create("DJ.System.product.CustProductStructTreeEdit.CustProductStructTreeStore");
Ext.app.CustProductStructTreeEdit.refreshStores=function() {
	Ext.app.CustProductStructTreeEdit.productstore.load();
	Ext.app.CustProductStructTreeEdit.cTreeStore.load();
}
Ext.define("DJ.System.product.CustProductStructTreeEdit.CustProductTreeEdit", {
			extend : "Ext.tree.Panel",
			id : "DJ.System.product.CustProductStructTreeEdit.CustProductTreeEdit",
			rootVisible : true,
			useArrows : true,
			autoScroll : true,
			border : false,
			store : Ext.app.CustProductStructTreeEdit.cTreeStore,
			containerScroll : true,
			alias : "widget.custtreegrid",
			dockedItems : [{
						xtype : 'toolbar',
						dock : 'top',
						enableOverflow : true,
						items : [{
									text : "增加",
									id : "add",
									tooltip : "试试回车键",
									handler : Ext.app.CustProductStructTreeEdit.addNodes
								}, {
									text : "删除",
									id : "delete",
									tooltip : "试试Delete键",
									handler : Ext.app.CustProductStructTreeEdit.deleteNode
								}, "|", {
									text : "刷新",
									handler : function() {
										Ext.app.CustProductStructTreeEdit.refreshStores();
									}
								}, "|", {
									id : "DJ.System.product.CustProductStructTreeEdit.defaultAddNo",
									xtype : "numberfield",
									fieldLabel : "添加数量",
									minValue : 1,
									value : 1,
									width : 145,
									labelWidth : 60,
									allowDecimals : false,
									allowBlank : false,
									listeners : {
										blur : function(numfield) {
										}
									}
								}]
					}],
			listeners : {
				cellkeydown : function(ttb, td, cellIndex, record, tr,
						rowIndex, e, eOpts) {
					Ext.app.CustProductStructTreeEdit.myKeypressSetObj.setmyKeypressSetObjProperty(
							Ext.EventObject.DELETE, deleteNode);
					Ext.app.CustProductStructTreeEdit.myKeypressSetObj.keypress(ttb, e, eOpts);
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
												 var obj = op.request.proxy.reader.rawData;
												op.records[0].reject();
												Ext.Msg.alert("发生错误", obj.msg);
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
						text : "客户产品",
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
			// tbar : [],
			listeners : {}
		});


Ext.define("DJ.System.product.CustProductStructTreeEdit", {
			extend : "Ext.window.Window",
			id : "DJ.System.product.CustProductStructTreeEdit",
			closeAction : "hide",
			modal : true,
			x : 50,
			y : 50,
			resizable : true,
			height : 500,
			width : 1000,
			layout : {
				type : "border"
			},
			title : "客户产品结构编辑框",
			initComponent : function() {
				var me = this;
				Ext.applyIf(me, {
							items : [{
										xtype : "custproductgridpanel",
										region : "center"
									}, {
										xtype : "custtreegrid",
										region : "west",
										split : true,
										width : 350
									}]
						});
				me.callParent(arguments);
			}
		});