Ext.define('DJ.order.saleOrder.MoreSupplierDeliverorderNumsRpt', {
			extend : 'Ext.c.GridPanel',
			title : "多家供应配送发货报表",
			id : 'DJ.order.saleOrder.MoreSupplierDeliverorderNumsRpt',
			pageSize : 100,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetMoreSupplierDeliverorderNumsRpt.do?',
			Delurl : "",
			EditUI : "",
			exporturl:"MoreSupplierDeliverorderNumsRpttoexect.do",
			onload : function() {
				Ext.getCmp('DJ.order.saleOrder.MoreSupplierDeliverorderNumsRpt.addbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.MoreSupplierDeliverorderNumsRpt.editbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.MoreSupplierDeliverorderNumsRpt.delbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.MoreSupplierDeliverorderNumsRpt.viewbutton').hide();
				var grid = this;
				grid.store.on('beforeload',function( store, operation, eOpts ){
				store.getProxy().timeout=600000;
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
			custbar : [ {
						text : '逻辑描述',
						height : 30,
						handler : function()
						{
							Ext.MessageBox.alert('描述', '<p><h4 align="center">"多家制造的产品"---其制造商发货情况</h4></p>' +
									'<ul><li>发货总合计：既当月该产品的发货总量;' +
									'以出库单（审核时间为当前月份，已审核）对应的发货信息（不是补单类型）统计每个产品当月的总发货量</li>' +
									'<li>发货数量：该制造商制造的产品当月发货总数量 ; 以出库单（审核时间为当前月份，已审核）对应的发货信息（不是补单类型）统计对应产品（产品分配比例不能100）的制造商当月的总发货量</li>' +
									'<li>实际配额：当前月份制造商实际发货数量占对应产品的发货总数量的比例；发货数量/发货总合计</li><li>配额：该产品正泰指定给各制造商的标准配额;既产品资料中填写的分配比例</li></ul>');
						}
						}],
			fields : [{
						name : 'fproductid'
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
						}, {
						name : 'fproportion'
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
						'header' : '发货数量',
						'dataIndex' : 'famounts',
						sortable : true
					}, {
						'header' : '发货总合计',
						'dataIndex' : 'totalnum',
						sortable : true
					},  {
						'header' : '实际配额',
						'dataIndex' : 'rate',
						sortable : true
					},  {
						'header' : '配额',
						'dataIndex' : 'fproportion',
						sortable : true
					}],
					selModel:Ext.create('Ext.selection.CheckboxModel')
		})