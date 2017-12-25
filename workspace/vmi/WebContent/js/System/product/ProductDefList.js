﻿Ext.require(['Ext.grid.*', 'Ext.toolbar.Paging', 'Ext.util.*', 'Ext.data.*',
		'Ext.ux.form.SearchField', 'Ext.selection.CheckboxModel',
		'Ext.ux.grid.FiltersFeature', 'Ext.ux.ajax.SimManager', 'Ext.ux.grid.MySimpleGridContextMenu']);

Ext.ns("Ext.app.ProductDefList");

Ext.app.ProductDefList.editPurchasePrice = function(btn) {
	var grid = Ext.getCmp("DJ.System.product.ProductDefList");// Ext.getCmp("DJ.System.UserListPanel")

	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行操作!');
		return;
	}

	// var editui = Ext.create('DJ.System.product.ProductPurchasePrice');

	var editui = Ext.getCmp("DJ.System.product.ProductPurchasePrice");

	if (editui == null) {
		editui = Ext.create('DJ.System.product.ProductPurchasePrice');
	}

	editui.seteditstate("edit");
	editui.setparent('DJ.System.product.ProductDefList');

	editui.loadfields(record[0].get("d_fid"));

	editui.getform().getForm().findField("fproductid").setReadOnly(true);
	editui.getform().getForm().findField("username").setReadOnly(true);
	editui.getform().getForm().findField("fcreatetime").setReadOnly(true);

	editui.show();
}

Ext.app.ProductDefList.upLoadImage = function(btn) {
	var grid = Ext.getCmp("DJ.System.product.ProductDefList");// Ext.getCmp("DJ.System.UserListPanel")

	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行操作!');
		return;
	}

	var win = Ext.create('DJ.System.product.ProductDefList.loadupWin');

	win.down("textfield[name=fproductID]").setValue(record[0].get("d_fid"));
	win.down("textfield[name=productName]").setValue(record[0].get("d_fname"));

	win.down("textfield[name=productName]").setReadOnly(true);

	win.show();
}

Ext.app.ProductDefList.ViewImage = function(btn) {
	var grid = Ext.getCmp("DJ.System.product.ProductDefList");// Ext.getCmp("DJ.System.UserListPanel")

	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行操作!');
		return;
	}

	var win = Ext.getCmp('DJ.System.product.ProductDrawListWin');
	if (win == null) {
		win = Ext.create('DJ.System.product.ProductDrawListWin');
	}

	var myfilter = [];
	myfilter.push({
		myfilterfield : "fproductID",
		CompareType : " = ",
		type : "string",
		value : record[0].get("d_fid")
	});

	var pstore = win.down("grid").getStore();

	pstore.setDefaultfilter(myfilter);
	pstore.setDefaultmaskstring(" #0 ");

	var me = this;
	// var el = me.getEl();
	// el.mask("系统处理中,请稍候……");

	Ext.Ajax.request({
		timeout : 60000,
		url : "selectProductdraws.do?productID="
				+ encodeURIComponent(record[0].get("d_fid")),
		success : function(response, option) {

			var obj = Ext.decode(response.responseText);
			if (obj.success == true) {

				if (obj.data != undefined && !obj.data.length == 0) {

					// win.down("grid").productID =
					// encodeURIComponent(record[0].get("d_fid"));

					// Ext.getCmp("DJ.System.product.ProductDrawList.fproductID")
					// .setDefaultfilter([{
					// myfilterfield : "fproductID",
					// CompareType : "=",
					// type : "string",
					// value : record[0].get("d_fid")
					// }]);
					win.show();

					pstore.load();

				} else {
					Ext.Msg.alert("提示", "没有图片");

					// win.close( );

					Ext.destroy(win);
				}

			} else {
				Ext.MessageBox.alert('错误', obj.msg);

			}
			// el.unmask();
		}
	});

		// var gridT = Ext.create("DJ.System.product.ProductDrawList");

		// win.down("grid").store.load({
		// scope : this,
		//
		// url : "selectProductdraws.do?productID="
		// + encodeURIComponent(record[0].get("d_fid")),
		//
		// callback : function(records, operation, success) {
		// // the operation object
		// // contains all of the details of the load operation
		// // console.log(records);
		// if (success) {
		// if (!records.length == 0) {
		// win.show();
		// } else {
		// Ext.Msg.alert("提示", "没有图片");
		//
		// Ext.destroy(win);
		// }
		// } else {
		// Ext.Msg.alert("提示", "失败");
		// }
		//
		// }
		// });

}

Ext.app.ProductDefList.tbar = Ext.create("Ext.Toolbar", {
	height : 40,
	enableOverflow : true,
	items : [{
		// id : 'AddButton',
		height : 40,
		text : '新  增',
		handler : onAddButtonClick
	}, {
		// id : 'SaveButton',
		text : '修   改',
		height : 40,
		handler : onEditButtonClick
			// handler : editProportion
			}, {
				// id : 'DelButton',
				text : '删    除',
				height : 40,
				handler : onDelButtonClick
			}, {
				// id : 'RefreshButton',
				text : '刷    新',
				height : 40,
				handler : onRefreshButtonClick
			}, 
//			{
//				// id : 'RefreshButton',
//				text : '关联客户产品',
//				height : 40,
//				handler : onRelationCustClick
//			}, 
			{
				// id : 'RefreshButton',
				text : '查看产品',
				height : 40,
				handler : onDetailClick
			}, {
				// id : 'DelButton',
				text : '启 用',
				height : 30,
				handler : onEffectClick
			}, {
				// id : 'DelButton',
				text : '禁 用',
				height : 30,
				handler : onForbiddenClick
			}, {
				// id : 'DelButton',
				text : '审核',
				height : 30,
				handler : onAuditClick
			}, {
				// id : 'DelButton',
				text : '反审核',
				height : 30,
				handler : OnUnauditClick
			}, {
				// id : 'DelButton',
				text : '编辑生产比例',
				height : 30,
				handler : editProportion

			}, {
				// id : 'DelButton',
				text : '编辑产品结构',
				height : 30,
				handler : editTreeLink
			}, {
				// id : 'DelButton',
				text : '编辑关联客户产品',
				height : 30,
				handler : editproductStruct
			}, {
				// id : 'DelButton',
				text : '结算价',
				height : 30,
				handler : productprice

			}, {
				// id : 'DelButton',
				text : '采购价',
				height : 30,
				handler : Ext.app.ProductDefList.editPurchasePrice

			}, {
				// id : 'DelButton',
				text : '上传图片',
				height : 30,
				handler : Ext.app.ProductDefList.upLoadImage

			}, {
				// id : 'DelButton',
				text : '查看图片',
				height : 30,
				handler : Ext.app.ProductDefList.ViewImage

			}, {
				// id : 'SaveButton',
				text : '生成新版',
				height : 40,
				handler : onVersionButtonClick
			// handler : editProportion
			}, 
//				{
//				text : 'test',
//				height : 40,
//				handler : function() {
//
//					var win = Ext.create("DJ.tools.file.MultiUploadDialog", {
//
//						id : "testidwin",
//
//						panelId : "testid",
//
//						url : 'fileUploadTest.do',
//						max_file_size : '11mb',
//						unique_names : true,
//						multiple_queues : true,
//						chunk_size : '3mb',
//						file_data_name : "upload1",
//						multipart : false,
//						multipart_params : {
//							'abc' : "cba"
//						}
////						,
////						filters : [{
////							title : "jpg文档",
////							extensions : "jpg,jpeg"
////						}]
//
//					});
//
//					win.show();
//
//				}
//
//			},
				{
				text : '附件',
				height : 40,
				handler : function() {

					var grid = Ext.getCmp("DJ.System.product.ProductDefList");// Ext.getCmp("DJ.System.UserListPanel")

					var record = grid.getSelectionModel().getSelection();
					if (record.length != 1) {
						Ext.MessageBox.alert('提示', '只能选中一条记录进行操作!');
						return;
					}

					var myfilter = [];
					myfilter.push({
						myfilterfield : "fparentid",
						CompareType : " = ",
						type : "string",
						value : record[0].get("d_fid")
					});

					
					
					var gridFile = Ext
								.getCmp("DJ.System.product.ProductDefList.AccessoryList");
					
//								gridFile.close();
								
					if (gridFile == null) {
					
						
						
						gridFile = Ext
								.create("DJ.System.product.ProductDefList.AccessoryList");
						
						
					}
								
					
					var pstore = gridFile.getStore();

					pstore.setDefaultfilter(myfilter);
					pstore.setDefaultmaskstring(" #0 ");

					pstore.loadPage(1);
					
					var win = Ext.getCmp("productasWin");
					
					if (win == null) {
					
						win = Ext.create('Ext.window.Window', {
						
						id : "productasWin",
						closeAction : 'hide',
						title : '附件',
						height : 300,
						width : 450,
						layout : 'fit',
						items : [gridFile]
						
//						,
//						
//						close: function( panel, eOpts ) {
//						
//							Ext.getCmp("DJ.System.product.ProductDefList.AccessoryList").close();
//							
//						}
						
					})
						
					}
					
					win.show();

				}

			}]
});

function productprice(btn) {
	var grid = Ext.getCmp("DJ.System.product.ProductDefList");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行操作!');
		return;
	}

	var editui = Ext.create('DJ.System.product.productpriceEdit');
	editui.seteditstate("edit");
	editui.setparent('DJ.System.product.ProductDefList');
	editui.loadfields(record[0].get("d_fid"));
	editui.getform().getForm().findField("fproductid").setReadOnly(true);
	editui.getform().getForm().findField("username").setReadOnly(true);
	editui.getform().getForm().findField("fcreatetime").setReadOnly(true);
	editui.show();
}
function editproductStruct(btn) {

	var grid = Ext.getCmp("DJ.System.product.ProductDefList");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行操作!');
		return;
	}
	if(record[0].get('schemedesignid')!=''){
		Ext.Msg.alert('提示','包装需求产生的产品不能操作！');
		return;
	}
	var fidd = record[0].get("d_fid");
	var el = grid.getEl();
	el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
		url : "GetProductRelationInfo.do",
		params : {
			fid : fidd
		}, // 参数
		success : function(response, option) {
			var obj = Ext.decode(response.responseText);
			if (obj.success == true) {
				var productdefdetail = Ext
						.getCmp('DJ.System.product.ProductRelationEdit');
				if (productdefdetail == null) {
					productdefdetail = Ext
							.create('DJ.System.product.ProductRelationEdit');
				}
				var structtree = Ext
						.getCmp('DJ.System.product.ProductRelationEdit.ProductTreeEdit');
				var gridProductWin = Ext
						.getCmp('DJ.System.product.ProductRelationEdit.CustProductList');
				Ext
						.getCmp('DJ.System.product.ProductRelationEdit.customerfname')
						.setValue(obj.data[0].fname);
				// setmyvalue("\"fid\":\""+ obj.data[0].fcustomerid +
				// "\",\"fname\":\""+ obj.data[0].fname + "\"");
				var idd = obj.data[0].rid;
				if (obj.data[0].rid == null || obj.data[0].rid == "null" || obj.data[0].rid == "") {
					idd = fidd + "," + obj.data[0].fcustomerid;
				}
				var structStore = structtree.store;
				var rootvalue = {
					expanded : true,
					leaf : false,
					text : record[0].get('d_fname'),
					id : idd,
					custid : fidd + "," + obj.data[0].fcustomerid
				};
				structStore.setRootNode(rootvalue);
				gridProductWin.store.proxy.extraParams.fcustomerid = obj.data[0].fcustomerid;
				gridProductWin.store.load();
				productdefdetail.show();
			} else {
				Ext.MessageBox.alert('错误', obj.msg);
			}
			el.unmask();
		}
	});

}

function onEffectClick(btn) {
	var grid = Ext.getCmp("DJ.System.product.ProductDefList");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
		return;
	}
	Ext.MessageBox.confirm("提示", "是否确认启用选中的产品?", function(btn) {
		if (btn == 'yes') {
			var ids = "";
			for (var i = 0; i < record.length; i++) {
				if (record[i].get("d_feffect") == "1") {
					Ext.MessageBox.alert('提示', '所选中的产品已经启用!');
					return;
				}
				var fid = record[i].get("d_fid");
				ids += fid;
				if (i < record.length - 1) {
					ids = ids + ",";
				}
			}
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				url : "effectProducts.do",
				params : {
					fidcls : ids
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						// Ext.MessageBox.alert('成功', obj.msg);
						grid.store.load();
						djsuccessmsg(obj.msg);
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
		}
	});
}

function onForbiddenClick(btn) {
	var grid = Ext.getCmp("DJ.System.product.ProductDefList");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
		return;
	}
	Ext.MessageBox.confirm("提示", "是否确认禁用选中的产品?", function(btn) {
		if (btn == 'yes') {
			var ids = "";
			for (var i = 0; i < record.length; i++) {
				if (record[i].get("d_feffect") == "0") {
					Ext.MessageBox.alert('提示', '所选中的产品未启用!');
					return;
				}
				var fid = record[i].get("d_fid");
				ids += fid;
				if (i < record.length - 1) {
					ids = ids + ",";
				}
			}
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				url : "forbiddenProduct.do",
				params : {
					fidcls : ids
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						// Ext.MessageBox.alert('成功', obj.msg);
						grid.store.load();
						djsuccessmsg(obj.msg);
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
		}
	});
}

function editTreeLink(btn) {

	var grid = Ext.getCmp("DJ.System.product.ProductDefList");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行查看!');
		return;
	}
	if(record[0].get('schemedesignid')!=''){
		Ext.Msg.alert('提示','包装需求产生的产品不能操作！');
		return;
	}
	var fid = record[0].get("d_fid");

	var productdefdetail = Ext.getCmp('DJ.System.product.ProductTreeEdit');

	if (productdefdetail == null) {
		productdefdetail = Ext.create('DJ.System.product.ProductTreeEdit');
	}

	productdefdetail.show();

	var structtree = Ext
			.getCmp('DJ.System.product.ProductTreeEdit.ProductTreeEdit');

	var gridProductWin = Ext
			.getCmp('DJ.System.product.ProductTreeEdit.ProductDefList');

	var structStore = structtree.store;

	var rootvalue = {
		expanded : true,
		leaf : false,
		text : record[0].get('d_fname'),
		id : fid,
		fnumber : record[0].get('d_fnumber')
	};
	structStore.setRootNode(rootvalue);

	gridProductWin.store.proxy.extraParams.dbfid = fid;

	gridProductWin.store.load();

}

function onAuditClick(btn) {
	var grid = Ext.getCmp("DJ.System.product.ProductDefList");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行审核!');
		return;
	}
	if(record[0].get('schemedesignid')!=''){
		Ext.Msg.alert('提示','包装需求产生的产品不能操作！');
		return;
	}
	if (record[0].get("d_faudited") == "1") {
		Ext.MessageBox.alert('提示', '该记录已审核!');
		return;
	}
	var fid = record[0].get("d_fid");

	var el = grid.getEl();
	el.mask("系统处理中,请稍候……");

	Ext.Ajax.request({
		url : "auditProductdef.do",
		params : {
			fid : fid
		}, // 参数
		success : function(response, option) {
			var obj = Ext.decode(response.responseText);
			if (obj.success == true) {
				// Ext.MessageBox.alert('成功', obj.msg);
				Ext.getCmp("DJ.System.product.ProductDefList").store.load();
				djsuccessmsg(obj.msg);
			} else {
				Ext.MessageBox.alert('错误', obj.msg);
			}
			el.unmask();
		}
	});
}

function OnUnauditClick(btn) {
	var grid = Ext.getCmp("DJ.System.product.ProductDefList");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行反审核!');
		return;
	}
	if (record[0].get("d_faudited") == "0") {
		Ext.MessageBox.alert('提示', '该记录未审核!');
		return;
	}
	var fid = record[0].get("d_fid");
	var el = grid.getEl();
	el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
		url : "unauditProductdef.do",
		params : {
			fid : fid
		}, // 参数
		success : function(response, option) {
			var obj = Ext.decode(response.responseText);
			if (obj.success == true) {
				// Ext.MessageBox.alert('成功', obj.msg);
				Ext.getCmp("DJ.System.product.ProductDefList").store.load();
				djsuccessmsg(obj.msg);
			} else {
				Ext.MessageBox.alert('错误', obj.msg);
			}
			el.unmask();
		}
	});
}
function onDetailClick(btn) {

	var grid = Ext.getCmp("DJ.System.product.ProductDefList");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行查看!');
		return;
	}
	var fid = record[0].get("d_fid");

	var productdefdetail = Ext.create('DJ.System.product.ProductdefDetails');
	var structtree = Ext.getCmp('DJ.System.product.ProductStructureTree');
	var structStore = structtree.store;
	var rootvalue = {
		expanded : true,
		leaf : false,
		text : record[0].get('d_fname'),
		id : fid + ",-1",
		fnumber : record[0].get('d_fnumber'),
		fparentnode : "-1"
	};
	structStore.setRootNode(rootvalue);
	productdefdetail.show();

}
function onRefreshButtonClick(btn) {
	Ext.getCmp("DJ.System.product.ProductDefList").store.load();

}
function onAddButtonClick(btn) {
	var grid = Ext.getCmp("DJ.System.product.ProductDefList");
	var el = grid.getEl();
	el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
		url : "AddProductdef.do",
		success : function(response, option) {
			var obj = Ext.decode(response.responseText);
			if (obj.success == true) {
				var productdefEdit = Ext
						.create('DJ.System.product.ProductDefEdit');
				var cform = Ext
						.getCmp("DJ.System.product.ProductDefEdit.dForm")
						.getForm();

				cform.findField('fversion').setValue(1.1);
				cform.findField('fversion').setReadOnly(true);
				cform.findField('fisAddVersion').setValue(0);
				var trees =  Ext.getCmp(el.id).up("panel").westPanel;
				var record = trees.getSelectionModel().getSelection();
				if (record.length > 0) {

					if (record[0].data.id != -1) {
						// cform.findField('fcustomerid').setValue(record[0].data.id);
						// cform.findField('fcustomerid').(record[0].data.text);
						cform.findField('fcustomerid').setmyvalue("\"fid\":\""
								+ record[0].data.id + "\",\"fname\":\""
								+ record[0].data.text + "\"");
					}
				}
				productdefEdit.show();
			}
			el.unmask();
		}
	});

}
function onEditButtonClick(btn) {

	var grid = Ext.getCmp("DJ.System.product.ProductDefList");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行修改!');
		return;
	}
	if (record.length >= 1) {
		if (record[0].get("d_faudited") == '1') {
			Ext.MessageBox.alert('提示', '已审核数据不能进行修改!');
			return;
		}
//		if(record[0].get('schemedesignid')!=''){
//			Ext.Msg.alert('提示','包装需求产生的产品不能修改！');
//			return;
//		}
	}
	var fid = record[0].get("d_fid");

	var el = grid.getEl();
	el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
		url : "GetProduct.do",
		params : {
			fid : fid
		}, // 参数
		success : function(response, option) {
			var obj = Ext.decode(response.responseText);
			if (obj.success == true) {

				var productdefEdit = Ext
						.create('DJ.System.product.ProductDefEdit');
				var cform = Ext
						.getCmp("DJ.System.product.ProductDefEdit.dForm")
						.getForm();
				cform.setValues(obj.data[0]);
				cform.findField('fversion').setReadOnly(true);
				cform.findField('fcustomerid').setmyvalue("\"fid\":\""
						+ obj.data[0].fcustomerid + "\",\"fname\":\""
						+ obj.data[0].cname + "\"");
				// cform.findField('fcustomerid').setValue(obj.data[0].cname);
				// cform.findField('fcustomerid').setRawValue(obj.data[0].cname);
				cform.findField('fisAddVersion').setValue(0);
				productdefEdit.show();
			} else {
				Ext.MessageBox.alert('错误', obj.msg);
			}
			el.unmask();
		}
	});
}

function onVersionButtonClick(btn) {
	var grid = Ext.getCmp("DJ.System.product.ProductDefList");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '请选中一条记录生成新版!');
		return;
	}
	if (record.length >= 1) {
		if (record[0].get("d_fishistory") == '1') {
			Ext.MessageBox.alert('提示', '历史版本数据不能生成新版!');
			return;
		}
	}
	Ext.MessageBox.confirm("提示", "操作生成新版,所选产品将被禁用更改为历史版本且不可下单,该操作不可逆,确认生成新版吗?",
			function(btn) {
				if (btn == 'yes') {
					var fid = record[0].get("d_fid");

					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "GetVersionProduct.do",
						params : {
							fid : fid
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {

								var productdefEdit = Ext
										.create('DJ.System.product.ProductDefEdit');
								var cform = Ext
										.getCmp("DJ.System.product.ProductDefEdit.dForm")
										.getForm();
								cform.setValues(obj.data[0]);
								cform.findField('fversion').setValue(Ext.Number
										.toFixed((obj.data[0].fversion) * 1
												+ 0.1, 1));
								cform.findField('fversion').setReadOnly(true);
								cform.findField('fcustomerid')
										.setmyvalue("\"fid\":\""
												+ obj.data[0].fcustomerid
												+ "\",\"fname\":\""
												+ obj.data[0].cname + "\"");
								// cform.findField('fcustomerid').setValue(obj.data[0].cname);
								// cform.findField('fcustomerid').setRawValue(obj.data[0].cname);
								cform.findField('fisAddVersion').setValue(1);

								productdefEdit.show();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});
				}
			});

}

function onDelButtonClick(btn) {

	var grid = Ext.getCmp("DJ.System.product.ProductDefList");
	var record = grid.getSelectionModel().getSelection();
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
		return;
	}
	Ext.MessageBox.confirm("提示", "是否确认删除选中的内容?", function(btn) {
		if (btn == 'yes') {
			for (var i = 0; i < record.length; i++) {
				if (record[i].get("d_faudited") == '1') {
					Ext.MessageBox.alert('提示', '已审核数据不能删除!');
					return;
				}
				if(record[i].get('schemedesignid')!=''){
					Ext.Msg.alert('提示','包装需求产生的产品不能删除！');
					return;
				}
			}

			var ids = "";
			for (var i = 0; i < record.length; i++) {
				var fid = record[i].get("d_fid");
				ids += fid;
				if (i < record.length - 1) {
					ids = ids + ",";
				}
			}
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				url : "deleteProducts.do",
				params : {
					fidcls : ids
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						// Ext.MessageBox.alert('成功', obj.msg);
						grid.store.load();
						djsuccessmsg(obj.msg);
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
		}
	});
}

function onRelationCustClick(btn) {
	var grid = Ext.getCmp("DJ.System.product.ProductDefList");
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行关联!');
		return;
	}
	if(record[0].get('schemedesignid')!=''){
		Ext.Msg.alert('提示','包装需求产生的产品不能关联客户产品！');
		return;
	}
	var edit = Ext.create('DJ.System.product.RelationCust');
	edit.settxtid(record[0].get("d_fid"));
	edit.settxtvalue(record[0].get("d_fname"));
	edit.show();

}

var productstore = Ext.create('DJ.System.product.ProductDefStore');
var selModel = Ext.create('Ext.selection.CheckboxModel');

function formatEffect(value) {
	return value == '1' ? '是' : '否';
}

var filters = {
	ftype : 'filters',
	encode : true,
	local : false
};

Ext.define('DJ.System.product.ProductDefList', {
	extend : 'Ext.grid.Panel',
	id : 'DJ.System.product.ProductDefList',
	selModel : selModel,
	columnLines : true,
	disableSelection : false,// 值为TRUE，表示禁止选择行
	// closable : true,// 是否现实关闭按钮,默认为false
	title : "产品基础资料",
	emptyMsg : "没有数据显示",
	features : [filters],
	tbar : Ext.app.ProductDefList.tbar,
	
	plugins : [{
	
		ptype : "mysimplegridcontextmenu",
		useExistingButtons : ["button[text=附件]"]
		
	}],
	
	dockedItems : [{
		xtype : 'pagingtoolbar',
		store : productstore, // same store GridPanel is using
		dock : 'bottom',
		displayInfo : true,
		items : ['->', {
			text : '查询条件',
			tooltip : 'Get Filter Data for Grid',
			handler : function() {
				var data = Ext.encode(Ext
						.getCmp('DJ.System.product.ProductDefList').filters
						.getFilterData());
				Ext.Msg.alert('All Filter Data', data);
			}
		}, {
			text : '显示全部',
			handler : function() {
				Ext.getCmp('DJ.System.product.ProductDefList').filters
						.clearFilters();
			}
		}]

	}],
	autoExpandColumn : "d_fcharacter",
	store : productstore,
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
		text : 'fid',
		dataIndex : 'd_fid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		text : '编号',
		dataIndex : 'd_fnumber',
		sortable : true,
		filter : {
			type : 'string'
		}
	}, {
		text : '名称',
		dataIndex : 'd_fname',
		sortable : true,
		filter : {
			type : 'string'
		}
	}, {
		text : '版本号',
		dataIndex : 'd_fversion',
		sortable : true
	}, {
		text : '特征',
		dataIndex : 'd_fcharacter',
		sortable : true,
		width : 100
	}, {
		text : '箱型',
		dataIndex : 'd_fboxmodelid',
		sortable : true

	}, {
		text : '类型',
		dataIndex : 'd_fnewtype',
		sortable : true
	}, {
		text : '历史版本',
		dataIndex : 'd_fishistory',
		renderer : formatEffect,
		sortable : true
	}, {
		text : '是否启用',
		dataIndex : 'd_feffect',
		renderer : formatEffect,
		sortable : true
	}, {
		text : '修改人',
		dataIndex : 'u2_fname',
		sortable : true
	}, {
		text : '修改时间',
		dataIndex : 'd_flastupdatetime',
		width : 150,
		sortable : true

	}, {
		text : '创建人',
		dataIndex : 'u1_fname',
		sortable : true
	}, {
		text : '创建时间',
		dataIndex : 'd_fcreatetime',
		width : 150,
		sortable : true
	}, {
		text : '审核',
		dataIndex : 'd_faudited',
		renderer : formatEffect,
		sortable : true,
		filter : {
			type : 'boolean'
		}
	}, {
		text : '审核人',
		dataIndex : 'u3_fname',
		sortable : true
	}, {
		text : '审核时间',
		dataIndex : 'd_faudittime',
		width : 150,
		sortable : true

	},{
		dataIndex:'fcharactername',
		text:'特性',
		width : 150,
		sortable : true
	},{
		dataIndex:'fcharacterid',
		hidden:true
	}]

});

function editProportion(btn) {


	var grid = Ext.getCmp("DJ.System.product.ProductDefList");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	var productid = '';
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请选择数据!');
		return;
	}
	for(var i = 0;i<record.length;i++){
		productid += record[i].get('d_fid');
		if(i<record.length-1){
			productid += ",";
		}
	}
	var editui = Ext.create('DJ.System.product.ProportionEdit');
	editui.seteditstate("edit");
	editui.setparent('DJ.System.product.ProductDefList');
	if(record.length==1){
		editui.loadfields(record[0].get("d_fid"));
	}else{
		editui.down('textfield[name=fid]').setValue(productid);
		editui.down('textfield[name=fname]').hide();
	}
	editui.show();
}

Ext.define('DJ.System.product.ProductDefList.loadupWin', {
	extend : 'Ext.Window',
	id : 'DJ.System.product.ProductDefList.loadupWin',
	modal : true,
	// title : "E上传",
	width : 450,// 230, //Window宽度
	height : 260,// 137, //Window高度
	resizable : false,
	closable : true, // 关闭按钮，默认为true
	// renderTo: 'upload',
	items : [{
		xtype : 'form',
		id : 'DJ.System.product.ProductDefList.loadupWin.form',
		baseCls : 'x-plain',

		width : 400,
		bodyPadding : 10,
		margin : '20 10 20 20',
		frame : true,

		defaults : {
			labelSeparator : ":",
			labelWidth : 100,
			width : 300,
			allowBlank : false,
			labelAlign : "left",
			msgTarget : ""
		},

		items : [

		{
			xtype : 'textfield',
			name : 'fproductID',

			hidden : true

		}, {
			xtype : 'textfield',
			name : 'productName',
			fieldLabel : '产品名称'

		}, {
			xtype : 'textfield',
			name : 'fdrawnNo',
			fieldLabel : '图号'

		}, {
			xtype : 'textfield',
			name : 'fversion',
			fieldLabel : '版本',
			value : "V1.0"

		}, {
			xtype : 'filefield',
			name : 'fileName',
			fieldLabel : '上传',

			msgTarget : 'side',
			allowBlank : false,
			anchor : '100%',
			regex : /(.)+((\.jpg)|(\.bmp)|(\.png)|(\.gif)(\w)?)$/i,
			regexText : "文件格式选择不正确",
			buttonText : '选择文件'
		}],
		buttons : [{
			text : '上传',
			handler : function() {
				var form = this.up('form').getForm();

				// 构建参数，表单形式上传，据测试无法用配置式传参。用url构建式可以
				var fproductID = form.findField('fproductID').getValue();
				var fdrawnNo = form.findField('fdrawnNo').getValue();
				var fversion = form.findField('fversion').getValue();

				// var urlT = 'uploadProudctImage.do';
				//				
				// urlT = Ext.String.urlAppend(urlT, string )

				var paramsArray = [];

				// 解决特殊字符的编码问题

				paramsArray.push("?fproductID");
				paramsArray.push("=" + encodeURIComponent(fproductID) + "&");

				paramsArray.push("fdrawnNo");
				paramsArray.push("=" + encodeURIComponent(fdrawnNo) + "&");

				paramsArray.push("fversion");
				paramsArray.push("=" + encodeURIComponent(fversion) + "");

				var paramsT = paramsArray.join("");

				// paramsT = Ext.String.escapeRegex(paramsT);

				if (form.isValid()) {
					form.submit({
						url : 'uploadProudctImage.do' + paramsT,

						timeout : 30000,
						waitMsg : '正在保存文件...',
						success : function(fp, o) {
							// Ext.Msg.alert('提示信息',
							// '文件成功上传,文件名字为：'+o.result.file);
							// Ext.Msg.show({
							// // title : '提示信息',
							// msg : "成功",
							// minWidth : 200,
							// modal : true,
							// buttons : Ext.Msg.OK
							// })
							form.findField('fileName').setRawValue('');
							form.findField('fileName').up('window').close();
							djsuccessmsg("上传成功");
							// Ext.getCmp("DJ.order.Deliver.generateDeliversList").store.load();
						},
						failure : function(fp, o) {
							Ext.Msg.show({
								title : '失败',
								msg : o.result.msg,
								minWidth : 200,
								modal : true,
								buttons : Ext.Msg.OK
							})
							form.findField('fileName').setRawValue('');
						}
					});
				}
			}
		}]
	// }]
	}]
});

Ext.define('DJ.System.product.ProductDefList.AccessoryList', {
	id : 'DJ.System.product.ProductDefList.AccessoryList',
	ctype : 'Productdemandfile',
	extend : 'Ext.c.GridPanel',

	mixins : ['DJ.tools.grid.MyGridHelper'],

	pageSize : 50,
//	alias : 'widget.custproductcustaccessorylist',
	closable : false,// 是否现实关闭按钮,默认为false
	url : 'gainProductAccessory.do',
	exporturl : "",// 导出为EXCEL方法
	// height:200,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	onload : function() {

		var me = this;
		
		this
				._operateButtonsView(true, [me.id + '.refreshbutton',
				"DJ.System.product.ProductDefList.AccessoryList.upload",
				"DJ.System.product.ProductDefList.AccessoryList.delete"]);

//		this.getStore().on("beforeload", function(store, operation, eOpts) {
//
//			var records = Ext
//					.getCmp("DJ.System.product.CustproductCustTreeList")
//					.getSelectionModel().getSelection();
//
//			if (records.length == 1) {
//
//				operation.params = {
//
//					fcusproductid : records[0].get("id")
//
//				}
//
//			}
//
//		});

	},
	custbar : [

	{
		id : "DJ.System.product.ProductDefList.AccessoryList.upload",
		text : "上传附件",
		handler : function() {
	
					var grid = Ext.getCmp("DJ.System.product.ProductDefList");// Ext.getCmp("DJ.System.UserListPanel")

					var record = grid.getSelectionModel().getSelection();
			
					var win = Ext.create("DJ.tools.file.MultiUploadDialog", {

//						id : "testidwin",
//
//						panelId : "testid",

						url : 'uploadProductAccessory.do',
						max_file_size : '11mb',
						unique_names : false,
						multiple_queues : true,
						chunk_size : '3mb',
						file_data_name : "upload1",
						multipart : false,
						multipart_params : {
							'fparentid' : record[0].get("d_fid")
						},
						
						listeners : {
						
							close : function( panel, eOpts ) {
							
								
								Ext.getCmp("DJ.System.product.ProductDefList.AccessoryList").getStore().loadPage(1);
							}
						}
						
//						uploadListeners : {
//						
//							UploadComplete : function (up,file) {
//							
//								win.close();
//								
//							}
//							
//						}
						
//						,
//						filters : [{
//							title : "jpg文档",
//							extensions : "jpg,jpeg"
//						}]

					});

					win.show();
			
		}
	},
	{
	
		
		id : "DJ.System.product.ProductDefList.AccessoryList.delete",
		text : "删除",
		handler : function() {
		
			var records = this.up("grid").getSelectionModel().getSelection();
			
			var fids = [];
			
			Ext.each(records, function(ele, index, all){
			
				fids.push(ele.get("fid"));
				
				
			});
			
			var me = this;
			var el = me.getEl();
			el.mask("系统处理中,请稍候……");

			Ext.Ajax.request({
				timeout : 6000,
				
				params  : {
				
					fids : fids.join(",")
				
				},
				
				url : "deleteProductAccessorys.do",
				success : function(response, option) {

					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						me.up("grid").getStore().loadPage(1);

					} else {
						Ext.MessageBox.alert('错误', obj.msg);

					}
					el.unmask();
				}
			});
			
		}
	
		
	}

	],
	fields : [{
		name : 'fid'
	}, {
		name : 'fname'
	}, {
		name : 'fpath'
	}, {
		name : 'fparentid'
	}],
	columns : [{
		dataIndex : 'fid',
		hidden : true,
		hideable : false
	}, {
		text : '路径',
		dataIndex : 'fpath',
		id : 'DJ.order.Deliver.productdemandfile.fpath',
		hidden : true,
		hideable : false
	}, {

		// 'header' : '图片',
		// 'dataIndex' : 'fimagePath',
		text : '名称',
		xtype : "templatecolumn",
		// height : 100,
		width : 300,
		tpl : '<a href="{fpath}" target="_blank">{fname}</a>'
		// maxHeight : 10,
		// sortable : true,
//		width : 80

	}, {
		xtype : 'templatecolumn',
		text : '动作',
		dataIndex : 'fname',
		tpl : '<a href="downloadProductdemandFile.do?fid={fid}" target="_blank" style="text-decoration:none;">下载</a>',
		// flex:1
		width : 40
	}, {
		dataIndex : 'fparentid',
		hidden : true,
		hideable : false
	}]
});