Ext.apply(Ext.QuickTips.getQuickTip(),{
	dismissDelay : 0
}); 
var stateStore = Ext.create('Ext.data.Store',{
	fields:['value','state'],
	data:[
	      {value:'0',state:'未接收'},
	      {value:'1',state:'已接收'},
	      {value:'2',state:'已接收'},
	      {value:'3',state:'已接收'},
//	      {value:'4',state:'已装配'},
	      {value:'5',state:'部分发货'},
	      {value:'6',state:'全部发货'},
	      {value:'',state:'全部'}
	]
})

Ext.require("Ext.ux.grid.print.MySimpleGridPrinterComponent");
Ext.require("DJ.tools.common.MyCommonToolsZ");
Ext.require("Ext.ux.form.MySearchBox");
Ext.require("Ext.ux.form.MyMixedSearchBox");
Ext.require("Ext.ux.form.MyDateTimeSearchBox");
Ext.require(["DJ.tools.fieldRel.MyFieldRelTools",
		"DJ.myComponent.button.UpLoadDeliverApplyWinButton",
		"Ext.ux.grid.MySimpleGridContextMenu"]);

Ext.define('DJ.order.Deliver.DeliversCustList', {
			extend : 'Ext.c.GridPanel',
			
			alias : 'widget.deliverscustlist',
			
			title : "循环订单",
			id : 'DJ.order.Deliver.DeliversCustList',
			pageSize : 50,
			closable : false,// 是否现实关闭按钮,默认为false
			url : 'selectDeliverapplyCustsMV.do',
			Delurl : "DelDeliverapplyList.do",
			EditUI : "DJ.order.Deliver.DeliversCustEdit",
			exporturl:"CustdeliverapplytoExcel.do",//导出为EXCEL方法
			
			mixins : ['DJ.tools.grid.mixer.MyGridSearchMixer'],
						
			//配套配置，todo，更简洁的配置
			stateful : true,
			
//			stateEvents : ['blur'],
			
//			stateId : 'DJ.order.Deliver.DeliversCustList',
						
			
			statics : {
//				DELIVERS_STATE : ["要货创建", "已接收", "已下单", "已分配", "已装配","部分发货", "已发货"]
				DELIVERS_STATE : ["未接收", "已接收", "已接收", "已接收", "已入库","部分发货", "全部发货"]
			},
			listeners: {
				itemclick: function(me,record,item, index, e){
					this.magnifier.loadImages({
						fid: record.get('fcusproductid')
					});
					var coord = e.getXY();
					this.magnifier.showAt({
		    			left: coord[0] + 80,
		    			top: coord[1] + 5
		    		});
				}
			},
			
			
			plugins : [{
			
				ptype : "mysimplegridcontextmenu",
				useExistingButtons : ["button[text=下单]"]
			
			}],
			onload : function() {
				
				Ext.Ajax.request({
									url:'getCustaccountbalanceInfo.do',
									success:function(response,opts){
										var obj = Ext.decode(response.responseText);
										if(obj.success == true){
//											djsuccessmsg(obj.msg);
//											me.grid.store.load();
											Ext.MessageBox.alert('提示',obj.msg/*,function(btn){alert(btn);}*/);
										}
										
									}
								});
								
				var me = this;
		
				//记住过滤、排序？
				this.getStore().statefulFilters  = true;
				this.store.on('beforeload',function( store, operation, eOpts ){
					if(store.autoLoad==true){
						store.autoLoad = false;
						return false;
					}
				})
				
				MyGridTools.rebuildExcelAction(me); 
				
				MyCommonToolsZ.setComAfterrender(me, function(com) {

					var createDateF = me.down("mydatetimesearchbox[labelFtext=创建时间]");
					
					var  btn = createDateF.down('button[text=查询]');
					
					btn.handler.call(btn);
					
//					var fieldT = com.down("textfield[id=findBy]");
//
//					MyCommonToolsZ.setQuickTip(fieldT.id, "", "可输入:客户名称、申请单号、包装物名称、包装物编号");

				});
				Ext.DomHelper.append(
						document.getElementsByTagName("head")[0],
						'<link rel="stylesheet" type="text/css" href="css/DeliversCustList.css">');
				
				// 隐藏按钮
		var btHideSs = ['button[text=新  增]', 'button[text=批量新增]'
//		,
//				'button[id=DJ.order.Deliver.DeliversCustList.exportbutton]'
				];

		Ext.each(btHideSs, function(ele, index, all) {

			me.down(ele).hide();
					
				} );
				
		// //同时也刷新图表
		me.getStore().on('load', function() {

			me.previousNode("chart").getStore().load();

		});
				
				
//				Ext.getCmp('DJ.order.Deliver.DeliversCustList.exportbutton').hide();//setVisible(false);
//				Ext.getCmp('DJ.order.Deliver.DeliversCustList.querybutton').hide();
				
				var r = DJ.order.Deliver.DeliversCustList.DELIVERS_STATE;
				var fstate = "";
				for(var i=0;i<r.length;i++){
					fstate += i+'是'+r[i];
					if(i<r.length-1){
						fstate += ","; 
					}
				}
				//"要货申请状态过滤值为数字：0为要货创建,1为已接收,2为已下单,3为已分配,4为已装配,5为已发货"
				Ext.getCmp("DJ.order.Deliver.DeliversCustList.querybutton").setTooltip("要货申请状态过滤值为数字："+fstate);
				this.down('toolbar').down('combo').setValue('');
//				this.down('toolbar').getComponent('combotime').setValue('');
				this.magnifier = Ext.create('Ext.ux.form.Magnifier');
			},
			switchOrderType: function(orderType){
				var me = this,bt;
				switch (orderType) {
					case "single" :
						bt = Ext.getCmp("DJ.order.Deliver.DeliversCustList.addbutton");
						bt.handler.call(bt);
						Ext.getCmp('DJ.order.Deliver.DeliversCustEdit')._relatedPanel = me;
						break;
					case "multi" :
						bt = Ext.getCmp("DJ.order.Deliver.DeliversCustList").down("button[text=批量新增]");
						bt.handler.call(bt);
						Ext.getCmp("timeForArrivetime").setValue(Ext.Date.add(new Date(),Ext.Date.DAY,5));
						break;
					case "file" :
						DJ.myComponent.button.UpLoadDeliverApplyWinButton.handlerC.call();
						break;
				}
			},
			Action_BeforeAddButtonClick : function(EditUI) {
				// 新增界面弹出前事件
			},
			Action_AfterAddButtonClick : function(EditUI) {
				// 新增界面弹出后事件
		
				Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.balance").setReadOnly(true);
		        Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.FNUMBER").setReadOnly(true);
		        Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.fcharacter").setReadOnly(true);
				var customerFiled = Ext
						.getCmp("DJ.order.Deliver.DeliversCustEdit.fcustomerid");
				var addressFiled = Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.faddressid");
		
				// 获取客户
		
				var me = this;
				var el = me.getEl();
				el.mask("系统处理中,请稍候……");
				
				
				Ext.Ajax.request({
					timeout : 60000,
					url : "selectAddressByCustomer.do",
					success : function(response, option) {
		
						var obj = Ext.decode(response.responseText);
						if (obj.success == true) {
							//   djsuccessmsg( obj.msg);
							if (obj.data != undefined) {
								customerFiled.setmyvalue("\"fid\":\""
										+ obj.data[0].customer + "\",\"fname\":\""
										+ obj.data[0].customerName + "\"");
//								if(obj.data[0].address!=undefined)
//								{
//										var form = Ext
//										.getCmp("DJ.order.Deliver.DeliversCustEdit")
//										.getform().getForm();
//									addressFiled.setmyvalue("\"fid\":\""
//										+ obj.data[0].address + "\",\"fname\":\""
//										+ obj.data[0].addressname + "\"");
//									form.findField('faddress')
//										.setValue(obj.data[0].fdetailaddress);// _combo.getValue();
//									form.findField('flinkman').setValue(obj.data[0]
//										.flinkman);// _combo.getValue();
//									form.findField('flinkphone')
//										.setValue(obj.data[0].fphone);// _combo.getValue();
//								}
							}
						} else {
							Ext.MessageBox.alert('错误', obj.msg);
		
						}
						el.unmask();
					}
				});
	},
			Action_BeforeEditButtonClick : function(EditUI) {
				// 修改界面弹出前事件
				var grid = Ext.getCmp("DJ.order.Deliver.DeliversCustList");
				var record = grid.getSelectionModel().getSelection();
				if (record.length != 1) {
					throw "只能选中一条记录进行修改!";
				}
				if(record[0].get("fiscreate")=="1"){
					throw "已生成要货管理不能修改!";
				}
			},
			Action_AfterEditButtonClick : function(EditUI) {
				// 修改界面弹出后事件
				Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.FNUMBER").setReadOnly(true);
				Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.fcharacter").setReadOnly(true);

			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
				var grid = Ext.getCmp("DJ.order.Deliver.DeliversCustList");
				var record = grid.getSelectionModel().getSelection();
				for ( var i = 0; i < record.length; i++) {
					if(record[i].get("fiscreate")=='1'&& record[i].get('ftraitid')==''){					
						 throw "已生成要货管理不能删除!";
						
					}
				}
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			}
			,
			
//			bbar : [ {
//		xtype : "mydatetimesearchbox",
//		conditionDateField : "d.fcreatetime",
//		labelFtext : "创建时间",
//		
//		custFrontInit : function(){
//		
//			var me = this;
//			
//			me.begainDate = '1999-01-15';
//			me.endDate = '2099-01-15';
//			
//		},
//		
//		beforeLoad : function(storeT, begainDate, endDate, myfilter) {
//
//			var me = this;
//			
//			me.begainDate = begainDate;
//			me.endDate = endDate;
//			
//			me.up('grid').relevanceByName();
//			
//			return false;// false阻止执行
//		}
//
//	},
//
//	{
//		xtype : "mydatetimesearchbox",
//		conditionDateField : "d.farrivetime",
//		
//		custFrontInit : function(){
//		
//			var me = this;
//			
//			me.begainDate = '1999-01-15';
//			me.endDate = '2099-01-15';
//			
//		},
//		
//		labelFtext : "配送时间",
//		beforeLoad : function(storeT, begainDate, endDate, myfilter) {
//
//			var me = this;
//			
//			me.begainDate = begainDate;
//			me.endDate = endDate;
//			
//			me.up('grid').relevanceByName();
//			
//			return false;// false阻止执行
//		}
//
//	}
//
//	],
			custbar : [
//				{
//		xtype : 'myexcelexportercuscfgbutton',
//		mode : MyExcelExporterCusCfgButton.PICK_MODE
//	}, ,
					
           
        
		
	//ie里打印组件有点问题，故用匿名函数表达式进行判断
	(function(){
	
	if(Ext.isIE) {
	
		return {
		xtype : 'label',
		width : 0
	};
		
		
	} else {
	
		return {
			xtype : 'mysimplegridprintercomponent',
			mode:'1'
	};
		
	}
		
	})()
	
	,{
		text : '下单',
		handler : function() {
			var button = this;
			Ext.Ajax.request({
				timeout : 6000,
				url : "gainOrderWayCfg.do",
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						var orderType = obj.data[0].fvalue;
						button.up('grid').switchOrderType(orderType);
						
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}

				}
			});

		}

	},{
				text:'批量新增',
				handler:function(){
					var win = Ext.create('DJ.order.Deliver.batchCustDeliverapplyEdit');
					win.setparent('DJ.order.Deliver.DeliversCustList');
					win._relatedPanel = this.up('grid');
					win.seteditstate('edit');
					win.show();
				}
			},
//				{
//				xtype:'combo',
//				width:80,
//				itemId : 'combotext',
//				displayField:'state',
//				valueField:'value',
//				queryMode:'local',
//				store:stateStore,
//				
//				_filteByState : function () {
//				
//						var grid = Ext.getCmp("DJ.order.Deliver.DeliversCustList");
//						var store = this.up('grid').getStore();
//						store.setDefaultfilter([{
//							myfilterfield : "d.fstate",
//							CompareType : " like ",
//							type : "string",
//							value : this.getValue()
//						}]);
//						store.setDefaultmaskstring(" #0 ");
//						store.loadPage(1);
//					
//				},
//				
//				listeners : {
//			select : function() {
////				this._filteByState();
////				var grid = Ext.getCmp("DJ.order.Deliver.DeliversCustList");
//				this.up('grid').relevanceByName();
//				
//			},
//			change : function() {
////				this._filteByState();
////				var grid = Ext.getCmp("DJ.order.Deliver.DeliversCustList");
//				this.up('grid').relevanceByName();
//			}
//		}
//			} 			,
			
			{

				xtype : 'mysimplesearchercombobox',
				filterMode : true,
				width : 80,
				fields : [

				{

					displayName : '未接收',
					trueValue : '0',
					field : 'fstate'

				}, 
				{

					displayName : '已接收',
					trueValue : '1,2,3',
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
				}

				]

			},
			
			'|',
			
//			{
//				
//		xtype : 'textfield',
//		itemId : 'textfield',
//		width:80,
//		id : 'findBy',
//		emptyText : '回车搜索',
//		enableKeyEvents:true,
//		
//		listeners:{
//			keypress:function(me,e){
//				if(e.getKey()==13){
////					var val = this.getValue();
////					var valforcomb = this.previousNode("combobox").getValue();
////					
////					var store = this.up('grid').getStore();
////					store.setDefaultfilter([{
////							myfilterfield : "d.fstate",
////							CompareType : " like ",
////							type : "string",
////							value : valforcomb
////						},{
////							myfilterfield : "cpdt.fname",
////							CompareType : " like ",
////							type : "string",
////							value : val
////						},{
////							myfilterfield : "cpdt.fnumber",
////							CompareType : " like ",
////							type : "string",
////							value : val
////						},{
////							myfilterfield : "c.fname",
////							CompareType : " like ",
////							type : "string",
////							value : val
////						} ]);
////						store.setDefaultmaskstring("#0 and (#1 or #2 or #3)");
////					store.loadPage(1);
//					
//					//var grid = Ext.getCmp("DJ.order.Deliver.DeliversCustList");
//					this.up('grid').relevanceByName();
//				}
//			}
//		}
//				
////				xtype : 'mymixedsearchbox',
////				condictionFields:['c.fname','cpdt.fname','cpdt.fnumber'],
////				tip:'可输入:客户名称、包装物编号、包装物名称',
////					useDefaultfilter : true
//		
////		xtype : 'mysearchbox',
////		useDefaultfilter : true
////		comboCfg : {// 前面combo的配置
////
////			store : [['cpdt.fname', '包装物名称'], ['cpdt.fnumber', '包装物编号']],// 必须
////			value : 'cpdt.fname'// 建议设置
////		},
////		textfieldCfg : {// 后面文本域的配置
////			
////			remoteFilter : true
////
////		}
//			},
			{

				xtype : 'mymixedsearchbox',
				width : 100,
				filterMode : true,
				useDefaultfilter : true,
				condictionFields : ['_custpdtname', '_custpdtnumber', '_custname',
						'fnumber'],

				tip : '可输入:客户名称、申请单号、包装物名称、包装物编号',
				useDefaultfilter : true

			},
			"|", {
		xtype : "mydatetimesearchbox",
		conditionDateField : "d.fcreatetime",
		labelFtext : "创建时间",
		useDefaultfilter : true,
		filterMode : true,
		
		//开始默认时间
		startDate : Ext.Date.subtract(new Date(), Ext.Date.MONTH, 2),
	
		//结束默认时间
		endDate : new Date()
		
//		custFrontInit : function(){
//		
//			var me = this;
//			
//			me.begainDate = '1999-01-15';
//			me.endDate = '2099-01-15';
//			
//		},
		
//		beforeLoad : function(storeT, begainDate, endDate, myfilter) {
//
//			var me = this;
//			
//			me.begainDate = begainDate;
//			me.endDate = endDate;
//			
//			me.up('grid').relevanceByName();
//			
//			return false;// false阻止执行
//		}

	},

	{
		xtype : "mydatetimesearchbox",
		conditionDateField : "farrivetime",
		useDefaultfilter : true,
		filterMode : true,
//		custFrontInit : function(){
//		
//			var me = this;
//			
//			me.begainDate = '1999-01-15';
//			me.endDate = '2099-01-15';
//			
//		},
		
		labelFtext : "配送时间"
		
//		beforeLoad : function(storeT, begainDate, endDate, myfilter) {
//
//			var me = this;
//			
//			me.begainDate = begainDate;
//			me.endDate = endDate;
//			
//			me.up('grid').relevanceByName();
//			
//			return false;// false阻止执行
//		}

	}

	
//		,
//			{
//		xtype:'combo',
//		fieldLabel : "创建时间",
//		labelWidth:60,
//		//width:150,
//				itemId : 'combotime',
//				displayField:'stime',
//				valueField:'value',
//				queryMode:'local',
//				store:Ext.create('Ext.data.Store',{
//				fields:['value','stime'],
//				data:[
//					  {value:'',stime:'全部'},
//				      {value:'to_days(now())-to_days(d.fcreatetime)=1',stime:'昨天'},
//				      {value:'to_days(d.fcreatetime)=to_days(now())',stime:'今天'},
//				      {value:'yearweek(d.fcreatetime) = yearweek(now())-1',stime:'上周'},
//				      {value:'yearweek(d.fcreatetime) = yearweek(now())',stime:'本周'},
//				      {value:'CONCAT(YEAR(d.fcreatetime),MONTH(d.fcreatetime))=CONCAT(YEAR(date_sub(curdate(), interval 1 month)),MONTH(date_sub(curdate(), interval 1 month))) ',stime:'上月'},
//				      {value:'CONCAT(YEAR(d.fcreatetime),MONTH(d.fcreatetime))=CONCAT(YEAR(curdate()),MONTH(curdate())) ',stime:'本月'}
//				]
//				}),		
//				_filteByState : function () {
//				
//						var grid = Ext.getCmp("DJ.order.Deliver.DeliversCustList");
//						var store = this.up('grid').getStore();
//						
//						store.setDefaultfilter([{
//							myfilterfield : "d.fcreatetime",
//							CompareType :" is not null "+(this.getValue()==''? "":" and " +this.getValue()),
//							type : "int",
//							value :0
//						}]);
//						store.setDefaultmaskstring(" #0 ");
//						store.loadPage(1);			
//				},
//				
//				listeners : {
//			select : function() {
////				this._filteByState();
//				var grid = Ext.getCmp("DJ.order.Deliver.DeliversCustList");
//				this.up('grid').relevanceByName();
//				
//			},
//			change : function() {
////				this._filteByState();
//				var grid = Ext.getCmp("DJ.order.Deliver.DeliversCustList");
//				this.up('grid').relevanceByName();
//			}
//		}
//		},
//		"|"
//	,{
//		xtype : "mydatetimesearchbox",
//		conditionDateField : "d.farrivetime",
//		labelFtext : "配送时间"
//		
//	},{
//		xtype : "mydatetimesearchbox",
//		conditionDateField : "d.fcreatetime",
//		labelFtext : "创建时间"
//		
//	}
			]
//			,			relevanceByName : function() {
//				
//				var me = this;
//				
//		var store = this.getStore();
//		var valText = this.down('toolbar').getComponent('textfield').getValue();
//		var valCombo = this.down('toolbar').getComponent('combotext')
//				.getValue();
////		var valCTime = this.down('toolbar').getComponent('combotime')
////				.getValue();
//				
////		var createDateFieldF = me.down('mydatetimesearchbox[labelFtext=创建时间]');
////		
////		var deliveryDateFieldF = me.down('mydatetimesearchbox[labelFtext=配送时间]');
//				
//		var filters = [{
//
//			myfilterfield : "d.fstate",
//			CompareType : " like ",
//			type : "string",
//			value : valCombo
//		}, {
//			myfilterfield : "cpdt.fname",
//			CompareType : " like ",
//			type : "string",
//			value : valText
//		}, {
//			myfilterfield : "cpdt.fnumber",
//			CompareType : " like ",
//			type : "string",
//			value : valText
//		}, {
//			myfilterfield : "c.fname",
//			CompareType : " like ",
//			type : "string",
//			value : valText
//		}, {
//			myfilterfield : "d.fnumber",
//			CompareType : " like ",
//			type : "string",
//			value : valText
//		}, 
////			{
////			myfilterfield : "d.fcreatetime",
////			CompareType : " is not null "
////					+ (valCTime == '' ? "" : " and " + valCTime),
////			type : "int",
////			value : 0
////		}, 
////			{
////			myfilterfield : 'd.farrivetime',
////			CompareType : ">=",
////			type : "datetime",
////			value : deliveryDateFieldF.begainDate
////		}, {
////			myfilterfield : 'd.farrivetime',
////			CompareType : "<=",
////			type : "datetime",
////			value : deliveryDateFieldF.endDate
////		}, {
////			myfilterfield : 'd.fcreatetime',
////			CompareType : ">=",
////			type : "datetime",
////			value : createDateFieldF.begainDate
////		}, {
////			myfilterfield : 'd.fcreatetime',
////			CompareType : "<=",
////			type : "datetime",
////			value : createDateFieldF.endDate
////		}
//
//		];
//										
//		store.setDefaultfilter(filters);
//		
//		store.setDefaultmaskstring("#0 and (#1 or #2 or #3 or #4)  ");//and #5 and #6 and #7 and #8 
//		store.loadPage(1);
//	}
			,fields : [{
						name : 'fid'
					}, {
						name : 'faddress',
						myfilterfield : 'faddress',
						myfiltername : '配送地址',
						myfilterable : true
					}, {
						name : 'fnumber',
						myfilterfield : 'fnumber',
						myfiltername : '申请单号',
						myfilterable : true,
						type : "String",
						sortDir : "DESC"
					}, {
						name : 'fcreatorid'
					}, {
						name : 'fupdateuserid'
					}, {
						name : 'fcustomerid'
					}, {
						name : 'fcusproductid'
					}, {
						name : '_custname',
						myfilterfield : '_custname',
						myfiltername : '客户名称',
						myfilterable : true
					}, {
						name : '_custpdtname',
						myfilterfield : '_custpdtname',
						myfiltername : '包装物名称',
						myfilterable : true
					}, {
						name : 'fspec',
						myfilterfield : '_spec',
						myfiltername : '包装规格',
						myfilterable : true
					}, {
						name : 'flinkman',
						myfilterfield : 'flinkman',
						myfiltername : '联系人',
						myfilterable : true
					}, {
						name : 'flinkphone',
						myfilterfield : 'flinkphone',
						myfiltername : '联系电话',
						myfilterable : true
					}, {
						name : 'famount'
					}, {
						name : 'fdescription'
					}, {
						name : 'farrivetime',
						myfilterfield : 'farrivetime',
						myfiltername : '配送时间',
						myfilterable : true
					}, {
						name : 'fcreator'
					}, {
						name : 'flastupdater'
					}, {
						name : 'fcreatetime'
					}, {
						name : 'fupdatetime'
					},{
						name : 'fsaleorderid'
					}, {
						name : 'fiscreate',
						myfilterfield : 'fiscreate',
						myfiltername : '是否生成',
						myfilterable : true
					}, {
						name : 'fstate',
						myfilterfield : 'fstate',
						myfiltername : '申请状态',
						myfilterable : true
					},{
						name : 'ftype'
					},{
						name : 'fwerkname'
					},{
						name : 'fsuppliername'
					},{
						name : 'fcharacter'
					},{
						name : 'stateinfo'
					},'forderunit' , '_custpdtnumber','fcreatetime','foutQty','fordernumber'

					
					],
			columns : {items:[Ext.create('DJ.Base.Grid.GridRowNum',{
						header:'序号',
						stateId : 'gridRowNum'
					}),{
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true

					},{
						'header' : 'fcreatorid',
						'dataIndex' : 'fcreatorid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : 'fupdateuserid',
						'dataIndex' : 'fupdateuserid',
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
						'header' : 'fcusproductid',
						'dataIndex' : 'fcusproductid',
						hidden : true,
						hideable : false,
						sortable : true

					},{
						'header' : 'fsaleorderid',
						'dataIndex' : 'fsaleorderid',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						'header' : 'forderentryid',
						'dataIndex' : 'forderentryid',
						hidden : true,
						hideable : false,
						sortable : true
					}, 
						{
						'header' : '客户名称',
						width : 140,
						'dataIndex' : '_custname',
						sortable : true,
						 renderer :function(v,m){
							 switch(m.record.data.fstate){
							 	case '1':m.css='my-row-accepted';break;
							 	case '2':m.css='my-row-placed';break;
							 	case '3':m.css='my-row-assigned';break;
							 	case '5':m.css='my-row-delivered';break;
							 	case '6':m.css='my-row-all-delivered';break;
							 	
							 }
				                return v;  
				          }
					},{
						'header' : '申请单号',
						width : 90,
						'dataIndex' : 'fnumber',
						sortable : true,
						 renderer :function(v,m){
							 switch(m.record.data.fstate){
							 	case '1':m.css='my-row-accepted';break;
							 	case '2':m.css='my-row-placed';break;
							 	case '3':m.css='my-row-assigned';break;
							 	case '5':m.css='my-row-delivered';break;
							 	case '6':m.css='my-row-all-delivered';break;
							 	
							 }
				                return v;  
				          }
					},{
						'header' : '采购订单号',
						width : 90,
						'dataIndex' : 'fordernumber',
						sortable : true,
						 renderer :function(v,m){
							 switch(m.record.data.fstate){
							 	case '1':m.css='my-row-accepted';break;
							 	case '2':m.css='my-row-placed';break;
							 	case '3':m.css='my-row-assigned';break;
							 	case '5':m.css='my-row-delivered';break;
							 	case '6':m.css='my-row-all-delivered';break;
							 	
							 }
				                return v;  
				          }
					},{
						'header' : '制造商',
						width : 80,
						'dataIndex' : 'fsuppliername',
						sortable : true,
						 renderer :function(v,m){
							 switch(m.record.data.fstate){
							 	case '1':m.css='my-row-accepted';break;
							 	case '2':m.css='my-row-placed';break;
							 	case '3':m.css='my-row-assigned';break;
							 	case '5':m.css='my-row-delivered';break;
							 	case '6':m.css='my-row-all-delivered';break;
							 	
							 }
				                return v;  
				          }
						
					},{
						'header' : '包装物名称',
						width : 200,
						'dataIndex' : '_custpdtname',
						sortable : true,
						 renderer :function(v,m){
							 switch(m.record.data.fstate){
							 	case '1':m.css='my-row-accepted';break;
							 	case '2':m.css='my-row-placed';break;
							 	case '3':m.css='my-row-assigned';break;
							 	case '5':m.css='my-row-delivered';break;
							 	case '6':m.css='my-row-all-delivered';break;
							 	
							 }
				                return v;  
				          }
					},{
						'header' : '包装物编号',
						width : 200,
						'dataIndex' : '_custpdtnumber',
						sortable : true,
						 renderer :function(v,m){
							 switch(m.record.data.fstate){
							 	case '1':m.css='my-row-accepted';break;
							 	case '2':m.css='my-row-placed';break;
							 	case '3':m.css='my-row-assigned';break;
							 	case '5':m.css='my-row-delivered';break;
							 	case '6':m.css='my-row-all-delivered';break;
							 	
							 }
				                return v;  
				          }
					},{
						'header' : '配送数量',
						'dataIndex' : 'famount',
						width : 60,
						sortable : true,
						 renderer :function(v,m){
							 switch(m.record.data.fstate){
							 	case '1':m.css='my-row-accepted';break;
							 	case '2':m.css='my-row-placed';break;
							 	case '3':m.css='my-row-assigned';break;
							 	case '5':m.css='my-row-delivered';break;
							 	case '6':m.css='my-row-all-delivered';break;
							 	
							 }
				                return v;  
				          }
					},{
						'header' : '实际配送数量',
						'dataIndex' : 'foutQty',
						width : 80,
						sortable : true,
						 renderer :function(v,m){
							 switch(m.record.data.fstate){
							 	case '1':m.css='my-row-accepted';break;
							 	case '2':m.css='my-row-placed';break;
							 	case '3':m.css='my-row-assigned';break;
							 	case '5':m.css='my-row-delivered';break;
							 	case '6':m.css='my-row-all-delivered';break;
							 	
							 }
				                return v;  
				          }
					},{
						'header' : '规格',
						'dataIndex' : 'fspec',
						width : 60,
						sortable : true,
						 renderer :function(v,m){
							 switch(m.record.data.fstate){
							 	case '1':m.css='my-row-accepted';break;
							 	case '2':m.css='my-row-placed';break;
							 	case '3':m.css='my-row-assigned';break;
							 	case '5':m.css='my-row-delivered';break;
							 	case '6':m.css='my-row-all-delivered';break;
							 	
							 }
				                return v;  
				          }
					},{
						'header' : '单位',
						'dataIndex' : 'forderunit',
						width : 60,
						sortable : true,
						 renderer :function(v,m){
							 switch(m.record.data.fstate){
							 	case '1':m.css='my-row-accepted';break;
							 	case '2':m.css='my-row-placed';break;
							 	case '3':m.css='my-row-assigned';break;
							 	case '5':m.css='my-row-delivered';break;
							 	case '6':m.css='my-row-all-delivered';break;
							 	
							 }
				                return v;  
				          }
					},{
						'header' : '配送时间',
						'dataIndex' : 'farrivetime',
						width : 140,
						sortable : true,
						xtype : "datecolumn",
						format : "Y-m-d H:i",
						 renderer :function(v,m){
							 switch(m.record.data.fstate){
							 	case '1':m.css='my-row-accepted';break;
							 	case '2':m.css='my-row-placed';break;
							 	case '3':m.css='my-row-assigned';break;
							 	case '5':m.css='my-row-delivered';break;
							 	case '6':m.css='my-row-all-delivered';break;
							 	
							 }
				                return v;  
				          }
					},  {
						'header' : '订单状态',
						'dataIndex' : 'fstate',
						width : 60,
						sortable : true,
						renderer : function(value,meta) {
							if(Ext.isIE && Ext.ieVersion<11){
								return value;
							}
							var r = Ext.Array.clone(DJ.order.Deliver.DeliversCustList.DELIVERS_STATE),
								stateValue = Ext.Number.from(value, 0),
								val = r[stateValue],
								stateinfo = meta.record.data.stateinfo.split(','),
								time0,
								time1,
								time4,
								time,
								title,
								style,
								i;
							stateinfo.forEach(function(item){
								i = parseInt(item);
								time = item.substr(2);
								r[i] = r[i]+"<br/>"+time;
								switch(i){
								case 0:
									time0 = Ext.Date.parse(time, "Y-m-d H:i");
									break;
								case 1:
									time1 = Ext.Date.parse(time, "Y-m-d H:i");
									break;
								case 4:
									time4 = Ext.Date.parse(time, "Y-m-d H:i");
									break;
								}
							});
							time = Ext.Date.parse(meta.record.data.farrivetime, "Y-m-d H:i");
							r.forEach(function(item,index){
								style = 0;
								if(index > stateValue && !/\d{4}-\d{2}-\d{2}/.test(item)){		//可能分配在入库之后
									style = 1;
								}
								if(index==4){
									time4 = time4 || new Date();
									if(time4.getTime()-Ext.Date.subtract(time, Ext.Date.DAY, 1).getTime()>0){
										style += 2;
									}
								}else if(index==1 && time1 && Ext.Date.subtract(time1, Ext.Date.HOUR, 2).getTime()-time0.getTime()>0){
									style += 2;
								}
								switch(style){
								case 0:
									r[index] = '<li>'+r[index]+'</li>';
									break;
								case 1:
									r[index] = '<li style=\'list-style-type:circle;\'>'+r[index]+'</li>';
									break;
								case 2:
									r[index] = '<li style=\'color:#f00;\'>'+r[index]+'</li>';
									break;
								case 3:
									r[index] = '<li style=\'list-style-type:circle;color:#f00;\'>'+r[index]+'</li>';
									break;
								}
							});
							r.splice(2,1);//正序 第2个去掉
							title = '<ul>'+r.reverse().join('')+'</ul>';
							 switch(meta.record.data.fstate){
							 	case '1':meta.css='my-row-accepted';break;
							 	case '2':meta.css='my-row-placed';break;
							 	case '3':meta.css='my-row-assigned';break;
							 	case '5':meta.css='my-row-delivered';break;
							 	case '6':meta.css='my-row-all-delivered';break;
							 	
							 }
							return '<span data-qtip="'+title+'">'+val+'</span>';
						}
					}, {
						'header' : '联系人',
						'dataIndex' : 'flinkman',
						width : 60,
						sortable : true,
						 renderer :function(v,m){
							 switch(m.record.data.fstate){
							 	case '1':m.css='my-row-accepted';break;
							 	case '2':m.css='my-row-placed';break;
							 	case '3':m.css='my-row-assigned';break;
							 	case '5':m.css='my-row-delivered';break;
							 	case '6':m.css='my-row-all-delivered';break;
							 	
							 }
				                return v;  
				          }
					}, {
						'header' : '联系电话',
						'dataIndex' : 'flinkphone',
						sortable : true,
						 renderer :function(v,m){
							 switch(m.record.data.fstate){
							 	case '1':m.css='my-row-accepted';break;
							 	case '2':m.css='my-row-placed';break;
							 	case '3':m.css='my-row-assigned';break;
							 	case '5':m.css='my-row-delivered';break;
							 	case '6':m.css='my-row-all-delivered';break;
							 	
							 }
				                return v;  
				          }
					},{
						'header' : '配送地址',
						'dataIndex' : 'faddress',
						width : 300,
						sortable : true,
						 renderer :function(v,m){
							 switch(m.record.data.fstate){
							 	case '1':m.css='my-row-accepted';break;
							 	case '2':m.css='my-row-placed';break;
							 	case '3':m.css='my-row-assigned';break;
							 	case '5':m.css='my-row-delivered';break;
							 	case '6':m.css='my-row-all-delivered';break;
							 	
							 }
				                return v;  
				          }
					},{
						'header' : '创建时间',
						'dataIndex' : 'fcreatetime',
						width : 140,
						sortable : true,
						xtype : "datecolumn",
						format : "Y-m-d H:i",
						 renderer :function(v,m){
							 switch(m.record.data.fstate){
							 	case '1':m.css='my-row-accepted';break;
							 	case '2':m.css='my-row-placed';break;
							 	case '3':m.css='my-row-assigned';break;
							 	case '5':m.css='my-row-delivered';break;
							 	case '6':m.css='my-row-all-delivered';break;
							 	
							 }
				                return v;  
				          }
					}, {
						'header' : '备注',
						'dataIndex' : 'fdescription',
						sortable : true,
						 renderer :function(v,m){
							 switch(m.record.data.fstate){
							 	case '1':m.css='my-row-accepted';break;
							 	case '2':m.css='my-row-placed';break;
							 	case '3':m.css='my-row-assigned';break;
							 	case '5':m.css='my-row-delivered';break;
							 	case '6':m.css='my-row-all-delivered';break;
							 	
							 }
				                return v;  
				          }
					}
					
					],

	defaults: {
//			stateful : true,
		
		 getStateId: function() {
       		 	var me = this;
       		 	
       		 	//表头的dataIndex可以作为唯一性标识
        		return me.dataIndex || me.stateId || (me.autoGenId ? null : me.id);
   		},
		
		renderer : DJ.tools.fieldRel.MyFieldRelTools.showEmptyWhenNull
    }
					},
					selModel:Ext.create('Ext.selection.CheckboxModel')
		})
		
		


		