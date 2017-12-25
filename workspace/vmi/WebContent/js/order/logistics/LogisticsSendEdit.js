Ext.define('DJ.order.logistics.LogisticsSendEdit', {
			extend : 'Ext.c.BaseEditUI',
			id : "DJ.order.logistics.LogisticsSendEdit",
			modal : true,
			onload : function() {
				this.down("toolbar").hide();
			},
			title : "派车信息",
			width : 300,
			height : 250,
			resizable : false,
			url : 'SendLogisticsOrders.do',
			closable : true,
			bodyStyle : 'background:#FFFFFF',
			bodyPadding : "30 30",
			items : [{
				border : false,
				layout : "anchor",
				defaults : {
					anchor : '100%',
					labelWidth : 65,
					labelSeparator : '&nbsp;&nbsp;',
					labelAlign : 'right',
					style : 'margin-bottom:15px'
				},
				items : [{
					name : 'fdriver',
					xtype : 'textfield',
					fieldLabel : '司机',
					allowBlank : false,
					blankText : '请填写司机名称'
				},{
					name : 'fdriverphone',
					xtype : 'textfield',
					fieldLabel : '司机电话',
					regex:/((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,
					regexText:'电话格式错误'
				},{
					name : 'fcarnumber',
					xtype : 'textfield',
					fieldLabel : '车牌号'
				},{
					name : 'fcost',
					xtype : 'numberfield',
					fieldLabel : '运费',
					hideTrigger:true,
					minValue: 0,
					minText:'运费不能小于0',
					negativeText:'运费大于0'
				},{
					name : 'fid',
					xtype : 'textfield',
				hidden:true
				}, {
					baseCls : "x-plain",
					margin : '0 0 0 50',
					buttons : [{
								xtype : 'tbfill'
							
							}, {
								text : '保存',
								minWidth : 60,
								minHeight : 20,
								handler : function() {

									var btn = this
											.up('window')
											.down('toolbar[dock=top] button[text*=保]');

									btn.handler.call(btn);
								}
							}, {
								xtype : 'tbspacer'
							}, {
								text : '关闭',
								minWidth : 60,
								minHeight : 20,
								handler : function() {
									this.up('window').close();
								}
							}, {
								xtype : 'tbfill'
							}]

				}]
			}]
			

		});