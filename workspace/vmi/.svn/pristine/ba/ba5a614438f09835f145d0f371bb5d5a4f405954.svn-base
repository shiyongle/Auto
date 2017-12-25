Ext.apply(Ext.form.VTypes, {
                password : function(val, field) {
                    if (field.initialPassField) {
                        var pwd = Ext.getCmp(field.initialPassField);
                        return (val == pwd.getValue());
                    }
                    return true;
                },
                passwordText : '两次输入的密码不一致!'
          });
  
Ext.define('DJ.System.Customer.SubUserEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.System.Customer.SubUserEdit",
	modal : true,
	onload : function() {
		
	},
	title : "子用户管理编辑界面",
	width : 400,
	height : 220,
	resizable : false,
	url : 'SaveSubUser.do',
	infourl : 'getSubUserInfo.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'getSubUserInfo.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true
	items : [{
				baseCls : 'x-plain',
				items : [

				{
//							id : 'DJ.System.Customer.SubUserEdit.UserFID',
							xtype : 'textfield',
							name : 'fid',
							fieldLabel : 'FID',
							width : 300,
							hidden : true

						}, {
//							id : 'DJ.System.Customer.SubUserEdit.UserName',
							xtype : 'textfield',
							name : 'fname',
							fieldLabel : '用户名称',
							allowBlank : false,
							blankText : '用户名不能为空',
							regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
							regexText : "不能包含特殊字符",
							width : 300

						}, {
//							id : 'DJ.System.Customer.SubUserEdit.PassWord',
							id:'fpassword',
							name : 'fpassword',
							xtype : 'textfield',
							fieldLabel : '密码',
							allowBlank : false,
							blankText : '密码不能为空',
							inputType : 'password',
							hidden : true,
							width : 300

						}, {
//							id : 'DJ.System.Customer.SubUserEdit.PassWord1',
							itemId:'password1',
							xtype : 'textfield',
							fieldLabel : '重复密码',
						    vtype : 'password',
                            initialPassField : 'fpassword',
							allowBlank : false,
							blankText : '请再次输入密码',
							inputType : 'password',
							submitValue:false,
							hidden : true,
							width : 300

						}, {
//							id : 'DJ.System.Customer.SubUserEdit.Email',
							name : 'femail',
							xtype : 'textfield',
							fieldLabel : '邮箱',
							regex : /^(\w|[.])*@(\w|[@.])*\.(\w|[.])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
							regexText : "不能包含特殊字符",
							width : 300

						}, {
//							id : 'DJ.System.Customer.SubUserEdit.Tel',
							name : 'ftel',
							xtype : 'textfield',
//							allowBlank : false,
//							blankText : '手机号不能为空',
							fieldLabel : '手机',
							regex : /^(1[0-9][0-9]|15[0|3|6|7|8|9]|18[0|8|9])\d{8}$/,// /^[^,\!@#$%^&*()_+}]*$/,
							regexText : "你输入的不是手机号",
							width : 300
						},
						 {
//							id : 'DJ.System.Customer.SubUserEdit.CreateTime',
							name : 'fcreatetime',
							xtype : 'textfield',
							hidden : true,
							width : 300
						 }]
			}],
	bodyStyle : "padding-top:5px;padding-left:30px"
});

