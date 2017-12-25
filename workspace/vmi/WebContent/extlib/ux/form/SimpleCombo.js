//简单的与vmi后台整合的下拉列表
Ext.define('Ext.ux.form.SimpleCombo',{
	extend: 'Ext.form.field.ComboBox',
	alias: 'widget.scombo',
	queryMode: 'local',
	displayField: 'text',
    valueField: 'value',
    data: null,			//数组数据，必填项
    filterfield: null,	//必填
    compareType: null,	//可选
    fieldType: null,	//可选
	initComponent: function(){
		var me = this;
		me.definedListeners = {
			select: function(c,records){
				var store = this.up('grid').getStore(),
					value = records[0].get('value');
				if(value){
					store.setDefaultfilter([{
						myfilterfield : me.filterfield,
						CompareType : me.compareType || 'like',
						type : me.fieldType || 'string',
						value : value
					}]);
					store.setDefaultmaskstring(" #0 ");
				}else{
					store.setDefaultfilter([]);
					store.setDefaultmaskstring('');
				}
				store.loadPage(1);
			}
		}
		Ext.apply(me.listeners,me.definedListeners);
		me.store = Ext.create('Ext.data.Store', {
		    fields: ['text', 'value'],
		    data : me.data
		});
		me.callParent();
	},
	listeners: {},
	doSelect: Ext.emptyFn
})