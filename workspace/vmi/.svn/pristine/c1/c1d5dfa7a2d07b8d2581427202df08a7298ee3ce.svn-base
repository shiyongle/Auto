/**
 * 分配权限
 * @param btn
 */
function onSetPermissionButtonClick(btn) {
	var grid = Ext.getCmp("DJ.System.RoleList");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
		return;
	}
	var fid = record[0].get("fid");
	var UserPermissionEidt = Ext.create('DJ.System.RolePermissionEidt');
	UserPermissionEidt.show();
	Ext.getCmp("DJ.System.RolePermissionEidt.UserName").setValue(record[0]
			.get("fname"));
	Ext.getCmp("DJ.System.RolePermissionEidt.UserID").setValue(fid);

}
function formatEffect(value) {
	return value == '1' ? '是' : '否';
}
function formatType(value) {
	return value == '10' ? '预设用户' : '自定义用户';
}
 function onUserButtonClick(btn)
 {
	 var grid = Ext.getCmp("DJ.System.RoleList");
		var record = grid.getSelectionModel().getSelection();
		if (record.length != 1) {
			Ext.MessageBox.alert('提示', '只能选中一条记录进行修改!');
			return;
		}
		var edit=Ext.create('DJ.System.RoleAssgins');
		edit.settxtid(record[0].get("fid"));
		edit.settxtvalue(record[0].get("fname"));
		edit.show();
 }
function relationSupplierButtonClick(btn)
{
	var grid = Ext.getCmp("DJ.System.RoleList");
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行修改!');
		return;
	}
	var edit  = Ext.create('DJ.System.RoleSupplier');
	edit.settxtid(record[0].get("fid"));
	edit.settxtvalue(record[0].get("fname"));
	edit.show();
}
function relationCustomerButtonClick(btn)
{
	
	var grid = Ext.getCmp("DJ.System.RoleList");
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行修改!');
		return;
	}
	var edit  = Ext.create('DJ.System.RoleCustomer');
	edit.settxtid(record[0].get("fid"));
	edit.settxtvalue(record[0].get("fname"));
	edit.show();
}
Ext.define('DJ.System.RoleList', {
			extend : 'Ext.c.GridPanel',
			title : "角色管理",
			id : 'DJ.System.RoleList',
			iconCls : 'user',
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			pageSize : 100,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetRoleList.do',
			Delurl : "DelRoleList.do",
			EditUI : "DJ.System.RoleEdit",
			exporturl:"roletoexcel.do",
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
			},
			Action_BeforeAddButtonClick : function(EditUI) {
				// 新增界面弹出前事件
			},
			Action_AfterAddButtonClick : function(EditUI) {
			},
			Action_BeforeEditButtonClick : function(EditUI) {
				// 修改界面弹出前事件
			},
			Action_AfterEditButtonClick : function(EditUI) {
			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			},
			custbar : [{
						// id : 'DelButton',
						text : '分配用户',
						height : 30,
						handler : onUserButtonClick
					}, {
						// id : 'DelButton',
						text : '分配权限',
						height : 30,
						handler : onSetPermissionButtonClick
					}, {
						// id : 'DelButton',
						text : '关联客户',
						height : 30,
						handler : relationCustomerButtonClick
					}, {
						// id : 'DelButton',
						text : '关联供应商',
						height : 30,
						handler : relationSupplierButtonClick
					}],
					fields : [{
						name : 'fid'
					}, {
						name : 'fnumber'
					}, {
						name : 'fname'
					}, {
						name : 'fdescription'
					}, {
						name : 'fisdefrole'
					}, {
						name : 'fcreateid'
					}, {
						name : 'ftype'
					}, {
						name : 'fcratetime'
					}, {
						name : 'flastupdateid'
					}, {
						name : 'flastupdatetime'
					}],
					columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : '编码',
						'dataIndex' : 'fnumber',
						sortable : true
					}, {
						'header' : '名称',
						'dataIndex' : 'fname',
						sortable : true
					}, {
						'header' : '描述',
						'dataIndex' : 'fdescription',
						width : 350,
						sortable : true
					}, {
						'header' : '默认角色',
						'dataIndex' : 'fisdefrole',
						renderer : formatEffect,
						sortable : true
					}, {
						'header' : '用户类型',
						'dataIndex' : 'ftype',
						hidden : true,
						renderer : formatType
					}]
		})
