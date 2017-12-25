Ext.define('DJ.order.saleOrder.ExceptionOrderList', {
			extend : 'Ext.c.GridPanel',
			title : "异常订单管理",
			id : 'DJ.order.saleOrder.ExceptionOrderList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetExceptionOrders.do',
			//Delurl : "deleteSaleOrders.do",
			//EditUI : "DJ.order.saleOrder.SaleOrderEdit",
//			exporturl:"productplantoexcel.do",
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
			},
			Action_BeforeAddButtonClick : function(EditUI) {
				// 新增界面弹出前事件
			},
			Action_AfterAddButtonClick : function(EditUI) {
				// 新增界面弹出后事件
//				Ext.getCmp("DJ.order.saleOrder.SaleOrderEdit").getform().getForm().findField('fnumber').hide();
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
			fields : [{
						name : 'fid'
					}, {
						name : 'fnumber',
						myfilterfield : 'd.fnumber',
						myfiltername : '编号',
						myfilterable : true
					}, {
						name : 'cname',
						myfilterfield : 'c.fname',
						myfiltername : '客户名称',
						myfilterable : true
					}, {
						name : 'pname',
						myfilterfield : 'p.fname',
						myfiltername : '客户产品名称',
						myfilterable : true
					}, {
						name : 'fname',
						myfilterfield : 'f.fname',
						myfiltername : '产品名称',
						myfilterable : true
					}, {
						name : 'sname'
					}, {
						name : 'fspec'
					}, {
						name : 'farrivetime'
					}, {
						name : 'fbizdate'
					}, {
						name : 'famount'
					}, {
						name : 'flastupdatetime'
					}, {
						name : 'u2_fname'
					}, {
						name : 'fcreatetime'
					}, {
						name : 'u1_fname'
					}, {
						name : 'faudittime'
					}, {
						name : 'fauditorid'
					}, {
						name : 'fauditor'
					},{
						name : 'faudited'
					}, {
						name : 'fordertype'
					}, {
						name : 'fseq'
					}, {
						name : 'fsuitProductID'
					}, {
						name : 'fparentOrderEntryId'
					}, {
						name : 'forderid'
					},{
						name : 'fimportEAS'
					}, {
						name : 'fstockoutqty'
					}, {
						name : 'fstockinqty'
					}, {
						name : 'fstoreqty'
					},{
						name : 'faffirmed'
					}, {
						name : 'fassemble'
					},{
						name : 'fiscombinecrosssubs'
					},{
						name : 'salefnumber'
					},{
						name : 'fcloseed'
					}],
			columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					},{
						'header' : 'fsuitProductID',
						'dataIndex' : 'fsuitProductID',
						hidden : true,
						hideable : false,
						sortable : true
					},{
						'header' : 'fassemble',
						'dataIndex' : 'fassemble',
						hidden : true,
						hideable : false,
						sortable : true
					},{
						'header' : 'fiscombinecrosssubs',
						'dataIndex' : 'fiscombinecrosssubs',
						hidden : true,
						hideable : false,
						sortable : true
					},{
						'header' : 'fparentOrderEntryId',
						'dataIndex' : 'fparentOrderEntryId',
						hidden : true,
						hideable : false,
						sortable : true
					},{
						'header' : 'forderid',
						'dataIndex' : 'forderid',
						hidden : true,
						hideable : false,
						sortable : true
					},{
						'header' : 'fparentorderid',
						'dataIndex' : 'fparentorderid',
						hidden : true,
						hideable : false,
						sortable : true
				
					}, {
						'header' : '制造订单号',
						'dataIndex' : 'fnumber',
						width:100,
						sortable : true
					}, {
						'header' : '订单类型',
						'dataIndex' : 'fordertype',
						width:60,
						sortable : true,
						 renderer: function(value){
						        if (value == 1) {
						            return '普通';
						        }else if(value==2){
						        	return '套装';}
					        	
						    }
					
					}, {
						'header' : '客户名称',
						width:100,
						'dataIndex' : 'cname',
						sortable : true
					},  {
						'header' : '产品名称',
						width:180,
						'dataIndex' : 'fname',
						sortable : true
					}, {
						'header' : '交期',
						'dataIndex' : 'farrivetime',
						width:130,
						sortable : true
					}, {
						'header' : '数量',
						width:50,
						'dataIndex' : 'famount',
						sortable : true
					}, {
						'header' : '入库',
						width:50,
						'dataIndex' : 'fstockinqty',
						sortable : true
					}, {
						'header' : '出库',
						width:50,
						'dataIndex' : 'fstockoutqty',
						sortable : true
					}, {
						'header' : '存量',
						width:50,
						'dataIndex' : 'fstoreqty',
						sortable : true
					},{
						'header' : '确认',
						width:50,
						'dataIndex' : 'faffirmed',
						hidden : false,
						hideable : false,
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
						'header' : '关闭',
						width:50,
						'dataIndex' : 'fcloseed',
						hidden : false,
						hideable : false,
						sortable : true,
						renderer: function(value){
					        if (value == 1) {
					            return '是';
					        }
					        else{
					        	return '否';
					        }
					    }
					},
						{
						'header' : '导入EAS',
						dataIndex : 'fimportEAS',
						width:70,
						sortable : true,
						renderer: function(value){
						        if (value == 1) {
						            return '是';
						        }
						        else{
						        	return '否';
						        }
						    }
						},  {
						'header' : '制造商名称',
						'dataIndex' : 'sname',
						sortable : true
					}, {
						'header' : '规格',
						'dataIndex' : 'fspec',
						sortable : true
					}, {
						'header' : '业务时间',
						'dataIndex' : 'fbizdate',
						sortable : true
					}, {
							text : '创建人',
							dataIndex : 'u1_fname',
							sortable : true
						}, {
							text : '创建时间',
							dataIndex : 'fcreatetime',
							width : 150,
							sortable : true

					},{
							text : '修改人',
							dataIndex : 'u2_fname',
							sortable : true
						}, {
							text : '修改时间',
							dataIndex : 'flastupdatetime',
							width : 150,
							sortable : true
						},
						{
						'header' : '生产订单号',
						'dataIndex' : 'salefnumber',
						sortable : true  
						}, {
						'header' : '生产分录',
						'dataIndex' : 'fseq',
						sortable : true
					}],
				
					selModel:Ext.create('Ext.selection.CheckboxModel')
		})