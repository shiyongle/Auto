/**
 * 启用/禁用功能
 * @param btn
 */
function onEffectButtonClick(btn) {
	var grid = Ext.getCmp("DJ.System.UserList");
	var feffect = 1;
	if (Ext.util.Format.trim(btn.text) == "启    用") {
		feffect = 0;
	}
	var record = grid.getSelectionModel().getSelection();
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
		return;
	}
	Ext.MessageBox.confirm("提示", "是否确认启用/禁用选中的用户?", function(btn) {
		if (btn == 'yes') {
			var ids = "";
			for (var i = 0; i < record.length; i++) {
				var fid = record[i].get("fid");
				if (feffect == 1
						&& Ext.util.Format.trim(fid) == "0f20f5bf-a80b-11e2-b222-60a44c5bbef3") {
					Ext.MessageBox.alert('提示', '不能禁用超级用户!');
					return;
				}
				ids += fid ;
				if (i < record.length - 1) {
					ids = ids + ",";
				}
			}
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
						url : "EffectUserList.do",
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
var grid = Ext.getCmp("DJ.System.UserList");
var record = grid.getSelectionModel().getSelection();
if (record.length != 1) {
	Ext.MessageBox.alert('提示', '只能选中一条记录进行修改!');
	return;
}
var edit=Ext.create('DJ.System.UserAssgins');
edit.settxtid(record[0].get("fid"));
edit.settxtvalue(record[0].get("fname"));
edit.parent = 'DJ.System.UserList';
edit.show();
}
/**
 * 分配权限
 * @param btn
 */
function onSetPermissionButtonClick(btn) {
var grid = Ext.getCmp("DJ.System.UserList");// Ext.getCmp("DJ.System.UserListPanel")
var record = grid.getSelectionModel().getSelection();
if (record.length == 0) {
	Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
	return;
}
var fid = record[0].get("fid");
var UserPermissionEidt = Ext.create('DJ.System.UserPermissionEidt');
UserPermissionEidt.show();
Ext.getCmp("DJ.System.UserPermissionEidt.UserName").setValue(record[0]
		.get("fname"));
Ext.getCmp("DJ.System.UserPermissionEidt.UserID").setValue(fid);

}
/**
 * 设置密码
 */
function onSetPasswordButtonClick(btn) {
var grid = Ext.getCmp("DJ.System.UserList");// Ext.getCmp("DJ.System.UserListPanel")
var record = grid.getSelectionModel().getSelection();
if (record.length == 0) {
	Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
	return;
}
var fid = record[0].get("fid");
var el = grid.getEl();
el.mask("系统处理中,请稍候……");
Ext.Ajax.request({
	url : "GetUserInfo.do",
	params : {
		fid : fid
	}, // 参数
	success : function(response, option) {
		var obj = Ext.decode(response.responseText);
		if (obj.success == true) {
			var SetPassWord = Ext.create('DJ.System.SetPassWord');
			SetPassWord.show();
			Ext.getCmp("DJ.System.UserEdit.UserFID").setValue(obj.data[0].fid);
			Ext.getCmp("DJ.System.UserEdit.UserName").setValue(obj.data[0].fname);
		} else {
			Ext.MessageBox.alert('错误', obj.msg);
		}
		el.unmask();
	}
});

}

//function relationButtonClick(btn)
//{
//	var fieldName='fsupplierid';
//	var a = Ext.getCmp('DJ.System.UserList.editbutton');
//	a.getEl().dom.click();
//	
//	var eidtform=Ext.getCmp("DJ.System.UserEdit").getform().getForm();
////	eidtform.findField('fcustomerid').show();
//	eidtform.findField('fsupplierid').show();
//	eidtform.getFields().each(function(field)
//	{
//				if(field.getName()!=fieldName)
//				{
//					field.setReadOnly(true);
//				}
//				
//	});
//	
//	
//	
//}
function relationUserButtonClick(btn){
	var grid = Ext.getCmp("DJ.System.UserList");
	var records = grid.getSelectionModel().getSelection();
	if (records.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行修改!');
		return;
	}
	var edit = Ext.create('DJ.System.UserToUserSelectFrameEdit');
	edit.settxtid(records[0].get("fid"));
	edit.settxtvalue(records[0].get("fname"));
	edit.parent = 'DJ.System.UserList';
	edit.show();
}
function relationSupplierButtonClick(btn)
{
	var grid = Ext.getCmp("DJ.System.UserList");
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行修改!');
		return;
	}
	var edit=Ext.create('DJ.System.UseSupplier');
	edit.settxtid(record[0].get("fid"));
	edit.settxtvalue(record[0].get("fname"));
	edit.parent = 'DJ.System.UserList';
	edit.show();
}
function relationCustomerButtonClick(btn)
{
	var grid = Ext.getCmp("DJ.System.UserList");
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行修改!');
		return;
	}
	
//	var userAssgins2 = Ext.create('DJ.System.UserCusSelectFrameUi');
//	
//	Ext.getCmp("DJ.System.UserCusSelectFrameUi.UserID").setValue(record[0].get("fid"));
//	Ext.getCmp("DJ.System.UserCusSelectFrameUi.UserName").setValue(record[0]
//			.get("fname"));
//	
	var edit=Ext.create('DJ.System.UserCusSelectFrameUi');
	edit.settxtid(record[0].get("fid"));
	edit.settxtvalue(record[0].get("fname"));
	edit.parent = 'DJ.System.UserList';
	edit.show();
//	var grid = Ext.getCmp("DJ.System.UserList");
//	var record = grid.getSelectionModel().getSelection();
//	if (record.length != 1) {
//		Ext.MessageBox.alert('提示', '只能选中一条记录进行修改!');
//		return;
//	}
//	var userCustomer = Ext.getCmp("DJ.System.UserCustomer")
//	if (userCustomer == null) {
//		userCustomer = Ext.create('DJ.System.UserCustomer');
//		
//	}
//	Ext.getCmp("DJ.System.UserCustomer.UserName").setFieldLabel("用户");
//	userCustomer.show();
//	Ext.getCmp("DJ.System.UserCustomer.UserFID").setValue(record[0].get("fid"));
//	Ext.getCmp("DJ.System.UserCustomer.UserName").setValue(record[0]
//			.get("fname"));
//	Ext.getCmp("DJ.System.UserCustomer.Ftype").setValue("0");
//	var custgrid = Ext.getCmp("DJ.System.UserCustomer.GridList");
//	var checkModel = custgrid.getSelectionModel();
//	var assginstore = custgrid.getStore();
//	assginstore.currentPage = 1;
//	assginstore.on("beforeload", function(store, options) {
//				Ext.apply(store.proxy.extraParams, {
//							fid : record[0].get("fid"),
//							ftype:"0"
//						});
//				checkModel.removeListener('deselect');
//			});
//	assginstore.load();
}
Ext.QuickTips.init();
Ext.define('DJ.System.UserList', {
			extend : 'Ext.c.GridPanel',
			title : "用户管理",
			id : 'DJ.System.UserList',
			iconCls : 'user',
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			pageSize : 100,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'getSysUserList.do',
//			Delurl : "DelUserList.do",
			EditUI : "DJ.System.UserEdit",
			exporturl:"sysuseroexcel.do",
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
				Ext.getCmp("DJ.System.UserList.querybutton").setTooltip("用户是否启用过滤值为数字：1：禁用；0：启用");
			},
			Action_BeforeAddButtonClick : function(EditUI) {
				// 新增界面弹出前事件
			},
			Action_AfterAddButtonClick : function(EditUI) {
				// 新增界面弹出后事件
				var eidtform=Ext.getCmp("DJ.System.UserEdit").getform().getForm();
				var pass= eidtform.findField('fpassword');
				pass.ownerCt.getComponent('password1').show();
				pass.show();
				eidtform.findField('fisfilter').hide();
				eidtform.findField('fisreadonly').hide();
//				eidtform.findField('fcustomerid').hide();
//				eidtform.findField('fsupplierid').hide();
			},
			Action_BeforeEditButtonClick : function(EditUI) {
				// 修改界面弹出前事件
			},
			Action_AfterEditButtonClick : function(EditUI) {
				// 修改界面弹出后事件
				// 可对编辑界面进行复制
				var eidtform=Ext.getCmp("DJ.System.UserEdit").getform().getForm();
				var pass= eidtform.findField('fpassword');
				var pass1= pass.ownerCt.getComponent('password1');
				pass1.setDisabled(true);
				eidtform.findField('fisfilter').show();
				eidtform.findField('fisreadonly').show();
//				eidtform.findField('fcustomerid').hide();
//				eidtform.findField('fsupplierid').hide();
			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			},
			custbar : [{
						// id : 'DelButton',
						text : '启    用',
						height : 30,
						handler : onEffectButtonClick
					}, {
						// id : 'DelButton',
						text : '禁    用',
						height : 30,
						handler : onEffectButtonClick
					}, {
						// id : 'DelButton',
						text : '修改密码',
						height : 30,
						handler : onSetPasswordButtonClick
					}, {
						// id : 'DelButton',
						text : '分配角色',
						height : 30,
						handler : assginRoleButtonClick
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

					},
//						{
//						text : '批量新增',
//						height : 30,
//						handler : function () {
//						
//							var winUserB = Ext.create("DJ.System.UserEditBatch"); 
//							winUserB.show();
//							
//						}
//					}, 
						{
						text : '关联用户',
						height : 30,
						handler : relationUserButtonClick
					}, {

		text : '关联地址',
		height : 30,
		handler : function() {

			var grid = Ext.getCmp("DJ.System.UserList");
			var records = grid.getSelectionModel().getSelection();
			if (records.length != 1) {
				Ext.MessageBox.alert('提示', '只能选中一条记录进行修改!');
				return;
			}
			var edit = Ext.create('DJ.System.UserAddressSelectFrameUi');
			edit.settxtid(records[0].get("fid"));
			edit.settxtvalue(records[0].get("fname"));
			edit.show();

		}

	}],
			fields : [{
						name : 'fid'
					}, {
						name : 'fname',
						myfilterfield : 'u.fname',
						myfiltername : '名称',
						myfilterable : true
					}, {
						name : 'fcustomername',
						myfilterfield : 'fcustomername',
						myfiltername : '客户名称',
						myfilterable : true
					}, {
						name : 'femail'
					}, {
						name : 'ftel'
					}, {
						name : 'fcreatetime'
					},{
						name : 'fisfilter',
						convert:function(value,record)
						{
							if(value=='1')
							{	
								return true;
							}else{
								return false;
							}	
						}
					},{
						name : 'fisreadonly',
						convert:function(value,record)
						{
							if(value=='1')
							{	
								return true;
							}else{
								return false;
							}	
						}
						
					},{
						name:'fcustomerid',
						myfilterfield : 'c.fname',
						myfiltername : '关联客户',
						myfilterable : true
					}, {
						name : 'feffect',//0为启用；1为禁用
						convert:function(value,record)
						{
							if(value=='1')
							{	
								return false;
							}else{
								return true;
							}	
						},
						myfilterfield : 'u.feffect',
						myfiltername : '是否启用',
						myfilterable : true
					},'fphone','ffax','fqq'],
			columns : [{
						xtype: 'rownumberer',
						header:'序号',
						width:30
					},{
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
						// readOnly:true,
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
						sortable : true
					}, {
						'xtype' : 'checkcolumn',
						'header' : '查看所有客户',//是否不过滤
						processEvent : function() {
						},
						// readOnly:true,
						'dataIndex' : 'fisfilter',
						sortable : true
					}, {
						'xtype' : 'checkcolumn',
						'header' : '只看自己帐号',//是否不过滤
						processEvent : function() {
						},
						// readOnly:true,
						'dataIndex' : 'fisreadonly',
						sortable : true
					},{
						'header' : '传真',
						'dataIndex' : 'ffax',
						width : 200,
						sortable : true,
						hidden:true
					},{
						'header' : '电话',
						'dataIndex' : 'fphone',
						width : 200,
						sortable : true,
						hidden:true
					},{
						'header' : 'QQ',
						'dataIndex' : 'fqq',
						width : 150,
						sortable : true
					}]
		})
