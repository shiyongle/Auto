/**
 * 
 * html到word导出工具类
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2015-6-26 下午5:07:22
 * 
 */
Ext.define("DJ.tools.common.MySimpleHTMLToWord", {
	singleton : true,

	requires : ['DJ.tools.common.MyCommonToolsZ','DJ.tools.grid.ExcelHelper'],

	alternateClassName : "MySimpleHTMLToWord",

	constructor : function() {

		return this;

	},

	/**
	 * 传入html代码，包括<html></html>
	 * 
	 * @param {}
	 *            str
	 */
	gainWordFromHTML : function(str) {

		var me = this;

		var url = 'data:application/msword;base64,'
				+ MyCommonToolsZ.base64.encode(str);
		if(!Ext.isIE){
		window.location = url;
		}else{
			ExcelHelper.downFileWithIe("生产任务单",str,IndexMessageRel.projectBasePath+'js/tools/grid/exportWordIe.jsp',false);
		}
    }
//	,

//	/**
//	 * 传入html代码，包括<html></html>
//	 * 
//	 * @param {}
//	 *            str
//	 */
//	gainExcelFromHTML : function(str) {
//
//		var me = this;
//
//		var url = 'application/vnd.ms-excel;base64,'
//				+ MyCommonToolsZ.base64.encode(str);
//
//		window.location = url;
//
//	}
	
	
//	,
//	gainPDFFromHTML : function(str) {
//
//		var me = this;
//
//		var url = 'data:application/pdf;base64,'
//				+ MyCommonToolsZ.base64.encode(str);
//
//		window.location = url;
//
//	}
	

})