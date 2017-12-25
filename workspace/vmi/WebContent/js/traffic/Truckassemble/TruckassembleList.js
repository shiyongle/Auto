
Ext.define('DJ.traffic.Truckassemble.TruckassembleList', {
			extend : 'Ext.c.GridPanel',
			title : "提货单",
			id : 'DJ.traffic.Truckassemble.TruckassembleList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetTruckassembleList.do',
			Delurl : "DelTruckassembleList.do",//DelTruckassembleList.do
			EditUI : "DJ.traffic.Truckassemble.TruckassembleEdit",
			exporturl:"Truckassembletoexcel.do",//导出为EXCEL方法
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
//				alert("DeliverList");
				Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleList.addbutton").setVisible(false);
				Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleList.editbutton").setVisible(false);
				Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleList.delbutton").setVisible(false);
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
			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			}
			,custbar : 
				[
//					{
//						// id : 'DelButton',
//						text : 'Excel文件上传',
//						height : 30,
//						handler : function() {
//							var loadupFormpanel = Ext.getCmp("DJ.order.Deliver.generateDeliversList.loadupFormpanel");
//							if(loadupFormpanel==null){
//								loadupFormpanel = Ext.create("DJ.order.Deliver.generateDeliversList.loadupFormpanel");
//							}
//	    					loadupFormpanel.show();	
//						}
//					},
//				 	{
//						text : '车辆提货',
//						height : 30,
//						handler : function() {
//							var grid = Ext.getCmp("DJ.order.Deliver.generateDeliversList");
//							var record = grid.getSelectionModel().getSelection();
//							if(record.length<1){
//								Ext.MessageBox.alert("信息","请选择至少一条记录导入EAS！");
//								return;
//							}
//							var ids = "(";
//							for ( var i = 0; i < record.length; i++) {
//								if(record[i].get("cutpdtname").length<1 || record[i].get("cutpdtname") == 'null'){
//									Ext.MessageBox.alert("信息","‘客户产品’为空不能生成！");
//									return;
//								}
//								
//								if(record[i].get("faddress").length<1 || record[i].get("faddress") == 'null'){
//									Ext.MessageBox.alert("信息","‘地址’为空不能生成！");
//									return;
//								}
//								
//								if(!record[i].get("fisread").length<1 && record[i].get("fisread") == '1'){
//									Ext.MessageBox.alert("信息","已读取不能再生成要货申请！");
//									return;
//								}
//								
//								var fid = record[i].get("fid");
//								ids += "'" + fid + "'";
//								if (i < record.length - 1) {
//									ids = ids + ",";
//								}
//							}
//							ids = ids + ")";
//							var el = grid.getEl();
//							el.mask("系统处理中,请稍候……");
//							Ext.Ajax.request({
//								timeout : 600000,
//								url : "generateDelivers.do",
//								params : {
//									fids : ids
//								}, // 参数
//								success : function(response, option) {
//									var obj = Ext.decode(response.responseText);
//									if (obj.success == true) {
//										Ext.MessageBox.alert('成功', obj.msg);
//										Ext.getCmp("DJ.order.Deliver.generateDeliversList").store
//												.load();
//									} else {
//										Ext.MessageBox.alert('错误', obj.msg);
//									}
//									el.unmask();
//								}
//							});
//						}
//					}
				]
			,fields : [
//					{
//						name : 'fid'
//					}, {
//						name : 'faddressid'
//					}
//					, {
//						name : 'faddress',
//						myfilterfield : 'd.faddress',
//						myfiltername : '配送地址',
//						myfilterable : true
//					}, {
//						name : 'fnumber',
//						myfilterfield : 'd.fnumber',
//						myfiltername : '申请单号',
//						myfilterable : true
//					}, {
//						name : 'fcustomerid'
//					}, {
//						name : 'fcusproductid'
//					}, {
//						name : 'fcustname',
//						myfilterfield : 'c.fname',
//						myfiltername : '客户名称',
//						myfilterable : true
//					}, {
//						name : 'cutpdtname',
//						myfilterfield : 'cpdt.fname',
//						myfiltername : '客户产品',
//						myfilterable : true
//					}, {
//						name : 'flinkman',
//						myfilterfield : 'd.flinkman',
//						myfiltername : '联系人',
//						myfilterable : true
//					}, {
//						name : 'flinkphone',
//						myfilterfield : 'd.flinkphone',
//						myfiltername : '联系电话',
//						myfilterable : true
//					}, {
//						name : 'famount'
//					}, {
//						name : 'fdescription'
//					}, {
//						name : 'farrivetime',
//						myfilterfield : 'd.farrivetime',
//						myfiltername : '配送时间',
//						myfilterable : true
//					}, {
//						name : 'fisread'
//					}
					{name : 'fid'},
					{name : 'fnumber',
					myfilterfield : 't.fnumber',
					myfiltername : '单据编号',
					myfilterable : true
					},
					{name : 'fbizdate',
					myfilterfield : 't.fbizdate',
					myfiltername : '业务日期',
					myfilterable : true},
					{name : 'fcreatorid'},
					{name : 'fcreatetime'},
					{name : 'flastupdateuserid'},
					{name : 'flastupdatetime'},
					{name : 'ftruckid',
					myfilterfield : 't.ftruckid',
					myfiltername : '车辆名称',
					myfilterable : true},
					{name : 'creator'},
					{name : 'updateuser'},
//					{name : 'fauditor',
//					myfilterfield : 'ad.fname',
//					myfiltername : '审核人',
//					myfilterable : true
//					},
//					{name : 'fauditdate'},
					{name : 'faudited',
					myfilterfield : 't.faudited',
					myfiltername : '审核',
					myfilterable : true
					},{
					name:"ftype",
					myfilterfield : 't.ftype',
					myfiltername : '发货类型',
					myfilterable : true
					}
					],
			columns : [
					{	'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					},
					{
						'header' : '单据编号',
						'dataIndex' : 'fnumber',
						sortable : true
					},
					{
						'header' : '业务日期',
						'dataIndex' : 'fbizdate',
						sortable : true
					},
					{
						'header' : '发货类型',
						'dataIndex' : 'ftype',
						sortable : true,
						renderer: function(value){
						        if (value == 1) {
						            return '协同发货';
						        }
						        else{
						        	return '自运发货';
						        }
						    }
					},
					{
						'header' : '制单人ID',
						'dataIndex' : 'fcreatorid',
						hidden : true,
						sortable : true
					},{
						'header' : '制单人',
						'dataIndex' : 'creator',
						sortable : true
					},
					{
						'header' : '制单时间',
						'dataIndex' : 'fcreatetime',
						sortable : true
					},
					{
						'header' : '修改人ID',
						'dataIndex' : 'flastupdateuserid',
						hidden : true,
						sortable : true
					},{
						'header' : '修改人',
						'dataIndex' : 'updateuser',
						sortable : true
					},
					{
						'header' : '修改时间',
						'dataIndex' : 'flastupdatetime',
						sortable : true
					},
					{
						'header' : '车辆名称',
						'dataIndex' : 'ftruckid',
						sortable : true
					},
//					{
//					'header' : '审核人',
//						'dataIndex' : 'fauditor',
//						sortable : true
//					},
//					{
//					'header' : '审核时间',
//						'dataIndex' : 'fauditdate',
//						sortable : true
//					},
					{
					'header' : '是否审核',
						'dataIndex' : 'faudited',
						sortable : true,
						renderer: function(value){
						        if (value == 1) {
						            return '是';
						        }
						        else{
						        	return '否';
						        }
						    }
					}
//					{
//						'header' : 'fid',
//						'dataIndex' : 'fid',
//						hidden : true,
//						hideable : false,
//						sortable : true
//
//					}, {
//						'header' : 'fcustomerid',
//						'dataIndex' : 'fcustomerid',
//						hidden : true,
//						hideable : false,
//						sortable : true
//
//					}, {
//						'header' : 'faddressid',
//						'dataIndex' : 'faddressid',
//						hidden : true,
//						hideable : false,
//						sortable : true
//					}, {
//						'header' : 'fcusproductid',
//						'dataIndex' : 'fcusproductid',
//						hidden : true,
//						hideable : false,
//						sortable : true
//
//					}, {
//						'header' : '客户名称',
//						'dataIndex' : 'fcustname',
//						sortable : true
//					}, {
//						'header' : '申请单号',
//						'dataIndex' : 'fnumber',
//						sortable : true
//					},{
//						'header' : '客户产品',
//						'dataIndex' : 'cutpdtname',
//                    editor: {
//												valueField : 'fname', // 组件隐藏值
//												id : "DJ.order.Deliver.generateDeliversList.fcusproductid",
//												name : "cutpdtname",
//												xtype : 'cCombobox',
//												displayField : 'fname',// 组件显示值
////												fieldLabel : '客户产品 ',
//												width : 260,
//												labelWidth : 70,
//												listeners : {
//													change : function(com) {
//														var rows = Ext
//																.getCmp("DJ.order.Deliver.generateDeliversList")
//																.getSelectionModel().getSelection();
//														rows[0].set("fcusproductid", com.getValue());
//														rows[0].set("cutpdtname", com.getRawValue());
//														Ext.Ajax.request({
//																timeout: 600000,
//																url : "SaveGenerateDelivers.do",
//																params : {
//																	fid : rows[0].get("fid"),
//																	cutpdtname : rows[0].get("cutpdtname"),
//																	faddress : rows[0].get("faddress")
//																}, // 参数
//																success : function(response, option) {
//																	var obj = Ext.decode(response.responseText);
//																	if (obj.success == true) {
//																		Ext.MessageBox.alert('成功', obj.msg);
//																		Ext.getCmp("DJ.order.Deliver.generateDeliversList").store.load();
//																		rows[0].set("cutpdtname", com.getRawValue());
//																	} else {
//																		Ext.MessageBox.alert('错误', obj.msg);
//																	}
//																}
//															});
//													}
//												},
//												MyConfig : {
//													width : 800,// 下拉界面
//													height : 200,// 下拉界面
//													url : 'GetProducts.do', // 下拉数据来源
//													fields : [
//															 	{
//																	name : 'fid'
//																}, {
//																	name : 'fname',
//																	myfilterfield : 't_bd_Custproduct.fname', 		// 查找字段，发送到服务端
//																	myfiltername : '名称',							// 在过滤下拉框中显示的名称
//																	myfilterable : true								// 该字段是否查找字段
//																}, {
//																	name : 'fnumber',
//																	myfilterfield : 't_bd_Custproduct.fnumber', 	// 查找字段，发送到服务端
//																	myfiltername : '编码',							// 在过滤下拉框中显示的名称
//																	myfilterable : true								// 该字段是否查找字段
//																}, {
//																	name : 'fspec'
//																}, {
//																	name : 'forderunit'
//																}, {
//																	name : 'fcustomerid'
//																}, {
//																	name : 'fdescription'
//																}, {
//																	name : 'fcreatorid'
//																}, {
//																	name : 'fcreatetime'
//																}, {
//																	name : 'flastupdateuserid'
//																}, {
//																	name : 'flastupdatetime'
//																}
//																	
//															],
//													columns : [{
//																	'header' : 'fid',
//																	'dataIndex' : 'fid',
//																	hidden : true,
//																	hideable : false,
//																	autoHeight: true, autoWidth:true,
//																	sortable : true
//																}, {
//																	'header' : '产品名称',
//																	'dataIndex' : 'fname',
//																	sortable : true,
//																	filter : {
//																		type : 'string'
//																	}
//																}, {
//																	'header' : '编码',
//																	'dataIndex' : 'fnumber',
//																	sortable : true,
//																	filter : {
//																		type : 'string'
//																	}
//																}, {
//																	'header' : '规格',
//																	width : 70,
//																	'dataIndex' : 'fspec',
//																	sortable : true
//																}, {
//																	'header' : '单位',
//																	width : 70,
//																	'dataIndex' : 'forderunit',
//																	sortable : true
//																}, {
//																	'header' : '客户',
//																	hidden : true,
//																	'dataIndex' : 'fcustomerid',
//																	sortable : true
//																}, {
//																	'header' : '修改时间',
//																	'dataIndex' : 'flastupdatetime',
//																	filter : {
//																		type : 'datetime',
//																		date: {
//																            format: 'Y-m-d'
//																        },
//																        time: {
//																            format: 'H:i:s A',
//																            increment: 1
//																        }
//																	},
//																	width : 140,
//																	sortable : true
//																}, {
//																	'header' : '创建时间',
//																	'dataIndex' : 'fcreatetime',
//																	filter : {
//																		type : 'datetime',
//																		date: {
//																            format: 'Y-m-d'
//																        },
//																        time: {
//																            format: 'H:i:s A',
//																            increment: 1
//																        }
//																	},
//																	width : 140,
//																	sortable : true
//																}, {
//																	'header' : '描述',
//																	hidden : true,
//																	'dataIndex' : 'fdescription',
//																	sortable : true
//																}
//															]
//												}
//											},
//						sortable : true
//					}, {
//						'header' : '配送时间',
//						'dataIndex' : 'farrivetime',
//						width : 100,
//						sortable : true
//					}, {
//						'header' : '联系人',
//						'dataIndex' : 'flinkman',
//						sortable : true
//					}, {
//						'header' : '联系电话',
//						'dataIndex' : 'flinkphone',
//						sortable : true
//					}, {
//						'header' : '配送数量',
//						'dataIndex' : 'famount',
//						sortable : true
//					},{
//						'header' : '配送地址',
//						'dataIndex' : 'faddress',
//                    editor: {
//													valueField : 'fname', // 组件隐藏值
//													name : "faddress",
//													xtype : 'cCombobox',
//													width : 260,
//													labelWidth : 70,
//													displayField : 'fname',// 组件显示值
////													fieldLabel : '配送地址 ',
//													listeners : {
//														change : function(com) {
//															var rows = Ext.getCmp("DJ.order.Deliver.generateDeliversList")
//																	.getSelectionModel().getSelection();
//															rows[0].set("faddressid", com.getValue());
//															rows[0].set("faddress", com.getRawValue());
//																					
//																Ext.Ajax.request({
//																		timeout: 600000,
//																		url : "SaveGenerateDelivers.do",
//																		params : {
//																			fid : rows[0].get("fid"),
//																			faddress : rows[0].get("faddress"),
//																			cutpdtname : rows[0].get("cutpdtname")
//																		}, // 参数
//																		success : function(response, option) {
//																			var obj = Ext.decode(response.responseText);
//																			if (obj.success == true) {
//																				Ext.MessageBox.alert('成功', obj.msg);
//																				Ext.getCmp("DJ.order.Deliver.generateDeliversList").store.load();
//																				rows[0].set("faddress", com.getRawValue());
//																			} else {
//																				Ext.MessageBox.alert('错误', obj.msg);
//																			}
//																		}
//																	});
//														}
//													},
//													MyConfig : {
//														width : 800,// 下拉界面
//														height : 200,// 下拉界面
//														url : 'GetAddressList.do', // 下拉数据来源
//														fields : [	{
//																		name : 'fid'
//																	}, {
//																		name : 'fname',
//																		myfilterfield : 'fname',
//																		myfiltername : '名称',
//																		myfilterable : true
//																	}, {
//																		name : 'fnumber',
//																		myfilterfield : 'fnumber',
//																		myfiltername : '编码',
//																		myfilterable : true
//																	}, {
//																		name : 'fcreatorid'
//																	}, {
//																		name : 'flastupdateuserid'
//																	}, {
//																		name : 'fcontrolunitid'
//																	}, {
//																		name : 'fdetailaddress'
//																	}, {
//																		name : 'fcountryid'
//																	}, {
//																		name : 'fcityidid'
//																	}, {
//																		name : 'femail'
//																	}, {
//																		name : 'flinkman'
//																	}, {
//																		name : 'fphone'
//																	}, {
//																		name : 'fprovinceid'
//																	}, {
//																		name : 'fdistrictidid'
//																	}, {
//																		name : 'fpostalcode'
//																	}, {
//																		name : 'ffax'
//																	}, {
//																		name : 'fcreatetime'
//																	}, {
//																		name : 'flastupdatetime'
//																	}
//																],
//														columns : [
//														           	{
//																		'header' : 'fid',
//																		'dataIndex' : 'fid',
//																		hidden : true,
//																		hideable : false,
//																		sortable : true
//			
//																	},{
//																		'header' : 'fcreatorid',
//																		'dataIndex' : 'fcreatorid',
//																		hidden : true,
//																		hideable : false,
//																		sortable : true
//			
//																	}, {
//																		'header' : 'flastupdateuserid',
//																		'dataIndex' : 'flastupdateuserid',
//																		hidden : true,
//																		hideable : false,
//																		sortable : true
//			
//																	}, {
//																		'header' : 'fprovinceid',
//																		'dataIndex' : 'fprovinceid',
//																		hidden : true,
//																		hideable : false,
//																		sortable : true
//			
//																	}, {
//																		'header' : 'fcountryid',
//																		'dataIndex' : 'fcountryid',
//																		hidden : true,
//																		hideable : false,
//																		sortable : true
//			
//																	}, {
//																		'header' : 'fcityidid',
//																		'dataIndex' : 'fcityidid',
//																		hidden : true,
//																		hideable : false,
//																		sortable : true
//			
//																	}, {
//																		'header' : 'fdistrictidid',
//																		'dataIndex' : 'fdistrictidid',
//																		hidden : true,
//																		hideable : false,
//																		sortable : true
//			
//																	}, {
//																		'header' : 'fcontrolunitid',
//																		'dataIndex' : 'fcontrolunitid',
//																		hidden : true,
//																		hideable : false,
//																		sortable : true
//			
//																	}, {
//																		'header' : '地址名称',
//																		'dataIndex' : 'fname',
//																		width : 405,
//																		sortable : true
//																	}, {
//																		'header' : '地址编码',
//																		'dataIndex' : 'fnumber',
//																		width : 70,
//																		sortable : true
//																	}, 
////																		{
////																		'header' : '邮政编码',
////																		'dataIndex' : 'fpostalcode',
////																		sortable : true
////																	}, 
//																		{
//																		'header' : '邮箱',
//																		'dataIndex' : 'femail',
//																		width : 50,
//																		sortable : true
//																	}, {
//																		'header' : '联系人',
//																		'dataIndex' : 'flinkman',
//																		width : 50,
//																		sortable : true
//																	},{
//																		'header' : '电话',
//																		'dataIndex' : 'fphone',
//																		width : 110,
//																		sortable : true
//																	},
////																		{
////																		'header' : '传真',
////																		'dataIndex' : 'ffax',
////																		sortable : true
////																	},
//																		{
//																		'header' : '详细地址',
//																		'dataIndex' : 'fdetailaddress',
//																		sortable : true
//																	}
////																	, {
////																		'header' : '创建时间',
////																		'dataIndex' : 'fcreatetime',
////																		width : 200,
////																		sortable : true
////																	}, {
////																		'header' : '修改时间',
////																		'dataIndex' : 'flastupdatetime',
////																		width : 200,
////																		sortable : true
////																	}
//																]
//													}
//												},
//						width : 200,
//						sortable : true
//					},{
//						'header' : '备注',
//						'dataIndex' : 'fdescription',
//						sortable : true
//					},{
//						'header' : '读取',
//						dataIndex : 'fisread',
//						sortable : true,
//						renderer: function(value){
//						        if (value == 1) {
//						            return '是';
//						        }
//						        else{
//						        	return '否';
//						        }
//						    }
//					}
					],
					selModel:Ext.create('Ext.selection.CheckboxModel')
//					,plugins: [
////                	Ext.create('Ext.grid.plugin.RowEditing', {
//					Ext.create('Ext.grid.plugin.CellEditing', {
//                })
//            ]

		});
