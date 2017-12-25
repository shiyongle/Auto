Ext.define('DJ.quickOrder.ProductAddWin', {
	extend : 'Ext.c.BaseEditUI',

	requires : ['Ext.button.Button'],

	 id : 'DJ.quickOrder.ProductAddWin',

	modal : true,

	url : 'SaveQuickCusproduct.do',
//	viewurl : "",
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

	editstate : 'add',

	stock : 0,

	onload : function() {

	},

	resizable : false,

//	Action_AfterSubmit : function(c0) {
//		
//		this.gridView.getStore().load();
//		
//	},
	
	// Action_BeforeSubmit : function() {
	// var store = this.down('grid').getStore();
	//
	// if (store.data.getCount() == 0) {
	//
	// throw '没有数据，无法保存！';
	//
	// }
	//
	// store.each(function(record) {
	// // 批量黏贴时，是没有id的
	// if (record.get('farrivetime') == '') {
	//
	// throw '配送时间不能为空！';
	//
	// } else if (record.get('famount') == '') {
	// throw '配送数量不能为空！';
	// } else if (record.get('faddressid') == '') {
	//
	// throw '地址不能为空！';
	//
	// }
	// });
	// },

	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [

			{
				xtype : 'container',
				flex : 1,

				height : 100,
				width : 200 / 9 * 16,

				layout : {
					type : 'vbox',
					align : 'stretch',
					padding : 10
				},
				items : [{

					name : 'fsupplierid',
					xtype : 'hiddenfield'

				}, {
					name : 'fname',
					xtype : 'textfield',
//					flex : 1,
					fieldLabel : '产品名称',
					allowBlank : false,
					allowOnlyWhitespace : false
				}, {
					name : 'fspec',
					xtype : 'textfield',
//					flex : 1,
					fieldLabel : '产品规格'
//					,
//					allowBlank : false,
//					allowOnlyWhitespace : false
				}]
			}

			]
		});

		me.callParent(arguments);
	}

});
