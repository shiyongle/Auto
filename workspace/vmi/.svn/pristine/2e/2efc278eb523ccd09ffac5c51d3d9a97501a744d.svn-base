Ext.require(["DJ.tools.common.MyCommonToolsZ","Ext.ux.form.MyMixedSearchBox","Ext.ux.form.DateTimeField"]);

Ext.define('DJ.Inv.Productcheck.ProductcheckitemList', {
	extend : 'Ext.c.GridPanel',
	// title : "盘点编辑",
	id : 'DJ.Inv.Productcheck.ProductcheckitemList',
	
	pageSize : 50,
	// closable : true,// 是否现实关闭按钮,默认为false
	url : 'gainProductcheckitems.do',
//	Delurl : "deleteProductcheckitemtos.do",
	EditUI : "",
	exporturl : "makeproductcheckitemtoExcel.do",
	
	plugins : [{
	
		ptype : "mysimplegridcontextmenu",
		useExistingButtons : ['button[text=删    除]'],
		pluginId: 'contextmenuT'
		
	}, {
		pluginId: 'celleditingT',
		ptype : "cellediting",
		clicksToEdit : 1,
		pluginId: 'celleditingT',
		listeners : {

			beforeedit : function(editor, e, eOpts) {

				var me = this;

				if (me.disabled) {

					return false;

				}

			}

		}
	
	
	
	}],
	
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等

		var me = this;
		
		var hideids = ['DJ.Inv.Productcheck.ProductcheckitemList.editbutton',
				"DJ.Inv.Productcheck.ProductcheckitemList.viewbutton",
				"DJ.Inv.Productcheck.ProductcheckitemList.refreshbutton",
				"DJ.Inv.Productcheck.ProductcheckitemList.querybutton"];
		Ext.getCmp("DJ.Inv.Productcheck.ProductcheckitemList.addbutton").setText("添加产品");
		Ext.each(hideids, function(ele, index, all) {

			Ext.getCmp(ele).hide();

		});

		var listT = Ext.getCmp("DJ.Inv.Productcheck.ProductPickerWin");
		
		var win = Ext.create(Ext.Window, {

			modal : true,
			title : "产品列表",
			items : [listT ? listT : listT = Ext
					.create("DJ.Inv.Productcheck.ProductPickerWin")],
			closeAction : 'hide',
			width : 1000,
			height : 350,
			layout : 'fit',
			resizable : false
			
//			,
//
//			listeners : {
//
//				close : function(panel, eOpts) {
//
//					me.getStore().load();
//
//				}
//
//			}

		});

		var buttonAndHandlers = [{

			chooser : "DJ.Inv.Productcheck.ProductcheckitemList.addbutton",
			handlerT : function(bt) {

				listT.fproductcheckid = me.fproductcheckid;
				win.show();

			}

		}];

		Ext.each(buttonAndHandlers, function(ele, index, all) {

			var comT = Ext.getCmp(ele.chooser);

			comT.setHandler(ele.handlerT, comT);

		});
		var deleteButton = [{

			chooser : "DJ.Inv.Productcheck.ProductcheckitemList.delbutton",
			handlerT : function(bt) {
				var a = Ext.getCmp("DJ.Inv.Productcheck.ProductcheckitemList");
				var b = a.getSelectionModel().getSelection();
				for(var i=0;i<b.length;i++){
					var length = a.getStore().indexOf(b[i]);
					a.getStore().remove(a.getStore().getAt(length));
				}

			}

		}];

		Ext.each(deleteButton, function(ele, index, all) {

			var comT = Ext.getCmp(ele.chooser);

			comT.setHandler(ele.handlerT, comT);

		});

	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件

	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件

	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
		// 可对编辑界面进行复制
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件

	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	custbar : [{
		xtype : "datetimefield",
		format : "Y-m-d",
		labelWidth:60,
		fieldLabel:'盘点日期',
		id : 'timeText',
		editable:false,
		width : 190
	},{
		xtype: 'text',
		text : '备注:',
		style : {
			marginLeft : '15px'
		},
	},{
		xtype : 'textfield',
		id : 'remarkText'
	},{
		xtype : "button",
		text : '保存',
		id : 'saveAllButton',
		style : {
			marginLeft : '130px'
		},
		handler : function(com, e) {
			if(com.up("grid").fproductcheckid!=null){
				var timeText = Ext.getCmp("timeText").rawValue;
				var me = Ext.getCmp("DJ.Inv.Productcheck.ProductcheckList");
				var fid = Ext.getCmp("DJ.Inv.Productcheck.ProductcheckList").getSelectionModel().getSelection()[0].get('fid');
				var el = me.getEl();
				var bt = Ext.getCmp("DJ.Inv.Productcheck.ProductcheckitemList");
				
				var gridT = bt.getStore();
				var items =  gridT.data.items;

				var objs = [];
				
				for(var i=0;i<items.length;i++){
					var itemData = items[i].data;
					var h = {
						fid : itemData.fid,
						fproductdefid : itemData.fproductdefid,
						actualquotation : itemData.actualquotation,
						fremark : itemData.fremark
					};
					objs.push(h);
				}
				
				var fremark = Ext.getCmp("remarkText").value;
				var timeValue = Ext.getCmp("timeText").rawValue;
				Ext.Ajax.request({
				timeout : 6000,

				params : {
					fid : fid,
					fstate : 0,
					fremark : fremark,
					timeValue : timeValue,
					fproductcheck : Ext.JSON.encode(objs),
					action : 'update'
					
				},

				url : "saveProductcheck.do",
				success : function(response, option) {

					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						me.getStore().load();
						djsuccessmsg("保存成功！");
						com.up('window').close();
						
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
			}else{
				var timeText = Ext.getCmp("timeText").rawValue;
				var me = Ext.getCmp("DJ.Inv.Productcheck.ProductcheckList");
				Ext.Ajax.request({
					timeout : 6000,

					url : "gainUUID.do",
					success : function(response, option) {
						var el = me.getEl();
						var obj = Ext.decode(response.responseText);
						if (obj.success == true) {

							var bt = Ext.getCmp("DJ.Inv.Productcheck.ProductcheckitemList");
							
							var gridT = bt.getStore();
							var items =  gridT.data.items;

							var objs = [];
							
							for(var i=0;i<items.length;i++){
								var itemData = items[i].data;
								var h = {
									fid : itemData.fid,
									fproductdefid : itemData.fproductdefid,
									actualquotation : itemData.actualquotation,
									fremark : itemData.fremark
								};
								objs.push(h);
							}
							
							var fremark = Ext.getCmp("remarkText").value;
							var timeValue = Ext.getCmp("timeText").rawValue;
							Ext.Ajax.request({
							timeout : 6000,

							params : {
								fid : obj.data[0].id,
								fstate : 0,
								fremark : fremark,
								timeValue : timeValue,
								fproductcheck : Ext.JSON.encode(objs),
								action : 'create'
								
							},

							url : "saveProductcheck.do",
							success : function(response, option) {

								var obj = Ext.decode(response.responseText);
								if (obj.success == true) {
									me.getStore().load();
									djsuccessmsg("保存成功！");
									com.up('window').close();
									
								} else {
									Ext.MessageBox.alert('错误', obj.msg);
								}
								el.unmask();
							}
						});
						}
					}
				});
			}
		}
	}],
	features: [{
		ftype : 'summary'
	}],
	fields : [{
		name : 'fid'
	}, {
		name : 'fproductcheckid'
	}, {
		name : 'fstoreqty',
		type : "int"
	}, {
		name : 'actualquotation',
		type : "int"
	}, {
		name : 'gainorlossCount',
		type : "int"
	},{
		name : 'fproductdefid'
	},

	'customername', 'productname', 'productnumber', 'fcharacter',
			'forderunitid','fremark'],
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : 'fproductdefid',
		'dataIndex' : 'fproductdefid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : '客户名称',
		'dataIndex' : 'customername',
		sortable : true,
		summaryType : '',
		summaryRenderer : function(value){
			return '本页总计'
		}
	}, {
		'header' : '包装物名称',
		dataIndex : 'productname',
		sortable : true

	}, {
		'header' : '包装物编号',
		dataIndex : 'productnumber',
		width : 120,
		sortable : true

	}, {
		'header' : '规格',
		dataIndex : 'fcharacter',
		width : 120,
		sortable : true

	}, {
		'header' : '单位',
		dataIndex : 'forderunitid',
		width : 60,
		sortable : true

	}, {
		'header' : '库存数量',
		dataIndex : 'fstoreqty',
		width : 60,
		sortable : true,
		summaryType : 'sum',
		summaryRenderer : function(value){
			return value
		}

	}, {
		'header' : '实盘数量',
		dataIndex : 'actualquotation',
		width : 60,
		sortable : true,
		
		editor : {
			
			xtype : "numberfield",
			allowDecimals : false,
			minValue : 0,
			listeners : {

				afterrender : function(com, eOpts) {

					if (Ext.getCmp("DJ.Inv.Productcheck.ProductcheckitemList")
							.getPlugin("celleditingT").disabled) {

						com.disable();

					}

				} ,
				
				blur : function(com, The, eOpts) {

					var grid = com.up("grid");
					
					var records = MyCommonToolsZ.pickSelectItems(grid);

					if (records == -1) {

						return;

					}

//					var fid = records[0].get("fid");
//					
//					Ext.Ajax.request({
//						timeout : 6000,
//
//						params : {
//							fid : fid,
//							action : "update",
//							fqty : com.getValue()
//						},
//
//						url : "updateProductcheckitem.do",
//						success : function(response, option) {
//
//							var obj = Ext.decode(response.responseText);
//							if (obj.success == true) {
//								grid.getStore().load();
//							} else {
//								Ext.MessageBox.alert('错误', obj.msg);
//							}
//
//						}
//					});

				}

			}
			
			
		},
		summaryType : 'sum',
		summaryRenderer : function(value){
			return value
		}
		

	}, {
		'header' : '损益数量',
		dataIndex : 'gainorlossCount',
		width : 60,
		sortable : true,
		renderer :function(v,m){
			return m.record.data.actualquotation-m.record.data.fstoreqty;
		},
		summaryType : 'sum',
		summaryRenderer : function(value, summaryData, dataIndex){
			return dataIndex.data.actualquotation - dataIndex.data.fstoreqty;
		}

	}, {
		'header' : '备注',
		dataIndex : 'fremark',
		width : 150,
		sortable : true,
		
		editor : {
			xtype : "textfield",

			listeners : {

				
				afterrender : function(com, eOpts) {

					if (Ext.getCmp("DJ.Inv.Productcheck.ProductcheckitemList")
							.getPlugin("celleditingT").disabled) {

						com.disable();

					}

				} ,
				
				blur : function(com, The, eOpts) {

					var grid = com.up("grid");
					
					var records = MyCommonToolsZ.pickSelectItems(grid);

					if (records == -1) {

						return;

					}
//
//					var fproductcheckid = records[0].get("fid");
//					
//					Ext.Ajax.request({
//						timeout : 6000,
//
//						params : {
//							fid : fproductcheckid,
//							action : "update",
//							fremark : com.getValue()
//						},
//
//						url : "updateProductcheckitem.do",
//						success : function(response, option) {
//
//							var obj = Ext.decode(response.responseText);
//							if (obj.success == true) {
//								com.up("grid").getStore().load();
//							} else {
//								Ext.MessageBox.alert('错误', obj.msg);
//							}
//
//						}
//					});

				}

			}
		}
		

	}],
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	getGridName : function(){
		var checkList = Ext.getCmp('DJ.Inv.Productcheck.ProductcheckList');
		var fremarkValue = checkList.getSelectionModel().getSelection()[0].data.fremark;
		var timeValue = checkList.getSelectionModel().getSelection()[0].data.fcreatetime;
		Ext.getCmp('remarkText').setValue(fremarkValue);
		Ext.getCmp('remarkText').setReadOnly(false);
		Ext.getCmp('timeText').setRawValue(timeValue);
	}
})

