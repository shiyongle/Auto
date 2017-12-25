Ext.QuickTips.init();
var KeypressSetObj = {
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
function addCustNodes() {
	var tree = Ext
			.getCmp("DJ.System.product.ProductRelationEdit.ProductTreeEdit");
	var parent = tree.getSelectionModel().getSelection()[0];
	var treestore = tree.store;
	if (!parent) {
		parent = tree.store.tree.root;
	}
	var rows = Ext
			.getCmp("DJ.System.product.ProductRelationEdit.CustProductList")
			.getSelectionModel().getSelection();
	var fcustproductid = rows[0].get('fid');
	Ext.Ajax.request({
		url : 'getProductsCustRelevance.do',
		params : {
			fcustproductid : fcustproductid
		},
		success: function(resp,opts) {
			var obj = Ext.decode(resp.responseText);
			var total = obj.total;
			if(total==0){
				var depthPT = parent.data.depth;
				var parentidd = parent.data.id;
				if (depthPT < 1) {
					for (var i = 0, len = rows.length; i < len; i++) {
						var innerLenth = len;
						var succes = true;
						var rec = new ProductTree({
							text : rows[i].data.fname,
							// id : rows[i].data.fid,
							// parentId : parent.data.id,
							custid : rows[i].data.fid,
							parentId : parentidd,
							isleaf : "1",
							mcount : Ext
									.getCmp("DJ.System.product.ProductRelationEdit.addamount")
									.getValue(),
							depth : depthPT + 1
						});
						parent.appendChild(rec);
						// rec.save({
						// action : "create",
						// parentNode : parent,
						// // synchronous:true,
						// success : function(rec, opt) {
						// innerLenth--;
						// if (innerLenth == 0) {
						// var obj = Ext.decode(opt.response.responseText);
						// parent.data.id=obj.msg;
						// refreshStores();
						// tree.expandNode(parent);
						// }
						//							
						// },
						// failure : function(e, op) {
						// Ext.Msg.alert("发生错误", op.error);
						// succes = false;
						//							
						// },
						// scope : tree
						// });
					}
					treestore.sync({
						success : function(opt) {
							var obj = treestore.getProxy().getReader().rawData;
							if ((parent.data.id).indexOf(",") > 0) {
								parent.data.id = obj.msg;
							}
							refreshStore();
						},
						failure : function(opt) {
							var obj = treestore.getProxy().getReader().rawData;
							Ext.Msg.alert("发生错误", obj.msg);
							refreshStore();

						}
					});
				} else {
					Ext.Msg.alert("", "最多只能添加2层树！");
				}
			}else{
				Ext.Msg.alert('提示','客户产品已关联产品资料');
				return ;
			}
		}
	});
}
function deleteCustNode() {
	var tree = Ext
			.getCmp("DJ.System.product.ProductRelationEdit.ProductTreeEdit");
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
						var uparentnode = rs.parentNode;
						rs.remove();
						ccTreeStore.sync({
							success : function(opt) {
								if (uparentnode.hasChildNodes() == false
										&& uparentnode.isRoot() == true) {
									uparentnode.data.id = uparentnode.data.custid;
								}
								refreshStore();
							},
							failure : function(opt) {
								var obj = ccTreeStore.getProxy().getReader().rawData;
								Ext.Msg.alert("发生错误", obj.msg);
								refreshStore();
							}
						});
						// refreshStores();
						this.view.select(0);
						this.view.focus(false);
					}
				}
			}, tree);
		}
	}
}
Ext.define("DJ.System.product.ProductRelationEdit.CustProductStore", {
	extend : "Ext.data.Store",
	storeId : "DJ.System.product.ProductRelationEdit.CustProductStore",
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
		name : 'fcustomerid'
	}, {
		name : 'fdescription'
	}, {
		name : 'fcreatorid'
	}, {
		name : 'fcreatetime'
	}, {
		name : 'flastupdateuserid'
	}, {
		name : 'flastupdatetime'
	}, {
		name : 'Frelationed'
	}],
	pageSize : 100,
	proxy : {
		type : "ajax",
		url : "GetRelationCustproductList.do",
		// url : "getProductsWithoutTreeNode.do",
		extraParams : {
			fcustomerid : ""
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
var pproductstore = Ext
		.create("DJ.System.product.ProductRelationEdit.CustProductStore");
var selModel = Ext.create("Ext.selection.CheckboxModel");
function formatEffect(value) {
	return value == "1" ? "是" : "否";
}
Ext.define("CustproductFilter", {
	id : "CustproductFilter",
	myFilter : function(action) {
		switch (action) {
			case "cus" :
				Ext
						.getStore("DJ.System.product.ProductRelationEdit.CustProductStore")
						.filter([{
							property : Ext.getCmp("cpFilterFiled").getValue(),
							anyMatch : true,
							value : Ext.getCmp("cpCondition").getValue()
						}]);
				Ext
						.getCmp("DJ.System.product.ProductRelationEdit.CustProductList")
						.getSelectionModel().deselectAll();
				break;
			case "all" :
				Ext
						.getStore("DJ.System.product.ProductRelationEdit.CustProductStore")
						.clearFilter(true);
				Ext
						.getStore("DJ.System.product.ProductRelationEdit.CustProductStore")
						.load();
				Ext
						.getCmp("DJ.System.product.ProductRelationEdit.CustProductList")
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
Ext.define("DJ.System.product.ProductRelationEdit.CustProductList", {
	extend : "Ext.grid.Panel",
	id : "DJ.System.product.ProductRelationEdit.CustProductList",
	selModel : Ext.create("Ext.selection.CheckboxModel", {}),
	columnLines : true,
	disableSelection : false,
	title : "客户产品基础资料 ( 请在这里选择要添加的数据 ) ",
	emptyMsg : "没有数据显示",
	alias : "widget.custproductpanel",
	listeners : {
		cellkeydown : function(ttb, td, cellIndex, record, tr, rowIndex, e,
				eOpts) {
			KeypressSetObj.myKey = 13;
			KeypressSetObj.myHandler = addCustNodes;
			KeypressSetObj.keypress(ttb, e, eOpts);
		}
	},
	dockedItems : [{
		xtype : "pagingtoolbar",
		store : pproductstore,
		dock : "bottom",
		displayInfo : true,
		items : []
	}, {
		xtype : "toolbar",
		dock : "top",
		items : [{
			id : "cpFilterFiled",
			xtype : "combobox",
			fieldLabel : "过滤字段",
			forceSelection : true,
			value : "fname",
			store : [["fname", "名称"], ["fnumber", "编号"]]
		}, {
			id : "cpCondition",
			xtype : "textfield",
			enableKeyEvents : true,
			listeners : {
				keypress : function(tf, e, eOpts) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						Ext.create("CustproductFilter").myFilter("cus");
					}
				}
			}
		}, {
			text : "过滤",
			tooltip : "试试回车键",
			handler : function() {
				Ext.create("CustproductFilter").myFilter("cus");
			}
		}, {
			text : "显示全部",
			handler : function() {
				Ext.create("CustproductFilter").myFilter("all");
			}
		}]
	}],
	store : pproductstore,
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		autoHeight : true,
		autoWidth : true,
		sortable : true
	}, {
		'header' : '产品名称',
		'dataIndex' : 'fname',
		sortable : true,
		filter : {
			type : "string"
		}
	}, {
		'header' : '编码',
		'dataIndex' : 'fnumber',
		sortable : true,
		filter : {
			type : "string"
		}
	}, {
		'header' : '关联',
		width : 40,
		'dataIndex' : 'Frelationed',
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	}, {
		'header' : '规格',
		width : 70,
		'dataIndex' : 'fspec',
		sortable : true
	}, {
		'header' : '单位',
		width : 70,
		'dataIndex' : 'forderunit',
		sortable : true
	}, {
		'header' : '客户',
		hidden : true,
		'dataIndex' : 'fcustomerid',
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
});
function converInteger(v, record) {
	return record.data.isleaf == "1" ? true : false;
}

Ext.define("ProductTree", {
	extend : "Ext.data.TreeModel",
	fields : [{
		name : "dbId"
	}, {
		name : "id"
	}, {
		name : "text"
	}, {
		name : "isleaf"
			// }, {
			// name : "fnumber"
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
			}, {
				name : 'fparentnode'
			}, {
				name : 'custid'
			}],
	validations : [{
		type : "presence",
		field : "text"
	}],
	proxy : {
		type : "ajax",
		api : {
			read : "GetProductRelationTree.do",
			create : "updateAndCreateProductRelationTree.do",
			destroy : "deleteProductRelationentryNode.do",
			update : "updateAndCreateProductRelationTree.do"
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

Ext.define("DJ.System.product.ProductRelationEdit.ProductStructTreeStore", {
	extend : "Ext.data.TreeStore",
	id : "DJ.System.product.ProductRelationEdit.ProductStructTreeStore",
	model : ProductTree,
	autoLoad : true,
	nodeParam : "id",
	// autoSync:true,
	// defaultRootId :"-1",
	// root : {
	// text : "目录",
	// id : "-1",
	// expanded : true
	// },
	listeners : {
		move : function(tree, node) {
			var me = this;
			node.save({
				failure : function(e, op) {
					Ext.Msg.alert("发生错误", "保存移动时发生错误，现在要刷新树！<br/>" + "错误原因："
							+ op.error, function() {
						this.load();
					}, me);
				},
				scope : me
			});
		}
	}
});
var ccTreeStore = Ext
		.create("DJ.System.product.ProductRelationEdit.ProductStructTreeStore");
function refreshStore() {
	pproductstore.load();
	ccTreeStore.load();
}
Ext.define("DJ.System.product.ProductRelationEdit.ProductTreeEdit", {
	extend : "Ext.tree.Panel",
	id : "DJ.System.product.ProductRelationEdit.ProductTreeEdit",
	rootVisible : true,
	useArrows : true,
	autoScroll : true,
	border : false,
	store : ccTreeStore,
	containerScroll : true,
	alias : "widget.producttreegrid",
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		enableOverflow : false,
		items : [{
			text : "增加",
			tooltip : "试试回车键",
			handler : addCustNodes
		}, "|", {
			text : "删除",
			tooltip : "试试Delete键",
			handler : deleteCustNode
		}, "|", {
			text : "刷新",
			handler : function() {
				refreshStore();
			}
		}, "|", {
			id : "DJ.System.product.ProductRelationEdit.addamount",
			xtype : "numberfield",
			fieldLabel : "添加比例",
			labelWidth : 60,
			minValue : 1,
			value : 1,
			allowDecimals : false,
			allowBlank : false,
			width : 120,
			listeners : {
				blur : function(numfield) {
				}
			}
		}, "|", {
			id : 'DJ.System.product.ProductRelationEdit.customerfname',
			fieldLabel : "客户名称",
			labelWidth : 60,
			width : 180,
			xtype: "displayfield",
//			xtype : "textfield",
			handler : function() {

			}
		}]
	}],
	listeners : {
		cellkeydown : function(ttb, td, cellIndex, record, tr, rowIndex, e,
				eOpts) {
			KeypressSetObj.setmyKeypressSetObjProperty(Ext.EventObject.DELETE,
					deleteCustNode);
			KeypressSetObj.keypress(ttb, e, eOpts);
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
						 var obj = op.request.proxy.reader.rawData;
						Ext.Msg.alert("发生错误", obj.msg );
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
		text : "比例",
		flex : 1,
		editor : {
			xtype : "numberfield",
			allowBlank : false,
			minValue : 1,
			allowDecimals : false
		}
	}],
	// tbar : [{
	// text : "增加",
	// tooltip : "试试回车键",
	// handler : addCustNodes
	// }, "|", {
	// text : "删除",
	// tooltip : "试试Delete键",
	// handler : deleteCustNode
	// }, "|", {
	// text : "刷新",
	// handler : function() {
	// refreshStore();
	// }
	// }, "|", {
	// id : "DJ.System.product.ProductRelationEdit.addamount",
	// xtype : "numberfield",
	// fieldLabel : "添加比例",
	// labelWidth:60,
	// minValue : 1,
	// value : 1,
	// allowDecimals : false,
	// allowBlank : false,
	// width:120,
	// listeners : {
	// blur : function(numfield) {
	// }
	// }
	// }, "|", {
	// id:'DJ.System.product.ProductRelationEdit.customerfname',
	// fieldLabel : "客户名称",
	// labelWidth:60,
	// width:180,
	// xtype : "textfield",
	// handler : function() {
	//							
	// }
	// }],
	listeners : {}
});
Ext.define("DJ.System.product.ProductRelationEdit", {
	extend : "Ext.window.Window",
	id : "DJ.System.product.ProductRelationEdit",
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
	title : "结构编辑框",
	initComponent : function() {
		var mee = this;
		Ext.applyIf(mee, {
			items : [{
				xtype : "custproductpanel",
				region : "center"
			}, {
				xtype : "producttreegrid",
				region : "west",
				split : true,
				width : 480
			}]
		});
		mee.callParent(arguments);
	}
});