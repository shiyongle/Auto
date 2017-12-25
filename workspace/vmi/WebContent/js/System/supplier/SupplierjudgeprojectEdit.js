Ext.apply(Ext.form.field.VTypes, {
	numRange : function(val, field) {
		var beginDate = null, beginDateCmp = null, endDate = null, endDateCmp = null, validStatus = true;
		if (field.numRange) {
			if (!Ext.isEmpty(field.numRange.begin)) {
				beginDateCmp = Ext.getCmp(field.numRange.begin);
				beginDate = beginDateCmp.getValue()
			}
			if (!Ext.isEmpty(field.numRange.end)) {
				endDateCmp = Ext.getCmp(field.numRange.end);
				endDate = endDateCmp.getValue()
			}
		}
		if (!Ext.isEmpty(beginDate) && !Ext.isEmpty(endDate)) {
			validStatus = Ext.Number.from(beginDate, 0) < Ext.Number.from(
					endDate, 0);
		}
		return validStatus
	},
	numRangeText : "下限 必须＜ 上限！"
});

Ext.define('DJ.System.supplier.SupplierjudgeprojectEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.System.supplier.SupplierjudgeprojectEdit",
	modal : true,
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等

	},
	title : "制造商评价项目编辑界面",
	width : 400,// 230, //Window宽度
	height : 500,// 137, //Window高度
	resizable : true,
	url : 'saveSupplierjudgeproject.do',
	infourl : 'selectSupplierjudgeprojectByID.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'selectSupplierjudgeprojectByID.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true
	ctype : "Supplierjudgeproject",
	initComponent : function() {
		Ext.apply(this, {
			items : [{
				layout : "column",
				baseCls : "x-plain",

				items : [{// title:"列1",
					baseCls : "x-plain",
					columnWidth : 1,

					defaults : {

						labelWidth : 70,
						width : 300,
						labelAlign : "left"

					},

					// bodyStyle :
					// 'padding-top:0px;padding-left:5px;padding-right:5px',
					items : [{
						name : 'fid',
						xtype : 'textfield',

						hidden : true
					},

							// {
							// name : 'fsupplierJudgementID',
							// xtype : 'textfield',
							//
							// hidden : true
							// }, {
							// name : 'ftakeNumberFormulaID',
							// xtype : 'textfield',
							//
							// hidden : true
							// },

							{
								name : 'fname',
								xtype : 'textfield',
								fieldLabel : '名称',
								allowBlank : false

							}, {
								name : 'fnumber',
								xtype : 'textfield',
								fieldLabel : '编码',
								allowBlank : false,
								blankText : '编码不能为空',
								regex : /^([\u4E00-\u9FA5]|\w|[@.()\-])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
								regexText : "不能包含特殊字符"

							}, {
								name : 'fsupplierJudgementId',
								fieldLabel : '制造商评价',
								valueField : 'fid', // 组件隐藏值
								xtype : 'cCombobox',
								displayField : 'fname',// 组件显示值
								allowBlank : false,
								MyConfig : {
									width : 800,// 下拉界面
									height : 200,// 下拉界面
									url : 'selectSupplierjudgements.do', // 下拉数据来源
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
										name : 'frate',
										type : "float"
									}],
									columns : {
										defaults : {
											width : 200
										},
										items : [
												Ext
														.create('DJ.Base.Grid.GridRowNum'),
												{
													'header' : 'fid',
													'dataIndex' : 'fid',
													hidden : true,
													hideable : false,
													sortable : true
												}, {
													'header' : '名称',
													'dataIndex' : 'fname',
													sortable : true
												}, {
													'header' : '编码',
													'dataIndex' : 'fnumber',
													sortable : true
												}, {
													'header' : '比例',
													// width : 70,
													'dataIndex' : 'frate',

													sortable : true
												}]
									}
								}
							}, {
								name : 'ftakeNumberFormulaId',
								fieldLabel : '取数公式',
								valueField : 'fid', // 组件隐藏值
								xtype : 'cCombobox',
								displayField : 'fname',// 组件显示值
								allowBlank : false,
								MyConfig : {
									width : 800,// 下拉界面
									height : 200,// 下拉界面
									url : 'selectTakenumberformulas.do', // 下拉数据来源
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
										name : 'fsqlStatement'
									}],
									columns : {
										defaults : {
											width : 200
										},
										items : [
												Ext
														.create('DJ.Base.Grid.GridRowNum'),
												{
													'header' : 'fid',
													'dataIndex' : 'fid',
													hidden : true,
													hideable : false,
													sortable : true
												}, {
													'header' : '名称',
													'dataIndex' : 'fname',
													sortable : true
												}, {
													'header' : '编码',
													'dataIndex' : 'fnumber',
													sortable : true
												}, {
													'header' : '语句',
													width : 500,
													'dataIndex' : 'fsqlStatement',

													sortable : true
												}]
									}
								}
							},

							{
								name : 'frate',
								xtype : 'numberfield',
								fieldLabel : '比例',
								allowBlank : false,
								minValue : 0
							}]
				}]

			}, {
				xtype : 'cTable',
				name : "SupplierJudgeProjectentry",
				id : "DJ.System.supplier.SupplierjudgeprojectEdit.table",
				width : 300,
				height : 250,
				pageSize : 100,
				url : "selectSupplierJudgeProjectentrys.do",
				parentfield : "fparentid",
				fields : [{
					name : "fid"
				}, {
					name : "fhighLimit",
					type : "float"
				}, {
					name : "flowLimit",
					type : "float"
				}, {
					name : "fparentid"
				}, {
					name : "fscore",
					type : "float"
				}],
				columns : {
					defaults : {
						width : 80
					},
					items : [{
						xtype : "rownumberer",
						text : "No"
					}, {
						width : 40,
						'header' : 'FID',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						'header' : 'FPARENTID',
						'dataIndex' : 'fparentid',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						xtype : "numbercolumn",
						dataIndex : "flowLimit",
						text : "下限",
						format : "0,000.00",
						editor : {
							id : "startdateEditor",
							numRange : {
								begin : "startdateEditor",
								end : "enddateEditor"
							},
							vtype : "numRange",
							xtype : "numberfield",
							allowBlank : false,
							allowDecimals : false,
							minValue : 0
						}
					}, {
						xtype : "numbercolumn",
						dataIndex : "fhighLimit",
						text : "上限",
						format : "0,000.00",
						editor : {

							id : "enddateEditor",
							numRange : {
								begin : "startdateEditor",
								end : "enddateEditor"
							},
							vtype : "numRange",

							xtype : "numberfield",
							allowBlank : false,
							allowDecimals : false,
							minValue : 0
						}
					}, {
						xtype : "numbercolumn",
						dataIndex : "fscore",
						text : "分数",
						format : "0,000.00",
						editor : {
							xtype : "numberfield",
							allowBlank : false,
							allowDecimals : false,
							minValue : 0
						}
					}

					]
				},
				selModel : Ext.create('Ext.selection.CheckboxModel'),

				plugins : [
				

				// Ext.create('Ext.grid.plugin.CellEditing', {
				// clicksToEdit : 2
				// })
				//
				//				 ,
				 Ext.create("Ext.grid.plugin.RowEditing", {
				 clicksToEdit : 2,
				 errorSummary : false,
				 saveBtnText : "更新",
				 cancelBtnText : "取消",
				 autoCancel : false
				 })

				]
			}]
		}), this.callParent(arguments);
	},
	bodyStyle : "padding-top:5px;padding-left:30px"
});
