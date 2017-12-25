Ext.apply(Ext.QuickTips.getQuickTip(),{
	dismissDelay : 0
}); 
Ext.require("DJ.tools.fieldRel.MyFieldRelTools");
Ext.require("DJ.tools.common.MyCommonToolsZ");
Ext.require("Ext.ux.form.MyMixedSearchBox");
Ext.require("Ext.ux.form.MyDateTimeSearchBox");
Ext.define('DJ.order.Deliver.OrderAppraiseList', {
	extend : 'Ext.c.GridPanel',
	alias : 'widget.orderappraiselist',	
	title : "订单评价明细表",
	id : 'DJ.order.Deliver.OrderAppraiseList',
	pageSize : 50,
	closable : false,// 是否现实关闭按钮,默认为false
	url : 'GetorderParisesList.do',
	Delurl : "",
	EditUI : "DJ.order.Deliver.OrderappraiseEdit",
	exporturl : "OrderParisesListtoexcel.do",// 导出为EXCEL方法
	onload : function() {
		var me = this;	
		// //同时也刷新图表
		Ext.getCmp("DJ.order.Deliver.OrderAppraiseList.timeboxbeginTime").setValue(new Date(new Date().setDate(1)));
		Ext.getCmp("DJ.order.Deliver.OrderAppraiseList.timeboxendTime").setValue(new Date());
		me.getStore().on('load', function() {	
				var defaultfilter=me.getStore().getDefaultfilter();
				var charstore=me.previousNode("chart").getStore();
				if(!Ext.isEmpty(defaultfilter)&&defaultfilter.length>1){
					var beginTime=defaultfilter[defaultfilter.length-2].value;
					var endTime=defaultfilter[defaultfilter.length-1].value;
					charstore.on('beforeload',function(store,options)
					{
						Ext.apply(store.proxy.extraParams, 
	    				{
	    					conditionDateField:"o.fappraisetime",
	    					beginTime:beginTime,
	    					endTime:endTime
	    				})
					}
					);
				 }
					charstore.load();
				});

	
				MyCommonToolsZ.setComAfterrender(me, function(com) {

					var fieldT = com.down("textfield[id=DJ.order.Deliver.OrderAppraiseList.findBy]");

					MyCommonToolsZ.setQuickTip(fieldT.id, "", "可输入：制造商名称、配送单号");

				});
//			this.down('toolbar')
		    Ext.getCmp('DJ.order.Deliver.OrderAppraiseList.querybutton').hide();
			Ext.getCmp('DJ.order.Deliver.OrderAppraiseList.addbutton').hide();
		//	Ext.getCmp('DJ.order.Deliver.OrderAppraiseList.editbutton').hide();
			Ext.getCmp('DJ.order.Deliver.OrderAppraiseList.delbutton').hide();
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
//		Ext.getCmp("DJ.order.Deliver.OrderappraiseEdit.fsupplierId").setReadOnly(true);
//		Ext.getCmp("DJ.order.Deliver.OrderappraiseEdit.fdeliverorderid").setReadOnly(true);
//		var dd=frames["DJ.order.Deliver.OrderappraiseEdit.appraiseiframe"];
//		dd.setstarts();
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	
	custbar :[{
	
//		xtype : 'mymixedsearchbox',
//		condictionFields:['s.fname','d.fnumber'],//时间只能配置为最后
//		condictionFieldsDateFields:["o.fappraisetime"],
//		tip:'可输入:制造商名称、配送单号',
//		useDefaultfilter : true
//	},{

	xtype : 'textfield',
		itemId : 'textfield',
		width:120,
		id : 'DJ.order.Deliver.OrderAppraiseList.findBy',
		emptyText : '回车搜索',
		enableKeyEvents:true,
		listeners:{
			keypress:function(me,e){
				if(e.getKey()==13){
					this.up('grid').relevanceByName();
					var bt=this.up('grid').down("button[text=查询]");
						var searchbox=this.up('grid').down("mydatetimesearchbox");
						bt.handler.call(bt,searchbox);
				}
			}
		}
	},{
			xtype : "mydatetimesearchbox",
			id:"DJ.order.Deliver.OrderAppraiseList.timebox",
			conditionDateField : "o.fappraisetime",
			labelFtext : "评价时间",
			useDefaultfilter : true,
			additionalCondition:true
			,
			beforeDefaultSearchAction : function (store) {
				this.up('grid').relevanceByName();
				return true;//false阻止执行
			}
	}],
	relevanceByName : function(){
			var store = this.getStore();
			var valString = this.down('toolbar').getComponent('textfield').getValue();
			store.setDefaultfilter([{
				myfilterfield : "o.fordernumber",
				CompareType : " like ",
				type : "string",
				value : valString
			},{
				myfilterfield : "s.fname",
				CompareType : " like ",
				type : "string",
				value : valString
			}                    
			]);
			store.setDefaultmaskstring("#0 or #1 ");
	},
	
	fields : [{
		name : 'fid'
	}, {
		name : 'fcustomername'
	}, {
		name : 'fsuppliername'
//		myfilterfield : 'd.fnumber',
//		myfiltername : '申请单号',
//		myfilterable : true
	}, {
		name : 'delivernumber'
	}, {
		name : 'fdeliverappraise'
	}, {
		name : 'fqualityappraise'
	}, {
		name : 'fserviceappraise'
	},{
		name : 'fmultipleappraise'
	}, {
		name : 'fdescription'
	}, {
		name : 'fparisename'
	}, {
		name : 'fappraisetime'
	}, {
		name : 'fupdatetime'
	},{
		name:'fboxtype'
	}
	],
	columns : {items : [{
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true
	},
	{
		'header' : '客户名称',
		'dataIndex' : 'fcustomername',
		sortable : true
	}, {
		'header' : '制造商名称',
		'dataIndex' : 'fsuppliername',
		sortable : true,
		width : 100
	}, {
		'header' : '订单类型',
		'dataIndex' : 'fboxtype',
		sortable : true,
		renderer : function(value) {
							if (value == 1) {
								return '纸板订单';
							}else {
								return '纸箱订单';
							}
		}
	},{
		'header' : '配送单号',
		'dataIndex' : 'delivernumber',
//		width : 100,
		sortable : true
	},{
		'header' : '交期满意度',
		'dataIndex' : 'fdeliverappraise',
		width : 70,
		sortable : true
	}, {
		'header' : '质量满意度',
		'dataIndex' : 'fqualityappraise',
		width : 70,
		sortable : true
	},  
		{
		'header' : '服务满意度',
		'dataIndex' : 'fserviceappraise',
		width : 70,
		sortable : true
	},
	{
		'header' : '数量准确度',
		'dataIndex' : 'fmultipleappraise',
		width : 70,
		sortable : true
	}, 	{
		'header' : '详细描述',
		'dataIndex' : 'fdescription',
		sortable : true
		//width : 48
	}, 	{
		'header' : '评价人',
		'dataIndex' : 'fparisename',
		sortable : true,
		width : 100
	}, {
										
		'header' : '评价时间',
		'dataIndex' : 'fappraisetime',
//		width : 140,
		sortable : true,
		format : "Y-m-d H:i"
	}, {
		'header' : '修改时间',
		'dataIndex' : 'fupdatetime',
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