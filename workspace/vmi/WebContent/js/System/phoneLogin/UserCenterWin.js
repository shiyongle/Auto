Ext.define('DJ.System.phoneLogin.UserCenterWin', {
	extend : 'Ext.window.Window',

	requires : ['Ext.form.Panel', 'Ext.form.field.Text', 'Ext.button.Button'],

	height : 600,
	width : 950,
	layout : 'fit',
	title : '用户中心',

	constrainHeader : true,

//	resizable : false,

	modal : true,

	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [{
				xtype : 'form',

				
				url:  'saveUserManageUser.do',
				
				defaults : {
					width : 300
				},
				bodyPadding : 10,
				layout : {
					type : 'vbox',
					align : 'center',
					defaultMargins : {
						top : 15,
						right : 0,
						bottom : 0,
						left : 0
					},
					pack : 'center'
				},
				items : [

				{

					xtype : 'hidden',
					name : 'fid'

				}, {
					name : 'ftel',
					xtype : 'textfield',
					flex : 0,
					fieldLabel : '*手机号',
					allowBlank : false,
					regex : /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/,
					regexText : '输入的手机号格式不正确'
				}, {
					name : 'fname',
					xtype : 'textfield',
					flex : 0,
					fieldLabel : '帐号名称',
//					emptyText : '保存后，可以使用帐号登录平台',

					// afterBodyEl : 'data-qtip="保存后，可以使用帐号登录平台"'

					listeners : {

						afterrender : function(com, eOpts) {

							MyCommonToolsZ
									.setToolTipZ(com.id, '保存后，可以使用帐号登录平台');

						}

					}

				}, {
					name : 'femail',
					xtype : 'textfield',
					flex : 0,
					fieldLabel : '邮箱',
//					emptyText : '保存后，可以使用邮箱登录平台',
					regex : /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
					regexText : '您的电子邮件格式不正确',

					listeners : {

						afterrender : function(com, eOpts) {

							MyCommonToolsZ
									.setToolTipZ(com.id, '保存后，可以使用邮箱登录平台');

						}

					}

				}, {
					name : 'fphone',
					xtype : 'textfield',
					flex : 0,
					fieldLabel : '电话'
				}, {

					name : 'ffax',
					xtype : 'textfield',
					flex : 0,
					fieldLabel : '传真'
				}, {

					name : 'fqq',
					xtype : 'textfield', 
					regex :/^[1-9][0-9]{4,}$/,
					regexText : 'QQ格式不正确',
					flex : 0,
					fieldLabel : 'QQ'
				},

				{
					xtype : 'textfield',
					flex : 0,
					fieldLabel : '重置密码',
					inputType : 'password',
					itemId : 'password',
					submitValue : false
				}, {
					xtype : 'button',
					flex : 0,
					margin : '50 0 0 0',
					width : 100,
					text : '保存',

					height : 35,

					handler : function() {

						var me = this;

						var win = me.up("window");

						var form = win.down('form').getForm();

						if (form.isValid()) {
							form.submit({
								params :

								(function() {

									if (!Ext.isEmpty(win
											.down("textfield[itemId=password]")
											.getValue())) {

										return {
											password : MD5(win
													.down("textfield[itemId=password]")
													.getValue())
										};

									} else {

										return {};
									}

								})(),
								success : function(form, action) {
									djsuccessmsg(action.result.msg);
									
									win.close();
								},
								failure : function(form, action) {
									Ext.Msg.alert('失败', action.result.msg);
								}
							});
						}

					}
				}]
			}]
		});

		me.callParent(arguments);
	}

});