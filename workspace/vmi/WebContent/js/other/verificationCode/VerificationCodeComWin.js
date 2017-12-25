Ext.require("DJ.other.verificationCode.VerificationCodeCom");

Ext.define('DJ.other.verificationCode.VerificationCodeComWin', {
	extend : 'Ext.window.Window',

	height : 40,
	width : 373,
	// title : '验证码',

	closeAction : 'hide',

	// border : false,
	//
	// frameHeader : false,
	header : false,
	//	
	// style : {
	//
	// backgroundColor : 'rgba(255, 255, 255, 0)',
	// boxShadow : 'rgba(244, 147, 147, 0) 0px 0px 6px'
	// },
	//
	// bodyStyle : {
	// backgroundColor : 'rgba(206, 217, 231, 0)'
	// // ,
	// // background : 'rgba(223, 232, 246, 0)'
	// },

	doUnVerfiedAction : function() {

	},

	doActionForVerified : function() {

	},

	closable : false,

	resizable : false,

	layout : {
		align : 'center',
		pack : 'center',
		type : 'vbox'
	},

	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [{
				xtype : 'verificationcodecom',

				doUnVerfiedAction : function() {
					me.doUnVerfiedAction();

					me.show();
				},

				doActionForVerified : function() {
					me.doActionForVerified();

					me.close();
				}

			}]
		});

		me.callParent(arguments);
	}

});