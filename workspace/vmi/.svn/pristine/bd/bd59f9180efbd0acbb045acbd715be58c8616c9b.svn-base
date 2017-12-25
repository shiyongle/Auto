Ext.define('DJ.order.Deliver.AssignDeliverapplyEdit', {
			extend : 'Ext.Window',
			id : 'DJ.order.Deliver.AssignDeliverapplyEdit',
			modal : true,
			title : "指定生成",
			width : 400,
			height : 150,
			resizable : false,
			closable : true,
			items : [{ 
				 xtype : 'form',
//				 id:'DJ.System.product.ProductDefEdit.dForm',
				 baseCls:'x-plain',
				 fieldDefaults: {
			            labelWidth: 85
	    		  },
				 layout : 'form',
			items : [{	
						name:'fproductid',
    	        		fieldLabel : '产品名称',
    	        		xtype:'cCombobox',
    	        		displayField:'fname', // 这个是设置下拉框中显示的值
    	        	    valueField:'fid', // 这个可以传到后台的值
    	        	    allowBlank : false,
    	        	    blankText:'请选择产品',
    	        	    editable: false, // 可以编辑不
    	        	     beforeExpand : function() {
							var form= Ext.getCmp("DJ.order.Deliver.AssignDeliverapplyEdit").items.getAt(0).getForm();
							var customerid=form.findField('fcustomerid').getValue();//_combo.getValue();
	        	    		if(customerid=='null'||customerid=='')
	        	    		{
	        	    			customerid="%";
	        	    		}
	        	    		form.findField('fproductid').setDefaultfilter([{
								myfilterfield : "d.fcustomerid",
								CompareType : "like",
								type : "string",
								value : customerid
							}]);
							form.findField('fproductid').setDefaultmaskstring(" #0 ");
	        	    		
    	        	    },
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
		    	   			name:'famount',
		    	   			fieldLabel : '数量',
		    	   			xtype:'numberfield',
		    	   			value:0,
	    	        		minValue:1,
	    	        		minText:'数量不能小于1！'
//	    	        		decimalPrecision:4,
		    	   		},{
		    	   			name:'fidcls',
		    	   			xtype:'hidden'
//							hidden : true
		    	   		},{
		    	   			name:'fcustomerid',
		    	   			xtype:'hidden'
//							hidden : true
		    	   		},{
		    	   			name:'ffnumber',
		    	   			xtype:'hidden'
//							hidden : true
		    	   		}]
		    	   		}],
			buttons : [{
						text : "确定",
						pressed : false,
						handler : function() {
						var editwin=Ext.getCmp("DJ.order.Deliver.AssignDeliverapplyEdit");
						 var cform=editwin.items.getAt(0).getForm();
						 if(!cform.isValid())
						 {
						 Ext.MessageBox.alert('提示', '输入项格式不正确，请按提示修改！');
						 return;
						 }
						
						 cform.submit({
						 url: 'AssigntoCreate.do',
						 method:'POST',
						 waitMsg:'正在处理请求……',
						 timeout: 60000,
						 success: function(form, action) {
						 var obj = Ext.decode(action.response.responseText);
						   djsuccessmsg( obj.msg);
							Ext.getCmp("DJ.order.Deliver.DeliversList").store.load();
							editwin.close();
						 },
						 failure:function(f,action){
						 var obj = Ext.decode(action.response.responseText);
						 Ext.MessageBox.alert('错误', obj.msg);
						 }
						 });
						
						}
					}
					, {
						text : "取消",
						handler : function() {
							var windows = Ext.getCmp("DJ.order.Deliver.AssignDeliverapplyEdit");
							if (windows != null) {
								windows.close();
							}
						}
					}
					],
			buttonAlign : "center",
			bodyStyle : "padding-top:15px ;padding-left:30px;padding-right:30px"
		});