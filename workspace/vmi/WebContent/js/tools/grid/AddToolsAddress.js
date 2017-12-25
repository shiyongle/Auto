Ext.define('DJ.tools.grid.AddToolsAddress',{
	extend:'Ext.AbstractPlugin',
	alias:'plugin.addToolsAddress',
	fcustomerid:'',
	fcustomername:'',
	init:function(){
		var me = this;
		if(me.cmp.xtype!='cCombobox'){
			return;
		}
		me.cmp.on('expand',function(combo, eOpts ){
			var bbar = this.picker.down('toolbar[dock=bottom]');
			if(bbar.items.length==13){
				bbar.add(bbar.items.length-1,{
				xtype : 'button',
				text:'<font color=blue>+新增地址</font>',
				width : 100,
				handler : function() {
					var editui = Ext.getCmp("DJ.System.UserAddressEdit");
					if (editui == null) {
						editui = Ext.create('DJ.System.UserAddressEdit');
					}
					editui.seteditstate("add");
					Ext.getCmp("DJ.System.UserAddressEdit").down('cCombobox[name=fcustomerid]').setReadOnly(true);
				    editui.show();
					editui.getform().form.findField("fcustomerid").setmyvalue("\"fid\":\""
								+me.fcustomerid + "\",\"fname\":\""
								+me.fcustomername + "\"");
					editui.on('close',function(){
						if(me.cmp.up().xtype=='editor'){
							var record = me.cmp.up('grid').getSelectionModel().getSelection()[0];
							var p = me.cmp.up('grid').plugins[0];
							var indexs = 0;
							me.cmp.up('grid').columns.forEach(function(column,index){
								if(column.dataIndex==me.cmp.up().field.name){
									indexs = index;
								}
							})
//							p.startEdit(record,indexs)
							var task = new Ext.util.DelayedTask(function(){
							     me.cmp.expand();
								 me.cmp.picker.store.load();
							});
							if (p.startEdit(record, indexs)) {
								task.delay(500);
							};
						}else{
							me.cmp.expand();
							me.cmp.picker.store.load();
						}
					})
				}
			},{
				xtype : 'tbfill'
			})
		}
		})
	}
})