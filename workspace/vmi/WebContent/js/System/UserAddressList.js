function formateffect(value) {
	return value == '0' ? '禁用' : '启用';
}
function onEffectButtonClick(btn) {
	var grid = Ext.getCmp("DJ.System.UserAddressList");// Ext.getCmp("DJ.System.UserListPanel")
	var feffect = 0;
	var record = grid.getSelectionModel().getSelection();
	if (Ext.util.Format.trim(btn.text) == "启    用") {
		feffect = 1;
	}
	else {
		if (record[0].get("fdefault") == "1") {
			Ext.MessageBox.alert('提示', '默认地址不能禁用!');
			return;
		}
	}
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
		return;
	}
	var cdids = "";
	for (var i = 0; i < record.length; i++) {
		var cdid = record[i].get("cdid");
		cdids += cdid;
		if (i < record.length - 1) {
			cdids += ",";
		}
	}
	var el = grid.getEl();
	el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
				url : "setCustAdEffect.do",
				params : {
					feffect : feffect,
					fidcls : cdids
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						djsuccessmsg(obj.msg);
						grid.store.load();
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
}
  Ext.define('DJ.System.UserAddressList', {
			extend : 'Ext.c.GridPanel',
			title : "我的地址",
			id : 'DJ.System.UserAddressList',
			iconCls : 'user',
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			pageSize : 100,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetUserAddressList.do',
			//Delurl : "DelUserAddress.do",
			EditUI : "DJ.System.UserAddressEdit",
			//exporturl:"suppliertoexcel.do",
			onload : function() {
				var win = this;	
				var btHideSs = ['button[text=删    除]', 'button[text=修  改]','button[text=导出Excel]'];
				Ext.each(btHideSs, function(ele, index, all) {
				   win.down(ele).hide();
				} );
				//覆盖默认删除方法
				var del = Ext.getCmp('DJ.System.UserAddressList.delbutton')
				del.setHandler(
				function(){
							var record = this.up('grid').getSelectionModel().getSelection();
							var fadid = '';
							var fcdid = '';
							if(record.length!=1){
								Ext.Msg.alert('提示',"请选中一条数据！");
								return;
							}
							Ext.Msg.confirm('提示','是否确认删除?',function(action){
							if(action == 'yes'){
								fadid = record[0].get('fid');
								fcdid = record[0].get('cdid');
								Ext.Ajax.request({
									url:'delUserToAddress.do',
									params:{fadid:fadid,fcdid:fcdid},
									success:function(response){
										var obj = Ext.decode(response.responseText);
										if(obj.success==true){
											djsuccessmsg(obj.msg);
											Ext.getCmp("DJ.System.UserAddressList").store.loadPage(1);
										}else{
											Ext.Msg.alert('提示',obj.msg);
										}
									}
								})
							}
				          });

						}
				);
				// 加载后事件，可以设置按钮，控件值等
				//只查看自己帐号的用户不能 启用，禁用地址
				this.getStore().addListener("load",function(me, records){
					if(records.length>1){
						if(records[0].get("feffect")==2){
							win.down('button[text=启    用]').hide();
							win.down('button[text=禁    用]').hide();
						}
					}
				});
			},
			Action_BeforeAddButtonClick : function(EditUI) {
				// 新增界面弹出前事件				
			},
			Action_AfterAddButtonClick : function(EditUI) {
				// 新增界面弹出后事件
				Ext.getCmp(EditUI.EditUI).down('cCombobox[name=fcustomerid]').setReadOnly(true);
			},
			Action_BeforeEditButtonClick : function(EditUI) {
		       // 修改界面弹出前事件
				var grid = Ext.getCmp("DJ.System.UserAddressList");
				var record = grid.getSelectionModel().getSelection();
				if(record.length!=1){
					throw ("请选择一条记录!");
				}
				Ext.Ajax.request({
							url : "checkUserAddress.do",
							async : false,
							params : {
								fid : record[0].get("fid")
							}, // 参数
							success : function(response, option) {
								var obj = Ext.decode(response.responseText);
								if (obj.success == false) {
									throw obj.msg;
								}
							}
				});
			
			},
			Action_AfterEditButtonClick : function(EditUI) {
				// 修改界面弹出后事件
				Ext.getCmp(EditUI.EditUI).down('cCombobox[name=fcustomerid]').setReadOnly(true);
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
					},{

						text:'地址默认',
						handler:function(){
							var record = this.up('grid').getSelectionModel().getSelection();
							var fadid = '';
							if(record.length!=1){
								Ext.Msg.alert('提示',"请选中一条数据！");
								return;
							}
							if(record[0].get("fdefault")=="1"){
								Ext.Msg.alert('提示',"已经设置为默认！");
								return;								
							}
							fadid = record[0].get('fid');
							fcdid = record[0].get('cdid');
							Ext.Ajax.request({
								url:'setUserAddressDft.do',
								params:{fadid:fadid,fcdid:fcdid},
								success:function(response){
									var obj = Ext.decode(response.responseText);
									if(obj.success==true){
										djsuccessmsg(obj.msg);
										Ext.getCmp("DJ.System.UserAddressList").store.loadPage(1);
									}else{
										Ext.Msg.alert('提示',obj.msg);
									}
								}
							})
						}
					
					}],
			fields : [{
						name : 'fid'
					}, {
						name : 'cdid'
					},{
						name : 'custname'
					}, {
						name : 'dtaddress',
						myfilterfield : 'ad.FDETAILADDRESS',
						myfiltername : '详细地址',
						myfilterable : true
					}, {
						name : 'linkman'
					}, {
						name : 'phone'
					},{
					    name : 'feffect'
					},{
					    name : 'fdefault'
					}],
					columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false
					},{
						'header' : 'cdid',
						'dataIndex' : 'cdid',
						 hidden : true,
						 hideable : false
					},{
						'header' : '客户名称',
						'dataIndex' : 'custname'
					}, {
						'header' : '详细地址',
						'dataIndex' : 'dtaddress',
						sortable : true,
						width : 262
					}, {
						'header' : '联系人',
						width : 70,
						'dataIndex' : 'linkman'
					}, {
						'header' : '联系方式',
						width : 70,
						'dataIndex' : 'phone'
					}, {
						'header' : '启用',
						'dataIndex' : 'feffect',
						renderer : formateffect,
						sortable : true
					},{
						'header' : '默认',
						'dataIndex' : 'fdefault',	
						sortable : true,
						renderer : function(value){
							if(value==1){
								return '默认';
							}
						}
					}]
		});
