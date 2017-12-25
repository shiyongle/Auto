Ext.define('DJ.System.UserOnlineManagement', {
			extend : 'Ext.c.GridPanel',
			title : "在线用户管理",
			id : 'DJ.System.UserOnlineManagement',
			iconCls : 'user',
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			pageSize : 100,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'getUserOnlineList.do',
			Delurl : "kickUserOnline.do",
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
				// this._showButtons(['DJ.System.UserOnlineManagement.delbutton',
				// 'DJ.System.UserOnlineManagement.refreshbutton',
				// 'DJ.System.UserOnlineManagement.querybutton']);

//				this._operateButtonsView(true, [
//								'DJ.System.UserOnlineManagement.delbutton',
//								'DJ.System.UserOnlineManagement.refreshbutton',
//								'DJ.System.UserOnlineManagement.querybutton']);

				this._operateButtonsView(true, [3, 4, 5]);
				
//				Ext.getCmp("DJ.System.UserOnlineManagement.delbutton").setText("");

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
			custbar : [],
			fields : [{
						name : 'fid'
					}, {
						name : 'fusername',
						myfilterfield : 'FUSERNAME',
						myfiltername : '名称',
						myfilterable : true
					}, {
						name : 'fuserid'

					}, {
						name : 'fip'

					}, {
						name : 'fbrowser'

					}, {
						name : 'fsystem'

					}, {
						name : 'flogintime'
					}, {
						name : 'flastoperatetime'
					}],
			columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : '用户ID',
						'dataIndex' : 'fuserid',
						sortable : true
					}, {
						'header' : '用户名称',
						'dataIndex' : 'fusername',
						sortable : true
					}, {
						'header' : '登录IP',
						'dataIndex' : 'fip',
						sortable : true
					}, {
						'header' : '客户端系统',
						width : 200,
						'dataIndex' : 'fsystem',
						sortable : true
					}, {
						'header' : '浏览器',
						'dataIndex' : 'fbrowser',
						width : 200,
						sortable : true
					}, {
						'header' : '登录时间',
						'dataIndex' : 'flogintime',
						width : 200,
						sortable : true
					}, {
						'header' : '注销时间',
						'dataIndex' : 'flastoperatetime',
						width : 200,
						sortable : true
					}],

			/**
			 * 主要调用这个方法
			 * 
			 * @param {}
			 *            show，bool，
			 * @param {}
			 *            array，元素为button ID或为数字索引（从0开始）
			 */
			_operateButtonsView : function(show, array) {

				if (Ext.typeOf(array[0]) == "number") {
					array = this._translateNumToDataIndex(array);
				}

				if (show) {
					this._showButtons(array);
				} else {
					this._hideButtons(array);
				}

			},
			_translateNumToDataIndex : function(array) {
				var arrayt = [];

				var arrayAll = this._getToolsButtonIDs();

				Ext.each(arrayAll, function(item, index, all) {
							if (Ext.Array.contains(array, index)) {
								arrayt.push(arrayAll[index]);
							}
						});

				return arrayt;
			},

			_hideButtons : function(array) {
				var t = array;
				Ext.each(t, function(item, index, all) {
							Ext.getCmp(item).hide();
						});
			},

			_showButtons : function(array) {
				var defaultButtons = this._getToolsButtonIDs();

				var tArray = Ext.Array.difference(defaultButtons, array);

				this._hideButtons(tArray);
			},

			_getToolsButtonIDs : function() {
				var defaultButtons = [];

				var t = this.dockedItems.items[2].items.items;

				Ext.each(t, function(item, index, all) {

							defaultButtons.push(item.id);

						});
				return defaultButtons;
			}
		});
