Ext.require("DJ.tools.fieldRel.MyFieldRelTools");

Ext.require("Ext.ux.form.MyMixedSearchBox");
Ext.require("Ext.ux.form.MyDateTimeSearchBox");

Ext.define('DJ.order.Deliver.ActualDeliverRptDetailList', {
	extend : 'Ext.c.GridPanel',
	
	alias : 'widget.actualdeliverrptdetaillist',
	
	title : "我的收货明细",
	id : 'DJ.order.Deliver.ActualDeliverRptDetailList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'selectMyFoutedDeliverorders.do',
	// Delurl : "DelDeliversList.do",
	Delurl : "",
	// EditUI : "DJ.order.Deliver.DeliversEdit",
	EditUI : "",
	exporturl : "",// 导出为EXCEL方法
	onload : function() {
		var me = this;
		
		
		Ext.getCmp('DJ.order.Deliver.ActualDeliverRptDetailList.querybutton').hide();
		
//		Ext.getCmp('DJ.order.Deliver.ActualDeliverRptDetailList.exportbutton').setVisible(false);
		
//		Ext.getCmp('DJ.order.Deliver.ActualDeliverRptDetailList.exportbutton').hide();
		
			Ext.getCmp('DJ.order.Deliver.ActualDeliverRptDetailList.addbutton').hide();
			Ext.getCmp('DJ.order.Deliver.ActualDeliverRptDetailList.editbutton').hide();
			Ext.getCmp('DJ.order.Deliver.ActualDeliverRptDetailList.delbutton').hide();
			Ext.getCmp('DJ.order.Deliver.ActualDeliverRptDetailList.viewbutton').hide();
			Ext.getCmp('DJ.order.Deliver.ActualDeliverRptDetailList.exportbutton').hide();
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
	
	custbar :[Ext.create("DJ.myComponent.button.ExportExcelButton"),{
	
		xtype : 'mymixedsearchbox',
		condictionFields:['c.fname','cpdt.fname','u1.fname','s.fname','d.fnumber'],
		tip:'可输入:客户名称、包装物名称、制造商名称、收货人、配送单号',
			useDefaultfilter : true,
			width : 200

	
		
	} ,"|"
	,{
//		id : "DJ.order.Deliver.ActualDeliverRpt.dateTimeSearchBoxPanel",
		xtype : "mydatetimesearchbox",
		conditionDateField : "d.fouttime",
		labelFtext : "出库时间",
		useDefaultfilter : true
		
	},{
		text : '逻辑描述',
		height : 30,
		handler : function() {
			Ext.MessageBox
					.alert(
							'描述',
							'<p><h4 align="center">"实际发货情况报表"----已经发货的配送信息</h4></p>');
		}
		},{
		text : '评价',
		height : 30,
		handler:function(){
							var grid = Ext.getCmp('DJ.order.Deliver.ActualDeliverRptDetailList');
							var records = grid.getSelectionModel().getSelection();
							if(records.length!=1){
								Ext.Msg.alert('提示','请选择一条数据进行评价！')
								return;
							}			
						Ext.Ajax.request({
						url : 'getDeliverorderByfidForappraise.do',
						params : {
							fid : records[0].get('fid')
						},
						success : function(res) {
							var obj = Ext.decode(res.responseText);
							if (obj.success) {
							var rinfo=obj.data;
							var win = Ext.create('DJ.order.Deliver.OrderappraiseEdit');
							win.setparent('DJ.order.Deliver.ActualDeliverRptDetailList');
							win.seteditstate('edit');
							Ext.getCmp("DJ.order.Deliver.OrderappraiseEdit.fsupplierId").setReadOnly(true);
							Ext.getCmp("DJ.order.Deliver.OrderappraiseEdit.fdeliverorderid").setReadOnly(true);
							Ext.getCmp("DJ.order.Deliver.OrderappraiseEdit.fcustomerid").setValue( rinfo[0].fcustomerid);
							Ext.getCmp("DJ.order.Deliver.OrderappraiseEdit.fsupplierId").setmyvalue("\"fid\":\""+ rinfo[0].fsupplierid_fid + "\",\"fname\":\""+ rinfo[0].fsupplierid_fname + "\"");
							Ext.getCmp("DJ.order.Deliver.OrderappraiseEdit.fdeliverorderid").setmyvalue("\"fid\":\""+ rinfo[0].fdeliverorderid_fid + "\",\"fnumber\":\""+ rinfo[0].fdeliverorderid_fnumber + "\"");
							
							win.show();
							} else {
								Ext.Msg.alert('提示', obj.msg);
							}
						}
					})
							
						}
	}
	],
	//enableOverflow : true
	
	fields : [{
		name : 'fid'
	}, {
		name : 'faddress',
		myfilterfield : 'd.faddress',
		myfiltername : '配送地址',
		myfilterable : true
	}, {
		name : 'fnumber',
		myfilterfield : 'd.fnumber',
		myfiltername : '申请单号',
		myfilterable : true
	}, {
		name : 'fcustomerid'
	}, {
		name : 'fcreator'
	}, {
		name : 'fcusproductid'
	}, {
		name : 'fsuppliername'
	}, {
		name : 'fcustname'
	},{
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
	}, 
		{
		name : 'farrivetime',
		myfilterfield : 'd.farrivetime',
		myfiltername : '配送时间',
		myfilterable : true
		
//		,
//		type : "date",
//		dateFormat : "Y-m-d H:i"
	}, 
		
//			MyFieldRelTools.buildDateCommonFieldCfg({
//			
//				name : 'farrivetime'
//				
//			})
				{
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
		name : 'fordernumber'
	}, {
		name : 'forderentryid'
	}, {
		name : 'fouted'
	},
	"pfnumber","saleorderNumber","forderunit",'fpcmordernumber'
	],
	columns : {items : [Ext.create('DJ.Base.Grid.GridRowNum',{
		header:'序号'
	}),{
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
	}, 
//		{
//		'header' : '订单编号',
//		'dataIndex' : 'fordernumber',
//		sortable : true
//	},
	{
		'header' : '客户名称',
		'dataIndex' : 'fcustname',
		sortable : true
	}, {
		'header' : '制造商名称',
		'dataIndex' : 'fsuppliername',
		sortable : true
	},{
		'header' : '收货人',
		'dataIndex' : 'fcreator',
		sortable : true
	},{
		'header' : '配送单号',
		'dataIndex' : 'saleorderNumber',
//		width : 100,
		sortable : true
	},{
		'header' : '采购订单号',
		'dataIndex' : 'fpcmordernumber',
//		width : 100,
		sortable : true
	}, {
		'header' : '包装物名称',
		'dataIndex' : 'cutpdtname',
//		width : 125,
		sortable : true
	},  
		{
		'header' : '包装物编号',
		'dataIndex' : 'pfnumber',
//		width : 118,
		sortable : true
	}
		
			,
				{
		'header' : '出库数量',
		'dataIndex' : 'famount',
		sortable : true,
		width : 73
		
	}, 	{
		'header' : '单位',
		'dataIndex' : 'forderunit',
		sortable : true,
		width : 48
	}, {
							
						
		'header' : '出库时间',
		'dataIndex' : 'farrivetime',
//		width : 140,
		sortable : true
//		,
//		xtype : "datecolumn",
//		format : "Y-m-d H:i"
	
	},{
		'header' : '收货地址',
		'dataIndex' : 'faddress',
		width : 200,
		sortable : true
	}, 
		{
		'header' : '备注',
		'dataIndex' : 'fdescription',
//		width : 50,
		sortable : true
		
	}
	],
	defaults: {
		renderer : DJ.tools.fieldRel.MyFieldRelTools.showEmptyWhenNull,
		width : 150
    }
	},
	selModel : Ext.create('Ext.selection.CheckboxModel')
})