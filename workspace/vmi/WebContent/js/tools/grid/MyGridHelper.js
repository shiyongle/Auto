/**
 * 
 * grid混入类
 * 
 * @since long ago
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2014-12-31 下午2:01:35
 * 
 */
Ext.define("DJ.tools.grid.MyGridHelper", {
	
	_hideButtons : function (ids) {
		
		var me = this;
		
		Ext.each(ids, function(ele){
			
			Ext.getCmp(me.id + '.' + ele).hide();
			
		});
		
		
	}
	,
	_hideDefaultButtons : function() {

		var me = this;

		var idT = me.id;

		var hideSs = ['addbutton', 'editbutton', 'viewbutton', 'delbutton',
				'refreshbutton', 'querybutton', 'exportbutton'];

		Ext.each(hideSs, function(ele, index, all) {

			var idTT = idT + "." + ele;

			Ext.getCmp(idTT).hide();

		});

	},
	
	/**
	 * 主要调用这个方法
	 * 
	 * @param {}
	 *            show，bool，
	 * @param {}
	 *            array，元素为button ID或为数字索引（从0开始）
	 */
	_operateButtonsView : function(show, array) {

		if (Ext.typeOf(array[0]) == "number") {
			array = this._translateNumToDataIndex(array);
		}

		if (show) {
			this._showButtons(array);
		} else {
			this._hideButtons(array);
		}

	},
	_translateNumToDataIndex : function(array) {
		var arrayt = [];

		var arrayAll = this._getToolsButtonIDs();

		Ext.each(arrayAll, function(item, index, all) {
					if (Ext.Array.contains(array, index)) {
						arrayt.push(arrayAll[index]);
					}
				});

		return arrayt;
	},

	_hideButtons : function(array) {
		var t = array;
		Ext.each(t, function(item, index, all) {
					Ext.getCmp(item).hide();
				});
	},

	_showButtons : function(array) {
		var defaultButtons = this._getToolsButtonIDs();

		var tArray = Ext.Array.difference(defaultButtons, array);

		this._hideButtons(tArray);
	},

	_getToolsButtonIDs : function() {
		var defaultButtons = [];

		var t = this.dockedItems.items[2].items.items;

		Ext.each(t, function(item, index, all) {

					defaultButtons.push(item.id);

				});
		return defaultButtons;
	}

});