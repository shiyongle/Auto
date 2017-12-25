Ext.define('DJ.quickOrder.OrderHolderPlanEditer', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.quickOrder.OrderHolderPlanEditer",
	modal : true,
	closeAction : 'hide',
	fdefaultday:5,
	fmindays:0,
	isFirstOpen : true,
	onload : function() {

		this.down('toolbar[dock=top]').hide();

		var me = this;

//		if (me.isFirstOpen) {
//
//			var mystockF = me.down('fieldset[name=mystock]');
//			// setTimeout(function() {
//			//		
//			// me.down('fieldset[name=mystock]').collapse();
//			//		
//			// }, 5000);
//
//			mystockF.expand();
//			mystockF.collapse();
//
//			me.isFirstOpen = false;
//
//		}

		// var mystockF = me.down('fieldset[name=mystock]');
		//		
		// mystockF.expand();
		// mystockF.collapse();

		// var mystockF = me.down('fieldset[name=mystock]');
		//
		// mystockF.on('show', function() {
		//
		// // mystockF.collapse();
		//
		// if (me.isFirstOpen) {
		//
		// setTimeout(function() {
		//
		// me.down('fieldset[name=mystock]').collapse();
		//
		// }, 2000);
		//
		// me.isFirstOpen = false;
		//
		// }
		//
		// });

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
	// bbar: [{
	// xtype: 'tbfill'
	// },{
	// xtype: 'button', text: '保存',width:40,height:25
	// },{
	// xtype: 'button', text: '关闭',width:40,height:25
	// }
	// ],
	title : "下单",
	width : 680,// 230, //Window宽度
	// height : 370,// 137, //Window高度
	resizable : false,
	url : 'saveMystockOrDeliverapply.do',
	closable : true, // 关闭按钮，默认为true
	listeners : {
		beforeshow : function() {
			
		},
	    show : function() {
	        var me = this;
	        var custmoerid = me.customerID;
	        var arminValue = me.arminValue;
//	        me.down('datefield[name=farrivetime]').setMinValue(arminValue);
	        me.down('datefield[name=farrivetime]').setValue(Ext.Date.add(new Date(new Date()),Ext.Date.DAY, me.fdefaultday));
	        me.down('datefield[name=fmintime]').setValue(Ext.Date.add(new Date(new Date()),Ext.Date.DAY, me.fmindays));
	        Ext.Ajax.request({
	          timeout : 60000,
	          url : "getQuickUserDefaultAddress.do",
	          params : {"fcustomerid" :  custmoerid},
	          success : function(response, option) {
	            var obj = Ext
	                .decode(response.responseText);
	            if (obj.success) {
	              var config;
	              if (obj.data) {
	                if (obj.data.length == 1) {
	                  var record = obj.data[0];
	                  config = {
	                    fid : record['fid'],
	                    fname : record['fname']
	                  };
	                  me.down('cCombobox[name=faddressid]').setmyvalue(config);
	                }
	              }
	            }
	          }
	        });
	      },
		afterrender : function() {
			var d = document.getElementById(this.id);
			if(Ext.isIE){
				var legend = d.getElementsByTagName("legend");
			}else{
				var legend = d.querySelectorAll('legend');
			}
			for (var i = 0; i < legend.length; i++) {
				
				if (i == 1 || i == 3) {
				
					continue ;
					
				}
				
				legend[i].setAttribute('style', 'text-align:center;');
			}
		}
	},
	// Action_AfterSubmit : function(c0) {
	//
	// var me = this;
	//
	// setTimeout(function() {
	//
	// me.gridView.getStore().load();
	//
	// }, 2000);
	//
	//		
	//		
	// },
	Action_BeforeSubmit : function(me) {
		var isreturn = false;
		var win = Ext.getCmp(me.id);
		var dform = win.down("form[ctype=Deliverapply]").getForm();
		var mform = win.down("form[ctype=Mystock]").getForm();
		dform.clearInvalid();
		mform.clearInvalid();
		if (!(Ext.isEmpty(dform.findField("famount").getValue()) || dform
				.findField("famount").getValue() === 0)) {
//			if( dform.findField("famount").getValue())
			var num=win.trueStock===false?-1:win.trueStock -dform.findField("famount").getValue();
			var farrivetime=dform.findField("farrivetime").getValue();
			var fmintime=win.down("datefield[name=fmintime]").getValue()
			if(num<0&&fmintime&&farrivetime.getTime()<fmintime.getTime()){
				throw '制造商设置的最早交期为'+Ext.Date.format(fmintime,'Y-m-d');
			}
			if (!dform.isValid()) {
				throw '请完善要货信息';
			}
			isreturn = true;
		}
		if (!(Ext.isEmpty(mform.findField("fplanamount").getValue()) || mform
				.findField("fplanamount").getValue() === 0)) {
			if (!mform.isValid()) {
				throw '请完善备货信息';
			}
			isreturn = true;
		}
		if (!isreturn) {
			throw '请选择下单方式，并完善';
		}
	},
	Action_Submit : function(cc0) {
		var win = Ext.getCmp(cc0.id);
		var cc2 = win.getform();
		var cc3 = cc2.getValues();
		var forms = win.query("fieldset>form");
		for (var zz1 = 0x0; zz1 < forms.length; zz1++) {
			if (!forms[zz1].getForm().hasInvalidField())
				cc3[forms[zz1].ctype] = Ext.encode(forms[zz1].getForm()
						.getValues());

		};
		if (cc0.cverifyinput() === false) {
			return;
		}
//		var cc4 = Ext.ComponentQuery.query("cTable", cc0);
//		for (var cc5 = 0x0; cc5 < cc4.length; cc5++) {
//			cc3[cc4[cc5].name] = Ext.encode(cc4[cc5].getcvalues());
//		};
		cc2.submit({
			url : cc0.url,
			clientValidation : false,
			method : "POST",
			waitMsg : "正在处理请求……",
			timeout : 60000,
			params : cc3,
			success : function(cc6, cc7) {
				 
				var cc8 = Ext.decode(cc7.response.responseText);
				
//				djsuccessmsg(cc8.msg);
				win.close();
			DJ.quickOrder.QuickOrderHolder.openOperationWin().show();
				if (win.parent != "") {
					Ext.getCmp(win.parent).store.load();
				};
				//				new Ext.util.DelayedTask(function(){cc0.opeWin.operation1();}).delay(5000);
				

			},
			failure : function(cc6, cc7) {
				var cc9 = Ext.decode(cc7.response.responseText);
				Ext.MessageBox.alert("提示", cc9.msg);
			}
		});
	},
	initComponent : function() {
		var me=this;
		Ext.apply(this, {
			items : [{
				xtype : 'textfield',
				hidden : true,
				name : "fsupplierids",
				listeners : {
					change : function(e, newValue, oldValue) {
						var coms = e.up('window').query('combobox[fieldLabel=制造商]');
						
						
						Ext.each(coms, function(ele, index, all) {

							ele.setValue(newValue);

						});
							
					}
				}
			}, {
				xtype : 'textfield',
				hidden : true,
				name : "fcustproductid",
				listeners: {
								change: function(me,newValue,oldValue){
										me.nextNode('image').setSrc('getFileSourceByParentId.do?fid='+newValue);									
								}
							}
			}, {
				xtype : 'textfield',
				hidden : true,
				name : "fcustomerid"
			}, {
				xtype: 'container',
				itemId: 'container1',
				style: 'border-bottom: 1px solid #bbb;padding-bottom:5px;',
				items: [{
					xtype: 'displayfield',
					value: '<b>产品信息</b>',
					style: 'margin-bottom: 5px'
				},{
					xtype: 'container',
					layout: 'hbox',
					items: [{
						xtype: 'container',
						width: 160,
						items: [{
							xtype: 'image',
							url: '/vmifile/defaultpic.png',
							width: 140,
							height: 120,
							style: 'border:1px solid #bbb',
							listeners : {
						        render : function(p) {//渲染后给el添加mouseover事件
							         p.getEl().on('click', function(p){ 
							             var magnifier = Ext.create('Ext.ux.form.Magnifier');
							             magnifier.loadImages({
							             	fid: me.down('textfield[name=fcustproductid]').getValue()
								         });
								         var coord = p.getXY();
								         magnifier.showAt({
								            left: coord[0] + 80,
								            top: coord[1] + 5
								         });
							         })
						        }
						   }
						}]
					},{
					xtype: 'container',
					flex:1,
					layout: {
				        type: 'vbox',
				        padding:'10 0 10 0',
				        flex:1
				    },
				    height:125,
				     defaults:{margin:'10',labelWidth:60 },
					items:[{
					xtype:'container',
				     layout: 'hbox',
				     width:'100%',
				     defaults:{margin:'0 10 0 0',labelWidth:60,  flex:1},
					items:[{
						xtype:'displayfield',
						name:'productname',
					    fieldLabel:'产品名称',
					    flex:1.5,
					    renderer : function(val) {
								var me = this;
								if (!Ext.isEmpty(val)) {
									return "<span data-qtip='产品名称：" + val + "'>"
											+ Ext.String.ellipsis(val,40)  + "</span>";
								}
							}
					},{
					xtype:'displayfield',
					fieldLabel:'可用库存',
					name:'productbalance',
					value:'-'
					}]
					},{
					xtype:'displayfield',name:'productspec',
					fieldLabel:'规格'
					,margin:'10 0 10 10' 
						},{
					xtype : 'tbspacer'
					,margin:0 },{
						xtype : 'datefield',
						name : 'fmintime',
						format : 'Y-m-d',
//						value:new Date(),
						hidden:true,
						disabled:true
					}]
					}]
				}]
			},{
				xtype : 'fieldset',
				title : '<b>采购订单</b>',
				name : 'deliverapply',
				collapsible : false,
				fieldDefaults : {
					labelAlign : 'left',
					labelWidth : 80
				},
				items : [{

					xtype : 'fieldset',
					title : '采购订单号',
					name : 'deliverapply',
					collapsible : true,
					
					collapsed : true,
//					fieldDefaults : {
//						labelAlign : 'left',
//						labelWidth : 80
//					},
					items : [{
						anchor : "50%",
						xtype : 'textfield',
						fieldLabel : '采购订单号',
						listeners : {
							change : function(e, newValue, oldValue) {
								e
										.up().up()
										.down('textfield[fieldLabel=采购订单号][hidden=true]')
										.setValue(newValue);
							}
						}
					}]

				}, {
					xtype : 'form',
					layout : "column",
					ctype : "Deliverapply",
					baseCls : "x-plain",
					items : [{
						anchor : "50%",
						xtype : 'textfield',
						fieldLabel : '采购订单号',
						name : 'fordernumber',
						hidden : true
					}, {
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
								minValue : 1,

								listeners : {

									blur : function(com, The, eOpts) {

										var win = com.up('window');

										var num = com.getValue();

										var deliveryF = win
												.down('datefield[name=farrivetime]');

										if (win.trueStock != false
												&& (win.trueStock - num) >= 0) {

//											deliveryF.setMinValue(Ext.Date.add(
//													new Date(new Date()),
//													Ext.Date.DAY, 0));
													
											deliveryF.setValue(Ext.Date.add(
													new Date(new Date()),
													Ext.Date.DAY, 1));
//
										} else {
////											if(win.down('combobox[name=fsupplierid]').getRawValue()=='东经'){
////												deliveryF.setMinValue(Ext.Date.add(
////													new Date(new Date()),
////													Ext.Date.DAY, 2));
////
												deliveryF.setValue(Ext.Date.add(
													new Date(new Date()),
													Ext.Date.DAY, win.fdefaultday));
//											}
										}

									}

								}

							}, {
								anchor : "95%",
								xtype : 'combobox',
								fieldLabel : '制造商',
								hidden : true,
								value : '1234',
								name : 'fsupplierid',
								itemId : 'supplier',
								valueField : 'fid',
								displayField : 'fname',
								editable : false,
								allowBlank : false,
								blankText : '请选择制造商！',
								enterIndex : 1,
								store : Ext.create('Ext.data.Store', {
									fields : ['fid', 'fname'],
									proxy : {
										type : 'ajax',
										url : 'getSupplierForDeliverApply.do',
										reader : {
											type : 'json',
											root : 'data'
										}
									}
								}),
								listeners : {
									afterrender : function() { // 自动设置制造商
										var me = this, store = me.getStore();
										store.load({
											callback : function(records,
													operation, success) {
												if (success
														&& records.length == 1) {
													me.setValue(records[0]);
													me.fireEvent('select', me,
															records[0])
													me.setEditable(false);
												}
											}
										});
									},
									select : function(com, record) {
										var mystocksupplier = com
												.up("window")
												.down("fieldset[name=mystock]")
												.down("combo[name=fsupplierid]");
										if (Ext.isEmpty(mystocksupplier
												.getValue())) {
											mystocksupplier.setValue(record);
											mystocksupplier.setEditable(false);
										}

									}
								}
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
//									minValue : Ext.Date.add(
//											new Date(new Date()), Ext.Date.DAY,
//											2),
									minValue:new Date()
//									,
//									value : Ext.Date.add(new Date(new Date()),
//											Ext.Date.DAY, 5)
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
									var bbar = this.picker
											.down('toolbar[dock=bottom]');
									if (bbar.items.length == 13) {
										bbar.add(bbar.items.length - 1, {
											xtype : 'button',
											text : '<font color=blue>+新增地址</font>',
											width : 100,
											handler : function() {
												var editui = Ext
														.getCmp("DJ.System.UserAddressEdit");
												var customerFiled = Ext
														.getCmp("DJ.quickOrder.OrderHolderPlanEditer")
														.down('textfield[name=fcustomerid]');
												if (editui == null) {
													editui = Ext
															.create('DJ.System.UserAddressEdit',{
																isQuickOrder:true
															});
												}
												editui.seteditstate("add");
												Ext
														.getCmp("DJ.System.UserAddressEdit")
														.down('cCombobox[name=fcustomerid]')
														.setReadOnly(true);
												editui.show();
												editui.getform().form
														.findField("fcustomerid")
														.setmyvalue("\"fid\":\""
																+ customerFiled
																		.getValue()
																+ "\",\"fname\":\""
																+ customerFiled
																		.getRawValue()
																+ "\"");
												editui.on('close', function() {
													me.expand();
													me.picker.store.load();
												})
											}
										}, {
											xtype : 'tbfill'
										})
									}
									var custmoerid = this.up('window').customerID;

									this.setDefaultfilter([{
										myfilterfield : "cd.fcustomerid",
										CompareType : "like",
										type : "string",
										value : custmoerid
									}]);

									this.setDefaultmaskstring(" #0 ");

									// this.picker.store.on('beforeload',function(store){
									//							
									// store.getProxy().setExtraParam('fcustomerid',
									// fcustomer);
									// })
								},
								listeners : {
									afterrender : function() {
										var me = this;
										var custmoerid = me.up('window').customerID;
										Ext.Ajax.request({
											timeout : 60000,
											url : "getQuickUserDefaultAddress.do",
											params : {"fcustomerid" :  custmoerid},
											success : function(response, option) {
												var obj = Ext
														.decode(response.responseText);
												if (obj.success) {
													var config;
													if (obj.data) {
														if (obj.data.length == 1) {
															var record = obj.data[0];
															config = {
																fid : record['fid'],
																fname : record['fname']
															};
															me
																	.setmyvalue(config);
														}else{
															var p = me.getPicker();
															p.store.on('load',function(store,records){
																if(records.length==1){
																	config = {
																		fid : records[0].get('fid'),
																		fname : records[0].get('fname')
																	};
																	me.setmyvalue(config);
																}
															})
															p.store.load();
													
														}
													}else{
														var p = me.getPicker();
														p.store.on('load',function(store,records){
															if(records.length==1){
																config = {
																	fid : records[0].get('fid'),
																	fname : records[0].get('fname')
																};
																me.setmyvalue(config);
															}
														})
														p.store.load();
													}
												}
											}
										});
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
			}, {

				// stateful : true,
				//
				// stateId : 'DJ.quickOrder.OrderHolderPlanEditerstateId',
				// stateEvents : ['collapse'],

				xtype : 'fieldset',
				title : '<b>备货计划</b>',
				name : "mystock",
				collapsible : true,
//				collapsed:true,
				fieldDefaults : {
					labelAlign : 'left',
					labelWidth : 80
				},
				items : [{

					xtype : 'fieldset',
					title : '采购订单号',
					name : 'deliverapply',
					collapsible : true,
					
					collapsed : true,
					
//					fieldDefaults : {
//						labelAlign : 'left',
//						labelWidth : 80
//					},
					items : [{
						anchor : "50%",
						xtype : 'textfield',
						fieldLabel : '采购订单号',
						listeners : {
							change : function(e, newValue, oldValue) {
								e
										.up().up()
										.down('textfield[fieldLabel=采购订单号][hidden=true]')
										.setValue(newValue);
							}
						}
					}]

				}, {
					xtype : 'form',
					layout : "column",
					baseCls : "x-plain",
					ctype : "Mystock",
					items : [{
						anchor : "50%",
						hidden : true,
						xtype : 'textfield',
						fieldLabel : '采购订单号',
						name : 'fpcmordernumber'
					}, {
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
								anchor : "95%",
								hidden : true,
								value : '1234',
								xtype : 'combobox',
								fieldLabel : '制造商',
								name : 'fsupplierid',
								itemId : 'supplier',
								valueField : 'fid',
								displayField : 'fname',
								editable : false,
								allowBlank : false,
								enterIndex : 1,
								store : Ext.create('Ext.data.Store', {
									fields : ['fid', 'fname'],
									proxy : {
										type : 'ajax',
										url : 'getSupplierForDeliverApply.do',
										reader : {
											type : 'json',
											root : 'data'
										}
									}
								})
							}, {
								anchor : "100%",
								fieldLabel : '首次发货',
								xtype : 'datefield',
								name : 'ffinishtime',
								listeners:{
									afterrender:function(me){
										me.up('fieldset[name=mystock]').collapse();
									}
								},
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

function formatEffect(value) {
	return value == '1' ? '是' : '否';
}