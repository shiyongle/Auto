/**
 * 上传excel
 * 
 * 
 */
Ext.define('DJ.myComponent.button.PlaceAnOrdeButton', {
	extend : 'Ext.button.Button',

	alias : ['widget.placeanordebutton'],

	text : "下单",
	
	noButtons : false,
	
	gianCustProductIds : function () {
	
		
		return -1;
	},
	
	gianSingleButtonOrHandler : function () {
	
		return -1;
	},
	
	gianMulitButtonOrHandler : function () {
	
		return -1;
		
	},
	
	statics : {

		

	},

	

	initComponent : function() {
		var me = this;

		var handlerT = function (button) {
		
			Ext.Ajax.request({
				timeout : 6000,
				
				url : "gainOrderWayCfg.do",
				success : function(response, option) {

					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {

						var orderType = obj.data[0].fvalue;

		
						switch (orderType) {

							case "single" :
								if (me.noButtons) {
									me.gianSingleButtonOrHandler.call(button);

								} else {
									var bt = me.gianSingleButtonOrHandler();
									bt.handler.call(bt);

								}
								break;

							case "multi" :

								if (me.noButtons) {
									me.gianMulitButtonOrHandler.call(button);

								} else {
									var bt = me.gianMulitButtonOrHandler();
									bt.handler.call(bt);

								}

								break;

							case "file" :

								DJ.myComponent.button.UpLoadDeliverApplyWinButton.handlerC
										.call(button);

								break;

						}

					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}

				}
			});

		
			
		} ;
		
		me.handler = DJ.myComponent.button.UpLoadDeliverApplyWinButton.handlerC;

		me.callParent(arguments);
	}
});