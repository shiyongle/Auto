Ext.define('DJ.System.supplier.ProductAddWin', {
	extend : 'Ext.c.BaseEditUI',

	requires : ['Ext.button.Button'],

	// id : 'DJ.quickOrder.ProductAddWin',

	modal : true,

	url : 'SaveSupOfCusproduct.do',
	infourl : 'GetSupOfPdtInfo.do',
	viewurl : 'GetSupOfPdtInfo.do',
	// closeAction : 'hide',

	// height : 400,
	// width : 500,
	// title : '暂存订单',

	resizable : false,

	gridView : '',
	
	statics : {

	},

	layout : {
		type : 'vbox',
		align : 'stretch'
	},


	stock : 0,

	onload : function() {

	},

	resizable : false,

	Action_AfterSubmit : function(c0) {
		
		//this.gridView.getStore().loadPage(1);
		
	},
	
	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [

			{
				xtype : 'container',
				flex : 1,

				height : 180,
				width : 200 / 9 * 16,

				layout : {
					type : 'vbox',
					align : 'stretch',
					padding : 10
				},
				items : [{
					name : 'fid',
					xtype : 'hiddenfield'
				},{
					name : 'fsupplierid',
					xtype : 'hiddenfield'

				},{
					name : 'fcustomerid',
					xtype : 'hiddenfield'

				}, {
					name : 'fname',
					xtype : 'textfield',
					flex : 0.2,
					fieldLabel : '产品名称',
					allowBlank : false,
					allowOnlyWhitespace : false
				}, {
					name : 'fspec',
					xtype : 'textfield',
					flex : 0.2,
					fieldLabel : '产品规格'
				},{
					name : 'fdescription',
					xtype : 'textarea',
					flex : 0.6,
					fieldLabel : '备注'
				}]
			}

			]
		});

		me.callParent(arguments);
	}

});
