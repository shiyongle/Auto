Ext.define('DJ.order.saleOrder.SupplierProductNumsRpt', {
			extend : 'Ext.c.GridPanel',
			title : "制造商生产报表",
			id : 'DJ.order.saleOrder.SupplierProductNumsRpt',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetSupplierProductNumsRpt.do?',
			Delurl : "",
			EditUI : "",
			exporturl:"SupplierProductNumsRpttoexect.do",
			onload : function() {
				Ext.getCmp('DJ.order.saleOrder.SupplierProductNumsRpt.addbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.SupplierProductNumsRpt.editbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.SupplierProductNumsRpt.delbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.SupplierProductNumsRpt.viewbutton').hide();
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
			fields : [{
						name : 'fproductdefid'
					}, {
						name : 'fname',
						myfilterfield : 'fname',
						myfiltername : '产品名称',
						myfilterable : true
					}, {
						name : 'fsupplierid'
					}, {
						name : 'lname',
						myfilterfield : 'lname',
						myfiltername : '供应商名称',
						myfilterable : true
					}, {
						name : 'famounts'
					}, {
						name : 'totalnum'
					}, {
						name : 'rate'
					}],
		columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
						'header' : 'fproductdefid',
						'dataIndex' : 'fproductdefid',
						hidden : true,
						hideable : false,
						sortable : true
					},{
						'header' : 'fsupplierid',
						'dataIndex' : 'fsupplierid',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						'header' : '产品名称',
						'dataIndex' : 'fname',
						width:180,
						sortable : true
					}, {
						'header' : '供应商名称',
						'dataIndex' : 'lname',
						width:100,
						sortable : true
					}, {
						'header' : '生产数量',
						'dataIndex' : 'famounts',
						sortable : true
					}, {
						'header' : '生产总合计',
						'dataIndex' : 'totalnum',
						sortable : true
					},  {
						'header' : '占比',
						'dataIndex' : 'rate',
						sortable : true
					}],
					selModel:Ext.create('Ext.selection.CheckboxModel')
		})