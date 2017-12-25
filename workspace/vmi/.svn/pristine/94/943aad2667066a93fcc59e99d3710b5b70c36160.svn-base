/**
 * 
 * 简单grid打印组件
 * 
 * @author ZJZ（447338871@qq.com）
 * 
 * @version 0.1 2014-12-9 下午1:35:39
 * @version 0.2 2015-1-27 下午4:17:59,增加选择打印功能
 * 
 */
Ext.define('Ext.ux.grid.print.MySimpleGridPrinterComponent', {
	extend : 'Ext.container.Container',

	alias : 'widget.mysimplegridprintercomponent',

	requires : ['DJ.tools.myPrint.MySimpleGridPrinter'],

	alternateClassName : 'MySimpleGridPrinterComponent',

	// height : 23,
	// width : 300,
	
	statics : {
		
		COMMON_MODE : 0,
		PRINT_SELECTED_MODE : 1
		
	},
	
	mode : 0,
	
	layout : {
		type : 'hbox'
	},
	frame : false,

	initComponent : function() {
		var me = this;

		var getGrid = function(com) {

			return com.up("grid");

		}

		var grid = me.up("grid");
		
		var printMS = 'doPrint';
		
		var showPreviewMS = 'showPrintPreview';
		
		if (me.mode == MySimpleGridPrinterComponent.PRINT_SELECTED_MODE) {
			
			printMS = 'doPrintForSelected';
			
			showPreviewMS = 'showPrintPreviewForSelected';
			
		}
		
		var showPrintPreview = {
			xtype : "button",
			text : '打印预览',
			handler : function(com, e) {

				MySimpleGridPrinter[showPreviewMS](getGrid(com));

			}
		};

		var doPrint = {
			xtype : "button",
			text : '打印',
			handler : function(com, e) {

				MySimpleGridPrinter[printMS](getGrid(com));

			}
		};

		var spacerCfg = {

			xtype : "tbspacer",
			width : 5

		};

		Ext.applyIf(me, {
			items : [showPrintPreview, spacerCfg, doPrint]
		});

		me.callParent(arguments);
	}
});