//Ext.require('Ext.ux.form.OneClickFileField');

Ext.define("Ext.c.GridPanelT", {
	extend : "Ext.grid.Panel",

	xtype : "cGridPanelT",
	// EditUI:"",
	initComponent : function() {
		var me = this;
		if (me.id == "") {
			me.id = Ext.id();
		}
		me.store = Ext.create("Ext.c.data.Store", {
			fields : me.fields,
			pageSize : me.pageSize,
			url : me.url,
			autoLoad : true,
			remoteSort : me.remoteSort ? me.remoteSort : false,
			listeners : {
				exception : function(dataProxy, response, action, options) {
					var o = Ext.decode(response.responseText)
					if (!o.success) {
						Ext.Msg.alert('错误提示', o.msg);
					}
				}
			}
				// 设置属性进行请求后台排序
				});

		// me.pageSize = 200;
		// createmGridPanelstore(me);

		me.dockedItems.push({
			xtype : "pagingtoolbar", 
			store : me.store, // same store GridPanel
			// is using
			dock : "bottom",
			displayInfo : true,
			afterPageText : '/{0}',

			style : {
				background : 'white'
			},

			// border : 0,

			// ui : '',

			listeners : {

				afterrender : function(com, eOpts) {

					var paging = com;

					// console.log(paging.items.items);

					Ext.each(paging.items.items, function(ele, index, all) {

						if (index == 1) {
						} else if (index == 7 || index == 4 || index == 15
								|| index == 14 || index == 13 || index == 12
								|| index == 10) {
						} else if (index == 5) {

						} else {
							ele.hide();
						}

					});

					com.add(0,
							// {
							// xtype : 'button',
							// text : '下单频次',
							// menu : {
							// xtype : 'menu',
							// width : 120,
							// items : [{
							// xtype : 'menuitem',
							// text : '从高到低',
							// handler : function() {
							//
							// var grid = this.up("grid");
							//
							// grid.getStore().sort('orderCount', 'DESC');
							//
							// }
							// }, {
							// xtype : 'menuitem',
							// text : '从低到高',
							// handler : function() {
							//
							// var grid = this.up("grid");
							//
							// grid.getStore().sort('orderCount', 'ASC');
							//
							// }
							// }]
							// }
							// }, {
							// xtype : 'button',
							// text : '产品用量',
							// menu : {
							// xtype : 'menu',
							// width : 120,
							// items : [{
							// xtype : 'menuitem',
							// text : '从高到低',
							// handler : function() {
							//
							// var grid = this.up("grid");
							//
							// grid.getStore()
							// .sort('productCount', 'DESC');
							//
							// }
							// }, {
							// xtype : 'menuitem',
							// text : '从低到高',
							// handler : function() {
							//
							// var grid = this.up("grid");
							//
							// grid.getStore().sort('productCount', 'ASC');
							//
							// }
							// }]
							// }
							// }, {
							// xtype : 'button',
							// text : '产品名称',
							// menu : {
							// xtype : 'menu',
							// items : [{
							// xtype : 'menuitem',
							// text : '升序',
							// handler : function() {
							//
							// var grid = this.up("grid");
							//
							// grid.getStore().sort('productName', 'ASC');
							//
							// }
							// }, {
							// xtype : 'menuitem',
							// text : '降序',
							// handler : function() {
							//
							// var grid = this.up("grid");
							//
							// grid.getStore().sort('productName', 'DESC');
							//
							// }
							// }]
							// }
							// },
//							{
//
//								xtype : 'mysimplebuttongroupfilter',
//
//								filterMode : true,
//
//								conditionObjs : [{
//
//									text : '常用产品',
//									filterField : 'fiscommon',
//									filterValue : '1'
//
//								}]
//
//							}, 
								
									{
								xtype : 'tbspacer',
								flex : 1
							}
							
							);

				}

			}

				// ,
				// displayInfo : true

				});

		this.callParent(arguments);
		me.onload();
	}
	

	
	,
	
	onload : function() {
		var me = this;
	},
	Action_BeforeAddButtonClick : function(me) {
	},
	Action_AfterAddButtonClick : function(me) {
	},
	Action_AddButtonClick : function(me) {
		// Ext.MessageBox.alert('提示', EditUI);
		// onAddButtonClick();
		// Ext.MessageBox.alert('提示', '子类事件');
		var editui = Ext.create(me.EditUI);
		editui.seteditstate("add");
		editui.setparent(me.id);
		editui.show();

	},
	Action_BeforeEditButtonClick : function(me) {
	},
	Action_AfterEditButtonClick : function(me) {
	},
	Action_BeforeViewButtonClick : function(me) {
	},
	Action_AfterViewButtonClick : function(me) {
	},
	loadfields : function(editui, id) {
		var url = "";
		if (editui.editstate === "view" || editui.editstate === "") {
			url = editui.viewurl;
		} else {
			url = editui.infourl;
		}

		if (url == null || Ext.util.Format.trim(url) == "") {
			throw "没有指定数据获取路径";
		};
		Ext.Ajax.request({
			url : url,
			params : {
				fid : id
			}, // 参数
			success : function(response, option) {
				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {
					if (obj.data instanceof Array) {
						editui.getform().setValues(obj.data[0], id);
						editui.editdata = obj.data[0];
					} else {
						editui.getform().setValues(obj.data, id);
						editui.editdata = obj.data;
					}
					editui.onloadfields();
					editui.show();
				} else {
					Ext.MessageBox.alert("成功", obj.msg);
					editui.close();
				}
			},
			failure : function(response, option) {

				if (option.failureType == "server") {
					Ext.MessageBox.alert("成功", option.result.msg);
					editui.close();
				} else if (option.failureType == "connect") {
					Ext.MessageBox.alert("成功", "无法连接服务器");
					editui.close();
				} else if (option.failureType == "client") {
					Ext.MessageBox.alert("成功", "数据错误，非法提交");
					editui.close();
				} else {
					// 其它类型的错误
					Ext.MessageBox.alert("成功", "服务器数据传输失败:"
							+ option.response.responseText);
					editui.close();
				}
			}
		});
	},
	Action_EditButtonClick : function(me) {
		// Ext.MessageBox.alert('提示', EditUI);
		// onAddButtonClick();
		// Ext.MessageBox.alert('提示', '子类事件');
		var record = me.getSelectionModel().getSelection();
		if (record.length == 0) {
			throw "请先选择您要操作的行!";
		};
		var editui = Ext.create(me.EditUI);
		editui.loadfields(record[0].get("fid"));
		editui.seteditstate("edit");
		editui.setparent(me.id);
		// me.loadfields(editui, record[0].get("fid"));
		editui.show();
	},
	Action_BeforeDelButtonClick : function(me, record) {
	},
	Action_AfterDelButtonClick : function(me, record) {
	},
	Action_DelButtonClick : function(me, record) {
		var ids = "";
		for (var i = 0; i < record.length; i++) {
			var fid = record[i].get("fid");
			ids += fid;
			if (i < record.length - 1) {
				ids = ids + ",";
			}
		}
		var el = me.getEl();
		el.mask("系统处理中,请稍候……");
		Ext.Ajax.request({
			url : me.Delurl,
			params : {
				fidcls : ids
			}, // 参数
			success : function(response, option) {
				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {
					// Ext.MessageBox.alert("成功", obj.msg);
					djsuccessmsg(obj.msg);
					el.unmask();
					me.store.load();
				} else {
					Ext.MessageBox.alert("错误", obj.msg);
					el.unmask();
					me.store.load();
				}
			}
		});

	}

});

Ext.define('DJ.quickOrder.quickOrderListList', {
	extend : 'Ext.c.GridPanelT',
	// title : "快速下单",
	id : 'DJ.quickOrder.quickOrderListList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	alias : 'widget.quickorderlistlist',
	fbatchstock:"0",//为1加入购物车 增加备货？
	// ui : '',

	pageSize : 15,

	orderHolder : Ext.create('DJ.quickOrder.QuickOrderHolder'),

	orderHolderPlanEditer : Ext.create('DJ.quickOrder.OrderHolderPlanEditer'),

	mixins : ['DJ.tools.grid.MySimpleGridMixer',
			'DJ.tools.grid.mixer.MyGridSearchMixer'],

	url : 'GetQuickCustProductList.do',
	requires : ['Ext.grid.column.Template', 'Ext.XTemplate', 'Ext.grid.View',
			'Ext.toolbar.Toolbar', 'Ext.button.Button', 'Ext.menu.Menu',
			'Ext.menu.Item', 'Ext.toolbar.Spacer', 'Ext.form.field.Text',
			'Ext.form.Label'],

	style : {

		// fontSize : '30pt',
		gridRowCellFontSize : '30pt'

	},

		plugins : [
	
	{
	ptype : 'mygriditemtipplugin',
	separator : '<br/>',
	showingFields : ['productName','productSpec','productNumber']
	
	}],
	
//	hideHeaders : true,

	closable : false,// 是否现实关闭按钮,默认为false

	showSearchAllBtn : false,

	doBeforeGridSearchAction : function(filters, maskA, nextIndex) {

		var me = this;

		var suplierID = me.down('combobox[name=fsupplierid]').getValue();

		filters.push({

			myfilterfield : "f.fsupplierid",
			CompareType : "=",
			type : "string",
			value : suplierID

		});

		filters.push({
			myfilterfield : 'f.fsupplierid',
			CompareType : " is null",
			value : ''
		});

		// maskstring = "(#0 or #1)";

		maskA.push(Ext.String.format("and (#{0} or #{1})", nextIndex++,
				nextIndex));

	},

	onload : function() {
		// 加载后事件，可以设置按钮，控件值等

		var me = this;
		
		this.magnifier = Ext.create('Ext.ux.form.Magnifier');

		
		
//		MyCommonToolsZ.setComAfterrender(me, function() {
//		
//			var imgs = Ext.query(".proudctImg");
//			
//			Ext.each(imgs, function(ele, index, all) {
//			
//				ele.onclick = DJ.quickOrder.quickOrderListList.showImgPreview;
//				
//				
//			} );
//			
//		} );
		
		
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
//	custbar : [{
//
//		xtype : 'oneclickfilefieldformulitichoice',
//		// xtype : 'ocfile',
//		// buttonText : '',
//		// buttonConfig :{
//		// iconCls : 'upload'
//		// },
//		url : 'uploadQuickCustProductImg.do'
////		,
////		doChange : function() {
////
////			var me = this;
////
////			var grid = me.up('grid');
////
////			me.params = {
////
////				fid : grid.itemId
////
////			};
////
////		}
//
//	}],

	statics : {

		cancelAsFavorites : function() {

			var grid = Ext.getCmp("DJ.quickOrder.quickOrderListList");

			MyCommonToolsZ.reqActionINCusFieldName("setCustproductCommon.do",
					grid, 'fid', "0", "fidcls", "g", false, false, function() {

						grid.getStore().loadPage(1);
						djsuccessmsg('成功');

					});

		},
		
			hold : function(ev) {

			var grid = Ext.getCmp("DJ.quickOrder.quickOrderListList");
			if(grid.fbatchstock==="1"){
			var selectwin=Ext.getCmp("DJ.quickOrder.orderselectWin")||Ext.create("DJ.quickOrder.orderselectWin");
			var  leftposition = ev.getBoundingClientRect().left+ev.getBoundingClientRect().width + 5;
			if (leftposition + selectwin.width > document.body.clientWidth) {
				leftposition = document.body.clientWidth - selectwin.width;
			}
			selectwin.showAt(leftposition,ev.getBoundingClientRect().top-30);
			}else{
			 DJ.quickOrder.quickOrderListList.holdInCar(0);
			}
		},
		holdInCar : function(ftype) {
			var grid = Ext.getCmp("DJ.quickOrder.quickOrderListList");

			var records = MyCommonToolsZ.pickSelectItems(grid);

			if (records == -1) {

				return;

			}
			var suplierID = grid.down('combobox[name=fsupplierid]').getValue();
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");

			Ext.Ajax.request({
				timeout : 6000,

				params : {

					fid : records[0].get('fid'),
					suplierID : suplierID,
					fordertype : records[0].get("hasStock"),
					ftype:ftype

				},

				url : "SaveCusPrivateDelivers.do",
				success : function(response, option) {

					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						djsuccessmsg(obj.msg);

					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
		},

		showImgPreview : function(ev) {

			var grid = Ext.getCmp("DJ.quickOrder.quickOrderListList");

			var records = MyCommonToolsZ.pickSelectItems(grid);

			if (records == -1) {

				return;

			}

			var fid = records[0].get("fid");

			grid.magnifier.loadImages({
				fid : fid
			});

			// var coord = e.getXY();

			ev = ev || window.event;

			var mousePosition = function(ev) {
				if (ev.pageX || ev.pageY) {
					return {
						x : ev.pageX,
						y : ev.pageY
					};
				}
				return {
					x : ev.clientX + document.body.scrollLeft
							- document.body.clientLeft,
					y : ev.clientY + document.body.scrollTop
							- document.body.clientTop
				};
			}

			var mousePos = mousePosition(ev);

			var topLength = mousePos.y + 5;

			if (topLength + 300 > document.body.clientHeight) {

				topLength = document.body.clientHeight - 300;

			}

			grid.magnifier.showAt({
				left : mousePos.x + 80,
				top : topLength
			});

		},

		giveOrders : function() {

			var grid = Ext.getCmp("DJ.quickOrder.quickOrderListList");

			var records = MyCommonToolsZ.pickSelectItems(grid);

			if (records == -1) {

				return;

			}

			var fid = records[0].get("fid");

			// var win = Ext.create('DJ.quickOrder.orderEdit', {
			//
			// cusProductID : fid,
			//
			// customerID : records[0].get("fcustomerid"),
			//
			// suplierID : grid.down('combobox[name=fsupplierid]').getValue()
			//
			// });

			// var win = grid.orderHolderPlanEditer;

			var win = Ext.getCmp('DJ.quickOrder.OrderHolderPlanEditer');

			if (Ext.isEmpty(win)) {

				win = Ext.create('DJ.quickOrder.OrderHolderPlanEditer');

			}

			win.down('form').getForm().reset(true);

			win.gridView = grid;

			win.parent = grid.id;

			win.down('textfield[name=fcustproductid]').setValue(fid);
			win.down('textfield[name=fsupplierids]').setValue(grid
					.down('combobox[name=fsupplierid]').getValue());
			win.down('displayfield[name=productname]').setValue(records[0].data.productName);
			win.down('displayfield[name=productspec]').setValue(records[0].data.productSpec);
			win.fmindays= !records[0].data.fdays?0:records[0].data.fdays;
			win.fdefaultday= !records[0].data.fdefaultdays?5:records[0].data.fdefaultdays;
			win.customerID = records[0].get("fcustomerid");
			win.arminValue = new Date()
//			if(grid.down('combobox[name=fsupplierid]').getRawValue()=='东经'){//包装
//				win.arminValue = Ext.Date.add(new Date(), Ext.Date.DAY,2);
//			}						
			if (records[0].get("hasStock") == 0) {
				
				win.trueStock = false;

			} else {

				win.trueStock = records[0].get("stock")> records[0].get("neededToSendCount")?records[0].get("stock")//库存-需发货数量
				- records[0].get("neededToSendCount"):0;
				win.down('displayfield[name=productbalance]').setValue(win.trueStock);
			}


			win.show();

		},

		viewProduct : function() {

		},

		setAsFavorites : function() {

			var grid = Ext.getCmp("DJ.quickOrder.quickOrderListList");

			MyCommonToolsZ.reqActionINCusFieldName("setCustproductCommon.do",
					grid, 'fid', "1", "fidcls", "g", false, false, function() {
						grid.getStore().loadPage(1);
						djsuccessmsg('成功');

					});

		},

		uploadImg : function() {

			var grid = Ext.getCmp("DJ.quickOrder.quickOrderListList");

			var record = grid.getSelectionModel().getSelection();

			if (record.length != 1) {

				Ext.Msg.alert("提示", "只能选择一条进行操作");

				return;

			}

			var uploaderCfg = {

				url : "uploadQuickCustProductImg.do" + "?fid="
						+ record[0].get("fid"),
				max_file_size : '11mb',
				unique_names : false,
				multiple_queues : true,
				chunk_size : '10mb',
				file_data_name : "upload1",
				multipart : true,
				multipart_params : {
					fid : record[0].get("fid")
				},

				listeners : {

					close : function(panel, eOpts) {

						grid.getStore().load();

					}
				},
				filters : [{
					title : "图片",
					extensions : "jpg,jpeg,png,gif,bmp"
				}]

			};

			// if (Ext.isIE) {
			//
			// uploaderCfg.runtimes = 'html5,html4';
			//
			// }

			var win = Ext
					.create("DJ.tools.file.MultiUploadDialog", uploaderCfg);

			win.show();

		},

		deleteImg : function() {

			var gridMain = Ext.getCmp("DJ.quickOrder.quickOrderListList");

			var record = gridMain.getSelectionModel().getSelection();

			if (record.length != 1) {

				Ext.Msg.alert("提示", "只能选择一条进行操作");

				return;

			}

			var grid = Ext.create("DJ.quickOrder.QuickOrderImgShower",{
				parent:'DJ.quickOrder.quickOrderListList',
				imgid:record[0].get("fid")
			}), store = grid
					.getStore(), me = this;
					
			grid.itemId = record[0].get("fid");

			var widthT = 500;

			var winT = Ext.create('Ext.window.Window', {
				// title: '',
				modal : true,
				resizable : false,
				height : widthT / 16 * 9,
				width : widthT,
				layout : 'fit',
				items : [grid],

				listeners : {

					close : function(panel, eOpts) {

						gridMain.getStore().load();

					}
				}

			});

			if (Ext.isIE) {

				winT.showAt(500, 300);
			} else {

				winT.show();

			}

			store.loadPage(1, {
				params : {

					fid : record[0].get("id")
				}
			});

		}

	},

	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			// columns: [
			// {
			// xtype: 'templatecolumn',
			// tpl: [
			// '<table width="150" height="150" border="0"><tr><td
			// colspan="2"><div align="center"><img src="" alt="product"
			// name="proudct" width="100" height="100"
			// id="proudct"></div></td></tr><tr><td width="50%"><div
			// align="center"><a href="#">上传图片</a></div></td><td
			// width="50%"><div align="center"><a
			// href="#">删除图片</a></div></td></tr></table>'
			// ],
			// width: 150,
			// text: 'MyTemplateColumn2'
			// },
			// {
			// xtype: 'templatecolumn',
			// tpl: [
			// '<table width="200" height="150" border="0"><tr><td><div
			// align="center">{productName}1</div></td></tr><tr><td><div
			// align="center">{productSpec}2</div></td></tr></table>'
			// ],
			// width: 200,
			// text: 'MyTemplateColumn3'
			// },
			// {
			// xtype: 'templatecolumn',
			// tpl: [
			// '<table width="150" height="150" border="0"><tr><td><div
			// align="center">下单{orderCount}次</div></td></tr><tr><td><div
			// align="center">已经购{productSpec}只</div></td></tr></table>'
			// ],
			// width: 150,
			// text: 'MyTemplateColumn1'
			// },
			// {
			// xtype: 'templatecolumn',
			// tpl: [
			// '<table width="150" height="150"
			// border="0"><tr><td></td></tr><tr><td><div align="center"><a
			// href="javascript：">下单</a></div></td></tr></table>'
			// ],
			// text: 'MyTemplateColumn'
			// }
			// ],
			dockedItems : [{
				xtype : 'toolbar',
				dock : 'top',
				style : {
					background : 'white'

				},
				border : 0,
				items : [{
					height : 30,
					text : "新  增",
					iconCls : "addnew",
					handler : function() {
						this.up().down('button[text=产品管理] menuitem[text=增加]')
								.handler();
					}
				}, {
					height : 30,
					text : "修  改",
					iconCls : "addnew",
					handler : function() {
						var record = this.up('grid').getSelectionModel()
								.getSelection();
						if (record.length != 1) {
							Ext.Msg.alert('提示', '请选择一条数据！');
							return;
						}
						this.up().down('button[text=产品管理] menuitem[text=增加]')
								.handler();
						var win = Ext.getCmp('DJ.quickOrder.ProductAddWin');
						// var oldUrl = win.url;
						win.url = 'UpdateQuickCusproduct.do?fid='
								+ record[0].get('fid');
						// win.down('button[text*=保]').hide();
						win.down('textfield[fieldLabel=产品名称]')
								.setValue(record[0].get('productName'));
						win.down('textfield[fieldLabel=产品规格]')
								.setValue(record[0].get('productSpec'));
						// var updateUrl = function(){
						// if(win.url==oldUrl){
						// win.url = oldUrl;
						// }
						// }
						// win.on({
						// "close":updateUrl,
						// "hide":updateUrl
						// });
					}
				}, {
					height : 30,
					text : "查  看",
					iconCls : "addnew",
					handler : function() {
						var record = this.up('grid').getSelectionModel()
								.getSelection();
						console.log(record);
						if (record.length != 1) {
							Ext.Msg.alert('提示', '请选择一条数据！');
							return;
						}
						this.up().down('button[text=产品管理] menuitem[text=增加]')
								.handler();
						var win = Ext.getCmp('DJ.quickOrder.ProductAddWin');
						win.down('button[text*=保]').hide();
						// Ext.each(win.query('textfield'),function(ele){
						win.down('textfield[fieldLabel=产品名称]')
								.setValue(record[0].get('productName'))
								.setReadOnly(true);
						win.down('textfield[fieldLabel=产品规格]')
								.setValue(record[0].get('productSpec'))
								.setReadOnly(true);
						// })
					}
				}, {
					height : 30,
					text : "删    除",
					iconCls : "delete",
					handler : function() {
						var me = this;
						var record = me.up('grid').getSelectionModel()
								.getSelection();
						if (record.length == 0) {
							Ext.Msg.alert('提示', '请选择数据！');
							return;
						}
						Ext.Msg.show({
							title : '提示',
							msg : '是否确认删除选中的内容？',
							buttons : Ext.Msg.YESNO,
							icon : Ext.Msg.QUESTION,
							fn : function(btn) {
								if (btn == 'yes') {
									me
											.up()
											.down('button[text=产品管理] menuitem[text=删除]')
											.handler();
								}
							}
						});
					}
				}, {
					xtype : 'button',
					text : '产品管理',
					hidden : true,
					menu : {
						xtype : 'menu',
						items : [{
							xtype : 'menuitem',
							text : '增加'
							// ,

							// menu :
							//
							// (function() {
							//
							// //Ext.isChrome
							// if (false) {
							//
							// return {};
							// } else {
							//
							// return {
							// xtype : 'menu',
							//
							// // ignoreParentClicks : true,
							//
							// items : [{
							// xtype : 'form',
							// layout : {
							// type : 'vbox',
							// align : 'stretch'
							// },
							// title : '快速保存',
							// bodyPadding : 10,
							//
							// url : 'SaveQuickCusproduct.do',
							//
							// items : [{
							// name : 'fname',
							// xtype : 'textfield',
							// flex : 1,
							// fieldLabel : '产品名称',
							// allowBlank : false,
							// allowOnlyWhitespace : false,
							//
							// enableKeyEvents : true,
							// listeners : {
							// keyup : function(com, e) {
							//
							// if (Ext.EventObject.ENTER == e
							// .getKey()) {
							//
							// var saveBtn = this
							// .up('form')
							// .down('button[text=保存]');
							//
							// saveBtn.handler
							// .call(saveBtn);
							//
							// }
							//
							// }
							// }
							//
							// }, {
							// name : 'fspec',
							// xtype : 'textfield',
							// flex : 1,
							// fieldLabel : '产品规格'
							// // ,
							// // allowBlank : false,
							// // allowOnlyWhitespace : false
							//
							// ,
							//
							// enableKeyEvents : true,
							// listeners : {
							// keyup : function(com, e) {
							//
							// if (Ext.EventObject.ENTER == e
							// .getKey()) {
							//
							// var saveBtn = this
							// .up('form')
							// .down('button[text=保存]');
							//
							// saveBtn.handler
							// .call(saveBtn);
							//
							// }
							//
							// }
							// }
							// }],
							//
							// buttons : [{
							// text : '保存',
							// handler : function() {
							//
							// var grid = Ext
							// .getCmp('DJ.quickOrder.quickOrderListList');
							//
							// // The getForm() method
							// // returns the
							// // Ext.form.Basic instance:
							// var form = this.up('form')
							// .getForm();
							// if (form.isValid()) {
							// // Submit the Ajax
							// // request and
							// // handle the response
							// form.submit({
							//
							// params : {
							// fsupplierid : grid
							// .down('combobox[name=fsupplierid]')
							// .getValue()
							// },
							//
							// success : function(
							// form,
							// action) {
							//
							// grid
							// .getStore()
							// .loadPage(1);
							// },
							// failure : function(
							// form,
							// action) {
							// Ext.Msg.alert(
							// '失败',
							// '保存失败');
							// }
							// });
							// }
							// }
							// }]
							//
							// }]
							//
							// }
							// }
							//
							// })()

							,

							handler : function(item, e) {

								var grid = Ext
										.getCmp("DJ.quickOrder.quickOrderListList");

								var winAdd = Ext
										.create('DJ.quickOrder.ProductAddWin');

								var supplierF = winAdd.down('hiddenfield');

								supplierF.setValue(grid
										.down('combobox[name=fsupplierid]')
										.getValue());

								winAdd.seteditstate('edit');

								winAdd.parent = grid.id;

								winAdd.gridView = grid;

								winAdd.showAt();
							}

						},

								{
									xtype : 'menuitem',
									text : '删除',
									handler : function() {

										var grid = Ext
												.getCmp("DJ.quickOrder.quickOrderListList");

										MyCommonToolsZ.reqAction(
												'forbiddenCusproduct.do', grid,
												null, 1, false, true);

									}
								}
						]
					}
				}, {

//					text : '暂存订单',
//					handler : function() {
//
//						var me = this;
//
//						// var holder = me.up('grid').orderHolder;
//
//						var holder = Ext
//								.getCmp('DJ.quickOrder.QuickOrderHolder');
//
//						if (Ext.isEmpty(holder)) {
//
//							holder = Ext
//									.create('DJ.quickOrder.QuickOrderHolder');
//
//						}
//
//						holder.parent = me.up('grid').id;
//
//						var holderGrid = holder.down('grid');
//
//						// 用模拟store技术
//
//						holder.remoteStore.loadPage(1, {
//
//							callback : function(recorders, operation, success) {
//
//								if (!success) {
//
//									Ext.Msg.alert("提示", '失败');
//
//								} else {
//
//									holderGrid.getStore().loadData(recorders);
//
//									holder.seteditstate('edit');
//
//									holder.show();
//								}
//
//							}
//
//						});
//
//					}
//
//				},{
//					text:'今日订单',
					text:'订单查看',
					handler:function(){
						var grid = this.up('grid');
						var win = Ext.getCmp('DJ.quickOrder.orderInfo.OrdInfoAndPrepareGoodsPanel');
						var newDate = new Date();
						var maintab = grid.up('tabpanel');
						var bol = false;
						if(Ext.isEmpty(win)){
							win = Ext.create('DJ.quickOrder.orderInfo.OrdInfoAndPrepareGoodsPanel');
							maintab.add(win);
						}
						Ext.each(maintab.items.items,function(item,index){
							if(item.id==win.id){
								bol = true;
							}
						})
						if(!bol){
							maintab.add(win);
						}
						maintab.setActiveTab(win);
						var orderGrid = win.down('grid');
//						orderGrid.store.setDefaultfilter([{
//							myfilterfield : "mv.fcreatetime",
//							CompareType : ">=",
//							type : "datetime",
//							value : newDate.toLocaleDateString().replace(/\//g,'-')+" 00:00:00"
//						},{
//							myfilterfield : "mv.fcreatetime",
//							CompareType : "<=",
//							type : "datetime",
//							value : newDate.toLocaleDateString().replace(/\//g,'-')+" 23:59:59"
//						}]);
//						orderGrid.store.setDefaultmaskstring('#0 and #1')
						orderGrid.store.loadPage(1);
					}
				}, 
//					{
//
//					xtype : 'oneclickfilefieldformulitichoice',
////					 xtype : 'ocfile',
//					// buttonText : '',
//					// buttonConfig :{
//					// iconCls : 'upload'
//					// },
//					url : 'uploadQuickCustProductImg.do',
//					doChange : function() {
//
//						var me = this;
//
//						var grid = me.up('grid');
//
//						me.params = {
//
//							fid : grid.itemId
//
//						};
//
//					}
//
//				},
				'|', {
					// fieldLabel:'制造商',
					name : 'fsupplierid',
					// id : 'DJ.order.Deliver.ProductDemandEdit.fsupplierid',
					// labelWidth:130,
					// width:380,
					xtype : 'combobox',
					displayField : 'fname',
					valueField : 'fid',
					editable : true,

					queryMode : 'local',
					typeAhead : true,
					forceSelection : true,
					queryCaching : false,
					doRawQuery : function() {
						if(Ext.isIE){
							this.doQuery(this.getRawValue(), false, true);	
						}else{
							this.doQuery(this.getRawValue().trim(), false, true);	
						}
						
					},
					changeAction : function(comb, record, eOpts) {
						var grid = comb.up("grid");
						var storeT = grid.getStore();
						if(Ext.isEmpty(comb.getRawValue().trim())||Ext.isEmpty(comb.getValue().trim())) {
						storeT.removeAll();
						return ;
						}
						var myfilter = [];
						myfilter.push({
							myfilterfield : 'f.fsupplierid',
							CompareType : "=",
							type : "string",
							value : comb.getValue()
						});

						myfilter.push({
							myfilterfield : 'f.fsupplierid',
							CompareType : " is  null",
							value : ''
						});

						maskstring = "(#0 or #1)";

						// maskstring = "#0";

						storeT.setDefaultfilter(myfilter);
						storeT.setDefaultmaskstring(maskstring);
						storeT.loadPage(1);
						if(record.length>0){//加入购物车状态转化
						grid.fbatchstock=record[0].data.fbacthstock;
						}

					},

					listeners : {

						select  : function(comb, records, eOpts) {
							comb.changeAction(comb, records, eOpts);

						},
						
						expand : function( field, eOpts ) {
						
							field.store.load();
							
						}

					},

					store : Ext.create('Ext.data.Store', {
						fields : ['fid', 'fname','fbacthstock'],
						proxy : {

							type : 'ajax',
							url : 'GetSuppliersOfCustomer.do',
							reader : {
								type : 'json',
								root : 'data'
							}
						},

						autoLoad : true,

						listeners : {
							load : function(me, records) {
								if (records && records.length
										&& records.length == 1) {

									var com = Ext
											.getCmp('DJ.quickOrder.quickOrderList')
											.down("combobox[name=fsupplierid]");
									if(Ext.isEmpty(com.getValue())){
										com.setValue(records[0].get('fid'));
										com.fireEvent('select', com,records,null );
									}

//									com
//											.changeAction(com, records[0]
//													.get('fid'));

								} else if (records && records.length
										&& records.length > 1) {
//									 var record = me.findRecord('fname','东经');
									var record= me.findRecord('fid','39gW7X9mRcWoSwsNJhU12TfGffw=');
									var com = Ext
											.getCmp('DJ.quickOrder.quickOrderList')
											.down("combobox[name=fsupplierid]");

									
									if(Ext.isEmpty(com.getValue())){
							
	                   				 if(record){
//										if(record>=0){	com.setValue(me.data.items[record].data.fid);}
	                   				 	com.setValue(record.get("fid"));
	                   				 	com.fireEvent('select', com,new Array(record),null );

	                   				 }
									}
									
//									com.setValue(records[0].get('fid'));

//									com
//											.changeAction(com, records[0]
//													.get('fid'));

								}
							}

						}
					})
				}, {
					xtype : 'tbspacer',
					flex : 1
				}, {
					xtype : 'mymixedsearchbox',
					condictionFields : ['c.fname', 'c.fspec'],
					tip : '请输入产品名称,规格',
					useDefaultfilter : true,
					filterMode : true,
					beforeSearchActionForStore:function(myfilter,maskstrings){
						Ext.each(myfilter,function(ele){
							if(ele.myfilterfield=='c.fspec'){
								ele.value = ele.value.replace(/\*/g,'_').replace(/\X/g,'_').replace(/\x/g,'_');
							}
						})		
					}
						// ,
						//					
						// beforeSearchActionForStore : function(myfilter,
						// maskstrings) {
						//
						// var me = this;
						//
						// var suplierID = me.up('grid')
						// .down('combobox[name=fsupplierid]').getValue();
						//
						// myfilter.push({
						//
						// myfilterfield : "f.fsupplierid",
						// CompareType : "=",
						// type : "string",
						// value : suplierID
						//
						// });
						//
						// maskstrings.push("and #1");
						//
						// }
						// ,
						// filterMode : true

						}]
			}, {
				xtype : 'toolbar',
				dock : 'top',
				style : {
					background : 'white'

				},
//				border : 0,
				items : [{

					xtype : 'mysimplebuttongroupfilter',

					filterMode : true,

					conditionObjs : [{

						text : '常用产品',
						filterField : 'fiscommon',
						filterValue : '1'

					}]

				},'|',{
				text : '购物车',
	
					handler : function() {

						var me = this;

						var holder = Ext.getCmp('DJ.quickOrder.QuickOrderHolder') || Ext.create('DJ.quickOrder.QuickOrderHolder');

						holder.reset();
						
						holder.parent = me.up('grid').id;

						var holderGrid = holder.table1,
							holderGrid2 = holder.table2;

						// 用模拟store技术

						holder.remoteStore.loadPage(1, {

							callback : function(records, operation, success) {

								if (!success) {

									Ext.Msg.alert("提示", '获取购物车数据失败，请刷新页面重试！');

								} else {
									var records1 = [],
										records2 = [];
									Ext.Array.each(records,function(item){
										if(item.get('ftype')=='0'){
											records1.push(item);
										}else{
											records2.push(item);
										}
									});
									if(records1.length>0){
										holderGrid.getStore().loadData(records1);
									}else if(records2.length>0){
										holder.down('radiogroup').setValue({type:'2'});
									}
									if(records2.length>0){
										holderGrid2.getStore().loadData(records2);
									}else{
										holder.down('radio[boxLabel=备货]').hide();
									}
									holder.seteditstate('edit');
									holder.show();
								}

							}

						});

					}
			
				}]

			}]
		});

		me.callParent(arguments);
		this.getStore().on('load',function(me,records){
			Ext.Array.each(records,function(record){
				record.set('_dc',new Date().getTime());
			});
		});
	},

	fields : [{
		name : 'fid'
	}, 'productName', 'proImgUrl','productSpec','_dc','productNumber','fprice','fdays','fdefaultdays', 'recentlyOrderType',{
		name : 'orderCount',

		type : 'int'

	}, {
		name : 'productCount',
		type : 'int'
	}, {
		name : 'neededToSendCount',
		type : 'int'
	}, {
		name : 'stock',
		type : 'int'
	}, {
		name : 'finishedProductStock',
		type : 'int'
	}, {
		name : 'inLineStock',
		type : 'int'
	}, {
		name : 'recentlyOrderCount',
		type : 'int'
	}, 'fcustomerid', 'recentlyOrderDate', {
		name : 'fiscommon',

		type : 'string',
		convert : function(val) {
			return val == "true" ? "1" : "0"
		}

	}, {
		name : 'hasStock',
		type : 'int',

		defaultValue : 0

	}],
	columns : [


			{
				xtype : 'templatecolumn',
//				tpl : ['<table width="150" height="150" border="0"><tr><td colspan="2"><div align="center"><img onclick="DJ.quickOrder.quickOrderListList.showImgPreview(event)"  src="{proImgUrl}" alt="product" class="proudctImg" width="100" height="100" /></div></td></tr><tr style = "font-size:12pt;font-family:\'宋体\',\'隶书\';"><td width="50%" ><div align="center"><a href="javascript:DJ.quickOrder.quickOrderListList.uploadImg();">上传图片</a></div></td><td width="50%"><div align="center"><a href="javascript:DJ.quickOrder.quickOrderListList.deleteImg();">删除图片</a></div></td></tr></table>'],
				
				tpl : ['<table width="150" height="150" border="0"><tr><td colspan="2"><div align="center"><img onclick="DJ.quickOrder.quickOrderListList.showImgPreview(event)" src="getFileSourceByParentId.do?fid={fid}&_dc={_dc}" alt="product" class="proudctImg" width="100" height="100"></div></td></tr><tr style="font-size:12pt;font-family:\'宋体\', \'隶书\'"><td width="100%"><div align="center"><a href="javascript:DJ.quickOrder.quickOrderListList.deleteImg();">编辑图片</a></div></td></tr></table>'],
				
				width : 150,
				
				align : 'center',
				
				text : '产品图片'
			}, {
				xtype : 'templatecolumn',
				tpl : ['<table  style = "font-size:16pt;font-family:\'宋体\',\'隶书\'" width="100%" height="150" border="0"  cellspacing="10"><tr><td  valign="bottom" ><div align="center">{productName}</div></td></tr><tpl if="productSpec.length &gt; 0 & productNumber.length &gt; 0"><tr><td valign="center"><div align="center">{productSpec}</div></td></tr></tpl><tr><td valign="top"><div align="center"><tpl if=" productNumber.length &gt; 0">{productNumber}<tpl else>{productSpec}</tpl></div></td></tr></table>'],
				// width : 200,
				flex : 4,
				align : 'center',
				text : '产品名称'
			},

			{
				xtype : 'templatecolumn',
				tpl : ['<tpl if="hasStock == 1"><table  border="0"	 cellspacing="10"><tr><td valign="bottom"><div align="center">需发货{neededToSendCount}只<br></div></td></tr><tr><td><div align="center">库存{stock}只</div></td></tr><tr><td valign="top"><div align="center">（成品{finishedProductStock}只，在生产{inLineStock}只）</div></td></tr></table></tpl>'],
//				flex: 1,
				width:180,
				text : '库存',
				align : 'center'
			},

			{
				xtype : 'templatecolumn',
				tpl : ['<table width="100%"  border="0" cellspacing="10"><tr><td valign="buttom"><tpl if="recentlyOrderCount != 0"><div align="center">{recentlyOrderDate}</div></tpl></td></tr><tr><td valign="center"><tpl if="recentlyOrderCount != 0"><div align="center">{recentlyOrderCount}只</div></tpl></td></tr><tr><td valign="top"><tpl if="recentlyOrderCount != 0&&recentlyOrderType !==\'要货\'"><div align="center">{recentlyOrderType}</div></tpl></td></tr></table>'],				flex: 2,
				text : '最近下单',
				align : 'center'
			},
			{ 
				text : '单价',
				align : 'center',
				dataIndex:'fprice',
				hideable : true,
				width:100,
				hidden:true,
				 renderer :function(v,m){
				 	if(!Ext.isEmpty(v)) v=Ext.util.Format.currency(v,'\u5143',1,true); // v=eval(v)+"元";
				 	return v;
				 }
			},
			// {
			// xtype : 'templatecolumn',
			// tpl : ['<table width="150" height="150" border="0"
			// cellspacing="10"><tr><td valign="bottom" ><div
			// align="center">下单{orderCount}次</div></td></tr><tr><td
			// valign="top"><div
			// align="center">已经购{productCount}只</div></td></tr></table>'],
			// width : 150,
			// text : 'MyTemplateColumn1'
			// },
			{
				xtype : 'templatecolumn',
				tpl : ['<table width="150" height="150" border="0" cellspacing="10" style = "font-size:12pt;font-family:\'宋体\',\'隶书\';"><tr><td valign="bottom"><div align="center"><a href="javascript:DJ.quickOrder.quickOrderListList.giveOrders();">下单</a></div></td></tr><tr><td valign="center"><div align="center"><a href="javascript:void(0);" onclick="DJ.quickOrder.quickOrderListList.hold(this)">加入购物车</a></div></td></tr><tr><td valign="top"><div align="center"><tpl if="fiscommon == 0"><a href="javascript:DJ.quickOrder.quickOrderListList.setAsFavorites();" >设为常用</a></tpl><tpl if="fiscommon== 1"><a href="javascript:DJ.quickOrder.quickOrderListList.cancelAsFavorites();">取消常用</a></tpl></div></td></tr></table>'],				text : '操作',
				flex: 2
			}
	// ,
	// {
	//
	// xtype : 'templatecolumn',
	// tpl : ['<table width="150" height="150" border="0"
	// cellspacing="10"><tr><td valign="bottom" ></td></tr><tr><td
	// valign="top"><div align="center"><a
	// href="javascript:DJ.quickOrder.quickOrderListList.setAsFavorites();">设为常用</a></div></td></tr></table>'],
	// width : 150,
	// text : 'MyTemplateColumn1'
	//
	// }

	],
	selModel : Ext.create('Ext.selection.CheckboxModel')
});

Ext.define('DJ.quickOrder.quickOrderList', {
	extend : 'Ext.panel.Panel',

	id : 'DJ.quickOrder.quickOrderList',

	requires : [
			// 'MyApp.view.MyGridPanel',
			'Ext.form.Label', 'Ext.grid.Panel'],

	// height: 494,
	// width: 733,
	title : '快速下单',

	layout : {
		type : 'vbox',
		align : 'stretch'
	},

	closable : true,

	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [
			// {
			// xtype : 'label',
			// flex : 0,
			// height : 52,
			// style : {
			// fontSize : '20pt',
			// textAlign : 'center'
			// },
			// text : '快速下单'
			// },
			{
				xtype : 'quickorderlistlist',
				flex : 1
			}]
		});

		me.callParent(arguments);
	}

});



// Ext.create('Ext.window.Window', {
// title : 'Hello',
// height : 200,
// width : 400,
// layout : 'fit',
// items : [ // Let's put an empty grid in just to illustrate fit layout
// Ext.create('DJ.quickOrder.quickOrderList')]
// }).show();

Ext.define('DJ.quickOrder.orderselectWin', {
			id : 'DJ.quickOrder.orderselectWin',
			extend : 'Ext.window.Window',
			title : '请选择类型',
			modal : true,
			closable : true,
			resizable : false,
			width : 80,
			style:{
			backgroundColor:'white'
			},
			bodyStyle : 'background:white;border:0',
			bodyPadding : "10 10",
			layout : {
				type : 'vbox',
				align : 'maxstretch'
			},
			header:false,
			items : [{
						style : {
							background : 'white'
						},
						border : 0,
						padding : "0 0 10 0",
						layout : {
							type : 'hbox',
							align : 'stretch'
						},
						items : [{
									boxLabel : ' ',
									xtype : "radio",
									name : 'ftype',
									handler:function(){
									 this.up("window").close();
									 DJ.quickOrder.quickOrderListList.holdInCar(0);
									}
									
								}, {
									xtype : "button",
									text : '要货'
									,
									handler:function(){
									 this.up("window").close();
									 DJ.quickOrder.quickOrderListList.holdInCar(0);
									}
									
								}]
					}, {
						style : {
							background : 'white'

						},
						border : 0,
						layout : {
							type : 'hbox',
							align : 'stretch'
						},
						items : [{
									boxLabel : ' ',
									xtype : "radio",
									name : 'ftype',
									handler:function(){
									 this.up("window").close();
									 DJ.quickOrder.quickOrderListList.holdInCar(1);
									}
								}, {
									xtype : "button",
									text : '备货'
									,
									handler:function(){
										this.up("window").close();
									 DJ.quickOrder.quickOrderListList.holdInCar(1);
									}
								}]
					}]
	
		});
