Ext.define('DJ.other.oneTimeLoginCode.OneTimeLoginCodeWin', {
	extend : 'Ext.window.Window',
	id: 'extWin',	//此id固定，用于设置css样式
	modal : true,
	autoShow : true,
	resizable : false,
	userName : '',
	initComponent: function(){
		var me = this;
		Ext.apply(me,{
			xtype: 'container',
			defaultType: 'textfield',
			defaults: {
				margin: 10
			},
			items: [{
				fieldLabel: '帐　号',
				itemId: 'username',
				labelWidth: 65,
				width: 275,
				validator: function(val){
					if(!Ext.String.trim(val)){
						return '请先填写帐号！';
					}
					return true;
				}
			},{
				xtype: 'container',
				layout: 'hbox',
				items: [{
					xtype: 'textfield',
					fieldLabel: '校验码',
					itemId: 'code',
					labelWidth: 65,
					validator: function(val){
						if(!Ext.String.trim(val)){
							return '请填写校验码！';
						}
						return true;
					}
				},{
					xtype: 'button',
					text: '获取校验码',
					style: 'margin-left:5px',
					handler: function(){
						var btn = this,
							usernameField = me.down('textfield[itemId=username]'),
							el = me.getEl();
						if(!usernameField.isValid()){
							return;
						}
						el.mask("正在获取校验码……");
						Ext.Ajax.request({
							url: 'gainLoginValidateVCode.do',
							timeout : 6000,
							params: {
								name: me.username = usernameField.getValue()
							},
							success : function(response, option) {
								var obj = Ext.decode(response.responseText);
								if (obj.success == true) {
									djsuccessmsg("短信已发送至您在平台所绑定的手机，请注意查收！");
									me.down('textfield[itemId=password]').show();
									me.down('container[itemId=psContainer]').show();
									btn.disable();
									setTimeout(function() {
										btn.enable();
									}, 60000);
								} else {
									Ext.MessageBox.alert('错误', obj.msg);
								}
								el.unmask();
							}
						});
					}
				}]
			},{
				fieldLabel: '新密码',
				itemId: 'password',
				labelWidth: 65,
				allowBlank: false,
				blankText: '请填写密码',
				width: 275,
				hidden: true
			},{
				xtype: 'container',
				layout: 'hbox',
				itemId: 'psContainer',
				hidden: true,
				items: [{
					xtype: 'textfield',
					fieldLabel: '确认密码',
					labelWidth: 65,
					width: 232,
					validator: function(val){
						var password;
						if(!val){
							return '请填写确认密码';
						}
						password = this.up('window').down('textfield[itemId=password]').getValue();
						if(password !== val){
							return '密码不一致！';
						}
						return true;
					}
				},{
					xtype: 'button',
					text: '确定',
					style: 'margin-left:5px',
					handler: function(){
						var passwordField = me.down('textfield[itemId=password]'),
							npasswordField = this.prev(),
							codeField = me.down('textfield[itemId=code]'),
							el = me.getEl();
						if(!codeField.isValid() || !passwordField.isValid() || !npasswordField.isValid()){
							return;
						}
						el.mask("系统处理中,请稍候……");
						Ext.Ajax.request({
							timeout : 6000,
							params : {
								name : me.username,
								lvc : codeField.getValue(),
								password : MD5(passwordField.getValue())
							},
							url : "changePWByLVC.do",
							success : function(response) {
								var obj = Ext.decode(response.responseText);
								if (obj.success) {
									me.close();
									djsuccessmsg(obj.msg);
								} else {
									Ext.MessageBox.alert('错误', obj.msg);
								}
								el.unmask();
							}
						});
					}
				}]
			}]
		});
		me.callParent(arguments);
	}
});
