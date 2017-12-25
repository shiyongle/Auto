Ext.require(["DJ.myComponent.img.jqueryImgCarousel.JqueryImgCarouselContainer",
		"DJ.tools.common.MyCommonToolsZ"]);

Ext.define('DJ.other.news.indexForTest', {
	extend : 'Ext.panel.Panel',

	id : 'DJ.other.news.indexForTest',

	// frame : true,

	title : "在线留言",
	closable : true,
	layout : {
		align : 'stretch',
		type : 'vbox'
	

	},
	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [

			{
				flex : 1,
				
//					height:'100%',
					autoScroll : true,
					html : '<iframe src="'+IndexMessageRel.projectBasePath+'js/myComponent/img/jqueryMessageBoard/index.html" frameborder="0"  width="100%" height="100%"></iframe>'
				

			}]
		});

		me.callParent(arguments);
	}

});
