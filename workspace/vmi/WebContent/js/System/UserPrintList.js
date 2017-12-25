Ext.define('DJ.System.UserPrintList',{
	extend : 'Ext.panel.Panel',
	id : 'DJ.System.UserPrintList',
	autoScroll : false,
	border : false,
	layout :  'fit',
	title:'用户基础资料',
	closable:true,
	items:[Ext.create('DJ.System.UserList',{
		id:'DJ.System.UserPrintList.UserList',
		title:'',
		iconCls:'',
		closable : false,
		url:'getUserPrintList.do',
		EditUI:'DJ.System.UserPrintEdit',
		onload:function(){
			this.query('toolbar[dock=top] button').forEach(function(button){
				button.hide();
			})
			this.down('button[text*=刷]').show();
			this.down('button[text*=修]').show();
			this.down('button[text*=看]').show();
			this.columns.forEach(function(column){
				if(column.hidden){
					if(column.text!='fid'){
						column.show();
					}
				}
				if(column.text=='是否启用'||column.text=='查看所有客户'||column.text=='只看自己帐号'){
					column.hide();
				}
				if(column.text=='客户名称'){
					column.setText('本公司名称');
				}
			})
		},
		Action_AfterEditButtonClick : function(EditUI) {
				// 修改界面弹出后事件
				// 可对编辑界面进行复制
				var eidtform=Ext.getCmp("DJ.System.UserPrintEdit").getform().getForm();
				Ext.getCmp("DJ.System.UserPrintEdit").url="SaveCustomerUser.do";
				var pass= eidtform.findField('fpassword');
				var pass1= pass.ownerCt.getComponent('password1');
				pass1.setDisabled(true);
				eidtform.findField('fisfilter').show();
				eidtform.findField('fisreadonly').show();
				eidtform.findField('fname').hide();
				eidtform.findField('fcustomername').setFieldLabel("本公司名称");
				Ext.getCmp("DJ.System.UserPrintEdit").query('checkboxfield').forEach(function(check){
					check.hide();
				})
				Ext.getCmp("DJ.System.UserPrintEdit").query('combobox,cCombobox').forEach(function(combobox){
					combobox.hide();
				})
				eidtform.findField('fname').setReadOnly(true);
//				eidtform.findField('fcustomername').setReadOnly(true);
		},
		Action_AfterViewButtonClick : function(EditUI) {
				var eidtform=Ext.getCmp("DJ.System.UserPrintEdit").getform().getForm();
				eidtform.findField('fname').hide();
				eidtform.findField('fcustomername').setFieldLabel("本公司名称");
				Ext.getCmp("DJ.System.UserPrintEdit").query('checkboxfield').forEach(function(check){
					check.hide();
				})
				Ext.getCmp("DJ.System.UserPrintEdit").query('combobox,cCombobox').forEach(function(combobox){
					combobox.hide();
				})
		}
	})]
})