
//Model
Ext.define('SchemeDesignProductInfo',{
	extend:'Ext.data.Model',
	fields:['fcustomername','subProductAmount','schemedesignid','forderunitid','fnumber','fhstaveexp','farea','fmaterialcost','fcharacter','fmodelmethod','fdescription','fid','fname','fversion','fmaterialcodeid','fmaterialcode','flayer','fboxmodelid','fboardlength','fboardwidth','fstavetype','fvstaveexp','fchromaticprecision','fcleartype','fquality','forderprice','fcusproductname','fcustomerid','ftilemodelid','fnewtype','fboxlength','fboxwidth','fboxheight','fmateriallength','fmaterialwidth','fsupplierid']
});

Ext.define('DJ.order.saleOrder.pCustomerStore', {
	extend : 'Ext.data.Store',
	id : 'DJ.order.saleOrder.pCustomerStore',
	fields : [ {
		name : 'fid'
	}, {
		name : 'fname'
	}],
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : 'GetCustomerList.do',
		reader : {
			
			type : 'json',
			root : 'data',
			totalProperty : 'total'
		}
	},
	autoLoad : false
});
var customerStore=Ext.create('DJ.order.saleOrder.pCustomerStore');
//form
Ext.define('DJ.order.saleOrder.ProductDefDetail', {
	extend : 'Ext.form.Panel',
	modal : true,
	frame:true,
	resizable : false,
		 id:'DJ.order.saleOrder.ProductDefDetail',
		 fieldDefaults: {
	            labelWidth: 85
	      },
		 items:[
		        {
		        	 layout:'column',
		        	 baseCls:'x-plain',
		        	 items:[ 
		    	      {
		    	    	bodyStyle : 'padding:5px;',
		    		    baseCls:'x-plain',
		    	        columnWidth:.5,
		    	        layout:'form',	
		    	        defaults:{xtype:'textfield'},
						    	   items:[ 
						    	         {
				    	        		name:'fnumber',
				    	        		fieldLabel : '包装物编号',
				    	        		allowBlank : false,
				    	        		blankText : '编码不能为空',
				    	        		regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
				    	        		regexText : "不能包含特殊字符"
						    	   		}, 
//						    	   		{
//						    	        	  name:'forderunitid',
//						    	        		fieldLabel : '计量单位',
//						    	        		regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//						    	        		regexText : "不能包含特殊字符",
//						    	        		hidden:true
//		
//						    	          },	 {
//						    	        	  name:'fcusproductname',
//					    	        		fieldLabel : '客户产品名称',
//					    	        		regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//					    	        		regexText : "不能包含特殊字符",
//					    	        		hidden:true
//					    	          },
					    	          {
					    	        	  name:'fcustomerid',
					    	        	  hidden:true
					    	          },
					    	          {
					    	        	  name:'fcustomername',
					    	        	  fieldLabel:'客户名称',
					    	        	  readOnly:true
					    	          },
					    	          /*{
						    	   		name:'fcustomername',
				    	        		fieldLabel : '客户名称',
				    	        		store:customerStore,
//				    	        		triggerAction:"all",
				    	        		xtype:'combo',
				    	        		 displayField:'fname', // 这个是设置下拉框中显示的值
				    	        	     valueField:'fid', // 这个可以传到后台的值
				    	        	      editable: false, // 可以编辑不
				    	        	      forceSelection: true,
				    	        	      pageSize:10,
				    	        	      mode:'remote',
				    	        	      editable:false,
				    	        	      hideTrigger:true
						    	   		},  */   
//					    	          {
//						    	        	  name:'ftilemodelid',
//						    	        		fieldLabel : '瓦楞楞型',
//						    	        		regex : /^([\u4E00-\u9FA5]|\w|\S)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//						    	        		regexText : "不能包含特殊字符",
//						    	        		hidden:true
//						    	        	
//						    	     
//						    	          }, {
//						    	        	  name:'fnewtype',
//					    	        			fieldLabel : '产品类型(新)',
//					    	        			regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//						    	        		regexText : "不能包含特殊字符",
//						    	        		hidden:true
//					    	        			
//					    	          },
					    	          {
							        	     
					        	        	baseCls:'x-plain',
					        	        	columnWidth:.25,
					        	        	 xtype:'fieldcontainer',
						    	        	 fieldLabel : '纸箱规格',
					        	        	 layout: 'hbox',
								    	     items:[{
								    	        		        	 xtype:'numberfield',
												    	        	 name:'fboxlength',
												    	        		 hideLabel: true,
												    	        		 value:0,
												    	        		 minValue:0,
												    	        		 width:64,
												    	        		 negativeText :'不能为负数',
												    	        		 allowBlank:false
								    	        		        },
								    	        		        {
								    	        		        	 xtype:'displayfield',
												    	        	value:'X',
												    	        		 width:8
								    	        		        },
								    	        		        {
								    	        		        	 xtype:'numberfield',
												    	        	 name:'fboxwidth',
												    	        	 value:0,
												    	        	 minValue:0,
												    	        	 width:64,
												    	        	 negativeText :'不能为负数',
												    	        	 allowBlank:false
								    	        		        },
								    	        		        {
								    	        		        	 xtype:'displayfield',
								    	        		        	 value:'X',
								    	        		        	 width:8
								    	        		        },
								    	        		        {
								    	        		        	 xtype:'numberfield',
												    	        	 name:'fboxheight',
												    	        	 value:0,
												    	        	 minValue:0,
												    	        	 width:64,
												    	        	 negativeText :'不能为负数',
												    	        	 allowBlank:false
								    	        		        }]
								    	     	
					        	        
					        	        },{
					    	        		xtype:'numberfield',
					    	        		name:'subProductAmount',
					    	        		id:'DJ.order.saleOrder.ProductDefDetail.subProductAmount',
					    	        		fieldLabel:'数量比例',
					    	        		allowBlank:false,
					    	        		minValue:1,
					    	        		value:1
					    	        	}
					    	        		,  
//						        	       {
//						        	        
//					        	        	baseCls:'x-plain',
//					        	        	columnWidth:.25,
//					        	        	 xtype:'fieldcontainer',
//						    	        	 fieldLabel : '出库规格',
//						    	        	 labelWidth: 87,
//					        	        	 layout: 'hbox',
//					        	        	 hidden:true,
//					        	        	 items:[{
//			    	        		        	 xtype:'numberfield',
//							    	        	 name:'fmateriallength',
//							    	        		 hideLabel: true,
//							    	        		 value:0,
//								    	        	 minValue:0,
//							    	        		 width:100,
//							    	        		 negativeText :'不能为负数'
//			    	        		        },
//			    	        		        {
//			    	        		        	 xtype:'displayfield',
//							    	        	value:'X',
//							    	        		 width:8
//			    	        		        },
//			    	        		        {
//			    	        		        	 xtype:'numberfield',
//							    	        	 name:'fmaterialwidth',
//							    	        	 value:0,
//							    	        	 minValue:0,
//							    	        	 width:100,
//							    	        	 negativeText :'不能为负数'
//							    	        	 
//			    	        		        }
//			    	        		        ]
//					        	        },
					    	         
//						    	          {
//						    	        	  name:'fhstaveexp',
//						    	        		fieldLabel : '横向跑线公式',
//						    	        		hidden:true,
//						    	        		regex : /^(\d|[HLWhlw]|\+|\-|\*|\/|\(|\)|\[|\]|\.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//						    	        		regexText : "不能包含特殊字符"
//						    	          }, {
//						    	        	  xtype:'numberfield',
//						    	        	   name:'farea',
//						    	        	   hidden:true,
//						    	        		fieldLabel : '单位面积',
//						    	        		 value:0,
//							    	        	 minValue:0,
//							    	        	 negativeText :'不能为负数'
//							    	      
//						    	        		
//						    	          }, {
//						    	        	  xtype:'numberfield',
//						    	        	  name:'fmaterialcost',
//						    	        	  hidden:true,
//						    	        		fieldLabel : '材料成本',
//						    	        		 value:0,
//							    	        	 minValue:0	,
//							    	        	 negativeText :'不能为负数'
//						    	          },  {
//						    	        	  xtype:'textareafield',
//						    	        	  name:'fcharacter',
//						    	        	  hidden:true,
//						    	        		fieldLabel : '产品特征',
//						    	        		regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//						    	        		regexText : "不能包含特殊字符",
//						    	        		  height :30,
//						    	        		preventScrollbars:false
//
//						    	          }, { 
//						    	        	  xtype:'textareafield',
//						    	        	  name:'fmodelmethod',
//						    	        		fieldLabel : '成型方式',
//						    	        		hidden:true,
//						    	        		regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//						    	        		regexText : "不能包含特殊字符",
//						    	        		  height :30,
//							    	        		preventScrollbars:false
//							    	        	}, { 
//						    	        	xtype:'textarea',
//						    	        	 name:'fdescription',
//						    	        	 hidden:true,
//						    	        		fieldLabel : '产品备注',
//						    	        		regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//						    	        		regexText : "不能包含特殊字符",
//						    	        		 height :30					    	        	
//
//
//							    	          }, 
					    	        		{
						    	          		 
												xtype : 'hidden',
												name:'fid'
							    	          
				    	          	},
					        	        {
				    	          		xtype:'hidden',
				    	          		name:'fschemedesignid'
				    	          	},{
				    	          		xtype:'hidden',
				    	          		name:'fsupplierid'
				    	          	}
						    	          ]
		    	       } 
		    	      ,{
			    		   bodyStyle : 'padding:5px;',
			    		   baseCls:'x-plain',
			    	        columnWidth:.5,
			    	        layout:'form',		    	    
			    	        defaults:{xtype:'textfield'},
					    	    items:[ {
					    	        	  xtype:'textfield',
					    	        		name:'fname',
					    	        		fieldLabel : '包装物名称',
					    	        		allowBlank : false,
					    	        		blankText : '名称不能为空',
					    	        		regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					    	        		regexText : "不能包含特殊字符"	
					    	          },{
					    	        	  name:'fversion',
					    	        		fieldLabel : '版本号',
					    	        		regex : /^([\u4E00-\u9FA5]|\w|\.|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					    	        		regexText : "不能包含特殊字符",
					    	        		allowBlank:false
					    	          } ,
					    	          {
					    	        	  name:'fmaterialcodeid',
					    	        		fieldLabel : '材料代码',
					    	        		regex : /^([\u4E00-\u9FA5]|\w|\S)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					    	        		regexText : "不能包含特殊字符",
					    	        		allowBlank:false
					    	          }
//					    	          ,  {
//						    	        	 
//					    	        	  name:'fmaterialcode',
//					    	        		fieldLabel : '用料编码',
//					    	        		hidden:true,
//					    	        		regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//					    	        		regexText : "不能包含特殊字符"
//					    	          },
//					    	          {
//					    	        	  xtype:'numberfield',
//					    	        	  name:'flayer',
//					    	        	  hidden:true,
//					    	        		fieldLabel : '材料层数',
//					    	        		 value:0,
//						    	        	 minValue:0,
//					    	        		 width:74,
//					    	        		 negativeText :'不能为负数'
//					    	        		
//					    	          }, {
//					    	        	  xtype:'textfield',
//					    	        	  name:'fboxmodelid',
//					    	        	  hidden:true,
//					    	        		fieldLabel : '箱型结构',
//					    	        		regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//					    	        		regexText : "不能包含特殊字符"	    	   
//					    	          },
//					    	          {
//					        	        	baseCls:'x-plain',
//					        	        	columnWidth:.25,
//					        	        	hidden:true,
//					        	        	 xtype:'fieldcontainer',
//						    	        	 fieldLabel : '纸板规格',
//						    	        	 labelWidth: 86,
//					        	        	 layout: 'hbox',
//					        	        	 items:[{
//			    	        		        	 xtype:'numberfield',
//							    	        	 name:'fboardlength',
//							    	        		 hideLabel: true,
//							    	        		 value:0,
//								    	        	 minValue:0,
//							    	        		 width:100,
//							    	        		 negativeText :'不能为负数'
//			    	        		        },
//			    	        		        {
//			    	        		        	 xtype:'displayfield',
//							    	        	value:'X',
//							    	        		 width:8
//			    	        		        },
//			    	        		        {
//			    	        		        	 xtype:'numberfield',
//							    	        	 name:'fboardwidth',
//							    	        	 value:0,
//							    	        	 minValue:0,
//							    	        	 width:100,
//							    	        	 negativeText :'不能为负数'
//							    	        	 
//			    	        		        }
//					    	          ]
//			    	     	
//					        	        }
//		    	      					,
					    	     
//					    	          {
//					    	        	  
//					    	        	  name:'fstavetype',
//					    	        	  hidden:true,
//					    	        		fieldLabel : '跑线方式',
//					    	        		regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//					    	        		regexText : "不能包含特殊字符"
//
//
//					    	          }, {
//					    	        	  name:'fvstaveexp',
//					    	        	  hidden:true,
//					    	        		fieldLabel : '纵向跑线公式',
//					    	        		regex : /^(\d|[HLWhlw]|\+|\-|\*|\/|\(|\)|\[|\]|\.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//					    	        		regexText : "不能包含特殊字符"
//					    	          },  
//					    	          {
//					    	        	  xtype:'numberfield',
//					    	        		 value:0,
//						    	        	 minValue:0,
//					    	        		 negativeText :'不能为负数',
//					    	        		 hidden:true,
//					    	        	  name:'fchromaticprecision',
//					    	        		fieldLabel : '套色精度'
//					    	        		
//					    	          },
//					    	          {
//					    	        	  name:'fcleartype',
//					    	        		fieldLabel : '清费类型',
//					    	        		hidden:true,
//					    	        		regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//					    	        		regexText : "不能包含特殊字符"
//					    	          	},	
//					    	          	{
//						    	        	  xtype:'textareafield',
//						    	        	  name:'fquality',
//						    	        	  hidden:true,
//						    	        		fieldLabel : '品质要求',
//						    	        		regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//						    	        		regexText : "不能包含特殊字符",
//						    	        		  height :30,
//							    	        		preventScrollbars:false
//						    	          },
//						    	          {
//						    	        	  xtype:'checkbox',
//						    	        	  name:'forderprice',
//						    	        	  hidden:true,
//						    	        	  boxLabel:  '按单价设'
//						    	          }
					    	      
					    	          ]
			    	        }
		    	       ]
	}]
	,
	bodyStyle : "padding-top:5px;padding-left:5px"	
});


Ext.define('DJ.order.saleOrder.SchemeDesignProductdefEdit',{
	extend:'Ext.Window',
	title:'产品信息',
	layout:'fit',
	modal:true,
	parentWin:'',
	resizable : false,
	closable : true,
	width : 730,
	minHeight : 180,
	state : '',
	setparentWin : function(o) {
		var me = this;
		me.parentWin = o;

	},
	initComponent:function(){
		Ext.apply(this,{
			items:Ext.create('DJ.order.saleOrder.ProductDefDetail'),
			dockedItems:[{
				xtype:'toolbar',
				docked:'top',
				items:[{
					text:'确定',
					handler:function(){
						var productForm = Ext.getCmp('DJ.order.saleOrder.ProductDefDetail'),
							win = this.up('window'),
							store = Ext.getCmp(win.parentWin.product).getStore(),
							//store = Ext.getCmp('DJ.order.Deliver.SchemeDesignProductProducts').getStore(),//暂时改为产品编辑

							record,fname;
						/*store.findBy(function(){
							
						});*/
						if(productForm.isValid()){
							fname = win.down("textfield[name=fname]").getValue();
							if(win.state=='add'){
								if(store.find('fname',fname,null,null,null,true)!=-1){
									Ext.Msg.alert('提示','此产品名称已存在，请重新填写！');
									return;
								}
								record = Ext.create('SchemeDesignProductInfo');
								productForm.getForm().updateRecord(record);
								store.add(record);
							}else{
								record = win.editModel;
								var index = store.findBy(function(model){
									if(model!=record&&model.get('fname')==fname){
										return true;
									}
								});
								if(index!=-1){
									Ext.Msg.alert('提示','此产品名称已存在，请重新填写！');
									return;
								}
								productForm.getForm().updateRecord(record);
								Ext.getCmp(win.parentWin.tree).reloadProductNode();
//								Ext.getCmp('DJ.order.saleOrder.schemeDesignProduct.ProductTree').reloadProductNode();
							}
							win.close();
						}
					}
				},{
					text:'取消',
					handler:function(){
						this.up('window').close();
					}
				}]
			}]
		});
		this.callParent(arguments);
	}
});
