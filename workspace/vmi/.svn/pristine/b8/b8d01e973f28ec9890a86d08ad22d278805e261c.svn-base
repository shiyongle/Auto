function LoginForm() {
	var panel = new Ext.Panel({
		autoTabs : true,
		deferredRender : false,
		border : false,
		items : [{
					xtype : "panel",
					height:110,
					contentEl : 'hello-tabs'
				}, {
					xtype : 'tabpanel',
					id : 'loginTabs',
					activeTab : 0,
					height : 140,
					border : false,
					items : [{
						title : "身份认证",
						xtype : 'form',
						id : 'loginForm',
						frame : true,
						defaults : {
							width : 260
						},
//						bodyStyle : 'padding:10 0 0 100',
						layout : "absolute",
						defaultType : 'textfield',
						labelSeparator : '：',
						items : [{
									fieldLabel : '帐&nbsp;号',
									name : 'username',
									id : 'username',
									labelWidth : 50,
									x : 90,
									y : 10,
									// fieldCls : 'user', // cls
									blankText : '帐号不能为空,请输入!',
									maxLength : 30,
									maxLengthText : '账号的最大长度为30个字符',
									allowBlank : false,
									listeners : {
										specialkey : function(field, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												Ext.getCmp('password').focus();
											}
										}
									}
								}, {
									fieldLabel : '密&nbsp;码',
									name : 'password',
									labelWidth : 50,
									x : 90,
									y : 35,
									id : 'password',
//									cls : 'key',
									inputType : 'password',
									blankText : '密码不能为空,请输入!',
									allowBlank : false,
									listeners : {
										specialkey : function(field, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												login();
											}
										}
									}
								}]
					}, {
						title : '信息公告',
						// contentEl : 'infoDiv',
						defaults : {
							width : 230
						}
					}, {
						title : '关于',
						// contentEl : 'aboutDiv',
						defaults : {
							width : 230
						}
					}]
				}]
	});

	var win = new Ext.Window({
				title : "东经智能供应协同系统---登陆窗口",
				renderTo : Ext.getBody(),
				layout : 'fit',
				width : 460,
				height : 300,
				closeAction : 'hide',
				plain : true,
				modal : true,
				collapsible : true,
				titleCollapse : true,
				maximizable : false,
				draggable : false,
				closable : false,
				resizable : false,
				animateTarget : document.body,
				items : [panel],
				buttons : [{
							text : '&nbsp;登录',
							iconCls : 'accept',
							handler : function() {
								if (Ext.isIE) {
									/*
									 * if (!Ext.isIE8) { Ext.MessageBox .alert(
									 * '温馨提示', '系统检测到您正在使用基于MSIE内核的浏览器<br>我们强烈建议立即切换到<b><a
									 * href="http://firefox.com.cn/"
									 * target="_blank">FireFox</a></b>或者<b><a
									 * href="http://www.google.com/chrome/?hl=zh-CN"
									 * target="_blank">GoogleChrome</a></b>浏览器体验飞一般的感觉!' + '<br>如果您还是坚持使用IE,那么请使用基于IE8内核的浏览器登录!')
									 * return; }
									 */
									login();
								} else {
									login();
								}
							}
						}
				// , {
				// text : '&nbsp;选项',
				// iconCls : 'tbar_synchronizeIcon',
				// menu : mainMenu
				// }
				]
			});

	win.show();

	// win.on('show', function() {
	// setTimeout(function() {
	// var account = Ext.getCmp('username').findById('account');
	// var password = Ext.getCmp('loginForm').findById('password');
	// var c_account = getCookie('g4.login.account');
	// account.setValue(c_account);
	// if (Ext.isEmpty(c_account)) {
	// account.focus();
	// } else {
	// password.focus();
	// }
	// }, 200);
	// }, this);

	//			
	//			
	//			
	//			
	//			
	//			
	//			
	//			
	//			
	//			
	// var leftPanel = new Ext.Panel({
	// id : "leftPanel",
	// height : 98,
	// bodyStyle : "background-image: url(images/dj.jpg);",
	// hiden : false
	// });
	//
	// var rightPanel = new Ext.form.FormPanel({
	// id : "rightPanel",
	// labelPad : 0,
	// mthod : 'post',
	// frame : true,
	// url : "logonAction.do",
	// labelWidth : 60,
	// bodyStyle : "padding-left:30px;padding-top:10px;padding-right:30px",
	// layout : "form",
	// items : [{
	// xtype : "textfield",
	// name : "username",
	// allowBlank : false,
	// blankText : '用户名不能为空',
	// emptyText : '请输入用户名',
	// fieldLabel : "用户名",
	// width : 100
	// }, {
	// id : "password",
	// xtype : "field",
	// name : "password",
	// fieldLabel : "密 码",
	// inputType : "password",
	// iconCls : "password",
	// width : 100
	// }],
	// buttons : [{
	// xtype : "button",
	// text : "确 定",
	// pressed : false,
	// iconCls : 'login',
	// handler : validatorData
	// }, {
	// xtype : "button",
	// text : "注 册",
	// iconCls : 'user',
	// handler : function() {
	// loginWindow.close();
	// }
	// }],
	// buttonAlign : "center"
	//
	// });
	//
	// var loginPanel = new Ext.Panel({
	// id : "loginPanel",
	// height : 150,
	// frame : true,
	// layout : "card",
	// items : [leftPanel, rightPanel]
	// });
	// var loginWindow;
	// if (!loginWindow) {
	// loginWindow = new Ext.Window({
	// id : "loginWindow",
	// modal : true,
	// title : "东经智能供应协同系统---登陆窗口",
	// width : 500,
	// height : 300,
	// resizable : false,
	// closable : false,
	// items : [leftPanel, loginPanel],
	// bodyStyle : "padding:20px;background-color:#000000;background-image:
	// url(images/loginbg.jpg);"
	// });
	// }
	//
	// loginWindow.show();
	// var map = new Ext.KeyMap(loginWindow.getEl(), {
	// key : 13,
	// fn : validatorData
	// });
	function login() {
		var form = Ext.getCmp('loginForm');
		if (form.isValid()) {
			var Password = Ext.getCmp("password").getValue();
			if (Ext.util.Format.trim(Password) == "") {
				Ext.MessageBox.alert("警告", "密码不能够为空！");
				return;
			}
			Ext.getCmp("password").setValue(MD5(Password));
			form.submit({
						waitMsg : '正在登录,请稍候...',
						url : "logonAction.do",
						mthod : 'POST',
						success : function(response, option, action) {
							if (option.result.success == true) {
								cookiemanager = new Ext.state.CookieProvider();
								Ext.state.Manager.setProvider(cookiemanager);
								cookiemanager.set("username",
										option.result.data.username);
								window.location.href = 'index.do';
							} else {
								Ext.MessageBox.alert("登录失败", option.result.msg); // obj.msg
								Ext.getCmp("password").setValue(Password);
							}
						},
						failure : function(response, option) {
							if (option.failureType == 'server') {
								Ext.MessageBox.alert('友情提示', option.result.msg);
							} else if (option.failureType == 'connect') {
								Ext.Msg.alert('连接错误', '无法连接服务器!');
							} else if (option.failureType == 'client') {
								Ext.Msg.alert('提示', '数据错误，非法提交');
							} else {
								Ext.MessageBox.alert('警告', '服务器数据传输失败：'
												+ option.response.responseText);
							}
							Ext.getCmp("password").setValue(Password);
						}
					});
		} else {
			Ext.MessageBox.alert("提示", '填写内容有误，请修改！');
		}
	}
}

Ext.onReady(LoginForm);
