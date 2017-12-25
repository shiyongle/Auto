/**
 * 
 * 
 * @author ZJZ（447338871@qq.com）
 * 
 * 
 */
Ext.define("DJ.myComponent.img.jqueryImgCarousel.JqueryImgCarouselContainer", {

	extend : 'Ext.container.Container',

	alias : "widget.jqueryimgcarouselcontainer",

	alternateClassName : 'JqueryImgCarouselContainer',

	statics : {

		showContentView : function(fid, fpath) {

			if (!Ext.isEmpty(fpath)) {
			
				Ext.Ajax.request({
				timeout : 6000,

				url : fpath,
				callback : function(options, success, response) {

					showNewView(success, fpath);

				}
			});
				
			} else {
			
				
				showNewView(false);
			}
			
			function showNewView(isImgAvaliable, fpath) {
			
				var objT = {
				
					isImgAvaliable : isImgAvaliable
					
				};
				
				if (isImgAvaliable) {
				
					objT.imgPath = fpath;
					
				}
				
				var newsView = Ext.create("DJ.other.news.NewsViewer",objT);

					newsView.seteditstate("edit");
		
					newsView.loadfields(fid);
					
					newsView.show();
				
			}
			
				

		},
		loadListStore : function() {

			if (Ext.getCmp("DJ.other.news.NewsList.NewsListGridList")) {

				Ext.getCmp("DJ.other.news.NewsList.NewsListGridList")
						.getStore().load();

			}

		}

	},

	height : 450,
	html : '<iframe src="js/myComponent/img/jqueryImgCarousel/index.html" frameborder="0"  width="750" height="430"></iframe>',

	width : 720,
	// resizable : false,

	initComponent : function() {

		var me = this;
		//
		// var bodyT = Ext.query("body")[0];
		//
		// var t = new Ext.Template('<input id =
		// "jqueryImgCarouselContainerValue" type
		// = "hidden" value="{idValue}" >');
		//
		// t.compile();
		//
		// t.insertFirst(bodyT, {
		// idValue : me.id
		// });

		me.callParent(arguments);
	}

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
// items : [{
// xtype : "jqueryimgcarouselcontainer"
// }]
//
// }).show();
