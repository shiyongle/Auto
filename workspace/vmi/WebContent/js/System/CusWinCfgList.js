Ext.define('DJ.System.CusWinCfgList', {
			extend : 'Ext.c.GridPanel',
			title : "自定义桌面",
			id : 'DJ.System.CusWinCfgList',
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'getWinCfgsByUser.do',
			Delurl : "deleteWinCfgs.do",
			EditUI : "DJ.System.CusWinCfgEdit",
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
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
			}

			,
			custbar : [
				{
				// id : 'DelButton',
				text : '刷新主页',
				height : 30,
				handler : function() {

					var storet = Ext.data.StoreManager
							.lookup('storeCusWinCfgId');

					storet.load({
								scope : this,
								callback : function(records, operation, success) {

									if (!success) {

										return;

									}

									var mainPageBgCfg = {
						xtype : "image",
						src : 'images/mainPageBg.jpg',
						style : {
							height : '100%',
							width : '100%'

						}
					};
									
									var winItems = [mainPageBgCfg];

									Ext.each(storet.data.items,
											function(item, index, all) {

												item = item.data;

												var obj = {
													xtype : 'panel',

													// constrainHeader : true,

													resizable : false,

													id : item.fid,
													title : item.ftitle,
													draggable : false,
													width : item.fwidth,
													height : item.fheight,
													x : item.fPositionx,
													y : item.fPositiony,
													html : item.fcode
												};

												winItems.push(obj);
											});

									Ext.getCmp("mianPagePanel").removeAll();
									
									Ext.getCmp("mianPagePanel").add(winItems);

								}
							})

				}
			}
			
			],

			fields : [{
						name : 'fid'
					}, {
						name : 'userName'
					}, {
						name : 'fPositionx',
						type : 'int'
					}, {
						name : 'fPositiony',
						type : 'int'
					}, {
						name : 'ftitle'
					}, {
						name : 'fwidth',
						type : 'float'
					}, {
						name : 'fheight',
						type : 'float'
					}, {
						name : 'ftype'
					}, {
						name : 'fcode'
					}],
			columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
						'header' : '用户名',
						'dataIndex' : 'userName',
						sortable : true

					}, {
						'header' : 'X坐标',
						'dataIndex' : 'fPositionx',
						sortable : true

					}, {
						'header' : 'Y坐标',
						'dataIndex' : 'fPositiony',
						sortable : true

					}, {
						'header' : '标题',
						'dataIndex' : 'ftitle',
						sortable : true

					}, {
						'header' : '宽度',
						'dataIndex' : 'fwidth',
						sortable : true

					}, {
						'header' : '高度',
						'dataIndex' : 'fheight',
						sortable : true

					}, {
						'header' : '类型',
						'dataIndex' : 'ftype',
						sortable : true

					}
			// , {
			// xtype: "gridcolumn",
			// 'header' : '内容码',
			// 'dataIndex' : 'fcode',
			// // height : 50,
			// sortable : true
			// }

			]
		})