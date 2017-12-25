Ext.define('DJ.order.logistics.LogisticsCarEdit', {
			extend : 'Ext.c.BaseEditUI',
			id : "DJ.order.logistics.LogisticsCarEdit",
			modal : true,
			onload : function() {
				this.down("toolbar").hide();
			},
			title : "新增",
			width : 300,
			height : 200,
			resizable : false,
			url : 'SaveLogisticsCarinfo.do',
			infourl : 'getLogisticsCarInfo.do',
			viewurl : 'getLogisticsCarInfo.do',
			closable : true,
			bodyStyle : 'background:#FFFFFF',
			bodyPadding : "30 30",
			ctype : 'LogisticsCarinfo',
			items : [{
				border : false,
				layout : "anchor",
				defaults : {
					anchor : '100%',
					labelWidth : 65,
					labelAlign : 'right',
					labelSeparator : '&nbsp;&nbsp;',
					style : 'margin-bottom:15px'
				},
				items : [{
					name : 'fcargotype',
					xtype : 'textfield',
					fieldLabel : '货物类型',
					allowBlank : false,
					blankText : '请填写货物类型'
					}, {
					name : 'fcartype',
					xtype : 'textfield',
					fieldLabel : '车辆类型',
					allowBlank : false,
					blankText : '请填写车辆类型'
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