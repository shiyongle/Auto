// 验证时间
function validate(val) {
	if (val == null || val == "")
		return true;
	if (val instanceof Date) {
		val = Ext.Date.format(value, "Y-m-d");
	}
	var tableid = this.ownerCt.findParentByType("cTable");
	var record = tableid.getSelectionModel().getSelection();
	if (this.getName() == "fstartdate") {
		var v1 = record[0].get("fenddate");
	} else {
		var v1 = record[0].get("fstartdate");
	}
	if (v1 == null || v1 == "")
		return true;
	if (v1 instanceof Date) {
		v1 = Ext.Date.format(v1, "Y-m-d");
	}
	if (this.getName() == "fstartdate") {
		return val < v1 ? true : '开始时间小于结束时间';
	} else {
		return val > v1 ? true : '结束时间大于开始日期';
	}

}

// 验证上下限制
function vallimit(val) {
	if (val == "" || val == 0)
		return true;
	var tableid = this.ownerCt.findParentByType("cTable");
	var record = tableid.getSelectionModel().getSelection();
	if (this.getName() == "flowerlimit") {
		var v1 = record[0].get("fupperlimit");
	} else {
		var v1 = record[0].get("flowerlimit");
	}
	if (v1 == "" || v1 == 0)
		return true;
	if (this.getName() == "flowerlimit") {
		return val < v1 ? true : '下限要小于上限';
	} else {
		return val > v1 ? true : '上限要大于下限';
	}

}
Ext.define('DJ.System.product.ProductPurchasePrice', {
	extend : 'Ext.c.BaseEditUI',
	id : 'DJ.System.product.ProductPurchasePrice',
	modal : true,
	title : "产品采购价界面",
	ctype : "Purchaseprice",
	width : 800,// 230, //Window宽度
	height : 525,// 137, //Window高度
	resizable : false,
	url : 'savePurchaseprice.do',
	infourl : 'getPurchaseprice.do',
	viewurl : 'getPurchaseprice.do',
	closable : true, // 关闭按钮，默认为true
	cverifyinput : function() {
		// throw "数据异常，不能保存！";
	},
	labelAlign : "left",

	initComponent : function() {
		Ext.apply(this, {

			items : [{
				layout : 'column',
				baseCls : 'x-plain',
				items : [{
					baseCls : 'x-plain',
					columnWidth : .4,
					items : [{
								name : 'fproductid',
								width : 280,
								labelWidth : 70,
								fieldLabel : '产品名称',
								xtype : 'cCombobox',
								displayField : 'fname', // 这个是设置下拉框中显示的值
								valueField : 'fid', // 这个可以传到后台的值
								allowBlank : false,
								blankText : '请选择产品',
								editable : false, // 可以编辑不
								MyConfig : {
									width : 800,// 下拉界面
									height : 200,// 下拉界面
									url : 'GetProductss.do', // 下拉数据来源
									fields : [{
												name : 'fid'
											}, {
												name : 'fname',
												myfilterfield : 'd.fname',
												myfiltername : '名称',
												myfilterable : true
											}, {
												name : 'fnumber'
											}, {
												name : 'fcreatorid'
											}, {
												name : 'fcreatetime'
											}, {
												name : 'u1_fname'
											}, {
												name : 'fcharacter'
											}, {
												name : 'fboxmodelid'
											}, {
												name : 'feffect',
												convert : function(value,
														record) {
													if (value == '1') {
														return true;
													} else {
														return false;
													}
												}

											}, {
												name : 'fnewtype'
											}, {
												name : 'fversion'
											}],
									columns : [{
												text : 'fid',
												dataIndex : 'fid',
												hidden : true,
												hideable : false,
												sortable : true
											}, {
												text : '编号',
												dataIndex : 'fnumber',
												sortable : true
											}, {
												text : '名称',
												dataIndex : 'fname',
												sortable : true
											}, {
												text : '版本号',
												dataIndex : 'fversion',
												sortable : true,
												width : 50
											}, {
												text : '特征',
												dataIndex : 'fcharacter',
												sortable : true
											}, {
												text : '箱型',
												dataIndex : 'fboxmodelid',
												sortable : true

											}, {
												text : '类型',
												dataIndex : 'fnewtype',
												sortable : true
											}, {
												text : '禁用/启用',
												xtype : 'checkcolumn',
												dataIndex : 'feffect',
												processEvent : function() {
												},
												width : 60,
												sortable : true
											}, {
												text : '创建人',
												dataIndex : 'u1_fname',
												sortable : true
											}, {
												text : '创建时间',
												dataIndex : 'fcreatetime',
												sortable : true

											}]
								}
							}]
				}, {
					baseCls : 'x-plain',
					columnWidth : .3,
					items : [{
						labelWidth : 70,
						name : 'fpurchaseType',
						xtype : 'combo',
						fieldLabel : '结算方式',
						store : Ext.create('Ext.data.Store', {
									fields : ['typevalue', 'typename'],
									data : [{
												"typevalue" : "1",
												"typename" : "面积"
											}, {
												"typevalue" : "2",
												"typename" : "产品"
											}]
								}),
						displayField : 'typename', // 这个是设置下拉框中显示的值
						valueField : 'typevalue', // 这个可以传到后台的值
						allowBlank : false,
						blankText : '请选择结算类型',
						triggerAction : 'all',
						editable : false
							// 可以编辑不
						}]
				}]
			},

			{
				xtype : 'cTable',
				name : "Purchaseourprice",
				id : "DJ.System.product.ProductPurchasePrice.Purchaseourprice",
				// title:"测试",
				// collapsible:true,
				// ctype : "Balancepriceprices",
				width : 775,
				height : 200,
				pageSize : 100,
				url : "getPurchaseourprices.do",
				parentfield : "e.fproductid",
				plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
							clicksToEdit : 1
						})],
				fields : [{
							name : "fid"
						}, {
							name : "fparentid"
						}, {
							name : "flowerlimit"
						}, {
							name : "fupperlimit"
						}, {
							name : "fprice"
						}, {
							name : "fremark"
						}, {
							name : "fstartdate"
						}, {
							name : "fenddate"
						}, {
							name : "fiseffect",
							type : "int",
							defaultValue : "1"
						}],
				columns : [{
							xtype : "rownumberer",
							text : "No"
						}, {
							xtype : "numbercolumn",
							dataIndex : "flowerlimit",
							text : "下限(>=)",
							editor : {
								// name : "flowerlimit",
								allowBlank : false,
								blankText : "请填写下限值",
								xtype : "numberfield",
								minValue : 0,
								value : 0,
								validator : vallimit
							}
						}, {
							xtype : "numbercolumn",
							dataIndex : "fupperlimit",
							text : "上限(<)",
							editor : {
								// name : "fupperlimit",
								allowBlank : false,
								blankText : "请填写上限值",
								xtype : "numberfield",
								minValue : 0,
								value : 0,
								validator : vallimit
							}
						}, {
							xtype : "numbercolumn",
							dataIndex : "fprice",
							text : "采购单价",
							format : "0.0000",
							editor : {
								// / name : 'fprice',
								xtype : "numberfield",
								allowBlank : false,
								blankText : "请填写采购单价",
								decimalPrecision : 4,
								minValue : 0,
								value : 0
							}
						}, {
							xtype : "datecolumn",
							format : "Y-m-d",
							dataIndex : "fstartdate",
							text : "开始时间",
							editor : {
								// name : 'fstartdate',
								xtype : "datefield",
								format : "Y-m-d",
								allowBlank : false,
								blankText : "请选择开始时间",
								validator : validate
							}
						}, {
							xtype : "datecolumn",
							format : "Y-m-d",
							dataIndex : "fenddate",
							text : "结束时间",
							editor : {
								// name : 'fenddate',
								xtype : "datefield",
								blankText : "请选择结束时间",
								format : "Y-m-d",
								allowBlank : false,
								validator : validate
							}
						}, {
							dataIndex : "fremark",
							text : "备注",
							editor : {
								// name : 'fremark',
								xtype : "textfield"
							}
						}]
			}, {
				xtype : 'cTable',
				name : "Purchasesupplierprice",
				id : "DJ.System.product.ProductPurchasePrice.Purchasesupplierprice",
				width : 775,
				height : 200,
				pageSize : 100,
				url : "getPurchasesupplierprices.do",
				parentfield : "e.fproductid",
				plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
							clicksToEdit : 1
						})],
				fields : [{
							name : "fid"
						}, {
							name : "fparentid"
						}, {
							name : "fsupplierId"
						}, {
							name : "fsuppliername",
							persist : false
						}, {
							name : "flowerlimit"
						}, {
							name : "fupperlimit"
						}, {
							name : "fprice"
						}, {
							name : "fremark"
						}, {
							name : "fstartdate"
						}, {
							name : "fenddate"
						}, {
							name : "fiseffect",
							type : "int",
							defaultValue : "1"
						}],
				columns : [{
							xtype : "rownumberer",
							text : "No"
						},

						{
							dataIndex : "fsuppliername",
							text : "供应商",
							width : 150,
							editor : {
								xtype : 'cCombobox',
								displayField : 'fid', // 这个是设置下拉框中显示的值
								valueField : 'fname', // 这个可以传到后台的值
								allowBlank : false,
								blankText : '请选择供应商',
								editable : false, // 可以编辑不
								MyDataChanged : function(com) {
									var row = Ext
											.getCmp("DJ.System.product.ProductPurchasePrice.Purchasesupplierprice")
											.getSelectionModel().getSelection();
									row[0].set("fsupplierId", com[0].data.fid);
								},
								MyConfig : {
									width : 800,// 下拉界面
									height : 200,// 下拉界面
									url : 'GetSupplierList.do', // 下拉数据来源
									fields : [{
												name : 'fid'
											}, {
												name : 'fname',
												myfilterfield : 'fname',
												myfiltername : '名称',
												myfilterable : true
											}, {
												name : 'fnumber',
												myfilterfield : 'fnumber',
												myfiltername : '编码',
												myfilterable : true

											}, {
												name : 'fdescription'
											}, {
												name : 'fcreatetime'
											}, {
												name : 'flastupdatetime'
											}, {
												name : 'fsimplename'
											}, {
												name : 'fartificialperson'
											}, {
												name : 'fbarcode'
											}, {
												name : 'fusedstatus',
												convert : function(value,
														record) {
													if (value == '1') {
														return true;
													} else {
														return false;
													}
												}
											}, {
												name : 'fmnemoniccode'
											}, {
												name : 'faddress'

											}],
									columns : [{
												'header' : 'fid',
												'dataIndex' : 'fid',
												hidden : true,
												hideable : false,
												autoHeight : true,
												autoWidth : true,
												sortable : true
											}, {
												'header' : '供应商名称',
												'dataIndex' : 'fname',
												sortable : true

											}, {
												'header' : '编码',
												'dataIndex' : 'fnumber',
												sortable : true

											}, {
												'header' : '简称',
												width : 70,
												'dataIndex' : 'fsimplename',
												sortable : true
											}, {
												'header' : '法人代表',
												width : 70,
												'dataIndex' : 'fartificialperson',
												sortable : true
											}, {
												'header' : '条形码',
												hidden : true,
												'dataIndex' : 'fbarcode',
												sortable : true
											}, {
												'header' : '状态',
												width : 50,
												'dataIndex' : 'fusedstatus',
												xtype : 'checkcolumn',
												processEvent : function() {
												},
												sortable : true
											}, {
												'header' : '助记码',
												'dataIndex' : 'fmnemoniccode',
												sortable : true
											}, {
												'header' : '地址',
												'dataIndex' : 'faddress',
												sortable : true
											}, {
												'header' : '修改时间',
												'dataIndex' : 'flastupdatetime',
												width : 140,
												sortable : true
											}, {
												'header' : '创建时间',
												'dataIndex' : 'fcreatetime',
												width : 140,
												sortable : true
											}]
								}

							}
						},

						{
							xtype : "numbercolumn",
							dataIndex : "flowerlimit",
							text : "下限(>=)",
							editor : {
								allowBlank : false,
								blankText : "请填写下限值",
								xtype : "numberfield",
								minValue : 0,
								value : 0,
								validator : vallimit
							}
						}, {
							xtype : "numbercolumn",
							dataIndex : "fupperlimit",
							text : "上限(<)",
							editor : {
								allowBlank : false,
								blankText : "请填写上限值",
								xtype : "numberfield",
								minValue : 0,
								value : 0,
								validator : vallimit
							}
						}, {
							xtype : "numbercolumn",
							dataIndex : "fprice",
							text : "采购单价",
							format : "0.0000",
							editor : {
								blankText : "请填写采购单价",
								xtype : "numberfield",
								allowBlank : false,
								decimalPrecision : 4,
								minValue : 0,
								value : 0
							}
						}, {
							xtype : "datecolumn",
							format : "Y-m-d",
							dataIndex : "fstartdate",
							text : "开始时间",
							editor : {
								xtype : "datefield",
								format : "Y-m-d",
								blankText : "请选择开始时间",
								allowBlank : false,
								validator : validate
							}
						}, {
							xtype : "datecolumn",
							format : "Y-m-d",
							dataIndex : "fenddate",
							text : "结束时间",
							editor : {
								xtype : "datefield",
								blankText : "请选择结束时间",
								format : "Y-m-d",
								allowBlank : false,
								validator : validate
							}
						}, {
							dataIndex : "fremark",
							text : "备注",
							editor : {
								xtype : "textfield"
								// allowBlank : false
							}
						}]
					// }, Ext.create('Ext.ux.form.DateTimeField', {
					// fieldLabel : '创建时间',
					// name : 'fcreatetime',
					// format : 'Y-m-d'
					// }), {

					// },{
			}, {
				layout : 'column',
				baseCls : 'x-plain',
				items : [{
							bodyStyle : 'padding-top:5px;',
							baseCls : 'x-plain',
							columnWidth : .3,
							items : [{
										fieldLabel : '创建者',
										name : 'username',
										xtype : 'textfield',
										// disabled:true,
										// readOnlyCls:'x-item-disabled',
										width : 200,
										labelWidth : 50

									}]
						}, {
							baseCls : 'x-plain',
							columnWidth : .3,
							bodyStyle : 'padding-top:5px;',
							items : [Ext.create('Ext.ux.form.DateTimeField', {
										fieldLabel : '创建时间',
										name : 'fcreatetime',
										// readOnlyCls:'x-item-disabled',
										format : 'Y-m-d',
										width : 200,
										labelWidth : 70

									})]
						}]
			}, {
				name : 'fid',
				xtype : 'hidden'
			}, {
				name : 'fcreatorid',
				xtype : 'hidden'
			}]

		}), this.callParent(arguments);
	},

	bodyStyle : "padding-top:5px;padding-left:10px"
});
