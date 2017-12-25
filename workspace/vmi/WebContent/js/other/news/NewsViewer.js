Ext.require(["DJ.myComponent.other.sharing.SharingContainer",
		'DJ.tools.common.MyCommonToolsZ']);

Ext.define('DJ.other.news.NewsViewer', {
	// extend: 'Ext.window.Window',

	extend : 'Ext.c.BaseEditUI',
	id : "DJ.other.news.NewsViewer",

	modal : true,

	height : 590,// 长度不变
	width : 480,
	// title: '新闻',
	infourl : 'gainNewsById.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'gainNewsById.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true

	resizable : false,

	frame : false,

	isImgAvaliable : false,

	imgPath : '',
	border : false,

	frameHeader : false,
	header : false,

	style : {

		backgroundColor : 'rgba(255, 255, 255, 0)'
	},

	bodyStyle : {
		backgroundColor : 'rgba(255, 255, 255, 0)'
	},

	onload : function() {
		var me = this;

		MyCommonToolsZ.setComAfterrender(me, function(com, eOpts) {

			// 隐藏按钮、组件
			Ext.getCmp("DJ.other.news.NewsViewer.savebutton").hide();

			Ext.getCmp("DJ.other.news.NewsViewer.closebutton").hide();

			Ext.getCmp("DJ.other.news.NewsViewer").down("toolbar").hide();

		});

	},

	onloadfields : function() {

		var me = this;

		var doImgViewAction = function() {

			var path = me.down("hidden[name=fpath]").getValue();

			var imgT = me.down("image");

			if (me.isImgAvaliable) {
				

				imgT.setSrc(path);
				// otherAction();

			} else {
				// 如果找不到图片就隐藏和拓展内容域

				// 隐藏按钮、组件
				var hT = imgT.getHeight();

				imgT.hide();

				var fcontentF = me.down("form").getForm().findField('fcontent');

				fcontentF.setHeight(fcontentF.getHeight() + hT);

			}

		}

		doImgViewAction();

		// MyCommonToolsZ.setComAfterrender(me, function(com, eOpts) {
		//
		// // otherAction();
		// });

		var otherAction = function() {

			// 设置fileds不可编辑

			var names = ['ftitle', 'fcreatetime', 'fcontent'];

			Ext.each(names, function(ele, index, all) {

				me.down("form").getForm().findField(ele).setReadOnly(true);
				
				if (ele == 'ftitle') {
				
					var textF = me.down("form").getForm().findField('ftitle');
					
//					MyCommonToolsZ.setComAfterrender(textF, function(){
					
						MyCommonToolsZ.setToolTipZ(textF.id, textF.getValue()); 
						
//						textF.inputEl .set( 'title', textF.getValue() );
						
//					});
					
						
					
				}

			});

			// 设置content换行

			var tedV = me.down("form").getForm().findField('fcontent')
					.getValue().replace(/<br\/>/g, String.fromCharCode(10));

			me.down("form").getForm().findField('fcontent').setValue(tedV);

			// 分享赋值

			window._bd_share_config = {};

			_bd_share_config.bdText = me.down("form").getForm()
					.findField("ftitle").getValue()
					+ "\n"
					+ me.down("form").getForm().findField("fcontent")
							.getValue();

//			var baseUrl = location.href;
//
//			var pathT = baseUrl.substring(0, baseUrl.indexOf("vmi") - 1);

			_bd_share_config.bdPic = IndexMessageRel.baseAppUrl + me.imgPath;
			
//			_bd_share_config.bdUrl = '';

		}
		otherAction();
	},

	items : [

	{

		dock : 'top',

		layout : {
			align : 'stretch',
			padding : "0 10 10 10",
			type : 'vbox'
		},
		items : [

		{
			name : 'fid',
			xtype : 'hidden'
		}, {

			name : 'fpath',
			xtype : 'hidden'

		}, {
			xtype : 'container',
			// flex : 1,
			layout : {
				type : 'hbox',
				align : 'stretch'
			},
			height : 50,
			defaults : {

				border : false

			},
			items : [{
				name : "ftitle",
				xtype : 'textfield',
				border : false,
				fieldStyle : 'font-size : 20pt;font-weight:bold; text-align:center;border-width:0;background-image:url();',
				// heigth : 50,
				style : {
					'class' : 'tnull'
				},
				flex : 2
			}]
		}, {
			name : "fcreatetime",
			xtype : 'textfield',
			border : false,
			fieldStyle : 'font-size : 5pt;font-style:italic;text-align:right;border-width:0;background-image:url();'
				// ,
				// heigth : 10,
				// flex : 1
		}, {
			xtype : 'container',
			// width : 392,
			layout : {
				align : 'stretch',
				type : 'vbox'
			},
			defaults : {

				border : false

			},
			items : [{
				name : "imageShower",
				xtype : 'image',
				border : false,
				height : 500 / 16 * 8,
				// width : 500,
				border : false
			}, {
				name : "fcontent",
				xtype : 'textareafield',
				// border : false,
				// xtype : 'textfield',
				// enterIsSpecial : true,

				fieldStyle : "font-size : 15pt;font-family:'楷体_GB2312';border-width:0;background-image:url();",

				style : {

					'class' : "newsContent"

				},
				// height : 500 / 16 * 9
				height : 150
			}]
		}, {
			xtype : 'container',

			layout : {
//				align : 'bottom',
				type : 'hbox'
			},

			height : 100,

			items : [

			{
				xtype : 'sharingcontainer',

				// ,
				flex : 5
			}, {

				xtype : 'container',
				margin : "25 0 0 0",
				html : '<a href="javascript:Ext.getCmp(\'DJ.other.news.NewsViewer\').close()">关闭</a>'
////			height : 20,
//				text : '关闭',
//				flex : 1,
//				handler : function() {
//
//					this.up("window").close();
//
//				}

			}

			]

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