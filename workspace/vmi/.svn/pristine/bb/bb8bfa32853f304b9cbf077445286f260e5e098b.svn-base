function converInteger(v, record) {
	return record.data.isleaf == '1' ? true : false;

}
Ext.define('DJ.System.product.ProductStructTreeStore', {
	extend : 'Ext.data.TreeStore',
	id : 'DJ.System.product.ProductStructTreeStore',
	fields : [{
				name : 'id'
			}, {
				name : 'text'
			}, {
				name : 'isleaf'
			}, {
				name : 'fnumber'
			}, {
				name : 'leaf',
				convert : converInteger
			}, {
				name : 'fparentnode'
			}],
	proxy : {
		type : 'ajax',
		url : 'GetProductStructTree.do'
	},
	reader : {
		type : 'json'
	},
	autoLoad : false,
	nodeParam : 'id'
		// defaultRootId : '-1',
		// root: {
		// id : '-1',
		// text : '所有客户',
		// expanded: true,
		// leaf: false
		// }
	});
var cTreeStore = Ext.create("DJ.System.product.ProductStructTreeStore");
Ext.define('DJ.System.product.ProductStructureTree', {
	extend : 'Ext.tree.Panel',
	id : 'DJ.System.product.ProductStructureTree',
	rootVisible : true,
	useArrows : true,
	autoScroll : true,
	border : false,
	store : cTreeStore,
	containerScroll : true,
	listeners : {
		itemclick : function(view, record, item, index, e, eOpts) {

			var fids = record.data.id.split(",");
			var fid = fids[0];
			var myMask = new Ext.LoadMask(document.body, {
						msg : '正在发送请求，请稍后....',
						removeMask : true
					});
			myMask.show();
			Ext.Ajax.request({
						url : "GetProduct.do",
						params : {
							fid : fid
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								var cform = Ext
										.getCmp("DJ.System.product.ProductDefDetail")
										.getForm();
								cform.reset();
								cform.setValues(obj.data[0]);
								cform.findField('fcustomerid')
										.setValue(obj.data[0].fcustomerid);
								cform.findField('fcustomerid')
										.setRawValue(obj.data[0].cname);

								myMask.hide();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);

								myMask.hide();
							}
						}
					});

		},
		load : function(view, node, records, successful, eOpts) {
			var trees = Ext.getCmp("DJ.System.product.ProductStructureTree");
			trees.getSelectionModel().select(node);
			trees.fireEvent('itemclick', trees, node);
		}
	}
});
