//简单的与vmi后台整合的下拉列表
Ext.define('Ext.ux.form.SimpleLocalCombo',{
	extend: 'Ext.form.field.ComboBox',
	alias: 'widget.slcombo',
	queryMode: 'local',
	displayField: 'text',
    valueField: 'value',
    data: null,			//数组数据，必填项
	initComponent: function(){
		var me = this;
		me.store = Ext.create('Ext.data.Store', {
		    fields: ['text', 'value'],
		    data : me.data
		});
		me.callParent();
	}
})