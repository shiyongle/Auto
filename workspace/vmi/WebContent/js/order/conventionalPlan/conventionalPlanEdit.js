
Ext
		.define(
				'DJ.order.conventionalPlan.conventionalPlanEdit',
				{
					extend : 'Ext.c.BaseEditUI',
					id : "DJ.order.conventionalPlan.conventionalPlanEdit",
					modal : true,
					onload : function() {
					},
					custbar : [ 
//					            {
//									// id : 'DelButton',
//									text : '自定义按钮1',
//									height : 30,
//									handler : function() {
////										var a = Ext.getCmp('DJ.order.conventionalPlan.conventionalPlanEdit');
////										a.seteditstate("edit"); // 设置界面可编辑"view"和""为不可编辑，其他都是可以编辑
////										String str = Ext.getCmp('DJ.order.conventionalPlan.conventionalPlaneditfarrivetime').getValue();
////										str = str.substr(0,8);
////										Ext.getCmp('DJ.order.conventionalPlan.conventionalPlanEdit.farrivedate').setValue(Ext.getCmp('DJ.order.conventionalPlan.conventionalPlaneditfarrivetime').getValue().substr(0,10).replace("-","/").replace("-","/"));
//								}
//								}, 
								{
									text : '数据导入测试',
									height : 30,
									handler : function(){
										var me=Ext.Cmp("DJ.order.conventionalPlan.conventionalPlanEdit");
										var fid = "[{fplanbegintime:'2013-08-09 13:27:00',fplanendtime:'2013-08-09 14:25:00',fplanAmount:'1',fplanDelivery:'2013-08-09 16:00:28.0',fplanQty:'1',fcustomerName:'数据测试导入',fplanCycle:'1',fcusproduct:'数据测试导入'}]";
										var el = me.getEl();
											el.mask("系统处理中,请稍候……");
										Ext.Ajax.request({
													url : "addmonthplan.do",
													params : {
														order : fid
													}, // 参数
													success : function(response, option) {
														var obj = Ext.decode(response.responseText);
														if (obj.success == true) {
															  djsuccessmsg( obj.msg);
															Ext.getCmp("DJ.order.conventionalPlan.conventionalPlanList").store.load();
															Ext.getCmp("DJ.order.conventionalPlan.conventionalPlanEdit").close();
														} else {
															Ext.MessageBox.alert('错误', obj.msg);
														}
														el.unmask();
													}
												});
									}
								}
					],
					title : "用户管理编辑界面",
					width : 680,// 230, //Window宽度
					height : 260,// 137, //Window高度
					resizable : false,
					url : 'SaveConventionalplan.do',
					infourl : 'getConventionalplanInfo.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
					viewurl : 'getConventionalplanInfo.do', // 查看状态数据源
					closable : true, // 关闭按钮，默认为true
					initComponent : function() {
						Ext.apply(this, {
					items : [ {
						layout : "column",
						baseCls : "x-plain",
						items : [
								{// title:"列1",
									baseCls : "x-plain",
									columnWidth : .5,
									bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
									items : [
											{
												// id :
												// 'DJ.System.product.CustproductEdit.FID',
												name : 'fid',
												xtype : 'textfield',
												labelWidth : 80,
												width : 280,
												hidden : true
											},
											{
												// id :
												// 'DJ.System.product.CustproductEdit.FID',
												name : 'fcreatorid',
												xtype : 'textfield',
												labelWidth : 80,
												width : 280,
												hidden : true
											},
											{
												// id :
												// 'DJ.System.product.CustproductEdit.FID',
												name : 'fupdateuserid',
												xtype : 'textfield',
												labelWidth : 80,
												width : 280,
												hidden : true
											},
											{
												id : "DJ.order.conventionalPlan.conventionalPlanEdit.fcustomerName",
												name : 'fcustomerName',
												xtype : 'textfield',
												fieldLabel : '客  户',
//												allowBlank : false,
//												blankText : '客户名称不能为空',
												regex : /^([\u4E00-\u9FA5]|\w|[@.()\-])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
												regexText : "不能包含特殊字符",
												width : 280,
												labelWidth : 80
											},{
												id : "DJ.order.conventionalPlan.conventionalPlanEdit.fcustproductName",
												name : 'fcustproductName',
												xtype : 'textfield',
												fieldLabel : '客户产品',
//												allowBlank : false,
//												blankText : '客户产品名称不能为空',
												regex : /^([\u4E00-\u9FA5]|\w|[@.()\-])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
												regexText : "不能包含特殊字符",
												width : 280,
												labelWidth : 80
											},  {
							    	        	  name:'fplanQty',
							    	        	  labelWidth:80,
						    	        		  fieldLabel : '计划产量',
						    	        		  xtype:'numberfield',
						    	        		  width : 280,
												  labelWidth : 80,
						    	        		  value:1
//							    	        	  minValue:1,
//							    	        	  minText:'请填写大于0的值',
//						    	        		  negativeText :'不能为负数'		    	         	
							    	          },Ext.create('Ext.ux.form.DateTimeField',{
								        			fieldLabel:'预计交期',  
								        			labelWidth : 80,
								        			width : 280,
								                    name: 'fplanDelivery',
								                    format:'Y-m-d'
//								                    validator:function(value)
//								                    {
//								                    	if(value instanceof Date){
//								                    	        value= Ext.Date.format(value,"Y-m-d H:i:s.u"); 
//								                    	}
////								                    	var pform=this.ownerCt;
////								                    	var v1=pform.items.get(5).value;
//								                    	var v1 = new Date();
//								                    	if(value==null||v1==null)
//								                    	{
//								                    		return true;
//								                    	}
//								                    	if(v1 instanceof Date){
//							                    	        v1= Ext.Date.format(v1,"Y-m-d H:i:s.u"); 
//							                    	    }
//								                        return value>v1?true:'请选择大于业务时间的时间';
//								                    }
							    	          }),Ext.create('Ext.ux.form.DateTimeField',{
								        			fieldLabel:'计划开始时间',  
								        			labelWidth : 80,
								        			width : 280,
								                    name: 'fplanbegintime',
								                    format:'Y-m-d'
//								                    validator:function(value)
//								                    {
//								                    	if(value instanceof Date){
//								                    	        value= Ext.Date.format(value,"Y-m-d H:i:s.u"); 
//								                    	}
////								                    	var pform=this.ownerCt;
////								                    	var v1=pform.items.get(5).value;
//								                    	var v1 = new Date();
//								                    	if(value==null||v1==null)
//								                    	{
//								                    		return true;
//								                    	}
//								                    	if(v1 instanceof Date){
//							                    	        v1= Ext.Date.format(v1,"Y-m-d H:i:s.u"); 
//							                    	    }
//								                        return value>v1?true:'请选择大于业务时间的时间';
//								                    }
							    	          })
									]
								},
								{// title:"列2",
									baseCls : "x-plain",
									columnWidth : .5,bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
		  				items : [
									{
										valueField : 'fid', // 组件隐藏值
										id : "DJ.order.conventionalPlan.conventionalPlanEdit.fcustomerid",
										name : "fcustomerid",
										xtype : 'cCombobox',
										displayField : 'fname',// 组件显示值
										fieldLabel : '客  户',
										allowBlank : false,
										blankText : '客户不能为空',
										width : 280,
										labelWidth : 80,
										MyConfig : {
											width : 800,// 下拉界面
											height : 200,// 下拉界面
											url : 'GetCustomerList.do', // 下拉数据来源
											fields : [
											          	{
															name : 'fid'
														}, {
															name : 'fname',
															myfilterfield : 't_bd_customer.fname', 	// 查找字段，发送到服务端
															myfiltername : '名称',						// 在过滤下拉框中显示的名称
															myfilterable : true							// 该字段是否查找字段
														}, {
															name : 'fnumber',
															myfilterfield : 't_bd_customer.fnumber', 	// 查找字段，发送到服务端
															myfiltername : '编码',						// 在过滤下拉框中显示的名称
															myfilterable : true							// 该字段是否查找字段
														}, {
															name : 'fdescription'
														}, {
															name : 'fcreatetime'
														}, {
															name : 'findustryid'
														}, {
															name : 'fartificialperson'
														}, {
															name : 'fbizregisterno'
														}, {
															name : 'fisinternalcompany'
														}, {
															name : 'ftxregisterno'
														}, {
															name : 'fmnemoniccode'
														}, {
															name : 'faddress'
														}, {
															name : 'fusedstatus'
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
															'header' : '编码',
															'dataIndex' : 'fnumber',
															sortable : true
														}, {
															'header' : '客户名称',
															'dataIndex' : 'fname',
															sortable : true
														}, {
															'header' : '助记码',
															'dataIndex' : 'fmnemoniccode',
															sortable : true
														}, {
															'header' : '行业',
															'dataIndex' : 'findustryid',
															sortable : true
														}, {
															'header' : '地址',
															'dataIndex' : 'faddress',
															sortable : true,
															width : 250
														}, {
															'header' : '内部客户',
															'dataIndex' : 'fisinternalcompany',
															renderer : formatEffect,
															sortable : true
														}, {
															'header' : '税务登记号',
															'dataIndex' : 'ftxregisterno',
															sortable : true
														}, {
															'header' : '工商注册号',
															'dataIndex' : 'fbizregisterno',
															sortable : true
														}, {
															'header' : '法人代表',
															'dataIndex' : 'fartificialperson',
															sortable : true
														}, {
															'header' : '状态',
															'dataIndex' : 'fusedstatus',
															renderer : formatEffect,
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
										valueField : 'fid', // 组件隐藏值
										id : "DJ.order.conventionalPlan.conventionalPlanEdit.fcustProductid",
										name : "fcustProductid",
										xtype : 'cCombobox',
										displayField : 'fname',// 组件显示值
										fieldLabel : '客户产品 ',
										allowBlank : false,
										blankText : '客户产品不能为空',
										width : 280,
										labelWidth : 80,
										beforeExpand : function() {
											var customerid = Ext.getCmp("DJ.order.conventionalPlan.conventionalPlanEdit.fcustomerid").getValue();//_combo.getValue();
			    	        	    		Ext.getCmp("DJ.order.conventionalPlan.conventionalPlanEdit.fcustProductid").setDefaultfilter([{
			    								myfilterfield : "t_bd_Custproduct.fcustomerid",
			    								CompareType : "like",
			    								type : "string",
			    								value : customerid
			    							}]);
			    							Ext.getCmp("DJ.order.conventionalPlan.conventionalPlanEdit.fcustProductid").setDefaultmaskstring(" #0 ");
										},
										MyConfig : {
											width : 800,// 下拉界面
											height : 200,// 下拉界面
											url : 'GetCustproductList.do', // 下拉数据来源
											fields : [
													 	{
															name : 'fid'
														}, {
															name : 'fname',
															myfilterfield : 't_bd_Custproduct.fname', 		// 查找字段，发送到服务端
															myfiltername : '名称',							// 在过滤下拉框中显示的名称
															myfilterable : true								// 该字段是否查找字段
														}, {
															name : 'fnumber',
															myfilterfield : 't_bd_Custproduct.fnumber', 	// 查找字段，发送到服务端
															myfiltername : '编码',							// 在过滤下拉框中显示的名称
															myfilterable : true								// 该字段是否查找字段
														}, {
															name : 'fspec'
														}, {
															name : 'forderunit'
														}, {
															name : 'fcustomerid'
														}, {
															name : 'fdescription'
														}, {
															name : 'fcreatorid'
														}, {
															name : 'fcreatetime'
														}, {
															name : 'flastupdateuserid'
														}, {
															name : 'flastupdatetime'
														}
															
													],
											columns : [{
															'header' : 'fid',
															'dataIndex' : 'fid',
															hidden : true,
															hideable : false,
															autoHeight: true, autoWidth:true,
															sortable : true
														}, {
															'header' : '产品名称',
															'dataIndex' : 'fname',
															sortable : true,
															filter : {
																type : 'string'
															}
														}, {
															'header' : '编码',
															'dataIndex' : 'fnumber',
															sortable : true,
															filter : {
																type : 'string'
															}
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
															filter : {
																type : 'datetime',
																date: {
														            format: 'Y-m-d'
														        },
														        time: {
														            format: 'H:i:s A',
														            increment: 1
														        }
															},
															width : 140,
															sortable : true
														}, {
															'header' : '创建时间',
															'dataIndex' : 'fcreatetime',
															filter : {
																type : 'datetime',
																date: {
														            format: 'Y-m-d'
														        },
														        time: {
														            format: 'H:i:s A',
														            increment: 1
														        }
															},
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
					    	        	  name:'fplanCycle',
					    	        	  width : 280,
					    	        	  labelWidth : 80,
					    	        	  fieldLabel : '计划周期',
				    	        		  xtype:'numberfield',
				    	        		  value:1
//					    	        	  minValue:1,
//					    	        	  minText:'请填写大于0的值',
//				    	        		  negativeText :'不能为负数'		    	         	
					    	          }, {
					    	        	  name:'fplanAmount',
					    	        	  width : 280,
					    	        	  labelWidth : 80,
				    	        		  fieldLabel : '计划数量',
				    	        		  xtype:'numberfield',
				    	        		  value:1
//					    	        	  minValue:1,
//					    	        	  minText:'请填写大于0的值',
//				    	        		  negativeText :'不能为负数'		    	         	
					    	          },Ext.create('Ext.ux.form.DateTimeField',{
						        			fieldLabel:'计划结束时间',  
						        			labelWidth: 80,
						        			width : 280,
						                    name: 'fplanendtime',
						                    format:'Y-m-d'
//						                    validator:function(value)
//						                    {
//						                    	if(value instanceof Date){
//						                    	        value= Ext.Date.format(value,"Y-m-d H:i:s.u"); 
//						                    	}
////						                    	var pform=this.ownerCt;
////						                    	var v1=pform.items.get(5).value;
//						                    	var v1 = new Date();
//						                    	if(value==null||v1==null)
//						                    	{
//						                    		return true;
//						                    	}
//						                    	if(v1 instanceof Date){
//					                    	        v1= Ext.Date.format(v1,"Y-m-d H:i:s.u"); 
//					                    	    }
//						                        return value>v1?true:'请选择大于业务时间的时间';
//						                    }
					    	          })
								]
								},{

									// title:"列1",
									baseCls : "x-plain",
									columnWidth : 600,
									bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
									items : [
//									          	{
//													 name : 'faddress',
//													 xtype : 'textfield',
//													 width : 600,
//													 fieldLabel : '配送地址',
//													 labelWidth : 80
//												 },
												 {
													 name : 'fdescription',
													 xtype : 'textfield',
													 width : 600,
													 fieldLabel : '备  注',
													 labelWidth : 80
												 }
											 ]
								}
								]
					} ]}), this.callParent(arguments);
					},
					bodyStyle : "padding-top:15px;padding-left:30px"
//					,listener : {
//						afterrender : function(){
//							alert("1");
//						}
//					}
				});

function formatEffect(value){        
    return value=='1'?'是':'否';  
}