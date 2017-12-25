/**
 * 启用/禁用功能
 * @param btn
 */
function actionEffectButtonClick(btn) {
	var grid = Ext.getCmp("DJ.System.UserIPList");
	var feffect = 0;
	if (Ext.util.Format.trim(btn.text) == "启    用") {
		feffect = 1;
	}
	var record = grid.getSelectionModel().getSelection();
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
		return;
	}
	Ext.MessageBox.confirm("提示", "是否确认启用选中的用户?", function(btn) {
		if (btn == 'yes') {
			var ids = "";
			for (var i = 0; i < record.length; i++) {
				var FUSERID = record[i].get("FUSERID");
				var fid = record[i].get("fid");
				if (feffect == 0
						&& Ext.util.Format.trim(FUSERID) == "0f20f5bf-a80b-11e2-b222-60a44c5bbef3") {
					Ext.MessageBox.alert('提示', '不能禁用超级用户!');
					return;
				}
				ids += fid ;
				if (i < record.length - 1) {
					ids = ids + ",";
				}
			}
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
						url : "EffectUserIPList.do",
						params : {
							feffect : feffect,
							fidcls : ids
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								Ext.MessageBox.alert('成功', obj.msg);
								grid.store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});
		}
	});
}

Ext.define('DJ.System.UserIPList', {
			extend : 'Ext.c.GridPanel',
			title : "用户IP",
			id : 'DJ.System.UserIPList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetUserIPList.do',
			Delurl : "DelUserIPList.do",
			EditUI : "DJ.System.UserIPEdit",
			exporturl:"UserIPtoExcel.do",//导出为EXCEL方法
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
//				alert("DeliverList");
//				Ext.getCmp("DJ.System.UserIPList.addbutton").setVisible(false);
//				Ext.getCmp("DJ.System.UserIPList.editbutton").setVisible(false);
//				Ext.getCmp("DJ.System.UserIPList.delbutton").setVisible(false);
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
					{
						// id : 'DelButton',
						text : '启    用',
						height : 30,
						handler : actionEffectButtonClick
					}, {
						// id : 'DelButton',
						text : '禁    用',
						height : 30,
						handler : actionEffectButtonClick
					}
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
					{name : 'FUSERID'},
					{name : 'fuser',
						myfilterfield : 'u1.fname',
						myfiltername : '用户名称',
						myfilterable : true
					},
					{name : 'FIP',
						myfilterfield : 'u.FIP',
						myfiltername : '用户IP',
						myfilterable : true
					},
					{name : 'FCREATORID'},
					{name : 'fcreator',
						myfilterfield : 'u2.fname',
						myfiltername : '创建人',
						myfilterable : true
					},
					{name : 'FCREATETIME',
						myfilterfield : 't.fbizdate',
						myfiltername : '创建时间',
						myfilterable : true
					},
					{name : 'FEFECTOR'},
					{name : 'efector',
						myfilterfield : 'u3.fname',
						myfiltername : '启禁人',
						myfilterable : true
					},
					{name : 'FEFECTTIME'},
					{name : 'FEFECTED'}
					],
			columns : [
					{	'header' : 'FID',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					},
					{	
						'header' : '用户名称',
						'dataIndex' : 'fuser',
						sortable : true
					},
					{	
						'header' : '用户IP',
						'dataIndex' : 'FIP',
						sortable : true
					},
					{	
						'header' : '创建人',
						'dataIndex' : 'fcreator',
						sortable : true
					},
					{	
						'header' : '创建时间',
						'dataIndex' : 'FCREATETIME',
						width : 160,
						sortable : true
					},
					{	
						'header' : '启禁人',
						'dataIndex' : 'efector',
						sortable : true
					},
					{	
						'header' : '启禁时间',
						'dataIndex' : 'FEFECTTIME',
						width : 160,
						sortable : true
					},
					{	
						'header' : '启用或禁用',
						'dataIndex' : 'FEFECTED',
						sortable : true,
						renderer : function(value){
							if(value==1){
								return '启用';
							}else{
								return '禁用';	
							}
						}
					}
					
//					,{
//					'header' : '审核人',
//						'dataIndex' : 'fauditor',
//						sortable : true
//					},
//					{
//					'header' : '审核时间',
//						'dataIndex' : 'fauditdate',
//						sortable : true
//					},
//					{
//					'header' : '是否审核',
//						'dataIndex' : 'faudited',
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
//													url : 'GetCustproductList.do', // 下拉数据来源
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
