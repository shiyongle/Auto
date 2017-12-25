/**
 * excel导出自定义导出按钮
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2015-1-26 下午3:07:26,可以导出前面，导出选择的，导出自定义的
 * 
 */
Ext.define('DJ.myComponent.button.MyExcelExporterCusCfgButton', {
	extend : 'Ext.button.Button',

	alias : ['widget.myexcelexportercuscfgbutton'],

	alternateClassName : ['MyExcelExporterCusCfgButton'],

	requires : ['DJ.tools.grid.MyGridTools', 'DJ.tools.common.MyCommonToolsZ',
			'DJ.tools.grid.ExcelHelper'],

	statics : {

		ALL_MODE : 0,// 有条数限制，过多会奔溃
		PICK_MODE : 1,//选择模式
		CUS_CFG_MODE : 2,//自定义模式，要传入cusCfg
		COMMON_MODE : 3//本页导出模式

	},

	text : '导出Excel', // 导出
	iconCls : 'excel',

	//不能随便改大，否则可能导致崩溃现象
	limit : 1000,

	mode : 3,

	tipCus : null,

	/**
	 * 非常规列表时使用
	 * @type {}
	 */
	columns : [],
	
	_columnsObj : {

		text : 'a',
		dataIndex : 'a',
		render : 'f'

	},
	
	// 示例
	cusCfg : {

		Defaultfilter : [],
		Cusfilter : [],
		Defaultmaskstring : '',
		Maskstring : '',
		Merge : ''
	// ,
	// page : 2,
	// start : 0,
	// limit : 100

	},

	// as the name
	excelTitle : null,

	// only the following indexs are show。只有下面的索引被展示，如果设置的话
	condition : [],

	// 如果表格不能正常获取，就要配置表格获取器
	gainGrid : function() {
		return false;
	},

	changeMode : function(mode, grid){
	
		return mode;
		
	},
	
	initComponent : function() {

		var me = this;

		MyCommonToolsZ.setComAfterrender(me, function(com) {

			me._buildBtnTip();

			var grid;

			if (me.gainGrid() != false) {

				grid = me.gainGrid();

			} else {

				grid = me.up("grid") || me.up("treepanel");
			}

			var typesS = grid.getXTypes();

			// some code like meta-programming
			var ms = "downLoadExcel";

			if (typesS.search("treepanel") != -1) {
				ms = "downLoadExcelForTree";
			}

			var handlerT;

			handlerT = function() {

				if (ms == 'downLoadExcel') {

					var storeS = grid.getStore();

					var storeT = Ext.create('Ext.c.data.Store', {
						fields : grid.fields,
						// pageSize : 1000,// 条数
						timeout : 60000,
						url : grid.url,
						autoLoad : true,
						remoteSort : grid.remoteSort ? grid.remoteSort : false,
						data : Ext.Array.clone(storeS.data.items),

						Defaultfilter : storeS.Defaultfilter,

						Cusfilter : storeS.Cusfilter,
						Defaultmaskstring : storeS.Defaultmaskstring,
						Maskstring : storeS.Maskstring,
						Merge : storeS.Merge

					});

					
					me.mode = me.changeMode(me.mode, grid);
					
					
					switch (me.mode) {

						case MyExcelExporterCusCfgButton.ALL_MODE :

							// storeT.getProxy().setExtraParam("nolimit", true);

							MyCommonToolsZ.showAllTip(Ext.String.format(
									'<p style="font-size: 16pt;">导出前 {0} 条数据</p>', me.limit));

							break;

						case MyExcelExporterCusCfgButton.PICK_MODE :

							var items = MyCommonToolsZ.pickSelectItems(grid);

							if (items == -1) {

								return;

							}

							var ids = Ext.Array.pluck(items, 'internalId');

							storeT.filterBy(function(item, id) {

								return Ext.Array.contains(ids, id);

							});

							break;

						case MyExcelExporterCusCfgButton.CUS_CFG_MODE :

							Ext.Object.each(me.cusCfg, function(key, value,
									myself) {

								storeT[key] = value;

							});

							break;

						case MyExcelExporterCusCfgButton.COMMON_MODE :

							break;
					}

					if (me.mode == MyExcelExporterCusCfgButton.CUS_CFG_MODE
							|| me.mode == MyExcelExporterCusCfgButton.ALL_MODE) {

						var el = grid.getEl();

						el.mask("系统处理中,请稍候……");

						storeT.load({
							limit : me.limit,
							callback : function(records, operation, success) {
								if (success) {

									ExcelHelper[ms](grid, me.excelTitle,
											me.condition, storeT, me.columns);

								}
								el.unmask();
							}
						});

					} else {

						ExcelHelper[ms](grid, me.excelTitle, me.condition,
								storeT, me.columns);

					}

				} else {

					ExcelHelper[ms](grid, me.excelTitle, me.condition, me.columns);

				}

			};

			me.handler = handlerT;

		});

		me.callParent(arguments);
	},
	_buildBtnTip : function() {

		var me = this;

		if (me.tipCus) {

			me.setTooltip(me.tipCus);
			return;

		}

		var tip = '';

		switch (me.mode) {

			case MyExcelExporterCusCfgButton.ALL_MODE :

				tip = Ext.String.format('导出前{0}条数据', me.limit);

				break;

			case MyExcelExporterCusCfgButton.PICK_MODE :

				tip = '导出选择的数据';

				break;

			case MyExcelExporterCusCfgButton.CUS_CFG_MODE :

				tip = '导出数据';

				break;

			case MyExcelExporterCusCfgButton.COMMON_MODE :

				tip = '导出本页数据';

				break;
		}

		me.setTooltip(tip);

	}

})