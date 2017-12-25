Ext.define('DJ.myComponent.form.ImgUploadAndshower', {
	extend : 'Ext.container.Container',
	alias : 'widget.imguploadandshower',

	statics : {

		imgUploadLableHtml : '证件需要清晰有效<br/>仅支持JPG、GIF、PNG图片文件，且需小于2M'

	},

	uploadUrl : '',

	doChange : Ext.emptyFn,

	imgFieldName : 'imgUrl',

	params : {},

	imgLableHtml : '*',

	requires : ['Ext.Img', 'Ext.form.Label', 'Ext.button.Button'],

	height : 200,
	width : 300,

	layout : {
		type : 'vbox',
		align : 'center',
		defaultMargins : {
			top : 5,
			right : 0,
			bottom : 5,
			left : 0
		}
	},

	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [{
				xtype : 'image',
				flex : 1,
				alt : '不可用',
//				height : me.height - 20,
				width : '98%',
				// style : {
				// maxWidth : '98%'
				// },
				listeners : {

					afterrender : function(com, eOpts) {

						var el = com.getEl();

						el.on('click', function() {

							open(com.src);

						});

					}

				}
			}, {
				xtype : 'label',
				// flex: 0,
				// text : me.imgLable
				html : me.imgLableHtml
			}, {
				xtype : 'oneclickfilefieldformulitichoice',
				text : '上传',
				url : me.uploadUrl,
				doChange : me.doChange,
				params : me.params,
				noStore : true,

				submitValue : false,
				
				fileType : ['jpg', 'jpeg', 'png', 'gif'],

				showImgTip : false,

				mulitiChoice : false,

				afterSuccessCallback : function(datas) {

					var mee = this;

					if (Ext.isEmpty(datas)) {

						return;

					}

					var url = datas[0][me.imgFieldName];

					var imgF = mee.previousNode('image');

					imgF.setSrc(url);

				}
			}]
		});
		me.callParent(arguments);
	}

});