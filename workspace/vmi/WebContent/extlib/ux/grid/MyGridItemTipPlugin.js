/**
 * griditem tip插件
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2014-11-14 下午1:41:59
 * 
 */
Ext.define('Ext.ux.grid.MyGridItemTipPlugin', {
	extend : "Ext.AbstractPlugin",
	alias : 'plugin.mygriditemtipplugin',

	requires : ['DJ.tools.common.MyCommonToolsZ'],

	// statics : {
	// // 返回处理器，使得obj可以是function也可以是选择器
	// selectHandler : function(obj, com) {
	//
	// if (typeof(obj) == 'function') {
	//
	// return obj;
	//
	// } else if (obj != null && obj != undefined && obj != "") {
	//
	// return com.down(obj).handler
	//
	// } else {
	//
	// return function() {
	// };
	// }
	// }
	// },

	separator : ',',

	// grid store field,本地
	showingFields : [],

	// // 在现实后的回调
	// showCallBack : function(com, eOpts, gridCom) {
	// },

	// 远程，不要和showingFields同时配置。后面三个是一套的，前面2个必须
	url : "",

	ajaxParams : [],

	tipProperty : "msg",

	_buildAjaxParams : function(tip, view) {

		var me = this;

		var params = {};

		var tipSA = [];

		Ext.each(me.ajaxParams, function(ele, index, all) {

			params[ele] = view.getRecord(tip.triggerElement).get(ele);

		});

		return params;

	},
	_buildTip : function(me, obj) {

		var objT = obj;

		var tipPropertys = me.tipProperty.split(".");

		Ext.each(tipPropertys, function(ele, index, all) {

			objT = objT[ele];

			if (Ext.typeOf(objT) == "array") {

				objT = objT[0];

			}

		});

		return objT;

	},
	init : function() {

		var me = this;

		// load
		var gridCom = me.cmp;

		MyCommonToolsZ.setComAfterrender(gridCom, function(com, eOpts) {

			var view = gridCom.getView();
			var tip = Ext.create('Ext.tip.ToolTip', {
				maxWidth : 1000,
				dismissDelay : 30000,
				// The overall target element.
				target : view.el,
				// Each grid row causes its own separate show and hide.
				delegate : view.itemSelector,
				// Moving within the row should not hide the tip.
				trackMouse : true,
				// Render immediately so that tip.body can be referenced
				// prior
				// to the first show.
				renderTo : Ext.getBody(),
				listeners : {

					// show : function(com, eOpts) {
					// me.showCallBack(com, eOpts, gridCom)
					// },

					// Change content dynamically depending on which element
					// triggered the show.
					beforeshow : function updateTipBody(tip) {

						// var tipS = "";
						tip.update("");

						if (me.showingFields.length != 0) {

							var tipSA = [];

							Ext.each(me.showingFields,
									function(ele, index, all) {

										tipSA.push(view
												.getRecord(tip.triggerElement)
												.get(ele));

									});

//							tip.show();

							tip.update(tipSA.join(me.separator));

						} else if (me.ajaxParams.lenght != 0) {

							var params = me._buildAjaxParams(tip, view);

							// var el = tip.getEl();
							// el.mask("系统处理中,请稍候……");

							Ext.Ajax.request({
								// timeout : 6000,

								params : params,

								url : me.url,
								success : function(response, option) {

									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										// Ext.MessageBox.alert('成功', obj.msg);
										// tip.update(obj[me.tipProperty]);

//										tip.show();

										var tipT = me._buildTip(me, obj);

										tip.update(tipT);
									} else {
										Ext.MessageBox.alert('错误', obj.msg);

									}
									// el.unmask();
								}
							});

						}

					}
				}
			});

		});

	}
});