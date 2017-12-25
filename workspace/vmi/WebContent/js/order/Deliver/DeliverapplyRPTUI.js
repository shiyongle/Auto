Ext.define('DJ.order.Deliver.DeliverapplyRPTUI', {
	extend : 'Ext.c.GridPanel',
	title : "要货报表",
	id : 'DJ.order.Deliver.DeliverapplyRPTUI',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetRPTUIData.do',
	// Delurl : "DelDeliversList.do",
	Delurl : "",
	// EditUI : "DJ.order.Deliver.DeliversEdit",
	EditUI : "",
	exporturl : "",// 导出为EXCEL方法
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		// alert("DeliverList");
		Ext.getCmp("DJ.order.Deliver.DeliverapplyRPTUI.addbutton").setVisible(false);
		Ext.getCmp("DJ.order.Deliver.DeliverapplyRPTUI.editbutton").setVisible(false);
		Ext.getCmp("DJ.order.Deliver.DeliverapplyRPTUI.delbutton").setVisible(false);
		Ext.getCmp("DJ.order.Deliver.DeliverapplyRPTUI.exportbutton").setVisible(false);
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
		text : '逻辑描述',
		height : 30,
		handler : function() {
			Ext.MessageBox
					.alert(
							'描述',
							'<p><h4 align="center">"要货报表"---已发货，并且产品有库存量的要货申请信息</h4></p>'
									+ '<ul><li>生产：既是否下单;要货申请通过与要货管理的关联，查询其是否已下单</li>'
									+ '<li>产品库存：产品当前库存量；库存表根据产品查询总库存量（库存量>0）</li></ul>');
		}
	}],
// custbar : [{
//		text : '下单',
//		height : 30,
//		handler : function() {
//			// var grid = Ext.getCmp("DJ.order.Deliver.DeliversOrderList");
//			// var record = grid.getSelectionModel().getSelection();
//			// if (record.length != 1) {
//			// Ext.MessageBox.alert('提示', '只能选中一条记录进行下单!');
//			// return;
//			// }
//			// var fid = record[0].get("fid");
//			var me=Ext.getCmp("DJ.order.Deliver.DeliversOrderList");
//			var el = me.getEl();
//			el.mask("系统处理中,请稍候……");
//			Ext.Ajax.request({
//				timeout : 600000,
//				url : "VMIorder.do",
//				params : {
//				// fid : fid,
//				// fnumber : record[0].get("fnumber"),
//				// fcusproductid : record[0].get("fcusproductid"),
//				// flinkman : record[0].get("flinkman"),
//				// flinkphone : record[0].get("flinkphone"),
//				// famount : record[0].get("famount"),
//				// farrivetime : record[0].get("farrivetime"),
//				// faddress : record[0].get("faddress"),
//				// fcustomerid : record[0].get("fcustomerid")
//				}, // 参数
//				success : function(response, option) {
//					var obj = Ext.decode(response.responseText);
//					if (obj.success == true) {
//						  djsuccessmsg( obj.msg);
//						Ext.getCmp("DJ.order.Deliver.DeliversOrderList").store
//								.load();
//					} else {
//						Ext.MessageBox.alert('错误', obj.msg);
//					}
//					el.unmask();
//				}
//			});
//		}
//	}, {
//		text : '分配配送单',
//		height : 30,
//		handler : function() {
//			var grid = Ext.getCmp("DJ.order.Deliver.DeliversOrderList");
////			var record = grid.getSelectionModel().getSelection();
////			var ids = "(";
////			for (var i = 0; i < record.length; i++) {
////				var fid = record[i].get("fid");
////				ids += "'" + fid + "'";
////				if (i < record.length - 1) {
////					ids = ids + ",";
////				}
////			}
////			ids = ids + ")";
//			var el = grid.getEl();
//			el.mask("系统处理中,请稍候……");
//			Ext.Ajax.request({
//				url : "deliversAllot.do",
////				params : {
////					fdeliverid : ids
////				}, // 参数
//				success : function(response, option) {
//					var obj = Ext.decode(response.responseText);
//					if (obj.success == true) {
//						  djsuccessmsg( obj.msg);
//						grid.store.load();
//						el.unmask();
//					} else {
//						Ext.MessageBox.alert('错误', obj.msg);
//						el.unmask();
//					}
//				}
//			});
//		}
//	}, {
//		text : '取消分配',
//		height : 30,
//		handler : function() {
//			var grid = Ext.getCmp("DJ.order.Deliver.DeliversOrderList");
//			var record = grid.getSelectionModel().getSelection();
//			var fid = "";
//							for(var i=0;i<record.length;i++){
//								if(record[i].get("falloted")==0){
//									Ext.MessageBox.alert('错误',"数据未分配不能取消分配！");
//									return;
//								}
//								if(fid.length<=0){
//									fid = record[i].get("fid");
//								}else{
//									fid = fid + ","+record[i].get("fid");
//								}
//							}
//			var el = grid.getEl();
//			el.mask("系统处理中,请稍候……");
//			Ext.Ajax.request({
//				url : "deliversUnAllot.do",
//				params : {
//					fidcls : fid
//				}, // 参数
//				success : function(response, option) {
//					var obj = Ext.decode(response.responseText);
//					if (obj.success == true) {
//						  djsuccessmsg( obj.msg);
//						grid.store.load();
//						el.unmask();
//					} else {
//						Ext.MessageBox.alert('错误', obj.msg);
//						el.unmask();
//					}
//				}
//			});
//		}
//		}, {
//		text : '指定订单',
//		height : 30,
//		handler : function() {
//			var grid = Ext.getCmp("DJ.order.Deliver.DeliversOrderList");
//			var record = grid.getSelectionModel().getSelection();
//			if(record.length!=1)
//			{
//				Ext.MessageBox.alert('错误',"请选择一条记录进行指定！");
//				return;
//			}
//			if(record[0].get("falloted")=="1")
//			{
//				Ext.MessageBox.alert('错误',"该要货信息已经分配！");
//				return;
//			}
//			var el = grid.getEl();
//			el.mask("系统处理中,请稍候……");
//			Ext.Ajax.request({
//				url : "getAssginDeliversInfo.do",
//				params : {
//					fid : record[0].get("fid")
//				}, // 参数
//				success : function(response, option) {
//					var obj = Ext.decode(response.responseText);
//					if (obj.success == true) {
//						var rinfo=obj.data;
//					//	alert(rinfo[0].famount);
//						var assignOrderEdit = Ext.getCmp("DJ.order.Deliver.AssignOrderEdit");
//			
//						if (assignOrderEdit != null) {
//						} else {
//							assignOrderEdit = Ext.create('DJ.order.Deliver.AssignOrderEdit');
//						}
//						Ext.getCmp("DJ.order.Deliver.deliverFnumber").setValue(rinfo[0].fnumber);
//						Ext.getCmp("DJ.order.Deliver.deliversid").setValue(rinfo[0].fid);
//						Ext.getCmp("DJ.order.Deliver.ftotalnum").setValue(rinfo[0].famount);
//						Ext.getCmp("DJ.order.Deliver.fproductid").setValue(rinfo[0].FPRODUCTDEFID);
//						assignOrderEdit.show();
//						var custgrid = Ext.getCmp("AssginOrderEditGridPanel");
//						var custstore = custgrid.getStore();
//						custstore.loadData("");
//						el.unmask();
////						grid.store.load();
//					} else {
//						Ext.MessageBox.alert('错误', obj.msg);
//						el.unmask();
//					}
//				}
//			});
//			
//		
//			
//			
////			var assignOrderEdit = Ext.getCmp("DJ.order.Deliver.AssignOrderEdit");
////
////			if (assignOrderEdit != null) {
////				assignOrderEdit.show();
////			} else {
////				var relationCust2 = Ext.create('DJ.order.Deliver.AssignOrderEdit');
////				relationCust2.show();
////			}
////		
////			Ext.getCmp("DJ.order.Deliver.deliverFnumber").setValue(record[0]
////					.get("fnumber"));
////			Ext.getCmp("DJ.order.Deliver.deliversid").setValue(record[0]
////					.get("fid"));
////			Ext.getCmp("DJ.order.Deliver.ftotalnum").setValue(record[0]
////					.get("famount"));
////			Ext.getCmp("DJ.order.Deliver.ftotalnum").setValue(record[0]
////					.get("famount"));
//		}
//	}],
	fields : [
	{
		name : 'fapplynum'
		,myfilterfield : 'a.fnumber',
		myfiltername : '要货申请编号',
		myfilterable : true
	}, {
		name : 'fcustpdtname'
		,myfilterfield : 'c.fname',
		myfiltername : '客户产品',
		myfilterable : true
	}, {
		name : 'famount'
		,myfilterfield : 'a.famount',
		myfiltername : '要货申请数量',
		myfilterable : true
	}, {
		name : 'fdeliversnum'
		,myfilterfield : 'd.fnumber',
		myfiltername : '要货管理编号',
		myfilterable : true
	}, {
		name : 'fordernumber'
		,myfilterfield : 'd.fordernumber',
		myfiltername : '制造商订单',
		myfilterable : true
	}, {
		name : 'fordered'
		,myfilterfield : 'd.fordered',
		myfiltername : '是否生产',
		myfilterable : true
	}, {
		name : 'fproductQty'
	}
	],
	columns : [
//	{
//		'header' : 'fid',
//		'dataIndex' : 'fid',
//		hidden : true,
//		hideable : false,
//		sortable : true
//
//	}, {
//		'header' : 'fcreatorid',
//		'dataIndex' : 'fcreatorid',
//		hidden : true,
//		hideable : false,
//		sortable : true
//
//	}, {
//		'header' : 'fupdateuserid',
//		'dataIndex' : 'fupdateuserid',
//		hidden : true,
//		hideable : false,
//		sortable : true
//
//	}, {
//		'header' : 'fcustomerid',
//		'dataIndex' : 'fcustomerid',
//		hidden : true,
//		hideable : false,
//		sortable : true
//
//	}, 
//	{
//		'header' : 'fcusproductid',
//		'dataIndex' : 'fcusproductid',
//		hidden : true,
//		hideable : false,
//		sortable : true
//	}, {
//		'header' : 'fproductid',
//		'dataIndex' : 'fproductid',
//		hidden : true,
//		hideable : false,
//		sortable : true
//
//	}, {
//		'header' : 'fsaleorderid',
//		'dataIndex' : 'fsaleorderid',
//		hidden : true,
//		hideable : false,
//		sortable : true
//	}, {
//		'header' : 'forderentryid',
//		'dataIndex' : 'forderentryid',
//		hidden : true,
//		hideable : false,
//		sortable : true
//	}, 
	{
		'header' : '要货申请编码',
		dataIndex : 'fapplynum',
		width : 90,
		sortable : true
		
	}, {
		'header' : '客户产品',
		width : 200,
		'dataIndex' : 'fcustpdtname',
		sortable : true
	}, {
		'header' : '要货数量',
		'dataIndex' : 'famount',
		width : 60,
		sortable : true
	}, {
		'header' : '要货管理编码',
		'dataIndex' : 'fdeliversnum',
		sortable : true
	}, {
		'header' : '订单编号',
		'dataIndex' : 'fordernumber',
		sortable : true
	}, {
		'header' : '生产',
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
	}, {
		'header' : '产品库存',
		dataIndex : 'fproductQty',
		width : 80,
		sortable : true
	}
	],
	selModel : Ext.create('Ext.selection.CheckboxModel')
})