Ext.define('DJ.System.phoneLogin.MyPhoneLoginWin', {
	extend : 'Ext.window.Window',

	requires : ['Ext.form.Panel', 'Ext.form.field.Text', 'Ext.button.Button','DJ.tools.mainPageRel.IndexMessageRel'],

	autoShow : true,
	layout : 'fit',
	title : '注册',
	constrainHeader : true,
	
//	resizable : false,
	
	modal: true,

	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [{
				xtype : 'form',
				
				url: 'submitRegister.do',
				
				bodyPadding : 10,
				layout : {
					type : 'vbox',
					align : 'stretch'
				},
				items : [{
					xtype : 'container',
					flex : 1,
					layout : {
						type : 'vbox',
						align : 'center',
						pack : 'center'
					},
					items : [{
						xtype : 'container',
						layout : {
							type : 'table',
							columns : 2
						},
						items : [{
							name : 'name',
							xtype : 'textfield',
							fieldLabel : '手机号',
							beforeLabelTextTpl: '<span style="color:red;font-weight:bold" data-qtip="必填项">*</span>',
							allowBlank : false,
							labelWidth: 60,
							width: 300,
							blankText:'请输入手机号',
							regex : /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/,
							regexText : '手机号错误'

						}, {
							xtype : 'button',
							text : '获取短信验证码',
							style: 'margin-left: 5px;',
							handler : function() {

								var me = this;

								var win = me.up("window");

								var phone = win.down("textfield[name=name]").getValue();

								if (Ext.isEmpty(phone)) {
								
									
									Ext.Msg.alert("提示", "请输入手机号");
									
									return ;
									
								}
								if(!win.down("textfield[name=name]").validate())
								{
									Ext.Msg.alert("提示", "手机号格式错误");
									return ;
								}
								
								var el = win.getEl();
								el.mask("系统处理中,请稍候……");

								Ext.Ajax.request({
									timeout : 60000,

									params : {
										name : phone
									},

									url : "gainLoginValidateVCodeTel.do",
									success : function(response, option) {

										var obj = Ext
												.decode(response.responseText);
										if (obj.success == true) {

											djsuccessmsg('发送成功');
										} else {
											Ext.MessageBox.alert('错误', obj.msg);
										}
										el.unmask();
									}
								});

							}
						}, {
							xtype : 'textfield',
							fieldLabel : '验证码',
							beforeLabelTextTpl: '<span style="color:red;font-weight:bold" data-qtip="必填项">*</span>',
							name : 'lvc',
							allowBlank : false,
							blankText:'请输入验证码',
							labelWidth: 60,
							width: 300,
							maxLength : 6,
							enforceMaxLength : true,
							minLengthText:"验证码至少6位",
							minLength : 6
							
						}]
					}]
				}, {
					xtype : 'container',
					flex : 1,
					layout : {
						type : 'vbox',
						align : 'center',
						pack : 'center'
					},
					items : [{
						xtype : 'button',
						height : 42,
						width : 117,
						text : '提交注册',
						handler : function() {

							var me = this;

							var win = me.up("window");

							var form = win.down('form').getForm();

							if (form.isValid()) {
								form.submit({
									success : function(form, action) {
										
										
										
										Ext.Msg
												.show({
														title:'提示信息',
														msg:'帐号注册成功，初始密码为注册验证码<br/>是否立即申请认证？<br/>认证后能获得更多你想要的权限哦！',
														buttons: Ext.MessageBox.YESNO,
														buttonText:{  yes: "是",   no: "否" },
														fn:function(buttonID) {

															cookiemanager.set("username",
																	form.getValues().name);
																	
															win.close();
																	
															window.location.href = 'index.do';
															
															if (buttonID == 'yes') {

																Ext.Ajax
																		.request({
																			timeout : 6000,

																			params : {

																			},

																			url : "getIsAuthenticationByCustomer.do",
																			success : function(
																					response,
																					option) {

																				var obj = Ext
																						.decode(response.responseText);
																				if (obj.success == true) {
																					if (obj.total == 1) {

																					} else {

																						var fid = obj.msg;

																						DJ.tools.mainPageRel.IndexMessageRel
																								.doUA(fid);

																					}

																				} else {
																					Ext.MessageBox
																							.alert(
																									'错误',
																									obj.msg);
																				}
																				//				el.unmask();
																			}
																		});

															} else if (buttonID == 'no') {
																
															}

														}

									}	);

									},
									failure : function(form, action) {
										Ext.Msg.alert('失败',
												action.result.msg);
									}
								});
							}else
							{
								var   fields = form.getFields();
								fields.each(function(field) {
									if(field.getErrors().length>0){
									  	Ext.Msg.alert('错误',field.getErrors()[0]);
									  	return false;
									}
								});
								
								

							}

						}
					}]
				}]
			}]
		});

		me.callParent(arguments);
	}

});

var win = Ext.create('DJ.System.phoneLogin.MyPhoneLoginWin');

win.show();