Ext.define('DJ.order.saleOrder.BatchRealAmountEdit',{
	extend : 'Ext.c.GridPanel',
//	title : "实配数量",
	id : 'DJ.order.saleOrder.BatchRealAmountEdit',
	url:'',
	autoScroll:true,
	closable : false,
	selModel : {selType:'checkboxmodel'},
	onload:function(){
		Ext.each(this.query('toolbar'),function(bar){
			bar.hide();
		});
	},
	plugins: [
	          Ext.create('Ext.grid.plugin.CellEditing', {
	              clicksToEdit: 1
	          })
	 ],
	fields:[{
		name : 'fid'
	}, {
		name : 'fordernumber'
	}, {
		name : 'fnumber'
	}, {
		name : 'productname'
	}, {
		name : 'faddress'
	}, {
		name : 'fcreatorid'
	}, {
		name : 'fupdateuserid'
	}, {
		name : 'fcustomerid'
	}, {
		name : 'fcusproductid'
	}, {
		name : 'fcustname'
	}, {
		name : 'cutpdtname'
	},{
		name : 'ftype'
	}, {
		name : 'flinkman'
	}, {
		name : 'flinkphone'
	}, {
		name : 'famount'
	}, {
		name : 'fdescription'
	}, {
		name : 'farrivetime'
	}, {
		name : 'fcreator'
	}, {
		name : 'flastupdater'
	}, {
		name : 'fcreatetime'
	}, {
		name : 'fupdatetime'
	}, {
		name : 'fordered'
	}, {
		name : 'fordermanid'
	}, {
		name : 'fordertime'
	}, {
		name : 'forderman'
	}, {
		name : 'fsaleorderid'
	}, {
		name : 'forderentryid'
	}, {
		name : 'fimportEAS'
	}, {
		name : 'faudittime'
	}, {
		name : 'fauditor'
	},  {
		name : 'faudited'
	}, {
		name : 'fouted'
	}, {
		name : 'fmatched'
	}, {
		name : 'fassembleQty'
	}, {
		name : 'foutQty'
	},'fpcmordernumber','cfspec','pfnumber','fstate','fouttime','fdelivertype','fboxtype','fmaterial','fbalanceqty','sname'],
	columns:[  
	{
		'header' : '包装物名称',
		width : 170,
		'dataIndex' : 'productname',
		sortable : true,
		flex:1
	}, {
		'header' : '纸箱规格',
		'dataIndex' : 'cfspec',
		sortable : true,
		flex:1
	}, {
		'header' : '下料规格',
		'dataIndex' : 'fmaterial',
		sortable : true,
		flex:1
	}, {
		'header' : '实际配送数量',
		'dataIndex' : 'famount',
		sortable : true,
		flex:1,
		editor:{
			xtype:'numberfield'
		}
	}
	]
})