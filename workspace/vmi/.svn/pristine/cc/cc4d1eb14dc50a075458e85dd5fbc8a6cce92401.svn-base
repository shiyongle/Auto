/**
 * 
 * grid通用类，其中excel相关方法主要是用于远程导出，有后端配合方法
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2014-12-15 下午3:03:48
 * @version 0.2 2015-3-27 下午1:41:52,二级表头支持，但只是扁平化而已
 */

Ext.define("DJ.tools.grid.MyGridTools", {
	singleton : true,

	alternateClassName : "MyGridTools",

	requires : ['DJ.tools.common.MyCommonToolsZ'],

	buildExcelExporterHandler : function(grid, urlL) {

		var me = this;

		var handlerNew = function(bt) {

			// var grid = bt.up("grid");

			var storeT = grid.getStore();

			var getDefaultfilter = storeT.getDefaultfilter;
			var getDefaultmaskstring = storeT.getDefaultmaskstring;
			var urlTT = urlL || grid.exporturl;

			if (MyCommonToolsZ.isEmptyOrNull(urlTT)) {

				Ext.MessageBox.alert('错误', '没有指定导出方法');

				return;
			}
			var objectparams=new Object({
				columns : Ext.JSON.encode(me.buildExcelColumns(grid)),
				Defaultfilter : Ext.JSON.encode(getDefaultfilter
						? (getDefaultfilter.call(storeT) ? getDefaultfilter
								.call(storeT) : [])
						: []),
				Defaultmaskstring : getDefaultmaskstring
						? (getDefaultmaskstring.call(storeT)
								? getDefaultmaskstring.call(storeT)
								: '')
						: ""
			});
			if(!Ext.isIE){
				var urlT = Ext.Object.toQueryString(objectparams);
				var urlG = Ext.String.urlAppend(urlTT, urlT);
				window.open(urlG, '导出EXCEL');
			}else{
			me.openPostWindow(urlTT, objectparams,'导出EXCEL');
			}
			}
		return handlerNew;
	},
	//IE替代window.open post传参方式 paramname参数名称;data 参数值,name窗口名称
	//openPostWindow:function (url, data, name,paramname)  
	openPostWindow : function (url, data, name) { 
     var tempForm = document.createElement("form");  
     tempForm.id="tempForm1";  
     tempForm.method="post";  
     tempForm.action=url;  
     tempForm.target=name;  
     Ext.Object.each(data, function(key, value, myself) {
	     var hideInput = document.createElement("input");  
	     hideInput.type="hidden";  
	     hideInput.name= key
	     hideInput.value= value;
	     tempForm.appendChild(hideInput); 
	}); 
     tempForm.attachEvent("onsubmit",function(){ 
//     window.open('about:blank',"导出EXCEL");  
     });
     document.body.appendChild(tempForm);  
     tempForm.fireEvent("onsubmit");
     tempForm.submit();
     document.body.removeChild(tempForm);
},


	constructor : function() {

		return this;

	},

	/**
	 * 门面
	 * 
	 * @param {}
	 *            grid，grid
	 * @param {}
	 *            condition,[],没有就设置空数组
	 * @return {}，对应列dataindex和text的对应
	 */
	buildColumns : function(grid, condition) {

		var columns;

		if (condition == undefined || condition == null
				|| condition.length == 0) {

			columns = MyGridTools.buildExcelColumns(grid);

		} else {

			columns = MyGridTools.buildExcelColumnsByCondtion(grid, condition);

		}
		return columns;
	},

	/**
	 * 构建有效的column dataindex 与 text的映射，并附带条件
	 * 
	 * @param {}
	 *            grid
	 * @param {}
	 *            condition
	 * @return {}
	 */
	buildExcelColumnsByCondtion : function(grid, condition) {

		var me = this;

		var colums = me.buildExcelColumns(grid);

		var columsR = [];

		Ext.each(colums, function(ele, index, all) {

			if (Ext.Array.contains(condition, ele.dataIndex)) {

				columsR.push(ele);

			}

		});

		return columsR;

	},

	/**
	 * 构建有效的column dataindex 与 text的映射
	 * 
	 * @param {}
	 *            grid
	 * @return {}
	 */
	buildExcelColumns : function(grid) {

		var columnsFinal = [];
		
		var columns = grid.columns;
		
		Ext.each(columns, function(ele, index) {

			if (!Ext.isEmpty(ele.items.items)) {

				columnsFinal.push(Ext.Array.flatten(ele.items.items
						.map(function(ele2, index) {

							var eleT = {

								text : ele.text + "-" + ele2.text,
								dataIndex : ele2.dataIndex,
								render : ele2.render
							}
							
							return eleT;

						})));

			} else {

				columnsFinal.push(ele);

			}

		});
		
		columns = Ext.Array.flatten(columnsFinal);

		var columnsG = Ext.Array.filter(columns, function(ele, index, all) {

			if (MyCommonToolsZ.isEmptyOrNull(ele.dataIndex)) {

				return false;

			}

			if (ele.hidden) {

				return false;

			}

			return true;

		});

		var columnsGSimple = [];

		Ext.each(columnsG, function(ele, index, all) {

			var renderer = ele.renderer;

			// 没有渲染函数、渲染函数是本地方法或者是树列。
			if ((!renderer)
					|| (renderer.toString().indexOf('[native code]') != -1)
					|| (ele.getXType() == 'treecolumn')) {

				renderer = false;

			}

			columnsGSimple.push({

				dataIndex : ele.dataIndex,
				text : ele.text,
				renderer : renderer

			});

		});

		return columnsGSimple;

	},

	/**
	 * 重构cgrid导出按钮的处理器，在onload里调用
	 * 
	 * @param {}
	 *            grid
	 */
	rebuildExcelAction : function(grid) {

		var me = this;

		var btExcel = grid.down("button[text=导出Excel]");

		btExcel
				.setHandler(MyGridTools.buildExcelExporterHandler(grid),
						btExcel);

	}

});