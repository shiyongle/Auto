Ext.require("DJ.tools.common.MyCommonToolsZ");

Ext.define('DJ.System.note.NoteList', {
	extend : 'Ext.c.GridPanel',
	title : "在线留言",
	id : 'DJ.System.note.NoteList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'gainNotesBase.do',
	Delurl : "deleteNotes.do",
	EditUI : "DJ.System.note.NoteEdit",
	// exporturl : "outWarehousetoexcel.do",

	plugins : [

	Ext.create("Ext.ux.grid.MySimpleGridContextMenu", {
		useExistingButtons : ['button[text=查   看]', 'button[text=删    除]',
				'button[text=回复]', 'button[text=展示回复]']

	}), Ext.create("Ext.ux.grid.MyGridItemDblClick", {

		dbClickHandler : "button[text=展示回复]"

	}), Ext.create("Ext.ux.grid.MyGridItemTipPlugin", {

//		showingFields : ['fcontent'],
		
			url : "gainNotesMsgByPreNote.do",

			ajaxParams : ['fid'],

			tipProperty : "data.msg"
			// ,
			// showCallBack : function(com, eOpts, gridCom) {
			//
			// var btn = gridCom.down("button[text=展示回复]");
			//			
			// var records = grid.getSelectionModel().getSelection();
			//
			// if (records.length != 1) {
			//
			// return;
			//
			// }
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

	statics : {
	
		viewButtonHandler : function(c1){
		
			if (c1.EditUI == null || Ext.util.Format.trim(c1.EditUI) == "") {
				Ext.MessageBox.alert("提示", "请选择");
				return;
			};
			try {
				var c3 = c1.getSelectionModel().getSelection();
				if (c3.length == 0) {
					throw "";
				}
				var c4 = Ext.create(c1.EditUI, {

					onloadfields : function() {

						var me = this;

						var notecontainerF = me.down("notecontainer");

						var values = notecontainerF.getValuesF();

						values[0] = values[0].replace(/<br\/>/g, String
								.fromCharCode(10));

						notecontainerF.setValuesF(values);
					}

				});

				var notecontainerF = c4.down("notecontainer");

				notecontainerF.setReadOnlyF(true);

				c4.loadfields(c3[0].get('fid'));
				c4.seteditstate('edit');
				c4.setparent(c1.id);

				c4.show();

				c4.down("button[text=保  存]").hide();
			} catch (e) {
				Ext.MessageBox.alert('提示', e)
			}
			
		}
		
	},
	
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等

		var me = this;

		var ids = ['querybutton', 'exportbutton', 'editbutton'];

		Ext.each(ids, function(ele, index, all) {

			Ext.getCmp(me.id + "." + ele).hide();

		});

		var viewButton = Ext.getCmp(me.id + "." + 'viewbutton');

		viewButton.setHandler(function() {

			var c1 = me;

			DJ.System.note.NoteList.viewButtonHandler(c1);
			

		}, viewButton);

	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
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
	custbar : [{
		text : "回复",
		handler : function() {

			var grid = this.up("grid");

			var items = MyCommonToolsZ.pickSelectItems(grid);

			if (items == -1) {

				return;

			}

			var fids = Ext.Array.pluck(Ext.Array.pluck(items, 'data'), "fid");

			var noteEdit = Ext.create("DJ.System.note.NoteEdit", {
				url : 'saveNotes.do',
				title : "回复"

			});

			noteEdit.seteditstate('edit');

			var fidsF = noteEdit.down("hidden[name=fids]");

			fidsF.setValue(Ext.JSON.encode(fids));

			noteEdit.show();

		}

	}, {
		text : "展示回复",
		handler : function() {

			var grid = this.up("grid");

			var items = MyCommonToolsZ.pickSelectItems(grid, 1);

			if (items == -1) {

				return;

			}

			var rWin = Ext.getCmp("DJ.System.note.NoteReplyListWin");

			if (rWin == null) {

				rWin = Ext.create("DJ.System.note.NoteReplyListWin");

			}

			var rWinGrid = rWin.down("grid");

			rWinGrid.preNoteId = items[0].get("fid");

			var notecontainerT = rWin.down("notecontainer");

			notecontainerT.setReadOnlyF(true);
			notecontainerT.setValuesF([items[0].get("fcontent"),
					items[0].get("fuserName"), items[0].get("fphone")]);

			var storeT = rWinGrid.getStore();

			var myfilter = [];
			myfilter.push({
				myfilterfield : 'fpre_note',
				CompareType : "=",
				type : "string",
				value : items[0].get("fid")
			});

			maskstring = "#0";

			storeT.setDefaultfilter(myfilter);
			storeT.setDefaultmaskstring(maskstring);
			storeT.load();

			rWin.show();

			Ext.Ajax.request({
				timeout : 6000,

				url : "isNoteAdminCurrentUser.do",
				success : function(response, option) {

					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {

						// 如果是管理员，就不设置读取标志。读取标志目前只对普通用户开放
						if (!obj.data.isAdmin) {

							// 设置状态为已读取
							Ext.Ajax.request({
								timeout : 6000,

								params : {
									fid : items[0].get("fid"),
									isUnRead : 0
								},

								url : "setNoteReadedState.do",
								success : function(response, option) {

									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										items[0].set("isUnRead", 0);

									} else {
										Ext.MessageBox.alert('错误', obj.msg);
									}

								}
							});

						}

					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}

				}
			});

		}

	}],
	fields : [{
		name : 'fid'
	}, {
		name : 'fuser'
	}, {
		name : 'fuserName'
	}, {
		name : 'fcontent'
	}, 'fphone', 'fdate', 'fpreNote', 'fprenoteMsg', 'isUnRead', {
		name : 'isUnRead',
		type : 'int'
	}],
	columns : {
		items : [Ext.create('DJ.Base.Grid.GridRowNum', {
			flex : 0
		}), {
			'header' : 'fid',
			'dataIndex' : 'fid',
			hidden : true,
			hideable : false,
			sortable : true
		}, {
			'header' : '用户',
			'dataIndex' : 'fuserName',
			sortable : true,
			width : 150

				// flex : 1
				}, {
					'header' : '内容',
					dataIndex : 'fcontent',
					sortable : true,
					flex : 1,

					renderer : function(value, metadata, record) {

						var isUnread = record.get("isUnRead");

						if (isUnread == 1) {

							metadata.style = 'color : red';

						} else {

							metadata.style = 'color : green';

						}

						return value;
					}
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
})