Ext.define('DJ.Inv.productindetail.productindetailList', {
			extend : 'Ext.c.GridPanel',
			title : "入库管理",
			id : 'DJ.Inv.productindetail.productindetailList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetproductindetailList.do',
			Delurl : "Deleteproductindetail.do",
			EditUI : "DJ.Inv.productindetail.productindetailEdit",
			exporturl:"productindetailtoexcel.do",
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
				var grid = Ext.getCmp("DJ.Inv.productindetail.productindetailList");
				var record = grid.getSelectionModel().getSelection();
				if (record.length >= 1) {
					if(record[0].get("faudited")=='1'){
						throw "已审核数据不能进行修改!";
					}
				}
			},
			Action_AfterEditButtonClick : function(EditUI) {
				// 修改界面弹出后事件
				// 可对编辑界面进行复制
			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
				var grid = Ext.getCmp("DJ.Inv.productindetail.productindetailList");
				var record = grid.getSelectionModel().getSelection();
				if (record.length >= 1) {
					if(record[0].get("faudited")=='1'){
						throw "已审核数据不能删除!";
					}
				}
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			},
			fields : [{
						name : 'fid'
					}, {
						name : 'fnumber',
						myfilterfield : 'w.fnumber',
						myfiltername : '编码',
						myfilterable : true
//					}, {
//						name : 'fname'
//					}, {
//						name : 'fsimplename'
					}, {
						name : 'FProductID'
					}, {
						name : 'pdtname'
					}, {
						name : 'fsaleorderid'
					}, {
						name : 'forderentryid'
					}, {
						name : 'plannumber',
						myfilterfield : 's.fnumber',
						myfiltername : '制造商订单编号',
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
						name : 'ftype'
//					}, {
//						name : 'fupdatetime'
//					}, {
//						name : 'lfname'
					}, {
						name : 'fcreatetime'
					}, {
						name : 'cfname'
					},{
						name : 'fdescription'
					}, {
						name : 'faudittime'
					}, {
						name : 'fauditorid'
					}, {
						name : 'fauditor'
					},{
						name : 'faudited'
					}],
			columns : [
			           {
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						'header' : '编码',
						'dataIndex' : 'fnumber',
						sortable : true
//					}, {
//						'header' : '名称',
//						'dataIndex' : 'fname',
//						sortable : true
//					}, {
//						'header' : '简称',
//						'dataIndex' : 'fsimplename',
//						sortable : true
					}, {
						'header' : '产品名称',
						'dataIndex' : 'pdtname',
						sortable : true
					}, {
						'header' : '制造商订单编号',
						'dataIndex' : 'plannumber',
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
					}, {
						'header' : '仓库类型',
						dataIndex : 'ftype',
						sortable : true,
						 renderer: function(value){
						        if (value == 1) {
						            return '生产入库';
						        }else if(value==2)
						        	{
						        	return '半成品';}
						        else{
						        	return '未分类';
						        }
						    }
					}, {
						'header' : '审核人',
						'dataIndex' : 'fauditorid',
						hidden : true,
						sortable : true
					}, {
						'header' : '审核人',
						'dataIndex' : 'fauditor',
						sortable : true
					}, {
						'header' : '审核',
						dataIndex : 'faudited',
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
						'header' : '审核时间',
						dataIndex : 'faudittime',
						width : 150,
						sortable : true
					}
				],
				selModel:Ext.create('Ext.selection.CheckboxModel')
		})
		
//function actionAudit(btn) {
//	var grid = Ext.getCmp("DJ.Inv.productindetail.productindetailList");
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
//					Ext.getCmp("DJ.Inv.productindetail.productindetailList").store.load();
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
//					Ext.getCmp("DJ.Inv.productindetail.productindetailList").store.load();
//				} else {
//					Ext.MessageBox.alert('错误', obj.msg);
//				}
//			}
//		});
//	}
//}
