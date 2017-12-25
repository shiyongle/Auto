Ext.define('DJ.System.Customer.SubRoleEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.System.Customer.SubRoleEdit",
	modal : true,
	title : "客户角色管理编辑界面",
	width : 350,// 230, //Window宽度
	height : 250,// 137, //Window高度
	resizable : false,
	url : 'saveSubRole.do',
	infourl : 'getSubRoleInfo.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'getSubRoleInfo.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true
	items : [{
				baseCls : 'x-plain',
				items : [{
					name:'fid',
					fieldLabel : 'FID',
					xtype : 'textfield',
					hidden : true
				}, {
					xtype : 'textfield',
					name:'fnumber',
					fieldLabel : '编码',
					labelWidth : 80,
					allowBlank : false,
					blankText : '编码不能为空',
					regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符"
				}, {
					name:'fname',
					xtype : 'textfield',
					fieldLabel : '角色名称',
					labelWidth : 80,
					allowBlank : false,
					blankText : '角色名不能为空',
					regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符"

				}, {
					fieldLabel : '描述',
					xtype : 'textareafield',
					name:'fdescription',
					labelWidth : 80,
					maxLength : 80,
					maxLengthText : '输入内容过长',
					regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					regexText : "不能包含特殊字符",
					height : 100
						}]
			}],
	bodyStyle : "padding-top:5px;padding-left:30px"
});




