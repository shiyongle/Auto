Ext
		.define(
				'DJ.System.UserIPEdit',
				{
					extend : 'Ext.c.BaseEditUI',
					id : 'DJ.System.UserIPEdit',
					modal : true,
					title : "用户IP编辑界面",
//					ctype : "Userip",
					width : 620,// 230, //Window宽度
					height : 200,// 137, //Window高度
					resizable : false,
					url : 'SaveSysUserIP.do',
					infourl : 'GetUserIPInfo.do',
					viewurl : 'GetUserIPInfo.do',
					closable : true, // 关闭按钮，默认为true
					custbar : [
							],
					cverifyinput : function() {
						 //throw "数据异常，不能保存！";
					},
					initComponent : function() {
								Ext
										.apply(
												this,
												{
													items : [{
															layout:"column",
															baseCls:"x-plain",
															items:[
														  			{//title:"列1",
														        			baseCls:"x-plain",columnWidth:.5,bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
														  				items : [
														  							{
//													//				        			id : 'DJ.System.product.CustproductEdit.FID',
																	        			name:'fid',
																	        			xtype : 'textfield',
																	        			labelWidth:50, 
																	  			        width : 260,
																	        			hidden : true
																        			},
//														  							,{
//													//				        			id : 'DJ.System.product.CustproductEdit.FID',
//																	        			name:'fcreatorid',
//																	        			xtype : 'textfield',
//																	        			labelWidth:50, 
//																	  			        width : 260,
//																	        			hidden : true
//																        			},
//																        			{
//													//			        		        id : 'DJ.System.product.CustproductEdit.FNUMBER',
//																        		        name:'fnumber',
//																        		        xtype : 'textfield',
//																        		        fieldLabel : '编码',
//																        		        allowBlank : false,
//																        		        blankText : '编码不能为空',
//																        		        regex : /^([\u4E00-\u9FA5]|\w|[@.()\-])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//																        		        regexText : "不能包含特殊字符",
//																	  			        width : 260,
//																        		        labelWidth:50
//																        		    },{
//													//				  			        id : 'DJ.System.product.CustproductEdit.FMNEMONICCODE',
//																	  			        name:'fspec',
//																	  			        xtype : 'textfield',
//																	  			        fieldLabel : '规格',
//																	  			        width : 260,
//																	  			      	labelWidth:50
//																  			        },
																  			        {
													//				  			        id : 'DJ.System.product.CustproductEdit.Fcustomer',
													//				  			        name:'fcustomerid',
													//				  			        width : 260,
													//				  			        labelWidth:50,
													//				  			      	fieldLabel : '客户',
													//				  			      	//xtype : 'textfield',
													//				  			      	xtype:'combo',
													//				  			      	store:customerStore,
													//			    	        		triggerAction:"all",
													//			    	        		displayField:'fname', //这个是设置下拉框中显示的值
													//			    	        	    valueField:'fid', //这个可以传到后台的值
													//			    	        	    editable: false, //可以编辑不
													//			    	        	    forceSelection: true,
													//			    	        	    pageSize : 10,
													//			    	        	    mode:'local'
																	  			      	
													//				  			      listeners:{
													//				    	        	    	  select:function(_combo,_record,_opt)
													//				    	        	    	  {
													//				    	        	    		 var fcustomerid= _combo.getValue();
													//				    	        	    		 var cforms=Ext.getCmp("DJ.System.product.CustproductEdit.CForm").getForm();
													//				    	        	    		 customerStore.on("beforeload", function(store, options) {
													//				    	        	    				Ext.apply(store.proxy.extraParams,{fcustomer:fcustomerid});
													//				    	        	    			});
													//				    	        	    		 customerStore.load();
													//				    	        	    	  }
													//				    	        	    	}
																  			        	width : 260,
																  			        	labelWidth:70,
																  			        	name:'FUSERID',
																    	        		fieldLabel : '用户名称',
																    	        		xtype:'cCombobox',
																    	        		displayField:'fname', // 这个是设置下拉框中显示的值
																    	        	    valueField:'fid', // 这个可以传到后台的值
																    	        	    allowBlank : false,
																    	        	    blankText:'请选择客户',
																    	        	    editable: false, // 可以编辑不
																    	        	    MyConfig : {
																    	 					width : 800,//下拉界面
																    	 					height : 200,//下拉界面
																    	 					url : 'GetUserList.do',  //下拉数据来源
																    	 					fields : [{
																										name : 'fid'
																									}, {
																										name : 'fname',
																										myfilterfield : 'fname',
																										myfiltername : '名称',
																										myfilterable : true
																									}, {
																										name : 'feffect',
																										convert:function(value,record)
																										{
																											if(value=='1')
																											{	
																												return true;
																											}else{
																												return false;
																											}	
																										}
																									}, {
																										name : 'fcustomername',
																										myfilterfield : 'fcustomername',
																										myfiltername : '客户名称',
																										myfilterable : true
																									}, {
																										name : 'femail'
																									}, {
																										name : 'ftel'
																									}, {
																										name : 'fcreatetime'
																									},{
																										name : 'fisfilter',
																										convert:function(value,record)
																										{
																											if(value=='1')
																											{	
																												return true;
																											}else{
																												return false;
																											}	
																										}
																										
																									}],
																							columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
																										'header' : 'fid',
																										'dataIndex' : 'fid',
																										hidden : true,
																										hideable : false,
																										sortable : true
																				
																									}, {
																										'header' : '用户名称',
																										'dataIndex' : 'fname',
																										sortable : true
																									}, {
																										'xtype' : 'checkcolumn',
																										'header' : '是否启用',
																										processEvent : function() {
																										},
																										// readOnly:true,
																										'dataIndex' : 'feffect',
																										sortable : true
																									}, {
																										'header' : '客户名称',
																										'dataIndex' : 'fcustomername',
																										sortable : true
																									}, {
																										'header' : '邮箱',
																										'dataIndex' : 'femail',
																										sortable : true
																									}, {
																										'header' : '手机',
																										'dataIndex' : 'ftel',
																										sortable : true
																									}, {
																										'header' : '创建时间',
																										'dataIndex' : 'fcreatetime',
																										width : 200,
																										sortable : true
																										}, {
																										'xtype' : 'checkcolumn',
																										'header' : '是否不过滤',
																										processEvent : function() {
																										},
																										// readOnly:true,
																										'dataIndex' : 'fisfilter',
																										sortable : true
																										
																									}]
																    	 				}
																	  			      	
																	  	           	},
//														  						{
//																					name : 'fuser',
//																					xtype : 'textfield',
//																					fieldLabel : '用户名称',
//																					labelWidth : 70
//																				},
																				{
																					id : "DJ.System.UserIPEdit.efector",
																					name : 'efector',
																					xtype : 'textfield',
																					fieldLabel : '启禁人',
																					width : 260,
																					labelWidth : 70,
//																					listeners:{
//																	    	        	    	  render:function(form,eOpts)
//																	    	        	    	  {
//																	    	        	    		 var value=Ext.getCmp("DJ.System.UserIPEdit.efector").getValue();
//																		    	        	    		if(value=='null'){
//																		    	        	    			Ext.getCmp("DJ.System.UserIPEdit.efector").setValue("");
//																										}
//																	    	        	    	  }
//																	    	        	    	},
																						disabled : true
																				},
																				{
																					id : "DJ.System.UserIPEdit.FEFECTED",
																					name : 'FEFECTED',
																					xtype : 'textfield',
																					fieldLabel : '启用或禁用',
																					width : 260,
																					labelWidth : 70,
//																					convert:function(value,record)
//																										{
//																											if(value==1){
//																												return '启用';
//																											}else{
//																												return '禁用';	
//																											}
//																										},
//																					render( this, eOpts ) : function(value){
//																						if(value==1){
//																							return '启用';
//																						}else{
//																							return '禁用';	
//																						}
//																					},
																					listeners:{
																	    	        	    	  render:function(form,eOpts)
																	    	        	    	  {
																	    	        	    		 var value=Ext.getCmp("DJ.System.UserIPEdit.FEFECTED").getValue();
																		    	        	    		if(value==1){
																		    	        	    			Ext.getCmp("DJ.System.UserIPEdit.FEFECTED").setValue("启用");
																										}else{
																											Ext.getCmp("DJ.System.UserIPEdit.FEFECTED").setValue("禁用");
																										}
																	    	        	    	  }
																	    	        	    	},
																						disabled : true
																				},
																				{
																					name : 'fcreator',
																					xtype : 'textfield',
																					fieldLabel : '创建人',
																					width : 260,
																					labelWidth : 70,
																					disabled : true
																				}
														  						]
													        		},{//title:"列2",
														        			baseCls:"x-plain",columnWidth:.5,bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
														  				items : [
//														  							{
//													//				  			        id : 'DJ.System.product.CustproductEdit.FNAME',
//																	  			        name:'fname',
//																	  			        xtype : 'textfield',
//																	  			        fieldLabel : '名称',
//																	  			        allowBlank : false,
//																	  			        blankText : '名称不能为空',
//													//				  			        regex : /^([\u4E00-\u9FA5]|\w|[()\-])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//													//				  			        regexText : "不能包含特殊字符",
//																	  			        width : 260,
//																	  			     	labelWidth:70
//																	  			     	
//															  			        	},
//															  			        	{
//													//			  			          id : 'DJ.System.product.CustproductEdit.Fcreatetime',
//																	  			      name:'fcreatetime',
//																  			          xtype : 'textfield',
//																  			          hidden : true
//													
//															  			          },{
//													//			  			          id : 'DJ.System.product.CustproductEdit.Fcreatetime',
//																	  			      name:'forderunit',
//																  			          xtype : 'textfield',
//																  			          fieldLabel : '单位',
//																	  			      width : 260,
//																  			          labelWidth:70
//															  			          },{
//													//				  			        id : 'DJ.System.product.CustproductEdit.FDESCRIPTION',
//																	  			        name:'fdescription',
//																	  			        xtype : 'textfield',
//																	  			        fieldLabel : '描述',
//																		  			    width : 260,
//																	  			        labelWidth:70
//																  			        }
																	  				{
																						name : 'FIP',
																						xtype : 'textfield',
																						fieldLabel : '用户IP',
																						width : 260,
																						labelWidth : 70
																						,regex : /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
																        		        regexText : "IP地址不正确"
																					},
																					{
																						id : "DJ.System.UserIPEdit.FEFECTTIME",
																						name : 'FEFECTTIME',
																						xtype : 'textfield',
																						fieldLabel : '启禁时间',
																						width : 260,
																						labelWidth : 70,
//																						listeners:{
//																	    	        	    	  render:function(form,eOpts)
//																	    	        	    	  {
//																	    	        	    		 var value=Ext.getCmp("DJ.System.UserIPEdit.FEFECTTIME").getValue();
//																		    	        	    		if(value=='null'){
//																		    	        	    			Ext.getCmp("DJ.System.UserIPEdit.FEFECTTIME").setValue("");
//																										}
//																	    	        	    	  }
//																	    	        	    	},
																						disabled : true
																					},
																					{
																						name : 'FCREATETIME',
																						xtype : 'textfield',
																						fieldLabel : '创建时间',
																						width : 260,
																						labelWidth : 70,
																						disabled : true
																					}
														  						]
													        		}
													  			]
														}]
												}), this.callParent(arguments);
					},
				 bodyStyle : "padding-top:10px;padding-left:20px"
				});