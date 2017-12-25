Ext
		.require(["DJ.tools.grid.MyGridHelper",
				"DJ.tools.fieldRel.MyFieldRelTools",
				"Ext.ux.grid.MyGridItemDblClick",
				"Ext.ux.grid.MySimpleGridContextMenu","Ext.ux.grid.MyGridItemTipPlugin"]);

Ext.define('DJ.System.SimplemessagePanel', {
	extend : 'Ext.panel.Panel',

//	html: '<iframe id = "printtest" width="100%" height="100%" />',
	
	// title : "*",
	id : "DJ.System.SimplemessagePanel",
	height : 297,
	width : 508,
	layout : {
		type : 'border'
	},

//	features : [ Ext.create('Myext.grid.SelectFeature')],  
	
	
	
	statics : {
	
		NODE_TEXT : ['全部',     '<font color = red>待收货通知</font>','<font color = green>方案评价通知</font>','<font color = blue>订单提醒</font>' ],
		
		loadDate : function(myAction, ftype) {

			var storeT = Ext
					.getCmp("DJ.System.SimplemessagePanel.SimplemessageList")
					.getStore();

			if (Ext.getCmp("DJ.System.SimplemessagePanel.SimplemessageList")) {

				storeT.proxy.extraParams.myAction = myAction;

			}

			storeT.proxy.extraParams.ftype = ftype;

			// 清除过滤
			storeT.proxy.extraParams.Defaultfilter = '';

			storeT.load();

		}
		
	},
	
	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [{
				xtype : 'treepanel',
				region : 'west',
				width : 150,
				split : true,

				id : 'messageTreepanelId',

				rootVisible : false,

				lines : false,

				root : {
					text : '',
					expanded : true,

					children : [{
						text : '全部',
						leaf : true,
						id : '-1'
					}, {
						text : '<font color = red>待收货通知</font>',
						leaf : true,
						id : '1'
					}, {
						text : '<font color = green>方案评价通知</font>',
						leaf : true,
						id : '2'
					}, {
						text : '<font color = blue>订单提醒</font>',
						leaf : true,
						id : '3'
					}]
				},

				viewConfig : {
					listeners : {
						refresh : function() {

							this.select(0);

						}
					}
				},

				listeners : {
					selectionchange : function(model, sels) {

						if (sels.length > 0) {
						
							DJ.System.SimplemessagePanel
									.loadDate(
											Ext
													.getCmp("DJ.System.SimplemessagePanel.SimplemessageList")
													.down("combo[name=readedState]")
													.getValue(),
											Ext
													.getCmp("DJ.System.SimplemessagePanel")
													.down("treepanel")
													.getSelectionModel()
													.getSelection()[0]
													.get("id"));
							
						}

					}
				}
			}, {
				xtype : 'simplemessagelist',
				region : 'center'

			}]
		});

		me.callParent(arguments);
	}

});



Ext.define('DJ.System.SimplemessagePanel.SimplemessageList', {
	extend : 'Ext.c.GridPanel',

	alias : 'widget.simplemessagelist',

	id : 'DJ.System.SimplemessagePanel.SimplemessageList',
	selModel : Ext.create('Ext.selection.CheckboxModel'),

	statics : {

		TYPES : ["一般", "待收货通知", "方案评价通知","订单提醒"]

	},
//viewConfig : {
//	
//		enableTextSelection:true
//		
//	
//	},
	pageSize : 50,
	// closable : ,// 是否现实关闭按钮,默认为false
	url : 'getMsgFacade.do',
	Delurl : "setSimplemessageReadState.do",
	EditUI : "DJ.System.SimplemessageEdit",
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		var ids = this._getToolsButtonIDs();

		Ext.getCmp(ids[0]).setText("写信息");

		this._operateButtonsView(true, [0, 2, 4, 5, 7, 8, 9]);
		
		this.getStore().on("load", function (store, records, successful, eOpts) {
		
			
			if (successful) {
			
//				var tipS = Ext.getCmp("DJ.System.SimplemessagePanel.SimplemessageList").down("pagingtoolbar").items.items[12].text;
//				
//				var tipS2 = tipS.substring(tipS.indexOf("共") + 1);  
//				
//				
//				
//				Ext
//													.getCmp("DJ.System.SimplemessagePanel")
//													.down("treepanel")
//													.getSelectionModel()
//													.getSelection()[0];
//				
//				
//													
//				comT.set("text",comT.raw.text + tipS2);
				
				
						
						Ext.Ajax.request({
							timeout : 6000,
							url : "gainMsgTipCount.do",
							success : function(response, option) {
								
								var obj = Ext.decode(response.responseText);
								if (obj.success == true) {
									
									var tips = [obj.unReadedCount, obj.unReceivingCount, obj.projectEvaluation,obj.productionreminds];
									
									Ext.each(Ext
													.getCmp("DJ.System.SimplemessagePanel")
													.down("treepanel").getStore().tree.root.childNodes, function (ele, index, all) {
													
														ele.set("text",ele.raw.text + " (" + tips[index] + ")条");
														
													});
									
								
								} else {
									Ext.MessageBox.alert('错误', obj.msg);
				
								}
								
							}
						});
				
			}
			
			
			
		});
		
		//do some req ation in view win
		
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
	Action_AfterViewButtonClick : function (me, record) {
		
		Ext.getCmp("DJ.System.SimplemessageEdit").setWidth(680);
		Ext.getCmp("DJ.System.SimplemessageEdit").setHeight(270);

		Ext.getCmp("DJ.System.SimplemessageEdit")
				.down("combobox[name=frecipient]").setFieldLabel("");

		// Ext.getCmp("DJ.System.SimplemessageEdit")
		// .down("combobox[name=frecipient]").setRawValue("尊敬的" +
		// Ext.getCmp("DJ.System.SimplemessageEdit")
		// .down("combobox[name=frecipient]").getRawValue());

		Ext.getCmp("DJ.System.SimplemessageEdit")
				.down("combobox[name=frecipientChooser]").hide();

		Ext.getCmp("DJ.System.SimplemessageEdit").down("checkboxfield").hide();

		Ext.getCmp("DJ.System.SimplemessageEdit")
				.down("textareafield[name=fremark]").hide();

		Ext.getCmp("DJ.System.SimplemessageEdit")
				.down("textareafield[name=fcontent]").setFieldLabel("");
				
		if (me.getSelectionModel().getSelection()[0].get("fhaveReaded") == 0) {
		
			Ext.getCmp("DJ.System.SimplemessagePanel.SimplemessageList.setReadedbutton").handler();
			
		} 
			
	}
	,
	plugins : [{
	
		ptype : 'mygriditemdblclick',
		dbClickHandler : 'button[text=查   看]'
		
	},{
		ptype : 'mysimplegridcontextmenu',
		useExistingButtons : ["button[text=设为已读]"]
	}
	,
		{
	
		
		ptype : 'mygriditemtipplugin',
		showingFields : ["fcontent"]//前端取值用这个。后端用后面，
//		,
//		url : "getSimplemessageById.do",
//	
//		ajaxParams : ['fid'],
//		
//		tipProperty : "data.fcontent"//不是必须，默认为msg
	
		
	}
//	, 
//	
//		new Ext.ux.plugins.Print()
		
	],
	custbar : [{

		xtype : "combo",
		name : "readedState",
		// fieldLabel : "",
		store : [[-1, '全部'], [0, '未读取'], [1, '已读取']],
		forceSelection : true,
		typeAhead : true,
		queryMode : 'local',
		value : -1,
		listeners : {

			select : function(combo, records, eOpts) {

				var storeT = Ext
						.getCmp("DJ.System.SimplemessagePanel.SimplemessageList")
						.getStore();

				storeT.proxy.extraParams.myAction = combo.getValue();

				if (combo.getValue() == 1) {
					Ext
							.getCmp("DJ.System.SimplemessagePanel.SimplemessageList.setReadedbutton")
							.hide();
				} else {

					Ext
							.getCmp("DJ.System.SimplemessagePanel.SimplemessageList.setReadedbutton")
							.show();
				}

				storeT.proxy.extraParams.ftype = Ext
						.getCmp("DJ.System.SimplemessagePanel")
						.down("treepanel").getSelectionModel().getSelection()[0]
						.get("id");

				// 清除过滤
				storeT.proxy.extraParams.Defaultfilter = '';

				storeT.load();

			}

		}

	}, {
		id : 'DJ.System.SimplemessagePanel.SimplemessageList.setReadedbutton',
		text : '设为已读',
		height : 30,

		handler : function() {

			var grid = Ext.getCmp("DJ.System.SimplemessagePanel.SimplemessageList");

			var models = grid.getSelectionModel().getSelection();

			if (models.length < 1) {
				Ext.Msg.alert("提示", "请选择");
				return;
			}

			var r = [];

			Ext.each(models, function(item, index, all) {

				r.push(item.get("fid"));

			});

			// 获取未读取信息
			var reqCfg = {
				url : "setSimplemessageReadState.do",
				params : {
					fidcls : r.toString()

				},
				callback : function(options, success, response) {

					if (success) {
					
						
						
					} else {
					
						Ext.Msg.alert("提示",
							Ext.JSON.decode(response.responseText).msg);
					}
					

					Ext
							.getCmp("DJ.System.SimplemessagePanel.SimplemessageList")
							.getStore().load();
				}
			};

			Ext.Ajax.request(reqCfg);

		}
	},{
		text : '消息设置',
		handler : function() {
			Ext.create("DJ.System.SimplemessagePanel.persistenceMsgCfgWin").show();
		}
	}],

	fields : [{
		name : 'fid'
	}, {
		name : 'fsender'
	}, {
		name : 'fsenderName',

		myfilterfield : 'tsu.fname',
		myfiltername : '发送人',
		myfilterable : true
	}, {
		name : 'ftime',
		type : 'data'
	}, {
		name : 'fcontent',

		myfilterfield : 'tss.fcontent',
		myfiltername : '内容',
		myfilterable : true
	}, {
		name : 'fremark',

		myfilterfield : 'tss.fremark',
		myfiltername : '备注',
		myfilterable : true
	}, {
		name : 'frecipient'
	}, {
		name : 'frecipientName',

		myfilterfield : 'tsu2.fname',
		myfiltername : '接收人',
		myfilterable : true
	}, {
		name : 'fhaveReaded',
		type : 'int'
	}, {
		name : 'freceivingTime',
		type : 'data'
	}, {
		name : 'ftype',
		type : 'int'
	}],
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
		'header' : '发送人',
		'dataIndex' : 'fsenderName',
		sortable : true

	}, {
		'header' : '发送时间',
		'dataIndex' : 'ftime',
		xtype : "datecolumn",
		format : "Y-m-d H:i:s",
		width : 150,
		sortable : true,
		renderer : function(value) {
			return value;
		}

	}, {
		'header' : '内容',
		'dataIndex' : 'fcontent',
		sortable : true,
		width : 330

	}, {
		'header' : '类别',
		'dataIndex' : 'ftype',
		sortable : true,
		renderer : function(value) {

			return DJ.System.SimplemessagePanel.SimplemessageList.TYPES[value];
		}

	},
			// {
			// 'header' : '备注',
			// 'dataIndex' : 'fremark',
			// sortable : true
			//
			// }, {
			// 'header' : '接收人',
			// 'dataIndex' : 'frecipientName',
			// sortable : true
			//
			// },
			{
				'header' : '读取时间',
				'dataIndex' : 'freceivingTime',
				xtype : "datecolumn",
				format : "Y-m-d H:i:s",
				width : 150,
				sortable : true,
				renderer : function(value) {
					var r = '';

					if (value == '' || value == null || value == 'null') {
						r = '<font color = red>未被读取</font>';
					} else {
						// r = value;

						r = '<font color = green>' + value + '</font>';
					}
					return r;
				}

			}, {
				'header' : '读取状态',
				'dataIndex' : 'fhaveReaded',
				sortable : true,
				renderer : function(value) {
					var r = '';

					if (value == 1) {
						r = '<font color = green>已读取</font>';
					} else if (value == 0) {
						r = '<font color = red>未读取</font>';
					}

					return r;
				}

			}

	]

	,

	/**
	 * 主要调用这个方法
	 * 
	 * @param {}
	 *            show，bool，
	 * @param {}
	 *            array，元素为button ID或为数字索引（从0开始）
	 */
	_operateButtonsView : function(show, array) {

		if (Ext.typeOf(array[0]) == "number") {
			array = this._translateNumToDataIndex(array);
		}

		if (show) {
			this._showButtons(array);
		} else {
			this._hideButtons(array);
		}

	},
	_translateNumToDataIndex : function(array) {
		var arrayt = [];

		var arrayAll = this._getToolsButtonIDs();

		Ext.each(arrayAll, function(item, index, all) {
			if (Ext.Array.contains(array, index)) {
				arrayt.push(arrayAll[index]);
			}
		});

		return arrayt;
	},

	_hideButtons : function(array) {
		var t = array;
		Ext.each(t, function(item, index, all) {
			Ext.getCmp(item).hide();
		});
	},

	_showButtons : function(array) {
		var defaultButtons = this._getToolsButtonIDs();

		var tArray = Ext.Array.difference(defaultButtons, array);

		this._hideButtons(tArray);
	},

	_getToolsButtonIDs : function() {
		var defaultButtons = [];

		var t = this.dockedItems.items[2].items.items;

		Ext.each(t, function(item, index, all) {

			defaultButtons.push(item.id);

		});
		return defaultButtons;
	}
});



Ext.define('DJ.System.SimplemessagePanel.persistenceMsgCfgWingrid', {
	extend : 'Ext.c.GridPanel',

	alias : 'widget.persistencemsgcfgwingrid',
	
//	title : "消息设置",
	id : 'DJ.System.SimplemessagePanel.persistenceMsgCfgWingrid',
	pageSize : 50,
//	closable : true,// 是否现实关闭按钮,默认为false
	url : 'gainSimplemessagecfgs.do',

	mixins : ['DJ.tools.grid.MyGridHelper'],
	width : 600,// 230, //Window宽度
	plugins : [{
		ptype : "cellediting",
		clicksToEdit : 1
	}],

	onload : function() {
		// 加载后事件，可以设置按钮，控件值等

		this._operateButtonsView(true, [7]);

	},
	custbar : [{
		text : '保存',
		height : 30,
		handler : function () {
		
			var storeT = Ext.getCmp("DJ.System.SimplemessagePanel.persistenceMsgCfgWingrid").getStore();
			
//			storeT.getModifiedRecords();
//			console.log(storeT.getModifiedRecords());
			
			var records = [];
			
			Ext.each(storeT.getModifiedRecords(), function (ele, index, all) {
			
				records.push(ele.data);
				
			} );
			
			var me = this;
			var el = me.getEl();
			el.mask("系统处理中,请稍候……");

			Ext.Ajax.request({
				timeout : 6000,
				
				params : {
				
					records : Ext.JSON.encode(records)
				
				},
				
				url : "saveSimplemessagecfgs.do",
				success : function(response, option) {

					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
//						Ext.MessageBox.alert('成功', obj.msg);
						
						me.up("window").close();
						
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
		name : 'ftype',
		type : "int"
	}, {
		name : 'fneedsms',
		type : "boolean",
		convert : function(v, rec) {

			return v == 1;
		}
	}, {
		name : 'frecipient'
	}, {
		name : 'fuserid'
	}, {
		name : 'fphone'
	}

	],
	columns : [{
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false
	}, {
		'header' : 'fuserid',
		'dataIndex' : 'fuserid',
		hidden : true,
		hideable : false
	}, {
		'header' : '消息类型',
		'dataIndex' : 'ftype',
		sortable : true,
		renderer : function(value) {

			return DJ.System.SimplemessagePanel.SimplemessageList.TYPES[value];
		}

	}, {
		xtype : "checkcolumn",
		'header' : '是否发送短信',
		'dataIndex' : 'fneedsms'
//		,
//		sortable : true,
//		trueText : '<input type="checkbox" checked="checked" />',
//		falseText : '<input type="checkbox"/>'
//		,
//
//		editor : {
//
//			xtype : "checkboxfield"
//
//		}

	}, {
		'header' : '接收人',
		'dataIndex' : 'frecipient',
		sortable : true,
		width : 200,
		renderer : MyFieldRelTools.convertToEmptyWhenNull,
		editor : {

			xtype : "textfield"

		}
	}, {
		'header' : '接收号码',
		'dataIndex' : 'fphone',
		sortable : true,
		width : 200,
		renderer : MyFieldRelTools.convertToEmptyWhenNull,
		editor : {
			allowBlank : false,
			enforceMaxLength : true,
			xtype : "textfield",
			maxLength : 11,
			validator : function(value) {
				
				if (/^1[3|4|5|8][0-9]\d{4,8}$/.test(value) && (value.length == 11)) {
				
					return true;
					
				} else {
				
					return "格式不对";
					
				}
				
			}

		}
	}]

});

Ext.define('DJ.System.SimplemessagePanel.persistenceMsgCfgWin', {
	extend : 'Ext.Window',
	id : 'DJ.System.SimplemessagePanel.persistenceMsgCfgWin',
	modal : true,
	title : "消息设置",
	width : 610,// 230, //Window宽度
	height : 160,// 137, //Window高度
	// x : 200,
	// y : 80,
	resizable : false,
	// closable : false, // 关闭按钮，默认为true
	// closeAction : 'destroy',
	// renderTo: 'upload',

	initComponent : function() {
		Ext.apply(this, {
			items : [{
			
				xtype : "persistencemsgcfgwingrid"
				
			}]
		});
		this.callParent(arguments);
	},

//	productID : "",
	// buttons : [{
	// text : '关闭',
	// handler : function() {
	//			
	// Ext.destroy(Ext.getCmp("DJ.System.product.ProductDrawList"));
	//			
	// this.up("window").close();
	//	
	// }
	// }] close( panel, eOpts )
	listeners : {
//		close : function(panel, eOpts) {
//			Ext.destroy(Ext.getCmp("DJ.System.product.CusproductdrawListWin1.ProductDrawList"));
//
//			Ext.destroy(Ext.getCmp("DJ.System.product.CusproductdrawListWin"));
//
////			Ext.getCmp("DJ.System.product.ProductDrawListWin").hide();
////			Ext.getCmp("DJ.System.product.ProductDrawList").hide();
//			// Ext.destroy(Ext.getCmp("DJ.System.product.ProductDrawList"));
//
//			// this.up("window").close();
//		}

	}
});

