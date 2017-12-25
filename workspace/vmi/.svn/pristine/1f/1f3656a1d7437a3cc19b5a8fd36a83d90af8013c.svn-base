Ext.ns("DJ.Inv.Storebalance");

DJ.Inv.Storebalance.CustomerStoreBalanceRptLineDataStore = Ext.create(
		"Ext.data.Store", {
			fields : [{
				name : "fdate",
				type : "string",
				// ,
				// dateFormat : "Y-m-d",
				// dateReadFormat : "Y-m-d"
				sortDir : 'DESC'
			}, {
				name : "fstoreqty",
				type : "int"
			}],

			proxy : {
				type : "ajax",
				url : "selectProductHistoryQty.do",
				reader : {
					type : "json",
					root : "data"
				}
			},
			autoLoad : true,

			listeners : {

				
				
				load : function(store, records, successful, eOpts) {
					
					var i = 0;
					
					store.filterBy(function () {
					
						i++;
						
						if (i == 1) {
						
							return true;
						
						} else if (i == records.length) {
						
							return true;
							
						} else if (i % 5 == 0) { 
						
							return true;
						} else {
						
							return false;
							
						}
					
					});
					
				}

			}

		});

Ext.define('DJ.Inv.Storebalance.CustomerStoreBalanceRptLine', {
	extend : 'Ext.chart.Chart',
	alias : 'widget.customerstorebalancerptline',

	animate : true,
	insetPadding : 20,

	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {

			store : DJ.Inv.Storebalance.CustomerStoreBalanceRptLineDataStore,
			legend : {
				position : 'top' // 图例位置
			},
			shadow : true,
			axes : [{
				type : 'Numeric',
				position : 'left',
				// minimum : 0,// 数轴最小值
				// maximum : 50,// 数轴最大值
				fields : ['fstoreqty'],
				title : '库存数量'
			}, {
				type : 'category',
				position : 'bottom',
				// dateFormat: 'Y-m-d',
				fields : ['fdate'],
				// constrain: true,
				title : '时间'
			}],
			series : [

			{
				type : 'line',
				highlight : {
					size : 7,
					radius : 7
				},
				fill : true,// 配置是否填充折线与坐标轴之间的空间
				axis : 'left',
				xField : 'fdate',// 横轴字段
				yField : 'fstoreqty',// 纵轴字段
				title : '时间',// 配置图例字段说明
				markerCfg : {// 节点标识配置
					type : 'circle', // 节点形状，圆形
					radius : 4
				// 半径为4像素
				},
				selectionTolerance : 10,// 鼠标到图表序列出发事件的偏移距离
				showInLegend : true,// 是否显示在图例当中
				smooth : true,// 是否平滑曲线
				showMarkers : true,
				// 配置是否显示折线节点标志

				tips : {

					trackMouse : true,
					width : 80,
					height : 40,
					renderer : function(storeItem, item) {
						this.setTitle(storeItem.get('fstoreqty'));
						this.update(storeItem.get('fdate'));
					}

				}

			}
			// ,
			// {
			// type : 'line',
			// axis : 'left',
			// xField : 'fdate',// 横轴字段
			// yField : 'fstoreqty',// 纵轴字段
			// title : '历史结存',// 配置图例字段说明
			// showInLegend : true
			// // 是否显示在图例当中
			// }

			]
		});

		me.callParent(arguments);
	}

});