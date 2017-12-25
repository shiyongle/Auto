/**
 * grid内容菜单插件，//com, record, item, index, e, eOpts等变量
 * 
 * @author ZJZ（447338871@qq.com）
 * 
 * @version 0.1 2014-10-30 下午2:14:18
 * @version 0.2 2015-1-5 下午5:00:43,新增row复制黏贴功能，优化样式
 * @version 0.2.1 2015-1-21 下午2:27:55，增加菜单otherItems handlerN配置，传入右键菜单参数
 * 
 * 
 */

// Ext.require("DJ.tools.grid.MyGridRowCopyPasteUntils");
Ext.define('Ext.ux.grid.MySimpleGridContextMenu', {
	extend : "Ext.AbstractPlugin",
	alias : 'plugin.mysimplegridcontextmenu',

	requires : ['DJ.tools.grid.MyGridRowCopyPasteUntils'],

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

	// 其他额外的组件，需要自己配置对象
	otherItems : [],

	disabled : false,

	// 复制表格，下面2个配套
	useCopyItem : false,
	// // 如果有表格复制，下面是是否启用快捷键.仅仅是显示提示
	// useCopyItemShortcutKey : false,

	// 选择器,menuseparator为分隔符。用了这个下面就不用配置了
	useExistingButtons : [],

	// 默认的三个按钮，一般用不到。当只有处理器而没有工具栏里的按钮时或许会用到。
	viewHandler : null,
	editHandler : null,
	deleteHandler : null,

	// 反转控制配置,在欲显示在菜单中的按钮配置
	showInMenu : false,

	gridKeyMap : null,

	enable : function() {
		var me = this;
		me.disabled = false;
		me.gridKeyMap.getGridKeyMap().setDisabled(me.disabled);

	},
	disable : function() {
		var me = this;
		me.disabled = true;
		me.gridKeyMap.getGridKeyMap().setDisabled(me.disabled);
	},

	init : function() {

		var me = this;

		// load
		var comGrid = this.cmp;

		if (me.disabled) {

			return;

		}

		comGrid.on("itemcontextmenu", function(com, record, item, index, e,
				eOpts) {

			if (me.disabled) {

				return;

			}

			e.stopEvent();// 取消浏览器默认事件

			var arrayConfig = [];

			if (me.useCopyItem) {

				// 快捷键工具
				me.gridKeyMap = Ext.create(
						"DJ.tools.grid.MyGridShortcutKeyUtil", {

							enableCopyPaste : true,
							grid : comGrid

						});

				arrayConfig = me._joinCopyItem(arrayConfig, comGrid, record,
						item, index, e, eOpts);
			}

			// 没有使用按钮配置
			if (me.useExistingButtons.length == 0 && !me.useCopyItem
					&& me.otherItems.length == 0) {
				arrayConfig = me._setDefaultMenuCfg(comGrid, arrayConfig, me);

			}

			if (me.useExistingButtons.length != 0) {
				me._joinUseExistingButtons(comGrid, arrayConfig, me);
			}

			// 控制反转，不常用
			me._joinShowInMenu(comGrid, arrayConfig);

			if (me.otherItems.length != 0) {

				Ext.each(me.otherItems, function(ele, index, all) {

					var handlerT = ele.handlerN;

					var handlerNT = function() {

						handlerT.call(this, com, record, item, index, e, eOpts);
					};

					ele.handler = handlerNT;

				});

				arrayConfig = arrayConfig.concat(me.otherItems);

			}

			var nodemenu = new Ext.menu.Menu({
				// plain : true,

				items : arrayConfig
			});
			nodemenu.showAt(e.getXY());// 菜单打开的位置

		});

	},
	_joinCopyItem : function(arrayConfig, com, record, item, index, e, eOpts) {

		var me = this;

		var copyItem = [{
			text : "复制行",

			tooltip : "试试Alt+c",
			handler : function(ele, eThis) {

				MyGridRowCopyPasteUntils.copyGridRow(com);

			}
		}, {

			text : "粘帖行",
			tooltip : "试试Alt+v",
			handler : function(ele, eThis) {

				MyGridRowCopyPasteUntils.pasteGridRow(com);

			}

		}];

		var r;

		if (me.useExistingButtons.length != 0 || me.otherItems.length != 0) {

			r = arrayConfig.concat(copyItem, {
				xtype : 'menuseparator'
			});

		} else {

			r = arrayConfig.concat(copyItem);
		}

		return r;

	},
	/*
	 * 设置为默认菜单
	 */
	_setDefaultMenuCfg : function(gridcom, arrayConfig, me) {

		var handlersP = [me.viewHandler, me.editHandler, me.deleteHandler];

		var handlers = [];

		Ext.each(handlersP, function(ele, index, all) {

			var arg = Ext.ux.grid.MySimpleGridContextMenu.selectHandler(ele,
					gridcom);

			handlers.push(arg[0].bind[1]);

		});

		return arrayConfig.concat([{
			text : '查看',
			handler : handlers[0]
		}, {

			xtype : 'menuseparator'

		}, {
			text : '修改',
			handler : handlers[1]
		}, {
			text : '删除',
			handler : handlers[2]
		}]);

	},

	/**
	 * 设置为现有的按钮
	 * 
	 * @param {}
	 *            gridcom
	 * @param {}
	 *            arrayConfig
	 * @param {}
	 *            me
	 */
	_joinUseExistingButtons : function(gridcom, arrayConfig, me) {
		Ext.each(me.useExistingButtons, function(ele, index, all) {

			var objT = {
				xtype : 'menuseparator'
			};

			if (ele != "menuseparator") {

				var comT = gridcom.down(ele);

				objT = {
					text : comT.text,
					handler : comT.handler.bind(comT)
				};

			}

			arrayConfig.push(objT);

		});
	},

	/**
	 * 控制反转，根据组件button的showInMenu添加菜单项目
	 * 
	 * @param {}
	 *            gridcom
	 * @param {}
	 *            arrayConfig
	 */
	_joinShowInMenu : function(gridcom, arrayConfig) {

		var buttons = gridcom.query("button[showInMenu=true]");

		Ext.each(buttons, function(ele, index, all) {

			var objT = {

				text : ele.text,
				handler : ele.handler.bind(ele)

			};

			arrayConfig.push(objT);

		});

	}
});