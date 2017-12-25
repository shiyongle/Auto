

Ext.define('DJ.order.saleOrder.OrderBalanceDetailRpt', {
			extend : 'Ext.c.GridPanel',
			title : "结算库存明细表",
			id : 'DJ.order.saleOrder.OrderBalanceDetailRpt',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetProductplanofBanlances.do?',
			Delurl : "",
			EditUI : "",
			exporturl:"ProductplanofBanlancestoexcel.do",
			require:["DJ.tools.fieldRel.MyFieldRelTools","Ext.ux.grid.MyGridItemDblClick"],
			onload : function() {
				Ext.getCmp('DJ.order.saleOrder.OrderBalanceDetailRpt.addbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.OrderBalanceDetailRpt.editbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.OrderBalanceDetailRpt.delbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.OrderBalanceDetailRpt.viewbutton').hide();
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
				// 可对编辑界面进行复制
			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			},
			plugins : [{
				ptype : "mygriditemdblclick",
				dbClickHandler : "button[text=查看明细]"
			}],
			custbar : [ {
						
				text : '查看明细',
				height : 30,
				handler : function() {
					var grid = Ext.getCmp("DJ.order.saleOrder.OrderBalanceDetailRpt");
					var record = grid.getSelectionModel().getSelection();
					if(record.length!=1){
						Ext.MessageBox.alert("信息","请选择一条记录进行操作");
						return;
					}
					var fid=record[0].get("fid");
					var details=Ext.getCmp("DJ.order.saleOrder.OrderBalanceDetailLists");
					if (details == null) {
					details = Ext.create('DJ.order.saleOrder.OrderBalanceDetailLists');
					}
					details.show();
					var pstore=Ext.getCmp("DJ.order.saleOrder.OrderBalanceDetailLists.OrderBalanceDetail").getStore();
					pstore.on("beforeload", function(store, options) {
	    				Ext.apply(store.proxy.extraParams, 
	    				{
	    					fid:fid
	    				});
				  	});
				 	pstore.loadPage(1);
				}
			}
			],
			fields : [{
						name : 'fid'
					}, {
						name : 'fnumber',
						myfilterfield : 'P.fnumber',
						myfiltername : '制造订单号',
						myfilterable : true
					}, {
						name : 'fname',
						myfilterfield : 'f.fname',
						myfiltername : '包装物名称',
						myfilterable : true
					}, {
						name : 'fcharacter'
					}, {
						name : 'famount'
					}, {
						name : 'fallotnum'//发货数量
					}, {
						name : 'fstoreqty'//实时库存
					},{
						name : 'balanceqty'//结算库存
					}],
		columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						'header' : '制造订单号',
						'dataIndex' : 'fnumber',
						width:100,
						sortable : true
					},  {
						'header' : '包装物名称',
						width:180,
						'dataIndex' : 'fname',
						sortable : true
					},{
						header:'规格',
						dataIndex:'fcharacter'
					
					}, {
						'header' : '订单数量',
						width:80,
						'dataIndex' : 'famount',
						sortable : true
					}, {
						'header' : '发货数量',
						width:80,
						'dataIndex' : 'fallotnum',
						sortable : true
					}, {
						'header' : '实时库存',
						width:80,
						'dataIndex' : 'fstoreqty',
						sortable : true
					}, {
						'header' : '结算库存',
						'dataIndex' : 'balanceqty',
							width:80,
						sortable : true
					},
					],
					defaults: {
					renderer : DJ.tools.fieldRel.MyFieldRelTools.showEmptyWhenNull
    				},
					selModel:Ext.create('Ext.selection.CheckboxModel')
		})