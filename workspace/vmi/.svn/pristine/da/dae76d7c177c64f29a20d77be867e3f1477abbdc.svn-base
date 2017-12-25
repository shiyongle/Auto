var houseType = Ext.create('Ext.data.Store', {
    fields: ['typevalue', 'typename'],
    data : [
        {"typevalue":"1", "typename":"成品仓库"},
        {"typevalue":"2", "typename":"半成品"},
        {"typevalue":"9", "typename":"未分类"}
    ]
});
Ext.define('DJ.Inv.OutWarehouse.OutWarehouseEdit', {
	extend : 'Ext.c.BaseEditUI',
	id:'DJ.Inv.OutWarehouse.OutWarehouseEdit',
	modal : true,
	title : "出库编辑界面",
	width : 650,// 230, //Window宽度
	height :250,// 137, //Window高度
	resizable : false,
	url : 'SaveOutWarehouse.do',
	infourl:'GetOutWarehouseInfo.do',
	viewurl:'GetOutWarehouseInfo.do', 
	closable : true, // 关闭按钮，默认为true
	showLock:false,
	onload : function() {
	},
	initComponent : function() {
		Ext.apply(this, {
     items : [
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
    	        		fieldLabel : '编码',
    	        		allowBlank : false,
    	        		blankText : '编码不能为空',
    	        		regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
    	        		regexText : "不能包含特殊字符"
		    	   		},{
			    	   		id : 'DJ.Inv.OutWarehouse.OutWarehouseEdit.fwarehouseid',
		    	   			name:'fwarehouseid',
	      	        		fieldLabel : '仓库',
	      	        		valueField : 'fid', // 组件隐藏值
							xtype : 'cCombobox',
							labelWidth : 70,
							displayField : 'fname',// 组件显示值
							MyConfig : {
								width : 800,// 下拉界面
								height : 200,// 下拉界面
								url : 'GetWarehouses.do', // 下拉数据来源
								fields : [{
											name : 'fid'
										}, {
											name : 'fname',
											myfilterfield : 'w.fname', // 查找字段，发送到服务端
											myfiltername : '仓库名称',// 在过滤下拉框中显示的名称
											myfilterable : true
											// 该字段是否查找字段
										}, {
											name : 'fsimplename'
										}, {
											name : 'ufname'
										}, {
											name : 'fcontrollerid'
										}, {
											name : 'cfaddresid'
										}, {
											name : 'dfname'
										},{
											name:'fdescription'
										}, {
											name : 'foutstorage'
										}, {
											name : 'finstorage'
										}, {
											name : 'cffreightprice'
										}, {
											name : 'fwarehousetype'
										}, {
											name : 'flastupdatetime'
										}, {
											name : 'lfname'
										}, {
											name : 'fcreatetime'
										}, {
											name : 'cfname'
										}],
								columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
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
										'header' : '名称',
										'dataIndex' : 'fname',
										sortable : true
										}, {
											'header' : '简称',
											'dataIndex' : 'fsimplename',
											sortable : true
										}, {
											'header' : '管理员',
											'dataIndex' : 'ufname',
											sortable : true
										}, {
											'header' : '送货地址',
											'dataIndex' : 'dfname',
											sortable : true
										}, {
											'header' : '描述',
											'dataIndex' : 'fdescription',
											sortable : true
										},{
											'header' : '修改人',
												dataIndex : 'lfname',
												sortable : true
											}, {
												'header' : '修改时间',
												dataIndex : 'flastupdatetime',
												width : 150,
												sortable : true
											}, {
												'header' : '创建人',
												dataIndex : 'cfname',
												sortable : true
											}, {
												'header' : '创建时间',
												dataIndex : 'fcreatetime',
												width : 150,
												sortable : true
											}, {
												'header' : '出库计件(元/m2)',
												dataIndex : 'foutstorage',
												sortable : true
											}, {
												'header' : '入库计件(元/m2)',
												dataIndex : 'finstorage',
												sortable : true
											}, {
												'header' : '仓库类型',
												dataIndex : 'fwarehousetype',
												sortable : true,
												 renderer: function(value){
												        if (value == 1) {
												            return '成品仓库';
												        }else if(value==2)
												        	{
												        	return '半成品';}
												        else{
												        	return '未分类';
												        }
												    }
											}, {
												'header' : '运费单价',
												dataIndex : 'cffreightprice',
												sortable : true,
												xtype: 'numbercolumn',
												format:'0.0000'
										}]
								}
		    	   		},{
				  		    	   		name:'fsaleorderid',
				      	        		fieldLabel : '销售订单',
				      	        		valueField : 'fid', // 组件隐藏值
										xtype : 'cCombobox',
										labelWidth : 70,
										displayField : 'fnumber',// 组件显示值
										MyConfig : {
											width : 800,// 下拉界面
											height : 200,// 下拉界面
											url : 'GetSaleOrders.do', // 下拉数据来源
											fields : [
											          	{
															name : 'fid'
														}, {
															name : 'fnumber'
														}, {
															name : 'cname',
															myfilterfield : 'c.fname',
															myfiltername : '客户名称',
															myfilterable : true
														}, {
															name : 'pname',
															myfilterfield : 'p.fname',
															myfiltername : '客户产品名称',
															myfilterable : true
														}, {
															name : 'fspec'
														}, {
															name : 'farrivetime'
														}, {
															name : 'fbizdate'
														}, {
															name : 'famount'
														}, {
															name : 'flastupdatetime'
														}, {
															name : 'u2_fname'
														}, {
															name : 'fcreatetime'
														}, {
															name : 'u1_fname'
														}
													],
											columns : [Ext.create('DJ.Base.Grid.GridRowNum'),
											           		{
																'header' : 'fid',
																'dataIndex' : 'fid',
																hidden : true,
																hideable : false,
																sortable : true
															}, {
																'header' : '订单编号',
																'dataIndex' : 'fnumber',
																sortable : true
															}, {
															'header' : '客户名称',
															'dataIndex' : 'cname',
															sortable : true
															}, {
																'header' : '客户产品名称',
																'dataIndex' : 'pname',
																sortable : true
															}, {
																'header' : '规格',
																'dataIndex' : 'fspec',
																sortable : true
															}, {
																'header' : '数量',
																'dataIndex' : 'famount',
																sortable : true
															}, {
																'header' : '交期',
																'dataIndex' : 'farrivetime',
																sortable : true
															}, {
																'header' : '业务时间',
																'dataIndex' : 'fbizdate',
																sortable : true
															},{
																	text : '修改人',
																	dataIndex : 'u2_fname',
																	sortable : true
																}, {
																	text : '修改时间',
																	dataIndex : 'flastupdatetime',
																	width : 150,
																	sortable : true
																}, {
																	text : '创建人',
																	dataIndex : 'u1_fname',
																	sortable : true
																}, {
																	text : '创建时间',
																	dataIndex : 'fcreatetime',
																	width : 150,
																	sortable : true
															}
														]
										}  		    	   		
		    	   		},{
//		    	   		xtype:'numberfield',
		    	   		xtype : 'textfield',
		    	   		fieldLabel : '数量',
	    	        	name:'foutqty',
	    	        	allowBlank : false,
	    	        	blankText : '入库数量不能为空',
						regex : /^(?!0)\d{0,10}$/,
						regexText : "请输入不超过10位大于0的数字"
//	    	        	minValue:0,
//	    	        	negativeText :'不能为负数'
		    	   		}]
      },{
    	  bodyStyle : 'padding:5px;',
  	    baseCls:'x-plain',
          columnWidth:.5,
          layout:'form',	
          defaults:{xtype:'textfield'},
  		 items:[ 
  		    	         {
  		    	        	 name:'fid',
  		    	        	hidden:true
  		    	         },{
//	  		    	        xtype:'combo',
//	      	        		name:'fissueenums',
//	      	        		fieldLabel : '出库类型',
//	      	        		valueField : 'fid', // 组件隐藏值
//							displayField : 'fname'
							
							name:'fissueenums',
		    	   			fieldLabel : '出库类型',
		    	   			xtype:'combo',
		    	   			labelWidth : 70,
		    	   			store: houseType,
		    	   			triggerAction: 'all',
		    	   			displayField: 'typename',
		    	   			valueField: 'typevalue',
		    	   			editable : false, // 可以编辑不
		    	   			value:'9'	
  		    	   		},{
  		    	   		id : 'DJ.Inv.OutWarehouse.OutWarehouseEdit.fwarehousesiteid',
  		    	   		name:'fwarehousesiteid',
      	        		fieldLabel : '库位',
      	        		valueField : 'fid', // 组件隐藏值
						xtype : 'cCombobox',
						displayField : 'fname',// 组件显示值
						beforeExpand : function() {
							var warehouseid = Ext.getCmp("DJ.Inv.OutWarehouse.OutWarehouseEdit.fwarehouseid").getValue();//_combo.getValue();
	        	    		Ext.getCmp("DJ.Inv.OutWarehouse.OutWarehouseEdit.fwarehousesiteid").setDefaultfilter([{
								myfilterfield : "s.fparentid",
								CompareType : "like",
								type : "string",
								value : warehouseid
							}]);
							Ext.getCmp("DJ.Inv.OutWarehouse.OutWarehouseEdit.fwarehousesiteid").setDefaultmaskstring(" #0 ");
						},
						MyConfig : {
							width : 800,// 下拉界面
							height : 200,// 下拉界面
							url : 'GetWarehouseSites.do', // 下拉数据来源
							fields : [{
								name : 'fid'
							}, {
								name : 'fnumber'
							}, {
								name : 'fname',
								myfilterfield : 'w.fname',
								myfiltername : '仓库名称',
								myfilterable : true
							}, {
								name : 'wfname'
							}, {
								name : 'fparentid'
							}, {
								name : 'faddress'
							},{
								name:'fremark'
							}, {
								name : 'finstoreprice'
							}, {
								name : 'foutstoreprice'
							}, {
								name : 'farea'
							}, {
								name : 'flastupdatetime'
							}, {
								name : 'lfname'
							}, {
								name : 'fcreatetime'
							}, {
								name : 'cfname'
							}],
					columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
								'header' : 'fid',
								'dataIndex' : 'fid',
								hidden : true,
								hideable : false,
								sortable : true
							}, {
								'header' : '仓位编码',
								'dataIndex' : 'fnumber',
								sortable : true
							}, {
							'header' : '仓位名称',
							'dataIndex' : 'fname',
							sortable : true
							}, {
								'header' : '所属仓库',
								'dataIndex' : 'wfname',
								sortable : true
							}, {
								'header' : '容量(m2)',
								'dataIndex' : 'farea',
								sortable : true,
								xtype: 'numbercolumn',
								format:'0.0000'

							}, {
								'header' : '出库计件(元/m2)',
								dataIndex : 'foutstoreprice',
								sortable : true,
								xtype: 'numbercolumn',
								format:'0.0000'
							}, {
								'header' : '入库计件(元/m2)',
								dataIndex : 'finstoreprice',
								sortable : true,
								format:'0.0000',
								xtype: 'numbercolumn'
							}, {
								'header' : '地址',
								'dataIndex' : 'faddress',
								sortable : true
							}, {
								'header' : '备注',
								'dataIndex' : 'fremark',
								sortable : true
							},{
								'header' : '修改人',
									dataIndex : 'lfname',
									sortable : true
								}, {
									'header' : '修改时间',
									dataIndex : 'flastupdatetime',
									width : 150,
									sortable : true
								}, {
									'header' : '创建人',
									dataIndex : 'cfname',
									sortable : true
								}, {
									'header' : '创建时间',
									dataIndex : 'fcreatetime',
									width : 150,
									sortable : true
								
							}]
						}
  		    	   		},{
  		    	   		name:'fproductid',
      	        		fieldLabel : '产品',
      	        		valueField : 'fid', // 组件隐藏值
						xtype : 'cCombobox',
						displayField : 'fname',// 组件显示值
						MyConfig : {
							width : 800,// 下拉界面
							height : 200,// 下拉界面
							url : 'GetProductss.do', // 下拉数据来源
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
								name : 'flastupdateuserid'
							}, {
								name : 'flastupdatetime'
							}, {
								name : 'u2_fname'
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
							}, {
								name : 'fishistory',
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
								sortable : true
							}, {
								text : '特征',
								dataIndex : 'fcharacter',
								sortable : true,
								width : 100
							}, {
								text: '箱型',
								dataIndex : 'fboxmodelid',
								sortable : true
								
							}, {
								text : '类型',
								dataIndex : 'fnewtype',
								sortable : true
							}, {
								text : '历史版本',
								dataIndex : 'fishistory',
								xtype : 'checkcolumn',
								processEvent : function() {
								},
								sortable : true
							}, {
								text : '禁用或启用',
								xtype : 'checkcolumn',
								dataIndex : 'feffect',
								processEvent : function() {
								},
								sortable : true
							},
							 {
								text : '修改人',
								dataIndex : 'u2_fname',
								sortable : true
							}, {
								text : '修改时间',
								dataIndex : 'flastupdatetime',
								width : 150,
								sortable : true
							
							}, {
								text : '创建人',
								dataIndex : 'u1_fname',
								sortable : true
							}, {
								text : '创建时间',
								dataIndex : 'fcreatetime',
								width : 150,
								sortable : true

							} ]
						}

      	     }]
      }]
     },{
    	 bodyStyle : 'padding-left:5px;padding-right:5px;',
 	    baseCls:'x-plain',
         layout:'anchor',	
         items:[{
					xtype:'textarea',
  	        		name:'fremak',
  	        		fieldLabel : '备注',
  	        		regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
  	        		regexText : "不能包含特殊字符",
  	        		anchor:'100%'
         }]
}],
	listeners:{
 		'beforeshow':function(win)
 		{
// 		var w=win.getform();
// 		alert(w.fieldDefaults.labelWidth);

 		
 		}
 		
 	},
 	bodyStyle : "padding-left:5px;"
		}), this.callParent(arguments);
 	}			
 		
 	
});