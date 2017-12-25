Ext.define('DJ.System.supplier.AddressManageEdit',{
	id:'DJ.System.supplier.AddressManageEdit',
	title:'地址管理编辑',
	extend : 'Ext.c.BaseEditUI',
	width : 340,// 230, //Window宽度
	height : 250,// 137, //Window高度
	resizable : false,
	bodyPadding:'15',
	ctype:'Address',
	url : 'SaveAddressMange.do',
	infourl : 'getAddressInfo.do', 
	viewurl : 'getAddressInfo.do', 
	onload : function() {
	},
	listeners:{
		show:function(me){
			var fcustomerid = Ext.isEmpty(me.fcustomerid)?Ext.getCmp(me.parent).down('combobox[name=fcustomerid]').getValue():me.fcustomerid;
			me.down('textfield[name=fcustomerid]').setValue(fcustomerid);
		}
	},
	items : [{
		name:'fcustomerid',
		xtype:'textfield',
		hidden:true,
		fieldLabel:'客户'
	},{
		name:'fid',
		xtype:'textfield',
		hidden:true,
		fieldLabel:'id'
	},{
		name:'flinkman',
		labelWidth:55,
		width:290,
		xtype:'textfield',
		fieldLabel:'联系人',
		allowBlank: false
	},{
		name:'fphone',
		labelWidth:55,
		width:290,
		xtype:'textfield',
		fieldLabel:'联系电话',
		allowBlank: false
//		,
//		regex : /^(1[0-9][0-9]|15[0|3|6|7|8|9]|18[0|8|9])\d{8}$/,
//		regexText : "你输入的不是手机号"
	},{
		name:'fdetailaddress',
		labelWidth:55,
		width:290,
		height:80,
		xtype:'textareafield',
		fieldLabel:'详细地址',
		allowBlank: false
	}]
})