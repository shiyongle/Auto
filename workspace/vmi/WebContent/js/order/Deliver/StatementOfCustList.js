Ext.require(["DJ.myComponent.viewer.PdfViewer"]);

Ext.define('DJ.order.Deliver.StatementOfCustList', {
	extend : 'Ext.c.GridPanel',
	title : "对账单",
	id : 'DJ.order.Deliver.StatementOfCustList',
	pageSize : 50,
	remoteSort:false,
	url : 'GetStatementList.do',
	closable: true,
	onload : function() {
		Ext.getCmp('DJ.order.Deliver.StatementOfCustList.addbutton').hide();
		Ext.getCmp('DJ.order.Deliver.StatementOfCustList.editbutton').hide();
		Ext.getCmp('DJ.order.Deliver.StatementOfCustList.delbutton').hide();
		Ext.getCmp('DJ.order.Deliver.StatementOfCustList.viewbutton').hide();
		Ext.getCmp('DJ.order.Deliver.StatementOfCustList.exportbutton').hide();
		
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
	},
	Action_BeforeEditButtonClick : function(EditUI) {
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
	},
	custbar : [],
	fields : [{
		name : 'fid'
	}, {
		name : 'fmonth',
		myfilterfield : 'tos.fmonth',
		myfiltername : '年月',
		myfilterable : true
	}, {
		name : 'ffileid',
	}, {
		name : 'fcreatename',
	}, {
		name : 'fcreatetime',
	}
	],
	columns : [{
		dataIndex:'fid',
		hidden:true,
		hideable:false
	}, {
		text:'年月',
		dataIndex:'fmonth',
		width : 90
	}, {
		dataIndex:'fcreatename',
		text:'上传者',
		width : 150
	}, {
		dataIndex:'fcreatetime',
		text:'上传时间',
		width : 150
	}, {
		xtype:'templatecolumn',
		text:'查看',
		dataIndex:'fname',
		tpl : '<a href="javascript:void(0)" style="text-decoration:none;" onclick="DJ.myComponent.viewer.PdfViewer.show(\'{ffileid}\',\'{fmonth}\');">查看</a>',
		width : 50
	}, {
		xtype:'templatecolumn',
		text:'下载',
		dataIndex:'fname',
		tpl : '<a href="downloadProductdemandFile.do?fid={ffileid}" target="_blank" style="text-decoration:none;">下载</a>',
		width : 50
	},{
		dataIndex:'',
		text:'',
		flex:1
	}],
	selModel : Ext.create('Ext.selection.CheckboxModel')
});