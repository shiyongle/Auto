

Ext.define("DJ.myComponent.other.sharing.SharingContainer", {

	extend : 'Ext.container.Container',

	alias : "widget.sharingcontainer",

	alternateClassName : "SharingContainer",

	statics : {

		/**
		 * 
		 * @param {}
		 *            content,内容
		 * @param {}
		 *            imgUrlRelative，相对图片路径
		 */
		setSharingContent : function(content, imgUrlRelative) {

			// 分享赋值

			window._bd_share_config = {};

			_bd_share_config.bdText = content;

			var baseUrl = location.href;

//			var pathT = baseUrl.substring(0, baseUrl.indexOf("vmi") - 1);

			_bd_share_config.bdPic = IndexMessageRel.baseAppUrl + imgUrlRelative;

		}

	},

	height : 100,
	html : '<iframe src="'+IndexMessageRel.projectBasePath+'js/myComponent/other/sharing/index.html" frameborder="0"  width="700" height="100"></iframe>',

//	width : 700,
	resizable : false,

	initComponent : function() {

		var me = this;

		me.callParent(arguments);
	}

});