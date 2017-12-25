Ext
		.define(
				'DJ.finance.Gathering.GatheringEdit',
				{
					extend : 'Ext.c.BaseEditUI',
					id : 'DJ.finance.Gathering.GatheringEdit',
					modal : true,
					title : "帐户支出编辑界面",
					ctype : "Gathering",
					width : 550,// 230, //Window宽度
					height : 300,// 137, //Window高度
					resizable : false,
					url : 'SaveGathering.do',
					infourl : 'getGatheringInfo.do',
					viewurl : 'getGatheringInfo.do',
					closable : true, // 关闭按钮，默认为true
					custbar : [
							],
					cverifyinput : function() {
						 //throw "数据异常，不能保存！";
					},
					initComponent : function() {
								Ext
										.apply(
												this,
												{
													items : [{
														layout : "column",
														baseCls : "x-plain",
														items : [{// title:"列1",
															baseCls : "x-plain",
															columnWidth : .5,
															bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
															items : [{
																		name : 'fid',
																		xtype : 'textfield',
																		labelWidth : 70,
																		hidden : true
																	},
																	{
																		name : 'FNUMBER',
																		xtype : 'textfield',
																		fieldLabel : '单据编号',
																		labelWidth : 70
																	},
																	{
																		name : 'deliverapplynum',
																		xtype : 'textfield',
																		fieldLabel : '要货申请',
																		labelWidth : 70
																	},
																	{
																		name : 'saledelivernum',
																		xtype : 'textfield',
																		fieldLabel : '出库单号',
																		labelWidth : 70
																	}, {
																		name : 'custproductname',
																		xtype : 'textfield',
																		fieldLabel : '客户产品',
																		labelWidth : 70
																	},
																	{
																		name : 'FAMOUNT',
																		xtype : 'textfield',
																		fieldLabel : '发货数量',
																		labelWidth : 70
																	},
																	{
																		name : 'FSUM',
																		xtype : 'textfield',
																		fieldLabel : '金额',
																		labelWidth : 70
																	},
																	{
																		name : 'FCREATETIME',
																		xtype : 'textfield',
																		fieldLabel : '创建时间',
																		labelWidth : 70
																	}
																	
																	
//																	, {
//																		name : 'fcreatorid',
//																		xtype : 'textfield',
//																		labelWidth : 70,
//																		hidden : true
//																	}, {
//																		name : 'fnumber',
//																		xtype : 'textfield',
//																		fieldLabel : '编码',
//																		allowBlank : false,
//																		blankText : '编码不能为空',
//																		regex : /^([\u4E00-\u9FA5]|\w|[@.()\-])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//																		regexText : "不能包含特殊字符",
//																		labelWidth : 70
//																	}, {
//																		name : 'fmnemoniccode',
//																		xtype : 'textfield',
//																		fieldLabel : '助记码',
//																		labelWidth : 70
//																	}, {
//																		name : 'fbarcode',
//																		xtype : 'textfield',
//																		fieldLabel : '条形码',
//																		labelWidth : 70
//																	}, {
//																		name : 'fbusiexequatur',
//																		xtype : 'textfield',
//																		fieldLabel : '经营许可证',
//																		labelWidth : 70
//																	}, {
//																		name : 'femail',
//																		xtype : 'textfield',
//																		fieldLabel : '邮箱',
//																		regex : /^(\w|[.])*@(\w|[@.])*\.(\w|[.])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//																		regexText : "不能包含特殊字符",
//																		labelWidth : 70
//																	}
//																	, {
//																		id : 'DJ.System.supplier.SupplierEdit.FWarehouseID',
//													    	   		name:'fwarehouseid',
//													        		fieldLabel : '仓库',
//													        		valueField : 'fid', // 组件隐藏值
//													        		 allowBlank : false,
//												    	        	 blankText:'请选择仓库',
//																xtype : 'cCombobox',
//																labelWidth : 70,
//																displayField : 'fname',// 组件显示值
//																MyConfig : {
//																	width : 800,// 下拉界面
//																	height : 200,// 下拉界面
//																	url : 'GetWarehouses.do', // 下拉数据来源
//																	fields : [{
//																				name : 'fid'
//																			}, {
//																				name : 'fname',
//																				myfilterfield : 'w.fname', // 查找字段，发送到服务端
//																				myfiltername : '仓库名称',// 在过滤下拉框中显示的名称
//																				myfilterable : true
//																				// 该字段是否查找字段
//																			}, {
//																				name : 'fsimplename'
//																			}, {
//																				name : 'ufname'
//																			}, {
//																				name : 'fcontrollerid'
//																			}, {
//																				name : 'cfaddresid'
//																			}, {
//																				name : 'dfname'
//																			},{
//																				name:'fdescription'
//																			}, {
//																				name : 'foutstorage'
//																			}, {
//																				name : 'finstorage'
//																			}, {
//																				name : 'cffreightprice'
//																			}, {
//																				name : 'fwarehousetype'
//																			}, {
//																				name : 'flastupdatetime'
//																			}, {
//																				name : 'lfname'
//																			}, {
//																				name : 'fcreatetime'
//																			}, {
//																				name : 'cfname'
//																			}],
//																	columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
//																				'header' : 'fid',
//																				'dataIndex' : 'fid',
//																				hidden : true,
//																				hideable : false,
//																				sortable : true
//																			}, {
//																				'header' : '编码',
//																				'dataIndex' : 'fnumber',
//																				sortable : true
//																			}, {
//																			'header' : '名称',
//																			'dataIndex' : 'fname',
//																			sortable : true
//																			}, {
//																				'header' : '简称',
//																				'dataIndex' : 'fsimplename',
//																				sortable : true
//																			}, {
//																				'header' : '管理员',
//																				'dataIndex' : 'ufname',
//																				sortable : true
//																			}, {
//																				'header' : '送货地址',
//																				'dataIndex' : 'dfname',
//																				sortable : true
//																			}, {
//																				'header' : '描述',
//																				'dataIndex' : 'fdescription',
//																				sortable : true
//																			},{
//																				'header' : '修改人',
//																					dataIndex : 'lfname',
//																					sortable : true
//																				}, {
//																					'header' : '修改时间',
//																					dataIndex : 'flastupdatetime',
//																					width : 150,
//																					sortable : true
//																				}, {
//																					'header' : '创建人',
//																					dataIndex : 'cfname',
//																					sortable : true
//																				}, {
//																					'header' : '创建时间',
//																					dataIndex : 'fcreatetime',
//																					width : 150,
//																					sortable : true
//																				}, {
//																					'header' : '出库计件(元/m2)',
//																					dataIndex : 'foutstorage',
//																					sortable : true
//																				}, {
//																					'header' : '入库计件(元/m2)',
//																					dataIndex : 'finstorage',
//																					sortable : true
//																				}, {
//																					'header' : '仓库类型',
//																					dataIndex : 'fwarehousetype',
//																					sortable : true,
//																					 renderer: function(value){
//																					        if (value == 1) {
//																					            return '成品仓库';
//																					        }else if(value==2)
//																					        	{
//																					        	return '半成品';}
//																					        else{
//																					        	return '未分类';
//																					        }
//																					    }
//																				}, {
//																					'header' : '运费单价',
//																					dataIndex : 'cffreightprice',
//																					sortable : true,
//																					xtype: 'numbercolumn',
//																					format:'0.0000'
//																			}]
//																	}
//													    	   		}
//													    	   		, {
//																		name : 'femail',
//																		xtype : 'textfield',
//																		fieldLabel : '邮箱',
//																		regex : /^(\w|[.])*@(\w|[@.])*\.(\w|[.])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//																		regexText : "不能包含特殊字符",
//																		labelWidth : 70
//																	}
//													    	   		,{
//																		name : 'fusedstatus',
//																		xtype : 'textfield',
//																		fieldLabel : '状态',
//																		hidden : true
//												
//																	}
												
															]
														}, {	// title:"列2",
																	baseCls : "x-plain",
																	columnWidth : .5,
																	bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
																	items : [
																			Ext.create('Ext.ux.form.DateTimeField', {
																				fieldLabel : '业务日期',
																				labelWidth : 70,
//																				width : 260,
																				name : 'FBIZDATE',
																				format : 'Y-m-d',
																				allowBlank : false,
																				blankText : '配送时间不能为空'
																			}),
																			{
																				name : 'deliverordernum',
																				xtype : 'textfield',
																				fieldLabel : '配送单号',
																				labelWidth : 70
																			},
																			{
																				name : 'productplannum',
																				xtype : 'textfield',
																				fieldLabel : '制造商订单',
																				labelWidth : 70
																			},
																			{
																				name : 'deliversnum',
																				xtype : 'textfield',
																				fieldLabel : '要货管理',
																				labelWidth : 70
																			},
																			{
																				name : 'FPRICE',
																				xtype : 'textfield',
																				fieldLabel : '单价',
																				labelWidth : 70
																			},
																			{
																				name : 'creator',
																				xtype : 'textfield',
																				fieldLabel : '创建人',
																				labelWidth : 70
																			}
																			
//																			, {
//																				name : 'fname',
//																				xtype : 'textfield',
//																				fieldLabel : '名称',
//																				style : "padding:5px",
//																				allowBlank : false,
//																				blankText : '名称不能为空',
//																				regex : /^([\u4E00-\u9FA5]|\w|[()\-])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//																				regexText : "不能包含特殊字符",
//																				labelWidth : 70
//												
//																			}, {
//																				name : 'fforeignname',
//																				xtype : 'textfield',
//																				fieldLabel : '外文名称',
//																				labelWidth : 70
//																			}, {
//																				name : 'ftaxregisterno',
//																				xtype : 'textfield',
//																				fieldLabel : '税务登记号',
//																				labelWidth : 70
//																			}, {
//																				name : 'fbusilicence',
//																				xtype : 'textfield',
//																				fieldLabel : '营业执照',
//																				labelWidth : 70
//																			}, {
//																				name : 'ftel',
//																				xtype : 'textfield',
//																				fieldLabel : '手机',
//																				regex : /^1[3|4|5|8][0-9]\d{4,8}$/,// /^[^,\!@#$%^&*()_+}]*$/,
//																				regexText : "你输入的不是手机号",
//																				labelWidth : 70
//																			}, {
//																				name : 'femail',
//																				xtype : 'textfield',
//																				fieldLabel : '邮箱',
//																				regex : /^(\w|[.])*@(\w|[@.])*\.(\w|[.])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//																				regexText : "不能包含特殊字符",
//																				labelWidth : 70
//																			}
//																			,{
//																				id : 'DJ.System.supplier.SupplierEdit.FWarehouseSiteID',
//														    	   				name:'fwarehousesiteid',
//												  	        					fieldLabel : '库位',
//												  	        					valueField : 'fid', // 组件隐藏值
//																				xtype : 'cCombobox',
//																				labelWidth : 70,
//																				 allowBlank : false,
//												    	        	 		 	  blankText:'请选择库位',
//																				displayField : 'fname',// 组件显示值
//																				beforeExpand : function() {
//																				var warehouseid = Ext.getCmp("DJ.System.supplier.SupplierEdit.FWarehouseID").getValue();//_combo.getValue();
//														        	    		Ext.getCmp("DJ.System.supplier.SupplierEdit.FWarehouseSiteID").setDefaultfilter([{
//																					myfilterfield : "s.fparentid",
//																					CompareType : "like",
//																					type : "string",
//																					value : warehouseid
//																				}]);
//																				Ext.getCmp("DJ.System.supplier.SupplierEdit.FWarehouseSiteID").setDefaultmaskstring(" #0 ");
//																			},
//																			MyConfig : {
//																				width : 800,// 下拉界面
//																				height : 200,// 下拉界面
//																				url : 'GetWarehousesiteList.do', // 下拉数据来源
//																				fields : [
//																				          		{
//																									name : 'fid'
//																								}, {
//																									name : 'fnumber'
//																								}, {
//																									name : 'fname',
//																									myfilterfield : 'w.fname',
//																									myfiltername : '库位名称',
//																									myfilterable : true
//																								}, {
//																									name : 'wfname'
//																								}, {
//																									name : 'fparentid'
//																								}, {
//																									name : 'faddress'
//																								},{
//																									name:'fremark'
//																								}, {
//																									name : 'finstoreprice'
//																								}, {
//																									name : 'foutstoreprice'
//																								}, {
//																									name : 'farea'
//																								}, {
//																									name : 'flastupdatetime'
//																								}, {
//																									name : 'lfname'
//																								}, {
//																									name : 'fcreatetime'
//																								}, {
//																									name : 'cfname'
//																								}
//																							],
//																				columns : [Ext.create('DJ.Base.Grid.GridRowNum'),
//																				           {
//																								'header' : 'fid',
//																								'dataIndex' : 'fid',
//																								hidden : true,
//																								hideable : false,
//																								sortable : true
//																							}, {
//																								'header' : '仓位编码',
//																								'dataIndex' : 'fnumber',
//																								sortable : true
//																							}, {
//																							'header' : '仓位名称',
//																							'dataIndex' : 'fname',
//																							sortable : true
//																							}, {
//																								'header' : '所属仓库',
//																								'dataIndex' : 'wfname',
//																								sortable : true
//																							}, {
//																								'header' : '容量(m2)',
//																								'dataIndex' : 'farea',
//																								sortable : true,
//																								xtype: 'numbercolumn',
//																								format:'0.0000'
//														
//																							}, {
//																								'header' : '出库计件(元/m2)',
//																								dataIndex : 'foutstoreprice',
//																								sortable : true,
//																								xtype: 'numbercolumn',
//																								format:'0.0000'
//																							}, {
//																								'header' : '入库计件(元/m2)',
//																								dataIndex : 'finstoreprice',
//																								sortable : true,
//																								format:'0.0000',
//																								xtype: 'numbercolumn'
//																							}, {
//																								'header' : '地址',
//																								'dataIndex' : 'faddress',
//																								sortable : true
//																							}, {
//																								'header' : '备注',
//																								'dataIndex' : 'fremark',
//																								sortable : true
//																							},{
//																								'header' : '修改人',
//																									dataIndex : 'lfname',
//																									sortable : true
//																								}, {
//																									'header' : '修改时间',
//																									dataIndex : 'flastupdatetime',
//																									width : 150,
//																									sortable : true
//																								}, {
//																									'header' : '创建人',
//																									dataIndex : 'cfname',
//																									sortable : true
//																								}, {
//																									'header' : '创建时间',
//																									dataIndex : 'fcreatetime',
//																									width : 150,
//																									sortable : true
//																							}
//																						]
//																					}
//														    	   		}
//														    	   		,{
//																				name : 'fcreatetime',
//																				xtype : 'textfield',
//																				hidden : true
//												
//																			}
																	]
																}
//																, {// title:"列3",
//																	baseCls : "x-plain",
//																	columnWidth : .3,
//																	bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
//																	items : [{
//																				name : 'fsimplename',
//																				xtype : 'textfield',
//																				fieldLabel : '简称',
//																				labelWidth : 70
//																			}, {
//																				name : 'fbizregisterno',
//																				xtype : 'textfield',
//																				fieldLabel : '工商注册号',
//																				labelWidth : 70
//																			}, {
//																				name : 'fartificialperson',
//																				xtype : 'textfield',
//																				fieldLabel : '法人代表',
//																				labelWidth : 70
//																			}, {
//																				name : 'fgspauthentication',
//																				xtype : 'textfield',
//																				fieldLabel : 'GSP认证',
//																				labelWidth : 70
//																			}
//																	]
//																}
//																, {// title:"列2",
//																	baseCls : "x-plain",
//																	columnWidth : 820,
//																	bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
//																	items : [{
//																				name : 'faddress',
//																				xtype : 'textfield',
//																				width : 690,
//																				fieldLabel : '地址',
//																				labelWidth : 70
//																			}, {
//																				name : 'fdescription',
//																				xtype : 'textfield',
//																				width : 690,
//																				fieldLabel : '描述',
//																				labelWidth : 70
//																			}
//												
//																	]
//																}
																]
																
													}]
												}), this.callParent(arguments);
					},
				 bodyStyle : "padding-top:10px;padding-left:20px"
				});