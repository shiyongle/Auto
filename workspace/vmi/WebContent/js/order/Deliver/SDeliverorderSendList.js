Ext.define('DJ.order.Deliver.SDeliverorderSendList', {
	extend : 'Ext.c.GridPanel',
	title : "配送单发货",
	id : 'DJ.order.Deliver.SDeliverorderSendList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetSDeliverorderSendList.do',
	// Delurl : "DelDeliversList.do",
	Delurl : "",
	// EditUI : "DJ.order.Deliver.DeliversEdit",
	EditUI : "",
	exporturl : "sdeliverordersendtoExcel.do",// 导出为EXCEL方法
	
	plugins : [Ext.create("Ext.ux.grid.MySimpleGridContextMenu", {

		useExistingButtons : ["button[text=自运发货]"]

	})]
	
		
		
		
	,
	
	statics : {
	
		CREAT_TRUCKASSEMBLE_URL : "creatTruckassembleByCondition.do"
		
	},
	
	
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		Ext.getCmp('DJ.order.Deliver.SDeliverorderSendList.addbutton').hide();
		Ext.getCmp('DJ.order.Deliver.SDeliverorderSendList.editbutton').hide();
		Ext.getCmp('DJ.order.Deliver.SDeliverorderSendList.delbutton').hide();
//		Ext.getCmp('DJ.order.Deliver.SDeliverorderSendList.viewbutton').hide();
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
		var grid = Ext.getCmp("DJ.order.Deliver.SDeliverorderSendList");
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
		var grid = Ext.getCmp("DJ.order.Deliver.SDeliverorderSendList");
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
//				{
//		text : '发货',
//		height : 30,
//		handler : function() {
//			var grid = Ext.getCmp("DJ.order.Deliver.DeliverorderSendList");
//			var record = grid.getSelectionModel().getSelection();
//			var ids = "";
//			for (var i = 0; i < record.length; i++) {
//				var fid = record[i].get("fid");
//				ids += fid;
//				if (i < record.length - 1) {
//					ids = ids + ",";
//				}
//			}
//			var el = grid.getEl();
//			el.mask("系统处理中,请稍候……");
//			Ext.Ajax.request({
//				url : "deliverorderSend.do",
//				params : {
//					fidcls : ids
//				}, // 参数
//				success : function(response, option) {
//					var obj = Ext.decode(response.responseText);
//					if (obj.success == true) {
//						Ext.MessageBox.alert('成功', obj.msg);
//						grid.store.load();
//						el.unmask();
//					} else {
//						Ext.MessageBox.alert('错误', obj.msg);
//						el.unmask();
//					}
//				}
//			});
//		}
//	},
	{
//		text : '导入协同物流',
		text : '导入接口',
		height : 30,
		handler : function() {
			var grid = Ext.getCmp("DJ.order.Deliver.SDeliverorderSendList");
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
						Ext.MessageBox.alert('成功', obj.msg);
						Ext.getCmp("DJ.order.Deliver.SDeliverorderSendList").store
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
			var grid = Ext.getCmp("DJ.order.Deliver.SDeliverorderSendList");
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
			
			if(record.length == 1){
				if(record[0].get("fouted")=="1" || record[0].get("fmatched")=="1" ){
					Ext.MessageBox.alert('错误', "该配送单已装配或已发货不能自运！");
					return;
				}
				var fid = record[0].get("fid");
				var amount = parseInt(record[0].get("famount"));
				var fassembleQty = record[0].get("fassembleQty")=="null"?0:parseInt(record[0].get("fassembleQty"));
				var RealAmountEdit = Ext.create('DJ.order.Deliver.RealAmountEdit');
				RealAmountEdit.parentid = 'DJ.order.Deliver.SDeliverorderSendList',
				RealAmountEdit.show();
				Ext.getCmp("DJ.order.Deliver.RealAmountEdit.deliverorderid").setValue(fid);
				Ext.getCmp("DJ.order.Deliver.RealAmountEdit.famount").setValue(amount);
				Ext.getCmp("DJ.order.Deliver.RealAmountEdit.fassembleQty").setValue(fassembleQty);
				Ext.getCmp("DJ.order.Deliver.RealAmountEdit.frealqty").setValue(amount-fassembleQty);
			}else {
				var el = grid.getEl();
				el.mask("系统处理中,请稍候……");
				Ext.Ajax.request({
					timeout : 600000,
//					url : "creatTruckassemble.do",
					url : DJ.order.Deliver.SDeliverorderSendList.CREAT_TRUCKASSEMBLE_URL,
					params : {
						fidcls : ids ,
						ftype : 0
					}, // 参数
					success : function(response, option) {
						var obj = Ext.decode(response.responseText);
						if (obj.success == true) {
							djsuccessmsg( obj.msg);
							Ext.getCmp("DJ.order.Deliver.SDeliverorderSendList").store
									.load();
						} else {
							Ext.MessageBox.alert('错误', obj.msg);
						}
						el.unmask();
					}
				});
			}
		}
	}],
	fields : [{
		name : 'fid'
	}, {
		name : 'fordernumber',
		myfilterfield : 'd.fordernumber',
		myfiltername : '制造商订单号',
		myfilterable : true
	}, {
		name : 'fnumber',
		myfilterfield : 'd.fnumber',
		myfiltername : '配送单号',
		myfilterable : true
	}, {
		name : 'productname',
		myfilterfield : 'pdef.fname',
		myfiltername : '产品名称',
		myfilterable : true
	}, {
		name : 'faddress',
		myfilterfield : 'd.faddress',
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
		myfilterfield : 'c.fname',
		myfiltername : '客户名称',
		myfilterable : true
	}, {
		name : 'cutpdtname',
		myfilterfield : 'cpdt.fname',
		myfiltername : '客户产品',
		myfilterable : true
	},{
		name : 'ftype'
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
		name : 'forderentryid'
	}, {
		name : 'fimportEAS',
		myfilterfield : 'd.fimportEAS',
		myfiltername : '是否导入',
		myfilterable : true
	}, {
		name : 'faudittime'
	}, {
		name : 'fauditor'
	},  {
		name : 'faudited'
	}, {
		name : 'fouted',
		myfilterfield : 'd.fouted',
		myfiltername : '是否发货',
		myfilterable : true
	}, {
		name : 'fmatched',
		myfilterfield : 'd.fmatched',
		myfiltername : '是否配货',
		myfilterable : true
	}, {
		name : 'fassembleQty'
	}, {
		name : 'foutQty'
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
		'header' : '提货数量',
		width:70,
		'dataIndex' : 'fassembleQty',
		sortable : true
	}, {
		'header' : '发货数量',
		width:70,
		'dataIndex' : 'foutQty',
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
	}, {
		'header' : '规格',
		'dataIndex' : 'fcharacter',
		sortable : true
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
	},{
		header : '类型',
		dataIndex : 'ftype',
		width : 70,
		sortable :true,
		renderer:function(value){
			return value==='0'?'正常':((value==1)?'补单':'补货');
		}
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