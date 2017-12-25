/*function formatEffect(value) {
	return value == '1' ? '是' : '否';
}
function formatStatus(value) {
	return value == '1' ? '核准' : (value == '0' ? '未核准' : '禁用');
}*/


Ext.define('DJ.System.Customer.CustomerList', {
			extend : 'Ext.c.GridPanel',
			title : "客户资料",
			id : 'DJ.System.Customer.CustomerList',
			iconCls : 'user',
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			pageSize : 100,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetCustomerList.do',
			Delurl : "DelCustomerList.do",
			EditUI : "DJ.System.Customer.CustomerEdit",
			exporturl:"customertoexcel.do",
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
			},
			Action_BeforeAddButtonClick : function(EditUI) {
				
				// 新增界面弹出前事件
//				Ext.getCmp('DJ.System.Customer.CustomerEdit').needFid = true;
				
//				EditUI.needFid = true;
			},
			Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件

		var editer = Ext.getCmp('DJ.System.Customer.CustomerEdit');

		var c0 = editer;

			Ext.Ajax.request({
				timeout : 6000,

				params : {

				},

				url : "getcustomeridWithAdd.do",
				success : function(response, option) {

					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						c0.down("hidden[name=fid]").setValue((obj.data)[0].fid);
						c0.down("textfield[name=fnumber]").setValue((obj.data)[0].fnumber);

					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}

				}
			});


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
			fields : [ {
					name : 'fid'
				}, {
					name : 'fname',
					myfilterfield : 'fname',
						myfiltername : '名称',
						myfilterable : true
				}, {
					name : 'fnumber',
					myfilterfield : 'fnumber',
						myfiltername : '编码',
						myfilterable : true
				}, {
					name : 'fdescription'
			//	}, {
			//		name : 'fcreatorid'
				}, {
					name : 'fcreatetime'
				}, {
					name : 'findustryid'
				}, {
					name : 'fartificialperson'
				}, {
					name : 'fbizregisterno'
				}, {
					name : 'fisinternalcompany'
				}, {
					name : 'ftxregisterno'
				}, {
					name : 'fmnemoniccode'
				}, {
					name : 'faddress'
				}, {
					name : 'fusedstatus'
				},{
					name : 'fschemedesign'
				} ],
			columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
						text : 'fid',
						dataIndex : 'fid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						text : '编码',
						dataIndex : 'fnumber',
						sortable : true,
						filter : {
							type : 'string'
						}
					}, {
						text : '客户名称',
						dataIndex : 'fname',
						sortable : true,
						filter : {
							type : 'string'
						}
//					}, {
//						text : '助记码',
//						dataIndex : 'fmnemoniccode',
//						sortable : true
					}, {
						text : '地址',
						dataIndex : 'faddress',
						sortable : true,
						width : 250
					}, {
						text : '法人代表',
						dataIndex : 'fartificialperson',
						sortable : true	
					}, {
						text : '注册号',
						dataIndex : 'ftxregisterno',
						sortable : true
					}, {
						text : '创建时间',
						dataIndex : 'fcreatetime',
						width : 150,
						sortable : true

					}, {
						text : '是否方案确认',
						dataIndex : 'fschemedesign',
						sortable : true,
						renderer:function(value){
							if(value==1){
								return '是';
							}else{
								return '否';
							}
						}
					}, {
						
						text : '是否认证',
						dataIndex : 'fisinternalcompany',
						sortable : true,
						renderer:function(value){
							if(value==1){
								return '是';
							}else{
								return '否';
							}
						}
					}],
					custbar : [{
								text : '关联地址',
								height : 30,
								handler : onCustRelationAdressClick
							},{
								text:'导入制造商资料',
								height:30,
								handler:function(){
									var record = this.up('grid').getSelectionModel().getSelection();
									var fids = '';
									if(record.length==0){
										Ext.Msg.alert('提示',"请选中一条数据！");
										return;
									}
									for(var i =0;i<record.length;i++){
										fids += record[i].get('fid');
										if(i<record.length-1){
											 fids +=',';
										}
									}
									Ext.Ajax.request({
										url:'setCustomerToSupplier.do',
										params:{fids:fids},
										success:function(response){
											var obj = Ext.decode(response.responseText);
											if(obj.success==true){
												djsuccessmsg(obj.msg);
											}else{
												Ext.Msg.alert('提示',obj.msg);
											}
										}
									})
								}
							}]
		})
function onCustRelationAdressClick(btn) {
	
	var records=Ext.getCmp('DJ.System.Customer.CustomerList').getSelectionModel().getSelection();
//	txtname.setVisible(true);
	if (records.length != 1) {
		Ext.Msg.alert("提示","只能选中一条记录进行修改!");
	}else{
		var edit = Ext.create('DJ.System.Customer.CustRelationAdress');
		edit.settxtid(records[0].get("fid"));
		edit.settxtvalue(records[0].get("fname"));

		var pstore = Ext.getCmp('DJ.System.Customer.CustRelationAdress.fcustomerid').getStore();
		pstore.getProxy().setExtraParam("fcustomerid", records[0].get("fid"));
		edit.show();
	}

}

