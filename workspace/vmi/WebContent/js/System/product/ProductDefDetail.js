Ext.define('DJ.System.product.pCustomerStore', {
	extend : 'Ext.data.Store',
	id : 'DJ.System.product.pCustomerStore',
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
var customerStore=Ext.create('DJ.System.product.pCustomerStore');

Ext.define('DJ.System.product.ProductDefDetail', {
	extend : 'Ext.form.Panel',
	modal : true,
////	title : "产品管理编辑界面",
////	width : 640,// 230, //Window宽度
////	height : 480,// 137, //Window高度
	header:false,
	resizable : false,
////	closable : true, // 关闭按钮，默认为true
		 id:'DJ.System.product.ProductDefDetail',
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
				    	        		fieldLabel : '产品编码',
				    	        		allowBlank : false,
				    	        		blankText : '编码不能为空',
				    	        		regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
				    	        		regexText : "不能包含特殊字符"
						    	   		}, {
						    	        	  name:'forderunitid',
						    	        		fieldLabel : '计量单位',
						    	        		regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						    	        		regexText : "不能包含特殊字符"
						    	        		
		
						    	          },	 {
						    	        	  name:'fcusproductname',
					    	        		fieldLabel : '客户产品名称',
					    	        		regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					    	        		regexText : "不能包含特殊字符"
					    	          },{
						    	   		name:'fcustomerid',
				    	        		fieldLabel : '客户名称',
				    	        		store:customerStore,
				    	        		triggerAction:"all",
				    	        		xtype:'combo',
				    	        		 displayField:'fname', // 这个是设置下拉框中显示的值
				    	        	     valueField:'fid', // 这个可以传到后台的值
				    	        	      editable: false, // 可以编辑不
				    	        	      forceSelection: true,
				    	        	      pageSize:10,
				    	        	      mode:'remote'
						    	   		},     {
						    	        	  name:'ftilemodelid',
						    	        		fieldLabel : '瓦楞楞型',
						    	        		regex : /^([\u4E00-\u9FA5]|\w|\S)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						    	        		regexText : "不能包含特殊字符"
						    	        	
						    	     
						    	          }, {
						    	        	  name:'fnewtype',
					    	        			fieldLabel : '产品类型(新)',
					    	        			regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						    	        		regexText : "不能包含特殊字符"
					    	        			
					    	          },{
							        	     
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
												    	        		 negativeText :'不能为负数'
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
												    	        	 negativeText :'不能为负数'
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
												    	        	 negativeText :'不能为负数'
								    	        		        }]
								    	     	
					        	        
					        	        },  
						        	       {
						        	        
					        	        	baseCls:'x-plain',
					        	        	columnWidth:.25,
					        	        	 xtype:'fieldcontainer',
						    	        	 fieldLabel : '出库规格',
						    	        	 labelWidth: 87,
					        	        	 layout: 'hbox',
					        	        	 items:[{
			    	        		        	 xtype:'numberfield',
							    	        	 name:'fmateriallength',
							    	        		 hideLabel: true,
							    	        		 value:0,
								    	        	 minValue:0,
							    	        		 width:100,
							    	        		 negativeText :'不能为负数'
			    	        		        },
			    	        		        {
			    	        		        	 xtype:'displayfield',
							    	        	value:'X',
							    	        		 width:8
			    	        		        },
			    	        		        {
			    	        		        	 xtype:'numberfield',
							    	        	 name:'fmaterialwidth',
							    	        	 value:0,
							    	        	 minValue:0,
							    	        	 width:100,
							    	        	 negativeText :'不能为负数'
							    	        	 
			    	        		        }]
					        	        },
					    	         
						    	          {
						    	        	  name:'fhstaveexp',
						    	        		fieldLabel : '横向跑线公式',
						    	        		regex : /^(\d|[HLWhlw]|\+|\-|\*|\/|\(|\)|\[|\]|\.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						    	        		regexText : "不能包含特殊字符"
						    	          }, {
						    	        	  xtype:'numberfield',
						    	        	   name:'farea',
						    	        		fieldLabel : '单位面积',
						    	        		 value:0,
							    	        	 minValue:0,
							    	        	 negativeText :'不能为负数'
							    	      
						    	        		
						    	          }, {
						    	        	  xtype:'numberfield',
						    	        	  name:'fmaterialcost',
						    	        		fieldLabel : '材料成本',
						    	        		 value:0,
							    	        	 minValue:0	,
							    	        	 negativeText :'不能为负数'
						    	          },  {
						    	        	  xtype:'textareafield',
						    	        	  name:'fcharacter',
						    	        		fieldLabel : '产品特征',
						    	        		regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						    	        		regexText : "不能包含特殊字符",
						    	        		  height :30,
						    	        		preventScrollbars:false

						    	          }, { 
						    	        	  xtype:'textareafield',
						    	        	  name:'fmodelmethod',
						    	        		fieldLabel : '成型方式',
						    	        		regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						    	        		regexText : "不能包含特殊字符",
						    	        		  height :30,
							    	        		preventScrollbars:false
							    	        	}, { 
						    	        	xtype:'textarea',
						    	        	 name:'fdescription',
						    	        		fieldLabel : '产品备注',
						    	        		regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						    	        		regexText : "不能包含特殊字符",
						    	        		 height :30					    	        	


							    	          }, {
						    	          		 
												xtype : 'hidden',
												name:'fid'
							    	          
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
					    	        		fieldLabel : '产品名称',
					    	        		allowBlank : false,
					    	        		blankText : '名称不能为空',
					    	        		regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					    	        		regexText : "不能包含特殊字符"	
					    	          },{
					    	        	  name:'fversion',
					    	        		fieldLabel : '版本号',
					    	        		regex : /^([\u4E00-\u9FA5]|\w|\.|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					    	        		regexText : "不能包含特殊字符"
					    	        		
					    	          } ,
					    	          {
					    	        	  name:'fmaterialcodeid',
					    	        		fieldLabel : '用料代码',
					    	        		regex : /^([\u4E00-\u9FA5]|\w|\S)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					    	        		regexText : "不能包含特殊字符"				    	         	
					    	          },  {
						    	        	 
					    	        	  name:'fmaterialcode',
					    	        		fieldLabel : '用料编码',
					    	        		regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					    	        		regexText : "不能包含特殊字符"
					    	          },
					    	          {
					    	        	  xtype:'numberfield',
					    	        	  name:'flayer',
					    	        		fieldLabel : '材料层数',
					    	        		 value:0,
						    	        	 minValue:0,
					    	        		 width:74,
					    	        		 negativeText :'不能为负数'
					    	        		
					    	          }, {
					    	        	  xtype:'textfield',
					    	        	  name:'fboxmodelid',
					    	        		fieldLabel : '箱型结构',
					    	        		regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					    	        		regexText : "不能包含特殊字符"	    	   
					    	          },{
					        	        	baseCls:'x-plain',
					        	        	columnWidth:.25,
					        	        	 xtype:'fieldcontainer',
						    	        	 fieldLabel : '纸板规格',
						    	        	 labelWidth: 86,
					        	        	 layout: 'hbox',
					        	        	 items:[{
			    	        		        	 xtype:'numberfield',
							    	        	 name:'fboardlength',
							    	        		 hideLabel: true,
							    	        		 value:0,
								    	        	 minValue:0,
							    	        		 width:100,
							    	        		 negativeText :'不能为负数'
			    	        		        },
			    	        		        {
			    	        		        	 xtype:'displayfield',
							    	        	value:'X',
							    	        		 width:8
			    	        		        },
			    	        		        {
			    	        		        	 xtype:'numberfield',
							    	        	 name:'fboardwidth',
							    	        	 value:0,
							    	        	 minValue:0,
							    	        	 width:100,
							    	        	 negativeText :'不能为负数'
							    	        	 
			    	        		        }]
			    	     	
					        	        },
					    	     
					    	          {
					    	        	  
					    	        	  name:'fstavetype',
					    	        		fieldLabel : '跑线方式',
					    	        		regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					    	        		regexText : "不能包含特殊字符"


					    	          }, {
					    	        	  name:'fvstaveexp',
					    	        		fieldLabel : '纵向跑线公式',
					    	        		regex : /^(\d|[HLWhlw]|\+|\-|\*|\/|\(|\)|\[|\]|\.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					    	        		regexText : "不能包含特殊字符"
					    	          },  {
					    	        	  xtype:'numberfield',
					    	        		 value:0,
						    	        	 minValue:0,
					    	        		 negativeText :'不能为负数',
					    	        	  name:'fchromaticprecision',
					    	        		fieldLabel : '套色精度'
					    	        		
					    	          },{
					    	        	  name:'fcleartype',
					    	        		fieldLabel : '清费类型',
					    	        		regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
					    	        		regexText : "不能包含特殊字符"
					    	          	},	{
						    	        	  xtype:'textareafield',
						    	        	  name:'fquality',
						    	        		fieldLabel : '品质要求',
						    	        		regex : /^([\u4E00-\u9FA5]|\w|.)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						    	        		regexText : "不能包含特殊字符",
						    	        		  height :30,
							    	        		preventScrollbars:false
						    	          },
						    	          {
						    	        	  xtype:'checkbox',
						    	        	  name:'forderprice',
						    	        	  boxLabel:  '按单价设'
						    	          }
					    	      
					    	          ]
			    	        }
		    	       ]
	}],
	bodyStyle : "padding-top:5px;padding-left:5px"	
});