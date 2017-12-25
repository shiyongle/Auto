Ext.app.QuickOrderHolderFields = [
		'fid',
		'fordernumber',
		'fsuppliername',
		'fsupplierid',
		{
			name : 'ftype',
			type : 'int',
			defaultValue : 0
		},
		'cutpdtname',
		'fcusproductid',
		'fcustproductid',
		'fspec',
		'fcustomerid',
		'famount',
		'fstock',
		'cutpdtname',
		'fplanamount',
		'faveragefamount',
		'fdays',
		{
			name: 'ffinishtime',
			convert: function(val){
				if(!val){
					return Ext.Date.format(Ext.Date.add(new Date(),Ext.Date.DAY, 7), 'Y-m-d');
				}
				return val;
			}
		},
		{
			name: 'fconsumetime',
			convert: function(val){
				if(!val){
					return Ext.Date.format(Ext.Date.add(new Date(),Ext.Date.MONTH, 1), 'Y-m-d');
				}
				return val;
			}/*,
			defaultValue: Ext.Date.format(Ext.Date.add(new Date(),Ext.Date.MONTH, 1), 'Y-m-d')*/
		},
		{
			name: 'faveragefamount',
			type: 'int',
			defaultValue: 0
		},
		{
			name : 'farrivetime',
			type : 'date'

		}, 'flinkman', 'flinkphone', 'fdescription', 'faddress', 'faddressid',
		{
			name : 'fseries',
			type : 'boolean',
			defaultValue : false

		}, {

			name : 'fboxmodel',
			type : 'int',
			defaultValue : 0

		}, 'fmaterialfid'];

Ext.define('DJ.quickOrder.QuickOrderHolder', {
	extend : 'Ext.c.BaseEditUI',

	requires : ['Ext.button.Button'],

	id : 'DJ.quickOrder.QuickOrderHolder',

	modal : true,

	url : 'SaveQuickbatchDeliverapply.do',

	closeAction : 'hide',

	width : 1350,
	
	title : '购物车',

	resizable : false,

	
	
	bbar: ['',{
		xtype: 'checkbox',
		boxLabel: '全选',
		handler: function(me,selected){
			var win = this.up('window'),
				m1 = win.table1.getSelectionModel(),
				m2 = win.table2.getSelectionModel();
			if(selected){
				m1.selectAll();
				m2.selectAll();
			}else{
				m1.deselectAll();
				m2.deselectAll();
			}
		}
	},{
		xtype: 'component',
		html: '<img width="27" height="23" style="vertical-align:middle;" src="images/garbage.png"/>&nbsp;移出购物车',
		style: 'cursor:pointer;margin-left:20px;',
		listeners: {
			afterrender: function(){
				var win = this.up('window');
				this.getEl().on('click',function(){
					this.highlight();
					var table1 = win.table1,
						table2 = win.table2,
						selec1 = table1.getSelectionModel().getSelection(),
						selec2 = table2.getSelectionModel().getSelection();
					if(selec1.length==0&&selec2.length==0){
						return;
					}
					Ext.Ajax.request({
						url: 'deleteCusPrivateDelivers.do',
						params: {
							fid: Ext.Array.map(selec1.concat(selec2),function(model){return model.get('fid')}).join(',')
						},
						success: function(res){
							var obj = Ext.decode(res.responseText);
							if(obj.success){
								table1.getStore().remove(selec1);
								table2.getStore().remove(selec2);
							}else{
								Ext.Msg.alert('提示',obj.msg);
							}
						}
					});
				});
			}
		}
	}],
	custbar : [{
		 xtype: 'tbspacer', width: 30
	},{
		xtype: 'radiogroup',
		columns: [60,60],
		listeners: {
			change: function(me,newObj,oldObj){
				var win = this.up('window');
				Ext.Array.each(win.query('[orderType]'),function(field){
					field.setVisible(field.orderType==newObj.type);
				});
			}
		},
		items: [
	        {boxLabel: '要货', name: 'type', inputValue: '1', checked: true},
            {boxLabel: '备货', name: 'type', inputValue: '2'}
		]
	}, {
		xtype : 'tbspacer',
		width : 50
	}, {

		xtype : 'textfield',
		fieldLabel : '统一订单号',
		labelWidth : 70,
		emptyText : '按回车键结束',
		enableKeyEvents : true,
		myAction : function(me, The, eOpts) {
			var win = me.up('window'),
				store;
			store = win.table1.getStore();
			store.each(function(model) {
				if (model.data.fordernumber == '') {
					model.set('fordernumber', me.value);
				}
			})
			store = win.table2.getStore();
			store.each(function(model) {
				if (model.data.fpcmordernumber == '') {
					model.set('fpcmordernumber', me.value);
				}
			})
		},

		listeners : {
			blur : function(me, The, eOpts) {
				var store = this.up('window').table1.getStore();
				store.each(function(model) {
					if (model.data.fordernumber == ''
							|| model.data.mystock == '') {
						model.set('fordernumber', me.value);
					}
				})
			},
			keypress : function(me, e, eOpts) {
				if (e.getKey() == 13) {
					var store = this.up('window').table1.getStore();
					store.each(function(model) {

						model.set('fordernumber', me.value);

					})
				}
			}
		}

	},{
		xtype : 'tbspacer',
		flex : 1
	}, {

		xtype : 'datefield',
		fieldLabel : '统一交期',
		format : 'Y-m-d',
		orderType: 1,
		// initComponent : function() {
		// this.callParent();
		// },
		// itemId : 'time',
		// id : 'timeForArrivetime',
		editable : false,
		labelWidth : 60,

		minValue : new Date(),

		listeners : {
			select : function(field, val) {
				var store = this.up('window').down('cTable').getStore();
				store.each(function(record) {
					record.set('farrivetime', val);
				})
			}
		}

	}, {

		xtype : 'radiogroup',
		orderType: 1,
		// flex : 1,
		width : 100,
		items : [{
			name : 'fseries',
			xtype : 'radiofield',
			boxLabel : '上午',

			listeners : {

				change : function(com, newValue, oldValue, eOpts) {

					if (!newValue) {

						return;

					}

					var store = this.up('window').down('cTable').getStore();
					store.each(function(record) {
						record.set('fseries', true);
					});
				}

			}

		}, {
			name : 'fseries',
			xtype : 'radiofield',
			boxLabel : '下午',

			listeners : {

				change : function(com, newValue, oldValue, eOpts) {

					if (!newValue) {

						return;

					}

					var store = this.up('window').down('cTable').getStore();
					store.each(function(record) {

						record.set('fseries', false);
					});

				}

			}

		}]

	}, {
		xtype : 'tbspacer',
		width : 50
	}, {

		fieldLabel : '统一地址',
		xtype : 'cCombobox',
		orderType: 1,
		width : 200,

		labelWidth : 60,
		// flex : 1,

		// enterIndex : 22,
		displayField : 'fname',
		valueField : 'fid',
		// allowBlank : false,
		blankText : '请选择送货地址',

		beforeExpand : function() {

			var me = this;

			var grid = Ext.getCmp('DJ.quickOrder.QuickOrderHolder.cTable');

			if (grid.getStore().data.length == 0) {

				return;

			}

			var win = me.up("window");

			var customerID = grid.getStore().data.items[0].get('fcustomerid');
			//
			// me.setDefaultfilter([{
			// myfilterfield : "cd.fcustomerid",
			// CompareType : "like",
			// type : "string",
			// value : customerID
			// }]);
			//
			// me.setDefaultmaskstring(" #0 ");

			var bbar = this.picker.down('toolbar[dock=bottom]');
			if (bbar.items.length == 13) {
				bbar.add(bbar.items.length - 1, {
					xtype : 'button',
					text : '<font color=blue>+新增地址</font>',
					width : 100,
					handler : function() {
						var editui = Ext.getCmp("DJ.System.UserAddressEdit");
						if (editui == null) {
							editui = Ext.create('DJ.System.UserAddressEdit', {
								isQuickOrder : true
							});
						}
						editui.seteditstate("add");
						Ext.getCmp("DJ.System.UserAddressEdit")
								.down('cCombobox[name=fcustomerid]')
								.setReadOnly(true);
						editui.show();
						editui.getform().form
								.findField("fcustomerid")
								.setmyvalue("\"fid\":\"" + customerID
										+ "\",\"fname\":\"" + customerID + "\"");
						editui.on('close', function() {
							me.expand();
							me.picker.store.load();
						})
					}
				}, {
					xtype : 'tbfill'
				})
			}

			var myfilter = [];
			myfilter.push({
				myfilterfield : 'cd.fcustomerid',
				CompareType : "=",
				type : "string",
				value : customerID
			});

			maskstring = "#0";

		},

		listeners : {

			select : function(combo, records) {
				var record = records[0], store = this.up('window')
						.down('cTable').getStore();
				this.up('window').down('cTable').chooseRecord.faddressComb = record;
				store.each(function(model) {
					model.set('faddressid', record.data.fid);
					model.set('faddress', record.data.fname);
					model.set('flinkphone', record.data.fphone);
					model.set('flinkman', record.data.flinkman);
				})
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

	}],

	statics : {

		doMySubmit : function(win) {
			var tables = Ext.ComponentQuery.query('cTable', win),
				data = {},i;
			for (var i = 0; i < tables.length; i++) {
				data[tables[i].name] = Ext.encode(tables[i].getcvalues());
			};
			Ext.Ajax.request({
				url : 'SaveCusPrivateDeliversForHolder.do',
				timeout : 60000,
				params : data,
				failure: function(){
					Ext.Msg.alert('提示','与服务器连接中断，请刷新重试！');
				}
			});
		},
		openOperationWin:function(){
			var container = Ext.getCmp('DJ.quickOrder.QuickOrderHolder.OpeWin') ||
			Ext.create('Ext.container.Container',{
				id: 'DJ.quickOrder.QuickOrderHolder.OpeWin',
				floating: true,
				width: 300,
				height: 130,
				modal: true,
				style: 'background: #efefef;border:1px solid #ccc;box-shadow:0 0 6px #888;z-index:99999',
				items: [{
					xtype: 'component',
					html: '保存成功',
					style: 'font-weight:bold;font-size:16px;line-height:60px;text-align:center;margin-bottom:20px;'
				},{
					xtype: 'component',
					style: 'text-align:center;font-size:14px;font-weight:bold;',
					html: '<a href="#" onclick="Ext.getCmp(\'DJ.quickOrder.QuickOrderHolder.OpeWin\').operation1()" style="margin-right:60px;" class="quick-order-holder">继续下单</a><a href="#" onclick="Ext.getCmp(\'DJ.quickOrder.QuickOrderHolder.OpeWin\').operation2()" class="quick-order-holder">查看订单详情</a>'
				}],
				renderTo: document.body,
				operation1: function(){
					this.hide();
				},
				operation2: function(){
					var subTabPanel;
					this.hide();
					if(!(subTabPanel = Ext.getCmp('DJ.quickOrder.orderInfo.OrdInfoAndPrepareGoodsPanel'))){
						subTabPanel = Ext.create('DJ.quickOrder.orderInfo.OrdInfoAndPrepareGoodsPanel');
						MainTabPanel.add(subTabPanel);
					}
					MainTabPanel.setActiveTab(subTabPanel); // 设置当前tab页
					MainTabPanel.doLayout(true);
					subTabPanel.down('grid').store.loadPage(1);
				}
			});
			return container;
		}

	},
	
	reset: function(){
		this.down('radio[boxLabel=备货]').show();
		this.down('radiogroup').setValue({type:"1"});
	},
	
	cautoverifyinput : false,

	remoteStore : new Ext.data.JsonStore({
		// store configs
		// storeId : 'myStore',

		proxy : {
			type : 'ajax',
			url : 'GetCusprivatedeliversByuser.do',
			reader : {
				type : 'json',
				root : 'data'
			}
		},

		// alternatively, a Ext.data.Model name can be given (see Ext.data.Store
		// for an example)
		fields : Ext.app.QuickOrderHolderFields
	}),

	layout : 'fit',

	listeners : {


		beforehide : function(com, eOpts) {

			DJ.quickOrder.QuickOrderHolder.doMySubmit(com);

		}

	},

	editstate : 'add',

	customerID : '',

	stock : 0,

	onload : function() {

		var me = this;

		var saveBtn = Ext.getCmp('DJ.quickOrder.QuickOrderHolder.savebutton');

		saveBtn.setText('下单');

	},

	Action_BeforeSubmit : function() {
		var store = this.table1.getStore(),
			store2 = this.table2.getStore();
		if (store.count==0 && store2.count==0) {
			throw '没有数据，无法保存！';
		}
		store.each(function(record) {
			var stock = parseInt(record.get('fstock')) || 0,
				amount = parseInt(record.get('famount')),
				arrivetime = record.get('farrivetime'),
				allowTime;
			if (!arrivetime) {
				throw '配送时间不能为空！';
			} else if (!amount) {
				throw '配送数量不能为空！';
			} else if (!record.get('faddressid')) {
				throw '地址不能为空！';
			}
			if(stock<amount){
				allowTime = Ext.Date.clearTime(Ext.Date.add(new Date(), Ext.Date.DAY, parseInt(record.get('fdays'))));
				if(Ext.Date.clearTime(arrivetime).getTime()<allowTime.getTime()){
					throw '产品"'+record.get('cutpdtname')+'"的交期不能在'+Ext.Date.format(allowTime,'Y-m-d')+'之前,请更改！';
				}
			}
		});
		store2.each(function(record){
			if(!record.get('fconsumetime')){
				throw '备货的备货周期不能为空！';
			}else if(!record.get('ffinishtime')){
				throw '备货的首次发货时间不能为空！';
			}else if(!record.get('fplanamount') || record.get('fplanamount')=='0'){
				throw '备货的计划数量不能为空！';
			}
		});
	},
	Action_Submit : function(me) {
		var editui = me;
		var myform = editui.getform();
		var new_params = {};
		new_params[me.table1.name] = Ext.encode(me.table1.getcvalues());
		new_params[me.table2.name] = Ext.encode(me.table2.getcvalues());
		myform.submit({
			url : me.url,
			method : "POST",
			waitMsg : "正在处理请求……",
			timeout : 60000,
			params : new_params,
			success : function(form, action) {
				me.hide();
				DJ.quickOrder.QuickOrderHolder.openOperationWin().show();
			},
			failure : function(f, action) {
				var obj = Ext.decode(action.response.responseText);
				Ext.MessageBox.alert("提示", obj.msg);
			}
		});
	},
	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [{
				xtype : 'cTable',
				itemId: 'table1',
				id : 'DJ.quickOrder.QuickOrderHolder.cTable',
				name : 'Deliverapply',
				selModel : Ext.create('Ext.selection.CheckboxModel'),// 'checkboxmodel',
				orderType: '1',
				chooseRecord : {},

				isFirstClick : true,

				height : 300,

				/**
				 * 隐藏工具栏
				 */
				onload : function() {

					var me = this;

					var tb = me.getDockedItems()[0];

					tb.hide();

				},
				plugins : [

				{
					ptype : 'cellediting',
					clicksToEdit : 1,
					listeners : {
						beforeedit : function(editor, e) {

							if (e.record.get('fordertype') == 1) {

								return false;

							}

						},
						edit : function(editor, e) {

							var record = e.record, cRecord = e.grid.chooseRecord;

							// 是目标域才进行赋值,&& !e.grid.isFirstClick
							if (e.column.dataIndex == 'faddress'
									&& (cRecord != -1)) {
								record.set('faddressid',
										cRecord.faddressComb.data.fid);
								record.set('faddress',
										cRecord.faddressComb.data.fname);

							}

							e.record.commit();

						}
					}
				}],
				fields : Ext.app.QuickOrderHolderFields,

				columns : [


						{

							dataIndex : 'fid',
							hideable : false,
							hidden : true

						}, {

							dataIndex : 'cutpdtId',
							hideable : false,
							hidden : true

						},

						{
							dataIndex : 'fordernumber',
							text : '采购订单号',

							hidden : true,

							// flex:2,
							editor : {
								xtype : 'textfield',
								allowBlank : false
							// requires a non-empty value
							}
						},

						{

							text : '制造商',
							dataIndex : 'fsuppliername'

						}, {
							text : '类型',
							dataIndex : 'ftype',
							sortable : true,
							renderer : function(val) {
								return '要货';
							}
						}, {

							dataIndex : 'cutpdtname',
							text : '产品名称',
							sortable : true,
							width : 190,
							// flex:0.5,
							renderer : function(val, m) {
								var me = this;
								if (!Ext.isEmpty(val)) {
									// var record =
									// me.chooseRecord.cutpdtnameComb;
									// return "<span data-qtip='包装物名称："+val+",
									// 包装物编码："+record.get('fnumber')+",
									// 规格："+record.get('fspec')+",
									// 特性："+record.get('fcharactername')+"'>"+val+"</span>";
									return "<span data-qtip='产品名称：" + val
											+ ", 产品编码："
											+ m.record.get('cutpdtnumber')
											+ ", 规格：" + m.record.get('fspec')
											+ ", 特性："
											+ m.record.get('fcharactername')
											+ "'>" + val + "</span>";
								}
							}
						}, {
							'header' : '规格',
							width : 120,
							'dataIndex' : 'fspec',
							sortable : true
						},

						{
							'header' : '数量',
							width : 60,
							'dataIndex' : 'famount',
							sortable : true,
							allowBlank : false,
							editor : {
								xtype : 'numberfield',
								allowBlank : false,
								minValue : 1
							}
						}, {
							'header' : '交期',
							'dataIndex' : 'farrivetime',
							xtype : "datecolumn",
							format : "Y-m-d",
							width : 120,
							sortable : true,
							editor : {
								xtype : 'datefield',
								allowBlank : false,
								format : 'Y-m-d',
								allowBlank : false,
								minValue : new Date()
							}
						},

						{

							xtype : 'checkcolumn',

							dataIndex : 'fseries',

							text : '时段',

							// value : true,

							renderer : function(value) {

								var tip = '<font color="red">上午</font>';

								if (!value) {
									tip = '<font color="green">下午</font>';
								}
								return tip;
							}

						},

						{
							dataIndex : 'faddress',
							text : '地址',
							sortable : true,
							width : 200,
							renderer : function(val) {
								// var me = this;
								if (!Ext.isEmpty(val)) {
									return "<div><span data-qtip='地址：" + val
											+ "'>" + val + "</span></div>";
								}
							},
							editor : {

								// fieldLabel : '地址',
								xtype : 'cCombobox',
								name : 'faddressid',

								// id :
								// 'DJ.quickOrder.QuickOrderHolder.AddressCombox',

								flex : 1,

								// enterIndex : 22,
								displayField : 'fname',
								valueField : 'fid',
								allowBlank : false,
								blankText : '请选择地址',
								beforeExpand : function() {

									var me = this;

									var grid = Ext
											.getCmp('DJ.quickOrder.QuickOrderHolder.cTable');

									var records = MyCommonToolsZ
											.pickSelectItems(grid);

									if (records == -1) {

										return;

									}

									var win = me.up("window");

									var customerID = records[0]
											.get('fcustomerid');

									me.setDefaultfilter([{
										myfilterfield : "cd.fcustomerid",
										CompareType : "like",
										type : "string",
										value : customerID
									}]);

									me.setDefaultmaskstring(" #0 ");

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
												if (editui == null) {
													editui = Ext
															.create(
																	'DJ.System.UserAddressEdit',
																	{
																		isQuickOrder : true
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
																+ customerID
																+ "\",\"fname\":\""
																+ customerID
																+ "\"");
												editui.on('close', function() {
													var record = grid
															.getSelectionModel()
															.getSelection()[0];
													var p = grid.plugins[0];
													var task = new Ext.util.DelayedTask(function() {
														me.expand();
														me.picker.store.load();
													});
													if (p.startEdit(record, 11)) {
														task.delay(500);
													};
												})
											}
										}, {
											xtype : 'tbfill'
										})
									}

								},

								listeners : {

									select : function(combo, record) {

										Ext.getCmp('DJ.quickOrder.QuickOrderHolder.cTable').chooseRecord.faddressComb = record[0];
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

							}

						},
			

						{
							'header' : '备注',
							'dataIndex' : 'fdescription',
							sortable : true,
							renderer : function(val) {
								var me = this;
								if (!Ext.isEmpty(val)) {
									return "<span data-qtip='备注：" + val + "'>"
											+ val + "</span>";
								}
							},
							editor : {
								xtype : 'textfield',
								allowBlank : false
							// requires a non-empty value
							}
						},

						{

							header : '删除',
							xtype : 'actioncolumn',
							align: 'center',
							hideable : false,

							items : [{

								icon : 'images/delete.png',
								handler : function(grid, rowIndex, colIndex) {
									var storeT = grid.getStore();
									var rec = grid.getStore().getAt(rowIndex);
									storeT.remove(rec);
									var fid = rec.get('fid');
									MyCommonToolsZ.doSimpleAjaxAction('deleteCusPrivateDelivers.do', {fid : fid});
								}
							}]
						},{
							header: '可用库存',
							dataIndex: 'fstock',
							flex: 1,
							align: 'center'
						}
				]
			},{
				xtype: 'cTable',
				itemId: 'table2',
				orderType: '2',
				name: 'Mystock',
				hidden: true,
				height: 300,
				onload : function() {
					this.getDockedItems()[0].hide();
				},
				selModel : Ext.create('Ext.selection.CheckboxModel'),
				plugins : [{
					ptype : 'cellediting',
					clicksToEdit : 1,
					listeners : {
						beforeedit : function(editor, e) {
							if (e.record.get('fordertype') == 1) {
								return false;
							}
						},
						edit : function(editor, e) {
							/*var record = e.record, cRecord = e.grid.chooseRecord;
							e.record.commit();*/
						}
					}
				}],
				fields : Ext.app.QuickOrderHolderFields,
				columns: [{
					text: '制造商',
					dataIndex: 'fsuppliername',
					flex: 2
				},{
					text: '类型',
					dataIndex: 'ftype',
					renderer: function(){
						return '备货';
					},
					flex: 1 
				},{
					text: '产品名称',
					dataIndex: 'cutpdtname',
					flex: 2
				},{
					text: '规格',
					dataIndex: 'fspec',
					flex: 1
				},{
					text: '计划数量',
					dataIndex: 'fplanamount',
					flex: 1,
					editor: {
						xtype : 'numberfield',
						allowBlank : false
					}
				},{
					text: '平均发货量',
					dataIndex: 'faveragefamount',
					flex: 1,
					editor: {
						xtype : 'numberfield'
					}
				},{
					text: '首次发货时间',
					dataIndex: 'ffinishtime',
					xtype: 'datecolumn',
					format: 'Y-m-d',
					flex: 1,
					editor: {
						xtype : 'datefield',
						allowBlank : false,
						format: 'Y-m-d',
						minValue: Ext.Date.add(new Date(),Ext.Date.DAY, 2)
					}
				},{
					text: '备货周期',
					dataIndex: 'fconsumetime',
					xtype: 'datecolumn',
					format: 'Y-m-d',
					flex: 1,
					editor: {
						xtype : 'datefield',
						allowBlank : false,
						format: 'Y-m-d',
						maxValue : Ext.Date.add(new Date(),Ext.Date.MONTH, 1),
						minValue : Ext.Date.add(new Date(),Ext.Date.DAY, 2)
					}
				}]
			}]
		});
		me.callParent(arguments);
		me.table1 = me.down('cTable[itemId=table1]');
		me.table2 = me.down('cTable[itemId=table2]');
	}

});
