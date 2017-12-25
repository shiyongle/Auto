//分配权限
function onSetPermissionButtonClick(btn) {
	var grid = Ext.getCmp("DJ.System.Customer.SubRoleList");
	var record = grid.getSelectionModel().getSelection();
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
		return;
	}
	Ext.vmiUser_Role_ID = record[0].get("fid");
	var	permissionPanel = Ext.create('DJ.System.Customer.SubUserPermissionEdit');
	Ext.getCmp('DJ.System.Customer.SubUserPermissionEdit.UserName').setValue(record[0].get('fname'));
	Ext.getCmp('DJ.System.Customer.SubUserPermissionEdit.UserId').setValue(Ext.vmiUser_Role_ID);
	permissionPanel.show();
}                                                                                                                                                                                                                                         
function onUserButtonClick(btn){
	var grid = Ext.getCmp("DJ.System.Customer.SubRoleList");
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行修改!');
		return;
	}
	var edit=Ext.create('DJ.System.Customer.SubUserToRoleEdit');
	edit.settxtid(record[0].get("fid"));
	edit.settxtvalue(record[0].get("fname"));
	edit.show();
}
Ext.define('DJ.System.Customer.SubRoleList', {
			extend : 'Ext.c.GridPanel',
			title : "客户角色管理",
			id : 'DJ.System.Customer.SubRoleList',
			iconCls : 'user',
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			pageSize : 100,
			closable : true,
			url : 'getSubRoleList.do',
			Delurl : "delSubRole.do",
			EditUI : "DJ.System.Customer.SubRoleEdit",
			exporturl:"subRoleExcel.do",
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
				Ext.getCmp("DJ.System.Customer.SubRoleList.exportbutton").setVisible(false);
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
						text : '分配用户',
						height : 30,
						handler : onUserButtonClick
					}, {
						text : '分配权限',
						height : 30,
						handler : onSetPermissionButtonClick
					}],
					fields : [{
						name : 'fid'
					}, {
						name : 'fnumber'
					}, {
						name : 'fname',
						myfilterfield : 'fname',
						myfiltername : '角色名称',
						myfilterable : true
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
						renderer : function(value) {
							return value == '1' ? '是' : '否';
						},
						sortable : true
					}]
})
