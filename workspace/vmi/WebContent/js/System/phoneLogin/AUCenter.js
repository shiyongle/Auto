Ext.define('DJ.System.phoneLogin.AUCenter', {
	extend : 'Ext.window.Window',

	requires : ['Ext.button.Button', 'Ext.form.Label', 'Ext.form.Panel',
			'Ext.form.field.Text', 'DJ.myComponent.form.ImgUploadAndshower'],

	activeItem : 0,

	height : 600,
	width : 950,

	constrainHeader : true,

	goalFid : '',

	hadSaved : false,

	resisable : false,

	modal : true,

	layout : 'card',
	title : '认证',

	goToOtherPage : function(itemId) {

		var me = this, gTitles = {
			enterprisePage : '企业认证',
			personPage : '个人认证'

		};

		me.setTitle(gTitles[itemId]);
		me.getLayout().setActiveItem(itemId);

	},

	listeners : {

		beforeclose : function(win, eOpts) {

			if (!win.hadSaved && win.title != '认证') {

				Ext.Ajax.request({
					timeout : 6000,

					params : {
						fid : win.goalFid
					},

					url : "deleteAuthenticationFileByfid.do",
					success : function(response, option) {

						// var obj = Ext.decode(response.responseText);
						// if (obj.success == true) {
						// Ext.MessageBox.alert('成功', obj.msg);
						//
						// } else {
						// Ext.MessageBox.alert('错误', obj.msg);
						// }
						// el.unmask();
					}
				});

			}

		}

	},

	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [{
				itemId : 'mainPage',
				xtype : 'panel',
				layout : {
					type : 'vbox',
					align : 'stretch',
					defaultMargins : {
						top : 15,
						right : 15,
						bottom : 15,
						left : 15
					},
					pack : 'center'
				},
				items : [{
					xtype : 'container',
					flex : 0,
					height : 200,
					layout : {
						type : 'hbox',
						align : 'middle',
						defaultMargins : {
							top : 0,
							right : 10,
							bottom : 0,
							left : 0
						},
						pack : 'center',
						padding : 10
					},
					items : [{
						xtype : 'button',
						height : 100,
						width : 100,
						text : '<font size=5>个人<br/>认证</font>',
						handler : function() {

							me.goToOtherPage('personPage');

						}
					}, {
						xtype : 'label',
						flex : 1,
						html : '<table width="100%" border="0">   <tr>     <td ><span style="font-size: 18px">个人认证</span>（即时开通，无需等待）</td>   </tr>   <tr>     <td>①点击立即认证》②输入身份证号、姓名，拍照上传证件》③确认认证信息，认证完成。</td>   </tr> </table>'
					}, {
						xtype : 'button',
						text : '立即认证',
						handler : function() {

							me.goToOtherPage('personPage');

						}
					}]
				}, {
					xtype : 'container',
					flex : 0,
					height : 200,
					layout : {
						type : 'hbox',
						align : 'middle',
						defaultMargins : {
							top : 0,
							right : 10,
							bottom : 0,
							left : 0
						},
						pack : 'center',
						padding : 10
					},
					items : [{
						xtype : 'button',
						text : '<font size=5>企业<br/>认证</font>',
						height : 100,
						width : 100,
						handler : function() {

							me.goToOtherPage('enterprisePage');

						}
					}, {
						xtype : 'label',
						flex : 1,
						html : '<table width="100%" border="0">   <tr>     <td ><span style="font-size: 18px">企业认证</span>（2小时）</td>   </tr>   <tr>     <td>①点击立即认证》②输入企业名称、法人代表、营业执照注册号，拍照上传证件》③确认认证信息，认证完成。</td>   </tr> </table>'
					}, {
						xtype : 'button',
						text : '立即认证',
						handler : function() {

							me.goToOtherPage('enterprisePage');

						}
					}]
				}]
			}, {
				itemId : 'personPage',
				xtype : 'form',
				bodyPadding : 10,
				// title: '个人认证',

				url : '',

				layout : {
					type : 'vbox',
					align : 'center',
					pack : 'center'
				},

				defaults : {
					width : 300
				},

				items : [{
					xtype : 'textfield',
					flex : 0,
					fieldLabel : '*姓名',
					allowBlank : false,
					blankText:'请填写姓名',
					name : 'name'
				}, {
					xtype : 'textfield',
					flex : 0,
					fieldLabel : '*身份证号',
					name : 'identitycard',
					allowBlank : false,
					blankText:'请填写身份证号',
					regex : /(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
					regexText : '身份证输入有误,请核对更改'
				}, {
					xtype : 'container',
					// flex : 0,
					margins : '20 0 0 0',
					height : 200,
					width : '98%',
					layout : {
						type : 'vbox',
						align : 'center',
						pack : 'center'
					},
					items : [{
						xtype : 'container',
						layout : {
							type : 'hbox',
							align : 'middle',
							defaultMargins : {
								top : 0,
								right : 5,
								bottom : 0,
								left : 5
							},
							pack : 'center'
						},
						items : [{
							xtype : 'imguploadandshower',
							imgLableHtml : '*身份证正面照',
							flex : 1,
							width : 300,
							
							params : {type : 0},
							
							uploadUrl : 'uploadCustomerAuthenticationFile.do',

							doChange : function() {

								var me = this;

								var fid = me.up('window').goalFid;

								me.params.fparentid = fid;

							}

						}, {
							xtype : 'imguploadandshower',
							imgLableHtml : '*身份证背面照',
							
							params : {type : 0},
							
							flex : 1,
							width : 300,
							uploadUrl : 'uploadCustomerAuthenticationFile.do',

							doChange : function() {

								var me = this;

								var fid = me.up('window').goalFid;

								me.params.fparentid = fid;

							}
						}]
					}, {
						xtype : 'label',
						flex : 1,
						html : '证件需要清晰有效<br/>仅支持JPG、GIF、PNG图片文件，且需小于2M'
					}]
				}, {
					xtype : 'button',
					margins : '30 0 0 0',
					text : '提交认证',
					width : 100,

					handler : function() {

						var me = this;

						var win = me.up("window");

						
						var formT =  win.down('form[itemId=personPage]');
						
						var form =formT.getForm();

						var fileFs = formT.query('imguploadandshower');

						
						var breakF = false;

						Ext.each(fileFs, function(ele, index, all) {

							if (!ele.down('oneclickfilefieldformulitichoice').hasUpLoaded) {

								Ext.Msg.alert("提示", "文件未上传");

								breakF = true;

								return;

							}

						});

						if (breakF) {

							return;

						}

						if (form.isValid()) {

							var el = win.getEl();

							el.mask("系统处理中,请稍候……");

							var goalP = form.getValues();

							goalP.type = 0;

							Ext.Ajax.request({
								timeout : 6000,

								params : goalP,

								url : 'sumbitAuthentication.do',
								success : function(response, option) {

									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {

										win.hadSaved = true;
										win.close();

										djsuccessmsg(obj.msg);
										
										setTimeout(function() {

											// 刷新
											window.location.reload();

										}, 1000);

									} else {
										Ext.MessageBox.alert('错误', obj.msg);
									}
									el.unmask();
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
			}, {
				itemId : 'enterprisePage',
				xtype : 'form',
				bodyPadding : 10,
				// title: '企业认证',

				url : '',

				layout : {
					type : 'vbox',
					align : 'center',
					pack : 'center'
				},

				defaults : {
					width : 500
				},

				items : [{
					xtype : 'textfield',
					flex : 0,
					allowBlank : false,
					fieldLabel : '*企业名称',
					name : 'companyname'

				}, {
					xtype : 'textfield',
					flex : 0,
					allowBlank : false,
					fieldLabel : '*注册号',
					name : 'registrationno',
					
				
					
					emptyText : '营业执照、组织机构代码证与税务登记证，三选一'
				}, {
					xtype : 'textfield',
					flex : 0,
					name : 'fartificialperson',
					allowBlank : false,
					fieldLabel : '*法人代表'
				}, {
					xtype : 'textfield',
					flex : 0,
					name : 'personfphone',
					fieldLabel : '法人联系电话'
				}, {
					xtype : 'container',
					flex : 0,
					margins : '20 0 0 0',
					// height : 189,
					width : "60%",
					layout : {
						type : 'vbox',
						align : 'center',
						pack : 'center'
					},
					items : [{
						xtype : 'container',
						layout : {
							type : 'hbox',
							align : 'middle',
							defaultMargins : {
								top : 0,
								right : 5,
								bottom : 0,
								left : 5
							},
							pack : 'center'
						},
						items : [{
							xtype : 'imguploadandshower',
							
								params : {type : 1},
							
							height : 300,
							width : 400,
							imgLableHtml : '*证件上传（上传的证件照必须与填写的注册号相同）',
							// flex : 1,
							uploadUrl : 'uploadCustomerAuthenticationFile.do',

							doChange : function() {

								var me = this;

								var fid = me.up('window').goalFid;

								me.params.fparentid = fid;

							}
						}]
					}, {
						xtype : 'label',

						flex : 1,
						html : '证件需要清晰有效<br/>仅支持JPG、GIF、PNG图片文件，且需小于2M'
					}]
				}, {
					xtype : 'button',
					margins : '30 0 0 0',
					text : '提交认证',
					width : 100,

					handler : function() {

						var me = this;

						var win = me.up("window");
						
						
						var formT =  win.down('form[itemId=enterprisePage]');
						
						var form =formT.getForm();

						var fileFs = formT.query('imguploadandshower');
						
						
						var breakF = false;

						Ext.each(fileFs, function(ele, index, all) {

							if (!ele.down('oneclickfilefieldformulitichoice').hasUpLoaded) {

								Ext.Msg.alert("提示", "文件未上传");

								breakF = true;

								return;

							}

						});

						if (breakF) {

							return;

						}

						if (form.isValid()) {

							var el = win.getEl();

							el.mask("系统处理中,请稍候……");

							var goalP = form.getValues();

							goalP.type = 1;

							Ext.Ajax.request({
								timeout : 6000,

								params : goalP,

								url : 'sumbitAuthentication.do',
								success : function(response, option) {

									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {

										win.hadSaved = true;
										win.close();

										djsuccessmsg(obj.msg);
										
										setTimeout(function() {

											// 刷新
											window.location.reload();

										}, 2000);
										
									

									} else {
										Ext.MessageBox.alert('错误', obj.msg);
									}
									el.unmask();
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