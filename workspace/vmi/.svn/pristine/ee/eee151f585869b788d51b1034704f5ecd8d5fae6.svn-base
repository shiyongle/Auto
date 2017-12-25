Ext.define('DJ.System.ChangePassWord', {
			extend : 'Ext.panel.Panel',
			id : 'DJ.System.ChangePassWord',
			title : "修改密码",
			closable : true, // 关闭按钮，默认为true
			items : [{
						id : 'DJ.System.ChangePassWord.OldPassWord',
						xtype : 'textfield',
						fieldLabel : '原    密   码',
						allowBlank : false,
						blankText : '密码不能为空',
						inputType : 'password',
						width : 300

					}, {
						id : 'DJ.System.ChangePassWord.PassWord',
						xtype : 'textfield',
						fieldLabel : '新    密    码',
						allowBlank : false,
						blankText : '密码不能为空',
						inputType : 'password',
						width : 300

					}, {
						id : 'DJ.System.ChangePassWord.PassWord1',
						xtype : 'textfield',
						fieldLabel : '重复密码',
						allowBlank : false,
						blankText : '密码不能为空',
						inputType : 'password',
						width : 300

					}, {
						xtype : "button",
						text : "确定",
						width : 55,
						pressed : false,
						margin : '0 0 0 245',
						handler : SaveData
					}],
			bodyStyle : "padding-top:5px;padding-left:30px"
		});
function SaveData() {
	var Password = Ext.getCmp("DJ.System.ChangePassWord.PassWord").getValue();
	var Password1 = Ext.getCmp("DJ.System.ChangePassWord.PassWord1").getValue();
	if (Ext.util.Format.trim(Password) != Ext.util.Format.trim(Password1)) {
		Ext.MessageBox.alert('提示', '两次密码不一致！');
		return;
	}
	var me=Ext.getCmp("DJ.System.ChangePassWord");
	var el = me.getEl();
					el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
		url : "ChangeSysUserpwd.do",
		params : {
			foldpassword : MD5(Ext
					.getCmp("DJ.System.ChangePassWord.OldPassWord").getValue()),
			fpassword : MD5(Password)
		}, // 参数
		success : function(response, option) {
			var obj = Ext.decode(response.responseText);
			if (obj.success == true) {
				Ext.MessageBox.alert('成功', obj.msg);
				Ext.getCmp("DJ.System.ChangePassWord").close();
			} else {
				Ext.MessageBox.alert('错误', obj.msg);
			}
			el.unmask();
		}
	});
}