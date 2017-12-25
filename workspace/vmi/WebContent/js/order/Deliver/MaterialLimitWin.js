Ext.define('DJ.order.Deliver.MaterialLimitWin',{
	extend: 'Ext.Window',
	title: '下料规格编辑界面',
	layout: 'fit',
	modal: true,
	frame: true,
	bodyPadding: 10,
	resizable: false,
	listeners: {
		show: function(){
			var extrernes = this.parentWin.extrernes;
			if(extrernes){
				this.down('numberfield[name=fmaxlength]').setValue(extrernes.fmaxlength);
				this.down('numberfield[name=fmaxwidth]').setValue(extrernes.fmaxwidth);
				this.down('numberfield[name=fminlength]').setValue(extrernes.fminlength);
				this.down('numberfield[name=fminwidth]').setValue(extrernes.fminwidth);
			}
			this.down('numberfield[name=fmaxlength]').focus(false,300);
		},
		close: function(){
			this.parentWin.isOpen = false;
		}
	},
	tbar: [{
		text: '保存',
		handler: function(){
			var win = this.up('window'),
				form = win.down('form');	//产品档案打开的有客户id
			if(!form.isValid()){
				return;
			}
			form.setLoading(true);
			form.submit({
				url: 'saveMaterialLimit.do',
				success: function(form,action){
					var obj = Ext.decode(action.response.responseText),
						extrernes = win.parentWin.extrernes = {};
					extrernes.fmaxlength = win.down('numberfield[name=fmaxlength]').getValue();
					extrernes.fmaxwidth = win.down('numberfield[name=fmaxwidth]').getValue();
					extrernes.fminlength = win.down('numberfield[name=fminlength]').getValue();
					extrernes.fminwidth = win.down('numberfield[name=fminwidth]').getValue();
					if(obj.success){
						djsuccessmsg('保存成功！');
						win.close();
					}else{
						Ext.Msg.alert('提示',obj.msg);
					}
				}
			});
		}
	},{
		text: '取消',
		handler: function(){
			this.up('window').close();
		}
	},'->','请您先设置下料规格的最值,再下单'],
	items: [{
		xtype: 'form',
		layout: 'hbox',
		baseCls: 'x-plain',
		fieldDefaults: {
			labelWidth: 65,
			width: 200
		},
		defaultType: 'container',
		items: [{
			flex: 1,
			items: [{
				xtype: 'numberfield',
				fieldLabel: '最大长度',
				name: 'fmaxlength',
				allowBlank: false
			},{
				xtype: 'numberfield',
				fieldLabel: '最大宽度',
				name: 'fmaxwidth',
				allowBlank: false
			}]
		},{
			flex: 1,
			items: [{
				xtype: 'numberfield',
				fieldLabel: '最小长度',
				name: 'fminlength',
				value: 0,
				allowBlank: false,
				minValue: 0,
				minValueText: '值不能小于0',
				validator: function(val){
					var maxLength = this.up('window').down('numberfield[name=fmaxlength]').getValue();
					if(val>maxLength){
						return '最小长度不能大于最大长度！';
					}
					return true;
				}
			},{
				xtype: 'numberfield',
				fieldLabel: '最小宽度',
				name: 'fminwidth',
				value: 0,
				allowBlank: false,
				minValue: 0,
				minValueText: '值不能小于0',
				listeners: {
					specialkey: function(field,e){
						if(e.getKey() == Ext.EventObject.ENTER){
							this.up('window').down('button[text=保存]').handler();
						}
					}
				},
				validator: function(val){
					var maxWidth = this.up('window').down('numberfield[name=fmaxwidth]').getValue();
					if(val>maxWidth){
						return '最小宽度不能大于最大宽度！';
					}
					return true;
				}
			}]
		}]
	}]
});