/**
 * grid row黏贴复制工具类
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2014-12-31 上午11:17:16
 * 
 * 
 */
Ext.define("DJ.tools.grid.MyGridRowCopyPasteUntils", {
	singleton : true,

	alternateClassName : "MyGridRowCopyPasteUntils",

	requires : ['DJ.tools.grid.MyGridTools', 'DJ.tools.common.MyCommonToolsZ'],

	//临时的内容容器
	items : [],

	/**
	 * 复制grid row
	 * @param {} grid
	 */
	copyGridRow : function(grid) {

		var me = this;

		var items = MyCommonToolsZ.pickSelectItems(grid);

		if (items == -1) {

			return;
		}

		var itemsT = [];
		
		Ext.each(items, function(ele, index, all) {
		
			itemsT.push(ele.data);
			
		} );
		
		me.items = itemsT;

	},

	/**
	 * 黏贴grid row
	 * @param {} grid
	 */
	pasteGridRow : function(grid) {

		var me = this;

		var store = grid.getStore();

		store.loadData(me.items, true);

		Ext.getCmp(grid.id).scrollByDeltaY(store.data.length * 20, true);

	},

	constructor : function() {

		return this;

	}

})