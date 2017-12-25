Ext.define('DJ.Inv.Storebalance.StorebalanceList', {
			extend : 'Ext.c.GridPanel',
			title : "库存表",
			id : 'DJ.Inv.Storebalance.StorebalanceList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetStorebalanceList.do',
			Delurl : "",
			EditUI : "",
			exporturl:"Storebalancetoexcel.do",
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
//			custbar : [{
//				// id : 'DelButton',
//				text : '审核',
//				height : 30,
//				handler : //actionAudit(1)
//					function() {
//					
//				}
//			}, {
//				// id : 'DelButton',
//				text : '反审核',
//				height : 30,
//				handler : //actionAudit(0)
//					function() {
//					
//				}
//			}],
			fields : [{
						name : 'fid'
					}, {
						name : 'fnumber',
						myfilterfield : 'w.fnumber',
						myfiltername : '编码',
						myfilterable : true
					}, {
						name : 'productplannum'
					}, {
						name : 'FProductID'
					}, {
						name : 'pdtname',
						myfilterfield : 'd.fname',
						myfiltername : '产品名称',
						myfilterable : true
					}, {
						name:'FWarehouseID'
					}, {
						name : 'whname'
					}, {
						name : 'FWarehouseSiteID'
					}, {
						name : 'whsname'
					}, {
						name : 'finqty'
					}, {
						name : 'foutqty'
					}, {
						name : 'fbalanceqty',
						myfilterfield : 'w.fbalanceqty',
						myfiltername : '库存数量',
						myfilterable : true
					}, {
						name : 'fupdatetime'
					}, {
						name : 'lfname'
					}, {
						name : 'fcreatetime'
					}, {
						name : 'cfname'
					},{
						name : 'fdescription'
					}],
			columns : [
			           {
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						'header' : '产品名称',
						'dataIndex' : 'pdtname',
						sortable : true
					}, {
						'header' : '制造商编号',
						'dataIndex' : 'productplannum',
						sortable : true
					}, {
						'header' : '仓库',
						dataIndex : 'whname',
						sortable : true
					}, {
						'header' : '库位',
						dataIndex : 'whsname',
						sortable : true
					}, {
						'header' : '入库数量',
						dataIndex : 'finqty',
						sortable : true,
						xtype: 'numbercolumn',
						format:'0'
//						format:'0.0000'
					}, {
						'header' : '出库数量',
						dataIndex : 'foutqty',
						sortable : true,
						xtype: 'numbercolumn',
						format:'0'
//						format:'0.0000'
					}, {
						'header' : '库存数量',
						dataIndex : 'fbalanceqty',
						sortable : true,
						xtype: 'numbercolumn',
						format:'0'
//						format:'0.0000'
//					},{
//						'header' : '修改人',
//						dataIndex : 'lfname',
//						sortable : true
//					}, {
//						'header' : '修改时间',
//						dataIndex : 'fupdatetime',
//						width : 150,
//						sortable : true
					}, {
						'header' : '创建人',
						dataIndex : 'cfname',
						sortable : true
					}, {
						'header' : '创建时间',
						dataIndex : 'fcreatetime',
						width : 150,
						sortable : true
//					}, {
//						'header' : '描述',
//						'dataIndex' : 'fdescription',
//						sortable : true
					}
				],
				selModel:Ext.create('Ext.selection.CheckboxModel')
		})
		
//function actionAudit(btn) {
//	var grid = Ext.getCmp("DJ.Inv.productindetailList");
//	var record = grid.getSelectionModel().getSelection();
//	if (record.length != 1) {
//		Ext.MessageBox.alert('提示', '只能选中一条记录进行修改!');
//		return;
//	}
//	var fid = record[0].get("fid");
//	
//	if(btn == 1){
//		Ext.Ajax.request({
//			url : "auditpit.do",
//			params : {
//				fid : fid
//			}, // 参数
//			success : function(response, option) {
//				var obj = Ext.decode(response.responseText);
//				if (obj.success == true) {
//					Ext.MessageBox.alert('成功', obj.msg);
//					Ext.getCmp("DJ.Inv.productindetailList").store.load();
//				} else {
//					Ext.MessageBox.alert('错误', obj.msg);
//				}
//			}
//		});
//	}else if (btn ==0){
//		Ext.Ajax.request({
//			url : "unauditpit.do",
//			params : {
//				fid : fid
//			}, // 参数
//			success : function(response, option) {
//				var obj = Ext.decode(response.responseText);
//				if (obj.success == true) {
//					Ext.MessageBox.alert('成功', obj.msg);
//					Ext.getCmp("DJ.Inv.productindetailList").store.load();
//				} else {
//					Ext.MessageBox.alert('错误', obj.msg);
//				}
//			}
//		});
//	}
//}
