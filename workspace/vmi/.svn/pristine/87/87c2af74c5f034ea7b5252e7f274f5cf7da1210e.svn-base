Ext.require("Ext.ux.form.MyDateTimeSearchBox");
Ext.define('DJ.order.saleOrder.OrdersList', {
			extend : 'Ext.c.GridPanel',
			title : "制造商接单明细表",
			id : 'DJ.order.saleOrder.OrdersList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetOrderProductTable.do',
			Delurl : "",
			EditUI : "",
			exporturl:"ExcelOrderProductTable.do",
		    features: [{
		        ftype: 'summary'
		    }],
			onload : function() {
				var me = this;
				Ext.getCmp('DJ.order.saleOrder.OrdersList.addbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.OrdersList.editbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.OrdersList.delbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.OrdersList.viewbutton').hide();

				Ext.getCmp('DJ.order.saleOrder.OrdersList.exportbutton').hide();
				
				MyGridTools.rebuildExcelAction(me); 
				me.getStore().on('load',function(){
					me.columns[0].dataIndex = '';
					me.columns[1].dataIndex = '';
				})
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
			custbar : [Ext.create("DJ.myComponent.button.ExportExcelButton"),"|",{
				xtype : "mydatetimesearchbox",
				conditionDateField : "d.fcreatetime",
				labelFtext : "创建时间"
			}],
			fields : [ {
						name : 'fnumber'
					}, {
						name : 'fname'
					}, {
						name : 'fcount'
					}, {
						name : 'fsum',
						type : 'int'
					}, {
						name : 'faffirmtime'
						},'fcreatetime'],
		columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
						'header' : '编号',
						'dataIndex' : 'fnumber'
					}, {
						'header' : '制造商名称',
						'dataIndex' : 'fname',
						width:180,
						sortable : true
					}, {
						'header' : '接受订单单数',
						'dataIndex' : 'fcount',
						sortable : true
					}, {
						'header' : '接收订单数量',
						'dataIndex' : 'fsum',
						sortable : true,
						summaryType : 'sum',
						summaryRenderer : function(value){
							return '本页总计:' + value; 
						}
					}],
					selModel:Ext.create('Ext.selection.CheckboxModel')
		})