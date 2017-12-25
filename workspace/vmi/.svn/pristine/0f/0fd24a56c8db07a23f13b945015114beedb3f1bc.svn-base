var selectF = function(com,records){
		com.up('form').down('textfield[name=faddressid]').setValue(records[0].get('fid'));
		com.up('form').down('combobox[name=flinkman]').setValue(records[0].get('flinkman'));
		com.up('form').down('combobox[name=fphone]').setValue(records[0].get('fphone'));
		com.up('form').down('combobox[name=fcargoaddress]').setValue(records[0].get('fname'));
}
var comSelect = function(com,records){
	  
		com.up('form').down('textfield[name=frecipientsid]').setValue(records[0].get('fid'));
		com.up('form').down('combobox[name=frecipientsname]').setValue(records[0].get('flinkman'));
		com.up('form').down('combobox[name=frecipientsphone]').setValue(records[0].get('fphone'));
		com.up('form').down('combobox[name=frecipientsaddress]').setValue(records[0].get('fname'));
}
var comFocus = function(com){
	com.expand();
}
Ext.define("DJ.order.logistics.LogisticsOrderWinEdit",{
	id:'DJ.order.logistics.LogisticsOrderWinEdit',
	extend:'Ext.c.BaseEditUI',
	title:'订单详情',
	minHeight : 450,
	height:670,
	width:770,
	autoScroll : true,
	infourl : "getLogisticsOrderInfo.do",
	viewurl : "getLogisticsOrderInfo.do",
	url : "saveOrUpdateLogisticsOrder.do",
	ctype:'Logisticsorder',
	style:{
		backgroundColor:'white'
	},
	bodyStyle : 'background:white',
	closable:true,
	bodyPadding:'30',
	requires:['Ext.ux.form.DateTimeField'],
	onload:function(){
		this.down('toolbar').hide();
	},
	listeners:{
//		afterrender:function(me){
//			var div = document.getElementById(me.id);
//			var fieldset =div.querySelectorAll('fieldset');
//			Ext.each(fieldset,function(e){
//				e.setAttribute('style','border-width:2px 2px 2px 2px;');
//			})
//			var com = me.query('combobox');
//			if(me.editstate!='view'){
//				Ext.each(com,function(c){
//					document.getElementById(c.id).addEventListener('click',function(){
//						c.expand();
//					})
//				c.onItemClick= function(picker, record){
//		        /*
//		         * If we're doing single selection, the selection change events won't fire when
//		         * clicking on the selected element. Detect it here.
//		         */
//		        var me = this,
//		            selection = me.picker.getSelectionModel().getSelection(),
//		            valueField = me.valueField;
//		
//		        if (!me.multiSelect && selection.length) {
//		            if (record.get(valueField) === selection[0].get(valueField)) {
//		                // Make sure we also update the display value if it's only partial
//		                me.displayTplData = [record.data];
//		                me.setRawValue(me.getDisplayValue());
//		                me.collapse();
//		                me.fireEvent('select', me,[record],this );
//		            }
//		        }
//		    }
//				})
//				this.down('fieldset[title*=派车信息]').hide();
//			}
////			this.down('header').hide();
//			
//		},
		show:function(me){
			if(this.editstate=='view'){
				Ext.each(document.querySelectorAll('.x-mask'),function(mask){
					mask.setAttribute('style','display:none');
				})
				Ext.each(me.query('button'),function(button){
					if(button.text=='提交'){
						button.hide();
					}
					button.setDisabled(false);
				})
			}
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
							displayField: 'fcargotype',
    						valueField: 'fcargotype',
    						queryMode: 'local',
    						hideTrigger : true,
    						store: Ext.create('Ext.data.Store', {
							    fields: ['fid', 'fcargotype','fcartype','flasted'],
							     proxy: {
							         type: 'ajax',
							         url: 'GetLogisticsCarinfoList.do',
							         reader: {
							             type: 'json',
							             root: 'data'
							         }
							     },
							     sorters: { property: 'flasted', direction: 'DESC' },
							     autoLoad:true
							}),
							listeners:{
//								focus:comFocus,
								select:function(com,records){
									com.up('form').down('combobox[name=fcartype]').setValue(records[0].get('fcartype'));
								}
							}
						},{
							name : 'fcustomerid',
							xtype : 'textfield',
							hidden:true
						},{
							name : 'faddressid',
							xtype : 'textfield',
							hidden:true
						},{
							name : 'flinkman',
							xtype : 'combobox',
							fieldLabel : '发货人',
							displayField: 'flinkman',
    						valueField: 'flinkman',
    						queryMode: 'local',
    						store: Ext.create('Ext.data.Store', {
							    fields: ['fid', 'fname','flinkman','fphone','flasted'],
							     proxy: {
							         type: 'ajax',
							         url: 'GetLogisticsAddressList.do',
							         reader: {
							             type: 'json',
							             root: 'data'
							         }
							     },
							     sorters: { property: 'flasted', direction: 'DESC' },
							     autoLoad:true
							     
							}),
							listeners:{
								select:selectF
							}
						}]
					},{
						columnWidth: 0.5,
						baseCls : "x-plain",
						items:[{
							name : 'fcartype',
							xtype : 'combobox',
							fieldLabel : '所需车型',
							displayField: 'fcartype',
    						valueField: 'fcartype',
    						queryMode: 'local',
    						hideTrigger : true,
    						store: Ext.create('Ext.data.Store', {
							    fields: ['fid', 'fcargotype','fcartype'],
							     proxy: {
							         type: 'ajax',
							         url: 'GetLogisticsCarinfoList.do',
							         reader: {
							             type: 'json',
							             root: 'data'
							         }
							     },
							     autoLoad:true
							}),
							listeners:{
//								focus:comFocus,
								select:function(com,records){
									com.up('form').down('combobox[name=fcargotype]').setValue(records[0].get('fcargotype'));
								}
							}
						},{
							name : 'fphone',
							xtype : 'combobox',
							allowBlank :false,
							displayField: 'fphone',
    						valueField: 'fphone',
    						queryMode: 'local',
    						store: Ext.create('Ext.data.Store', {
							    fields: ['fid', 'fname','flinkman','fphone'],
							     proxy: {
							         type: 'ajax',
							         url: 'GetLogisticsAddressList.do',
							         reader: {
							             type: 'json',
							             root: 'data'
							         }
							     },
							     autoLoad:true
						}),
    						listeners:{
								select:selectF
							},
							fieldLabel : '<font color="red">*</font> 发货人电话'
						}]
					}]
				},{
						name : 'fcargoaddress',
						xtype : 'combobox',
						displayField: 'fname',
						valueField: 'fname',
						queryMode: 'local',
						store: Ext.create('Ext.data.Store', {
							    fields: ['fid', 'fname','flinkman','fphone'],
							     proxy: {
							         type: 'ajax',
							         url: 'GetLogisticsAddressList.do',
							         reader: {
							             type: 'json',
							             root: 'data'
							         }
							     },
							     autoLoad:true
						}),
						allowBlank :false,
						listeners:{
								select:selectF
						},
						fieldLabel : '<font color="red">*</font> 货物位置',
						margin:'0 10 20 20',
						width:635
					}]
				},{
					xtype: 'component',
					style: 'margin-top:10px;margin-bottom:10px;',
					hidden:true,
					html: '<div style="border-bottom: 1px solid #dfe8f6;padding-bottom:10px;"></div>'
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
							         url: 'GetConsigneeAddressList.do',
							         reader: {
							             type: 'json',
							             root: 'data'
							         }
							     },
							     sorters: { property: 'flasted', direction: 'DESC' },
							     autoLoad:true
						}),
						listeners:{
							select:comSelect
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
							         url: 'GetConsigneeAddressList.do',
							         reader: {
							             type: 'json',
							             root: 'data'
							         }
							     },
							       autoLoad:true
						}),
						listeners:{
							select:comSelect
						}
						}]
					}]
				},{
						name : 'frecipientsaddress',
						xtype : 'combobox',
						allowBlank :false,
						fieldLabel : '<font color="red">*</font> 收货地址',
						margin:'0 10 20 20',
						width:635,
						displayField: 'fname',
						valueField: 'fname',
						queryMode: 'local',
						store: Ext.create('Ext.data.Store', {
							    fields: ['fid', 'fname','flinkman','fphone'],
							     proxy: {
							         type: 'ajax',
							         url: 'GetConsigneeAddressList.do',
							         reader: {
							             type: 'json',
							             root: 'data'
							         }
							     },
							       autoLoad:true
						}),
						listeners:{
							select : comSelect
						}
					},{
						name : 'fdescription',
						xtype : 'textareafield',
						fieldLabel : '备注',
						margin:'0 10 15 20',
						width:635
					}]
				},{
					
					xtype : 'fieldset',
					title : '<b>派车信息</b>',
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
							name : 'fnumber',
							xtype : 'textfield',
							fieldLabel : '订单编号'
						},{
							 xtype:'textfield',
							 name : 'fstate',
				    	     fieldLabel : '订单状态',
				    	     width:300,
				    	     listeners:{
				    	     	change:function(field,newValue,oldValue){
				    	     		if(newValue==0){
				    	     			field.setRawValue("未接收");
				    	     		}else if(newValue==1){
				    	     			field.setRawValue("已指定车辆");
				    	     		}
				    	     	}
				    	     }
						},{
							 xtype:'textfield',
							 name : 'fdriver',
				    	     fieldLabel : '司机',
				    	     width:300
						},{
							 xtype:'textfield',
							 name : 'fcarnumber',
				    	     fieldLabel : '车牌号',
				    	     width:300
						}]
					},{
						columnWidth: 0.5,
						baseCls : "x-plain",
						items:[{
							name : 'fcreatetime',
							xtype : 'textfield',
							fieldLabel : '申请时间'
						},{
							name : 'fconveyingstate',
							xtype : 'textfield',
							fieldLabel : '运输状态',
							hidden:true
						},{
							name : 'fdriverphone',
							xtype : 'textfield',
							fieldLabel : '司机电话'
						},{
							name : 'fcost',
							xtype : 'textfield',
							fieldLabel : '运费'
						}]
					}]
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
									Ext.Msg.alert('提示','只能填今日下午及以后时间');
									return;
								}else {
									Ext.Msg.alert('提示','只能填明日上午及以后时间');
									return;
								}
							}
							this.up('window').down('button[text*=保]').handler();
//							if (form.isValid()) {
//					            form.submit({
//					            	params:{
//					            		Logisticsorder:Ext.encode(form.getValues())
//					            	},
//					                success: function(form, action) {
//					                   Ext.Msg.alert('Success', action.result.msg);
//					                },
//					                failure: function(form, action) {
//					                    Ext.Msg.alert('Failed', action.result.msg);
//					                }
//					            });
//					        }
						}
					},{
						xtype:'tbspacer'
					},{
						text: '关闭',
						handler:function(){
							this.up('window').close();
						}
					},{
						xtype:'tbfill'
					}]
				}]
	},{
		xtype:'textfield',
		name:'fid',
		hidden:true
	},{
		xtype:'textfield',
		name:'fcreater',
		hidden:true
	}]

})