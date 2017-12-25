Ext.define('DJ.order.Deliver.RealAmountEdit', {
			extend : 'Ext.Window',
			id : 'DJ.order.Deliver.RealAmountEdit',
			modal : true,
			title : "实配数量",
			width : 400,
			height : 180,
			resizable : false,
			layout : 'form',
//			labelAlign : "left",
//			defaults : {
//				xtype : 'textfield',
//				msgTarget : 'side'
//			},
			closable : true,
			initComponent : function() {
						Ext.apply(this, {
			items : [	
						{
		    	   			id : 'DJ.order.Deliver.RealAmountEdit.deliverorderid',
		    	   			xtype : 'textfield',
		    	   			fieldLabel : 'deliverorderid',
							hidden : true
		    	   		},{
		    	   			id : 'DJ.order.Deliver.RealAmountEdit.famount',
		    	   			fieldLabel : 'fassembleQty',
							xtype : 'textfield',
							fieldLabel : '配送数量',
							hidden : true
		    	   		},{
		    	   			id : 'DJ.order.Deliver.RealAmountEdit.fassembleQty',
		    	   			fieldLabel : 'fassembleQty',
							xtype : 'textfield',
							fieldLabel : '提货数量',
							hidden : true
		    	   		},{
		    	   			id : 'DJ.order.Deliver.RealAmountEdit.frealqty',
		    	   			fieldLabel : 'famount',
							xtype : 'textfield',
							fieldLabel : '实配数量',
							width : 260,
							labelWidth : 70,
							allowBlank : false,
							blankText : '数量不能为空',
							regex : /^(?!0)\d{0,10}$/,
							regexText : "请输入不超过10位大于0的数字"
		    	   		}
		    	   		]}), this.callParent(arguments);
		    	   		},
			buttons : [{
						id : 'DJ.order.Deliver.RealAmountEdit.SaveButton',
						xtype : "button",
						text : "确定",
						pressed : false,
						handler : function() {

							if (!Ext.getCmp("DJ.order.Deliver.RealAmountEdit.famount").isValid()) {
								Ext.MessageBox.alert('提示', '输入项格式不正确，请修改后再提交！');
								return;
							}
							
							var fids = Ext.getCmp("DJ.order.Deliver.RealAmountEdit.deliverorderid").getValue();
							var famount = Ext.getCmp("DJ.order.Deliver.RealAmountEdit.famount").getValue();
							var fassembleQty = Ext.getCmp("DJ.order.Deliver.RealAmountEdit.fassembleQty").getValue();
							var frealqty = Ext.getCmp("DJ.order.Deliver.RealAmountEdit.frealqty").getValue();
							if(frealqty>(famount-fassembleQty)){
								Ext.MessageBox.alert('错误', "配送单实配数量之和不能大于配送数量！");
								return;
							}
							
							var el = Ext.getCmp("DJ.order.Deliver.RealAmountEdit").getEl();
							el.mask("系统处理中,请稍候……");
							
							Ext.Ajax.request({
								timeout : 600000,
								url : 'creatTruckassembleByCondition.do',//Ext.getCmp(Ext.getCmp('DJ.order.Deliver.RealAmountEdit').parentid).CREAT_TRUCKASSEMBLE_URL,
								params : {
									fidcls : fids ,
									frealamount : frealqty ,
									ftype : 1
								}, // 参数
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										  djsuccessmsg( obj.msg);
//										  if(Ext.getCmp("DJ.order.Deliver.DeliverorderList")== undefined){
//										  	Ext.getCmp("DJ.order.Deliver.SDeliverorderSendList").store.load();
//										  }else{
//										  	Ext.getCmp("DJ.order.Deliver.DeliverorderList").store.load();
//										  }
										
										var rgrid = Ext.getCmp(Ext.getCmp('DJ.order.Deliver.RealAmountEdit').parentid);
										
										var rec = rgrid.getSelectionModel().getSelection();
										var windows = Ext.getCmp("DJ.order.Deliver.RealAmountEdit");
										
										Ext.Ajax.request({
											url : 'GainCfgByFkey.do',
											success : function(response, option) {
												var objs = Ext.decode(response.responseText);
												if (objs.success == true) {
													var saleDeliverid = obj.data[0].saledeliverID.split(',');
													if(Ext.getCmp('DJ.order.Deliver.RealAmountEdit').parentid == 'DJ.order.saleOrder.MyDeliveryList'){
														Ext.MessageBox.confirm('打印界面', '是否立即打印出库单！',function(btn, text){
															if(btn=='yes'){
																for(var i =0;i<saleDeliverid.length;i++){
																	if(saleDeliverid[i]!=''){
																		rgrid.doPrint(saleDeliverid[i]);
																	}
																}
//												
															}
														})
													}
												}
												windows.close();
											}
										})
									
										
										rgrid.getStore().load();
										 if(Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleList")!= undefined){
										 	Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleList").store.load();
										 }
										
									} else {
										Ext.MessageBox.alert('错误', obj.msg);
									}
									el.unmask();
								}
							});
						
						}
					}
					, {
						id : 'DJ.order.Deliver.RealAmountEdit.ColseButton',
						xtype : "button",
						text : "取消",
						handler : function() {
							var windows = Ext.getCmp("DJ.order.Deliver.RealAmountEdit");
							if (windows != null) {
								windows.close();
							}
						}
					}],
			buttonAlign : "center",
			bodyStyle : "padding-top:15px ;padding-left:30px;padding-right:30px"
		});
		
		