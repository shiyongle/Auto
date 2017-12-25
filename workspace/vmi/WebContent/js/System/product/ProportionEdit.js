
Ext.define('DJ.System.product.ProportionEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : 'DJ.System.product.ProportionEdit',
	modal : true,
	title : "分配比例",
//	ctype : "Balanceprice",
	width : 611,// 230, //Window宽度
	height : 357,// 137, //Window高度
	resizable : false,
	url : 'saveProportion.do',
	infourl : 'GetCycleProductInfo.do',
	viewurl : 'GetCycleProductInfo.do',
	closable : true, // 关闭按钮，默认为true
	cverifyinput : function() {
		// throw "数据异常，不能保存！";
	},
	labelAlign : "left",
	initComponent : function() {
		Ext.apply(this, {
			items : [{
						xtype : "textfield",
						id : "DJ.System.product.bt.ProductName",
						name : "fname",
						fieldLabel : "产品",
						text : "产品",
						readOnly : true,
						flex : 1,
						width : 400,
						labelWidth : 45
					}, {
						xtype : "textfield",
						id : "DJ.System.product.bt.ProductFID",
						name : "fid",
						text : "FID",
						hidden : true,
						flex : 1
					}
					, {
				xtype : 'cTable',
				name : "Productcycle",
				id : "DJ.System.product.ProportionEdit.Productcycle",
				// title:"测试",
				// collapsible:true,
				// ctype : "Balancepriceprices",
				width : 775,
				height : 200,
				pageSize : 100,
				url : "getLifeCycles.do",
				parentfield : "pc.FPRODUCTDEFID",
				plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
					clicksToEdit : 2
				})],
				fields : [{
						name : "fid"
					}, {
						name : "fproductdefid"
					}, {
						name : "fsupplierid",
						defaultValue : ""
					}
					, {
						name : "fsuppliername"
					}
					, {
						name : "flifecycle",
						type : "int"
					}, {
						name : "fproportion",
						type : "float",
						defaultValue : "0"
					}],
				columns : [
					{
						xtype : "rownumberer",
						text : "No"
					}
					, {
						width : 164,
						dataIndex : "fsuppliername",
						text : "供应商",
						editor : {
							name : "fsupplierid",
							allowBlank : false,
							blankText : "请选择供应商",
							editable : false,
							xtype : "cCombobox",
							displayField : "fid",
							valueField : "fname",
//							listeners : {
//								change : function(com) {
////									var rows = Ext.getCmp("ProportionEditGridPanel").getSelectionModel().getSelection();
////									var rows = Ext.getCmp("DJ.System.product.ProportionEdit.Productcycle").getSelectionModel().getSelection();
////									rows[0].set("fsupplierid", com.getRawValue())
//								}
//							},
							MyDataChanged : function(com) {

									var rows = Ext
											.getCmp("DJ.System.product.ProportionEdit.Productcycle").getSelectionModel().getSelection();
									rows[0].set("fsupplierid", com[0].data.fid);
//									rows[0].set("fproductdefid", Ext.getCmp("DJ.System.product.bt.ProductFID").getValue())

								},
							MyConfig : {
								width : 596,
								height : 138,
								url : "GetSupplierList.do",
								fields : [{
											name : "fid"
										}, {
											name : "fname",
											myfilterfield : "fname",
											myfiltername : "名称",
											myfilterable : true
										}, {
											name : "fnumber",
											myfilterfield : "fnumber",
											myfiltername : "编码",
											myfilterable : true
										}, {
											name : "fdescription"
										}, {
											name : "fcreatetime"
										}, {
											name : "flastupdatetime"
										}, {
											name : "fsimplename"
										}, {
											name : "fartificialperson"
										}, {
											name : "fbarcode"
										}, {
											name : "fusedstatus",
											convert : function(value, record) {
												if (value == "1") {
													return true
												} else {
													return false
												}
											}
										}, {
											name : "fmnemoniccode"
										}, {
											name : "faddress"
										}],
								columns : [{
											header : "fid",
											dataIndex : "fid",
											hidden : true,
											hideable : false,
											autoHeight : true,
											autoWidth : true,
											sortable : true
										}, {
											header : "供应商名称",
											dataIndex : "fname",
											sortable : true
										}, {
											header : "编码",
											dataIndex : "fnumber",
											sortable : true
										}, {
											header : "简称",
											width : 70,
											dataIndex : "fsimplename",
											sortable : true
										}, {
											header : "法人代表",
											width : 70,
											dataIndex : "fartificialperson",
											sortable : true
										}, {
											header : "条形码",
											hidden : true,
											dataIndex : "fbarcode",
											sortable : true
										}, {
											header : "状态",
											width : 50,
											dataIndex : "fusedstatus",
											xtype : "checkcolumn",
											processEvent : function() {
											},
											sortable : true
										}, {
											header : "助记码",
											dataIndex : "fmnemoniccode",
											sortable : true
										}, {
											header : "地址",
											dataIndex : "faddress",
											sortable : true
										}, {
											header : "修改时间",
											dataIndex : "flastupdatetime",
											width : 140,
											sortable : true
										}, {
											header : "创建时间",
											dataIndex : "fcreatetime",
											width : 140,
											sortable : true
										}]
							}
						}
					}
					, {
						xtype : "numbercolumn",
						dataIndex : "flifecycle",
						text : "产品生产周期",
						format : "0,000",
						editor : {
							xtype : "numberfield",
							allowBlank : false,
							allowDecimals : false,
							minValue : 0
						}
					}, {
						xtype : "numbercolumn",
						summaryRenderer : function(val, params, data) {
							return "总计 : " + val + "% <br> 还剩 :" + (100 - val)
									+ "%"
						},
						format : "0.00%",
						summaryType : "sum",
						dataIndex : "fproportion",
						text : "分配比例",
						editor : {
							xtype : "numberfield",
							allowBlank : false,
							minValue : 0,
							step : 1
						}
					}],features : [{
						ftype : "summary"
					}]
			}
			]
		}), this.callParent(arguments);
	},
	bodyStyle : "padding-top:5px;padding-left:10px"
});