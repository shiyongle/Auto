var houseType = Ext.create('Ext.data.Store', {
    fields: ['typevalue', 'typename'],
    data : [
        {"typevalue":"1", "typename":"成品仓库"},
        {"typevalue":"2", "typename":"半成品"},
        {"typevalue":"9", "typename":"未分类"}
    ]
});


	Ext.define('DJ.Inv.warehouse.WarehouseEdit.WarehouseSitesList', {
		extend : 'Ext.c.GridPanel',
		id : 'DJ.Inv.warehouse.WarehouseEdit.WarehouseSitesList',
		pageSize : 10,
		width : 630,// 230, //Window宽度
		height :250,// 137, //Window高度
		onload : function() {
			// 加载后事件，可以设置按钮，控件值等
			var tools=Ext.getCmp("DJ.Inv.warehouse.WarehouseEdit.WarehouseSitesList.viewbutton").ownerCt;
 			tools.hide();
		},
		 initComponent : function() {
				Ext.apply(this, {
		url : 'GetWarehouseSites.do',
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
				 }), this.callParent(arguments);
		 	}
				
	})

Ext.define('DJ.Inv.warehouse.WarehouseEdit', {
	extend : 'Ext.c.BaseEditUI',
	id:'DJ.Inv.warehouse.WarehouseEdit',
	modal : true,
	title : "仓库编辑界面",
	width : 650,// 230, //Window宽度
	height :250,// 137, //Window高度
	resizable : false,
	url : 'SaveWarehouse.do',
	infourl:'GetWarehouseInfo.do',
	viewurl:'GetWarehouseInfo.do', 
	closable : true, // 关闭按钮，默认为true
	showLock:false,
	onload : function() {
	},
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
    	        		emptyText:'系统自动生成',
    	        		listeners:{
    	        		render:function(me){
    	        		me.setReadOnly(true)}
    	        		}
 //    	        		allowBlank : false,
//    	        		blankText : '编码不能为空',
//    	        		regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
//    	        		regexText : "不能包含特殊字符"
		    	   		},{
		    	   			name:'fname',
	    	        		fieldLabel : '名称',
	    	        		allowBlank : false,
	    	        		blankText : '名称不能为空',
	    	        		regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
	    	        		regexText : "不能包含特殊字符"		  
		    	   		},{
		    	   			name:'fcontrollerid',
	    	        		fieldLabel : '管理员',
//	    	        		allowBlank : false,
//	    	        		blankText : '管理员不能为空',
	    	        		valueField : 'fid', // 组件隐藏值
							xtype : 'cCombobox',
							displayField : 'fname',// 组件显示值
//							allowBlank:false,
							editable:false,
							MyConfig : {
								width : 800,// 下拉界面
								height : 200,// 下拉界面
								url : 'GetUserList.do', // 下拉数据来源
								fields : [{
											name : 'fid'
										}, {
											name : 'fname',
											myfilterfield : 'T_SYS_USER.fname', // 查找字段，发送到服务端
											myfiltername : '名称',// 在过滤下拉框中显示的名称
											myfilterable : true
											// 该字段是否查找字段
									}	, {
											name : 'feffect'
										}, {
											name : 'fcustomername',
											myfilterfield : 'T_SYS_USER.fcustomername',
											myfiltername : '客户名称',
											myfilterable : true
										}, {
											name : 'femail'
										}, {
											name : 'ftel'
										}, {
											name : 'fcreatetime'
										}],
								columns : [{
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
										}]
							}
		    	   		},{
		    	   			xtype:'numberfield',
		    	   			name:'foutstorage',
		    	   			fieldLabel : '出库计件(元/m2)',
		    	        	value:0,
		    	        	minValue:0,
		    	         	decimalPrecision:4,
		    	        	negativeText :'不能为负数'
		    	   		},{
		    	   			name:'fwarehousetype',
		    	   			fieldLabel : '仓库类型',
		    	   			xtype:'combo',
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
  		    	        	hidden:true
  		    	         },{
      	        		name:'fsimplename',
      	        		fieldLabel : '简称',
      	        		regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
      	        		regexText : "不能包含特殊字符",
      	        		value:''
  		    	   		},{
  		    	   		name:'cfaddresid',
      	        		fieldLabel : '送货地址',
      	        		valueField : 'fid', // 组件隐藏值
						xtype : 'cCombobox',
						displayField : 'fname',// 组件显示值
//						allowBlank:false,
						editable:false,
						MyConfig : {
							width : 800,// 下拉界面
							height : 200,// 下拉界面
							url : 'GetAddressList.do', // 下拉数据来源
							fields : [{
										name : 'fid'
									}, {
										name : 'fname',
										myfilterfield : 't_bd_Address.fname', // 查找字段，发送到服务端
										myfiltername : '名称',// 在过滤下拉框中显示的名称
										myfilterable : true
										// 该字段是否查找字段
								}	, {
										name : 'fnumber',
										myfilterfield : 'fnumber',
										myfiltername : '编码',
										myfilterable : true
									}, {
										name : 'fdetailaddress',
										myfilterfield : 'fdetailaddress',
										myfiltername : '详细地址',
										myfilterable : true
									}, {
										name : 'femail'
									}, {
										name : 'flinkman'
									}, {
										name : 'fphone'
									},{
										name:'fcreatetime'
									}],
							columns : [{
										'header' : 'fid',
										'dataIndex' : 'fid',
										hidden : true,
										hideable : false,
										sortable : true

									}, {
										'header' : '名称',
										'dataIndex' : 'fname',
										sortable : true
									}, {
										'header' : '编码',
										'dataIndex' : 'fnumber',
										sortable : true
									}, {
										'header' : '详细地址',
										'dataIndex' : 'fdetailaddress',
										sortable : true
									}, {
										'header' : '邮箱',
										'dataIndex' : 'femail',
										sortable : true
									}, {
										'header' : '联系人',
										'dataIndex' : 'flinkman',
										sortable : true
									}, {
										'header' : '联系电话',
										'dataIndex' : 'fphone',
										sortable : true
									}, {
										'header' : '创建时间',
										'dataIndex' : 'fcreatetime',
										width : 200,
										sortable : true
									}]
						}
  		    	   		},{
  		    	   		xtype:'numberfield',
	    	   			name:'finstorage',
	    	   			fieldLabel : '入库计件(元/m2)',
	    	        	value:0,
	    	        	minValue:0,
	    	        	decimalPrecision:4,
	    	        	negativeText :'不能为负数'
  		    	  	},{
  		    	   		xtype:'numberfield',
	    	   			name:'cffreightprice',
	    	   			fieldLabel : '运费单价',
	    	        	value:0,
	    	        	minValue:0,
	    	        	decimalPrecision:4,
	    	        	negativeText :'不能为负数'
      	        		
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
  	        		regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
  	        		regexText : "不能包含特殊字符",
  	        		anchor:'100%'
         }]
}],
	listeners:{
 		'beforeshow':function(win)
 		{
 			var gridd;
 			  if(!win['showLock']){
 				 win['showLock']=true;
 				 gridd=Ext.create("DJ.Inv.warehouse.WarehouseEdit.WarehouseSitesList");
					win.items.add('grid',gridd);
	 	 			win.updateLayout();
              } 
 			  else
 				  {
 				 gridd=Ext.getCmp("DJ.Inv.warehouse.WarehouseEdit.WarehouseSitesList");
 				  }
 	 			if( win.editstate=='view')
 	 			{
 	 				win.height=500;
 	 				var fparentid=win.editdata.fid;		
 	 				gridd.store.setDefaultfilter([{
 						myfilterfield : "fparentid",
 						CompareType : "=",
 						type : "string",
 						value : fparentid
 					}]);
 	 				gridd.store.setDefaultmaskstring(" #0 ");
 	 				gridd.show();
 	 			}
 	 			else
 	 				{
 	 				win.height=250;
 	 				gridd.hide();
 	 				}
 			  

// 			win.height=250;
// 			if(win.items.length<2 && win.editstate=='view')
// 			{
// 				win.height=500;
// 				var gridd=Ext.getCmp("DJ.Inv.warehouse.WarehouseEdit.WarehouseSitesList");
// 				if(gridd==null)
// 				{
// 					gridd=Ext.create("DJ.Inv.warehouse.WarehouseEdit.WarehouseSitesList");
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
 				
 		
 	
});