var state = "全部", filter = "", datetype = "", start = "", end = "";
Ext.require("DJ.tools.common.MyCommonToolsZ");
Ext.define('DJ.order.saleOrder.OrderSchemeDetailList', {
	extend : 'Ext.c.GridPanel',
	title : "方案下单明细表",
	id : 'DJ.order.saleOrder.OrderSchemeDetailList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetOrderSchemeDetailList.do?',
	Delurl : "",
	EditUI : "",
	exporturl : "OrderSchemeDetailListRpttoexect.do",
	onload : function() {
		var me = this;
		MyCommonToolsZ.setComAfterrender(me, function(com) {
			var fieldT = com
					.down("textfield[id=DJ.order.saleOrder.OrderSchemeDetailList.search]");
			MyCommonToolsZ.setQuickTip(fieldT.id, "",
					"可输入:客户名称、制造商、需求名称、方案名称、设计师");

		});
		MyCommonToolsZ.setComAfterrender(me, function(com) {
			var fieldT = com
					.down("textfield[id=DJ.order.saleOrder.OrderSchemeDetailList.combo2]");
			MyCommonToolsZ.setQuickTip(fieldT.id, "", "时间前包括后不包括");

		});
		Ext.getCmp('DJ.order.saleOrder.OrderSchemeDetailList.addbutton').hide();
		Ext.getCmp('DJ.order.saleOrder.OrderSchemeDetailList.editbutton')
				.hide();
		Ext.getCmp('DJ.order.saleOrder.OrderSchemeDetailList.delbutton').hide();
		Ext.getCmp('DJ.order.saleOrder.OrderSchemeDetailList.viewbutton')
				.hide();
		Ext.getCmp('DJ.order.saleOrder.OrderSchemeDetailList.querybutton')
				.hide();
		Ext.getCmp('DJ.order.saleOrder.OrderSchemeDetailList.combo1')
				.setValue("全部");
		start = Ext.Date
				.format(
						new Date(Ext
								.getCmp('DJ.order.saleOrder.OrderSchemeDetailList.startDate').value),
						'Y-m-d');
		end = Ext.Date
				.format(
						new Date(Ext
								.getCmp('DJ.order.saleOrder.OrderSchemeDetailList.endDate').value),
						'Y-m-d');
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
	},
	Action_BeforeEditButtonClick : function(EditUI) {
	},
	Action_AfterEditButtonClick : function(EditUI) {
	},
	Action_BeforeDelButtonClick : function(me, record) {
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	custbar : [{
		xtype : 'combo',
		id : 'DJ.order.saleOrder.OrderSchemeDetailList.combo1',
		store : [['未确认', '未确认'], ['已确认未下单', '已确认未下单'], ['已确认已下单', '已确认已下单'],
				['全部', '全部']],

		listeners : {
			select : function() {
				state = this.value;
				this.up('grid').relevanceByName();
			}
		}
	}, '|', {
		xtype : 'textfield',
		itemId : 'textfield',
		width : 120,
		id : 'findBy',
		emptyText : '回车搜索',
		enableKeyEvents : true,
		id : 'DJ.order.saleOrder.OrderSchemeDetailList.search',
		listeners : {
			keypress : function(me, e) {
				if (e.getKey() == 13) {
					filter = this.value;
					this.up('grid').relevanceByName();
				}
			}
		}
	}, '|', {
		xtype : 'combo',
		id : 'DJ.order.saleOrder.OrderSchemeDetailList.combo2',
		store : [['fauditortime', '需求发布时间'], ['freceivetime', '需求接受时间'],
				['fcreatetime', '方案创建时间'], ['fconfirmtime', '方案确定时间'],
				['', '不过滤']],
		emptyText : '选择过滤时间...',
		listeners : {
			select : function() {
				datetype = this.value;
			}
		}
	}, {
		xtype : 'datefield',
		id : 'DJ.order.saleOrder.OrderSchemeDetailList.startDate',
		format : "Y-m-d",
		labelWidth : 15,
		fieldLabel : "从",
		value : new Date()
	}, {
		xtype : 'datefield',
		id : 'DJ.order.saleOrder.OrderSchemeDetailList.endDate',
		format : "Y-m-d",
		labelWidth : 15,
		fieldLabel : "到",
		value : new Date()
	}, {
		xtype : 'button',
		text : "搜索",
		handler : function() {
			start = Ext.Date
					.format(
							new Date(Ext
									.getCmp('DJ.order.saleOrder.OrderSchemeDetailList.startDate').value),
							'Y-m-d');
			end = Ext.Date
					.format(
							new Date(Ext
									.getCmp('DJ.order.saleOrder.OrderSchemeDetailList.endDate').value),
							'Y-m-d');
			// if (datetype == "") {
			// Ext.Msg.alert("警告", "未选择时间过滤方式！")
			// return;
			// }
			if (start > end) {
				Ext.Msg.alert("警告", "起始日期不能超过结束日期！")
				return;
			}
			this.up('grid').relevanceByName();
		}
	}],
	relevanceByName : function() {
		console.log(datetype);
		var store = this.getStore();
		if (filter == "" || typeof(filter) == "undefined") {
			if (state == "全部"
					&& (datetype == "" || typeof(datetype) == "undefined")) {
				store.setDefaultfilter();
				store.setDefaultmaskstring("");
				store.loadPage(1);
			} else {
				if (state == "全部"
						&& (datetype != "" || typeof(datetype) != "undefined")) {
					store.setDefaultfilter([{
								myfilterfield : datetype,
								CompareType : " >= ",
								type : "string",
								value : start
							}, {
								myfilterfield : datetype,
								CompareType : " < ",
								type : "string",
								value : end
							}]);
					store.setDefaultmaskstring("#0 and #1");
					store.loadPage(1);
					return;
				}
				if (state != "全部"
						&& (datetype == "" || typeof(datetype) == "undefined")) {
					store.setDefaultfilter([{
								myfilterfield : "sstate",
								CompareType : " = ",
								type : "string",
								value : state
							}]);
					store.setDefaultmaskstring("#0");
					store.loadPage(1);
					return;
				}
				store.setDefaultfilter([{
							myfilterfield : datetype,
							CompareType : " >= ",
							type : "string",
							value : start
						}, {
							myfilterfield : datetype,
							CompareType : " < ",
							type : "string",
							value : end
						}, {
							myfilterfield : "sstate",
							CompareType : " = ",
							type : "string",
							value : state
						}]);
				store.setDefaultmaskstring("#0 and #1 and #2");
				store.loadPage(1);
			}
		} else {
			if (state == "全部"
					&& (datetype == "" || typeof(datetype) == "undefined")) {
				store.setDefaultfilter([{
							myfilterfield : "cname",
							CompareType : " like ",
							type : "string",
							value : filter
						}, {
							myfilterfield : "fsupplier",
							CompareType : " like ",
							type : "string",
							value : filter
						}, {
							myfilterfield : "fname",
							CompareType : " like ",
							type : "string",
							value : filter
						}, {
							myfilterfield : "sname",
							CompareType : " like ",
							type : "string",
							value : filter
						}, {
							myfilterfield : "flinkman",
							CompareType : " like ",
							type : "string",
							value : filter
						}

				]);
				store.setDefaultmaskstring("#0 or #1 or #2 or #3 or #4");
				store.loadPage(1);
			} else {
				if (state == "全部"
						&& (datetype != "" || typeof(datetype) != "undefined")) {
					store.setDefaultfilter([{
								myfilterfield : "cname",
								CompareType : " like ",
								type : "string",
								value : filter
							}, {
								myfilterfield : "fsupplier",
								CompareType : " like ",
								type : "string",
								value : filter
							}, {
								myfilterfield : "fname",
								CompareType : " like ",
								type : "string",
								value : filter
							}, {
								myfilterfield : "sname",
								CompareType : " like ",
								type : "string",
								value : filter
							}, {
								myfilterfield : "flinkman",
								CompareType : " like ",
								type : "string",
								value : filter
							}, {
								myfilterfield : datetype,
								CompareType : " >= ",
								type : "string",
								value : start
							}, {
								myfilterfield : datetype,
								CompareType : " < ",
								type : "string",
								value : end
							}]);
					store
							.setDefaultmaskstring("(#0 or #1 or #2 or #3 or #4) and #5 and #6");
					store.loadPage(1);
					return;
				}

				if (state != "全部"
						&& (datetype == "" || typeof(datetype) == "undefined")) {
					store.setDefaultfilter([{
								myfilterfield : "cname",
								CompareType : " like ",
								type : "string",
								value : filter
							}, {
								myfilterfield : "fsupplier",
								CompareType : " like ",
								type : "string",
								value : filter
							}, {
								myfilterfield : "fname",
								CompareType : " like ",
								type : "string",
								value : filter
							}, {
								myfilterfield : "sname",
								CompareType : " like ",
								type : "string",
								value : filter
							}, {
								myfilterfield : "flinkman",
								CompareType : " like ",
								type : "string",
								value : filter
							}, {
								myfilterfield : "sstate",
								CompareType : " = ",
								type : "string",
								value : state
							}

					]);
					store
							.setDefaultmaskstring("(#0 or #1 or #2 or #3 or #4) and #5");
					store.loadPage(1);
					return;
				}

				store.setDefaultfilter([{
							myfilterfield : "cname",
							CompareType : " like ",
							type : "string",
							value : filter
						}, {
							myfilterfield : "fsupplier",
							CompareType : " like ",
							type : "string",
							value : filter
						}, {
							myfilterfield : "fname",
							CompareType : " like ",
							type : "string",
							value : filter
						}, {
							myfilterfield : "sname",
							CompareType : " like ",
							type : "string",
							value : filter
						}, {
							myfilterfield : "flinkman",
							CompareType : " like ",
							type : "string",
							value : filter
						}, {
							myfilterfield : datetype,
							CompareType : " >= ",
							type : "string",
							value : start
						}, {
							myfilterfield : datetype,
							CompareType : " < ",
							type : "string",
							value : end
						}, {
							myfilterfield : "sstate",
							CompareType : " = ",
							type : "string",
							value : state
						}

				]);
				store
						.setDefaultmaskstring("(#0 or #1 or #2 or #3 or #4) and #5 and #6 and #7");
				store.loadPage(1);
			}
		}
	},
	fields : [{
				name : 'cname'
			}, {
				name : 'fname'
			}, {
				name : 'fauditortime'
			}, {
				name : 'freceivetime'
			}, {
				name : 'sname'
			}, {
				name : 'fcreatetime'
			}, {
				name : 'fconfirmtime'
			}, {
				name : 'sstate'
			}, {
				name : 'fsupplier'
			}, {
				name : 'flinkman'
			}, {
				name : 'flinkphone'
			}],
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
				'header' : '客户名称',
				'dataIndex' : 'cname',
				width : 180,
				sortable : true
			}, {
				'header' : '需求名称',
				'dataIndex' : 'fname',
				width : 100,
				sortable : true
			}, {
				'header' : '需求发布时间',
				'dataIndex' : 'fauditortime',
//				format : "Y-m-d H:i",
//				xtype : "datecolumn",
				renderer:function(val){
					return val.substring(0,16);
				},
				width : 120,
				sortable : true
			}, {
				'header' : '需求接收时间',
				'dataIndex' : 'freceivetime',
//				format : "Y-m-d H:i",
//				xtype : "datecolumn",
				renderer:function(val){
					return val.substring(0,16);
				},
				width : 120,
				sortable : true
			}, {
				'header' : '方案名称',
				'dataIndex' : 'sname',
				sortable : true
			}, {
				'header' : '方案创建时间',
				'dataIndex' : 'fcreatetime',
//				format : "Y-m-d H:i",
//				xtype : "datecolumn",
				renderer:function(val){
					return val.substring(0,16);
				},
				width : 120,
				sortable : true
			}, {
				'header' : '方案确定时间',
				'dataIndex' : 'fconfirmtime',
//				format : "Y-m-d H:i",
//				xtype : "datecolumn",
				renderer:function(val){
					return val.substring(0,16);
				},
				width : 120,
				sortable : true
			}, {
				'header' : '方案状态',
				'dataIndex' : 'sstate',
				sortable : true
			}, {
				'header' : '制造商',
				'dataIndex' : 'fsupplier',
				sortable : true
			}, {
				'header' : '设计师',
				'dataIndex' : 'flinkman',
				sortable : true
			}, {
				'header' : '设计师电话',
				'dataIndex' : 'flinkphone',
				sortable : true
			}],
	selModel : Ext.create('Ext.selection.CheckboxModel')
})