Ext.require(['DJ.tools.file.MultiUploadPanel']);

Ext.define('DJ.System.product.CustproductEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.System.product.CustproductEdit",
	modal : true,
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
	},
	
	custProductID : "-1",
	
	title : "客户产品管理编辑界面",
//	width : 680,// 230, //Window宽度
//	height : 180,// 137, //Window高度
	resizable : false,
	url : 'SaveCustproduct.do',
	infourl : 'GetCustproductInfo.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'GetCustproductInfo.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true
	frame : true,
	 Action_Submit : function(cc0) {
		  var cc1 = Ext.getCmp(cc0.id);
		  if (cc0.cautoverifyinput == true && !cc1.getform().isValid()) {
		   throw _$[406]
		  };
		  cc0.cverifyinput();
		  var cc2 = cc1.getform();
		  var cc3 = cc2.getValues();
		  cc3[cc0.ctype] = Ext.encode(cc3);
		  var cc4 = Ext.ComponentQuery.query(_$[407], cc0);
		  for (var cc5 = 0x0; cc5 < cc4.length; cc5++) {
		   cc3[cc4[cc5].name] = Ext.encode(cc4[cc5].getcvalues());
		  };
		  cc2.submit({
		     url : cc0.url,
		     clientValidation : cc0.cautoverifyinput,
		     method : _$[417],
		     waitMsg : _$[418],
		     timeout : 0xea60,
		     params : cc3,
		     success : function(cc6, cc7) {
		      var cc8 = Ext.decode(cc7.response.responseText);
		      if (cc1.parent != _$[420]) {
		       Ext.getCmp(cc1.parent).store.load()
		      };
		      djsuccessmsg(cc8.msg);
		      cc1.close()
		     },
		     failure : function(cc6, cc7) {
		      var cc8 = Ext.decode(cc7.response.responseText);
		      Ext.MessageBox.alert(_$[421], cc8.msg)
		     }
		    })
		 },
//	resizable : true,
	
	initComponent : function() {

		var me = this;

		var objT = {
			items : [{
				layout : {
					align : 'stretch',
					type : 'vbox'
				},
				baseCls : "x-plain",
				items : [{
					layout : "column",
					baseCls : "x-plain",
					
					defaults : { // defaults are applied to items, not the
									// container
						labelWidth : 70,
						width : 270,
						layout : {
							align : 'stretch',
							type : 'vbox'
						}
					},
					
					items : [{// title:"列1",
						baseCls : "x-plain",
						columnWidth : .5,
						bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
						items : [{

							xtype : 'hidden',
							name : "isFromBasePlatforms",
							value : 0
								// 1是新增

								},{

							xtype : 'hidden',
							name : "isCreate",
							value : 0
								// 1是新增

								},{
									name : 'ftype',
									xtype : 'textfield',
									hidden : true
								},{
									name : 'fproductid',
									xtype : 'textfield',
									hidden : true
								}, {
//									 id :
//									 'DJ.System.product.CustproductEdit.FID',
									name : 'fid',
									xtype : 'textfield',
//									labelWidth : 50,
//									width : 260,
									hidden : true
								}, {
									// id :
									// 'DJ.System.product.CustproductEdit.FID',
									name : 'fcreatorid',
									xtype : 'textfield',
//									labelWidth : 50,
//									width : 260,
									hidden : true
								}, {
									// id :
									// 'DJ.System.product.CustproductEdit.FNUMBER',
									name : 'fnumber',
									xtype : 'textfield',
									fieldLabel : '包装物编码',
									allowBlank : false,
									blankText : '编码不能为空',
									regex : /^([\u4E00-\u9FA5]|\w|[@.()\-])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
									regexText : "不能包含特殊字符"
//									,
//									width : 260,
//									labelWidth : 50
								}, {
									// id :
									// 'DJ.System.product.CustproductEdit.FMNEMONICCODE',
									name : 'fspec',
									xtype : 'textfield',
									fieldLabel : '规格'
//									,
//									width : 260,
//									labelWidth : 50
								}, {
									// id :
									// 'DJ.System.product.CustproductEdit.FMNEMONICCODE',
									name : 'fcharactername',
									xtype : 'textfield',
									fieldLabel : '特性'
//									,
//									width : 260,
//									labelWidth : 50
								}, {
									// id :
									// 'DJ.System.product.CustproductEdit.FMNEMONICCODE',
									name : 'fmaterial',
									xtype : 'textfield',
									fieldLabel : '材料'
//									,
//									width : 260,
//									labelWidth : 50
								}, {
									// id :
									// 'DJ.System.product.CustproductEdit.Fcustomer',
									// name:'fcustomerid',
									// width : 260,
									// labelWidth:50,
									// fieldLabel : '客户',
									// //xtype : 'textfield',
									// xtype:'combo',
									// store:customerStore,
									// triggerAction:"all",
									// displayField:'fname', //这个是设置下拉框中显示的值
									// valueField:'fid', //这个可以传到后台的值
									// editable: false, //可以编辑不
									// forceSelection: true,
									// pageSize : 10,
									// mode:'local'

									// listeners:{
									// select:function(_combo,_record,_opt)
									// {
									// var fcustomerid= _combo.getValue();
									// var
									// cforms=Ext.getCmp("DJ.System.product.CustproductEdit.CForm").getForm();
									// customerStore.on("beforeload",
									// function(store,
									// options) {
									// Ext.apply(store.proxy.extraParams,{fcustomer:fcustomerid});
									// });
									// customerStore.load();
									// }
									// }
//									width : 260,
//									labelWidth : 50,
									name : 'fcustomerid',
									fieldLabel : '客户名称',
									xtype : 'cCombobox',
									displayField : 'fname', // 这个是设置下拉框中显示的值
									valueField : 'fid', // 这个可以传到后台的值
									allowBlank : false,
									blankText : '请选择客户',
									editable : false, // 可以编辑不
									MyConfig : {
										width : 800,// 下拉界面
										height : 200,// 下拉界面
										url : 'GetCustomerList.do', // 下拉数据来源
										fields : [{
											name : 'fid'
										}, {
											name : 'fname',
											myfilterfield : 't_bd_customer.fname', // 查找字段，发送到服务端
											myfiltername : '客户名称',// 在过滤下拉框中显示的名称
											myfilterable : true
												// 该字段是否查找字段
										}, {
											name : 'fnumber'
										}, {
											name : 'findustryid'
										}, {
											name : 'faddress'
										}, {
											name : 'fisinternalcompany',
											convert : function(value, record) {
												if (value == '1') {
													return true;
												} else {
													return false;
												}
											}
										}],
										columns : [{
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
											xtype : 'checkcolumn',
											processEvent : function() {
											},
											sortable : true
										}]
									}

								}

						]
					}, {	// title:"列2",
						baseCls : "x-plain",
						columnWidth : .5,
						bodyStyle : 'padding-top:7px;padding-left:5px;padding-right:5px',
						items : [{
							// id : 'DJ.System.product.CustproductEdit.FNAME',
							name : 'fname',
							xtype : 'textfield',
							fieldLabel : '包装物名称',
							allowBlank : false,
							blankText : '名称不能为空'
//							,
							// regex : /^([\u4E00-\u9FA5]|\w|[()\-])*$/,//
							// /^[^,\!@#$%^&*()_+}]*$/,
							// regexText : "不能包含特殊字符"
//							,
//							width : 260,
//							labelWidth : 70

						}, {
							// id :
							// 'DJ.System.product.CustproductEdit.Fcreatetime',
							name : 'fcreatetime',
							xtype : 'textfield',
							hidden : true

						}, {
							// id :
							// 'DJ.System.product.CustproductEdit.Fcreatetime',
							name : 'forderunit',
							xtype : 'textfield',
							fieldLabel : '单位'
//							,
//							width : 260,
//							labelWidth : 70
						}, {
							// id :
							// 'DJ.System.product.CustproductEdit.FMNEMONICCODE',
							name : 'ftilemodel',
							xtype : 'textfield',
							fieldLabel : '楞型'
//							,
//							width : 260,
//							labelWidth : 70
						}, {
							// id :
							// 'DJ.System.product.CustproductEdit.FDESCRIPTION',
							name : 'fdescription',
							xtype : 'textfield',
							fieldLabel : '描述'
//							,
//							width : 260,
//							labelWidth : 70
						},{
							name : 'fproductmatching',
							xtype : 'textfield',
							fieldLabel : '发放产品匹配'
						}

						]
					}]

				},	(function(){
	
	if(Ext.isIE) {
	
//		return {
//		xtype : 'label',
//		width : 0
//	};
		
		
	} else {
	
		return {
//					heigth : 200,
					
					name : "djmultiuploadPanelfile",
					
					submitValue : false,
					
					xtype : 'djmultiuploadPanel',

					url : "uploadCustProductImg.do",
					max_file_size : '11mb',
					unique_names : false,
					multiple_queues : true,
					chunk_size : '3mb',
					file_data_name : "upload1",
					multipart : true,
					multipart_params : {
						fid : ""
					},
					filters : [{
						title : "图片",
						extensions : "jpg,jpeg,png,gif,bmp,cdr"
					}]

				};
		
	}
		
	})()
	]

			}]
		};

		
		   Ext.applyIf(me, objT);

		me.callParent(arguments);
		
	}
	
//	,
//	bodyStyle : "padding-top:5px;padding-left:5px"
});


//
// Ext.define('DJ.System.product.customerStore', {
// extend : 'Ext.data.Store',
// id : 'DJ.System.product.customerStore',
// fields : [ {
// name : 'fid'
// }, {
// name : 'fname'
// }],
// pageSize : 10,
// proxy : {
// type : 'ajax',
// url : 'GetcustomerAll.do',
// reader : {
// type : 'json',
// root : 'data'
// }
// },
// autoLoad : false
// });
//
// var customerStore = Ext.create('DJ.System.product.customerStore');
//
// Ext.define('DJ.System.product.CustproductEdit', {
// extend : 'Ext.Window',
// id : 'DJ.System.product.CustproductEdit',
// modal : true,
// title : "客户产品管理编辑界面",
// resizable : false,
// closable : true, // 关闭按钮，默认为true
//	
// width:680,
// height:180,
//	
// items:[{
// xtype : 'form',
// id:'DJ.System.product.CustproductEdit.CForm',
// layout:"column",
// baseCls:"x-plain",
// items:[
// {//title:"列1",
// baseCls:"x-plain",columnWidth:.5,bodyStyle :
// 'padding-top:0px;padding-left:5px;padding-right:5px',
//	  				items : [{
////				        			id : 'DJ.System.product.CustproductEdit.FID',
//				        			name:'fid',
//				        			xtype : 'textfield',
//				        			labelWidth:50, 
//				  			        width : 260,
//				        			hidden : true
//			        			},{
////				        			id : 'DJ.System.product.CustproductEdit.FID',
//				        			name:'fcreatorid',
//				        			xtype : 'textfield',
//				        			labelWidth:50, 
//				  			        width : 260,
//				        			hidden : true
//			        			},
//			        			{
////			        		        id : 'DJ.System.product.CustproductEdit.FNUMBER',
//			        		        name:'fnumber',
//			        		        xtype : 'textfield',
//			        		        fieldLabel : '编码',
//			        		        allowBlank : false,
//			        		        blankText : '编码不能为空',
//			        		        regex : /^([\u4E00-\u9FA5]|\w|[@.()\-])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//			        		        regexText : "不能包含特殊字符",
//				  			        width : 260,
//			        		        labelWidth:50
//			        		    },{
////				  			        id : 'DJ.System.product.CustproductEdit.FMNEMONICCODE',
//				  			        name:'fspec',
//				  			        xtype : 'textfield',
//				  			        fieldLabel : '规格',
//				  			        width : 260,
//				  			      	labelWidth:50
//			  			        },{
////				  			        id : 'DJ.System.product.CustproductEdit.Fcustomer',
////				  			        name:'fcustomerid',
////				  			        width : 260,
////				  			        labelWidth:50,
////				  			      	fieldLabel : '客户',
////				  			      	//xtype : 'textfield',
////				  			      	xtype:'combo',
////				  			      	store:customerStore,
////			    	        		triggerAction:"all",
////			    	        		displayField:'fname', //这个是设置下拉框中显示的值
////			    	        	    valueField:'fid', //这个可以传到后台的值
////			    	        	    editable: false, //可以编辑不
////			    	        	    forceSelection: true,
////			    	        	    pageSize : 10,
////			    	        	    mode:'local'
//				  			      	
////				  			      listeners:{
////				    	        	    	  select:function(_combo,_record,_opt)
////				    	        	    	  {
////				    	        	    		 var fcustomerid= _combo.getValue();
////				    	        	    		 var cforms=Ext.getCmp("DJ.System.product.CustproductEdit.CForm").getForm();
////				    	        	    		 customerStore.on("beforeload", function(store, options) {
////				    	        	    				Ext.apply(store.proxy.extraParams,{fcustomer:fcustomerid});
////				    	        	    			});
////				    	        	    		 customerStore.load();
////				    	        	    	  }
////				    	        	    	}
//			  			        	 width : 260,
//			  			        	 labelWidth:50,
//			  			        	name:'fcustomerid',
//			    	        		fieldLabel : '客户',
//			    	        		xtype:'cCombobox',
//			    	        		displayField:'fname', // 这个是设置下拉框中显示的值
//			    	        	    valueField:'fid', // 这个可以传到后台的值
//			    	        	    allowBlank : false,
//			    	        	    blankText:'请选择客户',
//			    	        	    editable: false, // 可以编辑不
//			    	        	    MyConfig : {
//			    	 					width : 800,//下拉界面
//			    	 					height : 200,//下拉界面
//			    	 					url : 'GetCustomerList.do',  //下拉数据来源
//			    	 					fields : [ {
//			    	 						name : 'fid'
//			    	 					}, {
//			    	 						name : 'fname',
//			    	 						myfilterfield : 't_bd_customer.fname', //查找字段，发送到服务端
//			    	 						myfiltername : '客户名称',//在过滤下拉框中显示的名称
//			    	 						myfilterable : true//该字段是否查找字段
//			    	 					}, {
//			    	 						name : 'fnumber'
//			    	 					}, {
//			    	 						name : 'findustryid'
//			    	 					}, {
//			    	 						name : 'faddress'
//			    	 					}, {
//			    	 						name : 'fisinternalcompany',
//			    	 						convert:function(value,record)
//			    							{
//			    								if(value=='1')
//			    								{	
//			    									return true;
//			    								}else{
//			    									return false;
//			    								}	
//			    							}
//			    	 					} ],
//			    	 					columns : [ {
//			    	 						text : 'fid',
//			    							dataIndex : 'fid',
//			    							hidden : true,
//			    							sortable : true
//			    						}, {
//			    							text : '编码',
//			    							dataIndex : 'fnumber',
//			    							sortable : true
//			    						}, {
//			    							text : '客户名称',
//			    							dataIndex : 'fname',
//			    							sortable : true
//			    						}, {
//			    							text : '行业',
//			    							dataIndex : 'findustryid',
//			    							sortable : true
//			    						}, {
//			    							text : '地址',
//			    							dataIndex : 'faddress',
//			    							sortable : true,
//			    							width : 250
//			    						}, {
//			    							text : '内部客户',
//			    							dataIndex : 'fisinternalcompany',
//			    							xtype:'checkcolumn',
//			    							processEvent : function() {
//			    							},
//			    							sortable : true
//			    						}]
//			    	 				}
//				  			      	
//				  	           	}
//	  						
//	  						]
//        		},{//title:"列2",
//	        			baseCls:"x-plain",columnWidth:.5,bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
//	  				items : [{
////				  			        id : 'DJ.System.product.CustproductEdit.FNAME',
//				  			        name:'fname',
//				  			        xtype : 'textfield',
//				  			        fieldLabel : '名称',
//				  			        allowBlank : false,
//				  			        blankText : '名称不能为空',
////				  			        regex : /^([\u4E00-\u9FA5]|\w|[()\-])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
////				  			        regexText : "不能包含特殊字符",
//				  			        width : 260,
//				  			     	labelWidth:70
//				  			     	
//		  			        	},
//		  			        	{
////			  			          id : 'DJ.System.product.CustproductEdit.Fcreatetime',
//				  			      name:'fcreatetime',
//			  			          xtype : 'textfield',
//			  			          hidden : true
//
//		  			          },{
////			  			          id : 'DJ.System.product.CustproductEdit.Fcreatetime',
//				  			      name:'forderunit',
//			  			          xtype : 'textfield',
//			  			          fieldLabel : '单位',
//				  			      width : 260,
//			  			          labelWidth:70
//		  			          },{
////				  			        id : 'DJ.System.product.CustproductEdit.FDESCRIPTION',
//				  			        name:'fdescription',
//				  			        xtype : 'textfield',
//				  			        fieldLabel : '描述',
//					  			    width : 260,
//				  			        labelWidth:70
//			  			        }
//	  				
//	  						]
//        		}
//  			]
//		}
//	],
//	buttons : [ {
//		xtype : "button",
//		text : "确定",
//		pressed : false,
//		handler : SaveData
//	}, {
//		xtype : "button",
//		text : "取消",
//		handler : function() {
//			var windows = Ext.getCmp("DJ.System.product.CustproductEdit");
//			if (windows != null) {
//				windows.close();
//			}
//		}
//	} ],
//	buttonAlign : "center",
//	bodyStyle : "padding-top:5px;padding-left:30px"
//// bodyStyle : "padding:20px;background-color:#000000;background-image:
//// url(images/loginbg.gif);",
//});
//function SaveData() {
//	// var
//	// grid=Ext.getCmp("DJ.System.UserList");//Ext.getCmp("DJ.System.UserListPanel")
//	// var record = grid.getSelectionModel().getSelection();
//	// if (record.length == 0) {
//	// Ext.MessageBox.show({
//	// title : "提示",
//	// msg : "请先选择您要操作的行!"
//	// // icon: Ext.MessageBox.INFO
//	// })
//	// return;
//	// } else {
//	// var ids = "";
//	// for ( var i = 0; i < record.length; i++) {
//	// ids += record[i].get("fid")
//	// if (i < record.length - 1) {
//	// ids = ids + ",";
//	// }
//	// }
//	// Ext.MessageBox.show({
//	// title : "所选ID列表",
//	// msg : ids
//	// // icon: Ext.MessageBox.INFO
//	// })
//	// }
////	if (!Ext.getCmp("DJ.System.product.CustproductEdit.UserName").isValid()
////			|| !Ext.getCmp("DJ.System.product.CustproductEdit.Email").isValid()
////			|| !Ext.getCmp("DJ.System.product.CustproductEdit.Tel").isValid()) {
////		Ext.MessageBox.alert('提示', '输入项格式不正确，请修改后再提交！');
////		return;
////	}
////	var Password = Ext.getCmp("DJ.System.product.CustproductEdit.PassWord").getValue();
////	var Password1 = Ext.getCmp("DJ.System.product.CustproductEdit.PassWord1").getValue();
////	if (Ext.util.Format.trim(Password) != Ext.util.Format.trim(Password1)) {
////		Ext.MessageBox.alert('提示', '两次密码不一致！');
////		return;
////	}
//	 var cform=Ext.getCmp("DJ.System.product.CustproductEdit.CForm").getForm();
//       if(!cform.isValid())
//       	{
//       	Ext.MessageBox.alert('提示', '输入项格式不正确，请按提示修改！');
//       	return;
//       	}
//       cform.submit({
//        	    url: 'SaveCustproduct.do',
//        	    method:'post',
//        	    waitMsg:'正在处理请求……',
//        	    timeout: 60000,
//        	    success: function(form, action) {
//        			var obj = Ext.decode(action.response.responseText);
//        				Ext.MessageBox.alert('成功', obj.msg);
//        				Ext.getCmp("DJ.System.product.CustproductList").store.load();
//        				Ext.getCmp("DJ.System.product.CustproductEdit").close();
//        		},
//        		failure:function(f,action){
//        			var obj = Ext.decode(action.response.responseText);
//        			Ext.MessageBox.alert('错误', obj.msg);
//        		}
//        		});
//        }
////	Ext.Ajax.request({
////		url : "SaveSupplier.do",
////		params : {
////			fid : Ext.getCmp("DJ.System.product.CustproductEdit.UserFID").getValue(),
////			fname : Ext.getCmp("DJ.System.product.CustproductEdit.UserName").getValue(),
////			fpassword : MD5(Password),
////			fcustomername : Ext.getCmp("DJ.System.product.CustproductEdit.CustomerName").getValue(),
////			femail : Ext.getCmp("DJ.System.product.CustproductEdit.Email").getValue(),
////			ftel : Ext.getCmp("DJ.System.product.CustproductEdit.Tel").getValue(),
////			fcreatetime : Ext.getCmp("DJ.System.product.CustproductEdit.CreateTime").getValue()
////		}, // 参数
////		success : function(response, option) {
////			var obj = Ext.decode(response.responseText);
////			if (obj.success == true) {
////				Ext.MessageBox.alert('成功', obj.msg);
////				Ext.getCmp("DJ.System.product.CustproductList").store.load();
////				Ext.getCmp("DJ.System.product.CustproductEdit").close();
////			} else {
////				Ext.MessageBox.alert('错误', obj.msg);
////			}
////		}
////	});