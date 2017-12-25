Ext.require(["Ext.app.MySimplePrinterXTemplateString",
		"Ext.app.MySimplePrinter", "MyApp.tools.MyDataHelper"]);
Ext.define('MyApp.store.StoreMultiRecord', {
	extend : 'Ext.data.Store',

	// requires : ['MyApp.model.MyModel'],

	headerAndDataIndex : {
		header : ['物料名称', '物料编码', '采购订单号', '数量', '实收数量', '备注'],
		headDataIndexs : ['proName', 'productNumber', 'purchaseOrderNo',
				'count', 'receivedCount', 'remark']
	},

	constructor : function(cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({

			fields : ['customerName', 'idSd', {

				name : 'billLadingNo',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
			}, {

				name : 'receiptNo',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
			},

			{

				name : 'productNumber',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
			},

			{

				name : 'vehicle',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
			}, {

				name : 'deliveryAddress',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
			},

			{

				name : 'supplierName',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
			}, {

				name : 'supplierNumber',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
			}, {

				name : 'createTime',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
			}, {
				name : 'proName',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}

				}
			}, {
				name : 'count',
				type : 'int',
				convert : function(v, rec) {

					if (v == 'null') {
						return " ";
					} else {
						var r = new Number(v);
						return r.toFixed(0);
					}

				}

			}, {
				name : 'receivedCount',
				convert : function(v, rec) {
					if (v == 'null') {
						return " ";
					} else {
						return v;
					}
				}
					// ,
					// type : 'int',
					// defaultValue : 0
					}, {
						name : 'unitPrice',
						convert : function(v, rec) {
							if (v == 'null') {
								return " ";
							} else {
								return v;
							}
						}
					// ,
					// type : 'float',
					// defaultValue : 0
					}, {
						name : 'sum',
						convert : function(v, rec) {
							if (v == 'null') {
								return " ";
							} else {
								return v;
							}
						}
					// ,
					// type : 'float',
					// defaultValue : 0
					}, {
						name : 'receivedCount',
						convert : function(v, rec) {
							if (v == 'null') {
								return " ";
							} else {
								return v;
							}
						}
					// ,
					// type : 'float',
					// defaultValue : 0
					}, {
						name : 'purchaseOrderNo',
						convert : function(v, rec) {
							if (v == 'null') {
								return " ";
							} else {
								return v;
							}
						}
					// ,
					// type : 'float',
					// defaultValue : 0
					}, {
						name : 'remark',
						convert : function(v, rec) {
							if (v == 'null') {
								return " ";
							} else {
								return v;
							}
						}
					// ,
					// type : 'float',
					// defaultValue : 0
					}],

			storeId : 'MyStoretCus',
			proxy : {
				url : 'getCusSaledeliverListByIds2.do',
				type : 'ajax',
				reader : {
					type : 'json',
					root : 'data'
				}

			}

		}, cfg)]);
	}
});
Ext.define('DJ.order.saleOrder.MyDeliveryList',{
	extend : 'Ext.c.GridPanel',
	title : "我的发货",
	id : 'DJ.order.saleOrder.MyDeliveryList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetMyDeliveryListMV.do',
	// Delurl : "DelDeliversList.do",
	Delurl : "",
	// EditUI : "DJ.order.Deliver.DeliversEdit",
	EditUI : "DJ.order.saleOrder.MyDeliveryEdit",
	exporturl : "ExportMyDelivery.do",// 导出为EXCEL方法
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	CREAT_TRUCKASSEMBLE_URL : "creatTruckassembleByCondition.do",
	onload:function(){
		this.down('button[text=新  增]').hide();
		this.down('button[text=修  改]').hide();
		this.down('button[text=删    除]').hide();
		var grid = this;
		grid.store.on('beforeload',function( store, operation, eOpts ){
			store.getProxy().timeout=600000;
			if(store.autoLoad==true){
				store.autoLoad=false;
				return false;
			}
		})
		
		
	},
	statics : {
//		DELIVERS_STATE : ["要货创建", "已接收", "已下单", "已分配", "已装配","部分发货", "已发货"]
		DELIVERS_STATE : ["要货创建", "已接收", "已下单", "已分配", "已入库","部分发货", "已发货","待发货"]
		
	},
	listeners:{
		itemdblclick:function(me, record, item, index, e, eOpts ){
			this.down('button[text=查   看]').handler();
		},
		afterrender:function(me){
			var com = me.down('combobox');
			com.setValue('fstate,0');
			me.down('button[text=查找]').handler();
		}
	},
	plugins: [
//	          Ext.create('Ext.grid.plugin.CellEditing', {
//	              clicksToEdit: 1,
//	              listeners:{
//	            	  beforeedit:function( editor, e, eOpts ){
//	            		  if(e.record.get('fstate')=='6'){
//	            			  return false;
//	            		  }
//	            	  }
//	              }
//	          })
	      ],
		doPrint : function(ids) {

			var cusStore = Ext.create("MyApp.store.StoreMultiRecord");

			var cusStoreHeaderAndDataIndex = cusStore.headerAndDataIndex;

			var data1 = {

				tableName : "",
				tableInterval : "0cm",
				pageSize : 10,
				border : "0",
				cellspacing : "0px", // 表格中行间距

				height : "100",

				style : "text-align:center;height:100;",

				countField : "count",
				priceField : "0",

				billLadingNo : "",
				receiptNo : "",
				customerName : "",
				vehicle : "",
				deliveryAddress : "",
				supplierName : "",

				supplierNumber : "",
				productNumber : ""
			};

			var me = this;

			cusStore.load({
				params : {
					fids : ids
				},
				// 当打印单子出现不足的现象时，可以*2调整
				limit : 5000,
				scope : me,
				callback : function(records, operation, success) {

					var obj = Ext
							.decode(operation.request.callback.arguments[2].responseText);
					if (obj.success == false) {
						Ext.MessageBox.alert('错误', obj.msg);
						return;
					}

					var modelsArray = [];

					var preidSd = " ";

					var models = [];

					cusStore.sort('idSd', 'DESC');

					cusStore.data.each(function(item, index, len) {

						if (index == 0) {
							preidSd = item.get("idSd");
						}

						// cusStore.data.items[index
						// + 1].data.idSd

						// 当订单号不同，
						if (preidSd != item.get("idSd")) {

							preidSd = item.get("idSd");
							// 第一个不加入，may be no use here
							if (models.length != 0) {

								var datat = Ext.clone(data1);

								var modelsT = Ext.Array.clone(models);

								modelsArray.push({
									models : modelsT,
									data2 : Ext
											.apply(datat,
													cusStore.data.items[index
															- 1].data)
								});

							}

							models.length = 0;

						}

						// 最后的时候单独判断
						if (index == len - 1) {
							if (preidSd == item.get("idSd")) {
								models.push(item.getData(false));

								var datat = Ext.clone(data1);

								var modelsT = Ext.Array.clone(models);

								modelsArray.push({
									models : modelsT,
									data2 : Ext.apply(datat, item
											.getData(false))
								});

							} else {
								var datat = Ext.clone(data1);

								var modelsT = Ext.Array.clone(models);

								modelsArray.push({
									models : modelsT,
									data2 : Ext
											.apply(datat,
													cusStore.data.items[index
															- 1].data)
								});

								models.length = 0;

								models.push(item.getData(false));

								var datat2 = Ext.clone(data1);

								var modelsT2 = Ext.Array.clone(models);

								modelsArray.push({
									models : modelsT2,
									data2 : Ext.apply(datat2, item
											.getData(false))
								});
							}
						}

						models.push(item.getData(false));

					});

					var newwin = window.open("", "printer");

					var ids = Ext.app.MySimplePrinter.buildIds([
							"outboundOrderHeader2", "stockOut",
							"outboundOrderFooter2"]);

					Ext.app.MySimplePrinter.setTpl(ids);

					Ext.each(modelsArray, function(item, index, all) {

						var data3 = MyApp.tools.MyDataHelper.buildDataByModels(
								item.data2, item.models,
								cusStoreHeaderAndDataIndex);

						Ext.app.MySimplePrinter.appendDataToPage(data3, newwin);

					});

					// 打印
					newwin.print();
					newwin.close();

					//					
					//
					// var data3 = MyApp.tools.MyDataHelper.buildDataByStore(
					// data2, cusStore, cusStoreHeaderAndDataIndex);
					//
					// data3.supplierName =
					// cusStore.getAt(0).get("supplierName");
					//
					// Ext.app.MySimplePrinter.doPrintByGridEasyer(data3, [
					// "outboundOrderHeader", "gridTest",
					// "outboundOrderFooter"]);
				}
			});
	},
	custbar:[{
		text : '自运发货',
		hidden:true,
		height : 30,
		handler : function() {
			var me = this.up('grid');
			var grid = Ext.getCmp("DJ.order.saleOrder.MyDeliveryList");
			var record = grid.getSelectionModel().getSelection();
			if(record.length<1){
				Ext.MessageBox.alert("信息","请选择至少一条记录导入EAS！");
				return;
			}
			var ids = "";
			for ( var i = 0; i < record.length; i++) {
				var fid = record[i].get("fid");
				ids +=  fid ;
				if (i < record.length - 1) {
					ids = ids + ",";
				}
			}
			
			if(record.length == 1){
				if(record[0].get("fouted")=="1" || record[0].get("fmatched")=="1" ){
					Ext.MessageBox.alert('错误', "该配送单已装配或已发货不能自运！");
					return;
				}
				var fid = record[0].get("fid");
				var amount = parseInt(record[0].get("famount"));
				var fassembleQty = record[0].get("fassembleQty")=="null"?0:parseInt(record[0].get("fassembleQty"));
				var RealAmountEdit = Ext.create('DJ.order.Deliver.RealAmountEdit');
				RealAmountEdit.parentid = 'DJ.order.saleOrder.MyDeliveryList';
				RealAmountEdit.show();
				Ext.getCmp("DJ.order.Deliver.RealAmountEdit.deliverorderid").setValue(fid);
				Ext.getCmp("DJ.order.Deliver.RealAmountEdit.famount").setValue(amount);
				Ext.getCmp("DJ.order.Deliver.RealAmountEdit.fassembleQty").setValue(fassembleQty);
				Ext.getCmp("DJ.order.Deliver.RealAmountEdit.frealqty").setValue(amount-fassembleQty);
			
			}else {
				var el = grid.getEl();
				el.mask("系统处理中,请稍候……");
				Ext.Ajax.request({
					timeout : 600000,
					url : "creatBatchTruckassembleByConditionNew.do",
//					url : me.CREAT_TRUCKASSEMBLE_URL,
					params : {
						fidcls : ids ,
						ftype : 0
					}, // 参数
					success : function(response, option) {
						var obj = Ext.decode(response.responseText);
						
						if (obj.success == true) {
							djsuccessmsg( obj.msg);
							Ext.getCmp("DJ.order.saleOrder.MyDeliveryList").store
							.load();
							Ext.Ajax.request({
								url : 'GainCfgByFkey.do',
								success : function(response, option) {
									var objs = Ext.decode(response.responseText);
									if (objs.success == true) {
										var saleDeliverid = obj.data[0].saledeliverID.split(',');
											Ext.MessageBox.confirm('打印界面', '是否立即打印出库单！',function(btn, text){
												if(btn=='yes'){
													for(var i =0;i<saleDeliverid.length;i++){
														if(saleDeliverid[i]!=''){
															me.doPrint(saleDeliverid[i]);
														}
													}
												}
											})
										}
									}
							})
//							var saleDeliverid = obj.data[0].saledeliverID.split(',');
//							if(obj.data[0].deliverordercls.match(/fouted=(\d)/)[1]!=0){//是否发货
//								Ext.MessageBox.confirm('打印界面', '是否立即打印出库单！',function(btn, text){
//									if(btn=='yes'){
//										for(var i =0;i<saleDeliverid.length;i++){
//											if(saleDeliverid[i]!=''){
//												me.doPrint(saleDeliverid[i]);
//											}
//											
//										}
//										
//									}
//								})
//							}
						} else {
							Ext.MessageBox.alert('错误', obj.msg);
						}
						el.unmask();
					}
				});
			}
		}
	},{
		text : '协同发货',
		height : 30,
		handler : function() {
			var grid = Ext.getCmp("DJ.order.saleOrder.MyDeliveryList");
			var record = grid.getSelectionModel().getSelection();
			if(record.length<1){
				Ext.MessageBox.alert("信息","请选择至少一条记录导入EAS！");
				return;
			}
			var ids = "";
			for ( var i = 0; i < record.length; i++) {
				var fid = record[i].get("fid");
				ids += fid ;
				if (i < record.length - 1) {
					ids = ids + ",";
				}
			}
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "deliverorderImportEAS.do",
				params : {
					fidcls : ids
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						Ext.MessageBox.alert('成功', obj.msg);
						Ext.getCmp("DJ.order.saleOrder.MyDeliveryList").store
								.load();
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
		}
	},{
		xtype:'combobox',
		id:'DJ.order.saleOrder.MyDeliveryList.fstate',
		displayField: 'name',
	    valueField: 'value',
	    editable:false,
	    value:'1,1',
		store:Ext.create('Ext.data.Store', {
		    fields: ['name', 'value'],
		    data : [
				{"name":"纸箱订单", "value":"fboxtype,0"},
				{"name":"纸板订单", "value":"fboxtype,1"},
		        {"name":"待发货", "value":"fstate,0"},
		        {"name":"部分发货", "value":"fstate,1"},
		        {"name":"已发货", "value":"fstate,2"},
		        {"name":"全部", "value":"1,1"}
		        
		        //...
		    ]
		}),
		listeners:{
			select:function( combo, records, eOpts){
				combo.nextSibling('[text=查找]').handler();
			}
		}
	},{
		xtype:'textfield',
		id:'DJ.order.saleOrder.MyDeliveryList.textfield',
		enableKeyEvents:true,
		listeners:{
			render:function(){
				Ext.tip.QuickTipManager.register({
				    target: 'DJ.order.saleOrder.MyDeliveryList.textfield',
				    text: '可输入制造订单号、配运单号、客户名称、包装物名称、采购订单号',
				    dismissDelay: 10000 // Hide after 10 seconds hover
				});
			},
			keypress:function( me, e, eOpts ){
				if(e.getKey()==13){
					me.nextSibling('[text=查找]').handler();
				}
			}
		}
	},{
		text:'查找',
		handler:function(){
			var combox = Ext.getCmp('DJ.order.saleOrder.MyDeliveryList.fstate').getValue();
			combox = combox.split(',');
			var value = Ext.getCmp('DJ.order.saleOrder.MyDeliveryList.textfield').getValue();
			var store = Ext.getCmp("DJ.order.saleOrder.MyDeliveryList").getStore();
			var myfilter = [{
				myfilterfield : combox[0],
				CompareType : '=',
				type : "int",
				value : combox[1]
			},{
				myfilterfield : '_productname',
				CompareType : "like",
				type : "string",
				value : value
			},{
				myfilterfield : '_custname',
				CompareType : "like",
				type : "string",
				value : value
			},{
				myfilterfield : 'fordernumber',
				CompareType : "like",
				type : "string",
				value : value
			},{
				myfilterfield : 'fnumber',
				CompareType : "like",
				type : "string",
				value : value
			},{
				myfilterfield : 'fpcmordernumber',
				CompareType : "like",
				type : "string",
				value : value
			}];
			store.setDefaultfilter(myfilter);
			store.setDefaultmaskstring("#0 and (#1 or #2 or #3 or #4 or #5)");
			store.loadPage(1);
		}
	},{
		text:'自运发货',
		handler:function(){
			var me = this;
			var record = this.up('grid').getSelectionModel().getSelection();
			if(record.length<1){
				Ext.Msg.alert('提示','请选择数据!');
				return;
			}else if(this.up('grid').store.data.items.length == record.length&&record.length>1){
				me.up().down('button[text=自运发货]').handler();
				return;
			}
			var win = Ext.create('Ext.Window', {
						width : 500,
						height : 300,
						title:'实配数量',
						layout:'fit',
						modal:true,
						tbar:[{
							text:'快速增加',
							handler:function(){
								var addwin = Ext.create('Ext.Window',{
									width : 550,
									height : 500,
									title:'快速增加出库产品',
									modal:true,
									layout:'fit',
									items:Ext.create('DJ.order.saleOrder.AddProductplanList')
								}).show();
								addwin.on('close',function(){
									Ext.getCmp('DJ.order.saleOrder.AddProductplanList').close();
								})
							}
						},{
							text:'删除',
							handler:function(){
								var records = this.up('window').down('grid').getSelectionModel().getSelection();
								if(records.length<1){
									Ext.Msg.alert('提示','请选择数据！');
									return;
								}
								this.up('window').down('grid').store.remove(records);
							}
						}],
						dockedItems : [ {
							xtype : 'toolbar',
							dock : 'bottom',
							ui: 'footer',
							layout: {
							    pack: 'center'
							},
							items:[{
								text:'确定',
								minWidth:60,
								minHeight:20,
								handler:function(){
									var m = this;
									var deliverorder = [];
									if(this.up('window').down('grid').store.getCount()==0){
										Ext.Msg.alert('提示','添加数据！');
										return;
									}
									Ext.each(this.up('window').down('grid').store.data.items,function(store){
										deliverorder.push(store.data);
									})
									
									var el = this.up('window').getEl();
									el.mask("系统处理中,请稍候……");
									Ext.Ajax.request({
//										url:'creatBatchTruckassembleByCondition.do',
										url:'creatBatchTruckassembleByConditionNew.do',
										timeout:600000,
										params:{'Deliverorder':Ext.encode(deliverorder),'ftype':1},
										success:function(response){
											var obj = Ext.decode(response.responseText);
											if(obj.success==true){
												el.unmask();
												djsuccessmsg(obj.msg);
												me.up('grid').store.load();
												m.up('window').close();
												Ext.MessageBox.confirm('打印界面', '是否立即打印出库单！',function(btn, text){
															if(btn=='yes'){
																me.up('grid').doPrint(obj.saledeliverIDs);
															}
												})
											}else{
												el.unmask();
												Ext.Msg.alert('提示',obj.msg);
											}
										}
									})
								}
								},{
								text:'取消',
								minWidth:60,
								minHeight:20,
								handler:function(){
									this.up('window').close();
								}
							}]
						}],
						items : Ext
								.create('DJ.order.saleOrder.BatchRealAmountEdit')
					});
			win.show();
			var store = Ext.getCmp('DJ.order.saleOrder.BatchRealAmountEdit').store;
			store.on('beforeload',function(store, operation, eOpts){
				store.loadRecords(record);
			})
			win.on('close',function(){
//				me.up('grid').store.load();
				var record = me.up('grid').getSelectionModel().getSelection();
				Ext.each(record,function(rec){
					rec.reject();
				})
			})
			
		}
	}],
	fields:[{
		name : 'fid'
	}, {
		name : 'fordernumber',
		myfilterfield : 'fordernumber',
		myfiltername : '制造商订单号',
		myfilterable : true
	}, {
		name : 'fnumber',
		myfilterfield : 'fnumber',
		myfiltername : '配送单号',
		myfilterable : true
	}, {
		name : 'productname',
		myfilterfield : '_productname',
		myfiltername : '包装物名称',
		myfilterable : true
	}, {
		name : 'faddress',
		myfilterfield : 'faddress',
		myfiltername : '配送地址',
		myfilterable : true
	}, {
		name : 'fcreatorid'
	}, {
		name : 'fupdateuserid'
	}, {
		name : 'fcustomerid'
	}, {
		name : 'fcusproductid'
	}, {
		name : 'fcustname',
		myfilterfield : '_custname',
		myfiltername : '客户名称',
		myfilterable : true
	}, {
		name : 'cutpdtname',
		myfilterfield : '_custpdtname',
		myfiltername : '客户产品',
		myfilterable : true
	},{
		name : 'ftype'
	}, {
		name : 'flinkman',
		myfilterfield : 'flinkman',
		myfiltername : '联系人',
		myfilterable : true
	}, {
		name : 'flinkphone',
		myfilterfield : 'flinkphone',
		myfiltername : '联系电话',
		myfilterable : true
	}, {
		name : 'famount'
	}, {
		name : 'fdescription'
	}, {
		name : 'farrivetime',
		myfilterfield : 'farrivetime',
		myfiltername : '配送时间',
		myfilterable : true
	}, {
		name : 'fcreator'
	}, {
		name : 'flastupdater'
	}, {
		name : 'fcreatetime'
	}, {
		name : 'fupdatetime'
	}, {
		name : 'fordered'
	}, {
		name : 'fordermanid'
	}, {
		name : 'fordertime'
	}, {
		name : 'forderman'
	}, {
		name : 'fsaleorderid'
	}, {
		name : 'forderentryid'
	}, {
		name : 'fimportEAS',
		myfilterfield : 'fimportEAS',
		myfiltername : '是否导入',
		myfilterable : true
	}, {
		name : 'faudittime'
	}, {
		name : 'fauditor'
	},  {
		name : 'faudited'
	}, {
		name : 'fouted',
		myfilterfield : 'fouted',
		myfiltername : '是否发货',
		myfilterable : true
	}, {
		name : 'fmatched',
		myfilterfield : 'fmatched',
		myfiltername : '是否配货',
		myfilterable : true
	}, {
		name : 'fassembleQty'
	}, {
		name : 'foutQty'
	},'fpcmordernumber','cfspec','pfnumber','fstate','fouttime','fdelivertype','fboxtype','fmaterial','fbalanceqty','sname','custpdtspec'],
	columns:[{
		text:'序号',
		xtype:'rownumberer',
		width:40
	}, {
		'header' : '导入',
		dataIndex : 'fimportEAS',
		sortable : true,
		hidden : true,
		width : 40,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	}, {
		'header' : '制造商',
		width : 100,
		'dataIndex' : 'sname',
		sortable : true
	}, {
		'header' : '制造商订单号',
		width : 100,
		'dataIndex' : 'fordernumber',
		sortable : true
	}, {
		'header' : '配送单号',
		width : 100,
		'dataIndex' : 'fnumber',
		sortable : true
	}, {
		'header' : '订单类型',
		'dataIndex' : 'fboxtype',
		sortable : true,
		renderer:function(val){
			return val=='1'?'纸板订单':'纸箱订单';
		}
	}, {
		'header' : '客户名称',
		width : 100,
		'dataIndex' : 'fcustname',
		sortable : true
		}, {
		'header' : '采购订单号',
		width : 100,
		'dataIndex' : 'fpcmordernumber',
		sortable : true
		
	}, {
		'header' : '包装物编号',
		'dataIndex' : 'pfnumber',
		sortable : true
	}, {
		'header' : '包装物名称',
		width : 170,
		'dataIndex' : 'productname',
		sortable : true
	}, {
		'header' : '纸箱规格',
		'dataIndex' : 'cfspec',
		sortable : true
	}, {
		'header' : '下料规格',
		'dataIndex' : 'fmaterial',
		sortable : true
	}, {
		'header' : '配送数量',
		'dataIndex' : 'famount',
		sortable : true
	}, {
		'header' : '实际配送数量',
		'dataIndex' : 'fassembleQty'
//		,
//		editor:{
//			xtype:'numberfield'
//		}
	},{
		'header' : '库存数量',
		'dataIndex' : 'fbalanceqty'
	}, {
		'header' : '订单来源',
		'dataIndex' : 'fdelivertype',
		sortable : true,
		renderer:function(val){
			return val==1?'客户订单':'平台订单';
		}
	}, {
		'header' : '发货类型',
		dataIndex : 'fdelivertype',
		sortable : true,
		renderer: function(value){
	        if (value == 1) {
	        	return '自运发货';
	           
	        }
	        else if(value == 2){
	        	 return '协同发货';
	        }else{
	        	return '';
	        }
	        
	    }
	}, {
		'header' : '配送时间',
		'dataIndex' : 'farrivetime',
		sortable : true
	}, {
		'header' : '实际配送时间',
		dataIndex : 'fouttime',
		sortable : true
	}, {

		'header' : '配送状态',
		dataIndex:'fstate',
		renderer:function(val,record){
			/*var r = DJ.order.saleOrder.MyDeliveryList.DELIVERS_STATE;
			if(val!=5&&val!=6){
				val=7;
			}*/
			switch(val){
			case '0': return '待发货';
			case '1': return '部分发货';
			default: return '已发货';
			}

		}
	}
	, {
		'header' : '配送类型',
		dataIndex : 'ftype',
		renderer:function(value){
			return value==='0'?'正常':((value==1)?'补单':'补货');
		}
	}
	,{
		'header' : '联系人',
		'dataIndex' : 'flinkman',
		sortable : true
	},{
		header : '联系电话',
		dataIndex : 'flinkphone',
		sortable :true
	}, {
		'header' : '配送地址',
		'dataIndex' : 'faddress',
		sortable : true
	}, {
		'header' : '备注',
		'dataIndex' : 'fdescription',
		sortable : true
	}, {
		'header' : '特征',
		'dataIndex' : 'custpdtspec',
		sortable : true
	}
	]
})