Ext.define('DJ.System.QueryConfig.QueryConfigList', {
	extend : 'Ext.c.GridPanel',
	title : "查询配置",
	id : 'DJ.System.QueryConfig.QueryConfigList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetQueryConfigList.do',
	Delurl : "DelQueryConfigList.do",
	EditUI : "DJ.System.QueryConfig.QueryConfigEdit",
	exporturl : "deliverstoExcel.do",// 导出为EXCEL方法
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
	custbar : [{
		// id : 'DelButton',
		text : '传回正泰数据',
		height : 30,
		handler : function() {
			var grid = Ext.getCmp("DJ.System.QueryConfig.QueryConfigList");
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout:600000,
				url : "ExportZTcusdelivers.do",
//				params : {
//					feffect : feffect,
//					fidcls : ids
//				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
//						Ext.MessageBox.alert('成功', obj.msg);
						djsuccessmsg( obj.msg);
						grid.store.load();
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
		}
	}]
	// ,custbar :
	// [
	// {
	// // id : 'DelButton',
	// text : '下单',
	// height : 30,
	// handler : function() {
	// var grid = Ext.getCmp("DJ.order.Deliver.DeliversList");
	// var record = grid.getSelectionModel().getSelection();
	// if (record.length != 1) {
	// Ext.MessageBox.alert('提示', '只能选中一条记录进行修改!');
	// return;
	// }
	// var fid = record[0].get("fid");
	//							
	// Ext.Ajax.request({
	// url : "order.do",
	// params : {
	// fid : fid,
	// fnumber : record[0].get("fnumber"),
	// fcusproductid : record[0].get("fcusproductid"),
	// flinkman : record[0].get("flinkman"),
	// flinkphone : record[0].get("flinkphone"),
	// famount : record[0].get("famount"),
	// farrivetime : record[0].get("farrivetime"),
	// faddress : record[0].get("faddress"),
	// fcustomerid : record[0].get("fcustomerid")
	// }, // 参数
	// success : function(response, option) {
	// var obj = Ext.decode(response.responseText);
	// if (obj.success == true) {
	// Ext.MessageBox.alert('成功', obj.msg);
	// Ext.getCmp("DJ.order.Deliver.DeliversList").store.load();
	// } else {
	// Ext.MessageBox.alert('错误', obj.msg);
	// }
	// }
	// });
	// }
	// }
	// // ,{
	// // // id : 'DelButton',
	// // text : '自定义按钮2',
	// // height : 30
	// // }
	// ]
	,
	fields : [{
		name : 'fid'
	}, {
		name : 'fpath',
		myfilterfield : 'fpath',
		myfiltername : '路径',
		myfilterable : true
	}, {
		name : 'fseq',
		myfilterfield : 'fseq',
		myfiltername : '序号',
		myfilterable : false
	}, {
		name : 'fcolumnname',
		myfilterfield : 'fcolumnname',
		myfiltername : '字段名称',
		myfilterable : true
	}, {
		name : 'fcolumnkey',
		myfilterfield : 'fcolumnkey',
		myfiltername : '字段键值',
		myfilterable : true
	}, {
		name : 'ffilterable',
		myfilterfield : 'ffilterable',
		myfiltername : '参与过滤',
		myfilterable : true
	}, {
		name : 'ffilterfield',
		myfilterfield : 'ffilterfield',
		myfiltername : '数据库字段名',
		myfilterable : true
	}, {
		name : 'ftype',
		myfilterfield : 'ftype',
		myfiltername : '类型',
		myfilterable : true
	}, {
		name : 'fwidth',
		myfilterfield : 'fwidth',
		myfiltername : '列宽',
		myfilterable : false
	}, {
		name : 'fhide',
		myfilterfield : 'fhide',
		myfiltername : '是否隐藏',
		myfilterable : false
	}, {
		name : 'fsql',
		myfilterfield : 'fsql',
		myfiltername : '查询语句',
		myfilterable : false
	}],
	columns : [{
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true

	}, {
		'header' : '路径',
		'dataIndex' : 'fpath',
		sortable : true
	}, {
		'header' : '序号',
		'dataIndex' : 'fseq',
		sortable : true
	}, {
		'header' : '字段名称',
		'dataIndex' : 'fcolumnname',
		sortable : true
	}, {
		'header' : '字段键值',
		'dataIndex' : 'fcolumnkey',
		width : 150,
		sortable : true
	}, {
		'header' : '参与过滤',
		'dataIndex' : 'ffilterable',
		sortable : true
	}, {
		'header' : '列宽',
		'dataIndex' : 'fwidth',
		sortable : true
	}, {
		'header' : '数据库字段名',
		'dataIndex' : 'ffilterfield',
		sortable : true
	}, {
		'header' : '类型',
		'dataIndex' : 'ftype',
		sortable : true
	}, {
		'header' : '是否隐藏',
		'dataIndex' : 'fhide',
		width : 200,
		sortable : true
	}, {
		'header' : '查询语句',
		'dataIndex' : 'fsql',
		sortable : true
	}],
	selModel : Ext.create('Ext.selection.CheckboxModel')
})