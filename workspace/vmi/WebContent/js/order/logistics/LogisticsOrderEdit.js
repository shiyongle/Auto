var selectF = function(com,records){
//		com.up('form').down('combobox[name=flinkman]').setValue(records[0].get('flinkman'));
//		com.up('form').down('combobox[name=fphone]').setValue(records[0].get('fphone'));
//		com.up('form').down('combobox[name=fcargoaddress]').setValue(records[0].get('fname'));
//		com.up('form').down('combobox[name=fcargoaddress]').store.load()
//		com.up('form').down('combobox[name=flinkman]').store.load();
//		com.up('form').down('combobox[name=fphone]').store.load();
}
var comSelect = function(com,records){
	  
//		com.up('form').down('combobox[name=frecipientsname]').setValue(records[0].get('flinkman'));
//		com.up('form').down('combobox[name=frecipientsphone]').setValue(records[0].get('fphone'));
//		com.up('form').down('combobox[name=frecipientsaddress]').setValue(records[0].get('fname'));
		com.up('form').down('combobox[name=frecipientsname]').store.load();
		com.up('form').down('combobox[name=frecipientsphone]').store.load();
}
var comFocus = function(com){
	com.expand();
}
Ext.define("DJ.order.logistics.LogisticsOrderEdit",{
	id:'DJ.order.logistics.LogisticsOrderEdit',
	extend:'Ext.form.Panel',
	title:'我的发货',
	minHeight : 450,
	url : "saveOrUpdateLogisticsOrder.do",
	ctype:'Logisticsorder',
	closable:true,
	bodyPadding:'30',
	requires:['Ext.ux.form.DateTimeField'],
	listeners:{
		afterrender:function(me){
			var div = document.getElementById(me.id);
			var fieldset =div.querySelectorAll('fieldset');
			Ext.each(fieldset,function(e){
				e.setAttribute('style','border-width:2px 2px 2px 2px;');
			})
			var com = me.query('combobox');
			Ext.each(com,function(c){
				document.getElementById(c.id).addEventListener('click',function(){
					c.expand();
				})
				c.onItemClick= function(picker, record){
		        /*
		         * If we're doing single selection, the selection change events won't fire when
		         * clicking on the selected element. Detect it here.
		         */
		        var me = this,
		            selection = me.picker.getSelectionModel().getSelection(),
		            valueField = me.valueField;
		
		        if (!me.multiSelect && selection.length) {
		            if (record.get(valueField) === selection[0].get(valueField)) {
		                // Make sure we also update the display value if it's only partial
		                me.displayTplData = [record.data];
		                me.setRawValue(me.getDisplayValue());
		                me.collapse();
//		                me.fireEvent('select', me,[record],this );
		            }
		        }
		    }
			})
			Ext.Ajax.request({
				url:'getDefaultLogisticsInfo.do',
				success:function(response){
					var obj = Ext.decode(response.responseText);
					if(obj.success==true){
						if(!Ext.isEmpty(obj.data[0].fname)){
						me.down('combobox[name=fcargoaddress]').setValue(obj.data[0].fname);
						me.down('combobox[name=flinkman]').setValue(obj.data[0].flinkman);
						me.down('combobox[name=fphone]').setValue(obj.data[0].fphone);
						}
						if(!Ext.isEmpty(obj.data[1].fname)){
						me.down('combobox[name=frecipientsaddress]').setValue(obj.data[1].fname);
						me.down('combobox[name=frecipientsname]').setValue(obj.data[1].flinkman);
						me.down('combobox[name=frecipientsphone]').setValue(obj.data[1].fphone);
						}
						if(!Ext.isEmpty(obj.data[2].fname)){
						me.down('combobox[name=fcargotype]').setValue(obj.data[2].flinkman);
						me.down('combobox[name=fcartype]').setValue(obj.data[2].fname);
						}
					}else{
						Ext.Msg.alert('提示',obj.msg);
					}
				}
			})
		}
	},
	layout:{
//		align: 'middle',
        pack: 'center',
        type: 'hbox'
	},
	items:[{
				xtype: 'container',
				itemId: 'container1',
				width:700,
				items: [{
				xtype : 'fieldset',
				title : '<b>送货信息 </b>',
				collapsible : false,
				layout:{
					type:'vbox'
				},
				fieldDefaults : {
					labelAlign : 'right',
					labelWidth : 80,
					margin:'20 15 20 20',
					width:300
				},
				items : [{
					layout:{
						type:'column'
					},
					baseCls : "x-plain",
					items:[{
						columnWidth: 0.5,
						baseCls : "x-plain",
						items:[{
							name : 'fcargotype',
							xtype : 'combobox',
							fieldLabel : '货物类型',
							id:'LogisticsOrderEdit.fcargotype',
							displayField: 'fcargotype',
    						valueField: 'fcargotype',
    						queryMode: 'local',
    						hideTrigger : true,
    						store: Ext.create('Ext.data.Store', {
							    fields: ['fid', 'fcargotype','fcartype','flasted'],
							     proxy: {
							         type: 'ajax',
							         url: 'GetLogisticsCargoTypeList.do',
							         reader: {
							             type: 'json',
							             root: 'data'
							         }
							     }
							}),
							listeners:{
								afterrender:function(com){
									com.store.load(function(records,store){
									if(records.length===1){
							     			com.setValue(records[0].get('fcargotype'));
							     		}
									});
								},
								select:function(com,records){
								}
//								,
//								expand:function(com){
//								com.store.load();
//								}
							}
						},{
							name : 'fcustomerid',
							xtype : 'textfield',
							hidden:true
						},{
							name : 'faddressid',
							xtype : 'textfield',
							hidden:true
						}]
					},{
						columnWidth: 0.5,
						baseCls : "x-plain",
						items:[{
							name : 'fcartype',
							xtype : 'combobox',
							fieldLabel : '所需车型',
							id:'LogisticsOrderEdit.fcartype',
							displayField: 'fcartype',
    						valueField: 'fcartype',
    						queryMode: 'local',
    						hideTrigger : true,
    						store: Ext.create('Ext.data.Store', {
							    fields: ['fid', 'fcargotype','fcartype'],
							     proxy: {
							         type: 'ajax',
							         url: 'GetLogisticsCarTypeList.do',
							         reader: {
							             type: 'json',
							             root: 'data'
							         }
							     }
//							     ,
//							     autoLoad:true
							}),
							listeners:{
								afterrender:function(com){
									com.store.load(function(records,store){
									if(records.length===1){
							     			com.setValue(records[0].get('fcartype'));
							     		}
									});
								}
								,
								select:function(com,records){
//									com.up('form').down('combobox[name=fcargotype]').setValue(records[0].get('fcargotype'));
								}
							}
						}]
					}]
				},{
						name : 'fcargoaddress',
						xtype : 'combobox',
						displayField: 'fname',
						valueField: 'fname',
						queryMode: 'local',
//						tiggerAction:'all',
						store: Ext.create('Ext.data.Store', {
							    fields: ['fid', 'fname','flinkman','fphone'],
							     proxy: {
							         type: 'ajax',
							         url: 'GetLogisticsAddressFnameList.do',
							         reader: {
							             type: 'json',
							             root: 'data'
							         }
							     }, 
							     listeners:{
				     	    		load:function(store,records){
				     	    				var edit = Ext.getCmp('DJ.order.logistics.LogisticsOrderEdit');
							     		if(records.length===1){
							     			edit.down('combobox[name=fcargoaddress]').setValue(records[0].get('fname'));
							     		}
							     	}
//							     	,
//							     	beforeload:function( store, operation, eOpts ){
//										var edit = Ext.getCmp('DJ.order.logistics.LogisticsOrderEdit');
//							     		var fphone = edit.down('combobox[name=fphone]').getRawValue();
//							     		var flinkman = edit.down('combobox[name=flinkman]').getRawValue();
//		
//							     			  var myfilter = [];
//							     			  if(!Ext.isEmpty(fphone)){
//												myfilter.push({	myfilterfield : "fphone",CompareType : " = ",type : "string",value : fphone});
//							     			  }
//							     			 if(!Ext.isEmpty(flinkman)){
//							     				 myfilter.push({myfilterfield : "flinkman",CompareType : " = ",type : "string",value : flinkman});
//							     			 }
//							     			store.getProxy().setExtraParam('Defaultfilter',myfilter.length>0?Ext.encode(myfilter):"");
//							     		
//							     			store.getProxy().setExtraParam('Defaultmaskstring',myfilter.length>0?'( #0 ' +(myfilter.length>1? 'and #1 )':')'):"");
//							     	}
							     }
						}),
						allowBlank :false,
						listeners:{
								select:function(com,records){
										com.up('form').down('combobox[name=flinkman]').store.load();
										com.up('form').down('combobox[name=fphone]').store.load();
								}
								,
								afterrender:function(com){
									com.store.load();
								}
						},
						fieldLabel : '<font color="red">*</font> 货物位置',
						margin:'0 10 0 20',
						width:635
					},{
					layout:{
						type:'column'
					},
					baseCls : "x-plain",
					items:[{
						columnWidth: 0.5,
						baseCls : "x-plain",
						items:[{
							name : 'flinkman',
							xtype : 'combobox',
							fieldLabel : '发货人',
							displayField: 'flinkman',
    						valueField: 'flinkman',
    						queryMode: 'local',
    						autoSelect:false,
//    						tiggerAction:'all',
    						store: Ext.create('Ext.data.Store', {
							    fields: ['flinkman'],
							     proxy: {
							         type: 'ajax',
							         url: 'GetLogisticsAddressFlinkmanList.do',
							         reader: {
							             type: 'json',
							             root: 'data'
							         }
							     },
							     listeners:{
							     	load:function(store,records){
							     	var edit = Ext.getCmp('DJ.order.logistics.LogisticsOrderEdit');
							     		if(records.length===1){
							     			edit.down('combobox[name=flinkman]').setValue(records[0].get('flinkman'));
							     		}
							     		
							     	}
//							     	,
//							     	beforeload:function( store, operation, eOpts ){
//							     		var edit = Ext.getCmp('DJ.order.logistics.LogisticsOrderEdit');
//							     		var fcargoaddress = edit.down('combobox[name=fcargoaddress]').getRawValue();
//							     		var fphone = edit.down('combobox[name=fphone]').getRawValue();
//							     			  var myfilter = [];
//							     			  if(!Ext.isEmpty(fcargoaddress)){
//												myfilter.push({	myfilterfield : "fname",CompareType : " = ",type : "string",value : fcargoaddress});
//							     			  }
//							     			 if(!Ext.isEmpty(fphone)){
//							     				 myfilter.push({myfilterfield : "fphone",CompareType : " = ",type : "string",value : fphone});
//							     			 }
//							     		store.getProxy().setExtraParam('Defaultfilter',myfilter.length>0?Ext.encode(myfilter):"");
//								     	store.getProxy().setExtraParam('Defaultmaskstring',myfilter.length>0?'( #0 ' +(myfilter.length>1? 'and #1 )':')'):"");
//							     	}
							     }
							     
							}),
							listeners:{
								select:function(com,records){
								com.up('form').down('combobox[name=fcargoaddress]').store.load();
									com.up('form').down('combobox[name=fphone]').store.load();
								}
								,
								afterrender:function(com){
									com.store.load();
								}

							}
						}]
					},{
						columnWidth: 0.5,
						baseCls : "x-plain",
						items:[{
							name : 'fphone',
							xtype : 'combobox',
							allowBlank :false,
							displayField: 'fphone',
    						valueField: 'fphone',
    						queryMode: 'local',
//    						tiggerAction:'all',
    						autoSelect:false,
    						store: Ext.create('Ext.data.Store', {
							    fields: ['fphone'],
							     proxy: {
							         type: 'ajax',
							         url: 'GetLogisticsAddressFphoneList.do',
							         reader: {
							             type: 'json',
							             root: 'data'
							         }
							     },
							     listeners:{
				     	    		load:function(store,records){
				     	    			var edit = Ext.getCmp('DJ.order.logistics.LogisticsOrderEdit');
							     		if(records.length===1){
							     			edit.down('combobox[name=fphone]').setValue(records[0].get('fphone'));
							     		}
							     	}
//							     	,
//							     	beforeload:function( store, operation, eOpts ){
//							     		
//										var edit = Ext.getCmp('DJ.order.logistics.LogisticsOrderEdit');
//							     		var fcargoaddress = edit.down('combobox[name=fcargoaddress]').getRawValue();
//							     		var flinkman = edit.down('combobox[name=flinkman]').getRawValue();
//							     			  var myfilter = [];
//							     			  if(!Ext.isEmpty(fcargoaddress)){
//												myfilter.push({	myfilterfield : "fname",CompareType : " = ",type : "string",value : fcargoaddress});
//							     			  }
//							     			 if(!Ext.isEmpty(flinkman)){
//							     				 myfilter.push({myfilterfield : "flinkman",CompareType : " = ",type : "string",value : flinkman});
//							     			 }
//							     	store.getProxy().setExtraParam('Defaultfilter',myfilter.length>0?Ext.encode(myfilter):"");
//									store.getProxy().setExtraParam('Defaultmaskstring',myfilter.length>0?'( #0 ' +(myfilter.length>1? 'and #1 )':')'):"");
//							     }
							     }
						}),
    						listeners:{
								select:function(com,records){
										com.up('form').down('combobox[name=fcargoaddress]').store.load();
										com.up('form').down('combobox[name=flinkman]').store.load();
								}
								,
								afterrender:function(com){
									com.store.load();
								}

							
							},
							fieldLabel : '<font color="red">*</font> 发货人电话'
						}]
					}]
				}]
				},{
					xtype: 'component',
					style: 'margin-top:10px;margin-bottom:10px;',
//					hidden:true,
					html: '<div style="border-bottom: 1px solid white;padding-bottom:10px;"></div>'
				},{
					xtype : 'fieldset',
					title : '<b>收货信息</b>',
					name : 'deliverapply',
					collapsible : false,
					layout:{
						type:'vbox'
					},
					fieldDefaults : {
						labelAlign : 'right',
						labelWidth : 80,
//						margin:'20 15 20 20',
						width:300
					},
					items : [{
						name : 'frecipientsaddress',
						xtype : 'combobox',
						allowBlank :false,
						fieldLabel : '<font color="red">*</font> 收货地址',
						margin:'20 15 0 20',
						width:635,
						displayField: 'fname',
						valueField: 'fname',
						queryMode: 'local',
						store: Ext.create('Ext.data.Store', {
							    fields: ['fid', 'fname','flinkman','fphone'],
							     proxy: {
							         type: 'ajax',
							         url: 'GetConsigneeAddressFnameList.do',
							         reader: {
							             type: 'json',
							             root: 'data'
							         }
							     },
					 listeners:{
							     	load:function(store,records){
							     	var edit = Ext.getCmp('DJ.order.logistics.LogisticsOrderEdit');
							     		if(records.length===1){
							     			edit.down('combobox[name=frecipientsaddress]').setValue(records[0].get('fname'));
							     		}
							     		
							     	}
							     	//,
//							     	beforeload:function( store, operation, eOpts ){
//							     		var edit = Ext.getCmp('DJ.order.logistics.LogisticsOrderEdit');
//							     		var frecipientsname = edit.down('combobox[name=frecipientsname]').getRawValue();
//							     		var frecipientsphone = edit.down('combobox[name=frecipientsphone]').getRawValue();
//							     			  var myfilter = [];
//							     			  if(!Ext.isEmpty(frecipientsname)){
//												myfilter.push({	myfilterfield : "flinkman",CompareType : " = ",type : "string",value : frecipientsname});
//							     			  }
//							     			 if(!Ext.isEmpty(frecipientsphone)){
//							     				 myfilter.push({myfilterfield : "fphone",CompareType : " = ",type : "string",value : frecipientsphone});
//							     			 }
//							     		store.getProxy().setExtraParam('Defaultfilter',myfilter.length>0?Ext.encode(myfilter):"");
//								     	store.getProxy().setExtraParam('Defaultmaskstring',myfilter.length>0?'( #0 ' +(myfilter.length>1? 'and #1 )':')'):"");
//							     	}
							     }
							     
							}),
							listeners:{
								select:function(com,records){
									com.up('form').down('combobox[name=frecipientsname]').store.load(	);
										com.up('form').down('combobox[name=frecipientsphone]').store.load();
								}
								,
								afterrender:function(com){
									com.store.load();
								}


							}
					},{
					layout:{
						type:'column'
					},
					baseCls : "x-plain",
					items:[{
						columnWidth: 0.5,
						baseCls : "x-plain",
						items:[{
							name : 'frecipientsid',
							xtype:'textfield',
							hidden:true
						},{
							margin:'20 15 20 20',
							name : 'frecipientsname',
							xtype : 'combobox',
							fieldLabel : '收货人',
							displayField: 'flinkman',
							valueField: 'flinkman',
							queryMode: 'local',
							store: Ext.create('Ext.data.Store', {
							    fields: ['fid', 'fname','flinkman','fphone','flasted'],
							     proxy: {
							         type: 'ajax',
							         url: 'GetConsigneeAddressFlinknameList.do',
							         reader: {
							             type: 'json',
							             root: 'data'
							         }
							     		     },
					 	listeners:{
							     	load:function(store,records){
							     	var edit = Ext.getCmp('DJ.order.logistics.LogisticsOrderEdit');
							     		if(records.length===1){
							     			edit.down('combobox[name=frecipientsname]').setValue(records[0].get('flinkman'));
							     		}
							     		
							     	}
//							     	,
//							     	beforeload:function( store, operation, eOpts ){
//							     		var edit = Ext.getCmp('DJ.order.logistics.LogisticsOrderEdit');
//							     		var frecipientsaddress = edit.down('combobox[name=frecipientsaddress]').getRawValue();
//							     		var frecipientsphone = edit.down('combobox[name=frecipientsphone]').getRawValue();
//							     			  var myfilter = [];
//							     			  if(!Ext.isEmpty(frecipientsaddress)){
//												myfilter.push({	myfilterfield : "fname",CompareType : " = ",type : "string",value : frecipientsaddress});
//							     			  }
//							     			 if(!Ext.isEmpty(frecipientsphone)){
//							     				 myfilter.push({myfilterfield : "fphone",CompareType : " = ",type : "string",value : frecipientsphone});
//							     			 }
//							     		store.getProxy().setExtraParam('Defaultfilter',myfilter.length>0?Ext.encode(myfilter):"");
//								     	store.getProxy().setExtraParam('Defaultmaskstring',myfilter.length>0?'( #0 ' +(myfilter.length>1? 'and #1 )':')'):"");
//							     	}
							     }
							     
							}),
							listeners:{
								select:function(com,records){
										com.up('form').down('combobox[name=frecipientsaddress]').store.load();
										com.up('form').down('combobox[name=frecipientsphone]').store.load();
								}
								,
								afterrender:function(com){
									com.store.load();
								}


							
							}
						},{
						 xtype:'fieldcontainer',
			    	     fieldLabel : '<font color="red">*</font> 送达时间',
			    	     margin:'20 15 20 20',
			    	     width:300,
			    	     labelWidth : 80,
		        	     layout: {
                                type: 'column'
                            },
		        	     items:[{
		        	     	columnWidth: 0.5,
		        	     	xtype:'datefield',
							width:120,
							name : 'farrivedate',
							format : 'Y-m-d',
							allowBlank : false,
							blankText : '配送时间不能为空',
							hideLabel: true,
							margin : '0 10 0 0'
		        	     },{
		        	     	columnWidth: 0.25,
                            xtype: 'radiofield',
                            boxLabel: '上午',
                            inputValue: '0',
                            name : 'farrivetime',
                            margin : '0 5 0 0'
                        },
                        {
                        	columnWidth: 0.25,
                            xtype: 'radiofield',
                            boxLabel: '下午',
                            inputValue: '1' ,
                            checked: true,
                            name : 'farrivetime',
                            margin : '0 5 0 0'
						
		        	     }]
					}]
					},{
						columnWidth: 0.5,
						baseCls : "x-plain",
						items:[{
							margin:'20 15 20 20',
							name : 'frecipientsphone',
							xtype : 'combobox',
							allowBlank :false,
							fieldLabel : '<font color="red">*</font> 收货人电话',
							displayField: 'fphone',
							valueField: 'fphone',
							queryMode: 'local',
							store:Ext.create('Ext.data.Store', {
							    fields: ['fid', 'fname','flinkman','fphone'],
							     proxy: {
							         type: 'ajax',
							         url: 'GetConsigneeAddressFphoneList.do',
							         reader: {
							             type: 'json',
							             root: 'data'
							         }
							     },
						listeners:{
							     	load:function(store,records){
							     	var edit = Ext.getCmp('DJ.order.logistics.LogisticsOrderEdit');
							     		if(records.length===1){
							     			edit.down('combobox[name=frecipientsphone]').setValue(records[0].get('fphone'));
							     		}
							     		
							     	}
//							     	,
//							     	beforeload:function( store, operation, eOpts ){
//							     		var edit = Ext.getCmp('DJ.order.logistics.LogisticsOrderEdit');
//							     		var frecipientsaddress = edit.down('combobox[name=frecipientsaddress]').getRawValue();
//							     		var frecipientsname = edit.down('combobox[name=frecipientsname]').getRawValue();
//							     			  var myfilter = [];
//							     			  if(!Ext.isEmpty(frecipientsaddress)){
//												myfilter.push({	myfilterfield : "fname",CompareType : " = ",type : "string",value : frecipientsaddress});
//							     			  }
//							     			 if(!Ext.isEmpty(frecipientsname)){
//							     				 myfilter.push({myfilterfield : "flinkman",CompareType : " = ",type : "string",value : frecipientsname});
//							     			 }
//							     		store.getProxy().setExtraParam('Defaultfilter',myfilter.length>0?Ext.encode(myfilter):"");
//								     	store.getProxy().setExtraParam('Defaultmaskstring',myfilter.length>0?'( #0 ' +(myfilter.length>1? 'and #1 )':')'):"");
//							     	}
							     }
							     
							}),
							listeners:{
								select:function(com,records){
										com.up('form').down('combobox[name=frecipientsaddress]').store.load();
										com.up('form').down('combobox[name=frecipientsname]').store.load(	);
								}
								,
								afterrender:function(com){
									com.store.load();
								}


							
							}
						
						}]
					}]
				},{
						name : 'fdescription',
						xtype : 'textareafield',
						fieldLabel : '备注',
						margin:'0 10 15 20',
						width:635
					}]
				},{
					baseCls : "x-plain",
					buttons: [{
						xtype:'tbfill'
					},{ 
						text: '提交',
						handler:function(){
							var form = this.up('form');
							var myDate = new Date();
							var hours=form.getValues().farrivetime==0?0:12;
							var farrivedate = new Date(form.getValues().farrivedate).setHours(hours);//送达时间 .setHours(7)
							if(myDate.getTime()>=farrivedate){
								if(myDate.getHours()<12){
									Ext.Msg.alert('提示','为方便安排车辆，送达时间请填今日下午及之后的时间。');
									return;
								}else {
									Ext.Msg.alert('提示','为方便安排车辆，送达时间请填明日上午及之后的时间。');
									return;
								}
							}
							
							if (form.isValid()) {
					            form.submit({
					            	params:{
					            		Logisticsorder:Ext.encode(form.getValues())
					            	},
					                success: function(forms, action) {
					                	var obj = Ext.decode(action.response.responseText);
					                	if(obj.success==true){
					                		var f = form.down('fieldset[title*=收货信息]');
											Ext.each(f.query('textfield'),function(e){
												e.setValue('');
											})
											Ext.each(form.query('combobox'),function(com){
												com.store.load();
											})
						                   var message = Ext.Msg.alert('成功', obj.msg);
						                   var task = new Ext.util.DelayedTask(function(){
												    message.close();
												});
											task.delay(2000);//2秒后关闭提示框
											
					                	}else{
					                		Ext.Msg.alert('错误', action.result.msg);
					                	}
					                },
					                failure: function(form, action) {
					                    Ext.Msg.alert('错误', action.result.msg);
					                }
					            });
					        }else{
					        	 Ext.Msg.alert('错误', '请完善信息！');
					        }
						}
					},{
						xtype:'tbspacer'
					},{
						text: '关闭',
						handler:function(){
							this.up('form').close();
						}
					},{
						xtype:'tbfill'
					}]
				}]
	}]

})