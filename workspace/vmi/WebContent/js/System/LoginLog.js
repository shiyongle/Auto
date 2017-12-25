Ext.ns("DJ.System.loginLog");
var str = "day";
var type = "user";
var date1;
var date2;
var date = "%";
var loginLogStore = new Ext.data.JsonStore({
	storeId : 'loginLogStore',
	fields : [{
				name : 'date',
				type : "String"
			}, {
				name : 'times',
				type : "int"
			}],
	proxy : {
		type : 'ajax',
		url : 'GetUserLoginLog.do',
		reader : {
			type : 'json',
			root : 'data'
		}
	}
		// autoLoad : true

	}).load({
			params : {
				start : 0,
				limit : 35,
				sdate : str,
				ctype : type
			}
		})

var loginLogGridStore = new Ext.data.JsonStore({
			storeId : 'loginLogGridStore',
			fields : [{
						name : 'fusername',
						type : "String"
					}, {
						name : 'fname',
						type : "String"
					}, {
						name : 'count',
						type : "String"
					}, {
						name : 'flogintime',
						type : "String"
					}, {
						name : 'fip',
						type : "String"
					}, {
						name : 'fbrowser',
						type : "String"
					}, {
						name : 'fremark',
						type : "String"
					}],
			autoLoad : true,
			proxy : {
				type : 'ajax',
				url : 'GetUserLoginLogGrid.do',
				reader : {
					type : 'json',
					root : 'data'
				}
			}
		});

var pagingToolbar = new Ext.PagingToolbar({
			pageSize : 25,
			store : loginLogGridStore,
			displayInfo : true,
			displayMsg : '第{0}-第{1}条，一共{2}条',
			emptyMsg : "没有记录"
		});

loginLogGridStore.on('beforeload', function(store, options) {
			Ext.apply(loginLogGridStore.proxy.extraParams, {
						sdate : str,
						date : date
					});

		});
Ext.define('DJ.System.LoginLog', {
	extend : 'Ext.panel.Panel',
	id : 'DJ.System.LoginLog',
	autoScroll : true,
	border : false,
	title : '平台流量报表',
	closable : true,
	items : [{
		xtype : 'panel',
		title : '登录次数统计',
		height : 300,
		layout : 'fit',
		tbar : new Ext.Toolbar({
					items : [{
						xtype : 'combo',
						store : [['day', '按天计'], ['week', '按周计'],
								['month', '按月计']],
						queryMode : 'local',
						emptyText : '时间（默认按天计）...',
						listeners : {
							'select' : function() {
								// Ext.getCmp("DJ.System.LoginLog.Chart").setTitle(str);
								str = this.value;
								if (str == 'week') {
									date = '%';
								}
								loginLogStore.load({
											params : {
												start : 0,
												limit : 35,
												sdate : str,
												ctype : type
											}
										})
							}
						}
					}, {
						xtype : 'combo',
						store : [['user', '按用户计'], ['cstmr', '按客户计']],
						queryMode : 'local',
						emptyText : '方式（默认按用户计）...',
						listeners : {
							'select' : function() {
								// Ext.getCmp("DJ.System.LoginLog.Chart").setTitle(str);
								type = this.value;
								if (type == "user") {
									Ext.getCmp("DJ.System.LoginLog.Chart").axes
											.get(0).setTitle("用户登录次数（次）");
								} else {
									// pagingToolbar.disable();
									Ext.getCmp("DJ.System.LoginLog.Chart").axes
											.get(0).setTitle("登录过的客户数（家）");
								}
								loginLogStore.load({
											params : {
												start : 0,
												limit : 35,
												sdate : str,
												ctype : type
											}
										})
							}
						}
					}]
				}),
		items : [{
			xtype : 'chart',
			id : 'DJ.System.LoginLog.Chart',
			store : loginLogStore,
			animate : true,// 是否启用动画效果
			legend : {
				position : 'bottom' // 图例位置
			},
			shadow : true,
			axes : [{
						type : 'Numeric',
						position : 'left',
						minimum : 0,// 数轴最小值
						// maximum : 200,// 数轴最大值
						fields : ['times'],
						title : '用户登录次数（次）'
					}, {
						type : 'Category',
						position : 'bottom',
						fields : ['date'],
						title : '日期'
					}],
			series : [{
				type : 'line',// 折线图表序列
				axis : 'left',
				xField : 'date',
				yField : 'times',
				title : '登录情况',
				markerCfg : {// 节点标识配置
					type : 'circle', // 节点形状，圆形
					radius : 2
					// 半径为4像素
				},
				highlight : {
					size : 4,
					radius : 4
				},
				// selectionTolerance:Number,
				showMarkers : true,
				tips : {
					trackMouse : true,
					width : 160,
					height : 40,
					smooth : true,
					lineWidth : 1,
					showMarkers : false,
					renderer : function(storeItem) {
						date1 = storeItem.get('date');
						if (type == "user") {
							this.setTitle(storeItem.get('date') + " : "
									+ storeItem.get('times') + "次<br>"
									+ "（单击显示详情）");
						} else {
							this.setTitle(storeItem.get('date') + " : "
									+ storeItem.get('times') + "家<br>"
									+ "（单击显示详情）");
						}
					}
				},
				listeners : {
					itemmouseup : function(storeItem) {
						date = date1;
						if (type == 'user') {
							// pagingToolbar.enable();
							for (var i = 0; i < 7; i++) {
								Ext.getCmp("DJ.System.LoginLog.grid").columns[2]
										.hide();
								if (i != 1 && i != 2) {
									Ext.getCmp("DJ.System.LoginLog.grid").columns[i]
											.show();
								}
							}
						} else {
							// pagingToolbar.disable();
							for (var i = 0; i < 7; i++) {
								if (i != 1) {
									Ext.getCmp("DJ.System.LoginLog.grid").columns[i]
											.hide();
								}
								Ext.getCmp("DJ.System.LoginLog.grid").columns[2]
										.show();
							}
						}
						loginLogGridStore.currentPage = 1;
						if (type == 'user') {
							loginLogGridStore.load({
										params : {
											date : date,
											start : 0,
											limit : 25,
											page : 1,
											ctype : type
										}
									});
						} else {
							loginLogGridStore.load({
										params : {
											date : date,
											start : 0,
											nolimit : 25,
											ctype : type
										}
									});
						}
					}
				}
			}]

		}]
	}, {
		xtype : 'panel',
		height : 337,
		layout : 'fit',
		items : [{
					xtype : 'grid',
					title : '日登录明细',
					id : 'DJ.System.LoginLog.grid',
					store : loginLogGridStore,
					columns : [{
								header : '用户',
								dataIndex : 'fusername',
								width : 140
							}, {
								header : '客户',
								dataIndex : 'fname',
								width : 140
							}, {
								header : '登录次数',
								dataIndex : 'count',
								width : 1100,
								hidden : true
							}, {
								header : '登录时间',
								dataIndex : 'flogintime',
								width : 160
							}, {
								header : 'ip地址',
								dataIndex : 'fip',
								width : 130
							}, {
								header : '浏览器',
								dataIndex : 'fbrowser',
								width : 170
							}, {
								header : '备注',
								dataIndex : 'fremark',
								width : 500
							}]
				}],
		bbar : pagingToolbar
	}]
});