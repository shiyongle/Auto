Ext.define('DJ.System.CertManagement', {
	extend : 'Ext.c.GridPanel',
	title : "登录设备管理",
	id : 'DJ.System.CertManagement',
	iconCls : 'user',
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	pageSize : 100,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'getCertList.do',
	custbar : [{
		// id : 'DelButton',
		text : '审    核',
		height : 30,
		handler : onAuditButtonClick
	}, {
		// id : 'DelButton',
		text : '反审核',
		height : 30,
		handler : onUnAuditButtonClick
	}],
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		// this._showButtons(['DJ.System.UserOnlineManagement.delbutton',
		// 'DJ.System.UserOnlineManagement.refreshbutton',
		// 'DJ.System.UserOnlineManagement.querybutton']);
				Ext.getCmp("DJ.System.CertManagement.addbutton").setVisible(false);
				Ext.getCmp("DJ.System.CertManagement.editbutton").setVisible(false);
				Ext.getCmp("DJ.System.CertManagement.delbutton").setVisible(false);
		// this._operateButtonsView(true, [
		// 'DJ.System.UserOnlineManagement.delbutton',
		// 'DJ.System.UserOnlineManagement.refreshbutton',
		// 'DJ.System.UserOnlineManagement.querybutton']);

//		this._operateButtonsView(true, [3, 4, 5,6,7,8]);

		// Ext.getCmp("DJ.System.UserOnlineManagement.delbutton").setText("");

	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件

	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
		// 可对编辑界面进行复制
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	fields : [{
		name : 'fid'
	}, {
		name : 'fusername',
		myfilterfield : 'FUSERNAME',
		myfiltername : '用户名',
		myfilterable : true
	}, {
		name : 'fcreatetime'

	}, {
		name : 'fauditor'

	}, {
		name : 'fauditedtime'

	}, {
		name : 'fserial'

	}, {
		name : 'flastlogintime'
	}, {
		name : 'fremark'
	}, {
		name : 'faudited'
	}],
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true

	}, {
		'header' : '用户名称',
		'dataIndex' : 'fusername',
		width : 100,
		sortable : true
	}, {
		'header' : '申请时间',
		width : 150,
		'dataIndex' : 'fcreatetime',
		sortable : true
	}, {
		'header' : '审核人',
		width : 100,
		'dataIndex' : 'fauditor',
		sortable : true
	}, {
		'header' : '审核时间',
		'dataIndex' : 'fauditedtime',
		width : 150,
		sortable : true
	}, {
		'header' : '机器码',
		'dataIndex' : 'fserial',
		width : 400,
		sortable : true
	}, {
		'header' : '最后登录时间',
		'dataIndex' : 'flastlogintime',
		width : 200,
		sortable : true
	}, {
		'header' : '备注',
		'dataIndex' : 'fremark',
		width : 200,
		sortable : true
	}, {
		'header' : '是否审核',
		'dataIndex' : 'faudited',
		width : 200,
		sortable : true
	}]
});

function onUnAuditButtonClick(btn) {
	var grid = Ext.getCmp("DJ.System.CertManagement");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
		return;
	}
	var fidcls = record[0].get("fid");
	var el = grid.getEl();
	el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
		url : "CertUnAudited.do",
		params : {
			fidcls : fidcls
		}, // 参数
		success : function(response, option) {
			var obj = Ext.decode(response.responseText);
			if (obj.success == true) {
				Ext.MessageBox.alert('成功',  obj.msg);
				grid.store.load();
			} else {
				Ext.MessageBox.alert('错误', obj.msg);
			}
			el.unmask();
		}
	});

}
function onAuditButtonClick(btn) {
	var grid = Ext.getCmp("DJ.System.CertManagement");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
		return;
	}
	var fidcls = record[0].get("fid");
	var el = grid.getEl();
	el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
		url : "CertAudited.do",
		params : {
			fidcls : fidcls
		}, // 参数
		success : function(response, option) {
			var obj = Ext.decode(response.responseText);
			if (obj.success == true) {
				Ext.MessageBox.alert('成功',  obj.msg);
				grid.store.load();
			} else {
				Ext.MessageBox.alert('错误', obj.msg);
			}
			el.unmask();
		}
	});

}
