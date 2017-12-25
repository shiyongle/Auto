Ext.require(["Ext.ux.form.FieldRecorder", "Ext.ux.form.MySearchBox",
		"DJ.tools.fieldRel.MyFieldRelTools", "DJ.tools.common.MyCommonToolsZ",
		"Ext.ux.grid.MySimpleGridContextMenu"]);

Ext.ns("DJ.Inv.Storebalance");

DJ.Inv.Storebalance.showEmptyWhenNull = function(value) {

	if (value == null || value == 0 || value == 'null') {

		return "";

	} else {
		return value;
	}

};

Ext.define('DJ.Inv.Storebalance.CustomerStorebalanceList', {
	extend : 'Ext.c.GridPanel',
	title : "我的库存",

	alias : 'widget.customerstorebalancelist',

	id : 'DJ.Inv.Storebalance.CustomerStorebalanceList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
//	url : 'selectStorebalances.do',
	url : 'queryStoreBalances.do',
	Delurl : "",
	EditUI : "",
	
//	exporturl : "",
	
	plugins : [{

		ptype : "mysimplegridcontextmenu",
		useExistingButtons : ["button[text=下单]"]
//		,
//		useCopyItem : false

	}
//	,Ext.create("Ext.ux.grid.MyGridShortcutKeyPlugin")
	
	],
	listeners: {
		itemclick: function(me,record,item, index, e){
			this.magnifier.loadImages({
				fid: record.get('fid')
			});
			var coord = e.getXY();
			this.magnifier.showAt({
    			left: coord[0] + 80,
    			top: coord[1] + 5
    		});
		}
	},
	onload : function() {
		var grid = this;
		grid.store.on('beforeload',function( store, operation, eOpts ){
			store.getProxy().timeout=600000;
		})
				
		Ext.getCmp('DJ.Inv.Storebalance.CustomerStorebalanceList.addbutton')
				.hide();
		Ext.getCmp('DJ.Inv.Storebalance.CustomerStorebalanceList.editbutton')
				.hide();
		Ext.getCmp('DJ.Inv.Storebalance.CustomerStorebalanceList.delbutton')
				.hide();
		Ext.getCmp('DJ.Inv.Storebalance.CustomerStorebalanceList.viewbutton')
				.hide();
		Ext.getCmp('DJ.Inv.Storebalance.CustomerStorebalanceList.exportbutton')
				.hide();
		Ext.getCmp('DJ.Inv.Storebalance.CustomerStorebalanceList.querybutton')
				.hide();
		this.magnifier = Ext.create('Ext.ux.form.Magnifier');
	},
	switchOrderType: function(orderType){
		var gridT = this;
		switch (orderType) {

			case "single" :
	
				
	
				var records = MyCommonToolsZ
						.pickSelectItems(gridT);
	
						
				if (records == -1) {
				
					break ;
					
				}
						var el = gridT.getEl();
				el.mask("系统处理中,请稍候……");
				Ext.Ajax.request({
					timeout : 6000,
	
					params : {
						fids : records[0].get("fid")
					},
	
					url : "gainCusProductsByIds.do",
					success : function(response, option) {
	
						var obj = Ext
								.decode(response.responseText);
						if (obj.success == true) {
	
							var goalObj = obj.data[0];
	
							var editui = Ext
									.create('DJ.order.Deliver.DeliversCustEdit');
							editui.editstate = "add";
							editui._relatedPanel = gridT;
							editui.down("button[text=保  存]").show();
							
							Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.fcusproductid")
									.setmyvalue({
	
										fid : goalObj.fid,
										fname : goalObj.fname
	
									});
	
							Ext
									.getCmp("DJ.order.Deliver.DeliversCustEdit.fcharacter")
									.setValue(goalObj.fcharactername);
	
	//						Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.balance").setValue(goalObj.balance);
							Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.balance").setValue(records[0].data.fbalanceqty);
									
							Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.fcustomerid").setmyvalue({
							
								fname: "",
								fid : goalObj.fcustomerid
								
							});
							
							Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.fcustomerid").hide();
						
							editui.show();
	
						} else {
							Ext.MessageBox.alert('错误', obj.msg);
						}
	
						el.unmask();
					}
				});
	
				break;
	
			case "multi" :
	
				var records = MyCommonToolsZ
							.pickSelectItems(gridT);
			
				if (records != -1)
				{
	
					var fids = [];
	
					Ext.each(records,
							function(ele, index, all) {
	
								fids.push(ele.get("fid"));
	
							});
	
					var el = gridT.getEl();
					el.mask("系统处理中,请稍候……");
	
					Ext.Ajax.request({
						timeout : 6000,
	
						params : {
							fids : fids.join(",")
						},
						url : "gainCusProductsByIds.do",
						success : function(response, option) {
	
							var obj = Ext
									.decode(response.responseText);
							if (obj.success == true) {
	
								var win = Ext
										.create('DJ.order.Deliver.batchCustDeliverapplyEdit');
	
								win._relatedPanel = gridT;
								win.seteditstate("edit");
										
								var items = obj.data,
	
								store = Ext
										.getCmp('DJ.order.Deliver.Deliverapplys')
										.getStore(), arr = [], records = [];
	
								items.forEach(function(record) {
									arr.push({
										fcusproductid : record.fid,
										cutpdtname : record.fname,
										cutpdtnumber : record.fnumber,
										fcharacter : record.fcharactername,
										ftraitid : record.fcharacterid,
										fspec : record.fspec,
										balance : record.balance,
										famount : 0,
										farrivetime : Ext.Date
												.add(
														new Date(new Date()
																.setHours(
																		14,
																		0,
																		0,
																		0)),
														Ext.Date.DAY,
														2)
									});
								});
								store.each(function(record) {
									if (record
											.get('fcusproductid') == '') {
										records.push(record);
									}
								});
								Ext.each(records,
										function(item) {
											store.remove(item);
										})
								store.add(arr);
	
								win.show();
	
							} else {
								Ext.MessageBox.alert('错误',
										obj.msg);
							}
							el.unmask();
						}
					});
				}
							
				
	
				break;
	
			case "file" :
	
				DJ.myComponent.button.UpLoadDeliverApplyWinButton.handlerC
						.call(button);
	
				break;
	
		}
	},
	custbar : [Ext.create("DJ.myComponent.button.ExportExcelButton"),
	
//	Ext.create("DJ.myComponent.button.ExcelPasterButton",{
//	
//		condition:['cname','fname','fnumber']
//		
//	}),
		{xtype : "tbspacer", width : 20},{
		xtype:'textfield',
		id:'text',
		width:120,
		emptyText:'回车搜索...',
		enableKeyEvents:true,
		listeners:{
			render:function(){
				Ext.tip.QuickTipManager.register({
				    target: 'text',
				    text: '可输入：包装物名称、编号、客户名称!'
//				    width: 100,
//				    dismissDelay: 10000 // Hide after 10 seconds hover
				});
			},
			keypress:function(me,e){
				if(e.getKey()==13){
					var val = this.getValue();
					var store = this.up('grid').getStore();
					store.setDefaultfilter([{
							myfilterfield : "t.fname",
							CompareType : " like ",
							type : "string",
							value : val
						},{
							myfilterfield : "t.fnumber",
							CompareType : " like ",
							type : "string",
							value : val
						},{
							myfilterfield : "c.fname",
							CompareType : " like ",
							type : "string",
							value : val
						} ]);
						store.setDefaultmaskstring(" #0 or #1 or #2");
					store.loadPage(1);
				}
			}
		}
	},
	{// 查找按钮
		text : '查找',
		xtype : 'button',
		handler : function() {
			var text = Ext.getCmp('text');
			var val = text.getValue();
			var store = text.up('grid').getStore();
			store.setDefaultfilter([ {
				myfilterfield : "t.fname",
				CompareType : " like ",
				type : "string",
				value : val
			}, {
				myfilterfield : "t.fnumber",
				CompareType : " like ",
				type : "string",
				value : val
			},{
				myfilterfield : "c.fname",
				CompareType : " like ",
				type : "string",
				value : val
			} ]);
			store.setDefaultmaskstring(" #0 or #1 or #2");
			store.loadPage(1);
		}
	}, {

		text : '下单',

		handler : function() { 

			var me = this;
			
			var gridT = me.up("grid");

			Ext.Ajax.request({
				timeout : 6000,

				url : "gainOrderWayCfg.do",
				success : function(response, option) {

					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {

						var orderType = obj.data[0].fvalue;
						
						gridT.switchOrderType(orderType);
						

					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}

				}
			});

		}

	},{
				text : '逻辑描述',
				height : 30,
				handler : function() {
					Ext.MessageBox
							.alert(
									'描述',
									'<p><h4 align="center">"产品库存报表"--产品的库存数量</h4></p>'
											+ '<ul><li>入库数量：产品的累计入库总数量</li>'
											+ '<li>出货数量：产品的累计出库总数量</li>'
											+ '<li>存库数量：当前产品的库存数量；库存表按产品统计各总数量</li></ul>');
				}
	},{
				xtype:'checkbox',
				boxLabel  : '按产品统计',
//				height : 30,
				listeners:{
				change:function(t,oldvalue,newvalue,e)
				{
				var text = Ext.getCmp('text');
				var val = text.getValue();
				var store = text.up('grid').getStore();
				store.setDefaultfilter([{
					myfilterfield : "t.fname",
					CompareType : " like ",
					type : "string",
					value : val
				}, {
					myfilterfield : "t.fnumber",
					CompareType : " like ",
					type : "string",
					value : val
				}, {
					myfilterfield : "c.fname",
					CompareType : " like ",
					type : "string",
					value : val
				}]);
				store.setDefaultmaskstring(" #0 or #1 or #2");
				store.on("beforeload", function(store, options) {
					Ext.apply(store.proxy.extraParams, {
						ftype : t.checked==true?1:0
					});
				});
				store.loadPage(1);
			}
			}
	}],
	fields : [{
		name : 'fproductid'
	}, {
		name : 'fname',
		myfilterfield : 'fname',
		myfiltername : '产品名称',
		myfilterable : true
	}, {
		name : 'finqty'
	}, {
		name : 'foutqty'
	}, {
		name : 'fbalanceqty'
	}, {
		name : 'fcharacter'
	}, 'fmakingqty', 'fusedqty', 'fnumber','fid','suppliername'
	,{name:'cname'}
	],
	columns : {items:[Ext.create('DJ.Base.Grid.GridRowNum',{
		header:'序号'
	}),{
		'header' : 'fproductid',
		'dataIndex' : 'fproductid',
		hidden : true,
		hideable : false,
		sortable : true
	},{
		'header' : '客户名称',
		'dataIndex' : 'cname',
		width : 180,
		sortable : true
	}, {
		'header' : '包装物名称',
		'dataIndex' : 'fname',
		width : 180,
		sortable : true
	}, {
		'header' : '包装物编号',
		'dataIndex' : 'fnumber',
		width : 180,
		sortable : true
		}, {
		'header' : '制造商名称',
		'dataIndex' : 'suppliername',
		width : 180,
		sortable : true 
		},{
				'header' : '需发货数量',
				dataIndex : 'fusedqty',
				sortable : true,
				xtype : 'numbercolumn',
				width : 80,
				format : '0'
			},{
				'header' : '库存',
				dataIndex : 'fbalanceqty',
				sortable : true,
				xtype : 'numbercolumn',
				width : 80,
				format : '0'
			},  {
				'header' : '在生产数量',
				dataIndex : 'fmakingqty',
				sortable : true,
				xtype : 'numbercolumn',
				flex : 1,
				format : '0'
			}]
//	,
//			
//				defaults : {
//				
//					renderer : MyFieldRelTools.showEmptyWhenNull
//				}
			},
	selModel : Ext.create('Ext.selection.CheckboxModel')

});

