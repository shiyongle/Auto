

Ext.define('DJ.Inv.WarehouseSites.WarehouseSitesEdit', {
	extend : 'Ext.c.BaseEditUI',
	id:'DJ.Inv.WarehouseSites.WarehouseSitesEdit',
	modal : true,
	title : "仓位编辑界面",
	width : 650,// 230, //Window宽度
	height :250,// 137, //Window高度
	resizable : false,
	url : 'SaveWarehouseSite.do',
	infourl:'GetWarehouseSiteInfo.do',
	viewurl:'GetWarehouseSiteInfo.do', 
	closable : true, // 关闭按钮，默认为true
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
	},
	 initComponent : function() {
			Ext.apply(this, {
     items : [{
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
		    	   			xtype:'numberfield',
		    	   			name:'farea',
	    	        		fieldLabel : '容量(m2)',
	    	        		value:0,
		    	        	minValue:0,
		    	         	decimalPrecision:4,
		    	        	negativeText :'不能为负数'
		    	   		},{
		    	   			xtype:'numberfield',
		    	   			name:'foutstoreprice',
		    	   			fieldLabel : '出库计件(元/m2)',
		    	        	value:0,
		    	        	minValue:0,
		    	         	decimalPrecision:4,
		    	        	negativeText :'不能为负数'
		    	   	
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
      	        		name:'fname',
      	        		fieldLabel : '名称',
      	        		allowBlank : false,
    	        		blankText : '名称不能为空',
      	        		regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
      	        		regexText : "不能包含特殊字符"
  		    	   		},{
  		    	   		name:'fparentid',
      	        		fieldLabel : '所属仓库',
      	        		valueField : 'fid', // 组件隐藏值
						xtype : 'cCombobox',
						displayField : 'fname',// 组件显示值
						allowBlank:false,
//						editable:false,
						MyConfig : {
							width : 800,// 下拉界面
							height : 200,// 下拉界面
							url : 'GetWarehouses.do', // 下拉数据来源
							fields : [{
										name : 'fid'
									}, {
										name : 'fnumber'
									}, {
										name : 'fname',
										myfilterfield : 'w.fname', // 查找字段，发送到服务端
										myfiltername : '仓库名称',// 在过滤下拉框中显示的名称
										myfilterable : true
										// 该字段是否查找字段
									},{
										name:'fdescription'
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
										'header' : '描述',
										'dataIndex' : 'fdescription',
										sortable : true
									}]
						}
  		    	   		},{
  		    	   		xtype:'numberfield',
	    	   			name:'finstoreprice',
	    	   			fieldLabel : '入库计件(元/m2)',
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
  	        		name:'faddress',
  	        		fieldLabel : '地址',
  	        		regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
  	        		regexText : "不能包含特殊字符",
  	        		anchor:'100%'
         },{
        	 xtype:'textfield',
       		name:'fremark',
       		fieldLabel : '描述',
       		regex : /^([\u4E00-\u9FA5]|\w|\-)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
       		regexText : "不能包含特殊字符",
       		anchor:'100%'
         }]
}]
	 }), this.callParent(arguments);
 	}
 				
 		
 	
});