Ext.require(['Ext.ux.grid.MySimpleGridContextMenu',"Ext.ux.form.MyMixedSearchBox",
		"DJ.tools.common.MyCommonToolsZ",'Ext.ux.grid.MyGridItemDblClick']);

Ext.define('DJ.Inv.Productcheck.ProductcheckList', {
	extend : 'Ext.c.GridPanel',
	title : "产品盘点表",
	id : 'DJ.Inv.Productcheck.ProductcheckList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'gainProductchecks.do',
	Delurl : "DeleteProductcheck.do",
	EditUI : "DJ.Inv.OutWarehouse.OutWarehouseEdit",
	exporturl : "productchecktoexcel.do",

	plugins : [{
		ptype : 'mygriditemdblclick',
		dbClickHandler : 'button[text=查   看]'
			
	},{

		ptype : "mysimplegridcontextmenu",
		useExistingButtons : ['button[text=修  改]', 'button[text=查   看]']

	}, {

		ptype : "cellediting",
		clicksToEdit : 1

	}],
	
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等

		var me = this;
		
		var hideCSs = [
				'DJ.Inv.Productcheck.ProductcheckList.querybutton'];
		
		
		Ext.each(hideCSs, function(ele, index, all) {

			Ext.getCmp(ele).hide();

		});

		var listT = Ext.getCmp("DJ.Inv.Productcheck.ProductcheckitemList");

		var win = Ext.create(Ext.Window, {
			modal : true,
			title : "盘点表编辑界面",
			items : [listT ? listT : listT = Ext
					.create("DJ.Inv.Productcheck.ProductcheckitemList")],
			closeAction : 'hide',
			width : 1000,
			height : 400,
			layout : 'fit',
			resizable : false
				// ,
				//
				//			
				//			
				// listeners : {
				//
				// close : function(panel, eOpts) {
				//
				// me.getStore().load();
				//
				// }
				//
				// }
				});

		var addBt = Ext
				.getCmp("DJ.Inv.Productcheck.ProductcheckList.addbutton");

		addBt.setHandler(function(bt) {
			var date = new Date();
//			Ext.Ajax.request({
//				timeout : 6000,
//
//				url : "gainUUID.do",
//				success : function(response, option) {
//
//					var obj = Ext.decode(response.responseText);
//					if (obj.success == true) {
//
//						var fproductcheckid = obj.data[0].id;
//
//						listT.fproductcheckid = fproductcheckid;
//
//						listT.getStore().getProxy().setExtraParam(
//								"fproductcheckid", fproductcheckid);
//
//						var el = me.getEl();
//
//						var storeT = listT.getStore();
//
//						var myfilter = [];
//						myfilter.push({
//							myfilterfield : 'fproductcheckid',
//							CompareType : "=",
//							type : "string",
//							value : fproductcheckid
//						});
//
//						maskstring = "#0";
//
//						storeT.setDefaultfilter(myfilter);
//						storeT.setDefaultmaskstring(maskstring);
//
//						storeT.load({
//							params : {
//
//								fproductcheckid : fproductcheckid
//
//							},
//							callback : function(records, operation, success) {
//								
//								if (success) {
									var currentDT = new Date();  
									  var y,m,date,hs,ms,ss,theDateStr;  
									  y = currentDT.getFullYear(); //四位整数表示的年份  
									  m = currentDT.getMonth(); //月  
									  date = currentDT.getDate(); //日  
									  hs = currentDT.getHours(); //时  
									  ms = currentDT.getMinutes(); //分  
									  ss = currentDT.getSeconds(); //秒
									 var theDateStr = y+"-"+m+"-"+date+" "+hs+":"+ms;
									
									Ext.getCmp("DJ.Inv.Productcheck.ProductcheckitemList").getPlugin("celleditingT").enable();
									Ext.getCmp('DJ.Inv.Productcheck.ProductcheckitemList.addbutton').show();
									Ext.getCmp('DJ.Inv.Productcheck.ProductcheckitemList.delbutton').show();
									Ext.getCmp('saveAllButton').show();
									Ext.getCmp("timeText").enable();
									Ext.getCmp('timeText').setValue(date.toLocaleString());
									Ext.getCmp('remarkText').setReadOnly(false);
									Ext.getCmp('remarkText').setValue('');
									win.show();
									Ext.getCmp('timeText').setRawValue(theDateStr);
									Ext.getCmp("DJ.Inv.Productcheck.ProductcheckitemList").getStore().removeAll();
									
//								}
//							}
//
//						});
//
//					} else {
//						Ext.MessageBox.alert('错误', obj.msg);
//
//					}
//
//				}
//			});

		}, addBt);

		var updateBt = Ext
				.getCmp("DJ.Inv.Productcheck.ProductcheckList.editbutton");

		updateBt.setHandler(function(bt) {

			var records = MyCommonToolsZ.pickSelectItems(me);

			if (records == -1) {

				return;

			}

			var fproductcheckid = records[0].get("fid");

			listT.fproductcheckid = fproductcheckid;

			var storeT = listT.getStore();

			var myfilter = [];
			myfilter.push({
				myfilterfield : 'fproductcheckid',
				CompareType : "=",
				type : "string",
				value : fproductcheckid
			});

			maskstring = "#0";

			storeT.setDefaultfilter(myfilter);
			storeT.setDefaultmaskstring(maskstring);

			storeT.getProxy().setExtraParam("fproductcheckid", fproductcheckid);

			storeT.load({
				action : "update",
				callback : function(records, operation, success) {

					if (success) {
						Ext.getCmp("DJ.Inv.Productcheck.ProductcheckitemList").getPlugin("celleditingT").enable();
						Ext.getCmp("timeText").enable();
						Ext.getCmp('DJ.Inv.Productcheck.ProductcheckitemList.addbutton').show();
						Ext.getCmp('DJ.Inv.Productcheck.ProductcheckitemList.delbutton').show();
						Ext.getCmp('saveAllButton').show();
						Ext.getCmp('DJ.Inv.Productcheck.ProductcheckitemList').getGridName();
						win.show();
						

					}

				}

			});

		}, updateBt);

		var viewbutton = Ext
				.getCmp("DJ.Inv.Productcheck.ProductcheckList.viewbutton");

		viewbutton.setHandler(function() {

			var records = MyCommonToolsZ.pickSelectItems(me);

			if (records == -1) {

				return;

			}

			var fproductcheckid = records[0].get("fid");

			listT.fproductcheckid = fproductcheckid;

			var storeT = listT.getStore();

			var myfilter = [];
			myfilter.push({
				myfilterfield : 'fproductcheckid',
				CompareType : "=",
				type : "string",
				value : fproductcheckid
			});

			maskstring = "#0";

			storeT.setDefaultfilter(myfilter);
			storeT.setDefaultmaskstring(maskstring);

			storeT.getProxy().setExtraParam("fproductcheckid", fproductcheckid);

			storeT.load({
				action : "view",
				callback : function(records, operation, success) {

					if (success) {
						Ext.getCmp("DJ.Inv.Productcheck.ProductcheckitemList").getPlugin("celleditingT").disable();
						Ext.getCmp("DJ.Inv.Productcheck.ProductcheckitemList").getPlugin("contextmenuT").disabled=true;
						Ext.getCmp("timeText").disable();
						Ext.getCmp('DJ.Inv.Productcheck.ProductcheckitemList.addbutton').hide();
						Ext.getCmp('DJ.Inv.Productcheck.ProductcheckitemList.delbutton').hide();
						Ext.getCmp('saveAllButton').hide();
						Ext.getCmp('DJ.Inv.Productcheck.ProductcheckitemList').getGridName();
						Ext.getCmp('remarkText').setReadOnly(true);
						win.show();
						
					}

				}

			});

		}, viewbutton);

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
		text : '校准库存',
		handler : function() {
			var grid = Ext.getCmp('DJ.Inv.Productcheck.ProductcheckList');
			var records = grid.getSelectionModel().getSelection();
			if (records.length < 1) {
				Ext.MessageBox.alert("错误", "请选择一条记录！");
				return;
			}
			var result = "('";
			for (var i = 0; i < records.length; i++) {
				result += records[i].get("fid");
				if (i < records.length - 1) {
					result += "','";
				}
			}
			result += "')";
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				url : 'calibrationTheInventory.do',
				params : {
					fids : result
				},
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						djsuccessmsg(obj.msg);
						Ext.getCmp("DJ.Inv.Productcheck.ProductcheckList").store
								.load();

					} else {
						Ext.MessageBox.alert('错误', obj.msg);

					}
					el.unmask();
				}
			})
		}
	},Ext.create("Ext.ux.grid.print.MySimpleGridPrinterComponent"), '-', {
		xtype : 'mymixedsearchbox',
		condictionFields : ['tip.fcreatetime', 'tsy.fname'],
		tip : '可输入:盘点日期、操作人',
		useDefaultfilter : true
	}],
	fields : [{
		name : 'fid'
	}, {
		name : 'fnumber'
	}, {
		name : 'fupdatetime'
	}, {
		name : 'fcreatetime'
	}, {
		name : 'fuserid'
	}, {
		name : 'fremark'
	}, {
		name : 'fstate'
	}, {

		name : "username"
	}],
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : '单据编号',
		'dataIndex' : 'fnumber',
		sortable : true
	}, {
		'header' : '盘点日期',
		dataIndex : 'fcreatetime',
		width : 150,
		sortable : true
	}, {
		'header' : '修改日期',
		dataIndex : 'fupdatetime',
		width : 150,
		sortable : true
	}, {
		'header' : '操作人',
		'dataIndex' : 'username',
		sortable : true
	}, {
		'header' : '状态',
		'dataIndex' : 'fstate',
		sortable : true,
		renderer : function(v) {

			return v == 1 ? '校准' : '创建'

		}
	}, {
		'header' : '备注',
		'dataIndex' : 'fremark',
		sortable : true,
		editor : {
			xtype : "textfield",
			listeners : {

				blur : function(com, The, eOpts) {

					var grid = com.up("grid");
					var records = MyCommonToolsZ.pickSelectItems(grid);

					if (records == -1) {

						return;

					}

					var fproductcheckid = records[0].get("fid");
					
					Ext.Ajax.request({
						timeout : 6000,

						params : {
							fid : fproductcheckid,
							fremark : com.getValue()
						},

						url : "updateProductcheckFremark.do",
						success : function(response, option) {

							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {

							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}

						}
					});

				}

			}
		}

	}],
	selModel : Ext.create('Ext.selection.CheckboxModel')
})