Ext.define('DJ.order.saleOrder.designerEdit', {
			extend : 'Ext.Window',
			id : 'DJ.order.saleOrder.designerEdit',
			modal : true,
			title : "选择设计师",
			width : 350,
			height : 180,
			resizable : false,
			layout: { 
			type:'hbox',
			align:'middle'
			},
			closable : true,
			initComponent : function() {
						Ext.apply(this, {
			items : [{
				flex:1,
				xtype:'form',
				baseCls : 'x-plain',
				fieldDefaults : {
				labelWidth : 80,
				labelAlign : "left"
				},
//				defaults : {
//				msgTarget : 'side'
//				},
				items:[{
				id : 'DJ.order.saleOrder.designerEdit.fsupplierid',
				name : 'fsupplierid',
				fieldLabel : '供应商名称',
				allowBlank : false,
				blankText : '请选择供应商',
//				editable : false, // 可以编辑不
				xtype : 'cCombobox',
				displayField : 'fname', // 这个是设置下拉框中显示的值
				valueField : 'fid', // 这个可以传到后台的值
				MyConfig : {
					width : 800,// 下拉界面
					height : 200,// 下拉界面
					url : 'GetSupplierList.do', // 下拉数据来源
						hiddenToolbar:true,
					fields : [{
						name : 'fid'
					}, {
						name : 'fname',
						myfilterfield : 'fname',
						myfiltername : '名称',
						myfilterable : true
					}, {
						name : 'fnumber',
						myfilterfield : 'fnumber',
						myfiltername : '编码',
						myfilterable : true
					}, {
						name : 'fcreatetime'
					}, {
						name : 'flastupdatetime'

					}],
					columns : [{
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						autoHeight : true,
						autoWidth : true,
						sortable : true
					}, {
						'header' : '供应商名称',
						'dataIndex' : 'fname',
						sortable : true

					}, {
						'header' : '编码',
						'dataIndex' : 'fnumber',
						sortable : true
					}, {
						'header' : '修改时间',
						'dataIndex' : 'flastupdatetime',
						width : 140,
						sortable : true
					}, {
						'header' : '创建时间',
						'dataIndex' : 'fcreatetime',
						width : 140,
						sortable : true
					}]
				}
			},{
	   			id : 'DJ.order.saleOrder.designerEdit.FdesignerID',
	    	   		name:'FdesignerID',
	        		fieldLabel : '设计师',
	        		allowBlank : false,
	        		blankText : '请选择设计师',
	        		valueField : 'fid', // 组件隐藏值
				xtype : 'cCombobox',
				displayField : 'fname',// 组件显示值
				readOnlyCls:'x-item-disabled',
				MyConfig : {
					width : 800,// 下拉界面
					height : 200,// 下拉界面
					url : 'GetDesignerlist.do', // 下拉数据来源
					fields : [{
								name : 'fid'
							}, {
								name : 'fname',
								myfilterfield : 'fname', // 查找字段，发送到服务端
								myfiltername : '名称',// 在过滤下拉框中显示的名称
								myfilterable : true
								// 该字段是否查找字段
							}, {
								name : 'femail'
							}, {
								name : 'ftel'
							}, {
								name : 'fqq'
							}],
					columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
								'header' : 'fid',
								'dataIndex' : 'fid',
								hidden : true,
								hideable : false,
								sortable : true
							}, {
							'header' : '名称',
							'dataIndex' : 'fname',
							sortable : true
							}, {
								'header' : 'Email',
								'dataIndex' : 'femail',
								sortable : true
							}, {
								'header' : '联系方式',
								'dataIndex' : 'ftel',
								sortable : true
							}, {
								'header' : 'QQ',
								'dataIndex' : 'fqq',
								sortable : true
							}]
					}
	    	   		},{
	    	   				xtype:'textfield',
		    	   			id : 'DJ.order.saleOrder.designerEdit.FresultID',
							name : 'FresultID',
							hidden : true
		    	   		}]}]}), this.callParent(arguments);
		    	   		},
			buttons : [{
						id : 'DJ.order.saleOrder.designerEdit.SaveButton',
						xtype : "button",
						text : "确定",
						pressed : false,
						handler : function() {
							var windows = Ext.getCmp("DJ.order.saleOrder.designerEdit");
							var cform = windows.down("form").getForm();
							if (!cform.isValid()) {
								Ext.MessageBox.alert('提示', '输入项格式不正确，请按提示修改！');
								return;
							}
							var el = windows.getEl();
							el.mask("系统处理中,请稍候……");
							Ext.Ajax.request({
											url:'receiveProductdemand.do',
											params:cform.getValues(),
											success : function(response, option) {
												var obj = Ext.decode(response.responseText);
												if (obj.success == true) {
													djsuccessmsg(obj.msg);
													Ext.getCmp("DJ.order.saleOrder.SchemeDesignID").store
															.load();

												} else {
													
													if(obj.msg==="请先在用户中心维护QQ信息(企业QQ)")
													{
														Ext.MessageBox.show({title:'提示',msg: obj.msg, buttons: Ext.MessageBox.YESNO,buttonText:{  yes: "是",   no: "否" },fn:function(btn, text){
															if(btn=="yes"){
																
																DJ.tools.mainPageRel.IndexMessageRel.openUserCenter();
															}
														}
														})
													}else
													{
														Ext.MessageBox.alert('错误', obj.msg);
													}

												}
												el.unmask();
												windows.close();
											}
										})
						}
					}
					, {
						id : 'DJ.order.saleOrder.designerEdit.ColseButton',
						xtype : "button",
						text : "取消",
						handler : function() {
							var windows = Ext.getCmp("DJ.order.saleOrder.designerEdit");
							if (windows != null) {
								windows.close();
							}
						}
					}],
			buttonAlign : "center",
			bodyStyle : "padding-top:15px ;padding-left:30px;padding-right:30px"
		});