Ext.define('DJ.myComponent.button.MySimpleUpLoadExcelWin', {
	extend : 'Ext.Window',
	id : 'DJ.myComponent.button.MySimpleUpLoadExcelWin',
	modal : true,
	title : "Excel文件上传",
	width : 450,// 230, //Window宽度
	height : 150,// 137, //Window高度
	resizable : false,
	closable : true, // 关闭按钮，默认为true
	// renderTo: 'upload',

	uploadUrl : '',

	items : [{
		xtype : 'form',
		// id :
		// 'DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel.form',
		baseCls : 'x-plain',
		fieldDefaults : {
			labelWidth : 400
		},
		// items:[
		// {
		// title: '上传图片',
		width : 400,
		bodyPadding : 10,
		margin : '20 10 20 20',
		frame : true,

		items : [{
			xtype : 'filefield',
			name : 'fileName',
			fieldLabel : '上传',
			labelWidth : 50,
			msgTarget : 'side',
			allowBlank : false,
			anchor : '100%',
			regex : /(.)+((\.xlsx)|(\.xls)(\w)?)$/i,
			regexText : "文件格式选择不正确",
			buttonText : '选择文件'
		}]
	// }]
	}],

	initComponent : function() {

		var me = this;

		me.buttons = [{
			text : '上传',
			handler : function() {

				var form = this.up('window').down('form').getForm();

				if (form.isValid()) {
					form.submit({
						url : me.uploadUrl,
						waitMsg : '正在保存文件...',
						success : function(fp, o) {
							Ext.Msg.show({
								title : '提示信息',
								msg : o.result.msg,
								minWidth : 200,
								modal : true,
								buttons : Ext.Msg.OK
							})
							form.findField('fileName').setRawValue('');
							this.up("window").close();
						},
						failure : function(fp, o) {
							Ext.Msg.show({
								title : '提示信息',
								msg : o.result.msg,
								minWidth : 200,
								modal : true,
								buttons : Ext.Msg.OK
							})
							form.findField('fileName').setRawValue('');
						}
					});
				}
			}
		}]

		me.callParent(arguments);

	}

});

Ext.define('DJ.myComponent.button.MySimpleUpLoadExcelWinButton', {
	extend : 'Ext.button.Button',

	alias : ['widget.mysimpleuploadexcelwinbutton'],

	uploadUrl : '',

	statics : {

		handlerC : function(me) {

			var loadupFormpanel = Ext
					.getCmp("DJ.myComponent.button.MySimpleUpLoadExcelWin");
			if (loadupFormpanel == null) {
				loadupFormpanel = Ext.create(
						"DJ.myComponent.button.MySimpleUpLoadExcelWin", {

							uploadUrl : me.uploadUrl

						});
			}

			loadupFormpanel.show();

		}

	},

	text : "批量导入",

	initComponent : function() {
		var me = this;

		me.handler = function() {

			me.self.handlerC(me);

		}

		me.callParent(arguments);
	}
});
