Ext.define('DJ.System.product.ProductDrawListWin', {
	extend : 'Ext.Window',
	id : 'DJ.System.product.ProductDrawListWin',
	modal : true,
	// title : "E上传",
	width : 850,// 230, //Window宽度
	height : 500,// 137, //Window高度
	// x : 200,
	// y : 80,
	resizable : true,
	// closable : false, // 关闭按钮，默认为true
	// closeAction : 'destroy',
	// renderTo: 'upload',

	initComponent : function() {
		Ext.apply(this, {
			items : [{
				xtype : 'productlrawlist'
			}]
		}), this.callParent(arguments);
	},

	productID : "",
	// buttons : [{
	// text : '关闭',
	// handler : function() {
	//			
	// Ext.destroy(Ext.getCmp("DJ.System.product.ProductDrawList"));
	//			
	// this.up("window").close();
	//	
	// }
	// }] close( panel, eOpts )
	listeners : {
		close : function(panel, eOpts) {
			Ext.destroy(Ext.getCmp("DJ.System.product.ProductDrawList"));

			Ext.destroy(Ext.getCmp("DJ.System.product.ProductDrawListWin"));

//			Ext.getCmp("DJ.System.product.ProductDrawListWin").hide();
//			Ext.getCmp("DJ.System.product.ProductDrawList").hide();
			// Ext.destroy(Ext.getCmp("DJ.System.product.ProductDrawList"));

			// this.up("window").close();
		}

	}
});

Ext.define('DJ.System.product.ProductDrawList', {
	extend : 'Ext.c.GridPanel',
	// title : "异常跟踪",

	// requires: ['DJ.order.Deliver.DeliverorderexceptionEdit'],

	alias : 'widget.productlrawlist',

	id : 'DJ.System.product.ProductDrawList',
	pageSize : 50,
	// closable : ,// 是否现实关闭按钮,默认为false
	url : 'selectProductdraws.do',
	Delurl : "deleleProudctDraws.do",
	// EditUI : "DJ.order.Deliver.DeliverorderexceptionEdit",
	// exporturl : "",// 导出为EXCEL方法
	width : 800,
	height : 500,
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		// alert("DeliverList");
		this._operateButtonsView(true, [3]);
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件

	},
	Action_AfterAddButtonClick : function(EditUI) {

	},
	Action_BeforeEditButtonClick : function(EditUI) {
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件

	},
	Action_BeforeDelButtonClick : function(me, record) {
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
		this.up("window").close();
		Ext.destroy(this);
		// this.store.load({
		// scope : this,
		// url : "selectProductdraws.do?productID=" +
		// this.up("window").productID
		// // extraParams : {
		// // productID : record[0].get("d_fid")
		// //
		// // },
		// // callback : function(records, operation, success) {
		// // // the operation object
		// // // contains all of the details of the load operation
		// // // console.log(records);
		// // if (success) {
		// // if (!records.length == 0) {
		// // win.show();
		// // } else {
		// // Ext.Msg.alert("提示", "没有图片");
		// // Ext.destroy(win);
		// // }
		// // } else {
		// // Ext.Msg.alert("提示", "");
		// // }
		// //
		// // }
		// });

	},
	custbar : [],

	fields : [{
		name : 'fid'
	}, {
		name : 'fproductID'
	}, {
		name : 'fimagePath'
	}, {
		name : 'fdrawnNo'
	}, {
		name : 'fversion'
	}

	],

	initComponent : function() {
		Ext.apply(this, {
			columns : [{
				'header' : 'fid',
				'dataIndex' : 'fid',
				hidden : true,
				hideable : false,
				sortable : true

			}, {
				// id : "DJ.System.product.ProductDrawList.fproductID",
				'header' : 'fproductID',
				'dataIndex' : 'fproductID',
				hidden : true,
				hideable : false,
				sortable : true

			}, {
				'header' : '图号',
				width : 200,
				'dataIndex' : 'fdrawnNo',
				sortable : true
			}, {
				'header' : '版本',
				width : 200,
				'dataIndex' : 'fversion',
				sortable : true
			}, {
				// 'header' : '图片',
				'dataIndex' : 'fimagePath',
				xtype : "templatecolumn",
				// height : 100,
				width : 300,
				tpl : '<a href="{fimagePath}" target="_blank"><img src="{fimagePath}"  alt="there is some problem" height="200" width = "200" /></a>',
				maxHeight : 10,
				sortable : true
			}],
			selModel : Ext.create('Ext.selection.CheckboxModel')
		}), this.callParent(arguments);
	},

	/**
	 * 主要调用这个方法
	 * 
	 * @param {}
	 *            show，bool，
	 * @param {}
	 *            array，元素为button ID或为数字索引（从0开始）
	 */
	_operateButtonsView : function(show, array) {

		if (Ext.typeOf(array[0]) == "number") {
			array = this._translateNumToDataIndex(array);
		}

		if (show) {
			this._showButtons(array);
		} else {
			this._hideButtons(array);
		}

	},
	_translateNumToDataIndex : function(array) {
		var arrayt = [];

		var arrayAll = this._getToolsButtonIDs();

		Ext.each(arrayAll, function(item, index, all) {
			if (Ext.Array.contains(array, index)) {
				arrayt.push(arrayAll[index]);
			}
		});

		return arrayt;
	},

	_hideButtons : function(array) {
		var t = array;
		Ext.each(t, function(item, index, all) {
			Ext.getCmp(item).hide();
		});
	},

	_showButtons : function(array) {
		var defaultButtons = this._getToolsButtonIDs();

		var tArray = Ext.Array.difference(defaultButtons, array);

		this._hideButtons(tArray);
	},

	_getToolsButtonIDs : function() {
		var defaultButtons = [];

		var t = this.dockedItems.items[2].items.items;

		Ext.each(t, function(item, index, all) {

			defaultButtons.push(item.id);

		});
		return defaultButtons;
	}
})