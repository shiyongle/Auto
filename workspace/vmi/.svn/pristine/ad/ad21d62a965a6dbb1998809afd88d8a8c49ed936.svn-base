Ext.define('DJ.Inv.ProductInvControl.ProductInvControlEdit', {
	extend : 'Ext.Window',
	id : 'DJ.Inv.ProductInvControl.ProductInvControlEdit',
	modal : true,
	title : "订单出入库",
	width : 400,
	height : 180,
	resizable : false,
	layout : 'form',

	productID : "",

	labelAlign : "left",

	defaults : {
		xtype : 'textfield',
		msgTarget : 'side'
	},

	closable : true,

	initComponent : function() {
		Ext.apply(this, {
			items : [{
				id : 'DJ.Inv.ProductInvControl.ProductInvControlEdit.theSetNumber',

				xtype : 'numberfield',
				fieldLabel : '数量',
				width : 260,
				labelWidth : 70,
				allowBlank : false,
				allowDecimals : false,
				blankText : '数量不能为空'

			}]
		}), this.callParent(arguments);
	},
	buttons : [{

		xtype : "button",
		text : "确定",

		handler : function() {

			var el = this.up("window").getEl();

			el.mask("系统处理中,请稍候……");

			Ext.Ajax.request({
				timeout : 60000,
				url : "doUpdateInBalanceAndWarehouseInOut.do",
				params : {
					fproductID : this.up("window").productID,
					theSetNumber : Ext
							.getCmp("DJ.Inv.ProductInvControl.ProductInvControlEdit.theSetNumber")
							.getValue()

				}, // 参数

				callback : function(obj, success, response) {

					var obj = Ext.decode(response.responseText);

					if (obj.success == true) {

						Ext.create('widget.uxNotification', {
									// title : '提示',
									width : 100,
									height : 50,
									position : 'tc',
									manager : 'demo1',
									iconCls : 'ux-notification-icon-information',
									autoCloseDelay : 2000,
									spacing : 3000,
									html : '成功'
								}).show();

						Ext
								.getCmp("DJ.Inv.ProductInvControl.ProductInvControlList").store
								.load();

					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}

					el.unmask();
					Ext
							.getCmp("DJ.Inv.ProductInvControl.ProductInvControlEdit")
							._thisClose();
				}
			});

		}
	}, {
		xtype : "button",
		text : "取消",
		handler : function() {
			Ext.getCmp("DJ.Inv.ProductInvControl.ProductInvControlEdit")
					._thisClose();
		}
	}],

	_thisClose : function() {

		var windows = this;
		if (windows != null) {
			windows.close();
		}

	},
	buttonAlign : "center",
	bodyStyle : "padding-top:15px ;padding-left:30px;padding-right:30px"
});