Ext.define('DJ.quickOrder.orderInfo.PrepareGoodsList', {
	extend : 'DJ.myComponent.grid.MySimpleConciseGrid',
	id : 'DJ.quickOrder.orderInfo.PrepareGoodsList',
	url : 'getQuickMystockList.do',
	Delurl : "delQuickMystock.do",
	EditUI : "DJ.quickOrder.orderInfo.PrepareGoodsEditor",
	// exporturl:"outWarehousetoexcel.do",
	pagingtoolbarDock : 'bottom',
	mixins : ['DJ.tools.grid.MySimpleGridMixer',
			'DJ.tools.grid.mixer.MyGridSearchMixer'],
			
//	closeAction : 'destroy',
			
	stateful : true,
			
	statics : {
		
		showImgPreview : function(ev) {

			var grid = Ext.getCmp("DJ.quickOrder.orderInfo.PrepareGoodsList");

			var records = MyCommonToolsZ.pickSelectItems(grid);

			if (records == -1) {

				return;

			}

			var fid = records[0].get("fcustproductid");

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
			var grid = Ext.getCmp('DJ.quickOrder.orderInfo.PrepareGoodsList');
			grid.doDeleteAction(grid);

		},
		editCus : function() {
			var grid = Ext.getCmp('DJ.quickOrder.orderInfo.PrepareGoodsList');
			grid.doEditAction(grid);

		}

	},
	showSearchAllBtn : false,
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
			filterValue : '1,2'
		}]

	}, {

		xtype : 'tbspacer',
		flex : 1

	}, {

		xtype : 'mymixedsearchbox',
		condictionFields : ['c.fname', 'c.fspec', 's.fname', 'm.fnumber',
				'm.fpcmordernumber'],
		tip : '请输入“产品名称”“规格”“制造商”“申请单号”“采购订单号”',
		useDefaultfilter : true,
		filterMode : true,
		beforeSearchActionForStore : function(myfilter, maskstrings,
				condictionValue) {
			Ext.each(myfilter,function(ele){
				if(ele.myfilterfield=='c.fspec'){
					ele.value = ele.value.replace(/\*/g,'_').replace(/\X/g,'_').replace(/\x/g,'_');
				}
			})			
		}
	}, {

		xtype : 'button',
		text : '筛选条件',
		menu : {
			xtype : 'menu',
			items : [{

				xtype : 'mydatetimesearchbox',
				filterMode : true,
				useDefaultfilter : true,
				labelFtext : '首次发货',
				conditionDateField : 'm.ffinishtime',

				// 开始默认时间
				startDate : Ext.Date.subtract(new Date(), Ext.Date.MONTH, 2)

				//	// 结束默认时间
				//	endDate : null,
				
			}, {

				xtype : 'mydatetimesearchbox',
				filterMode : true,
				useDefaultfilter : true,
				labelFtext : '备货周期',
				conditionDateField : 'm.fconsumetime'

			}]
		}

	},
			// {
			// xtype : 'button',
			// text : '导出',
			//
			// handler : function() {
			//
			// }
			// }
			//	
			// ,
			{

				xtype : 'myexcelexportercuscfgbutton',
				mode : 1,
				excelTitle : '备货信息',
				
				text : '导出', // 导出
				iconCls : '',
				
				changeMode : function(mode, grid) {

					var records = grid.getSelectionModel().getSelection();

					return records.length == 0 ? 3 : 1;

				},
				
				columns : [{
					text : '产品名称',
					dataIndex : 'custname'
				}, {
					text : '规格',
					dataIndex : 'fspec',
					width:120
				}, {
					text : '计划数量',
					width:60,
					dataIndex : 'fplanamount',
					renderer : function(v) {
						return (Ext.isEmpty(v) ? "0" : v) + "只";
					}
				}, {
					text : '首次发货',
					dataIndex : 'ffinishtime',
						width:100,
					renderer : function(v) {
						return Ext.Date.format(Ext.Date.parse(v, "Y-m-d"), 'Y/m/d');
					}
				}, {
					text : '备货周期',
						width:100,
					dataIndex : 'fconsumetime',
					renderer : function(v) {
						return Ext.Date.format(Ext.Date.parse(v, "Y-m-d"), 'Y/m/d');
					}
				}, {
					text : '制造商',
					dataIndex : 'suppliername'
				}, {
					text : '状态',
					dataIndex : 'fstate',
					width:50,
					renderer : function(v) {
						var cfgObjs = ['未接收', '已接收', '已接收'];
						return cfgObjs[v];
					}
				}, {
					text : '采购订单号',
					width:120,
					dataIndex : 'fpcmordernumber',
					hidden: true
				}, {
					text : '备货单号',
					width:120,
					dataIndex : 'fnumber',
					hidden: true
				}, {
					text : '备注',
					dataIndex : 'fremark'
				}]

			}

	],

	onload : function() {
		// 加载后事件，可以设置按钮，控件值等

		this.magnifier = Ext.create('Ext.ux.form.Magnifier');
		
//		var me = this;
//
//		MyCommonToolsZ.setComAfterrender(me, function() {
//
//			setTimeout(function() {
//				var btnSC = me.down('button[text=筛选条件]');
//				btnSC.showMenu();
//
//				me.doGridSearchAction();
//
//				btnSC.hideMenu();
//
//			}, 500);
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
		name : 'custname'
	}, {
		name : 'fspec'
	}, {
		name : 'fcustproductid'
	}, {
		name : 'fplanamount',
		type : 'int'
	}, {
		name : 'ffinishtime'
	}, {name:'fconsumetime'},'suppliername', 'fpcmordernumber', 'fnumber','fremark',
	{
		name : 'fstate',
		type : 'int'
	}
	],
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		xtype : 'mysimpleimagecolumn',
		imgUrlField : 'getFileSourceByParentId.do?fid={fcustproductid}',
		imgClickAble : false,
		
		onclick : 'DJ.quickOrder.orderInfo.PrepareGoodsList.showImgPreview(event)',
		
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
		stateId : 'proInfo',
		
		minWidth : 300,
		
		flex : 1,
		textActions : [{

			text : '{custname}'

		}, {

			text : '{fspec}'

		}],
		sortable : true

	}, {
		xtype : 'mysimplevmultitextcolumn',
		align : 'center',
		'header' : '计划数量',
		stateId : 'fplanamount',
		textActions : [{
			text : '{fplanamount}只'
		}],
		width : 150,
		sortable : true

	}, {
		xtype : 'mysimplevmultitextcolumn',
		// 'header' : '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;首次发货/备货周期',
		'header' : '首次发货/备货周期',
		align : 'center',
		stateId : 'ffinishtime',
		textActions : [{

			text : '{ffinishtime}'

		}, {

			text : '{fconsumetime}'

		}],
		width : 160,
		sortable : true

	},  {

		header : '状态',
		align : 'center',
		dataIndex : 'fstate',

		renderer : function(v, a, record) {

			var cfgObjs = ['未接收', '已接收', '已接收'];

			return cfgObjs[v];
		}
		
	},{

		header : '制造商',
		align : 'center',
		dataIndex : 'suppliername',
		width : 180
	}, {
		header : '采购订单号',
		align : 'center',
		dataIndex : 'fpcmordernumber',
		width : 150,
		hidden: true
	}, {

		header : '备货单号',
		align : 'center',
		dataIndex : 'fnumber',
		width : 150
	},{
		header : '备注',
		align : 'center',
		dataIndex : 'fremark',
		width : 150
	},{


		header : '操作',
		align : 'center',
		xtype : 'mysimplevmultitextactioncolumn',
		stateId : 'action',
		showCondition : 'fstate==0',

		textActions : [{

			// 文字
			text : '删除',
			// condtion : '',
			// 行为
			action : 'DJ.quickOrder.orderInfo.PrepareGoodsList.deleteCus'

		}, {	// 文字
			text : '修改',
			// condtion : '',
			// 行为
			action : 'DJ.quickOrder.orderInfo.PrepareGoodsList.editCus'
		}]

	}

	],
	selModel : Ext.create('Ext.selection.CheckboxModel')
});