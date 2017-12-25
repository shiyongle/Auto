/**
 * 
 * 
 * @since 2013-10-21
 * @version 0.2 2014-1-4 上午11:00:35
 * 添加多重数据添加功能，供外部调用
 * @author zjz
 * 
 * 简单打印类，主要调用
 * 
 * doPrint : function(data, ids) doPrintByGridEasyer : function(data, ids)
 * 
 * 另外，在用之前先在onready里面添加查找路径
 * 
 * 
 * Ext.Loader.setConfig({ enabled : true, paths : { 'Ext.app' : '.'//替换成目标路径 }
 * });
 * 
 * 
 */

Ext.define("Ext.app.MySimplePrinter", {
	singleton : true,

	requires : ["Ext.app.MySimplePrinterXTemplateString"],

	alias : "MySimplePrinter",

	/**
	 * 下面三个属性可以根据需要修改
	 * 
	 * @type String
	 */
	tip : "提示",
	printMessage : '非Chrome浏览器，您可以直接打印（不预览按yes）或是手动预览打印（按no），请选择 !<br/> ',
	tplNullTip : "the XTemplate is null, failed to print!",

	remberCheck : ' <div align="right"><label>记住选择?<input type="checkbox" name="checkbox" value="checkbox" id = "remberCheck"/></label></div>',

	
	/*
	 * 'yes'直接打印 'no' 预览
	 */
	remberAction : "yes",


	tpl : null,

	setTpl : function(ids) {
		if (ids != null && ids.length != 0) {
			this.tpl = Ext.app.MySimplePrinterXTemplateString
					.getxTemplateByID(ids);
		}
	},

	/**
	 * 
	 * @param {}
	 *            ids，XS对象长度为3，分别为表格头，表格（约定的customsName部分），表格尾。顺序不能错！
	 * @return {} 原始的ids
	 */
	buildIds : function(ids) {
		var tableName = ids[1], header = ids[0], footer = ids[2], tablePart = [];

		for (var i = 1; i <= 5; i++) {
			tablePart.push(tableName + "Part" + i);
		}

		ids = [tablePart[0], header, tablePart[1], footer, tablePart[2],
				header, tablePart[3], footer, tablePart[4]];

		return ids;
	},

	/**
	 * 打印，有头部和尾部
	 * 
	 * @param {}
	 *            data
	 * @param {}
	 *            ids，XS对象长度为3，分别为表格头，表格（约定的customsName部分），表格尾。顺序不能错！
	 */

	doPrintByGridEasyer : function(data, ids) {

		ids = this.buildIds(ids);

		this.setTpl(ids);

		this.doPrintBetter(data);
	},

	/**
	 * 打印
	 * 
	 * @param {}
	 *            data
	 * @param {}
	 *            ids ，一定要是数组！（如果只有一个id也必须是）
	 */
	doPrint : function(data, ids) {

		this.setTpl(ids);

		this.doPrintBetter(data);
	},

	/**
	 * 更高效地打印，但用之前的开发者必须确保tpl是有值的。一般无须使用
	 * 
	 * @param {}
	 *            data
	 */
	doPrintBetter : function(data) {

		if (this.tpl != null) {
			if (navigator.userAgent.match("Chrome") != null) {
				this.printPageInChrome(data);
			} else {
				this.printPageInOthers(data);
			}
		} else {
			Ext.Msg.alert(this.tip, this.tplNullTip);
		}
	},

	printPageInOthers : function(data) {
		var newwin;
		var me = this;
		if (this.remberAction == "") {
			Ext.Msg.confirm(me.tip, me.printMessage + me.remberCheck, function(
							id) {
						if (Ext.get("remberCheck").dom.checked) {
							me.remberAction = id;
						}
						if (id == 'yes') {
							doneYes();
						} else {
							doneNo();
						}
					}, me);

		} else {
			if (this.remberAction == 'yes') {
				doneYes();
			} else {
				doneNo();
			}
		};
		function doneYes() {
			newwin = window.open("", "printer");
			me.tpl.append(newwin.document.body, data);
			newwin.print();
			newwin.close();
		};
		function doneNo() {
			newwin = window.open("", "printer");
			me.tpl.append(newwin.document.body, data);
			newwin.focus();
		};
	},
	printPageInChrome : function(data) {

		var newwin = window.open("", "printer");
		this.tpl.append(newwin.document.body, data);
		newwin.print();
		newwin.close();
	},

	/**
	 * 添加数据到页面
	 * 
	 * @param {}
	 *            data，数据
	 * @param {}
	 *            page，页面
	 */
	appendDataToPage : function(data, page) {

		this.tpl.append(page.document.body, data);

	}
});