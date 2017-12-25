Ext.define('DJ.order.Deliver.SDeliverorderList', {
	extend : 'Ext.c.GridPanel',
	title : "配送信息",
	id : 'DJ.order.Deliver.SDeliverorderList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetSDeliverorderList.do',
	// Delurl : "DelDeliversList.do",
	Delurl : "",
	// EditUI : "DJ.order.Deliver.DeliversEdit",
	EditUI : "",
	exporturl : "sdeliverordertoExcel.do",// 导出为EXCEL方法
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		Ext.getCmp('DJ.order.Deliver.SDeliverorderList.addbutton').hide();
		Ext.getCmp('DJ.order.Deliver.SDeliverorderList.editbutton').hide();
		Ext.getCmp('DJ.order.Deliver.SDeliverorderList.delbutton').hide();
//		Ext.getCmp('DJ.order.Deliver.SDeliverorderList.viewbutton').hide();
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
		var grid = Ext.getCmp("DJ.order.Deliver.SDeliverorderList");
		var record = grid.getSelectionModel().getSelection();
		if (record.length != 1) {
			throw "只能选中一条记录进行修改!";
		}
		if (record[0].get("fordered") == "1") {
			throw "已下单数据不能修改!";
		}
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
		var grid = Ext.getCmp("DJ.order.Deliver.SDeliverorderList");
		var record = grid.getSelectionModel().getSelection();
		for (var i = 0; i < record.length; i++) {
			if (record[i].get("fordered") == '1') {
				throw "已下单数据不能删除!";
			}
		}
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	fields : [{
		name : 'fid'
	}, {
		name : 'faddress',
		myfilterfield : 'd.faddress',
		myfiltername : '配送地址',
		myfilterable : true
	}, {
		name : 'fnumber',
		myfilterfield : 'd.fnumber',
		myfiltername : '配送单号',
		myfilterable : true
	}, {
		name : 'fcreatorid'
	}, {
		name : 'fupdateuserid'
	}, {
		name : 'fcustomerid'
	}, {
		name : 'fcusproductid'
	}, {
		name : 'fcustname',
		myfilterfield : 'c.fname',
		myfiltername : '客户名称',
		myfilterable : true
	}, {
		name : 'cutpdtname',
		myfilterfield : 'cpdt.fname',
		myfiltername : '包装物名称',
		myfilterable : true
	}, {
		name : 'flinkman',
		myfilterfield : 'd.flinkman',
		myfiltername : '联系人',
		myfilterable : true
	}, {
		name : 'flinkphone',
		myfilterfield : 'd.flinkphone',
		myfiltername : '联系电话',
		myfilterable : true
	}, {
		name : 'famount'
	}, {
		name : 'fdescription'
	}, {
		name : 'farrivetime',
		myfilterfield : 'd.farrivetime',
		myfiltername : '配送时间',
		myfilterable : true
	}, {
		name : 'fcreator'
	}, {
		name : 'flastupdater'
	}, {
		name : 'fcreatetime'
	}, {
		name : 'fupdatetime'
	}, {
		name : 'fordered'
	}, {
		name : 'fordermanid'
	}, {
		name : 'fordertime'
	}, {
		name : 'forderman'
	}, {
		name : 'fsaleorderid'
	}, {
		name : 'fordernumber'
	}, {
		name : 'forderentryid'
	}, {
		name : 'faudittime'
	}, {
		name : 'fauditor'
	},  {
		name : 'faudited'
	}, {
		name : 'fimportEAS',
		myfilterfield : 'd.fimportEAS',
		myfiltername : '是否导入',
		myfilterable : true
	}, {
		name : 'fouted',
		myfilterfield : 'd.fouted',
		myfiltername : '是否发货',
		myfilterable : true
	},{
		name:'fmatched'
	},{
		name : 'ftype'
	},{
		name : 'fcharacter'
	},'fpcmordernumber'],
	columns : [{
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true

	}, {
		'header' : 'fcreatorid',
		'dataIndex' : 'fcreatorid',
		hidden : true,
		hideable : false,
		sortable : true

	}, {
		'header' : 'fupdateuserid',
		'dataIndex' : 'fupdateuserid',
		hidden : true,
		hideable : false,
		sortable : true

	}, {
		'header' : 'fcustomerid',
		'dataIndex' : 'fcustomerid',
		hidden : true,
		hideable : false,
		sortable : true

	}, {
		'header' : 'fcusproductid',
		'dataIndex' : 'fcusproductid',
		hidden : true,
		hideable : false,
		sortable : true

	}, {
		'header' : 'fsaleorderid',
		'dataIndex' : 'fsaleorderid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : 'forderentryid',
		'dataIndex' : 'forderentryid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : '订单编号',
		'dataIndex' : 'fordernumber',
		sortable : true
	}, {
		'header' : '采购订单编号',
		'dataIndex' : 'fpcmordernumber',
		sortable : true
	}, {
		'header' : '客户名称',
		width:100,
		'dataIndex' : 'fcustname',
		sortable : true
	}, {
		'header' : '配送单号',
		'dataIndex' : 'fnumber',
		sortable : true
	}, {
		'header' : '包装物名称',
		width:200,
		'dataIndex' : 'cutpdtname',
		sortable : true
	}, {
		'header' : '配送数量',
		width:70,
		'dataIndex' : 'famount',
		sortable : true
	}, {
		'header' : '导入接口',
		width:60,
		dataIndex : 'fimportEAS',
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
		'dataIndex' : 'fcharacter',
		sortable : true
		
	},
		{

		'header' : '发货',
		width:40,
		dataIndex : 'fouted',
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	}, {

		'header' : '配货',
		dataIndex : 'fmatched',
		width : 40,
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	}, {
		'header' : '审核',
		dataIndex : 'faudited',
		width : 30,
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	}, {
		'header' : '配送时间',
		'dataIndex' : 'farrivetime',
		width : 140,
		sortable : true
	},{
		header : '类型',
		dataIndex : 'ftype',
		width : 70,
		sortable : true,
		renderer:function(value){
			return value==='0'?'正常':((value==1)?'补单':'补货');
		}
	}, {
		'header' : '联系人',
		width:70,
		'dataIndex' : 'flinkman',
		sortable : true
	}, {
		'header' : '联系电话',
		width:80,
		'dataIndex' : 'flinkphone',
		sortable : true
	}, {
		'header' : '配送地址',
		'dataIndex' : 'faddress',
			width : 250,
		sortable : true
	}, {
		'header' : '备注',
		'dataIndex' : 'fdescription',
		sortable : true
	}, {
		'header' : '审核人',
		'dataIndex' : 'fauditor',
		sortable : true
	}, {
		'header' : '审核时间',
		dataIndex : 'faudittime',
		width : 150,
		sortable : true
	}, {
		'header' : '创建人',
		'dataIndex' : 'fcreator',
		sortable : true
	}, {
		'header' : '创建时间',
		'dataIndex' : 'fcreatetime',
		width : 150,
		sortable : true
	}, {
		'header' : '修改人',
		'dataIndex' : 'flastupdater',
		sortable : true
	}, {
		'header' : '修改时间',
		'dataIndex' : 'fupdatetime',
		width : 150,
		sortable : true
	}
//	, {
//		'header' : '下单人',
//		'dataIndex' : 'fordermanid',
//		hidden : true,
//		sortable : true
//
//	}
//		{
//		'header' : '下单人',
//		'dataIndex' : 'forderman',
//		sortable : true
//	}, 
//		{
//		'header' : '下单',
//		dataIndex : 'fordered',
//		sortable : true,
//		renderer : function(value) {
//			if (value == 1) {
//				return '是';
//			} else {
//				return '否';
//			}
//		}
//	}, {
//		'header' : '下单时间',
//		dataIndex : 'fordertime',
//		width : 150,
//		sortable : true
//	}
	],
	selModel : Ext.create('Ext.selection.CheckboxModel')
})