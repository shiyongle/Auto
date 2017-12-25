Ext.define('DJ.order.saleOrder.MyDeliveryEdit',{
	extend : 'Ext.c.BaseEditUI',
	title : "我的发货编辑界面",
	id : 'DJ.order.saleOrder.MyDeliveryEdit',
	closable : true,// 是否现实关闭按钮,默认为false
	width : 620,
	height : 400,
	viewurl:'GetMyDeliveryInfo.do',
	bodyPadding:'20 15',
	statics : {
		DELIVERS_STATE : ["要货创建", "已接收", "已下单", "已分配", "已入库","部分发货", "已发货","待发货"]
	},
	items:[{
		layout : "column",
		baseCls : "x-plain",
		items : [{// title:"列1",
			baseCls : "x-plain",
			columnWidth : .5,
			bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
			items : [{
				name : 'fordernumber',
				xtype : 'textfield',
				fieldLabel : '制造商订单号',
				width : 260,
				labelWidth : 80
			}, {
				name : 'fcustname',
				xtype : 'textfield',
				fieldLabel : '客户名称',
				width : 260,
				labelWidth : 80
			}, {
				name : 'productname',
				xtype : 'textfield',
				fieldLabel : '包装物名称',
				width : 260,
				labelWidth : 80
			}, {
				name : 'famount',
				xtype : 'textfield',
				fieldLabel : '配送数量',
				width : 260,
				labelWidth : 80
			},{
				name : 'farrivetime',
				xtype : 'textfield',
				fieldLabel : '配送时间',
				width : 260,
				labelWidth : 80
			}, {
				name : 'fstate',
				xtype : 'combobox',
				fieldLabel : '配送状态',
				width : 260,
				labelWidth : 80,
				displayField: 'name',
			    valueField: 'value',
				store:Ext.create('Ext.data.Store', {
				    fields: ['name', 'value'],
				    data : [
				        {"name":"已发货", "value":"6"},
				        {"name":"部分发货", "value":"5"},
				        {"name":"待发货", "value":"4"},
				        {"name":"待发货", "value":"3"},
				        {"name":"待发货", "value":"2"},
				        {"name":"待发货", "value":"1"},
				        {"name":"待发货", "value":"0"}
				        //...
				    ]
				})
			}, {
				name : 'flinkman',
				xtype : 'textfield',
				fieldLabel : '联系人',
				width : 260,
				labelWidth : 80
			}, {
				name : 'cfspec',
				xtype : 'textfield',
				fieldLabel : '规格',
				width : 260,
				labelWidth : 80
			}]
		}, {	// title:"列2",
			baseCls : "x-plain",
			columnWidth : .5,
			bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
			items : [{
				name : 'fnumber',
				xtype : 'textfield',
				fieldLabel : '配送单号',
				width : 260,
				labelWidth : 80
			}, {
				name : 'ftype',
				xtype : 'combobox',
				fieldLabel : '订单类型',
				width : 260,
				labelWidth : 80,
				displayField: 'name',
			    valueField: 'value',
				store:Ext.create('Ext.data.Store', {
				    fields: ['name', 'value'],
				    data : [
				        {"name":"客户订单", "value":"0"},
				        {"name":"平台订单", "value":"1"},
				        {"name":"平台订单", "value":"2"}
				    ]
				})
			}, {
				name:'pfnumber',
				xtype : 'textfield',
				fieldLabel : '包装物编号',
				width : 260,
				labelWidth : 80
			}, {
				name:'fassembleQty',
				xtype : 'textfield',
				fieldLabel : '实际配送数量',
				width : 260,
				labelWidth : 80
			},{
				name : 'fouttime',
				xtype : 'textfield',
				fieldLabel : '实际配送时间',
				width : 260,
				labelWidth : 80
			}, {
				name : 'fdelivertype',
				xtype : 'combobox',
				fieldLabel : '发货类型',
				width : 260,
				labelWidth : 80,
				displayField: 'name',
			    valueField: 'value',
				store:Ext.create('Ext.data.Store', {
				    fields: ['name', 'value'],
				    data : [
				        {"name":"", "value":"0"},
				        {"name":"自运发货", "value":"1"},
				        {"name":"协同发货", "value":"2"}
				    ]
				})
			}, {
				name : 'flinkphone',
				xtype : 'textfield',
				fieldLabel : '联系电话',
				width : 260,
				labelWidth : 80
			}]
		},{
			baseCls : "x-plain",
			columnWidth : 600,
			bodyStyle : 'padding-top:0px;padding-left:5px;padding-right:5px',
			items:[{
				name : 'faddress',
				xtype : 'textfield',
				fieldLabel : '配送地址',
				width : 580,
				labelWidth : 80
			},{
				name : 'fdescription',
				xtype : 'textfield',
				fieldLabel : '备注',
				width : 580,
				labelWidth : 80
			}]
		}]
	}]
})
