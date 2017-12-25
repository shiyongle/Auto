/**
 * 多文件上传panel，也可以用这个，可以嵌入其他面板
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2014-8-28 上午10:54:54
 * 
 */
Ext.define("DJ.tools.file.MultiUploadPanel", {

	extend : 'Ext.panel.Panel',

	alias : "widget.djmultiuploadPanel",

	height : 350,
	html : '<iframe src="uploadStatic.do" frameborder="0"  width="780" height="380"></iframe>',

	width : 780,
	resizable : false,

	initComponent : function() {
		var me = this;

		var bodyT = Ext.query("body")[0];

		var t = new Ext.Template('<input id = "myMultiFileUploadValue" type = "hidden" value="{idValue}" >');

		t.compile();

		t.insertFirst(bodyT, {
			idValue : me.id
		});

		me.callParent(arguments);
	}

});