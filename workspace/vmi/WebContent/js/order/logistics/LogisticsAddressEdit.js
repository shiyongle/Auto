Ext.define('DJ.order.logistics.LogisticsAddressEdit', {
			extend : 'Ext.c.BaseEditUI',
			id : "DJ.order.logistics.LogisticsAddressEdit",
			modal : true,
			onload : function() {
				this.down("toolbar").hide();
			},
			title : "新增",
			width : 450,
			height : 200,
			resizable : false,
			url : 'SaveLogisticsAddress.do',
			infourl : 'getLogisticsAddressInfo.do',
			viewurl : 'getLogisticsAddressInfo.do',
			closable : true,
			bodyStyle : 'background:#FFFFFF',
			bodyPadding : "30 30",
			ctype : 'LogisticsAddress',
			items : [{
				border : false,
				layout : "anchor",
				defaults : {
					anchor : '100%',
					labelWidth : 65,
					labelAlign : 'right',
					style : 'margin-bottom:15px'
				},
				items : [{
					layout : {
						type : "hbox",
						pack : 'start '
					},
					anchor : '100%',
					xtype : 'container',
					items : [{
								layout : 'anchor',
								border : false,
								flex : 1,
								items : [{
											name : 'flinkman',
											xtype : 'textfield',
											anchor : '100%',
											fieldLabel : '发货人',
											labelWidth : 65,
											labelAlign : 'right',
											labelSeparator : ' ',
											margin : '0 10 0 0'
										}]
							}, {
								layout : 'anchor',
								border : false,
								flex : 1,
								items : [{
											margin : '0 0 0 10',
											name : 'fphone',
											xtype : 'textfield',
											anchor : '100%',
											labelWidth : 65,
											labelAlign : 'right',
											regex:/((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,
											regexText:'电话格式错误',
											fieldLabel : '发货人电话',
											allowBlank : false,
											blankText : '电话不能为空',
											labelSeparator : ' '
										}]
							}]
						// }]
					}, {
					name : 'fname',
					xtype : 'textfield',
					labelSeparator : '',
					anchor : '100%',
					fieldLabel : '货物位置',
					allowBlank : false,
					blankText : '货物位置不能为空'
				}, {
					name : 'fid',
					xtype : 'textfield',
					hidden : true
				}, {
					name : 'fcreatorid',
					xtype : 'textfield',
					hidden : true
				}, {
					name : 'fcreatetime',
					xtype : 'textfield',
					hidden : true
				}, {
					name : 'ftype',
					xtype : 'textfield',
					hidden : true,
					value:'0'
				}, {
					name : 'fcustomerid',
					xtype : 'textfield',
					hidden : true
				}, {
					name : 'flasted',
					xtype : 'textfield',
					value:'0',
					hidden : true
				}, {
					baseCls : "x-plain",
					margin : '30 0 0 0',
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
			}],
			listeners:{
				'beforeshow':function(win){
				 if(win.editstate==="edit")
				 { win.setTitle("修改");
				 }						
				},
				'colse':function(editui){
					if (editui.parent != "") {
							Ext.getCmp(editui.parent).store.load();
						}
				}
			}

		});