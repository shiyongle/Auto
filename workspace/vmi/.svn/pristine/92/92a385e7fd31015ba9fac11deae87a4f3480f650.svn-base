
/**
 * 简易grid打印工具类
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2014-12-5 下午3:20:43
 * @version 0.1 2015-1-27 下午3:20:14，增加打印选中条目功能
 * 
 */
Ext.define("DJ.tools.myPrint.MySimpleGridPrinter", {
	singleton : true,

	alternateClassName : "MySimpleGridPrinter",

	requires : ['DJ.tools.common.MyCommonToolsZ'],
	
	_PRINTERIF_ID : "mySimpleGridPrinterPrinterIFId",

	// 内联框架
	printerIF : null,

	constructor : function() {

		var me = this;

		var idT = me._PRINTERIF_ID;

		Ext.DomHelper.append(Ext.getBody(), Ext.String.format(
				'<iframe id = "{0}" width="100%" height="100%" />', idT));

		Ext.DomHelper
				.append(
						Ext.get(idT).dom.contentWindow.document.head,
						Ext.String
								.format(
										'<link rel="stylesheet" type="text/css" href="{0}extlib/resources/css/ext-all.css">',
										IndexMessageRel.projectBasePath));

										
			Ext.DomHelper
				.append(
						Ext.get(idT).dom.contentWindow.document.head,
						'<style>@media print {@page {margin: 5mm 10mm 0mm 10mm;size: A4 portrait;}} table{table-layout:fixed;}}body { color: #000; background: #fff; }</style>');

										
		me.printerIF = Ext.get(idT);
	},

	/**
	 * 打印
	 * 
	 * @param {}
	 *            grid
	 */
	doPrint : function(grid) {

		var me = this;

		// Ext.DomHelper.overwrite(me.printerIF.dom.contentWindow.document.body,
		// grid.body.dom.innerHTML);

		me._setPrinterContent(me.printerIF.dom.contentWindow, grid);

		me.printerIF.dom.contentWindow.focus();
		me.printerIF.dom.contentWindow.print();

	},
	/**
	 * 打印预览
	 * 
	 * @param {}
	 *            grid
	 */
	showPrintPreview : function(grid) {
		var me = this;

		var newPage = window.open('', 'printer', '');

		var baseUrl = location.href;

//		var pathT = baseUrl.substring(0, baseUrl.indexOf("vmi") - 1);

		Ext.DomHelper
				.append(
						newPage.document.head,
						Ext.String
								.format(
										'<link rel="stylesheet" type="text/css" href="{0}{1}extlib/resources/css/ext-all.css">',
										'http://'+location.host,IndexMessageRel.projectBasePath));

		me._setPrinterContent(newPage, grid);

	},

	/**
	 * 打印选择的条目
	 * 
	 * @param {}
	 *            grid
	 */
	doPrintForSelected : function(grid) {

		var me = this;

		if (MyCommonToolsZ.pickSelectItems(grid) == -1) {
		
			return ;
			
		}
		
		// Ext.DomHelper.overwrite(me.printerIF.dom.contentWindow.document.body,
		// grid.body.dom.innerHTML);

		me._setPrinterContentSelected(me.printerIF.dom.contentWindow, grid);

		me.printerIF.dom.contentWindow.focus();
		me.printerIF.dom.contentWindow.print();

	},
	/**
	 * 打印预览for选择的条目
	 * 
	 * @param {}
	 *            grid
	 */
	showPrintPreviewForSelected : function(grid) {
		var me = this;

		if (MyCommonToolsZ.pickSelectItems(grid) == -1) {
		
			return ;
			
		}
		
		var newPage = window.open('', 'printer', '');

		var baseUrl = location.href;

//		var pathT = baseUrl.substring(0, baseUrl.indexOf("vmi") - 1);

		Ext.DomHelper
				.append(
						newPage.document.head,
						Ext.String
								.format(
										'<link rel="stylesheet" type="text/css" href="{0}{1}extlib/resources/css/ext-all.css">',
										'http://'+location.host,IndexMessageRel.projectBasePath));

		me._setPrinterContentSelected(newPage, grid);

	},

	_setPrinterContent : function(win, grid) {

		var gridComT = grid.el.dom.cloneNode(true);

		gridComT.removeChild(gridComT.firstChild);

		Ext.DomHelper.overwrite(win.document.body, gridComT.innerHTML);

		// Ext.DomHelper.overwrite(win.document.body, grid.el.dom.innerHTML);

	},

	_setPrinterContentSelected : function(win, grid) {

		var gridComT = grid.el.dom.cloneNode(true);

		var domsToRemove = Ext.query('tr:not(tr[class*=x-grid-row-selected])', Ext
				.query('table[id^=grid]', gridComT, "select", true));

		gridComT.removeChild(gridComT.firstChild);

		Ext.each(domsToRemove, function(ele, index, all) {

			Ext.removeNode(ele);

		});

		Ext.DomHelper.overwrite(win.document.body, gridComT.innerHTML);

		// Ext.DomHelper.overwrite(win.document.body, grid.el.dom.innerHTML);

	}

// ,
// _setPrinterContentDirectly : function(win, innerHTML) {
//
// // var gridComT = grid.el.dom.cloneNode(true);
// //
// // gridComT.removeChild(gridComT.firstChild);
//
// Ext.DomHelper.overwrite(win.document.body, innerHTML);
//
// }

});