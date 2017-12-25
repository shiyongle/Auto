

Ext.define('DJ.test.testEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.test.testEdit",
	modal : true,
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
	},
	custbar : [{
				// id : 'DelButton',
				text : '自定义按钮1',
				height : 30,
				handler : function() {
					// var a = Ext.getCmp('DJ.test.testEdit');
					// a.seteditstate("edit"); // 设置界面可编辑"view"和""为不可编辑，其他都是可以编辑
					Ext.getCmp("usercom").setDefaultfilter([{
								myfilterfield : "T_SYS_USER.fname",
								CompareType : "like",
								type : "string",
								value : "e"
							}]);
					Ext.getCmp("usercom").setDefaultmaskstring(" #0 ");
					Ext.getCmp("usercom").setmyvalue("fid:\"3f6112db-a952-11e2-90fc-60a44c5bbef3\",fname:\"EAS\",feffect:\"\",fcustomername:\"\",femail:\"\",ftel:\"\",fcreatetime:\"\"");
//					Ext.getCmp("usercom").setmyvalue("fid:\"3f6112db-a952-11e2-90fc-60a44c5bbef3\",fname:\"EAS\",feffect:\"\",fcustomername:\"\",femail:\"\",ftel:\"\",fcreatetime:\"\"");

				}
			}, {
				// id : 'DelButton',
				text : '自定义按钮2',
				height : 30
			}],
	title : "用户管理编辑界面",
	width : 400,// 230, //Window宽度
	height : 300,// 137, //Window高度
	resizable : false,
	url : 'SaveSysUser.do',
	infourl : 'GetUserInfo.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'GetUserInfo.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true
	items : [{
				baseCls : 'x-plain',
				items : [

				{
							id : 'DJ.System.UserEdit.UserFID',
							xtype : 'textfield',
							name : 'fid',
							fieldLabel : 'FID',
							width : 300,
							hidden : true

						}, {
							id : 'DJ.System.UserEdit.UserName',
							xtype : 'textfield',
							name : 'fname',
							fieldLabel : '用户名称',
							allowBlank : false,
							blankText : '用户名不能为空',
							regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
							regexText : "不能包含特殊字符",
							width : 300

						}, {
							id : 'DJ.System.UserEdit.PassWord',
							name : 'fpassword',
							xtype : 'textfield',
							fieldLabel : '密码',
							allowBlank : false,
							blankText : '用户名不能为空',
							inputType : 'password',
							width : 300

						}, {
							id : 'DJ.System.UserEdit.PassWord1',
							xtype : 'textfield',
							fieldLabel : '重复密码',
							allowBlank : false,
							blankText : '用户名不能为空',
							inputType : 'password',
							width : 300

						}, {
							id : 'DJ.System.UserEdit.CustomerName',
							name : 'fcustomername',
							xtype : 'textfield',
							fieldLabel : '客户名称',
							allowBlank : false,
							blankText : '用户名不能为空',
							regex : /^([\u4E00-\u9FA5]|\w|[@.])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
							regexText : "不能包含特殊字符",
							width : 300

						}, {
							id : 'DJ.System.UserEdit.Email',
							name : 'femail',
							xtype : 'textfield',
							allowBlank : false,
							blankText : '用户名不能为空',
							fieldLabel : '邮箱',
							// regex :
							// /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/,//
							// /^[^,\!@#$%^&*()_+}]*$/,
							regex : /^(\w|[.])*@(\w|[@.])*\.(\w|[.])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
							regexText : "不能包含特殊字符",
							width : 300

						}, {
							id : 'DJ.System.UserEdit.Tel',
							name : 'ftel',
							xtype : 'textfield',
							allowBlank : false,
							blankText : '用户名不能为空',
							fieldLabel : '手机',
							regex : /^(13[0-9]|15[0|3|6|7|8|9]|18[0|8|9])\d{8}$/,// /^[^,\!@#$%^&*()_+}]*$/,
							regexText : "你输入的不是手机号",
							width : 300

						},
						// combobox控件
						{
							valueField : 'fid', // 组件隐藏值
							id : "usercom",
							name : "user",
							xtype : 'cCombobox',
							displayField : 'fname',// 组件显示值
							fieldLabel : '下拉',
							beforeExpand : function() {
								var s = 1;
							},
							MyConfig : {
								width : 800,// 下拉界面
								height : 200,// 下拉界面
								url : 'GetUserList.do', // 下拉数据来源
								fields : [{
											name : 'fid'
										}, {
											name : 'fname',
											myfilterfield : 'T_SYS_USER.fname', // 查找字段，发送到服务端
											myfiltername : '名称',// 在过滤下拉框中显示的名称
											myfilterable : true
											// 该字段是否查找字段
									}	, {
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
							}
						}, {
							id : 'DJ.System.UserEdit.CreateTime',
							name : 'fcreatetime',
							xtype : 'textfield',
							hidden : true,
							width : 300

						}]
			}],
	bodyStyle : "padding-top:5px;padding-left:30px"
});