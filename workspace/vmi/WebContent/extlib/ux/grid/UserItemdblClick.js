Ext.define('Ext.ux.grid.UserItemdblClick', {
	extend : 'Ext.AbstractPlugin',
	alias : 'plugin.itemdblclick',
//	alternateClassname : 'Ext.view.itemdbclick',
	callButton : '',
	init : function() {
		this.cmp.on('itemdblclick', this.itemdblclick, this);
	},
	itemdblclick : function(m, td, cellIndex, record, tr, rowIndex, e, eOpts) {
		var me = this, button = me.callButton;
		if(Ext.isEmpty(button)){
			return;
		}
		me.cmp.up('window').down('button[text='+button+']').handler();
	}
})