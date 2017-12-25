
Ext.define('DJ.traffic.SaleDeliver.TransitDetailsRpt', {
	extend : 'Ext.c.GridPanel',
	title : "物流发货明细表",
	id : 'DJ.traffic.SaleDeliver.TransitDetailsRpt',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetTransitdetails.do',
//	Delurl : "",
//	EditUI : "",
	exporturl : "TransitDetailsRpttoexcel.do",// 导出为EXCEL方法
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		// alert("DeliverList");
		Ext.getCmp("DJ.traffic.SaleDeliver.TransitDetailsRpt.addbutton")
				.setVisible(false);
		Ext.getCmp("DJ.traffic.SaleDeliver.TransitDetailsRpt.editbutton")
				.setVisible(false);
		Ext.getCmp("DJ.traffic.SaleDeliver.TransitDetailsRpt.delbutton")
				.setVisible(false);
		Ext.getCmp("DJ.traffic.SaleDeliver.TransitDetailsRpt.viewbutton")
				.setVisible(false);
//		Ext.getCmp("DJ.traffic.SaleDeliver.TransitDetailsRpt.exportbutton")
//				.setVisible(false);
				
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
	fields : [
			{
				name : "fid"
			}, {
				name : 'cname',
				myfilterfield : 'cname',
				myfiltername : '客户产品名称',
				myfilterable : true
			}, {
				name : 'fname',
				myfilterfield : 'fname',
				myfiltername : '产品名称',
				myfilterable : true
			}, {
				name : 'fouttime',
				myfilterfield : 'fouttime',
				myfiltername : '发货时间',
				myfilterable : true
			}, {
				name : 'trafficunit',
				myfilterfield : 'trafficunit',
				myfiltername : '运输单位',
				myfilterable : true
			 }, {
			   name : 'fspec'
			 }, {
			   name : 'famount'
			}, {
			   name : 'supplier',
				myfilterfield : 'supplier',
				myfiltername : '制造商',
				myfilterable : true
			}],
	columns : [{
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : '制造商',
		'dataIndex' : 'supplier',
			width : 180,
		sortable : true
	}, {
		'header' : '客户产品名称',
		'dataIndex' : 'cname',
			width : 180,
		sortable : true
	}, {
		'header' : '产品名称',
		'dataIndex' : 'fname',
			width : 180,
		sortable : true
	}, {
		'header' : '规格',
		'dataIndex' : 'fspec',
		sortable : true,
		width : 100
	}, {
		'header' : '数量',
		'dataIndex' : 'famount',
		xtype: 'numbercolumn',
		format:'0.00',
		sortable : true
	}, {
		'header' : '运输单位',
		'dataIndex' : 'trafficunit',
		sortable : true
	}, {
		'header' : '发货时间',
		 xtype: 'datecolumn',
		'dataIndex' : 'fouttime',
		sortable : true,
		format:'Y-m-d H:m:i',
		width : 150,
		renderer : function(value) {
					return value;
				}
	}],
	selModel : Ext.create('Ext.selection.CheckboxModel')

});
