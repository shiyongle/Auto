
Ext.define('DJ.order.Deliver.OrderappraiseEdit',{
	extend:'Ext.c.BaseEditUI',
	title:'订单评价编辑界面',
	id:'DJ.order.Deliver.OrderappraiseEdit',
	width:450,
	height:350,
	ctype:'Orderappraise',
	bodyPadding : '10 15',
	url:'saveOrUpdateOrderAppraise.do',
	infourl : 'getOrderAppraiseListByfid.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'getOrderAppraiseListByfid.do', // 查看状态数据源
	onload:function(){
		this.down('toolbar').setVisible(false);
	},
	items:[
			  {name:'fid',id:'fid',xtype:'textfield',hidden:true},
			  {name:'fcreatorid',xtype:'textfield',hidden:true},
			  {name:'fcreatetime',xtype:'textfield',hidden:true,value:'2014-05-27 00:00:00.000'},
			  {name:'fupdateuserid',xtype:'textfield',hidden:true},
			  {name:'fupdatetime',xtype:'textfield',hidden:true,value:'2014-05-27 00:00:00.000'},
			  {name:'fdeliverappraise',id:'DJ.order.Deliver.OrderappraiseEdit.fdeliverappraise',xtype:'textfield',hidden:true ,	allowBlank : false},
			  {name:'fqualityappraise',id:'DJ.order.Deliver.OrderappraiseEdit.fqualityappraise',xtype:'textfield',hidden:true, allowBlank : false  },
			  {name:'fserviceappraise',id:'DJ.order.Deliver.OrderappraiseEdit.fserviceappraise',xtype:'textfield',hidden:true,allowBlank : false },
			  {name:'fmultipleappraise',id:'DJ.order.Deliver.OrderappraiseEdit.fmultipleappraise',xtype:'textfield',hidden:true,allowBlank : false},
			  {name:'fcustomerid',id:'DJ.order.Deliver.OrderappraiseEdit.fcustomerid',xtype:'textfield',hidden:true},
			  {name:'fappraiseman',xtype:'textfield',hidden:true},
			  {name:'fappraisetime',xtype:'textfield',hidden:true,value:'2014-05-27 00:00:00.000'},
			  {name:'fordertype',xtype:'numberfield',hidden:true},
			  {name:'fordernumber',xtype:'textfield',hidden:true},
			  {name:'ftype',xtype:'numberfield',hidden:true},
			  {
				layout:{
					type:'table',
					columns:2
				},
				baseCls:'x-plain',
				defaults:{
					width:200,
					labelWidth:85,
					bodyStyle:'padding:20;'
				},
				items:[{
							id:'DJ.order.Deliver.OrderappraiseEdit.fsupplierId',
							valueField : 'fid', // 组件隐藏值
							name : "fsupplierId",
							xtype : 'cCombobox',
							displayField : 'fname',// 组件显示值
							fieldLabel : '制造商名称 ',
							readOnlyCls:'x-item-disabled',
							readOnly : true,
							//allowBlank : false,
							MyConfig : {
								width : 800,// 下拉界面
								height : 200,// 下拉界面
//								url : 'GetSupplierList.do', // 下拉数据来源
								fields : [{
											name : 'fid'
										}, {
											name : 'fname'
										}],
										columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
											'header' : 'fid',
											'dataIndex' : 'fid',
											hidden : true,
											hideable : false,
											sortable : true
										}, {
											'header' : '供应商名称',
											'dataIndex' : 'fname',
											sortable : true
										}]
							}
						},{
							id:'DJ.order.Deliver.OrderappraiseEdit.fdeliverorderid',
							labelStyle:'padding-left:20px;',
						    valueField : 'fid', // 组件隐藏值
							name : "fdeliverorderid",
							xtype : 'cCombobox',
							displayField : 'fnumber',// 组件显示值
							fieldLabel : '配送单号 ',
							readOnly : true,
							readOnlyCls:'x-item-disabled',
						//	allowBlank : false,
							MyConfig : {
								width : 800,// 下拉界面
								height : 200,// 下拉界面
//								url : 'GetSupplierList.do', // 下拉数据来源
								fields : [{
											name : 'fid'
										}, {
											name : 'fnumber'
										}],
										columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
											'header' : 'fid',
											'dataIndex' : 'fid',
											hidden : true,
											hideable : false,
											sortable : true
										}, {
											'header' : '配送单号',
											'dataIndex' : 'fnumber',
											sortable : true
										}]
							}
						}
	]
			
	},{
		 xtype: 'container',
//		xtype:'fieldset',
//		 maskOnDisable:false,
         width:400,
         height:100,
         labelSeparator : '',
//         disabledCls:"x-mask-msg,x-item-disabled",
         html : '<iframe name="DJ.order.Deliver.OrderappraiseEdit.appraiseiframe" src="'+IndexMessageRel.projectBasePath+'js/myComponent/orderpraise/SSOrderpraise.html" frameborder="0"  width="420" height="90"></iframe>'
 
	},{
		xtype:'displayfield',
		fieldLabel:'详细描述',
		labelSeparator : ''
	},{
		xtype:'textareafield',
		name:'fdescription',
		width:400,
		height:100
	}],
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'bottom',
		layout: {
		    pack: 'center'
		},
		items : [{
			text : '<b>提交</b>',
			handler:function(){
				var iserror=true;
				var dframe=frames["DJ.order.Deliver.OrderappraiseEdit.appraiseiframe"];
				var inputvalue=dframe.document.getElementsByTagName("input");
				var appriaseState=["交期满意度","质量满意度","服务满意度","综合满意度"];	
				Ext.Array.each(inputvalue,function(item,index,countriesItSelf)
				{
					if(inputvalue[index].value=="")
					{
					Ext.Msg.alert('提示',"请对"+appriaseState[index]+"进行评分!");
					iserror=false;
					return 	false;				
					}
				})
				if(iserror==true){
					var edit=Ext.getCmp('DJ.order.Deliver.OrderappraiseEdit');
					edit.Action_Submit(edit);
				}
			}
		},{
			text : '<b>取消</b>',
			handler:function(){
				Ext.getCmp('DJ.order.Deliver.OrderappraiseEdit').close();
				
			}
		}]
	}]
	,listeners : {
		'beforeshow':function(win)
		{
				Ext.getCmp("DJ.order.Deliver.OrderappraiseEdit.fsupplierId").setReadOnly(true);
				Ext.getCmp("DJ.order.Deliver.OrderappraiseEdit.fdeliverorderid").setReadOnly(true);
				var getButtons=win.dockedItems.items[0];
				
				if( win.editstate=='view')
 	 			{
 	 				getButtons.items.items[0].setVisible(false);
 	 			}else
 	 			{
 	 				getButtons.items.items[0].setVisible(true);
 	 			}
 	 			
		}
	}
})