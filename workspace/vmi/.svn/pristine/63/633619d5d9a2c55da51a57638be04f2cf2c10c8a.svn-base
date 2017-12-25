Ext.tip.QuickTipManager.init();

/**
 * double date picker compoment,双日期选择器
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2015-1-10 上午8:26:10
 * @version 0.2 2015-3-13 上午9:03:05,使得在菜单里展示时，也能像平常一样使用
 * 
 */
Ext.define('Ext.ux.form.MyDateTimeSearchBoxMyDoubleDatePick', {

	extend : 'Ext.panel.Panel',

	alias : 'widget.mydatetimesearchboxmydoubledatepick',

	height : 310,
	width : 480,
	// 开始日期域
	beginDateF : null,
	// 结束日期域
	endDateF : null,

	// 开始日期域配置
	beginDateCfg : {},
	// 结束日期域配置
	endDateCfg : {},

	statics : {

		setDateFieldsEndNow : function(com, gainBeginFValue) {

			var now = new Date();
			Ext.ux.form.MyDateTimeSearchBoxMyDoubleDatePick.setDateFields(com,
					gainBeginFValue(now), now);

		},
		setDateFields : function(com, beginDate, endDate) {

			var beginF = com.beginDateF;
			var endF = com.endDateF;

			beginF.setValue(beginDate);

			endF.setValue(endDate);

			beginF.isValid();
			// beginF.checkChange( );
			// endF.checkChange( );

			// setTimeout(function(){
			//			
			// //使得begin域正确触发验证
			// beginF.setValue(beginDate);
			//				
			// },100);

			var beginFP = com.down("datepicker[itemId=beginDate]");
			var endFP = com.down("datepicker[itemId=endDate]");

			//都不空时才设置
			if (!Ext.isEmpty(beginDate) && !Ext.isEmpty(beginDate)) {

				beginFP.setValue(beginDate);
				endFP.setValue(endDate);

			}
			
			

		}

	},

	beforeRender : function() {
		/*
		 * days array for looping through 6 full weeks (6 weeks * 7 days) Note
		 * that we explicitly force the size here so the template creates all
		 * the appropriate cells.
		 */
		var me = this;

		// If there's a Menu among our ancestors, then add the menu class.
		// This is so that the MenuManager does not see a mousedown in this
		// Component as a document mousedown, outside the Menu
		if (me.up('menu')) {
			me.addCls(Ext.baseCSSPrefix + 'menu');
		}

		me.callParent();

		me.protoEl.unselectable();
	},

	layout : {
		align : 'stretch',

		defaultMargins : {
			top : 5,
			right : 5,
			bottom : 5,
			left : 5
		},
		type : 'vbox'
	},

	initComponent : function() {
		var me = this;

		var beginDateCO = {
			flex : 1,
			itemId : 'beginDate',
			xtype : 'datepicker',
			// minDate : new Date(),
			handler : function(picker, date) {
				// do something with the selected date
				me.beginDateF.setValue(date);

				me.endDateF.isValid();
			}
				// ,
				// listeners : {
				// select : function(com, date, eOpts) {
				//
				// }
				// }
		};

		Ext.apply(beginDateCO, me.beginDateCfg);

		var endDateCO = {

			itemId : 'endDate',
			flex : 1,
			xtype : 'datepicker',
			// margin :'0 0 0 5',
			// minDate : new Date(),
			handler : function(picker, date) {
				// do something with the selected date
				me.endDateF.setValue(date);

				me.beginDateF.isValid();
			}

		};

		Ext.apply(endDateCO, me.endDateCfg);

		var buttonContainerCfg = {

			xtype : 'fieldcontainer',
			flex : 1,

			layout : {
				defaultMargins : {
					top : 0,
					right : 5,
					bottom : 0,
					left : 5
				},
				align : 'stretch',
				type : 'hbox'
			// ,
			// padding: 5
			},

			items : [{
				xtype : 'button',
				flex : 1,
				text : '今天',

				selectDates : function() {

					Ext.ux.form.MyDateTimeSearchBoxMyDoubleDatePick
							.setDateFieldsEndNow(me, function(now) {

								return now;

							});

				},

				handler : function() {

					this.selectDates();
				}
			}, {
				xtype : 'button',
				flex : 1,
				text : '昨天',

				selectDates : function() {

					var yesterday = Ext.Date.subtract(new Date(), Ext.Date.DAY, 1);

					Ext.ux.form.MyDateTimeSearchBoxMyDoubleDatePick
							.setDateFields(me, yesterday, yesterday);
				},

				handler : function() {
					this.selectDates()
				}
			}, {
				xtype : 'button',
				flex : 1,
				text : '本周',

				selectDates : function() {

					var now = new Date();

					var beginDate = Ext.Date.subtract(now, Ext.Date.DAY, now
							.getDay());

					var endDate = Ext.Date.add(now, Ext.Date.DAY, 6
							- now.getDay());

					Ext.ux.form.MyDateTimeSearchBoxMyDoubleDatePick
							.setDateFields(me, beginDate, endDate);

				},

				handler : function() {
					this.selectDates()
				}
			}, {
				xtype : 'button',
				flex : 1,
				text : '上周',

				selectDates : function() {

					var now = new Date();

					// 上周最后一天
					var lastDateOfPreW = Ext.Date.subtract(now, Ext.Date.DAY,
							now.getDay() + 1);

					Ext.ux.form.MyDateTimeSearchBoxMyDoubleDatePick
							.setDateFields(me, Ext.Date.subtract(
									lastDateOfPreW, Ext.Date.DAY, 6),
									lastDateOfPreW);

				},

				handler : function() {
					this.selectDates();
				}
			}, {
				xtype : 'button',
				flex : 1,
				text : '本月',

				selectDates : function() {

					var now = new Date();

					Ext.ux.form.MyDateTimeSearchBoxMyDoubleDatePick
							.setDateFields(me, Ext.Date
									.getFirstDateOfMonth(now), Ext.Date
									.getLastDateOfMonth(now));

				},

				handler : function() {
					this.selectDates();
				}
			}, {
				xtype : 'button',
				flex : 1,
				text : '上月',

				selectDates : function() {var now = new Date();

					// 减2天保证是上个月
					var lastDateOfPreM = Ext.Date.subtract(Ext.Date
							.getFirstDateOfMonth(now), Ext.Date.DAY, 2);

					Ext.ux.form.MyDateTimeSearchBoxMyDoubleDatePick
							.setDateFields(me, Ext.Date
									.getFirstDateOfMonth(lastDateOfPreM),
									Ext.Date.getLastDateOfMonth(lastDateOfPreM));},

				handler : function() {
					this.selectDates();
				}
			}, {
				xtype : 'button',
				flex : 1,
				text : '全部',
				style : {
					color : 'red',
					fontStyle : 'italic'
				// ,
				// textDecoration : 'underline'
				// ,
				// background: 'green'
				},

				selectDates : function() {

					Ext.ux.form.MyDateTimeSearchBoxMyDoubleDatePick
							.setDateFields(me, '', '')

				},

				handler : function() {
					this.selectDates();
				}

			}]

		};

		var confirmCCfg = {

			xtype : 'fieldcontainer',
			flex : 1,

			layout : {
				defaultMargins : {
					top : 0,
					right : 5,
					bottom : 0,
					left : 5
				},
				align : 'stretch',
				type : 'hbox'
			// ,
			// padding: 5
			},

			items : [{
				xtype : 'button',
				flex : 1,
				text : '确定',
				handler : function() {

					var comT = me.beginDateF.up("mydatetimesearchbox");

					Ext.ux.form.MyDateTimeSearchBox._searchAction(comT, comT);

					me.pickerField.collapse();

				}
			}, {
				xtype : 'button',
				flex : 1,
				text : '取消',
				handler : function() {

					me.pickerField.collapse();

					// me.hide();
				}
			}]

		};

		var ts = me.beginDateF.up("mydatetimesearchbox");
		// if (ts.ownerCt.getXType() == "menu") {
		//
//		Ext.apply(buttonContainerCfg, {
//
//			// disabled : true,
//
//			defaults : {
//
//				// tooltip : "按钮点击无效，请回车搜索",
//
//				listeners : {
//					// 悬浮触发选择
//					mouseover : function(com, e, eOpts) {
//
//						// 提示											
//						com.selectDates.call(com);
//			
//					}
//
//				}
//
//			}
//
//		});

		// confirmCCfg.disabled = true;

		// confirmCCfg.defaults = {
		// tooltip : "请回车搜索"
		// };
		//
		// } else {

		// 否则就添加处理器关闭功能
		Ext.each(buttonContainerCfg.items, function(ele, index, all) {

			var handlerT = ele.handler;

			ele.handler = Ext.Function.createSequence(handlerT, function() {

				var comT = me.beginDateF.up("mydatetimesearchbox");

				Ext.ux.form.MyDateTimeSearchBox._searchAction(comT, comT);

				me.pickerField.collapse();

			}, ele);

		});

		// }

		Ext.applyIf(me, {
			items : [

			{
				xtype : 'container',
				// flex: 5,

				layout : {
					type : 'hbox',
					defaultMargins : {
						top : 0,
						right : 5,
						bottom : 0,
						left : 5
					}
				// ,
				// padding: 5
				},

				items : [beginDateCO, endDateCO]
			}, buttonContainerCfg, confirmCCfg

			]
		});

		me.callParent(arguments);
	}

});

Ext.apply(Ext.form.field.VTypes, {
	dateRange : function(val, field) {
		var beginDate = null, beginDateCmp = null, endDate = null, endDateCmp = null, validStatus = true;
		if (field.dateRange) {
			if (!Ext.isEmpty(field.dateRange.begin)) {
				beginDateCmp = Ext.getCmp(field.dateRange.begin);
				beginDate = beginDateCmp.getValue()
			}
			if (!Ext.isEmpty(field.dateRange.end)) {
				endDateCmp = Ext.getCmp(field.dateRange.end);
				endDate = endDateCmp.getValue()
			}
		}
		if (!Ext.isEmpty(beginDate) && !Ext.isEmpty(endDate)) {
			validStatus = beginDate <= endDate
		}
		return validStatus
	},
	dateRangeText : "开始日期不能大于结束日期，请重新选择。"
});

/**
 * info： 日期搜索框，
 * 
 * usage: 加载，配置
 * 
 * @since long ago
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 long ago,原生域基本功能
 * @version 0.2 2015-1-10 上午8:33:55，double date picker as picker for
 *          convenient,给picker添加回车按钮查询功能
 * @version 0.2.1 2015-1-29 下午3:14:35, // 前面的条件生效additionalCondition : false,
 * @version 0.2.2 ，前端搜索统一解决方案功能
 * @version 0.3 2015-3-13 上午9:03:45，使得在菜单里展示时，也能像平常一样使用
 * 
 */
Ext.define('Ext.ux.form.MyDateTimeSearchBox', {
	extend : 'Ext.container.Container',

	alias : 'widget.mydatetimesearchbox',

	requires : ['DJ.tools.common.MyCommonToolsZ'],

	mixins : ['DJ.tools.fieldRel.mixer.MySearchFieldMixer'],

	height : 23,
	width : 460,
	layout : {

		type : 'hbox'
	},
	frame : false,

	// 这个设为true的话，必须有additionalCondition : false,
	filterMode : false,

	filters : [],

	conditionLinker : [],

	gainGrid : function() {

		return this.up('grid');

	},

	storeG : null,

	paramsZ : {},

	labelFtext : '',

	conditionDateField : '',

	// 只对默认的grid的store有效
	useDefaultfilter : false,

	// 开始默认时间
	startDate : null,

	// 结束默认时间
	endDate : null,

	// 上面为true的附带配置,前面的条件生效,弃用
	additionalCondition : false,

	// 默认过滤器之后的处理器
	afterDefaultSearchAction : function(store) {

	},

	// 默认过滤器之前的处理器
	beforeDefaultSearchAction : function(store) {

		return true;// false阻止执行
	},

	/**
	 * 这个比较常用
	 * 
	 * @param {}
	 *            storeT
	 * @param {}
	 *            begainDate
	 * @param {}
	 *            endDate
	 * @param {}
	 *            myfilter
	 * @return {Boolean}
	 */
	beforeLoad : function(storeT, begainDate, endDate, myfilter) {

		return true;// false阻止执行
	},

	// 在初始化前部执行的用户处理器
	custFrontInit : function() {

	},

	// 覆写，判断是否已经在grid coms内,全否则加
	// _hasBeenAdded : function(item, index, all) {
	//
	// var me = this;
	//
	// return ((item.labelFtext == me.labelFtext) && (item.conditionDateField ==
	// me.conditionDateField));
	//
	// },

	_removePrev : function(item, index, all) {

		var me = this;

		var isAdded = (item.labelFtext == me.labelFtext)
				&& (item.conditionDateField == me.conditionDateField)

		// 保存之前date组件的状态
		if (isAdded) {

			me.conditionLinker = Ext.Array.clone(item.conditionLinker);

			me.filters = Ext.Array.clone(item.filters);

			var beginTimeFT = me.down('datefield[id$=beginTime]');
			var endTimeFT = me.down('datefield[id$=endTime]');

			var dates = me._gainPreDates();

			if (!Ext.isEmpty(dates[0])) {

				beginTimeFT.setValue(dates[0].substring(0, 10));
				endTimeFT.setValue(dates[1].substring(0, 10));

			}

		}

		return !isAdded;

	},

	inheritableStatics : {
		_searchAction : function(me, com) {

			var beginTimeFT = Ext.getCmp(me.id + "beginTime");
			var endTimeFT = Ext.getCmp(me.id + "endTime");
			
			var beginTimeFTValue = beginTimeFT.getValue();
			var endTimeFTValue = endTimeFT.getValue();
			
			//校验
			if (!(beginTimeFT.isValid() && endTimeFT.isValid())) {

				return;

			}

			// 一个为空，一个有效
			if ((Ext.isEmpty(beginTimeFTValue) && !Ext.isEmpty(endTimeFTValue)) || (!Ext.isEmpty(beginTimeFTValue) && Ext.isEmpty(endTimeFTValue))) {

				return ;
				
			}
			
			

			
			
			var beginTime = Ext.Date.format(beginTimeFTValue,
					beginTimeFT.format)
					+ " 00:00:00";

			var endTime = Ext.Date.format(endTimeFTValue,
					endTimeFT.format)
					+ " 23:59:59";
					
			if (Ext.isEmpty(beginTimeFT.getValue())
					&& Ext.isEmpty(endTimeFT.getValue())) {

				beginTime = "1999-01-15 00:00:00";

				endTime = "2099-01-15 23:59:59";

			}

			var callbackT = function(records, operation, success) {

				if (!success) {

					Ext.Msg.alert("提示", operation.response.responseText);
				}

			};

			var paramsT = {

				conditionDateField : me.conditionDateField,

				beginTime : beginTime,

				endTime : endTime

			};

			Ext.applyIf(paramsT, me.paramsZ);

			if (me.useDefaultfilter) {

				var storeT = me.storeG || me.up("grid").getStore();

				if (!me.beforeDefaultSearchAction(storeT)) {

					return;

				}

				var myfilter = [];

				var defaultmaskstring = ' #0 and #1 ';

				if (me.additionalCondition && !me.filterMode) {

					var maskT = storeT.getDefaultmaskstring();
					var filterT = Ext.clone(storeT.getDefaultfilter());

					// 那说明前一次的时间查询状态还在
					if (maskT.search(/\).+and.+#.+#/) != -1) {

						// maskT =
						// maskT.substring(1,maskT.search(/\).+and.+#.+#/));
						//						
						filterT.pop();
						filterT.pop();

						defaultmaskstring = maskT;

					} else {

						var indexT = maskT.split(/#\d/).length - 1;

						defaultmaskstring = (indexT == 0 ? '' : Ext.String
								.format('( {0} ) and', maskT))
								+ Ext.String.format(" #{0} and #{1} ", indexT,
										++indexT);

					}
					myfilter = Ext.clone(filterT);

				}

				myfilter.push({
					myfilterfield : paramsT.conditionDateField,
					CompareType : ">=",
					type : "datetime",
					value : beginTime
				});

				myfilter.push({
					myfilterfield : paramsT.conditionDateField,
					CompareType : "<=",
					type : "datetime",
					value : endTime
				});

				if (!me.beforeLoad(storeT, beginTime, endTime, myfilter)) {

					return;

				}

				if (!me.filterMode) {

					storeT.setDefaultfilter(myfilter);
					storeT.setDefaultmaskstring(defaultmaskstring);

					storeT.loadPage(1);

				} else {

					me.filters = myfilter;
					me.conditionLinker = ['and'];

					me.gainGrid().doGridSearchAction();

				}

				me.afterDefaultSearchAction(storeT);

			} else {

				if (me.storeG) {
					me.storeG.load({

						params : paramsT,
						callback : callbackT

					});
				} else {
					com.up("grid").getStore().loadPage(1, {

						params : paramsT,
						callback : callbackT

					});
				}

			}

		}
	},

	_gainPreDates : function() {

		var me = this;

		var dates = [];

		if (!Ext.isEmpty(me.filters)) {

			dates.push(me.filters[0].value);
			dates.push(me.filters[1].value);

		}

		return dates;

	},

	initComponent : function() {
		var me = this;

		me.custFrontInit();

		me.pushSelfToGridComsAF();

		//
		var dates = me._gainPreDates();

		// 覆写picker创建器
		var createPickerT = function() {

			var a = this;

			var beginF = me.getComponent("beginDateF");
			var endF = me.getComponent("endDateF");

			var doublePicker = Ext.create(
					"Ext.ux.form.MyDateTimeSearchBoxMyDoubleDatePick", {

						pickerField : a,
						ownerCt : a.ownerCt,

						renderTo : document.body,

						floating : true,
						hidden : true,
						focusOnShow : true,

						beginDateF : beginF,
						endDateF : endF,

						beginDateCfg : {

							// pickerField : beginF,
							// ownerCt : beginF.ownerCt,
							// renderTo : document.body,
							// floating : true,
							// hidden : true,
							// focusOnShow : true,
							minDate : beginF.minValue,
							maxDate : beginF.maxValue,
							disabledDatesRE : beginF.disabledDatesRE,
							disabledDatesText : beginF.disabledDatesText,
							disabledDays : beginF.disabledDays,
							disabledDaysText : beginF.disabledDaysText,
							format : beginF.format,
							showToday : beginF.showToday,
							startDay : beginF.startDay,
							minText : Ext.String.format(beginF.minText, beginF
									.formatDate(beginF.minValue)),
							maxText : Ext.String.format(beginF.maxText, beginF
									.formatDate(beginF.maxValue)),
							// listeners : {
							// scope : beginF,
							// select : beginF.onSelect
							// },
							keyNavConfig : {
								enter : function(e) {
									Ext.ux.form.MyDateTimeSearchBox
											._searchAction(me, me);
								},
								esc : function() {
									beginF.collapse();
								}
							}

						},
						endDateCfg : {

							// pickerField : endF,
							// ownerCt : endF.ownerCt,
							// renderTo : document.body,
							// floating : true,
							// hidden : true,
							// focusOnShow : true,
							minDate : endF.minValue,
							maxDate : endF.maxValue,
							disabledDatesRE : endF.disabledDatesRE,
							disabledDatesText : endF.disabledDatesText,
							disabledDays : endF.disabledDays,
							disabledDaysText : endF.disabledDaysText,
							format : endF.format,
							showToday : endF.showToday,
							startDay : endF.startDay,
							minText : Ext.String.format(endF.minText, endF
									.formatDate(endF.minValue)),
							maxText : Ext.String.format(endF.maxText, endF
									.formatDate(endF.maxValue)),
							// listeners : {
							// scope : endF,
							// select : endF.onSelect
							// },
							keyNavConfig : {
								enter : function(e) {
									Ext.ux.form.MyDateTimeSearchBox
											._searchAction(me, me);
								},
								esc : function() {
									endF.collapse();
								}
							}

						}

					});

			// a.picker = doublePicker;

			// doublePicker.ownerCt = a;

			// a.picker = doublePicker;

			// doublePicker.on("click", function (){
			//					
			// beginF.focus();
			//						
			// });

			// MyCommonToolsZ.setComAfterrender(doublePicker, function(com) {
			//
			// var ele = com.getEl();
			//
			// ele.on({
			//
			// "click" : function(e, t, eOpts) {
			//
			// e.stopEvent();
			// e.stopPropagation();
			// beginF.focus();
			//
			// },
			// focus : function(e, t, eOpts) {
			//
			// e.stopEvent();
			// e.stopPropagation();
			// beginF.focus();
			//
			// }
			//
			// });
			//
			// // ele.on("click",function(e, t, eOpts){
			// //
			// // e.stopEvent( );
			// // e.stopPropagation( );
			// // // beginF.focus();
			// //
			// // });
			// });

			// var nav = new Ext.util.KeyNav({
			// target : doublePicker.getEl().dom,
			// defaultEventAction : false,
			// enter : function(e) {
			// Ext.ux.form.MyDateTimeSearchBox._searchAction(me, me);
			// },
			//
			// // defaultAction
			// scope : this
			// });

			return doublePicker;

		};

		var listenersT = {
			keyup : function(com, e) {

				if (Ext.EventObject.ENTER == e.getKey()) {

					Ext.ux.form.MyDateTimeSearchBox._searchAction(me, com);
				}

			}
		};

		var datefieldBeginF = {
			enableKeyEvents : true,
			id : me.id + "beginTime",
			itemId : 'beginDateF',
			xtype : 'datefield',
			format : "Y-m-d",
			labelWidth : 80,
			// height : 20,
			fieldLabel : me.labelFtext + ' 从',
			//默认值是本月第一天
			value : dates[0] || me.startDate || Ext.Date.getFirstDateOfMonth(new Date()),
			dateRange : {
				begin : me.id + "beginTime",
				end : me.id + "endTime"
			},
			vtype : "dateRange",
			createPicker : createPickerT,
			// onSelect : function(a, c) {
			// var b = this;
			// b.setValue(c);
			// b.fireEvent("select", b, c);
			// b.collapse()
			// },
			listeners : listenersT,
			onExpand : function() {
				var a = this.getValue();
				this.picker.down("datepicker[itemId=beginDate]").setValue(Ext
						.isDate(a) ? a : new Date())
			}
		}

		Ext.apply(datefieldBeginF, this.datefieldBeginF);

		var datefieldEndF = {
			enableKeyEvents : true,
			id : me.id + "endTime",
			itemId : 'endDateF',
			xtype : 'datefield',
			format : "Y-m-d",
			labelWidth : 20,
			// height : 20,
			fieldLabel : '到',
			value : dates[1] || me.endDate || new Date(),
			dateRange : {
				begin : me.id + "beginTime",
				end : me.id + "endTime"
			},
			vtype : "dateRange",
			createPicker : createPickerT,
			listeners : listenersT,
			onExpand : function() {
				var a = this.getValue();
				this.picker.down("datepicker[itemId=endDate]").setValue(Ext
						.isDate(a) ? a : new Date())
			}

		}

		Ext.apply(datefieldEndF, this.datefieldEndF);

		// var butonPick = {
		//		
		// xtype : 'button',
		// tooltip : "butonPick",
		// menu : {xtype : 'menu',
		// items : [{xtype : 'menuitem',
		// text : 'test'}]
		//				
		// },
		//			
		// listeners : {
		// menushow : function(com, menu, eOpts) {
		//					
		// menu.add(createPickerT());
		//					
		// }
		// }
		//			
		// };

		var searchBtF = {

			xtype : 'button',

			tooltip : "回车搜索",

			handler : function(button, event) {
				Ext.ux.form.MyDateTimeSearchBox._searchAction(me, button);
			},
			text : '查询'

		}

		// labelF,

		Ext.apply(searchBtF, this.searchBtF);

		var spacerCfg = {

			xtype : "tbspacer",
			width : 5

		}

		Ext.apply(spacerCfg, this.spacerCfg);

		Ext.applyIf(me, {
			items : [datefieldBeginF, spacerCfg, datefieldEndF, spacerCfg,
					searchBtF]
		});

		me.callParent(arguments);
	}

});
