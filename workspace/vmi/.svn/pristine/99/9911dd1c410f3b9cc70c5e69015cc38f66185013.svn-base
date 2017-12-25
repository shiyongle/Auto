Ext
		.define(
				'DJ.test.productEdit',
				{
					extend : 'Ext.c.BaseEditUI',
					id : 'DJ.test.productEdit',
					modal : true,
					title : "用户管理编辑界面",
					ctype:"Productdef",
					width : 1250,// 230, //Window宽度
					height : 600,// 137, //Window高度
					resizable : false,
					url : 'SaveProductdef.do',
					infourl : 'GetProductInfo.do',
					viewurl : 'GetProductInfo.do',
					closable : true, // 关闭按钮，默认为true
					custbar : [
							{
								// id : 'DelButton',
								text : '自定义按钮1',
								height : 30,
								handler : function() {
//									Ext.getCmp('DJ.test.testBillEdit.table')
//									.setceditable(true);
									Ext.getCmp('DJ.test.testBillEdit.table')
									.addclockedcolumn("asdf");
									Ext.getCmp('DJ.test.testBillEdit.table')
											.addclockedcolumn("providername");
								}
							},
							{
								// id : 'DelButton',
								text : '自定义按钮2',
								height : 30,
								handler : function() {
//									Ext.getCmp('DJ.test.testBillEdit.table')
//											.setceditable(true);
									Ext.getCmp('DJ.test.testBillEdit.table')
									.removeclockedcolumn("providername");
								}
							} ],
							cverifyinput:function(){
								//throw "数据异常，不能保存！";
							},
					initComponent : function() {
								Ext
										.apply(
												this,
												{
													items : [
															{
																layout : 'column',
																baseCls : 'x-plain',
																items : [
																		{
																			bodyStyle : 'padding:5px;',
																			baseCls : 'x-plain',
																			columnWidth : .25,
																			layout : 'form',
																			defaults : {
																				xtype : 'textfield'
																			},
																			items : [
																					{
																						name : 'fnumber',
																						fieldLabel : '产品编码',
																						allowBlank : false,
																						blankText : '编码不能为空',
																						regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"
																					},
																					{
																						name : 'fcustomerid',
																						fieldLabel : '客户名称',
																						xtype : 'cCombobox',
																						displayField : 'fname', // 这个是设置下拉框中显示的值
																						valueField : 'fid', // 这个可以传到后台的值
																						MyConfig : {
																							width : 800,// 下拉界面
																							height : 200,// 下拉界面
																							url : 'GetCustomerList.do', // 下拉数据来源
																							fields : [
																									{
																										name : 'fid'
																									},
																									{
																										name : 'fname',
																										myfilterfield : 't_bd_customer.fname', // 查找字段，发送到服务端
																										myfiltername : '客户名称',// 在过滤下拉框中显示的名称
																										myfilterable : true
																									// 该字段是否查找字段
																									},
																									{
																										name : 'fnumber'
																									},
																									{
																										name : 'findustryid'
																									},
																									{
																										name : 'faddress'
																									},
																									{
																										name : 'fisinternalcompany',
																										convert : function(
																												value,
																												record) {
																											if (value == '1') {
																												return true;
																											} else {
																												return false;
																											}
																										}
																									} ],
																							columns : [
																									{
																										text : 'fid',
																										dataIndex : 'fid',
																										hidden : true,
																										sortable : true

																									},
																									{
																										text : '编码',
																										dataIndex : 'fnumber',
																										sortable : true
																									},
																									{
																										text : '客户名称',
																										dataIndex : 'fname',
																										sortable : true
																									},
																									{
																										text : '行业',
																										dataIndex : 'findustryid',
																										sortable : true
																									},
																									{
																										text : '地址',
																										dataIndex : 'faddress',
																										sortable : true,
																										width : 250
																									},
																									{
																										text : '内部客户',
																										dataIndex : 'fisinternalcompany',
																										xtype : 'checkcolumn',
																										processEvent : function() {
																										},
																										sortable : true
																									} ]
																						}
																					},
																					{
																						name : 'fmaterialcodeid',
																						fieldLabel : '用料代码',
																						regex : /^([\u4E00-\u9FA5]|\w|\S)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"
																					},
																					{

																						name : 'fstavetype',
																						fieldLabel : '跑线方式',
																						regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"

																					} ]
																		},
																		{
																			bodyStyle : 'padding:5px;',
																			baseCls : 'x-plain',
																			columnWidth : .25,
																			layout : 'form',
																			defaults : {
																				xtype : 'textfield'
																			},
																			items : [
																					{
																						xtype : 'textfield',
																						name : 'fname',
																						fieldLabel : '产品名称',
																						allowBlank : false,
																						blankText : '名称不能为空',
																						regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"
																					},
																					{
																						name : 'forderunitid',
																						fieldLabel : '计量单位',
																						regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"

																					},
																					{
																						name : 'ftilemodelid',
																						fieldLabel : '瓦楞楞型',
																						regex : /^([\u4E00-\u9FA5]|\w|\S)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"

																					},
																					{
																						name : 'fhstaveexp',
																						fieldLabel : '横向跑线公式',
																						regex : /^(\d|\+|\-|\*|\/|\(|\)|\[|\]|\.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"
																					} ]
																		},
																		{

																			bodyStyle : 'padding: 5px;',
																			baseCls : 'x-plain',
																			columnWidth : .25,
																			layout : 'form',
																			defaults : {
																				xtype : 'textfield'
																			},
																			items : [
																					{
																						name : 'fversion',
																						fieldLabel : '版本号',
																						regex : /^([\u4E00-\u9FA5]|\w|\.|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"

																					},
																					{
																						xtype : 'numberfield',
																						name : 'farea',
																						fieldLabel : '单位面积',
																						value : 0,
																						minValue : 0,
																						negativeText : '不能为负数'

																					},
																					{
																						xtype : 'numberfield',
																						name : 'flayer',
																						fieldLabel : '材料层数',
																						value : 0,
																						minValue : 0,
																						width : 74,
																						negativeText : '不能为负数'

																					},
																					{
																						name : 'fvstaveexp',
																						fieldLabel : '纵向跑线公式',
																						regex : /^(\d|\+|\-|\*|\/|\(|\)|\[|\]|\.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"
																					} ]
																		},
																		{

																			bodyStyle : 'padding: 5px;',
																			baseCls : 'x-plain',
																			columnWidth : .25,
																			layout : 'form',
																			defaults : {
																				xtype : 'textfield'
																			},
																			items : [
																					{
																						name : 'fnewtype',
																						fieldLabel : '产品类型(新)',
																						regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"

																					},
																					{
																						xtype : 'numberfield',
																						name : 'fmaterialcost',
																						fieldLabel : '材料成本',
																						value : 0,
																						minValue : 0,
																						negativeText : '不能为负数'
																					},
																					{

																						name : 'fmaterialcode',
																						fieldLabel : '用料编码',
																						regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"
																					},

																					{
																						name : 'fcleartype',
																						fieldLabel : '清费类型',
																						regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"
																					},
																					{

																						xtype : 'hidden',
																						name : 'fid'

																					} ]
																		} ]
															},
															{
																layout : 'column',
																baseCls : 'x-plain',
																bodyStyle : 'padding-left:5px;',
																items : [
																		{

																			baseCls : 'x-plain',
																			columnWidth : .25,
																			xtype : 'fieldcontainer',
																			fieldLabel : '纸箱规格',
																			layout : 'hbox',
																			items : [
																					{
																						xtype : 'numberfield',
																						name : 'fboxlength',
																						hideLabel : true,
																						value : 0,
																						minValue : 0,
																						width : 59,
																						negativeText : '不能为负数'
																					},
																					{
																						xtype : 'displayfield',
																						value : 'X',
																						width : 8
																					},
																					{
																						xtype : 'numberfield',
																						name : 'fboxwidth',
																						value : 0,
																						minValue : 0,
																						width : 59,
																						negativeText : '不能为负数'
																					},
																					{
																						xtype : 'displayfield',
																						value : 'X',
																						width : 8
																					},
																					{
																						xtype : 'numberfield',
																						name : 'fboxheight',
																						value : 0,
																						minValue : 0,
																						width : 59,
																						negativeText : '不能为负数'
																					} ]

																		},
																		{
																			baseCls : 'x-plain',
																			columnWidth : .25,
																			xtype : 'fieldcontainer',
																			fieldLabel : '纸板规格',
																			labelWidth : 101,
																			layout : 'hbox',
																			items : [
																					{
																						xtype : 'numberfield',
																						name : 'fboardlength',
																						hideLabel : true,
																						minValue : 0,
																						width : 93,
																						negativeText : '不能为负数'
																					},
																					{
																						xtype : 'displayfield',
																						value : 'X',
																						width : 8
																					},
																					{
																						xtype : 'numberfield',
																						id : "asdf",
																						name : 'fboardwidth',
																						minValue : 0,
																						width : 93,
																						negativeText : '不能为负数'

																					} ]

																		},
																		{

																			baseCls : 'x-plain',
																			columnWidth : .25,
																			xtype : 'fieldcontainer',
																			fieldLabel : '出库规格',
																			labelWidth : 102,
																			layout : 'hbox',
																			items : [
																					{
																						xtype : 'numberfield',
																						name : 'fmateriallength',
																						hideLabel : true,
																						value : 0,
																						minValue : 0,
																						width : 93,
																						negativeText : '不能为负数'
																					},
																					{
																						xtype : 'displayfield',
																						value : 'X',
																						width : 8
																					},
																					{
																						xtype : 'numberfield',
																						name : 'fmaterialwidth',
																						value : 0,
																						minValue : 0,
																						width : 93,
																						negativeText : '不能为负数'

																					} ]
																		} ]

															},
															{
																layout : 'column',
																baseCls : 'x-plain',
																items : [
																		{
																			bodyStyle : 'padding-left: 5px;padding-right: 5px;padding-top: 5px;',
																			baseCls : 'x-plain',
																			columnWidth : .25,
																			layout : 'form',
																			defaults : {
																				xtype : 'textfield'
																			},
																			items : [ {
																				xtype : 'textfield',
																				name : 'fboxmodelid',
																				fieldLabel : '箱型结构',
																				regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																				regexText : "不能包含特殊字符"
																			} ]
																		},
																		{
																			bodyStyle : 'padding-left: 5px;padding-right: 5px;padding-top: 5px;',
																			baseCls : 'x-plain',
																			columnWidth : .25,
																			layout : 'form',
																			defaults : {
																				xtype : 'textfield'
																			},
																			items : [ {
																				name : 'fcharacter',
																				fieldLabel : '产品特征',
																				regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																				regexText : "不能包含特殊字符"

																			} ]
																		},
																		{
																			bodyStyle : 'padding-left: 5px;padding-right: 5px;padding-top: 5px;',
																			baseCls : 'x-plain',
																			columnWidth : .25,
																			layout : 'form',
																			labelWidth : 20,
																			defaults : {
																				xtype : 'textfield'
																			},
																			items : [ {
																				xtype : 'numberfield',
																				value : 0,
																				minValue : 0,
																				negativeText : '不能为负数',
																				name : 'fchromaticprecision',
																				fieldLabel : '套色精度'

																			} ]
																		} ]
															},
															{
																layout : 'column',
																baseCls : 'x-plain',
																items : [
																		{
																			bodyStyle : 'padding-left:5px;padding-right:5px;',
																			baseCls : 'x-plain',
																			columnWidth : .5,
																			layout : 'form',
																			defaults : {
																				xtype : 'textfield'
																			},
																			items : [
																					{
																						xtype : 'textfield',
																						name : 'fquality',
																						fieldLabel : '品质要求',
																						regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"
																					},
																					{
																						xtype : 'textfield',
																						name : 'fdescription',
																						fieldLabel : '产品备注',
																						regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"

																					},
																					Ext
																							.create(
																									'Ext.ux.form.DateTimeField',
																									{
																										fieldLabel : '创建时间',
																										name : 'fcreatetime',
																										format : 'Y-m-d'
																									}) ]
																		},
																		{
																			bodyStyle : 'padding-left:5px;padding-right:5px;',
																			baseCls : 'x-plain',
																			columnWidth : .25,
																			layout : 'form',
																			defaults : {
																				xtype : 'textfield'
																			},
																			items : [

																					{
																						name : 'fcusproductname',
																						fieldLabel : '客户产品名称',
																						regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
																						regexText : "不能包含特殊字符"

																					},
																					{
																						xtype : 'checkbox',
																						inputValue:true,
																						name : 'forderprice',
																						boxLabel : '按单价设'
																					}

																			]
																		} ]
															},
															{
																xtype : 'cTable',
																name:"Productcycle",
																id : "DJ.test.testBillEdit.table",
																width : 1000,
																height : 200,
																pageSize : 100,
																url : "getLifeCycles.do",
																parentfield : "pc.FPRODUCTDEFID",
																fields : [
																		{
																			name : "fid"
																		},
																		{
																			name : "productid"
																		},
																		{
																			name : "fsupplierid",
																			defaultValue : ""
																		},
																		{
																			name : "providername"
																		},
																		{
																			name : "plifecycle",
																			type : "int"
																		},
																		{
																			name : "mproportion",
																			type : "float",
																			defaultValue : "0"
																		} ],
																columns : [
																		{
																			xtype : "rownumberer",
																			text : "No"
																		},
																		{
																			width : 164,
																			dataIndex : "providername",
																			text : "供应商",
																			editor : {
																				//name : "fsupplierid",
																				allowBlank : false,
																				blankText : "请选择供应商",
																				editable : false,
																				xtype : "cCombobox",
																				displayField : "fid",
																				valueField : "fname",
																				MyDataChanged : function(
																						com) {

																					var rows = Ext
																							.getCmp(
																									"DJ.test.testBillEdit.table")
																							.getSelectionModel()
																							.getSelection();
																					rows[0]
																							.set(
																									"fsupplierid",
																									com[0].data.fid)

																				},
//																				listeners : {
//																					change : function(
//																							com) {
//																						var rows = Ext
//																								.getCmp(
//																										"ProportionEditGridPanel")
//																								.getSelectionModel()
//																								.getSelection();
//																						rows[0]
//																								.set(
//																										"fsupplierid",
//																										com
//																												.getRawValue())
//																					}
//																				},
																				MyConfig : {
																					width : 800,
																					height : 200,
																					url : "GetSupplierList.do",
																					fields : [
																							{
																								name : "fid"
																							},
																							{
																								name : "fname",
																								myfilterfield : "fname",
																								myfiltername : "名称",
																								myfilterable : true
																							},
																							{
																								name : "fnumber",
																								myfilterfield : "fnumber",
																								myfiltername : "编码",
																								myfilterable : true
																							},
																							{
																								name : "fdescription"
																							},
																							{
																								name : "fcreatetime"
																							},
																							{
																								name : "flastupdatetime"
																							},
																							{
																								name : "fsimplename"
																							},
																							{
																								name : "fartificialperson"
																							},
																							{
																								name : "fbarcode"
																							},
																							{
																								name : "fusedstatus",
																								convert : function(
																										value,
																										record) {
																									if (value == "1") {
																										return true
																									} else {
																										return false
																									}
																								}
																							},
																							{
																								name : "fmnemoniccode"
																							},
																							{
																								name : "faddress"
																							} ],
																					columns : [
																							{
																								header : "fid",
																								dataIndex : "fid",
																								hidden : true,
																								hideable : false,
																								autoHeight : true,
																								autoWidth : true,
																								sortable : true
																							},
																							{
																								header : "供应商名称",
																								dataIndex : "fname",
																								sortable : true
																							},
																							{
																								header : "编码",
																								dataIndex : "fnumber",
																								sortable : true
																							},
																							{
																								header : "简称",
																								width : 70,
																								dataIndex : "fsimplename",
																								sortable : true
																							},
																							{
																								header : "法人代表",
																								width : 70,
																								dataIndex : "fartificialperson",
																								sortable : true
																							},
																							{
																								header : "条形码",
																								hidden : true,
																								dataIndex : "fbarcode",
																								sortable : true
																							},
																							{
																								header : "状态",
																								width : 50,
																								dataIndex : "fusedstatus",
																								xtype : "checkcolumn",
																								processEvent : function() {
																								},
																								sortable : true
																							},
																							{
																								header : "助记码",
																								dataIndex : "fmnemoniccode",
																								sortable : true
																							},
																							{
																								header : "地址",
																								dataIndex : "faddress",
																								sortable : true
																							},
																							{
																								header : "修改时间",
																								dataIndex : "flastupdatetime",
																								width : 140,
																								sortable : true
																							},
																							{
																								header : "创建时间",
																								dataIndex : "fcreatetime",
																								width : 140,
																								sortable : true
																							} ]
																				}
																			}
																		},
																		{
																			xtype : "numbercolumn",
																			dataIndex : "plifecycle",
																			text : "产品生产周期",
																			format : "0,000",
																			editor : {
																				xtype : "numberfield",
																				allowBlank : false,
																				allowDecimals : false,
																				minValue : 0
																			}
																		},
																		{
																			xtype : "numbercolumn",
																			summaryRenderer : function(
																					val,
																					params,
																					data) {
																				return "总计 : "
																						+ val
																						+ "% <br> 还剩 :"
																						+ (100 - val)
																						+ "%"
																			},
																			format : "0.00%",
																			summaryType : "sum",
																			dataIndex : "mproportion",
																			text : "分配比例",
																			editor : {
																				xtype : "numberfield",
																				allowBlank : false,
																				minValue : 0,
																				step : 1
																			}
																		} ],
																features : [ {
																	ftype : "summary"
																} ],
																plugins : [ Ext
																		.create(
																				'Ext.grid.plugin.CellEditing',
																				{
																					clicksToEdit : 2
																				})
																// Ext
																// .create(
																// "Ext.grid.plugin.RowEditing",
																// {
																// clicksToEdit
																// : 2,
																// errorSummary
																// : false,
																// saveBtnText :
																// "更新",
																// cancelBtnText
																// : "取消",
																// autoCancel :
																// false
																// })
																]
															} ]
												}), this.callParent(arguments);
					}
				// bodyStyle : "padding-top:5px;padding-left:30px"
				});