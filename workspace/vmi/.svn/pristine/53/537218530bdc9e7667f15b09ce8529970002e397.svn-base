/**
 * excel批量粘帖组件
 * 
 * @author ZJZ（447338871@qq.com）
 * 
 * @version 0.1 2014-12-31 下午1:27:04
 * @version 0.2 2015-1-5
 *          上午11:20:16,大幅升级功能：支持隐藏域附带赋值；低侵入式直调原方法保存；部分列黏贴；黏贴后的回调函数支持；
 * 
 */
Ext.define("DJ.myComponent.button.ExcelPasterButton", {
	extend : 'Ext.button.Button',

	alias : ['widget.exportexcelbutton'],

	requires : ['DJ.tools.grid.MyGridTools', 'DJ.tools.common.MyCommonToolsZ',
			'DJ.tools.grid.ExcelHelper', 'DJ.tools.grid.ExcelPaster'],

	text : '批量黏贴',

	// 禁用自带同步，下面4个是配套配置
	disableSync : true,
	// 后台url
	saveUrl : "",
	// 对应的java bean class name
	javaBeanClassName : "-1",
	// 同步后删除添加的items
	removeItemsAfterSync : false,

	
	/**
	 * 当需要非grid列域的值时配置，如根据name获取id值。其中对象格式如__otherFieldRemoteCfg
	 * 
	 * @type {}
	 */
	otherFieldRemoteCfgs : [],
	
	//不要使用，仅供otherFieldRemoteCfgs配置参考
	__otherFieldRemoteCfg : {
		beanName : '',// hql里面的bean名称
		sourceField : '',// 源域，用于获取错误的中文提示
		sourceFieldInBean : '',// 在bean中的名称，用于hql取值
		goalField : '',// 目标域，在bean中的名称，用于hql取值
		goalDataIndex : ''// 目标域，dataIndex，用于返回后的赋值
	},
	
	// dataItems : [],

	// 列条件，设置就不选择全部列，选择其中的
	condition : [],

	//黏贴后的回调
	onAfterPasteToGrid : function (com, dataItemsMC) {
	
		
	
	},
	
	// 如果表格不能正常获取，就要配置表格获取器
	gainGrid : function() {
		return false;
	},

	//一般用不到，用自己同步方法时配置且有原来的保存方法时配置，用于创建拦截器
	gainSaveButton : function() {

		return false;
	},

	// mixdCollection,private
	dataItemsMC : [],

	isSyncedOrNoData : function() {

		return this.dataItemsMC.length == 0;

	},

	showAndGetDataItemsMCState : function() {

		var me = this;

		if (!me.isSyncedOrNoData()) {

			Ext.Msg.alert("提示", "有未同步数据，请先同步或撤销！");

			return false;
		} else {

			return true;

		}

	},

	updateDataItemsMC : function(dataItemsMC) {

		var me = this;

		if (dataItemsMC == -1) {

			me.dataItemsMC = [];

		} else {

			me.dataItemsMC = me.dataItemsMC.concat(dataItemsMC);

		}

	},

	_resetSaveButtonHandler : function() {

		var me = this;

		var btn = me.gainSaveButton();

		if (btn) {

			var handlerT = Ext.Function.createInterceptor(btn.handler,
					function(btn) {
						return me.showAndGetDataItemsMCState();
					}, btn);

			btn.setHandler(handlerT);

		}

	},

	listeners : {

		menushow : function(com, menu, eOpts) {
			com.setDataItemsRelField();
		},
		mouseover : function(com, e, eOpts) {
			com.showMenu();
		}
	// ,
	// mouseout : function(com, e, eOpts) {
	//
	// if (e.relatedTarget.id.indexOf(com.menu.id) == -1) {
	//
	// com.hideMenu();
	//
	// }
	//
	// }

	},

	// 设置组件的状态
	setDataItemsRelField : function() {

		var me = this;

		var menu = me.menu;

		var textfield = menu.down("textfield");
		var menuitem = menu.down("menuitem[text=同步]");

		var menuitemC = menu.down("menuitem[text=撤销黏贴]");

		var length = me.dataItemsMC.length;

		if (length != 0) {

			textfield.setValue(Ext.String.format('共有{0}条数据待同步！', length));
			textfield.setFieldStyle({

				color : "red"
			});

		} else {

			textfield.setValue('没有待同步数据');
			textfield.setFieldStyle({

				color : "green"
			});
		}

		menuitem.setDisabled(length == 0);
		menuitemC.setDisabled(length == 0);

		var visible = !me.disableSync;

		textfield.setVisible(visible);
		menuitem.setVisible(visible);
		menu.getComponent('menuseparator2').setVisible(visible);

	},

	initComponent : function() {

		var me = this;

		// var grid = me.up("grid"); //在初始化的时候或许不能获取grid

		MyCommonToolsZ.setComAfterrender(me, function() {

			me._resetSaveButtonHandler();
		});

		var gainGridT = function() {

			var grid = me.gainGrid() || me.up("grid");

			return grid;

		}

		me.menu = {
			xtype : 'menu',
			items : [{
				xtype : 'menuitem',
				text : '批量黏贴',
				handler : function() {

					var grid = gainGridT(); // 在初始化的时候或许不能获取grid

					showWin();

				}
			}, {
				xtype : 'menuitem',
				text : '撤销黏贴',
				handler : function() {

					var grid = gainGridT();

					var store = grid.getStore();

					store.remove(me.dataItemsMC);

					me.updateDataItemsMC(-1);

					me.setDataItemsRelField();

				}
			}, {
				xtype : 'menuseparator'
			}, {
				xtype : "textfield",
				readOnly : true

			}, {
				xtype : 'menuitem',
				text : '同步',
				handler : function() {

					// me.updateDataItems(-1);
					var grid = gainGridT();

					var mixc = grid.getStore().data;

					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");

					var itemsT = [];

					// 处理模型s，使其变成标准的对象
					Ext.each(me.dataItemsMC, function(ele, index, all) {

						
						
						itemsT.push(mixc.get(ele.internalId).data);

					});

					Ext.Ajax.request({
						timeout : 60000,

						params : {
							dataItems : Ext.JSON.encode(itemsT),
							javaBeanClassName : me.javaBeanClassName
						},

						url : me.saveUrl,
						success : function(response, option) {

							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {

								if (me.removeItemsAfterSync) {

									grid.getStore().remove(me.dataItemsMC);

								}

								me.updateDataItemsMC(-1);

							} else {
								Ext.MessageBox.alert('错误', obj.msg);

							}
							el.unmask();
						}
					});

				}
			}, {
				itemId : "menuseparator2",
				xtype : 'menuseparator'
			}, Ext.create("DJ.myComponent.button.ExportExcelButton", {
				text : "导出模板例子",

				condition : me.condition
			})]
		};

		var showWin = function() {

			var win = Ext.create("ExcelPasterButtonWindow", {
				title : me.text,
				buttonP : me
			});

			win.show();

		};

		me.callParent(arguments);
	}
});

/**
 * 黏贴的win
 * 
 */
Ext.define('ExcelPasterButtonWindow', {
	extend : 'Ext.window.Window',

	id : "ExcelPasterButtonWindow",

	height : 350,
	width : 530,
	layout : {
		align : 'stretch',
		type : 'vbox'
	},
	// title : 'My Window',

	resizable : false,
	
	modal : true,

	initComponent : function() {
		var me = this;

		var lfS = String.fromCharCode(10);
		
		Ext.applyIf(me, {
			items : [{
				xtype : 'textareafield',
				flex : 4,
				height : 130,
				emptyText : '' + lfS + '1.内容从excel里整行复制过来（空值也要，整行复制）;' + lfS + lfS
						+ '2.不要在这边直接修改，请先在excel里编辑好，否则会格式错误！（因为模块根据excel里的格式解析内容）'
			
			}, {
				xtype : 'button',
				flex : 1,
				text : '黏贴到表格',
				handler : function() {

					var me = this;

					var textArea = me.previousNode("textareafield");

					var win = me.up("window");

					var grid = win.buttonP.up("grid");

					// 更新MC

					if (!Ext.isEmpty(win.buttonP.otherFieldRemoteCfgs)) {

						ExcelPaster.loadDataToStoreRemote(textArea.getValue(),
								grid, win.buttonP.condition,
								win.buttonP.otherFieldRemoteCfgs, function(
										dataItemsMC) {
									
									win.buttonP.updateDataItemsMC(dataItemsMC);
									
									win.buttonP.onAfterPasteToGrid(win.buttonP, dataItemsMC);

								});
					} else {

						var dataItemsMCT = ExcelPaster
								.loadDataToStore(textArea.getValue(), grid,
										win.buttonP.condition);
						
						win.buttonP.updateDataItemsMC(dataItemsMCT);
										
						win.buttonP.onAfterPasteToGrid(win.buttonP, dataItemsMCT);

					}

					// 滚动
					grid.scrollByDeltaY(grid.getStore().data.length * 20, true);

					win.close();

				}
			}]
		});

		me.callParent(arguments);
	}

});
