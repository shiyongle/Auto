Ext.require(["DJ.myComponent.img.jqueryImgCarousel.JqueryImgCarouselContainer",
		"DJ.tools.common.MyCommonToolsZ"]);

Ext.define('DJ.other.news.NewsList', {
	extend : 'Ext.panel.Panel',

	id : 'DJ.other.news.NewsList',

	// frame : true,

//	title : "新闻",
	closable : false,

	// height: 376,
	// width: 689,
	layout : {
		align : 'stretch',
		type : 'vbox'
	// ,
	// regionWeights : {
	//
	// north : -20,
	// south : -10,
	// center : 0,
	// west : 10,
	// east : 20
	//
	// }

	},
	// style : {
	//
	// backgroundColor : 'white'
	//	
	// },
	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [

			{
				layout : {
					align : 'stretch',
					type : 'hbox'
				},
				height : 435,
				items : [{

					// xtype : 'container',
					// layout : {
					// align : 'stretch',
					// type : 'vbox'
					// },
					// items : [{
					// xtype : 'jqueryimgcarouselcontainer',
					// height : 410
					// // flex : 3
					// }],

					xtype : 'jqueryimgcarouselcontainer',
					height : 410,
					// flex : 3

					// region : 'west',
					width : 740
				}, {
					xtype : 'newslistgridlist',
					region : 'center',
					padding : 10,

					flex : 1

				}]

			}, {

				fieldLabel : '帮助',
				labelWidth : 40,
				xtype : 'fieldcontainer',
				flex : 1,
				// padding : 10,
				layout : {

					align : 'middle',
					pack : 'center',
					type : 'hbox',
					defaultMargins : {

						top : 0,
						right : 20,
						bottom : 0,
						left : 20

					}
				// ,
				// padding : 10
				},

				defaults : {
					padding : 10

				},

				items : [{
					xtype : 'button',
					flex : 1,
					// padding : 10,
					text : '在线设计',
							handler : function() {
								
								Ext.Ajax.request({
									timeout : 60000,
									url : "Button/GetMainmenuAndButton.do",
									params:{
									menufurl:"DJ.order.Deliver.FistproductList",
									msg:'您没有设计权限'
//									,buttonaction:"SaveOrupdateFirstproductdemand.do"//保存按钮事件 可选
									},
									success : function(response, option) {
						
										var obj = Ext.decode(response.responseText);
						
										if (obj.success == true) {//成功
											IndexMessageRel.setActionItem(obj.msg, 'button[id=DJ.order.Deliver.FistproductdemandList.addbutton]');

										}else
										{
//											Ext.MessageBox.alert("提示",obj.msg);
											djsuccessmsg(obj.msg);
										}
						
									}
								});								
							}
				},
						// {
						// xtype : 'tbspacer',
						// flex : 1
						// },
						{
							xtype : 'button',
							flex : 1,
							// padding : 10,
							text : '在线下单',
							handler : function() {

								Ext.Ajax.request({
									timeout : 60000,
									url : "Button/GetMainmenuAndButton.do",
									params:{
									menufurl:"DJ.order.Deliver.DeliversCustListPanel,DJ.order.Deliver.DeliversBoardList"//菜单配置项
									},
									success : function(response, option) {
						
										var obj = Ext.decode(response.responseText);
										if (obj.success == true) {//成功
								        	IndexMessageRel.setActionItem(obj.msg, 'button[text=下单]');
										}else
										{
											djsuccessmsg(obj.msg);
										}
						
									}
								});			
								
							}
						}, {
							xtype : 'button',
							tooltip : '如果有任何疑问可以在此留言！',
							flex : 1,
							// padding : 10,
							text : '在线留言', 
							handler : function() {

								IndexMessageRel.setActionItem("DJ.other.news.indexForTest");
								
							}
						}
				// , {
				// xtype : 'displayfield',
				// flex : 1,
				// rtl : true,
				// // html : "<a>帮助</a>"
				// // ,
				// padding : 10,
				// style : {
				//
				// textAlign : 'right'
				//
				// },
				// value : '<a href="http://www.baidu.com" target = "_blank"
				// >帮助</a>'
				// }
				],

				region : 'south'
					// ,
					// height : 135
			}]
		});

		me.callParent(arguments);
	}

});

Ext.define("DJ.other.news.NewsList.NewsListGridList", {

	extend : 'Ext.c.GridPanel',
	// title : "地址簿",

	alias : 'widget.newslistgridlist',

	id : 'DJ.other.news.NewsList.NewsListGridList',
	pageSize : 50,
	closable : false,// 是否现实关闭按钮,默认为false
	url : 'gainVmiNews.do',
	// Delurl : "DelAddressList.do",
	// EditUI : "DJ.System.AddressEdit",

	// resizable : true,

	hideHeaders : true,

	loadMask : false,

	onload : function() {
		// 加载后事件，可以设置按钮，控件值等

		var me = this;

		MyCommonToolsZ.setComAfterrender(me, function(com, eOpts) {

			Ext.getCmp("DJ.other.news.NewsList.NewsListGridList")
					.down("toolbar").hide();
			Ext.getCmp("DJ.other.news.NewsList.NewsListGridList")
					.down("pagingtoolbar").hide();

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
		'header' : 'ftitle',
		// 'dataIndex' : 'ftitle',
		tpl : "<a href='javascript:parent.DJ.myComponent.img.jqueryImgCarousel.JqueryImgCarouselContainer.showContentView({fid}, \"{fpath}\")'> {ftitle} </a>",

//		width : 270,
		flex : 27,
		sortable : true
	}, {
		'header' : 'fcreatetime',
		'dataIndex' : 'fcreatetime',
//		width : 140,
		flex : 14,
		sortable : true
	}]
});