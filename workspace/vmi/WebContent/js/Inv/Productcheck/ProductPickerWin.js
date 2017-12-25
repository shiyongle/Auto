Ext.require(["Ext.ux.form.MyMixedSearchBox","DJ.tools.common.MyCommonToolsZ","Ext.ux.form.MyMixedSearchBox",'Ext.ux.grid.MyGridItemDblClick']);

Ext.define('DJ.Inv.Productcheck.ProductPickerWin', {
	extend : 'Ext.c.GridPanel',
	// title : "产品列表",
	id : 'DJ.Inv.Productcheck.ProductPickerWin',
	pageSize : 50,
	// closable : true,// 是否现实关闭按钮,默认为false
	
	mixins: ['DJ.tools.grid.MyGridHelper'],
	
	url : 'gianProductsWithStoreqty.do',
	Delurl : "DeleteOutWarehouse.do",
	EditUI : "DJ.Inv.OutWarehouse.OutWarehouseEdit",
	exporturl : "outWarehousetoexcel.do",
	
	plugins : [{
		ptype : 'mygriditemdblclick',
		dbClickHandler : 'button[text=添加]'
			
	},{
	
		ptype : "mysimplegridcontextmenu",
		useExistingButtons : ['button[text=添加]','button[text=添加并继续]']
		
	}],
	
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		//this._hideDefaultButtons();
	var me = this;
			
			var hideids = ['DJ.Inv.Productcheck.ProductPickerWin.editbutton',
					"DJ.Inv.Productcheck.ProductPickerWin.viewbutton",
					"DJ.Inv.Productcheck.ProductPickerWin.delbutton",
					"DJ.Inv.Productcheck.ProductPickerWin.querybutton",
					"DJ.Inv.Productcheck.ProductPickerWin.addbutton"];
	
			Ext.each(hideids, function(ele, index, all) {
	
				Ext.getCmp(ele).hide();
	
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
		xtype : "mymixedsearchbox",
		useDefaultfilter : true,
		condictionFields : ['customername','productname','productnumber'],
		width : 200
	
		
		
	}, {
		text : '添加',
		handler : function(bt) {

			var bt = this;
			
			if (bt.nextNode("button[text=添加并继续]").handler(bt) != -1) {
			
				bt.up("window").close();
				
			}
					
		}

	}, {
		text : '添加并继续',
		handler : function() {

			var bt = this;
			
			var gridT = bt.up("grid");

			var items = MyCommonToolsZ.pickSelectItems(gridT);

			if (items == -1) {

				return -1;

			}

			var fproductcheckid = gridT.fproductcheckid;

			var objs = [];
			
			Ext.each(items, function (ele, index, all) {
			
				var obj = {
                    fproductid : ele.get('fproductdefid'),
                	fqty:ele.get('fstoreqty')
                };

				objs.push(obj);
				
			} );
			
//			var fproductid = items[0].get('fproductdefid');
//
//			var fqty = items[0].get('fstoreqty');

			var el = gridT.getEl();
			el.mask("系统处理中,请稍候……");
			var girdValue = Ext.getCmp('DJ.Inv.Productcheck.ProductPickerWin').getSelectionModel().getSelection();
//			Ext.Ajax.request({
//				timeout : 6000,
//
//				params : {
//					
//					action : "batchAdd",
//					
//					fproductcheckid : fproductcheckid,
////					fproductid : fproductid,
////					fqty : fqty
//                    objs :Ext.JSON.encode(objs)
//				},
//
//				url : "saveProductcheckitem.do",
//				success : function(response, option) {
//
//					var obj = Ext.decode(response.responseText);
//					if (obj.success == true) {
//						Ext.getCmp("DJ.Inv.Productcheck.ProductcheckitemList").getStore().load();
//					} else {
//						Ext.MessageBox.alert('错误', obj.msg);
//
//					}
//					el.unmask();
//				}
//			});
			for(var i=0;i<girdValue.length;i++){
//				var rowlength = Ext.getCmp("DJ.Inv.Productcheck.ProductcheckitemList").getStore().data.length + 1; 
				Ext.getCmp("DJ.Inv.Productcheck.ProductcheckitemList").getStore().add( girdValue[i].data);
			}
			el.unmask();
		}

	}],
	fields : [{
		name : 'fproductdefid'
	}, {
		name : 'fsupplierid'
	}, {
		name : 'customername'
	}, {
		name : 'productname'
	}, {
		name : 'productnumber'
	}, {
		name : 'actualquotation'
	}, 'fcharacter', 'forderunitid', 'fstoreqty'],
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
		'header' : 'fproductdefid',
		'dataIndex' : 'fproductdefid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : '客户名称',
		'dataIndex' : 'customername',
		sortable : true
	}, {
		'header' : '包装物名称',
		dataIndex : 'productname',
		sortable : true

	}, {
		'header' : '包装物编码',
		dataIndex : 'productnumber',
		width : 150,
		sortable : true

	}, {
		'header' : '规格',
		dataIndex : 'fcharacter',
		width : 150,
		sortable : true

	}, {
		'header' : '单位',
		dataIndex : 'forderunitid',
		width : 150,
		sortable : true

	}, {
		'header' : '库存数量',
		dataIndex : 'fstoreqty',
		width : 150,
		sortable : true

	}],
	selModel : Ext.create('Ext.selection.CheckboxModel')
})