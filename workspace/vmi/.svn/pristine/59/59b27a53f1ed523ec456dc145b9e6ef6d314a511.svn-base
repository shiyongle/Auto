//Ext.apply(Ext.QuickTips.getQuickTip(),{
//	dismissDelay : 0
//}); 

Ext.require("DJ.tools.common.MyCommonToolsZ");
Ext.require("Ext.ux.form.MySearchBox");
Ext.require("Ext.ux.form.MyMixedSearchBox");
Ext.require("Ext.ux.form.MyDateTimeSearchBox");
Ext.require(["DJ.tools.fieldRel.MyFieldRelTools",
		"DJ.myComponent.button.UpLoadDeliverApplyWinButton",
		"Ext.ux.grid.MySimpleGridContextMenu"]);

Ext.define('DJ.order.Deliver.DemandRespondDetailsRpt', {
			extend : 'Ext.c.GridPanel',
			alias : 'widget.demandresponddetailsrpt',
			title : "需求响应明细表",
			id : 'DJ.order.Deliver.DemandRespondDetailsRpt',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'getVmiResponseDetails.do',
			Delurl : "",
			EditUI : "",
			exporturl:"vmiResponseDetailstoexcel.do",//导出为EXCEL方法
			onload : function() {
				
				var me = this;
				MyGridTools.rebuildExcelAction(me); 
				//导出时配合金众前台传入的columns数据.header-checkbox和row-numberer不需要传入
				me.getStore().on('load',function(){
					me.columns[0].dataIndex = '';
					me.columns[1].dataIndex = '';
				})
				
				MyCommonToolsZ.setComAfterrender(me, function(com) {

					var fieldT = com.down("textfield[id=findBy]");

					MyCommonToolsZ.setQuickTip(fieldT.id, "", "可输入:客户名称、需求名称、方案名称、制造商、接收人、方案设计师");

				});
				Ext.getCmp('DJ.order.Deliver.DemandRespondDetailsRpt.addbutton').hide();
				Ext.getCmp('DJ.order.Deliver.DemandRespondDetailsRpt.editbutton').hide();
				Ext.getCmp('DJ.order.Deliver.DemandRespondDetailsRpt.delbutton').hide();
				Ext.getCmp('DJ.order.Deliver.DemandRespondDetailsRpt.viewbutton').hide();
				Ext.getCmp('DJ.order.Deliver.DemandRespondDetailsRpt.querybutton').hide();
//				Ext.getCmp('DJ.order.Deliver.DemandRespondDetailsRpt.exportbutton').hide();
				this.down('toolbar').getComponent('statecombo').setValue('');
				this.down('toolbar').getComponent('timecombo').setValue('t1.fauditortime');
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
			}
			,
			custbar : [
//				Ext.create("DJ.myComponent.button.MyExcelExporterCusCfgButton"),
				{
				xtype:'combo',
				itemId:'statecombo',
				queryMode:'local',
				store:[["","全部"],["已分配","已分配"],["已接收","已接收"],["已设计","已设计"],["已完成","已完成"],["已关闭","已关闭"]],
				listeners:{
				select:function(){
						var bt=this.up('grid').down("button[text=查询]");
						var searchbox=this.up('grid').down("mydatetimesearchbox");
						bt.handler.call(bt,searchbox);
				}
				}},"|",
			{
				
		xtype : 'textfield',
		itemId : 'textfield',
		width:120,
		id : 'findBy',
		emptyText : '回车搜索',
		enableKeyEvents:true,
		listeners:{
			keypress:function(me,e){
				if(e.getKey()==13){
						var bt=this.up('grid').down("button[text=查询]");
						var searchbox=this.up('grid').down("mydatetimesearchbox");
//						searchbox.conditionDateField=valCTime;
						bt.handler.call(bt,searchbox);
				}
			}
		}},"|",{
				xtype:'combo',
				itemId:'timecombo',
				queryMode:'local',
				store:[["t1.fauditortime","需求发布时间"],["t1.fallottime","需求分配时间"],["t1.freceivetime","需求接收时间"],["t2.fcreatetime","方案创建时间"],["t2.fconfirmtime","方案确认时间"]],
				listeners:{
				select:function(){
					var searchbox=this.up('toolbar').down("mydatetimesearchbox");
					searchbox.conditionDateField=this.getValue();
				}
				}},	
			{
			id:'DJ.order.Deliver.DemandRespondDetailsRpt.boxmydate',
			xtype : "mydatetimesearchbox",
			conditionDateField : "t1.fauditortime",
			labelFtext : "",
			useDefaultfilter : true,
			additionalCondition:true,
			beforeDefaultSearchAction : function (store) {
				
				this.up('grid').relevanceByName();
				return true;//false阻止执行
			}
			}
			],
			relevanceByName : function(){
			var store = this.getStore();
			var valText = this.down('toolbar').getComponent('textfield').getValue();
			var valCombo = this.down('toolbar').getComponent('statecombo').getValue();
			var valCTime = this.down('toolbar').getComponent('timecombo').getValue();
				var myfilter = [{
				myfilterfield : "t1.fstate",
				CompareType : " like ",
				type : "string",
				value : valCombo
			},{
				myfilterfield : "cus.fname",
				CompareType : " like ",
				type : "string",
				value : valText
			},{
				myfilterfield : "t1.fname",
				CompareType : " like ",
				type : "string",
				value : valText
			},{
				myfilterfield : "t2.fname",
				CompareType : " like ",
				type : "string",
				value : valText
			},{
				myfilterfield : "s.fname",
				CompareType : " like ",
				type : "string",
				value : valText
				},{
				myfilterfield : "u.fname",
				CompareType : " like ",
				type : "string",
				value : valText
				},{
				myfilterfield : "u2.fname",
				CompareType : " like ",
				type : "string",
				value : valText
			}
                       
			];
			var markstring="#0 and (#1 or #2 or #3 or #4 or #5 or #6) ";
			store.setDefaultfilter(myfilter);
			store.setDefaultmaskstring(markstring);
			}
			,
			fields : [{
						name : 'fid'
					}, {
						name : 'customername'
					}, {
						name : 'demandname'
					}, {
						name : 'fauditortime'
					}, {
						name : 'fallottime'
					}, {
						name : 'freceivetime'
					}, {
						name : 'fsuppliername'
					}, {
						name : 'freceiver'
					}, {
						name : 'schemename'
					}, {
						name : 'fcreatetime'
					}, {
						name : 'fconfirmtime'
					}, {
						name : 'vmitime'
					}, {
						name : 'suppliertime'
					}, {
						name : 'designtime'
					}, {
						name : 'confirmonlinetime'
					}, {
						name : 'planepic'
					}, {
						name : 'strcutpic'
					}, {
						name : 'schemetorname'
					}
					],
			columns : {items:[Ext.create('DJ.Base.Grid.GridRowNum',{
						header:'序号'
					}),{
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					}, 
						{
						'header' : '客户名称',
						width : 140,
						'dataIndex' : 'customername',
						sortable : true
					},{
						'header' : '需求名称',
						width : 140,
						'dataIndex' : 'demandname',
						sortable : true
					},{
						'header' : '发布时间',
						width : 125,
						'dataIndex' : 'fauditortime',
						sortable : true
					},{
						'header' : '分配时间',
						width : 125,
						'dataIndex' : 'fallottime',
						sortable : true
						
					},{
						'header' : '接收时间',
						width : 125,
						'dataIndex' : 'freceivetime',
						sortable : true
					},{
						'header' : '制造商',
						'dataIndex' : 'fsuppliername',
						sortable : true
					},{
						'header' : '接收人',
						'dataIndex' : 'freceiver',
						sortable : true
					},{
						'header' : '方案名称',
						'dataIndex' : 'schemename',
						width : 140,
						sortable : true
					},{
						'header' : '方案创建时间',
						'dataIndex' : 'fcreatetime',
						width : 125,
						sortable : true
					},{
						'header' : '方案确认时间',
						'dataIndex' : 'fconfirmtime',
						width : 125,
						sortable : true
					},{
						'header' : '平台响应时间',
						'dataIndex' : 'vmitime',
						sortable : true
					},  {
						'header' : '制造商响应时间',
						'dataIndex' : 'suppliertime',
						sortable : true
					}, {
						'header' : '在线设计时间',
						'dataIndex' : 'designtime',
						sortable : true
					},{
						'header' : '在线确认时间',
						'dataIndex' : 'confirmonlinetime',
						sortable : true
					},{
						'header' : '结构图纸数量',
						'dataIndex' : 'strcutpic',
//						width : 140,
						sortable : true
					}, {
						'header' : '版面图纸数量',
						'dataIndex' : 'planepic',
						sortable : true
					}, {
						'header' : '方案设计师',
						'dataIndex' : 'schemetorname',
						sortable : true
					}
					
					],

	defaults: {
		renderer : DJ.tools.fieldRel.MyFieldRelTools.showEmptyWhenNullNoZERO
    }
					},
					selModel:Ext.create('Ext.selection.CheckboxModel')
		})
		
		


		