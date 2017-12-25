Ext
		.define(
				"DJ.order.Deliver.MystockListGrid",
				{ // xtype : 'gridpanel',
					id : 'DJ.order.Deliver.MystockListGrid',
					extend : 'Ext.c.GridPanel',
					
					region : 'north',
//					width : '70%',
					height:'60%',
					autoScroll:true,
//					split : true,
					exporturl : "exportMystockList.do",// 导出为EXCEL方法
					url : 'getMystockList.do',
					onload : function() {

						this.down("toolbar").hide();
						this.magnifier = Ext.create('Ext.ux.form.Magnifier');
						//		
//						this
//								.getStore()
//								.on(
//										"load",
//										function() {
//											Ext.Ajax
//													.request({
//														url : 'getPlanamount.do',
//														success : function(res) {
//															var obj = Ext
//																	.decode(res.responseText);
//															if (obj.success) {
//																Ext
//																		.getCmp(
//																				'DJ.order.Deliver.panel.thisMonthStock')
//																		.setValue(
//																				obj.data[0].fplanamount);
//																Ext
//																		.getCmp(
//																				'DJ.order.Deliver.panel.lastMonthStock')
//																		.setValue(
//																				obj.data[1].fplanamount);
//																var compare = obj.data[0].fplanamount
//																		- obj.data[1].fplanamount;
//																Ext
//																		.getCmp(
//																				'DJ.order.Deliver.panel.compare')
//																		.setValue(
//																				compare);
//															} else {
//																Ext.Msg
//																		.alert(
//																				'提示',
//																				obj.msg);
//															}
//															// Ext.getCmp('DJ.System.MaigaoEntryGrids').getStore().reload();
//														}
//													})
//
//										});

					},
					selModel : Ext.create('Ext.selection.CheckboxModel'),
					// store : DJ.order.Deliver.myStore,
					// title: 'My Grid Panel',
					fields : [ {
						name : 'fid'
					}, {
						name : 'fnumber',
						type : 'string'
					}, {
						name : 'fcustproductid'
					}, {
						name : 'fplanamount'
					}, {
						name : 'funit'
					}, {
						name : 'ffinishtime'
					}, {
						name : 'fconsumetime'
					}, {
						name : 'fisconsumed'
					}, {
						name : 'fdescription'
					}, {
						name : 'fcustproductname'
					}, {
						name : 'fcreatetime'
					}, {
						name : "fcreateid"
					}, {
						name : 'fcustomerid'
					}, {
						name : 'cfnumber'
					}, {
						name : 'fordered'
					}, {
						name : 'fsaleorderid'
					}, {
						name : 'fbalanceqty'
					}, {
						name : 'fcharactername'
					}, {
						name : 'fcharacterid'
					},{
						name : 'cname'
					},{
						name : 'fremark'
					},{
						name:'sname'
					},{
						name:'fstate'
					},{
						name:'fsupplierid'
					},{
						name:'mcount'
					},{
						name:'fpcmordernumber'
					} ],
					
					// remoteFilter : true,
					columns : [ Ext.create('DJ.Base.Grid.GridRowNum',{
						header:'序号'
					}),{
						dataIndex:'fsupplierid',
						hidden : true,
						hideable : false
					},{
						dataIndex : 'fid',
						text : 'fid',
						hidden : true,
						hideable : false
					}, {
						dataIndex : 'fcharacterid',
						text : 'fcharacterid',
						hidden : true,
						hideable : false
					}, {
						dataIndex : 'fbalanceqty',
						text : 'fbalanceqty',
						hidden : true,
						hideable : false
					}, {
						dataIndex : 'fsaleorderid',
						text : 'fsaleorderid',
						hidden : true,
						hideable : false
					}, {
						dataIndex : 'fordered',
						text : 'fordered',
						hidden : true,
						hideable : false
					}, {
						dataIndex : 'fcreateid',
						text : 'fcreateid',
						hidden : true,
						hideable : false
					}, {
						dataIndex : 'fcustomerid',
						text : 'fcreateid',
						hidden : true,
						hideable : false
					}, {
						// xtype : 'gridcolumn',
						dataIndex : 'fnumber',
						text : '备货单号'
					}, {
						// xtype : 'gridcolumn',
						dataIndex : 'cname',
						text : '客户名称'
					},{
						dataIndex : 'fpcmordernumber',
						text : '采购订单号'
					}, {
						// xtype: 'numbercolumn',
						dataIndex : 'fcustproductname',
						text : '包装物名称'
					// flex:1
					}, {
						// xtype: 'numbercolumn',
						dataIndex : 'fcustproductid',
						text : '包装物名称',
						hidden : true,
						hideable : false
					}, {
						// xtype: 'numbercolumn',
						dataIndex : 'cfnumber',
						text : '包装物编号'
					},{
						dataIndex : 'sname',
						text : '制造商名称'
					},{
						dataIndex : 'fstate',
						text : '状态',
						renderer:function(value){
							if(value=='0'){
								return "已创建";
							}else if(value=='1'){
								return "已安排生产";
							}else if(value=='2'){
								return "已结束备货";
							}
							
						}
					},{
						
						// xtype: 'datecolumn',
						dataIndex : 'fplanamount',
						text : '计划数量',
						align : "center"
					// width:50
					}, {
						dataIndex : 'fbalanceqty',
						text : '库存数量',
						align : "center"
					},{
						dataIndex : 'mcount',
						text : '生产欠数',
						align : "center"
					}, {
						// xtype: 'datecolumn',
						dataIndex : 'fcharactername',
						text : '特性'
					// width:50
					}, {
						// xtype: 'booleancolumn',
						dataIndex : 'funit',
						text : '单位',
						align : "center"
					// width:50
					}, {
						dataIndex : 'fcreatetime',
						text : '创建时间',
						hidden : true,
						hideable : false
					}, {
						dataIndex : 'ffinishtime',
						text : '要求完成时间'
					}, {
						dataIndex : 'fconsumetime',
						text : '预计消耗时间'
					}, {
						dataIndex : 'fisconsumed',
						text : '是否消耗完毕',
						hidden:true,
						align : "center",
						value : 0,
						renderer : function(value) {
							if (value == '1') {
								return "是"
							} else {
								return "否"
							}
						}
					}, {
						dataIndex : 'fremark',
						text : '备注'
					} ],
					listeners : {
						itemclick: function(me,record,item, index, e){
							this.magnifier.loadImages({
								fid: record.get('fcustproductid')
							});
							var coord = e.getXY();
							this.magnifier.showAt({
								left: coord[0] + 80,
								top: coord[1] + 5
							});
						},
						select : function(com, record, item, index, e, eOpts) {
							Ext.getCmp('DJ.order.Deliver.MystockList.funit')
									.setReadOnly(false);
							
							Ext.getCmp('DJ.order.Deliver.MystockListForm')
									.getForm().loadRecord(record);
							if(record.get('fstate')!=0){
								Ext.getCmp('DJ.order.Deliver.MystockListForm').setDisabled(true);
							}else{
								Ext.getCmp('DJ.order.Deliver.MystockListForm').setDisabled(false);
							}
							Ext.getCmp('DJ.order.Deliver.MystockList.fnumber').setReadOnly(true);
							if (record.get("funit") == null
									|| record.get("funit") == "") {
								Ext
										.getCmp(
												'DJ.order.Deliver.MystockList.funit')
										.setValue('只(套)/片');
							}
							Ext
									.getCmp(
											'DJ.order.Deliver.MystockList.fcustproductid')
									.setmyvalue(
											"\"fid\":\""
													+ record
															.get("fcustproductid")
													+ "\",\"fname\":\""
													+ record
															.get("fcustproductname")
													+ "\"");

//							DJ.order.Deliver.productStore.load({
//
//								params : {
//
//									fcustproductid : record
//											.get("fcustproductid")
//
//								}
//
//							});

							var deliverorder = Ext
									.getCmp(
											'DJ.order.Deliver.MystockList.deliverorder')
									.getStore();
//							var myfilter = [];
//							myfilter.push({
//								myfilterfield : "m.fsaleorderid",
//								CompareType : " = ",
//								type : "string",
//								value : record.get("fsaleorderid")
//							});
//							deliverorder.setDefaultfilter(myfilter);
//							deliverorder.setDefaultmaskstring(" #0 ");
							deliverorder.load({
								params : {

									fsaleorderid : record
											.get("fsaleorderid"),
									fcharacterid:record
											.get("fcharacterid")

								}});
							// Ext.getCmp('DJ.order.Deliver.MystockList.deliverorder').getStore().load({
							// params:{
							// fcusproductid:record.get("fcustproductid")
							// }
							// });
						}
					}

				})