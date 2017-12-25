Ext.define('DJ.traffic.SaleDeliver.RealReceiptEdit', {
			extend : 'Ext.Window',
			id : 'DJ.traffic.SaleDeliver.RealReceiptEdit',
			modal : true,
			title : "订单出入库",
			width : 400,
			height : 150,
			resizable : false,
			layout : 'form',
			labelAlign : "left",
			defaults : {
				xtype : 'textfield',
				msgTarget : 'side'
			},
			closable : true,
			initComponent : function() {
						Ext.apply(this, {
			items : [{
		    	   			id : 'DJ.traffic.SaleDeliver.RealReceiptEdit.frealamount',
		    	   			fieldLabel : 'frealamount',
							xtype : 'textfield',
							fieldLabel : '实收数量',
							width : 260,
							labelWidth : 70,
							allowBlank : false,
							blankText : '数量不能为空',
							regex : /^(?!0)\d{0,10}$/,
							regexText : "请输入不超过10位大于0的数字"
						},{
		    	   			id : 'DJ.traffic.SaleDeliver.RealReceiptEdit.fid',
		    	   			fieldLabel : 'fid',
							hidden : true
		    	   		}]}), this.callParent(arguments);
		    	   		},
			buttons : [{
						xtype : "button",
						text : "确定",
						pressed : false,
						handler : function() {

							if (!Ext.getCmp("DJ.traffic.SaleDeliver.RealReceiptEdit.frealamount").isValid()) {
								Ext.MessageBox.alert('提示', '输入项格式不正确，请修改后再提交！');
								return;
							}
							
							var el = Ext.getCmp("DJ.traffic.SaleDeliver.RealReceiptEdit").getEl();
							el.mask("系统处理中,请稍候……");
							Ext.Ajax.request({
								timeout: 600000,
								url : 'updateSaledeliverentry.do',
								params : {
									frealamount : Ext.getCmp("DJ.traffic.SaleDeliver.RealReceiptEdit.frealamount").getValue(),
									fid : Ext.getCmp("DJ.traffic.SaleDeliver.RealReceiptEdit.fid").getValue()
								}, // 参数
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										//Ext.MessageBox.alert('成功', obj.msg);
										djsuccessmsg( obj.msg);
										Ext.getCmp("DJ.traffic.SaleDeliver.SaleDeliverEdit.table").store.load();
										var windows = Ext.getCmp("DJ.traffic.SaleDeliver.RealReceiptEdit");
										windows.close();
									} else {
										Ext.MessageBox.alert('错误', obj.msg);
									}
									el.unmask();
									
								}
							});
							
						}
					}
					, {
						xtype : "button",
						text : "取消",
						handler : function() {
							var windows = Ext.getCmp("DJ.traffic.SaleDeliver.RealReceiptEdit");
							if (windows != null) {
								windows.close();
							}
						}
					}],
			buttonAlign : "center",
			bodyStyle : "padding-top:15px ;padding-left:30px;padding-right:30px"
		});