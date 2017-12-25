Ext.define('DJ.System.Store.SubUserPermissionStore', {
			extend : 'Ext.data.TreeStore',
			proxy : {
				type : 'ajax',
				url : 'getSubUserPermissionList.do'
			},
			model : 'MenuModel',
			root : {
				fid : 'a51803d8-a65d-11e2-a596-60a44c5bbef3',
				url : '',
				expanded : true,
				text : '系统权限'
			},
			listeners : {
				'beforeload' : function(_store, record) {
					var new_params = {
						fid : record.node.data.fid,
						userid : Ext.vmiUser_Role_ID
					};
					Ext.apply(_store.proxy.extraParams, new_params);
				}
			}
});
Ext.define('DJ.System.Store.CurrentUserPermissionStore', {
	extend : 'Ext.data.TreeStore',
	proxy : {
		type : 'ajax',
		url : 'getCurrentUserPermissionList.do'
	},
	model : 'MenuModel',
	root : {
		fid : 'a51803d8-a65d-11e2-a596-60a44c5bbef3',
		url : '',
		expanded : true,
		text : '系统权限'
	},
	listeners : {
		'beforeload' : function(_store, record) {
			_store.proxy.extraParams.fid = record.node.data.fid;
		}
	}
});
Ext.define('DJ.System.Customer.SubUserPermissionEdit.Panel',{
	extend:'Ext.panel.Panel',
	id:'DJ.System.Customer.SubUserPermissionEdit.Panel',
	items:[{
		bodyPadding:5,
		anchor:'panel',
		height:30,
		defaultType:'textfield',
		items:[{
			fieldLabel:'名称',
			xtype:'displayfield',
			labelWidth:60,
			value:'',
			id:'DJ.System.Customer.SubUserPermissionEdit.UserName'
		},{
			fieldLabel:'用户ID',
			id:'DJ.System.Customer.SubUserPermissionEdit.UserId',
			hidden:true
		}, {
			id : 'DJ.System.Customer.SubUserPermissionEdit.PermissionID',
			fieldLabel : '权限ID',
			hidden : true
		}, {
			id : 'DJ.System.Customer.SubUserPermissionEdit.DelPermissionID',
			fieldLabel : '删除权限ID',
			hidden : true
		}]
	},{
		layout:'hbox',
		defaults:{
			height:1000
		},
		items:[{
			flex:1,
			xtype:'treepanel',
			useArrows : true,
			rootVisible : true,
			autoScroll : false,
			store : Ext.create('DJ.System.Store.CurrentUserPermissionStore'),
			listeners : {
				itemclick : function(node, record) {// 用了select这个事件不会触发。
					Ext.getCmp('DJ.System.Customer.SubUserPermissionEdit.PermissionID').setValue(record.data.fid);
				}
			}
		},{
			width:50,
			bodyStyle : "padding-top:150px;",
			items : [{
				xtype : "button",
				text : "分配",
				handler:addPermission,
				margin : '0 0 50 5'
			}, {
				xtype : "button",
				text : "收回",
				handler:receivePermission,
				margin : '0 0 0 5'
			}]
		},{
			flex:1,
			xtype:'treepanel',
			id:'DJ.System.Customer.SubUserPermissionEdit.RightTree',
			useArrows : true,
			rootVisible : true,
			autoScroll : false,
			store : Ext.create('DJ.System.Store.SubUserPermissionStore'),
			listeners : {
				itemclick : function(node, record) {// 用了select这个事件不会触发。
					var fid = record.data.fid;
					Ext.getCmp('DJ.System.Customer.SubUserPermissionEdit.DelPermissionID').setValue(record.data.fid);
				}
			}
		}]
	}]
});
//权限窗口
Ext.define('DJ.System.Customer.SubUserPermissionEdit',{
	extend:'Ext.window.Window',
	width:500,
	height:600,
	id:'DJ.System.Customer.SubUserPermissionEdit',
	layout:'fit',
	initComponent:function(){
		Ext.apply(this,{
			items:Ext.create('DJ.System.Customer.SubUserPermissionEdit.Panel')
		});
		this.callParent(arguments);
	},
	buttons:[{
		text:'取消',
		handler:function(){
			this.up('window').close();
		}
	}]
});
//收回权限
function receivePermission(btn) {
	var permissionID = Ext.getCmp("DJ.System.Customer.SubUserPermissionEdit.DelPermissionID").getValue();
	var userID = Ext.getCmp("DJ.System.Customer.SubUserPermissionEdit.UserId").getValue();
	var me=Ext.getCmp("DJ.System.Customer.SubUserPermissionEdit");
	var el = me.getEl();
	el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
				url : "delSubUserPermission.do",
				params : {
					permissionID : permissionID,
					userID : userID
				},
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						Ext.MessageBox.alert('错误', '收回成功!');
						Ext.getCmp("DJ.System.Customer.SubUserPermissionEdit.RightTree").store.load();
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
	});
}
//分配权限
function addPermission(btn) {
	var permissionID = Ext.getCmp("DJ.System.Customer.SubUserPermissionEdit.PermissionID").getValue();
	var userID = Ext.getCmp("DJ.System.Customer.SubUserPermissionEdit.UserId").getValue();
	var me=Ext.getCmp("DJ.System.Customer.SubUserPermissionEdit");
	var el = me.getEl();
	el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
				url : "saveSubUserPermission.do",
				params : {
					permissionID : permissionID,
					userID : userID
				},
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						Ext.MessageBox.alert('错误', '分配成功!');
						Ext.getCmp("DJ.System.Customer.SubUserPermissionEdit.RightTree").store.load();
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});

}