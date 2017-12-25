

Ext.define('DJ.myComponent.button.MySimpleUpLoadExcelWinButton', {
	extend : 'Ext.button.Button',

	alias : ['widget.mysimpleuploadexcelwinbutton'],

	statics : {

		handlerC : function() {

			var loadupFormpanel = Ext
					.getCmp("DJ.myComponent.win.UpLoadDeliverApplyWin");
			if (loadupFormpanel == null) {
				loadupFormpanel = Ext
						.create("DJ.myComponent.win.UpLoadDeliverApplyWin");
			}

			Ext.Ajax.request({
				timeout : 600000,
				url : "GetCustomerListByUserId.do",
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						if (obj.data.length == 1) {
							var cform = Ext
									.getCmp("DJ.myComponent.win.UpLoadDeliverApplyWin").down("form")
									.getForm();
							cform.findField('fcustomerid')
									.setmyvalue("\"fid\":\"" + obj.data[0].fid
											+ "\",\"fname\":\""
											+ obj.data[0].fname + "\"");
						}
					} else {

					}
				}
			});
			loadupFormpanel.show();

		}

	},

	text : "批量导入",

	initComponent : function() {
		var me = this;

		me.handler = DJ.myComponent.button.UpLoadDeliverApplyWinButton.handlerC;

		me.callParent(arguments);
	}
});