var ButtonTreePanel = Ext.create('Ext.tree.Panel', {
	useArrows : true,
	rootVisible : true,
	store : Ext.create('DJ.System.Store.ButtonStore'),
	listeners : {
		itemclick : function(view, record, item, index, e) {
			if (record.data.fisleaf == 1) {
				Ext.getCmp("DJ.System.ButtonList.ButtonParent")
						.setValue(record.data.fid);
				Ext.getCmp("DJ.System.ButtonList.MainMenuId")
						.setValue(record.data.furl);
				Ext.getCmp("DJ.System.ButtonList.ButtonFid").setValue(null);
				Ext.getCmp("DJ.System.ButtonList.ButtonName").setValue(null);
				Ext.getCmp("DJ.System.ButtonList.ButtonAction").setValue(null);
				Ext.getCmp("DJ.System.ButtonList.ButtonId").setValue(null);
				Ext.getCmp("DJ.System.ButtonList.Fpath")
						.setValue(record.data.fpath);
			} else if (record.data.fisleaf == 2) {
				
				var el = view.getEl();
				el.mask("系统处理中,请稍候……");
				Ext.Ajax.request({
							url : "Button/GetButtonInfo.do",
							params : {
								fid : record.data.fid
							}, // 参数
							success : function(response, option) {
								var obj = Ext.decode(response.responseText);
								if (obj.success == true) {
									Ext
											.getCmp("DJ.System.ButtonList.ButtonFid")
											.setValue(obj.data.fid);
									Ext
											.getCmp("DJ.System.ButtonList.ButtonName")
											.setValue(obj.data.fname);
									Ext
											.getCmp("DJ.System.ButtonList.ButtonAction")
											.setValue(obj.data.fbuttonaction);
									Ext.getCmp("DJ.System.ButtonList.ButtonId")
											.setValue(obj.data.fbuttonid);
									Ext
											.getCmp("DJ.System.ButtonList.ButtonParent")
											.setValue(obj.data.fparentid);
									Ext.getCmp("DJ.System.ButtonList.Fpath")
											.setValue(obj.data.fpath);
								} else {
									Ext.MessageBox.alert('提示', obj.msg);
								}
								el.unmask();
							}
						});
			} else {
				Ext.getCmp("DJ.System.ButtonList.ButtonParent").setValue(null);
				Ext.getCmp("DJ.System.ButtonList.MainMenuId").setValue(null);
				Ext.getCmp("DJ.System.ButtonList.ButtonFid").setValue(null);
				Ext.getCmp("DJ.System.ButtonList.ButtonName").setValue(null);
				Ext.getCmp("DJ.System.ButtonList.ButtonAction").setValue(null);
				Ext.getCmp("DJ.System.ButtonList.ButtonId").setValue(null);
				Ext.getCmp("DJ.System.ButtonList.Fpath").setValue(null);
			}
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
					}, {
						text : '权限回收',
						height : 40,
						handler : onRecoverPermissionButtonClick
					}]
		});
			
function onRefreshButtonClick(btn) {
	ButtonTreePanel.store.load();
}
function onAddButtonClick(btn) {
	var parentid = Ext.getCmp("DJ.System.ButtonList.ButtonParent").getValue();
	if (Ext.util.Format.trim(parentid) == "") {
		Ext.MessageBox.alert('提示', '节点不能增加按钮！');
		return;
	}
	var fid = Ext.getCmp("DJ.System.ButtonList.ButtonFid").getValue();
	if (Ext.util.Format.trim(fid) != "") {
		Ext.MessageBox.alert('提示', '按钮节点不能增加按钮！');
		return;
	}
	Ext.getCmp("DJ.System.ButtonList.ButtonFid").setValue(null);
	Ext.getCmp("DJ.System.ButtonList.ButtonName").setValue(null);
	Ext.getCmp("DJ.System.ButtonList.ButtonAction").setValue(null);
	Ext.getCmp("DJ.System.ButtonList.ButtonId").setValue(null);
}
function onSaveButtonClick(btn) {
	if (!Ext.getCmp("DJ.System.ButtonList.ButtonName").isValid()
			|| !Ext.getCmp("DJ.System.ButtonList.ButtonAction").isValid()
			|| !Ext.getCmp("DJ.System.ButtonList.ButtonId").isValid()) {
		Ext.MessageBox.alert('提示', '输入项格式不正确，请修改后再提交！');
		return;
	}
	var parentid = Ext.getCmp("DJ.System.ButtonList.ButtonParent").getValue();
	if (Ext.util.Format.trim(parentid) == "") {
		Ext.MessageBox.alert('提示', '节点不能增加按钮！');
		return;
	}
	var me=Ext.getCmp("DJ.System.ButtonList");
	var el = me.getEl();
	el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
				url : "Button/SaveButton.do",
				params : {
					fid : Ext.getCmp("DJ.System.ButtonList.ButtonFid")
							.getValue(),
					fname : Ext.getCmp("DJ.System.ButtonList.ButtonName")
							.getValue(),
					fbuttonaction : Ext
							.getCmp("DJ.System.ButtonList.ButtonAction")
							.getValue(),
					fbuttonid : Ext.getCmp("DJ.System.ButtonList.ButtonId")
							.getValue(),
					fparentid : Ext.getCmp("DJ.System.ButtonList.ButtonParent")
							.getValue(),
					fpath : Ext.getCmp("DJ.System.ButtonList.Fpath").getValue()
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						Ext.MessageBox.alert('成功', obj.msg);
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
					ButtonTreePanel.store.load();
					Ext.getCmp("DJ.System.ButtonList.ButtonFid").setValue(null);
					Ext.getCmp("DJ.System.ButtonList.ButtonName")
							.setValue(null);
					Ext.getCmp("DJ.System.ButtonList.ButtonAction")
							.setValue(null);
					Ext.getCmp("DJ.System.ButtonList.ButtonId").setValue(null);
					Ext.getCmp("DJ.System.ButtonList.ButtonParent")
							.setValue(null);
					Ext.getCmp("DJ.System.ButtonList.Fpath").setValue(null);
					
				}
			});
}
function onDelButtonClick(btn) {
	var fid = Ext.getCmp("DJ.System.ButtonList.ButtonFid").getValue();
	if (Ext.util.Format.trim(fid) == "") {
		Ext.MessageBox.alert('提示', '未选中菜单节点');
		return;
	}
	var me=Ext.getCmp("DJ.System.ButtonList");
	var el = me.getEl();
	el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
				url : "Button/DelButton.do",
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
					ButtonTreePanel.store.load();
					Ext.getCmp("DJ.System.ButtonList.ButtonFid").setValue(null);
					Ext.getCmp("DJ.System.ButtonList.ButtonName")
							.setValue(null);
					Ext.getCmp("DJ.System.ButtonList.ButtonAction")
							.setValue(null);
					Ext.getCmp("DJ.System.ButtonList.ButtonId").setValue(null);
					Ext.getCmp("DJ.System.ButtonList.Fpath").setValue(null);
				}
			});
}

function onRecoverPermissionButtonClick(btn) {
	var record = ButtonTreePanel.getSelectionModel().getSelection();
	if (record.length === 0) {
		Ext.MessageBox.alert('提示', '未选中节点');
		return;
	}
	if (record[0].data.depth<2) {
		Ext.MessageBox.alert('提示', '该节点不能回收');
		return;
	}
	var me=Ext.getCmp("DJ.System.ButtonList");
	var el = me.getEl();
	el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
				url : "Button/RecoverPermissions.do",
				params : {
					fid : record[0].get("fid")
					}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						Ext.MessageBox.alert('成功', obj.msg);
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
					ButtonTreePanel.store.load();
					Ext.getCmp("DJ.System.ButtonList.ButtonFid").setValue(null);
					Ext.getCmp("DJ.System.ButtonList.ButtonName")
							.setValue(null);
					Ext.getCmp("DJ.System.ButtonList.ButtonAction")
							.setValue(null);
					Ext.getCmp("DJ.System.ButtonList.ButtonId").setValue(null);
					Ext.getCmp("DJ.System.ButtonList.Fpath").setValue(null);
				}
			});
}	
Ext.define('DJ.System.ButtonListPanel', {
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
						items : [ButtonTreePanel],
						collapsible : false,
						layout : 'fit'
					}, {
						region : 'center',
						bodyStyle : "padding-top:5px", // padding-left:120px;
						xtype : 'panel',
						margins : '0 0 0 0',
						items : [{
									id : 'DJ.System.ButtonList.ButtonFid',
									xtype : 'textfield',
									fieldLabel : 'FID',
									width : 500,
									// hidden : true
									readOnly : true

								}, {
									id : 'DJ.System.ButtonList.ButtonName',
									xtype : 'textfield',
									fieldLabel : '权限名称',
									regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
									regexText : "不能包含特殊字符",
									width : 500
								}, {
									id : 'DJ.System.ButtonList.ButtonAction',
									xtype : 'textfield',
									fieldLabel : '按钮事件',
									width : 500
								}, {
									id : 'DJ.System.ButtonList.ButtonId',
									xtype : 'textfield',
									fieldLabel : '按钮ID',
									width : 500
								}, {
									id : 'DJ.System.ButtonList.ButtonParent',
									xtype : 'textfield',
									fieldLabel : '父节点ID',
									width : 500,
									readOnly : true
								}, {
									id : 'DJ.System.ButtonList.MainMenuId',
									xtype : 'textfield',
									fieldLabel : '菜单Id',
									width : 500,
									readOnly : true
								}, {
									id : 'DJ.System.ButtonList.Fpath',
									xtype : 'textfield',
									fieldLabel : '路径',
									width : 500,
									readOnly : true
								}]
					}]
		});
Ext.define('DJ.System.ButtonList', {
			extend : 'Ext.panel.Panel',
			id : 'DJ.System.ButtonList',
			closable : true,// 是否现实关闭按钮,默认为false
			title : "权限管理",
			items : [Ext.create('DJ.System.ButtonListPanel')],
			layout : 'fit'
		});