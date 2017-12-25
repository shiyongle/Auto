Ext.define('DJ.order.saleOrder.DeliverorderNumsRpt', {
			extend : 'Ext.c.GridPanel',
			title : "配送发货报表",
			id : 'DJ.order.saleOrder.DeliverorderNumsRpt',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetDeliverorderNumsRpt.do?',
			Delurl : "",
			EditUI : "",
			exporturl:"DeliverorderNumsRpttoexect.do",
			onload : function() {
				Ext.getCmp('DJ.order.saleOrder.DeliverorderNumsRpt.addbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.DeliverorderNumsRpt.editbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.DeliverorderNumsRpt.delbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.DeliverorderNumsRpt.viewbutton').hide();
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
							Ext.MessageBox.alert('描述', '<p><h4 align="center">"配送发货报表"---制造商配送各产品到指定制造部的发货情况</h4></p>' +
									'<ul><li>制造部：正泰制造部名称 ;通过发货信息找到对应的要货信息，根据要货信息与要货申请的关联，查询制造部名称</li>' +
									'<li>发货总合计：既当月该产品发给相关制造部的发货总量;以出库单（审核时间为当前月份，已审核）对应的发货信息（不是补单类型,对应的制造部不能为空）按产品、制造部统计当月的总发货量</li>' +
									'<li>发货数量：该制造商制造的产品发给相关制造部的当月发货总数量 ; 以出库单（审核时间为当前月份，已审核）对应的发货信息（不是补单类型,对应的制造部不能为空)按产品、制造部、制造商统计当月的总发货量</li>' +
									'<li>实际配额：当前月份制造商实际发货数量占发货给各制造部的产品发货总数量的比例；发货数量/发货总合计</li></ul>');
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
						myfiltername : '制造商名称',
						myfilterable : true
					}, {
						name : 'famounts'
					}, {
						name : 'totalnums'
					}, {
						name : 'rate'
						}, {
						name : 'fwerkname',
						myfilterfield : 'fwerkname',
						myfiltername : '制造部名称',
						myfilterable : true
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
						'header' : '制造部名称',
						'dataIndex' : 'fwerkname',
						width:180,
						sortable : true
					}, {
						'header' : '制造商名称',
						'dataIndex' : 'lname',
						width:100,
						sortable : true
					}, {
						'header' : '发货数量',
						'dataIndex' : 'famounts',
						sortable : true
					}, {
						'header' : '发货总合计',
						'dataIndex' : 'totalnums',
						sortable : true
					},  {
						'header' : '实际配额',
						'dataIndex' : 'rate',
						sortable : true
					}],
					selModel:Ext.create('Ext.selection.CheckboxModel')
		})