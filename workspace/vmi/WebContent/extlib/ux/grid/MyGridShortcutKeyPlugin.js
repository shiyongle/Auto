/**
 * grid快捷键
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2014-12-31 下午1:19:58
 * 
 * 
 */
Ext.define('Ext.ux.grid.MyGridShortcutKeyPlugin', {
	extend : "Ext.AbstractPlugin",
	alias : 'plugin.mygridshortcutkeyplugin',

	alternateClassName : 'MyGridShortcutKeyPlugin',

	disabled : false,

	// 启用复制粘帖功能
	enableCopyPaste : true,
	
	// 启用全选
	enableSelectAll : true,
	
	// 其他快捷键设置
	bindings : [],
	
	// private
	gridKeyMap : null,
	_enableGridKeyMap : function() {

		var me = this;

		me.gridKeyMap.getGridKeyMap().setDisabled(me.disabled);

	},
	/**
	 * 覆写启用禁用方法，用顺序器
	 * 
	 * @param {}
	 *            me
	 */
	_resetDisableRel : function(me) {
		var enableT = Ext.Function.createSequence(me.enable,
				me._enableGridKeyMap);
		if(!Ext.isIE){
			me.enable = enableT.bind(me);
		}				

		var disableT = Ext.Function.createSequence(me.disable,
				me._enableGridKeyMap);
		if(!Ext.isIE){
			me.disable = disableT.bind(me);
		}
	},
	init : function() {

		var me = this;
		this._resetDisableRel(me);
		// load
		var comGrid = this.cmp;

		if (me.disabled) {

			return;

		}

		// 持有对应工具类实例
		var map = Ext.create("DJ.tools.grid.MyGridShortcutKeyUtil", {

			enableCopyPaste : me.enableCopyPaste,
			bindings : me.bindings,
			grid : comGrid,
			enableSelectAll : me.enableSelectAll

		});

		me.gridKeyMap = map;

	},
	destroy : function() {

		var me = this;
		// 释放
		me.gridKeyMap = null;

	}
});