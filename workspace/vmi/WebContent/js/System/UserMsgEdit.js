Ext.define('DJ.System.UserMsgEdit', {
			extend : 'Ext.panel.Panel',
			id : 'DJ.System.UserMsgEdit',
			title : "用户信息编辑",
			closable : true, 
			listeners:{
				afterrender:function(){
					Ext.Ajax.request({
						 url : "getolduserMSG.do",
						 success : function(response, option) {
								var obj = Ext.decode(response.responseText);
								var femail = obj.data[0].femail;
								var ftel = obj.data[0].ftel;
								Ext.getCmp('DJ.System.UserMsgEdit.mail').setValue(femail);
								Ext.getCmp('DJ.System.UserMsgEdit.tel').setValue(ftel);
							}
					 });
				}
			},
			items : [{
						id : 'DJ.System.UserMsgEdit.mail',
						xtype : 'textfield',
						fieldLabel : '邮    箱',
						allowBlank : true,
						width : 300
					}, {
						id : 'DJ.System.UserMsgEdit.tel',
						xtype : 'textfield',
						fieldLabel : '手    机',
						allowBlank : true,
						width : 300

					}, {
						xtype : "button",
						text : "确定",
						width : 55,
						pressed : false,
						margin : '0 0 0 245',
						handler : SaveMsg
					}],
			bodyStyle : "padding-top:5px;padding-left:30px"
		});
function SaveMsg(){
	var userMail = Ext.getCmp('DJ.System.UserMsgEdit.mail').getValue();
	var userTel = Ext.getCmp('DJ.System.UserMsgEdit.tel').getValue();
	var filterforMail  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	 if(!Ext.isEmpty(userMail)){
		 if (!filterforMail.test(userMail)){
			 Ext.MessageBox.alert('提示', '邮箱格式不正确！');
			return; 
		 } 
	 }
	 if(!Ext.isEmpty(userTel)){
		 if(!(/^1[3|4|5|8]\d{9}$/.test(userTel))&&!(/^(\d{3,4}(\-)?)?\d{7,8}$/).test(userTel)){
			 Ext.MessageBox.alert('提示', '联系方式格式不正确！');
				return;
		 } 
	 }
	 
	 var me=Ext.getCmp("DJ.System.UserMsgEdit");
	 var el = me.getEl();
	 el.mask("系统处理中,请稍候……");
	 Ext.Ajax.request({
		 url : "ChangeSysUserMsg.do",
		 params : {
			 userMail : userMail,
			 userTel : userTel
		 },
		 success : function(response, option) {
				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {
					Ext.MessageBox.alert('成功', obj.msg);
					Ext.getCmp("DJ.System.UserMsgEdit").close();
				} else {
					Ext.MessageBox.alert('错误', obj.msg);
				}
				el.unmask();
			}
	 });
}