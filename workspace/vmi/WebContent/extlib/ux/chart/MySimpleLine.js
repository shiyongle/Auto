Ext.define('Ext.ux.chart.MySimpleLine', {
	extend : 'Ext.chart.Chart',
	alias : 'widget.mysimpleline',

	animate : true,
	insetPadding : 20,

	initComponent : function() {
		var me = this;

		var mysimplelineStoreCfg = {
			fields : [{
				name : me.mysimplelineStoreCfg.fields[0].name,
				type : "int"
			}, {
				name : me.mysimplelineStoreCfg.fields[1].name,
				type : "string"
//				,
//				dateFormat : "Y-m-d"
					// ,
					// sortDir : 'DESC'
					}],

			proxy : {
				type : "ajax",
				url : me.mysimplelineStoreCfg.proxy.url,
				reader : {
					type : "json",
					root : "data"
				}
			},
			autoLoad : true,

			listeners : {

				load : function(store, records, successful, eOpts) {

					
					if(!records){
						return;
					}
					
					var lengthT = records.length;

					// 数据不对才转换处理
					if (lengthT != 7) {

						var datesT = [];

						var n = 7;

						while (n-- > 0) {

							datesT.push(Ext.Date.format(Ext.Date.subtract(
									new Date(), Ext.Date.DAY, 6 - n), 'Y-m-d'));

						}

						Ext.each(records,
								function(item, index, countriesItSelf) {

									var dateS = item.get(me.mysimplelineStoreCfg.fields[1].name);
									
									Ext.Array.remove(datesT, dateS);

								});

						var modelsT = [];

						Ext.each(datesT,
								function(item, index, countriesItSelf) {

									var objT = {};
									objT[me.mysimplelineStoreCfg.fields[0].name] = 0;
									objT[me.mysimplelineStoreCfg.fields[1].name] = item;

									modelsT.push(objT);

								});

						store.add(modelsT);
						
//						store.sort( me.mysimplelineStoreCfg.fields[1].name, 'DESC'); 
						
						store.sort({
							sorterFn : function(obj1, obj2) {

								var date1 = Ext.Date.parseDate(obj1.get(me.mysimplelineStoreCfg.fields[1].name), "Y-m-d");
								var date2 = Ext.Date.parseDate(obj2.get(me.mysimplelineStoreCfg.fields[1].name), "Y-m-d");

								if (date1 > date2) {

									return 1;
								} else if (date1 < date2) {

									return -1;
								} else {

									return 0
								}

							}
						}); 
						
// console.log(store);

					}

					// // 端点强制确定
					// if (store.getAt(lengthT -
					// 1)[me.mysimplelineStoreCfg.fields[1].name] !=
					// Ext.Date
					// .format(new Date(), 'Y-m-d')) {
					//
					// var objT = {};
					// objT[me.mysimplelineStoreCfg.fields[0].name] = 0;
					// objT[me.mysimplelineStoreCfg.fields[1].name] = Ext.Date
					// .format(new Date(), 'Y-m-d');
					//
					// // store.removeAt(lengthT - 1);
					// store.insert(lengthT - 1, [objT]);
					//
					// }

					// if (store.getAt(lengthT -
					// 1)[me.mysimplelineStoreCfg.fields[1].name] != Ext.Date
					// .format(Ext.Date.subtract(new Date(), Ext.Date.DAY,
					// 30), 'Y-m-d')) {
					//
					// var objT = {};
					// objT[me.mysimplelineStoreCfg.fields[0].name] = 0;
					// objT[me.mysimplelineStoreCfg.fields[1].name] = Ext.Date
					// .format(Ext.Date.subtract(new Date(),
					// Ext.Date.DAY, 30), 'Y-m-d')
					//
					// // store.removeAt(0);
					// store.insert(0, [objT]);
					//
					// }

					// var i = 0;

					// if (lengthT > 5) {
					//
					// var divisor = lengthT / 5;
					//
					// divisor = Ext.Number.toFixed(divisor, 0);
					//
					// store.filterBy(function() {
					//
					// i++;
					//
					// if (i == 1) {
					//
					// return true;
					//
					// } else if (i == lengthT) {
					//
					// return true;
					//
					// } else if (i % divisor == 0) {
					//
					// return true;
					// } else {
					//
					// return false;
					//
					// }
					//
					// });
					//
					// }

				}

			}

		};

		// Ext.applyIf(mysimplelineStoreCfg, me.mysimplelineStoreCfg);

		var mysimplelineStore = Ext.create("Ext.data.Store",
				mysimplelineStoreCfg);

		// console.log(mysimplelineStore);

		var lineCfg = {
			// listeners : {
			// click : function(e, eOpts) {
			// mysimplelineStore.load();
			// }
			//
			// },

			store : mysimplelineStore,
			legend : {
				position : 'top' // 图例位置
			},
			shadow : true,
			axes : [{
				type : 'Numeric',
				position : 'left',
				// minimum : 0,// 数轴最小值
				// maximum : 50,// 数轴最大值
				fields : [me.mysimplelineStoreCfg.fields[0].name],
				title : me.lineCfg.axes[0].title
			}, {
				type : 'category',
				position : 'bottom',
//				dateFormat : 'Y-m-d',
				fields : [me.mysimplelineStoreCfg.fields[1].name],
//				constrain : true,
				title : me.lineCfg.axes[1].title
//				,
//				step : [Ext.Date.DAY, 1],
//				fromDate : Ext.Date.subtract(new Date(), Ext.Date.DAY, 6),
//				toDate : new Date()
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
				xField : me.mysimplelineStoreCfg.fields[1].name,// 横轴字段
				yField : me.mysimplelineStoreCfg.fields[0].name,// 纵轴字段
				// title : '时间',// 配置图例字段说明
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
						this.setTitle(storeItem
								.get(me.mysimplelineStoreCfg.fields[0].name));
						this.update( storeItem
								.get(me.mysimplelineStoreCfg.fields[1].name));
					}
				}
			}]
		};

		if (me.lineCfg.debug) {

			Ext.applyIf(lineCfg, {
				listeners : {
					click : function(e, eOpts) {
						mysimplelineStore.load();
					}

				}
			});
		}

		// Ext.applyIf(lineCfg, me.lineCfg);

		Ext.applyIf(me, lineCfg);

		me.callParent(arguments);
	}

});
