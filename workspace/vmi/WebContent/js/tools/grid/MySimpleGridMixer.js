/**
 * 
 * grid简单混入类
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2015-1-30 下午4:02:58
 * 
 */
Ext.define("DJ.tools.grid.MySimpleGridMixer", {

	/**
	 * 
	 * @param {} ids,没有前缀的id
	 */
	_hideButtons : function(ids) {

		var me = this;

		Ext.each(ids, function(ele) {

			Ext.getCmp(me.id + '.' + ele).hide();

		});

	},
	

	_hideButtonsCURD : function() {

		var me = this;

		me._hideButtons(['addbutton', 'editbutton', 'viewbutton', 'delbutton']);

	},
	

	_hideExportExcellButton : function() {

		var me = this;

		me._hideButtons(['exportbutton']);

	},
	_hideButtonsCURDExclude : function(ids) {

		var me = this;

		var arrayT = ['addbutton', 'editbutton', 'viewbutton', 'delbutton'];

		me._hideButtons(Ext.Array.difference( arrayT, ids ));

	},
	
	_hideSearchButton : function() {

		var me = this;

		me._hideButtons(['querybutton']);

	}
	
});