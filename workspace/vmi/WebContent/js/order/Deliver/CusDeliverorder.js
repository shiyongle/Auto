function converInteger(v, record) {
	return record.data.isleaf == '1' ? true : false;

}
Ext.define('DJ.order.Deliver.CusDeliverorderTreeStore', {
	extend : 'Ext.data.TreeStore',
	id : 'DJ.order.Deliver.CusDeliverorderTreeStore',
	fields : [{
		name : 'id'
	}, {
		name : 'text'
	}, {
		name : 'isleaf'
	}, {
		name : 'leaf',
		convert : converInteger
	}],
	proxy : {
		type : 'ajax',
		url : 'GetCustomerTree.do'
	},
	reader : {
		type : 'json'
	},
	autoLoad : false,
	nodeParam : 'id',
	defaultRootId : '-1',
	root : {
		id : '-1',
		text : '所有客户',
		expanded : true,
		leaf : false
	}
});

var cTreeStore = Ext.create("DJ.order.Deliver.CusDeliverorderTreeStore");
var selectpath = null;
var selectnodes = new Array();// 查询条件值存放地

function sarchNode(btn) {
	var filterValue = Ext.getCmp("DJ.System.product.CustproductTree.Input")
			.getValue();
			
	var trees = Ext.getCmp("DJ.order.Deliver.CusDeliverorder");
	var cstore = trees.getStore();
	
	if (Ext.String.trim(filterValue).length == 0) {
		return;
	}
	if (selectnodes.length == 0) {

		var el = trees.getEl();
		el.mask("系统处理中,请稍候……");
		Ext.Ajax.request({
			url : "SearchCustomerNode.do",
			params : {
				fname : filterValue
			},
			success : function(response, option) {
				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {
					for (var i = 0; i < obj.total; i++) {
						selectnodes.push(obj.data[i].id);
					}
					var node1 = cstore
							.getNodeById(selectnodes[selectnodes.length - 1])
							.getPath('text');
					trees.expandPath(node1, 'text', '/', function(bSucess,
							oLastNode) {
						selectnodes.pop();
						trees.getSelectionModel().select(oLastNode);
						trees.fireEvent('itemclick', trees, oLastNode);
					}, this);

				} else {
					Ext.MessageBox.alert('提示', obj.msg);

				}
				el.unmask();
			}
		});
	} else {
		var node2 = cstore.getNodeById(selectnodes[selectnodes.length - 1])
				.getPath('text');
		trees.expandPath(node2, 'text', '/', function(bSucess, oLastNode) {
			selectnodes.pop();
			trees.getSelectionModel().select(oLastNode);
			trees.fireEvent('itemclick', trees, oLastNode);

		}, this);
	}
}

Ext.define('DJ.order.Deliver.CusDeliverorder', {
	extend : 'Ext.tree.Panel',
	id : 'DJ.order.Deliver.CusDeliverorder',
	rootVisible : true,
	useArrows : true,
	autoScroll : true,
	border : false,
	store : cTreeStore,
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			text : '刷新',

			handler : function(btn) {

				var trees = Ext.getCmp("DJ.order.Deliver.CusDeliverorder");

				var record = trees.getSelectionModel().getSelection();
				if (record.length == 0) {
					selectpath = null;
				} else {
					selectpath = record[0].getPath('text');
				}
				cTreeStore.load();
			}
		}, {
			xtype : 'textfield',
			emptyText : '根据客户名称查找...',
			id : 'DJ.order.Deliver.CusDeliverorder.Input',
			width : 100,
			listeners : {
				change : function(fild, newValue, oldValue, eOpts) {
					selectnodes = new Array();
				}
			}
		}, {
			text : '查找',
			handler : sarchNode
		}]
	}],
	listeners : {
		itemclick : function(view, record, item, index, e, eOpts) {
			var customerid = '';
			if (record.data.id != '-1') {
				customerid = record.data.id;
			}

			var pstore;
			if (Ext.getCmp("DJ.order.Deliver.DeliverorderList") == undefined) {
				pstore = Ext.getCmp("DJ.order.Deliver.DeliverorderList")
						.getStore();
			} else {
				pstore = Ext.getCmp("DJ.order.Deliver.DeliverorderList")
						.getStore();
			}
			
			var myfilter = [];
			
			//如果是根节点则不设置过滤器
			if (customerid == "") {
				
				myfilter.push({
					myfilterfield : "1",
					CompareType : " like ",
					type : "string",
					value : '1'
				});
				
			} else {

				myfilter.push({
					myfilterfield : "d.fcustomerid",
					CompareType : " like ",
					type : "string",
					value : customerid
				});
				
			}
			
			pstore.setDefaultfilter(myfilter);
			pstore.setDefaultmaskstring(" #0 ");

			pstore.loadPage(1);
		},
		load : function(view, node, records, successful, eOpts) {
			var trees = Ext.getCmp("DJ.order.Deliver.CusDeliverorder");

			if (selectpath != null) {
				trees.expandPath(selectpath, 'text', '/', function(bSucess,
						oLastNode) {
					trees.getSelectionModel().select(oLastNode);
					trees.fireEvent('itemclick', trees, oLastNode);
				}, this);
			} else {
				trees.getSelectionModel().select(node);
				trees.fireEvent('itemclick', trees, node);
			}
		}
	}
});
