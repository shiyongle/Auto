/**
 * 前端校验组件
 * 
 * @author ZJZ（447338871@qq.com）
 * 
 * @version 0.1
 * @version 0.1.1 2015-3-21 上午9:12:11,根据服务端状态校验控制前端显示
 * 
 */
Ext.define('DJ.other.verificationCode.VerificationCodeCom', {
	extend : 'Ext.container.Container',

	alias : 'widget.verificationcodecom',

	requires : ['DJ.tools.common.MyCommonToolsZ'],

	height : 26,
	width : 373,
	layout : {
		type : 'hbox'
	},

	hidden : true,

	doUnVerfiedAction : function() {

	},

	doActionForVerified : function() {

	},

//	vCTextfieldLabel : '<font size="3px">验证码</font>',
	
	vCTextfieldWidth : 145,
	
	// 超时时间
	verifieTimeInterval : 1000 * 60 * 5,

	// verifieTimeInterval : 1000 * 5,

	// 验证长度
	vCLength : 4,

	task : null,

	// 用下面方法修改，不要直接修改
	isVerified : true,

	setIsVerified : function(state) {

		var me = this;

		me.isVerified = state;

		if (state) {

			me.reloadVCImg();

			me.doActionForVerified();

			me.hide();

		} else {

			me.doUnVerfiedAction();

			me.show();

			me.down('textfield').focus(true);
			
			me.task.cancel();

			me.task.delay(me.verifieTimeInterval);
		}

	},

	reloadVCImg: function(){
	
		var me = this;
		
		var imgC = me.down('image');
		
		imgC.setSrc(me.self.IMG_PATH + "?" + Math.random());
		
		me.down('textfield').focus(true);
		
	},
	
	/**
	 * 根据服务端的验证状态更改前端的状态
	 */
	gainRemoteVCStateAndSetPreView : function() {

		var me = this;

		Ext.Ajax.request({
			timeout : 6000,

			url : "gainValidateVCodeState.do",
			success : function(response, option) {

				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {

					if (obj.data.isValidate.toString() == 'true') {

						me.setIsVerified(true);

					} else {

						me.setIsVerified(false);

					}

				} else {
					Ext.MessageBox.alert('错误', obj.msg);
				}

			}
		});

	},

	statics : {

		IMG_PATH : 'gainVCImg.do'

	},

	initComponent : function() {
		var me = this;

		me.task = new Ext.util.DelayedTask(function() {
			me.setIsVerified(true);
		});

		MyCommonToolsZ.setComAfterrender(me, function() {

			(function setImgClickToChange() {

				var imgC = me.down('image');

				imgC.getEl().on('click', function() {

					me.reloadVCImg();

				});

			})();
			
			/**
			 * 根据服务端状态调整一次
			 */
			me.gainRemoteVCStateAndSetPreView();

		});

		Ext.applyIf(me, {
			items : [{
				xtype : 'textfield',
//				fieldLabel : ' ',
//				labelWidth : 50,
				maxLength : me.vCLength,

				emptyText : '请输入验证码',
				
				enforceMaxLength : true,
				
				width : me.vCTextfieldWidth,
				
				listeners : {

					change : function(com, newValue, oldValue, eOpts) {

						if (newValue.length == com.up('verificationcodecom').vCLength) {

							Ext.Ajax.request({
								timeout : 6000,

								params : {
									vCode : newValue
								},

								url : "validateVCode.do",
								success : function(response, option) {

									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {

										if (obj.data.isValidate.toString() == 'true') {

											com.up('verificationcodecom')
													.setIsVerified(true);

										} else {

											com.up('verificationcodecom')
													.setIsVerified(false);

										}

									} else {
										Ext.MessageBox.alert('错误', obj.msg);
									}

								}
							});

						}

					}

				}

			},
					// {
					// xtype : 'displayfield',
					// itemId : 'displayfield',
					// html : '<font color = "green">√</font>'
					//				
					// },
					{
						xtype : 'image',
						src : me.self.IMG_PATH,
						height : 26,
						width : 80
					// ,
					// flex : 1
					}
//					, {
//						xtype : 'button',
//						itemId : 'buttonChangeVc',
//						text : '看不清，换一个',
//						handler : function() {
//
//							var sme = this;
//
//							var imgC = sme.previousSibling("image");
//
//							imgC.setSrc(me.self.IMG_PATH + "?" + Math.random());
//
//						}
//					}
					]
		});

		me.callParent(arguments);
	}

});