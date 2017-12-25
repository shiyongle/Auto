
Ext.define('DJ.System.product.ProductCycleEdit', {
	extend : 'Ext.c.BaseEditUI',
	id:'DJ.System.product.ProductCycleEdit',
	modal : true,
	title : "生命周期管理编辑界面",
	width : 350,// 230, //Window宽度
	height : 180,// 137, //Window高度
	resizable : false,
	url : 'SaveProductCycle.do',
	infourl:'GetCycleInfo.do',
	viewurl:'GetCycleInfo.do', 
	closable : true, // 关闭按钮，默认为true
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
	},
     layout:{
    	 type: 'vbox',
         align: 'center',
         pack:'center'
     } ,
     initComponent : function() {
 		Ext.apply(this, {
    	 items:[{
     
    	        		name:'fproductdefid',
    	        		fieldLabel : '产品名称',
    	        		xtype:'cCombobox',
    	        		labelWidth: 86,
    	        		displayField:'fname', // 这个是设置下拉框中显示的值
    	        	    valueField:'fid', // 这个可以传到后台的值
    	        	    allowBlank : false,
    	        	    blankText:'请选择产品',
    	        	    editable: false, // 可以编辑不
    	        	    MyConfig : {
    	 					width : 800,//下拉界面
    	 					height : 200,//下拉界面
    	 					url : 'GetProductss.do',  //下拉数据来源
    	 					fields : [{
    							name : 'fid'
    						}, {
    							name : 'fname',
    							myfilterfield : 'd.fname',
    							myfiltername : '名称',
    							myfilterable : true
    						}, {
    							name : 'fnumber'
    						}, {
    							name : 'fcreatorid'
    						}, {
    							name : 'fcreatetime'
    						}, {
    							name : 'u1_fname'
    						}, {
    							name : 'fcharacter'
    						}, {
    							name : 'fboxmodelid'
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
    							name : 'fnewtype'
    						}, {
    							name : 'fversion'
    						}],
    	 					columns : [{
    							text : 'fid',
    							dataIndex : 'fid',
    							hidden : true,
    							hideable : false,
    							sortable : true
    						}, {
    							text : '编号',
    							dataIndex : 'fnumber',
    							sortable : true
    						}, {
    							text : '名称',
    							dataIndex : 'fname',
    							sortable : true
    						}, {
    							text : '版本号',
    							dataIndex : 'fversion',
    							sortable : true,
    							width:50
    						}, {
    							text : '特征',
    							dataIndex : 'fcharacter',
    							sortable : true
    						}, {
    							text: '箱型',
    							dataIndex : 'fboxmodelid',
    							sortable : true
    							
    						}, {
    							text : '类型',
    							dataIndex : 'fnewtype',
    							sortable : true
    						}, {
    							text : '禁用/启用',
    							xtype : 'checkcolumn',
    							dataIndex : 'feffect',
    							processEvent : function() {
    							},
    							width:60,
    							sortable : true
    						},{
    							text : '创建人',
    							dataIndex : 'u1_fname',
    							sortable : true
    						}, {
    							text : '创建时间',
    							dataIndex : 'fcreatetime',		
    							sortable : true

    						} ]
    	 				}
		    	   		},{
		    	   		name:'fsupplierid',
    	        		fieldLabel : '供应商名称',  	
    	        		labelWidth: 86,
    	        		allowBlank : false,
    	        		 blankText:'请选择供应商',
    	        		 editable: false, // 可以编辑不
    	        		xtype:'cCombobox',
    	        		displayField:'fname', // 这个是设置下拉框中显示的值
    	        	    valueField:'fid', // 这个可以传到后台的值
    	        	     MyConfig : {
    	 					width : 800,//下拉界面
    	 					height : 200,//下拉界面
    	 					url : 'GetSupplierList.do',  //下拉数据来源
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
    							name : 'fdescription'
    						}, {
    							name : 'fcreatetime'
    						}, {
    							name : 'flastupdatetime'
    						}, {
    							name : 'fsimplename'
    						}, {
    							name : 'fartificialperson'
    						}, {
    							name : 'fbarcode'
    						}, {
    							name : 'fusedstatus',
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
    							name : 'fmnemoniccode'
    						}, {
    							name : 'faddress'
    						
    						}],
    	 					columns :[{
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
    							'header' : '简称',
    							width : 70,
    							'dataIndex' : 'fsimplename',
    							sortable : true
    						}, {
    							'header' : '法人代表',
    							width : 70,
    							'dataIndex' : 'fartificialperson',
    							sortable : true
    						}, {
    							'header' : '条形码',
    							hidden : true,
    							'dataIndex' : 'fbarcode',
    							sortable : true
    						}, {
    							'header' : '状态',
    							width : 50,
    							'dataIndex' : 'fusedstatus',
    							xtype : 'checkcolumn',
    							processEvent : function() {
    							},
    							sortable : true
    						}, {
    							'header' : '助记码',
    							'dataIndex' : 'fmnemoniccode',
    							sortable : true
    						}, {
    							'header' : '地址',
    							'dataIndex' : 'faddress',
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
		    	   		
		    	   		
		    	   		}
		    	   		, {
//	    	        	  name:'flifecycle',
//	    	        	  allowBlank : false,
//    	        		  fieldLabel : '生命周期',
//    	        		  xtype:'numberfield',
//	    	        	  value:0,
//		    	          minValue:0,
//	    	        	  negativeText :'不能为负数'		    	         	
		    	   		  
		        	        	baseCls:'x-plain',
		        	        	columnWidth:.25,
		        	        	 xtype:'fieldcontainer',
			    	        	 fieldLabel : '生命周期',
			    	        	 labelWidth: 86,
		        	        	 layout: 'hbox',
		        	        	 items:[{
    	        		        	  name:'flifecycle',
				    	        	  allowBlank : false,
			    	        		  hideLabel: true,
			    	        		  xtype:'numberfield',
				    	        	  value:0,
					    	          minValue:0,
				    	        	  negativeText :'不能为负数'
    	        		        },
    	        		        {
    	        		        	xtype:'displayfield',
				    	        	value:'小时',
				    	        	width:28
    	        		        }]
	    	          }
	    	          ,{
	    	        	  
	    	        	  name:'fid',
	    	        	  xtype:'hidden'
	    	          }]
       
 		}), this.callParent(arguments);
 	}
	
//	bodyStyle : "padding-top:5px;padding-left:30px"
	});