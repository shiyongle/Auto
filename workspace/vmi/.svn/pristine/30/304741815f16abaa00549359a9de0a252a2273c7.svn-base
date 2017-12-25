Ext.require("DJ.System.note.NoteContainer");

Ext.define('DJ.System.note.NoteEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.System.note.NoteEdit",
	modal : true,

	ctype : 'Note',

	addState : true,
	
	resizable : false,

	onload : function() {
		// // 加载后事件，可以设置按钮，控件值等

		var me = this;

		if (me.addState) {

			// var el = me.getEl();
			// el.mask("系统处理中,请稍候……");

			Ext.Ajax.request({
				timeout : 6000,

				url : "gainCurrentUser.do",
				success : function(response, option) {

					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {

						var nameF = me.down("textfield[name=fuserName]");
						var phoneF = me.down("textfield[name=fphone]");

						nameF.setValue(obj.data[0][0]);
						phoneF.setValue(obj.data[0][1]);

					} else {
						Ext.MessageBox.alert('错误', obj.msg);

					}
					// el.unmask();
				}
			});

		}

	},
	// custbar : [{
	// // id : 'DelButton',
	// text : '自定义按钮1',
	// height : 30,
	// handler : function() {
	// var a = Ext.getCmp('DJ.System.AddressEdit');
	// a.seteditstate("edit"); // 设置界面可编辑"view"和""为不可编辑，其他都是可以编辑
	//
	// }
	// }, {
	// // id : 'DelButton',
	// text : '自定义按钮2',
	// height : 30
	// }],
	title : "在线留言",

	width : 504,

	height : 310,

	url : 'saveNote.do',
	infourl : 'gainNotesByID.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'gainNotesByID.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true

	layout : 'fit',

	items : [{xtype:'notecontainer'
//	,	width : 580
 ,
		height : 580 / 16 * 9
		
		}]

});