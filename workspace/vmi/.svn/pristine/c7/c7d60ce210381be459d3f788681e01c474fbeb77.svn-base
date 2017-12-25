Ext.define('DJ.order.Deliver.AssignAllotEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : 'DJ.order.Deliver.AssignAllotEdit',
	modal : true,
	title : "指定订单",
	ctype : "Delivers",
	width : 611,// 230, //Window宽度
	height : 357,// 137, //Window高度
	resizable : false,
//	url : 'saveAssginOrder.do',
	url : 'deliversToAllot.do?allottype=1',
	infourl : 'getDeliversInfo.do',
	viewurl : 'getDeliversInfo.do',
	closable : true, // 关闭按钮，默认为true
	cverifyinput : function() {
	var liststore=Ext.getCmp("DJ.order.Deliver.AssignAllotEdit.StoreBalanceList").store;
	var totalnum= Ext.getCmp("DJ.order.Deliver.AssignAllotEdit.famount").getValue();
	for (var i = 0, n = liststore.data.items.length; i < n; i++) {
		if (liststore.data.items[i].data.fbalanceqty == 0) {
				throw "分配数量不能为0";
		}
	}
	for (var i = 0; i < liststore.data.items.length; i++) {
		if (liststore.data.items[i].data.fid == "") {
			throw "产品名称不能为空";
		}
	}
	var s = 0;
	for (var i = 0; i < liststore.data.items.length; i++) {
		s += liststore.data.items[i].data.fbalanceqty;
	}
	if (s != totalnum) {
		throw "分配数量和必须为"+totalnum;
	} 
	},
	labelAlign : "left",
	layout : {
				align : "stretch",
				type : "vbox"
	},
	initComponent : function() {
		Ext.apply(this, {
			items : [{
				xtype : "textfield",
				name:'fnumber',
				fieldLabel : "要货管理单号",
				readOnly : true,
				width : 400,
				labelWidth : 80,
				flex : 1
			},{
				xtype : 'cTable',
				name : "Storebalance",
				id : "DJ.order.Deliver.AssignAllotEdit.StoreBalanceList",
//				width : 400,
				flex : 1,
				height : 250,
				pageSize : 100,
				url : "",
//				parentfield : "e.fid",
				features : [{ftype : "summary"}],
				plugins : [Ext.create("Ext.grid.plugin.RowEditing", {
						clicksToEdit : 2,
						errorSummary : false,
						saveBtnText : "更新",
						cancelBtnText : "取消",
						autoCancel : false
					})],
//				plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
//					clicksToEdit : 1
//				})],
				fields : [{
					name : "fid"
				}, {
					name : "pdtname",
					persist : false
				}, {
					name : "fproductplanId"
				},{
					name:"fcreatorid"
//					persist : false
				}, {
					name : "fbalanceqty",
						type : "int",
						defaultValue : 0
				}, {
					name : "forderentryid"
				}, {
					name : "fsaleorderid"
				},{
					name :"fsupplierID"
				},{
					name :"ftype"
				},{
					name :"fupdateuserid"
				}],
				columns : [{
					xtype : "rownumberer",
					text : "No"
				},{
					dataIndex : "pdtname",
					text : "产品名称",
					width : 150,
					editor : {
						id:'DJ.order.Deliver.AssignAllotEdit.StoreBalanceList.pdtname',
						xtype : 'cCombobox',
						displayField : 'fid', // 这个是设置下拉框中显示的值
						valueField : 'pdtname', // 这个可以传到后台的值
						allowBlank : false,
						blankText : '请选择！',
						editable : false, // 可以编辑不
						MyDataChanged : function(com) {
							var row = Ext
									.getCmp("DJ.order.Deliver.AssignAllotEdit.StoreBalanceList")
									.getSelectionModel().getSelection();
							row[0].set("fid", com[0].data.fid);
							row[0].set("fcreatorid", com[0].data.productplannum);
							row[0].set("fproductplanId", com[0].data.pfid);
							row[0].set("forderentryid", com[0].data.forderentryid);
							row[0].set("fsaleorderid", com[0].data.fsaleorderid);
							row[0].set("fsupplierID", com[0].data.fsupplierid);
							row[0].set("ftype", com[0].data.ftype);
							row[0].set("fupdateuserid", com[0].data.fissuit);
//							row[0].set("fcreatorid", com[0].data.productplannum);
						},
					    beforeExpand : function() {
							 var editwidgt=	Ext.getCmp("DJ.order.Deliver.AssignAllotEdit.StoreBalanceList.pdtname");
							var productid=Ext.getCmp("DJ.order.Deliver.AssignAllotEdit.fproductid").getValue();//_combo.getValue();
							var famount=Ext.getCmp("DJ.order.Deliver.AssignAllotEdit.famount").getValue();//_combo.getValue();
	        	    		editwidgt.setDefaultfilter([{
//								myfilterfield : "w.fproductid",
	        	    			myfilterfield : "fparentid",
								CompareType : "=",
								type : "string",
								value : productid
							}]);
	        	    		editwidgt.setDefaultmaskstring(" #0 ");

    	        	   		 },
						MyConfig : {
							width : 800,// 下拉界面
							height : 200,// 下拉界面
							url : 'GetStorebalancebyTypeList.do', // 下拉数据来源
							fields : [{
								name : 'fid'
							}, {
								name : 'pdtname'
							}, {
								name : 'productplannum',
								myfilterfield : 'pp.fnumber',
								myfiltername : '制造商编码',
								myfilterable : true
							},{
								name:'pfid'
							}, {
								name : 'fproductid'
							}, {
								name : 'finqty'
							}, {
								name : 'foutqty'
							}, {
								name : 'fbalanceqty'
							}, {
								name : 'sfname'
							}, {
								name : 'fsaleorderid'
							}, {
								name : 'forderentryid'
							}, {
								name : 'fsupplierid'
							},{
								name:'favailablenum'
							},{
								name:'fissuit'
							},{
								name:'ftype'
							},{
								name:'fallotqty'
							}],
							columns : [{
								'header' : 'fid',
								'dataIndex' : 'fid',
								hidden : true,
								hideable : false,
								autoHeight : true,
								autoWidth : true,
								sortable : true
							}, {
								'header' : '供应商名称',
								'dataIndex' : 'sfname',
								sortable : true

							}, {
								'header' : '入库数量',
								'dataIndex' : 'finqty',
								sortable : true

							}, {
								'header' : '出库数量',
								'dataIndex' : 'foutqty',
								sortable : true
							}, {
								'header' : '库存数量',
								'dataIndex' : 'fbalanceqty',
								sortable : true
							}, {
								'header' : '可用数量',
								'dataIndex' : 'favailablenum',
								sortable : true
							}, {
								'header' : '调拨在途数量',
								'dataIndex' : 'fallotqty',
								sortable : true
							}, {
								'header' : '制造商编码',
								'dataIndex' : 'productplannum',
								sortable : true
							},{
								'dataIndex' : 'pdtname',
								hidden : true,
								hideable : false
							},{
								'dataIndex' : 'fproductid',
								hidden : true,
								hideable : false
								},{
								'dataIndex' : 'pfid',
								hidden : true,
								hideable : false
							},{
								'dataIndex' : 'fsaleorderid',
								hidden : true,
								hideable : false
							},{
								'dataIndex' : 'forderentryid',
								hidden : true,
								hideable : false
							},{
								'dataIndex' : 'fsupplierid',
								hidden : true,
								hideable : false
							},{
								'dataIndex' : 'forderentryid',
								hidden : true,
								hideable : false
							},{
								'dataIndex' : 'fsupplierid',
								hidden : true,
								hideable : false
							},{
								'dataIndex' : 'fissuit',
								hidden : true,
								hideable : false
							},{
								'dataIndex' : 'ftype',
								hidden : true,
								hideable : false
							}]
						}

					}
				}, {
						xtype : "numbercolumn",
						summaryRenderer : function(val, params, data) {
							return "总计 : " + val + " <br> 还剩 :" + (Ext.getCmp("DJ.order.Deliver.AssignAllotEdit.famount").getValue() - val)	
						},
						format : "0,000",
						summaryType : "sum",
						dataIndex : "fbalanceqty",
						text : "数量",
						editor : {
							xtype : "numberfield",
							allowBlank : false,
							minValue : 1,
							step : 1,
							minText:'填写的数量要大于等于1',
							blankText : "请填写数量"
						}
				}, {
					dataIndex : "fcreatorid",
					text : "制造商编号"
				}, {
					dataIndex : "fproductplanId",
					text : "产品ID",
					hidden : true,
					hideable : false
				}]
				}, {
				xtype:'hidden',
				name:'fid',
					flex : 1
			}, {
				xtype : 'hidden',
				name:'famount',
					flex : 1,
				id:"DJ.order.Deliver.AssignAllotEdit.famount"
			}, {
				xtype : 'hidden',
					flex : 1,
				name:'fproductid',
				id:"DJ.order.Deliver.AssignAllotEdit.fproductid"
			}]

		}), this.callParent(arguments);
	},

	bodyStyle : "padding-top:5px;padding-left:10px"
});
