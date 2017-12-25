Ext.define('DJ.Inv.OutWarehouse.OutWarehouseList', {
			extend : 'Ext.c.GridPanel',
			title : "出库管理",
			id : 'DJ.Inv.OutWarehouse.OutWarehouseList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetOutWarehouse.do',
			Delurl : "DeleteOutWarehouse.do",
			EditUI : "DJ.Inv.OutWarehouse.OutWarehouseEdit",
			exporturl:"outWarehousetoexcel.do",
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
				Ext.getCmp('DJ.Inv.OutWarehouse.OutWarehouseList.addbutton').hide();
				Ext.getCmp('DJ.Inv.OutWarehouse.OutWarehouseList.editbutton').hide();
			},
			Action_BeforeAddButtonClick : function(EditUI) {
				// 新增界面弹出前事件
			},
			Action_AfterAddButtonClick : function(EditUI) {
				// 新增界面弹出后事件
				
			},
			Action_BeforeEditButtonClick : function(EditUI) {
				// 修改界面弹出前事件
				var grid = Ext.getCmp("DJ.Inv.OutWarehouse.OutWarehouseList");
				var record = grid.getSelectionModel().getSelection();
				if (record.length >= 1) {
					if(record[0].get("faudited")=='1'){
						throw "已审核数据不能进行修改!";
					}
				}
			},
			Action_AfterEditButtonClick : function(EditUI) {
				// 修改界面弹出后事件
				// 可对编辑界面进行复制
			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
				var grid = Ext.getCmp("DJ.Inv.OutWarehouse.OutWarehouseList");
				var record = grid.getSelectionModel().getSelection();
				if (record.length >= 1) {
					if(record[0].get("faudited")=='1'){
						throw "已审核数据不能删除!";
					}
				}
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			},
			fields : [{
						name : 'fid'
					}, {
						name : 'fnumber',
						myfilterfield : 'o.fnumber',
						myfiltername : '编号',
						myfilterable : true
					}, {
						name : 'fsaleorderid'
					}, {
						name : 'forderentryid'
					}, {
						name : 'planfnumber',
						myfilterfield : 's.fnumber',
						myfiltername : '订单编号',
						myfilterable : true
					}, {
						name : 'custfname'
					}, {
						name : 'fproductid'
					}, {
						name : 'pfname',
						myfilterfield : 'p.fname',
						myfiltername : '产品名称',
						myfilterable : true
					}, {
						name : 'foutqty'
					},{
						name:'fwarehouseid'
					}, {
						name : 'wfname1'
					}, {
						name : 'fwarehousesiteid'
					}, {
						name : 'wfname2'
//					}, {
//						name : 'fremak'
					},{
						name:'fissueenums'
//					}, {
//						name : 'flastupdatetime'
//					}, {
//						name : 'lfname'
					}, {
						name : 'fcreatetime'
					}, {
						name : 'cfname'
					}, {
						name : 'faudittime'
					}, {
						name : 'fauditorid'
					}, {
						name : 'fauditor'
					},{
						name : 'faudited'
					}],
			columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						'header' : '编码',
						'dataIndex' : 'fnumber',
						sortable : true
					}, {
					'header' : '客户',
					'dataIndex' : 'custfname',
					sortable : true
					}, {
						'header' : '制造商订单编号',
						'dataIndex' : 'planfnumber',
						sortable : true
					}, {
						'header' : '产品',
						'dataIndex' : 'pfname',
						sortable : true
					}, {
						'header' : '数量',
						'dataIndex' : 'foutqty',
						sortable : true,
						xtype: 'numbercolumn',
						format:'0'
					}, {
						'header' : '仓库',
						'dataIndex' : 'wfname1',
						sortable : true
					},{
						'header' : '库位',
						dataIndex : 'wfname2',
						sortable : true
//					},{
//						'header' : '修改人',
//							dataIndex : 'lfname',
//							sortable : true
//						}, {
//							'header' : '修改时间',
//							dataIndex : 'flastupdatetime',
//							width : 150,
//							sortable : true
						}, {
							'header' : '创建人',
							dataIndex : 'cfname',
							sortable : true
						}, {
							'header' : '创建时间',
							dataIndex : 'fcreatetime',
							width : 150,
							sortable : true
//						}, {
//							'header' : '备注',
//							dataIndex : 'fremak',
//							width : 200,
//							sortable : true
//					
						}, {
							'header' : '出库类型',
							dataIndex : 'fwarehousetype',
							sortable : true,
							 renderer: function(value){
							        if (value == 1) {
							            return '产品出库';
							        }else if(value==2)
							        	{
							        	return '半成品';}
							        else{
							        	return '未分类';
							        }
							    }
					}, {
						'header' : '审核人',
						'dataIndex' : 'fauditorid',
						hidden : true,
						sortable : true
					}, {
						'header' : '审核人',
						'dataIndex' : 'fauditor',
						sortable : true
					}, {
						'header' : '审核',
						dataIndex : 'faudited',
						sortable : true,
						 renderer: function(value){
						        if (value == 1) {
						            return '是';
						        }
						        else{
						        	return '否';
						        }
						    }
					}, {
						'header' : '审核时间',
						dataIndex : 'faudittime',
						width : 150,
						sortable : true
				}],
					selModel:Ext.create('Ext.selection.CheckboxModel')
		})