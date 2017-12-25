/**
 * grid快捷键工具类
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2014-12-31 下午1:12:27
 * 
 */
Ext.define("DJ.tools.grid.MyGridShortcutKeyUtil", {

	alternateClassName : "MyGridShortcutKeyUtil",

	requires : ['DJ.tools.grid.MyGridRowCopyPasteUntils',
			'DJ.tools.common.MyCommonToolsZ'],

	statics : {
		// 复制配置
		copyCfg : {

			key : Ext.EventObject.C,
			alt : true,
			//演示,提示配置函数，没有则没有提示
			gainComAndTip : function(comGrid) {
				return {

					com : null,
					tip : ""

				};
			},
			handler : function(keyCode, e) {

				var grid = e.grid;

				MyGridRowCopyPasteUntils.copyGridRow(grid);
			}

		},

		pasteCfg : {

			key : Ext.EventObject.V,
			alt : true,
			handler : function(keyCode, e) {

				var grid = e.grid;
				MyGridRowCopyPasteUntils.pasteGridRow(grid);
			}

		},
		//全选配置
		selectAllCfg : {

			key : Ext.EventObject.A,
			alt : true,
			handler : function(keyCode, e) {

				var grid = e.grid;

				grid.getSelectionModel().selectAll(true);
			}

		}
	},

	config : {
		// keymap实例
		gridKeyMap : null

	},
	// 启用复制黏贴快捷键
	enableCopyPaste : true,
	// 启用全选
	enableSelectAll : true, 
	// 其他额外的快捷键配置
	bindings : [],

	grid : null,

	/**
	 * 构建keymap cfg
	 * 
	 * @param {}
	 *            me
	 * @param {}
	 *            comGrid
	 * @return {}
	 */
	buildCfg : function(me, comGrid) {
		var bindingsT = [];

		if (me.enableCopyPaste) {

			bindingsT.push(MyGridShortcutKeyUtil.copyCfg);
			bindingsT.push(MyGridShortcutKeyUtil.pasteCfg);

		}

		if (me.enableSelectAll) {
		
			bindingsT.push(MyGridShortcutKeyUtil.selectAllCfg);
		}
		
		bindingsT = bindingsT.concat(me.bindings);

		var keyMapCfg = {
			target : comGrid.id,

			processEvent : function(event) {

				// Load the event with the extra information needed by the
				event.grid = comGrid;
				return event;
			},

			binding : bindingsT

		};
		return keyMapCfg;
	},

	/**
	 * 创建keymap
	 */
	builGridKeyMap : function() {

		var me = this;

		var comGrid = me.grid;

		var keyMapCfg = me.buildCfg(me, comGrid);

		MyCommonToolsZ.setComAfterrender(comGrid, function(com, eOpts) {

			//设置提示
			Ext.each(me.bindings, function(ele, index, all) {
			
				if (ele.gainComAndTip) {

					var ret = ele.gainComAndTip(comGrid);

					var com = ret.com;
					var tip = ret.tip;

					MyCommonToolsZ.setQuickTip(com.id, "", "试试" + tip);

				}
			} );
			
			me.gridKeyMap = Ext.create("Ext.util.KeyMap", keyMapCfg);

		});

	},

	constructor : function(config) {

		var me = this;
		// 复制配置
		Ext.apply(me, config);
		// 初始化config
		this.initConfig();

		me.builGridKeyMap();

		return this;
	}

})
