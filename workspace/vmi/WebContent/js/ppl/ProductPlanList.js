Ext.define('DJ.ppl.ProductPlanList', {
			extend : 'Ext.c.GridPanel',
			title : "制造商订单管理",
			id : 'DJ.ppl.ProductPlanList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetProductPlans.do',
			//Delurl : "deleteSaleOrders.do",
			//EditUI : "DJ.order.saleOrder.SaleOrderEdit",
			exporturl:"productplantoexcel.do",
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
			custbar : [
			{
				// id : 'DelButton',
				text : '审核',
				height : 30,
				handler : //actionAudit(1)
					function() {
					var grid = Ext.getCmp("DJ.ppl.ProductPlanList");
					var record = grid.getSelectionModel().getSelection();
//					if (record.length != 1) {
//						Ext.MessageBox.alert('提示', '请选中一条记录进行审核！');
//						return;
//					}
//					var fid = record[0].get("fid");
					if(record.length<1){
						Ext.MessageBox.alert("信息","请选择至少一条订单进行审核！");
						return;
					}
					var ids="";
					for ( var i = 0; i < record.length; i++) {
						ids +=  record[i].get("fid");
						if (i < record.length - 1) {
								ids = ids + ",";
							}
					}
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "auditProductplan.do",
						params : {
							fidcls : ids
//							fid : fid,
//							fproductid : record[0].get("FProductID"),
//							finqty : record[0].get("finqty"),
//							fwarehouseid : record[0].get("FWarehouseID"),
//							fwarehousesiteid : record[0].get("FWarehouseSiteID"),
//							fdescription : record[0].get("fdescription")
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								//Ext.MessageBox.alert('成功', obj.msg);
								djsuccessmsg( obj.msg);
								Ext.getCmp("DJ.ppl.ProductPlanList").store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});
				}
			}, {
				// id : 'DelButton',
				text : '反审核',
				height : 30,
				handler : //actionAudit(0)
					function() {
					var grid = Ext.getCmp("DJ.ppl.ProductPlanList");
					var record = grid.getSelectionModel().getSelection();
//					if (record.length != 1) {
//						Ext.MessageBox.alert('提示', '请选中一条记录反审核！');
//						return;
//					}
//					if(record[0].get("fallot")==1){
//						Ext.MessageBox.alert('提示', '已分配的订单不能反审核！');
//						return;
//					}
//					if(record[0].get("fimportEAS")==1){
//						Ext.MessageBox.alert('提示', '已导入EAS的订单不能反审核！');
//						return;
//					}
//					var fid = record[0].get("fid");
					if(record.length<1){
						Ext.MessageBox.alert("信息","请选择至少一条订单进行反审核！");
						return;
					}
					var ids="";
					for ( var i = 0; i < record.length; i++) {
						ids += record[i].get("fid") ;
						if (i < record.length - 1) {
								ids = ids + ",";
							}
					}
					
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "unauditProductplan.do",
						params : {
							fidcls : ids
//							fid : fid,
//							fproductid : record[0].get("FProductID"),
//							finqty : record[0].get("finqty"),
//							fwarehouseid : record[0].get("FWarehouseID"),
//							fwarehousesiteid : record[0].get("FWarehouseSiteID"),
//							fdescription : record[0].get("fdescription")
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								//Ext.MessageBox.alert('成功', obj.msg);
								djsuccessmsg( obj.msg);
								Ext.getCmp("DJ.ppl.ProductPlanList").store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});
				}
			}, 
			{
				text : '订单关闭',
				height : 30,
				handler : //AssageSupplier(1,fids)
				function() {
					var grid = Ext.getCmp("DJ.ppl.ProductPlanList");
					var record = grid.getSelectionModel().getSelection();
					var ids="";
					if(record.length<1){
						Ext.MessageBox.alert("信息","请选择至少一条订单分录进行操作！");
						return;
					}
					for ( var i = 0; i < record.length; i++) {
						ids += record[i].get("fid") ;
						if (i < record.length - 1) {
								ids = ids + ",";
							}
					}
					
					Ext.Ajax.request({
						timeout: 600000,
						url : "orderclose.do",
						params : {
							fidcls:ids
						},
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								//Ext.MessageBox.alert('成功', obj.msg);
								djsuccessmsg( obj.msg);
								Ext.getCmp("DJ.ppl.ProductPlanList").store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
								
							}
						}
					});
					
				}
			},
			{
				text : '订单反关闭',
				height : 30,
				handler : //AssageSupplier(1,fids)
				function() {
					var grid = Ext.getCmp("DJ.ppl.ProductPlanList");
					var record = grid.getSelectionModel().getSelection();
					var ids="";
					if(record.length<1){
						Ext.MessageBox.alert("信息","请选择至少一条订单分录进行操作！");
						return;
					}
					for ( var i = 0; i < record.length; i++) {
						ids +=  record[i].get("fid") ;
						if (i < record.length - 1) {
								ids = ids + ",";
							}
					}
					
					Ext.Ajax.request({
						timeout: 600000,
						url : "UnOrderclose.do",
						params : {
							fidcls:ids
						},
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								//Ext.MessageBox.alert('成功', obj.msg);
								djsuccessmsg( obj.msg);

								Ext.getCmp("DJ.ppl.ProductPlanList").store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
								
							}
						}
					});
					
				}
			}],
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
						name : 'sname',
						myfilterfield : 's.fname',
						myfiltername : '制造商',
						myfilterable : true
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
						name : 'faudited',
						myfilterfield : 'd.faudited',
						myfiltername : '是否审核',
						myfilterable : true
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
						name : 'fcloseed',
						myfilterfield : 'd.fcloseed',
						myfiltername : '是否关闭',
						myfilterable : true
					},{
						name : 'fisfinished',
						myfilterfield : 'd.fisfinished',
						myfiltername : '是否完成',
						myfilterable : true
					},{
						name : 'ftype',
						myfilterfield : 'd.ftype',
						myfiltername : '订单类型',
						myfilterable : true
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
						'header' : '产品类型',
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
						'header' : '订单类型',
						'dataIndex' : 'ftype',
						width:60,
						sortable : true,
						 renderer: function(value){
						        if (value == 0) {
						            return '初始化';
						        }else if(value==1){
						        	return '正常';}
					        	
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
					}, {
						'header' : '完成',
						width:50,
						'dataIndex' : 'fisfinished',
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
						'header' : '供应商名称',
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
//						{
//						'header' : '审核人',
//						'dataIndex' : 'fauditorid',
//						hidden : true,
//						sortable : true
//					}, {
//						'header' : '审核人',
//						'dataIndex' : 'fauditor',
//						sortable : true
//					}, 
						{
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
					}, 
//					{
//						'header' : '审核时间',
//						dataIndex : 'faudittime',
//						width : 150,
//						sortable : true
//					},
//						{
//						'header' : '客户产品名称',
//						'dataIndex' : 'pname',
//						sortable : true
//					},
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
//			custbar : [{
//				// id : 'DelButton',
//				text : '分配',
//				height : 30,
//				handler : //AssageSupplier(0)
//				function() {
//				}
//			}],
//			fields : [{
//						name : 'fid'
//					}, {
//						name : 'fnumber'
//					}, {
//						name : 'cname1',
//						myfilterfield : 'c.fname',
//						myfiltername : '客户名称',
//						myfilterable : true
//					}, {
//						name : 'cname2',
//						myfilterfield : 'cp.fname',
//						myfiltername : '客户产品名称',
//						myfilterable : true
//					}, {
//						name : 'fname',
//						myfilterfield : 'g.fname',
//						myfiltername : '产品名称',
//						myfilterable : true
//					}, {
//						name : 'farrivetime'
//					}, {
//						name : 'fbizdate'
//					}, {
//						name : 'famount'
//					}, {
//						name : 'fcreatetime'
//					}, {
//						name : 'cfname'
//					}, {
//						name : 'fallottime'
//					}, {
//						name : 'falloted'
//					}, {
//						name : 'afname'
//					}],
//			columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
//						'header' : 'fid',
//						'dataIndex' : 'fid',
//						hidden : true,
//						hideable : false,
//						sortable : true
//					}, {
//						'header' : '编号',
//						'dataIndex' : 'fnumber',
//						sortable : true
//					}, {
//						'header' : '客户名称',
//						'dataIndex' : 'cname1',
//						sortable : true
//					}, {
//						'header' : '客户产品名称',
//						'dataIndex' : 'cname2',
//						sortable : true
//					}, {
//						'header' : '产品名称',
//						'dataIndex' : 'fname',
//						sortable : true
//					}, {
//						'header' : '数量',
//						'dataIndex' : 'famount',
//						sortable : true
//					}, {
//						'header' : '交期',
//						'dataIndex' : 'farrivetime',
//						sortable : true
//					}, {
//						'header' : '业务时间',
//						'dataIndex' : 'fbizdate',
//						sortable : true
//						}, {
//							text : '创建人',
//							dataIndex : 'u1_fname',
//							sortable : true
//						}, {
//							text : '创建时间',
//							dataIndex : 'fcreatetime',
//							width : 150,
//							sortable : true
//
//			
//					}, {
//						'header' : '审核人',
//						'dataIndex' : 'afname',
//						sortable : true
//					}, {
//						'header' : '分配',
//						dataIndex : 'falloted',
//						sortable : true,
//						renderer: function(value){
//						        if (value == 1) {
//						            return '是';
//						        }
//						        else{
//						        	return '否';
//						        }
//						    }
//					}, {
//						'header' : '分配时间',
//						dataIndex : 'fallottime',
//						width : 150,
//						sortable : true
//					}],
//					selModel:Ext.create('Ext.selection.CheckboxModel')
//		})
		
		
