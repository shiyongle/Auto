Ext.require(['Ext.ux.grid.MyGridItemTipPlugin']);

Ext.define('DJ.quickOrder.QuickOrderImgShower', {

	// id : 'DJ.quickOrder.quickOrderList.CustproductCustAccessoryList',
	// ctype : 'Productdemandfile',
	extend : 'Ext.c.GridPanel',

	mixins : ['DJ.tools.grid.MySimpleGridMixer'],

	pageSize : 50,
	// alias : 'widget.custproductcustaccessorylist',
	closable : false,// 是否现实关闭按钮,默认为false

	itemId : '',
	
	url : 'selectQuickAccessoryByCusPId.do',
	Delurl : "deleteQuickCustProductAccessorys.do",

	exporturl : "",// 导出为EXCEL方法
	// height:200,
	selModel : {

		selType : 'checkboxmodel'

	},

	// Ext.create('Ext.selection.CheckboxModel')

	plugins : [{

		ptype : "mygriditemtipplugin",
		showingFields : ['fname']

	}],

	custbar : [
	
	{

		xtype : 'oneclickfilefieldformulitichoice',
		// xtype : 'ocfile',
		// buttonText : '',
		// buttonConfig :{
		// iconCls : 'upload'
		// },
		url : 'uploadQuickCustProductImg.do',
		doChange : function() {

			var me = this;

			var grid = me.up('grid');

			me.params = {

				fid : grid.itemId

			};

		}

	}
	
	],

	onload : function() {

		var me = this;

		MyCommonToolsZ.setComAfterrender(me, function() {

			me._hideButtons(['addbutton', 'editbutton', 'viewbutton',
					'querybutton', 'exportbutton']);

		});

		this.getStore().on("beforeload", function(store, operation, eOpts) {

			var records = Ext.getCmp("DJ.quickOrder.quickOrderListList")
					.getSelectionModel().getSelection();

			if (records.length == 1) {

				operation.params = {

					fcusproductid : records[0].get("fid")

				}

			}

		});

	},
	fields : [{
		name : 'fid'
	}, {
		name : 'fname'
	}, {
		name : 'fpath'
	}, {
		name : 'fparentid'
	}],
	columns : [{
		dataIndex : 'fid',
		hidden : true,
		hideable : false
	}, {
		text : '路径',
		dataIndex : 'fpath',
		// id : 'DJ.order.Deliver.productdemandfile.fpath',
		hidden : true,
		hideable : false
	}, {

		// 'header' : '图片',
		// 'dataIndex' : 'fimagePath',
		text : '名称',
		xtype : "templatecolumn",
		// height : 100,
		width : 370,
		tpl : '<a href="{fpath}" target="_blank">{fname}</a>'
			// maxHeight : 10,
			// sortable : true,
			// flex : 1

			}, {
				xtype : 'templatecolumn',
				text : '动作',
				dataIndex : 'fname',
				tpl : '<a href="downloadProductdemandFile.do?fid={fid}" target="_blank" style="text-decoration:none;">下载</a>',
				// flex:1
				width : 80
			}, {
				dataIndex : 'fparentid',
				hidden : true,
				hideable : false
			}]
});

// Ext.create('Ext.window.Window', {
// // title: '',
// height : 200,
// width : 400,
// layout : 'fit',
// items : [Ext
// .create("DJ.quickOrder.quickOrderList.CustproductCustAccessoryList")]
// }).show();
