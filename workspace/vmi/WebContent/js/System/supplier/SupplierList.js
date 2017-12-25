function formatfusedstatus(value) {
	return value == '1' ? '启用' : '禁用';
}
function onEffectButtonClick(btn) {
	var grid = Ext.getCmp("DJ.System.supplier.SupplierList");// Ext.getCmp("DJ.System.UserListPanel")
	var feffect = 0;
	if (Ext.util.Format.trim(btn.text) == "启    用") {
		feffect = 1;
	}
	var record = grid.getSelectionModel().getSelection();
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
		return;
	}
	Ext.MessageBox.confirm("提示", "是否确认启用选中的用户?", function(btn) {
				if (btn == 'yes') {
					var ids = "";
					for (var i = 0; i < record.length; i++) {
						var fid = record[i].get("fid");
						ids +=  fid ;
						if (i < record.length - 1) {
							ids = ids + ",";
						}
					}
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
								url : "EffectSupplierList.do",
								params : {
									feffect : feffect,
									fidcls : ids
								}, // 参数
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
//										Ext.MessageBox.alert('成功', obj.msg);
										djsuccessmsg( obj.msg);
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
  Ext.define('DJ.System.supplier.SupplierList', {
			extend : 'Ext.c.GridPanel',
			title : "供应商管理",
			id : 'DJ.System.supplier.SupplierList',
			iconCls : 'user',
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			pageSize : 100,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetSupplierList.do',
			Delurl : "DelSupplierList.do",
			EditUI : "DJ.System.supplier.SupplierEdit",
			exporturl:"suppliertoexcel.do",
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
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

						text:'导入客户资料',
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
								url:'setSupplierToCustomer.do',
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
					
					}],
			fields : [{
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
					}, {
						name : 'femail'
					}, {
						name : 'ftel'
					}, {
						name : 'fcreatetime'
					}, {
						name : 'flastupdatetime'
					}, {
						name : 'fsimplename'
					}, {
						name : 'fartificialperson'
					}, {
						name : 'fbarcode'
					}, {
						name : 'fbusiexequatur'
					}, {
						name : 'fbizregisterno'
					}, {
						name : 'fbusilicence'
					}, {
						name : 'fgspauthentication'
					}, {
						name : 'ftaxregisterno'
					}, {
						name : 'fusedstatus'
					}, {
						name : 'fmnemoniccode'
					}, {
						name : 'fforeignname'
					}, {
						name : 'faddress'
					}, {
						name : 'fcreatorid'
					}, {
						name : 'flastupdateuserid'
					}, {
						name : 'fcountry'
					}, {
						name : 'fcity'},{
						name:'fisManageStock'
					}],
					columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						'header' : '供应商名称',
						'dataIndex' : 'fname',
						sortable : true
					}, {
						'header' : '编码',
						'dataIndex' : 'fnumber',
						sortable : true
					}, {
						'header' : '简称',
						width : 70,
						'dataIndex' : 'fsimplename',
						sortable : true
					}, {
						'header' : '法人代表',
						width : 70,
						'dataIndex' : 'fartificialperson',
						sortable : true
					}, {
						'header' : '条形码',
						hidden : true,
						'dataIndex' : 'fbarcode',
						sortable : true
					}, {
						'header' : '经营许可证',
						'dataIndex' : 'fbusiexequatur',
						sortable : true
					}, {
						'header' : '工商注册号',
						'dataIndex' : 'fbizregisterno',
						sortable : true
					}, {
						'header' : '营业执照',
						'dataIndex' : 'fbusilicence',
						sortable : true
					}, {
						'header' : 'GSP认证',
						'dataIndex' : 'fgspauthentication',
						sortable : true
					}, {
						'header' : '税务登记号',
						'dataIndex' : 'ftaxregisterno',
						sortable : true
					}, {
						'header' : '状态',
						width : 50,
						'dataIndex' : 'fusedstatus',
						renderer : formatfusedstatus,
						sortable : true
					}, {
						'header' : '出入库管理',
						width : 80,
						'dataIndex' : 'fisManageStock',
						renderer : function (value) {
							return value == '1' ? '是' : '否';
						},
						sortable : true
					}, {
						'header' : '助记码',
						'dataIndex' : 'fmnemoniccode',
						sortable : true
					}, {
						'header' : '外文名称',
						hidden : true,
						'dataIndex' : 'fforeignname',
						sortable : true
					}, {
						'header' : '地址',
						'dataIndex' : 'faddress',
						sortable : true
					}, {
						'header' : '手机',
						hidden : true,
						'dataIndex' : 'ftel',
						sortable : true
					}, {
						'header' : '邮箱',
						hidden : true,
						'dataIndex' : 'femail',
						sortable : true
					}, {
						'header' : '国家',
						hidden : true,
						width : 50,
						'dataIndex' : 'fcountry',
						sortable : true
					}, {
						'header' : '城市',
						hidden : true,
						width : 50,
						'dataIndex' : 'fcity',
						sortable : true
					}, {
						'header' : '修改时间',
						'dataIndex' : 'flastupdatetime',
						width : 140,
						sortable : true
					}, {
						'header' : '创建时间',
						'dataIndex' : 'fcreatetime',
						width : 140,
						sortable : true
					}, {
						'header' : '描述',
						hidden : true,
						'dataIndex' : 'fdescription',
						sortable : true
					}]
		});
