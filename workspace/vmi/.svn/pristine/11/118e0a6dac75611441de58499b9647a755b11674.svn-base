/**
 * 启用/禁用功能
 * @param btn
 */
function onEffectButtonClick(btn) {
	var grid = Ext.getCmp("DJ.System.Customer.SubUserList");
	var feffect = 0;
	if (Ext.util.Format.trim(btn.text) == "启    用") {
		feffect = 1;
	}
	var record = grid.getSelectionModel().getSelection();
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
		return;
	}
	Ext.MessageBox.confirm("提示", "是否确认"+(feffect?'启':'禁')+"用选中的用户?", function(btn) {
		if (btn == 'yes') {
			var ids = "";
			for (var i = 0; i < record.length; i++) {
				ids += record[i].get("fid");
				if (i < record.length - 1) {
					ids = ids + ",";
				}
			}
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
						url : "effectSubUserList.do",
						params : {
							feffect : feffect,
							fidcls : ids
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								Ext.MessageBox.alert('成功', obj.msg);
								grid.store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});
		}
	});
}
/**
 * 分配角色
 * @param btn
 */
function assginRoleButtonClick(btn) {
	var grid = Ext.getCmp("DJ.System.Customer.SubUserList");
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行修改!');
		return;
	}
	var edit=Ext.create('DJ.System.Customer.SubRoleToUserEdit');
	edit.settxtid(record[0].get("fid"));
	edit.settxtvalue(record[0].get("fname"));
	edit.show();
}
/**
 * 分配权限
 * @param btn
 */
function onSetPermissionButtonClick(btn) {
	var grid = Ext.getCmp("DJ.System.Customer.SubUserList");
	var record = grid.getSelectionModel().getSelection();
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
		return;
	}
	Ext.vmiUser_Role_ID = record[0].get('fid');
	var	permissionPanel = Ext.create('DJ.System.Customer.SubUserPermissionEdit');
	Ext.getCmp('DJ.System.Customer.SubUserPermissionEdit.UserName').setValue(record[0].get('fname'));
	Ext.getCmp('DJ.System.Customer.SubUserPermissionEdit.UserId').setValue(Ext.vmiUser_Role_ID);
	permissionPanel.show();

}
/**
 * 设置密码
 */
function onSetPasswordButtonClick(btn) {
	var grid = Ext.getCmp("DJ.System.Customer.SubUserList");
	var record = grid.getSelectionModel().getSelection();
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
		return;
	}
	var fid = record[0].get("fid");
	var el = grid.getEl();
	el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
		url : "getSubUserInfo.do",
		params : {
			fid : fid
		}, 
		success : function(response, option) {
			var obj = Ext.decode(response.responseText);
			if (obj.success == true) {
				var SetPassWord = Ext.create('DJ.System.Customer.SetSubUserPassWord');
				SetPassWord.show();
				Ext.getCmp("DJ.System.Customer.SubUserEdit.UserFID").setValue(obj.data[0].fid);
				Ext.getCmp("DJ.System.Customer.SubUserEdit.UserName").setValue(obj.data[0].fname);
			} else {
				Ext.MessageBox.alert('错误', obj.msg);
			}
			el.unmask();
		}
	});

}

Ext.define('DJ.System.Customer.SubUserList', {
			extend : 'Ext.c.GridPanel',
			title : "子用户管理",
			id : 'DJ.System.Customer.SubUserList',
			iconCls : 'user',
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			pageSize : 100,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'getSubUserList.do',
			Delurl : "delSubUserList.do",
			EditUI : "DJ.System.Customer.SubUserEdit",
			exporturl:"subUserExcel.do",
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等	
			Ext.getCmp("DJ.System.Customer.SubUserList.exportbutton").setVisible(false);

			},
			Action_BeforeAddButtonClick : function(EditUI) {
				// 新增界面弹出前事件
			},
			Action_AfterAddButtonClick : function(EditUI) {
				// 新增界面弹出后事件
				var eidtform=Ext.getCmp("DJ.System.Customer.SubUserEdit").getform().getForm();
				var pass= eidtform.findField('fpassword');
				pass.ownerCt.getComponent('password1').show();
				pass.show();
			},
			Action_BeforeEditButtonClick : function(EditUI) {
				// 修改界面弹出前事件
			},
			Action_AfterEditButtonClick : function(EditUI) {
				// 修改界面弹出后事件
				// 可对编辑界面进行复制
				var eidtform=Ext.getCmp("DJ.System.Customer.SubUserEdit").getform().getForm();
				var pass= eidtform.findField('fpassword');
				var pass1= pass.ownerCt.getComponent('password1');
				pass1.setDisabled(true);
			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			},
			custbar : [{
						text : '启    用',
						height : 30,
						handler : onEffectButtonClick
					}, {
						text : '禁    用',
						height : 30,
						handler : onEffectButtonClick
					}, {
						text : '修改密码',
						height : 30,
						handler : onSetPasswordButtonClick
					}, {
						text : '分配角色',
						height : 30,
						handler : assginRoleButtonClick
					}, {
						text : '分配权限',
						height : 30,
						handler : onSetPermissionButtonClick
					}],
			fields : [{
						name : 'fid'
					}, {
						name : 'fname',
						myfilterfield : 'fname',
						myfiltername : '用户名称',
						myfilterable : true
					}, {
						name : 'feffect',
						convert:function(value,record)
						{
							if(value=='1')
							{	
								return true;
							}else{
								return false;
							}	
						}
					}, {
						name : 'fcustomername'
					}, {
						name : 'femail'
					}, {
						name : 'ftel'
					}, {
						name : 'fcreatetime'
					}],
			columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : '用户名称',
						'dataIndex' : 'fname',
						sortable : true
					}, {
						'xtype' : 'checkcolumn',
						'header' : '是否启用',
						processEvent : function() {
						},
						'dataIndex' : 'feffect',
						sortable : true
					}, {
						'header' : '客户名称',
						'dataIndex' : 'fcustomername',
						sortable : true
					}, {
						'header' : '邮箱',
						'dataIndex' : 'femail',
						sortable : true
					}, {
						'header' : '手机',
						'dataIndex' : 'ftel',
						sortable : true
					}, {
						'header' : '创建时间',
						'dataIndex' : 'fcreatetime',
						width : 200,
						flex : 1,
						sortable : true
						}]
		})

