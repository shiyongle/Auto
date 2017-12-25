Ext.define('DJ.System.QueryConfig.QueryConfigEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.System.QueryConfig.QueryConfigEdit",
	modal : true,
	onload : function() {
	},
	// custbar : [ {
	// // id : 'DelButton',
	// text : '自定义按钮1',
	// height : 30,
	// handler : function() {
	// // var a = Ext.getCmp('DJ.order.Deliver.DeliversEdit');
	// // a.seteditstate("edit"); // 设置界面可编辑"view"和""为不可编辑，其他都是可以编辑
	// // String str =
	// Ext.getCmp('DJ.order.Deliver.Delivers.farrivetime').getValue();
	// // str = str.substr(0,8);
	// //
	// Ext.getCmp('DJ.order.Deliver.DeliversEdit.farrivedate').setValue(Ext.getCmp('DJ.order.Deliver.Delivers.farrivetime').getValue().substr(0,10).replace("-","/").replace("-","/"));
	// }
	// }, {
	// // id : 'DelButton',
	// text : '自定义按钮2',
	// height : 30
	// } ],
	title : "查询配置编辑界面",
	width : 400,// 230, //Window宽度
	height : 350,// 137, //Window高度
	resizable : false,
	url : 'SaveQueryConfig.do',
	infourl : 'GetQueryConfiginfo.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'GetQueryConfiginfo.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true
	initComponent : function() {
		Ext.apply(this, {
			items : [{
				layout : "column",
				baseCls : "x-plain",
				items : [{// title:"列1",
					baseCls : "x-plain",
					columnWidth : 1,
					bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
					items : [{
								// id :
								// 'DJ.order.Deliver.product.CustproductEdit.FID',
								name : 'fid',
								xtype : 'textfield',
								labelWidth : 50,
								width : 300,
								hidden : true
							}, {
								// id :
								// 'DJ.order.Deliver.product.CustproductEdit.FID',
								name : 'fpath',
								xtype : 'textfield',
								fieldLabel : '路径',
								labelWidth : 100,
								width : 300,
								hidden : false
							}
							, {
								// id :
								// 'DJ.order.Deliver.product.CustproductEdit.FID',
								name : 'fseq',
								xtype : 'textfield',
								fieldLabel : '序号',
								labelWidth : 100,
								width : 300,
								hidden : false
							}, {
								// id :
								// 'DJ.order.Deliver.product.CustproductEdit.FID',
								name : 'fcolumnname',
								xtype : 'textfield',
								fieldLabel : '字段名称',
								labelWidth : 100,
								width : 300,
								hidden : false
							},
							// {
							// id : 'DJ.order.Deliver.Delivers.farrivetime',
							// name : 'farrivetime',
							// xtype : 'textfield',
							// fieldLabel : '配送时间',
							// hidden : true,
							// lesteners : function(){
							// afterrender : function(){
							// Ext.getCmp('DJ.order.Deliver.DeliversEdit.farrivedate').setValue(Ext.getCmp('DJ.order.Deliver.Delivers.farrivetime').getValue().substr(0,10).replace("-","/").replace("-","/"));
							// }
							// }
							// },
							{
								// id :
								// 'DJ.order.Deliver.product.CustproductEdit.FNUMBER',
								name : 'fcolumnkey',
								xtype : 'textfield',
								fieldLabel : '字段键值',
								// // allowBlank : false,
								// // blankText : '采购订单号不能为空',
								// regex : /^([\u4E00-\u9FA5]|\w|[@.()\-])*$/,//
								// /^[^,\!@#$%^&*()_+}]*$/,
								// regexText : "不能包含特殊字符",
								width : 300,
								labelWidth : 100
							}, {
								xtype : 'fieldcontainer',
								fieldLabel : '参与过滤',
								defaultType : 'checkboxfield',
								items : [{
											boxLabel : '',
											name : 'ffilterable',
//											readOnly : true,
											inputValue : '1'
										}]
							}, {
								// id :
								// 'DJ.order.Deliver.product.CustproductEdit.Fcustomer',
								name : 'ffilterfield',
								xtype : 'textfield',
								fieldLabel : '数据库字段名',
								labelWidth : 100,
								// regex : /^1[3|4|5|8][0-9]\d{4,8}$/,//
								// /^[^,\!@#$%^&*()_+}]*$/,
								// regexText : "你输入的不是手机号",
								width : 300
							}, {
								// id :
								// 'DJ.order.Deliver.product.CustproductEdit.Fcustomer',
								name : 'ftype',
								xtype : 'textfield',
								fieldLabel : '类型',
								// regex : /^1[3|4|5|8][0-9]\d{4,8}$/,//
								// /^[^,\!@#$%^&*()_+}]*$/,
								// regexText : "你输入的不是手机号",
								width : 300,
								labelWidth : 100
							}, {
								// id :
								// 'DJ.order.Deliver.product.CustproductEdit.Fcustomer',
								name : 'fwidth',
								xtype : 'textfield',
								fieldLabel : '列宽',
								// regex : /^1[3|4|5|8][0-9]\d{4,8}$/,//
								// /^[^,\!@#$%^&*()_+}]*$/,
								// regexText : "你输入的不是手机号",
								width : 300,
								labelWidth : 100
							}, {
								xtype : 'fieldcontainer',
								fieldLabel : '是否隐藏',
								defaultType : 'checkboxfield',
								items : [{
											boxLabel : '',
											name : 'fhide',
//											readOnly : true,
											inputValue : '1'
										}]
							}, {
								// id :
								// 'DJ.order.Deliver.product.CustproductEdit.Fcustomer',
								name : 'fsql',
								xtype : 'textfield',
								fieldLabel : '查询语句',
								// regex : /^1[3|4|5|8][0-9]\d{4,8}$/,//
								// /^[^,\!@#$%^&*()_+}]*$/,
								// regexText : "你输入的不是手机号",
								width : 300,
								labelWidth : 100
							}]
				}]
			}]
		}), this.callParent(arguments);
	},
	bodyStyle : "padding-top:15px;padding-left:30px"
		// ,listener : {
		// afterrender : function(){
		// alert("1");
		// }
		// }
});

function formatEffect(value) {
	return value == '1' ? '是' : '否';
}