var houseType = Ext.create('Ext.data.Store', {
    fields: ['typevalue', 'typename'],
    data : [
        {"typevalue":"1", "typename":"生产入库"},
//        {"typevalue":"2", "typename":"生产入库"},
        {"typevalue":"9", "typename":"未分类"}
    ]
});

Ext.define('DJ.Inv.productindetail.productindetailEdit', {
	extend : 'Ext.c.BaseEditUI',
	id:'DJ.Inv.productindetail.productindetailEdit',
	modal : true,
	title : "入库编辑界面",
	width : 650,// 230, //Window宽度
	height :250,// 137, //Window高度
	resizable : false,
	url : 'Saveproductindetail.do',
	infourl:'GetproductindetailInfo.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl:'GetproductindetailInfo.do', 
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
    	        		labelWidth : 70,
    	        		allowBlank : false,
    	        		blankText : '编码不能为空',
    	        		regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
    	        		regexText : "不能包含特殊字符"
		    	   		},{
		    	   			name:'fname',
	    	        		fieldLabel : '名称',
	    	        		labelWidth : 70,
	    	        		allowBlank : false,
	    	        		blankText : '名称不能为空',
	    	        		regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
	    	        		regexText : "不能包含特殊字符"		  
		    	   		},{
		    	   			id : 'DJ.Inv.productindetail.productindetailEdit.FWarehouseID',
	  		    	   		name:'FWarehouseID',
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
										},{
											name : 'fnumber'
										}, {
											name : 'fname',
											myfilterfield : 'w.fname', // 查找字段，发送到服务端
											myfiltername : '仓库名称',// 在过滤下拉框中显示的名称
											myfilterable : true
											// 该字段是否查找字段
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
										}]
								}
	  		    	   		},{
	  		    	   			id : 'DJ.Inv.productindetail.productindetailEdit.FWarehouseSiteID',
		  		    	   		name:'FWarehouseSiteID',
		      	        		fieldLabel : '库位',
		      	        		valueField : 'fid', // 组件隐藏值
								xtype : 'cCombobox',
								labelWidth : 70,
								displayField : 'fname',// 组件显示值
								beforeExpand : function() {
									var warehouseid = Ext.getCmp("DJ.Inv.productindetail.productindetailEdit.FWarehouseID").getValue();//_combo.getValue();
	    	        	    		Ext.getCmp("DJ.Inv.productindetail.productindetailEdit.FWarehouseSiteID").setDefaultfilter([{
	    								myfilterfield : "s.fparentid",
	    								CompareType : "like",
	    								type : "string",
	    								value : warehouseid
	    							}]);
	    							Ext.getCmp("DJ.Inv.productindetail.productindetailEdit.FWarehouseSiteID").setDefaultmaskstring(" #0 ");
								},
								MyConfig : {
									width : 800,// 下拉界面
									height : 200,// 下拉界面
									url : 'GetWarehousesiteList.do', // 下拉数据来源
									fields : [
									          		{
														name : 'fid'
													}, {
														name : 'fnumber'
													}, {
														name : 'fname',
														myfilterfield : 'w.fname',
														myfiltername : '库位名称',
														myfilterable : true
													}, {
														name : 'wfname'
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
												}
										]
									}
		  		    	   		},{
		    	   			name:'ftype',
		    	   			fieldLabel : '入库类型',
		    	   			xtype:'combo',
		    	   			labelWidth : 70,
		    	   			store: houseType,
		    	   			triggerAction: 'all',
		    	   			displayField: 'typename',
		    	   			valueField: 'typevalue',
		    	   			editable : false, // 可以编辑不
		    	   			value:'9'	
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
  		    	        	labelWidth : 70,
  		    	        	hidden:true
  		    	         },{
      	        		name:'fsimplename',
      	        		fieldLabel : '简  称',
      	        		labelWidth : 70,
      	        		regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
      	        		regexText : "不能包含特殊字符",
      	        		value:''
  		    	   		},{
  		    	   		name:'FSaleOrderID',
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
							valueField : 'fid', // 组件隐藏值
							id : "DJ.Inv.productindetail.productindetailEdit.FProductID",
							name : "FProductID",
							xtype : 'cCombobox',
							displayField : 'fname',// 组件显示值
							fieldLabel : '产  品 ',
							width : 260,
							labelWidth : 70,
							beforeExpand : function() {
								var customerid = Ext.getCmp("DJ.System.DeliversEdit.fcustomerid").getValue();//_combo.getValue();
    	        	    		Ext.getCmp("DJ.System.DeliversEdit.fproductid").setDefaultfilter([{
    								myfilterfield : "t_pdt_productdef.fcustomerid",
    								CompareType : "like",
    								type : "string",
    								value : customerid
    							}]);
    							Ext.getCmp("DJ.System.DeliversEdit.fproductid").setDefaultmaskstring(" #0 ");
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
							id : 'DJ.Inv.productindetail.productindetailEdit.finqty',
							name : 'finqty',
//							xtype:'numberfield',
							xtype : 'textfield',
							fieldLabel : '入库数量',
							width : 260,
							labelWidth : 70,
							allowBlank : false,
							blankText : '入库数量不能为空',
							regex : /^(?!0)\d{0,10}$/,
							regexText : "请输入不超过10位大于0的数字"
//		    	        	minValue:0,
//		    	        	negativeText :'不能为负数'

						}]
      }]
     },{
    	 bodyStyle : 'padding-left:5px;padding-right:5px;',
 	    baseCls:'x-plain',
         layout:'anchor',	
         items:[{
					xtype:'textfield',
  	        		name:'fdescription',
  	        		fieldLabel : '描述',
  	        		labelWidth : 70,
  	        		regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
  	        		regexText : "不能包含特殊字符",
  	        		anchor:'100%'
         }]
}],
	listeners:{
 		'beforeshow':function(win)
 		{
// 			var gridd;
// 			  if(!win['showLock']){
// 				 win['showLock']=true;
// 				 gridd=Ext.create("DJ.System.warehouse.WarehouseEdit.WarehouseSitesList");
//					win.items.add('grid',gridd);
//	 	 			win.updateLayout();
//              } 
// 			  else
// 				  {
// 				 gridd=Ext.getCmp("DJ.System.warehouse.WarehouseEdit.WarehouseSitesList");
// 				  }
// 	 			if( win.editstate=='view')
// 	 			{
// 	 				win.height=500;
// 	 				var fparentid=win.editdata.fid;		
// 	 				gridd.store.setDefaultfilter([{
// 						myfilterfield : "fparentid",
// 						CompareType : "=",
// 						type : "string",
// 						value : fparentid
// 					}]);
// 	 				gridd.store.setDefaultmaskstring(" #0 ");
// 	 				gridd.show();
// 	 			}
// 	 			else
// 	 				{
// 	 				win.height=250;
// 	 				gridd.hide();
// 	 				}
 			  

// 			win.height=250;
// 			if(win.items.length<2 && win.editstate=='view')
// 			{
// 				win.height=500;
// 				var gridd=Ext.getCmp("DJ.System.warehouse.WarehouseEdit.WarehouseSitesList");
// 				if(gridd==null)
// 				{
// 					gridd=Ext.create("DJ.System.warehouse.WarehouseEdit.WarehouseSitesList");
// 					win.items.add('grid',gridd);
//// 	 	 			win.doLayout();
// 	 	 			win.updateLayout();
// 				}
// 				var fparentid=win.editdata.fid;		
// 				gridd.store.setDefaultfilter([{
//					myfilterfield : "fparentid",
//					CompareType : "=",
//					type : "string",
//					value : fparentid
//				}]);
// 				gridd.store.setDefaultmaskstring(" #0 ");
// 			
// 			}

 		}
 		
 	},
 	bodyStyle : "padding-left:5px;"
	}), this.callParent(arguments);
		}
});

function formatEffect(value){        
    return value=='1'?'是':'否';  
}
