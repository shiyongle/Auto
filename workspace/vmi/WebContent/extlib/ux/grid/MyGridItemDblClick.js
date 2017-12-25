/**
 * grid双击插件，可在传入函数中使用com, record, item, index, e, eOpts 等变量
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2014-11-1 下午1:22:18
 * 
 */
Ext.define('Ext.ux.grid.MyGridItemDblClick', {
	extend : "Ext.AbstractPlugin",
	alias : 'plugin.mygriditemdblclick',

	statics : {
		// 返回处理器，使得obj可以是function也可以是选择器
		
		selectHandler : function(obj, com) {

			var caller = null;

			if (typeof(obj) == 'function') {

				return [obj, caller];

			} else if (obj != null && obj != undefined && obj != "") {

				caller = com.down(obj);

				return [com.down(obj).handler, caller];

			} else {

				return [function() {
				}, caller];
			}
		}
	},

	// 可以函数或是按钮选择器
	dbClickHandler : null,

	init : function() {

		var me = this;
		// load
		var comGrid = this.cmp;

		comGrid.on("itemdblclick",
				function(com, record, item, index, e, eOpts) {

					var paramsT = Ext.ux.grid.MyGridItemDblClick.selectHandler(
							me.dbClickHandler, comGrid);

					paramsT[0].call(paramsT[1], com, record, item, index, e,
							eOpts);

				}

		);

	}
});