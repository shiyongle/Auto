function editCustproductTreeLink(btn) {

	var grid = Ext.getCmp("DJ.System.product.CustproductList");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行查看!');
		return;
	}
	if(record[0].get('fcharacterid')!=''){
		Ext.Msg.alert('提示','包装需求产生的产品不能操作！');
		return;
	}
	var fid = record[0].get("fid");

	var productdefdetail = Ext.getCmp('DJ.System.product.CustProductStructTreeEdit');

	if (productdefdetail == null) {
		productdefdetail = Ext.create('DJ.System.product.CustProductStructTreeEdit');
	}

	productdefdetail.show();
	var structtree = Ext
			.getCmp('DJ.System.product.CustProductStructTreeEdit.CustProductTreeEdit');

	var gridProductWin = Ext
			.getCmp('DJ.System.product.CustProductStructTreeEdit.CustProductList');

	var structStore = structtree.store;
	
	var rootvalue = {
		expanded : true,
		leaf : false,
		text : record[0].get('fname'),
		id : fid,
		fnumber : record[0].get('fnumber')
	};
	structStore.setRootNode(rootvalue);

	gridProductWin.store.proxy.extraParams.dbfid = fid;
	gridProductWin.store.proxy.extraParams.fcustomerid = record[0].get("fcustomerid");//???

	gridProductWin.store.load();

}



function onCustRelationProductClick(btn)
{
//	var grid = Ext.getCmp("DJ.System.product.CustproductList");
//	var record = grid.getSelectionModel().getSelection();
//	if (record.length != 1) {
//		Ext.MessageBox.alert('提示', '只能选中一条记录进行关联!');
//		return;
//	}
//	if(record[0].get("fcustomerid")=="null"||record[0].get("fcustomerid")=="")
//	{
//		Ext.MessageBox.alert('提示', '请先设置对应的客户或数据不同步请刷新！');
//		return;
//	}
//	var edit=Ext.create('DJ.System.product.RelationProduct');
//	edit.settxtid(record[0].get("fid"));
//	edit.settxtvalue(record[0].get("fname"));
//	edit.show();
//	Ext.getCmp("DJ.System.product.CustproductList").store.load();
	
	var grid = Ext.getCmp("DJ.System.product.CustproductList");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行查看!');
		return;
	}
	if(record[0].get('feffect')==0){
		Ext.Msg.alert('提示','该产品已禁用！');
		return;
	}
	if(record[0].get('fcharacterid')!=''){
		Ext.Msg.alert('提示','包装需求产生的客户产品不能操作！');
		return;
	}
	var fid = record[0].get("fid");

	var productdefdetail = Ext.getCmp('DJ.System.product.CustProductTreeEdit');

	if (productdefdetail == null) {
		productdefdetail = Ext.create('DJ.System.product.CustProductTreeEdit');
	}

	productdefdetail.show();

	var structtree = Ext
			.getCmp('DJ.System.product.CustProductTreeEdit.CustProductTreeEdit');

	var gridProductWin = Ext
			.getCmp('DJ.System.product.CustProductTreeEdit.CustProductDefList');

	var structStore = structtree.store;
	var rootvalue = {
		expanded : true,
		leaf : false,
		text : record[0].get('fname'),
		id : fid,
		fnumber : record[0].get('fnumber')
	};
	structStore.setRootNode(rootvalue);

	gridProductWin.store.proxy.extraParams.dbfid = fid;

	gridProductWin.store.load();
	
	Ext.getCmp('DJ.System.product.CustproductList').store.load;
}

function custproductprice(btn) {
	var grid = Ext.getCmp("DJ.System.product.CustproductList");
	var record = grid.getSelectionModel().getSelection();
	if (record.length != 1) {
		Ext.MessageBox.alert('提示', '只能选中一条记录进行操作!');
		return;
	}

	var editui = Ext.create('DJ.System.product.CustproductpriceEdit');
	editui.seteditstate("edit");
	editui.setparent('DJ.System.product.CustproductList');
	editui.loadfields(record[0].get("fid"));
	editui.getform().getForm().findField("fcustproductid").setReadOnly(true);
	editui.getform().getForm().findField("username").setReadOnly(true);
	editui.getform().getForm().findField("fcreatetime").setReadOnly(true);
	editui.show();
}

function onUserRelationCustClick(btn) {
	var edit = Ext.create('DJ.System.product.UserRelationCust');
	var txtname=Ext.getCmp('DJ.System.product.UserRelationCustTxtName');
	txtname.setVisible(false);
	edit.show();

}
Ext.require(['DJ.myComponent.button.UploadAccessoryButton','Ext.ux.grid.MySimpleGridContextMenu',
             'Ext.ux.grid.MyGridItemDblClick']);
Ext.define('DJ.System.product.CustproductList.loadupFormpanel', {
	extend : 'Ext.Window',
	id : 'DJ.System.product.CustproductList.loadupFormpanel',
	modal : true,
	title : "Excel文件上传",
	width : 450,// 230, //Window宽度
	height : 150,// 137, //Window高度
	resizable : false,
	closable : true, // 关闭按钮，默认为true
	// renderTo: 'upload',
	items : [{
		xtype : 'form',
		id : 'DJ.System.product.CustproductList.loadupFormpanel.form',
		baseCls : 'x-plain',
		fieldDefaults : {
			labelWidth : 400
		},
		// items:[
		// {
		// title: '上传图片',
		width : 400,
		bodyPadding : 10,
		margin : '20 10 20 20',
		frame : true,

		items : [
			{
				id : 'DJ.System.product.CustproductList.loadupFormpanel.form.fcustomerid',
				name:'fcustomerid',
				fieldLabel : '上传',
				labelWidth : 50,
				msgTarget : 'side',
				allowBlank : false,
				anchor : '83%',
        		fieldLabel : '客户',
        		xtype:'cCombobox',
        		displayField:'fname', // 这个是设置下拉框中显示的值
        	    valueField:'fid', // 这个可以传到后台的值
        	    blankText:'请选择客户',
        	    editable: false, // 可以编辑不
        	    MyConfig : {
 					width : 800,//下拉界面
 					height : 200,//下拉界面
 					url : 'GetCustomerListByUserId.do',  //下拉数据来源控制为当前用户关联过的客户;
 					fields : [ {
 						name : 'fid'
 					}, {
 						name : 'fname',
 						myfilterfield : 't_bd_customer.fname', //查找字段，发送到服务端
 						myfiltername : '客户名称',//在过滤下拉框中显示的名称
 						myfilterable : true//该字段是否查找字段
 					}, {
 						name : 'fnumber'
 					}, {
 						name : 'findustryid'
 					}, {
 						name : 'faddress'
 					}, {
 						name : 'fisinternalcompany',
 						convert:function(value,record)
						{
							if(value=='1')
							{	
								return true;
							}else{
								return false;
							}	
						}
 					} ],
 					columns : [ {
 						text : 'fid',
						dataIndex : 'fid',
						hidden : true,
						sortable : true
					}, {
						text : '包装物名称',
						dataIndex : 'fnumber',
						sortable : true
					}, {
						text : '包装物编号',
						dataIndex : 'fname',
						sortable : true
					}, {
						text : '规格',
						dataIndex : 'findustryid',
						sortable : true
					}, {
						text : '特性',
						dataIndex : 'faddress',
						sortable : true,
						width : 250
					}, {
						text : '材料',
						dataIndex : 'fisinternalcompany',
						xtype:'checkcolumn',
						sortable : true
					}, {
						text : '楞型',
						dataIndex : 'faddress',
						sortable : true
					}, {
						text : '单位',
						dataIndex : 'faddress',
						sortable : true
					}, {
						text : '描述',
						dataIndex : 'faddress',
						sortable : true
					}]
 				}
			},
			{
			xtype : 'filefield',
			name : 'fileName',
			fieldLabel : '上传',
			labelWidth : 50,
			msgTarget : 'side',
			allowBlank : false,
			anchor : '100%',
			regex : /(.)+((\.xlsx)|(\.xls)(\w)?)$/i,
			regexText : "文件格式选择不正确",
			buttonText : '选择文件'
		}],
		
		buttons : [{
			text : '上传',
			handler : function() {
				var form = this.up('form').getForm();
				var fcustomerid = form.findField('fcustomerid').getValue();
				if (form.isValid()) {
					form.submit({
//						url : 'uploadFile.do?action='+encodeURIComponent(fcustomerid),  custGenerateDeliversList
						url : 'saveUploadCustExcelData.do?action='+encodeURIComponent(fcustomerid),  
//						url : '',
//						params : fcustomerid, // 参数
//						timeout : 600000,
						// method:'POST',
						// type:'ajax',
						waitMsg : '正在保存文件...',
						success : function(fp, o) {
							// Ext.Msg.alert('提示信息',
							// '文件成功上传,文件名字为：'+o.result.file);
							Ext.Msg.show({
								title : '提示信息',
								msg : o.result.msg,
								minWidth : 200,
								modal : true,
								buttons : Ext.Msg.OK
							})
							form.findField('fileName').setRawValue('');
							Ext.getCmp("DJ.System.product.CustproductList.loadupFormpanel").close();
							Ext.getCmp("DJ.System.product.CustproductList").store.load();
						},
						failure : function(fp, o) {
							Ext.Msg.show({
								title : '提示信息',
								msg : o.result.msg,
								minWidth : 200,
								modal : true,
								buttons : Ext.Msg.OK
							})
							form.findField('fileName').setRawValue('');
						}
					});
				}
			}
		}]
	// }]
	}]
});

 Ext.define('DJ.System.product.CustproductList', {
			extend : 'Ext.c.GridPanel',
			title : "客户产品管理",
			id : 'DJ.System.product.CustproductList',
			iconCls : 'user',
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			pageSize : 100,
//			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetCustproductList.do',
			Delurl : "DelCustproductList.do",
			EditUI : "DJ.System.product.CustproductEdit",
			exporturl:"custproducttoexcel.do",
			
			plugins : [{
				ptype : 'mygriditemdblclick',
				dbClickHandler : 'button[text=查   看]'
					
			},{
				ptype : 'mysimplegridcontextmenu',
				useExistingButtons : ['button[text=上传附件]','button[text=查   看]']
			}],
			
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
			},
			Action_BeforeAddButtonClick : function(EditUI) {
				// 新增界面弹出前事件
			},
			Action_AfterAddButtonClick : function(EditUI) {
				// 新增界面弹出后事件
				var editui = Ext.getCmp("DJ.System.product.CustproductEdit");
				editui.getform().down("djmultiuploadPanel").hide();
				editui.down("hidden[name=isCreate]").setValue(1);
				
				editui.down("hidden[name=isFromBasePlatforms]").setValue(1);
				
				var editform=Ext.getCmp("DJ.System.product.CustproductEdit").getform().getForm();
				var trees=Ext.getCmp("DJ.System.product.CustproductTreeBaseInfo");	
				var record=trees.getSelectionModel().getSelection();
				if(record.length > 0)
				{
					if(record[0].data.id!=-1)
					{
					editform.findField('fcustomerid').setmyvalue("\"fid\":\""+record[0].data.id+"\",\"fname\":\""+record[0].data.text+"\"");
					}
				}
				
				
				
			},
			Action_BeforeEditButtonClick : function(EditUI) {
				// 修改界面弹出前事件
				
				var record = this.getSelectionModel().getSelection()[0];
				if(record&&record.get('fcharacterid')!=''){
					throw '包装需求产生的客户产品不能操作！';
				}
			},
			Action_AfterEditButtonClick : function(EditUI) {
				var editui = Ext.getCmp("DJ.System.product.CustproductEdit");
				editui.down('textfield[name=ftype]').setValue(1);
				editui.getform().down("djmultiuploadPanel").hide();
				// 修改界面弹出后事件
			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
				var record = this.getSelectionModel().getSelection();
				for(var i=0;i<record.length;i++){
					if(record[i].get('fcharacterid')!=''){
						throw '包装需求产生的客户产品不能操作！';
					}
				}
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			},
			Action_AfterViewButtonClick : function(){
				var editui = Ext.getCmp("DJ.System.product.CustproductEdit");
				editui.getform().down("djmultiuploadPanel").hide();
			},
			custbar : [{
				text : '关联产品',
				height : 30,
				handler : onCustRelationProductClick
					}, {
						// id : 'DelButton',
						text : '结算价',
						height : 30,
						handler : custproductprice
						}, {
						// id : 'DelButton',
						text : '产品过滤',
						height : 30,
						handler : onUserRelationCustClick
						}, {
							text : '编辑客户产品结构',
							height : 30,
							handler : editCustproductTreeLink
	
					},{
						// id : 'DelButton',
						text : 'Excel文件上传',
						height : 30,
						handler : function() {
							var loadupFormpanel = Ext.getCmp("DJ.System.product.CustproductList.loadupFormpanel");
							if (loadupFormpanel == null) {
								loadupFormpanel = Ext.create("DJ.System.product.CustproductList.loadupFormpanel");
							}
							var grid = Ext.getCmp("DJ.System.product.CustproductList");
							var el = grid.getEl();
							el.mask("系统处理中,请稍候……");
							Ext.Ajax.request({
								timeout : 600000,
								url : "GetCustomerListByUserId.do",
//								params : {
//									fids : ids
//								}, // 参数
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										if(obj.data.length==1){
											var cform = Ext
												.getCmp("DJ.System.product.CustproductList.loadupFormpanel.form")
												.getForm();
										cform.findField('fcustomerid').setmyvalue("\"fid\":\""
												+ obj.data[0].fid + "\",\"fname\":\""
												+ obj.data[0].fname + "\"");
										}
									} else {
									}
									el.unmask();
								}
							});
							loadupFormpanel.show();
						}
					},{
						text:'客户产品模板下载',
						handler : function() {
							window.open('downloadCustExcel.do','下载模板')
							}
					},{
						xtype : "uploadaccessorybutton",
						fileUrl : "uploadCustProductImg.do",
						gridId : "DJ.System.product.CustproductList",
						fileGridId : 'DJ.System.product.CustproductAccessoryList',
						filedName : "fid"
					},{
						text:'禁用',
						handler:function(){
							var records = Ext.getCmp('DJ.System.product.CustproductList').getSelectionModel().getSelection();
							var fids = '';
							for(var i = 0;i<records.length;i++){
								fids += records[i].get('fid');
								if(i<records.length-1){
									fids += ",";
								}
							}
						var el = Ext.getCmp('DJ.System.product.CustproductList').getEl();
						el.mask("系统处理中,请稍候……");
						Ext.Ajax.request({
							url: 'custProductFeffect.do',
							params:{
								'fids':fids,
								'type':0
								},
							success: function(response, opts) {
							     var obj = Ext.decode(response.responseText);
							     if(obj.success==true){
							    	 djsuccessmsg(obj.msg);
							    	 Ext.getCmp('DJ.System.product.CustproductList').getStore().load();
							     }else{
							    	 Ext.MessageBox.alert('提示',obj.msg);
							     }
							     el.unmask();
							    }
							});
					}
					},{
						text:'启用',
						handler:function(){
							var records = Ext.getCmp('DJ.System.product.CustproductList').getSelectionModel().getSelection();
							var fids = '';
							for(var i = 0;i<records.length;i++){
								fids += records[i].get('fid');
								if(i<records.length-1){
									fids += ",";
								}
							}
						Ext.Ajax.request({
							url: 'custProductFeffect.do',
							params:{
								'fids':fids,
								'type':1
								},
							success: function(response, opts) {
							     var obj = Ext.decode(response.responseText);
							     if(obj.success==true){
							    	 djsuccessmsg(obj.msg);
							    	 Ext.getCmp('DJ.System.product.CustproductList').getStore().load();
							     }else{
							    	 Ext.MessageBox.alert('提示',obj.msg);
							     }
							    }
							});
					}},{
						text:'禁止匹配',
						handler:function(){
							var me = this;
							var record = me.up('grid').getSelectionModel().getSelection();
							var fids = '';
							if(record.length==0){
								 Ext.MessageBox.alert('提示','请选择数据！');
								 return;
							}
							for(var i = 0;i<record.length;i++){
								fids += record[i].get('fid');
								if(i<record.length-1){
									fids += ',';
								}
							}
							Ext.Ajax.request({
								url:'cleanProductmatching.do',
								params:{fids:fids},
								success: function(response, opts) {
								     var obj = Ext.decode(response.responseText);
								     if(obj.success==true){
								    	 djsuccessmsg(obj.msg);
								    	 me.up('grid').getStore().load();
								     }else{
								    	 Ext.MessageBox.alert('提示',obj.msg);
								     }
								    }
							})
						}
					}],
					listeners : {
						itemclick : function(com, record, item, index, e, eOpts) {
							Ext.getCmp("DJ.System.product.CustproductAccessoryList")
							.getStore().loadPage(1, {
								params : {

									fcusproductid : record.get("fid")
								}

							});
						}
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
					name : 'fspec'
				}, {
					name : 'forderunit'
				}, {
					name : 'fcustomerid'
				}, {
					name : 'fdescription'
				}, {
					name : 'fcreatorid'
				}, {
					name : 'fcreatetime'
				}, {
					name : 'flastupdateuserid'
				}, {
					name : 'flastupdatetime'
				}, {
					name : 'Frelationed'
				}, {
					name : 'ftilemodel'
				}, {
					name : 'fmaterial'
				}, {
					name : 'fcharactername'
				}, {
					name : 'fcharacterid'
					},{
						name:'feffect'
					}],
				columns : [ Ext.create('DJ.Base.Grid.GridRowNum'), 
	            {
					'header' : 'fid',
					'dataIndex' : 'fid',
					hidden : true,
					hideable : false,
					autoHeight: true, 
					autoWidth:true,
					sortable : true
				},{
					'header' : 'fcharacterid',
					'dataIndex' : 'fcharacterid',
					hidden : true,
					hideable : false,
					sortable : true
				}, {
					'header' : '产品名称',
					'dataIndex' : 'fname',
					
					sortable : true
				}, {
					'header' : '编码',
					'dataIndex' : 'fnumber',
					sortable : true
				}, {
					'header' : '关联',
					width : 40,
					'dataIndex' : 'Frelationed',
					sortable : true,
					renderer: function(value){
					        if (value == 1) {
					            return '是';
					        }
					        else{
					        	return '否';
					        }
					    }
				}, {
					'header' : '规格',
					width : 70,
					'dataIndex' : 'fspec',
					sortable : true
				}, {
					'header' : '单位',
					width : 70,
					'dataIndex' : 'forderunit',
					sortable : true
				}, {
					'header' : '客户',
					hidden : true,
					'dataIndex' : 'fcustomerid',
					sortable : true
				}, {
					'header' : '材料',
					width : 70,
					'dataIndex' : 'fmaterial',
					sortable : true
				}, {
					'header' : '楞型',
					'dataIndex' : 'ftilemodel',
					width : 70,
					sortable : true
				}, {
					'header' : '特性',
					'dataIndex' : 'fcharactername',
					width : 140,
					sortable : true
				},{
					header:'状态',
					dataIndex:'feffect',
					sortable : true,
					renderer:function(val){
						if(val==1){
							return '启用'
						}else{
							return '禁用'
						}
					}
				
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


//
//Ext.require([ 'Ext.grid.*', 'Ext.toolbar.Paging', 'Ext.util.*', 'Ext.data.*',
//		'Ext.ux.form.SearchField', 'Ext.selection.CheckboxModel',
//		'Ext.ux.grid.FiltersFeature', 'Ext.ux.ajax.SimManager']);
//var custname = "";
//var filters = {
//		ftype : 'filters',
//		encode : true, // json encode the filter query
//		local : false, // defaults to false (remote filtering)
//		filters : [{
//					type : 'boolean',
//					dataIndex : 'visible'
//				}]
//	};
//var tbar = Ext.create("Ext.Toolbar", {
//	height : 40,
//	items : [ {
//		// id : 'AddButton',
//		height : 40,
//		text : '新  增',
//		handler : onAddButtonClick
//	}, {
//		// id : 'SaveButton',
//		text : '修   改',
//		height : 40,
//		handler : onEditButtonClick
//	}, {
//		// id : 'DelButton',
//		text : '删    除',
//		height : 40,
//		handler : onDelButtonClick
//	}, {
//		// id : 'RefreshButton',
//		text : '刷    新',
//		height : 40,
//		handler : onRefreshButtonClick
//	}, {
//	// id : 'RefreshButton',
//		text : '关联产品',
//		height : 40,
//		handler : onCustRelationProductClick
//	}
////	{
////		// id : 'RefreshButton',
////		text : '启    用',
////		height : 40,
////		handler : onEffectButtonClick
////	}, {
////		// id : 'RefreshButton',
////		text : '禁    用',
////		height : 40,
////		handler : onEffectButtonClick
////	}
//	]
//});
//function onRefreshButtonClick(btn) {
//	// MainMenuTreePanel.store.load();
//	// Ext.MessageBox.alert('提示', '刷新')
//	// var
//	// grid=Ext.getCmp("DJ.System.UserList");//Ext.getCmp("DJ.System.UserListPanel")
//	// var record = grid.getSelectionModel().getSelection();
//	// if (record.length == 0) {
//	// Ext.MessageBox.show({
//	// title : "提示",
//	// msg : "请先选择您要操作的行!"
//	// // icon: Ext.MessageBox.INFO
//	// })
//	// return;
//	// } else {
//	// var ids = "";
//	// for ( var i = 0; i < record.length; i++) {
//	// ids += record[i].get("fid")
//	// if (i < record.length - 1) {
//	// ids = ids + ",";
//	// }
//	// }
//	// Ext.MessageBox.show({
//	// title : "所选ID列表",
//	// msg : ids
//	// // icon: Ext.MessageBox.INFO
//	// })
//	// }
//	Ext.getCmp("DJ.System.product.CustproductList").store.load();
//
//}
//function onAddButtonClick(btn) {
//	// Ext.MessageBox.alert('提示', '新增')
//	var CustproductEdit = Ext.create('DJ.System.product.CustproductEdit');
//	
//	CustproductEdit.show();
//	//新增自动把客户树ID赋给Edit的客店;
//	
//	var record=Ext.getCmp("DJ.System.product.CustproductTree").getSelectionModel().getSelection();
//	if(record.length > 0)
//	{
//		if(record[0].data != null && record[0].data.id!="-1")
//		  {
//			  customerid=record[0].data.id;
//			  customerName=record[0].data.text;
//			  var cforms=Ext.getCmp("DJ.System.product.CustproductEdit.CForm").getForm();
////			  cforms.findField('fcustomerid').setReadOnly( {readOnly : true} );
////			  cforms.findField('fcustomerid').setValue(customerid);
////			  cforms.findField('fcustomerid').setRawValue(customerName);
//			  cforms.findField('fcustomerid').setmyvalue("\"fid\":\""+record[0].data.id+"\",\"fname\":\""+record[0].data.text+"\"");
//			 
//		  }
//	}
//}
//function onEditButtonClick(btn) {
//	// Ext.MessageBox.alert('提示', '修改');
//	var grid = Ext.getCmp("DJ.System.product.CustproductList");// Ext.getCmp("DJ.System.UserListPanel")
//	var record = grid.getSelectionModel().getSelection();
//	if (record.length != 1) {
//		Ext.MessageBox.alert('提示', '只能选中一条记录进行修改!');
//		return;
//	}
//	var fid = record[0].get("fid");
////	var custname = "";
//	Ext.Ajax
//			.request({
//				url : "GetCustproductInfo.do",
//				params : {
//					Custproductfid : fid
//				}, // 参数
//				success : function(response, option) {
//					var obj = Ext.decode(response.responseText);
//					if (obj.success == true) {
////						if (obj.data[0].fusedstatus == '1') {
////							Ext.MessageBox.alert('错误', '不能修改已经启用的供应商!');
////							return;
////						}
////						custname = obj.data[0].custname;
//						var CustproductEdit = Ext.create('DJ.System.product.CustproductEdit');
//						
//						var cform=Ext.getCmp("DJ.System.product.CustproductEdit.CForm").getForm();
//						cform.setValues(obj.data[0]);
////						cform.findField('fcustomerid').store.load();
////						cform.findField('fcustomerid').setValue(obj.data[0].fid);
////						cform.findField('fcustomerid').setRawValue(obj.data[0].fcustname);
//						cform.findField('fcustomerid').setmyvalue("\"fid\":\""+obj.data[0].fid+"\",\"fname\":\""+obj.data[0].fcustname+"\"");
//						CustproductEdit.show();
//						
//					} else {
//						Ext.MessageBox.alert('错误', obj.msg);
//					}
//				}
//			});
//}
//
//function onDelButtonClick(btn) {
//	// Ext.MessageBox.alert('提示', '删除');
//	var grid = Ext.getCmp("DJ.System.product.CustproductList");// Ext.getCmp("DJ.System.UserListPanel")
//	var record = grid.getSelectionModel().getSelection();
//	if (record.length == 0) {
//		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
//		return;
//	}
//	Ext.MessageBox
//			.confirm(
//					"提示",
//					"是否确认删除选中的内容?",
//					function(btn) {
//						if (btn == 'yes') {
//							var ids = "(";
//							for ( var i = 0; i < record.length; i++) {
//								var fid = record[i].get("fid");
////								if (Ext.util.Format.trim(fid) == "0f20f5bf-a80b-11e2-b222-60a44c5bbef3") {
////									Ext.MessageBox.alert('提示', '不能删除超级用户!');
////									return;
////								}
////								if(record[i].get("fusedstatus") == "1"){
////									Ext.MessageBox.alert('提示', '不能删除已启用供应商!');
////									return;
////								}
//								ids += "'" + fid + "'";
//								if (i < record.length - 1) {
//									ids = ids + ",";
//								}
//							}
//							ids = ids + ")";
//							Ext.Ajax
//									.request({
//										url : "DelCustproductList.do",
//										params : {
//											fidcls : ids
//										}, // 参数
//										success : function(response, option) {
//											var obj = Ext
//													.decode(response.responseText);
//											if (obj.success == true) {
//												Ext.MessageBox.alert('成功',
//														obj.msg);
//												grid.store.load();
//											} else {
//												Ext.MessageBox.alert('错误',
//														obj.msg);
//											}
//										}
//									});
//						}
//					});
//}
//function onCustRelationProductClick(btn)
//{
//	var grid = Ext.getCmp("DJ.System.product.CustproductList");
//	var record = grid.getSelectionModel().getSelection();
//	if (record.length != 1) {
//		Ext.MessageBox.alert('提示', '只能选中一条记录进行关联!');
//		return;
//	}
//	if(record[0].get("fcustomerid")=="null"||record[0].get("fcustomerid")=="")
//	{
//		Ext.MessageBox.alert('提示', '请先设置对应的客户或数据不同步请刷新！');
//		return;
//	}
//var edit=Ext.create('DJ.System.product.RelationProduct');
//	edit.settxtid(record[0].get("fid"));
//	edit.settxtvalue(record[0].get("fname"));
//	edit.show();
//	Ext.getCmp("DJ.System.product.CustproductList").store.load();
//}
//// Ext.Ajax.request({
//// url : 'GetUserList.do',
//// params : {
//// action : "query"
//// },
//// method : 'POST',
//// success : function(response) {
//// var obj = Ext.JSON.decode(response.responseText); // 获得后台传递json
////
//// var store = Ext.create('Ext.data.Store', {
//// fields : obj.fieldsNames,// 把json的fieldsNames赋给fields
//// data : obj.data
//// // 把json的data赋给data
//// }); //Ext.getCmp
//// var gridlist=Ext.getCmp("DJ.System.UserListPanel");
//// if (gridlist != null) {
//// Ext.getCmp("DJ.System.UserListPanel").reconfigure(store, obj.columModle); //
//// 定义grid的store和column
//// Ext.getCmp("DJ.System.UserListPanel").render();
//// }
////
//// }
//// });
//
//// Ext.define('DJ.System.UserList', {
//// extend : 'Ext.grid.Panel',
//// id : 'DJ.System.UserList',
//// closable : true,// 是否现实关闭按钮,默认为false
//// title : "用户管理",
//// emptyMsg : "没有数据显示",
//// tbar : tbar,
//// // bodyStyle : "width:100%",
//// // viewConfig : {
//// // forceFit : true
//// // },
//// // columnLines : true,
//// enableColumnMove:true,//是否允许拖放列，默认为true
//// enableColumnResize : true,// 是否允许改变列宽，默认为true
//// //autoExpandColumn:"memory",
//// items : [{fieldsNames:[{name: 'fid'},{name: 'fname'},{name: 'feffect'},{name:
//// 'fcustomername'},{name:'femail'},{name:'ftel'},{name:'fcreatetime'}]}],
//// columns : []
//// });
//
//// 数据类型分为以下几种：
//// 1、auto（默认）
//// 2、string
//// 3、int
//// 4、float
//// 5、boolean
//// 6、date
//// 创建多选
//var selModel = Ext.create('Ext.selection.CheckboxModel');
//var userstore = Ext.create('Ext.data.Store', {
//	fields : [ {
//		name : 'fid'
//	}, {
//		name : 'fname'
//	}, {
//		name : 'fnumber'
//	}, {
//		name : 'fspec'
//	}, {
//		name : 'forderunit'
//	}, {
//		name : 'fcustomerid'
//	}, {
//		name : 'fdescription'
//	}, {
//		name : 'fcreatorid'
//	}, {
//		name : 'fcreatetime'
//	}, {
//		name : 'flastupdateuserid'
//	}, {
//		name : 'flastupdatetime'
//	}, {
//		name : 'Frelationed'
//	}],
//	pageSize : 100,
//	proxy : {
//		type : 'ajax',
//		// data : provice,
//		url : 'GetCustproductList.do',
//		extraParams : {fcustomerid:'' },
//		reader : {
//			type : 'json',
//			root : 'data',
//			totalProperty : 'total'
//		// totalProperty : 'total'
//		}
//	},
//	autoLoad : true
//});
//// var PageRowNumberer = Ext.extend(Ext.grid.RowNumberer, {
//// width : 40,
//// renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
//// if (store.lastOptions.params != null) {
//// var pageindex = store.lastOptions.params.start;
//// return pageindex + rowIndex + 1;
//// } else {
//// return rowIndex + 1;
//// }
//// }
//// });
////定义渲染函数，格式化性别显示  
//function formatfusedstatus(value){
//    return value=='1'?'启用':'禁用';  
//}
//Ext.define('DJ.System.product.CustproductList', {
//	extend : 'Ext.grid.Panel',
//	id : 'DJ.System.product.CustproductList',
//	// // Use a PagingGridScroller (this is interchangeable with a
//	// PagingToolbar)
//	// verticalScrollerType: 'paginggridscroller',
//	// // do not reset the scrollbar when the view refreshs
//	// invalidateScrollerOnRefresh: false,
//	// // infinite scrolling does not support selection
//	// disableSelection: true,
//	selModel : selModel,
//	features : [filters],
//	columnLines : true,
//	disableSelection : false,// 值为TRUE，表示禁止选择行
//	closable : true,// 是否现实关闭按钮,默认为false
//	title : "客户产品",
//	emptyMsg : "没有数据显示",
//	tbar : tbar,
//	dockedItems : [ {
//		xtype : 'pagingtoolbar',
//		store : userstore, // same store GridPanel is using
//		dock : 'bottom',
//		displayInfo : true,
//		items : ['->', {
//			text : '查询条件',
//			tooltip : 'Get Filter Data for Grid',
//			handler : function() {
//				var data = Ext.encode(Ext.getCmp('DJ.System.product.CustproductList').filters.getFilterData());
//				Ext.Msg.alert('All Filter Data', data);
//			}
//		}, {
//			text : '显示全部',
//			handler : function() {
//				Ext.getCmp('DJ.System.product.CustproductList').filters.clearFilters();
//			}
//		}]
//	} ],
//	autoExpandColumn : "fcustomername",
//	store : userstore,
//	//autoHeight: true, autoWidth:true,
//	columns : [ Ext.create('DJ.Base.Grid.GridRowNum'), 
//	            {
//					'header' : 'fid',
//					'dataIndex' : 'fid',
//					hidden : true,
//					hideable : false,
//					autoHeight: true, autoWidth:true,
//					sortable : true
//				}, {
//					'header' : '产品名称',
//					'dataIndex' : 'fname',
//					sortable : true,
//					filter : {
//						type : 'string'
//					}
//				}, {
//					'header' : '编码',
//					'dataIndex' : 'fnumber',
//					sortable : true,
//					filter : {
//						type : 'string'
//					}
//				}, {
//					'header' : '关联',
//					width : 40,
//					'dataIndex' : 'Frelationed',
//					sortable : true,
//					renderer: function(value){
//					        if (value == 1) {
//					            return '是';
//					        }
//					        else{
//					        	return '否';
//					        }
//					    },
//					filter : {
//						type : 'string'
//					}
//				}, {
//					'header' : '规格',
//					width : 70,
//					'dataIndex' : 'fspec',
//					sortable : true
//				}, {
//					'header' : '单位',
//					width : 70,
//					'dataIndex' : 'forderunit',
//					sortable : true
//				}, {
//					'header' : '客户',
//					hidden : true,
//					'dataIndex' : 'fcustomerid',
//					sortable : true
//				}, {
//					'header' : '修改时间',
//					'dataIndex' : 'flastupdatetime',
//					filter : {
//						type : 'datetime',
//						date: {
//				            format: 'Y-m-d'
//				        },
//				        time: {
//				            format: 'H:i:s A',
//				            increment: 1
//				        }
//					},
//					width : 140,
//					sortable : true
//				}, {
//					'header' : '创建时间',
//					'dataIndex' : 'fcreatetime',
//					filter : {
//						type : 'datetime',
//						date: {
//				            format: 'Y-m-d'
//				        },
//				        time: {
//				            format: 'H:i:s A',
//				            increment: 1
//				        }
//					},
//					width : 140,
//					sortable : true
//				}, {
//					'header' : '描述',
//					hidden : true,
//					'dataIndex' : 'fdescription',
//					sortable : true
//				}
//			]
//});
//
//
