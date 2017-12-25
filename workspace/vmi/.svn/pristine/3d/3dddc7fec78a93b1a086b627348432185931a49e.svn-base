
Ext.define('DJ.order.Deliver.TraitidMainList.DeliversOrderList', {
	extend : 'Ext.c.GridPanel',
	title : "要货管理",
	id : 'DJ.order.Deliver.TraitidMainList.DeliversOrderList',
	pageSize : 50,
//	closable : true,// 是否现实关闭按钮,默认为false
	url:'GetDeliversList.do',
	Delurl : "",
	EditUI : "",
	exporturl : "",// 导出为EXCEL方法
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		Ext.getCmp('DJ.order.Deliver.TraitidMainList.DeliversOrderList.viewbutton')
				.setVisible(false);
//		Ext.getCmp('DJ.order.Deliver.TraitidMainList.DeliversOrderList.refreshbutton')
//				.setVisible(false);
		Ext.getCmp('DJ.order.Deliver.TraitidMainList.DeliversOrderList.querybutton')
				.setVisible(false);
		Ext.getCmp('DJ.order.Deliver.TraitidMainList.DeliversOrderList.addbutton')
				.setVisible(false);
		Ext.getCmp('DJ.order.Deliver.TraitidMainList.DeliversOrderList.editbutton')
				.setVisible(false);
		Ext.getCmp('DJ.order.Deliver.TraitidMainList.DeliversOrderList.delbutton')
				.setVisible(false);
		Ext.getCmp('DJ.order.Deliver.TraitidMainList.DeliversOrderList.exportbutton')
				.setVisible(false);
		var instore = this.store;
		var myfilter = [];
		myfilter.push({
			myfilterfield : "d.ftraitid",
			CompareType : " = ",
			type : "string",
			value : '-1'
		});
		instore.setDefaultfilter(myfilter);
		instore.setDefaultmaskstring(" #0 ");
		instore.load();
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
	},
	Action_BeforeEditButtonClick : function(EditUI) {
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
	},
	Action_BeforeDelButtonClick : function(me, record) {
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	fields : [{
		name : 'fid'
	}, {
		name : 'fcustname',
		myfilterfield : 'c.fname',
		myfiltername : '客户名称',
		myfilterable : true
	}, {
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
	{
		name : 'falloted',
		myfilterfield : 'd.falloted',
		myfiltername : '是否分配',
		myfilterable : true
	},{
		name : 'ftype'
	},{
		name : 'fcharacter'
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
	},{
		'header' : '要货申请编码',
		dataIndex : 'fapplynumber',
		width : 90,
		sortable : true
	}, {
		'header' : '要货管理编码',
		'dataIndex' : 'fnumber',
		sortable : true
	}, {
		'header' : '订单编号',
		'dataIndex' : 'fordernumber',
		sortable : true
	}, {
		'header' : '客户名称',
		'dataIndex' : 'fcustname',
		sortable : true
	}, {
		'header' : '客户产品',
		width : 200,
		'dataIndex' : 'cutpdtname',
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
	}
	],
	selModel : Ext.create('Ext.selection.CheckboxModel')
})

Ext.define('DJ.order.Deliver.TraitidMainList', {
	extend : 'Ext.panel.Panel',
	id : 'DJ.order.Deliver.TraitidMainList',
	autoScroll : true,
	border : false,
	layout : 'border',
	title : "一次性配送信息",
	closable:true,
	width:1000,
	height:500,
	items:
			[{
				region : 'center',
				items :[
				Ext.create("DJ.order.Deliver.TraitidAllotList")
				],
				layout:'fit'
			},
			{
				region : 'south',
				split : true,
				width : 220,
				height:250,
				layout:'fit',
				items : [ 
				Ext.create("DJ.order.Deliver.TraitidMainList.DeliversOrderList")
				]
			}]
	       
});

