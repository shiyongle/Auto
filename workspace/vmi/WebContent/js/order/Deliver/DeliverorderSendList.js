Ext.define('DJ.order.Deliver.DeliverorderSendList', {
	extend : 'Ext.c.GridPanel',
	title : "配送单发货",
	id : 'DJ.order.Deliver.DeliverorderSendList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetDeliverorderSendListMV.do',
	// Delurl : "DelDeliversList.do",
	Delurl : "",
	// EditUI : "DJ.order.Deliver.DeliversEdit",
	EditUI : "",
	exporturl : "DeliverorderSendtoExcel.do",// 导出为EXCEL方法
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		// alert("DeliverList");
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
		var grid = Ext.getCmp("DJ.order.Deliver.DeliverorderSendList");
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
		var grid = Ext.getCmp("DJ.order.Deliver.DeliverorderSendList");
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
	custbar : [
	{
//		text : '导入协同物流',
		text : '导入接口',
		height : 30,
		handler : function() {
			var grid = Ext.getCmp("DJ.order.Deliver.DeliverorderSendList");
			var record = grid.getSelectionModel().getSelection();
			if(record.length<1){
				Ext.MessageBox.alert("信息","请选择至少一条记录导入EAS！");
				return;
			}
			var ids = "";
			for ( var i = 0; i < record.length; i++) {
				var fid = record[i].get("fid");
				ids += fid ;
				if (i < record.length - 1) {
					ids = ids + ",";
				}
			}
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "deliverorderImportEAS.do",
				params : {
					fidcls : ids
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						  djsuccessmsg( obj.msg);
						Ext.getCmp("DJ.order.Deliver.DeliverorderSendList").store
								.load();
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
		}
	}, 
		{
		text : '自运发货',
		height : 30,
		handler : function() {
			var grid = Ext.getCmp("DJ.order.Deliver.DeliverorderSendList");
			var record = grid.getSelectionModel().getSelection();
			if(record.length<1){
				Ext.MessageBox.alert("信息","请选择至少一条记录导入EAS！");
				return;
			}
			var ids = "";
			for ( var i = 0; i < record.length; i++) {
				var fid = record[i].get("fid");
				ids +=  fid ;
				if (i < record.length - 1) {
					ids = ids + ",";
				}
			}
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "creatTruckassemble.do",
				params : {
					fidcls : ids
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						  djsuccessmsg( obj.msg);
						Ext.getCmp("DJ.order.Deliver.DeliverorderSendList").store
								.load();
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
		}
	}],
	fields : [{
		name : 'fid'
	}, {
		name : 'fordernumber',
		myfilterfield : 'fordernumber',
		myfiltername : '制造商订单号',
		myfilterable : true
	}, {
		name : 'fnumber',
		myfilterfield : 'fnumber',
		myfiltername : '配送单号',
		myfilterable : true
	}, {
		name : 'productname',
		myfilterfield : '_productname',
		myfiltername : '产品名称',
		myfilterable : true
	}, {
		name : 'faddress',
		myfilterfield : 'faddress',
		myfiltername : '配送地址',
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
		myfilterfield : '_custname',
		myfiltername : '客户名称',
		myfilterable : true
	}, {
		name : 'cutpdtname',
		myfilterfield : '_custpdtname',
		myfiltername : '客户产品',
		myfilterable : true
	}, {
		name : 'flinkman',
		myfilterfield : 'flinkman',
		myfiltername : '联系人',
		myfilterable : true
	}, {
		name : 'flinkphone',
		myfilterfield : 'flinkphone',
		myfiltername : '联系电话',
		myfilterable : true
	}, {
		name : 'famount'
	}, {
		name : 'fdescription'
	}, {
		name : 'farrivetime',
		myfilterfield : 'farrivetime',
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
		name : 'forderentryid'
	}, {
		name : 'fimportEAS'
	}, {
		name : 'faudittime'
	}, {
		name : 'fauditor'
	},  {
		name : 'faudited',
		myfilterfield : 'faudited',
		myfiltername : '是否审核',
		myfilterable : true
	}, {
		name : 'fouted',
		myfilterfield : 'fouted',
		myfiltername : '是否发货',
		myfilterable : true
	}, {
		name : 'fmatched',
		myfilterfield : 'fmatched',
		myfiltername : '是否配货',
		myfilterable : true
	},'fpcmordernumber'],
	columns : [{
		'header' : '制造商订单号',
		width : 100,
		'dataIndex' : 'fordernumber',
		sortable : true
	}, {
		'header' : '采购订单号',
		width : 100,
		'dataIndex' : 'fpcmordernumber',
		sortable : true
	}, {
		'header' : '客户名称',
		width : 100,
		'dataIndex' : 'fcustname',
		sortable : true
	}, {
		'header' : '配送单号',
		'dataIndex' : 'fnumber',
		sortable : true
	}, {
		'header' : '产品名称',
		width : 170,
		'dataIndex' : 'productname',
		sortable : true
	}, {
		'header' : '客户产品',
		'dataIndex' : 'cutpdtname',
		sortable : true
	}, {
		'header' : '配送数量',
		width : 60,
		'dataIndex' : 'famount',
		sortable : true
	}, {
		'header' : '导入',
		dataIndex : 'fimportEAS',
		sortable : true,
		width : 40,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	}, 
		{

		'header' : '发货',
		dataIndex : 'fouted',
		width : 40,
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	}, 
		{

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
	},{
		'header' : '配送时间',
		'dataIndex' : 'farrivetime',
		width : 120,
		sortable : true
	}, {
		'header' : '联系人',
		width : 70,
		'dataIndex' : 'flinkman',
		sortable : true
	}, {
		'header' : '联系电话',
		'dataIndex' : 'flinkphone',
		sortable : true
	}, {
		'header' : '配送地址',
		'dataIndex' : 'faddress',
		width : 200,
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