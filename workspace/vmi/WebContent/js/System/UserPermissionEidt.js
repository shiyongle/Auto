Ext.require(['Ext.ux.grid.UserItemdblClick','Ext.ux.grid.FieldQueryFilter']);
Ext.define('SysPermissionTreePanel', {
	extend : 'Ext.tree.Panel',
	plugins: [{
			ptype:'itemdblclick',
			callButton:'分配'
		}],
	initComponent : function() {
		Ext.apply(this, {
					useArrows : true,
					rootVisible : true,
					autoScroll : false,
					store : Ext.create('DJ.System.Store.SysPermissionStore'),
					listeners : {
						itemclick : function(node, record) {// 用了select这个事件不会触发。
							var fid = record.data.fid;
							Ext
									.getCmp("DJ.System.UserPermissionEidt.PermissionID")
									.setValue(fid);
						}
					}

				}), this.callParent(arguments);
	}
});
Ext.define('UserPermissionTreePanel', {
	extend : 'Ext.tree.Panel',
	id : 'UserPermissionTreePanel',
		plugins: [{
			ptype:'itemdblclick',
			callButton:'收回'
		}],
	initComponent : function() {
		Ext.apply(this, {
					useArrows : true,
					rootVisible : true,
					autoScroll : false,
					store : Ext.create('DJ.System.Store.UserPermissionStore'),
					listeners : {
						itemclick : function(node, record) {// 用了select这个事件不会触发node,
							var fid = record.data.fid;
							Ext
									.getCmp("DJ.System.UserPermissionEidt.DelPermissionID")
									.setValue(fid);
						}
					}
				}), this.callParent(arguments);
	}
});

Ext.define('UserPermissionPanels', {
	extend : 'Ext.panel.Panel',
	width : 490,
	flex : 1,
	initComponent : function() {
		Ext.apply(this, {
			items : [{
				xtype : 'panel',
				height : 35,
				bodyStyle : "padding-top:5px",
				split : false,
				items : [{
							id : 'DJ.System.UserPermissionEidt.UserName',
							xtype : 'textfield',
							fieldLabel : '用户名称',
							readOnly : true,
							width : 300

						}, {
							id : 'DJ.System.UserPermissionEidt.UserID',
							xtype : 'textfield',
							fieldLabel : '用户ID',
							readOnly : true,
							hidden : true,
							width : 300

						}, {
							id : 'DJ.System.UserPermissionEidt.PermissionID',
							xtype : 'textfield',
							fieldLabel : '权限ID',
							readOnly : true,
							hidden : true,
							width : 300

						}, {
							id : 'DJ.System.UserPermissionEidt.DelPermissionID',
							xtype : 'textfield',
							fieldLabel : '删除权限ID',
							readOnly : true,
							hidden : true,
							width : 300

						}]
			}, {

				xtype : 'panel',
				layout : 'column',
				items : [{
							columnWidth : .45,
							layout : 'fit',
							height : 483,
							items : [Ext.create('SysPermissionTreePanel')]
						}, {
							columnWidth : .1,
							bodyStyle : "padding-top:150px",
							items : [{
										xtype : "button",
										text : "分配",
										pressed : false,

										handler : SaveData
									}, {
										xtype : "button",
										text : "收回",
										margin : '50 0 0 0',
										handler : DelData
									}],
							height : 483

						}, {
							columnWidth : .45,
							height : 483,
							layout : 'fit',
							items : [Ext.create('UserPermissionTreePanel')]
						}]
			}]
		}), this.callParent(arguments);
	}
});
Ext.define('DJ.System.UserPermissionEidt', {
			extend : 'Ext.window.Window',
			modal:true,
			id : 'DJ.System.UserPermissionEidt',
			initComponent : function() {
				Ext.apply(this, {
							width : 500,
							height : 600,
							title : '用户权限分配界面',
							layout : 'vbox',
							items : [Ext.create('UserPermissionPanels')],
							buttons : [{
								xtype : "button",
								text : "取消",
								handler : function() {
									var windows = Ext
											.getCmp("DJ.System.UserPermissionEidt");
									if (windows != null) {
										windows.close();
									}
								}
							}],
							buttonAlign : "right" // center
						}), this.callParent(arguments);
			}

		});

function DelData(btn) {
	var PermissionID = Ext
			.getCmp("DJ.System.UserPermissionEidt.DelPermissionID").getValue();
	var UserID = Ext.getCmp("DJ.System.UserPermissionEidt.UserID").getValue();
	
		var me=Ext.getCmp("DJ.System.UserPermissionEidt");
var el = me.getEl();
el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
				url : "Button/Delpermission.do",
				params : {
					PermissionID : PermissionID,
					UserID : UserID
				},
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						Ext.MessageBox.alert('错误', '收回成功!');
						Ext.getCmp("UserPermissionTreePanel").store.load();
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
}
function SaveData(btn) {
	var PermissionID = Ext.getCmp("DJ.System.UserPermissionEidt.PermissionID")
			.getValue();
	var UserID = Ext.getCmp("DJ.System.UserPermissionEidt.UserID").getValue();
	var me=Ext.getCmp("DJ.System.UserPermissionEidt");
	var el = me.getEl();
	el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
				url : "Button/Savepermission.do",
				params : {
					PermissionID : PermissionID,
					UserID : UserID
				},
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						Ext.MessageBox.alert('错误', '分配成功!');
						Ext.getCmp("UserPermissionTreePanel").store.load();
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});

}