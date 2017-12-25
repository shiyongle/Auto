Ext.require(['Ext.ux.grid.MyGridItemTipPlugin']);

Ext.define('DJ.System.product.CustproductAccessoryList', {
	id : 'DJ.System.product.CustproductAccessoryList',
	ctype : 'Productdemandfile',
	extend : 'Ext.c.GridPanel',

	mixins : ['DJ.tools.grid.MyGridHelper'],

	pageSize : 50,
	alias : 'widget.custproductaccessorylist',
	closable : false,// 是否现实关闭按钮,默认为false
	url : 'selectAccessoryByCusPId.do',
	Delurl : "deleteCustProductAccessorys.do",
	
	exporturl : "",// 导出为EXCEL方法
	// height:200,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	
	plugins : [{
	
		ptype : "mygriditemtipplugin",
		showingFields : ['fname'] 
		
	}],
	
	onload : function() {

		this._operateButtonsView(true, [
				'DJ.System.product.CustproductAccessoryList.refreshbutton','DJ.System.product.CustproductAccessoryList.delbutton']);

		this.getStore().on("beforeload", function(store, operation, eOpts) {

			var records = Ext
					.getCmp("DJ.System.product.CustproductList")
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
//		id : 'DJ.order.Deliver.productdemandfile.fpath',
		hidden : true,
		hideable : false
	}, {

		// 'header' : '图片',
		// 'dataIndex' : 'fimagePath',
		text : '名称',
		xtype : "templatecolumn",
		// height : 100,
		width : 300,
		tpl : '<a href="{fpath}" target="_blank">{fname}</a>',
		// maxHeight : 10,
		// sortable : true,
		width : 80

	}, {
		xtype : 'templatecolumn',
		text : '动作',
		dataIndex : 'fname',
		tpl : '<a href="downloadProductdemandFile.do?fid={fid}" target="_blank" style="text-decoration:none;">下载</a>',
		// flex:1
		width : 40
	}, {
		dataIndex : 'fparentid',
		hidden : true,
		hideable : false
	}]
})