
var myKeypressSetObj = {
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
	var tree = Ext.getCmp("DJ.System.product.ProductTreeEdit.ProductTreeEdit");
	var parent = tree.getSelectionModel().getSelection()[0];
	if (!parent) {
		parent = tree.store.tree.root;
	}
	var rows = Ext.getCmp("DJ.System.product.ProductTreeEdit.ProductDefList")
			.getSelectionModel().getSelection();
	var depthPT = parent.data.depth;
	if (depthPT < 1) {
		for (var i = 0, len = rows.length; i < len; i++) {
			var innerLenth = len;
			var succes = true;
			var rec = new TreeTest({
						text : "新节点",
						id : rows[i].data.d_fid,
						parentId : parent.data.id,
						leaf : true,
						mcount : Ext.getCmp("defaultAddNo").value,
						depth : depthPT + 1
					});
			rec.save({
						action : "create",
						parentNode : parent,
						success : function(rec, opt) {
							innerLenth--;
							if (innerLenth == 0) {
								refreshStores();
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
function deleteNode() {
	var tree = Ext.getCmp("DJ.System.product.ProductTreeEdit.ProductTreeEdit");
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
											refreshStores();
											
								},
								failure : function(opt) {
									var obj = cTreeStore.getProxy().getReader().rawData;
									Ext.Msg.alert("发生错误", obj.msg);
									refreshStores();
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
Ext.define("DJ.System.product.ProductTreeEdit.ProductDefStore", {
			extend : "Ext.data.Store",
			storeId : "DJ.System.product.ProductTreeEdit.ProductDefStore",
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
				url : "getProductsWithoutTreeNode.do",
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
		.create("DJ.System.product.ProductTreeEdit.ProductDefStore");
var selModel = Ext.create("Ext.selection.CheckboxModel");
function formatEffect(value) {
	return value == "1" ? "是" : "否";
}
Ext.define("MySimpleFilter", {
	id : "MySimpleFilter",
	myFilter : function(action) {
		switch (action) {
			case "cus" :
				Ext
						.getStore("DJ.System.product.ProductTreeEdit.ProductDefStore")
						.filter([{
									property : Ext.getCmp("cmFilterFiled")
											.getValue(),
									anyMatch : true,
									value : Ext.getCmp("tfCondition")
											.getValue()
								}]);
				Ext.getCmp("DJ.System.product.ProductTreeEdit.ProductDefList")
						.getSelectionModel().deselectAll();
				break;
			case "all" :
				Ext
						.getStore("DJ.System.product.ProductTreeEdit.ProductDefStore")
						.clearFilter(true);
				Ext
						.getStore("DJ.System.product.ProductTreeEdit.ProductDefStore")
						.load();
				Ext.getCmp("DJ.System.product.ProductTreeEdit.ProductDefList")
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
Ext.define("DJ.System.product.ProductTreeEdit.ProductDefList", {
			extend : "Ext.grid.Panel",
			id : "DJ.System.product.ProductTreeEdit.ProductDefList",
			selModel : Ext.create("Ext.selection.CheckboxModel", {}),
			columnLines : true,
			disableSelection : false,
			title : "产品基础资料 ( 请在这里选择要添加的数据 ) ",
			emptyMsg : "没有数据显示",
			alias : "widget.mygridpanel",
			listeners : {
				cellkeydown : function(ttb, td, cellIndex, record, tr,
						rowIndex, e, eOpts) {
					myKeypressSetObj.myKey = 13;
					myKeypressSetObj.myHandler = addNodes;
					myKeypressSetObj.keypress(ttb, e, eOpts);
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
									id : "cmFilterFiled",
									xtype : "combobox",
									fieldLabel : "过滤字段",
									forceSelection : true,
									value : "d_fname",
									store : [["d_fname", "名称"],
											["d_fnumber", "编号"]]
								}, {
									id : "tfCondition",
									xtype : "textfield",
									enableKeyEvents : true,
									listeners : {
										keypress : function(tf, e, eOpts) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												Ext.create("MySimpleFilter")
														.myFilter("cus");
											}
										}
									}
								}, {
									text : "过滤",
									tooltip : "试试回车键",
									handler : function() {
										Ext.create("MySimpleFilter")
												.myFilter("cus");
									}
								}, {
									text : "显示全部",
									handler : function() {
										Ext.create("MySimpleFilter")
												.myFilter("all");
									}
								}]
					}],
			autoExpandColumn : "d_fcharacter",
			store : productstore,
			columns : [{
						xtype : "rownumberer"
					}, {
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
Ext.define("TreeTest", {
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
					read : "GetProductStructTree.do",
					create : "updateAndCreateProductStructTree.do",
					destroy : "deleteProductStructTreeNodes.do",
					update : "updateAndCreateProductStructTree.do"
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
Ext.define("DJ.System.product.ProductTreeEdit.ProductStructTreeStore", {
			extend : "Ext.data.TreeStore",
			id : "DJ.System.product.ProductTreeEdit.ProductStructTreeStore",
			model : TreeTest,
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
var cTreeStore = Ext
		.create("DJ.System.product.ProductTreeEdit.ProductStructTreeStore");
function refreshStores() {
	productstore.load();
	cTreeStore.load();
}
Ext.define("DJ.System.product.ProductTreeEdit.ProductTreeEdit", {
			extend : "Ext.tree.Panel",
			id : "DJ.System.product.ProductTreeEdit.ProductTreeEdit",
			rootVisible : true,
			useArrows : true,
			autoScroll : true,
			border : false,
			store : cTreeStore,
			containerScroll : true,
			alias : "widget.treegrid",
			dockedItems : [{
						xtype : 'toolbar',
						dock : 'top',
						enableOverflow : true,
						items : [{
									text : "增加",
									id : "add",
									tooltip : "试试回车键",
									handler : addNodes
								}, {
									text : "删除",
									id : "delete",
									tooltip : "试试Delete键",
									handler : deleteNode
								}, "|", {
									text : "刷新",
									handler : function() {
										refreshStores();
									}
								}, "|", {
									id : "defaultAddNo",
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
					myKeypressSetObj.setmyKeypressSetObjProperty(
							Ext.EventObject.DELETE, deleteNode);
					myKeypressSetObj.keypress(ttb, e, eOpts);
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
			// tbar : [],
			listeners : {}
		});
Ext.define("DJ.System.product.ProductTreeEdit", {
			extend : "Ext.window.Window",
			id : "DJ.System.product.ProductTreeEdit",
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
			title : "产品结构编辑框",
			initComponent : function() {
				var me = this;
				Ext.applyIf(me, {
							items : [{
										xtype : "mygridpanel",
										region : "center"
									}, {
										xtype : "treegrid",
										region : "west",
										split : true,
										width : 350
									}]
						});
				me.callParent(arguments);
			}
		});