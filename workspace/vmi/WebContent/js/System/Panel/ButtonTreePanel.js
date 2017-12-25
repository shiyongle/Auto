Ext.define('DJ.System.Panel.ButtonTreePanel', {
			extend : 'Ext.tree.Panel',
			initComponent : function() {
				Ext.apply(this, {
							useArrows : true,
							rootVisible : true,
							autoScroll : false,
							store : Ext.create('DJ.System.Store.ButtonStore')
						}), this.callParent(arguments);
			}
		});