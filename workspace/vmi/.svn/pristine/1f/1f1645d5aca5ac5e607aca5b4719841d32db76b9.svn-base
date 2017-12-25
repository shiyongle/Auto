Ext.require(["DJ.tools.common.MyCommonToolsZ","DJ.myComponent.img.jqueryImgCarousel.JqueryImgCarouselContainer"]);

Ext.define("DJ.other.news.NewsListNoLimit", {

	extend : 'Ext.c.GridPanel',
	title : "新闻编辑",

	// alias : 'widget.newslistgridlist',
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	id : 'DJ.other.news.NewsListNoLimit',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'gainVmiNewsNoLimit.do',
	Delurl : "deleteNews.do",

	mixins : ['DJ.tools.grid.MyGridHelper'],

	// EditUI : "DJ.System.AddressEdit",

	// resizable : true,

	// hideHeaders : true,

	onload : function() {
		// 加载后事件，可以设置按钮，控件值等

		var me = this;

		MyCommonToolsZ.setComAfterrender(me, function(com, eOpts) {

			var idsT = ["addbutton", "delbutton", "refreshbutton"];

			Ext.each(idsT, function(ele, index, all) {

				idsT[index] = me.id + "." + ele;

			});

			me._operateButtonsView(true, idsT);

			Ext.getCmp(idsT[0]).setHandler(function() {

				var newsEditer = Ext.create("DJ.other.news.NewsEditer");
  			 	newsEditer.setparent(me.id);
				newsEditer.seteditstate("edit");

				newsEditer.onSuccess = function () {
				
					me.getStore().loadPage(1);
					
				}
				
				newsEditer.show();

			});

		});

	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
	},
	Action_AfterEditButtonClick : function(EditUI) {
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
	},
	Action_AfterDelButtonClick : function(me, record) {
	},
	fields : [{
		name : 'fid'
	}, {
		name : 'fname'
	}, {
		name : 'fcontent'
	}, {
		name : 'fcreaterid'
	}, {
		name : 'fpath'
	}, {
		name : 'ftitle'
	}, {
		name : 'fcreatetime'
	}],
	columns : [{
		xtype : 'rownumberer'
	}, {
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true

	}, {
		'header' : 'fcreaterid',
		'dataIndex' : 'fcreaterid',
		hidden : true,
		hideable : false,
		sortable : true

	}, {
		xtype : "templatecolumn",
		'header' : '标题',
		// 'dataIndex' : 'ftitle',
		tpl : "<a href='javascript:parent.DJ.myComponent.img.jqueryImgCarousel.JqueryImgCarouselContainer.showContentView({fid},  \"{fpath}\")'> {ftitle} </a>",

		width : 350,
		sortable : true
	}, {
		'header' : '内容',
		'dataIndex' : 'fcontent',
		// width : 140,
		flex : 1,
		sortable : true,
		renderer : function(value) {

			return Ext.String.ellipsis(value, 55, true);

		}
	}, {
		'header' : '创建时间',
		'dataIndex' : 'fcreatetime',
		width : 150,
		sortable : true
	}]
});