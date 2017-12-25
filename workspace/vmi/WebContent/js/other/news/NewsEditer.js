Ext.require(["DJ.tools.common.MyCommonToolsZ"]);

Ext.define('DJ.other.news.NewsEditer', {
	// extend: 'Ext.window.Window',

	extend : 'Ext.c.BaseEditUI',
	id : "DJ.other.news.NewsEditer",

	onSuccess : function() {
	},
	
	modal : false,

	height : 272,
	width : 540,
	// title: '新闻',
	// url : 'saveVmiNew.do',
	// infourl : 'gainNewsById.do', //
	// 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	// viewurl : 'gainNewsById.do', // 查看状态数据源
	// closable : true, // 关闭按钮，默认为true

//	resizable : true,
	
	onShow : function () {
	
		Ext.getCmp("DJ.other.news.NewsEditer.savebutton").hide();

		Ext.getCmp("DJ.other.news.NewsEditer.closebutton").hide();
		//		
		Ext.getCmp("DJ.other.news.NewsEditer").down("toolbar").hide();
		
		
	},
	
	onloadfields : function() {

		
		
		
//		MyCommonToolsZ.setComAfterrender(this, function () {
//		
//			// //隐藏按钮、组件
//	
//			
//		} );
		
		
		//		
		// //设置图片
		// var me = this;
		//
		// var path = me.down("hidden[name=fpath]").getValue();
		//
		// me.down("image").setSrc(path);
		//		
		// //设置fileds不可编辑
		//
		// var names = ['ftitle','fcreatetime','fcontent'];
		//		 
		// Ext.each(names, function (ele, index, all) {
		//		 
		// me.down("form").getForm().findField( ele ).setReadOnly( true );
		//		 	
		// } );

	},
	items : [{
		xtype : 'form',
		dock : 'top',
//		height : 220,
//		width : 530,
		// resizable : true,
		// margin : 10,
		// padding : 50,
		layout : {
			align : 'stretch',
			padding : "10 10 10 0",
			type : 'vbox'
		},
		buttons : [{
			text : '上传',
			handler : function() {
				var form = this.up('form').getForm();

				// 构建参数，表单形式上传，据测试无法用配置式传参。用url构建式可以
				var ftitle = form.findField('ftitle').getValue();
				var fcontent = form.findField('fcontent').getValue();

				var objT = {

					ftitle : ftitle,
					fcontent : fcontent

				};

//				var paramsT = MyCommonToolsZ.buildGetParams(objT)

				if (form.isValid()) {
					form.submit({
						url : 'saveVmiNew.do',

						 params: objT,
						
						timeout : 5000,
						waitMsg : '正在保存文件...',
						success : function(fp, o) {
							
							djsuccessmsg("成功");
//							Ext.getCmp("DJ.other.news.NewsEditer").close();
							Ext.getCmp("DJ.other.news.NewsEditer").onSuccess();
							
							//不知何故，要第二次设置一次才能使得第二次上传成功
							Ext.getCmp("DJ.other.news.NewsEditer")
									.down("filefield").setDisabled(Ext
											.getCmp("DJ.other.news.NewsEditer")
											.down("checkbox").getValue());
						
						
						},
						failure : function(fp, o) {
							Ext.Msg.show({
								title : '失败',
								msg : o.result.msg,
								minWidth : 200,
								modal : true,
								buttons : Ext.Msg.OK
							})
//							form.findField('fileName').setRawValue('');
						}
					});
				}
			}
		}],
		items : [

		{
			fieldLabel : '标题',
			name : "ftitle",
			xtype : 'textfield'

		}, {
			xtype : 'container',
			// width : 392,
			layout : {
				align : 'stretch',
				type : 'vbox'
			},
			items : [{
				fieldLabel : '图片',
				name : "imageFile",
				xtype : 'filefield',
				anchor : '100%',
				buttonText : '选择',
				regex : /(.)+((\.jpeg)|(\.jpg)|(\.bmp)|(\.png)|(\.gif)(\w)?)$/i,
				regexText : "图片格式选择不正确"

			}, {
				fieldLabel : '不传图片？',
//				name : "fcontent",
				xtype : 'checkboxfield',
				submitValue : false,
				handler : function (com, checked) {
				
					com.previousNode("filefield").setDisabled(checked);
					
				}
//				height : 100
			}, {
				fieldLabel : '内容',
				name : "fcontent",
				xtype : 'textareafield',
				
				height : 100
			}]
		}]
	}]

// initComponent: function() {
		// var me = this;
		//
		// Ext.applyIf(me, {});
		//
		// me.callParent(arguments);
		// }

		});



// Ext.create("Ext.window.Window", {
//
// height : 600,
//
// resizable : true,
//
// modal : true,
//
// width : 792,
// items : [
// Ext.create("DJ.other.news.NewsEditer")
// ]
//
// }).show();
