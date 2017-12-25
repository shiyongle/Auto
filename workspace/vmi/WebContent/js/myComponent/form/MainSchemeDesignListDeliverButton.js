Ext.define('DJ.myComponent.form.MainSchemeDesignListDeliverButton', {
	extend : 'Ext.button.Button',

	alias : ['widget.mainschemedesignlistdeliverbutton'],


	isCreateDeliver : true,

	initComponent : function() {
		var me = this;

		var textT = "生成配送";
		if (!me.isCreateDeliver) {

			textT = "取消生成配送";

		}

		me.text = textT;

		var handlerT = function(button, event) {

			var grid = button.up("grid");
			
			var records = grid.getSelectionModel().getSelection();
			if (records.length < 1) {
				Ext.MessageBox.alert("提示", "请选择一条或多条记录进行操作！");
				return;
			}
			var result = [];
			for (var i = 0; i < records.length; i++) {

				result.push(records[i].get('fid'));

			}
			if (result.length == 0) {
				Ext.MessageBox.alert("提示", "请选择一条或多条记录进行操作！");
				return;
			}
			result = result.join(",");

			if (result == -1) {
				return;
			}

			var me = this;
			var el = me.getEl();
			el.mask("系统处理中,请稍候……");

			var url = "generateDelivery.do";

			if (!me.isCreateDeliver) {

				url = "undoGenerateDelivery.do";

			}

			Ext.Ajax.request({
				timeout : 60000,
				url : url,
				params : {
					fids : result
				},
				success : function(response, option) {

					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						grid.getStore().load();
						Ext.MessageBox.alert('成功', obj.msg);

					} else {
						Ext.MessageBox.alert('错误', obj.msg);

					}
					el.unmask();
				}
			});

		};

		me.handler = handlerT;
		
		me.callParent(arguments);
	}
});