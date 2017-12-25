//Ext.apply(Ext.form.field.VTypes, {
//	saveresultvalue : function(val, field) {
//		// if(field.saveresultvalue){
//		if (Ext.getCmp("DJ.System.supplier.Supplierjudgementrptui")
//				.getSelectionModel() != "undefined") {
//
//			var rows = Ext.getCmp("DJ.System.supplier.Supplierjudgementrptui")
//					.getSelectionModel().getSelection();
//
//			if (Ext.Number.from(Ext.getCmp("resultvalue").value) > 0) {
//
//				Ext.Ajax.request({
//					timeout : 600000,
//					url : "SaveResultValue.do",
//					params : {
//						fid : rows[0].get("fid"),
//						fresultValue : Ext.getCmp("resultvalue").value,
//						type : "0"
//					}, // 参数
//					success : function(response, option) {
//
//						var obj = Ext.decode(response.responseText);
//						if (obj.success == true) {
//							Ext.MessageBox.alert('成功', obj.msg);
//							// Ext.getCmp("DJ.System.supplier.Supplierjudgementrptui").store.load();
//
//						} else {
//
//							Ext.MessageBox.alert('错误', obj.msg);
//
//						}
//					}
//				});
//
//			}
//		}
//		// }
//	}
//})

Ext.define('DJ.System.supplier.Supplierjudgementrptui', {
	extend : 'Ext.c.GridPanel',
	title : "制造商评估报表",
	id : 'DJ.System.supplier.Supplierjudgementrptui',
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false

	plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
		clicksToEdit : 2
	})],
	url : 'getSupplierjudgementrpt.do',
	Delurl : "",

	EditUI : "",
	exporturl : "SupplierjudgementrpttoExcel.do",// 导出为EXCEL方法
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		// this._operateButtonsView(true, [4, 5, 6, 7]);
		Ext.getCmp("DJ.System.supplier.Supplierjudgementrptui.addbutton")
				.setVisible(false);
		Ext.getCmp("DJ.System.supplier.Supplierjudgementrptui.editbutton")
				.setVisible(false);
		Ext.getCmp("DJ.System.supplier.Supplierjudgementrptui.delbutton")
				.setVisible(false);
		Ext.getCmp("DJ.System.supplier.Supplierjudgementrptui.viewbutton")
				.setVisible(false);
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
	}

	,
	custbar : [{
		text : '生成评价结果',
		height : 30,
		handler : function() {
			var grid = this.up("gridpanel");
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "createAssessmentResult.do",
				params : {
					type : "1"
				},
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
//						Ext.MessageBox.alert('成功', obj.msg);
						djsuccessmsg( obj.msg);
						Ext.getCmp("DJ.System.supplier.Supplierjudgementrptui").store
								.load();

					} else {
						Ext.MessageBox.alert('错误', obj.msg);

					}
					el.unmask();
				}

			});
		}
	}, {
		text : '计算结果值',
		height : 30,
		handler : function() {
			var grid = this.up("gridpanel");
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "calculateResult.do",
				params : {
					type : "2"
				},
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
//						Ext.MessageBox.alert('成功', obj.msg);
						djsuccessmsg( obj.msg);
						Ext.getCmp("DJ.System.supplier.Supplierjudgementrptui").store
								.load();

					} else {
						Ext.MessageBox.alert('错误', obj.msg);

					}
					el.unmask();
				}

			});
		}
	}, {
		text : '计算得分',
		height : 30,
		handler : function() {
			var grid = this.up("gridpanel");
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "calculateScore.do",
				params : {
					type : "3"
				},
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
//						Ext.MessageBox.alert('成功', obj.msg);
						djsuccessmsg( obj.msg);
						Ext.getCmp("DJ.System.supplier.Supplierjudgementrptui").store
								.load();

					} else {
						Ext.MessageBox.alert('错误', obj.msg);

					}
					el.unmask();
				}

			});
		}
	}],

	fields : [{
		name : 'fid'
	}, {
		name : 'fsupplierid'
	}, {
		name : 'supplier',
		myfilterfield : 's.fname',
		myfiltername : '制造商',
		myfilterable : true
	}, {
		name : 'fyear',
		type : 'int',
		myfilterfield : 'fyear',
		myfiltername : '年',
		myfilterable : true
	}, {
		name : 'fmonth',
		type : 'int',
		myfilterfield : 'fmonth',
		myfiltername : '月',
		myfilterable : true
	}, {
		name : 'fsupplierJudgementid'
	}, {
		name : 'supplierJudgement',
		myfilterfield : 'st.fname',
		myfiltername : '评估类型',
		myfilterable : true
	}, {
		name : 'fsupplierJudgeProjectid'
	}, {
		name : 'supplierJudgeProject',
		myfilterfield : 'sp.fname',
		myfiltername : '评估项目',
		myfilterable : true
	}, {
		name : 'fresultValue'
	}, {
		name : 'fjudgeProjectScore'
	}, {
		name : 'fjudgementScore'
	}, {
		name : 'fendScore'
	}],
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
		'header' : '制造商',
		'dataIndex' : 'supplier',
		sortable : true

	}, {
		'header' : '年',
		'dataIndex' : 'fyear',
		sortable : true

	}, {
		'header' : '月',
		'dataIndex' : 'fmonth',
		sortable : true

	}, {
		'header' : '评估类型',
		'dataIndex' : 'supplierJudgement',
		sortable : true

	}, {
		'header' : '评估项目',
		'dataIndex' : 'supplierJudgeProject',
		sortable : true

	}, {
		'header' : '结果值',
		'dataIndex' : 'fresultValue',
		sortable : true,
		editor : {
//			id : "resultvalue",
//			vtype : "saveresultvalue",
			xtype : "numberfield",
			listeners : {
				blur : function(com, The, eOpts) {
					if (Ext.getCmp("DJ.System.supplier.Supplierjudgementrptui")
							.getSelectionModel() != "undefined") {
			
						var rows = Ext.getCmp("DJ.System.supplier.Supplierjudgementrptui")
								.getSelectionModel().getSelection();
			
						if (Ext.Number.from(rows[0].data.fresultValue) > 0) {
			
							Ext.Ajax.request({
								timeout : 600000,
								url : "SaveResultValue.do",
								params : {
									fid : rows[0].data.fid,
									fresultValue : rows[0].data.fresultValue,
									type : "0"
								}, // 参数
								success : function(response, option) {
			
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
//										Ext.MessageBox.alert('成功', obj.msg);
										djsuccessmsg(obj.msg);
										// Ext.getCmp("DJ.System.supplier.Supplierjudgementrptui").store.load();
			
									} else {
			
										Ext.MessageBox.alert('错误', obj.msg);
			
									}
								}
							});
			
						}
					}
				}
			}
		}
			// ,
			// listeners : {
			// change : function(com) {
			// if(Ext.getCmp("DJ.System.supplier.Supplierjudgementrptui")!="undefined"){
			// var rows =
			// Ext.getCmp("DJ.System.supplier.Supplierjudgementrptui").getSelectionModel().getSelection();
			// Ext.Ajax.request({
			// timeout : 600000,
			// url : "SaveResultValue.do",
			// params : {
			// fid : rows[0].get("fid"),
			// fresultValue : rows[0].get("fresultValue"),
			// type : "0"
			// }, // 参数
			// success : function(response, option) {
			// var obj = Ext.decode(response.responseText);
			// if (obj.success == true) {
			// Ext.MessageBox.alert('成功', obj.msg);
			// Ext.getCmp("DJ.System.supplier.Supplierjudgementrptui").store.load();
			// } else {
			// Ext.MessageBox.alert('错误', obj.msg);
			// }
			// }
			// });
			// }
			//								
			// }
			// }

			}, {
				'header' : '项目得分',
				'dataIndex' : 'fjudgeProjectScore',
				sortable : true

			}, {
				'header' : '类型得分',
				'dataIndex' : 'fjudgementScore',
				sortable : true
			}, {
				'header' : '最终得分',
				'dataIndex' : 'fendScore',
				sortable : true
			}]
// ,
/**
 * 主要调用这个方法
 * 
 * @param {}
 *            show，bool，
 * @param {}
 *            array，元素为button ID或为数字索引（从0开始）
 */
// _operateButtonsView : function(show, array) {
//
// if (Ext.typeOf(array[0]) == "number") {
// array = this._translateNumToDataIndex(array);
// }
//
// if (show) {
// this._showButtons(array);
// } else {
// this._hideButtons(array);
// }
//
// },
// _translateNumToDataIndex : function(array) {
// var arrayt = [];
//
// var arrayAll = this._getToolsButtonIDs();
//
// Ext.each(arrayAll, function(item, index, all) {
// if (Ext.Array.contains(array, index)) {
// arrayt.push(arrayAll[index]);
// }
// });
//
// return arrayt;
// },
//
// _hideButtons : function(array) {
// var t = array;
// Ext.each(t, function(item, index, all) {
// Ext.getCmp(item).hide();
// });
// },
//
// _showButtons : function(array) {
// var defaultButtons = this._getToolsButtonIDs();
//
// var tArray = Ext.Array.difference(defaultButtons, array);
//
// this._hideButtons(tArray);
// },
//
// _getToolsButtonIDs : function() {
// var defaultButtons = [];
//
// var t = this.dockedItems.items[2].items.items;
//
// Ext.each(t, function(item, index, all) {
//
// defaultButtons.push(item.id);
//
// });
// return defaultButtons;
// }
})