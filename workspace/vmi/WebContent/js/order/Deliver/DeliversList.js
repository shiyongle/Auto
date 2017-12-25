Ext.QuickTips.init();
Ext.define('DJ.order.Deliver.DeliversList', {
	extend : 'Ext.c.GridPanel',
	title : "要货申请",
	id : 'DJ.order.Deliver.DeliversList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetDeliverapplyListMV.do',
	Delurl : "DelDeliverapplyList.do",
	EditUI : "DJ.order.Deliver.DeliversEdit",
	exporturl : "deliverapplytoExcelMV.do",// 导出为EXCEL方法

	statics : {
		DELIVERS_STATE : ["已创建", "已接收", "已排产", "已排产", "已入库","部分发货", "全部发货"]
	},

	onload : function() {
		var r = DJ.order.Deliver.DeliversList.DELIVERS_STATE;
		var fstate = "";
		for(var i=0;i<r.length;i++){
			fstate += i+'是'+r[i];
			if(i<r.length-1){
				fstate += ","; 
			}
		}
		//"要货申请状态过滤值为数字：0为要货创建,1为已接收,2为已下单,3为已分配,4为已装配,5为已发货"
		Ext.getCmp("DJ.order.Deliver.DeliversList.querybutton").setTooltip("要货申请状态过滤值为数字："+fstate);

	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件

		Ext.getCmp("DJ.order.Deliver.DeliversEdit.balance").setReadOnly(true);
		Ext.getCmp("DJ.order.Deliver.DeliversEdit.fcharacter").setReadOnly(true);

		var customerFiled = Ext
				.getCmp("DJ.order.Deliver.DeliversEdit.fcustomerid");

		// 获取客户

		var me = this;
		var el = me.getEl();
		el.mask("系统处理中,请稍候……");

		Ext.Ajax.request({
			timeout : 60000,
			url : "selectCustomerByUser.do",
			success : function(response, option) {

				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {
					// djsuccessmsg( obj.msg);
					if (obj.data != undefined) {
						customerFiled.setmyvalue("\"fid\":\""
								+ obj.data[0].customer + "\",\"fname\":\""
								+ obj.data[0].customerName + "\"");
					}
				} else {
					Ext.MessageBox.alert('错误', obj.msg);

				}
				el.unmask();
			}
		});
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
		var grid = Ext.getCmp("DJ.order.Deliver.DeliversList");
		var record = grid.getSelectionModel().getSelection();
		if (record.length != 1) {
			throw "只能选中一条记录进行修改!";
		}
		if (record[0].get("fiscreate") == "1") {
			throw "已生成要货管理不能修改!";
		}
	},
	Action_AfterEditButtonClick : function(EditUI) {
		Ext.getCmp("DJ.order.Deliver.DeliversEdit.fcharacter").setReadOnly(true);
		// 修改界面弹出后事件
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
		var grid = Ext.getCmp("DJ.order.Deliver.DeliversList");
		var record = grid.getSelectionModel().getSelection();
		for (var i = 0; i < record.length; i++) {
			if (record[i].get("fiscreate") == '1'&& record[i].get('ftraitid')=='') {
				throw "已生成要货管理不能删除!";

			}
		}
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	custbar : [/*{
		text : '生成管理测试',
		height : 30,
		handler : function() {
			var me = Ext.getCmp("DJ.order.Deliver.DeliversList");
			var el = me.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "autocreatedelivers.do",
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						djsuccessmsg(obj.msg);
						Ext.getCmp("DJ.order.Deliver.DeliversList").store
								.load();

					} else {
						Ext.MessageBox.alert('错误', obj.msg);

					}
					el.unmask();
				}
			});

		}
	},*/{
		text : '生成管理',
		height : 30,
		handler : function() {
			var me = Ext.getCmp("DJ.order.Deliver.DeliversList");
			var record = me.getSelectionModel().getSelection();
			if (record.length < 1) {
				Ext.MessageBox.alert("信息", "请选择至少一条信息进行操作！");
				return;
			}
			var el = me.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "CreatetoDelivers.do",
				params : {
					fcustomerid : record[0].get("fcustomerid")
				}, // 参
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						djsuccessmsg(obj.msg);
						Ext.getCmp("DJ.order.Deliver.DeliversList").store
								.load();

					} else {
						Ext.MessageBox.alert('错误', obj.msg);

					}
					el.unmask();
				}
			});

		}
	}, {
		text : '指定生成',
		height : 30,
		handler : function() {

			var fapplyid = "", info = "", customerid = "", fnumber = "";

			var grid = Ext.getCmp("DJ.order.Deliver.DeliversList");
			var record = grid.getSelectionModel().getSelection();
			if (record.length < 1) {
				Ext.MessageBox.alert("信息", "请选择至少一条信息进行操作！");
				return;
			}
			for (var i = 0; i < record.length; i++) {
				if (record[i].get("fiscreate") == '1') {
					Ext.MessageBox.alert('错误', "已生成要货管理!");
					return;
				}
				fapplyid += record[i].get("fid");
				fnumber += record[i].get("fnumber");
				if (info == "") {
					info = record[i].get("fcustomerid")
							+ record[i].get("farrivetime")
							+ record[i].get("faddress");
					customerid = record[i].get("fcustomerid");
				} else {
					if (info != (record[i].get("fcustomerid")
							+ record[i].get("farrivetime") + record[i]
							.get("faddress"))) {
						Ext.MessageBox.alert('错误', "所选的送货地址或客户或送达时间不一致不能进行操作!");
						return;
					}
				}
				if (i < record.length - 1) {
					fapplyid = fapplyid + ",";
					fnumber = fnumber + ",";
				}
			}
			var newwid = Ext.create('DJ.order.Deliver.AssignDeliverapplyEdit');
			newwid.show();
			newwid.items.getAt(0).getForm().findField("fidcls")
					.setValue(fapplyid);
			newwid.items.getAt(0).getForm().findField("fcustomerid")
					.setValue(customerid);
			newwid.items.getAt(0).getForm().findField("ffnumber")
					.setValue(fnumber);

		}
	}, {
		text : '取消生成',
		height : 30,
		handler : function() {
			var grid = Ext.getCmp("DJ.order.Deliver.DeliversList");
			var record = grid.getSelectionModel().getSelection();
			var fidcls = "";
			if (record.length < 1) {
				Ext.MessageBox.alert("信息", "请至少选择一条信息进行操作！");
				return;
			}

			for (var i = 0; i < record.length; i++) {
				if (record[i].get("fiscreate") == 0) {
					Ext.MessageBox.alert("信息", "该申请未生成要货管理！");
					return;
				}
				if (fidcls.length <= 0) {
					fidcls = record[i].get("fid");
				} else {
					fidcls = fidcls + "," + record[i].get("fid");
				}
			}
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "CanceltoCreate.do",
				params : {
					fidcls : fidcls
				}, // 参
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						djsuccessmsg(obj.msg);
						Ext.getCmp("DJ.order.Deliver.DeliversList").store
								.load();

					} else {
						Ext.MessageBox.alert('错误', obj.msg);

					}
					el.unmask();
				}

			});
		}

	}, {
		text : '拆分要货申请',
		height : 30,
		handler : function() {
			var grid = Ext.getCmp("DJ.order.Deliver.DeliversList");
			var record = grid.getSelectionModel().getSelection();
			if (record.length != 1) {
				Ext.MessageBox.alert('错误', "请选择一条记录进行拆分！");
				return;
			}
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				url : "getSplitdeliverapplyInfo.do",
				params : {
					fid : record[0].get("fid")
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						var rinfo = obj.data;
						var assignOrderEdit = Ext
								.getCmp("DJ.order.Deliver.SplitDeliversEdit");

						if (assignOrderEdit != null) {
						} else {
							assignOrderEdit = Ext
									.create('DJ.order.Deliver.SplitDeliversEdit');
						}
						Ext
								.getCmp("DJ.order.Deliver.SplitDeliversEdit.orderfnumber")
								.setValue(rinfo[0].fnumber);
						Ext
								.getCmp("DJ.order.Deliver.SplitDeliversEdit.deliverorderid")
								.setValue(rinfo[0].fid);
						Ext
								.getCmp("DJ.order.Deliver.SplitDeliversEdit.ftotalnum")
								.setValue(rinfo[0].famount);
						Ext
								.getCmp("DJ.order.Deliver.SplitDeliversEdit.farrivetime")
								.setValue(rinfo[0].farrivetime);
						assignOrderEdit.show();
						var custgrid = Ext.getCmp("SplitOrderEditGridPanel");
						var custstore = custgrid.getStore();
						custstore.loadData("");
						el.unmask();
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
						el.unmask();
					}
				}
			});
		}

	}, {
		text : '更改状态',
		height : 30,
		handler : function() {
			record = Ext.getCmp('DJ.order.Deliver.DeliversList')
					.getSelectionModel().getSelection();
			if (record.length < 1) {
				Ext.Msg.alert('提示', '请至少选择一条记录');
				return;
			}
			for (var i = 0; i < record.length; i++) {
				if (record[i].get('fiscreate') == '1') {
					Ext.Msg.alert('提示', '已生成要货管理，不能更改类型');
					return;
				}
			}
			var typeEditWin = Ext.getCmp('DJ.order.Deliver.DeliversTypeEdit')
					|| Ext.create('DJ.order.Deliver.DeliversTypeEdit');
			typeEditWin.show();
		}
	}/*,{
		text : '取消生成(快速下单)',
		height : 30,
		handler : function() {
			var grid = Ext.getCmp("DJ.order.Deliver.DeliversList");
			var record = grid.getSelectionModel().getSelection();
			var fidcls = "";
			if (record.length < 1) {
				Ext.MessageBox.alert("信息", "请至少选择一条信息进行操作！");
				return;
			}

			for (var i = 0; i < record.length; i++) {
//				if (record[i].get("fiscreate") == 0) {
//					Ext.MessageBox.alert("信息", "该申请未生成要货管理！");
//					return;
//				}
				if (fidcls.length <= 0) {
					fidcls = record[i].get("fid");
				} else {
					fidcls = fidcls + "," + record[i].get("fid");
				}
			}
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "deletecreatedelivers.do",
				params : {
					fids : fidcls
				}, // 参
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						djsuccessmsg(obj.msg);
						Ext.getCmp("DJ.order.Deliver.DeliversList").store
								.load();

					} else {
						Ext.MessageBox.alert('错误', obj.msg);

					}
					el.unmask();
				}

			});
		}
	}*/],
	fields : [{
		name : 'fid'
	}, {
		name : 'faddress',
		myfilterfield : 'faddress',
		myfiltername : '配送地址',
		myfilterable : true
	}, {
		name : 'fnumber',
		myfilterfield : 'mv.fnumber',
		myfiltername : '申请单号',
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
	},{
		name : 'fsupplier'
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
		name : 'fsaleorderid'
	}, {
		name : 'fiscreate',
		myfilterfield : 'fiscreate',
		myfiltername : '是否生成',
		myfilterable : true
	}, {
		name : 'fstate',
		myfilterfield : 'mv.fstate',
		myfiltername : '申请状态',
		myfilterable : true
	}, {
		name : 'ftype'
	},{
		name : 'fwerkname'
	},{
		name : 'fcharacter'
	},{
		name : 'ftraitid'
	},'foutQty','fordernumber','fisupdate'],
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
		'header' : '客户名称',
		width : 140,
		'dataIndex' : 'fcustname',
		sortable : true
	},{
		'header' : '制造商',
		width : 140,
		'dataIndex' : 'fsupplier',
		sortable : true
	},{
		header : '制造部',
		dataIndex : 'fwerkname',
		width : 120,
		sortable : true
	},{
		header : '采购订单号',
		dataIndex : 'fordernumber',
		width : 90,
		sortable : true
	}, {
		'header' : '申请单号',
		width : 90,
		'dataIndex' : 'fnumber',
		sortable : true
	},{
		text:'产品变更',
		width : 90,
		sortable : true,
		dataIndex:'fisupdate',
		renderer:function(val){
			return val==1?"产品信息有变动":"";
		}
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
	},{
		'header' : '实发数量',
		'dataIndex' : 'foutQty',
		width : 60,
		sortable : true
	}, {
		'header' : '状态',
		'dataIndex' : 'fstate',
		width : 60,
		sortable : true,
		renderer : function(value) {
			var r = DJ.order.Deliver.DeliversList.DELIVERS_STATE;
			var index = Ext.Number.from(value, 0);

			return r[index];
		}
	}, {
		'header' : '配送时间',
		'dataIndex' : 'farrivetime',
		width : 140,
		sortable : true
	}, {
		'header' : '配送地址',
		'dataIndex' : 'faddress',
		width : 300,
		sortable : true
	}, {
		'header' : '联系人',
		'dataIndex' : 'flinkman',
		width : 60,
		sortable : true
	}, {
		'header' : '联系电话',
		'dataIndex' : 'flinkphone',
		sortable : true
	}, {
		'header' : '生成',
		dataIndex : 'fiscreate',
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
		'header' : '类型',
		dataIndex : 'ftype',
		width : 70,
		sortable : true,
		renderer : function(value) {
			return value === '0' ? '正常' : ((value == 1) ? '补单' : '补货');
		}
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
		header : '特性名称',
		dataIndex : 'fcharacter',
		width : 150,
		sortable : true
	}],
	selModel : Ext.create('Ext.selection.CheckboxModel')
})