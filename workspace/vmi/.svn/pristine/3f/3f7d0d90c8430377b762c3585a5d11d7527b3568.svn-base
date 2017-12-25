//暂时不用
Ext.define('DJ.order.saleOrder.OIsaleOrderList', {
			extend : 'Ext.c.GridPanel',
			title : "制造商订单管理",
			id : 'DJ.order.saleOrder.OIsaleOrderList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetSaleOrders.do',
			Delurl : "",
			EditUI : "",
			exporturl:"saletoexcel.do",
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
				var grid = Ext.getCmp("DJ.order.saleOrder.OIsaleOrderList");
				var record = grid.getSelectionModel().getSelection();
				if (record.length >= 1) {
					if(record[0].get("faudited")=='1'){
						throw "已审核数据不能进行修改！";
					}
				}
			},
			Action_AfterEditButtonClick : function(EditUI) {
				// 修改界面弹出后事件
				// 可对编辑界面进行复制
			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
				var grid = Ext.getCmp("DJ.order.saleOrder.OIsaleOrderList");
				var record = grid.getSelectionModel().getSelection();
				for ( var i = 0; i < record.length; i++) {
					if(record[i].get("faudited")=='1'){
						throw "已审核数据不能删除！";
					}
				}
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			},
			custbar : [ {
						// id : 'DelButton',
						text : '确认',
						height : 30,
						handler : //actionAudit(1)
							function() {
							var grid = Ext.getCmp("DJ.order.saleOrder.OIsaleOrderList");
							var record = grid.getSelectionModel().getSelection();
							if (record.length <= 0) {
								Ext.MessageBox.alert('提示', '请选中记录确认！');
								return;
							}
							var fid = "";
							for(var i=0;i<record.length;i++){
								if(record[i].get("faffirmed")==1){
									continue;
								}
								if(fid.length<=0){
									fid =record[i].get("fid");
								}else{
									fid = fid + ","+record[i].get("fid");
								}
							}
							var el = me.getEl();
							el.mask("系统处理中,请稍候……");
							Ext.Ajax.request({
								url : "supplierAffirm.do",
								params : {
									fidcls : fid
								}, // 参数
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										  djsuccessmsg( obj.msg);
										Ext.getCmp("DJ.order.saleOrder.OIsaleOrderList").store.load();
									} else {
										Ext.MessageBox.alert('错误', obj.msg);
									}
									el.unmask();
								}
							});
						}
					}, {
						// id : 'DelButton',
						text : '取消确认',
						height : 30,
						handler : //actionAudit(1)
							function() {
							var grid = Ext.getCmp("DJ.order.saleOrder.OIsaleOrderList");
							var record = grid.getSelectionModel().getSelection();
							if (record.length <= 0) {
								Ext.MessageBox.alert('提示', '请选中记录取消确认！');
								return;
							}
							var fid = "";
							for(var i=0;i<record.length;i++){
								if(record[i].get("faffirmed")==0){
									continue;
								}
								if(fid.length<=0){
									fid = +record[i].get("fid");
								}else{
									fid = fid + ","+record[i].get("fid");
								}
							}
							var el = me.getEl();
							el.mask("系统处理中,请稍候……");
							Ext.Ajax.request({
								url : "supplierUnAffirm.do",
								params : {
									fidcls : fid
								}, // 参数
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										  djsuccessmsg( obj.msg);
										Ext.getCmp("DJ.order.saleOrder.OIsaleOrderList").store.load();
									} else {
										Ext.MessageBox.alert('错误', obj.msg);
									}
									el.unmask();
								}
							});
						}
					},
			           {
						// id : 'DelButton',
						text : '入库',
						height : 30,
						handler : //actionAudit(1)
							function() {
							var grid = Ext.getCmp("DJ.order.saleOrder.OIsaleOrderList");
							var record = grid.getSelectionModel().getSelection();
							if (record.length != 1) {
								Ext.MessageBox.alert('提示', '请选中一条记录入库！');
								return;
							}
							if (record[0].get("faffirmed")==0 || record[0].get("faffirmed")=='null') {
								Ext.MessageBox.alert('提示', '制造商未确认不能入库！');
								return;
							}
							var fid = record[0].get("fid");
							var SaleOrderOIEdit = Ext.create('DJ.order.saleOrder.OISaleOrderEdit');
							SaleOrderOIEdit.show();
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FProductID").setValue(record[0].get("fproductdefid"));
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.forderid").setValue(record[0].get("forderid"));
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.forderEntryid").setValue(fid);
							var finqty = record[0].get("famount")-record[0].get("fstockinqty");
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.famount").setValue(finqty);
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.ftype").setValue("0");
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fordertype").setValue(record[0].get("fordertype"));
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fentryProductType").setValue(record[0].get("fentryProductType"));
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fassemble").setValue(record[0].get("fassemble"));
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fiscombinecrosssubs").setValue(record[0].get("fiscombinecrosssubs"));
						}
					}, {
						// id : 'DelButton',
						text : '出库',
						height : 30,
						handler : //actionAudit(1)
							function() {
							var grid = Ext.getCmp("DJ.order.saleOrder.OIsaleOrderList");
							var record = grid.getSelectionModel().getSelection();
							if (record.length != 1) {
								Ext.MessageBox.alert('提示', '请选中一条记录入库！');
								return;
							}
							if (record[0].get("faffirmed")==0 || record[0].get("faffirmed")=='null') {
								Ext.MessageBox.alert('提示', '制造商未确认不能出库！');
								return;
							}
							var fid = record[0].get("fid");
							var SaleOrderOIEdit = Ext.create('DJ.order.saleOrder.OISaleOrderEdit');
							SaleOrderOIEdit.show();
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FProductID").setValue(record[0].get("fproductdefid"));
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.forderid").setValue(record[0].get("forderid"));
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.forderEntryid").setValue(fid);
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.famount").setValue(record[0].get("fstoreqty"));
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.ftype").setValue("1");
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fordertype").setValue(record[0].get("fordertype"));
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fentryProductType").setValue(record[0].get("fentryProductType"));
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fassemble").setValue(record[0].get("fassemble"));
							Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fiscombinecrosssubs").setValue(record[0].get("fiscombinecrosssubs"));
						}
					}],
			fields : [{
						name : 'fid'
					}, {
						name : 'fnumber'
					},{
						name : 'fproductdefid'
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
					},{
						name : 'fentryProductType'
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
					}, {
						'header' : '订单编号',
						'dataIndex' : 'fnumber',
						sortable : true
					}, {
						'header' : '订单类型',
						'dataIndex' : 'fordertype',
						sortable : true,
						 renderer: function(value){
						        if (value == 1) {
						            return '普通';
						        }else if(value==2){
						        	return '套装';}
					        	
						    }
					}, {
						'header' : '订单分录',
						'dataIndex' : 'fseq',
						sortable : true
					},{
						'header' : '导入EAS',
						'dataIndex' : 'fimportEAS',
						hidden : true,
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
						'header' : '客户名称',
						'dataIndex' : 'cname',
						sortable : true
					}, {
						'header' : '客户产品名称',
						'dataIndex' : 'pname',
						sortable : true
					}, {
						'header' : '产品名称',
						'dataIndex' : 'fname',
						sortable : true
					}, {
						'header' : '入库',
						'dataIndex' : 'fstockinqty',
						sortable : true
					}, {
						'header' : '出库',
						'dataIndex' : 'fstockoutqty',
						sortable : true
					}, {
						'header' : '存量',
						'dataIndex' : 'fstoreqty',
						sortable : true
					},{
						'header' : '确认',
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
						'header' : '制造商名称',
						'dataIndex' : 'sname',
						sortable : true
					}, {
						'header' : '规格',
						'dataIndex' : 'fspec',
						sortable : true
					}, {
						'header' : '数量',
						'dataIndex' : 'famount',
						sortable : true
					}, {
						'header' : '交期',
						'dataIndex' : 'farrivetime',
						sortable : true
					}, {
						'header' : '业务时间',
						'dataIndex' : 'fbizdate',
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
					}, {
						'header' : '是否导入EAS',
						dataIndex : 'fimportEAS',
						sortable : true,
						renderer: function(value){
						        if (value == 1) {
						            return '是';
						        }
						        else{
						        	return '否';
						        }
						    }
					}],
					selModel:Ext.create('Ext.selection.CheckboxModel')
		})