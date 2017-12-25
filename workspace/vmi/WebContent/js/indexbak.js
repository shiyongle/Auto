function LoginForm() {
	Ext.Loader.setConfig({
				enabled : true,
				paths : {
					'DJ' : 'js',
					'Ext.ux' : 'extlib/ux'
				}
			});
	var leftPanel = new Ext.Panel({
				id : "leftPanel",
				height : 98,
				bodyStyle : "background-image: url(images/dj.jpg);",
				hiden : false
			});

	var rightPanel = new Ext.form.FormPanel({
				id : "rightPanel",
				labelPad : 0,
				mthod : 'post',
				frame : true,
				url : "logonAction.do",
				labelWidth : 60,
				bodyStyle : "padding-left:30px;padding-top:10px;padding-right:30px",
				layout : "form",
				items : [{
							xtype : "textfield",
							name : "username",
							allowBlank : false,
							blankText : '用户名不能为空',
							emptyText : '请输入用户名',
							fieldLabel : "用户名",
							width : 100
						}, {
							id : "password",
							xtype : "field",
							name : "password",
							fieldLabel : "密        码",
							inputType : "password",
							iconCls : "password",
							width : 100
						}],
				buttons : [{
							xtype : "button",
							text : "确          定",
							pressed : false,
							iconCls : 'login',
							handler : validatorData
						}, {
							xtype : "button",
							text : "注          册",
							iconCls : 'user',
							handler : function() {
								loginWindow.close();
							}
						}],
				buttonAlign : "center"

			});

	var loginPanel = new Ext.Panel({
				id : "loginPanel",
				height : 150,
				frame : true,
				layout : "card",
				items : [leftPanel, rightPanel]
			});
	var loginWindow;
	if (!loginWindow) {
		loginWindow = new Ext.Window({
			id : "loginWindow",
			modal : true,
			title : "东经智能供应协同系统---登陆窗口",
			width : 500,
			height : 300,
			resizable : false,
			closable : false,
			items : [leftPanel, loginPanel],
			bodyStyle : "padding:20px;background-color:#000000;background-image: url(images/loginbg.jpg);"
		});
	}

	loginWindow.show();
	var map = new Ext.KeyMap(loginWindow.getEl(), {
				key : 13,
				fn : validatorData
			});
	function validatorData() {
		if (rightPanel.isValid()) {
			var Password = Ext.getCmp("password").getValue();
			if (Ext.util.Format.trim(Password) == "") {
				Ext.MessageBox.alert("警告", "密码不能够为空！");
				return;
			}
			Ext.getCmp("password").setValue(MD5(Password));
			rightPanel.submit({
						waitMsg : '正在登录,请稍候...',
						success : function(response, option, action) {
							if (option.result.success == true) {
								cookiemanager = new Ext.state.CookieProvider();
								Ext.state.Manager.setProvider(cookiemanager);
								cookiemanager.set("username",
										option.result.username);
								Ext.create('DJ.MainList');
								loginWindow.close();

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
