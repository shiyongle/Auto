Ext.define('DJ.order.Deliver.DeliversOrderList', {
	extend : 'Ext.c.GridPanel',
	title : "要货管理",
	id : 'DJ.order.Deliver.DeliversOrderList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetDeliversList.do',
	// Delurl : "DelDeliversList.do",
	Delurl : "",
	// EditUI : "DJ.order.Deliver.DeliversEdit",
	EditUI : "",
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
		var grid = Ext.getCmp("DJ.order.Deliver.DeliversOrderList");
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
		var grid = Ext.getCmp("DJ.order.Deliver.DeliversOrderList");
		var record = grid.getSelectionModel().getSelection();
		for (var i = 0; i < record.length; i++) {
			if (record[i].get("fordered") == '1') {
				throw "已下单数据不能删除!";
			}
			
			if (record[i].get("falloted") == '1') {
				throw "已分配配送单数据不能删除!";
			}
		}
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	custbar : [{
		text : '下单',
		height : 30,
		handler : function() {
			// var grid = Ext.getCmp("DJ.order.Deliver.DeliversOrderList");
			// var record = grid.getSelectionModel().getSelection();
			// if (record.length != 1) {
			// Ext.MessageBox.alert('提示', '只能选中一条记录进行下单!');
			// return;
			// }
			// var fid = record[0].get("fid");
			var me=Ext.getCmp("DJ.order.Deliver.DeliversOrderList");
			var record = me.getSelectionModel().getSelection();
			if (record.length < 1) {
				Ext.MessageBox.alert("信息", "请选择至少一条信息进行操作！");
				return;
			}
			var el = me.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "VMIorder.do",
				params : {
				// fid : fid,
				// fnumber : record[0].get("fnumber"),
				// fcusproductid : record[0].get("fcusproductid"),
				// flinkman : record[0].get("flinkman"),
				// flinkphone : record[0].get("flinkphone"),
				// famount : record[0].get("famount"),
				// farrivetime : record[0].get("farrivetime"),
				// faddress : record[0].get("faddress"),
				 fcustomerid : record[0].get("fcustomerid")
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						  djsuccessmsg( obj.msg);
						Ext.getCmp("DJ.order.Deliver.DeliversOrderList").store
								.load();
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
		}
	}, {
		text : '分配配送单',
		height : 30,
		handler : function() {
			var grid = Ext.getCmp("DJ.order.Deliver.DeliversOrderList");
			var record = grid.getSelectionModel().getSelection();
			if (record.length < 1) {
				Ext.MessageBox.alert("信息", "请选择至少一条信息进行操作！");
				return;
			}
//			var record = grid.getSelectionModel().getSelection();
//			var ids = "(";
//			for (var i = 0; i < record.length; i++) {
//				var fid = record[i].get("fid");
//				ids += "'" + fid + "'";
//				if (i < record.length - 1) {
//					ids = ids + ",";
//				}
//			}
//			ids = ids + ")";
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "deliversToAllot.do",
				params : {
					fcustomerid : record[0].get("fcustomerid"),
					allottype : 0//分配类型
				}, // 参
//				params : {
//					fdeliverid : ids
//				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						  djsuccessmsg( obj.msg);
						grid.store.load();
						el.unmask();
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
						el.unmask();
					}
				}
			});
		}
	}, {
		text : '取消分配',
		height : 30,
		handler : function() {
			var grid = Ext.getCmp("DJ.order.Deliver.DeliversOrderList");
			var record = grid.getSelectionModel().getSelection();
			var fid = "";
							for(var i=0;i<record.length;i++){
								if(record[i].get("falloted")==0){
									Ext.MessageBox.alert('错误',"数据未分配不能取消分配！");
									return;
								}
								if(fid.length<=0){
									fid = record[i].get("fid");
								}else{
									fid = fid + ","+record[i].get("fid");
								}
							}
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				url : "deliversUnAllot.do",
				params : {
					fidcls : fid
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						  djsuccessmsg( obj.msg);
						grid.store.load();
						el.unmask();
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
						el.unmask();
					}
				}
			});
		}
		}, {
		text : '指定订单',
		height : 30,
		handler : function() {
			var grid = Ext.getCmp("DJ.order.Deliver.DeliversOrderList");
			var record = grid.getSelectionModel().getSelection();
			if(record.length!=1)
			{
				Ext.MessageBox.alert('错误',"请选择一条记录进行指定！");
				return;
			}
			if(record[0].get("falloted")=="1")
			{
				Ext.MessageBox.alert('错误',"该要货信息已经分配！");
				return;
			}
			var editui = Ext.create('DJ.order.Deliver.AssignAllotEdit');
			editui.seteditstate("edit");
			editui.setparent('DJ.order.Deliver.DeliversOrderList');
			editui.loadfields(record[0].get("fid"));
			editui.show();
		}
	}],
	fields : [{
		name : 'fid'
	}, {
		name : 'fcustname',
		myfilterfield : 'c.fname',
		myfiltername : '客户名称',
		myfilterable : true
	}, {
		name : 'fsupplier'
	},{
		name : 'fordernumber',
		myfilterfield : 'd.fordernumber',
		myfiltername : '订单编号',
		myfilterable : true
	}, {
		name : 'fnumber',
		myfilterfield : 'd.fnumber',
		myfiltername : '要货管理编码',
		myfilterable : true
	}, {
		name : 'pname',
		myfilterfield : 'f.fname',
		myfiltername : '产品名称',
		myfilterable : true
	}, {
		name : 'faddress',
		myfilterfield : 'd.faddress',
		myfiltername : '配送地址',
		myfilterable : true
	},{
		name:'fapplynumber',
		myfilterfield : 'd.fapplynumber',
		myfiltername : '要货申请编码',
		myfilterable : true
	}, {
		name : 'fcreatorid'
	}, {
		name : 'fupdateuserid'
	}, {
		name : 'fcustomerid'
	}, {
		name : 'fcusproductid'
	},{
		name:'fproductid'
	}, {
		name : 'cutpdtname',
		myfilterfield : 'cpdt.fname',
		myfiltername : '客户产品',
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
		name : 'fordered',
		myfilterfield : 'd.fordered',
		myfiltername : '是否下单',
		myfilterable : true
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
	}, 
//		{
//		name : 'fimportEAS'
//	}, 
//		{
//		name : 'fouted'
//	},
	{
		name : 'falloted',
		myfilterfield : 'd.falloted',
		myfiltername : '是否分配',
		myfilterable : true
	},{
		name : 'ftype'
	},{
		name : 'fcharacter'
	},{
		name:'fpcmordernumber'
	}],
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
		'header' : 'fproductid',
		'dataIndex' : 'fproductid',
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
		'header' : '采购订单号',
		'dataIndex' : 'fpcmordernumber',
		sortable : true
	}, {
		'header' : '客户名称',
		'dataIndex' : 'fcustname',
		sortable : true
	}, {
		'header' : '制造商',
		'dataIndex' : 'fsupplier',
		sortable : true
	} ,{
		'header' : '要货管理编码',
		'dataIndex' : 'fnumber',
		sortable : true
	}, {
		'header' : '产品名称',
		width : 200,
		'dataIndex' : 'pname',
		sortable : true
	}, {
		header : '特性名称',
		dataIndex : 'fcharacter',
		width : 150,
		sortable : true
	}, { 
		'header' : '客户产品',
		width : 200,
		'dataIndex' : 'cutpdtname',
		sortable : true
	}, {
		'header' : '配送数量',
		'dataIndex' : 'famount',
		width : 60,
		sortable : true
	}, {
		'header' : '配送时间',
		'dataIndex' : 'farrivetime',
		width : 140,
		sortable : true
	}, {
		'header' : '下单',
		dataIndex : 'fordered',
		width : 40,
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	},{
		'header' : '分配',
		dataIndex : 'falloted',
		width : 40,
		sortable : true,
		renderer: function(value){
		        if (value == 1) {
		            return '是';
		        }
		        else{
		        	return '否';
		        }
		    }
	},{
		'header' : '要货申请编码',
		dataIndex : 'fapplynumber',
		width : 90,
		sortable : true
		
	},{
		header : '类型',
		dataIndex : 'ftype',
		sortable : true,
		width : 70,
		renderer:function(value){
			return value==='0'?'正常':((value==1)?'补单':'补货');
		}
	}, {
		'header' : '联系人',
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
	}, {
		'header' : '下单人',
		'dataIndex' : 'fordermanid',
		hidden : true,
		sortable : true
	}, {
		'header' : '下单人',
		'dataIndex' : 'forderman',
		sortable : true
	}, {
		'header' : '下单时间',
		dataIndex : 'fordertime',
		width : 150,
		sortable : true
	}
//		{
//		'header' : '导入EAS',
//		dataIndex : 'fimportEAS',
//		sortable : true,
//		renderer : function(value) {
//			if (value == 1) {
//				return '是';
//			} else {
//				return '否';
//			}
//		}
//	},
//	{
//		'header' : '发货',
//		dataIndex : 'fouted',
//		sortable : true,
//		renderer : function(value) {
//			if (value == 1) {
//				return '是';
//			} else {
//				return '否';
//			}
//		}
//	},
		],
	selModel : Ext.create('Ext.selection.CheckboxModel')
})