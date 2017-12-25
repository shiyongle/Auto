Ext.define('DJ.order.Deliver.DeliverapplyHistoryList', {
	extend : 'Ext.c.GridPanel',
	title : "历史接口数据",
	id : 'DJ.order.Deliver.DeliverapplyHistoryList',
	pageSize : 200,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'getDeliverapplyHistoryList.do',
	Delurl : "",
	EditUI : "",
	exporturl : "deliverapplyHistoryoExcel.do",// 导出为EXCEL方法
	onload : function() {
			Ext.getCmp('DJ.order.Deliver.DeliverapplyHistoryList.addbutton').hide();
			Ext.getCmp('DJ.order.Deliver.DeliverapplyHistoryList.editbutton').hide();
			Ext.getCmp('DJ.order.Deliver.DeliverapplyHistoryList.delbutton').hide();
			Ext.getCmp('DJ.order.Deliver.DeliverapplyHistoryList.viewbutton').hide();
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	fields : [{
		name : 'fid'
	}, {
		name : 'fmaktx',
		myfilterfield : 'd.fmaktx',
		myfiltername : '发放产品名称',
		myfilterable : true
	}, {
		name : 'freqaddress',
		myfilterfield : 'd.freqaddress',
		myfiltername : '发放地址',
		myfilterable : true
	}, {
		name : 'faddressid'
	}, {
		name : 'freqdate',
		myfilterfield : 'd.freqdate',
		myfiltername : '发放配送时间',
		myfilterable : true
	}, {
		name : 'faddress',
		myfilterfield : 'a.fdetailaddress',
		myfiltername : '配送地址',
		myfilterable : true
	}, {
		name : 'fnumber',
		myfilterfield : 'd.fnumber',
		myfiltername : '申请单号',
		myfilterable : true
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
		myfiltername : '客户产品',
		myfilterable : true
	}, {
		name : 'flinkman',
		myfilterfield : 'd.flinkman',
		myfiltername : '发放人',
		myfilterable : true
	}, {
		name : 'flinkphone',
		myfilterfield : 'd.flinkphone',
		myfiltername : '联系电话',
		myfilterable : true
	}, {
		name : 'famount',
		myfilterfield : 'd.famount',
		myfiltername : '发放数量',
		myfilterable : true
	}, {
		name : 'fdescription',
		myfilterfield : 'd.fdescription',
		myfiltername : '备注',
		myfilterable : true
	}, {
		name : 'farrivetime',
		myfilterfield : 'd.farrivetime',
		myfiltername : '配送时间',
		myfilterable : true
	}, {
		name : 'fisread'
	}],
	columns : [{
		'header' : 'fid',
		'dataIndex' : 'fid',
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
		'header' : 'faddressid',
		'dataIndex' : 'faddressid',
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
		'header' : '客户名称',
		'dataIndex' : 'fcustname',
		width : 110,
		sortable : true
	}, {
		'header' : '发放产品名称',
		width : 180,
		'dataIndex' : 'fmaktx',
		sortable : true
	}, {
		'header' : '发放地址',
		width : 120,
		'dataIndex' : 'freqaddress',
		sortable : true
	}, {
		'header' : '申请单号',
		'dataIndex' : 'fnumber',
		hidden : true,
		sortable : true
	}, {
		'header' : '发放配送时间',
		'dataIndex' : 'freqdate',
		width : 140,
		sortable : true
	}, {
		'header' : '发放人',
		'dataIndex' : 'flinkman',
		width : 50,
		sortable : true
	}, {
		'header' : '联系电话',
		'dataIndex' : 'flinkphone',
		hidden : true,
		sortable : true
	}, {
		'header' : '发放数量',
		width : 60,
		'dataIndex' : 'famount',
		sortable : true
	}, {
		'header' : '配送时间',
		'dataIndex' : 'farrivetime',
		width : 140,
		sortable : true
	}, {
		'header' : '客户产品',
		'dataIndex' : 'cutpdtname',
		width : 180,
		sortable : true
	}, {
		'header' : '配送地址',
		'dataIndex' : 'faddress',
		width : 200,
		sortable : true
	}, {
		'header' : '备注',
		'dataIndex' : 'fdescription',
		width : 120,
		sortable : true
	}, {
		'header' : '读取',
		dataIndex : 'fisread',
		width : 40,
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	}],
	selModel : Ext.create('Ext.selection.CheckboxModel')
});
