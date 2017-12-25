Ext.define('DJ.quickOrder.orderInfo.PrepareGoodsEditor', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.quickOrder.orderInfo.PrepareGoodsEditor",
	modal : true,
//	closeAction : 'close',
	isFirstOpen : true,

	onload : function() {

		this.down('toolbar[dock=top]').hide();

		var me = this;

	},
	gridView : '',
	dockedItems : [{
		xtype : 'toolbar',
		dock : 'bottom',
		height : 25,
		ui : 'footer',
		layout : {
			pack : 'end'
		},
		items : [{
			xtype : 'button',
			text : '保存',
			minWidth : 60,
			minHeight : 20,
			handler : function() {

				var btn = this.up('window')
						.down('toolbar[dock=top] button[text*=保]');

				btn.handler.call(btn);
			}
		}, {
			xtype : 'button',
			text : '关闭',
			minWidth : 60,
			minHeight : 20,
			handler : function() {
				this.up('window').close();
			}
		}]
	}],
	title : "修改",
	width : 680,// 230, //Window宽度
	resizable : false,
	url : 'saveOrUpdateQuickMystock.do',
	infourl : 'getQuickMyStockInfo.do',
	ctype:"Mystock",
	viewurl : 'getQuickMyStockInfo.do',
	closable : true, // 关闭按钮，默认为true
	listeners : {
		beforeshow : function(me) {
		},
		afterrender : function() {
			var d = document.getElementById(this.id);
			var legend = d.querySelectorAll('legend');
			for (var i = 0; i < legend.length; i++) {
				legend[i].setAttribute('style', 'text-align:center;');
			}
		}
	},
	initComponent : function() {
		Ext.apply(this, {
			items : [{
				xtype : 'textfield',
				hidden : true,
				name : "fid"
			},{
				xtype : 'textfield',
				hidden : true,
				name : "fsupplierid"
			}, {
				xtype : 'textfield',
				hidden : true,
				name : "fcustproductid"
			}, {
				xtype : 'textfield',
				hidden : true,
				name : "fcustomerid"
			}, {
				xtype : 'textfield',
				hidden : true,
				name : "fnumber"
				}, {
				xtype : 'textfield',
				hidden : true,
				name : "funit"
				}, {
				xtype : 'textfield',
				hidden : true,
				name : "fisconsumed"
				}, {
				xtype : 'textfield',
				hidden : true,
				name : "fcreatetime"
				}, {
				xtype : 'textfield',
				hidden : true,
				name : "fcreateid"
				}, {
				xtype : 'textfield',
				hidden : true,
				name : "fstate"
				}, {
				xtype : 'textfield',
				hidden : true,
				name : "fcharactername"
				}, {
				xtype : 'textfield',
				hidden : true,
				name : "fcharacterid"
				}, {
				xtype : 'textfield',
				hidden : true,
				name : "fsaleorderid"
				}, {
				xtype : 'textfield',
				hidden : true,
				name : "fordered"
				}, {
				xtype : 'textfield',
				hidden : true,
				name : "fordernumber"
				}, {
				xtype : 'textfield',
				hidden : true,
				name : "fordertime"
				}, {
				xtype : 'textfield',
				hidden : true,
				name : "fordermanid"
				}, {
				xtype : 'textfield',
				hidden : true,
				name : "forderentryid"
			},{
				xtype : 'fieldset',
				title : '<b>备货计划</b>',
				fieldDefaults : {
					labelAlign : 'left',
					labelWidth : 80
				},
				items : [{
					style: {
           				 marginTop: '10px'
      				  },
					anchor : "50% ",
					xtype : 'textfield',
					fieldLabel : '采购订单号',
					name:'fpcmordernumber'
				}, {
					layout : "column",
					baseCls : "x-plain",
					items : [{
						columnWidth : .5,
						baseCls : "x-plain",
						items : [{
							layout : "anchor",
							baseCls : "x-plain",
							items : [{
								anchor : "100%",
								fieldLabel : '计划数量',
								xtype : 'numberfield',
								name : 'fplanamount',
								// hideTrigger:true,
								allowBlank : false,
								allowDecimals : false,
								minValue : 1
							}, {
								anchor : "100%",
								fieldLabel : '首次发货',
								xtype : 'datefield',
								name : 'ffinishtime',
								value : Ext.Date.add(new Date(new Date()),
										Ext.Date.DAY, 7),
								format : 'Y-m-d',
								allowBlank : false,
								minValue : Ext.Date.add(new Date(new Date()),
										Ext.Date.DAY, 2)
							}, {
								margin : '0 0 0 85',
								xtype : 'buttongroup',
								layout : 'hbox',
								anchor : "100%",
								items : [{
									xtype : 'button',
									text : '2日内',
									toggleGroup : "ffinishtime",
									anchor : '30%',
									margin : '5 10 5 10',
									handler : function() {
										this
												.up()
												.up()
												.down('datefield[name=ffinishtime]')
												.setValue(Ext.Date.add(
														new Date(new Date()),
														Ext.Date.DAY, 2))
									}
								}, {
									xtype : 'button',
									text : '5日内',
									toggleGroup : "ffinishtime",
									anchor : '30%',
									margin : '5 10 5 10',
									handler : function() {
										this
												.up()
												.up()
												.down('datefield[name=ffinishtime]')
												.setValue(Ext.Date.add(
														new Date(new Date()),
														Ext.Date.DAY, 5))
									}
								}, {
									xtype : 'button',
									toggleGroup : "ffinishtime",
									text : '7日内',
									anchor : '30%',
									pressed : true,
									margin : '5 10 5 10',
									handler : function() {
										this
												.up()
												.up()
												.down('datefield[name=ffinishtime]')
												.setValue(Ext.Date.add(
														new Date(new Date()),
														Ext.Date.DAY, 7))
									}
								}]

							}]
						}]
					}, {
						columnWidth : .5,
						baseCls : "x-plain",
						bodyStyle : 'padding-left:20px;padding-right:10px',
						items : [{
							layout : "anchor",
							baseCls : "x-plain",
							items : [{
								anchor : "100%",
								fieldLabel : '平均发货量',
								xtype : 'numberfield',
								name : 'faveragefamount',
								// allowBlank : false,
								allowDecimals : false,
								minValue : 1
							}, {
								fieldLabel : '备货周期',
								xtype : 'datefield',
								anchor : "100%",
								name : 'fconsumetime',
								format : 'Y-m-d',
								allowBlank : false,

								maxValue : Ext.Date.add(new Date(new Date()),
										Ext.Date.MONTH, 1),

								minValue : Ext.Date.add(new Date(new Date()),
										Ext.Date.DAY, 2),
								value : Ext.Date.add(new Date(new Date()),
										Ext.Date.MONTH, 1)
							}, {
								margin : '0 0 0 85',
								xtype : 'buttongroup',
								layout : 'hbox',
								anchor : '100%',
								items : [{
									xtype : 'button',
									toggleGroup : "fconsumetime",
									text : '一周内',
									anchor : '30%',
									margin : '5 10 5 10',
									handler : function() {
										this
												.up()
												.up()
												.down('datefield[name=fconsumetime]')
												.setValue(Ext.Date.add(
														new Date(new Date()),
														Ext.Date.DAY, 7))
									}
								}, {
									xtype : 'button',
									text : '半月内',
									toggleGroup : "fconsumetime",
									anchor : '30%',
									margin : '5 10 5 10',
									handler : function() {
										this
												.up()
												.up()
												.down('datefield[name=fconsumetime]')
												.setValue(Ext.Date.add(
														new Date(new Date()),
														Ext.Date.DAY, 15))
									}
								}, {
									xtype : 'button',
									text : '1月内',
									pressed : true,
									toggleGroup : "fconsumetime",
									anchor : '-10',
									margin : '5 0 5 0',
									handler : function() {
										this
												.up()
												.up()
												.down('datefield[name=fconsumetime]')
												.setValue(Ext.Date.add(
														new Date(new Date()),
														Ext.Date.MONTH, 1))
									}
								}]
							}]
						}]

					}, {
						columnWidth : 1,
						baseCls : "x-plain",
						bodyStyle : 'padding-right:10px;padding-top:5px',
						items : [{
							layout : "anchor",
							baseCls : "x-plain",
							items : [{
								anchor : "100%",
								name : 'fremark',
								xtype : 'textfield',
								fieldLabel : '备  注'
							}]
						}]
					}]
				}]

			}]
		}), this.callParent(arguments);
	},
	bodyStyle : "padding-top:15px;padding-left:15px;padding-right:15px"
});
