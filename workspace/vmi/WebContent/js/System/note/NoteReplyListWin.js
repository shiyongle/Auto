Ext.require(["DJ.tools.common.MyCommonToolsZ", "DJ.System.note.NoteContainer"]);

Ext.define('DJ.System.note.NoteReplyListWinGrid', {
	extend : 'Ext.c.GridPanel',
	// title : "在线留言",

	// closeAction : "destroy",

	alias : 'widget.notereplylistwingrid',

	id : 'DJ.System.note.NoteReplyListWinGrid',
	pageSize : 50,
	// closable : true,// 是否现实关闭按钮,默认为false
	url : 'gainNotesByPreNote.do',
	Delurl : "deleteNotes.do",
	EditUI : "DJ.System.note.NoteEdit",

	preNoteId : '',

	// exporturl : "outWarehousetoexcel.do",

	plugins : [

	Ext.create("Ext.ux.grid.MySimpleGridContextMenu", {
		useExistingButtons : ['button[text=查   看]', 'button[text=删    除]',
				'button[text=回复]']

	}), Ext.create("Ext.ux.grid.MyGridItemDblClick", {

		dbClickHandler : "button[text=回复]"

	}), Ext.create("Ext.ux.grid.MyGridItemTipPlugin", {

		showingFields : ['fcontent']
			// ,
			// showCallBack : function(com, eOpts, gridCom) {
			//
			// var btn = gridCom.down("button[text=查 看]");
			//
			// Ext.Function.defer(btn.handler, 5000, btn);
			//
			// }

			}), Ext.create("Ext.ux.grid.MyGridShortcutKeyPlugin", {

		// 启用复制粘帖功能
		enableCopyPaste : false,

		// 其他快捷键设置
		bindings : [{

			key : Ext.EventObject.DELETE,
			// alt : true,
			// 演示,提示配置函数，没有则没有提示
			gainComAndTip : function(comGrid) {
				return {

					com : comGrid.down("button[text=删    除]"),
					tip : "DELETE"

				};
			},
			handler : function(keyCode, e) {

				var grid = e.grid;

				var btn = grid.down("button[text=删    除]");

				btn.handler.call(btn);
			}

		}]

	})

	],
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等

		var me = this;

		var ids = ['querybutton', 'exportbutton', 'addbutton', 'editbutton'];

		Ext.each(ids, function(ele, index, all) {

			var com = Ext.getCmp(me.id + "." + ele);

			// if (ele == 'addbutton') {
			//			
			// com.setText( "回复" );
			//				
			// }else {

			com.hide();

				// }

			});

		var viewButton = Ext.getCmp(me.id + "." + 'viewbutton');

		viewButton.setHandler(function() {

			var c1 = me;

			DJ.System.note.NoteList.viewButtonHandler(c1);

		}, viewButton);

	},
	// Action_BeforeAddButtonClick : function(EditUI) {
	// // 新增界面弹出前事件
	//
	// var gridT = this;
	//
	// var items = MyCommonToolsZ.pickSelectItems(gridT);
	//
	// if (items == -1) {
	//
	// return false;
	//
	// }
	//
	// var replyFids = Ext.Array.pluck(Ext.Array.pluck(items, 'data'), "fid");
	//
	// EditUI.url = "saveReplyNotes.do";
	//
	// MyCommonToolsZ.setComAfterrender(EditUI, function() {
	//
	// var fidsF = EditUI.down("hidden[name=fids]");
	// var replyFidsF = EditUI.down("hidden[name=replyFids]");
	//
	// fidsF.setValue(gridT.preNoteId);
	// replyFidsF.setValue(Ext.JSON.encode(replyFids));
	//
	// });
	//
	// },
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件

	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件

	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
		// 可对编辑界面进行复制
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件

	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	custbar : [

	{
		text : "回复",
		handler : function() {

			var me = this;

			var grid = me.up("grid");

			var items = MyCommonToolsZ.pickSelectItems(grid);

			if (items == -1) {

				return;

			}

			Ext.Ajax.request({
				timeout : 6000,

				url : "isNoteAdminCurrentUser.do",
				success : function(response, option) {

					var obj = Ext.decode(response.responseText);
					// 不是管理员才自动赋值
					if (obj.success == true) {

						var addState = true;

						var noteEdit = Ext.create("DJ.System.note.NoteEdit", {
							url : 'saveReplyNotes.do',
							title : "回复",
							addState : obj.data.isAdmin
						});

						noteEdit.seteditstate('edit');
						noteEdit
								.setparent('DJ.System.note.NoteReplyListWinGrid');

						var replyFids = Ext.Array.pluck(Ext.Array.pluck(items,
								'data'), "fid");

						var fidsF = noteEdit.down("hidden[name=fids]");
						var replyFidsF = noteEdit
								.down("hidden[name=replyFids]");

						fidsF.setValue(grid.preNoteId);
						replyFidsF.setValue(Ext.JSON.encode(replyFids));

						if (!obj.data.isAdmin) {

							var notecontainerT = noteEdit.down("notecontainer");

							var values = grid.up("window")
									.down("notecontainer").getValuesF();

							values[0] = "";

							notecontainerT.setValuesF(values);

						}

						noteEdit.show();

					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
				}
			});

		}

	}

	],
	fields : [{
		name : 'fid'
	}, {
		name : 'fuser'
	}, {
		name : 'fuserName'
	}, {
		name : 'fcontent'
	}, 'fphone', 'fdate', 'fpreNote', 'fprenoteMsg', {
		name : 'isUnRead',
		type : 'int'
	}],
	columns : {
		items : [
				// Ext.create('DJ.Base.Grid.GridRowNum', {
				// flex : 0
				// }),
				{
					'header' : '@',
					'dataIndex' : 'fprenoteMsg',
					sortable : true,
					width : 150

				// flex : 1
				}, {
					'header' : 'fid',
					'dataIndex' : 'fid',
					hidden : true,
					hideable : false,
					sortable : true
				}, {
					'header' : '内容',
					dataIndex : 'fcontent',
					sortable : true,
					flex : 1
				// ,
				//
				// renderer : function(value, metadata, record) {
				//
				// var isUnread = record.get("isUnRead");
				//
				// if (isUnread == 1) {
				//
				// metadata.style = 'color : red';
				//
				// } else {
				//
				// metadata.style = 'color : green';
				//
				// }
				//
				// return value;
				// }

				}, {
					'header' : '用户',
					'dataIndex' : 'fuserName',
					sortable : true,
					width : 80

				// flex : 1
				}, {
					'header' : '日期',
					dataIndex : 'fdate',
					sortable : true,
					width : 150
				// flex : 1
				}]
	// ,
	// defaults : {
	// flex : 1
	// }
	},
	selModel : Ext.create('Ext.selection.CheckboxModel', {
		flex : 0
	})
});

Ext.define('DJ.System.note.NoteReplyListWin', {
	extend : 'Ext.window.Window',

	id : 'DJ.System.note.NoteReplyListWin',

	width : 680,
	height : 600,

	modal : true,

	resizable : false,

	layout : {
		align : 'stretch',
		type : 'vbox'
	},
	title : '回复列表',

	closeAction : 'hide',

	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [{
				xtype : 'notereplylistwingrid',
				height : 400
					// ,
					// flex : 1
					}, {
						xtype : 'notecontainer',

						// width : 450
						// ,
						height : 240
					// ,
					// flex : 1
					}]
		});

		me.callParent(arguments);
	}

});