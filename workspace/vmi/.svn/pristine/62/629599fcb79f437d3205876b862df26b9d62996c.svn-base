Ext.define('DJ.quickOrder.orderInfo.OrdInfoList', {
	extend : 'DJ.myComponent.grid.MySimpleConciseGrid',
	// title : "",
	id : 'DJ.quickOrder.orderInfo.OrdInfoList',
	// pageSize : 50,

	// closable : true,// 是否现实关闭按钮,默认为false
	url : 'gainCustDelApplylist.do',

	Delurl : "delCustDelApplyInfo.do",
	EditUI : "DJ.quickOrder.orderInfo.OrdInfoEditor",
	// exporturl:"outWarehousetoexcel.do",

//	closeAction : 'destroy',
	
	stateful : true,
	
	// stateShower : Ext
	// .create('DJ.quickOrder.orderInfo.simpleCompnent.StateShower'),

	pagingtoolbarDock : 'bottom',

	mixins : ['DJ.tools.grid.MySimpleGridMixer',
			'DJ.tools.grid.mixer.MyGridSearchMixer'],

	isFirststateShower : true,
	showSearchAllBtn : false,
	statics : {

		showImgPreview : function(ev) {

			var grid = Ext.getCmp("DJ.quickOrder.orderInfo.OrdInfoList");

			var records = MyCommonToolsZ.pickSelectItems(grid);

			if (records == -1) {

				return;

			}

			var fid = records[0].get("fcusproductid");

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
		
		deleteCus : function() {
			var grid = Ext.getCmp('DJ.quickOrder.orderInfo.OrdInfoList');

			grid.doDeleteAction(grid);

		},

		editCus : function() {

			var grid = Ext.getCmp('DJ.quickOrder.orderInfo.OrdInfoList');

			grid.doEditAction(grid);

		}

	},

	cusTopBarItems : [{

		xtype : 'mysimplebuttongroupfilter',

		filterMode : true,

		conditionObjs : [{

			text : '未接收',
			filterField : 'fstate',
			filterValue : '0'

		}, {

			text : '已接收',
			filterField : 'fstate',
			filterValue : '1,2,3'

		}, {

			text : '已入库',
			filterField : 'fstate',
			filterValue : '4'

		}, {

			text : '部分发货',
			filterField : 'fstate',
			filterValue : '5'

		}, {

			text : '全部发货',
			filterField : 'fstate',
			filterValue : '6'

		}]

	}, {

		xtype : 'tbspacer',
		flex : 1

	}, {

		xtype : 'mymixedsearchbox',
		condictionFields : ['mv._custpdtname', 'mv._spec', 'mv._suppliername',
				'mv.fnumber', 'mv.fordernumber'],
		tip : '请输入“产品名称”“规格”“制造商”“申请单号”“采购订单号”'
		,
		
		beforeSearchActionForStore : function(myfilter, maskstrings,
				condictionValue) {
			Ext.each(myfilter,function(ele){
				if(ele.myfilterfield=='mv._spec'){
					ele.value = ele.value.replace(/\*/g,'_').replace(/\X/g,'_').replace(/\x/g,'_');
				}
			})			
		},
		
		
		useDefaultfilter : true,
		filterMode : true
		

	}, {

		xtype : 'button',
		text : '筛选条件',
		menu : {
			xtype : 'menu',
			items : [{

				xtype : 'mydatetimesearchbox',
				filterMode : true,
				useDefaultfilter : true,
				labelFtext : '下单时间',
				conditionDateField : 'mv.fcreatetime',

				startDate : Ext.Date.subtract(new Date(), Ext.Date.MONTH, 2)

			}, {

				xtype : 'mydatetimesearchbox',
				filterMode : true,
				useDefaultfilter : true,
				labelFtext : '交        期',
				conditionDateField : 'mv.farrivetime'

			}]
		}

	}, {

				xtype : 'myexcelexportercuscfgbutton',
				mode : 1,
				excelTitle : '订单信息',
				
					text : '导出', // 导出
					iconCls : '',
				
				changeMode : function(mode, grid) {

					var records = grid.getSelectionModel().getSelection();

					return records.length == 0 ? 3 : 1;

				},
				
				columns : [{
					text : '产品名称',
					dataIndex : 'fpdtname',
					width : 100
				}, {
					text : '规格',
					dataIndex : 'fpdtspec',
					width : 100
				}, {
					text : '采购数量',
					dataIndex : 'famount',
					width : 50,
					renderer : function(val) {
						return val + '只'
					}
				}, {
					text : '实送数量',
					width : 50,
					dataIndex : 'foutqty',
					renderer : function(val) {
						return val + '只'
					}
				}, {
					text : '数量',
					width : 50,
					dataIndex : 'famount',
					renderer : function(val) {
						return val + '只'
					}
				}, {
					text : '交期',
					width : 100,
					dataIndex : 'farrivetime'
				}, {
					text : '地址',
					dataIndex : 'faddress'
				}, {
					text : '制造商',
					width : 100,
					dataIndex : 'fsupplier'
				}, {
					text : '采购订单号',
					width : 100,
					dataIndex : 'fordernumber'
				}, {
					width : 50,
					text : '状态',
					dataIndex : 'fstate',
					renderer : function(val) {
						if (val == 0) {
							return '未接收';
						} else if (val == 1 || val == 2 || val == 3) {
							return '已接收';
						} else if (val == 4) {
							return '已入库';
						} else if (val == 5) {
							return '部分发货';
						} else {
							return '全部发货';
						}
					}
				}, {
					text : '申请单号',
					width : 100,
					dataIndex : 'fnumber'
				}, {
					text : '下单时间',
					width : 100,
					dataIndex : 'fcreatetime'
				}, {
					text : '备注',
					dataIndex : 'fdescription'
				}]

			}

	],

	onload : function() {
		
		this.magnifier = Ext.create('Ext.ux.form.Magnifier');
		
		// 加载后事件，可以设置按钮，控件值等

//		var me = this;
//
//		MyCommonToolsZ.setComAfterrender(me, function() {
//
//			setTimeout(function() {
//				var btnSC = me.down('button[text=筛选条件]');
//				
//				btnSC.showMenu();
//
//				
//
//				setTimeout(function(){
//				
//					me.doGridSearchAction();
//					btnSC.hideMenu();
//				},500);
//				
//				
//
//			}, 1000);
//
//		});

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

	fields : [{
		name : 'fid'
	}, {
		name : 'fpdtname'
	}, {
		name : 'fpdtspec'
	}, {
		name : 'forderentryid'
	}, {
		name : 'famount',
		type : 'int'
	}, {
		name : 'farrivetime',

		convert : function(v, record) {

			var dateFormat = 'Y-m-d H:i:s.u';

			var date = Ext.Date.parse(v, dateFormat);

			var fs = Ext.Date.format(date, 'Y-m-d');

			var noonF = date.getHours() <= 12 ? '上午' : '下午';

			fs += ' ' + noonF;

			return fs;

		}

	}, 'faddress', 'fsupplier', 'fordernumber', 'fnumber', 'fcreatetime',

	{
		name : 'fstate',

		type : 'int'

	}, 'fdescription', 'fcusproductid', {

		name : 'foutqty',
		type : 'int'

	}, 'stateShowerF'

	],

	listeners : {

		cellmousedown : function(com, td, cellIndex, record, tr, rowIndex, e,
				eOpts) {

//			if (cellIndex != 11) {
//
//				return;
//
//			}
           var arrcon = ['未接收','已接收','已入库','部分发货','全部发货']
			if(arrcon.indexOf(td.textContent)==-1){
			    return;
			}
			var grid = com.panel;

			var jsonS = record.get('stateShowerF');

			if (!td.hasTip && !Ext.isEmpty(jsonS)) {

				var cfgObj = Ext.JSON.decode(jsonS);

				var show = Ext
						.create('DJ.quickOrder.orderInfo.simpleCompnent.StateShower');

				show.setTimes(cfgObj);

				// if (grid.isFirststateShower) {

				var tip = Ext.create('Ext.tip.ToolTip', {
					maxWidth : 1000,
					// autoShow : true,
					target : td,
					items : [show],
					dismissDelay : 60000
				});
				td.hasTip = true;

				tip.onMouseDown = Ext.emptyFn;

				tip.onDocMouseDown = Ext.emptyFn;

				// tip.showAt(e.getXY());

				tip.onTargetOver(e);

				// grid.isFirststateShower = false;
				//
				// }

			}

		}

	},

	columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		xtype : 'mysimpleimagecolumn',
		imgUrlField : 'getFileSourceByParentId.do?fid={fcusproductid}',
		imgClickAble : false,
		
		onclick : 'DJ.quickOrder.orderInfo.OrdInfoList.showImgPreview(event)',
		
		'header' : '产品图片',
	    align : 'center',
		width : 150,
		menuDisabled : true,
		sortable : true,
		
		stateId : 'img'
		
	}, {

		xtype : 'mysimplevmultitextcolumn',
		'header' : '产品信息',
		align : 'center',
		
		minWidth : 300,
		
		stateId : 'proInfo',
		
		textActions : [{

			text : '{fpdtname}'

		}, {

			text : '{fpdtspec}'

		}],
		sortable : true,

		flex : 1

			// width : 230
			}, {
				xtype : 'mysimplevmultitextcolumn',
				'header' : '数量',
				align : 'center',
				
				stateId : 'proNo',
				
				textActions : [{

					text : '{famount}只'

				}, {

					text : '(实送{foutqty}只)'
					,
					condition : 'foutqty != 0'
					
				}],
				width : 150,
				sortable : true

			}, {
				xtype : 'mysimplevmultitextcolumn',
				'header' : '交期/地址',
				align : 'center',
				
				stateId : 'dateOrAddress',
				
				textActions : [{

					text : '{farrivetime}'

				}, {

					text : '{faddress}'

				}],
				width : 200,
				sortable : true

			}, {
				header : '状态',
				align : 'center',
				dataIndex : 'fstate',
				renderer : function(val) {
					if (val == 0) {
						return '未接收';
					} else if (val == 1 || val == 2 || val == 3) {
						return '已接收';
					} else if (val == 4) {
						return '已入库';
					} else if (val == 5) {
						return '部分发货';
					} else {
						return '全部发货';
					}
				}
			}, {

				header : '制造商',
				align : 'center',
				dataIndex : 'fsupplier',
				width : 100
			}, {

				header : '采购订单号',
				align : 'center',
				dataIndex : 'fordernumber',
			    hidden: true

			}, {

				header : '申请单号',
				align : 'center',
				dataIndex : 'fnumber'

			}, {

				header : '下单时间',
				align : 'center',
				dataIndex : 'fcreatetime',
				width : 180,
				hidden: true
			},{

				header : '备注',
				align : 'center',
				dataIndex : 'fdescription'
			}, {
				
				stateId : 'actions',
				header : '操作',
				xtype : 'mysimplevmultitextactioncolumn',
				align : 'center',
				showCondition : 'fstate==0',

				textActions : [{
					// 文字
					text : '删除',
					// condtion : '',
					// 行为
					action : 'DJ.quickOrder.orderInfo.OrdInfoList.deleteCus'

				}, {	// 文字
					text : '修改',
					// condtion : '',
					// 行为
					action : 'DJ.quickOrder.orderInfo.OrdInfoList.editCus'
				}]

			}

	],
	selModel : Ext.create('Ext.selection.CheckboxModel')
});