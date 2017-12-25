var store = Ext.create('Ext.data.TreeStore', {
			model : 'MenuModel',
			proxy : {
				type : 'ajax',
				url : 'MainMenuTree.do'
			},
			reader : {
				type : 'json'
			},
			writer : {
				type : "json"
			},

			root : {
				fid : 'a51803d8-a65d-11e2-a596-60a44c5bbef3',
				url : '',
				expanded : true,
				text : 'VMI系统菜单',
				fpath : 'VMI系统菜单'
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
var MainMenuTreePanel = Ext.create('Ext.tree.Panel', {
	useArrows : true,
	rootVisible : true,
	store : store,
	listeners : {
		itemclick : function(view, record, item, index, e) {// 用了select这个事件不会触发。
			var el = view.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
						url : "GetMainMenu.do",
						params : {
							fid : record.data.fid
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								Ext
										.getCmp("DJ.System.MainMenuList.MainMenuFid")
										.setValue(obj.data.fid);
								Ext
										.getCmp("DJ.System.MainMenuList.MainMenuName")
										.setValue(obj.data.fname);
								Ext
										.getCmp("DJ.System.MainMenuList.MainMenuIsLeaf")
										.setValue(obj.data.fisleaf);
								Ext
										.getCmp("DJ.System.MainMenuList.MainMenuUrl")
										.setValue(obj.data.furl);
								Ext
										.getCmp("DJ.System.MainMenuList.MainMenuLevel")
										.setValue(obj.data.flevel);
								Ext
										.getCmp("DJ.System.MainMenuList.MainMenuParent")
										.setValue(obj.data.fparentid);
								Ext
										.getCmp("DJ.System.MainMenuList.MainMenuOrder")
										.setValue(obj.data.forder);
								Ext.getCmp("DJ.System.MainMenuList.FPath")
										.setValue(obj.data.fpath);
										Ext.getCmp("DJ.System.MainMenuList.FiconCls")
										.setValue(obj.data.iconCls);
							}
							el.unmask();

						}
					});

		}
	}

});

var tbar = Ext.create("Ext.Toolbar", {
			height : 40,
			items : [{
						height : 40,
						text : '新  增',
						handler : onAddButtonClick
					}, {
						id : 'DJ.System.MainMenuList.SaveButton',
						text : '保    存',
						height : 40,
						handler : onSaveButtonClick
					}, {
						text : '删    除',
						height : 40,
						handler : onDelButtonClick
					}, {
						text : '刷    新',
						height : 40,
						handler : onRefreshButtonClick
					}]
		});
function onRefreshButtonClick(btn) {
	MainMenuTreePanel.store.load();
}
function onAddButtonClick(btn) {
	var parentid = Ext.getCmp("DJ.System.MainMenuList.MainMenuParent")
			.getValue();
	Level = Ext.getCmp("DJ.System.MainMenuList.MainMenuLevel").getValue();
	var fid = Ext.getCmp("DJ.System.MainMenuList.MainMenuFid").getValue();
	if (Ext.util.Format.trim(parentid) == "") {
		Ext.MessageBox.alert('提示', '未选中菜单节点');
		return;
	}
	if (Ext.util.Format.trim(fid) == "") {
		Ext.MessageBox.alert('提示', '请先保存后在新增');
		return;
	}
	Ext.getCmp("DJ.System.MainMenuList.MainMenuFid").setValue(null);
	Ext.getCmp("DJ.System.MainMenuList.MainMenuName").setValue(null);
	Ext.getCmp("DJ.System.MainMenuList.MainMenuIsLeaf").setValue(true);
	Ext.getCmp("DJ.System.MainMenuList.MainMenuUrl").setValue(null);
	Ext.getCmp("DJ.System.MainMenuList.MainMenuLevel").setValue(Level + 1);
	Ext.getCmp("DJ.System.MainMenuList.MainMenuParent").setValue(fid);
	Ext.getCmp("DJ.System.MainMenuList.MainMenuOrder").setValue(0);
	Ext.getCmp("DJ.System.MainMenuList.FiconCls").setValue("");

}
function onSaveButtonClick(btn) {
	if (!Ext.getCmp("DJ.System.MainMenuList.MainMenuName").isValid()
			|| !Ext.getCmp("DJ.System.MainMenuList.MainMenuUrl").isValid()
			|| !Ext.getCmp("DJ.System.MainMenuList.MainMenuOrder").isValid()) {
		Ext.MessageBox.alert('提示', '输入项格式不正确，请修改后再提交！');
		return;
	}
	var me=Ext.getCmp("DJ.System.MainMenuList");
	var el = me.getEl();
			el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
				url : "SaveMainMenu.do",
				params : {
					fid : Ext.getCmp("DJ.System.MainMenuList.MainMenuFid")
							.getValue(),
					fname : Ext.getCmp("DJ.System.MainMenuList.MainMenuName")
							.getValue(),
					fisleaf : Ext
							.getCmp("DJ.System.MainMenuList.MainMenuIsLeaf")
							.getValue(),
					furl : Ext.getCmp("DJ.System.MainMenuList.MainMenuUrl")
							.getValue(),
					flevel : Ext.getCmp("DJ.System.MainMenuList.MainMenuLevel")
							.getValue(),
					fparentid : Ext
							.getCmp("DJ.System.MainMenuList.MainMenuParent")
							.getValue(),
					forder : Ext.getCmp("DJ.System.MainMenuList.MainMenuOrder")
							.getValue(),
					fpath : Ext.getCmp("DJ.System.MainMenuList.FPath")
							.getValue(),
					ficonCls: Ext.getCmp("DJ.System.MainMenuList.FiconCls")
							.getValue()
					
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						Ext.MessageBox.alert('成功', obj.msg);
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
					MainMenuTreePanel.store.load();
					Ext.getCmp("DJ.System.MainMenuList.MainMenuFid")
							.setValue(null);
					Ext.getCmp("DJ.System.MainMenuList.MainMenuName")
							.setValue(null);
					Ext.getCmp("DJ.System.MainMenuList.MainMenuIsLeaf")
							.setValue(null);
					Ext.getCmp("DJ.System.MainMenuList.MainMenuUrl")
							.setValue(null);
					Ext.getCmp("DJ.System.MainMenuList.MainMenuLevel")
							.setValue(0);
					Ext.getCmp("DJ.System.MainMenuList.MainMenuParent")
							.setValue(null);
					Ext.getCmp("DJ.System.MainMenuList.MainMenuOrder")
							.setValue(0);
					Ext.getCmp("DJ.System.MainMenuList.FPath").setValue(null);
					Ext.getCmp("DJ.System.MainMenuList.FiconCls").setValue("");
					
				}
			});
}
function onDelButtonClick(btn) {
	var fid = Ext.getCmp("DJ.System.MainMenuList.MainMenuFid").getValue();
	if (Ext.util.Format.trim(fid) == "") {
		Ext.MessageBox.alert('提示', '未选中菜单节点');
		return;
	}
	if (Ext.util.Format.trim(fid) == "a51803d8-a65d-11e2-a596-60a44c5bbef3") {
		Ext.MessageBox.alert('提示', '根节点不能删除');
		return;
	}
	var me=Ext.getCmp("DJ.System.MainMenuList");
var el = me.getEl();
el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
				url : "DelMainMenu.do",
				params : {
					fid : fid
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						Ext.MessageBox.alert('成功', obj.msg);
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
					MainMenuTreePanel.store.load();
					Ext.getCmp("DJ.System.MainMenuList.MainMenuFid")
							.setValue(null);
					Ext.getCmp("DJ.System.MainMenuList.MainMenuName")
							.setValue(null);
					Ext.getCmp("DJ.System.MainMenuList.MainMenuIsLeaf")
							.setValue(null);
					Ext.getCmp("DJ.System.MainMenuList.MainMenuUrl")
							.setValue(null);
					Ext.getCmp("DJ.System.MainMenuList.MainMenuLevel")
							.setValue(0);
					Ext.getCmp("DJ.System.MainMenuList.MainMenuParent")
							.setValue(null);
					Ext.getCmp("DJ.System.MainMenuList.MainMenuOrder")
							.setValue(0);
					Ext.getCmp("DJ.System.MainMenuList.FPath").setValue(null);
					Ext.getCmp("DJ.System.MainMenuList.FiconCls").setValue("");
				}
			});
}
Ext.define('DJ.System.MainMenuListPanel', {
	extend : 'Ext.panel.Panel',
	layout : 'border',
	items : [{
				region : 'north',
				xtype : 'panel',
				height : 35,
				split : false,
				tbar : tbar,
				html : 'header',
				margins : '0 0 0 0',
				layout : 'fit'
			}, {
				region : 'west',
				xtype : 'panel',
				margins : '0 0 0 0',
				width : 500,
				items : [MainMenuTreePanel],
				collapsible : false,
				layout : 'fit'
			}, {
				region : 'center',
				bodyStyle : "padding-top:5px", // padding-left:120px;
				xtype : 'panel',
				margins : '0 0 0 0',
				items : [{
							id : 'DJ.System.MainMenuList.MainMenuFid',
							xtype : 'textfield',
							fieldLabel : 'FID',
							width : 500,
							hidden : true
						}, {
							id : 'DJ.System.MainMenuList.MainMenuName',
							xtype : 'textfield',
							fieldLabel : '菜单名称',
							regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
							regexText : "不能包含特殊字符",
							width : 500
						}, {
							xtype : 'fieldcontainer',
							fieldLabel : '是否子节点',
							defaultType : 'checkboxfield',
							items : [{
										boxLabel : '',
										name : 'fisleaf',
										readOnly : true,
										inputValue : '1',
										id : 'DJ.System.MainMenuList.MainMenuIsLeaf'
									}]
						}, {
							id : 'DJ.System.MainMenuList.MainMenuUrl',
							xtype : 'textfield',
							fieldLabel : '调用页面',
							width : 500
						}, {
							id : 'DJ.System.MainMenuList.MainMenuLevel',
							xtype : 'numberfield',
							fieldLabel : '层级',
							readOnly : true,
							width : 500
						}, {
							id : 'DJ.System.MainMenuList.MainMenuParent',
							xtype : 'textfield',
							fieldLabel : '父节点ID',
							width : 500,
							hidden : true,
							readOnly : true
						}, {
							id : 'DJ.System.MainMenuList.MainMenuOrder',
							xtype : 'numberfield',
							fieldLabel : '顺序',
							width : 500
						}, {
							id : 'DJ.System.MainMenuList.FPath',
							xtype : 'textfield',
							fieldLabel : '路径',
							width : 500,
							readOnly : true
						}, {
							id : 'DJ.System.MainMenuList.FiconCls',
							xtype : 'textfield',
							fieldLabel : '图标',
							width : 500
						}]
			}]
});
Ext.define('DJ.System.MainMenuList', {
			extend : 'Ext.panel.Panel',
			id : 'DJ.System.MainMenuList',
			closable : true,// 是否现实关闭按钮,默认为false
			title : "菜单管理",
			items : [Ext.create('DJ.System.MainMenuListPanel')],
			layout : 'fit'
		});