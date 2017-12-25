 function onCustRelationProductClick(btn)
{
//	var grid = Ext.getCmp("DJ.System.product.CustproductCustList");
//	var record = grid.getSelectionModel().getSelection();
//	if (record.length != 1) {
//		Ext.MessageBox.alert('提示', '只能选中一条记录进行关联!');
//		return;
//	}
//	if(record[0].get("fcustomerid")=="null"||record[0].get("fcustomerid")=="")
//	{
//		Ext.MessageBox.alert('提示', '请先设置对应的客户或数据不同步请刷新！');
//		return;
//	}
//	var edit=Ext.create('DJ.System.product.RelationProduct');
//	edit.settxtid(record[0].get("fid"));
//	edit.settxtvalue(record[0].get("fname"));
//	edit.show();
//	Ext.getCmp("DJ.System.product.CustproductCustList").store.load();
	
	var grid = Ext.getCmp("DJ.System.product.CustproductCustList");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行查看!');
		return;
	}
	var fid = record[0].get("fid");

	var productdefdetail = Ext.getCmp('DJ.System.product.CustProductTreeEdit');

	if (productdefdetail == null) {
		productdefdetail = Ext.create('DJ.System.product.CustProductTreeEdit');
	}

	productdefdetail.show();

	var structtree = Ext
			.getCmp('DJ.System.product.CustProductTreeEdit.CustProductTreeEdit');

	var gridProductWin = Ext
			.getCmp('DJ.System.product.CustProductTreeEdit.CustProductDefList');

	var structStore = structtree.store;
	
	var rootvalue = {
		expanded : true,
		leaf : false,
		text : record[0].get('fname'),
		id : fid,
		fnumber : record[0].get('fnumber')
	};
	structStore.setRootNode(rootvalue);

	gridProductWin.store.proxy.extraParams.dbfid = fid;

	gridProductWin.store.load();
	
//	Ext.getCmp('DJ.System.product.CustproductCustList').store.load;
}

function custproductprice(btn) {
	var grid = Ext.getCmp("DJ.System.product.CustproductCustList");
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行操作!');
		return;
	}

	var editui = Ext.create('DJ.System.product.CustproductpriceEdit');
	editui.seteditstate("edit");
	editui.setparent('DJ.System.product.CustproductCustList');
	editui.loadfields(record[0].get("fid"));
	editui.getform().getForm().findField("fcustproductid").setReadOnly(true);
	editui.getform().getForm().findField("username").setReadOnly(true);
	editui.getform().getForm().findField("fcreatetime").setReadOnly(true);
	editui.show();
}
 
 Ext.define('DJ.System.product.CustproductCustList', {
			extend : 'Ext.c.GridPanel',
			title : "客户产品管理",
			id : 'DJ.System.product.CustproductCustList',
			iconCls : 'user',
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			pageSize : 100,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetCustproductList.do',
			Delurl : "DelCustproductList.do",
			EditUI : "DJ.System.product.CustproductEdit",
			exporturl:"custproducttoexcel.do",
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
				Ext.getCmp("DJ.System.product.CustproductCustList.addbutton").setVisible(false);
				Ext.getCmp("DJ.System.product.CustproductCustList.editbutton").setVisible(false);
				Ext.getCmp("DJ.System.product.CustproductCustList.delbutton").setVisible(false);
				Ext.getCmp("DJ.System.product.CustproductCustList.exportbutton").setVisible(false);
			},
			Action_BeforeAddButtonClick : function(EditUI) {
				// 新增界面弹出前事件
			},
			Action_AfterAddButtonClick : function(EditUI) {
				// 新增界面弹出后事件
				var editform=Ext.getCmp("DJ.System.product.CustproductEdit").getform().getForm();
				var trees=Ext.getCmp("DJ.System.product.CustproductTree");	
				var record=trees.getSelectionModel().getSelection();
				if(record.length > 0)
				{
					if(record[0].data.id!=-1)
					{
					editform.findField('fcustomerid').setmyvalue("\"fid\":\""+record[0].data.id+"\",\"fname\":\""+record[0].data.text+"\"");
					}
				}
			},
			Action_BeforeEditButtonClick : function(EditUI) {
				// 修改界面弹出前事件
			},
			Action_AfterEditButtonClick : function(EditUI) {
				// 修改界面弹出后事件
			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			},
//			custbar : [{
//				text : '关联产品',
//				height : 30,
//				handler : onCustRelationProductClick
//					}, {
//						// id : 'DelButton',
//						text : '结算价',
//						height : 30,
//						handler : custproductprice
//
//					}],
			fields : [ {
					name : 'fid'
				}, {
					name : 'fname',
					myfilterfield : 'fname',
					myfiltername : '名称',
					myfilterable : true
				}, {
					name : 'fnumber',
					myfilterfield : 'fnumber',
					myfiltername : '编码',
					myfilterable : true
				}, {
					name : 'fspec'
				}, {
					name : 'forderunit'
				}, {
					name : 'fcustomerid'
				}, {
					name : 'fdescription'
				}, {
					name : 'fcreatorid'
				}, {
					name : 'fcreatetime'
				}, {
					name : 'flastupdateuserid'
				}, {
					name : 'flastupdatetime'
				}, {
					name : 'Frelationed'
					}],
				columns : [ Ext.create('DJ.Base.Grid.GridRowNum'), 
	            {
					'header' : 'fid',
					'dataIndex' : 'fid',
					hidden : true,
					hideable : false,
					autoHeight: true, 
					autoWidth:true,
					sortable : true
				}, {
					'header' : '产品名称',
					'dataIndex' : 'fname',
					sortable : true
				}, {
					'header' : '编码',
					'dataIndex' : 'fnumber',
					sortable : true
				}, {
					'header' : '关联',
					width : 40,
					'dataIndex' : 'Frelationed',
					sortable : true,
					renderer: function(value){
					        if (value == 1) {
					            return '是';
					        }
					        else{
					        	return '否';
					        }
					    }
				}, {
					'header' : '规格',
					width : 70,
					'dataIndex' : 'fspec',
					sortable : true
				}, {
					'header' : '单位',
					width : 70,
					'dataIndex' : 'forderunit',
					sortable : true
				}, {
					'header' : '客户',
					hidden : true,
					'dataIndex' : 'fcustomerid',
					sortable : true
				}, {
					'header' : '修改时间',
					'dataIndex' : 'flastupdatetime',
					width : 140,
					sortable : true
				}, {
					'header' : '创建时间',
					'dataIndex' : 'fcreatetime',
					width : 140,
					sortable : true
				}, {
					'header' : '描述',
					hidden : true,
					'dataIndex' : 'fdescription',
					sortable : true
					}]
		});


//
//Ext.require([ 'Ext.grid.*', 'Ext.toolbar.Paging', 'Ext.util.*', 'Ext.data.*',
//		'Ext.ux.form.SearchField', 'Ext.selection.CheckboxModel',
//		'Ext.ux.grid.FiltersFeature', 'Ext.ux.ajax.SimManager']);
//var custname = "";
//var filters = {
//		ftype : 'filters',
//		encode : true, // json encode the filter query
//		local : false, // defaults to false (remote filtering)
//		filters : [{
//					type : 'boolean',
//					dataIndex : 'visible'
//				}]
//	};
//var tbar = Ext.create("Ext.Toolbar", {
//	height : 40,
//	items : [ {
//		// id : 'AddButton',
//		height : 40,
//		text : '新  增',
//		handler : onAddButtonClick
//	}, {
//		// id : 'SaveButton',
//		text : '修   改',
//		height : 40,
//		handler : onEditButtonClick
//	}, {
//		// id : 'DelButton',
//		text : '删    除',
//		height : 40,
//		handler : onDelButtonClick
//	}, {
//		// id : 'RefreshButton',
//		text : '刷    新',
//		height : 40,
//		handler : onRefreshButtonClick
//	}, {
//	// id : 'RefreshButton',
//		text : '关联产品',
//		height : 40,
//		handler : onCustRelationProductClick
//	}
////	{
////		// id : 'RefreshButton',
////		text : '启    用',
////		height : 40,
////		handler : onEffectButtonClick
////	}, {
////		// id : 'RefreshButton',
////		text : '禁    用',
////		height : 40,
////		handler : onEffectButtonClick
////	}
//	]
//});
//function onRefreshButtonClick(btn) {
//	// MainMenuTreePanel.store.load();
//	// Ext.MessageBox.alert('提示', '刷新')
//	// var
//	// grid=Ext.getCmp("DJ.System.UserList");//Ext.getCmp("DJ.System.UserListPanel")
//	// var record = grid.getSelectionModel().getSelection();
//	// if (record.length == 0) {
//	// Ext.MessageBox.show({
//	// title : "提示",
//	// msg : "请先选择您要操作的行!"
//	// // icon: Ext.MessageBox.INFO
//	// })
//	// return;
//	// } else {
//	// var ids = "";
//	// for ( var i = 0; i < record.length; i++) {
//	// ids += record[i].get("fid")
//	// if (i < record.length - 1) {
//	// ids = ids + ",";
//	// }
//	// }
//	// Ext.MessageBox.show({
//	// title : "所选ID列表",
//	// msg : ids
//	// // icon: Ext.MessageBox.INFO
//	// })
//	// }
//	Ext.getCmp("DJ.System.product.CustproductCustList").store.load();
//
//}
//function onAddButtonClick(btn) {
//	// Ext.MessageBox.alert('提示', '新增')
//	var CustproductEdit = Ext.create('DJ.System.product.CustproductEdit');
//	
//	CustproductEdit.show();
//	//新增自动把客户树ID赋给Edit的客店;
//	
//	var record=Ext.getCmp("DJ.System.product.CustproductTree").getSelectionModel().getSelection();
//	if(record.length > 0)
//	{
//		if(record[0].data != null && record[0].data.id!="-1")
//		  {
//			  customerid=record[0].data.id;
//			  customerName=record[0].data.text;
//			  var cforms=Ext.getCmp("DJ.System.product.CustproductEdit.CForm").getForm();
////			  cforms.findField('fcustomerid').setReadOnly( {readOnly : true} );
////			  cforms.findField('fcustomerid').setValue(customerid);
////			  cforms.findField('fcustomerid').setRawValue(customerName);
//			  cforms.findField('fcustomerid').setmyvalue("\"fid\":\""+record[0].data.id+"\",\"fname\":\""+record[0].data.text+"\"");
//			 
//		  }
//	}
//}
//function onEditButtonClick(btn) {
//	// Ext.MessageBox.alert('提示', '修改');
//	var grid = Ext.getCmp("DJ.System.product.CustproductCustList");// Ext.getCmp("DJ.System.UserListPanel")
//	var record = grid.getSelectionModel().getSelection();
//	if (record.length != 1) {
//		Ext.MessageBox.alert('提示', '只能选中一条记录进行修改!');
//		return;
//	}
//	var fid = record[0].get("fid");
////	var custname = "";
//	Ext.Ajax
//			.request({
//				url : "GetCustproductInfo.do",
//				params : {
//					Custproductfid : fid
//				}, // 参数
//				success : function(response, option) {
//					var obj = Ext.decode(response.responseText);
//					if (obj.success == true) {
////						if (obj.data[0].fusedstatus == '1') {
////							Ext.MessageBox.alert('错误', '不能修改已经启用的供应商!');
////							return;
////						}
////						custname = obj.data[0].custname;
//						var CustproductEdit = Ext.create('DJ.System.product.CustproductEdit');
//						
//						var cform=Ext.getCmp("DJ.System.product.CustproductEdit.CForm").getForm();
//						cform.setValues(obj.data[0]);
////						cform.findField('fcustomerid').store.load();
////						cform.findField('fcustomerid').setValue(obj.data[0].fid);
////						cform.findField('fcustomerid').setRawValue(obj.data[0].fcustname);
//						cform.findField('fcustomerid').setmyvalue("\"fid\":\""+obj.data[0].fid+"\",\"fname\":\""+obj.data[0].fcustname+"\"");
//						CustproductEdit.show();
//						
//					} else {
//						Ext.MessageBox.alert('错误', obj.msg);
//					}
//				}
//			});
//}
//
//function onDelButtonClick(btn) {
//	// Ext.MessageBox.alert('提示', '删除');
//	var grid = Ext.getCmp("DJ.System.product.CustproductCustList");// Ext.getCmp("DJ.System.UserListPanel")
//	var record = grid.getSelectionModel().getSelection();
//	if (record.length == 0) {
//		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
//		return;
//	}
//	Ext.MessageBox
//			.confirm(
//					"提示",
//					"是否确认删除选中的内容?",
//					function(btn) {
//						if (btn == 'yes') {
//							var ids = "(";
//							for ( var i = 0; i < record.length; i++) {
//								var fid = record[i].get("fid");
////								if (Ext.util.Format.trim(fid) == "0f20f5bf-a80b-11e2-b222-60a44c5bbef3") {
////									Ext.MessageBox.alert('提示', '不能删除超级用户!');
////									return;
////								}
////								if(record[i].get("fusedstatus") == "1"){
////									Ext.MessageBox.alert('提示', '不能删除已启用供应商!');
////									return;
////								}
//								ids += "'" + fid + "'";
//								if (i < record.length - 1) {
//									ids = ids + ",";
//								}
//							}
//							ids = ids + ")";
//							Ext.Ajax
//									.request({
//										url : "DelCustproductList.do",
//										params : {
//											fidcls : ids
//										}, // 参数
//										success : function(response, option) {
//											var obj = Ext
//													.decode(response.responseText);
//											if (obj.success == true) {
//												Ext.MessageBox.alert('成功',
//														obj.msg);
//												grid.store.load();
//											} else {
//												Ext.MessageBox.alert('错误',
//														obj.msg);
//											}
//										}
//									});
//						}
//					});
//}
//function onCustRelationProductClick(btn)
//{
//	var grid = Ext.getCmp("DJ.System.product.CustproductCustList");
//	var record = grid.getSelectionModel().getSelection();
//	if (record.length != 1) {
//		Ext.MessageBox.alert('提示', '只能选中一条记录进行关联!');
//		return;
//	}
//	if(record[0].get("fcustomerid")=="null"||record[0].get("fcustomerid")=="")
//	{
//		Ext.MessageBox.alert('提示', '请先设置对应的客户或数据不同步请刷新！');
//		return;
//	}
//var edit=Ext.create('DJ.System.product.RelationProduct');
//	edit.settxtid(record[0].get("fid"));
//	edit.settxtvalue(record[0].get("fname"));
//	edit.show();
//	Ext.getCmp("DJ.System.product.CustproductCustList").store.load();
//}
//// Ext.Ajax.request({
//// url : 'GetUserList.do',
//// params : {
//// action : "query"
//// },
//// method : 'POST',
//// success : function(response) {
//// var obj = Ext.JSON.decode(response.responseText); // 获得后台传递json
////
//// var store = Ext.create('Ext.data.Store', {
//// fields : obj.fieldsNames,// 把json的fieldsNames赋给fields
//// data : obj.data
//// // 把json的data赋给data
//// }); //Ext.getCmp
//// var gridlist=Ext.getCmp("DJ.System.UserListPanel");
//// if (gridlist != null) {
//// Ext.getCmp("DJ.System.UserListPanel").reconfigure(store, obj.columModle); //
//// 定义grid的store和column
//// Ext.getCmp("DJ.System.UserListPanel").render();
//// }
////
//// }
//// });
//
//// Ext.define('DJ.System.UserList', {
//// extend : 'Ext.grid.Panel',
//// id : 'DJ.System.UserList',
//// closable : true,// 是否现实关闭按钮,默认为false
//// title : "用户管理",
//// emptyMsg : "没有数据显示",
//// tbar : tbar,
//// // bodyStyle : "width:100%",
//// // viewConfig : {
//// // forceFit : true
//// // },
//// // columnLines : true,
//// enableColumnMove:true,//是否允许拖放列，默认为true
//// enableColumnResize : true,// 是否允许改变列宽，默认为true
//// //autoExpandColumn:"memory",
//// items : [{fieldsNames:[{name: 'fid'},{name: 'fname'},{name: 'feffect'},{name:
//// 'fcustomername'},{name:'femail'},{name:'ftel'},{name:'fcreatetime'}]}],
//// columns : []
//// });
//
//// 数据类型分为以下几种：
//// 1、auto（默认）
//// 2、string
//// 3、int
//// 4、float
//// 5、boolean
//// 6、date
//// 创建多选
//var selModel = Ext.create('Ext.selection.CheckboxModel');
//var userstore = Ext.create('Ext.data.Store', {
//	fields : [ {
//		name : 'fid'
//	}, {
//		name : 'fname'
//	}, {
//		name : 'fnumber'
//	}, {
//		name : 'fspec'
//	}, {
//		name : 'forderunit'
//	}, {
//		name : 'fcustomerid'
//	}, {
//		name : 'fdescription'
//	}, {
//		name : 'fcreatorid'
//	}, {
//		name : 'fcreatetime'
//	}, {
//		name : 'flastupdateuserid'
//	}, {
//		name : 'flastupdatetime'
//	}, {
//		name : 'Frelationed'
//	}],
//	pageSize : 100,
//	proxy : {
//		type : 'ajax',
//		// data : provice,
//		url : 'GetCustproductList.do',
//		extraParams : {fcustomerid:'' },
//		reader : {
//			type : 'json',
//			root : 'data',
//			totalProperty : 'total'
//		// totalProperty : 'total'
//		}
//	},
//	autoLoad : true
//});
//// var PageRowNumberer = Ext.extend(Ext.grid.RowNumberer, {
//// width : 40,
//// renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
//// if (store.lastOptions.params != null) {
//// var pageindex = store.lastOptions.params.start;
//// return pageindex + rowIndex + 1;
//// } else {
//// return rowIndex + 1;
//// }
//// }
//// });
////定义渲染函数，格式化性别显示  
//function formatfusedstatus(value){
//    return value=='1'?'启用':'禁用';  
//}
//Ext.define('DJ.System.product.CustproductCustList', {
//	extend : 'Ext.grid.Panel',
//	id : 'DJ.System.product.CustproductCustList',
//	// // Use a PagingGridScroller (this is interchangeable with a
//	// PagingToolbar)
//	// verticalScrollerType: 'paginggridscroller',
//	// // do not reset the scrollbar when the view refreshs
//	// invalidateScrollerOnRefresh: false,
//	// // infinite scrolling does not support selection
//	// disableSelection: true,
//	selModel : selModel,
//	features : [filters],
//	columnLines : true,
//	disableSelection : false,// 值为TRUE，表示禁止选择行
//	closable : true,// 是否现实关闭按钮,默认为false
//	title : "客户产品",
//	emptyMsg : "没有数据显示",
//	tbar : tbar,
//	dockedItems : [ {
//		xtype : 'pagingtoolbar',
//		store : userstore, // same store GridPanel is using
//		dock : 'bottom',
//		displayInfo : true,
//		items : ['->', {
//			text : '查询条件',
//			tooltip : 'Get Filter Data for Grid',
//			handler : function() {
//				var data = Ext.encode(Ext.getCmp('DJ.System.product.CustproductCustList').filters.getFilterData());
//				Ext.Msg.alert('All Filter Data', data);
//			}
//		}, {
//			text : '显示全部',
//			handler : function() {
//				Ext.getCmp('DJ.System.product.CustproductCustList').filters.clearFilters();
//			}
//		}]
//	} ],
//	autoExpandColumn : "fcustomername",
//	store : userstore,
//	//autoHeight: true, autoWidth:true,
//	columns : [ Ext.create('DJ.Base.Grid.GridRowNum'), 
//	            {
//					'header' : 'fid',
//					'dataIndex' : 'fid',
//					hidden : true,
//					hideable : false,
//					autoHeight: true, autoWidth:true,
//					sortable : true
//				}, {
//					'header' : '产品名称',
//					'dataIndex' : 'fname',
//					sortable : true,
//					filter : {
//						type : 'string'
//					}
//				}, {
//					'header' : '编码',
//					'dataIndex' : 'fnumber',
//					sortable : true,
//					filter : {
//						type : 'string'
//					}
//				}, {
//					'header' : '关联',
//					width : 40,
//					'dataIndex' : 'Frelationed',
//					sortable : true,
//					renderer: function(value){
//					        if (value == 1) {
//					            return '是';
//					        }
//					        else{
//					        	return '否';
//					        }
//					    },
//					filter : {
//						type : 'string'
//					}
//				}, {
//					'header' : '规格',
//					width : 70,
//					'dataIndex' : 'fspec',
//					sortable : true
//				}, {
//					'header' : '单位',
//					width : 70,
//					'dataIndex' : 'forderunit',
//					sortable : true
//				}, {
//					'header' : '客户',
//					hidden : true,
//					'dataIndex' : 'fcustomerid',
//					sortable : true
//				}, {
//					'header' : '修改时间',
//					'dataIndex' : 'flastupdatetime',
//					filter : {
//						type : 'datetime',
//						date: {
//				            format: 'Y-m-d'
//				        },
//				        time: {
//				            format: 'H:i:s A',
//				            increment: 1
//				        }
//					},
//					width : 140,
//					sortable : true
//				}, {
//					'header' : '创建时间',
//					'dataIndex' : 'fcreatetime',
//					filter : {
//						type : 'datetime',
//						date: {
//				            format: 'Y-m-d'
//				        },
//				        time: {
//				            format: 'H:i:s A',
//				            increment: 1
//				        }
//					},
//					width : 140,
//					sortable : true
//				}, {
//					'header' : '描述',
//					hidden : true,
//					'dataIndex' : 'fdescription',
//					sortable : true
//				}
//			]
//});
//
//
