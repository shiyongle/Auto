Ext.define('DJ.order.saleOrder.PreProductDemandEastEdit', {
	extend : 'Ext.c.BaseEditUI',
	id:'DJ.order.saleOrder.PreProductDemandEastEdit',
	modal : true,
	title : "模版资料上传界面",
	width : 450,
	height : 250,
	resizable : false,
	url : 'savePreProductDemandPlan.do',
	bodyPadding : 30,
	items : [{
		xtype : 'textfield',
		fieldLabel : '客户版面',
		name : 'fcustpage',
		labelWidth : 100,
		width : 320,
		allowBlank : false,
		blankText : '客户版面不能为空！'
	},{
		xtype : 'filefield',
		fieldLabel : '平面图纸名称',
		buttonText : '上传',
		name : 'file',
		labelWidth : 100,
		width : 320,
		margin : '20 0 0 0',
		allowBlank : false,
		blankText : '请选择上传文件...',
		regex : /\.jpg$/,
		regexText : '只能上传jpg格式的文件！',
		msgTarget : 'side'
	},{
		xtype: 'textfield',
		name: 'fcustomerid',
		hidden: true
	}]
});