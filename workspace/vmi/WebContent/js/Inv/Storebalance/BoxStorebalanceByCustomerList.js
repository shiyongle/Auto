Ext.require("DJ.tools.common.MyCommonToolsZ");
Ext.define('DJ.Inv.Storebalance.BoxStorebalanceByCustomerList', {
	extend : 'Ext.c.GridPanel',
	title : "纸箱库存",
	alias : 'widget.boxstorebalancebycustomerlist',
	id : 'DJ.Inv.Storebalance.BoxStorebalanceByCustomerList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'queryBoxStoreBalances.do',
	Delurl : "",
	EditUI : "DJ.order.Deliver.DeliversAndStockCustEdit",
	mixins : ['DJ.tools.grid.MySimpleGridMixer',
			'DJ.tools.grid.mixer.MyGridSearchMixer'],
	exporturl : "",
	onload : function() {
		this._hideButtons(['addbutton','delbutton','viewbutton', 'querybutton', 'exportbutton']);
		var editbutton=this.down("button[text=修  改]").setText("下单");
		editbutton.iconCls="";
	},
	Action_EditButtonClick : function(me) {
		var record = me.getSelectionModel().getSelection();
		if (record.length == 0) {
			throw "请先选择您要操作的行!";
		};
		var editui = Ext.create(me.EditUI);
		editui.seteditstate("add");
		editui.setparent(me.id);
		editui.show();
		editui.down("textfield[name=fcustproductid]").setValue(record[0].get("fid"));
	},
	custbar :
	[Ext.create("DJ.myComponent.button.ExportExcelButton"),{
		xtype : 'mymixedsearchbox',
		condictionFields : ['c.fname'],
		useDefaultfilter : true
	}
	],
	fields : [{
		name:'fid'
	},{
		name : 'fproductid'
	}, {
		name : 'fname',
		myfilterfield : 'c.fname',
		myfiltername : '包装物名称',
		myfilterable : true
	}, {
		name : 'fbalanceqty', sortType:'asInt'
	}, {name:'fmakingqty', sortType:'asInt'
	},{name:'fusedqty', sortType:'asInt'
	}, {name:'amount', sortType:'asInt'}
	,{name:'cname'}
	],
	columns : {items:[Ext.create('DJ.Base.Grid.GridRowNum',{
		header:'序号'
	}),{
		'header' : 'fproductid',
		'dataIndex' : 'fproductid',
		hidden : true,
		hideable : false,
		sortable : true
	},{
		'header' : '客户名称',
		'dataIndex' : 'cname',
		width:180,
		hidden : true,
		sortable : true
	}, {
		'header' : '包装物名称',
		'dataIndex' : 'fname',
		width:300,
		sortable : true
		},{
				'header' : '需发货数量',
				dataIndex : 'fusedqty',
				sortable : true,
				xtype : 'numbercolumn',
				width:120,
				format : '0'
			},{
					'header' : '库存',
						hideable: false,
						draggable : false,
				columns: [{
				'header' : '成品数量',
				dataIndex : 'amount',
				sortable : true,
				xtype : 'numbercolumn',
				width:120,
				format : '0'
			},  {
				'header' : '在生产数量',
				dataIndex : 'fmakingqty',
				sortable : true,
				xtype : 'numbercolumn',
				width:120,
				format : '0'
			}]},{
				'header' : '库存数量',
				dataIndex : 'fbalanceqty',
				sortable : true,
				xtype : 'numbercolumn',
				width:120,
				format : '0'
			}]
			},
	selModel : Ext.create('Ext.selection.CheckboxModel')

});

