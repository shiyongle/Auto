Ext.require(["Ext.ux.form.MyMixedSearchBox"]);

Ext.define('DJ.order.saleOrder.OrderInList', {
	extend : 'Ext.c.GridPanel',
	title : "制造商入库",
	id : 'DJ.order.saleOrder.OrderInList',
	pageSize : 50,

	mixins : ['DJ.tools.grid.MyGridHelper'],

	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetAffirmOrderInProductplan.do?',
	// 'Defaultmaskstring=0&Defaultfilter=[{myfilterfield:d.faffirmed,CompareType:=,type:int,value:1}]',
	Delurl : "",
	EditUI : "",
	exporturl : "productplantoexcel.do",
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等

		this._operateButtonsView(false, [this.id + '.addbutton',
				this.id + '.editbutton', this.id + '.viewbutton', this.id + '.delbutton']);

		// var instore=Ext.getCmp("DJ.order.saleOrder.OrderInList").store;
		// var instore= this.store;
		// var myfilter = [];
		// myfilter.push({
		// myfilterfield : "d.faffirmed",
		// CompareType : " = ",
		// type : "int",
		// value : 1
		// }
		// // ,{
		// // myfilterfield : "d.fcloseed",
		// // CompareType : " = ",
		// // type : "int",
		// // value : 0
		// // }
		// );
		// instore.setDefaultfilter(myfilter);
		// instore.setDefaultmaskstring(" #0 ");
		// // instore.setDefaultmaskstring(" #0 and #1 ");
		// instore.load();
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
		// Ext.getCmp("DJ.order.saleOrder.SaleOrderEdit").getform().getForm().findField('fnumber').hide();
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
		var grid = Ext.getCmp("DJ.order.saleOrder.OrderInList");
		var record = grid.getSelectionModel().getSelection();
		if (record.length >= 1) {
			if (record[0].get("faudited") == '1') {
				throw "已审核数据不能进行修改！";
			}
		}
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
		// 可对编辑界面进行复制
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
		var grid = Ext.getCmp("DJ.order.saleOrder.OrderInList");
		var record = grid.getSelectionModel().getSelection();
		for (var i = 0; i < record.length; i++) {
			if (record[i].get("faudited") == '1') {
				throw "已审核数据不能删除！";
			}
		}
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	custbar : [{
		// id : 'DelButton',
		text : '入库',
		height : 30,
		handler : // actionAudit(1)
		function() {
			var grid = Ext.getCmp("DJ.order.saleOrder.OrderInList");
			var record = grid.getSelectionModel().getSelection();
			if (record.length != 1) {
				Ext.MessageBox.alert('提示', '请选中一条记录入库！');
				return;
			}
			if (record[0].get("faffirmed") == 0
					|| record[0].get("faffirmed") == 'null') {
				Ext.MessageBox.alert('提示', '供应商未确认不能入库！');
				return;
			}
			var fid = record[0].get("fid");
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				url : "GetproductplanToIn.do",
				params : {
					fid : fid
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						var datas = obj.data[0];
						var SaleOrderOIEdit = Ext
								.create('DJ.order.saleOrder.OISaleOrderEdit');
						var finqty = datas.famount - datas.fstockinqty;
						Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.famount").setValue(finqty);
						if(datas.fschemedesignid!=''){
							Ext.Ajax.request({
								url:'getFtraitidBySchemeDesignId.do',
								params:{
									fid : datas.fschemedesignid
								},
								success:function(res){
									var o = Ext.decode(res.responseText);
									if(o.success){
										if(o.data.length>0){
											SaleOrderOIEdit.add({
												xtype:'cCombobox',
												fieldLabel:'特性',
												id:'DJ.order.saleOrder.OISaleOrderEdit.ftraitid',
												name:'ftraitid',
												valueField:'fid',
												displayField:'fcharacter',
												blankText:'请填写客户',
												MyDataChanged:function(ui){
													var field = Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.famount");
													var num = ui[0].get('fentryamount')-ui[0].get('finqty');
													field.setValue(num);
													field.setMaxValue(num);
												},
												MyConfig:{
													width:500,
													height:200,
													hiddenToolbar:true,
													url : 'getFtraitidBySchemeDesignId.do?fid='+datas.fschemedesignid,
													fields:[{
														name:'fid'
													},{
														name:'fcharacter'
													},{
														name:'fentryamount'
													},{
														name:'finqty'
													}],
													columns:[{
														text:'特性',
														dataIndex:'fcharacter',
														sortable:true,
														flex:1
													},{
														text:'配送数量',
														dataIndex:'fentryamount',
														sortable:true,
														flex:1
													},{
														text:'入库数量',
														dataIndex:'finqty',
														sortable:true,
														flex:1
													}]
												}
											});
										}
									}else{
										Ext.Msg.alert('错误',o.msg);
									}
								}
							});
						}
						SaleOrderOIEdit.show();
						Ext
								.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FProductID")
								.setValue(datas.fproductdefid);
						Ext
								.getCmp("DJ.order.saleOrder.OISaleOrderEdit.forderid")
								.setValue(datas.forderid);
						Ext
								.getCmp("DJ.order.saleOrder.OISaleOrderEdit.forderEntryid")
								.setValue(datas.fparentorderid);
						Ext
								.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fplanid")
								.setValue(fid);
						Ext
								.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseID")
								.setmyvalue("\"fid\":\"" + datas.fwarehouseid
										+ "\",\"fname\":\"" + datas.wname
										+ "\"");
						Ext
								.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseID")
								.setReadOnly(true);
						Ext
								.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseSiteID")
								.setReadOnly(true);
						Ext
								.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseID")
								.setVisible(false);
						Ext
								.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseSiteID")
								.setVisible(false);
						Ext
								.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseSiteID")
								.setmyvalue("\"fid\":\""
										+ datas.fwarehousesiteid
										+ "\",\"fname\":\"" + datas.wsfname
										+ "\"");
						
						Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.ftype")
								.setValue("0");
						Ext
								.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fordertype")
								.setValue(datas.fordertype);
						Ext
								.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fentryProductType")
								.setValue(datas.fentryProductType);
						Ext
								.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fassemble")
								.setValue(datas.fassemble);
						Ext
								.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fiscombinecrosssubs")
								.setValue(datas.fiscombinecrosssubs);
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});

		}

	},'|',
   
    { xtype : "mymixedsearchbox",
    useDefaultfilter : true,
     condictionFields : ['f.fname', 'd.fnumber', 'd.fdescription']//参数数组
}],
	fields : [{
		name : 'fid'
	}, {
		name : 'fnumber',
		myfilterfield : 'd.fnumber',
		myfiltername : '编号',
		myfilterable : true
	}, {
		name : 'cname',
		myfilterfield : 'c.fname',
		myfiltername : '客户名称',
		myfilterable : true
			// }, {
			// name : 'pname',
			// myfilterfield : 'p.fname',
			// myfiltername : '客户产品名称',
			// myfilterable : true
			}, {
				name : 'fname',
				myfilterfield : 'f.fname',
				myfiltername : '产品名称',
				myfilterable : true
			}, {
				name : 'sname'
			}, {
				name : 'fspec'
			}, {
				name : 'farrivetime'
			}, {
				name : 'fbizdate'
			}, {
				name : 'famount'
			}, {
				name : 'flastupdatetime'
			}, {
				name : 'u2_fname'
			}, {
				name : 'fcreatetime'
			}, {
				name : 'u1_fname'
			}, {
				name : 'faudittime'
			}, {
				name : 'fauditorid'
			}, {
				name : 'fauditor'
			}, {
				name : 'faudited'
			}, {
				name : 'fordertype'
			}, {
				name : 'fseq'
			}, {
				name : 'fsuitProductID'
			}, {
				name : 'fparentOrderEntryId'
			}, {
				name : 'forderid'
			}, {
				name : 'fimportEAS'
			}, {
				name : 'fstockoutqty'
			}, {
				name : 'fstockinqty'
			}, {
				name : 'fstoreqty'
			}, {
				name : 'faffirmed'
			}, {
				name : 'fassemble'
			}, {
				name : 'fiscombinecrosssubs'
			}, {
				name : 'salefnumber'
			}, {
				name : 'fparentorderid'

			}, {
				name : 'fcloseed',
				myfilterfield : 'd.fcloseed',
				myfiltername : '是否关闭',
				myfilterable : true
			}, {
				name : 'ftype',
				myfilterfield : 'd.ftype',
				myfiltername : '订单类型',
				myfilterable : true
			},{
				name:'fdescription'
			}],
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : 'fsuitProductID',
		'dataIndex' : 'fsuitProductID',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : 'fassemble',
		'dataIndex' : 'fassemble',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : 'fiscombinecrosssubs',
		'dataIndex' : 'fiscombinecrosssubs',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : 'fparentOrderEntryId',
		'dataIndex' : 'fparentOrderEntryId',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : 'forderid',
		'dataIndex' : 'forderid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : 'fparentorderid',
		'dataIndex' : 'fparentorderid',
		hidden : true,
		hideable : false,
		sortable : true

	}, {
		'header' : '制造订单号',
		'dataIndex' : 'fnumber',
		width : 100,
		sortable : true
	}, {
		'header' : '产品类型',
		'dataIndex' : 'fordertype',
		width : 60,
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '普通';
			} else if (value == 2) {
				return '套装';
			}

		}

	}, {
		'header' : '订单类型',
		'dataIndex' : 'ftype',
		width : 60,
		sortable : true,
		renderer : function(value) {
			if (value == 0) {
				return '初始化';
			} else if (value == 1) {
				return '正常';
			}

		}

	}, {
		'header' : '客户名称',
		width : 100,
		'dataIndex' : 'cname',
		sortable : true
	}, {
		'header' : '产品名称',
		width : 180,
		'dataIndex' : 'fname',
		sortable : true
	}, {
		'header' : '交期',
		'dataIndex' : 'farrivetime',
		width : 130,
		sortable : true
	}, {
		'header' : '数量',
		width : 50,
		'dataIndex' : 'famount',
		sortable : true
	}, {
		'header' : '入库',
		width : 50,
		'dataIndex' : 'fstockinqty',
		sortable : true
	}, {
		'header' : '出库',
		width : 50,
		'dataIndex' : 'fstockoutqty',
		sortable : true
	}, {
		'header' : '存量',
		width : 50,
		'dataIndex' : 'fstoreqty',
		sortable : true
	}, {
		'header' : '确认',
		width : 50,
		'dataIndex' : 'faffirmed',
		hidden : false,
		hideable : false,
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	}, {
		'header' : '导入接口',
		dataIndex : 'fimportEAS',
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	}, {
		'header' : '制造商名称',
		'dataIndex' : 'sname',
		sortable : true
	}, {
		'header' : '规格',
		'dataIndex' : 'fspec',
		sortable : true
	}, {
		'header' : '业务时间',
		'dataIndex' : 'fbizdate',
		sortable : true
			// }, {
			// text : '创建人',
			// dataIndex : 'u1_fname',
			// sortable : true
			// }, {
			// text : '创建时间',
			// dataIndex : 'fcreatetime',
			// width : 150,
			// sortable : true
			//
			// },{
			// text : '修改人',
			// dataIndex : 'u2_fname',
			// sortable : true
			// }, {
			// text : '修改时间',
			// dataIndex : 'flastupdatetime',
			// width : 150,
			// sortable : true
			// },
			// {
			// 'header' : '审核人',
			// 'dataIndex' : 'fauditorid',
			// hidden : true,
			// sortable : true
			// }, {
			// 'header' : '审核人',
			// 'dataIndex' : 'fauditor',
			// sortable : true
			// }, {
			// 'header' : '审核',
			// dataIndex : 'faudited',
			// sortable : true,
			// renderer: function(value){
			// if (value == 1) {
			// return '是';
			// }
			// else{
			// return '否';
			// }
			// }
			// }, {
			// 'header' : '审核时间',
			// dataIndex : 'faudittime',
			// width : 150,
			// sortable : true
			// },
			// {
			// 'header' : '客户产品名称',
			// 'dataIndex' : 'pname',
			// sortable : true
			// },
			// {
			// 'header' : '生产订单号',
			// 'dataIndex' : 'salefnumber',
			// sortable : true
			// }, {
			// 'header' : '生产分录',
			// 'dataIndex' : 'fseq',
			// sortable : true
			}, {
				'header' : '关闭',
				width : 50,
				'dataIndex' : 'fcloseed',
				hidden : false,
				hideable : false,
				sortable : true,
				renderer : function(value) {
					if (value == 1) {
						return '是';
					} else {
						return '否';
					}
				}
			},{
						header:'备注',
						dataIndex:'fdescription'
					}],
	selModel : Ext.create('Ext.selection.CheckboxModel')
})