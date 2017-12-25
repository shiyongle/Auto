Ext.define('DJ.System.supplier.BoardRelateCustPdt',{
	extend: 'Ext.Window',
	id : 'DJ.System.supplier.BoardRelateCustPdt',
	title: '产品档案快捷新增',
	layout: 'fit',
	modal: true,
	closable : true,
	width : 675,
	height : 225,
	frame: true,
	bodyPadding: 10,
	resizable: true,
	listeners: {
//		show: function(){
//		},
//		close: function(){			
//		}
	},
	tbar: [{
		text: '保存',
		handler: function(){
			var win = this.up('window'),
			form = win.down('form');
			if(!form.isValid()){
				return;
			}
			form.setLoading(true);
			form.submit({
				url: 'saveBoardToCustpdt.do',
				   
				success: function(form,action){
				var obj = Ext.decode(action.response.responseText);
					if(obj.success){
						djsuccessmsg('保存成功！');
						win.close();
					}else{
						Ext.Msg.alert('提示',obj.msg);
						win.close();
					}
				},
			    failure: function(form, action) {
			    	var obj = Ext.decode(action.response.responseText);
			    	Ext.Msg.alert('提示',obj.msg);
			    	win.close();
			    }	
			});
		}
	},{
		text: '取消',
		handler: function(){
			this.up('window').close();
		}
	}],
	items: [{
				xtype: 'form',
				layout: 'hbox',
				baseCls: 'x-plain',
				fieldDefaults: {
				labelWidth: 65,
				width: 200
		},
		//defaultType: 'container',
		items: [{
						xtype: 'container',
						flex: 5,
						margin: '8 10 10 10',
						defaults: {
							labelWidth: 65,
							layout : 'vbox',
							margin : '12 0 0 0'
						},
						items : [{
										name : 'fcustomerid',
										id: 'DJ.System.supplier.BoardRelateCustPdt.fcustomerid',
										fieldLabel: '客户',
										width : 300,
										xtype : 'ncombobox',
										displayField : 'fname',
										valueField : 'fid',
										editable : true,
										queryMode : 'local',
										typeAhead : true,
										forceSelection : true,
										store : Ext.create('Ext.data.Store', {
											fields : ['fid', 'fname'],
											proxy : {							
												type : 'ajax',
												url : 'GetStanderCustomersOfSupplier.do',
												reader : {
													type : 'json',
													root : 'data'
												}
											},
											listeners : {
												load : function(me, records) {
													if (records && records.length && records.length > 0) {
														var com = Ext.getCmp('DJ.System.supplier.BoardRelateCustPdt.fcustomerid');
														com.setValue(records[0].get('fid'));
													}
												}
											},
											autoLoad : true
										})				
						},{
										xtype: 'textfield',
										name: 'fname',
										fieldLabel: '产品名称',
										width : 300,
										allowBlank: false,
										blankText: '请填写产品名称',
										msgTarget: 'side'
						},{
										xtype: 'numberfield',
										fieldLabel: '单价',
										name: 'fprice'	,
										decimalPrecision : 3,
										value : 0,
										minValue : 0
						},{
								        xtype: 'textfield',
								        hidden: true,
								        name: 'fboardid'
								        //submitValue : 'false'
						}]
						
		},{
					    xtype: 'textarea',
						fieldLabel: '备注',
						name: 'fdescription',
						labelAlign: 'top',
						height: '90%',
						labelWidth: 55,
						flex: 5
		}]
	}]
});