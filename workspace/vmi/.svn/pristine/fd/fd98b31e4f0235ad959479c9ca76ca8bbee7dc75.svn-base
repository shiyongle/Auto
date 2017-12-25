var customerStore = Ext.create('DJ.System.product.pCustomerStore');

///**
// * 新增时自动恢复上次输入状态，在form里用
// * 
// * @date 2014-6-9 下午1:54:38, ZJZ (447338871@qq.com, any Question)
// */
//Ext.define('Ext.ux.FormDataRecorder', {
//	extend : "Ext.AbstractPlugin",
//	alias : 'plugin.formdatarecorder',
//
//	alternateClassName : 'Ext.data.FormDataRecorder',
//
//	init : function() {
//
//		// load
//		var com = this.cmp;
//		var paramsS = localStorage.getItem(com.id);
//
//		if (paramsS != null && paramsS != "") {
//
//			var formT = com.getForm();
//
//			var obj = Ext.JSON.decode(paramsS);
//
//			formT.setValues(obj);
//		}
//
//		// save
//		this.cmp.on("beforedestroy", function(com, eOpts) {
//
//			if (com.getForm().getValues().fid == null
//					|| com.getForm().getValues().fid == "") {
//
//				localStorage.setItem(com.id, Ext.JSON.encode(com.getForm()
//						.getValues()));
//
//			}
//
//		}, this);
//
//	}
//});

Ext.require("Ext.ux.FormDataRecorder");

Ext.define('DJ.System.product.ProductDefEdit', {
	extend : 'Ext.Window',
	id : 'DJ.System.product.ProductDefEdit',
	modal : true,
	title : "产品管理编辑界面",
	width : 1250,// 230, //Window宽度
	height : 365,// 137, //Window高度
	resizable : false,
	closable : true, // 关闭按钮，默认为true
	items : [{
		xtype : 'form',
		id : 'DJ.System.product.ProductDefEdit.dForm',
		baseCls : 'x-plain',

		plugins : ['formdatarecorder'],

		fieldDefaults : {
			labelWidth : 85
		},

		items : [{
			layout : 'column',
			baseCls : 'x-plain',
			items : [{
				bodyStyle : 'padding:5px;',
				baseCls : 'x-plain',
				columnWidth : .25,
				layout : 'form',
				defaults : {
					xtype : 'textfield'
				},
				items : [{
					name : 'fnumber',
					fieldLabel : '产品编码',
					allowBlank : false,
					blankText : '编码不能为空',
					regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符"
				}, {
					id : 'DJ.System.product.ProductDefEdit.fisAddVersion',
					name : 'fisAddVersion',
					labelWidth : 50,
					width : 260,
					hidden : true
				}, {
					id : 'DJ.System.product.ProductDefEdit.fbaseid',
					name : 'fbaseid',
					labelWidth : 50,
					width : 260,
					hidden : true
				}, {
					// name:'fcustomerid',
					// fieldLabel : '客户名称',
					// store:customerStore,
					// triggerAction:"all",
					// xtype:'combo',
					// displayField:'fname', // 这个是设置下拉框中显示的值
					// valueField:'fid', // 这个可以传到后台的值
					// editable: false, // 可以编辑不
					// forceSelection: true,
					// pageSize:10,
					// mode:'remote'
					name : 'fcustomerid',
					fieldLabel : '客户名称',
					xtype : 'cCombobox',
					displayField : 'fname', // 这个是设置下拉框中显示的值
					valueField : 'fid', // 这个可以传到后台的值
					allowBlank : false,
					blankText : '请选择客户',
					editable : false, // 可以编辑不
					MyConfig : {
						width : 800,// 下拉界面
						height : 200,// 下拉界面
						url : 'GetCustomerList.do', // 下拉数据来源
						fields : [{
							name : 'fid'
						}, {
							name : 'fname',
							myfilterfield : 't_bd_customer.fname', // 查找字段，发送到服务端
							myfiltername : '客户名称',// 在过滤下拉框中显示的名称
							myfilterable : true
								// 该字段是否查找字段
								}, {
									name : 'fnumber'
								}, {
									name : 'findustryid'
								}, {
									name : 'faddress'
								}, {
									name : 'fisinternalcompany',
									convert : function(value, record) {
										if (value == '1') {
											return true;
										} else {
											return false;
										}
									}
								}],
						columns : [{
							text : 'fid',
							dataIndex : 'fid',
							hidden : true,
							sortable : true
						}, {
							text : '编码',
							dataIndex : 'fnumber',
							sortable : true
						}, {
							text : '客户名称',
							dataIndex : 'fname',
							sortable : true
						}, {
							text : '行业',
							dataIndex : 'findustryid',
							sortable : true
						}, {
							text : '地址',
							dataIndex : 'faddress',
							sortable : true,
							width : 250
						}, {
							text : '内部客户',
							dataIndex : 'fisinternalcompany',
							xtype : 'checkcolumn',
							processEvent : function() {
							},
							sortable : true
						}]
					}
				}, {
					name : 'fmaterialcodeid',
					fieldLabel : '用料代码',
					regex : /^([\u4E00-\u9FA5]|\w|\S)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符"
				}, {

					name : 'fstavetype',
					fieldLabel : '跑线方式',
					regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符"

				}]
			}, {
				bodyStyle : 'padding:5px;',
				baseCls : 'x-plain',
				columnWidth : .25,
				layout : 'form',
				defaults : {
					xtype : 'textfield'
				},
				items : [{
					xtype : 'textfield',
					name : 'fname',
					fieldLabel : '产品名称',
					allowBlank : false,
					blankText : '名称不能为空',
					regex : /^([\u4E00-\u9FA5]|\w|.|\s)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符"
				}, {
					name : 'forderunitid',
					fieldLabel : '计量单位',
					regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符"

				}, {
					name : 'ftilemodelid',
					fieldLabel : '瓦楞楞型',
					regex : /^([\u4E00-\u9FA5]|\w|\S)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符"

				}, {
					name : 'fhstaveexp',
					fieldLabel : '横向跑线公式',
					regex : /^(\d|[HLWhlw]|\+|\-|\*|\/|\(|\)|\[|\]|\.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符"
				}]
			}, {

				bodyStyle : 'padding: 5px;',
				baseCls : 'x-plain',
				columnWidth : .25,
				layout : 'form',
				defaults : {
					xtype : 'textfield'
				},
				items : [{
					name : 'fversion',
					fieldLabel : '版本号',
					allowBlank : false,
					blankText : '版本号不能为空',
					regex : /^([\u4E00-\u9FA5]|\w|\.|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符"

				}, {
					xtype : 'numberfield',
					name : 'farea',
					fieldLabel : '单位面积',
					value : 0,
					minValue : 0,
					negativeText : '不能为负数'

				}, {
					xtype : 'numberfield',
					name : 'flayer',
					fieldLabel : '材料层数',
					value : 0,
					minValue : 0,
					width : 74,
					negativeText : '不能为负数'

				}, {
					name : 'fvstaveexp',
					fieldLabel : '纵向跑线公式',
					regex : /^(\d|[HLWhlw]|\+|\-|\*|\/|\(|\)|\[|\]|\.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符"
				}]
			}, {

				bodyStyle : 'padding: 5px;',
				baseCls : 'x-plain',
				columnWidth : .25,
				layout : 'form',
				defaults : {
					xtype : 'textfield'
				},
				items : [{
					// name:'fnewtype',
					// fieldLabel : '产品类型(新)',
					// regex : /^([\u4E00-\u9FA5]|\w)*$/,//
					// /^[^,\!@#$%^&*()_+}]*$/,
					// regexText : "不能包含特殊字符"

					name : 'fnewtype',
					fieldLabel : '产品类型(新)',
					regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符",
					xtype : 'combo',
					// labelWidth : 70,
					// store: houseType,
					store : Ext.create('Ext.data.Store', {
						fields : ['typevalue', 'typename'],
						data : [{
							"typevalue" : "3",
							"typename" : "普通纸箱"
						}, {
							"typevalue" : "4",
							"typename" : "套装纸箱"
						}, {
							"typevalue" : "5",
							"typename" : "面板产品"
						}]
					}),
					triggerAction : 'all',
					displayField : 'typename',
					valueField : 'typevalue',
					editable : false, // 可以编辑不
					value : '3'
				}, {
					xtype : 'numberfield',
					name : 'fmaterialcost',
					fieldLabel : '材料成本',
					value : 0,
					minValue : 0,
					negativeText : '不能为负数'
				}, {

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
				}, {

					xtype : 'hidden',
					name : 'fid'

				}]
			}]
		}, {
			layout : 'column',
			baseCls : 'x-plain',
			bodyStyle : 'padding-left:5px;',
			items : [{

				baseCls : 'x-plain',
				columnWidth : .25,
				xtype : 'fieldcontainer',
				fieldLabel : '纸箱规格',
				layout : 'hbox',
				items : [{
					xtype : 'numberfield',
					name : 'fboxlength',
					hideLabel : true,
					value : 0,
					minValue : 0,
					width : 64,
					negativeText : '不能为负数'
				}, {
					xtype : 'displayfield',
					value : 'X',
					width : 8
				}, {
					xtype : 'numberfield',
					name : 'fboxwidth',
					value : 0,
					minValue : 0,
					width : 64,
					negativeText : '不能为负数'
				}, {
					xtype : 'displayfield',
					value : 'X',
					width : 8
				}, {
					xtype : 'numberfield',
					name : 'fboxheight',
					value : 0,
					minValue : 0,
					width : 64,
					negativeText : '不能为负数'
				}]

			}, {
				baseCls : 'x-plain',
				columnWidth : .25,
				xtype : 'fieldcontainer',
				fieldLabel : '纸板规格',
				labelWidth : 86,
				layout : 'hbox',
				items : [{
					xtype : 'numberfield',
					name : 'fboardlength',
					hideLabel : true,
					value : 0,
					minValue : 0,
					width : 100,
					negativeText : '不能为负数'
				}, {
					xtype : 'displayfield',
					value : 'X',
					width : 8
				}, {
					xtype : 'numberfield',
					name : 'fboardwidth',
					value : 0,
					minValue : 0,
					width : 100,
					negativeText : '不能为负数'

				}]

			}, {

				baseCls : 'x-plain',
				columnWidth : .25,
				xtype : 'fieldcontainer',
				fieldLabel : '出库规格',
				labelWidth : 87,
				layout : 'hbox',
				items : [{
					xtype : 'numberfield',
					name : 'fmateriallength',
					hideLabel : true,
					value : 0,
					minValue : 0,
					width : 100,
					negativeText : '不能为负数'
				}, {
					xtype : 'displayfield',
					value : 'X',
					width : 8
				}, {
					xtype : 'numberfield',
					name : 'fmaterialwidth',
					value : 0,
					minValue : 0,
					width : 100,
					negativeText : '不能为负数'

				}]
			}]

		}, {
			layout : 'column',
			baseCls : 'x-plain',
			items : [{
				bodyStyle : 'padding-left: 5px;padding-right: 5px;padding-top: 5px;',
				baseCls : 'x-plain',
				columnWidth : .25,
				layout : 'form',
				defaults : {
					xtype : 'textfield'
				},
				items : [{
					xtype : 'textfield',
					name : 'fboxmodelid',
					fieldLabel : '箱型结构',
					regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符"
				}]
			}, {
				bodyStyle : 'padding-left: 5px;padding-right: 5px;padding-top: 5px;',
				baseCls : 'x-plain',
				columnWidth : .25,
				layout : 'form',
				defaults : {
					xtype : 'textfield'
				},
				items : [{
					name : 'fcharacter',
					fieldLabel : '产品特征',
					regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符"

				}]
			}, {
				bodyStyle : 'padding-left: 5px;padding-right: 5px;padding-top: 5px;',
				baseCls : 'x-plain',
				columnWidth : .25,
				layout : 'form',
				labelWidth : 20,
				defaults : {
					xtype : 'textfield'
				},
				items : [{
					xtype : 'numberfield',
					value : 0,
					minValue : 0,
					negativeText : '不能为负数',
					name : 'fchromaticprecision',
					fieldLabel : '套色精度'

				}]
			}]
		}, {
			layout : 'column',
			baseCls : 'x-plain',
			items : [{
				bodyStyle : 'padding-left:5px;padding-right:5px;',
				baseCls : 'x-plain',
				columnWidth : .5,
				layout : 'form',
				defaults : {
					xtype : 'textfield'
				},
				items : [{
					xtype : 'textfield',
					name : 'fquality',
					fieldLabel : '品质要求',
					regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符"
				}, {
					xtype : 'textfield',
					name : 'fmodelmethod',
					fieldLabel : '成型方式',
					regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符"
				}, {
					xtype : 'textarea',
					name : 'fdescription',
					fieldLabel : '产品备注',
					regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符"
				}]
			}, {
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

				}, {
					xtype : 'checkbox',
					name : 'forderprice',
					boxLabel : '按单价设'
				}

				]
			}]
		}]
	}],
	buttons : [{
		xtype : "button",
		text : "确定",
		pressed : false,
		handler : SaveData
	}, {
		xtype : "button",
		text : "取消",
		handler : function() {
			var windows = Ext.getCmp("DJ.System.product.ProductDefEdit");
			if (windows != null) {
				windows.close();
			}
		}
	}],
	buttonAlign : "center",
	bodyStyle : "padding-top:5px;padding-left:5px"
});
function SaveData() {
	var cform = Ext.getCmp("DJ.System.product.ProductDefEdit.dForm").getForm();
	if (!cform.isValid()) {
		Ext.MessageBox.alert('提示', '输入项格式不正确，请按提示修改！');
		return;
	}
	var paramsT = Ext.getCmp("DJ.System.product.ProductDefEdit.fisAddVersion")
			.getValue();

	cform.submit({
		url : 'SaveProductdef.do?paramsT=' + paramsT,
		method : 'POST',
		waitMsg : '正在处理请求……',
		timeout : 60000,
		success : function(form, action) {
			var obj = Ext.decode(action.response.responseText);
//			Ext.MessageBox.alert('成功', obj.msg);
			Ext.getCmp("DJ.System.product.ProductDefList").store.load();
			Ext.getCmp("DJ.System.product.ProductDefEdit").close();
			djsuccessmsg( obj.msg);
		},
		failure : function(f, action) {
			var obj = Ext.decode(action.response.responseText);
			Ext.MessageBox.alert('错误', obj.msg);
		}
	});
}