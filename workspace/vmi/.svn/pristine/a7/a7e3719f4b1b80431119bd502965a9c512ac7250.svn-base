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
  
Ext.define('DJ.System.UserEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.System.UserEdit",
	modal : true,
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		
	},
	title : "用户管理编辑界面",
	width : 400,// 230, //Window宽度
	minHeight : 300,// 137, //Window高度
	resizable : false,
	url : 'SaveSysUser.do',
	infourl : 'GetUserInfo.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'GetUserInfo.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true
	Action_BeforeSubmit:function(){
		
		
		var me = this;
		
		if (!me.down('form').isValid( )){
			
			Ext.Msg.alert("提示", "数据有误！");
			return ;
			
		}
		
		var pw = Ext.getCmp('fpassword'),
			pw1 = pw.up('form').down('#password1'),
			password;
		if(pw.getValue()!=''){
			password = MD5(pw.getValue());
			pw.setValue(password);
			pw1.setValue(password);
		}
	},
	items : [{
				baseCls : 'x-plain',
				items : [

				{
							xtype : 'textfield',
							name : 'fid',
							fieldLabel : 'FID',
							width : 300,
							hidden : true

						},{
							xtype : 'textfield',
							name : 'fimid',
							hidden : true
						},{
							xtype : 'textfield',
							name : 'feffect',
							hidden : true
						},{
							xtype : 'textfield',
							name : 'fstate',
							hidden : true

						}, {
//							id : 'DJ.System.UserEdit.UserName',
							xtype : 'textfield',
							name : 'fname',
							fieldLabel : '用户名称',
							allowBlank : false,
							blankText : '用户名不能为空',
							regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
							regexText : "不能包含特殊字符",
							width : 300

						}, {
//							id : 'DJ.System.UserEdit.PassWord',
							id:'fpassword',
							name : 'fpassword',
							xtype : 'textfield',
							fieldLabel : '密码',
//							allowBlank : false,
							blankText : '密码不能为空',
							inputType : 'password',
							hidden : true,
							width : 300

						}, {
//							id : 'DJ.System.UserEdit.PassWord1',
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
//							id : 'DJ.System.UserEdit.CustomerName',
							name : 'fcustomername',
							xtype : 'textfield',
							fieldLabel : '客户名称',
							allowBlank : false,
							blankText : '客户名称不能为空',
							regex : /^([\u4E00-\u9FA5]|\w|[@.])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
							regexText : "不能包含特殊字符",
							width : 300

						}, {
//							id : 'DJ.System.UserEdit.Email',
							name : 'femail',
							xtype : 'textfield',
//							allowBlank : false,
//							blankText : '邮箱不能为空',
							fieldLabel : '邮箱',
							regex : /^(\w|[.])*@(\w|[@.])*\.(\w|[.])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
							regexText : "不能包含特殊字符",
							width : 300

						}, {
//							id : 'DJ.System.UserEdit.Tel',
							name : 'ftel',
							xtype : 'textfield',
							/*allowBlank : false,
							blankText : '手机号不能为空',*/
							fieldLabel : '手机',
							regex : /^(1[0-9][0-9]|15[0|3|6|7|8|9]|18[0|8|9])\d{8}$/,// /^[^,\!@#$%^&*()_+}]*$/,
							regexText : "你输入的不是手机号",
							width : 300
						},{
							name: 'fphone',
							xtype: 'textfield',
							fieldLabel: '电话',
							width: 300
						},{
							name: 'ffax',
							xtype: 'textfield',
							fieldLabel: '传真',
							width: 300
						},{
							name: 'fqq',
							xtype: 'textfield',
							fieldLabel: 'QQ',
							regex :/^[1-9][0-9]{4,}$/,
							regexText : 'QQ格式不正确',
							width: 300
						},
						 {
//							id : 'DJ.System.UserEdit.CreateTime',
							name : 'fcreatetime',
							xtype : 'textfield',
							hidden : true,
							width : 300
						 },{
			    	        	  xtype:'checkbox',
			    	        	  name:'fisfilter',
			    	        	  fieldLabel : '查看所有客户',
			    	        	  inputValue:'1',
			    	        	  uncheckedValue :'0'
//			    	        	  boxLabel:  '按单价设',
			    	      },{
			    	        	  xtype:'checkbox',
			    	        	  name:'fisreadonly',
			    	        	  fieldLabel : '只看自己帐号',
			    	        	  inputValue:'1',
			    	        	  uncheckedValue :'0'
						 },{
							width : 300,
							name : 'ftype',
							xtype : 'combo',
							fieldLabel : '用户类型',
							store : Ext.create('Ext.data.Store', {
								fields : ['typevalue', 'typename'],
								data : [{
									"typevalue" : "0",
									"typename" : "客户"
								}, {
									"typevalue" : "1",
									"typename" : "制造商"
								}, {
									"typevalue" : "2",
									"typename" : "平台"
								}, {
									"typevalue" : "3",
									"typename" : "设计师"
								}]
							}),
							displayField : 'typename', // 这个是设置下拉框中显示的值
							valueField : 'typevalue', // 这个可以传到后台的值
							allowBlank : false,
							blankText : '请选择结算类型',
							triggerAction : 'all',
							editable : false,
							value:"0"
						},{
							 name:'fcustomerid',
							 width:300,
							 xtype:'cCombobox',
							 fieldLabel:'管理子账号',
							 valueField:'fid',
							 displayField:'fname',
							 MyConfig:{
								 width:600,
								 height:200,
								 url:'GetCustomerList.do',
								 fields : [ {
		    	 						name : 'fid'
		    	 					}, {
		    	 						name : 'fname',
		    	 						myfilterfield : 't_bd_customer.fname', // 查找字段，发送到服务端
		    	 						myfiltername : '客户名称',// 在过滤下拉框中显示的名称
		    	 						myfilterable : true// 该字段是否查找字段
		    	 					}, {
		    	 						name : 'fnumber'
		    	 					}, {
		    	 						name : 'findustryid'
		    	 					}, {
		    	 						name : 'faddress'
		    	 					}],
		    	 					columns : [ {
		    	 						text : 'fid',
		    							dataIndex : 'fid',
		    							hidden : true,
		    							sortable : true
		    						}, {
		    							text : '编码',
		    							dataIndex : 'fnumber',
		    							sortable : true
		    						}, {
		    							text : '客户名称',
		    							dataIndex : 'fname',
		    							sortable : true
		    						}, {
		    							text : '行业',
		    							dataIndex : 'findustryid',
		    							sortable : true
		    						}, {
		    							text : '地址',
		    							dataIndex : 'faddress',
		    							sortable : true,
		    							width : 250
		    						}]
							 }
						 }]
			}],
	bodyStyle : "padding-top:5px;padding-left:30px"
});

