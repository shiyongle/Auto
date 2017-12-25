Ext.define('DJ.System.product.ProductCycleList', {
			extend : 'Ext.c.GridPanel',
			title : "生命周期管理",
			id : 'DJ.System.product.ProductCycleList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetCycles.do',
			Delurl : "",//DeleteCycles.do
			EditUI : "",//DJ.System.product.ProductCycleEdit

			// EditUI : "DJ.System.product.ProportionEdit",

			
//			custbar : [
//				{
//				// id : 'DelButton',
//				text : '比例分配',
//				height : 30,
//				handler : editProportion
//						
//				}
//			],
			
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
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
			fields : [{
						name : 'fid'
					}, {
						name : 'fproductdefid_fname',
						myfilterfield : 'f.fname',
						myfiltername : '产品名称',
						myfilterable : true
					}, {
						name : 'fsupplierid_fname',
						myfilterfield : 's.fname',
						myfiltername : '供应商名称',
						myfilterable : true
					}, {
						name : 'flifecycle'
					}, {
						name : 'fcreatetime'
					}, {
						name : 'flastupdatetime'
					}, {
						name : 'fname1'
					}, {
						name : 'fname2'
					}],
			columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						'header' : '产品名称',
						'dataIndex' : 'fproductdefid_fname',
						sortable : true
					}, {
						'header' : '供应商名称',
						'dataIndex' : 'fsupplierid_fname',
						sortable : true
					}, {
						'header' : '生命周期',
						'dataIndex' : 'flifecycle',
						sortable : true
					}, {
						text : '修改人',
						dataIndex : 'fname2',
						sortable : true
					}, {
						text : '修改时间',
						dataIndex : 'flastupdatetime',
						width : 150,
						sortable : true
					}, {
						text : '创建人',
						dataIndex : 'fname1',
						sortable : true
					}, {
						text : '创建时间',
						dataIndex : 'fcreatetime',
						width : 150,
						sortable : true

					}],
			selModel : Ext.create('Ext.selection.CheckboxModel')
		});
		
//function editProportion (btn) {
//	var grid = Ext.getCmp("DJ.System.product.ProductCycleList");
//	var record = grid.getSelectionModel().getSelection();
//	
//	if (record.length != 1) {
//		Ext.MessageBox.alert('提示', '只能选中一条记录进行关联!');
//		return;
//	}
//	
//	var relationCust = Ext.getCmp("DJ.System.product.ProportionEdit");
//	
//	if (relationCust != null) {
//		relationCust.show();
//	} else {
//		var relationCust2 = Ext.create('DJ.System.product.ProportionEdit');
//		relationCust2.show();
//	}
//
//	Ext.getCmp("DJ.System.product.bt.ProductFID").setValue(record[0]
//			.get("fid"));
//	Ext.getCmp("DJ.System.product.bt.ProductName").setValue(record[0]
//			.get("fproductdefid_fname"));
//
//	var custgrid = Ext.getCmp("ProportionEditGridPanel");
//
//	var checkModel = custgrid.getSelectionModel();
//	var custstore = custgrid.getStore();
//
//	custstore.currentPage = 1;
//
//	custstore.on("beforeload", function(store, options) {
//				Ext.apply(store.proxy.extraParams, {
//							productid : record[0].get("fid")
//						});
//				checkModel.removeListener('deselect');
//			});
//	custstore.load();
//};