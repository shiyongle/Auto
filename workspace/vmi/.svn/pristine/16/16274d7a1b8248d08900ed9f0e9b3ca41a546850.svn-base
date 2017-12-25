Ext.define('DJ.order.Deliver.DeliverorderexceptionEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.order.Deliver.DeliverorderexceptionEdit",
	modal : true,
	ctype : "Deliverorderexception",
	onload : function() {
	},
	title : "异常跟踪",
	width : 400,// 230, //Window宽度
	height : 300,// 137, //Window高度
	resizable : false,
	url : 'saveOrUpdateDeliverorderexception.do',
	infourl : 'selectDeliverorderexceptionByID.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'selectDeliverorderexceptionByID.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true

	listeners : {
		afterrender : function(comThis, eOpts) {
			var com = Ext
					.getCmp("DJ.order.Deliver.DeliverorderexceptionEdit.fnumber");

			com.setReadOnly(true);
		}
	},

	initComponent : function() {
		Ext.apply(this, {
			items : [{
				layout : "column",
				baseCls : "x-plain",
				items : [{// title:"列1",
					baseCls : "x-plain",
					columnWidth : .9,
					bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',

					defaults : {
						labelWidth : 100,
						width : 300

					},

					items : [{
						name : 'fid',
						xtype : 'textfield',
						value : "",
						hidden : true
					}, {
						name : 'fdeliverorderId',
						xtype : 'textfield',

						hidden : true
					}, {
						id : "DJ.order.Deliver.DeliverorderexceptionEdit.fnumber",
						name : 'fnumber',
						fieldLabel : '配送单号',
						xtype : 'textfield'

					}, {
						name : 'fremark',
						xtype : 'textareafield',
						fieldLabel : '备注',
                        height : 180,// 137, //Window高度
						rows : 5

					}]
				}]
			}]
		}), this.callParent(arguments);
	},
	bodyStyle : "padding-top:15px;padding-left:30px"
});
