Ext.define('DJ.order.Deliver.DeliversAndStockCustEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.order.Deliver.DeliversAndStockCustEdit",
	modal : true,
	onload : function() {
	},
	title : "下单",
	width : 680,// 230, //Window宽度
	height : 370,// 137, //Window高度
	resizable : false,
	url : 'saveMystockOrDeliverapply.do',
//	infourl : 'getCustDeliverapplyInfo.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
//	viewurl : 'getCustDeliverapplyInfo.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true
	listeners:{
		beforeshow:function(){
			var me = this;
			var cutidF = me.down('textfield[name=fcustomerid]');		
			Ext.Ajax.request({
					url:'gainCurrentCusID.do',
					success:function(res){
						var obj = Ext.decode(res.responseText);
						if(obj.success){
							cutidF.setValue( obj.data[0].fcustmerid);
						} else {
						
							Ext.MessageBox.alert('错误', obj.msg);
							me.close();
						}
					}
				});
		}
	},
	Action_BeforeSubmit:function(me){
	var isreturn=false;
	var win = Ext.getCmp(me.id);
	var dform=win.down("form[ctype=Deliverapply]").getForm();
	var mform=win.down("form[ctype=Mystock]").getForm();
		dform.clearInvalid();
		mform.clearInvalid();
		if(!(Ext.isEmpty(dform.findField("famount").getValue())||dform.findField("famount").getValue()===0))
		{
		
			if(!dform.isValid())
			{
				throw '请完善要货信息';
			}
			isreturn=true;
		}
		if(!(Ext.isEmpty(mform.findField("fplanamount").getValue())||mform.findField("fplanamount").getValue()===0))
		{
			if(!mform.isValid())
			{
				throw '请完善备货信息';
			}
				isreturn=true;
		}
		if(!isreturn)
		{
			throw '请选择下单方式，并完善';
		}
	},
	Action_Submit : function(cc0) {
		var win = Ext.getCmp(cc0.id);
		var cc2 = win.getform();
		var cc3 = cc2.getValues();
		var forms=win.query("fieldset>form");
		for (var zz1 = 0x0; zz1 < forms.length; zz1++) {
			if(!forms[zz1].getForm().hasInvalidField())
			cc3[forms[zz1].ctype] = Ext.encode(forms[zz1].getForm().getValues());
			
		};
//		if (cc0.cautoverifyinput == true && !win.getform().isValid()) {
//			throw "输入项格式不正确，请修改后再提交！";
//		};
//		if (cc0.cautoverifyinput == true) {
//			throw "输入项格式不正确，请修改后再提交！";
//		};
		if(cc0.cverifyinput()===false){
			return;
		}
		var cc4 = Ext.ComponentQuery.query("cTable", cc0);
		for (var cc5 = 0x0; cc5 < cc4.length; cc5++) {
			cc3[cc4[cc5].name] = Ext.encode(cc4[cc5].getcvalues());
		};
		cc2.submit({
					url : cc0.url,
					clientValidation : false,
					method : "POST",
					waitMsg : "正在处理请求……",
					timeout : 0xea60,
					params : cc3,
					success : function(cc6, cc7) {
						var cc8 = Ext.decode(cc7.response.responseText);
						if (win.parent != "") {
							Ext.getCmp(win.parent).store.load();
						};
						djsuccessmsg(cc8.msg);
						win.close();

					},
					failure : function(cc6, cc7) {
						var cc9 = Ext.decode(cc7.response.responseText);
						Ext.MessageBox.alert("提示", cc9.msg);
					}
		});
	},
	initComponent : function() {
		Ext.apply(this, {
			items : [{xtype:'textfield',hidden:true,name:"fcustomerid"},{xtype:'textfield',hidden:true,name:"fcustproductid"},{
		xtype:'fieldset',
        title: '送货',
        name:'deliverapply',  
        collapsible: false,
     	  fieldDefaults: {
        labelAlign: 'left',
        labelWidth: 80
    },items:[{xtype:'form',
        	layout : "column",
        	 ctype : "Deliverapply",
        	baseCls : "x-plain",
        items:[{
        	columnWidth : .5,
        	baseCls : "x-plain",
        	items : [{layout:"anchor",
        		baseCls : "x-plain",
        		items:[{
					anchor:"95%",
        			xtype : 'combobox',
					fieldLabel: '制造商',
					name: 'fsupplierid',
					itemId: 'supplier',
					valueField : 'fid',
					displayField : 'fname',
					editable:false,
					allowBlank: false,
					blankText : '请选择制造商！',
					enterIndex: 1,
					store:Ext.create('Ext.data.Store',{
						fields: ['fid', 'fname'],
						proxy:{
							type:'ajax',
							url: 'getSupplierForDeliverApply.do',
					         reader: {
					             type: 'json',
					             root: 'data'
					         }
						}
					}),
					listeners:{
						afterrender: function(){ //自动设置制造商
							var me = this,
								store = me.getStore();
							store.load({
								callback: function(records, operation, success){
									if(success && records.length==1){
										me.setValue(records[0]);
										me.fireEvent('select',me,records[0])
										me.setEditable(false);
									}
								}
							});
						},
						select: function(com,record){	
							var mystocksupplier=com.up("window").down("fieldset[name=mystock]").down("combo[name=fsupplierid]");
							if(Ext.isEmpty(mystocksupplier.getValue()))
							{
								mystocksupplier.setValue(record);
								mystocksupplier.setEditable(false);
							}
							
						}
					}
					},{
					xtype:'fieldcontainer',
			    	     fieldLabel : '配送时间',
			    	     anchor:"95%",
		        	     layout: {
                                type: 'hbox'
                            },
		        	     items:[{
		        	     	xtype:'datefield',
							flex:2.1,
							name : 'farrivetime',
							format : 'Y-m-d',
							allowBlank : false,
							blankText : '配送时间不能为空',
							hideLabel: true,
							margin : '0 10 10 0',
							minValue : new Date(),
							value:new Date()
		        	     },{

                                            xtype: 'radiofield',
                                            boxLabel: '上午',
                                            inputValue: '09',
                                            flex:1,
                                            name : 'fatime',
                                            margin : '0 5 5 0'
                                        },
                                        {
                                        	 flex:1,
                                            xtype: 'radiofield',
                                            boxLabel: '下午',
                                            inputValue: '14' ,
                                             checked: true,
                                            name : 'fatime',
                                              margin : '0 5 5 0'
// }]
                                        }]
		        	     }]
				}]
				},{
				columnWidth : .5,
				baseCls : "x-plain",
				bodyStyle : 'padding-left:20px;padding-right:10px',
        		items : [
        		{layout:"anchor",
        		baseCls : "x-plain",
        		items:[{
        		anchor:"100%",
				name:"supplierid",
					fieldLabel : '配送数量',
					xtype: 'numberfield',
					originalValue:"",
					name: 'famount',
					hideTrigger:true,
					allowBlank: false,
					allowDecimals: false,
					minValue: 1
					},{
					anchor:"100%",
					fieldLabel:'配送地址',
					xtype:'cCombobox',
					name: 'faddressid',
					enterIndex: 22,
					displayField:'fname',
					valueField:'fid',
					allowBlank:false,
					blankText:'请选择送货地址',
					listeners: {
						afterrender: function(){
							var me = this;
							Ext.Ajax.request({
								timeout : 60000,
								url : "getUserDefaultAddress.do",
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if(obj.success){
										var config;
										if(obj.data){
										if(obj.data.length==1){
											var record = obj.data[0];
											config = {
												fid: record['fid'],
												fname: record['fname']
											};
											me.setmyvalue(config);
										}
										}
									}
								}
							});
						}
					},
					MyConfig:{
						width:650,
						height:200,
						url : 'getUserToCustAddress.do',
						hiddenToolbar : true,
						fields:[{
									name : 'fid'
								},{
									name : 'fname',
									myfilterfield : 'ad.fname',
									myfiltername : '名称',
									myfilterable : true
								},{
									name : 'flinkman',
									myfilterfield : 'ad.flinkman',
									myfiltername : '联系人',
									myfilterable : true
								},{
									name : 'fphone',
									myfilterfield : 'ad.fphone', 
									myfiltername : '联系电话',
									myfilterable : true
								}],
						columns:[{
									header : '地址名称',
									dataIndex : 'fname',
									flex: 5,
									sortable : true
								},{
									header : '联系人',
									dataIndex : 'flinkman',
									flex: 1,
									sortable : true
								},{
									header : '联系电话',
									dataIndex : 'fphone',
									flex: 1,
									sortable : true
						}]
					}
				},{
				xtype : 'button',
						text:'<font color=blue>新增地址</font>',
						width : 100,
						handler : function() {
							var editui = Ext.getCmp("DJ.System.UserAddressEdit");
							var customerFiled = Ext
						.getCmp("DJ.order.Deliver.DeliversCustEdit.fcustomerid");
							if (editui == null) {
								editui = Ext.create('DJ.System.UserAddressEdit');
							}
							editui.seteditstate("add");
							Ext.getCmp("DJ.System.UserAddressEdit").down('cCombobox[name=fcustomerid]').setReadOnly(true);
						    editui.show();
						editui.getform().form.findField("fcustomerid").setmyvalue("\"fid\":\""
										+customerFiled.getValue()  + "\",\"fname\":\""
										+customerFiled.getRawValue() + "\"");
						}
				}]
					}]
					
        },{
        columnWidth : 1,
       baseCls : "x-plain",
       bodyStyle : 'padding-right:10px;padding-top:5px',
        	items : [{layout:"anchor",
        		baseCls : "x-plain",
        items:[{
        	anchor:"100%",
        name : 'fdescription',
		xtype : 'textfield',
		fieldLabel : '备  注'
        }]
        }]
        }]
        }]
        },{
        xtype:'fieldset',
        title: '备货',
        name:"mystock",
        collapsible: false,
     	  fieldDefaults: {
        labelAlign: 'left',
        labelWidth: 80},
        items:[{xtype:'form',
        	layout : "column",
        	baseCls : "x-plain",
        	ctype : "Mystock",
		 items:[{
        	columnWidth : .5,
        	baseCls : "x-plain",
        	items : [{layout:"anchor",
        		baseCls : "x-plain",
        		items:[{
					anchor:"95%",
        			xtype : 'combobox',
					fieldLabel: '制造商',
					name: 'fsupplierid',
					itemId: 'supplier',
					valueField : 'fid',
					displayField : 'fname',
					editable:false,
					allowBlank: false,
					enterIndex: 1,
					store:Ext.create('Ext.data.Store',{
						fields: ['fid', 'fname'],
						proxy:{
							type:'ajax',
							url: 'getSupplierForDeliverApply.do',
					         reader: {
					             type: 'json',
					             root: 'data'
					         }
						}
					})
					},{
						anchor:"95%",
							fieldLabel : '要求完成时间',
		        	     	xtype:'datefield',
							flex:2.1,
							id:"DJ.order.Deliver.DeliversAndStockCustEdit.ffinishtime",
							name : 'ffinishtime',
							value:Ext.Date.add(new Date(new Date()),Ext.Date.DAY,2),
							format : 'Y-m-d',
							allowBlank : false,
							minValue :Ext.Date.add(new Date(new Date()),Ext.Date.DAY,2)
					},{
		margin : '0 0 0 85',			
		xtype:'buttongroup',
		layout: 'hbox',
		anchor:"95%",
		items:[{
			xtype : 'button',
			text : '2日内',
			pressed : true,
			toggleGroup : "ffinishtime",
			anchor:'30%',
			margin : '5 10 5 10',
			handler:function(){
				Ext.getCmp('DJ.order.Deliver.DeliversAndStockCustEdit.ffinishtime').setValue(Ext.Date.add(new Date(new Date()),Ext.Date.DAY,2))
			}
		}, {
			xtype : 'button',
			text : '5日内',
			toggleGroup : "ffinishtime",
			anchor:'30%',
			margin : '5 10 5 10',
			handler:function(){
				Ext.getCmp('DJ.order.Deliver.DeliversAndStockCustEdit.ffinishtime').setValue(Ext.Date.add(new Date(new Date()),Ext.Date.DAY,5))
			}
		}, {
			xtype : 'button',
			toggleGroup : "ffinishtime",
			text : '7日内',
			anchor:'30%',
			margin : '5 10 5 10',
			handler:function(){
				Ext.getCmp('DJ.order.Deliver.DeliversAndStockCustEdit.ffinishtime').setValue(Ext.Date.add(new Date(new Date()),Ext.Date.DAY,7))
			}
		}]
		
		        	     }]
				}]
				},{
				columnWidth : .5,
				baseCls : "x-plain",
				bodyStyle : 'padding-left:20px;padding-right:10px',
        		items : [
        		{layout:"anchor",
        		baseCls : "x-plain",
        		items:[{
        		anchor:"100%",
					fieldLabel : '计划数量',
					xtype: 'numberfield',
					name: 'fplanamount',
					hideTrigger:true,
					allowBlank: false,
					allowDecimals: false,
					minValue: 1
					},{
						fieldLabel : '预计消耗时间',
						xtype:'datefield',
						anchor:"100%",
						id:"DJ.order.Deliver.DeliversAndStockCustEdit.fconsumetime",
						name : 'fconsumetime',
						format : 'Y-m-d',
						allowBlank : false,
						minValue :Ext.Date.add(new Date(new Date()),Ext.Date.DAY,2),
						value:Ext.Date.add(new Date(new Date()),Ext.Date.MONTH,1)
//						blankText : '配送时间不能为空',
				},{
		margin : '0 0 0 85',		
		xtype:'buttongroup',
		layout: 'hbox',
		anchor:'100%',
		items:[{
			xtype : 'button',
			toggleGroup : "fconsumetime",
			text : '一周内',
				anchor:'30%',
			margin : '5 10 5 10',
			handler:function(){
				Ext.getCmp('DJ.order.Deliver.DeliversAndStockCustEdit.fconsumetime').setValue(Ext.Date.add(new Date(new Date()),Ext.Date.DAY,7))
			}
		}, {
			xtype : 'button',
			text : '半月内',
			toggleGroup : "fconsumetime",
		anchor:'30%',
			margin : '5 10 5 10',
			handler:function(){
				Ext.getCmp('DJ.order.Deliver.DeliversAndStockCustEdit.fconsumetime').setValue(Ext.Date.add(new Date(new Date()),Ext.Date.DAY,15))
			}
		}, {
			xtype : 'button',
			text : '1月内',
			pressed : true,
			toggleGroup : "fconsumetime",
			anchor:'-10',
			margin : '5 0 5 0',
			handler:function(){
				Ext.getCmp('DJ.order.Deliver.DeliversAndStockCustEdit.fconsumetime').setValue(Ext.Date.add(new Date(new Date()),Ext.Date.MONTH,1))
			}
		} ]						
				}]
					}]
					
        },{
        columnWidth : 1,
       baseCls : "x-plain",
       bodyStyle : 'padding-right:10px;padding-top:5px',
        	items : [{layout:"anchor",
        		baseCls : "x-plain",
        items:[{
        	anchor:"100%",
        name : 'fremark',
		xtype : 'textfield',
		fieldLabel : '备  注'
        }]
        }]
         }]
        }]

			}]
		}), this.callParent(arguments);
	}
	,
	bodyStyle : "padding-top:15px;padding-left:15px;padding-right:15px"
});

function formatEffect(value) {
	return value == '1' ? '是' : '否';
}