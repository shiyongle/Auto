Ext.define('DJ.order.Deliver.MystockOrderList',{
	id:'DJ.order.Deliver.MystockOrderList',
	extend:'Ext.c.GridPanel',
	title:'我的备货下单',
	closable:true,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	url:'getMyStockOrderList.do',
	Delurl : "",
	EditUI : "",
	
	
	exporturl:"exportMystockList.do",//导出为EXCEL方法
	onload:function(){
		Ext.getCmp('DJ.order.Deliver.MystockOrderList.addbutton').setVisible(false);
		Ext.getCmp('DJ.order.Deliver.MystockOrderList.editbutton').setVisible(false);
		Ext.getCmp('DJ.order.Deliver.MystockOrderList.viewbutton').setVisible(false);
		Ext.getCmp('DJ.order.Deliver.MystockOrderList.delbutton').setVisible(false);
//		Ext.getCmp('DJ.order.Deliver.MystockOrderList.refreshbutton').setVisible(false);
//		Ext.getCmp('DJ.order.Deliver.MystockOrderList.querybutton').setVisible(false);
//		Ext.getCmp('DJ.order.Deliver.MystockOrderList.exportbutton').setVisible(false);
//		Ext.getCmp('DJ.order.Deliver.MystockOrderList.exportbutton').setVisible(false);
	},
	custbar:[{
		text:'下单', 
		handler:function(){
			var record = Ext.getCmp('DJ.order.Deliver.MystockOrderList').getSelectionModel().getSelection();
			if(record.length<1){
				Ext.Msg.alert('提示','请选择一条记录!');
				return;
			}
			Ext.Ajax.request({
				url:'myStockOrder.do',
				params:{
					fcustomerid:record[0].get('fcustomerid')
				},
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						djsuccessmsg(obj.msg);
						Ext.getCmp("DJ.order.Deliver.MystockOrderList").store
								.load();

					} else {
						Ext.MessageBox.alert('错误', obj.msg);

					}
				}
			})
		}
	}],
	fields : [{name:'fid'},  {name:'cfname',myfilterfield : 'cu.fname',myfiltername : '客户名称',myfilterable : true},
      {name:'fordered',myfilterfield : 'm.fordered',myfiltername : '是否下单',myfilterable : true},{name:'fnumber',type:'string',myfilterfield : 'm.fnumber',myfiltername : '备货编码',myfilterable : true},{name:'fcustproductid'},{name:'fplanamount'},{name:'funit'},
      {name:'ffinishtime'},{name:'fconsumetime'},{name:'fisconsumed'},{name:'fdescription'},{name:'fcustproductname',myfilterfield : 'c.fname',myfiltername : '产品名称',myfilterable : true},
      {name:'fcreatetime'},{name:"fcreateid"},{name:'fcustomerid'},{name:'cfnumber'},{name:'fordernumber'},{name:'fbalanceqty'},'fremark','fspec'],
//      remoteFilter : true,
	columns : [ {
		dataIndex : 'fid',
		text:'fid',
		hidden:true,
		hideable:false
	},{
		dataIndex : 'fcreateid',
		text:'fcreateid',
		hidden:true,
		hideable:false
	},{
		dataIndex : 'fcustomerid',
		text:'fcreateid',
		hidden:true,
		hideable:false
	},{
//		xtype : 'gridcolumn',
		dataIndex : 'fnumber',
		text : '备货编号'
	},{
//		xtype : 'gridcolumn',
		dataIndex : 'cfname',
		text : '客户名称'
	},{
//		xtype : 'gridcolumn',
		dataIndex : 'fordernumber',
		text : '订单编号'
	}, {
		// xtype: 'numbercolumn',
		dataIndex : 'fcustproductname',
		text : '包装物品名称'
//		flex:1
	}, {
		// xtype: 'numbercolumn',
		dataIndex : 'fcustproductid',
		text : '包装物品名称',
		hidden:true,
		hideable:false
	}, {
		// xtype: 'numbercolumn',
		dataIndex : 'cfnumber',
		text : '包装物品编码'
	},{
		dataIndex : 'fspec',
		text : '规格'
	}, {
		// xtype: 'datecolumn',
		dataIndex : 'fplanamount',
		text : '计划数量',
		align:"center"
//		width:50
	}, {
		// xtype: 'booleancolumn',
		dataIndex : 'funit',
		text : '单位',
		align:"center"
//		width:50
	},{
		dataIndex : 'fcreatetime',
		text:'创建时间',
		hidden:true,
		hideable:false
	}, {
		dataIndex : 'ffinishtime',
		text : '要求完成时间'
	}, {
		dataIndex : 'fconsumetime',
		text : '预计消耗时间'
	}, {
		dataIndex : 'fisconsumed',
		text : '是否消耗完毕',
		align:"center",
//		width:50,
		renderer:function(value){
			if(value=='1'){
				return "是"
			}else{
				return "否"
			}
		}
	},{
		dataIndex : 'fremark',
		text : '备注'
	}, {
		dataIndex : 'fbalanceqty',
		text : '库存数量',
		align:"center"
	}
	]
});