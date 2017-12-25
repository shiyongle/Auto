Ext.define('DJ.System.SyscfgList', {
	extend : 'Ext.grid.property.Grid',
	id : 'DJ.System.SyscfgList',
	// height : 313,
	// width : 482,
	title : '用户配置',

	statics : {

		ORDER_WAY_STORE : [['single', "单款新增"], ['multi', '批量新增'],
				['file', '批量导入']],

			
		PTP_A	:[['2','二等分'],['3','三等分'],['4','A4打印']]
	

	},

	closable : true,

	nameColumnWidth : 400,

	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {

			// listeners : {
			//			
			// afterrender : function ( com, eOpts ) {
			//					
			//					
			// DJ.System.SyscfgList.actions.setPropertyGridData();
			// }
			//				
			// },
			// source: {
			// 'Property 1': 'String',
			// 'Property 2': true,
			// 'Property 3': '2014-07-16T10:17:49',
			// 'Property 4': 123
			// },
			propertyNames : {
				safeWidget : "启用安全控件",
				orderWay : "下单方式",
				autoDepart : "自动发车",
				thePrintTP : '打印模板模式',
				affirmSchemedesign : '确认方案'
				
			},

			customEditors : {

				safeWidget : {
					xtype : 'checkboxfield',
					
					listeners : {

						change : function(com, newValue, oldValue, eOpts) {

							Ext.getCmp("DJ.System.SyscfgList").setProperty(
									"safeWidget", newValue ? 1 : 0);

							DJ.System.SyscfgList.actions
									.updateCfg("safeWidget");

						}

					}

				},
				orderWay : {

					xtype : "combo",

					queryMode : 'loacal',
					forceSelection : true,
					typeAhead : true,
					value : 'single',
					editable : false,
					store : DJ.System.SyscfgList.ORDER_WAY_STORE,

					listeners : {

						change : function(com, newValue, oldValue, eOpts) {

							Ext.getCmp("DJ.System.SyscfgList").setProperty(
									"orderWay", newValue);

							DJ.System.SyscfgList.actions.updateCfg("orderWay");

						}

					}

				},
				autoDepart : {
					xtype : 'checkboxfield',

					listeners : {

						change : function(com, newValue, oldValue, eOpts) {

							Ext.getCmp("DJ.System.SyscfgList").setProperty(
									"autoDepart", newValue ? 1 : 0);

							DJ.System.SyscfgList.actions
									.updateCfg("autoDepart");

						}

					}

				},
				thePrintTP : {
					
					xtype : "combo",

					queryMode : 'loacal',
					forceSelection : true,
					typeAhead : true,
					value : 'single',
					editable : false,
					store : DJ.System.SyscfgList.PTP_A,

					listeners : {

						change : function(com, newValue, oldValue, eOpts) {

							
							if (newValue != null && newValue != 0) {

								Ext.getCmp("DJ.System.SyscfgList").setProperty(
										"thePrintTP", newValue);

								DJ.System.SyscfgList.actions
										.updateCfg("thePrintTP");

							}
							
							

						}

					}
					
					
				},
				affirmSchemedesign:{
					xtype : "combo",
					queryMode : 'loacal',
					forceSelection : true,
					typeAhead : true,
					editable : false,
//   				 	value:0,
					store : [['1','是'],['0','否']],

					listeners : {

						change : function(com, newValue, oldValue, eOpts) {

							
							if (newValue != null) {

								Ext.getCmp("DJ.System.SyscfgList").setProperty(
										"affirmSchemedesign", newValue);

								DJ.System.SyscfgList.actions
										.updateCfg("affirmSchemedesign");

							}
							
							

						}

					}
				
				}

			},

			customRenderers : {

				safeWidget : function(v) {

					return v == 1 ? '是' : "否";

				},
				orderWay : function(v) {

					var text;

					Ext.each(DJ.System.SyscfgList.ORDER_WAY_STORE, function(
							ele, index, all) {

						if (ele[0] == v) {

							text = ele[1];

							return false;
						}

					});

					return text;
				},
				autoDepart : function(v) {

					return v == 1 ? '是' : "否";

				},
				thePrintTP : function(v) {

					switch (v) {
						case '0' :
							return '请选择'
							break;

						case '2' :
							return '二等分'
							break;

						case '3' :
							return '三等分'
							break;
						case '4' :
							return 'A4打印'
							break;
						default :
							break;
					}

				},
				affirmSchemedesign:function(v){
					return v == 1 ? '是' : "否";
				}

			},

			dockedItems : [{
				xtype : 'toolbar',
				dock : 'top',
				items : [{
					xtype : 'button',
					text : '刷新',
					handler : function() {

						// DJ.System.SyscfgList.actions.storeT.reload();

						DJ.System.SyscfgList.actions.setPropertyGridData();

					}
				},{
			  		text:'打印模板修改',
			  		handler:function(me,e,fn){
			  			 function req(param){
				  			Ext.Ajax.request({
								url:'getFtuPrintTemplate.do',
								params:{key:param},
								success:function(response, opts){//3联的
									var win =	Ext.create('DJ.order.Deliver.FtuPrintTemplate',{
											html:response.responseText
										}).show();
									if(Ext.isFunction(fn)){
										win.on('close',function(){
											fn();
										})
									}
								},
								failure: function(response, opts) {
							        Ext.Msg.alert('提示','请联系管理员！')
							    }
							})
			  			}
						Ext.Ajax.request({
							url:'gainCfgByFkey.do',
							params:{'key':'thePrintTP'},
							success:function(response){
								var obj = Ext.decode(response.responseText);
								if(obj.success==true){
									if(obj.data[0].fvalue=='2'){//2等分
										req(2);
									}else if(obj.data[0].fvalue=='3'){//3等分
										req(3);
									}else{
										Ext.Msg.confirm({
											title : '提示',
											icon : this.QUESTION,
											msg : '请选择打印模板大小',
											buttons : Ext.Msg.YESNO,
											callback : function(btn) {
												if (btn == 'yes') {
													Ext.Ajax.request({
														url:'updateSyscfgsByKeyAndValue.do',
														params:{'key':'thePrintTP','value':2},
														success:function(response){
															var obj = Ext.decode(response.responseText);
															if(obj.success==true){
																req(2);
															}
														}
													})
												} else if (btn == 'no') {
													Ext.Ajax.request({
														url:'updateSyscfgsByKeyAndValue.do',
														params:{'key':'thePrintTP','value':3},
														success:function(response){
															var obj = Ext.decode(response.responseText);
															if(obj.success==true){
																req(3);
															}
														}
													})
												} 
											},
											buttonText : {
												yes : '二等分',
												no : '三等分'
											},
											scope : this
										})
									
									}
								}
							}
						})
			  		}
				}]
			}]
		});

		me.callParent(arguments);
	}

});

DJ.System.SyscfgList.actions = {

	storeT : new Ext.data.JsonStore({
		// store configs
		storeId : 'DJ.System.SyscfgList.jsonStore',

		proxy : {
			type : 'ajax',
			url : 'selectUserCfgs.do',
			reader : {
				type : 'json',
				root : 'data',
				idProperty : 'fid'
			}
		},

		// alternatively, a Ext.data.Model name can be given (see Ext.data.Store
		// for
		// an example)
		fields : ['fid', 'fkey', 'fvalue', 'fuser', 'fkeyName']
	}),
	setPropertyGridData : function() {

		var me = this;
		
		// 用第三方store实现远程读取配置项
		me.storeT.load({
		
			 callback : function(records, operation, success) {
			 	
			 	if(!success){
			 	
			 		return;
			 		
			 	}
			 	
			 	
			 	
				var sourceObj = {};

				// 暂时未用
				var propertyNamesObj = {};

				var items = me.storeT.data;

				items.each(function(item) {

					// 隐藏自动发车
					if (item.get("fkey") == 'autoDepart'||item.get("fkey") == 'safeWidget') {

						return;

					}

					sourceObj[item.get("fkey")] = item.get("fvalue");

					propertyNamesObj[item.get("fkey")] = item.get("fkeyName");

				});

				Ext.getCmp("DJ.System.SyscfgList").setSource(sourceObj);
				// Ext.getCmp("DJ.System.SyscfgList").
			}
			
		});

		

	},
	updateCfg : function(key) {

		var valueT = Ext.getCmp("DJ.System.SyscfgList").getStore().findRecord(
				"name", key).get("value");

		// boolen value translate to int
		if (key == 'safeWidget') {

			valueT = valueT ? 1 : 0;

		}

		Ext.Ajax.request({
			timeout : 60000,
			url : "updateSyscfgs.do",

			params : {

				Syscfg : Ext.JSON.encode({
					fid : DJ.System.SyscfgList.actions.storeT.findRecord(
							"fkey", key).get("fid"),
					fkey : DJ.System.SyscfgList.actions.storeT.findRecord(
							"fkey", key).get("fkey"),
					fvalue : valueT
				})

			},

			success : function(response, option) {

				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {
					// Ext.MessageBox.alert('成功', obj.msg);

				} else {
					Ext.MessageBox.alert('错误', obj.msg);

				}
				// el.unmask();
			}
		});

	}

};


DJ.System.SyscfgList.actions.setPropertyGridData();

//// 不知何故，调用2次才有效果
//setTimeout(function() {
//
//	DJ.System.SyscfgList.actions.setPropertyGridData();
//
//	setTimeout(function() {
//
//		
//
//	}, 100);
//
//}, 100);
