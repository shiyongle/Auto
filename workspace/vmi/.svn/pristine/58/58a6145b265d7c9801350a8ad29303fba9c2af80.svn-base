Ext.define('DJ.ppl.VmiPlanListView', {
	extend : 'Ext.c.GridPanel',
	title : "VMI计划管理",
	id : 'DJ.ppl.VmiPlanListView',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetVmipdtParamList.do',
	Delurl : "",
	EditUI : "",
	exporturl : "vmitoexcel.do",
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
		// alert("新增前");
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
		// alert("新增后");
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
		// 可对编辑界面进行复制
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	custbar : [{
		// id : 'DelButton',
		text : 'VMI自动下单',
		height : 30,
		handler : function() {
			var me=Ext.getCmp("DJ.ppl.VmiPlanListView");
			var el = me.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "vmiplan.do",
				params : {
				// fid : fid,
				// fnumber : record[0].get("fnumber"),
				// fcusproductid : record[0].get("fcusproductid"),
				// flinkman : record[0].get("flinkman"),
				// flinkphone : record[0].get("flinkphone"),
				// famount : record[0].get("famount"),
				// farrivetime : record[0].get("farrivetime"),
				// faddress : record[0].get("faddress"),
				// fcustomerid : record[0].get("fcustomerid")
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						//Ext.MessageBox.alert('成功', obj.msg);
						djsuccessmsg( obj.msg);

						Ext.getCmp("DJ.ppl.VmiPlanListView").store.load();
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
		}
	},{
		// id : 'DelButton',
		text : '模拟VMI下单',
		height : 30,
		handler : function() {
			var me=Ext.getCmp("DJ.ppl.VmiPlanListView");
			var el = me.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "prevmiplan.do",
				params : {
				// fid : fid,
				// fnumber : record[0].get("fnumber"),
				// fcusproductid : record[0].get("fcusproductid"),
				// flinkman : record[0].get("flinkman"),
				// flinkphone : record[0].get("flinkphone"),
				// famount : record[0].get("famount"),
				// farrivetime : record[0].get("farrivetime"),
				// faddress : record[0].get("faddress"),
				// fcustomerid : record[0].get("fcustomerid")
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						//Ext.MessageBox.alert('成功', obj.msg);
						djsuccessmsg( obj.msg);

						Ext.getCmp("DJ.ppl.VmiPlanListView").store.load();
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
		name : 'fcreatorid'
	}, {
		name : 'fcreator'
	}, {
		name : 'fcreatetime'
	}, {
		name : 'flastupdateuserid'
	}, {
		name : 'flastupdater'
	}, {
		name : 'flastupdatetime'
	}, {
		name : 'fcustomerid'
	}, {
		name : 'fcustname',
		myfilterfield : 'c.fname',
		myfiltername : '客户名称',
		myfilterable : true
	}, {
		name : 'fproductid'
	}, {
		name : 'fpdtname',
		myfilterfield : 'd.fname',
		myfiltername : '产品名称',
		myfilterable : true
	}, {
		name : 'fmaxstock'
	}, {
		name : 'forderamount'
	}, {
		name : 'fminstock'
	}, {
		name : 'fbalanceqty'
	}, {
		name : 'fproducedqty'
	}, {
		name : 'fdescription',
		myfilterfield : 'v.fdescription',
		myfiltername : '备注',
		myfilterable : true
		
	}, {
		name : 'fcontrolunitid'
	}, {name : 'fsupplier',
		myfilterfield : 'sp.fname',
		myfiltername : '制造商',
		myfilterable : true
	}, {
		name : 'fsupplierId'
	}, {
		name : 'ftype'
	}],
	columns : [{
		'dataIndex' : 'fid',
		'header' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'dataIndex' : 'fpdtname',
		'header' : '产品名称',
		width:300,
		sortable : true
	}, {
		'dataIndex' : 'fsupplier',
		'header' : '制造商',
		sortable : true
	}, {
		'dataIndex' : 'fsupplierId',
		'header' : '制造商',
		hidden : true,
		sortable : true
	}, {
		'dataIndex' : 'fdescription',
		width:600,
		'header' : '备注',
		sortable : true
	}, {
		'dataIndex' : 'fmaxstock',
		'header' : '最大库存量',
		sortable : true
	}, {
		'dataIndex' : 'forderamount',
		'header' : '下单批量',
		sortable : true
	}, {
		'dataIndex' : 'fminstock',
		'header' : '最小库存量',
		sortable : true
	}, {
		'dataIndex' : 'ftype',
		'header' : '下单类型',
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '订单';
			} else {
				return '通知';
			}
		}
	}, {
		'dataIndex' : 'fbalanceqty',
		'header' : '库存数量',
		hidden : true,
		sortable : true
	}, {
		'dataIndex' : 'fproducedqty',
		'header' : '生产数量',
		hidden : true,
		sortable : true
	}, {
		'dataIndex' : 'fcreatorid',
		'header' : 'fcreatorid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'dataIndex' : 'fcreator',
		'header' : '创建人',
		sortable : true
	}, {
		'dataIndex' : 'fcreatetime',
		'header' : '创建时间',
		width : 200,
		sortable : true
	}, {
		'dataIndex' : 'flastupdateuserid',
		'header' : 'flastupdateuserid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'dataIndex' : 'flastupdater',
		'header' : '最后修改人',
		sortable : true
	}, {
		'dataIndex' : 'flastupdatetime',
		'header' : '最后修改时间',
		width : 200,
		sortable : true
	}, {
		'dataIndex' : 'fcustomerid',
		'header' : 'fcustomerid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'dataIndex' : 'fcustname',
		'header' : '客户名称',
		sortable : true
	}, {
		'dataIndex' : 'fcontrolunitid',
		'header' : '描述',
		width : 200,
		sortable : true
	}, {
		'dataIndex' : 'fproductid',
		'header' : 'fproductid',
		hidden : true,
		hideable : false,
		sortable : true
//	}, {
//		'dataIndex' : 'fcontrolunitid',
//		'header' : 'fcontrolunitid',
//		hidden : true,
//		hideable : false,
//		sortable : true
	}]
});

Ext.define('DJ.ppl.VmiPlanList',{
	extend:'DJ.System.BindCustTreePanel',
	id:'DJ.ppl.VmiPlanList',
	title:'VMI计划管理',
	mainView:'DJ.ppl.VmiPlanListView'
});