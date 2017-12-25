Ext.apply(Ext.QuickTips.getQuickTip(), {
	dismissDelay : 0
});

Ext.require("DJ.tools.common.MyCommonToolsZ");
Ext.require("Ext.ux.form.MyDateTimeSearchBox");
Ext.require(["DJ.tools.fieldRel.MyFieldRelTools",
		"Ext.ux.grid.MySimpleGridContextMenu"]);

Ext.define('DJ.order.Deliver.DeliversOrderBoardList.DeliversOrderBoardList', {
	extend : 'Ext.c.GridPanel',

	alias : 'widget.deliversorderboardlist',

	title : "纸板要货管理",
	id : 'DJ.order.Deliver.DeliversOrderBoardList.DeliversOrderBoardList',

	pageSize : 50,
//	closable : true,// 是否现实关闭按钮,默认为false

	url : 'gainCardboardDeliversOrdersMV.do',

	// Delurl : "DelBoardDeliverapplyList.do",
	// EditUI : "DJ.order.Deliver.batchBoardDeliverapplyEdit",
	//			
	// exporturl:"deliverapplytoExcel.do",//导出为EXCEL方法

	mixins : ['DJ.tools.grid.MySimpleGridMixer',
			'DJ.tools.grid.mixer.MyGridSearchMixer'],

	statics : {
		DELIVERS_STATE : ["已创建", "已接收", "已分配", "已生产", "部分发货", "全部发货"]
	},
	onload : function() {

		var me = this;

		me._hideButtons(['addbutton', 'viewbutton', 'editbutton', 'delbutton',
				'exportbutton']);

	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件

	},
	Action_BeforeEditButtonClick : function(EditUI) {
	},
	Action_EditButtonClick : function(c1) {
	},
	Action_AfterEditButtonClick : function(EditUI) {
	},
	Action_BeforeDelButtonClick : function(me, record) {
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	//2015-06-27  在 MyGridSearchMixer重写doBeforeGridSearchAction方法，当输入查询值与正则匹配
	//那么Defaultfilter中改变condictionFields数组中的CompareType
	doBeforeGridSearchAction: function(filters, maskA,indexAll){
		var me = this,
		store = me.getStore(),
		query = Ext.String.trim(me.down('mymixedsearchbox').down("textfield").getValue());
		store.getProxy().extraParams = {};
		if(/^\d+\.?\d*\*\d+\.?\d*(\*\d+\.?\d*)?$/.test(query)){
			query = query.replace(/\*/g,'___=');
			 var arrcon = me.down('mymixedsearchbox').condictionFields;			 
			 Ext.each(filters, function(ele, index, all) {
			 	if (Ext.Array.contains(arrcon, ele.myfilterfield)) {
			 		ele.CompareType = 'not like';
			 	}
			 });
			 query = Ext.encode(query);
			 store.getProxy().extraParams.query = query.substring(1,query.length-1);	
		}		
	},
	custbar : [{

		text : '接收',
		handler : function() {
			Ext.djRequest('receiveBoardOrders.do',this,{
				fstate: '0'
			});
		}

	}, {

		text : '取消接收',
		handler : function() {
			Ext.djRequest('unReceiveBoardOrders.do',this,{
				fstate: '3'
			});
		}

	}/*, {

		text : '下单',
		handler : function() {
			
			MyCommonToolsZ.reqAction('placeCarboardOrder.do', this.up('grid'),
					'fid', 0, true);
		}

	}*/, {
		xtype : 'myexcelexportercuscfgbutton'
	},

	{

		xtype : 'mysimplesearchercombobox',
		filterMode : true,
		fields : [

		{

			displayName : '已创建',
			trueValue : '0',
			field : 'fstate'

		}, {

			displayName : '已接收',
			trueValue : '1',
			field : 'fstate'
		}, {

			displayName : '已排产',
			trueValue : '2',
			field : 'fstate'

		}, {

			displayName : '已入库',
			trueValue : '4',
			field : 'fstate'
		}, {

			displayName : '部分发货',
			trueValue : '5',
			field : 'fstate'

		}, {

			displayName : '全部发货',
			trueValue : '6',
			field : 'fstate'
		}, {

			displayName : '作废',
			trueValue : '8',
			field : 'fstate'
		}

		]

	}, {
		xtype : 'mymixedsearchbox',

		filterMode : true,
		useDefaultfilter : true,
		condictionFields : ['fnumber','fplanNumber', '_custname', '_suppliername','fseries'],
		beforeSearchActionForStore : function(defaultFilers){
			Ext.each(defaultFilers,function(ele,index,all){
			
				if (ele.myfilterfield == 'fseries'){
					
					ele.CompareType = '=';
						
				}
				
			});
		}
	},
	 {

		xtype : 'mydatetimesearchbox',
		filterMode : true,
		useDefaultfilter : true,
		labelFtext : '创建时间',
		conditionDateField : 'fcreatetime'

	},{
		
		text:'作废',
		handler:function(){
			var me = this;
			var record = this.up('grid').getSelectionModel().getSelection();
			if(record.length!=1){
				Ext.Msg.alert('提示','请选择一条数据！');
				return;
			}
			if(record[0].get('fstate')==0&&Ext.isEmpty(record[0].get('fplanNumber'))){
				Ext.Msg.show({
				     title:'提示',
				     msg: '是否作废？',
				     buttons: Ext.Msg.YESNO,
				     icon: Ext.Msg.QUESTION,
				     fn:function(btn){
				     	console.log(btn);
				     	if(btn=="yes"){
				     		Ext.Msg.prompt('异常记录对话框', '', function(btn, text){
							    if (btn == 'ok'){
							    	if(Ext.isEmpty(text)){
							    		Ext.Msg.alert('提示','请填写信息！');
							    		return;
							    	}
							        Ext.Ajax.request({
							        	url:'updateCarboardDescription.do',
							        	params:{'fid':record[0].get('fid'),'description':text},
							        	success:function(response){
							        		var obj = Ext.decode(response.responseText);
							        		if(obj.success==true){
							        			djsuccessmsg(obj.msg);
							        			me.up('grid').store.load();
							        		}else{
							        			Ext.Msg.alert('提示',obj.msg);
							        		}
							        	}
							        })
							    }
							},this,true);
				     	}
				     }
				});
				
			}else{
				Ext.Msg.alert('提示','必须是已创建状态且订单编号为空的记录！');
				return;
				
			}
		}
	
	}
	, {

		xtype : 'mydatetimesearchbox',
		filterMode : true,
		useDefaultfilter : true,
		labelFtext : '配送时间',
		conditionDateField : 'farrivetime'

	}
	// , {
	//
	// xtype : 'combo',
	// itemId : 'statecombo',
	// queryMode : 'local',
	// store : [["", "全部"], ["0", "已创建"], ["1", "已接收"], ["3", "已分配"],
	// ["2", "已生产"], ["5", "部分发货"], ["6", "全部发货"]],
	// listeners : {
	// select : function() {
	// this.up('grid').relevanceByName();
	// this.up('grid').getStore().loadPage(1);
	//
	// },
	// change : function() {
	// this.up('grid').relevanceByName();
	// this.up('grid').getStore().loadPage(1);
	// }
	// }
	// }, '|',

	// {
	//
	// xtype : 'textfield',
	// itemId : 'textfield',
	// width : 120,
	// id : 'findBy',
	// emptyText : '回车搜索',
	// enableKeyEvents : true,
	// listeners : {
	// keypress : function(me, e) {
	// if (e.getKey() == 13) {
	// this.up('grid').relevanceByName();
	// this.up('grid').getStore().loadPage(1);
	// }
	// }
	// }
	// }, "|", {
	// xtype : "mydatetimesearchbox",
	// conditionDateField : "d.farrivetime",
	// labelFtext : "配送时间",
	// useDefaultfilter : true,
	// additionalCondition : true,
	// beforeDefaultSearchAction : function(store) {
	// this.up('grid').relevanceByName();
	// return true;// false阻止执行
	// }
	// }, {
	// xtype : "mydatetimesearchbox",
	// conditionDateField : "d.fcreatetime",
	// labelFtext : "创建时间",
	// useDefaultfilter : true,
	// additionalCondition : true,
	// beforeDefaultSearchAction : function(store) {
	// this.up('grid').relevanceByName();
	// return true;// false阻止执行
	// }
	// }
	],
	fields : [
			{
				name : 'fid'
			},
			{
				name : 'faddress'
			// ,
			// myfilterfield : 'd.faddress',
			// myfiltername : '配送地址',
			// myfilterable : true
			}, {
				name : 'fnumber',
				myfilterfield : 'fnumber',
				myfiltername : '申请单号',
				myfilterable : true,
				type : "String",
				sortDir : "DESC"
			}, {
				name : 'fcustomerid'
			}, {
				name : '_custname',
				myfilterfield : '_custname',
				myfiltername : '客户名称',
				myfilterable : true
			}, {
				name : 'flinkman'

			}, {
				name : 'flinkphone'

			}, {
				name : 'famount'
			}, {
				name : 'farrivetime'

			}, {
				name : 'fcreator'
			}, {
				name : 'fcreatetime'
			}, {
				name : 'fstate',

				myfilterfield : 'fstate',
				myfiltername : '订单状态',
				myfilterable : true

			}, {
				name : '_suppliername',
				myfilterfield : '_suppliername',
				myfiltername : '制造商',
				myfilterable : true
			}, {
				name : 'stateinfo'
			}, 'fcreatetime', 'foutQty', 'fmaterialname', 'fboxmodel', {

				name : 'fboxlength',
				myfilterfield : 'fboxlength',
				myfiltername : '长-纸箱规格',
				myfilterable : true

			}, {

				name : 'fboxwidth',
				myfilterfield : 'fboxlength',
				myfiltername : '宽-纸箱规格',
				myfilterable : true

			}, {

				name : 'fboxheight',
				myfilterfield : 'fboxlength',
				myfiltername : '高-纸箱规格',
				myfilterable : true

			}, {

				name : 'fmateriallength',
				myfilterfield : 'fmateriallength',
				myfiltername : '总长-下料规格',
				myfilterable : true

			}, {

				name : 'fmaterialwidth',
				myfilterfield : 'fmaterialwidth',
				myfiltername : '总高-下料规格',
				myfilterable : true

			},{
				name: 'fdescription'
			},{
				name: 'fstockinqty'
			},

			'fstavetype', 'fvformula', 'fhformula', 'famountpiece',
			'foutQtypiece', {
				name : 'fseries',
				myfilterfield : 'fseries',
				myfiltername : '成型方式',
				myfilterable : true

			}, 'fouttime', 'frecipient',

			'freceiptTime', {
				name : 'fplanNumber',
				myfilterfield : 'fplanNumber',
				myfiltername : '订单编号',
				myfilterable : true

			},'fwerkname'

	],
	columns : {
		items : [Ext.create('DJ.Base.Grid.GridRowNum', {
			header : '序号'
		}), {
			'header' : 'fid',
			'dataIndex' : 'fid',
			hidden : true,
			hideable : false,
			sortable : true
		}, {
			'header' : 'fcustomerid',
			'dataIndex' : 'fcustomerid',
			hidden : true,
			hideable : false,
			sortable : true
		}, {
			'header' : '申请单号',
			width : 100,
			'dataIndex' : 'fnumber',
			sortable : true
		}, {
			'header' : '订单编号',
			width : 100,
			'dataIndex' : 'fplanNumber',
			sortable : true
		},

		{
			'header' : '客户名称',
			width : 140,
			'dataIndex' : '_custname',
			sortable : true
		}, {
			'header' : '制造商',
			width : 80,
			'dataIndex' : '_suppliername',
			sortable : true
		}, {
			'header' : '订单状态',
			'dataIndex' : 'fstate',
			width : 60,
			sortable : true,
			renderer : function(value) {
				if (value === '0') {
					return '已创建';
				} else if (value == 1 || value==2 || value==3) {
					return '已排产';
				}else if (value == 4) {
					return '已入库';
				}  else if (value == 5) {
					return '部分发货';
				} else if (value == 6) {
					return '全部发货';
				} else if (value == 7) {
					return '暂存';
				}else if(value==8){
					return '作废';
				} else {
					return '';
				}
			}
		}, {
			'header' : '材料',
			width : 100,
			'dataIndex' : 'fmaterialname',
			sortable : true
		}, {
			'header' : '纸箱规格(CM)',
			columns : [{
				'header' : '长',
				'dataIndex' : 'fboxlength',
				width : 50,
				sortable : true
			}, {
				'header' : '宽',
				'dataIndex' : 'fboxwidth',
				width : 50,
				sortable : true
			}, {
				'header' : '高',
				'dataIndex' : 'fboxheight',
				width : 50,
				sortable : true
			}]

		}, {
			'header' : '下料规格(CM)',
			columns : [{
				'header' : '总长',
				'dataIndex' : 'fmateriallength',
				width : 60,
				sortable : true
			}, {
				'header' : '总高',
				'dataIndex' : 'fmaterialwidth',
				width : 60,
				sortable : true
			}]
		}, {
			'header' : '箱型',
			width : 80,
			'dataIndex' : 'fboxmodel',
			sortable : true,
			renderer : function(value) {
				if (value == 1) {
					return '普通';
				}else if(value == 2){
					return '全包';
				}else if(value == 3){
					return '半包';
			    }else if(value == 4){
					return '有底无盖';
			    }else if(value == 5){
					return '有盖无底';
			    }else if(value == 6){
					return '围框';
			    }else if(value == 7){
					return '天地盖';
			    }else if(value == 8){
					return '立体箱';
			    }else if(value == 0){
			    	return '其它';
			    }
			}
		}, {
			'header' : '压线方式',
			'dataIndex' : 'fstavetype',
			width : 60,
			sortable : true
				// renderer : function(value) {
				// if (value == 1) {
				// return '纵向内压';
				// }else if(value == 2){
				// return '纵向平压';
				// }else if(value == 3){
				// return '纵向外压';
				// }else if(value == 4){
				// return '横压';
				// }else {
				// return '不压线';
				// }
				// }
				}, {
					'header' : '压线公式',
					columns : [{
						'header' : '纵向公式',
						'dataIndex' : 'fvformula',
						width : 100,
						sortable : true
					}, {
						'header' : '横向公式',
						'dataIndex' : 'fhformula',
						width : 100,
						sortable : true
					}]
				}, {
					'header' : '成型方式',
					'dataIndex' : 'fseries',
					width : 60,
					sortable : true
				// renderer : function(value) {
				// if (value == 1) {
				// return '一片';
				// }else if(value == 2){
				// return '二片';
				// }else if(value == 4){
				// return '四片';
				// }else {
				// return '';
				// }
				// }
				}, {
					'header' : '配送数量',
					columns : [{
						'header' : '只',
						'dataIndex' : 'famount',
						width : 50,
						sortable : true
					}, {
						'header' : '片',
						'dataIndex' : 'famountpiece',
						width : 50,
						sortable : true
					}]
				}, {
					'header' : '实际配送数量(片)',
					// columns: [{
					// 'header' : '只',
					width : 120,
					'dataIndex' : 'foutQty',
					sortable : true
				// },{
				// 'header' : '片',
				// 'dataIndex' : 'foutQtypiece',
				// width : 50,
				// sortable : true
				// }]
				}, {
					'header' : '配送时间',
					'dataIndex' : 'farrivetime',
					width : 140,
					sortable : true
				}, {
					'header' : '实际配送时间',
					'dataIndex' : 'fouttime',
					width : 140,
					sortable : true
				}, {
					'header' : '配送地址',
					'dataIndex' : 'faddress',
					width : 300,
					sortable : true
				}, {
					'header' : '特殊要求',
					'dataIndex' : 'fdescription',
					width : 140,
					sortable : true
				}, {
					'header' : '入库数量/片',
					'dataIndex' : 'fstockinqty',
					width : 60,
					sortable : true
				}, {
					'header' : '联系人',
					'dataIndex' : 'flinkman',
					width : 60,
					sortable : true
				}, {
					'header' : '联系电话',
					'dataIndex' : 'flinkphone',
					sortable : true
				}, {
					'header' : '创建时间',
					'dataIndex' : 'fcreatetime',
					width : 140,
					sortable : true
				}, {
					'header' : '接收人',
					'dataIndex' : 'frecipient',
					width : 140,
					sortable : true
				}, {
					'header' : '接收时间',
					'dataIndex' : 'freceiptTime',
					width : 140,
					sortable : true
				},{
					text:'备注',
					dataIndex:'fwerkname',
					width:140,
					sortable : true
				}

		],

		defaults : {
			renderer : DJ.tools.fieldRel.MyFieldRelTools.showEmptyWhenNullNoZERO
		}
	},
	selModel : Ext.create('Ext.selection.CheckboxModel')
})