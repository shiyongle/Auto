Ext.define('DJ.test.testList', {
			extend : 'Ext.c.GridPanel',
			title : "测试列表界面",
			id : 'DJ.test.testList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetUserList.do',
			Delurl : "DelUserList.do",
			EditUI : "DJ.test.testEdit",
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
			},
			custbar : [{
						// id : 'DelButton',
						text : '自定义按钮1',
						height : 30,
						handler : function() {
							var me = this;
						}
					}, {
						// id : 'DelButton',
						text : '自定义按钮2',
						height : 30
					}],
			fields : [{
						name : 'fid'
					}, {
						name : 'fname',
						myfilterfield : 'T_SYS_USER.fname',
						myfiltername : '名称',
						myfilterable : true
					}, {
						name : 'feffect'
					}, {
						name : 'fcustomername',
						myfilterfield : 'T_SYS_USER.fcustomername',
						myfiltername : '客户名称',
						myfilterable : true
					}, {
						name : 'femail'
					}, {
						name : 'ftel'
					}, {
						name : 'fcreatetime'
					}],
			columns : [{
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : '用户名称',
						'dataIndex' : 'fname',
						sortable : true
					}, {
						'xtype' : 'checkcolumn',
						'header' : '是否启用',
						processEvent : function() {
						},
						// readOnly:true,
						'dataIndex' : 'feffect',
						sortable : true
					}, {
						'header' : '客户名称',
						'dataIndex' : 'fcustomername',
						sortable : true
					}, {
						'header' : '邮箱',
						'dataIndex' : 'femail',
						sortable : true
					}, {
						'header' : '手机',
						'dataIndex' : 'ftel',
						sortable : true
					}, {
						'header' : '创建时间',
						'dataIndex' : 'fcreatetime',
						width : 200,
						sortable : true
					}]
		})