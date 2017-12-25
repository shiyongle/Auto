Ext.define("DJ.order.Deliver.MystockListForm",
		{
	extend:'Ext.form.Panel',
//	xtype : 'form',
	id:'DJ.order.Deliver.MystockListForm',
	region : 'west',
	width : 320,
	defaultType : 'textfield',
	layout:'form',
	bodyPadding : '10 15',
	frame : true,
	defaults : {
		width : 280,
		labelWidth : 85,
		bodyStyle : 'padding:20;'
	},
//	layout:"form",
	items : [ {
		name : 'fid',
		hidden:true,
		id:'DJ.order.Deliver.MystockList.fid'
	},{
		name : 'fstate',
		hidden:true
	},{
		name : 'fcharactername',
		id:'DJ.order.Deliver.MystockList.fcharactername',
		hidden:true
	},{
		name : 'fcharacterid',
		id:'DJ.order.Deliver.MystockList.fcharacterid',
		hidden:true
	},{
		name : 'fsaleorderid',
		hidden:true
	},{
		name : 'fbalanceqty',
		hidden:true
	},{
		name : 'fordered',
		hidden:true
	},{
		name : 'fordernumber',
		hidden:true
	},{
		name : 'fordertime',
		hidden:true,
		value:'2014-01-01'
	},{
		name : 'fordermanid',
		hidden:true
	},{
		name : 'forderentryid',
		hidden:true
	},{
		name:'fisconsumed',
		value:0,
		hidden:true
	},{
		name : 'fcreatetime',
		hidden:true,
		id:'DJ.order.Deliver.MystockList.fcreatetime',
		value:'2014-01-01 00:00:00'
	},{
		name : 'fcreateid',
		id:'DJ.order.Deliver.MystockList.fcreateid',
		hidden:true
	},{
		name : 'fcustomerid',
		hidden:true
	}
	,{
		fieldLabel : '备货单号',
		name : 'fnumber',
		readOnlyCls:'x-item-disabled',
		readOnly:true,
		id:'DJ.order.Deliver.MystockList.fnumber',
		hidden:true
	},{
		fieldLabel : '采购订单单号',
		name : 'fpcmordernumber'
	}, {
		fieldLabel : '包装物名称',
		name : 'fcustproductid',
		allowBlank:false,
		blankText : '包装物名称不能为空',
		id:'DJ.order.Deliver.MystockList.fcustproductid',
		xtype:'cCombobox',
		displayField: 'fname',
	    valueField: 'fid',
	    listeners:{
	    	expand:function(){
	    		Ext.getCmp('DJ.order.Deliver.MystockList.GetProducts').down("toolbar").setVisible(false);
	    	},
	    	change : function(com, newValue, oldValue, eOpts) {
				
				Ext.tip.QuickTipManager.register({
					target : "DJ.order.Deliver.MystockList.fcustproductid",
					text : com.getRawValue()
					
				});
	    	},
	    	render: function() {
				
				Ext.tip.QuickTipManager.register({
					target : "DJ.order.Deliver.MystockList.fcustproductid",
					title : ''
					
					
				});
			},
			select:function(combo,record){
				var funit = record[0].get('forderunit');
				if(funit!=null&&funit!=''&&funit!='null'){
					Ext.getCmp('DJ.order.Deliver.MystockList.funit').setValue(funit);
					Ext.getCmp('DJ.order.Deliver.MystockList.funit').setReadOnly(true);
				}else{
					Ext.getCmp('DJ.order.Deliver.MystockList.funit').setValue('只(套)/片');
					Ext.getCmp('DJ.order.Deliver.MystockList.funit').setReadOnly(false);
				}
				Ext.getCmp('DJ.order.Deliver.MystockList.fcharactername').setValue(record[0].get('fcharactername'));
				Ext.getCmp('DJ.order.Deliver.MystockList.fcharacterid').setValue(record[0].get('fcharacterid'));
			}
	    },
		MyConfig : {
			width : 600,// 下拉界面
			height : 100,// 下拉界面
			id:'DJ.order.Deliver.MystockList.GetProducts',
			url : 'GetCustproductList.do', // 下拉数据来源
			ShowImg: true,
			fields : [{
				name : 'fid'
			}, {
				name : 'fname',
				myfilterfield : 't_bd_Custproduct.fname', // 查找字段，发送到服务端
				myfiltername : '名称', // 在过滤下拉框中显示的名称
				myfilterable : true
					// 该字段是否查找字段
					}, {
						name : 'fnumber',
						myfilterfield : 't_bd_Custproduct.fnumber', // 查找字段，发送到服务端
						myfiltername : '编码', // 在过滤下拉框中显示的名称
						myfilterable : true
					// 该字段是否查找字段
					}, {
						name : 'fspec'
					}, {
						name : 'forderunit'
					}, {
						name : 'fcustomerid'
					}, {
						name : 'fdescription'
					}, {
						name : 'fcreatorid'
					}, {
						name : 'fcreatetime'
					}, {
						name : 'flastupdateuserid'
					}, {
						name : 'flastupdatetime'
					},{
						name : 'fmaterial'
					},{
						name:'ftilemodel'
					},{
						name:'fcharactername'
					},{
						name:'fcharacterid'
					}

			],
			columns : [{
				'header' : 'fcharacterid',
				'dataIndex' : 'fcharacterid',
				hidden : true,
				hideable : false,
				autoHeight : true,
				autoWidth : true,
				sortable : true
			},{
				'header' : 'fid',
				'dataIndex' : 'fid',
				hidden : true,
				hideable : false,
				autoHeight : true,
				autoWidth : true,
				sortable : true
			}, {
				'header' : '包装物品名称',
				'dataIndex' : 'fname',
				sortable : true,
				flex:1,
				filter : {
					type : 'string'
				}
			}, {
				'header' : '包装物品编码',
				'dataIndex' : 'fnumber',
				sortable : true,
				flex:1,
				filter : {
					type : 'string'
				}
			}, {
				'header' : '规格',
				width : 70,
				'dataIndex' : 'fspec',
				flex:1,
				sortable : true
			}, {
				'header' : '单位',
				width : 70,
				flex:1,
				'dataIndex' : 'forderunit',
				sortable : true
			},{
				dataIndex:'fcharactername',
				text:'特性',
				flex:1
			}]
		}
	}, {
		xtype:'numberfield',
		fieldLabel : '计划数量',
		name : 'fplanamount',
		allowBlank:false,
		blankText : '计划数量不能为空',
		id:'DJ.order.Deliver.MystockList.fplanamount'
	},{
		fieldLabel : '制造商名称',
		id:'DJ.order.Deliver.MystockList.fsupplierid',
		name : 'fsupplierid',
		xtype:'combobox',
		displayField:'fname',
		valueField:'fid',
		editable:false,
		store:Ext.create('Ext.data.Store',{
			fields: ['fid', 'fname'],
			autoLoad:true,
			proxy:{
				type:'ajax',
				url: 'getSupplierListOfCustomer.do',
		         reader: {
		             type: 'json',
		             root: 'data'
		         }
			},
			listeners:{
				load:function(me,records){
					if(records.length==1){
						Ext.getCmp('DJ.order.Deliver.MystockList.fsupplierid').setValue(records[0].get('fid'));
					}
					// 2015-06-04 by lu  关联东经，默认东经
					else if(records.length>1){
						var newArry = Ext.Array.filter(records,function(item,index,records){ 
							  if(item.get('fname')=='东经')
								  return true;
						  });
						if(newArry.length==1){
							Ext.getCmp('DJ.order.Deliver.MystockList.fsupplierid').setValue(newArry[0].get('fid'));
						}
					}
				}
			}
	})
	    
	}, {
		fieldLabel : '单位',
		name : 'funit',
		id:'DJ.order.Deliver.MystockList.funit'
	},{
		xtype: 'datefield',
		fieldLabel : '要求完成时间',
		id:'DJ.order.Deliver.MystockList.ffinishtime',
		labelWidth : 84,
		format : 'Y-m-d',
		value:Ext.Date.add(new Date(new Date()),Ext.Date.DAY,7),
		name : 'ffinishtime',
		allowBlank : false,
		blankText : '要求完成时间不能为空',
		listeners:{
			render:function(){
//				var id;
//				var time3 = Ext.Date.add(new Date(new Date()),Ext.Date.DAY,3);
//				var time5 = Ext.Date.add(new Date(new Date()),Ext.Date.DAY,5);
//				var time7 = Ext.Date.add(new Date(new Date()),Ext.Date.DAY,7);
//				var ffinishtime = Ext.getCmp('DJ.order.Deliver.MystockList.ffinishtime').getValue();
//				if(ffinishtime==time3){
//					id = "DJ.order.Deliver.MystockList.button3";
//				}else if(ffinishtime==time5){
//					id = "DJ.order.Deliver.MystockList.button5";
//				}else if(ffinishtime==time7){
//					id = "DJ.order.Deliver.MystockList.button7";
//					
//				}
//				Ext.getCmp(id).toggle();
			}
		}
	}, {
		xtype:'buttongroup',
		layout: 'hbox',
		windth:'100%',
		id:'DJ.order.Deliver.MystockList.buttongroup',
		items:[{
			xtype : 'button',
			id:'DJ.order.Deliver.MystockList.button3',
			text : '3日内',
			toggleGroup : "ffinishtime",
//			width : '30%',
			margin : '5 10 5 10',
			handler:function(){
				Ext.getCmp('DJ.order.Deliver.MystockList.ffinishtime').setValue(Ext.Date.add(new Date(new Date()),Ext.Date.DAY,3))
			}
		}, {
			xtype : 'button',
			id:'DJ.order.Deliver.MystockList.button5',
			text : '5日内',
			toggleGroup : "ffinishtime",
//			width : '30%',
			margin : '5 10 5 10',
			handler:function(){
				Ext.getCmp('DJ.order.Deliver.MystockList.ffinishtime').setValue(Ext.Date.add(new Date(new Date()),Ext.Date.DAY,5))
			}
		}, {
			xtype : 'button',
			toggleGroup : "ffinishtime",
			pressed : true,
			id:'DJ.order.Deliver.MystockList.button7',
			text : '7日内',
//			width : '30%',
			margin : '5 10 5 10',
			handler:function(){
				Ext.getCmp('DJ.order.Deliver.MystockList.ffinishtime').setValue(Ext.Date.add(new Date(new Date()),Ext.Date.DAY,7))
			}
		}]
		
	},{
		xtype: 'datefield',
		fieldLabel : '预计消耗时间',
		id:'DJ.order.Deliver.MystockList.fconsumetime',
		labelWidth : 84,
		format : 'Y-m-d',
		value:Ext.Date.add(new Date(new Date()),Ext.Date.DAY,7),
		name : 'fconsumetime',
		allowBlank : false,
		blankText : '预计消耗时间不能为空'
	}, {
		xtype:'buttongroup',
		layout: 'hbox',
		windth:'100%',
		items:[{
			id:'DJ.order.Deliver.MystockList.week',
			xtype : 'button',
			toggleGroup : "fconsumetime",
			pressed : true,
			text : '一周内',
//			width : '30%',
			margin : '5 10 5 10',
			handler:function(){
				Ext.getCmp('DJ.order.Deliver.MystockList.fconsumetime').setValue(Ext.Date.add(new Date(new Date()),Ext.Date.DAY,7))
			}
		}, {
			xtype : 'button',
			text : '半月内',
			toggleGroup : "fconsumetime",
//			width : '30%',
			margin : '5 10 5 10',
			handler:function(){
				Ext.getCmp('DJ.order.Deliver.MystockList.fconsumetime').setValue(Ext.Date.add(new Date(new Date()),Ext.Date.DAY,15))
			}
		}, {
			xtype : 'button',
			text : '1月内',
			toggleGroup : "fconsumetime",
//			width : '30%',
			margin : '5 10 5 10',
			handler:function(){
				Ext.getCmp('DJ.order.Deliver.MystockList.fconsumetime').setValue(Ext.Date.add(new Date(new Date()),Ext.Date.MONTH,1))
			}
		} ]
		
	},{
		xtype:'panel',
		title:'备注',
		layout:'fit',
		height:90,
		items:[{
			name : 'fremark',
			id : 'DJ.order.Deliver.MystockList.fremark',
			maxLength : 800,
			xtype : 'textarea'
		}]
	},{
		fieldLabel : '昵称',
		xtype:'textfield',
		id:'nickName'
	},{
		fieldLabel : '输入信息',
		xtype:'textfield',
		id:"textMessage"
	},{
		xtype:'button',
		text:'发送信息',
		handler:function(){
		
		    function sendMyMessage() {
		        var nickName = Ext.getCmp('nickName');
		        var textMessage = Ext.getCmp('textMessage');
		        if (ws != null && textMessage.value != '') {
		            ws.send(nickName.value + '!@#$%' + textMessage.value);
		            textMessage.setValue('');
		        }
		    }
		    sendMyMessage();
		}
	},{
		xtype:'displayfield',
		id:'content'
	}]
})