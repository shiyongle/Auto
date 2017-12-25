
Ext.define('DJ.order.saleOrder.SaleOrderEdit', {
	extend : 'Ext.c.BaseEditUI',
	id:'DJ.order.saleOrder.SaleOrderEdit',
	modal : true,
	title : "订单编辑界面",
	width : 380,// 230, //Window宽度
	height : 350,// 137, //Window高度
	resizable : false,
	url : 'SaveSaleOrder.do',
	infourl:'GetSaleOrderInfo.do',
	viewurl:'GetSaleOrderInfo.do', 
	closable : true, // 关闭按钮，默认为true
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
//		var cform=this.getform().getForm();
//		cform.findField('fnumber').setReadOnly(true);
//		cform.findField('fbizdate').setReadOnly(true);
	
	},

     layout:{
    	 type: 'vbox',
         align: 'stretch',
         pack:'center',
         defaultMargins: {top: 0, right: 0, bottom: 0, left:60}
     } ,
     
     initComponent : function() {
 		Ext.apply(this, {
    	 items:[{
//    		 readOnly:true,
    		
    		name:'fnumber',
     		fieldLabel : '订单编号',
     		xtype:'textfield',
     		 labelWidth: 80
 	   		} ,
    	        {
    	        		id : 'DJ.order.saleOrder.SaleOrderEdit.fcustomerid',
 	   		 			labelWidth: 80,
    	        		name:'fcustomerid',
    	        		fieldLabel : '客户名称',
    	        		xtype:'cCombobox',
    	        		displayField:'fname', // 这个是设置下拉框中显示的值
    	        	    valueField:'fid', // 这个可以传到后台的值
    	        	    allowBlank : false,
    	        	    blankText:'请选择客户',
    	        	    editable: false, // 可以编辑不
    	        	    listeners : {
								change : function(com) {
									Ext.getCmp("DJ.order.saleOrder.SaleOrderEdit.fproductdefid").setValue(null);
									Ext.getCmp("DJ.order.saleOrder.SaleOrderEdit.fcustproduct").setValue(null);
								}
							},
    	        	    MyConfig : {
    	 					width : 800,//下拉界面
    	 					height : 200,//下拉界面
    	 					url : 'GetCustomerList.do',  //下拉数据来源
    	 					fields : [ {
    	 						name : 'fid'
    	 					}, {
    	 						name : 'fname',
    	 						myfilterfield : 't_bd_customer.fname', //查找字段，发送到服务端
    	 						myfiltername : '客户名称',//在过滤下拉框中显示的名称
    	 						myfilterable : true//该字段是否查找字段
    	 					}, {
    	 						name : 'fnumber'
    	 					}, {
    	 						name : 'findustryid'
    	 					}, {
    	 						name : 'faddress'
    	 					}, {
    	 						name : 'fisinternalcompany',
    	 						convert:function(value,record)
    							{
    								if(value=='1')
    								{	
    									return true;
    								}else{
    									return false;
    								}	
    							}
    	 					} ],
    	 					columns : [ {
    	 						text : 'fid',
    							dataIndex : 'fid',
    							hidden : true,
    							sortable : true
    						}, {
    							text : '编码',
    							dataIndex : 'fnumber',
    							sortable : true
    						}, {
    							text : '客户名称',
    							dataIndex : 'fname',
    							sortable : true
    						}, {
    							text : '行业',
    							dataIndex : 'findustryid',
    							sortable : true
    						}, {
    							text : '地址',
    							dataIndex : 'faddress',
    							sortable : true,
    							width : 250
    						}, {
    							text : '内部客户',
    							dataIndex : 'fisinternalcompany',
    							xtype:'checkcolumn',
    							processEvent : function() {
    							},
    							sortable : true
    						}]
    	 				}
		    	   		},{
							valueField : 'fid', // 组件隐藏值
							id : "DJ.order.saleOrder.SaleOrderEdit.fproductdefid",
							name : "fproductdefid",
							xtype : 'cCombobox',
							displayField : 'fname',// 组件显示值
							fieldLabel : '产  品 ',
							allowBlank : false,
    	        	    	blankText:'请选择产品',
//							width : 260,
							labelWidth : 80,
							beforeExpand : function() {
								var customerid = Ext.getCmp("DJ.order.saleOrder.SaleOrderEdit.fcustomerid").getValue();//_combo.getValue();
    	        	    		Ext.getCmp("DJ.order.saleOrder.SaleOrderEdit.fproductdefid").setDefaultfilter([{
    								myfilterfield : "t_pdt_productdef.fcustomerid",
    								CompareType : "like",
    								type : "string",
    								value : customerid
    							}]);
    							Ext.getCmp("DJ.order.saleOrder.SaleOrderEdit.fproductdefid").setDefaultmaskstring(" #0 ");
							},
							listeners:{
								select:function( combo, records, eOpts ){
									Ext.Ajax.request({
										url:'getCustproductByProductid.do',
										params:{fproductid:records[0].get('fid')},
									success : function(response, option) {
										var obj = Ext.decode(response.responseText);
										if (obj.success == true) {
											Ext.getCmp('DJ.order.saleOrder.SaleOrderEdit.fcustproduct').setmyvalue("\"fid\":\""
											+ obj.data[0].fid + "\",\"fname\":\""
											+ obj.data[0].fname + "\"");
										} 
									}
									})
								}
							},
							MyConfig : {
								width : 800,// 下拉界面
								height : 200,// 下拉界面
								url : 'GetProductlist.do', // 下拉数据来源
								fields : [
											{
												name : 'fid'
											}, {
												name : 'fname',
												myfilterfield : 't_pdt_productdef.fname', 	// 查找字段，发送到服务端
												myfiltername : '名称',							// 在过滤下拉框中显示的名称
												myfilterable : true								// 该字段是否查找字段
											}, {
												name : 'fnumber',
												myfilterfield : 't_pdt_productdef.fnumber', 	// 查找字段，发送到服务端
												myfiltername : '编码',							// 在过滤下拉框中显示的名称
												myfilterable : true								// 该字段是否查找字段
											}, {
												name : 'fcreatorid'
											}, {
												name : 'fcreatetime'
											}, {
												name : 'flastupdateuserid'
											}, {
												name : 'flastupdatetime'
											}, {
												name : 'fcharacter'
											}, {
												name : 'fboxmodelid'
											}, {
												name : 'feffect'
											}, {
												name : 'fnewtype'
											}, {
												name : 'fversion'
											}, {
												name : 'fishistory'
											}
										],
								columns : [
								           	{
												'header' : 'fid',
												'dataIndex' : 'fid',
												hidden : true,
												hideable : false,
												sortable : true
											}, {
												'header' : '编号',
												'dataIndex' : 'fnumber',
												sortable : true
											}, {
												'header' : '名称',
												'dataIndex' : 'fname',
												sortable : true
											}, {
												'header' : '版本号',
												'dataIndex' : 'fversion',
												sortable : true
											}, {
												'header' : '特征',
												'dataIndex' : 'fcharacter',
												sortable : true,
												width : 100
											}, {
												'header': '箱型',
												'dataIndex' : 'fboxmodelid',
												sortable : true
												
											}, {
												'header' : '类型',
												'dataIndex' : 'fnewtype',
												sortable : true
											}, {
												'header' : '历史版本',
												'dataIndex' : 'fishistory',
												renderer:formatEffect,
												sortable : true
											}, {
												'header' : '禁用或启用',
												'dataIndex' : 'feffect',
												renderer:formatEffect,
												sortable : true
											},{
												'header' : '修改时间',
												'dataIndex' : 'flastupdatetime',
												width : 150,
												sortable : true
											
											}, {
												'header' : '创建时间',
												'dataIndex' : 'fcreatetime',
												width : 150,
												sortable : true
											}
										]
							}
						},{
			    	   		labelWidth: 80,
			    	   		id : "DJ.order.saleOrder.SaleOrderEdit.fcustproduct",
			    	   		name:'fcustproduct',
	    	        		fieldLabel : '客户产品名称',  	
//	    	        		allowBlank : false,
//			        		blankText:'请选择客户产品',
			        		editable: false, // 可以编辑不
	    	        		xtype:'cCombobox',
	    	        		displayField:'fname', // 这个是设置下拉框中显示的值
	    	        	    valueField:'fid', // 这个可以传到后台的值
	    	        	    beforeExpand : function() {
							
							var form= Ext.getCmp("DJ.order.saleOrder.SaleOrderEdit").getform().getForm();
							var customerid=form.findField('fcustomerid').getValue();//_combo.getValue();
	        	    		if(customerid=='null'||customerid=='')
	        	    		{
	        	    			customerid="%";
	        	    		}
	        	    		form.findField('fcustproduct').setDefaultfilter([{
								myfilterfield : "fcustomerid",
								CompareType : "like",
								type : "string",
								value : customerid
							}]);
							form.findField('fcustproduct').setDefaultmaskstring(" #0 ");
	        	    		
    	        	    }
						,

    	        	     MyConfig : {
    	 					width : 800,//下拉界面
    	 					height : 200,//下拉界面
    	 					url : 'GetCustproductList.do',  //下拉数据来源
    	 					fields : [ {
    	 						name : 'fid'
    	 					}, {
    	 						name : 'fname',
    	 						myfilterfield : 'fname', //查找字段，发送到服务端
    	 						myfiltername : '客户产品编码',//在过滤下拉框中显示的名称
    	 						myfilterable : true//该字段是否查找字段
    	 					}, {
    	 						name : 'fnumber',
    	 						myfilterfield : 'fnumber', //查找字段，发送到服务端
    	 						myfiltername : '客户产品编码',//在过滤下拉框中显示的名称
    	 						myfilterable : true//该字段是否查找字段
    	 					}, {
    	 						name : 'fspec'
    	 					}, {
    	 						name : 'forderunit'
////    	 					}, {
////    	 						name : 'fcustomerid'
    	 					}, {
    	 						name : 'fdescription'
////    	 					}, {
////    	 						name : 'fcreatorid'
    	 					}, {
    	 						name : 'fcreatetime'
////    	 					}, {
////    	 						name : 'flastupdateuserid'
    	 					}, {
    	 						name : 'flastupdatetime'
    	 					}],
    	 					columns : [ Ext.create('DJ.Base.Grid.GridRowNum'), 
    	 					            {
    	 									'header' : 'fid',
    	 									'dataIndex' : 'fid',
    	 									hidden : true,
    	 									hideable : false,
    	 									autoHeight: true, autoWidth:true,
    	 									sortable : true
    	 								}, {
    	 									'header' : '产品名称',
    	 									'dataIndex' : 'fname',
    	 									sortable : true
    	 									
    	 								}, {
    	 									'header' : '编码',
    	 									'dataIndex' : 'fnumber',
    	 									sortable : true
    	 									
    	 								}, {
    	 									'header' : '规格',
    	 									width : 70,
    	 									'dataIndex' : 'fspec',
    	 									sortable : true
    	 								}, {
    	 									'header' : '单位',
    	 									width : 70,
    	 									'dataIndex' : 'forderunit',
    	 									sortable : true
    	 								}, {
    	 									'header' : '客户',
    	 									hidden : true,
    	 									'dataIndex' : 'fcustomerid',
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
    	 								}, {
    	 									'header' : '描述',
    	 									hidden : true,
    	 									'dataIndex' : 'fdescription',
    	 									sortable : true
    	 								}
    	 							]
    	 				}
		    	   		
		    	   		
		    	   		}, {
    	        	  name:'famount',
    	        	  labelWidth:80,
    	        		fieldLabel : '数量',
    	        		 xtype:'numberfield',
	    	        		 value:0,
		    	        	 minValue:1,
		    	        	 minText:'请填写大于0的值',
	    	        		 negativeText :'不能为负数',
	    	        		 allowBlank:false
    	          },Ext.create('Ext.ux.form.DateTimeField',{
	        			fieldLabel:'交期',  
	        			 labelWidth: 80,
	        			allowBlank : false,
			        	blankText:'交期不能为空',
	                    name: 'farrivetime',
	                    format:'Y-m-d',
	                    validator:function(value)
	                    {
	                    	if(value instanceof Date){
	                    	        value= Ext.Date.format(value,"Y-m-d H:i:s.u"); 
	                    	}
	                    	var pform=this.ownerCt;
	                    	var v1=pform.items.get(6).value;
	                    	if(value==null||v1==null)
	                    	{
	                    		return true;
	                    	}
	                    	if(v1 instanceof Date){
                    	        v1= Ext.Date.format(v1,"Y-m-d H:i:s.u"); 
                    	    }
	                        return value>v1?true:'请选择大于业务时间的时间';
	                    }
    	          }),
	                    Ext.create('Ext.ux.form.DateTimeField',{
		        			fieldLabel:'业务时间',  
		                    name: 'fbizdate',
		                    labelWidth: 80,
		                    format:'Y-m-d'
//		                    value: new Date()
		                   
	                    }),
    	          {
    	        	  
    	        	  name:'fid',
    	        	  xtype:'hidden'
    	        		  
    	          }
    	          , {
    	          		labelWidth: 80,
						name : 'fdescription',
						xtype : 'textareafield',
						fieldLabel : '备  注'
					}
					]
 				
 		}), this.callParent(arguments);
 	},
 	listeners:{
 		'beforeshow':function(win)
 		{
 			
 				
 			var cform=win.getform().getForm();
 			if(win.editstate=='add')
 				{
 				cform.findField('fbizdate').setValue(new Date());
 				cform.findField('fnumber').hide();
 				}
 			
 			cform.findField('fnumber').setReadOnly(true);
 			cform.findField('fbizdate').setReadOnly(true);
 			
 		}
 	}
	});
	
	function formatEffect(value){        
    return value=='1'?'是':'否';  
}