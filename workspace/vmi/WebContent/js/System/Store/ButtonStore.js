/**
 * 权限管理Store
 */

Ext.define('DJ.System.Store.ButtonStore', {
			extend : 'Ext.data.TreeStore',
			proxy : {
				type : 'ajax',
				url : 'Button/GetButtonList.do'
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
						fid : record.node.data.fid
					};
					Ext.apply(_store.proxy.extraParams, new_params);
				}
			}

		});