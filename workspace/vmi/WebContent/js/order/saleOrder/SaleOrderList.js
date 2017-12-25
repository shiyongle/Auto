Ext.define('DJ.order.saleOrder.SaleOrderList', {
			extend : 'Ext.c.GridPanel',
			title : "生产订单",
			id : 'DJ.order.saleOrder.SaleOrderList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetSaleOrders.do',
			Delurl : "deleteSaleOrdersNew.do",
			EditUI : "DJ.order.saleOrder.SaleOrderEdit",
			exporturl:"saletoexcel.do",
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
//				Ext.getCmp("DJ.order.saleOrder.SaleOrderList.editbutton").setVisible(false);
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
				var grid = Ext.getCmp("DJ.order.saleOrder.SaleOrderList");
				var record = grid.getSelectionModel().getSelection();
				if (record.length >= 1) {
					if(record[0].get("fallot")=='1'){//faudited 取消审核功能
						throw "已分配数据不能进行修改"//"已审核数据不能进行修改！";
					}
					if(record[0].get("ftype")=='1'){
						throw "只能修改初始化类型订单!";
					}
				}
			},
			Action_AfterEditButtonClick : function(EditUI) {
				// 修改界面弹出后事件
				// 可对编辑界面进行复制
			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
//				var grid = Ext.getCmp("DJ.order.saleOrder.SaleOrderList");
//				var record = grid.getSelectionModel().getSelection();
//				for ( var i = 0; i < record.length; i++) {
//					if(record[i].get("faudited")=='1'){
//						throw "已审核数据不能删除！";
//					}
//				}
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			},
			custbar : [{
				// id : 'DelButton',
				text : '审核',
				height : 30,
				handler : //actionAudit(1)
					function() {
					var grid = Ext.getCmp("DJ.order.saleOrder.SaleOrderList");
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
					var forderids="";
					for ( var i = 0; i < record.length; i++) {
						forderids +=  record[i].get("forderid");
						if (i < record.length - 1) {
								forderids = forderids + ",";
							}
					}
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "auditOrder.do",
						params : {
							fidcls : forderids
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
								  djsuccessmsg( obj.msg);
								Ext.getCmp("DJ.order.saleOrder.SaleOrderList").store.load();
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
					var grid = Ext.getCmp("DJ.order.saleOrder.SaleOrderList");
					var record = grid.getSelectionModel().getSelection();
					if(record.length<1){
						Ext.MessageBox.alert("信息","请选择至少一条订单进行反审核！");
						return;
					}
					var forderids="";
					for ( var i = 0; i < record.length; i++) {
						forderids += record[i].get("forderid") ;
						if (i < record.length - 1) {
								forderids = forderids + ",";
							}
					}
					
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "unauditOrder.do",
						params : {
							fidcls : forderids
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								  djsuccessmsg( obj.msg);
								Ext.getCmp("DJ.order.saleOrder.SaleOrderList").store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});
				}
			}, {
				// id : 'DelButton',
				text : '分配',
				height : 30,
				handler : //AssageSupplier(0)
				function() {
					var me = Ext.getCmp("DJ.order.saleOrder.SaleOrderList");
					var el = me.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "AssageSupplier.do?ftype=0",
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								  djsuccessmsg( obj.msg);
								Ext.getCmp("DJ.order.saleOrder.SaleOrderList").store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
								Ext.getCmp("DJ.order.saleOrder.SaleOrderList").store.load();
							}
							el.unmask();
						}
					});
					
				}
				}, {
				// id : 'DelButton',
				text : '手动分配',
				height : 30,
				handler : //AssageSupplier(0)
				function() {
					var grid = Ext.getCmp("DJ.order.saleOrder.SaleOrderList");
					var record = grid.getSelectionModel().getSelection();
					if(record.length!=1)
					{
						Ext.MessageBox.alert('错误',"请选择一条记录进行指定！");
						return;
					}
						if(record[0].get("faudited")=='0')
							{
							Ext.MessageBox.alert("信息","未审核的不能分配！");
							return;
							}
							if(record[0].get("fallot")=='1')
							{
							Ext.MessageBox.alert("信息","所选记录已经分配，请先选择取消分配！");
							return;
							}
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "getAssginSaleorderInfo.do",
						params : {
							fid : record[0].get("fid")
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								var rinfo=obj.data;
							//	alert(rinfo[0].famount);
								var assignOrderEdit = Ext.getCmp("DJ.order.saleOrder.AssignSaleOrderEdit");
					
								if (assignOrderEdit != null) {
								} else {
									assignOrderEdit = Ext.create('DJ.order.saleOrder.AssignSaleOrderEdit');
								}
								Ext.getCmp("DJ.order.saleOrder.saleorderFnumber").setValue(rinfo[0].fnumber);
								Ext.getCmp("DJ.order.saleOrder.saleorderid").setValue(rinfo[0].fid);
								Ext.getCmp("DJ.order.saleOrder.ftotalnum").setValue(rinfo[0].famount);
								Ext.getCmp("DJ.order.saleOrder.fproductid").setValue(rinfo[0].FPRODUCTDEFID);
								assignOrderEdit.show();
								var custgrid = Ext.getCmp("AssginSaleOrderEditGridPanel");
								var custstore = custgrid.getStore();
								custstore.loadData("");
								el.unmask();
		//						grid.store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
								el.unmask();
							}
						}
					});
				}
				
			}, {
				// id : 'DelButton',
				text : '取消分配',
				height : 30,
				handler : //AssageSupplier(1,fids)
				function() {
					var grid = Ext.getCmp("DJ.order.saleOrder.SaleOrderList");
					var record = grid.getSelectionModel().getSelection();
					var ids="";
					if(record.length<1){
						Ext.MessageBox.alert("信息","请选择至少一条记录取消分配！");
						return;
					}
					for ( var i = 0; i < record.length; i++) {
						ids +=record[i].get("fid");
						
						if(record[i].get("faudited")=='0')
							{
							Ext.MessageBox.alert("信息","未审核的不能取消分配...或数据不同步请刷新！");
							return;
							}
						if(record[i].get("fallot")=='0')
						{
							Ext.MessageBox.alert("信息","该订单未分配");
							return;
						}
						
						if(record[i].get("fimportEAS")==1){
							Ext.MessageBox.alert('提示', '已导入EAS的订单不能取消分配！');
							return;
						}
						if (i < record.length - 1) {
								ids = ids + ",";
							}
						}
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "AssageSupplier.do?",
						params : {
							ftype : '1',
							fidcls:ids
						},
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								  djsuccessmsg( obj.msg);
								Ext.getCmp("DJ.order.saleOrder.SaleOrderList").store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
								
							}
							el.unmask();
						}
					});
					
				}
			}],
//			plugins : [{
//				ptype : "mygriditemtipplugin",
//				showingFields : ['fdescription'] 
//			}],
//			listeners:{
//				render:function(){
//					this.getStore.addListener('load',function(){
//						
//					});
//				}
//			},
			fields : [{
						name : 'fid'
					}, {
						name : 'fnumber',
						myfilterfield : 'd.fnumber',
						myfiltername : '订单编号',
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
					},  {
						name : 'fstockoutqty'
					}, {
						name : 'fstockinqty'
					}, {
						name : 'fstoreqty'
					},{
						name : 'famount'
					},  {
						name : 'faudited'
					},{
						name : 'fallot',
						myfilterfield : 'd.fallot',
						myfiltername : '分配',
						myfilterable : true
					},{
						name : 'sname'
					}, {
						name : 'fspec'
					}, {
						name : 'farrivetime'
					}, {
						name : 'fbizdate'
					},{
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
					},{
						name : 'faffirmed'
					}, {
						name : 'fassemble'
					},{
						name : 'fiscombinecrosssubs'
					},  {
						name : 'fallottime'
					},{
						name : 'fallotor'
					},{
						name : 'ftype'
					},{
						name : 'fdescription'
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
						'header' : 'ftype',
						'dataIndex' : 'ftype',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						'header' : '订单编号',
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
						renderer: function(value,metadata,record){
								var title = record.get('fdescription');
						        if (value == 0) {
						            return '初始化';
						        }else if(value==1){
						        	return '正常';
						        }else if(value == 2){
						        	return '<span data-qtip="'+title+'" style="color:#700">异常</span>';
						        }
						    }
					}, {
						'header' : '分录',
						'dataIndex' : 'fseq',
						width:30,
						sortable : true
					},{
						'header' : '客户名称',
						'dataIndex' : 'cname',
						sortable : true
					},  {
						'header' : '产品名称',
						'dataIndex' : 'fname',
						width:200,
						sortable : true
					}, {
						'header' : '数量',
						'dataIndex' : 'famount',
						width:50,
						sortable : true
					}, {
						'header' : '入库',
						'dataIndex' : 'fstockinqty',
						width:50,
						sortable : true
					}, {
						'header' : '出库',
						'dataIndex' : 'fstockoutqty',
						width:50,
						sortable : true
					}, {
						'header' : '存量',
						'dataIndex' : 'fstoreqty',
						width:50,
						sortable : true
					}, 
						{
						'header' : '规格',
						width:50,
						'dataIndex' : 'fspec',
						sortable : true
					}, {
						'header' : '交期',
						width:130,
						'dataIndex' : 'farrivetime',
						sortable : true
					},{
						'header' : '审核',
						dataIndex : 'faudited',
						width:30,
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
						'header' : '分配',
						dataIndex : 'fallot',
						width:30,
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
						'header' : '业务时间',
						'dataIndex' : 'fbizdate',
						sortable : true
					},{
						'header' : '客户产品名称',
						'dataIndex' : 'pname',
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
						}, {
							text : '创建人',
							dataIndex : 'u1_fname',
							sortable : true
						}, {
							text : '创建时间',
							dataIndex : 'fcreatetime',
							width : 150,
							sortable : true

					}, {
						'header' : '审核人',
						'dataIndex' : 'fauditorid',
						hidden : true,
						sortable : true
					}, {
						'header' : '审核人',
						'dataIndex' : 'fauditor',
						sortable : true
					},{
						'header' : '审核时间',
						dataIndex : 'faudittime',
						width : 150,
						sortable : true
					}, {
						'header' : '分配人',
						'dataIndex' : 'fallotor',
						sortable : true
					},  {
						'header' : '分配时间',
						dataIndex : 'fallottime',
						width : 150,
						sortable : true
					}],
					selModel:Ext.create('Ext.selection.CheckboxModel')
		})