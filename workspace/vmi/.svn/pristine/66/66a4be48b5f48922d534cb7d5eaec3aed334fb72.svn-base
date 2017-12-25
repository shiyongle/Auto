/**
 * 用户角色未分配权限Store
 */

Ext.define('DJ.System.Store.RolePermissionStore', {
			extend : 'Ext.data.TreeStore',
			proxy : {
				type : 'ajax',
				url : 'Button/GetUserPermissionList.do'
			},
			reader : {
				type : 'json'
			},
			writer : {
				type : "json"
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
						userid : Ext.getCmp("DJ.System.RoleList")
								.getSelectionModel().getSelection()[0].get("fid")
					};
					Ext.apply(_store.proxy.extraParams, new_params);
				}
			}

		});