Ext.define('DJ.quickOrder.orderInfo.OrdInfoEditor', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.quickOrder.orderInfo.OrdInfoEditor",
	modal : true,
	url : 'saveCustDelApplyInfo.do',
	infourl : 'getCustDelApplyInfo.do',
	viewurl : 'getCustDelApplyInfo.do',
   ctype : "Deliverapply",
	onload : function() {
		this.down('toolbar[dock=top]').hide();
	},

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
	width : 680,
	resizable : false,
	closable : true, // 关闭按钮，默认为true
	listeners : {
		beforeshow : function() {

		},
		show : function() {

		},
		afterrender : function() {
			var d = document.getElementById(this.id);
			if(Ext.isIE){
				var legend = d.getElementsByTagName("legend");
			}else{
				var legend = d.querySelectorAll('legend');
			}
			for (var i = 0; i < legend.length; i++) {
				legend[i].setAttribute('style', 'text-align:center;');
			}
		}
	},

//	Action_BeforeSubmit : function(me) {
//
//	},
//	Action_Submit : function(cc0) {
//
//	},
	initComponent : function() {
		Ext.apply(this, {
			items : [{
						name : 'fid',
						xtype : 'textfield',
						labelWidth : 100,
						width : 300,
						hidden : true
					},{
				xtype : 'textfield',
				hidden : true,
				name : "fsupplierid"
			}, {
				xtype : 'textfield',
				hidden : true,
				name : "fcusproductid"
			}, {
				xtype : 'textfield',
				hidden : true,
				name : "fcustomerid"
			}, {
				xtype : 'fieldset',
				title : '<b>采购下单</b>',
				name : 'deliverapply',
				collapsible : false,
				fieldDefaults : {
					labelAlign : 'left',
					labelWidth : 80
				},
				items : [{
						anchor : "50%",
						xtype : 'textfield',
						fieldLabel : '采购订单号',
						name : 'fordernumber'
				   },{
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
								name : "supplierid",
								fieldLabel : '数量',
								xtype : 'numberfield',
								originalValue : "",
								name : 'famount',
								hideTrigger : true,
								allowBlank : false,
								allowDecimals : false,
								minValue : 1
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
								xtype : 'fieldcontainer',
								fieldLabel : '交期',
								labelWidth : 55,
								anchor : "100%",
								layout : {
									type : 'hbox'
								},
								items : [{
									xtype : 'datefield',
									flex : 2.5,
									// width:120,
									name : 'farrivetime',
									format : 'Y-m-d',
									allowBlank : false,
									blankText : '交期不能为空',
									hideLabel : true,
									margin : '0 10 0 0',
									minValue : Ext.Date.add(
											new Date(new Date()), Ext.Date.DAY,
											2),
									value : Ext.Date.add(new Date(new Date()),
											Ext.Date.DAY, 5)
								}, {
									xtype : 'radiofield',
									boxLabel : '上午',
									inputValue : '09',
									flex : 1,
									margin : '0 5 0 0',
									name : 'fatime'
								}, {
									flex : 1,
									xtype : 'radiofield',
									boxLabel : '下午',
									inputValue : '14',
									checked : true,
									name : 'fatime'
								}]
							}]
						}]

					}, {
						columnWidth : 1,
						baseCls : "x-plain",
						bodyStyle : 'padding-right:10px;',// padding-top:5px
						items : [{
							layout : "anchor",
							baseCls : "x-plain",
							items : [{
								anchor : "100%",
								fieldLabel : '地址',
								xtype : 'cCombobox',
								name : 'faddressid',
								enterIndex : 22,
								displayField : 'fname',
								valueField : 'fid',
								allowBlank : false,
								blankText : '请选择送货地址',
								beforeExpand : function() {
									var me = this;
									var customerid = me.up("textfield[name=fcustomerid]").getValue();
									this.setDefaultfilter([{
											myfilterfield : "cd.fcustomerid",
											CompareType : "like",
											type : "string",
											value : customerid									
									}]);
									var bbar = this.picker.down('toolbar[dock=bottom]');
									if (bbar.items.length == 13) {
										bbar.add(bbar.items.length - 1, {
											xtype : 'button',
											text : '<font color=blue>+新增地址</font>',
											width : 100,
											handler : function() {
												var editui = Ext.getCmp("DJ.System.UserAddressEdit");
												var customerFiled = Ext.getCmp("DJ.quickOrder.OrderHolderPlanEditer").down('textfield[name=fcustomerid]');
												if (editui == null) {
													editui = Ext.create('DJ.System.UserAddressEdit',{isQuickOrder : true});
												}
												editui.seteditstate("add");
												Ext.getCmp("DJ.System.UserAddressEdit").down('cCombobox[name=fcustomerid]').setReadOnly(true);
												editui.show();
												editui.getform().form.findField("fcustomerid").setmyvalue("\"fid\":\""+ customerFiled.getValue()
																+ "\",\"fname\":\""+ customerFiled.getRawValue()+ "\"");
												editui.on('close', function() {
													me.expand();
													me.picker.store.load();
												})
											}
										}, {
											xtype : 'tbfill'
										})
									}
									this.setDefaultfilter([{
										myfilterfield : "cd.fcustomerid",
										CompareType : "like",
										type : "string",
										value : customerid
									}]);

									this.setDefaultmaskstring(" #0 ");
								},
								listeners : {
									afterrender : function() {
									}
								},
								MyConfig : {
									width : 650,
									height : 200,
									url : 'getQuickUserToCustAddress.do',
									hiddenToolbar : true,
									fields : [{
										name : 'fid'
									}, {
										name : 'fname',
										myfilterfield : 'ad.fname',
										myfiltername : '名称',
										myfilterable : true
									}, {
										name : 'flinkman',
										myfilterfield : 'ad.flinkman',
										myfiltername : '联系人',
										myfilterable : true
									}, {
										name : 'fphone',
										myfilterfield : 'ad.fphone',
										myfiltername : '联系电话',
										myfilterable : true
									}],
									columns : [{
										header : '地址名称',
										dataIndex : 'fname',
										flex : 5,
										sortable : true
									}, {
										header : '联系人',
										dataIndex : 'flinkman',
										flex : 1,
										sortable : true
									}, {
										header : '联系电话',
										dataIndex : 'fphone',
										flex : 1,
										sortable : true
									}]
								}
							}, {
								anchor : "100%",
								name : 'fdescription',
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
