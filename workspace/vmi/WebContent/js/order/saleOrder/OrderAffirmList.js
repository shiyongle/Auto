Ext.require(["Ext.ux.form.MyMixedSearchBox"]);

Ext.define('DJ.System.product.productfile', {
	extend : 'Ext.c.GridPanel',

	alias : 'widget.productfile',

	id : 'DJ.System.product.productfile',
	pageSize : 50,
	url : 'GetProductfile.do',
	Delurl : "deleleProudctDraws.do",
	width : 400,
	minHeight : 200,
	emptyText:'<span style="font-size:16px">当前产品没有附件</span>',
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		// alert("DeliverList");
		this.down('toolbar').setVisible(false);
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
		this.up("window").close();
		Ext.destroy(this);

	},

	fields:[{
		name:'fid'
	},{
		name:'fname'
	},{
		name:'fpath'
	},{
		name:'fparentid'
	}],

	initComponent : function() {
		Ext.apply(this, {
		columns:[{
			dataIndex:'fid',
			hidden:true,
			hideable:false
		},{
			text:'路径',
			dataIndex:'fpath',
			id:'DJ.order.Deliver.productfile.fpath',
			hidden:true,
			hideable:false
		},{
			xtype:'templatecolumn',
			text:'附件名称',
			dataIndex:'fname',
			tpl : '<a href="downloadProductdemandFile.do?fid={fid}" target="_blank" style="text-decoration:none;">{fname}</a>',
			flex:1
		},{
			dataIndex:'fparentid',
			hidden:true,
			hideable:false
		}],
			selModel : Ext.create('Ext.selection.CheckboxModel')
		}), this.callParent(arguments);
	}

	
})

Ext.define('DJ.order.saleOrder.OrderAffirmList', {
			extend : 'Ext.c.GridPanel',
			title : "制造商订单确认",
			id : 'DJ.order.saleOrder.OrderAffirmList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetAuditedProductPlans.do?',
			Delurl : "",
			EditUI : "",
			exporturl:"AffirmOrderProductplantoexect.do",
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
//				var instore= this.store; 
//			 	var myfilter = [];
//						myfilter.push({
//							myfilterfield : "d.faudited",
//							CompareType : " = ",
//							type : "int",
//							value : 1
//						}
////						,{
////							myfilterfield : "d.fcloseed",
////							CompareType : " = ",
////							type : "int",
////							value : 0
////						}
//						);
//						instore.setDefaultfilter(myfilter);
//						instore.setDefaultmaskstring(" #0 ");
////						instore.setDefaultmaskstring(" #0 and #1 ");
//				instore.load();
				Ext.getCmp('DJ.order.saleOrder.OrderAffirmList.addbutton').setVisible(false);
				Ext.getCmp('DJ.order.saleOrder.OrderAffirmList.editbutton').setVisible(false);
				Ext.getCmp('DJ.order.saleOrder.OrderAffirmList.delbutton').setVisible(false);
				Ext.getCmp('DJ.order.saleOrder.OrderAffirmList.viewbutton').setVisible(false);
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
				var grid = Ext.getCmp("DJ.order.saleOrder.OrderAffirmList");
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
				var grid = Ext.getCmp("DJ.order.saleOrder.OrderAffirmList");
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
							var grid = Ext.getCmp("DJ.order.saleOrder.OrderAffirmList");
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
									fid = record[i].get("fid");
								}else{
									fid = fid + ","+record[i].get("fid");
								}
							}
							var el = grid.getEl();
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
										Ext.getCmp("DJ.order.saleOrder.OrderAffirmList").store.load();
									} else {
										Ext.MessageBox.alert('错误', obj.msg);
									}
									el.unmask();
								}
							});
						}
//					}, {
//						// id : 'DelButton',
//						text : '取消确认',
//						height : 30,
//						handler : //actionAudit(1)
//							function() {
//							var grid = Ext.getCmp("DJ.order.saleOrder.OrderAffirmList");
//							var record = grid.getSelectionModel().getSelection();
//							if (record.length <= 0) {
//								Ext.MessageBox.alert('提示', '请选中记录取消确认！');
//								return;
//							}
//							var fid = "";
//							for(var i=0;i<record.length;i++){
//								if(record[i].get("faffirmed")==0){
//									continue;
//								}
//								if(fid.length<=0){
//									fid = record[i].get("fid");
//								}else{
//									fid = fid + ","+record[i].get("fid");
//								}
//							}
//								var el = grid.getEl();
//							el.mask("系统处理中,请稍候……");
//							Ext.Ajax.request({
//								url : "supplierUnAffirm.do",
//								params : {
//									fidcls : fid
//								}, // 参数
//								success : function(response, option) {
//									var obj = Ext.decode(response.responseText);
//									if (obj.success == true) {
//										  djsuccessmsg( obj.msg);
//										Ext.getCmp("DJ.order.saleOrder.OrderAffirmList").store.load();
//									} else {
//										Ext.MessageBox.alert('错误', obj.msg);
//									}
//									el.unmask();
//								}
//							});
//						}
					},{
				text : '查看客户产品',
				height : 30,
				handler : function() {
					var grid = Ext.getCmp("DJ.order.saleOrder.OrderAffirmList");
					var record = grid.getSelectionModel().getSelection();
					var ids="";
					if(record.length!=1){
						Ext.MessageBox.alert("信息","请选择一条记录进行操作");
						return;
					}
					
					var el = grid.getEl();
							el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						timeout: 600000,
						url : "GetCustProductList.do?",
						params : {
							fid:record[0].get("fid")
//							Defaultfilter:"[{\"myfilterfield\":\"p.fid\",\"CompareType\":\" = \",\"type\":\"string\",\"value\":"+record[0].get("fid")+"}]",
//							Defaultmaskstring: "#0" 
						},
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								var details=Ext.getCmp("DJ.order.saleOrder.CustproductDetail");
								if (details != null) {
								} else {
									details = Ext.create('DJ.order.saleOrder.CustproductDetail');
								}
								Ext.getCmp("DJ.order.saleOrder.CustproductDetail.fname").setValue(record[0].get("fname"));
								details.show();
								var store=	details.items.get(1).getStore();
								  store.loadRawData(obj);
							} else {
								Ext.MessageBox.alert('错误',obj.msg);
							}
							el.unmask();
						}
					});
				}
			},{
				text:'查看附件',
				handler:function(){
					var grid = Ext.getCmp("DJ.order.saleOrder.OrderAffirmList");// Ext.getCmp("DJ.System.UserListPanel")

					var record = grid.getSelectionModel().getSelection();
					if (record.length != 1) {
						Ext.MessageBox.alert('提示', '只能选中一条记录进行操作!');
						return;
					}
				var win =	Ext.create('Ext.Window',{
//						items:{xtype:'productlrawlist'}
					items:{xtype:'productfile',
						url:'GetProductfile.do?fid='+record[0].get('fproductid')}
					})
				
					win.show();
				}
			},'|',
   
    { xtype : "mymixedsearchbox",
    useDefaultfilter : true,
     condictionFields : ['f.fname', 'd.fnumber', 'd.fdescription']//参数数组
}
			],
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
//					}, {
//						name : 'pname',
//						myfilterfield : 'p.fname',
//						myfiltername : '客户产品名称',
//						myfilterable : true
					}, {
						name : 'fname',
						myfilterfield : 'f.fname',
						myfiltername : '产品名称',
						myfilterable : true
					},{
						name:'fproductid'
//						hidden:true
					},{
						name : 'sname'
					}, {
						name : 'fspec'
					}, {
						name : 'farrivetime'
					}, {
						name : 'fbizdate'
					}, {
						name : 'famount'
//					}, {
//						name : 'flastupdatetime'
//					}, {
//						name : 'u2_fname'
//					}, {
//						name : 'fcreatetime'
//					}, {
//						name : 'u1_fname'
//					}, {
//						name : 'faudittime'
//					}, {
//						name : 'fauditorid'
//					}, {
//						name : 'fauditor'
					},{
						name : 'faudited'
					}, {
						name : 'fordertype'
//					}, {
//						name : 'fseq'
					}, {
						name : 'fsuitProductID'
					}, {
						name : 'fparentOrderEntryId'
					}, {
						name : 'forderid'
					},{
						name : 'fimportEAS',
						myfilterfield : 'd.fimportEAS',
						myfiltername : '导入',
						myfilterable : true
					}, {
						name : 'fstockoutqty'
					}, {
						name : 'fstockinqty'
					}, {
						name : 'fstoreqty'
					},{
						name : 'faffirmed'
//						,
//						myfilterfield : 'd.faffirmed',
//						myfiltername : '确认',
//						myfilterable : true
					}, {
						name : 'fassemble'
					},{
						name : 'fiscombinecrosssubs'
//					},{
//						name : 'salefnumber'
					},{
						name : 'fparentorderid'
						
					},{
						name : 'ftype',
						myfilterfield : 'd.ftype',
						myfiltername : '订单类型',
						myfilterable : true
					},{
						name:'fdescription'
					}],
		columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
						text:'产品ID',
						dataIndex:'fproductid',
						hidden : true,
						hideable : false,
						sortable : true
					},{
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
						'header' : '导入接口',
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
//							text : '创建人',
//							dataIndex : 'u1_fname',
//							sortable : true
//						}, {
//							text : '创建时间',
//							dataIndex : 'fcreatetime',
//							width : 150,
//							sortable : true
//
//					},{
//							text : '修改人',
//							dataIndex : 'u2_fname',
//							sortable : true
//						}, {
//							text : '修改时间',
//							dataIndex : 'flastupdatetime',
//							width : 150,
//							sortable : true
//						},
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
//						{
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
//					}, 
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
//						{
//						'header' : '生产订单号',
//						'dataIndex' : 'salefnumber',
//						sortable : true  
//						}, {
//						'header' : '生产分录',
//						'dataIndex' : 'fseq',
//						sortable : true
					},{
						header:'备注',
						dataIndex:'fdescription'
					}],
					selModel:Ext.create('Ext.selection.CheckboxModel')
		})
		

		