Ext.define('DJ.supplier.LinkedCustomerEdit',{
	extend:'Ext.c.BaseEditUI',
	title:'客户管理编辑界面',
	id:'DJ.supplier.LinkedCustomerEdit',
	url : 'saveCustomerInfoBySupplier.do',
	ctype: 'Customer',
	infourl : 'getCustomerInfoOfSupplier.do',
	viewurl : 'getCustomerInfoOfSupplier.do',
	modal : true,
	resizable : false,
	width:800,
	bodyPadding : '10 15',
	items: [{
		baseCls : "x-plain",
		layout: 'column',
		items:[/*{
			xtype:'textfield',
			name: 'fischangedphone',//判断是否新建账号还是修改账号，用于编辑时
			value:0,
			hidden: true
		},*/{
			xtype:'textfield',
			name: 'fid',
			hidden: true
		},{
			xtype:'textfield',
			name: 'fisinvited',
			hidden: true
		},{
			xtype:'textfield',
			name: 'fcreatetime',
			hidden: true
		},{
			xtype:'textfield',
			name: 'fcreatorid',
			hidden: true
		},{
			xtype:'textfield',
			name: 'flastupdateuserid',
			hidden: true
		},{
			xtype:'textfield',
			name: 'flastupdatetime',
			hidden: true
		},{
			columnWidth: .33,
			baseCls : "x-plain",
			defaults: {
				margin: '10 5',
				labelWidth:65,
				width:240
			},
			items:[{
				xtype:'textfield',
				fieldLabel: '客户名称',
				name: 'fname',
				allowBlank: false
			},{
				xtype:'textfield',
				fieldLabel: '工商注册号',
				name: 'fbizregisterno'
//				allowBlank: false
			},{
				layout:'hbox',
				baseCls : "x-plain",
				items:[{
					labelWidth:65,
					xtype:'numberfield',
					fieldLabel: '包装总用量',
					name: 'fpacktotal'
				},{
					xtype:'label',
					text: '元 ',
					margin: '5 0 0 5'
				}]
				
			},{
				xtype:'textfield',
				fieldLabel: '行业',
				name: 'findustryid'
//				allowBlank: false
			}]
		},{
			columnWidth: .33,
			baseCls : "x-plain",
			defaults: {
				margin: '10 5',
				labelWidth:65,
				width:240
			},
			items:[{
				xtype:'textfield',
				fieldLabel: '联系人',
				name: 'flinkman'
			},{
				xtype:'textfield',
				fieldLabel: '税务登记号',
				name: 'ftxregisterno'
			},{
				layout:'hbox',
				baseCls : "x-plain",
				items:[{
					labelWidth:65,
					xtype:'numberfield',
					fieldLabel: '包装采购量',
					name: 'fpackbuyamount'
				},{
					xtype:'label',
					text: '元 ',
					margin: '5 0 0 5'
				}]
				
			
			}]
		},{
			columnWidth: .33,
			baseCls : "x-plain",
			defaults: {
				margin: '10 5',
				labelWidth:65,
				width:240
			},
			items:[{
				xtype:'textfield',
				fieldLabel: '下单手机',
				name: 'fphone',
				regex : /^(1[0-9][0-9]|15[0|3|6|7|8|9]|18[0|8|9])\d{8}$/,
				regexText : "你输入的不是手机号"
			},{
				xtype:'textfield',
				fieldLabel: '法人代表',
				name: 'fartificialperson'
			},{
				xtype:'textfield',
				fieldLabel: '联系电话',
				name: 'fartificialpersonphone'
			}]
		}]
		},{
			labelWidth:65,
			width:740,
			margin: '0 0 10 5',
			xtype:'textfield',
			fieldLabel: '地址',
			hidden:true,
			name: 'faddress'
		},{
			labelWidth:65,
			width:740,
			xtype:'textfield',
			fieldLabel: '备注',
			margin: '0 0 0 5',
			name: 'fdescription'
		}]
});