Ext.define('DJ.System.supplier.SupOfBoardOrderEdit',{
	extend : 'Ext.Window',
	id:'DJ.System.supplier.SupOfBoardOrderEdit',
	title : "采购纸板",
	width : 450,
	closable : true,
	modal : true,
	resizable:false,
	bodyPadding: 10,
	layout: 'fit',
	bbar: ['->',{
		text: '确定',
		padding: '5 10',
		handler: function(){
			var win = this.up('window');
			win.down('form').submit({
				success: function(form,action){
					var obj = action.result;
					if(obj.success){
						djsuccessmsg('采购订单成功！');
						win.close();
					}else{
						Ext.Msg.alert('提示',obj.msg);
					}
				}
			});
		}
	},{
		text: '关闭',
		padding: '5 10',
		handler: function(){
			this.up('window').close();
		}
	},'->'],
	items: [{
		xtype: 'form',
		baseCls : 'x-plain',
		url:'saveBoardOrderFromProduct.do',
		defaults: {
			labelWidth: 55
		},
		items: [{
			xtype: 'fieldcontainer',
			fieldLabel: '数量',
			layout: 'hbox',
			items: [{
				xtype: 'numberfield',
				name: 'famount',
				allowBlank: false,
				blankText: '请填写数量！',
				msgTarget: 'side',
				emptyText: '只',
				width: 80,
				listeners: {
					blur: function(){
						this.nextSibling('[itemId=piece]').setValue(this.getValue() * this.up('window').seriesValue);
					}
				}
			},{
				xtype: 'component',
				width: 30
			},{
				xtype: 'textfield',
				editable: false,
				itemId: 'piece',
				emptyText: '片',
				readOnly: true,
				width: 80
			}]
		},{
			xtype: 'combobox',
			fieldLabel: '地址',
			name: 'faddressid',
			allowBlank: false,
			blankText: '请填写地址！',
			editable: false,
			msgTarget: 'side',
			width: 400,
			style: 'margin-top:20px;',
			displayField: 'fdetailaddress',
			valueField: 'fid',
			store:Ext.create('Ext.data.Store', {
			    fields: ['fdetailaddress', 'fid'],
			    proxy : {
					type : 'ajax',
					url : 'getUserToCustAddress.do',
					reader : {
						type : 'json',
						root : 'data'
					}
				}
			})
		},{
			xtype: 'textfield',
			hidden: true,
			name: 'fproductid'
		}]
	}]

});