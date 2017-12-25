Ext.define('DJ.order.Deliver.FTUSupplierStatementList', {
	extend : 'Ext.c.GridPanel',
	title : "送货凭证制造商报表",
	id : 'DJ.order.Deliver.FTUSupplierStatementList',
	pageSize : 50,
	closable:true,
	url:'getFTUstatementList.do',
	exporturl : "ExcelFTUstatementsList.do",// 导出为EXCEL方法
	mixins : ['DJ.tools.grid.mixer.MyGridSearchMixer'],
	onload : function() {
		var me = this;
		this.down('button[text*=新]').hide();
		this.down('button[text*=修]').hide();
		this.down('button[text*=查]').hide();
		this.down('button[text*=删]').hide();
		
		MyGridTools.rebuildExcelAction(me);//导出设置
		
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
	Action_BeforeDelButtonClick : function(me, record) {
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	fields : [{
		name : 'fid'
	}, {
		name : 'fsuppliername',
		 myfilterfield : 'sa.fsuppliername',
		 myfiltername : '制造商名称',
		 myfilterable : true
	}, {
		name : 'fsupplierid'
//		myfilterfield : 'd.fordernumber',
//		myfiltername : '订单编号',
//		myfilterable : true
	}, {
		name : 'maxfcreatetime'
	}, {
		name : 'minfcreatetime'
	},{
		name : 'fcustomercount',
		myfilterfield : 'c.fname',
		myfiltername : '终端客户数量',
		myfilterable : true
	},'ftucount','sname'],
columns : {
	defaults: { // defaults are applied to items, not the container
//	    sortable: false
	},
	items:[{
		xtype: 'rownumberer',
		text:'序号',
		hidden:true
	},{
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true
	},{
		'header' : '制造商名称',
		dataIndex : 'sname',
		flex : 1,
		align : 'center'
	},{
		'header' : '发货单位',
		dataIndex : 'fsuppliername',
		flex : 1,
		align : 'center'
	}, {
		'header' : '出库单数量',
		'dataIndex' : 'ftucount',
		flex : 1,
		align : 'center'
	},{
		dataIndex:'maxfcreatetime',
		text:'最近使用时间',
		flex : 1,
		align : 'center'
	},{
		dataIndex:'minfcreatetime',
		text:'首次使用时间',
		flex : 1,
		align : 'center'
	},{
		dataIndex:'fcustomercount',
		text:'终端客户家数',
		flex : 1,
		align : 'center'
	}
	]},
	
	selModel : Ext.create('Ext.selection.CheckboxModel')
})