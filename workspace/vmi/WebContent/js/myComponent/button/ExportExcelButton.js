/**
 * excel导出按钮
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2014-12-15 下午2:59:06
 * 
 */
Ext.define('DJ.myComponent.button.ExportExcelButton', {
	extend : 'Ext.button.Button',

	alias : ['widget.exportexcelbutton'],

	requires : ['DJ.tools.grid.MyGridTools', 'DJ.tools.common.MyCommonToolsZ',
			'DJ.tools.grid.ExcelHelper'],

	text : '导出Excel', // 导出
	iconCls : 'excel',

	// the following two items are for remote
	remoteAction : false,
	exportExcelUrl : "",

	// as the name
	excelTitle : null,

	//only the following indexs are show。只有下面的索引被展示，如果设置的话
	condition : [],
	
	gridSelector : null,
	
	//如果表格不能正常获取，就要配置表格获取器
	gainGrid : function() {
		return false;
	},
	
	initComponent : function() {

		var me = this;

		MyCommonToolsZ.setComAfterrender(me, function(com) {

			var grid;
			
			if (me.gridSelector != null) {
			
				grid = Ext.getCmp(me.gridSelector);
			
			}else if (me.gainGrid() != false){
			
				grid = me.gainGrid();
				
			}else {
			
				grid = me.up("grid") || me.up("treepanel");
			}
			
			if (me.remoteAction) {

				me.handler = MyGridTools.buildExcelExporterHandler(grid,
						me.exportExcelUrl);
			} else {
				var typesS = grid.getXTypes();

				//some code like meta-programming
				var ms = "downLoadExcel";

				if (typesS.search("treepanel") != -1) {
					ms = "downLoadExcelForTree";
				}

				var handlerT;
//
//				if (me.excelTitle == null) {
//
//					handlerT = function() {
//
//						ExcelHelper[ms](grid, me.condition);
//
//					};
//
//				} else {
//					handlerT = function() {
//
//						ExcelHelper[ms](grid, me.excelTitle, me.condition);
//
//					};
//
//				}

				handlerT = function() {

						ExcelHelper[ms](grid, me.excelTitle, me.condition);

					};
				
				me.handler = handlerT;

			}

		});

		me.callParent(arguments);
	}

})