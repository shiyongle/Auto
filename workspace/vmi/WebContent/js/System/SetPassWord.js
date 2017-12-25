Ext.define('DJ.System.SetPassWord', {
	extend : 'Ext.Window',
	id : 'DJ.System.SetPassWord',
	modal : true,
	title : "设置用户密码",
	width : 400,// 230, //Window宽度
	height : 300,// 137, //Window高度
	resizable : false,
	closable : true, // 关闭按钮，默认为true
	items : [ {
		id : 'DJ.System.UserEdit.UserFID',
		xtype : 'textfield',
		fieldLabel : 'FID',
		width : 300,
		hidden : true

	}, {
		id : 'DJ.System.UserEdit.UserName',
		xtype : 'textfield',
		fieldLabel : '用户名称',
		readOnly:true,
		width : 300

	}, {
		id : 'DJ.System.UserEdit.PassWord',
		xtype : 'textfield',
		fieldLabel : '密码',
		allowBlank : false,
		blankText : '用户名不能为空',
		inputType : 'password',
		width : 300

	}, {
		id : 'DJ.System.UserEdit.PassWord1',
		xtype : 'textfield',
		fieldLabel : '重复密码',
		allowBlank : false,
		blankText : '用户名不能为空',
		inputType : 'password',
		width : 300

	} ],
	buttons : [ {
		xtype : "button",
		text : "确定",
		pressed : false,
		handler : SaveData
	}, {
		xtype : "button",
		text : "取消",
		handler : function() {
			var windows = Ext.getCmp("DJ.System.SetPassWord");
			if (windows != null) {
				windows.close();
			}
		}
	} ],
	buttonAlign : "center",
	bodyStyle : "padding-top:5px;padding-left:30px"
});
function SaveData() {
	var Password = Ext.getCmp("DJ.System.UserEdit.PassWord").getValue();
	var Password1 = Ext.getCmp("DJ.System.UserEdit.PassWord1").getValue();
	if (Ext.util.Format.trim(Password) != Ext.util.Format.trim(Password1)) {
		Ext.MessageBox.alert('提示', '两次密码不一致！');
		return;
	}
	var me=Ext.getCmp("DJ.System.SetPassWord");
	var el = me.getEl();
	el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
		url : "SaveSysUserpwd.do",
		params : {
			fid : Ext.getCmp("DJ.System.UserEdit.UserFID").getValue(),
			fpassword : MD5(Password)
		}, // 参数
		success : function(response, option) {
			var obj = Ext.decode(response.responseText);
			if (obj.success == true) {
				Ext.MessageBox.alert('成功', obj.msg);
				Ext.getCmp("DJ.System.SetPassWord").close();
			} else {
				Ext.MessageBox.alert('错误', obj.msg);
			}
			el.unmask();
		}
	});
}